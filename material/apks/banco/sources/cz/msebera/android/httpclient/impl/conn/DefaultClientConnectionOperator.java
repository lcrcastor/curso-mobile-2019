package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.scheme.SchemeLayeredSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.net.InetAddress;
import java.net.Socket;

@ThreadSafe
@Deprecated
public class DefaultClientConnectionOperator implements ClientConnectionOperator {
    protected final DnsResolver dnsResolver;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    protected final SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry2) {
        Args.notNull(schemeRegistry2, "Scheme registry");
        this.schemeRegistry = schemeRegistry2;
        this.dnsResolver = new SystemDefaultDnsResolver();
    }

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry2, DnsResolver dnsResolver2) {
        Args.notNull(schemeRegistry2, "Scheme registry");
        Args.notNull(dnsResolver2, "DNS resolver");
        this.schemeRegistry = schemeRegistry2;
        this.dnsResolver = dnsResolver2;
    }

    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    private SchemeRegistry a(HttpContext httpContext) {
        SchemeRegistry schemeRegistry2 = (SchemeRegistry) httpContext.getAttribute(ClientContext.SCHEME_REGISTRY);
        return schemeRegistry2 == null ? this.schemeRegistry : schemeRegistry2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d2 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void openConnection(cz.msebera.android.httpclient.conn.OperatedClientConnection r18, cz.msebera.android.httpclient.HttpHost r19, java.net.InetAddress r20, cz.msebera.android.httpclient.protocol.HttpContext r21, cz.msebera.android.httpclient.params.HttpParams r22) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r22
            java.lang.String r7 = "Connection"
            cz.msebera.android.httpclient.util.Args.notNull(r2, r7)
            java.lang.String r7 = "Target host"
            cz.msebera.android.httpclient.util.Args.notNull(r3, r7)
            java.lang.String r7 = "HTTP parameters"
            cz.msebera.android.httpclient.util.Args.notNull(r6, r7)
            boolean r7 = r18.isOpen()
            r8 = 1
            r7 = r7 ^ r8
            java.lang.String r9 = "Connection must not be open"
            cz.msebera.android.httpclient.util.Asserts.check(r7, r9)
            cz.msebera.android.httpclient.conn.scheme.SchemeRegistry r7 = r1.a(r5)
            java.lang.String r9 = r19.getSchemeName()
            cz.msebera.android.httpclient.conn.scheme.Scheme r7 = r7.getScheme(r9)
            cz.msebera.android.httpclient.conn.scheme.SchemeSocketFactory r9 = r7.getSchemeSocketFactory()
            java.lang.String r10 = r19.getHostName()
            java.net.InetAddress[] r10 = r1.resolveHostname(r10)
            int r11 = r19.getPort()
            int r7 = r7.resolvePort(r11)
            r11 = 0
            r12 = 0
        L_0x0048:
            int r13 = r10.length
            if (r12 >= r13) goto L_0x00dc
            r13 = r10[r12]
            int r14 = r10.length
            int r14 = r14 - r8
            if (r12 != r14) goto L_0x0053
            r14 = 1
            goto L_0x0054
        L_0x0053:
            r14 = 0
        L_0x0054:
            java.net.Socket r15 = r9.createSocket(r6)
            r2.opening(r15, r3)
            cz.msebera.android.httpclient.conn.HttpInetSocketAddress r8 = new cz.msebera.android.httpclient.conn.HttpInetSocketAddress
            r8.<init>(r3, r13, r7)
            r13 = 0
            if (r4 == 0) goto L_0x0068
            java.net.InetSocketAddress r13 = new java.net.InetSocketAddress
            r13.<init>(r4, r11)
        L_0x0068:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r11 = r1.log
            boolean r11 = r11.isDebugEnabled()
            if (r11 == 0) goto L_0x0089
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r11 = r1.log
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r16 = r7
            java.lang.String r7 = "Connecting to "
            r4.append(r7)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            r11.debug(r4)
            goto L_0x008b
        L_0x0089:
            r16 = r7
        L_0x008b:
            java.net.Socket r4 = r9.connectSocket(r15, r8, r13, r6)     // Catch:{ ConnectException -> 0x00a5, ConnectTimeoutException -> 0x00a0 }
            if (r15 == r4) goto L_0x0095
            r2.opening(r4, r3)     // Catch:{ ConnectException -> 0x00a5, ConnectTimeoutException -> 0x00a0 }
            r15 = r4
        L_0x0095:
            r1.prepareSocket(r15, r5, r6)     // Catch:{ ConnectException -> 0x00a5, ConnectTimeoutException -> 0x00a0 }
            boolean r4 = r9.isSecure(r15)     // Catch:{ ConnectException -> 0x00a5, ConnectTimeoutException -> 0x00a0 }
            r2.openCompleted(r4, r6)     // Catch:{ ConnectException -> 0x00a5, ConnectTimeoutException -> 0x00a0 }
            return
        L_0x00a0:
            r0 = move-exception
            r4 = r0
            if (r14 == 0) goto L_0x00aa
            throw r4
        L_0x00a5:
            r0 = move-exception
            r4 = r0
            if (r14 == 0) goto L_0x00aa
            throw r4
        L_0x00aa:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r1.log
            boolean r4 = r4.isDebugEnabled()
            if (r4 == 0) goto L_0x00d2
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r1.log
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r11 = "Connect to "
            r7.append(r11)
            r7.append(r8)
            java.lang.String r8 = " timed out. "
            r7.append(r8)
            java.lang.String r8 = "Connection will be retried using another IP address"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r4.debug(r7)
        L_0x00d2:
            int r12 = r12 + 1
            r7 = r16
            r4 = r20
            r8 = 1
            r11 = 0
            goto L_0x0048
        L_0x00dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.DefaultClientConnectionOperator.openConnection(cz.msebera.android.httpclient.conn.OperatedClientConnection, cz.msebera.android.httpclient.HttpHost, java.net.InetAddress, cz.msebera.android.httpclient.protocol.HttpContext, cz.msebera.android.httpclient.params.HttpParams):void");
    }

    public void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        Asserts.check(operatedClientConnection.isOpen(), "Connection must be open");
        Scheme scheme = a(httpContext).getScheme(httpHost.getSchemeName());
        Asserts.check(scheme.getSchemeSocketFactory() instanceof SchemeLayeredSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        SchemeLayeredSocketFactory schemeLayeredSocketFactory = (SchemeLayeredSocketFactory) scheme.getSchemeSocketFactory();
        Socket createLayeredSocket = schemeLayeredSocketFactory.createLayeredSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), httpParams);
        prepareSocket(createLayeredSocket, httpContext, httpParams);
        operatedClientConnection.update(createLayeredSocket, httpHost, schemeLayeredSocketFactory.isSecure(createLayeredSocket), httpParams);
    }

    /* access modifiers changed from: protected */
    public void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
    }

    /* access modifiers changed from: protected */
    public InetAddress[] resolveHostname(String str) {
        return this.dnsResolver.resolve(str);
    }
}
