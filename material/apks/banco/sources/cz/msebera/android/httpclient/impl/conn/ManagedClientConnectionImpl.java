package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpConnectionMetrics;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.conn.ClientConnectionOperator;
import cz.msebera.android.httpclient.conn.ManagedClientConnection;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

@NotThreadSafe
@Deprecated
class ManagedClientConnectionImpl implements ManagedClientConnection {
    private final ClientConnectionManager a;
    private final ClientConnectionOperator b;
    private volatile HttpPoolEntry c;
    private volatile boolean d = false;
    private volatile long e = Long.MAX_VALUE;

    public String getId() {
        return null;
    }

    ManagedClientConnectionImpl(ClientConnectionManager clientConnectionManager, ClientConnectionOperator clientConnectionOperator, HttpPoolEntry httpPoolEntry) {
        Args.notNull(clientConnectionManager, "Connection manager");
        Args.notNull(clientConnectionOperator, "Connection operator");
        Args.notNull(httpPoolEntry, "HTTP pool entry");
        this.a = clientConnectionManager;
        this.b = clientConnectionOperator;
        this.c = httpPoolEntry;
    }

    /* access modifiers changed from: 0000 */
    public HttpPoolEntry a() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public HttpPoolEntry b() {
        HttpPoolEntry httpPoolEntry = this.c;
        this.c = null;
        return httpPoolEntry;
    }

    public ClientConnectionManager c() {
        return this.a;
    }

    private OperatedClientConnection d() {
        HttpPoolEntry httpPoolEntry = this.c;
        if (httpPoolEntry == null) {
            return null;
        }
        return (OperatedClientConnection) httpPoolEntry.getConnection();
    }

    private OperatedClientConnection e() {
        HttpPoolEntry httpPoolEntry = this.c;
        if (httpPoolEntry != null) {
            return (OperatedClientConnection) httpPoolEntry.getConnection();
        }
        throw new ConnectionShutdownException();
    }

    private HttpPoolEntry f() {
        HttpPoolEntry httpPoolEntry = this.c;
        if (httpPoolEntry != null) {
            return httpPoolEntry;
        }
        throw new ConnectionShutdownException();
    }

    public void close() {
        HttpPoolEntry httpPoolEntry = this.c;
        if (httpPoolEntry != null) {
            OperatedClientConnection operatedClientConnection = (OperatedClientConnection) httpPoolEntry.getConnection();
            httpPoolEntry.a().reset();
            operatedClientConnection.close();
        }
    }

    public void shutdown() {
        HttpPoolEntry httpPoolEntry = this.c;
        if (httpPoolEntry != null) {
            OperatedClientConnection operatedClientConnection = (OperatedClientConnection) httpPoolEntry.getConnection();
            httpPoolEntry.a().reset();
            operatedClientConnection.shutdown();
        }
    }

    public boolean isOpen() {
        OperatedClientConnection d2 = d();
        if (d2 != null) {
            return d2.isOpen();
        }
        return false;
    }

    public boolean isStale() {
        OperatedClientConnection d2 = d();
        if (d2 != null) {
            return d2.isStale();
        }
        return true;
    }

    public void setSocketTimeout(int i) {
        e().setSocketTimeout(i);
    }

    public int getSocketTimeout() {
        return e().getSocketTimeout();
    }

    public HttpConnectionMetrics getMetrics() {
        return e().getMetrics();
    }

    public void flush() {
        e().flush();
    }

    public boolean isResponseAvailable(int i) {
        return e().isResponseAvailable(i);
    }

    public void receiveResponseEntity(HttpResponse httpResponse) {
        e().receiveResponseEntity(httpResponse);
    }

    public HttpResponse receiveResponseHeader() {
        return e().receiveResponseHeader();
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        e().sendRequestEntity(httpEntityEnclosingRequest);
    }

    public void sendRequestHeader(HttpRequest httpRequest) {
        e().sendRequestHeader(httpRequest);
    }

    public InetAddress getLocalAddress() {
        return e().getLocalAddress();
    }

    public int getLocalPort() {
        return e().getLocalPort();
    }

    public InetAddress getRemoteAddress() {
        return e().getRemoteAddress();
    }

    public int getRemotePort() {
        return e().getRemotePort();
    }

    public boolean isSecure() {
        return e().isSecure();
    }

    public void bind(Socket socket) {
        throw new UnsupportedOperationException();
    }

    public Socket getSocket() {
        return e().getSocket();
    }

    public SSLSession getSSLSession() {
        Socket socket = e().getSocket();
        if (socket instanceof SSLSocket) {
            return ((SSLSocket) socket).getSession();
        }
        return null;
    }

