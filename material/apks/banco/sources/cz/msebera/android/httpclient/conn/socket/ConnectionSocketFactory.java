package cz.msebera.android.httpclient.conn.socket;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.net.InetSocketAddress;
import java.net.Socket;

public interface ConnectionSocketFactory {
    Socket connectSocket(int i, Socket socket, HttpHost httpHost, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpContext httpContext);

    Socket createSocket(HttpContext httpContext);
}
