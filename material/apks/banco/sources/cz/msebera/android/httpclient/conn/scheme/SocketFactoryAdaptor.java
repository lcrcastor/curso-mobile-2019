package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpParams;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

@Deprecated
class SocketFactoryAdaptor implements SocketFactory {
    private final SchemeSocketFactory a;

    SocketFactoryAdaptor(SchemeSocketFactory schemeSocketFactory) {
        this.a = schemeSocketFactory;
    }

    public Socket createSocket() {
        return this.a.createSocket(new BasicHttpParams());
    }

    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) {
        InetSocketAddress inetSocketAddress;
        if (inetAddress != null || i2 > 0) {
            if (i2 <= 0) {
                i2 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i2);
        } else {
            inetSocketAddress = null;
        }
        return this.a.connectSocket(socket, new InetSocketAddress(InetAddress.getByName(str), i), inetSocketAddress, httpParams);
    }

    public boolean isSecure(Socket socket) {
        return this.a.isSecure(socket);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SocketFactoryAdaptor) {
            return this.a.equals(((SocketFactoryAdaptor) obj).a);
        }
        return this.a.equals(obj);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
