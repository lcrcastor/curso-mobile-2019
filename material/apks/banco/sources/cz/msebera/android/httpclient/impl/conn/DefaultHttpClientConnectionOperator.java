package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.conn.HttpClientConnectionOperator;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.SchemePortResolver;
import cz.msebera.android.httpclient.conn.UnsupportedSchemeException;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.socket.LayeredConnectionSocketFactory;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class DefaultHttpClientConnectionOperator implements HttpClientConnectionOperator {
    private final Lookup<ConnectionSocketFactory> a;
    private final SchemePortResolver b;
    private final DnsResolver c;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public DefaultHttpClientConnectionOperator(Lookup<ConnectionSocketFactory> lookup, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
        Args.notNull(lookup, "Socket factory registry");
        this.a = lookup;
        if (schemePortResolver == null) {
            schemePortResolver = DefaultSchemePortResolver.INSTANCE;
        }
        this.b = schemePortResolver;
        if (dnsResolver == null) {
            dnsResolver = SystemDefaultDnsResolver.INSTANCE;
        }
        this.c = dnsResolver;
    }

    private Lookup<ConnectionSocketFactory> a(HttpContext httpContext) {
        Lookup<ConnectionSocketFactory> lookup = (Lookup) httpContext.getAttribute(ClientContext.SOCKET_FACTORY_REGISTRY);
        return lookup == null ? this.a : lookup;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x011b  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x013d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(cz.msebera.android.httpclient.conn.ManagedHttpClientConnection r21, cz.msebera.android.httpclient.HttpHost r22, java.net.InetSocketAddress r23, int r24, cz.msebera.android.httpclient.config.SocketConfig r25, cz.msebera.android.httpclient.protocol.HttpContext r26) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            r10 = r22
            r11 = r26
            cz.msebera.android.httpclient.config.Lookup r3 = r1.a(r11)
            java.lang.String r4 = r22.getSchemeName()
            java.lang.Object r3 = r3.lookup(r4)
            r12 = r3
            cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory r12 = (cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory) r12
            if (r12 != 0) goto L_0x0034
            cz.msebera.android.httpclient.conn.UnsupportedSchemeException r2 = new cz.msebera.android.httpclient.conn.UnsupportedSchemeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r22.getSchemeName()
            r3.append(r4)
            java.lang.String r4 = " protocol is not supported"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0034:
            java.net.InetAddress r3 = r22.getAddress()
            r13 = 0
            r14 = 1
            if (r3 == 0) goto L_0x0046
            java.net.InetAddress[] r3 = new java.net.InetAddress[r14]
            java.net.InetAddress r4 = r22.getAddress()
            r3[r13] = r4
        L_0x0044:
            r15 = r3
            goto L_0x0051
        L_0x0046:
            cz.msebera.android.httpclient.conn.DnsResolver r3 = r1.c
            java.lang.String r4 = r22.getHostName()
            java.net.InetAddress[] r3 = r3.resolve(r4)
            goto L_0x0044
        L_0x0051:
            cz.msebera.android.httpclient.conn.SchemePortResolver r3 = r1.b
            int r9 = r3.resolve(r10)
            r8 = 0
        L_0x0058:
            int r3 = r15.length
            if (r8 >= r3) goto L_0x0143
            r3 = r15[r8]
            int r4 = r15.length
            int r4 = r4 - r14
            if (r8 != r4) goto L_0x0064
            r16 = 1
            goto L_0x0066
        L_0x0064:
            r16 = 0
        L_0x0066:
            java.net.Socket r5 = r12.createSocket(r11)
            int r4 = r25.getSoTimeout()
            r5.setSoTimeout(r4)
            boolean r4 = r25.isSoReuseAddress()
            r5.setReuseAddress(r4)
            boolean r4 = r25.isTcpNoDelay()
            r5.setTcpNoDelay(r4)
            boolean r4 = r25.isSoKeepAlive()
            r5.setKeepAlive(r4)
            int r4 = r25.getSoLinger()
            if (r4 < 0) goto L_0x008f
            r5.setSoLinger(r14, r4)
        L_0x008f:
            r2.bind(r5)
            java.net.InetSocketAddress r7 = new java.net.InetSocketAddress
            r7.<init>(r3, r9)
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log
            boolean r3 = r3.isDebugEnabled()
            if (r3 == 0) goto L_0x00b5
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Connecting to "
            r4.append(r6)
            r4.append(r7)
            java.lang.String r4 = r4.toString()
            r3.debug(r4)
        L_0x00b5:
            r3 = r12
            r4 = r24
            r6 = r10
            r17 = r7
            r18 = r8
            r8 = r23
            r19 = r9
            r9 = r11
            java.net.Socket r3 = r3.connectSocket(r4, r5, r6, r7, r8, r9)     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            r2.bind(r3)     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            if (r3 == 0) goto L_0x00e7
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            r4.<init>()     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            java.lang.String r5 = "Connection established "
            r4.append(r5)     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            r4.append(r2)     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            java.lang.String r4 = r4.toString()     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
            r3.debug(r4)     // Catch:{ SocketTimeoutException -> 0x0109, ConnectException -> 0x00ed, NoRouteToHostException -> 0x00e8 }
        L_0x00e7:
            return
        L_0x00e8:
            r0 = move-exception
            r3 = r0
            if (r16 == 0) goto L_0x0113
            throw r3
        L_0x00ed:
            r0 = move-exception
            r3 = r0
            if (r16 == 0) goto L_0x0113
            java.lang.String r2 = r3.getMessage()
            java.lang.String r4 = "Connection timed out"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0103
            cz.msebera.android.httpclient.conn.ConnectTimeoutException r2 = new cz.msebera.android.httpclient.conn.ConnectTimeoutException
            r2.<init>(r3, r10, r15)
            throw r2
        L_0x0103:
            cz.msebera.android.httpclient.conn.HttpHostConnectException r2 = new cz.msebera.android.httpclient.conn.HttpHostConnectException
            r2.<init>(r3, r10, r15)
            throw r2
        L_0x0109:
            r0 = move-exception
            r3 = r0
            if (r16 == 0) goto L_0x0113
            cz.msebera.android.httpclient.conn.ConnectTimeoutException r2 = new cz.msebera.android.httpclient.conn.ConnectTimeoutException
            r2.<init>(r3, r10, r15)
            throw r2
        L_0x0113:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log
            boolean r3 = r3.isDebugEnabled()
            if (r3 == 0) goto L_0x013d
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r1.log
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Connect to "
            r4.append(r5)
            r5 = r17
            r4.append(r5)
            java.lang.String r5 = " timed out. "
            r4.append(r5)
            java.lang.String r5 = "Connection will be retried using another IP address"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.debug(r4)
        L_0x013d:
            int r8 = r18 + 1
            r9 = r19
            goto L_0x0058
        L_0x0143:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.DefaultHttpClientConnectionOperator.connect(cz.msebera.android.httpclient.conn.ManagedHttpClientConnection, cz.msebera.android.httpclient.HttpHost, java.net.InetSocketAddress, int, cz.msebera.android.httpclient.config.SocketConfig, cz.msebera.android.httpclient.protocol.HttpContext):void");
    }

    public void upgrade(ManagedHttpClientConnection managedHttpClientConnection, HttpHost httpHost, HttpContext httpContext) {
        ConnectionSocketFactory connectionSocketFactory = (ConnectionSocketFactory) a(HttpClientContext.adapt(httpContext)).lookup(httpHost.getSchemeName());
        if (connectionSocketFactory == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(httpHost.getSchemeName());
            sb.append(" protocol is not supported");
            throw new UnsupportedSchemeException(sb.toString());
        } else if (!(connectionSocketFactory instanceof LayeredConnectionSocketFactory)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(httpHost.getSchemeName());
            sb2.append(" protocol does not support connection upgrade");
            throw new UnsupportedSchemeException(sb2.toString());
        } else {
            managedHttpClientConnection.bind(((LayeredConnectionSocketFactory) connectionSocketFactory).createLayeredSocket(managedHttpClientConnection.getSocket(), httpHost.getHostName(), this.b.resolve(httpHost), httpContext));
        }
    }
}
