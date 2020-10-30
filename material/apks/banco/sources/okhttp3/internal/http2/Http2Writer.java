package okhttp3.internal.http2;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.primitives.UnsignedBytes;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

final class Http2Writer implements Closeable {
    private static final Logger b = Logger.getLogger(Http2.class.getName());
    final Writer a = new Writer(this.e);
    private final BufferedSink c;
    private final boolean d;
    private final Buffer e = new Buffer();
    private int f = 16384;
    private boolean g;

    Http2Writer(BufferedSink bufferedSink, boolean z) {
        this.c = bufferedSink;
        this.d = z;
    }

    public synchronized void a() {
        if (this.g) {
            throw new IOException("closed");
        } else if (this.d) {
            if (b.isLoggable(Level.FINE)) {
                b.fine(Util.format(">> CONNECTION %s", Http2.a.hex()));
            }
            this.c.write(Http2.a.toByteArray());
            this.c.flush();
        }
    }

    public synchronized void a(Settings settings) {
        if (this.g) {
            throw new IOException("closed");
        }
        this.f = settings.d(this.f);
        if (settings.c() != -1) {
            this.a.a(settings.c());
        }
        a(0, 0, 4, 1);
        this.c.flush();
    }

    public synchronized void a(int i, int i2, List<Header> list) {
        if (this.g) {
            throw new IOException("closed");
        }
        this.a.a(list);
        long size = this.e.size();
        int min = (int) Math.min((long) (this.f - 4), size);
        long j = (long) min;
        a(i, min + 4, 5, size == j ? (byte) 4 : 0);
        this.c.writeInt(i2 & SubsamplingScaleImageView.TILE_SIZE_AUTO);
        this.c.write(this.e, j);
        if (size > j) {
            b(i, size - j);
        }
    }

    public synchronized void b() {
        if (this.g) {
            throw new IOException("closed");
        }
        this.c.flush();
    }

    public synchronized void a(boolean z, int i, int i2, List<Header> list) {
        if (this.g) {
            throw new IOException("closed");
        }
        b(z, i, list);
    }

    public synchronized void a(boolean z, int i, List<Header> list) {
        if (this.g) {
            throw new IOException("closed");
        }
        b(z, i, list);
    }

    public synchronized void a(int i, ErrorCode errorCode) {
        if (this.g) {
            throw new IOException("closed");
        } else if (errorCode.httpCode == -1) {
            throw new IllegalArgumentException();
        } else {
            a(i, 4, 3, 0);
            this.c.writeInt(errorCode.httpCode);
            this.c.flush();
        }
    }

    public int c() {
        return this.f;
    }

    public synchronized void a(boolean z, int i, Buffer buffer, int i2) {
        if (this.g) {
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
            this.c.write(buffer, (long) i2);
        }
    }

    public synchronized void b(Settings settings) {
        if (this.g) {
            throw new IOException("closed");
        }
        int i = 0;
        a(0, settings.b() * 6, 4, 0);
        while (i < 10) {
            if (settings.a(i)) {
                int i2 = i == 4 ? 3 : i == 7 ? 4 : i;
                this.c.writeShort(i2);
                this.c.writeInt(settings.b(i));
            }
            i++;
        }
        this.c.flush();
    }

    public synchronized void a(boolean z, int i, int i2) {
        if (this.g) {
            throw new IOException("closed");
        }
        a(0, 8, 6, z ? (byte) 1 : 0);
        this.c.writeInt(i);
        this.c.writeInt(i2);
        this.c.flush();
    }

    public synchronized void a(int i, ErrorCode errorCode, byte[] bArr) {
        if (this.g) {
            throw new IOException("closed");
        } else if (errorCode.httpCode == -1) {
            throw Http2.a("errorCode.httpCode == -1", new Object[0]);
        } else {
            a(0, bArr.length + 8, 7, 0);
            this.c.writeInt(i);
            this.c.writeInt(errorCode.httpCode);
            if (bArr.length > 0) {
                this.c.write(bArr);
            }
            this.c.flush();
        }
    }

    public synchronized void a(int i, long j) {
        if (this.g) {
            throw new IOException("closed");
        }
        if (j != 0) {
            if (j <= 2147483647L) {
                a(i, 4, 8, 0);
                this.c.writeInt((int) j);
                this.c.flush();
            }
        }
        throw Http2.a("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
    }

    public void a(int i, int i2, byte b2, byte b3) {
        if (b.isLoggable(Level.FINE)) {
            b.fine(Http2.a(false, i, i2, b2, b3));
        }
        if (i2 > this.f) {
            throw Http2.a("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.f), Integer.valueOf(i2));
        } else if ((Integer.MIN_VALUE & i) != 0) {
            throw Http2.a("reserved bit set: %s", Integer.valueOf(i));
        } else {
            a(this.c, i2);
            this.c.writeByte(b2 & UnsignedBytes.MAX_VALUE);
            this.c.writeByte(b3 & UnsignedBytes.MAX_VALUE);
            this.c.writeInt(i & SubsamplingScaleImageView.TILE_SIZE_AUTO);
        }
    }

    public synchronized void close() {
        this.g = true;
        this.c.close();
    }

    private static void a(BufferedSink bufferedSink, int i) {
        bufferedSink.writeByte((i >>> 16) & 255);
        bufferedSink.writeByte((i >>> 8) & 255);
        bufferedSink.writeByte(i & 255);
    }

    private void b(int i, long j) {
        while (j > 0) {
            int min = (int) Math.min((long) this.f, j);
            long j2 = (long) min;
            long j3 = j - j2;
            a(i, min, 9, j3 == 0 ? (byte) 4 : 0);
            this.c.write(this.e, j2);
            j = j3;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z, int i, List<Header> list) {
        if (this.g) {
            throw new IOException("closed");
        }
        this.a.a(list);
        long size = this.e.size();
        int min = (int) Math.min((long) this.f, size);
        long j = (long) min;
        byte b2 = size == j ? (byte) 4 : 0;
        if (z) {
            b2 = (byte) (b2 | 1);
        }
        a(i, min, 1, b2);
        this.c.write(this.e, j);
        if (size > j) {
            b(i, size - j);
        }
    }
}
