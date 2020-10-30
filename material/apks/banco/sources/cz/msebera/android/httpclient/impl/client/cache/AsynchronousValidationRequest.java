package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.IOException;

public class AsynchronousValidationRequest implements Runnable {
    private final AsynchronousValidator a;
    private final CachingExec b;
    private final HttpRoute c;
    private final HttpRequestWrapper d;
    private final HttpClientContext e;
    private final HttpExecutionAware f;
    private final HttpCacheEntry g;
    private final String h;
    private final int i;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    private boolean a(int i2) {
        return i2 < 500;
    }

    AsynchronousValidationRequest(AsynchronousValidator asynchronousValidator, CachingExec cachingExec, HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry, String str, int i2) {
        this.a = asynchronousValidator;
        this.b = cachingExec;
        this.c = httpRoute;
        this.d = httpRequestWrapper;
        this.e = httpClientContext;
        this.f = httpExecutionAware;
        this.g = httpCacheEntry;
        this.h = str;
        this.i = i2;
    }

    public void run() {
        try {
            if (a()) {
                this.a.b(this.h);
            } else {
                this.a.c(this.h);
            }
        } finally {
            this.a.a(this.h);
        }
    }

    private boolean a() {
        CloseableHttpResponse a2;
        try {
            a2 = this.b.a(this.c, this.d, this.e, this.f, this.g);
            boolean z = a(a2.getStatusLine().getStatusCode()) && a((HttpResponse) a2);
            a2.close();
            return z;
        } catch (IOException e2) {
            this.log.debug("Asynchronous revalidation failed due to I/O error", e2);
            return false;
        } catch (HttpException e3) {
            this.log.error("HTTP protocol exception during asynchronous revalidation", e3);
            return false;
        } catch (RuntimeException e4) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("RuntimeException thrown during asynchronous revalidation: ");
            sb.append(e4);
            httpClientAndroidLog.error(sb.toString());
            return false;
        } catch (Throwable th) {
            a2.close();
            throw th;
        }
    }

    private boolean a(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getHeaders("Warning");
        if (headers != null) {
            for (Header value : headers) {
                String value2 = value.getValue();
                if (value2.startsWith("110") || value2.startsWith("111")) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getIdentifier() {
        return this.h;
    }

    public int getConsecutiveFailedAttempts() {
        return this.i;
    }
}
