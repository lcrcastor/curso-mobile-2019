package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

final class RealBufferedSink implements BufferedSink {
    public final Buffer a = new Buffer();
    public final Sink b;
    boolean c;

    RealBufferedSink(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.b = sink;
    }

    public Buffer buffer() {
        return this.a;
    }

    public void write(Buffer buffer, long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(buffer, j);
        emitCompleteSegments();
    }

    public BufferedSink write(ByteString byteString) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(byteString);
        return emitCompleteSegments();
    }

    public BufferedSink writeUtf8(String str) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeUtf8(str);
        return emitCompleteSegments();
    }

    public BufferedSink writeUtf8(String str, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeUtf8(str, i, i2);
        return emitCompleteSegments();
    }

    public BufferedSink writeUtf8CodePoint(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeUtf8CodePoint(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeString(String str, Charset charset) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeString(str, charset);
        return emitCompleteSegments();
    }

    public BufferedSink writeString(String str, int i, int i2, Charset charset) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeString(str, i, i2, charset);
        return emitCompleteSegments();
    }

    public BufferedSink write(byte[] bArr) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(bArr);
        return emitCompleteSegments();
    }

    public BufferedSink write(byte[] bArr, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(bArr, i, i2);
        return emitCompleteSegments();
    }

    public int write(ByteBuffer byteBuffer) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        int write = this.a.write(byteBuffer);
        emitCompleteSegments();
        return write;
    }

    public long writeAll(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long read = source.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (read == -1) {
                return j;
            }
            long j2 = j + read;
            emitCompleteSegments();
            j = j2;
        }
    }

    public BufferedSink write(Source source, long j) {
        while (j > 0) {
            long read = source.read(this.a, j);
            if (read == -1) {
                throw new EOFException();
            }
            long j2 = j - read;
            emitCompleteSegments();
            j = j2;
        }
        return this;
    }

    public BufferedSink writeByte(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeByte(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeShort(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeShort(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeShortLe(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeShortLe(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeInt(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeInt(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeIntLe(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeIntLe(i);
        return emitCompleteSegments();
    }

    public BufferedSink writeLong(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeLong(j);
        return emitCompleteSegments();
    }

    public BufferedSink writeLongLe(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeLongLe(j);
        return emitCompleteSegments();
    }

    public BufferedSink writeDecimalLong(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeDecimalLong(j);
        return emitCompleteSegments();
    }

    public BufferedSink writeHexadecimalUnsignedLong(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.writeHexadecimalUnsignedLong(j);
        return emitCompleteSegments();
    }

    public BufferedSink emitCompleteSegments() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long completeSegmentByteCount = this.a.completeSegmentByteCount();
        if (completeSegmentByteCount > 0) {
            this.b.write(this.a, completeSegmentByteCount);
        }
        return this;
    }

    public BufferedSink emit() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long size = this.a.size();
        if (size > 0) {
            this.b.write(this.a, size);
        }
        return this;
    }

    public OutputStream outputStream() {
        return new OutputStream() {
            public void write(int i) {
                if (RealBufferedSink.this.c) {
                    throw new IOException("closed");
                }
                RealBufferedSink.this.a.writeByte((int) (byte) i);
                RealBufferedSink.this.emitCompleteSegments();
            }

            public void write(byte[] bArr, int i, int i2) {
                if (RealBufferedSink.this.c) {
                    throw new IOException("closed");
                }
                RealBufferedSink.this.a.write(bArr, i, i2);
                RealBufferedSink.this.emitCompleteSegments();
            }

            public void flush() {
                if (!RealBufferedSink.this.c) {
                    RealBufferedSink.this.flush();
                }
            }

            public void close() {
                RealBufferedSink.this.close();
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(RealBufferedSink.this);
                sb.append(".outputStream()");
                return sb.toString();
            }
        };
    }

    public void flush() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (this.a.b > 0) {
            this.b.write(this.a, this.a.b);
        }
        this.b.flush();
    }

    public boolean isOpen() {
        return !this.c;
    }

    public void close() {
        if (!this.c) {
            Throwable th = null;
            try {
                if (this.a.b > 0) {
                    this.b.write(this.a, this.a.b);
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                this.b.close();
            } catch (Throwable th3) {
                if (th == null) {
                    th = th3;
                }
            }
            this.c = true;
            if (th != null) {
                Util.a(th);
            }
        }
    }

    public Timeout timeout() {
        return this.b.timeout();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("buffer(");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
