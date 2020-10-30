package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.params.HttpParams;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

@Deprecated
class SchemeSocketFactoryAdaptor implements SchemeSocketFactory {
    private final SocketFactory a;

    SchemeSocketFactoryAdaptor(SocketFactory socketFactory) {
        this.a = socketFactory;
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) {
        int i;
        InetAddress inetAddress;
        String hostName = inetSocketAddress.getHostName();
        int port = inetSocketAddress.getPort();
        if (inetSocketAddress2 != null) {
            inetAddress = inetSocketAddress2.getAddress();
            i = inetSocketAddress2.getPort();
        } else {
            inetAddress = null;
            i = 0;
        }
        return this.a.connectSocket(socket, hostName, port, inetAddress, i, httpParams);
    }

    public Socket createSocket(HttpParams httpParams) {
        return this.a.createSocket();
    }

    public boolean isSecure(Socket socket) {
        return this.a.isSecure(socket);
    }

    public SocketFactory a() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SchemeSocketFactoryAdaptor) {
            return this.a.equals(((SchemeSocketFactoryAdaptor) obj).a);
        }
        return this.a.equals(obj);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
