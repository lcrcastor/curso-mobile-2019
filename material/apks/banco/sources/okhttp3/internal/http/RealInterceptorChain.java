package okhttp3.internal.http;

import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;

public final class RealInterceptorChain implements Chain {
    private final List<Interceptor> a;
    private final StreamAllocation b;
    private final HttpCodec c;
    private final RealConnection d;
    private final int e;
    private final Request f;
    private final Call g;
    private final EventListener h;
    private final int i;
    private final int j;
    private final int k;
    private int l;

    public RealInterceptorChain(List<Interceptor> list, StreamAllocation streamAllocation, HttpCodec httpCodec, RealConnection realConnection, int i2, Request request, Call call, EventListener eventListener, int i3, int i4, int i5) {
        this.a = list;
        this.d = realConnection;
        this.b = streamAllocation;
        this.c = httpCodec;
        this.e = i2;
        this.f = request;
        this.g = call;
        this.h = eventListener;
        this.i = i3;
        this.j = i4;
        this.k = i5;
    }

    public Connection connection() {
        return this.d;
    }

    public int connectTimeoutMillis() {
        return this.i;
    }

    public Chain withConnectTimeout(int i2, TimeUnit timeUnit) {
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, Util.checkDuration("timeout", (long) i2, timeUnit), this.j, this.k);
        return realInterceptorChain;
    }

    public int readTimeoutMillis() {
        return this.j;
    }

    public Chain withReadTimeout(int i2, TimeUnit timeUnit) {
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, Util.checkDuration("timeout", (long) i2, timeUnit), this.k);
        return realInterceptorChain;
    }

    public int writeTimeoutMillis() {
        return this.k;
    }

    public Chain withWriteTimeout(int i2, TimeUnit timeUnit) {
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, Util.checkDuration("timeout", (long) i2, timeUnit));
        return realInterceptorChain;
    }

    public StreamAllocation streamAllocation() {
        return this.b;
    }

    public HttpCodec httpStream() {
        return this.c;
    }

    public Call call() {
        return this.g;
    }

    public EventListener eventListener() {
        return this.h;
    }

    public Request request() {
        return this.f;
    }

    public Response proceed(Request request) {
        return proceed(request, this.b, this.c, this.d);
    }

    public Response proceed(Request request, StreamAllocation streamAllocation, HttpCodec httpCodec, RealConnection realConnection) {
        if (this.e >= this.a.size()) {
            throw new AssertionError();
        }
        this.l++;
        if (this.c != null && !this.d.supportsUrl(request.url())) {
            StringBuilder sb = new StringBuilder();
            sb.append("network interceptor ");
            sb.append(this.a.get(this.e - 1));
            sb.append(" must retain the same host and port");
            throw new IllegalStateException(sb.toString());
        } else if (this.c == null || this.l <= 1) {
            RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.a, streamAllocation, httpCodec, realConnection, this.e + 1, request, this.g, this.h, this.i, this.j, this.k);
            Interceptor interceptor = (Interceptor) this.a.get(this.e);
            Response intercept = interceptor.intercept(realInterceptorChain);
            if (httpCodec != null && this.e + 1 < this.a.size() && realInterceptorChain.l != 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("network interceptor ");
                sb2.append(interceptor);
                sb2.append(" must call proceed() exactly once");
                throw new IllegalStateException(sb2.toString());
            } else if (intercept == null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("interceptor ");
                sb3.append(interceptor);
                sb3.append(" returned null");
                throw new NullPointerException(sb3.toString());
            } else if (intercept.body() != null) {
                return intercept;
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("interceptor ");
                sb4.append(interceptor);
                sb4.append(" returned a response with no body");
                throw new IllegalStateException(sb4.toString());
            }
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("network interceptor ");
            sb5.append(this.a.get(this.e - 1));
            sb5.append(" must call proceed() exactly once");
            throw new IllegalStateException(sb5.toString());
        }
    }
}
