package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.primitives.UnsignedBytes;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

final class RealBufferedSource implements BufferedSource {
    public final Buffer a = new Buffer();
    public final Source b;
    boolean c;

    RealBufferedSource(Source source) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        this.b = source;
    }

    public Buffer buffer() {
        return this.a;
    }

    public long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.c) {
            throw new IllegalStateException("closed");
        } else if (this.a.b == 0 && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        } else {
            return this.a.read(buffer, Math.min(j, this.a.b));
        }
    }

    public boolean exhausted() {
        if (!this.c) {
            return this.a.exhausted() && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1;
        }
        throw new IllegalStateException("closed");
    }

    public void require(long j) {
        if (!request(j)) {
            throw new EOFException();
        }
    }

    public boolean request(long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.c) {
            throw new IllegalStateException("closed");
        } else {
            while (this.a.b < j) {
                if (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    public byte readByte() {
        require(1);
        return this.a.readByte();
    }

    public ByteString readByteString() {
        this.a.writeAll(this.b);
        return this.a.readByteString();
    }

    public ByteString readByteString(long j) {
        require(j);
        return this.a.readByteString(j);
    }

    public int select(Options options) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        do {
            int a2 = this.a.a(options);
            if (a2 == -1) {
                return -1;
            }
            long size = (long) options.a[a2].size();
            if (size <= this.a.b) {
                this.a.skip(size);
                return a2;
            }
        } while (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
        return -1;
    }

    public byte[] readByteArray() {
        this.a.writeAll(this.b);
        return this.a.readByteArray();
    }

    public byte[] readByteArray(long j) {
        require(j);
        return this.a.readByteArray(j);
    }

    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr) {
        try {
            require((long) bArr.length);
            this.a.readFully(bArr);
        } catch (EOFException e) {
            int i = 0;
            while (this.a.b > 0) {
                int read = this.a.read(bArr, i, (int) this.a.b);
                if (read == -1) {
                    throw new AssertionError();
                }
                i += read;
            }
            throw e;
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        long j = (long) i2;
        Util.a((long) bArr.length, (long) i, j);
        if (this.a.b == 0 && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.a.read(bArr, i, (int) Math.min(j, this.a.b));
    }

    public int read(ByteBuffer byteBuffer) {
        if (this.a.b == 0 && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.a.read(byteBuffer);
    }

    public void readFully(Buffer buffer, long j) {
        try {
            require(j);
            this.a.readFully(buffer, j);
        } catch (EOFException e) {
            buffer.writeAll(this.a);
            throw e;
        }
    }

    public long readAll(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long j = 0;
        while (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1) {
            long completeSegmentByteCount = this.a.completeSegmentByteCount();
            if (completeSegmentByteCount > 0) {
                long j2 = j + completeSegmentByteCount;
                sink.write(this.a, completeSegmentByteCount);
                j = j2;
            }
        }
        if (this.a.size() <= 0) {
            return j;
        }
        long size = j + this.a.size();
        sink.write(this.a, this.a.size());
        return size;
    }

    public String readUtf8() {
        this.a.writeAll(this.b);
        return this.a.readUtf8();
    }

    public String readUtf8(long j) {
        require(j);
        return this.a.readUtf8(j);
    }

    public String readString(Charset charset) {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.a.writeAll(this.b);
        return this.a.readString(charset);
    }

    public String readString(long j, Charset charset) {
        require(j);
        if (charset != null) {
            return this.a.readString(j, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Nullable
    public String readUtf8Line() {
        long indexOf = indexOf(10);
        if (indexOf != -1) {
            return this.a.a(indexOf);
        }
        return this.a.b != 0 ? readUtf8(this.a.b) : null;
    }

    public String readUtf8LineStrict() {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    public String readUtf8LineStrict(long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("limit < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        long indexOf = indexOf(10, 0, j2);
        if (indexOf != -1) {
            return this.a.a(indexOf);
        }
        if (j2 < Long.MAX_VALUE && request(j2) && this.a.getByte(j2 - 1) == 13 && request(j2 + 1) && this.a.getByte(j2) == 10) {
            return this.a.a(j2);
        }
        Buffer buffer = new Buffer();
        this.a.copyTo(buffer, 0, Math.min(32, this.a.size()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\\n not found: limit=");
        sb2.append(Math.min(this.a.size(), j));
        sb2.append(" content=");
        sb2.append(buffer.readByteString().hex());
        sb2.append(8230);
        throw new EOFException(sb2.toString());
    }

    public int readUtf8CodePoint() {
        require(1);
        byte b2 = this.a.getByte(0);
        if ((b2 & 224) == 192) {
            require(2);
        } else if ((b2 & 240) == 224) {
            require(3);
        } else if ((b2 & 248) == 240) {
            require(4);
        }
        return this.a.readUtf8CodePoint();
    }

    public short readShort() {
        require(2);
        return this.a.readShort();
    }

    public short readShortLe() {
        require(2);
        return this.a.readShortLe();
    }

    public int readInt() {
        require(4);
        return this.a.readInt();
    }

    public int readIntLe() {
        require(4);
        return this.a.readIntLe();
    }

    public long readLong() {
        require(8);
        return this.a.readLong();
    }

    public long readLongLe() {
        require(8);
        return this.a.readLongLe();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readDecimalLong() {
        /*
            r6 = this;
            r0 = 1
            r6.require(r0)
            r0 = 0
            r1 = 0
        L_0x0007:
            int r2 = r1 + 1
            long r3 = (long) r2
            boolean r3 = r6.request(r3)
            if (r3 == 0) goto L_0x003f
            okio.Buffer r3 = r6.a
            long r4 = (long) r1
            byte r3 = r3.getByte(r4)
            r4 = 48
            if (r3 < r4) goto L_0x001f
            r4 = 57
            if (r3 <= r4) goto L_0x0026
        L_0x001f:
            if (r1 != 0) goto L_0x0028
            r4 = 45
            if (r3 == r4) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r1 = r2
            goto L_0x0007
        L_0x0028:
            if (r1 != 0) goto L_0x003f
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.String r2 = "Expected leading [0-9] or '-' character but was %#x"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r4[r0] = r3
            java.lang.String r0 = java.lang.String.format(r2, r4)
            r1.<init>(r0)
            throw r1
        L_0x003f:
            okio.Buffer r0 = r6.a
            long r0 = r0.readDecimalLong()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.RealBufferedSource.readDecimalLong():long");
    }

    public long readHexadecimalUnsignedLong() {
        require(1);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request((long) i2)) {
                break;
            }
            byte b2 = this.a.getByte((long) i);
            if ((b2 >= 48 && b2 <= 57) || ((b2 >= 97 && b2 <= 102) || (b2 >= 65 && b2 <= 70))) {
                i = i2;
            } else if (i == 0) {
                throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[]{Byte.valueOf(b2)}));
            }
        }
        return this.a.readHexadecimalUnsignedLong();
    }

    public void skip(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            if (this.a.b == 0 && this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                throw new EOFException();
            }
            long min = Math.min(j, this.a.size());
            this.a.skip(min);
            j -= min;
        }
    }

    public long indexOf(byte b2) {
        return indexOf(b2, 0, Long.MAX_VALUE);
    }

    public long indexOf(byte b2, long j) {
        return indexOf(b2, j, Long.MAX_VALUE);
    }

    public long indexOf(byte b2, long j, long j2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        } else if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", new Object[]{Long.valueOf(j), Long.valueOf(j2)}));
        } else {
            while (j < j2) {
                long indexOf = this.a.indexOf(b2, j, j2);
                if (indexOf != -1) {
                    return indexOf;
                }
                long j3 = this.a.b;
                if (j3 >= j2 || this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                j = Math.max(j, j3);
            }
            return -1;
        }
    }

    public long indexOf(ByteString byteString) {
        return indexOf(byteString, 0);
    }

    public long indexOf(ByteString byteString, long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long indexOf = this.a.indexOf(byteString, j);
            if (indexOf != -1) {
                return indexOf;
            }
            long j2 = this.a.b;
            if (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1;
            }
            j = Math.max(j, (j2 - ((long) byteString.size())) + 1);
        }
    }

    public long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0);
    }

    public long indexOfElement(ByteString byteString, long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long indexOfElement = this.a.indexOfElement(byteString, j);
            if (indexOfElement != -1) {
                return indexOfElement;
            }
            long j2 = this.a.b;
            if (this.b.read(this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1;
            }
            j = Math.max(j, j2);
        }
    }

    public boolean rangeEquals(long j, ByteString byteString) {
        return rangeEquals(j, byteString, 0, byteString.size());
    }

    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        } else if (j < 0 || i < 0 || i2 < 0 || byteString.size() - i < i2) {
            return false;
        } else {
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = j + ((long) i3);
                if (!request(j2 + 1) || this.a.getByte(j2) != byteString.getByte(i + i3)) {
                    return false;
                }
            }
            return true;
        }
    }

    public InputStream inputStream() {
        return new InputStream() {
            public int read() {
                if (RealBufferedSource.this.c) {
                    throw new IOException("closed");
                } else if (RealBufferedSource.this.a.b == 0 && RealBufferedSource.this.b.read(RealBufferedSource.this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                } else {
                    return RealBufferedSource.this.a.readByte() & UnsignedBytes.MAX_VALUE;
                }
            }

            public int read(byte[] bArr, int i, int i2) {
                if (RealBufferedSource.this.c) {
                    throw new IOException("closed");
                }
                Util.a((long) bArr.length, (long) i, (long) i2);
                if (RealBufferedSource.this.a.b == 0 && RealBufferedSource.this.b.read(RealBufferedSource.this.a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.a.read(bArr, i, i2);
            }

            public int available() {
                if (!RealBufferedSource.this.c) {
                    return (int) Math.min(RealBufferedSource.this.a.b, 2147483647L);
                }
                throw new IOException("closed");
            }

            public void close() {
                RealBufferedSource.this.close();
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(RealBufferedSource.this);
                sb.append(".inputStream()");
                return sb.toString();
            }
        };
    }

    public boolean isOpen() {
        return !this.c;
    }

    public void close() {
        if (!this.c) {
            this.c = true;
            this.b.close();
            this.a.clear();
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
