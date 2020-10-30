package com.squareup.okhttp.internal.spdy;

import android.support.v4.view.ViewCompat;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Okio;
import okio.Sink;

public final class Spdy3 implements Variant {
    static final byte[] a;

    static final class Reader implements FrameReader {
        private final BufferedSource a;
        private final boolean b;
        private final NameValueBlockReader c = new NameValueBlockReader(this.a);

        public void readConnectionPreface() {
        }

        Reader(BufferedSource bufferedSource, boolean z) {
            this.a = bufferedSource;
            this.b = z;
        }

        public boolean nextFrame(Handler handler) {
            boolean z = false;
            try {
                int readInt = this.a.readInt();
                int readInt2 = this.a.readInt();
                int i = (-16777216 & readInt2) >>> 24;
                int i2 = readInt2 & ViewCompat.MEASURED_SIZE_MASK;
                if ((Integer.MIN_VALUE & readInt) != 0) {
                    int i3 = (2147418112 & readInt) >>> 16;
                    int i4 = readInt & 65535;
                    if (i3 != 3) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("version != 3: ");
                        sb.append(i3);
                        throw new ProtocolException(sb.toString());
                    }
                    switch (i4) {
                        case 1:
                            a(handler, i, i2);
                            return true;
                        case 2:
                            b(handler, i, i2);
                            return true;
                        case 3:
                            c(handler, i, i2);
                            return true;
                        case 4:
                            h(handler, i, i2);
                            return true;
                        case 6:
                            f(handler, i, i2);
                            return true;
                        case 7:
                            g(handler, i, i2);
                            return true;
                        case 8:
                            d(handler, i, i2);
                            return true;
                        case 9:
                            e(handler, i, i2);
                            return true;
                        default:
                            this.a.skip((long) i2);
                            return true;
                    }
                } else {
                    int i5 = readInt & SubsamplingScaleImageView.TILE_SIZE_AUTO;
                    if ((i & 1) != 0) {
                        z = true;
                    }
                    handler.data(z, i5, this.a, i2);
                    return true;
                }
            } catch (IOException unused) {
                return false;
            }
        }

        private void a(Handler handler, int i, int i2) {
            int readInt = this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int readInt2 = this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            this.a.readShort();
            List a2 = this.c.a(i2 - 10);
            handler.headers((i & 2) != 0, (i & 1) != 0, readInt, readInt2, a2, HeadersMode.SPDY_SYN_STREAM);
        }

        private void b(Handler handler, int i, int i2) {
            handler.headers(false, (i & 1) != 0, this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO, -1, this.c.a(i2 - 4), HeadersMode.SPDY_REPLY);
        }

