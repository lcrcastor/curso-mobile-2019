package okhttp3;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.connection.StreamAllocation.StreamAllocationReference;
import okhttp3.internal.platform.Platform;

public final class ConnectionPool {
    static final /* synthetic */ boolean c = true;
    private static final Executor d;
    final RouteDatabase a;
    boolean b;
    private final int e;
    private final long f;
    private final Runnable g;
    private final Deque<RealConnection> h;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, SubsamplingScaleImageView.TILE_SIZE_AUTO, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
        d = threadPoolExecutor;
    }

    public ConnectionPool() {
        this(5, 5, TimeUnit.MINUTES);
    }

    public ConnectionPool(int i, long j, TimeUnit timeUnit) {
        this.g = new Runnable() {
            /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
            /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x002c */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r8 = this;
                L_0x0000:
                    okhttp3.ConnectionPool r0 = okhttp3.ConnectionPool.this
                    long r1 = java.lang.System.nanoTime()
                    long r0 = r0.a(r1)
                    r2 = -1
                    int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r4 != 0) goto L_0x0011
                    return
                L_0x0011:
                    r2 = 0
                    int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r4 <= 0) goto L_0x0000
                    r2 = 1000000(0xf4240, double:4.940656E-318)
                    long r4 = r0 / r2
                    long r2 = r2 * r4
                    long r6 = r0 - r2
                    okhttp3.ConnectionPool r0 = okhttp3.ConnectionPool.this
                    monitor-enter(r0)
                    okhttp3.ConnectionPool r1 = okhttp3.ConnectionPool.this     // Catch:{ InterruptedException -> 0x002c }
                    int r2 = (int) r6     // Catch:{ InterruptedException -> 0x002c }
                    r1.wait(r4, r2)     // Catch:{ InterruptedException -> 0x002c }
                    goto L_0x002c
                L_0x002a:
                    r1 = move-exception
                    goto L_0x002e
                L_0x002c:
                    monitor-exit(r0)     // Catch:{ all -> 0x002a }
                    goto L_0x0000
                L_0x002e:
                    monitor-exit(r0)     // Catch:{ all -> 0x002a }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: okhttp3.ConnectionPool.AnonymousClass1.run():void");
            }
        };
        this.h = new ArrayDeque();
        this.a = new RouteDatabase();
        this.e = i;
        this.f = timeUnit.toNanos(j);
        if (j <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("keepAliveDuration <= 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public synchronized int idleConnectionCount() {
        int i;
        i = 0;
        for (RealConnection realConnection : this.h) {
            if (realConnection.allocations.isEmpty()) {
                i++;
            }
        }
        return i;
    }

    public synchronized int connectionCount() {
        return this.h.size();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public RealConnection a(Address address, StreamAllocation streamAllocation, Route route) {
        if (c || Thread.holdsLock(this)) {
            for (RealConnection realConnection : this.h) {
                if (realConnection.isEligible(address, route)) {
                    streamAllocation.acquire(realConnection, true);
                    return realConnection;
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Socket a(Address address, StreamAllocation streamAllocation) {
        if (c || Thread.holdsLock(this)) {
            for (RealConnection realConnection : this.h) {
                if (realConnection.isEligible(address, null) && realConnection.isMultiplexed() && realConnection != streamAllocation.connection()) {
                    return streamAllocation.releaseAndAcquire(realConnection);
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a(RealConnection realConnection) {
        if (c || Thread.holdsLock(this)) {
            if (!this.b) {
                this.b = true;
                d.execute(this.g);
            }
            this.h.add(realConnection);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public boolean b(RealConnection realConnection) {
        if (!c && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (realConnection.noNewStreams || this.e == 0) {
            this.h.remove(realConnection);
            return true;
        } else {
            notifyAll();
            return false;
        }
    }

    public void evictAll() {
        ArrayList<RealConnection> arrayList = new ArrayList<>();
        synchronized (this) {
            Iterator it = this.h.iterator();
            while (it.hasNext()) {
                RealConnection realConnection = (RealConnection) it.next();
                if (realConnection.allocations.isEmpty()) {
                    realConnection.noNewStreams = true;
                    arrayList.add(realConnection);
                    it.remove();
                }
            }
        }
        for (RealConnection socket : arrayList) {
            Util.closeQuietly(socket.socket());
        }
    }

    /* access modifiers changed from: 0000 */
    public long a(long j) {
        synchronized (this) {
            long j2 = Long.MIN_VALUE;
            RealConnection realConnection = null;
            int i = 0;
            int i2 = 0;
            for (RealConnection realConnection2 : this.h) {
                if (a(realConnection2, j) > 0) {
                    i2++;
                } else {
                    i++;
                    long j3 = j - realConnection2.idleAtNanos;
                    if (j3 > j2) {
                        realConnection = realConnection2;
                        j2 = j3;
                    }
                }
            }
            if (j2 < this.f) {
                if (i <= this.e) {
                    if (i > 0) {
                        long j4 = this.f - j2;
                        return j4;
                    } else if (i2 > 0) {
                        long j5 = this.f;
                        return j5;
                    } else {
                        this.b = false;
                        return -1;
                    }
                }
            }
            this.h.remove(realConnection);
            Util.closeQuietly(realConnection.socket());
            return 0;
        }
    }

    private int a(RealConnection realConnection, long j) {
        List<Reference<StreamAllocation>> list = realConnection.allocations;
        int i = 0;
        while (i < list.size()) {
            Reference reference = (Reference) list.get(i);
            if (reference.get() != null) {
                i++;
            } else {
                StreamAllocationReference streamAllocationReference = (StreamAllocationReference) reference;
                StringBuilder sb = new StringBuilder();
                sb.append("A connection to ");
                sb.append(realConnection.route().address().url());
                sb.append(" was leaked. Did you forget to close a response body?");
                Platform.get().logCloseableLeak(sb.toString(), streamAllocationReference.callStackTrace);
                list.remove(i);
                realConnection.noNewStreams = true;
                if (list.isEmpty()) {
                    realConnection.idleAtNanos = j - this.f;
                    return 0;
                }
            }
        }
        return list.size();
    }
}
