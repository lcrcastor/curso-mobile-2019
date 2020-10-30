package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.message.BasicHeader;
import java.io.InputStream;
import java.io.OutputStream;

class MultipartFormEntity implements HttpEntity {
    private final AbstractMultipartForm a;
    private final Header b;
    private final long c;

    public Header getContentEncoding() {
        return null;
    }

    MultipartFormEntity(AbstractMultipartForm abstractMultipartForm, ContentType contentType, long j) {
        this.a = abstractMultipartForm;
        this.b = new BasicHeader("Content-Type", contentType.toString());
        this.c = j;
    }

    public boolean isRepeatable() {
        return this.c != -1;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        return this.c;
    }

    public Header getContentType() {
        return this.b;
    }

    public void consumeContent() {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    public void writeTo(OutputStream outputStream) {
        this.a.a(outputStream);
    }
}
