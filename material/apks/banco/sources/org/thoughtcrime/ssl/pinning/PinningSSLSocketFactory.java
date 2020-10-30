package org.thoughtcrime.ssl.pinning;

import android.content.Context;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class PinningSSLSocketFactory extends SSLSocketFactory {
    private final javax.net.ssl.SSLSocketFactory a;

    public PinningSSLSocketFactory(Context context, String[] strArr, long j) {
        super(null);
        SystemKeyStore instance = SystemKeyStore.getInstance(context);
        SSLContext instance2 = SSLContext.getInstance("TLS");
        instance2.init(null, a(instance, strArr, j), null);
        this.a = instance2.getSocketFactory();
    }

    public Socket createSocket() {
        return this.a.createSocket();
    }

    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) {
        if (socket == null) {
            socket = createSocket();
        }
        SSLSocket sSLSocket = (SSLSocket) socket;
        if (inetAddress != null || i2 > 0) {
            if (i2 < 0) {
                i2 = 0;
            }
            sSLSocket.bind(new InetSocketAddress(inetAddress, i2));
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
        sSLSocket.connect(new InetSocketAddress(str, i), connectionTimeout);
        sSLSocket.setSoTimeout(soTimeout);
        try {
            SSLSocketFactory.STRICT_HOSTNAME_VERIFIER.verify(str, sSLSocket);
            return sSLSocket;
        } catch (IOException e) {
            try {
                sSLSocket.close();
            } catch (Exception unused) {
            }
            throw e;
        }
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        if (i == -1) {
            i = 443;
        }
        SSLSocket sSLSocket = (SSLSocket) this.a.createSocket(socket, str, i, z);
        SSLSocketFactory.STRICT_HOSTNAME_VERIFIER.verify(str, sSLSocket);
        return sSLSocket;
    }

    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        throw new IllegalArgumentException("Only strict hostname verification (default)  is supported!");
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
    }

    private TrustManager[] a(SystemKeyStore systemKeyStore, String[] strArr, long j) {
        return new TrustManager[]{new PinningTrustManager(systemKeyStore, strArr, j)};
    }
}
