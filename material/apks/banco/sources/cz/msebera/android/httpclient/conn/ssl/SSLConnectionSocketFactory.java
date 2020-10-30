package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.socket.LayeredConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcherLoader;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.ssl.SSLContexts;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

@ThreadSafe
public class SSLConnectionSocketFactory implements LayeredConnectionSocketFactory {
    @Deprecated
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = AllowAllHostnameVerifier.INSTANCE;
    @Deprecated
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = BrowserCompatHostnameVerifier.INSTANCE;
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    @Deprecated
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = StrictHostnameVerifier.INSTANCE;
    public static final String TLS = "TLS";
    private final SSLSocketFactory a;
    private final HostnameVerifier b;
    private final String[] c;
    private final String[] d;
    public HttpClientAndroidLog log;

    /* access modifiers changed from: protected */
    public void prepareSocket(SSLSocket sSLSocket) {
    }

    public static HostnameVerifier getDefaultHostnameVerifier() {
        return new DefaultHostnameVerifier(PublicSuffixMatcherLoader.getDefault());
    }

    public static SSLConnectionSocketFactory getSocketFactory() {
        return new SSLConnectionSocketFactory(SSLContexts.createDefault(), getDefaultHostnameVerifier());
    }

    private static String[] a(String str) {
        if (TextUtils.isBlank(str)) {
            return null;
        }
        return str.split(" *, *");
    }

