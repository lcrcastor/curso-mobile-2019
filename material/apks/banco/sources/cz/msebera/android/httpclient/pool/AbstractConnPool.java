package cz.msebera.android.httpclient.pool;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.concurrent.FutureCallback;
import cz.msebera.android.httpclient.pool.PoolEntry;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public abstract class AbstractConnPool<T, C, E extends PoolEntry<T, C>> implements ConnPool<T, E>, ConnPoolControl<T> {
    private final Lock a = new ReentrantLock();
    private final ConnFactory<T, C> b;
    private final Map<T, RouteSpecificPool<T, C, E>> c = new HashMap();
    private final Set<E> d = new HashSet();
    private final LinkedList<E> e = new LinkedList<>();
    private final LinkedList<PoolEntryFuture<E>> f = new LinkedList<>();
    private final Map<T, Integer> g = new HashMap();
    private volatile boolean h;
    private volatile int i;
    private volatile int j;
    private volatile int k;

    public abstract E createEntry(T t, C c2);

    /* access modifiers changed from: protected */
    public void onLease(E e2) {
    }

    /* access modifiers changed from: protected */
    public void onRelease(E e2) {
    }

    /* access modifiers changed from: protected */
    public void onReuse(E e2) {
    }

    public boolean validate(E e2) {
        return true;
    }

    public AbstractConnPool(ConnFactory<T, C> connFactory, int i2, int i3) {
        this.b = (ConnFactory) Args.notNull(connFactory, "Connection factory");
        this.i = Args.positive(i2, "Max per route value");
        this.j = Args.positive(i3, "Max total value");
    }

    public boolean isShutdown() {
        return this.h;
    }

    public void shutdown() {
        if (!this.h) {
            this.h = true;
            this.a.lock();
            try {
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    ((PoolEntry) it.next()).close();
                }
                for (E close : this.d) {
                    close.close();
                }
                for (RouteSpecificPool g2 : this.c.values()) {
                    g2.g();
                }
                this.c.clear();
                this.d.clear();
                this.e.clear();
            } finally {
                this.a.unlock();
            }
        }
    }

    private RouteSpecificPool<T, C, E> a(final T t) {
        RouteSpecificPool<T, C, E> routeSpecificPool = (RouteSpecificPool) this.c.get(t);
        if (routeSpecificPool != null) {
            return routeSpecificPool;
        }
        AnonymousClass1 r0 = new RouteSpecificPool<T, C, E>(t) {
            /* access modifiers changed from: protected */
            public E a(C c) {
                return AbstractConnPool.this.createEntry(t, c);
            }
        };
        this.c.put(t, r0);
        return r0;
    }

    public Future<E> lease(T t, Object obj, FutureCallback<E> futureCallback) {
        Args.notNull(t, "Route");
        Asserts.check(!this.h, "Connection pool shut down");
        final T t2 = t;
        final Object obj2 = obj;
        AnonymousClass2 r2 = new PoolEntryFuture<E>(this.a, futureCallback) {
            /* renamed from: a */
            public E b(long j, TimeUnit timeUnit) {
                E a2 = AbstractConnPool.this.a(t2, obj2, j, timeUnit, this);
                AbstractConnPool.this.onLease(a2);
                return a2;
            }
        };
        return r2;
    }

    public Future<E> lease(T t, Object obj) {
        return lease(t, obj, null);
    }

    /* access modifiers changed from: private */
    public E a(T t, Object obj, long j2, TimeUnit timeUnit, PoolEntryFuture<E> poolEntryFuture) {
        RouteSpecificPool a2;
        E e2 = null;
        Date date = j2 > 0 ? new Date(System.currentTimeMillis() + timeUnit.toMillis(j2)) : null;
        this.a.lock();
        try {
            a2 = a(t);
            while (true) {
                if (e2 != null) {
                    break;
                }
                Asserts.check(!this.h, "Connection pool shut down");
                while (true) {
                    e2 = a2.b(obj);
                    if (e2 != null) {
                        if (e2.isExpired(System.currentTimeMillis())) {
                            e2.close();
                        } else if (this.k > 0 && e2.getUpdated() + ((long) this.k) <= System.currentTimeMillis() && !validate(e2)) {
                            e2.close();
                        }
                        if (!e2.isClosed()) {
                            break;
                        }
                        this.e.remove(e2);
                        a2.a(e2, false);
                    } else {
                        break;
                    }
                }
                if (e2 == null) {
                    int b2 = b(t);
                    int max = Math.max(0, (a2.d() + 1) - b2);
                    if (max > 0) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= max) {
                                break;
                            }
                            PoolEntry e3 = a2.e();
                            if (e3 == null) {
                                break;
                            }
                            e3.close();
                            this.e.remove(e3);
                            a2.a(e3);
                            i2++;
                        }
                    }
                    if (a2.d() < b2) {
                        int max2 = Math.max(this.j - this.d.size(), 0);
                        if (max2 > 0) {
                            if (this.e.size() > max2 - 1 && !this.e.isEmpty()) {
                                PoolEntry poolEntry = (PoolEntry) this.e.removeLast();
                                poolEntry.close();
                                a(poolEntry.getRoute()).a(poolEntry);
                            }
                            E c2 = a2.c(this.b.create(t));
                            this.d.add(c2);
                            this.a.unlock();
                            return c2;
                        }
                    }
                    a2.a(poolEntryFuture);
                    this.f.add(poolEntryFuture);
                    boolean a3 = poolEntryFuture.a(date);
                    a2.b(poolEntryFuture);
                    this.f.remove(poolEntryFuture);
                    if (!a3 && date != null && date.getTime() <= System.currentTimeMillis()) {
                        break;
                    }
                } else {
                    this.e.remove(e2);
                    this.d.add(e2);
                    onReuse(e2);
                    this.a.unlock();
                    return e2;
                }
            }
            throw new TimeoutException("Timeout waiting for connection");
        } catch (Throwable th) {
            this.a.unlock();
            throw th;
        }
    }

    public void release(E e2, boolean z) {
        this.a.lock();
        try {
            if (this.d.remove(e2)) {
                RouteSpecificPool a2 = a(e2.getRoute());
                a2.a(e2, z);
                if (!z || this.h) {
                    e2.close();
                } else {
                    this.e.addFirst(e2);
                    onRelease(e2);
                }
                PoolEntryFuture f2 = a2.f();
                if (f2 != null) {
                    this.f.remove(f2);
                } else {
                    f2 = (PoolEntryFuture) this.f.poll();
                }
                if (f2 != null) {
                    f2.a();
                }
            }
        } finally {
            this.a.unlock();
        }
    }

    private int b(T t) {
        Integer num = (Integer) this.g.get(t);
        if (num != null) {
            return num.intValue();
        }
        return this.i;
    }

    public void setMaxTotal(int i2) {
        Args.positive(i2, "Max value");
        this.a.lock();
        try {
            this.j = i2;
        } finally {
            this.a.unlock();
        }
    }

    public int getMaxTotal() {
        this.a.lock();
        try {
            return this.j;
        } finally {
            this.a.unlock();
        }
    }

    public void setDefaultMaxPerRoute(int i2) {
        Args.positive(i2, "Max per route value");
        this.a.lock();
        try {
            this.i = i2;
        } finally {
            this.a.unlock();
        }
    }

    public int getDefaultMaxPerRoute() {
        this.a.lock();
        try {
            return this.i;
        } finally {
            this.a.unlock();
        }
    }

    public void setMaxPerRoute(T t, int i2) {
        Args.notNull(t, "Route");
        Args.positive(i2, "Max per route value");
        this.a.lock();
        try {
            this.g.put(t, Integer.valueOf(i2));
        } finally {
            this.a.unlock();
        }
    }

    public int getMaxPerRoute(T t) {
        Args.notNull(t, "Route");
        this.a.lock();
        try {
            return b(t);
        } finally {
            this.a.unlock();
        }
    }

    public PoolStats getTotalStats() {
        this.a.lock();
        try {
            return new PoolStats(this.d.size(), this.f.size(), this.e.size(), this.j);
        } finally {
            this.a.unlock();
        }
    }

    public PoolStats getStats(T t) {
        Args.notNull(t, "Route");
        this.a.lock();
        try {
            RouteSpecificPool a2 = a(t);
            PoolStats poolStats = new PoolStats(a2.a(), a2.b(), a2.c(), b(t));
            return poolStats;
        } finally {
            this.a.unlock();
        }
    }

    public Set<T> getRoutes() {
        this.a.lock();
        try {
            return new HashSet(this.c.keySet());
        } finally {
            this.a.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void enumAvailable(PoolEntryCallback<T, C> poolEntryCallback) {
        this.a.lock();
        try {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                PoolEntry poolEntry = (PoolEntry) it.next();
                poolEntryCallback.process(poolEntry);
                if (poolEntry.isClosed()) {
                    a(poolEntry.getRoute()).a(poolEntry);
                    it.remove();
                }
            }
            a();
        } finally {
            this.a.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void enumLeased(PoolEntryCallback<T, C> poolEntryCallback) {
        this.a.lock();
        try {
            for (E process : this.d) {
                poolEntryCallback.process(process);
            }
        } finally {
            this.a.unlock();
        }
    }

    private void a() {
        Iterator it = this.c.entrySet().iterator();
        while (it.hasNext()) {
            RouteSpecificPool routeSpecificPool = (RouteSpecificPool) ((Entry) it.next()).getValue();
            if (routeSpecificPool.b() + routeSpecificPool.d() == 0) {
                it.remove();
            }
        }
    }

    public void closeIdle(long j2, TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        long millis = timeUnit.toMillis(j2);
        if (millis < 0) {
            millis = 0;
        }
        final long currentTimeMillis = System.currentTimeMillis() - millis;
        enumAvailable(new PoolEntryCallback<T, C>() {
            public void process(PoolEntry<T, C> poolEntry) {
                if (poolEntry.getUpdated() <= currentTimeMillis) {
                    poolEntry.close();
                }
            }
        });
    }

    public void closeExpired() {
        final long currentTimeMillis = System.currentTimeMillis();
        enumAvailable(new PoolEntryCallback<T, C>() {
            public void process(PoolEntry<T, C> poolEntry) {
                if (poolEntry.isExpired(currentTimeMillis)) {
                    poolEntry.close();
                }
            }
        });
    }

    public int getValidateAfterInactivity() {
        return this.k;
    }

    public void setValidateAfterInactivity(int i2) {
        this.k = i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[leased: ");
        sb.append(this.d);
        sb.append("][available: ");
        sb.append(this.e);
        sb.append("][pending: ");
        sb.append(this.f);
        sb.append("]");
        return sb.toString();
    }
}
