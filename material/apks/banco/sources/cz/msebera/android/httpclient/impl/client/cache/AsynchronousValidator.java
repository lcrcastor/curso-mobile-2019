package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.Closeable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

class AsynchronousValidator implements Closeable {
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());
    private final SchedulingStrategy b;
    private final Set<String> c;
    private final CacheKeyGenerator d;
    private final FailureCache e;

    AsynchronousValidator(SchedulingStrategy schedulingStrategy) {
        this.b = schedulingStrategy;
        this.c = new HashSet();
        this.d = new CacheKeyGenerator();
        this.e = new DefaultFailureCache();
    }

    public void close() {
        this.b.close();
    }

    public synchronized void a(CachingExec cachingExec, HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry) {
        String a2;
        synchronized (this) {
            try {
                HttpRequestWrapper httpRequestWrapper2 = httpRequestWrapper;
                HttpCacheEntry httpCacheEntry2 = httpCacheEntry;
                a2 = this.d.a(httpClientContext.getTargetHost(), httpRequestWrapper2, httpCacheEntry2);
                if (!this.c.contains(a2)) {
                    AsynchronousValidationRequest asynchronousValidationRequest = new AsynchronousValidationRequest(this, cachingExec, httpRoute, httpRequestWrapper2, httpClientContext, httpExecutionAware, httpCacheEntry2, a2, this.e.getErrorCount(a2));
                    this.b.schedule(asynchronousValidationRequest);
                    this.c.add(a2);
                }
            } catch (RejectedExecutionException e2) {
                RejectedExecutionException rejectedExecutionException = e2;
                HttpClientAndroidLog httpClientAndroidLog = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("Revalidation for [");
                sb.append(a2);
                sb.append("] not scheduled: ");
                sb.append(rejectedExecutionException);
                httpClientAndroidLog.debug(sb.toString());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(String str) {
        this.c.remove(str);
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        this.e.resetErrorCount(str);
    }

    /* access modifiers changed from: 0000 */
    public void c(String str) {
        this.e.increaseErrorCount(str);
    }
}
