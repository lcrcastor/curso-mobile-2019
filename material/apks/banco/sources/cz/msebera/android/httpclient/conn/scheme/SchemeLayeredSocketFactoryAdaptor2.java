package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.params.HttpParams;
import java.net.InetSocketAddress;
import java.net.Socket;

@Deprecated
class SchemeLayeredSocketFactoryAdaptor2 implements SchemeLayeredSocketFactory {
    private final LayeredSchemeSocketFactory a;

    SchemeLayeredSocketFactoryAdaptor2(LayeredSchemeSocketFactory layeredSchemeSocketFactory) {
        this.a = layeredSchemeSocketFactory;
    }

    public Socket createSocket(HttpParams httpParams) {
        return this.a.createSocket(httpParams);
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) {
        return this.a.connectSocket(socket, inetSocketAddress, inetSocketAddress2, httpParams);
    }

    public boolean isSecure(Socket socket) {
        return this.a.isSecure(socket);
    }

    public Socket createLayeredSocket(Socket socket, String str, int i, HttpParams httpParams) {
        return this.a.createLayeredSocket(socket, str, i, true);
    }
}
