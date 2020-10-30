package com.squareup.okhttp.internal.spdy;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Http2 implements Variant {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(FrameLogger.class.getName());
    /* access modifiers changed from: private */
    public static final ByteString b = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    static final class ContinuationSource implements Source {
        int a;
        byte b;
        int c;
        int d;
        short e;
        private final BufferedSource f;

        public void close() {
        }

        public ContinuationSource(BufferedSource bufferedSource) {
            this.f = bufferedSource;
        }

        public long read(Buffer buffer, long j) {
            while (this.d == 0) {
                this.f.skip((long) this.e);
                this.e = 0;
                if ((this.b & 4) != 0) {
                    return -1;
                }
                a();
            }
            long read = this.f.read(buffer, Math.min(j, (long) this.d));
            if (read == -1) {
                return -1;
            }
            this.d = (int) (((long) this.d) - read);
            return read;
        }

        public Timeout timeout() {
            return this.f.timeout();
        }

        private void a() {
            int i = this.c;
            int a2 = Http2.b(this.f);
            this.d = a2;
            this.a = a2;
            byte readByte = (byte) (this.f.readByte() & UnsignedBytes.MAX_VALUE);
            this.b = (byte) (this.f.readByte() & UnsignedBytes.MAX_VALUE);
            if (Http2.a.isLoggable(Level.FINE)) {
                Http2.a.fine(FrameLogger.a(true, this.c, this.a, readByte, this.b));
            }
            this.c = this.f.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            if (readByte != 9) {
                throw Http2.d("%s != TYPE_CONTINUATION", Byte.valueOf(readByte));
            } else if (this.c != i) {
                throw Http2.d("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }
    }

    static final class FrameLogger {
        private static final String[] a = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        private static final String[] b = new String[64];
        private static final String[] c = new String[256];

        FrameLogger() {
        }

        static String a(boolean z, int i, int i2, byte b2, byte b3) {
            String format = b2 < a.length ? a[b2] : String.format("0x%02x", new Object[]{Byte.valueOf(b2)});
            String a2 = a(b2, b3);
            String str = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            objArr[0] = z ? "<<" : ">>";
            objArr[1] = Integer.valueOf(i);
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = format;
            objArr[4] = a2;
            return String.format(str, objArr);
        }

        static String a(byte b2, byte b3) {
            String str;
            if (b3 == 0) {
                return "";
            }
            switch (b2) {
                case 2:
                case 3:
                case 7:
                case 8:
                    return c[b3];
                case 4:
                case 6:
                    return b3 == 1 ? "ACK" : c[b3];
                default:
                    if (b3 < b.length) {
                        str = b[b3];
                    } else {
                        str = c[b3];
                    }
                    if (b2 != 5 || (b3 & 4) == 0) {
                        return (b2 != 0 || (b3 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED");
                    }
                    return str.replace("HEADERS", "PUSH_PROMISE");
            }
        }

        static {
            int[] iArr;
            for (int i = 0; i < c.length; i++) {
                c[i] = String.format("%8s", new Object[]{Integer.toBinaryString(i)}).replace(TokenParser.SP, TarjetasConstants.ULT_NUM_AMEX);
            }
            b[0] = "";
            b[1] = "END_STREAM";
            int[] iArr2 = {1};
            b[8] = "PADDED";
            for (int i2 : iArr2) {
                String[] strArr = b;
                int i3 = i2 | 8;
                StringBuilder sb = new StringBuilder();
                sb.append(b[i2]);
                sb.append("|PADDED");
                strArr[i3] = sb.toString();
            }
            b[4] = "END_HEADERS";
            b[32] = "PRIORITY";
            b[36] = "END_HEADERS|PRIORITY";
            for (int i4 : new int[]{4, 32, 36}) {
                for (int i5 : iArr2) {
                    String[] strArr2 = b;
                    int i6 = i5 | i4;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(b[i5]);
                    sb2.append('|');
                    sb2.append(b[i4]);
                    strArr2[i6] = sb2.toString();
                    String[] strArr3 = b;
                    int i7 = i6 | 8;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(b[i5]);
                    sb3.append('|');
                    sb3.append(b[i4]);
                    sb3.append("|PADDED");
                    strArr3[i7] = sb3.toString();
                }
            }
            for (int i8 = 0; i8 < b.length; i8++) {
                if (b[i8] == null) {
                    b[i8] = c[i8];
                }
            }
        }
    }

    static final class Reader implements FrameReader {
        final Reader a;
        private final BufferedSource b;
        private final ContinuationSource c = new ContinuationSource(this.b);
        private final boolean d;

        Reader(BufferedSource bufferedSource, int i, boolean z) {
            this.b = bufferedSource;
            this.d = z;
            this.a = new Reader(i, this.c);
        }

        public void readConnectionPreface() {
            if (!this.d) {
                ByteString readByteString = this.b.readByteString((long) Http2.b.size());
                if (Http2.a.isLoggable(Level.FINE)) {
                    Http2.a.fine(String.format("<< CONNECTION %s", new Object[]{readByteString.hex()}));
                }
                if (!Http2.b.equals(readByteString)) {
                    throw Http2.d("Expected a connection header but was %s", readByteString.utf8());
                }
            }
        }

        public boolean nextFrame(Handler handler) {
            try {
                this.b.require(9);
                int a2 = Http2.b(this.b);
                if (a2 < 0 || a2 > 16384) {
                    throw Http2.d("FRAME_SIZE_ERROR: %s", Integer.valueOf(a2));
                }
                byte readByte = (byte) (this.b.readByte() & UnsignedBytes.MAX_VALUE);
                byte readByte2 = (byte) (this.b.readByte() & UnsignedBytes.MAX_VALUE);
                int readInt = this.b.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
                if (Http2.a.isLoggable(Level.FINE)) {
                    Http2.a.fine(FrameLogger.a(true, readInt, a2, readByte, readByte2));
                }
                switch (readByte) {
                    case 0:
                        b(handler, a2, readByte2, readInt);
                        break;
                    case 1:
                        a(handler, a2, readByte2, readInt);
                        break;
                    case 2:
                        c(handler, a2, readByte2, readInt);
                        break;
                    case 3:
                        d(handler, a2, readByte2, readInt);
                        break;
                    case 4:
                        e(handler, a2, readByte2, readInt);
                        break;
                    case 5:
                        f(handler, a2, readByte2, readInt);
                        break;
                    case 6:
                        g(handler, a2, readByte2, readInt);
                        break;
                    case 7:
                        h(handler, a2, readByte2, readInt);
                        break;
                    case 8:
                        i(handler, a2, readByte2, readInt);
                        break;
                    default:
                        this.b.skip((long) a2);
                        break;
                }
                return true;
            } catch (IOException unused) {
                return false;
            }
        }

        private void a(Handler handler, int i, byte b2, int i2) {
            short s = 0;
            if (i2 == 0) {
                throw Http2.d("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean z = (b2 & 1) != 0;
            if ((b2 & 8) != 0) {
                s = (short) (this.b.readByte() & UnsignedBytes.MAX_VALUE);
            }
            if ((b2 & 32) != 0) {
                a(handler, i2);
                i -= 5;
            }
            handler.headers(false, z, i2, -1, a(Http2.b(i, b2, s), s, b2, i2), HeadersMode.HTTP_20_HEADERS);
        }

        private List<Header> a(int i, short s, byte b2, int i2) {
            ContinuationSource continuationSource = this.c;
            this.c.d = i;
            continuationSource.a = i;
            this.c.e = s;
            this.c.b = b2;
            this.c.c = i2;
            this.a.a();
            return this.a.b();
        }

        private void b(Handler handler, int i, byte b2, int i2) {
            boolean z = true;
            short s = 0;
            boolean z2 = (b2 & 1) != 0;
            if ((b2 & 32) == 0) {
                z = false;
            }
            if (z) {
                throw Http2.d("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            if ((b2 & 8) != 0) {
                s = (short) (this.b.readByte() & UnsignedBytes.MAX_VALUE);
            }
            handler.data(z2, i2, this.b, Http2.b(i, b2, s));
            this.b.skip((long) s);
        }

        private void c(Handler handler, int i, byte b2, int i2) {
            if (i != 5) {
                throw Http2.d("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw Http2.d("TYPE_PRIORITY streamId == 0", new Object[0]);
            } else {
                a(handler, i2);
            }
        }

        private void a(Handler handler, int i) {
            int readInt = this.b.readInt();
            handler.priority(i, readInt & SubsamplingScaleImageView.TILE_SIZE_AUTO, (this.b.readByte() & UnsignedBytes.MAX_VALUE) + 1, (Integer.MIN_VALUE & readInt) != 0);
        }

        private void d(Handler handler, int i, byte b2, int i2) {
            if (i != 4) {
                throw Http2.d("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw Http2.d("TYPE_RST_STREAM streamId == 0", new Object[0]);
            } else {
                int readInt = this.b.readInt();
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt);
                if (fromHttp2 == null) {
                    throw Http2.d("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(readInt));
                } else {
                    handler.rstStream(i2, fromHttp2);
                }
            }
        }

        private void e(Handler handler, int i, byte b2, int i2) {
            if (i2 != 0) {
                throw Http2.d("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((b2 & 1) != 0) {
                if (i != 0) {
                    throw Http2.d("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                handler.ackSettings();
            } else if (i % 6 != 0) {
                throw Http2.d("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
            } else {
                Settings settings = new Settings();
                for (int i3 = 0; i3 < i; i3 += 6) {
                    short readShort = this.b.readShort();
                    int readInt = this.b.readInt();
                    switch (readShort) {
                        case 1:
                        case 6:
                            break;
                        case 2:
                            if (!(readInt == 0 || readInt == 1)) {
                                throw Http2.d("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case 3:
                            readShort = 4;
                            break;
                        case 4:
                            readShort = 7;
                            if (readInt >= 0) {
                                break;
                            } else {
                                throw Http2.d("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            }
                        case 5:
                            if (readInt >= 16384 && readInt <= 16777215) {
                                break;
                            } else {
                                throw Http2.d("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(readInt));
                            }
                            break;
                        default:
                            throw Http2.d("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(readShort));
                    }
                    settings.a(readShort, 0, readInt);
                }
                handler.settings(false, settings);
                if (settings.c() >= 0) {
                    this.a.a(settings.c());
                }
            }
        }

        private void f(Handler handler, int i, byte b2, int i2) {
            short s = 0;
            if (i2 == 0) {
                throw Http2.d("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            if ((b2 & 8) != 0) {
                s = (short) (this.b.readByte() & UnsignedBytes.MAX_VALUE);
            }
            handler.pushPromise(i2, this.b.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO, a(Http2.b(i - 4, b2, s), s, b2, i2));
        }

        private void g(Handler handler, int i, byte b2, int i2) {
            boolean z = false;
            if (i != 8) {
                throw Http2.d("TYPE_PING length != 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw Http2.d("TYPE_PING streamId != 0", new Object[0]);
            } else {
                int readInt = this.b.readInt();
                int readInt2 = this.b.readInt();
                if ((b2 & 1) != 0) {
                    z = true;
                }
                handler.ping(z, readInt, readInt2);
            }
        }

        private void h(Handler handler, int i, byte b2, int i2) {
            if (i < 8) {
                throw Http2.d("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw Http2.d("TYPE_GOAWAY streamId != 0", new Object[0]);
            } else {
                int readInt = this.b.readInt();
                int readInt2 = this.b.readInt();
                int i3 = i - 8;
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt2);
                if (fromHttp2 == null) {
                    throw Http2.d("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(readInt2));
                }
                ByteString byteString = ByteString.EMPTY;
                if (i3 > 0) {
                    byteString = this.b.readByteString((long) i3);
                }
                handler.goAway(readInt, fromHttp2, byteString);
            }
        }

        private void i(Handler handler, int i, byte b2, int i2) {
            if (i != 4) {
                throw Http2.d("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
            }
            long readInt = ((long) this.b.readInt()) & 2147483647L;
            if (readInt == 0) {
                throw Http2.d("windowSizeIncrement was 0", Long.valueOf(readInt));
            } else {
                handler.windowUpdate(i2, readInt);
            }
        }

        public void close() {
            this.b.close();
        }
    }

    static final class Writer implements FrameWriter {
        private final BufferedSink a;
        private final boolean b;
        private final Buffer c = new Buffer();
        private final Writer d = new Writer(this.c);
        private int e = 16384;
        private boolean f;

        Writer(BufferedSink bufferedSink, boolean z) {
            this.a = bufferedSink;
            this.b = z;
        }

        public synchronized void flush() {
            if (this.f) {
                throw new IOException("closed");
            }
            this.a.flush();
        }

        public synchronized void ackSettings(Settings settings) {
            if (this.f) {
                throw new IOException("closed");
            }
            this.e = settings.d(this.e);
            a(0, 0, 4, 1);
            this.a.flush();
        }

        public synchronized void connectionPreface() {
            if (this.f) {
                throw new IOException("closed");
            } else if (this.b) {
                if (Http2.a.isLoggable(Level.FINE)) {
                    Http2.a.fine(String.format(">> CONNECTION %s", new Object[]{Http2.b.hex()}));
                }
                this.a.write(Http2.b.toByteArray());
                this.a.flush();
            }
        }

        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) {
            if (z2) {
                try {
                    throw new UnsupportedOperationException();
                } catch (Throwable th) {
                    throw th;
                }
            } else if (this.f) {
                throw new IOException("closed");
            } else {
                a(z, i, list);
            }
        }

        public synchronized void synReply(boolean z, int i, List<Header> list) {
            if (this.f) {
                throw new IOException("closed");
            }
            a(z, i, list);
        }

        public synchronized void headers(int i, List<Header> list) {
            if (this.f) {
                throw new IOException("closed");
            }
            a(false, i, list);
        }

        public synchronized void pushPromise(int i, int i2, List<Header> list) {
            if (this.f) {
                throw new IOException("closed");
            } else if (this.c.size() != 0) {
                throw new IllegalStateException();
            } else {
                this.d.a(list);
                long size = this.c.size();
                int min = (int) Math.min((long) (this.e - 4), size);
                long j = (long) min;
                a(i, min + 4, 5, size == j ? (byte) 4 : 0);
                this.a.writeInt(i2 & SubsamplingScaleImageView.TILE_SIZE_AUTO);
                this.a.write(this.c, j);
                if (size > j) {
                    a(i, size - j);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z, int i, List<Header> list) {
            if (this.f) {
                throw new IOException("closed");
            } else if (this.c.size() != 0) {
                throw new IllegalStateException();
            } else {
                this.d.a(list);
                long size = this.c.size();
                int min = (int) Math.min((long) this.e, size);
                long j = (long) min;
                byte b2 = size == j ? (byte) 4 : 0;
                if (z) {
                    b2 = (byte) (b2 | 1);
                }
                a(i, min, 1, b2);
                this.a.write(this.c, j);
                if (size > j) {
                    a(i, size - j);
                }
            }
        }

        private void a(int i, long j) {
            while (j > 0) {
                int min = (int) Math.min((long) this.e, j);
                long j2 = (long) min;
                long j3 = j - j2;
                a(i, min, 9, j3 == 0 ? (byte) 4 : 0);
                this.a.write(this.c, j2);
                j = j3;
            }
        }

        public synchronized void rstStream(int i, ErrorCode errorCode) {
            if (this.f) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            } else {
                a(i, 4, 3, 0);
                this.a.writeInt(errorCode.httpCode);
                this.a.flush();
            }
        }

        public int maxDataLength() {
            return this.e;
        }

        public synchronized void data(boolean z, int i, Buffer buffer, int i2) {
            if (this.f) {
                throw new IOException("closed");
            }
            byte b2 = 0;
            if (z) {
                b2 = (byte) 1;
            }
            a(i, b2, buffer, i2);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, byte b2, Buffer buffer, int i2) {
            a(i, i2, 0, b2);
            if (i2 > 0) {
                this.a.write(buffer, (long) i2);
            }
        }

        public synchronized void settings(Settings settings) {
            if (this.f) {
                throw new IOException("closed");
            }
            int i = 0;
            a(0, settings.b() * 6, 4, 0);
            while (i < 10) {
                if (settings.a(i)) {
                    int i2 = i == 4 ? 3 : i == 7 ? 4 : i;
                    this.a.writeShort(i2);
                    this.a.writeInt(settings.b(i));
                }
                i++;
            }
            this.a.flush();
        }

        public synchronized void ping(boolean z, int i, int i2) {
            if (this.f) {
                throw new IOException("closed");
            }
            a(0, 8, 6, z ? (byte) 1 : 0);
            this.a.writeInt(i);
            this.a.writeInt(i2);
            this.a.flush();
        }

        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) {
            if (this.f) {
                throw new IOException("closed");
            } else if (errorCode.httpCode == -1) {
                throw Http2.c("errorCode.httpCode == -1", new Object[0]);
            } else {
                a(0, bArr.length + 8, 7, 0);
                this.a.writeInt(i);
                this.a.writeInt(errorCode.httpCode);
                if (bArr.length > 0) {
                    this.a.write(bArr);
                }
                this.a.flush();
            }
        }

        public synchronized void windowUpdate(int i, long j) {
            if (this.f) {
                throw new IOException("closed");
            }
            if (j != 0) {
                if (j <= 2147483647L) {
                    a(i, 4, 8, 0);
                    this.a.writeInt((int) j);
                    this.a.flush();
                }
            }
            throw Http2.c("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
        }

        public synchronized void close() {
            this.f = true;
            this.a.close();
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2, byte b2, byte b3) {
            if (Http2.a.isLoggable(Level.FINE)) {
                Http2.a.fine(FrameLogger.a(false, i, i2, b2, b3));
            }
            if (i2 > this.e) {
                throw Http2.c("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.e), Integer.valueOf(i2));
            } else if ((Integer.MIN_VALUE & i) != 0) {
                throw Http2.c("reserved bit set: %s", Integer.valueOf(i));
            } else {
                Http2.b(this.a, i2);
                this.a.writeByte(b2 & UnsignedBytes.MAX_VALUE);
                this.a.writeByte(b3 & UnsignedBytes.MAX_VALUE);
                this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            }
        }
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public FrameReader newReader(BufferedSource bufferedSource, boolean z) {
        return new Reader(bufferedSource, 4096, z);
    }

    public FrameWriter newWriter(BufferedSink bufferedSink, boolean z) {
        return new Writer(bufferedSink, z);
    }

    /* access modifiers changed from: private */
    public static IllegalArgumentException c(String str, Object... objArr) {
        throw new IllegalArgumentException(String.format(str, objArr));
    }

    /* access modifiers changed from: private */
    public static IOException d(String str, Object... objArr) {
        throw new IOException(String.format(str, objArr));
    }

    /* access modifiers changed from: private */
    public static int b(int i, byte b2, short s) {
        if ((b2 & 8) != 0) {
            i--;
        }
        if (s <= i) {
            return (short) (i - s);
        }
        throw d("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i));
    }

    /* access modifiers changed from: private */
    public static int b(BufferedSource bufferedSource) {
        return (bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) | ((bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) << 8);
    }

    /* access modifiers changed from: private */
    public static void b(BufferedSink bufferedSink, int i) {
        bufferedSink.writeByte((i >>> 16) & 255);
        bufferedSink.writeByte((i >>> 8) & 255);
        bufferedSink.writeByte(i & 255);
    }
}
