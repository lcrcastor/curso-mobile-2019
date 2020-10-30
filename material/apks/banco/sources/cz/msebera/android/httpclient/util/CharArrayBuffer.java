package cz.msebera.android.httpclient.util;

import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.Serializable;
import java.nio.CharBuffer;

@NotThreadSafe
public final class CharArrayBuffer implements Serializable, CharSequence {
    private static final long serialVersionUID = -6208952725094867135L;
    private char[] a;
    private int b;

    public CharArrayBuffer(int i) {
        Args.notNegative(i, "Buffer capacity");
        this.a = new char[i];
    }

    private void a(int i) {
        char[] cArr = new char[Math.max(this.a.length << 1, i)];
        System.arraycopy(this.a, 0, cArr, 0, this.b);
        this.a = cArr;
    }

    public void append(char[] cArr, int i, int i2) {
        if (cArr != null) {
            if (i >= 0 && i <= cArr.length && i2 >= 0) {
                int i3 = i + i2;
                if (i3 >= 0 && i3 <= cArr.length) {
                    if (i2 != 0) {
                        int i4 = this.b + i2;
                        if (i4 > this.a.length) {
                            a(i4);
                        }
                        System.arraycopy(cArr, i, this.a, this.b, i2);
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
            sb.append(cArr.length);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public void append(String str) {
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        int i = this.b + length;
        if (i > this.a.length) {
            a(i);
        }
        str.getChars(0, length, this.a, this.b);
        this.b = i;
    }

    public void append(CharArrayBuffer charArrayBuffer, int i, int i2) {
        if (charArrayBuffer != null) {
            append(charArrayBuffer.a, i, i2);
        }
    }

    public void append(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer != null) {
            append(charArrayBuffer.a, 0, charArrayBuffer.b);
        }
    }

    public void append(char c) {
        int i = this.b + 1;
        if (i > this.a.length) {
            a(i);
        }
        this.a[this.b] = c;
        this.b = i;
    }

    public void append(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            if (i >= 0 && i <= bArr.length && i2 >= 0) {
                int i3 = i + i2;
                if (i3 >= 0 && i3 <= bArr.length) {
                    if (i2 != 0) {
                        int i4 = this.b;
                        int i5 = i2 + i4;
                        if (i5 > this.a.length) {
                            a(i5);
                        }
                        while (i4 < i5) {
                            this.a[i4] = (char) (bArr[i] & UnsignedBytes.MAX_VALUE);
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
            sb.append(bArr.length);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public void append(ByteArrayBuffer byteArrayBuffer, int i, int i2) {
        if (byteArrayBuffer != null) {
            append(byteArrayBuffer.buffer(), i, i2);
        }
    }

    public void append(Object obj) {
        append(String.valueOf(obj));
    }

    public void clear() {
        this.b = 0;
    }

    public char[] toCharArray() {
        char[] cArr = new char[this.b];
        if (this.b > 0) {
            System.arraycopy(this.a, 0, cArr, 0, this.b);
        }
        return cArr;
    }

    public char charAt(int i) {
        return this.a[i];
    }

    public char[] buffer() {
        return this.a;
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

    public int indexOf(int i, int i2, int i3) {
        if (i2 < 0) {
            i2 = 0;
        }
        if (i3 > this.b) {
            i3 = this.b;
        }
        if (i2 > i3) {
            return -1;
        }
        while (i2 < i3) {
            if (this.a[i2] == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public int indexOf(int i) {
        return indexOf(i, 0, this.b);
    }

    public String substring(int i, int i2) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Negative beginIndex: ");
            sb.append(i);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 > this.b) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex: ");
            sb2.append(i2);
            sb2.append(" > length: ");
            sb2.append(this.b);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (i <= i2) {
            return new String(this.a, i, i2 - i);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("beginIndex: ");
            sb3.append(i);
            sb3.append(" > endIndex: ");
            sb3.append(i2);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public String substringTrimmed(int i, int i2) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Negative beginIndex: ");
            sb.append(i);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 > this.b) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex: ");
            sb2.append(i2);
            sb2.append(" > length: ");
            sb2.append(this.b);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (i > i2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("beginIndex: ");
            sb3.append(i);
            sb3.append(" > endIndex: ");
            sb3.append(i2);
            throw new IndexOutOfBoundsException(sb3.toString());
        } else {
            while (i < i2 && HTTP.isWhitespace(this.a[i])) {
                i++;
            }
            while (i2 > i && HTTP.isWhitespace(this.a[i2 - 1])) {
                i2--;
            }
            return new String(this.a, i, i2 - i);
        }
    }

    public CharSequence subSequence(int i, int i2) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Negative beginIndex: ");
            sb.append(i);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 > this.b) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex: ");
            sb2.append(i2);
            sb2.append(" > length: ");
            sb2.append(this.b);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (i <= i2) {
            return CharBuffer.wrap(this.a, i, i2);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("beginIndex: ");
            sb3.append(i);
            sb3.append(" > endIndex: ");
            sb3.append(i2);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public String toString() {
        return new String(this.a, 0, this.b);
    }
}
