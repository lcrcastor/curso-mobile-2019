package cz.msebera.android.httpclient.pool;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.pool.PoolEntry;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

@NotThreadSafe
abstract class RouteSpecificPool<T, C, E extends PoolEntry<T, C>> {
    private final T a;
    private final Set<E> b = new HashSet();
    private final LinkedList<E> c = new LinkedList<>();
    private final LinkedList<PoolEntryFuture<E>> d = new LinkedList<>();

    /* access modifiers changed from: protected */
    public abstract E a(C c2);

    RouteSpecificPool(T t) {
        this.a = t;
    }

    public int a() {
        return this.b.size();
    }

    public int b() {
        return this.d.size();
    }

    public int c() {
        return this.c.size();
    }

    public int d() {
        return this.c.size() + this.b.size();
    }

    public E b(Object obj) {
        if (!this.c.isEmpty()) {
            if (obj != null) {
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    E e = (PoolEntry) it.next();
                    if (obj.equals(e.getState())) {
                        it.remove();
                        this.b.add(e);
                        return e;
                    }
                }
            }
            Iterator it2 = this.c.iterator();
            while (it2.hasNext()) {
                E e2 = (PoolEntry) it2.next();
                if (e2.getState() == null) {
                    it2.remove();
                    this.b.add(e2);
                    return e2;
                }
            }
        }
        return null;
    }

    public E e() {
        if (!this.c.isEmpty()) {
            return (PoolEntry) this.c.getLast();
        }
        return null;
    }

    public boolean a(E e) {
        Args.notNull(e, "Pool entry");
        return this.c.remove(e) || this.b.remove(e);
    }

    public void a(E e, boolean z) {
        Args.notNull(e, "Pool entry");
        Asserts.check(this.b.remove(e), "Entry %s has not been leased from this pool", (Object) e);
        if (z) {
            this.c.addFirst(e);
        }
    }

    public E c(C c2) {
        E a2 = a(c2);
        this.b.add(a2);
        return a2;
    }

    public void a(PoolEntryFuture<E> poolEntryFuture) {
        if (poolEntryFuture != null) {
            this.d.add(poolEntryFuture);
        }
    }

    public PoolEntryFuture<E> f() {
        return (PoolEntryFuture) this.d.poll();
    }

    public void b(PoolEntryFuture<E> poolEntryFuture) {
        if (poolEntryFuture != null) {
            this.d.remove(poolEntryFuture);
        }
    }

    public void g() {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            ((PoolEntryFuture) it.next()).cancel(true);
        }
        this.d.clear();
        Iterator it2 = this.c.iterator();
        while (it2.hasNext()) {
            ((PoolEntry) it2.next()).close();
        }
        this.c.clear();
        for (E close : this.b) {
            close.close();
        }
        this.b.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[route: ");
        sb.append(this.a);
        sb.append("][leased: ");
        sb.append(this.b.size());
        sb.append("][available: ");
        sb.append(this.c.size());
        sb.append("][pending: ");
        sb.append(this.d.size());
        sb.append("]");
        return sb.toString();
    }
}