        private void c(Handler handler, int i, int i2) {
            if (i2 != 8) {
                throw a("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(i2));
            }
            int readInt = this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int readInt2 = this.a.readInt();
            ErrorCode fromSpdy3Rst = ErrorCode.fromSpdy3Rst(readInt2);
            if (fromSpdy3Rst == null) {
                throw a("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(readInt2));
            } else {
                handler.rstStream(readInt, fromSpdy3Rst);
            }
        }

        private void d(Handler handler, int i, int i2) {
            handler.headers(false, false, this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO, -1, this.c.a(i2 - 4), HeadersMode.SPDY_HEADERS);
        }

        private void e(Handler handler, int i, int i2) {
            if (i2 != 8) {
                throw a("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(i2));
            }
            int readInt = this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            long readInt2 = (long) (this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            if (readInt2 == 0) {
                throw a("windowSizeIncrement was 0", Long.valueOf(readInt2));
            } else {
                handler.windowUpdate(readInt, readInt2);
            }
        }

        private void f(Handler handler, int i, int i2) {
            boolean z = true;
            if (i2 != 4) {
                throw a("TYPE_PING length: %d != 4", Integer.valueOf(i2));
            }
            int readInt = this.a.readInt();
            if (this.b != ((readInt & 1) == 1)) {
                z = false;
            }
            handler.ping(z, readInt, 0);
        }

        private void g(Handler handler, int i, int i2) {
            if (i2 != 8) {
                throw a("TYPE_GOAWAY length: %d != 8", Integer.valueOf(i2));
            }
            int readInt = this.a.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            int readInt2 = this.a.readInt();
            ErrorCode fromSpdyGoAway = ErrorCode.fromSpdyGoAway(readInt2);
            if (fromSpdyGoAway == null) {
                throw a("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(readInt2));
            } else {
                handler.goAway(readInt, fromSpdyGoAway, ByteString.EMPTY);
            }
        }

        private void h(Handler handler, int i, int i2) {
            int readInt = this.a.readInt();
            boolean z = false;
            if (i2 != (readInt * 8) + 4) {
                throw a("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(i2), Integer.valueOf(readInt));
            }
            Settings settings = new Settings();
            for (int i3 = 0; i3 < readInt; i3++) {
                int readInt2 = this.a.readInt();
                int i4 = (-16777216 & readInt2) >>> 24;
                settings.a(readInt2 & ViewCompat.MEASURED_SIZE_MASK, i4, this.a.readInt());
            }
            if ((i & 1) != 0) {
                z = true;
            }
            handler.settings(z, settings);
        }

        private static IOException a(String str, Object... objArr) {
            throw new IOException(String.format(str, objArr));
        }

        public void close() {
            this.c.a();
        }
    }

    static final class Writer implements FrameWriter {
        private final BufferedSink a;
        private final Buffer b = new Buffer();
        private final BufferedSink c;
        private final boolean d;
        private boolean e;

        public void ackSettings(Settings settings) {
        }

        public int maxDataLength() {
            return 16383;
        }

        public void pushPromise(int i, int i2, List<Header> list) {
        }

        Writer(BufferedSink bufferedSink, boolean z) {
            this.a = bufferedSink;
            this.d = z;
            Deflater deflater = new Deflater();
            deflater.setDictionary(Spdy3.a);
            this.c = Okio.buffer((Sink) new DeflaterSink((Sink) this.b, deflater));
        }

        public synchronized void connectionPreface() {
        }

        public synchronized void flush() {
            if (this.e) {
                throw new IOException("closed");
            }
            this.a.flush();
        }

        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) {
            if (this.e) {
                throw new IOException("closed");
            }
            a(list);
            int size = (int) (this.b.size() + 10);
            boolean z3 = z | (z2 ? (char) 2 : 0);
            this.a.writeInt(-2147287039);
            this.a.writeInt(((z3 & true ? 1 : 0) << true) | (size & ViewCompat.MEASURED_SIZE_MASK));
            this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            this.a.writeInt(Integer.MAX_VALUE & i2);
            this.a.writeShort(0);
            this.a.writeAll(this.b);
            this.a.flush();
        }

        public synchronized void synReply(boolean z, int i, List<Header> list) {
            if (this.e) {
                throw new IOException("closed");
            }
            a(list);
            int size = (int) (this.b.size() + 4);
            this.a.writeInt(-2147287038);
            this.a.writeInt(((z & true ? 1 : 0) << true) | (size & ViewCompat.MEASURED_SIZE_MASK));
            this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            this.a.writeAll(this.b);
            this.a.flush();
        }

        public synchronized void headers(int i, List<Header> list) {
            if (this.e) {
                throw new IOException("closed");
            }
            a(list);
            int size = (int) (this.b.size() + 4);
            this.a.writeInt(-2147287032);
            this.a.writeInt((size & ViewCompat.MEASURED_SIZE_MASK) | 0);
            this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            this.a.writeAll(this.b);
        }

        public synchronized void rstStream(int i, ErrorCode errorCode) {
            if (this.e) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            } else {
                this.a.writeInt(-2147287037);
                this.a.writeInt(8);
                this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
                this.a.writeInt(errorCode.spdyRstCode);
                this.a.flush();
            }
        }

        public synchronized void data(boolean z, int i, Buffer buffer, int i2) {
            a(i, z ? 1 : 0, buffer, i2);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2, Buffer buffer, int i3) {
            if (this.e) {
                throw new IOException("closed");
            }
            long j = (long) i3;
            if (j > 16777215) {
                StringBuilder sb = new StringBuilder();
                sb.append("FRAME_TOO_LARGE max size is 16Mib: ");
                sb.append(i3);
                throw new IllegalArgumentException(sb.toString());
            }
            this.a.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
            this.a.writeInt(((i2 & 255) << 24) | (16777215 & i3));
            if (i3 > 0) {
                this.a.write(buffer, j);
            }
        }

        private void a(List<Header> list) {
            if (this.b.size() != 0) {
                throw new IllegalStateException();
            }
            this.c.writeInt(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString byteString = ((Header) list.get(i)).name;
                this.c.writeInt(byteString.size());
                this.c.write(byteString);
                ByteString byteString2 = ((Header) list.get(i)).value;
                this.c.writeInt(byteString2.size());
                this.c.write(byteString2);
            }
            this.c.flush();
        }

        public synchronized void settings(Settings settings) {
            if (this.e) {
                throw new IOException("closed");
            }
            int b2 = settings.b();
            int i = (b2 * 8) + 4;
            this.a.writeInt(-2147287036);
            this.a.writeInt((i & ViewCompat.MEASURED_SIZE_MASK) | 0);
            this.a.writeInt(b2);
            for (int i2 = 0; i2 <= 10; i2++) {
                if (settings.a(i2)) {
                    this.a.writeInt(((settings.c(i2) & 255) << 24) | (i2 & ViewCompat.MEASURED_SIZE_MASK));
                    this.a.writeInt(settings.b(i2));
                }
            }
            this.a.flush();
        }

        public synchronized void ping(boolean z, int i, int i2) {
            if (this.e) {
                throw new IOException("closed");
            }
            boolean z2 = false;
            if (this.d != ((i & 1) == 1)) {
                z2 = true;
            }
            if (z != z2) {
                throw new IllegalArgumentException("payload != reply");
            }
            this.a.writeInt(-2147287034);
            this.a.writeInt(4);
            this.a.writeInt(i);
            this.a.flush();
        }

        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) {
            if (this.e) {
                throw new IOException("closed");
            } else if (errorCode.spdyGoAwayCode == -1) {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            } else {
                this.a.writeInt(-2147287033);
                this.a.writeInt(8);
                this.a.writeInt(i);
                this.a.writeInt(errorCode.spdyGoAwayCode);
                this.a.flush();
            }
        }

        public synchronized void windowUpdate(int i, long j) {
            if (this.e) {
                throw new IOException("closed");
            }
            if (j != 0) {
                if (j <= 2147483647L) {
                    this.a.writeInt(-2147287031);
                    this.a.writeInt(8);
                    this.a.writeInt(i);
                    this.a.writeInt((int) j);
                    this.a.flush();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("windowSizeIncrement must be between 1 and 0x7fffffff: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }

        public synchronized void close() {
            this.e = true;
            Util.closeAll(this.a, this.c);
        }
    }

    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    static {
        try {
            a = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
        } catch (UnsupportedEncodingException unused) {
            throw new AssertionError();
        }
    }

    public FrameReader newReader(BufferedSource bufferedSource, boolean z) {
        return new Reader(bufferedSource, z);
    }

    public FrameWriter newWriter(BufferedSink bufferedSink, boolean z) {
        return new Writer(bufferedSink, z);
    }
}
