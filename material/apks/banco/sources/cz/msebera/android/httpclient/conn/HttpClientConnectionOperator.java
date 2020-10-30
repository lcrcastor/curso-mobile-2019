package cz.msebera.android.httpclient.conn;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.net.InetSocketAddress;

public interface HttpClientConnectionOperator {
    void connect(ManagedHttpClientConnection managedHttpClientConnection, HttpHost httpHost, InetSocketAddress inetSocketAddress, int i, SocketConfig socketConfig, HttpContext httpContext);

    void upgrade(ManagedHttpClientConnection managedHttpClientConnection, HttpHost httpHost, HttpContext httpContext);
}
