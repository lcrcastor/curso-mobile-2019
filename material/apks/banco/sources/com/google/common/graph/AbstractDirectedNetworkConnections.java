package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

abstract class AbstractDirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
    protected final Map<E, N> a;
    protected final Map<E, N> b;
    /* access modifiers changed from: private */
    public int c;

    protected AbstractDirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int i) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = (Map) Preconditions.checkNotNull(map2);
        this.c = Graphs.a(i);
        Preconditions.checkState(i <= map.size() && i <= map2.size());
    }

    public Set<N> a() {
        return Sets.union(e(), f());
    }

    public Set<E> b() {
        return new AbstractSet<E>() {
            /* renamed from: a */
            public UnmodifiableIterator<E> iterator() {
                return Iterators.unmodifiableIterator((AbstractDirectedNetworkConnections.this.c == 0 ? Iterables.concat(AbstractDirectedNetworkConnections.this.a.keySet(), AbstractDirectedNetworkConnections.this.b.keySet()) : Sets.union(AbstractDirectedNetworkConnections.this.a.keySet(), AbstractDirectedNetworkConnections.this.b.keySet())).iterator());
            }

            public int size() {
                return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.a.size(), AbstractDirectedNetworkConnections.this.b.size() - AbstractDirectedNetworkConnections.this.c);
            }

            public boolean contains(@Nullable Object obj) {
                return AbstractDirectedNetworkConnections.this.a.containsKey(obj) || AbstractDirectedNetworkConnections.this.b.containsKey(obj);
            }
        };
    }

    public Set<E> c() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    public Set<E> d() {
        return Collections.unmodifiableSet(this.b.keySet());
    }

    public N a(Object obj) {
        return Preconditions.checkNotNull(this.b.get(obj));
    }

    public N a(Object obj, boolean z) {
        if (z) {
            int i = this.c - 1;
            this.c = i;
            Graphs.a(i);
        }
        return Preconditions.checkNotNull(this.a.remove(obj));
    }

    public N b(Object obj) {
        return Preconditions.checkNotNull(this.b.remove(obj));
    }

    public void a(E e, N n, boolean z) {
        boolean z2 = true;
        if (z) {
            int i = this.c + 1;
            this.c = i;
            Graphs.b(i);
        }
        if (this.a.put(e, n) != null) {
            z2 = false;
        }
        Preconditions.checkState(z2);
    }

    public void a(E e, N n) {
        Preconditions.checkState(this.b.put(e, n) == null);
    }
}
