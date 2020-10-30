package cz.msebera.android.httpclient.impl.io;

import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.BufferInfo;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import cz.msebera.android.httpclient.params.CoreConnectionPNames;
import cz.msebera.android.httpclient.params.CoreProtocolPNames;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

@NotThreadSafe
@Deprecated
public abstract class AbstractSessionOutputBuffer implements BufferInfo, SessionOutputBuffer {
    private static final byte[] a = {Ascii.CR, 10};
    private OutputStream b;
    private ByteArrayBuffer c;
    private Charset d;
    private boolean e;
    private int f;
    private HttpTransportMetricsImpl g;
    private CodingErrorAction h;
    private CodingErrorAction i;
    private CharsetEncoder j;
    private ByteBuffer k;

    protected AbstractSessionOutputBuffer(OutputStream outputStream, int i2, Charset charset, int i3, CodingErrorAction codingErrorAction, CodingErrorAction codingErrorAction2) {
        Args.notNull(outputStream, "Input stream");
        Args.notNegative(i2, "Buffer size");
        this.b = outputStream;
        this.c = new ByteArrayBuffer(i2);
        if (charset == null) {
            charset = Consts.ASCII;
        }
        this.d = charset;
        this.e = this.d.equals(Consts.ASCII);
        this.j = null;
        if (i3 < 0) {
            i3 = 512;
        }
        this.f = i3;
        this.g = createTransportMetrics();
        if (codingErrorAction == null) {
            codingErrorAction = CodingErrorAction.REPORT;
        }
        this.h = codingErrorAction;
        if (codingErrorAction2 == null) {
            codingErrorAction2 = CodingErrorAction.REPORT;
        }
        this.i = codingErrorAction2;
    }

    public AbstractSessionOutputBuffer() {
    }

    /* access modifiers changed from: protected */
    public void init(OutputStream outputStream, int i2, HttpParams httpParams) {
        Args.notNull(outputStream, "Input stream");
        Args.notNegative(i2, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.b = outputStream;
        this.c = new ByteArrayBuffer(i2);
        String str = (String) httpParams.getParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET);
        this.d = str != null ? Charset.forName(str) : Consts.ASCII;
        this.e = this.d.equals(Consts.ASCII);
        this.j = null;
        this.f = httpParams.getIntParameter(CoreConnectionPNames.MIN_CHUNK_LIMIT, 512);
        this.g = createTransportMetrics();
        CodingErrorAction codingErrorAction = (CodingErrorAction) httpParams.getParameter(CoreProtocolPNames.HTTP_MALFORMED_INPUT_ACTION);
        if (codingErrorAction == null) {
            codingErrorAction = CodingErrorAction.REPORT;
        }
        this.h = codingErrorAction;
        CodingErrorAction codingErrorAction2 = (CodingErrorAction) httpParams.getParameter(CoreProtocolPNames.HTTP_UNMAPPABLE_INPUT_ACTION);
        if (codingErrorAction2 == null) {
            codingErrorAction2 = CodingErrorAction.REPORT;
        }
        this.i = codingErrorAction2;
    }

    /* access modifiers changed from: protected */
    public HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }

    public int capacity() {
        return this.c.capacity();
    }

    public int length() {
        return this.c.length();
    }

    public int available() {
        return capacity() - length();
    }

    /* access modifiers changed from: protected */
    public void flushBuffer() {
        int length = this.c.length();
        if (length > 0) {
            this.b.write(this.c.buffer(), 0, length);
            this.c.clear();
            this.g.incrementBytesTransferred((long) length);
        }
    }

    public void flush() {
        flushBuffer();
        this.b.flush();
    }

    public void write(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            if (i3 > this.f || i3 > this.c.capacity()) {
                flushBuffer();
                this.b.write(bArr, i2, i3);
                this.g.incrementBytesTransferred((long) i3);
            } else {
                if (i3 > this.c.capacity() - this.c.length()) {
                    flushBuffer();
                }
                this.c.append(bArr, i2, i3);
            }
        }
    }

    public void write(byte[] bArr) {
        if (bArr != null) {
            write(bArr, 0, bArr.length);
        }
    }

    public void write(int i2) {
        if (this.c.isFull()) {
            flushBuffer();
        }
        this.c.append(i2);
    }

    public void writeLine(String str) {
        if (str != null) {
            if (str.length() > 0) {
                if (this.e) {
                    for (int i2 = 0; i2 < str.length(); i2++) {
                        write((int) str.charAt(i2));
                    }
                } else {
                    a(CharBuffer.wrap(str));
                }
            }
            write(a);
        }
    }

    public void writeLine(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer != null) {
            int i2 = 0;
            if (this.e) {
                int length = charArrayBuffer.length();
                while (length > 0) {
                    int min = Math.min(this.c.capacity() - this.c.length(), length);
                    if (min > 0) {
                        this.c.append(charArrayBuffer, i2, min);
                    }
                    if (this.c.isFull()) {
                        flushBuffer();
                    }
                    i2 += min;
                    length -= min;
                }
            } else {
                a(CharBuffer.wrap(charArrayBuffer.buffer(), 0, charArrayBuffer.length()));
            }
            write(a);
        }
    }

    private void a(CharBuffer charBuffer) {
        if (charBuffer.hasRemaining()) {
            if (this.j == null) {
                this.j = this.d.newEncoder();
                this.j.onMalformedInput(this.h);
                this.j.onUnmappableCharacter(this.i);
            }
            if (this.k == null) {
                this.k = ByteBuffer.allocate(1024);
            }
            this.j.reset();
            while (charBuffer.hasRemaining()) {
                a(this.j.encode(charBuffer, this.k, true));
            }
            a(this.j.flush(this.k));
            this.k.clear();
        }
    }

    private void a(CoderResult coderResult) {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.k.flip();
        while (this.k.hasRemaining()) {
            write((int) this.k.get());
        }
        this.k.compact();
    }

    public HttpTransportMetrics getMetrics() {
        return this.g;
    }
}
