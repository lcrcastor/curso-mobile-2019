package okhttp3.internal.http2;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

final class Http2Reader implements Closeable {
    static final Logger a = Logger.getLogger(Http2.class.getName());
    final Reader b = new Reader(4096, this.d);
    private final BufferedSource c;
    private final ContinuationSource d = new ContinuationSource(this.c);
    private final boolean e;

    static final class ContinuationSource implements Source {
        int a;
        byte b;
        int c;
        int d;
        short e;
        private final BufferedSource f;

        public void close() {
        }

        ContinuationSource(BufferedSource bufferedSource) {
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
            int a2 = Http2Reader.a(this.f);
            this.d = a2;
            this.a = a2;
            byte readByte = (byte) (this.f.readByte() & UnsignedBytes.MAX_VALUE);
            this.b = (byte) (this.f.readByte() & UnsignedBytes.MAX_VALUE);
            if (Http2Reader.a.isLoggable(Level.FINE)) {
                Http2Reader.a.fine(Http2.a(true, this.c, this.a, readByte, this.b));
            }
            this.c = this.f.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            if (readByte != 9) {
                throw Http2.b("%s != TYPE_CONTINUATION", Byte.valueOf(readByte));
            } else if (this.c != i) {
                throw Http2.b("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }
    }

    interface Handler {
        void a();

        void a(int i, int i2, int i3, boolean z);

        void a(int i, int i2, List<Header> list);

        void a(int i, long j);

        void a(int i, ErrorCode errorCode);

        void a(int i, ErrorCode errorCode, ByteString byteString);

        void a(boolean z, int i, int i2);

        void a(boolean z, int i, int i2, List<Header> list);

        void a(boolean z, int i, BufferedSource bufferedSource, int i2);

        void a(boolean z, Settings settings);
    }

    Http2Reader(BufferedSource bufferedSource, boolean z) {
        this.c = bufferedSource;
        this.e = z;
    }

    public void a(Handler handler) {
        if (!this.e) {
            ByteString readByteString = this.c.readByteString((long) Http2.a.size());
            if (a.isLoggable(Level.FINE)) {
                a.fine(Util.format("<< CONNECTION %s", readByteString.hex()));
            }
            if (!Http2.a.equals(readByteString)) {
                throw Http2.b("Expected a connection header but was %s", readByteString.utf8());
            }
        } else if (!a(true, handler)) {
            throw Http2.b("Required SETTINGS preface not received", new Object[0]);
        }
    }

    public boolean a(boolean z, Handler handler) {
        try {
            this.c.require(9);
            int a2 = a(this.c);
            if (a2 < 0 || a2 > 16384) {
                throw Http2.b("FRAME_SIZE_ERROR: %s", Integer.valueOf(a2));
            }
            byte readByte = (byte) (this.c.readByte() & UnsignedBytes.MAX_VALUE);
            if (!z || readByte == 4) {
                byte readByte2 = (byte) (this.c.readByte() & UnsignedBytes.MAX_VALUE);
                int readInt = this.c.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
                if (a.isLoggable(Level.FINE)) {
                    a.fine(Http2.a(true, readInt, a2, readByte, readByte2));
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
                        this.c.skip((long) a2);
                        break;
                }
                return true;
            }
            throw Http2.b("Expected a SETTINGS frame but was %s", Byte.valueOf(readByte));
        } catch (IOException unused) {
            return false;
        }
    }

    private void a(Handler handler, int i, byte b2, int i2) {
        short s = 0;
        if (i2 == 0) {
            throw Http2.b("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }
        boolean z = (b2 & 1) != 0;
        if ((b2 & 8) != 0) {
            s = (short) (this.c.readByte() & UnsignedBytes.MAX_VALUE);
        }
        if ((b2 & 32) != 0) {
            a(handler, i2);
            i -= 5;
        }
        handler.a(z, i2, -1, a(a(i, b2, s), s, b2, i2));
    }

    private List<Header> a(int i, short s, byte b2, int i2) {
        ContinuationSource continuationSource = this.d;
        this.d.d = i;
        continuationSource.a = i;
        this.d.e = s;
        this.d.b = b2;
        this.d.c = i2;
        this.b.a();
        return this.b.b();
    }

    private void b(Handler handler, int i, byte b2, int i2) {
        short s = 0;
        if (i2 == 0) {
            throw Http2.b("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
        }
        boolean z = true;
        boolean z2 = (b2 & 1) != 0;
        if ((b2 & 32) == 0) {
            z = false;
        }
        if (z) {
            throw Http2.b("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }
        if ((b2 & 8) != 0) {
            s = (short) (this.c.readByte() & UnsignedBytes.MAX_VALUE);
        }
        handler.a(z2, i2, this.c, a(i, b2, s));
        this.c.skip((long) s);
    }

    private void c(Handler handler, int i, byte b2, int i2) {
        if (i != 5) {
            throw Http2.b("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
        } else if (i2 == 0) {
            throw Http2.b("TYPE_PRIORITY streamId == 0", new Object[0]);
        } else {
            a(handler, i2);
        }
    }

    private void a(Handler handler, int i) {
        int readInt = this.c.readInt();
        handler.a(i, readInt & SubsamplingScaleImageView.TILE_SIZE_AUTO, (this.c.readByte() & UnsignedBytes.MAX_VALUE) + 1, (Integer.MIN_VALUE & readInt) != 0);
    }

    private void d(Handler handler, int i, byte b2, int i2) {
        if (i != 4) {
            throw Http2.b("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
        } else if (i2 == 0) {
            throw Http2.b("TYPE_RST_STREAM streamId == 0", new Object[0]);
        } else {
            int readInt = this.c.readInt();
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt);
            if (fromHttp2 == null) {
                throw Http2.b("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(readInt));
            } else {
                handler.a(i2, fromHttp2);
            }
        }
    }

    private void e(Handler handler, int i, byte b2, int i2) {
        if (i2 != 0) {
            throw Http2.b("TYPE_SETTINGS streamId != 0", new Object[0]);
        } else if ((b2 & 1) != 0) {
            if (i != 0) {
                throw Http2.b("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
            handler.a();
        } else if (i % 6 != 0) {
            throw Http2.b("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
        } else {
            Settings settings = new Settings();
            for (int i3 = 0; i3 < i; i3 += 6) {
                short readShort = this.c.readShort() & 65535;
                int readInt = this.c.readInt();
                switch (readShort) {
                    case 2:
                        if (!(readInt == 0 || readInt == 1)) {
                            throw Http2.b("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                        }
                    case 3:
                        readShort = 4;
                        break;
                    case 4:
                        readShort = 7;
                        if (readInt >= 0) {
                            break;
                        } else {
                            throw Http2.b("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        }
                    case 5:
                        if (readInt >= 16384 && readInt <= 16777215) {
                            break;
                        } else {
                            throw Http2.b("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(readInt));
                        }
                        break;
                }
                settings.a(readShort, readInt);
            }
            handler.a(false, settings);
        }
    }

    private void f(Handler handler, int i, byte b2, int i2) {
        short s = 0;
        if (i2 == 0) {
            throw Http2.b("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }
        if ((b2 & 8) != 0) {
            s = (short) (this.c.readByte() & UnsignedBytes.MAX_VALUE);
        }
        handler.a(i2, this.c.readInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO, a(a(i - 4, b2, s), s, b2, i2));
    }

    private void g(Handler handler, int i, byte b2, int i2) {
        boolean z = false;
        if (i != 8) {
            throw Http2.b("TYPE_PING length != 8: %s", Integer.valueOf(i));
        } else if (i2 != 0) {
            throw Http2.b("TYPE_PING streamId != 0", new Object[0]);
        } else {
            int readInt = this.c.readInt();
            int readInt2 = this.c.readInt();
            if ((b2 & 1) != 0) {
                z = true;
            }
            handler.a(z, readInt, readInt2);
        }
    }

    private void h(Handler handler, int i, byte b2, int i2) {
        if (i < 8) {
            throw Http2.b("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
        } else if (i2 != 0) {
            throw Http2.b("TYPE_GOAWAY streamId != 0", new Object[0]);
        } else {
            int readInt = this.c.readInt();
            int readInt2 = this.c.readInt();
            int i3 = i - 8;
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt2);
            if (fromHttp2 == null) {
                throw Http2.b("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(readInt2));
            }
            ByteString byteString = ByteString.EMPTY;
            if (i3 > 0) {
                byteString = this.c.readByteString((long) i3);
            }
            handler.a(readInt, fromHttp2, byteString);
        }
    }

    private void i(Handler handler, int i, byte b2, int i2) {
        if (i != 4) {
            throw Http2.b("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
        }
        long readInt = ((long) this.c.readInt()) & 2147483647L;
        if (readInt == 0) {
            throw Http2.b("windowSizeIncrement was 0", Long.valueOf(readInt));
        } else {
            handler.a(i2, readInt);
        }
    }

    public void close() {
        this.c.close();
    }

    static int a(BufferedSource bufferedSource) {
        return (bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) | ((bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bufferedSource.readByte() & UnsignedBytes.MAX_VALUE) << 8);
    }

    static int a(int i, byte b2, short s) {
        if ((b2 & 8) != 0) {
            i--;
        }
        if (s <= i) {
            return (short) (i - s);
        }
        throw Http2.b("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i));
    }
}
