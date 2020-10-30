package cz.msebera.android.httpclient.entity.mime.content;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MIME;
import cz.msebera.android.httpclient.util.Args;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamBody extends AbstractContentBody {
    private final InputStream a;
    private final String b;

    public long getContentLength() {
        return -1;
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    @Deprecated
    public InputStreamBody(InputStream inputStream, String str, String str2) {
        this(inputStream, ContentType.create(str), str2);
    }

    public InputStreamBody(InputStream inputStream, String str) {
        this(inputStream, ContentType.DEFAULT_BINARY, str);
    }

    public InputStreamBody(InputStream inputStream, ContentType contentType, String str) {
        super(contentType);
        Args.notNull(inputStream, "Input stream");
        this.a = inputStream;
        this.b = str;
    }

    public InputStreamBody(InputStream inputStream, ContentType contentType) {
        this(inputStream, contentType, (String) null);
    }

    public InputStream getInputStream() {
        return this.a;
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int read = this.a.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    outputStream.flush();
                    return;
                }
            }
        } finally {
            this.a.close();
        }
    }

    public String getFilename() {
        return this.b;
    }
}
