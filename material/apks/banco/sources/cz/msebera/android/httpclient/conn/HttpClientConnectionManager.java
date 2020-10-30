package cz.msebera.android.httpclient.conn;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.util.concurrent.TimeUnit;

public interface HttpClientConnectionManager {
    void closeExpiredConnections();

    void closeIdleConnections(long j, TimeUnit timeUnit);

    void connect(HttpClientConnection httpClientConnection, HttpRoute httpRoute, int i, HttpContext httpContext);

    void releaseConnection(HttpClientConnection httpClientConnection, Object obj, long j, TimeUnit timeUnit);

    ConnectionRequest requestConnection(HttpRoute httpRoute, Object obj);

    void routeComplete(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext);

    void shutdown();

    void upgrade(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext);
}
