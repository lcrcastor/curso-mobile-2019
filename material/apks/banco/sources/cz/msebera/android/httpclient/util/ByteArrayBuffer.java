package cz.msebera.android.httpclient.util;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.Serializable;

@NotThreadSafe
public final class ByteArrayBuffer implements Serializable {
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] a;
    private int b;

    public ByteArrayBuffer(int i) {
        Args.notNegative(i, "Buffer capacity");
        this.a = new byte[i];
    }

    private void a(int i) {
        byte[] bArr = new byte[Math.max(this.a.length << 1, i)];
        System.arraycopy(this.a, 0, bArr, 0, this.b);
        this.a = bArr;
    }

    public void append(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            if (i >= 0 && i <= bArr.length && i2 >= 0) {
                int i3 = i + i2;
                if (i3 >= 0 && i3 <= bArr.length) {
                    if (i2 != 0) {
                        int i4 = this.b + i2;
                        if (i4 > this.a.length) {
                            a(i4);
                        }
                        System.arraycopy(bArr, i, this.a, this.b, i2);
                        this.b = i4;
                        return;
                    }
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("off: ");
            sb.append(i);
            sb.append(" len: ");
            sb.append(i2);
            sb.append(" b.length: ");
            sb.append(bArr.length);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public void append(int i) {
        int i2 = this.b + 1;
        if (i2 > this.a.length) {
            a(i2);
        }
        this.a[this.b] = (byte) i;
        this.b = i2;
    }

    public void append(char[] cArr, int i, int i2) {
        if (cArr != null) {
            if (i >= 0 && i <= cArr.length && i2 >= 0) {
                int i3 = i + i2;
                if (i3 >= 0 && i3 <= cArr.length) {
                    if (i2 != 0) {
                        int i4 = this.b;
                        int i5 = i2 + i4;
                        if (i5 > this.a.length) {
                            a(i5);
                        }
                        while (i4 < i5) {
                            this.a[i4] = (byte) cArr[i];
                            i++;
                            i4++;
                        }
                        this.b = i5;
                        return;
                    }
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("off: ");
            sb.append(i);
            sb.append(" len: ");
            sb.append(i2);
            sb.append(" b.length: ");
            sb.append(cArr.length);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public void append(CharArrayBuffer charArrayBuffer, int i, int i2) {
        if (charArrayBuffer != null) {
            append(charArrayBuffer.buffer(), i, i2);
        }
    }

    public void clear() {
        this.b = 0;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[this.b];
        if (this.b > 0) {
            System.arraycopy(this.a, 0, bArr, 0, this.b);
        }
        return bArr;
    }

    public int byteAt(int i) {
        return this.a[i];
    }

    public int capacity() {
        return this.a.length;
    }

    public int length() {
        return this.b;
    }

    public void ensureCapacity(int i) {
        if (i > 0 && i > this.a.length - this.b) {
            a(this.b + i);
        }
    }

    public byte[] buffer() {
        return this.a;
    }

    public void setLength(int i) {
        if (i < 0 || i > this.a.length) {
            StringBuilder sb = new StringBuilder();
            sb.append("len: ");
            sb.append(i);
            sb.append(" < 0 or > buffer len: ");
            sb.append(this.a.length);
            throw new IndexOutOfBoundsException(sb.toString());
        }
        this.b = i;
    }

    public boolean isEmpty() {
        return this.b == 0;
    }

    public boolean isFull() {
        return this.b == this.a.length;
    }

    public int indexOf(byte b2, int i, int i2) {
        if (i < 0) {
            i = 0;
        }
        if (i2 > this.b) {
            i2 = this.b;
        }
        if (i > i2) {
            return -1;
        }
        while (i < i2) {
            if (this.a[i] == b2) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int indexOf(byte b2) {
        return indexOf(b2, 0, this.b);
    }
}
