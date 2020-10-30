package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.concurrent.FutureCallback;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

class HttpRequestTaskCallable<V> implements Callable<V> {
    private final HttpUriRequest a;
    private final HttpClient b;
    private final AtomicBoolean c = new AtomicBoolean(false);
    private final long d = System.currentTimeMillis();
    private long e = -1;
    private long f = -1;
    private final HttpContext g;
    private final ResponseHandler<V> h;
    private final FutureCallback<V> i;
    private final FutureRequestExecutionMetrics j;

    HttpRequestTaskCallable(HttpClient httpClient, HttpUriRequest httpUriRequest, HttpContext httpContext, ResponseHandler<V> responseHandler, FutureCallback<V> futureCallback, FutureRequestExecutionMetrics futureRequestExecutionMetrics) {
        this.b = httpClient;
        this.h = responseHandler;
        this.a = httpUriRequest;
        this.g = httpContext;
        this.i = futureCallback;
        this.j = futureRequestExecutionMetrics;
    }

    public long a() {
        return this.d;
    }

    public long b() {
        return this.e;
    }

    public long c() {
        return this.f;
    }

    public V call() {
        if (!this.c.get()) {
            try {
                this.j.a().incrementAndGet();
                this.e = System.currentTimeMillis();
                this.j.b().decrementAndGet();
                V execute = this.b.execute(this.a, this.h, this.g);
                this.f = System.currentTimeMillis();
                this.j.c().a(this.e);
                if (this.i != null) {
                    this.i.completed(execute);
                }
                this.j.e().a(this.e);
                this.j.f().a(this.e);
                this.j.a().decrementAndGet();
                return execute;
            } catch (Exception e2) {
                this.j.d().a(this.e);
                this.f = System.currentTimeMillis();
                if (this.i != null) {
                    this.i.failed(e2);
                }
                throw e2;
            } catch (Throwable th) {
                this.j.e().a(this.e);
                this.j.f().a(this.e);
                this.j.a().decrementAndGet();
                throw th;
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("call has been cancelled for request ");
            sb.append(this.a.getURI());
            throw new IllegalStateException(sb.toString());
        }
    }

    public void d() {
        this.c.set(true);
        if (this.i != null) {
            this.i.cancelled();
        }
    }
}
