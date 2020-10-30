package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.cache.InputLimit;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import java.io.InputStream;
import java.lang.reflect.Proxy;

@NotThreadSafe
class SizeLimitedResponseReader {
    private final ResourceFactory a;
    private final long b;
    private final HttpRequest c;
    /* access modifiers changed from: private */
    public final CloseableHttpResponse d;
    private InputStream e;
    private InputLimit f;
    private Resource g;
    private boolean h;

    public SizeLimitedResponseReader(ResourceFactory resourceFactory, long j, HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse) {
        this.a = resourceFactory;
        this.b = j;
        this.c = httpRequest;
        this.d = closeableHttpResponse;
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (!this.h) {
            g();
        }
    }

    private void e() {
        if (this.h) {
            throw new IllegalStateException("Response has already been consumed");
        }
    }

    private void f() {
        if (!this.h) {
            throw new IllegalStateException("Response has not been consumed");
        }
    }

    private void g() {
        e();
        this.h = true;
        this.f = new InputLimit(this.b);
        HttpEntity entity = this.d.getEntity();
        if (entity != null) {
            String uri = this.c.getRequestLine().getUri();
            this.e = entity.getContent();
            try {
                this.g = this.a.generate(uri, this.e, this.f);
            } finally {
                if (!this.f.isReached()) {
                    this.e.close();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        f();
        return this.f.isReached();
    }

    /* access modifiers changed from: 0000 */
    public Resource c() {
        f();
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse d() {
        f();
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(this.d.getStatusLine());
        basicHttpResponse.setHeaders(this.d.getAllHeaders());
        CombinedEntity combinedEntity = new CombinedEntity(this.g, this.e);
        HttpEntity entity = this.d.getEntity();
        if (entity != null) {
            combinedEntity.setContentType(entity.getContentType());
            combinedEntity.setContentEncoding(entity.getContentEncoding());
            combinedEntity.setChunked(entity.isChunked());
        }
        basicHttpResponse.setEntity(combinedEntity);
        return (CloseableHttpResponse) Proxy.newProxyInstance(ResponseProxyHandler.class.getClassLoader(), new Class[]{CloseableHttpResponse.class}, new ResponseProxyHandler(basicHttpResponse) {
            public void a() {
                SizeLimitedResponseReader.this.d.close();
            }
        });
    }
}
