package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.Closeable;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class Buffer implements Cloneable, ByteChannel, BufferedSink, BufferedSource {
    private static final byte[] c = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    @Nullable
    Segment a;
    long b;

    public static final class UnsafeCursor implements Closeable {
        private Segment a;
        public Buffer buffer;
        public byte[] data;
        public int end = -1;
        public long offset = -1;
        public boolean readWrite;
        public int start = -1;

        public int next() {
            if (this.offset == this.buffer.b) {
                throw new IllegalStateException();
            } else if (this.offset == -1) {
                return seek(0);
            } else {
                return seek(this.offset + ((long) (this.end - this.start)));
            }
        }

        public int seek(long j) {
            Segment segment;
            long j2;
            if (j < -1 || j > this.buffer.b) {
                throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", new Object[]{Long.valueOf(j), Long.valueOf(this.buffer.b)}));
            } else if (j == -1 || j == this.buffer.b) {
                this.a = null;
                this.offset = j;
                this.data = null;
                this.start = -1;
                this.end = -1;
                return -1;
            } else {
                long j3 = 0;
                long j4 = this.buffer.b;
                Segment segment2 = this.buffer.a;
                Segment segment3 = this.buffer.a;
                if (this.a != null) {
                    long j5 = this.offset - ((long) (this.start - this.a.b));
                    if (j5 > j) {
                        segment3 = this.a;
                        j4 = j5;
                    } else {
                        segment2 = this.a;
                        j3 = j5;
                    }
                }
                if (j4 - j > j - j3) {
                    while (j >= j2 + ((long) (segment.c - segment.b))) {
                        long j6 = j2 + ((long) (segment.c - segment.b));
                        segment2 = segment.f;
                        j3 = j6;
                    }
                } else {
                    j2 = j4;
                    segment = segment3;
                    while (j2 > j) {
                        segment = segment.g;
                        j2 -= (long) (segment.c - segment.b);
                    }
                }
                if (this.readWrite && segment.d) {
                    Segment b = segment.b();
                    if (this.buffer.a == segment) {
                        this.buffer.a = b;
                    }
                    segment = segment.a(b);
                    segment.g.c();
                }
                this.a = segment;
                this.offset = j;
                this.data = segment.a;
                this.start = segment.b + ((int) (j - j2));
                this.end = segment.c;
                return this.end - this.start;
            }
        }

        public long resizeBuffer(long j) {
            long j2 = j;
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            } else if (!this.readWrite) {
                throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
            } else {
                long j3 = this.buffer.b;
                if (j2 <= j3) {
                    if (j2 < 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("newSize < 0: ");
                        sb.append(j2);
                        throw new IllegalArgumentException(sb.toString());
                    }
                    long j4 = j3 - j2;
                    while (true) {
                        if (j4 <= 0) {
                            break;
                        }
                        Segment segment = this.buffer.a.g;
                        long j5 = (long) (segment.c - segment.b);
                        if (j5 > j4) {
                            segment.c = (int) (((long) segment.c) - j4);
                            break;
                        }
                        this.buffer.a = segment.c();
                        SegmentPool.a(segment);
                        j4 -= j5;
                    }
                    this.a = null;
                    this.offset = j2;
                    this.data = null;
                    this.start = -1;
                    this.end = -1;
                } else if (j2 > j3) {
                    long j6 = j2 - j3;
                    boolean z = true;
                    while (j6 > 0) {
                        Segment a2 = this.buffer.a(1);
                        int min = (int) Math.min(j6, (long) (8192 - a2.c));
                        a2.c += min;
                        long j7 = j6 - ((long) min);
                        if (z) {
                            this.a = a2;
                            this.offset = j3;
                            this.data = a2.a;
                            this.start = a2.c - min;
                            this.end = a2.c;
                            z = false;
                        }
                        j6 = j7;
                    }
                }
                this.buffer.b = j2;
                return j3;
            }
        }

        public long expandBuffer(int i) {
            if (i <= 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("minByteCount <= 0: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } else if (i > 8192) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("minByteCount > Segment.SIZE: ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
            } else if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            } else if (!this.readWrite) {
                throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
            } else {
                long j = this.buffer.b;
                Segment a2 = this.buffer.a(i);
                int i2 = 8192 - a2.c;
                a2.c = 8192;
                long j2 = (long) i2;
                this.buffer.b = j + j2;
                this.a = a2;
                this.offset = j;
                this.data = a2.a;
                this.start = 8192 - i2;
                this.end = 8192;
                return j2;
            }
        }

        public void close() {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.buffer = null;
            this.a = null;
            this.offset = -1;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }
    }

    public Buffer buffer() {
        return this;
    }

    public void close() {
    }

    public BufferedSink emit() {
        return this;
    }

    public Buffer emitCompleteSegments() {
        return this;
    }

    public void flush() {
    }

    public boolean isOpen() {
        return true;
    }

    public long size() {
        return this.b;
    }

    public OutputStream outputStream() {
        return new OutputStream() {
            public void close() {
            }

            public void flush() {
            }

            public void write(int i) {
                Buffer.this.writeByte((int) (byte) i);
            }

            public void write(byte[] bArr, int i, int i2) {
                Buffer.this.write(bArr, i, i2);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Buffer.this);
                sb.append(".outputStream()");
                return sb.toString();
            }
        };
    }

    public boolean exhausted() {
        return this.b == 0;
    }

    public void require(long j) {
        if (this.b < j) {
            throw new EOFException();
        }
    }

    public boolean request(long j) {
        return this.b >= j;
    }

    public InputStream inputStream() {
        return new InputStream() {
            public void close() {
            }

            public int read() {
                if (Buffer.this.b > 0) {
                    return Buffer.this.readByte() & UnsignedBytes.MAX_VALUE;
                }
                return -1;
            }

            public int read(byte[] bArr, int i, int i2) {
                return Buffer.this.read(bArr, i, i2);
            }

            public int available() {
                return (int) Math.min(Buffer.this.b, 2147483647L);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Buffer.this);
                sb.append(".inputStream()");
                return sb.toString();
            }
        };
    }

    public Buffer copyTo(OutputStream outputStream) {
        return copyTo(outputStream, 0, this.b);
    }

    public Buffer copyTo(OutputStream outputStream, long j, long j2) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.a(this.b, j, j2);
        if (j2 == 0) {
            return this;
        }
        Segment segment = this.a;
        while (j >= ((long) (segment.c - segment.b))) {
            long j3 = j - ((long) (segment.c - segment.b));
            segment = segment.f;
            j = j3;
        }
        while (j2 > 0) {
            int i = (int) (((long) segment.b) + j);
            int min = (int) Math.min((long) (segment.c - i), j2);
            outputStream.write(segment.a, i, min);
            long j4 = j2 - ((long) min);
            segment = segment.f;
            j = 0;
            j2 = j4;
        }
        return this;
    }

    public Buffer copyTo(Buffer buffer, long j, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.a(this.b, j, j2);
        if (j2 == 0) {
            return this;
        }
        buffer.b += j2;
        Segment segment = this.a;
        while (j >= ((long) (segment.c - segment.b))) {
            long j3 = j - ((long) (segment.c - segment.b));
            segment = segment.f;
            j = j3;
        }
        while (j2 > 0) {
            Segment a2 = segment.a();
            a2.b = (int) (((long) a2.b) + j);
            a2.c = Math.min(a2.b + ((int) j2), a2.c);
            if (buffer.a == null) {
                a2.g = a2;
                a2.f = a2;
                buffer.a = a2;
            } else {
                buffer.a.g.a(a2);
            }
            long j4 = j2 - ((long) (a2.c - a2.b));
            segment = segment.f;
            j = 0;
            j2 = j4;
        }
        return this;
    }

    public Buffer writeTo(OutputStream outputStream) {
        return writeTo(outputStream, this.b);
    }

    public Buffer writeTo(OutputStream outputStream, long j) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.a(this.b, 0, j);
        Segment segment = this.a;
        while (j > 0) {
            int min = (int) Math.min(j, (long) (segment.c - segment.b));
            outputStream.write(segment.a, segment.b, min);
            segment.b += min;
            long j2 = (long) min;
            this.b -= j2;
            long j3 = j - j2;
            if (segment.b == segment.c) {
                Segment c2 = segment.c();
                this.a = c2;
                SegmentPool.a(segment);
                segment = c2;
            }
            j = j3;
        }
        return this;
    }

    public Buffer readFrom(InputStream inputStream) {
        a(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    public Buffer readFrom(InputStream inputStream, long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
        a(inputStream, j, false);
        return this;
    }

    private void a(InputStream inputStream, long j, boolean z) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (j > 0 || z) {
                Segment a2 = a(1);
                int read = inputStream.read(a2.a, a2.c, (int) Math.min(j, (long) (8192 - a2.c)));
                if (read != -1) {
                    a2.c += read;
                    long j2 = (long) read;
                    this.b += j2;
                    j -= j2;
                } else if (!z) {
                    throw new EOFException();
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public long completeSegmentByteCount() {
        long j = this.b;
        if (j == 0) {
            return 0;
        }
        Segment segment = this.a.g;
        if (segment.c < 8192 && segment.e) {
            j -= (long) (segment.c - segment.b);
        }
        return j;
    }

    public byte readByte() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        int i3 = i + 1;
        byte b2 = segment.a[i];
        this.b--;
        if (i3 == i2) {
            this.a = segment.c();
            SegmentPool.a(segment);
        } else {
            segment.b = i3;
        }
        return b2;
    }

    public byte getByte(long j) {
        Util.a(this.b, j, 1);
        if (this.b - j > j) {
            Segment segment = this.a;
            while (true) {
                long j2 = (long) (segment.c - segment.b);
                if (j < j2) {
                    return segment.a[segment.b + ((int) j)];
                }
                long j3 = j - j2;
                segment = segment.f;
                j = j3;
            }
        } else {
            long j4 = j - this.b;
            Segment segment2 = this.a.g;
            while (true) {
                long j5 = j4 + ((long) (segment2.c - segment2.b));
                if (j5 >= 0) {
                    return segment2.a[segment2.b + ((int) j5)];
                }
                segment2 = segment2.g;
                j4 = j5;
            }
        }
    }

    public short readShort() {
        if (this.b < 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("size < 2: ");
            sb.append(this.b);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 2) {
            return (short) (((readByte() & UnsignedBytes.MAX_VALUE) << 8) | (readByte() & UnsignedBytes.MAX_VALUE));
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        byte b2 = ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8) | (bArr[i3] & UnsignedBytes.MAX_VALUE);
        this.b -= 2;
        if (i4 == i2) {
            this.a = segment.c();
            SegmentPool.a(segment);
        } else {
            segment.b = i4;
        }
        return (short) b2;
    }

    public int readInt() {
        if (this.b < 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("size < 4: ");
            sb.append(this.b);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 4) {
            return ((readByte() & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((readByte() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((readByte() & UnsignedBytes.MAX_VALUE) << 8) | (readByte() & UnsignedBytes.MAX_VALUE);
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        byte b2 = ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        int i5 = i4 + 1;
        byte b3 = b2 | ((bArr[i4] & UnsignedBytes.MAX_VALUE) << 8);
        int i6 = i5 + 1;
        byte b4 = b3 | (bArr[i5] & UnsignedBytes.MAX_VALUE);
        this.b -= 4;
        if (i6 == i2) {
            this.a = segment.c();
            SegmentPool.a(segment);
        } else {
            segment.b = i6;
        }
        return b4;
    }

    public long readLong() {
        if (this.b < 8) {
            StringBuilder sb = new StringBuilder();
            sb.append("size < 8: ");
            sb.append(this.b);
            throw new IllegalStateException(sb.toString());
        }
        Segment segment = this.a;
        int i = segment.b;
        int i2 = segment.c;
        if (i2 - i < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
        }
        byte[] bArr = segment.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        int i10 = i9 + 1;
        long j = ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i3]) & 255) << 48) | ((((long) bArr[i4]) & 255) << 40) | ((((long) bArr[i5]) & 255) << 32) | ((((long) bArr[i6]) & 255) << 24) | ((((long) bArr[i7]) & 255) << 16) | ((((long) bArr[i8]) & 255) << 8) | (((long) bArr[i9]) & 255);
        this.b -= 8;
        if (i10 == i2) {
            this.a = segment.c();
            SegmentPool.a(segment);
        } else {
            segment.b = i10;
        }
        return j;
    }

    public short readShortLe() {
        return Util.a(readShort());
    }

    public int readIntLe() {
        return Util.a(readInt());
    }

    public long readLongLe() {
        return Util.a(readLong());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a5, code lost:
        if (r12 != r13) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a7, code lost:
        r0.a = r10.c();
        okio.SegmentPool.a(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b1, code lost:
        r10.b = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b3, code lost:
        if (r6 != false) goto L_0x00b9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readDecimalLong() {
        /*
            r18 = this;
            r0 = r18
            long r1 = r0.b
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x0012
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "size == 0"
            r1.<init>(r2)
            throw r1
        L_0x0012:
            r5 = -7
            r7 = 0
            r8 = r5
            r5 = 0
            r6 = 0
        L_0x0018:
            okio.Segment r10 = r0.a
            byte[] r11 = r10.a
            int r12 = r10.b
            int r13 = r10.c
        L_0x0020:
            if (r12 >= r13) goto L_0x00a5
            byte r15 = r11[r12]
            r14 = 48
            if (r15 < r14) goto L_0x0076
            r1 = 57
            if (r15 > r1) goto L_0x0076
            int r14 = r14 - r15
            r1 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r16 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r16 < 0) goto L_0x0049
            int r16 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r16 != 0) goto L_0x0040
            long r1 = (long) r14
            int r16 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r16 >= 0) goto L_0x0040
            goto L_0x0049
        L_0x0040:
            r1 = 10
            long r3 = r3 * r1
            long r1 = (long) r14
            long r14 = r3 + r1
            r3 = r14
            goto L_0x0082
        L_0x0049:
            okio.Buffer r1 = new okio.Buffer
            r1.<init>()
            okio.Buffer r1 = r1.writeDecimalLong(r3)
            okio.Buffer r1 = r1.writeByte(r15)
            if (r5 != 0) goto L_0x005b
            r1.readByte()
        L_0x005b:
            java.lang.NumberFormatException r2 = new java.lang.NumberFormatException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Number too large: "
            r3.append(r4)
            java.lang.String r1 = r1.readUtf8()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0076:
            r1 = 45
            if (r15 != r1) goto L_0x0087
            if (r7 != 0) goto L_0x0087
            r1 = 1
            long r14 = r8 - r1
            r8 = r14
            r5 = 1
        L_0x0082:
            int r12 = r12 + 1
            int r7 = r7 + 1
            goto L_0x0020
        L_0x0087:
            if (r7 != 0) goto L_0x00a4
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Expected leading [0-9] or '-' character but was 0x"
            r2.append(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r15)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00a4:
            r6 = 1
        L_0x00a5:
            if (r12 != r13) goto L_0x00b1
            okio.Segment r1 = r10.c()
            r0.a = r1
            okio.SegmentPool.a(r10)
            goto L_0x00b3
        L_0x00b1:
            r10.b = r12
        L_0x00b3:
            if (r6 != 0) goto L_0x00b9
            okio.Segment r1 = r0.a
            if (r1 != 0) goto L_0x0018
        L_0x00b9:
            long r1 = r0.b
            long r6 = (long) r7
            long r8 = r1 - r6
            r0.b = r8
            if (r5 == 0) goto L_0x00c3
            goto L_0x00c4
        L_0x00c3:
            long r3 = -r3
        L_0x00c4:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        if (r9 != r10) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a0, code lost:
        r0.a = r7.c();
        okio.SegmentPool.a(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00aa, code lost:
        r7.b = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ac, code lost:
        if (r2 != false) goto L_0x00b2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0082 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readHexadecimalUnsignedLong() {
        /*
            r17 = this;
            r0 = r17
            long r1 = r0.b
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x0012
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "size == 0"
            r1.<init>(r2)
            throw r1
        L_0x0012:
            r1 = 0
            r5 = r3
            r2 = 0
        L_0x0015:
            okio.Segment r7 = r0.a
            byte[] r8 = r7.a
            int r9 = r7.b
            int r10 = r7.c
        L_0x001d:
            if (r9 >= r10) goto L_0x009e
            byte r11 = r8[r9]
            r12 = 48
            if (r11 < r12) goto L_0x002c
            r12 = 57
            if (r11 > r12) goto L_0x002c
            int r12 = r11 + -48
            goto L_0x0045
        L_0x002c:
            r12 = 97
            if (r11 < r12) goto L_0x0039
            r12 = 102(0x66, float:1.43E-43)
            if (r11 > r12) goto L_0x0039
            int r12 = r11 + -97
            int r12 = r12 + 10
            goto L_0x0045
        L_0x0039:
            r12 = 65
            if (r11 < r12) goto L_0x0080
            r12 = 70
            if (r11 > r12) goto L_0x0080
            int r12 = r11 + -65
            int r12 = r12 + 10
        L_0x0045:
            r13 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r15 = r5 & r13
            int r13 = (r15 > r3 ? 1 : (r15 == r3 ? 0 : -1))
            if (r13 == 0) goto L_0x0075
            okio.Buffer r1 = new okio.Buffer
            r1.<init>()
            okio.Buffer r1 = r1.writeHexadecimalUnsignedLong(r5)
            okio.Buffer r1 = r1.writeByte(r11)
            java.lang.NumberFormatException r2 = new java.lang.NumberFormatException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Number too large: "
            r3.append(r4)
            java.lang.String r1 = r1.readUtf8()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0075:
            r11 = 4
            long r5 = r5 << r11
            long r11 = (long) r12
            long r13 = r5 | r11
            int r9 = r9 + 1
            int r1 = r1 + 1
            r5 = r13
            goto L_0x001d
        L_0x0080:
            if (r1 != 0) goto L_0x009d
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Expected leading [0-9a-fA-F] character but was 0x"
            r2.append(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r11)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x009d:
            r2 = 1
        L_0x009e:
            if (r9 != r10) goto L_0x00aa
            okio.Segment r8 = r7.c()
            r0.a = r8
            okio.SegmentPool.a(r7)
            goto L_0x00ac
        L_0x00aa:
            r7.b = r9
        L_0x00ac:
            if (r2 != 0) goto L_0x00b2
            okio.Segment r7 = r0.a
            if (r7 != 0) goto L_0x0015
        L_0x00b2:
            long r2 = r0.b
            long r7 = (long) r1
            long r9 = r2 - r7
            r0.b = r9
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    public ByteString readByteString(long j) {
        return new ByteString(readByteArray(j));
    }

    public int select(Options options) {
        Segment segment = this.a;
        if (segment == null) {
            return options.indexOf(ByteString.EMPTY);
        }
        ByteString[] byteStringArr = options.a;
        int length = byteStringArr.length;
        for (int i = 0; i < length; i++) {
            ByteString byteString = byteStringArr[i];
            if (this.b >= ((long) byteString.size())) {
                if (a(segment, segment.b, byteString, 0, byteString.size())) {
                    try {
                        skip((long) byteString.size());
                        return i;
                    } catch (EOFException e) {
                        throw new AssertionError(e);
                    }
                }
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public int a(Options options) {
        Segment segment = this.a;
        ByteString[] byteStringArr = options.a;
        int length = byteStringArr.length;
        int i = 0;
        while (i < length) {
            ByteString byteString = byteStringArr[i];
            int min = (int) Math.min(this.b, (long) byteString.size());
            if (min != 0) {
                if (!a(segment, segment.b, byteString, 0, min)) {
                    i++;
                }
            }
            return i;
        }
        return -1;
    }

    public void readFully(Buffer buffer, long j) {
        if (this.b < j) {
            buffer.write(this, this.b);
            throw new EOFException();
        } else {
            buffer.write(this, j);
        }
    }

    public long readAll(Sink sink) {
        long j = this.b;
        if (j > 0) {
            sink.write(this, j);
        }
        return j;
    }

    public String readUtf8() {
        try {
            return readString(this.b, Util.a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readUtf8(long j) {
        return readString(j, Util.a);
    }

    public String readString(Charset charset) {
        try {
            return readString(this.b, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readString(long j, Charset charset) {
        Util.a(this.b, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount > Integer.MAX_VALUE: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j == 0) {
            return "";
        } else {
            Segment segment = this.a;
            if (((long) segment.b) + j > ((long) segment.c)) {
                return new String(readByteArray(j), charset);
            }
            String str = new String(segment.a, segment.b, (int) j, charset);
            segment.b = (int) (((long) segment.b) + j);
            this.b -= j;
            if (segment.b == segment.c) {
                this.a = segment.c();
                SegmentPool.a(segment);
            }
            return str;
        }
    }

    @Nullable
    public String readUtf8Line() {
        long indexOf = indexOf(10);
        if (indexOf != -1) {
            return a(indexOf);
        }
        return this.b != 0 ? readUtf8(this.b) : null;
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
        long j2 = Long.MAX_VALUE;
        if (j != Long.MAX_VALUE) {
            j2 = j + 1;
        }
        long indexOf = indexOf(10, 0, j2);
        if (indexOf != -1) {
            return a(indexOf);
        }
        if (j2 < size() && getByte(j2 - 1) == 13 && getByte(j2) == 10) {
            return a(j2);
        }
        Buffer buffer = new Buffer();
        copyTo(buffer, 0, Math.min(32, size()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\\n not found: limit=");
        sb2.append(Math.min(size(), j));
        sb2.append(" content=");
        sb2.append(buffer.readByteString().hex());
        sb2.append(8230);
        throw new EOFException(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    public String a(long j) {
        if (j > 0) {
            long j2 = j - 1;
            if (getByte(j2) == 13) {
                String readUtf8 = readUtf8(j2);
                skip(2);
                return readUtf8;
            }
        }
        String readUtf82 = readUtf8(j);
        skip(1);
        return readUtf82;
    }

    public int readUtf8CodePoint() {
        byte b2;
        int i;
        byte b3;
        if (this.b == 0) {
            throw new EOFException();
        }
        byte b4 = getByte(0);
        int i2 = 1;
        if ((b4 & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
            b3 = b4 & Ascii.DEL;
            i = 1;
            b2 = 0;
        } else if ((b4 & 224) == 192) {
            b3 = b4 & Ascii.US;
            i = 2;
            b2 = UnsignedBytes.MAX_POWER_OF_TWO;
        } else if ((b4 & 240) == 224) {
            b3 = b4 & Ascii.SI;
            i = 3;
            b2 = Ascii.NUL;
        } else if ((b4 & 248) == 240) {
            b3 = b4 & 7;
            i = 4;
            b2 = Ascii.NUL;
        } else {
            skip(1);
            return 65533;
        }
        long j = (long) i;
        if (this.b < j) {
            StringBuilder sb = new StringBuilder();
            sb.append("size < ");
            sb.append(i);
            sb.append(": ");
            sb.append(this.b);
            sb.append(" (to read code point prefixed 0x");
            sb.append(Integer.toHexString(b4));
            sb.append(")");
            throw new EOFException(sb.toString());
        }
        while (i2 < i) {
            long j2 = (long) i2;
            byte b5 = getByte(j2);
            if ((b5 & 192) == 128) {
                b3 = (b3 << 6) | (b5 & 63);
                i2++;
            } else {
                skip(j2);
                return 65533;
            }
        }
        skip(j);
        if (b3 > 1114111) {
            return 65533;
        }
        if ((b3 < 55296 || b3 > 57343) && b3 >= b2) {
            return b3;
        }
        return 65533;
    }

    public byte[] readByteArray() {
        try {
            return readByteArray(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] readByteArray(long j) {
        Util.a(this.b, 0, j);
        if (j > 2147483647L) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount > Integer.MAX_VALUE: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
        byte[] bArr = new byte[((int) j)];
        readFully(bArr);
        return bArr;
    }

    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            int read = read(bArr, i, bArr.length - i);
            if (read == -1) {
                throw new EOFException();
            }
            i += read;
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        Util.a((long) bArr.length, (long) i, (long) i2);
        Segment segment = this.a;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(i2, segment.c - segment.b);
        System.arraycopy(segment.a, segment.b, bArr, i, min);
        segment.b += min;
        this.b -= (long) min;
        if (segment.b == segment.c) {
            this.a = segment.c();
            SegmentPool.a(segment);
        }
        return min;
    }

    public int read(ByteBuffer byteBuffer) {
        Segment segment = this.a;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), segment.c - segment.b);
        byteBuffer.put(segment.a, segment.b, min);
        segment.b += min;
        this.b -= (long) min;
        if (segment.b == segment.c) {
            this.a = segment.c();
            SegmentPool.a(segment);
        }
        return min;
    }

    public void clear() {
        try {
            skip(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public void skip(long j) {
        while (j > 0) {
            if (this.a == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, (long) (this.a.c - this.a.b));
            long j2 = (long) min;
            this.b -= j2;
            long j3 = j - j2;
            this.a.b += min;
            if (this.a.b == this.a.c) {
                Segment segment = this.a;
                this.a = segment.c();
                SegmentPool.a(segment);
            }
            j = j3;
        }
    }

    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.a(this);
        return this;
    }

    public Buffer writeUtf8(String str) {
        return writeUtf8(str, 0, str.length());
    }

    public Buffer writeUtf8(String str, int i, int i2) {
        char c2;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("beginIndex < 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex < beginIndex: ");
            sb2.append(i2);
            sb2.append(" < ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i2 > str.length()) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("endIndex > string.length: ");
            sb3.append(i2);
            sb3.append(" > ");
            sb3.append(str.length());
            throw new IllegalArgumentException(sb3.toString());
        } else {
            while (i < i2) {
                char charAt = str.charAt(i);
                if (charAt < 128) {
                    Segment a2 = a(1);
                    byte[] bArr = a2.a;
                    int i3 = a2.c - i;
                    int min = Math.min(i2, 8192 - i3);
                    int i4 = i + 1;
                    bArr[i + i3] = (byte) charAt;
                    while (i4 < min) {
                        char charAt2 = str.charAt(i4);
                        if (charAt2 >= 128) {
                            break;
                        }
                        int i5 = i4 + 1;
                        bArr[i4 + i3] = (byte) charAt2;
                        i4 = i5;
                    }
                    int i6 = (i3 + i4) - a2.c;
                    a2.c += i6;
                    this.b += (long) i6;
                    i = i4;
                } else if (charAt < 2048) {
                    writeByte((charAt >> 6) | 192);
                    writeByte((int) (charAt & '?') | 128);
                    i++;
                } else if (charAt < 55296 || charAt > 57343) {
                    writeByte((charAt >> 12) | 224);
                    writeByte(((charAt >> 6) & 63) | 128);
                    writeByte((int) (charAt & '?') | 128);
                    i++;
                } else {
                    int i7 = i + 1;
                    if (i7 < i2) {
                        c2 = str.charAt(i7);
                    } else {
                        c2 = 0;
                    }
                    if (charAt > 56319 || c2 < 56320 || c2 > 57343) {
                        writeByte(63);
                        i = i7;
                    } else {
                        int i8 = (((charAt & 10239) << 10) | (9215 & c2)) + Ascii.MIN;
                        writeByte((i8 >> 18) | 240);
                        writeByte(((i8 >> 12) & 63) | 128);
                        writeByte(((i8 >> 6) & 63) | 128);
                        writeByte((i8 & 63) | 128);
                        i += 2;
                    }
                }
            }
            return this;
        }
    }

    public Buffer writeUtf8CodePoint(int i) {
        if (i < 128) {
            writeByte(i);
        } else if (i < 2048) {
            writeByte((i >> 6) | 192);
            writeByte((i & 63) | 128);
        } else if (i < 65536) {
            if (i < 55296 || i > 57343) {
                writeByte((i >> 12) | 224);
                writeByte(((i >> 6) & 63) | 128);
                writeByte((i & 63) | 128);
            } else {
                writeByte(63);
            }
        } else if (i <= 1114111) {
            writeByte((i >> 18) | 240);
            writeByte(((i >> 12) & 63) | 128);
            writeByte(((i >> 6) & 63) | 128);
            writeByte((i & 63) | 128);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected code point: ");
            sb.append(Integer.toHexString(i));
            throw new IllegalArgumentException(sb.toString());
        }
        return this;
    }

    public Buffer writeString(String str, Charset charset) {
        return writeString(str, 0, str.length(), charset);
    }

    public Buffer writeString(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("beginIndex < 0: ");
            sb.append(i);
            throw new IllegalAccessError(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex < beginIndex: ");
            sb2.append(i2);
            sb2.append(" < ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i2 > str.length()) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("endIndex > string.length: ");
            sb3.append(i2);
            sb3.append(" > ");
            sb3.append(str.length());
            throw new IllegalArgumentException(sb3.toString());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(Util.a)) {
            return writeUtf8(str, i, i2);
        } else {
            byte[] bytes = str.substring(i, i2).getBytes(charset);
            return write(bytes, 0, bytes.length);
        }
    }

    public Buffer write(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public Buffer write(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = (long) i2;
        Util.a((long) bArr.length, (long) i, j);
        int i3 = i2 + i;
        while (i < i3) {
            Segment a2 = a(1);
            int min = Math.min(i3 - i, 8192 - a2.c);
            System.arraycopy(bArr, i, a2.a, a2.c, min);
            i += min;
            a2.c += min;
        }
        this.b += j;
        return this;
    }

    public int write(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        int remaining = byteBuffer.remaining();
        int i = remaining;
        while (i > 0) {
            Segment a2 = a(1);
            int min = Math.min(i, 8192 - a2.c);
            byteBuffer.get(a2.a, a2.c, min);
            i -= min;
            a2.c += min;
        }
        this.b += (long) remaining;
        return remaining;
    }

    public long writeAll(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long read = source.read(this, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (read == -1) {
                return j;
            }
            j += read;
        }
    }

    public BufferedSink write(Source source, long j) {
        while (j > 0) {
            long read = source.read(this, j);
            if (read == -1) {
                throw new EOFException();
            }
            j -= read;
        }
        return this;
    }

    public Buffer writeByte(int i) {
        Segment a2 = a(1);
        byte[] bArr = a2.a;
        int i2 = a2.c;
        a2.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.b++;
        return this;
    }

    public Buffer writeShort(int i) {
        Segment a2 = a(2);
        byte[] bArr = a2.a;
        int i2 = a2.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i & 255);
        a2.c = i4;
        this.b += 2;
        return this;
    }

    public Buffer writeShortLe(int i) {
        return writeShort((int) Util.a((short) i));
    }

    public Buffer writeInt(int i) {
        Segment a2 = a(4);
        byte[] bArr = a2.a;
        int i2 = a2.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i & 255);
        a2.c = i6;
        this.b += 4;
        return this;
    }

    public Buffer writeIntLe(int i) {
        return writeInt(Util.a(i));
    }

    public Buffer writeLong(long j) {
        Segment a2 = a(8);
        byte[] bArr = a2.a;
        int i = a2.c;
        int i2 = i + 1;
        bArr[i] = (byte) ((int) ((j >>> 56) & 255));
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) ((j >>> 48) & 255));
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) ((j >>> 40) & 255));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) ((j >>> 32) & 255));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) ((j >>> 24) & 255));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) ((j >>> 16) & 255));
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((int) ((j >>> 8) & 255));
        int i9 = i8 + 1;
        bArr[i8] = (byte) ((int) (j & 255));
        a2.c = i9;
        this.b += 8;
        return this;
    }

    public Buffer writeLongLe(long j) {
        return writeLong(Util.a(j));
    }

    public Buffer writeDecimalLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        boolean z = false;
        int i = 1;
        if (j < 0) {
            j = -j;
            if (j < 0) {
                return writeUtf8("-9223372036854775808");
            }
            z = true;
        }
        if (j >= 100000000) {
            i = j < 1000000000000L ? j < 10000000000L ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS) {
            i = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i = 2;
        }
        if (z) {
            i++;
        }
        Segment a2 = a(i);
        byte[] bArr = a2.a;
        int i2 = a2.c + i;
        while (j != 0) {
            i2--;
            bArr[i2] = c[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i2 - 1] = 45;
        }
        a2.c += i;
        this.b += (long) i;
        return this;
    }

    public Buffer writeHexadecimalUnsignedLong(long j) {
        if (j == 0) {
            return writeByte(48);
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        Segment a2 = a(numberOfTrailingZeros);
        byte[] bArr = a2.a;
        int i = a2.c;
        for (int i2 = (a2.c + numberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = c[(int) (j & 15)];
            j >>>= 4;
        }
        a2.c += numberOfTrailingZeros;
        this.b += (long) numberOfTrailingZeros;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Segment a(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException();
        } else if (this.a == null) {
            this.a = SegmentPool.a();
            Segment segment = this.a;
            Segment segment2 = this.a;
            Segment segment3 = this.a;
            segment2.g = segment3;
            segment.f = segment3;
            return segment3;
        } else {
            Segment segment4 = this.a.g;
            if (segment4.c + i > 8192 || !segment4.e) {
                segment4 = segment4.a(SegmentPool.a());
            }
            return segment4;
        }
    }

    public void write(Buffer buffer, long j) {
        int i;
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        } else if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            Util.a(buffer.b, 0, j);
            while (j > 0) {
                if (j < ((long) (buffer.a.c - buffer.a.b))) {
                    Segment segment = this.a != null ? this.a.g : null;
                    if (segment != null && segment.e) {
                        long j2 = j + ((long) segment.c);
                        if (segment.d) {
                            i = 0;
                        } else {
                            i = segment.b;
                        }
                        if (j2 - ((long) i) <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                            buffer.a.a(segment, (int) j);
                            buffer.b -= j;
                            this.b += j;
                            return;
                        }
                    }
                    buffer.a = buffer.a.a((int) j);
                }
                Segment segment2 = buffer.a;
                long j3 = (long) (segment2.c - segment2.b);
                buffer.a = segment2.c();
                if (this.a == null) {
                    this.a = segment2;
                    Segment segment3 = this.a;
                    Segment segment4 = this.a;
                    Segment segment5 = this.a;
                    segment4.g = segment5;
                    segment3.f = segment5;
                } else {
                    this.a.g.a(segment2).d();
                }
                buffer.b -= j3;
                this.b += j3;
                j -= j3;
            }
        }
    }

    public long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.b == 0) {
            return -1;
        } else {
            if (j > this.b) {
                j = this.b;
            }
            buffer.write(this, j);
            return j;
        }
    }

    public long indexOf(byte b2) {
        return indexOf(b2, 0, Long.MAX_VALUE);
    }

    public long indexOf(byte b2, long j) {
        return indexOf(b2, j, Long.MAX_VALUE);
    }

    public long indexOf(byte b2, long j, long j2) {
        long j3;
        long j4 = 0;
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", new Object[]{Long.valueOf(this.b), Long.valueOf(j), Long.valueOf(j2)}));
        }
        long j5 = j2 > this.b ? this.b : j2;
        if (j == j5) {
            return -1;
        }
        Segment segment = this.a;
        if (segment == null) {
            return -1;
        }
        if (this.b - j >= j) {
            while (true) {
                long j6 = j3 + ((long) (segment.c - segment.b));
                if (j6 >= j) {
                    break;
                }
                segment = segment.f;
                j4 = j6;
            }
        } else {
            j3 = this.b;
            while (j3 > j) {
                segment = segment.g;
                j3 -= (long) (segment.c - segment.b);
            }
        }
        long j7 = j;
        while (j3 < j5) {
            byte[] bArr = segment.a;
            int min = (int) Math.min((long) segment.c, (((long) segment.b) + j5) - j3);
            for (int i = (int) ((((long) segment.b) + j7) - j3); i < min; i++) {
                if (bArr[i] == b2) {
                    return ((long) (i - segment.b)) + j3;
                }
            }
            byte b3 = b2;
            long j8 = j3 + ((long) (segment.c - segment.b));
            segment = segment.f;
            j7 = j8;
            j3 = j7;
        }
        return -1;
    }

    public long indexOf(ByteString byteString) {
        return indexOf(byteString, 0);
    }

    public long indexOf(ByteString byteString, long j) {
        long j2;
        byte[] bArr;
        Segment segment;
        Buffer buffer = this;
        if (byteString.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long j3 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment2 = buffer.a;
        long j4 = -1;
        if (segment2 == null) {
            return -1;
        }
        if (buffer.b - j >= j) {
            while (true) {
                long j5 = j2 + ((long) (segment2.c - segment2.b));
                if (j5 >= j) {
                    break;
                }
                segment2 = segment2.f;
                j3 = j5;
            }
        } else {
            j2 = buffer.b;
            while (j2 > j) {
                segment2 = segment2.g;
                j2 -= (long) (segment2.c - segment2.b);
            }
        }
        ByteString byteString2 = byteString;
        byte b2 = byteString2.getByte(0);
        int size = byteString.size();
        long j6 = (buffer.b - ((long) size)) + 1;
        long j7 = j;
        long j8 = j2;
        Segment segment3 = segment2;
        while (j8 < j6) {
            byte[] bArr2 = segment3.a;
            int min = (int) Math.min((long) segment3.c, (((long) segment3.b) + j6) - j8);
            int i = (int) ((((long) segment3.b) + j7) - j8);
            while (i < min) {
                if (bArr2[i] == b2) {
                    Buffer buffer2 = buffer;
                    bArr = bArr2;
                    segment = segment3;
                    if (buffer2.a(segment3, i + 1, byteString2, 1, size)) {
                        return ((long) (i - segment.b)) + j8;
                    }
                } else {
                    bArr = bArr2;
                    segment = segment3;
                }
                i++;
                segment3 = segment;
                bArr2 = bArr;
                buffer = this;
            }
            Segment segment4 = segment3;
            long j9 = j8 + ((long) (segment4.c - segment4.b));
            segment3 = segment4.f;
            j7 = j9;
            j8 = j7;
            buffer = this;
            j4 = -1;
        }
        return j4;
    }

    public long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0);
    }

    public long indexOfElement(ByteString byteString, long j) {
        long j2;
        long j3 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.a;
        if (segment == null) {
            return -1;
        }
        if (this.b - j >= j) {
            while (true) {
                long j4 = j2 + ((long) (segment.c - segment.b));
                if (j4 >= j) {
                    break;
                }
                segment = segment.f;
                j3 = j4;
            }
        } else {
            j2 = this.b;
            while (j2 > j) {
                segment = segment.g;
                j2 -= (long) (segment.c - segment.b);
            }
        }
        if (byteString.size() == 2) {
            byte b2 = byteString.getByte(0);
            byte b3 = byteString.getByte(1);
            while (j2 < this.b) {
                byte[] bArr = segment.a;
                int i = segment.c;
                for (int i2 = (int) ((((long) segment.b) + j) - j2); i2 < i; i2++) {
                    byte b4 = bArr[i2];
                    if (b4 == b2 || b4 == b3) {
                        return ((long) (i2 - segment.b)) + j2;
                    }
                }
                long j5 = j2 + ((long) (segment.c - segment.b));
                segment = segment.f;
                j = j5;
                j2 = j;
            }
        } else {
            byte[] a2 = byteString.a();
            while (j2 < this.b) {
                byte[] bArr2 = segment.a;
                int i3 = segment.c;
                for (int i4 = (int) ((((long) segment.b) + j) - j2); i4 < i3; i4++) {
                    byte b5 = bArr2[i4];
                    for (byte b6 : a2) {
                        if (b5 == b6) {
                            return ((long) (i4 - segment.b)) + j2;
                        }
                    }
                }
                long j6 = j2 + ((long) (segment.c - segment.b));
                segment = segment.f;
                j = j6;
                j2 = j;
            }
        }
        return -1;
    }

    public boolean rangeEquals(long j, ByteString byteString) {
        return rangeEquals(j, byteString, 0, byteString.size());
    }

    public boolean rangeEquals(long j, ByteString byteString, int i, int i2) {
        if (j < 0 || i < 0 || i2 < 0 || this.b - j < ((long) i2) || byteString.size() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (getByte(j + ((long) i3)) != byteString.getByte(i + i3)) {
                return false;
            }
        }
        return true;
    }

    private boolean a(Segment segment, int i, ByteString byteString, int i2, int i3) {
        int i4 = segment.c;
        byte[] bArr = segment.a;
        while (i2 < i3) {
            if (i == i4) {
                segment = segment.f;
                byte[] bArr2 = segment.a;
                int i5 = segment.b;
                bArr = bArr2;
                i = i5;
                i4 = segment.c;
            }
            if (bArr[i] != byteString.getByte(i2)) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    public ByteString md5() {
        return a(CommonUtils.MD5_INSTANCE);
    }

    public ByteString sha1() {
        return a(CommonUtils.SHA1_INSTANCE);
    }

    public ByteString sha256() {
        return a("SHA-256");
    }

    public ByteString sha512() {
        return a("SHA-512");
    }

    private ByteString a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            if (this.a != null) {
                instance.update(this.a.a, this.a.b, this.a.c - this.a.b);
                Segment segment = this.a;
                while (true) {
                    segment = segment.f;
                    if (segment == this.a) {
                        break;
                    }
                    instance.update(segment.a, segment.b, segment.c - segment.b);
                }
            }
            return ByteString.of(instance.digest());
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public ByteString hmacSha1(ByteString byteString) {
        return a("HmacSHA1", byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        return a("HmacSHA256", byteString);
    }

    public ByteString hmacSha512(ByteString byteString) {
        return a("HmacSHA512", byteString);
    }

    private ByteString a(String str, ByteString byteString) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(new SecretKeySpec(byteString.toByteArray(), str));
            if (this.a != null) {
                instance.update(this.a.a, this.a.b, this.a.c - this.a.b);
                Segment segment = this.a;
                while (true) {
                    segment = segment.f;
                    if (segment == this.a) {
                        break;
                    }
                    instance.update(segment.a, segment.b, segment.c - segment.b);
                }
            }
            return ByteString.of(instance.doFinal());
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        if (this.b != buffer.b) {
            return false;
        }
        long j = 0;
        if (this.b == 0) {
            return true;
        }
        Segment segment = this.a;
        Segment segment2 = buffer.a;
        int i = segment.b;
        int i2 = segment2.b;
        while (j < this.b) {
            long min = (long) Math.min(segment.c - i, segment2.c - i2);
            int i3 = i2;
            int i4 = i;
            int i5 = 0;
            while (((long) i5) < min) {
                int i6 = i4 + 1;
                int i7 = i3 + 1;
                if (segment.a[i4] != segment2.a[i3]) {
                    return false;
                }
                i5++;
                i4 = i6;
                i3 = i7;
            }
            if (i4 == segment.c) {
                segment = segment.f;
                i = segment.b;
            } else {
                i = i4;
            }
            if (i3 == segment2.c) {
                segment2 = segment2.f;
                i2 = segment2.b;
            } else {
                i2 = i3;
            }
            j += min;
        }
        return true;
    }

    public int hashCode() {
        Segment segment = this.a;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            for (int i2 = segment.b; i2 < segment.c; i2++) {
                i = (i * 31) + segment.a[i2];
            }
            segment = segment.f;
        } while (segment != this.a);
        return i;
    }

    public String toString() {
        return snapshot().toString();
    }

    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.b == 0) {
            return buffer;
        }
        buffer.a = this.a.a();
        Segment segment = buffer.a;
        Segment segment2 = buffer.a;
        Segment segment3 = buffer.a;
        segment2.g = segment3;
        segment.f = segment3;
        Segment segment4 = this.a;
        while (true) {
            segment4 = segment4.f;
            if (segment4 != this.a) {
                buffer.a.g.a(segment4.a());
            } else {
                buffer.b = this.b;
                return buffer;
            }
        }
    }

    public ByteString snapshot() {
        if (this.b <= 2147483647L) {
            return snapshot((int) this.b);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("size > Integer.MAX_VALUE: ");
        sb.append(this.b);
        throw new IllegalArgumentException(sb.toString());
    }

    public ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, i);
    }

    public UnsafeCursor readUnsafe() {
        return readUnsafe(new UnsafeCursor());
    }

    public UnsafeCursor readUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = false;
        return unsafeCursor;
    }

    public UnsafeCursor readAndWriteUnsafe() {
        return readAndWriteUnsafe(new UnsafeCursor());
    }

    public UnsafeCursor readAndWriteUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = true;
        return unsafeCursor;
    }
}
