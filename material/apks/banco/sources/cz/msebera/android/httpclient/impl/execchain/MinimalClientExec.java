package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.net.URI;
import java.net.URISyntaxException;

@Immutable
public class MinimalClientExec implements ClientExecChain {
    private final HttpRequestExecutor a;
    private final HttpClientConnectionManager b;
    private final ConnectionReuseStrategy c;
    private final ConnectionKeepAliveStrategy d;
    private final HttpProcessor e;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public MinimalClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        this.e = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", getClass())));
        this.a = httpRequestExecutor;
        this.b = httpClientConnectionManager;
        this.c = connectionReuseStrategy;
        this.d = connectionKeepAliveStrategy;
    }

    static void a(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute) {
        URI uri;
        try {
            URI uri2 = httpRequestWrapper.getURI();
            if (uri2 != null) {
                if (uri2.isAbsolute()) {
                    uri = URIUtils.rewriteURI(uri2, null, true);
                } else {
                    uri = URIUtils.rewriteURI(uri2);
                }
                httpRequestWrapper.setURI(uri);
            }
        } catch (URISyntaxException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid URI: ");
            sb.append(httpRequestWrapper.getRequestLine().getUri());
            throw new ProtocolException(sb.toString(), e2);
        }
    }

    /* JADX WARNING: type inference failed for: r8v3, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r7v3, types: [java.lang.Throwable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b9 A[Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e9 A[Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f8 A[Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0101 A[Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cz.msebera.android.httpclient.client.methods.CloseableHttpResponse execute(cz.msebera.android.httpclient.conn.routing.HttpRoute r7, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper r8, cz.msebera.android.httpclient.client.protocol.HttpClientContext r9, cz.msebera.android.httpclient.client.methods.HttpExecutionAware r10) {
        /*
            r6 = this;
            java.lang.String r0 = "HTTP route"
            cz.msebera.android.httpclient.util.Args.notNull(r7, r0)
            java.lang.String r0 = "HTTP request"
            cz.msebera.android.httpclient.util.Args.notNull(r8, r0)
            java.lang.String r0 = "HTTP context"
            cz.msebera.android.httpclient.util.Args.notNull(r9, r0)
            a(r8, r7)
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r0 = r6.b
            r1 = 0
            cz.msebera.android.httpclient.conn.ConnectionRequest r0 = r0.requestConnection(r7, r1)
            if (r10 == 0) goto L_0x002f
            boolean r2 = r10.isAborted()
            if (r2 == 0) goto L_0x002c
            r0.cancel()
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r7 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r8 = "Request aborted"
            r7.<init>(r8)
            throw r7
        L_0x002c:
            r10.setCancellable(r0)
        L_0x002f:
            cz.msebera.android.httpclient.client.config.RequestConfig r2 = r9.getRequestConfig()
            int r3 = r2.getConnectionRequestTimeout()     // Catch:{ InterruptedException -> 0x013f, ExecutionException -> 0x012e }
            if (r3 <= 0) goto L_0x003b
            long r3 = (long) r3     // Catch:{ InterruptedException -> 0x013f, ExecutionException -> 0x012e }
            goto L_0x003d
        L_0x003b:
            r3 = 0
        L_0x003d:
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x013f, ExecutionException -> 0x012e }
            cz.msebera.android.httpclient.HttpClientConnection r0 = r0.get(r3, r5)     // Catch:{ InterruptedException -> 0x013f, ExecutionException -> 0x012e }
            cz.msebera.android.httpclient.impl.execchain.ConnectionHolder r3 = new cz.msebera.android.httpclient.impl.execchain.ConnectionHolder
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r6.log
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r5 = r6.b
            r3.<init>(r4, r5, r0)
            if (r10 == 0) goto L_0x006f
            boolean r4 = r10.isAborted()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r4 == 0) goto L_0x005f
            r3.close()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r7 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r8 = "Request aborted"
            r7.<init>(r8)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            throw r7     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
        L_0x005f:
            r10.setCancellable(r3)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            goto L_0x006f
        L_0x0063:
            r7 = move-exception
            goto L_0x0117
        L_0x0066:
            r7 = move-exception
            goto L_0x011b
        L_0x0069:
            r7 = move-exception
            goto L_0x011f
        L_0x006c:
            r7 = move-exception
            goto L_0x0123
        L_0x006f:
            boolean r10 = r0.isOpen()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r10 != 0) goto L_0x0087
            int r10 = r2.getConnectTimeout()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r4 = r6.b     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r10 <= 0) goto L_0x007e
            goto L_0x007f
        L_0x007e:
            r10 = 0
        L_0x007f:
            r4.connect(r0, r7, r10, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r10 = r6.b     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r10.routeComplete(r0, r7, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
        L_0x0087:
            int r10 = r2.getSocketTimeout()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r10 < 0) goto L_0x0090
            r0.setSocketTimeout(r10)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
        L_0x0090:
            cz.msebera.android.httpclient.HttpRequest r10 = r8.getOriginal()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            boolean r2 = r10 instanceof cz.msebera.android.httpclient.client.methods.HttpUriRequest     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r2 == 0) goto L_0x00b6
            cz.msebera.android.httpclient.client.methods.HttpUriRequest r10 = (cz.msebera.android.httpclient.client.methods.HttpUriRequest) r10     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.net.URI r10 = r10.getURI()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            boolean r2 = r10.isAbsolute()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r2 == 0) goto L_0x00b6
            cz.msebera.android.httpclient.HttpHost r2 = new cz.msebera.android.httpclient.HttpHost     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r4 = r10.getHost()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            int r5 = r10.getPort()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r10 = r10.getScheme()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r2.<init>(r4, r5, r10)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            goto L_0x00b7
        L_0x00b6:
            r2 = r1
        L_0x00b7:
            if (r2 != 0) goto L_0x00bd
            cz.msebera.android.httpclient.HttpHost r2 = r7.getTargetHost()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
        L_0x00bd:
            java.lang.String r10 = "http.target_host"
            r9.setAttribute(r10, r2)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r10 = "http.request"
            r9.setAttribute(r10, r8)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r10 = "http.connection"
            r9.setAttribute(r10, r0)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.lang.String r10 = "http.route"
            r9.setAttribute(r10, r7)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.protocol.HttpProcessor r7 = r6.e     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r7.process(r8, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.protocol.HttpRequestExecutor r7 = r6.a     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.HttpResponse r7 = r7.execute(r8, r0, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.protocol.HttpProcessor r8 = r6.e     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r8.process(r7, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.ConnectionReuseStrategy r8 = r6.c     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            boolean r8 = r8.keepAlive(r7, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r8 == 0) goto L_0x00f8
            cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy r8 = r6.d     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            long r8 = r8.getKeepAliveDuration(r7, r9)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r3.a(r8, r10)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r3.b()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            goto L_0x00fb
        L_0x00f8:
            r3.c()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
        L_0x00fb:
            cz.msebera.android.httpclient.HttpEntity r8 = r7.getEntity()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r8 == 0) goto L_0x010e
            boolean r8 = r8.isStreaming()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            if (r8 != 0) goto L_0x0108
            goto L_0x010e
        L_0x0108:
            cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy r8 = new cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r8.<init>(r7, r3)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            return r8
        L_0x010e:
            r3.releaseConnection()     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy r8 = new cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            r8.<init>(r7, r1)     // Catch:{ ConnectionShutdownException -> 0x006c, HttpException -> 0x0069, IOException -> 0x0066, RuntimeException -> 0x0063 }
            return r8
        L_0x0117:
            r3.abortConnection()
            throw r7
        L_0x011b:
            r3.abortConnection()
            throw r7
        L_0x011f:
            r3.abortConnection()
            throw r7
        L_0x0123:
            java.io.InterruptedIOException r8 = new java.io.InterruptedIOException
            java.lang.String r9 = "Connection has been shut down"
            r8.<init>(r9)
            r8.initCause(r7)
            throw r8
        L_0x012e:
            r7 = move-exception
            java.lang.Throwable r8 = r7.getCause()
            if (r8 != 0) goto L_0x0136
            goto L_0x0137
        L_0x0136:
            r7 = r8
        L_0x0137:
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r8 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r9 = "Request execution failed"
            r8.<init>(r9, r7)
            throw r8
        L_0x013f:
            r7 = move-exception
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r8 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r9 = "Request aborted"
            r8.<init>(r9, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MinimalClientExec.execute(cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper, cz.msebera.android.httpclient.client.protocol.HttpClientContext, cz.msebera.android.httpclient.client.methods.HttpExecutionAware):cz.msebera.android.httpclient.client.methods.CloseableHttpResponse");
    }
}
