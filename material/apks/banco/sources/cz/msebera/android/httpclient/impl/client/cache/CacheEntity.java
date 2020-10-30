package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.util.Args;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

@Immutable
class CacheEntity implements HttpEntity, Serializable {
    private static final long serialVersionUID = -3467082284120936233L;
    private final HttpCacheEntry a;

    public void consumeContent() {
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return true;
    }

    public boolean isStreaming() {
        return false;
    }

    public CacheEntity(HttpCacheEntry httpCacheEntry) {
        this.a = httpCacheEntry;
    }

    public Header getContentType() {
        return this.a.getFirstHeader("Content-Type");
    }

    public Header getContentEncoding() {
        return this.a.getFirstHeader("Content-Encoding");
    }

    public long getContentLength() {
        return this.a.getResource().length();
    }

    public InputStream getContent() {
        return this.a.getResource().getInputStream();
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        InputStream inputStream = this.a.getResource().getInputStream();
        try {
            IOUtils.a(inputStream, outputStream);
        } finally {
            inputStream.close();
        }
    }

    public Object clone() {
        return super.clone();
    }
}
