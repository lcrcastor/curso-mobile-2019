package cz.msebera.android.httpclient.impl.io;

import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.BufferInfo;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.params.CoreConnectionPNames;
import cz.msebera.android.httpclient.params.CoreProtocolPNames;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

@NotThreadSafe
@Deprecated
public abstract class AbstractSessionInputBuffer implements BufferInfo, SessionInputBuffer {
    private InputStream a;
    private byte[] b;
    private ByteArrayBuffer c;
    private Charset d;
    private boolean e;
    private int f;
    private int g;
    private HttpTransportMetricsImpl h;
    private CodingErrorAction i;
    private CodingErrorAction j;
    private int k;
    private int l;
    private CharsetDecoder m;
    private CharBuffer n;

    /* access modifiers changed from: protected */
    public void init(InputStream inputStream, int i2, HttpParams httpParams) {
        Args.notNull(inputStream, "Input stream");
        Args.notNegative(i2, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.a = inputStream;
        this.b = new byte[i2];
        this.k = 0;
        this.l = 0;
        this.c = new ByteArrayBuffer(i2);
        String str = (String) httpParams.getParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET);
        this.d = str != null ? Charset.forName(str) : Consts.ASCII;
        this.e = this.d.equals(Consts.ASCII);
        this.m = null;
        this.f = httpParams.getIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, -1);
        this.g = httpParams.getIntParameter(CoreConnectionPNames.MIN_CHUNK_LIMIT, 512);
        this.h = createTransportMetrics();
        CodingErrorAction codingErrorAction = (CodingErrorAction) httpParams.getParameter(CoreProtocolPNames.HTTP_MALFORMED_INPUT_ACTION);
        if (codingErrorAction == null) {
            codingErrorAction = CodingErrorAction.REPORT;
        }
        this.i = codingErrorAction;
        CodingErrorAction codingErrorAction2 = (CodingErrorAction) httpParams.getParameter(CoreProtocolPNames.HTTP_UNMAPPABLE_INPUT_ACTION);
        if (codingErrorAction2 == null) {
            codingErrorAction2 = CodingErrorAction.REPORT;
        }
        this.j = codingErrorAction2;
    }

    /* access modifiers changed from: protected */
    public HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }

    public int capacity() {
        return this.b.length;
    }

    public int length() {
        return this.l - this.k;
    }

    public int available() {
        return capacity() - length();
    }

    /* access modifiers changed from: protected */
    public int fillBuffer() {
        if (this.k > 0) {
            int i2 = this.l - this.k;
            if (i2 > 0) {
                System.arraycopy(this.b, this.k, this.b, 0, i2);
            }
            this.k = 0;
            this.l = i2;
        }
        int i3 = this.l;
        int read = this.a.read(this.b, i3, this.b.length - i3);
        if (read == -1) {
            return -1;
        }
        this.l = i3 + read;
        this.h.incrementBytesTransferred((long) read);
        return read;
    }

    /* access modifiers changed from: protected */
    public boolean hasBufferedData() {
        return this.k < this.l;
    }

    public int read() {
        while (!hasBufferedData()) {
            if (fillBuffer() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.b;
        int i2 = this.k;
        this.k = i2 + 1;
        return bArr[i2] & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            return 0;
        }
        if (hasBufferedData()) {
            int min = Math.min(i3, this.l - this.k);
            System.arraycopy(this.b, this.k, bArr, i2, min);
            this.k += min;
            return min;
        } else if (i3 > this.g) {
            int read = this.a.read(bArr, i2, i3);
            if (read > 0) {
                this.h.incrementBytesTransferred((long) read);
            }
            return read;
        } else {
            while (!hasBufferedData()) {
                if (fillBuffer() == -1) {
                    return -1;
                }
            }
            int min2 = Math.min(i3, this.l - this.k);
            System.arraycopy(this.b, this.k, bArr, i2, min2);
            this.k += min2;
            return min2;
        }
    }

    public int read(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return read(bArr, 0, bArr.length);
    }

    private int a() {
        for (int i2 = this.k; i2 < this.l; i2++) {
            if (this.b[i2] == 10) {
                return i2;
            }
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004d, code lost:
        if (r2 == -1) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readLine(cz.msebera.android.httpclient.util.CharArrayBuffer r8) {
        /*
            r7 = this;
            java.lang.String r0 = "Char array buffer"
            cz.msebera.android.httpclient.util.Args.notNull(r8, r0)
            r0 = 1
            r1 = 0
            r2 = 0
        L_0x0008:
            r3 = -1
            if (r0 == 0) goto L_0x0066
            int r4 = r7.a()
            if (r4 == r3) goto L_0x0031
            cz.msebera.android.httpclient.util.ByteArrayBuffer r0 = r7.c
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x001e
            int r8 = r7.a(r8, r4)
            return r8
        L_0x001e:
            int r4 = r4 + 1
            int r0 = r7.k
            int r0 = r4 - r0
            cz.msebera.android.httpclient.util.ByteArrayBuffer r3 = r7.c
            byte[] r5 = r7.b
            int r6 = r7.k
            r3.append(r5, r6, r0)
            r7.k = r4
        L_0x002f:
            r0 = 0
            goto L_0x0050
        L_0x0031:
            boolean r2 = r7.hasBufferedData()
            if (r2 == 0) goto L_0x0049
            int r2 = r7.l
            int r4 = r7.k
            int r2 = r2 - r4
            cz.msebera.android.httpclient.util.ByteArrayBuffer r4 = r7.c
            byte[] r5 = r7.b
            int r6 = r7.k
            r4.append(r5, r6, r2)
            int r2 = r7.l
            r7.k = r2
        L_0x0049:
            int r2 = r7.fillBuffer()
            if (r2 != r3) goto L_0x0050
            goto L_0x002f
        L_0x0050:
            int r3 = r7.f
            if (r3 <= 0) goto L_0x0008
            cz.msebera.android.httpclient.util.ByteArrayBuffer r3 = r7.c
            int r3 = r3.length()
            int r4 = r7.f
            if (r3 < r4) goto L_0x0008
            java.io.IOException r8 = new java.io.IOException
            java.lang.String r0 = "Maximum line length limit exceeded"
            r8.<init>(r0)
            throw r8
        L_0x0066:
            if (r2 != r3) goto L_0x0071
            cz.msebera.android.httpclient.util.ByteArrayBuffer r0 = r7.c
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0071
            return r3
        L_0x0071:
            int r8 = r7.a(r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.io.AbstractSessionInputBuffer.readLine(cz.msebera.android.httpclient.util.CharArrayBuffer):int");
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
        if (this.e) {
            charArrayBuffer.append(this.c, 0, length);
        } else {
            length = a(charArrayBuffer, ByteBuffer.wrap(this.c.buffer(), 0, length));
        }
        this.c.clear();
        return length;
    }

    private int a(CharArrayBuffer charArrayBuffer, int i2) {
        int i3 = this.k;
        this.k = i2 + 1;
        if (i2 > i3 && this.b[i2 - 1] == 13) {
            i2--;
        }
        int i4 = i2 - i3;
        if (!this.e) {
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
        if (this.m == null) {
            this.m = this.d.newDecoder();
            this.m.onMalformedInput(this.i);
            this.m.onUnmappableCharacter(this.j);
        }
        if (this.n == null) {
            this.n = CharBuffer.allocate(1024);
        }
        this.m.reset();
        while (byteBuffer.hasRemaining()) {
            i2 += a(this.m.decode(byteBuffer, this.n, true), charArrayBuffer, byteBuffer);
        }
        int a2 = i2 + a(this.m.flush(this.n), charArrayBuffer, byteBuffer);
        this.n.clear();
        return a2;
    }

    private int a(CoderResult coderResult, CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.n.flip();
        int remaining = this.n.remaining();
        while (this.n.hasRemaining()) {
            charArrayBuffer.append(this.n.get());
        }
        this.n.compact();
        return remaining;
    }

    public String readLine() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }

    public HttpTransportMetrics getMetrics() {
        return this.h;
    }
}
