package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
class RequestEntityProxy implements HttpEntity {
    private final HttpEntity a;
    private boolean b = false;

    static void a(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        HttpEntity entity = httpEntityEnclosingRequest.getEntity();
        if (entity != null && !entity.isRepeatable() && !a(entity)) {
            httpEntityEnclosingRequest.setEntity(new RequestEntityProxy(entity));
        }
    }

    static boolean a(HttpEntity httpEntity) {
        return httpEntity instanceof RequestEntityProxy;
    }

    static boolean a(HttpRequest httpRequest) {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            if (entity != null) {
                if (!a(entity) || ((RequestEntityProxy) entity).a()) {
                    return entity.isRepeatable();
                }
                return true;
            }
        }
        return true;
    }

    RequestEntityProxy(HttpEntity httpEntity) {
        this.a = httpEntity;
    }

    public boolean a() {
        return this.b;
    }

    public boolean isRepeatable() {
        return this.a.isRepeatable();
    }

    public boolean isChunked() {
        return this.a.isChunked();
    }

    public long getContentLength() {
        return this.a.getContentLength();
    }

    public Header getContentType() {
        return this.a.getContentType();
    }

    public Header getContentEncoding() {
        return this.a.getContentEncoding();
    }

    public InputStream getContent() {
        return this.a.getContent();
    }

    public void writeTo(OutputStream outputStream) {
        this.b = true;
        this.a.writeTo(outputStream);
    }

    public boolean isStreaming() {
        return this.a.isStreaming();
    }

    @Deprecated
    public void consumeContent() {
        this.b = true;
        this.a.consumeContent();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RequestEntityProxy{");
        sb.append(this.a);
        sb.append('}');
        return sb.toString();
    }
}
