package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.entity.HttpEntityWrapper;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
@Deprecated
public class EntityEnclosingRequestWrapper extends RequestWrapper implements HttpEntityEnclosingRequest {
    private HttpEntity a;
    /* access modifiers changed from: private */
    public boolean b;

    class EntityWrapper extends HttpEntityWrapper {
        EntityWrapper(HttpEntity httpEntity) {
            super(httpEntity);
        }

        public void consumeContent() {
            EntityEnclosingRequestWrapper.this.b = true;
            super.consumeContent();
        }

        public InputStream getContent() {
            EntityEnclosingRequestWrapper.this.b = true;
            return super.getContent();
        }

        public void writeTo(OutputStream outputStream) {
            EntityEnclosingRequestWrapper.this.b = true;
            super.writeTo(outputStream);
        }
    }

    public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        super(httpEntityEnclosingRequest);
        setEntity(httpEntityEnclosingRequest.getEntity());
    }

    public HttpEntity getEntity() {
        return this.a;
    }

    public void setEntity(HttpEntity httpEntity) {
        this.a = httpEntity != null ? new EntityWrapper(httpEntity) : null;
        this.b = false;
    }

    public boolean expectContinue() {
        Header firstHeader = getFirstHeader("Expect");
        return firstHeader != null && HTTP.EXPECT_CONTINUE.equalsIgnoreCase(firstHeader.getValue());
    }

    public boolean isRepeatable() {
        return this.a == null || this.a.isRepeatable() || !this.b;
    }
}
