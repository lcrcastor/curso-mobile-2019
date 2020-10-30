package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

abstract class AbstractUndirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
    protected final Map<E, N> a;

    protected AbstractUndirectedNetworkConnections(Map<E, N> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    public Set<N> e() {
        return a();
    }

    public Set<N> f() {
        return a();
    }

    public Set<E> b() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    public Set<E> c() {
        return b();
    }

    public Set<E> d() {
        return b();
    }

    public N a(Object obj) {
        return Preconditions.checkNotNull(this.a.get(obj));
    }

    public N a(Object obj, boolean z) {
        if (!z) {
            return b(obj);
        }
        return null;
    }

    public N b(Object obj) {
        return Preconditions.checkNotNull(this.a.remove(obj));
    }

    public void a(E e, N n, boolean z) {
        if (!z) {
            a(e, n);
        }
    }

    public void a(E e, N n) {
        Preconditions.checkState(this.a.put(e, n) == null);
    }
}
