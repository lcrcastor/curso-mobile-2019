package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteSelector.Selection;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

public final class StreamAllocation {
    static final /* synthetic */ boolean a = true;
    public final Address address;
    private Selection b;
    private Route c;
    public final Call call;
    private final ConnectionPool d;
    private final Object e;
    public final EventListener eventListener;
    private final RouteSelector f;
    private int g;
    private RealConnection h;
    private boolean i;
    private boolean j;
    private boolean k;
    private HttpCodec l;

    public static final class StreamAllocationReference extends WeakReference<StreamAllocation> {
        public final Object callStackTrace;

        StreamAllocationReference(StreamAllocation streamAllocation, Object obj) {
            super(streamAllocation);
            this.callStackTrace = obj;
        }
    }

    public StreamAllocation(ConnectionPool connectionPool, Address address2, Call call2, EventListener eventListener2, Object obj) {
        this.d = connectionPool;
        this.address = address2;
        this.call = call2;
        this.eventListener = eventListener2;
        this.f = new RouteSelector(address2, b(), call2, eventListener2);
        this.e = obj;
    }

    public HttpCodec newStream(OkHttpClient okHttpClient, Chain chain, boolean z) {
        try {
            HttpCodec newCodec = a(chain.connectTimeoutMillis(), chain.readTimeoutMillis(), chain.writeTimeoutMillis(), okHttpClient.pingIntervalMillis(), okHttpClient.retryOnConnectionFailure(), z).newCodec(okHttpClient, chain, this);
            synchronized (this.d) {
                this.l = newCodec;
            }
            return newCodec;
        } catch (IOException e2) {
            throw new RouteException(e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0.isHealthy(r9) != false) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.connection.RealConnection a(int r4, int r5, int r6, int r7, boolean r8, boolean r9) {
        /*
            r3 = this;
        L_0x0000:
            okhttp3.internal.connection.RealConnection r0 = r3.a(r4, r5, r6, r7, r8)
            okhttp3.ConnectionPool r1 = r3.d
            monitor-enter(r1)
            int r2 = r0.successCount     // Catch:{ all -> 0x0019 }
            if (r2 != 0) goto L_0x000d
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            return r0
        L_0x000d:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            boolean r1 = r0.isHealthy(r9)
            if (r1 != 0) goto L_0x0018
            r3.noNewStreams()
            goto L_0x0000
        L_0x0018:
            return r0
        L_0x0019:
            r4 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.StreamAllocation.a(int, int, int, int, boolean, boolean):okhttp3.internal.connection.RealConnection");
    }

    /* JADX INFO: finally extract failed */
    private RealConnection a(int i2, int i3, int i4, int i5, boolean z) {
        Socket a2;
        Socket socket;
        RealConnection realConnection;
        RealConnection realConnection2;
        Route route;
        RealConnection realConnection3;
        boolean z2;
        boolean z3;
        synchronized (this.d) {
            try {
                if (this.j) {
                    throw new IllegalStateException("released");
                } else if (this.l != null) {
                    throw new IllegalStateException("codec != null");
                } else if (this.k) {
                    throw new IOException("Canceled");
                } else {
                    RealConnection realConnection4 = this.h;
                    a2 = a();
                    socket = null;
                    if (this.h != null) {
                        realConnection2 = this.h;
                        realConnection = null;
                    } else {
                        realConnection = realConnection4;
                        realConnection2 = null;
                    }
                    if (!this.i) {
                        realConnection = null;
                    }
                    if (realConnection2 == null) {
                        Internal.instance.get(this.d, this.address, this, null);
                        if (this.h != null) {
                            realConnection3 = this.h;
                            route = null;
                            z2 = true;
                        } else {
                            route = this.c;
                            realConnection3 = realConnection2;
                        }
                    } else {
                        realConnection3 = realConnection2;
                        route = null;
                    }
                    z2 = false;
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        Util.closeQuietly(a2);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (z2) {
            this.eventListener.connectionAcquired(this.call, realConnection3);
        }
        if (realConnection3 != null) {
            return realConnection3;
        }
        if (route != null || (this.b != null && this.b.hasNext())) {
            z3 = false;
        } else {
            this.b = this.f.next();
            z3 = true;
        }
        synchronized (this.d) {
            try {
                if (this.k) {
                    throw new IOException("Canceled");
                }
                if (z3) {
                    List all = this.b.getAll();
                    int size = all.size();
                    int i6 = 0;
                    while (true) {
                        if (i6 >= size) {
                            break;
                        }
                        Route route2 = (Route) all.get(i6);
                        Internal.instance.get(this.d, this.address, this, route2);
                        if (this.h != null) {
                            realConnection3 = this.h;
                            this.c = route2;
                            z2 = true;
                            break;
                        }
                        i6++;
                    }
                }
                if (!z2) {
                    if (route == null) {
                        route = this.b.next();
                    }
                    this.c = route;
                    this.g = 0;
                    realConnection3 = new RealConnection(this.d, route);
                    acquire(realConnection3, false);
                }
            } catch (Throwable th2) {
                while (true) {
                    throw th2;
                }
            }
        }
        if (z2) {
            this.eventListener.connectionAcquired(this.call, realConnection3);
            return realConnection3;
        }
        realConnection3.connect(i2, i3, i4, i5, z, this.call, this.eventListener);
        b().connected(realConnection3.route());
        synchronized (this.d) {
            try {
                this.i = true;
                Internal.instance.put(this.d, realConnection3);
                if (realConnection3.isMultiplexed()) {
                    socket = Internal.instance.deduplicate(this.d, this.address, this);
                    realConnection3 = this.h;
                }
            } catch (Throwable th3) {
                while (true) {
                    throw th3;
                }
            }
        }
        Util.closeQuietly(socket);
        this.eventListener.connectionAcquired(this.call, realConnection3);
        return realConnection3;
    }

    private Socket a() {
        if (a || Thread.holdsLock(this.d)) {
            RealConnection realConnection = this.h;
            if (realConnection == null || !realConnection.noNewStreams) {
                return null;
            }
            return a(false, false, true);
        }
        throw new AssertionError();
    }

    public void streamFinished(boolean z, HttpCodec httpCodec, long j2, IOException iOException) {
        RealConnection realConnection;
        Socket a2;
        boolean z2;
        this.eventListener.responseBodyEnd(this.call, j2);
        synchronized (this.d) {
            if (httpCodec != null) {
                if (httpCodec == this.l) {
                    if (!z) {
                        this.h.successCount++;
                    }
                    realConnection = this.h;
                    a2 = a(z, false, true);
                    if (this.h != null) {
                        realConnection = null;
                    }
                    z2 = this.j;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("expected ");
            sb.append(this.l);
            sb.append(" but was ");
            sb.append(httpCodec);
            throw new IllegalStateException(sb.toString());
        }
        Util.closeQuietly(a2);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (iOException != null) {
            this.eventListener.callFailed(this.call, iOException);
        } else if (z2) {
            this.eventListener.callEnd(this.call);
        }
    }

    public HttpCodec codec() {
        HttpCodec httpCodec;
        synchronized (this.d) {
            httpCodec = this.l;
        }
        return httpCodec;
    }

    private RouteDatabase b() {
        return Internal.instance.routeDatabase(this.d);
    }

    public Route route() {
        return this.c;
    }

    public synchronized RealConnection connection() {
        return this.h;
    }

    public void release() {
        RealConnection realConnection;
        Socket a2;
        synchronized (this.d) {
            realConnection = this.h;
            a2 = a(false, true, false);
            if (this.h != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(a2);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
            this.eventListener.callEnd(this.call);
        }
    }

    public void noNewStreams() {
        RealConnection realConnection;
        Socket a2;
        synchronized (this.d) {
            realConnection = this.h;
            a2 = a(true, false, false);
            if (this.h != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(a2);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    private Socket a(boolean z, boolean z2, boolean z3) {
        Socket socket;
        if (a || Thread.holdsLock(this.d)) {
            if (z3) {
                this.l = null;
            }
            if (z2) {
                this.j = true;
            }
            if (this.h != null) {
                if (z) {
                    this.h.noNewStreams = true;
                }
                if (this.l == null && (this.j || this.h.noNewStreams)) {
                    a(this.h);
                    if (this.h.allocations.isEmpty()) {
                        this.h.idleAtNanos = System.nanoTime();
                        if (Internal.instance.connectionBecameIdle(this.d, this.h)) {
                            socket = this.h.socket();
                            this.h = null;
                            return socket;
                        }
                    }
                    socket = null;
                    this.h = null;
                    return socket;
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    public void cancel() {
        HttpCodec httpCodec;
        RealConnection realConnection;
        synchronized (this.d) {
            this.k = true;
            httpCodec = this.l;
            realConnection = this.h;
        }
        if (httpCodec != null) {
            httpCodec.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    public void streamFailed(IOException iOException) {
        boolean z;
        RealConnection realConnection;
        Socket a2;
        synchronized (this.d) {
            if (iOException instanceof StreamResetException) {
                ErrorCode errorCode = ((StreamResetException) iOException).errorCode;
                if (errorCode == ErrorCode.REFUSED_STREAM) {
                    this.g++;
                    if (this.g > 1) {
                        this.c = null;
                    }
                    z = false;
                    realConnection = this.h;
                    a2 = a(z, false, true);
                    if (this.h != null || !this.i) {
                        realConnection = null;
                    }
                } else {
                    if (errorCode != ErrorCode.CANCEL) {
                        this.c = null;
                    }
                    z = false;
                    realConnection = this.h;
                    a2 = a(z, false, true);
                    realConnection = null;
                }
            } else {
                if (this.h != null && (!this.h.isMultiplexed() || (iOException instanceof ConnectionShutdownException))) {
                    if (this.h.successCount == 0) {
                        if (!(this.c == null || iOException == null)) {
                            this.f.connectFailed(this.c, iOException);
                        }
                        this.c = null;
                    }
                }
                z = false;
                realConnection = this.h;
                a2 = a(z, false, true);
                realConnection = null;
            }
            z = true;
            realConnection = this.h;
            a2 = a(z, false, true);
            realConnection = null;
        }
        Util.closeQuietly(a2);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    public void acquire(RealConnection realConnection, boolean z) {
        if (!a && !Thread.holdsLock(this.d)) {
            throw new AssertionError();
        } else if (this.h != null) {
            throw new IllegalStateException();
        } else {
            this.h = realConnection;
            this.i = z;
            realConnection.allocations.add(new StreamAllocationReference(this, this.e));
        }
    }

    private void a(RealConnection realConnection) {
        int size = realConnection.allocations.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((Reference) realConnection.allocations.get(i2)).get() == this) {
                realConnection.allocations.remove(i2);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public Socket releaseAndAcquire(RealConnection realConnection) {
        if (!a && !Thread.holdsLock(this.d)) {
            throw new AssertionError();
        } else if (this.l == null && this.h.allocations.size() == 1) {
            Reference reference = (Reference) this.h.allocations.get(0);
            Socket a2 = a(true, false, false);
            this.h = realConnection;
            realConnection.allocations.add(reference);
            return a2;
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasMoreRoutes() {
        return this.c != null || (this.b != null && this.b.hasNext()) || this.f.hasNext();
    }

    public String toString() {
        RealConnection connection = connection();
        return connection != null ? connection.toString() : this.address.toString();
    }
}
