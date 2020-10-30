package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Immutable
@Deprecated
public class PlainSocketFactory implements SchemeSocketFactory, SocketFactory {
    private final HostNameResolver a;

    public final boolean isSecure(Socket socket) {
        return false;
    }

    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
    }

    @Deprecated
    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.a = hostNameResolver;
    }

    public PlainSocketFactory() {
        this.a = null;
    }

    public Socket createSocket(HttpParams httpParams) {
        return new Socket();
    }

    public Socket createSocket() {
        return new Socket();
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        if (socket == null) {
            socket = createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket.bind(inetSocketAddress2);
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        try {
            socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
            socket.connect(inetSocketAddress, connectionTimeout);
            return socket;
        } catch (SocketTimeoutException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Connect to ");
            sb.append(inetSocketAddress);
            sb.append(" timed out");
            throw new ConnectTimeoutException(sb.toString());
        }
    }

    @Deprecated
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) {
        InetSocketAddress inetSocketAddress;
        InetAddress inetAddress2;
        if (inetAddress != null || i2 > 0) {
            if (i2 <= 0) {
                i2 = 0;
            }
            inetSocketAddress = new InetSocketAddress(inetAddress, i2);
        } else {
            inetSocketAddress = null;
        }
        if (this.a != null) {
            inetAddress2 = this.a.resolve(str);
        } else {
            inetAddress2 = InetAddress.getByName(str);
        }
        return connectSocket(socket, new InetSocketAddress(inetAddress2, i), inetSocketAddress, httpParams);
    }
}