    public HttpRoute getRoute() {
        return f().c();
    }

    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) {
        OperatedClientConnection operatedClientConnection;
        HttpHost httpHost;
        Args.notNull(httpRoute, "Route");
        Args.notNull(httpParams, "HTTP parameters");
        synchronized (this) {
            if (this.c == null) {
                throw new ConnectionShutdownException();
            }
            RouteTracker a2 = this.c.a();
            Asserts.notNull(a2, "Route tracker");
            Asserts.check(!a2.isConnected(), "Connection already open");
            operatedClientConnection = (OperatedClientConnection) this.c.getConnection();
        }
        HttpHost proxyHost = httpRoute.getProxyHost();
        ClientConnectionOperator clientConnectionOperator = this.b;
        if (proxyHost != null) {
            httpHost = proxyHost;
        } else {
            httpHost = httpRoute.getTargetHost();
        }
        clientConnectionOperator.openConnection(operatedClientConnection, httpHost, httpRoute.getLocalAddress(), httpContext, httpParams);
        synchronized (this) {
            if (this.c == null) {
                throw new InterruptedIOException();
            }
            RouteTracker a3 = this.c.a();
            if (proxyHost == null) {
                a3.connectTarget(operatedClientConnection.isSecure());
            } else {
                a3.connectProxy(proxyHost, operatedClientConnection.isSecure());
            }
        }
    }

    public void tunnelTarget(boolean z, HttpParams httpParams) {
        HttpHost targetHost;
        OperatedClientConnection operatedClientConnection;
        Args.notNull(httpParams, "HTTP parameters");
        synchronized (this) {
            if (this.c == null) {
                throw new ConnectionShutdownException();
            }
            RouteTracker a2 = this.c.a();
            Asserts.notNull(a2, "Route tracker");
            Asserts.check(a2.isConnected(), "Connection not open");
            Asserts.check(!a2.isTunnelled(), "Connection is already tunnelled");
            targetHost = a2.getTargetHost();
            operatedClientConnection = (OperatedClientConnection) this.c.getConnection();
        }
        operatedClientConnection.update(null, targetHost, z, httpParams);
        synchronized (this) {
            if (this.c == null) {
                throw new InterruptedIOException();
            }
            this.c.a().tunnelTarget(z);
        }
    }

    public void tunnelProxy(HttpHost httpHost, boolean z, HttpParams httpParams) {
        OperatedClientConnection operatedClientConnection;
        Args.notNull(httpHost, "Next proxy");
        Args.notNull(httpParams, "HTTP parameters");
        synchronized (this) {
            if (this.c == null) {
                throw new ConnectionShutdownException();
            }
            RouteTracker a2 = this.c.a();
            Asserts.notNull(a2, "Route tracker");
            Asserts.check(a2.isConnected(), "Connection not open");
            operatedClientConnection = (OperatedClientConnection) this.c.getConnection();
        }
        operatedClientConnection.update(null, httpHost, z, httpParams);
        synchronized (this) {
            if (this.c == null) {
                throw new InterruptedIOException();
            }
            this.c.a().tunnelProxy(httpHost, z);
        }
    }

    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) {
        HttpHost targetHost;
        OperatedClientConnection operatedClientConnection;
        Args.notNull(httpParams, "HTTP parameters");
        synchronized (this) {
            if (this.c == null) {
                throw new ConnectionShutdownException();
            }
            RouteTracker a2 = this.c.a();
            Asserts.notNull(a2, "Route tracker");
            Asserts.check(a2.isConnected(), "Connection not open");
            Asserts.check(a2.isTunnelled(), "Protocol layering without a tunnel not supported");
            Asserts.check(!a2.isLayered(), "Multiple protocol layering not supported");
            targetHost = a2.getTargetHost();
            operatedClientConnection = (OperatedClientConnection) this.c.getConnection();
        }
        this.b.updateSecureConnection(operatedClientConnection, targetHost, httpContext, httpParams);
        synchronized (this) {
            if (this.c == null) {
                throw new InterruptedIOException();
            }
            this.c.a().layerProtocol(operatedClientConnection.isSecure());
        }
    }

    public Object getState() {
        return f().getState();
    }

    public void setState(Object obj) {
        f().setState(obj);
    }

    public void markReusable() {
        this.d = true;
    }

    public void unmarkReusable() {
        this.d = false;
    }

    public boolean isMarkedReusable() {
        return this.d;
    }

    public void setIdleDuration(long j, TimeUnit timeUnit) {
        if (j > 0) {
            this.e = timeUnit.toMillis(j);
        } else {
            this.e = -1;
        }
    }

    public void releaseConnection() {
        synchronized (this) {
            if (this.c != null) {
                this.a.releaseConnection(this, this.e, TimeUnit.MILLISECONDS);
                this.c = null;
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:6|7|8|9|10|11|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void abortConnection() {
        /*
            r4 = this;
            monitor-enter(r4)
            cz.msebera.android.httpclient.impl.conn.HttpPoolEntry r0 = r4.c     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            return
        L_0x0007:
            r0 = 0
            r4.d = r0     // Catch:{ all -> 0x0023 }
            cz.msebera.android.httpclient.impl.conn.HttpPoolEntry r0 = r4.c     // Catch:{ all -> 0x0023 }
            java.lang.Object r0 = r0.getConnection()     // Catch:{ all -> 0x0023 }
            cz.msebera.android.httpclient.conn.OperatedClientConnection r0 = (cz.msebera.android.httpclient.conn.OperatedClientConnection) r0     // Catch:{ all -> 0x0023 }
            r0.shutdown()     // Catch:{ IOException -> 0x0015 }
        L_0x0015:
            cz.msebera.android.httpclient.conn.ClientConnectionManager r0 = r4.a     // Catch:{ all -> 0x0023 }
            long r1 = r4.e     // Catch:{ all -> 0x0023 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0023 }
            r0.releaseConnection(r4, r1, r3)     // Catch:{ all -> 0x0023 }
            r0 = 0
            r4.c = r0     // Catch:{ all -> 0x0023 }
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            return
        L_0x0023:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.conn.ManagedClientConnectionImpl.abortConnection():void");
    }
}
