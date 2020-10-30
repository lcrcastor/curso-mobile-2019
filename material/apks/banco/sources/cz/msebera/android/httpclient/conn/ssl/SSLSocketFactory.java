package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.HttpInetSocketAddress;
import cz.msebera.android.httpclient.conn.scheme.HostNameResolver;
import cz.msebera.android.httpclient.conn.scheme.LayeredSchemeSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.LayeredSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.SchemeLayeredSocketFactory;
import cz.msebera.android.httpclient.conn.socket.LayeredConnectionSocketFactory;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import cz.msebera.android.httpclient.util.TextUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

@ThreadSafe
@Deprecated
public class SSLSocketFactory implements LayeredSchemeSocketFactory, LayeredSocketFactory, SchemeLayeredSocketFactory, LayeredConnectionSocketFactory {
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    public static final String TLS = "TLS";
    private final javax.net.ssl.SSLSocketFactory a;
    private final HostNameResolver b;
    private volatile X509HostnameVerifier c;
    private final String[] d;
    private final String[] e;

    /* access modifiers changed from: protected */
    public void prepareSocket(SSLSocket sSLSocket) {
    }

    public static SSLSocketFactory getSocketFactory() {
        return new SSLSocketFactory(SSLContexts.createDefault(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    private static String[] a(String str) {
        if (TextUtils.isBlank(str)) {
            return null;
        }
        return str.split(" *, *");
    }

    public static SSLSocketFactory getSystemSocketFactory() {
        return new SSLSocketFactory((javax.net.ssl.SSLSocketFactory) javax.net.ssl.SSLSocketFactory.getDefault(), a(System.getProperty("https.protocols")), a(System.getProperty("https.cipherSuites")), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) {
        this(SSLContexts.custom().useProtocol(str).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, str2 != null ? str2.toCharArray() : null).loadTrustMaterial(keyStore2).build(), hostNameResolver);
    }

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) {
        this(SSLContexts.custom().useProtocol(str).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, str2 != null ? str2.toCharArray() : null).loadTrustMaterial(keyStore2, trustStrategy).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, X509HostnameVerifier x509HostnameVerifier) {
        this(SSLContexts.custom().useProtocol(str).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, str2 != null ? str2.toCharArray() : null).loadTrustMaterial(keyStore2).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(KeyStore keyStore, String str, KeyStore keyStore2) {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, str != null ? str.toCharArray() : null).loadTrustMaterial(keyStore2).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore, String str) {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, str != null ? str.toCharArray() : null).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore) {
        this(SSLContexts.custom().loadTrustMaterial(keyStore).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy) {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext) {
        this(sSLContext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext, HostNameResolver hostNameResolver) {
        this.a = sSLContext.getSocketFactory();
        this.c = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.b = hostNameResolver;
        this.d = null;
        this.e = null;
    }

    public SSLSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), (String[]) null, (String[]) null, x509HostnameVerifier);
    }

    public SSLSocketFactory(SSLContext sSLContext, String[] strArr, String[] strArr2, X509HostnameVerifier x509HostnameVerifier) {
        this(((SSLContext) Args.notNull(sSLContext, "SSL context")).getSocketFactory(), strArr, strArr2, x509HostnameVerifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, (String[]) null, (String[]) null, x509HostnameVerifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory, String[] strArr, String[] strArr2, X509HostnameVerifier x509HostnameVerifier) {
        this.a = (javax.net.ssl.SSLSocketFactory) Args.notNull(sSLSocketFactory, "SSL socket factory");
        this.d = strArr;
        this.e = strArr2;
        if (x509HostnameVerifier == null) {
            x509HostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        }
        this.c = x509HostnameVerifier;
        this.b = null;
    }

    public Socket createSocket(HttpParams httpParams) {
        return createSocket((HttpContext) null);
    }

    public Socket createSocket() {
        return createSocket((HttpContext) null);
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) {
        HttpHost httpHost;
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        if (inetSocketAddress instanceof HttpInetSocketAddress) {
            httpHost = ((HttpInetSocketAddress) inetSocketAddress).getHttpHost();
        } else {
            httpHost = new HttpHost(inetSocketAddress.getHostName(), inetSocketAddress.getPort(), "https");
        }
        HttpHost httpHost2 = httpHost;
        int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        socket.setSoTimeout(soTimeout);
        return connectSocket(connectionTimeout, socket, httpHost2, inetSocketAddress, inetSocketAddress2, (HttpContext) null);
    }

    public boolean isSecure(Socket socket) {
        Args.notNull(socket, "Socket");
        Asserts.check(socket instanceof SSLSocket, "Socket not created by this factory");
        Asserts.check(!socket.isClosed(), "Socket is closed");
        return true;
    }

    public Socket createLayeredSocket(Socket socket, String str, int i, HttpParams httpParams) {
        return createLayeredSocket(socket, str, i, (HttpContext) null);
    }

    public Socket createLayeredSocket(Socket socket, String str, int i, boolean z) {
        return createLayeredSocket(socket, str, i, (HttpContext) null);
    }

    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        Args.notNull(x509HostnameVerifier, "Hostname verifier");
        this.c = x509HostnameVerifier;
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.c;
    }

    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) {
        InetAddress inetAddress2;
        if (this.b != null) {
            inetAddress2 = this.b.resolve(str);
        } else {
            inetAddress2 = InetAddress.getByName(str);
        }
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || i2 > 0) {
            if (i2 <= 0) {
                i2 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i2);
        }
        return connectSocket(socket, new HttpInetSocketAddress(new HttpHost(str, i), inetAddress2, i), inetSocketAddress, httpParams);
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return createLayeredSocket(socket, str, i, z);
    }

    private void a(SSLSocket sSLSocket) {
        if (this.d != null) {
            sSLSocket.setEnabledProtocols(this.d);
        }
        if (this.e != null) {
            sSLSocket.setEnabledCipherSuites(this.e);
        }
        prepareSocket(sSLSocket);
    }

    public Socket createSocket(HttpContext httpContext) {
        SSLSocket sSLSocket = (SSLSocket) this.a.createSocket();
        a(sSLSocket);
        return sSLSocket;
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
        try {
            socket.connect(inetSocketAddress, i);
            if (!(socket instanceof SSLSocket)) {
                return createLayeredSocket(socket, httpHost.getHostName(), inetSocketAddress.getPort(), httpContext);
            }
            SSLSocket sSLSocket = (SSLSocket) socket;
            sSLSocket.startHandshake();
            a(sSLSocket, httpHost.getHostName());
            return socket;
        } catch (IOException e2) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
            throw e2;
        }
    }

    public Socket createLayeredSocket(Socket socket, String str, int i, HttpContext httpContext) {
        SSLSocket sSLSocket = (SSLSocket) this.a.createSocket(socket, str, i, true);
        a(sSLSocket);
        sSLSocket.startHandshake();
        a(sSLSocket, str);
        return sSLSocket;
    }

    private void a(SSLSocket sSLSocket, String str) {
        try {
            this.c.verify(str, sSLSocket);
        } catch (IOException e2) {
            try {
                sSLSocket.close();
            } catch (Exception unused) {
            }
            throw e2;
        }
    }
}
