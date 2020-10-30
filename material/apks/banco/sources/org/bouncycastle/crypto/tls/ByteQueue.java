package org.bouncycastle.crypto.tls;

public class ByteQueue {
    private byte[] a;
    private int b;
    private int c;

    public ByteQueue() {
        this(1024);
    }

    public ByteQueue(int i) {
        this.b = 0;
        this.c = 0;
        this.a = new byte[i];
    }

    public static int nextTwoPow(int i) {
        int i2 = i | (i >> 1);
        int i3 = i2 | (i2 >> 2);
        int i4 = i3 | (i3 >> 4);
        int i5 = i4 | (i4 >> 8);
        return (i5 | (i5 >> 16)) + 1;
    }

    public void addData(byte[] bArr, int i, int i2) {
        if (this.b + this.c + i2 > this.a.length) {
            int nextTwoPow = nextTwoPow(this.c + i2);
            if (nextTwoPow > this.a.length) {
                byte[] bArr2 = new byte[nextTwoPow];
                System.arraycopy(this.a, this.b, bArr2, 0, this.c);
                this.a = bArr2;
            } else {
                System.arraycopy(this.a, this.b, this.a, 0, this.c);
            }
            this.b = 0;
        }
        System.arraycopy(bArr, i, this.a, this.b + this.c, i2);
        this.c += i2;
    }

    public void read(byte[] bArr, int i, int i2, int i3) {
        if (bArr.length - i < i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Buffer size of ");
            sb.append(bArr.length);
            sb.append(" is too small for a read of ");
            sb.append(i2);
            sb.append(" bytes");
            throw new IllegalArgumentException(sb.toString());
        } else if (this.c - i3 < i2) {
            throw new IllegalStateException("Not enough data to read");
        } else {
            System.arraycopy(this.a, this.b + i3, bArr, i, i2);
        }
    }

    public void removeData(int i) {
        if (i > this.c) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot remove ");
            sb.append(i);
            sb.append(" bytes, only got ");
            sb.append(this.c);
            throw new IllegalStateException(sb.toString());
        }
        this.c -= i;
        this.b += i;
    }

    public void removeData(byte[] bArr, int i, int i2, int i3) {
        read(bArr, i, i2, i3);
        removeData(i3 + i2);
    }

    public byte[] removeData(int i, int i2) {
        byte[] bArr = new byte[i];
        removeData(bArr, 0, i, i2);
        return bArr;
    }

    public int size() {
        return this.c;
    }
}
