package com.squareup.okhttp;

import android.support.v4.app.NotificationCompat;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Call {
    volatile boolean a;
    Request b;
    HttpEngine c;
    /* access modifiers changed from: private */
    public final OkHttpClient d;
    private boolean e;

    class ApplicationInterceptorChain implements Chain {
        private final int b;
        private final Request c;
        private final boolean d;

        public Connection connection() {
            return null;
        }

        ApplicationInterceptorChain(int i, Request request, boolean z) {
            this.b = i;
            this.c = request;
            this.d = z;
        }

        public Request request() {
            return this.c;
        }

        public Response proceed(Request request) {
            if (this.b >= Call.this.d.interceptors().size()) {
                return Call.this.a(request, this.d);
            }
            return ((Interceptor) Call.this.d.interceptors().get(this.b)).intercept(new ApplicationInterceptorChain(this.b + 1, request, this.d));
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final Callback b;
        private final boolean c;

        private AsyncCall(Callback callback, boolean z) {
            super("OkHttp %s", Call.this.b.urlString());
            this.b = callback;
            this.c = z;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            return Call.this.b.url().getHost();
        }

        /* access modifiers changed from: 0000 */
        public Object b() {
            return Call.this.b.tag();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Call.this.cancel();
        }

        /* access modifiers changed from: 0000 */
        public Call d() {
            return Call.this;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0030 A[SYNTHETIC, Splitter:B:13:0x0030] */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x004f A[Catch:{ all -> 0x0029 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r5 = this;
                r0 = 1
                r1 = 0
                com.squareup.okhttp.Call r2 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x002b }
                boolean r3 = r5.c     // Catch:{ IOException -> 0x002b }
                com.squareup.okhttp.Response r2 = r2.a(r3)     // Catch:{ IOException -> 0x002b }
                com.squareup.okhttp.Call r3 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x002b }
                boolean r3 = r3.a     // Catch:{ IOException -> 0x002b }
                if (r3 == 0) goto L_0x0023
                com.squareup.okhttp.Callback r1 = r5.b     // Catch:{ IOException -> 0x0021 }
                com.squareup.okhttp.Call r2 = com.squareup.okhttp.Call.this     // Catch:{ IOException -> 0x0021 }
                com.squareup.okhttp.Request r2 = r2.b     // Catch:{ IOException -> 0x0021 }
                java.io.IOException r3 = new java.io.IOException     // Catch:{ IOException -> 0x0021 }
                java.lang.String r4 = "Canceled"
                r3.<init>(r4)     // Catch:{ IOException -> 0x0021 }
                r1.onFailure(r2, r3)     // Catch:{ IOException -> 0x0021 }
                goto L_0x005c
            L_0x0021:
                r1 = move-exception
                goto L_0x002e
            L_0x0023:
                com.squareup.okhttp.Callback r1 = r5.b     // Catch:{ IOException -> 0x0021 }
                r1.onResponse(r2)     // Catch:{ IOException -> 0x0021 }
                goto L_0x005c
            L_0x0029:
                r0 = move-exception
                goto L_0x006a
            L_0x002b:
                r0 = move-exception
                r1 = r0
                r0 = 0
            L_0x002e:
                if (r0 == 0) goto L_0x004f
                java.util.logging.Logger r0 = com.squareup.okhttp.internal.Internal.logger     // Catch:{ all -> 0x0029 }
                java.util.logging.Level r2 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0029 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0029 }
                r3.<init>()     // Catch:{ all -> 0x0029 }
                java.lang.String r4 = "Callback failure for "
                r3.append(r4)     // Catch:{ all -> 0x0029 }
                com.squareup.okhttp.Call r4 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0029 }
                java.lang.String r4 = r4.b()     // Catch:{ all -> 0x0029 }
                r3.append(r4)     // Catch:{ all -> 0x0029 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0029 }
                r0.log(r2, r3, r1)     // Catch:{ all -> 0x0029 }
                goto L_0x005c
            L_0x004f:
                com.squareup.okhttp.Callback r0 = r5.b     // Catch:{ all -> 0x0029 }
                com.squareup.okhttp.Call r2 = com.squareup.okhttp.Call.this     // Catch:{ all -> 0x0029 }
                com.squareup.okhttp.internal.http.HttpEngine r2 = r2.c     // Catch:{ all -> 0x0029 }
                com.squareup.okhttp.Request r2 = r2.getRequest()     // Catch:{ all -> 0x0029 }
                r0.onFailure(r2, r1)     // Catch:{ all -> 0x0029 }
            L_0x005c:
                com.squareup.okhttp.Call r0 = com.squareup.okhttp.Call.this
                com.squareup.okhttp.OkHttpClient r0 = r0.d
                com.squareup.okhttp.Dispatcher r0 = r0.getDispatcher()
                r0.b(r5)
                return
            L_0x006a:
                com.squareup.okhttp.Call r1 = com.squareup.okhttp.Call.this
                com.squareup.okhttp.OkHttpClient r1 = r1.d
                com.squareup.okhttp.Dispatcher r1 = r1.getDispatcher()
                r1.b(r5)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Call.AsyncCall.execute():void");
        }
    }

    Call(OkHttpClient okHttpClient, Request request) {
        this.d = okHttpClient.c();
        this.b = request;
    }

    public Response execute() {
        synchronized (this) {
            if (this.e) {
                throw new IllegalStateException("Already Executed");
            }
            this.e = true;
        }
        try {
            this.d.getDispatcher().a(this);
            Response a2 = a(false);
            if (a2 != null) {
                return a2;
            }
            throw new IOException("Canceled");
        } finally {
            this.d.getDispatcher().b(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public Object a() {
        return this.b.tag();
    }

    public void enqueue(Callback callback) {
        a(callback, false);
    }

    /* access modifiers changed from: 0000 */
    public void a(Callback callback, boolean z) {
        synchronized (this) {
            if (this.e) {
                throw new IllegalStateException("Already Executed");
            }
            this.e = true;
        }
        this.d.getDispatcher().a(new AsyncCall(callback, z));
    }

    public void cancel() {
        this.a = true;
        if (this.c != null) {
            this.c.disconnect();
        }
    }

    public boolean isCanceled() {
        return this.a;
    }

    /* access modifiers changed from: private */
    public String b() {
        String str = this.a ? "canceled call" : NotificationCompat.CATEGORY_CALL;
        try {
            String url = new URL(this.b.url(), "/...").toString();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" to ");
            sb.append(url);
            return sb.toString();
        } catch (MalformedURLException unused) {
            return str;
        }
    }

    /* access modifiers changed from: private */
    public Response a(boolean z) {
        return new ApplicationInterceptorChain(0, this.b, z).proceed(this.b);
    }

    /* access modifiers changed from: 0000 */
    public Response a(Request request, boolean z) {
        RequestBody body = request.body();
        if (body != null) {
            Builder newBuilder = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                newBuilder.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                newBuilder.header("Content-Length", Long.toString(contentLength));
                newBuilder.removeHeader("Transfer-Encoding");
            } else {
                newBuilder.header("Transfer-Encoding", HTTP.CHUNK_CODING);
                newBuilder.removeHeader("Content-Length");
            }
            request = newBuilder.build();
        }
        HttpEngine httpEngine = new HttpEngine(this.d, request, false, false, z, null, null, null, null);
        this.c = httpEngine;
        int i = 0;
        while (!this.a) {
            try {
                this.c.sendRequest();
                this.c.readResponse();
                Response response = this.c.getResponse();
                Request followUpRequest = this.c.followUpRequest();
                if (followUpRequest == null) {
                    if (!z) {
                        this.c.releaseConnection();
                    }
                    return response;
                }
                i++;
                if (i > 20) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Too many follow-up requests: ");
                    sb.append(i);
                    throw new ProtocolException(sb.toString());
                }
                if (!this.c.sameConnection(followUpRequest.url())) {
                    this.c.releaseConnection();
                }
                HttpEngine httpEngine2 = new HttpEngine(this.d, followUpRequest, false, false, z, this.c.close(), null, null, response);
                this.c = httpEngine2;
            } catch (RequestException e2) {
                throw e2.getCause();
            } catch (RouteException e3) {
                HttpEngine recover = this.c.recover(e3);
                if (recover != null) {
                    this.c = recover;
                } else {
                    throw e3.getLastConnectException();
                }
            } catch (IOException e4) {
                HttpEngine recover2 = this.c.recover(e4, null);
                if (recover2 != null) {
                    this.c = recover2;
                } else {
                    throw e4;
                }
            }
        }
        this.c.releaseConnection();
        throw new IOException("Canceled");
    }
}