    public static SSLConnectionSocketFactory getSystemSocketFactory() {
        return new SSLConnectionSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault(), a(System.getProperty("https.protocols")), a(System.getProperty("https.cipherSuites")), getDefaultHostnameVerifier());
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext) {
        this(sSLContext, getDefaultHostnameVerifier());
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), (String[]) null, (String[]) null, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLContext sSLContext, String[] strArr, String[] strArr2, X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), strArr, strArr2, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, (String[]) null, (String[]) null, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, String[] strArr, String[] strArr2, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, strArr, strArr2, (HostnameVerifier) x509HostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext, HostnameVerifier hostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), (String[]) null, (String[]) null, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext, String[] strArr, String[] strArr2, HostnameVerifier hostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), strArr, strArr2, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        this(sSLSocketFactory, (String[]) null, (String[]) null, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, String[] strArr, String[] strArr2, HostnameVerifier hostnameVerifier) {
        this.log = new HttpClientAndroidLog(getClass());
        this.a = (SSLSocketFactory) Args.notNull(sSLSocketFactory, "SSL socket factory");
        this.c = strArr;
        this.d = strArr2;
        if (hostnameVerifier == null) {
            hostnameVerifier = getDefaultHostnameVerifier();
        }
        this.b = hostnameVerifier;
    }

    public Socket createSocket(HttpContext httpContext) {
        return SocketFactory.getDefault().createSocket();
    }

    public Socket connectSocket(int i, Socket socket, HttpHost httpHost, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpContext httpContext) {
        Args.notNull(httpHost, "HTTP host");
        Args.notNull(inetSocketAddress, "Remote address");
        if (socket == null) {
            socket = createSocket(httpContext);
        }
        if (inetSocketAddress2 != null) {
            socket.bind(inetSocketAddress2);
        }
        if (i > 0) {
            try {
                if (socket.getSoTimeout() == 0) {
                    socket.setSoTimeout(i);
                }
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException unused) {
                }
                throw e;
            }
        }
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Connecting socket to ");
            sb.append(inetSocketAddress);
            sb.append(" with timeout ");
            sb.append(i);
            httpClientAndroidLog.debug(sb.toString());
        }
        socket.connect(inetSocketAddress, i);
        if (!(socket instanceof SSLSocket)) {
            return createLayeredSocket(socket, httpHost.getHostName(), inetSocketAddress.getPort(), httpContext);
        }
        SSLSocket sSLSocket = (SSLSocket) socket;
        this.log.debug("Starting handshake");
        sSLSocket.startHandshake();
        a(sSLSocket, httpHost.getHostName());
        return socket;
    }

    public Socket createLayeredSocket(Socket socket, String str, int i, HttpContext httpContext) {
        SSLSocket sSLSocket = (SSLSocket) this.a.createSocket(socket, str, i, true);
        if (this.c != null) {
            sSLSocket.setEnabledProtocols(this.c);
        } else {
            String[] enabledProtocols = sSLSocket.getEnabledProtocols();
            ArrayList arrayList = new ArrayList(enabledProtocols.length);
            for (String str2 : enabledProtocols) {
                if (!str2.startsWith("SSL")) {
                    arrayList.add(str2);
                }
            }
            if (!arrayList.isEmpty()) {
                sSLSocket.setEnabledProtocols((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
        }
        if (this.d != null) {
            sSLSocket.setEnabledCipherSuites(this.d);
        }
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Enabled protocols: ");
            sb.append(Arrays.asList(sSLSocket.getEnabledProtocols()));
            httpClientAndroidLog.debug(sb.toString());
            HttpClientAndroidLog httpClientAndroidLog2 = this.log;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Enabled cipher suites:");
            sb2.append(Arrays.asList(sSLSocket.getEnabledCipherSuites()));
            httpClientAndroidLog2.debug(sb2.toString());
        }
        prepareSocket(sSLSocket);
        this.log.debug("Starting handshake");
        sSLSocket.startHandshake();
        a(sSLSocket, str);
        return sSLSocket;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x012f */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0137 A[Catch:{ IOException -> 0x0171 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0170 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(javax.net.ssl.SSLSocket r9, java.lang.String r10) {
        /*
            r8 = this;
            javax.net.ssl.SSLSession r0 = r9.getSession()     // Catch:{ IOException -> 0x0171 }
            if (r0 != 0) goto L_0x001a
            java.io.InputStream r0 = r9.getInputStream()     // Catch:{ IOException -> 0x0171 }
            r0.available()     // Catch:{ IOException -> 0x0171 }
            javax.net.ssl.SSLSession r0 = r9.getSession()     // Catch:{ IOException -> 0x0171 }
            if (r0 != 0) goto L_0x001a
            r9.startHandshake()     // Catch:{ IOException -> 0x0171 }
            javax.net.ssl.SSLSession r0 = r9.getSession()     // Catch:{ IOException -> 0x0171 }
        L_0x001a:
            if (r0 != 0) goto L_0x0024
            javax.net.ssl.SSLHandshakeException r10 = new javax.net.ssl.SSLHandshakeException     // Catch:{ IOException -> 0x0171 }
            java.lang.String r0 = "SSL session not available"
            r10.<init>(r0)     // Catch:{ IOException -> 0x0171 }
            throw r10     // Catch:{ IOException -> 0x0171 }
        L_0x0024:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r8.log     // Catch:{ IOException -> 0x0171 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ IOException -> 0x0171 }
            r2 = 0
            if (r1 == 0) goto L_0x012f
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r8.log     // Catch:{ IOException -> 0x0171 }
            java.lang.String r3 = "Secure session established"
            r1.debug(r3)     // Catch:{ IOException -> 0x0171 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r8.log     // Catch:{ IOException -> 0x0171 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0171 }
            r3.<init>()     // Catch:{ IOException -> 0x0171 }
            java.lang.String r4 = " negotiated protocol: "
            r3.append(r4)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r4 = r0.getProtocol()     // Catch:{ IOException -> 0x0171 }
            r3.append(r4)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0171 }
            r1.debug(r3)     // Catch:{ IOException -> 0x0171 }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r8.log     // Catch:{ IOException -> 0x0171 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0171 }
            r3.<init>()     // Catch:{ IOException -> 0x0171 }
            java.lang.String r4 = " negotiated cipher suite: "
            r3.append(r4)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r4 = r0.getCipherSuite()     // Catch:{ IOException -> 0x0171 }
            r3.append(r4)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0171 }
            r1.debug(r3)     // Catch:{ IOException -> 0x0171 }
            java.security.cert.Certificate[] r1 = r0.getPeerCertificates()     // Catch:{ Exception -> 0x012f }
            r1 = r1[r2]     // Catch:{ Exception -> 0x012f }
            java.security.cert.X509Certificate r1 = (java.security.cert.X509Certificate) r1     // Catch:{ Exception -> 0x012f }
            javax.security.auth.x500.X500Principal r3 = r1.getSubjectX500Principal()     // Catch:{ Exception -> 0x012f }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r4 = r8.log     // Catch:{ Exception -> 0x012f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012f }
            r5.<init>()     // Catch:{ Exception -> 0x012f }
            java.lang.String r6 = " peer principal: "
            r5.append(r6)     // Catch:{ Exception -> 0x012f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x012f }
            r5.append(r3)     // Catch:{ Exception -> 0x012f }
            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x012f }
            r4.debug(r3)     // Catch:{ Exception -> 0x012f }
            java.util.Collection r3 = r1.getSubjectAlternativeNames()     // Catch:{ Exception -> 0x012f }
            r4 = 1
            if (r3 == 0) goto L_0x00d0
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x012f }
            r5.<init>()     // Catch:{ Exception -> 0x012f }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x012f }
        L_0x009e:
            boolean r6 = r3.hasNext()     // Catch:{ Exception -> 0x012f }
            if (r6 == 0) goto L_0x00ba
            java.lang.Object r6 = r3.next()     // Catch:{ Exception -> 0x012f }
            java.util.List r6 = (java.util.List) r6     // Catch:{ Exception -> 0x012f }
            boolean r7 = r6.isEmpty()     // Catch:{ Exception -> 0x012f }
            if (r7 != 0) goto L_0x009e
            java.lang.Object r6 = r6.get(r4)     // Catch:{ Exception -> 0x012f }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x012f }
            r5.add(r6)     // Catch:{ Exception -> 0x012f }
            goto L_0x009e
        L_0x00ba:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r3 = r8.log     // Catch:{ Exception -> 0x012f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012f }
            r6.<init>()     // Catch:{ Exception -> 0x012f }
            java.lang.String r7 = " peer alternative names: "
            r6.append(r7)     // Catch:{ Exception -> 0x012f }
            r6.append(r5)     // Catch:{ Exception -> 0x012f }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x012f }
            r3.debug(r5)     // Catch:{ Exception -> 0x012f }
        L_0x00d0:
            javax.security.auth.x500.X500Principal r3 = r1.getIssuerX500Principal()     // Catch:{ Exception -> 0x012f }
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r5 = r8.log     // Catch:{ Exception -> 0x012f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012f }
            r6.<init>()     // Catch:{ Exception -> 0x012f }
            java.lang.String r7 = " issuer principal: "
            r6.append(r7)     // Catch:{ Exception -> 0x012f }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x012f }
            r6.append(r3)     // Catch:{ Exception -> 0x012f }
            java.lang.String r3 = r6.toString()     // Catch:{ Exception -> 0x012f }
            r5.debug(r3)     // Catch:{ Exception -> 0x012f }
            java.util.Collection r1 = r1.getIssuerAlternativeNames()     // Catch:{ Exception -> 0x012f }
            if (r1 == 0) goto L_0x012f
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x012f }
            r3.<init>()     // Catch:{ Exception -> 0x012f }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Exception -> 0x012f }
        L_0x00fd:
            boolean r5 = r1.hasNext()     // Catch:{ Exception -> 0x012f }
            if (r5 == 0) goto L_0x0119
            java.lang.Object r5 = r1.next()     // Catch:{ Exception -> 0x012f }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Exception -> 0x012f }
            boolean r6 = r5.isEmpty()     // Catch:{ Exception -> 0x012f }
            if (r6 != 0) goto L_0x00fd
            java.lang.Object r5 = r5.get(r4)     // Catch:{ Exception -> 0x012f }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x012f }
            r3.add(r5)     // Catch:{ Exception -> 0x012f }
            goto L_0x00fd
        L_0x0119:
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r1 = r8.log     // Catch:{ Exception -> 0x012f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012f }
            r4.<init>()     // Catch:{ Exception -> 0x012f }
            java.lang.String r5 = " issuer alternative names: "
            r4.append(r5)     // Catch:{ Exception -> 0x012f }
            r4.append(r3)     // Catch:{ Exception -> 0x012f }
            java.lang.String r3 = r4.toString()     // Catch:{ Exception -> 0x012f }
            r1.debug(r3)     // Catch:{ Exception -> 0x012f }
        L_0x012f:
            javax.net.ssl.HostnameVerifier r1 = r8.b     // Catch:{ IOException -> 0x0171 }
            boolean r1 = r1.verify(r10, r0)     // Catch:{ IOException -> 0x0171 }
            if (r1 != 0) goto L_0x0170
            java.security.cert.Certificate[] r0 = r0.getPeerCertificates()     // Catch:{ IOException -> 0x0171 }
            r0 = r0[r2]     // Catch:{ IOException -> 0x0171 }
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0     // Catch:{ IOException -> 0x0171 }
            javax.security.auth.x500.X500Principal r0 = r0.getSubjectX500Principal()     // Catch:{ IOException -> 0x0171 }
            javax.net.ssl.SSLPeerUnverifiedException r1 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ IOException -> 0x0171 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0171 }
            r2.<init>()     // Catch:{ IOException -> 0x0171 }
            java.lang.String r3 = "Host name '"
            r2.append(r3)     // Catch:{ IOException -> 0x0171 }
            r2.append(r10)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r10 = "' does not match "
            r2.append(r10)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r10 = "the certificate subject provided by the peer ("
            r2.append(r10)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r10 = r0.toString()     // Catch:{ IOException -> 0x0171 }
            r2.append(r10)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r10 = ")"
            r2.append(r10)     // Catch:{ IOException -> 0x0171 }
            java.lang.String r10 = r2.toString()     // Catch:{ IOException -> 0x0171 }
            r1.<init>(r10)     // Catch:{ IOException -> 0x0171 }
            throw r1     // Catch:{ IOException -> 0x0171 }
        L_0x0170:
            return
        L_0x0171:
            r10 = move-exception
            r9.close()     // Catch:{ Exception -> 0x0175 }
        L_0x0175:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory.a(javax.net.ssl.SSLSocket, java.lang.String):void");
    }
}
