package okio;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

final class SegmentedByteString extends ByteString {
    final transient byte[][] e;
    final transient int[] f;

    SegmentedByteString(Buffer buffer, int i) {
        super(null);
        Util.a(buffer.b, 0, (long) i);
        int i2 = 0;
        Segment segment = buffer.a;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            if (segment.c == segment.b) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += segment.c - segment.b;
            i4++;
            segment = segment.f;
        }
        this.e = new byte[i4][];
        this.f = new int[(i4 * 2)];
        Segment segment2 = buffer.a;
        int i5 = 0;
        while (i2 < i) {
            this.e[i5] = segment2.a;
            i2 += segment2.c - segment2.b;
            if (i2 > i) {
                i2 = i;
            }
            this.f[i5] = i2;
            this.f[this.e.length + i5] = segment2.b;
            segment2.d = true;
            i5++;
            segment2 = segment2.f;
        }
    }

    public String utf8() {
        return b().utf8();
    }

    public String string(Charset charset) {
        return b().string(charset);
    }

    public String base64() {
        return b().base64();
    }

    public String hex() {
        return b().hex();
    }

    public ByteString toAsciiLowercase() {
        return b().toAsciiLowercase();
    }

    public ByteString toAsciiUppercase() {
        return b().toAsciiUppercase();
    }

    public ByteString md5() {
        return b().md5();
    }

    public ByteString sha1() {
        return b().sha1();
    }

    public ByteString sha256() {
        return b().sha256();
    }

    public ByteString hmacSha1(ByteString byteString) {
        return b().hmacSha1(byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        return b().hmacSha256(byteString);
    }

    public String base64Url() {
        return b().base64Url();
    }

    public ByteString substring(int i) {
        return b().substring(i);
    }

    public ByteString substring(int i, int i2) {
        return b().substring(i, i2);
    }

    public byte getByte(int i) {
        int i2;
        Util.a((long) this.f[this.e.length - 1], (long) i, 1);
        int a = a(i);
        if (a == 0) {
            i2 = 0;
        } else {
            i2 = this.f[a - 1];
        }
        return this.e[a][(i - i2) + this.f[this.e.length + a]];
    }

    private int a(int i) {
        int binarySearch = Arrays.binarySearch(this.f, 0, this.e.length, i + 1);
        return binarySearch >= 0 ? binarySearch : binarySearch ^ -1;
    }

    public int size() {
        return this.f[this.e.length - 1];
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[this.f[this.e.length - 1]];
        int length = this.e.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.f[length + i];
            int i4 = this.f[i];
            System.arraycopy(this.e[i], i3, bArr, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr;
    }

    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public void write(OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        int length = this.e.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.f[length + i];
            int i4 = this.f[i];
            outputStream.write(this.e[i], i3, i4 - i2);
            i++;
            i2 = i4;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Buffer buffer) {
        int length = this.e.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.f[length + i];
            int i4 = this.f[i];
            Segment segment = new Segment(this.e[i], i3, (i3 + i4) - i2, true, false);
            if (buffer.a == null) {
                segment.g = segment;
                segment.f = segment;
                buffer.a = segment;
            } else {
                buffer.a.g.a(segment);
            }
            i++;
            i2 = i4;
        }
        buffer.b += (long) i2;
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2, int i3) {
        int i4;
        if (i < 0 || i > size() - i3) {
            return false;
        }
        int a = a(i);
        while (i3 > 0) {
            if (a == 0) {
                i4 = 0;
            } else {
                i4 = this.f[a - 1];
            }
            int min = Math.min(i3, ((this.f[a] - i4) + i4) - i);
            if (!byteString.rangeEquals(i2, this.e[a], (i - i4) + this.f[this.e.length + a], min)) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            a++;
        }
        return true;
    }

    public boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        int i4;
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int a = a(i);
        while (i3 > 0) {
            if (a == 0) {
                i4 = 0;
            } else {
                i4 = this.f[a - 1];
            }
            int min = Math.min(i3, ((this.f[a] - i4) + i4) - i);
            if (!Util.a(this.e[a], (i - i4) + this.f[this.e.length + a], bArr, i2, min)) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            a++;
        }
        return true;
    }

    public int indexOf(byte[] bArr, int i) {
        return b().indexOf(bArr, i);
    }

    public int lastIndexOf(byte[] bArr, int i) {
        return b().lastIndexOf(bArr, i);
    }

    private ByteString b() {
        return new ByteString(toByteArray());
    }

    /* access modifiers changed from: 0000 */
    public byte[] a() {
        return toByteArray();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        if (rangeEquals(0, r5, 0, size()) != false) goto L_0x0021;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof okio.ByteString
            r2 = 0
            if (r1 == 0) goto L_0x0020
            okio.ByteString r5 = (okio.ByteString) r5
            int r1 = r5.size()
            int r3 = r4.size()
            if (r1 != r3) goto L_0x0020
            int r1 = r4.size()
            boolean r5 = r4.rangeEquals(r2, r5, r2, r1)
            if (r5 == 0) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.SegmentedByteString.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = this.c;
        if (i != 0) {
            return i;
        }
        int length = this.e.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 1;
        while (i2 < length) {
            byte[] bArr = this.e[i2];
            int i5 = this.f[length + i2];
            int i6 = this.f[i2];
            int i7 = (i6 - i3) + i5;
            while (i5 < i7) {
                i4 = (i4 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i3 = i6;
        }
        this.c = i4;
        return i4;
    }

    public String toString() {
        return b().toString();
    }

    private Object writeReplace() {
        return b();
    }
}
