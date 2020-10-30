package com.crashlytics.android.core;

import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.eac.CertificateBody;

final class CodedOutputStream implements Flushable {
    private final byte[] a;
    private final int b;
    private int c = 0;
    private final OutputStream d;

    static class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    public static int b(float f) {
        return 4;
    }

    public static int b(boolean z) {
        return 1;
    }

    public static int d(long j) {
        if ((j & -128) == 0) {
            return 1;
        }
        if ((j & -16384) == 0) {
            return 2;
        }
        if ((j & -2097152) == 0) {
            return 3;
        }
        if ((j & -268435456) == 0) {
            return 4;
        }
        if ((j & -34359738368L) == 0) {
            return 5;
        }
        if ((j & -4398046511104L) == 0) {
            return 6;
        }
        if ((j & -562949953421312L) == 0) {
            return 7;
        }
        if ((j & -72057594037927936L) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static int l(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int n(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private CodedOutputStream(OutputStream outputStream, byte[] bArr) {
        this.d = outputStream;
        this.a = bArr;
        this.b = bArr.length;
    }

    public static CodedOutputStream a(OutputStream outputStream) {
        return a(outputStream, 4096);
    }

    public static CodedOutputStream a(OutputStream outputStream, int i) {
        return new CodedOutputStream(outputStream, new byte[i]);
    }

    public void a(int i, float f) {
        g(i, 5);
        a(f);
    }

    public void a(int i, long j) {
        g(i, 0);
        a(j);
    }

    public void a(int i, boolean z) {
        g(i, 0);
        a(z);
    }

    public void a(int i, ByteString byteString) {
        g(i, 2);
        a(byteString);
    }

    public void a(int i, int i2) {
        g(i, 0);
        b(i2);
    }

    public void b(int i, int i2) {
        g(i, 0);
        c(i2);
    }

    public void c(int i, int i2) {
        g(i, 0);
        d(i2);
    }

    public void a(float f) {
        m(Float.floatToRawIntBits(f));
    }

    public void a(long j) {
        c(j);
    }

    public void a(int i) {
        if (i >= 0) {
            k(i);
        } else {
            c((long) i);
        }
    }

    public void a(boolean z) {
        i(z ? 1 : 0);
    }

    public void a(ByteString byteString) {
        k(byteString.a());
        c(byteString);
    }

    public void b(int i) {
        k(i);
    }

    public void c(int i) {
        a(i);
    }

    public void d(int i) {
        k(n(i));
    }

    public static int b(int i, float f) {
        return j(i) + b(f);
    }

    public static int b(int i, long j) {
        return j(i) + b(j);
    }

    public static int b(int i, boolean z) {
        return j(i) + b(z);
    }

    public static int b(int i, ByteString byteString) {
        return j(i) + b(byteString);
    }

    public static int d(int i, int i2) {
        return j(i) + f(i2);
    }

    public static int e(int i, int i2) {
        return j(i) + g(i2);
    }

    public static int f(int i, int i2) {
        return j(i) + h(i2);
    }

    public static int b(long j) {
        return d(j);
    }

    public static int e(int i) {
        if (i >= 0) {
            return l(i);
        }
        return 10;
    }

    public static int b(ByteString byteString) {
        return l(byteString.a()) + byteString.a();
    }

    public static int f(int i) {
        return l(i);
    }

    public static int g(int i) {
        return e(i);
    }

    public static int h(int i) {
        return l(n(i));
    }

    private void a() {
        if (this.d == null) {
            throw new OutOfSpaceException();
        }
        this.d.write(this.a, 0, this.c);
        this.c = 0;
    }

    public void flush() {
        if (this.d != null) {
            a();
        }
    }

    public void a(byte b2) {
        if (this.c == this.b) {
            a();
        }
        byte[] bArr = this.a;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = b2;
    }

    public void i(int i) {
        a((byte) i);
    }

    public void c(ByteString byteString) {
        a(byteString, 0, byteString.a());
    }

    public void a(byte[] bArr) {
        a(bArr, 0, bArr.length);
    }

    public void a(byte[] bArr, int i, int i2) {
        if (this.b - this.c >= i2) {
            System.arraycopy(bArr, i, this.a, this.c, i2);
            this.c += i2;
            return;
        }
        int i3 = this.b - this.c;
        System.arraycopy(bArr, i, this.a, this.c, i3);
        int i4 = i + i3;
        int i5 = i2 - i3;
        this.c = this.b;
        a();
        if (i5 <= this.b) {
            System.arraycopy(bArr, i4, this.a, 0, i5);
            this.c = i5;
            return;
        }
        this.d.write(bArr, i4, i5);
    }

    public void a(ByteString byteString, int i, int i2) {
        if (this.b - this.c >= i2) {
            byteString.a(this.a, i, this.c, i2);
            this.c += i2;
            return;
        }
        int i3 = this.b - this.c;
        byteString.a(this.a, i, this.c, i3);
        int i4 = i + i3;
        int i5 = i2 - i3;
        this.c = this.b;
        a();
        if (i5 <= this.b) {
            byteString.a(this.a, i4, 0, i5);
            this.c = i5;
            return;
        }
        InputStream b2 = byteString.b();
        long j = (long) i4;
        if (j != b2.skip(j)) {
            throw new IllegalStateException("Skip failed.");
        }
        while (i5 > 0) {
            int min = Math.min(i5, this.b);
            int read = b2.read(this.a, 0, min);
            if (read != min) {
                throw new IllegalStateException("Read failed.");
            }
            this.d.write(this.a, 0, read);
            i5 -= read;
        }
    }

    public void g(int i, int i2) {
        k(WireFormat.a(i, i2));
    }

    public static int j(int i) {
        return l(WireFormat.a(i, 0));
    }

    public void k(int i) {
        while ((i & -128) != 0) {
            i((i & CertificateBody.profileType) | 128);
            i >>>= 7;
        }
        i(i);
    }

    public void c(long j) {
        while ((j & -128) != 0) {
            i((((int) j) & CertificateBody.profileType) | 128);
            j >>>= 7;
        }
        i((int) j);
    }

    public void m(int i) {
        i(i & 255);
        i((i >> 8) & 255);
        i((i >> 16) & 255);
        i((i >> 24) & 255);
    }
}
