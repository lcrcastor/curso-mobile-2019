package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.SocketConnector;
import com.squareup.okhttp.internal.http.SocketConnector.ConnectedSocket;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyConnection.Builder;
import io.reactivex.annotations.SchedulerSupport;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.util.List;
import okio.BufferedSink;
import okio.BufferedSource;

public final class Connection {
    private final ConnectionPool a;
    private final Route b;
    private Socket c;
    private boolean d = false;
    private HttpConnection e;
    private SpdyConnection f;
    private Protocol g = Protocol.HTTP_1_1;
    private long h;
    private Handshake i;
    private int j;
    private Object k;

    public Connection(ConnectionPool connectionPool, Route route) {
        this.a = connectionPool;
        this.b = route;
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        if (!j()) {
            synchronized (this.a) {
                if (this.k != null) {
                    throw new IllegalStateException("Connection already has an owner!");
                }
                this.k = obj;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        synchronized (this.a) {
            if (this.k == null) {
                return false;
            }
            this.k = null;
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Object obj) {
        if (j()) {
            throw new IllegalStateException();
        }
        synchronized (this.a) {
            if (this.k == obj) {
                this.k = null;
                this.c.close();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3, int i4, Request request, List<ConnectionSpec> list, boolean z) {
        ConnectedSocket connectedSocket;
        if (this.d) {
            throw new IllegalStateException("already connected");
        }
        SocketConnector socketConnector = new SocketConnector(this, this.a);
        if (this.b.a.getSslSocketFactory() != null) {
            connectedSocket = socketConnector.connectTls(i2, i3, i4, request, this.b, list, z);
        } else if (!list.contains(ConnectionSpec.CLEARTEXT)) {
            StringBuilder sb = new StringBuilder();
            sb.append("CLEARTEXT communication not supported: ");
            sb.append(list);
            throw new RouteException(new UnknownServiceException(sb.toString()));
        } else {
            connectedSocket = socketConnector.connectCleartext(i2, i3, this.b);
        }
        this.c = connectedSocket.socket;
        this.i = connectedSocket.handshake;
        this.g = connectedSocket.alpnProtocol == null ? Protocol.HTTP_1_1 : connectedSocket.alpnProtocol;
        try {
            if (this.g != Protocol.SPDY_3) {
                if (this.g != Protocol.HTTP_2) {
                    this.e = new HttpConnection(this.a, this, this.c);
                    this.d = true;
                }
            }
            this.c.setSoTimeout(0);
            this.f = new Builder(this.b.a.b, true, this.c).protocol(this.g).build();
            this.f.sendConnectionPreface();
            this.d = true;
        } catch (IOException e2) {
            throw new RouteException(e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(OkHttpClient okHttpClient, Object obj, Request request) {
        a(obj);
        if (!b()) {
            Request request2 = request;
            a(okHttpClient.getConnectTimeout(), okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout(), request2, this.b.a.getConnectionSpecs(), okHttpClient.getRetryOnConnectionFailure());
            if (j()) {
                okHttpClient.getConnectionPool().b(this);
            }
            okHttpClient.b().connected(getRoute());
        }
        a(okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout());
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.d;
    }

    public Route getRoute() {
        return this.b;
    }

    public Socket getSocket() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public BufferedSource c() {
        if (this.e != null) {
            return this.e.rawSource();
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public BufferedSink d() {
        if (this.e != null) {
            return this.e.rawSink();
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return !this.c.isClosed() && !this.c.isInputShutdown() && !this.c.isOutputShutdown();
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        if (this.e != null) {
            return this.e.isReadable();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        if (this.f != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.h = System.nanoTime();
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        return this.f == null || this.f.isIdle();
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return this.f == null ? this.h : this.f.getIdleStartTimeNs();
    }

    public Handshake getHandshake() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public Transport a(HttpEngine httpEngine) {
        return this.f != null ? new SpdyTransport(httpEngine, this.f) : new HttpTransport(httpEngine, this.e);
    }

    /* access modifiers changed from: 0000 */
    public boolean j() {
        return this.f != null;
    }

    public Protocol getProtocol() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public void a(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.g = protocol;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3) {
        if (!this.d) {
            throw new IllegalStateException("setTimeouts - not connected");
        } else if (this.e != null) {
            try {
                this.c.setSoTimeout(i2);
                this.e.setTimeouts(i2, i3);
            } catch (IOException e2) {
                throw new RouteException(e2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void k() {
        this.j++;
    }

    /* access modifiers changed from: 0000 */
    public int l() {
        return this.j;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.b.a.b);
        sb.append(":");
        sb.append(this.b.a.c);
        sb.append(", proxy=");
        sb.append(this.b.b);
        sb.append(" hostAddress=");
        sb.append(this.b.c.getAddress().getHostAddress());
        sb.append(" cipherSuite=");
        sb.append(this.i != null ? this.i.cipherSuite() : SchedulerSupport.NONE);
        sb.append(" protocol=");
        sb.append(this.g);
        sb.append('}');
        return sb.toString();
    }
}
