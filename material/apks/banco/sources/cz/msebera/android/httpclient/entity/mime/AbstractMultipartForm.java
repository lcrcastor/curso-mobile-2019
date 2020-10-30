package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

abstract class AbstractMultipartForm {
    private static final ByteArrayBuffer c = a(MIME.DEFAULT_CHARSET, ": ");
    private static final ByteArrayBuffer d = a(MIME.DEFAULT_CHARSET, "\r\n");
    private static final ByteArrayBuffer e = a(MIME.DEFAULT_CHARSET, "--");
    final Charset a;
    final String b;

    public abstract List<FormBodyPart> a();

    /* access modifiers changed from: protected */
    public abstract void a(FormBodyPart formBodyPart, OutputStream outputStream);

    private static ByteArrayBuffer a(Charset charset, String str) {
        ByteBuffer encode = charset.encode(CharBuffer.wrap(str));
        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(encode.remaining());
        byteArrayBuffer.append(encode.array(), encode.position(), encode.remaining());
        return byteArrayBuffer;
    }

    private static void a(ByteArrayBuffer byteArrayBuffer, OutputStream outputStream) {
        outputStream.write(byteArrayBuffer.buffer(), 0, byteArrayBuffer.length());
    }

    private static void a(String str, Charset charset, OutputStream outputStream) {
        a(a(charset, str), outputStream);
    }

    private static void a(String str, OutputStream outputStream) {
        a(a(MIME.DEFAULT_CHARSET, str), outputStream);
    }

    protected static void a(MinimalField minimalField, OutputStream outputStream) {
        a(minimalField.getName(), outputStream);
        a(c, outputStream);
        a(minimalField.getBody(), outputStream);
        a(d, outputStream);
    }

    protected static void a(MinimalField minimalField, Charset charset, OutputStream outputStream) {
        a(minimalField.getName(), charset, outputStream);
        a(c, outputStream);
        a(minimalField.getBody(), charset, outputStream);
        a(d, outputStream);
    }

    public AbstractMultipartForm(Charset charset, String str) {
        Args.notNull(str, "Multipart boundary");
        if (charset == null) {
            charset = MIME.DEFAULT_CHARSET;
        }
        this.a = charset;
        this.b = str;
    }

    /* access modifiers changed from: 0000 */
    public void a(OutputStream outputStream, boolean z) {
        ByteArrayBuffer a2 = a(this.a, this.b);
        for (FormBodyPart formBodyPart : a()) {
            a(e, outputStream);
            a(a2, outputStream);
            a(d, outputStream);
            a(formBodyPart, outputStream);
            a(d, outputStream);
            if (z) {
                formBodyPart.getBody().writeTo(outputStream);
            }
            a(d, outputStream);
        }
        a(e, outputStream);
        a(a2, outputStream);
        a(e, outputStream);
        a(d, outputStream);
    }

    public void a(OutputStream outputStream) {
        a(outputStream, true);
    }

    public long b() {
        long j = 0;
        for (FormBodyPart body : a()) {
            long contentLength = body.getBody().getContentLength();
            if (contentLength < 0) {
                return -1;
            }
            j += contentLength;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a((OutputStream) byteArrayOutputStream, false);
            return j + ((long) byteArrayOutputStream.toByteArray().length);
        } catch (IOException unused) {
            return -1;
        }
    }
}
