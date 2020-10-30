package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.BasicRouteDirector;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRouteDirector;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.auth.HttpAuthenticator;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class MainClientExec implements ClientExecChain {
    private final HttpRequestExecutor a;
    private final HttpClientConnectionManager b;
    private final ConnectionReuseStrategy c;
    private final ConnectionKeepAliveStrategy d;
    private final HttpProcessor e;
    private final AuthenticationStrategy f;
    private final AuthenticationStrategy g;
    private final HttpAuthenticator h;
    private final UserTokenHandler i;
    private final HttpRouteDirector j;
    public HttpClientAndroidLog log;

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpProcessor, "Proxy HTTP processor");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.h = new HttpAuthenticator();
        this.j = new BasicRouteDirector();
        this.a = httpRequestExecutor;
        this.b = httpClientConnectionManager;
        this.c = connectionReuseStrategy;
        this.d = connectionKeepAliveStrategy;
        this.e = httpProcessor;
        this.f = authenticationStrategy;
        this.g = authenticationStrategy2;
        this.i = userTokenHandler;
    }

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        ImmutableHttpProcessor immutableHttpProcessor = new ImmutableHttpProcessor(new RequestTargetHost());
        this(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, immutableHttpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r1v12, types: [java.lang.Throwable] */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02ec, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02ee, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02f0, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0337, code lost:
        r2 = new java.io.InterruptedIOException("Connection has been shut down");
        r2.initCause(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x0341, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c2, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c1 A[Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }, ExcHandler: ConnectionShutdownException (r0v18 'e' cz.msebera.android.httpclient.impl.conn.ConnectionShutdownException A[CUSTOM_DECLARE, Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }]), Splitter:B:121:0x0240] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cz.msebera.android.httpclient.client.methods.CloseableHttpResponse execute(cz.msebera.android.httpclient.conn.routing.HttpRoute r24, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper r25, cz.msebera.android.httpclient.client.protocol.HttpClientContext r26, cz.msebera.android.httpclient.client.methods.HttpExecutionAware r27) {
        /*
            r23 = this;
            r7 = r23
            r8 = r24
            r9 = r25
            r10 = r26
            r11 = r27
            java.lang.String r1 = "HTTP route"
            cz.msebera.android.httpclient.util.Args.notNull(r8, r1)
            java.lang.String r1 = "HTTP request"
            cz.msebera.android.httpclient.util.Args.notNull(r9, r1)
            java.lang.String r1 = "HTTP context"
            cz.msebera.android.httpclient.util.Args.notNull(r10, r1)
            cz.msebera.android.httpclient.auth.AuthState r1 = r26.getTargetAuthState()
            if (r1 != 0) goto L_0x0029
            cz.msebera.android.httpclient.auth.AuthState r1 = new cz.msebera.android.httpclient.auth.AuthState
            r1.<init>()
            java.lang.String r2 = "http.auth.target-scope"
            r10.setAttribute(r2, r1)
        L_0x0029:
            r12 = r1
            cz.msebera.android.httpclient.auth.AuthState r1 = r26.getProxyAuthState()
            if (r1 != 0) goto L_0x003a
            cz.msebera.android.httpclient.auth.AuthState r1 = new cz.msebera.android.httpclient.auth.AuthState
            r1.<init>()
            java.lang.String r2 = "http.auth.proxy-scope"
            r10.setAttribute(r2, r1)
        L_0x003a:
            r13 = r1
            boolean r1 = r9 instanceof cz.msebera.android.httpclient.HttpEntityEnclosingRequest
            if (r1 == 0) goto L_0x0045
            r1 = r9
            cz.msebera.android.httpclient.HttpEntityEnclosingRequest r1 = (cz.msebera.android.httpclient.HttpEntityEnclosingRequest) r1
            cz.msebera.android.httpclient.impl.execchain.RequestEntityProxy.a(r1)
        L_0x0045:
            java.lang.Object r14 = r26.getUserToken()
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r1 = r7.b
            cz.msebera.android.httpclient.conn.ConnectionRequest r1 = r1.requestConnection(r8, r14)
            if (r11 == 0) goto L_0x0065
            boolean r2 = r27.isAborted()
            if (r2 == 0) goto L_0x0062
            r1.cancel()
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r1 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r2 = "Request aborted"
            r1.<init>(r2)
            throw r1
        L_0x0062:
            r11.setCancellable(r1)
        L_0x0065:
            cz.msebera.android.httpclient.client.config.RequestConfig r15 = r26.getRequestConfig()
            int r2 = r15.getConnectionRequestTimeout()     // Catch:{ InterruptedException -> 0x0354, ExecutionException -> 0x0342 }
            r16 = 0
            if (r2 <= 0) goto L_0x0073
            long r2 = (long) r2     // Catch:{ InterruptedException -> 0x0354, ExecutionException -> 0x0342 }
            goto L_0x0075
        L_0x0073:
            r2 = r16
        L_0x0075:
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0354, ExecutionException -> 0x0342 }
            cz.msebera.android.httpclient.HttpClientConnection r6 = r1.get(r2, r4)     // Catch:{ InterruptedException -> 0x0354, ExecutionException -> 0x0342 }
            java.lang.String r1 = "http.connection"
            r10.setAttribute(r1, r6)
            boolean r1 = r15.isStaleConnectionCheckEnabled()
            if (r1 == 0) goto L_0x00a3
            boolean r1 = r6.isOpen()
            if (r1 == 0) goto L_0x00a3
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log
            java.lang.String r2 = "Stale connection check"
            r1.debug(r2)
            boolean r1 = r6.isStale()
            if (r1 == 0) goto L_0x00a3
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log
            java.lang.String r2 = "Stale connection detected"
            r1.debug(r2)
            r6.close()
        L_0x00a3:
            cz.msebera.android.httpclient.impl.execchain.ConnectionHolder r5 = new cz.msebera.android.httpclient.impl.execchain.ConnectionHolder
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r2 = r7.b
            r5.<init>(r1, r2, r6)
            if (r11 == 0) goto L_0x00c5
            r11.setCancellable(r5)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            goto L_0x00c5
        L_0x00b2:
            r0 = move-exception
            r1 = r0
            r11 = r5
            goto L_0x0325
        L_0x00b7:
            r0 = move-exception
            r1 = r0
            r11 = r5
            goto L_0x032c
        L_0x00bc:
            r0 = move-exception
            r1 = r0
            r11 = r5
            goto L_0x0333
        L_0x00c1:
            r0 = move-exception
            r1 = r0
            goto L_0x0337
        L_0x00c5:
            r4 = 1
            r3 = 1
        L_0x00c7:
            if (r3 <= r4) goto L_0x00d7
            boolean r1 = cz.msebera.android.httpclient.impl.execchain.RequestEntityProxy.a(r25)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            if (r1 != 0) goto L_0x00d7
            cz.msebera.android.httpclient.client.NonRepeatableRequestException r1 = new cz.msebera.android.httpclient.client.NonRepeatableRequestException     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            java.lang.String r2 = "Cannot retry request with a non-repeatable request entity."
            r1.<init>(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
        L_0x00d7:
            if (r11 == 0) goto L_0x00e7
            boolean r1 = r27.isAborted()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            if (r1 == 0) goto L_0x00e7
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r1 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            java.lang.String r2 = "Request aborted"
            r1.<init>(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
        L_0x00e7:
            boolean r1 = r6.isOpen()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0330, IOException -> 0x0329, RuntimeException -> 0x0322 }
            if (r1 != 0) goto L_0x0131
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            java.lang.String r4 = "Opening connection "
            r2.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            r2.append(r8)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x00bc, IOException -> 0x00b7, RuntimeException -> 0x00b2 }
            r1 = r7
            r2 = r13
            r19 = r3
            r3 = r6
            r18 = 1
            r4 = r8
            r20 = r14
            r14 = r5
            r5 = r9
            r21 = r14
            r14 = r6
            r6 = r10
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ TunnelRefusedException -> 0x0117 }
            goto L_0x013a
        L_0x0117:
            r0 = move-exception
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            if (r1 == 0) goto L_0x0129
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r2 = r0.getMessage()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x0129:
            cz.msebera.android.httpclient.HttpResponse r1 = r0.getResponse()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r11 = r21
            goto L_0x02de
        L_0x0131:
            r19 = r3
            r21 = r5
            r20 = r14
            r18 = 1
            r14 = r6
        L_0x013a:
            int r1 = r15.getSocketTimeout()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r1 < 0) goto L_0x0156
            r14.setSocketTimeout(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            goto L_0x0156
        L_0x0144:
            r0 = move-exception
            r1 = r0
            r11 = r21
            goto L_0x0325
        L_0x014a:
            r0 = move-exception
            r1 = r0
            r11 = r21
            goto L_0x032c
        L_0x0150:
            r0 = move-exception
            r1 = r0
            r11 = r21
            goto L_0x0333
        L_0x0156:
            if (r11 == 0) goto L_0x0166
            boolean r1 = r27.isAborted()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            if (r1 == 0) goto L_0x0166
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r1 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r2 = "Request aborted"
            r1.<init>(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x0166:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r1 == 0) goto L_0x0188
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r3 = "Executing request "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            cz.msebera.android.httpclient.RequestLine r3 = r25.getRequestLine()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x0188:
            java.lang.String r1 = "Authorization"
            boolean r1 = r9.containsHeader(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r1 != 0) goto L_0x01b7
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            if (r1 == 0) goto L_0x01b2
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r3 = "Target auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            cz.msebera.android.httpclient.auth.AuthProtocolState r3 = r12.getState()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x01b2:
            cz.msebera.android.httpclient.impl.auth.HttpAuthenticator r1 = r7.h     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.generateAuthResponse(r9, r12, r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x01b7:
            java.lang.String r1 = "Proxy-Authorization"
            boolean r1 = r9.containsHeader(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r1 != 0) goto L_0x01ec
            boolean r1 = r24.isTunnelled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            if (r1 != 0) goto L_0x01ec
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            if (r1 == 0) goto L_0x01e7
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r3 = "Proxy auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            cz.msebera.android.httpclient.auth.AuthProtocolState r3 = r13.getState()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x01e7:
            cz.msebera.android.httpclient.impl.auth.HttpAuthenticator r1 = r7.h     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r1.generateAuthResponse(r9, r13, r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x01ec:
            cz.msebera.android.httpclient.protocol.HttpRequestExecutor r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            cz.msebera.android.httpclient.HttpResponse r6 = r1.execute(r9, r14, r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            cz.msebera.android.httpclient.ConnectionReuseStrategy r1 = r7.c     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            boolean r1 = r1.keepAlive(r6, r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r1 == 0) goto L_0x024b
            cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy r1 = r7.d     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            long r1 = r1.getKeepAliveDuration(r6, r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            if (r3 == 0) goto L_0x0240
            int r3 = (r1 > r16 ? 1 : (r1 == r16 ? 0 : -1))
            if (r3 <= 0) goto L_0x0228
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r3.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r4 = "for "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r3.append(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r4 = " "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r3 = r3.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            goto L_0x022a
        L_0x0228:
            java.lang.String r3 = "indefinitely"
        L_0x022a:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r5.<init>()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r11 = "Connection can be kept alive "
            r5.append(r11)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r5.append(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            java.lang.String r3 = r5.toString()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
            r4.debug(r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x0150, IOException -> 0x014a, RuntimeException -> 0x0144 }
        L_0x0240:
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x031e, IOException -> 0x031a, RuntimeException -> 0x0316 }
            r11 = r21
            r11.a(r1, r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            r11.b()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            goto L_0x0250
        L_0x024b:
            r11 = r21
            r11.c()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x0250:
            r1 = r7
            r2 = r12
            r3 = r13
            r4 = r8
            r5 = r6
            r8 = r6
            r6 = r10
            boolean r1 = r1.a(r2, r3, r4, r5, r6)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 == 0) goto L_0x02dd
            cz.msebera.android.httpclient.HttpEntity r1 = r8.getEntity()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            boolean r2 = r11.a()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r2 == 0) goto L_0x026b
            cz.msebera.android.httpclient.util.EntityUtils.consume(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            goto L_0x02b2
        L_0x026b:
            r14.close()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            cz.msebera.android.httpclient.auth.AuthProtocolState r1 = r13.getState()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            cz.msebera.android.httpclient.auth.AuthProtocolState r2 = cz.msebera.android.httpclient.auth.AuthProtocolState.SUCCESS     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 != r2) goto L_0x0290
            cz.msebera.android.httpclient.auth.AuthScheme r1 = r13.getAuthScheme()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 == 0) goto L_0x0290
            cz.msebera.android.httpclient.auth.AuthScheme r1 = r13.getAuthScheme()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            boolean r1 = r1.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 == 0) goto L_0x0290
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            java.lang.String r2 = "Resetting proxy auth state"
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            r13.reset()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x0290:
            cz.msebera.android.httpclient.auth.AuthProtocolState r1 = r12.getState()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            cz.msebera.android.httpclient.auth.AuthProtocolState r2 = cz.msebera.android.httpclient.auth.AuthProtocolState.SUCCESS     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 != r2) goto L_0x02b2
            cz.msebera.android.httpclient.auth.AuthScheme r1 = r12.getAuthScheme()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 == 0) goto L_0x02b2
            cz.msebera.android.httpclient.auth.AuthScheme r1 = r12.getAuthScheme()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            boolean r1 = r1.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 == 0) goto L_0x02b2
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r7.log     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            java.lang.String r2 = "Resetting target auth state"
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            r12.reset()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x02b2:
            cz.msebera.android.httpclient.HttpRequest r1 = r25.getOriginal()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            java.lang.String r2 = "Authorization"
            boolean r2 = r1.containsHeader(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r2 != 0) goto L_0x02c3
            java.lang.String r2 = "Authorization"
            r9.removeHeaders(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x02c3:
            java.lang.String r2 = "Proxy-Authorization"
            boolean r1 = r1.containsHeader(r2)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r1 != 0) goto L_0x02d0
            java.lang.String r1 = "Proxy-Authorization"
            r9.removeHeaders(r1)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x02d0:
            int r3 = r19 + 1
            r5 = r11
            r6 = r14
            r14 = r20
            r4 = 1
            r8 = r24
            r11 = r27
            goto L_0x00c7
        L_0x02dd:
            r1 = r8
        L_0x02de:
            if (r20 != 0) goto L_0x02f2
            cz.msebera.android.httpclient.client.UserTokenHandler r2 = r7.i     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            java.lang.Object r14 = r2.getUserToken(r10)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            java.lang.String r2 = "http.user-token"
            r10.setAttribute(r2, r14)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            goto L_0x02f4
        L_0x02ec:
            r0 = move-exception
            goto L_0x0324
        L_0x02ee:
            r0 = move-exception
            goto L_0x032b
        L_0x02f0:
            r0 = move-exception
            goto L_0x0332
        L_0x02f2:
            r14 = r20
        L_0x02f4:
            if (r14 == 0) goto L_0x02f9
            r11.a(r14)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
        L_0x02f9:
            cz.msebera.android.httpclient.HttpEntity r2 = r1.getEntity()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r2 == 0) goto L_0x030c
            boolean r2 = r2.isStreaming()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            if (r2 != 0) goto L_0x0306
            goto L_0x030c
        L_0x0306:
            cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy r2 = new cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            r2.<init>(r1, r11)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            return r2
        L_0x030c:
            r11.releaseConnection()     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy r2 = new cz.msebera.android.httpclient.impl.execchain.HttpResponseProxy     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            r3 = 0
            r2.<init>(r1, r3)     // Catch:{ ConnectionShutdownException -> 0x00c1, HttpException -> 0x02f0, IOException -> 0x02ee, RuntimeException -> 0x02ec }
            return r2
        L_0x0316:
            r0 = move-exception
            r11 = r21
            goto L_0x0324
        L_0x031a:
            r0 = move-exception
            r11 = r21
            goto L_0x032b
        L_0x031e:
            r0 = move-exception
            r11 = r21
            goto L_0x0332
        L_0x0322:
            r0 = move-exception
            r11 = r5
        L_0x0324:
            r1 = r0
        L_0x0325:
            r11.abortConnection()
            throw r1
        L_0x0329:
            r0 = move-exception
            r11 = r5
        L_0x032b:
            r1 = r0
        L_0x032c:
            r11.abortConnection()
            throw r1
        L_0x0330:
            r0 = move-exception
            r11 = r5
        L_0x0332:
            r1 = r0
        L_0x0333:
            r11.abortConnection()
            throw r1
        L_0x0337:
            java.io.InterruptedIOException r2 = new java.io.InterruptedIOException
            java.lang.String r3 = "Connection has been shut down"
            r2.<init>(r3)
            r2.initCause(r1)
            throw r2
        L_0x0342:
            r0 = move-exception
            r1 = r0
            java.lang.Throwable r2 = r1.getCause()
            if (r2 != 0) goto L_0x034b
            goto L_0x034c
        L_0x034b:
            r1 = r2
        L_0x034c:
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r2 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r3 = "Request execution failed"
            r2.<init>(r3, r1)
            throw r2
        L_0x0354:
            r0 = move-exception
            r1 = r0
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
            cz.msebera.android.httpclient.impl.execchain.RequestAbortedException r2 = new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException
            java.lang.String r3 = "Request aborted"
            r2.<init>(r3, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MainClientExec.execute(cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper, cz.msebera.android.httpclient.client.protocol.HttpClientContext, cz.msebera.android.httpclient.client.methods.HttpExecutionAware):cz.msebera.android.httpclient.client.methods.CloseableHttpResponse");
    }

    /* access modifiers changed from: 0000 */
    public void a(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) {
        int nextStep;
        int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        RouteTracker routeTracker = new RouteTracker(httpRoute);
        do {
            HttpRoute route = routeTracker.toRoute();
            nextStep = this.j.nextStep(httpRoute, route);
            int i2 = 0;
            switch (nextStep) {
                case -1:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to establish route: planned = ");
                    sb.append(httpRoute);
                    sb.append("; current = ");
                    sb.append(route);
                    throw new HttpException(sb.toString());
                case 0:
                    this.b.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    continue;
                case 1:
                    HttpClientConnectionManager httpClientConnectionManager = this.b;
                    if (connectTimeout > 0) {
                        i2 = connectTimeout;
                    }
                    httpClientConnectionManager.connect(httpClientConnection, httpRoute, i2, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    continue;
                case 2:
                    this.b.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectProxy(httpRoute.getProxyHost(), false);
                    continue;
                case 3:
                    boolean b2 = b(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(b2);
                    continue;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean a2 = a(httpRoute, hopCount, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(hopCount), a2);
                    continue;
                case 5:
                    this.b.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    continue;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unknown step indicator ");
                    sb2.append(nextStep);
                    sb2.append(" from RouteDirector.");
                    throw new IllegalStateException(sb2.toString());
            }
        } while (nextStep > 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a5, code lost:
        r3 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ad, code lost:
        if (r0.c.keepAlive(r3, r8) == false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00af, code lost:
        r0.log.debug("Connection kept alive");
        cz.msebera.android.httpclient.util.EntityUtils.consume(r3.getEntity());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00bf, code lost:
        r19.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b(cz.msebera.android.httpclient.auth.AuthState r18, cz.msebera.android.httpclient.HttpClientConnection r19, cz.msebera.android.httpclient.conn.routing.HttpRoute r20, cz.msebera.android.httpclient.HttpRequest r21, cz.msebera.android.httpclient.client.protocol.HttpClientContext r22) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            r8 = r22
            cz.msebera.android.httpclient.client.config.RequestConfig r9 = r22.getRequestConfig()
            int r10 = r9.getConnectTimeout()
            cz.msebera.android.httpclient.HttpHost r2 = r20.getTargetHost()
            cz.msebera.android.httpclient.HttpHost r11 = r20.getProxyHost()
            java.lang.String r2 = r2.toHostString()
            cz.msebera.android.httpclient.message.BasicHttpRequest r12 = new cz.msebera.android.httpclient.message.BasicHttpRequest
            java.lang.String r3 = "CONNECT"
            cz.msebera.android.httpclient.ProtocolVersion r4 = r21.getProtocolVersion()
            r12.<init>(r3, r2, r4)
            cz.msebera.android.httpclient.protocol.HttpRequestExecutor r2 = r0.a
            cz.msebera.android.httpclient.protocol.HttpProcessor r3 = r0.e
            r2.preProcess(r12, r3, r8)
            r13 = 0
        L_0x002d:
            r2 = r13
        L_0x002e:
            r3 = 0
            if (r2 != 0) goto L_0x00cb
            boolean r2 = r19.isOpen()
            if (r2 != 0) goto L_0x0045
            cz.msebera.android.httpclient.conn.HttpClientConnectionManager r2 = r0.b
            if (r10 <= 0) goto L_0x003f
            r14 = r20
            r3 = r10
            goto L_0x0041
        L_0x003f:
            r14 = r20
        L_0x0041:
            r2.connect(r1, r14, r3, r8)
            goto L_0x0047
        L_0x0045:
            r14 = r20
        L_0x0047:
            java.lang.String r2 = "Proxy-Authorization"
            r12.removeHeaders(r2)
            cz.msebera.android.httpclient.impl.auth.HttpAuthenticator r2 = r0.h
            r15 = r18
            r2.generateAuthResponse(r12, r15, r8)
            cz.msebera.android.httpclient.protocol.HttpRequestExecutor r2 = r0.a
            cz.msebera.android.httpclient.HttpResponse r7 = r2.execute(r12, r1, r8)
            cz.msebera.android.httpclient.StatusLine r2 = r7.getStatusLine()
            int r2 = r2.getStatusCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 >= r3) goto L_0x0080
            cz.msebera.android.httpclient.HttpException r1 = new cz.msebera.android.httpclient.HttpException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unexpected response to CONNECT request: "
            r2.append(r3)
            cz.msebera.android.httpclient.StatusLine r3 = r7.getStatusLine()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0080:
            boolean r2 = r9.isAuthenticationEnabled()
            if (r2 == 0) goto L_0x00c7
            cz.msebera.android.httpclient.impl.auth.HttpAuthenticator r2 = r0.h
            cz.msebera.android.httpclient.client.AuthenticationStrategy r5 = r0.g
            r3 = r11
            r4 = r7
            r6 = r15
            r16 = r7
            r7 = r8
            boolean r2 = r2.isAuthenticationRequested(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x00c4
            cz.msebera.android.httpclient.impl.auth.HttpAuthenticator r2 = r0.h
            cz.msebera.android.httpclient.client.AuthenticationStrategy r5 = r0.g
            r3 = r11
            r4 = r16
            r6 = r15
            r7 = r8
            boolean r2 = r2.handleAuthChallenge(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x00c4
            cz.msebera.android.httpclient.ConnectionReuseStrategy r2 = r0.c
            r3 = r16
            boolean r2 = r2.keepAlive(r3, r8)
            if (r2 == 0) goto L_0x00bf
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r2 = r0.log
            java.lang.String r4 = "Connection kept alive"
            r2.debug(r4)
            cz.msebera.android.httpclient.HttpEntity r2 = r3.getEntity()
            cz.msebera.android.httpclient.util.EntityUtils.consume(r2)
            goto L_0x002d
        L_0x00bf:
            r19.close()
            goto L_0x002d
        L_0x00c4:
            r3 = r16
            goto L_0x00c8
        L_0x00c7:
            r3 = r7
        L_0x00c8:
            r2 = r3
            goto L_0x002e
        L_0x00cb:
            cz.msebera.android.httpclient.StatusLine r4 = r2.getStatusLine()
            int r4 = r4.getStatusCode()
            r5 = 299(0x12b, float:4.19E-43)
            if (r4 <= r5) goto L_0x0103
            cz.msebera.android.httpclient.HttpEntity r3 = r2.getEntity()
            if (r3 == 0) goto L_0x00e5
            cz.msebera.android.httpclient.entity.BufferedHttpEntity r4 = new cz.msebera.android.httpclient.entity.BufferedHttpEntity
            r4.<init>(r3)
            r2.setEntity(r4)
        L_0x00e5:
            r19.close()
            cz.msebera.android.httpclient.impl.execchain.TunnelRefusedException r1 = new cz.msebera.android.httpclient.impl.execchain.TunnelRefusedException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "CONNECT refused by proxy: "
            r3.append(r4)
            cz.msebera.android.httpclient.StatusLine r4 = r2.getStatusLine()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3, r2)
            throw r1
        L_0x0103:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MainClientExec.b(cz.msebera.android.httpclient.auth.AuthState, cz.msebera.android.httpclient.HttpClientConnection, cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.HttpRequest, cz.msebera.android.httpclient.client.protocol.HttpClientContext):boolean");
    }

    private boolean a(HttpRoute httpRoute, int i2, HttpClientContext httpClientContext) {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean a(AuthState authState, AuthState authState2, HttpRoute httpRoute, HttpResponse httpResponse, HttpClientContext httpClientContext) {
        if (httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            HttpHost targetHost = httpClientContext.getTargetHost();
            if (targetHost == null) {
                targetHost = httpRoute.getTargetHost();
            }
            if (targetHost.getPort() < 0) {
                targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
            }
            boolean isAuthenticationRequested = this.h.isAuthenticationRequested(targetHost, httpResponse, this.f, authState, httpClientContext);
            HttpHost proxyHost = httpRoute.getProxyHost();
            if (proxyHost == null) {
                proxyHost = httpRoute.getTargetHost();
            }
            boolean isAuthenticationRequested2 = this.h.isAuthenticationRequested(proxyHost, httpResponse, this.g, authState2, httpClientContext);
            if (isAuthenticationRequested) {
                return this.h.handleAuthChallenge(targetHost, httpResponse, this.f, authState, httpClientContext);
            } else if (isAuthenticationRequested2) {
                return this.h.handleAuthChallenge(proxyHost, httpResponse, this.g, authState2, httpClientContext);
            }
        }
        return false;
    }
}
