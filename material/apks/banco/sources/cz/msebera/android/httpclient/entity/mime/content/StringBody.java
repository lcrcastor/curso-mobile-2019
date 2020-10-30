package cz.msebera.android.httpclient.entity.mime.content;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MIME;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringBody extends AbstractContentBody {
    private final byte[] a;

    public String getFilename() {
        return null;
    }

    public String getTransferEncoding() {
        return MIME.ENC_8BIT;
    }

    @Deprecated
    public static StringBody create(String str, String str2, Charset charset) {
        try {
            return new StringBody(str, str2, charset);
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Charset ");
            sb.append(charset);
            sb.append(" is not supported");
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    @Deprecated
    public static StringBody create(String str, Charset charset) {
        return create(str, null, charset);
    }

    @Deprecated
    public static StringBody create(String str) {
        return create(str, null, null);
    }

    @Deprecated
    public StringBody(String str, String str2, Charset charset) {
        this(str, ContentType.create(str2, charset));
    }

    @Deprecated
    public StringBody(String str, Charset charset) {
        this(str, HTTP.PLAIN_TEXT_TYPE, charset);
    }

    @Deprecated
    public StringBody(String str) {
        this(str, HTTP.PLAIN_TEXT_TYPE, Consts.ASCII);
    }

    public StringBody(String str, ContentType contentType) {
        super(contentType);
        Args.notNull(str, "Text");
        Charset charset = contentType.getCharset();
        if (charset == null) {
            charset = Consts.ASCII;
        }
        this.a = str.getBytes(charset);
    }

    public Reader getReader() {
        Charset charset = getContentType().getCharset();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.a);
        if (charset == null) {
            charset = Consts.ASCII;
        }
        return new InputStreamReader(byteArrayInputStream, charset);
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.a);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = byteArrayInputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    public long getContentLength() {
        return (long) this.a.length;
    }
}
