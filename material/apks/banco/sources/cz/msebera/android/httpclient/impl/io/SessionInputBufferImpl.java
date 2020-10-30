package cz.msebera.android.httpclient.impl.io;

import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.MessageConstraintException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.config.MessageConstraints;
import cz.msebera.android.httpclient.io.BufferInfo;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

@NotThreadSafe
public class SessionInputBufferImpl implements BufferInfo, SessionInputBuffer {
    private final HttpTransportMetricsImpl a;
    private final byte[] b;
    private final ByteArrayBuffer c;
    private final int d;
    private final MessageConstraints e;
    private final CharsetDecoder f;
    private InputStream g;
    private int h;
    private int i;
    private CharBuffer j;

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int i2, int i3, MessageConstraints messageConstraints, CharsetDecoder charsetDecoder) {
        Args.notNull(httpTransportMetricsImpl, "HTTP transport metrcis");
        Args.positive(i2, "Buffer size");
        this.a = httpTransportMetricsImpl;
        this.b = new byte[i2];
        this.h = 0;
        this.i = 0;
        if (i3 < 0) {
            i3 = 512;
        }
        this.d = i3;
        if (messageConstraints == null) {
            messageConstraints = MessageConstraints.DEFAULT;
        }
        this.e = messageConstraints;
        this.c = new ByteArrayBuffer(i2);
        this.f = charsetDecoder;
    }

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int i2) {
        this(httpTransportMetricsImpl, i2, i2, null, null);
    }

    public void bind(InputStream inputStream) {
        this.g = inputStream;
    }

    public boolean isBound() {
        return this.g != null;
    }

    public int capacity() {
        return this.b.length;
    }

    public int length() {
        return this.i - this.h;
    }

    public int available() {
        return capacity() - length();
    }

    private int a(byte[] bArr, int i2, int i3) {
        Asserts.notNull(this.g, "Input stream");
        return this.g.read(bArr, i2, i3);
    }

    public int fillBuffer() {
        if (this.h > 0) {
            int i2 = this.i - this.h;
            if (i2 > 0) {
                System.arraycopy(this.b, this.h, this.b, 0, i2);
            }
            this.h = 0;
            this.i = i2;
        }
        int i3 = this.i;
        int a2 = a(this.b, i3, this.b.length - i3);
        if (a2 == -1) {
            return -1;
        }
        this.i = i3 + a2;
        this.a.incrementBytesTransferred((long) a2);
        return a2;
    }

    public boolean hasBufferedData() {
        return this.h < this.i;
    }

    public void clear() {
        this.h = 0;
        this.i = 0;
    }

    public int read() {
        while (!hasBufferedData()) {
            if (fillBuffer() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.b;
        int i2 = this.h;
        this.h = i2 + 1;
        return bArr[i2] & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            return 0;
        }
        if (hasBufferedData()) {
            int min = Math.min(i3, this.i - this.h);
            System.arraycopy(this.b, this.h, bArr, i2, min);
            this.h += min;
            return min;
        } else if (i3 > this.d) {
            int a2 = a(bArr, i2, i3);
            if (a2 > 0) {
                this.a.incrementBytesTransferred((long) a2);
            }
            return a2;
        } else {
            while (!hasBufferedData()) {
                if (fillBuffer() == -1) {
                    return -1;
                }
            }
            int min2 = Math.min(i3, this.i - this.h);
            System.arraycopy(this.b, this.h, bArr, i2, min2);
            this.h += min2;
            return min2;
        }
    }

    public int read(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return read(bArr, 0, bArr.length);
    }

    public int readLine(CharArrayBuffer charArrayBuffer) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int maxLineLength = this.e.getMaxLineLength();
        boolean z = true;
        int i2 = 0;
        while (z) {
            int i3 = this.h;
            while (true) {
                if (i3 >= this.i) {
                    i3 = -1;
                    break;
                } else if (this.b[i3] == 10) {
                    break;
                } else {
                    i3++;
                }
            }
            if (maxLineLength > 0) {
                if ((this.c.length() + (i3 > 0 ? i3 : this.i)) - this.h >= maxLineLength) {
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                }
            }
            if (i3 == -1) {
                if (hasBufferedData()) {
                    this.c.append(this.b, this.h, this.i - this.h);
                    this.h = this.i;
                }
                i2 = fillBuffer();
                if (i2 != -1) {
                }
            } else if (this.c.isEmpty()) {
                return a(charArrayBuffer, i3);
            } else {
                int i4 = i3 + 1;
                this.c.append(this.b, this.h, i4 - this.h);
                this.h = i4;
            }
            z = false;
        }
        if (i2 != -1 || !this.c.isEmpty()) {
            return a(charArrayBuffer);
        }
        return -1;
    }

    private int a(CharArrayBuffer charArrayBuffer) {
        int length = this.c.length();
        if (length > 0) {
            if (this.c.byteAt(length - 1) == 10) {
                length--;
            }
            if (length > 0 && this.c.byteAt(length - 1) == 13) {
                length--;
            }
        }
        if (this.f == null) {
            charArrayBuffer.append(this.c, 0, length);
        } else {
            length = a(charArrayBuffer, ByteBuffer.wrap(this.c.buffer(), 0, length));
        }
        this.c.clear();
        return length;
    }

    private int a(CharArrayBuffer charArrayBuffer, int i2) {
        int i3 = this.h;
        this.h = i2 + 1;
        if (i2 > i3 && this.b[i2 - 1] == 13) {
            i2--;
        }
        int i4 = i2 - i3;
        if (this.f != null) {
            return a(charArrayBuffer, ByteBuffer.wrap(this.b, i3, i4));
        }
        charArrayBuffer.append(this.b, i3, i4);
        return i4;
    }

    private int a(CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) {
        int i2 = 0;
        if (!byteBuffer.hasRemaining()) {
            return 0;
        }
        if (this.j == null) {
            this.j = CharBuffer.allocate(1024);
        }
        this.f.reset();
        while (byteBuffer.hasRemaining()) {
            i2 += a(this.f.decode(byteBuffer, this.j, true), charArrayBuffer, byteBuffer);
        }
        int a2 = i2 + a(this.f.flush(this.j), charArrayBuffer, byteBuffer);
        this.j.clear();
        return a2;
    }

    private int a(CoderResult coderResult, CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.j.flip();
        int remaining = this.j.remaining();
        while (this.j.hasRemaining()) {
            charArrayBuffer.append(this.j.get());
        }
        this.j.compact();
        return remaining;
    }

    public String readLine() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }

    public boolean isDataAvailable(int i2) {
        return hasBufferedData();
    }

    public HttpTransportMetrics getMetrics() {
        return this.a;
    }
}
