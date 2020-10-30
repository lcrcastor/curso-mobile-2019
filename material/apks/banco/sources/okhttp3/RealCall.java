package okhttp3;

import android.support.v4.app.NotificationCompat;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

final class RealCall implements Call {
    final OkHttpClient a;
    final RetryAndFollowUpInterceptor b;
    final Request c;
    final boolean d;
    /* access modifiers changed from: private */
    public EventListener e;
    private boolean f;

    final class AsyncCall extends NamedRunnable {
        private final Callback b;

        AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.d());
            this.b = callback;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            return RealCall.this.c.url().host();
        }

        /* access modifiers changed from: 0000 */
        public RealCall b() {
            return RealCall.this;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x003d A[SYNTHETIC, Splitter:B:14:0x003d] */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x005d A[Catch:{ all -> 0x0036 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r5 = this;
                r0 = 1
                r1 = 0
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x0038 }
                okhttp3.Response r2 = r2.e()     // Catch:{ IOException -> 0x0038 }
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x0038 }
                okhttp3.internal.http.RetryAndFollowUpInterceptor r3 = r3.b     // Catch:{ IOException -> 0x0038 }
                boolean r3 = r3.isCanceled()     // Catch:{ IOException -> 0x0038 }
                if (r3 == 0) goto L_0x0023
                okhttp3.Callback r1 = r5.b     // Catch:{ IOException -> 0x0021 }
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x0021 }
                java.io.IOException r3 = new java.io.IOException     // Catch:{ IOException -> 0x0021 }
                java.lang.String r4 = "Canceled"
                r3.<init>(r4)     // Catch:{ IOException -> 0x0021 }
                r1.onFailure(r2, r3)     // Catch:{ IOException -> 0x0021 }
                goto L_0x002a
            L_0x0021:
                r1 = move-exception
                goto L_0x003b
            L_0x0023:
                okhttp3.Callback r1 = r5.b     // Catch:{ IOException -> 0x0021 }
                okhttp3.RealCall r3 = okhttp3.RealCall.this     // Catch:{ IOException -> 0x0021 }
                r1.onResponse(r3, r2)     // Catch:{ IOException -> 0x0021 }
            L_0x002a:
                okhttp3.RealCall r0 = okhttp3.RealCall.this
                okhttp3.OkHttpClient r0 = r0.a
                okhttp3.Dispatcher r0 = r0.dispatcher()
                r0.b(r5)
                goto L_0x0070
            L_0x0036:
                r0 = move-exception
                goto L_0x0071
            L_0x0038:
                r0 = move-exception
                r1 = r0
                r0 = 0
            L_0x003b:
                if (r0 == 0) goto L_0x005d
                okhttp3.internal.platform.Platform r0 = okhttp3.internal.platform.Platform.get()     // Catch:{ all -> 0x0036 }
                r2 = 4
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0036 }
                r3.<init>()     // Catch:{ all -> 0x0036 }
                java.lang.String r4 = "Callback failure for "
                r3.append(r4)     // Catch:{ all -> 0x0036 }
                okhttp3.RealCall r4 = okhttp3.RealCall.this     // Catch:{ all -> 0x0036 }
                java.lang.String r4 = r4.c()     // Catch:{ all -> 0x0036 }
                r3.append(r4)     // Catch:{ all -> 0x0036 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0036 }
                r0.log(r2, r3, r1)     // Catch:{ all -> 0x0036 }
                goto L_0x002a
            L_0x005d:
                okhttp3.RealCall r0 = okhttp3.RealCall.this     // Catch:{ all -> 0x0036 }
                okhttp3.EventListener r0 = r0.e     // Catch:{ all -> 0x0036 }
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ all -> 0x0036 }
                r0.callFailed(r2, r1)     // Catch:{ all -> 0x0036 }
                okhttp3.Callback r0 = r5.b     // Catch:{ all -> 0x0036 }
                okhttp3.RealCall r2 = okhttp3.RealCall.this     // Catch:{ all -> 0x0036 }
                r0.onFailure(r2, r1)     // Catch:{ all -> 0x0036 }
                goto L_0x002a
            L_0x0070:
                return
            L_0x0071:
                okhttp3.RealCall r1 = okhttp3.RealCall.this
                okhttp3.OkHttpClient r1 = r1.a
                okhttp3.Dispatcher r1 = r1.dispatcher()
                r1.b(r5)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.RealCall.AsyncCall.execute():void");
        }
    }

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        this.a = okHttpClient;
        this.c = request;
        this.d = z;
        this.b = new RetryAndFollowUpInterceptor(okHttpClient, z);
    }

    static RealCall a(OkHttpClient okHttpClient, Request request, boolean z) {
        RealCall realCall = new RealCall(okHttpClient, request, z);
        realCall.e = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    public Request request() {
        return this.c;
    }

    public Response execute() {
        synchronized (this) {
            if (this.f) {
                throw new IllegalStateException("Already Executed");
            }
            this.f = true;
        }
        f();
        this.e.callStart(this);
        try {
            this.a.dispatcher().a(this);
            Response e2 = e();
            if (e2 == null) {
                throw new IOException("Canceled");
            }
            this.a.dispatcher().b(this);
            return e2;
        } catch (IOException e3) {
            this.e.callFailed(this, e3);
            throw e3;
        } catch (Throwable th) {
            this.a.dispatcher().b(this);
            throw th;
        }
    }

    private void f() {
        this.b.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.f) {
                throw new IllegalStateException("Already Executed");
            }
            this.f = true;
        }
        f();
        this.e.callStart(this);
        this.a.dispatcher().a(new AsyncCall(callback));
    }

    public void cancel() {
        this.b.cancel();
    }

    public synchronized boolean isExecuted() {
        return this.f;
    }

    public boolean isCanceled() {
        return this.b.isCanceled();
    }

    /* renamed from: a */
    public RealCall clone() {
        return a(this.a, this.c, this.d);
    }

    /* access modifiers changed from: 0000 */
    public StreamAllocation b() {
        return this.b.streamAllocation();
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.d ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(d());
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        return this.c.url().redact();
    }

    /* access modifiers changed from: 0000 */
    public Response e() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.a.interceptors());
        arrayList.add(this.b);
        arrayList.add(new BridgeInterceptor(this.a.cookieJar()));
        arrayList.add(new CacheInterceptor(this.a.a()));
        arrayList.add(new ConnectInterceptor(this.a));
        if (!this.d) {
            arrayList.addAll(this.a.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.d));
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(arrayList, null, null, null, 0, this.c, this, this.e, this.a.connectTimeoutMillis(), this.a.readTimeoutMillis(), this.a.writeTimeoutMillis());
        return realInterceptorChain.proceed(this.c);
    }
}
