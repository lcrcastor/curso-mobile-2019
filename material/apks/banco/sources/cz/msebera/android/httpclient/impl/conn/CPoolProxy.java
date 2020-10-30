package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpConnectionMetrics;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSession;

@NotThreadSafe
class CPoolProxy implements ManagedHttpClientConnection, HttpContext {
    private volatile CPoolEntry a;

    CPoolProxy(CPoolEntry cPoolEntry) {
        this.a = cPoolEntry;
    }

    /* access modifiers changed from: 0000 */
    public CPoolEntry a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public CPoolEntry b() {
        CPoolEntry cPoolEntry = this.a;
        this.a = null;
        return cPoolEntry;
    }

    /* access modifiers changed from: 0000 */
    public ManagedHttpClientConnection c() {
        CPoolEntry cPoolEntry = this.a;
        if (cPoolEntry == null) {
            return null;
        }
        return (ManagedHttpClientConnection) cPoolEntry.getConnection();
    }

    /* access modifiers changed from: 0000 */
    public ManagedHttpClientConnection d() {
        ManagedHttpClientConnection c = c();
        if (c != null) {
            return c;
        }
        throw new ConnectionShutdownException();
    }

    public void close() {
        CPoolEntry cPoolEntry = this.a;
        if (cPoolEntry != null) {
            cPoolEntry.c();
        }
    }

    public void shutdown() {
        CPoolEntry cPoolEntry = this.a;
        if (cPoolEntry != null) {
            cPoolEntry.d();
        }
    }

    public boolean isOpen() {
        CPoolEntry cPoolEntry = this.a;
        if (cPoolEntry != null) {
            return !cPoolEntry.isClosed();
        }
        return false;
    }

    public boolean isStale() {
        ManagedHttpClientConnection c = c();
        if (c != null) {
            return c.isStale();
        }
        return true;
    }

    public void setSocketTimeout(int i) {
        d().setSocketTimeout(i);
    }

    public int getSocketTimeout() {
        return d().getSocketTimeout();
    }

    public String getId() {
        return d().getId();
    }

    public void bind(Socket socket) {
        d().bind(socket);
    }

    public Socket getSocket() {
        return d().getSocket();
    }

    public SSLSession getSSLSession() {
        return d().getSSLSession();
    }

    public boolean isResponseAvailable(int i) {
        return d().isResponseAvailable(i);
    }

    public void sendRequestHeader(HttpRequest httpRequest) {
        d().sendRequestHeader(httpRequest);
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        d().sendRequestEntity(httpEntityEnclosingRequest);
    }

    public HttpResponse receiveResponseHeader() {
        return d().receiveResponseHeader();
    }

    public void receiveResponseEntity(HttpResponse httpResponse) {
        d().receiveResponseEntity(httpResponse);
    }

    public void flush() {
        d().flush();
    }

    public HttpConnectionMetrics getMetrics() {
        return d().getMetrics();
    }

    public InetAddress getLocalAddress() {
        return d().getLocalAddress();
    }

    public int getLocalPort() {
        return d().getLocalPort();
    }

    public InetAddress getRemoteAddress() {
        return d().getRemoteAddress();
    }

    public int getRemotePort() {
        return d().getRemotePort();
    }

    public Object getAttribute(String str) {
        ManagedHttpClientConnection d = d();
        if (d instanceof HttpContext) {
            return ((HttpContext) d).getAttribute(str);
        }
        return null;
    }

    public void setAttribute(String str, Object obj) {
        ManagedHttpClientConnection d = d();
        if (d instanceof HttpContext) {
            ((HttpContext) d).setAttribute(str, obj);
        }
    }

    public Object removeAttribute(String str) {
        ManagedHttpClientConnection d = d();
        if (d instanceof HttpContext) {
            return ((HttpContext) d).removeAttribute(str);
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CPoolProxy{");
        ManagedHttpClientConnection c = c();
        if (c != null) {
            sb.append(c);
        } else {
            sb.append("detached");
        }
        sb.append('}');
        return sb.toString();
    }

    public static HttpClientConnection a(CPoolEntry cPoolEntry) {
        return new CPoolProxy(cPoolEntry);
    }

    private static CPoolProxy c(HttpClientConnection httpClientConnection) {
        if (CPoolProxy.class.isInstance(httpClientConnection)) {
            return (CPoolProxy) CPoolProxy.class.cast(httpClientConnection);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected connection proxy class: ");
        sb.append(httpClientConnection.getClass());
        throw new IllegalStateException(sb.toString());
    }

    public static CPoolEntry a(HttpClientConnection httpClientConnection) {
        CPoolEntry a2 = c(httpClientConnection).a();
        if (a2 != null) {
            return a2;
        }
        throw new ConnectionShutdownException();
    }

    public static CPoolEntry b(HttpClientConnection httpClientConnection) {
        return c(httpClientConnection).b();
    }
}
