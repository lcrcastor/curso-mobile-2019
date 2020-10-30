package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

final class DirectedMultiNetworkConnections<N, E> extends AbstractDirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> c;
    @LazyInit
    private transient Reference<Multiset<N>> d;

    private DirectedMultiNetworkConnections(Map<E, N> map, Map<E, N> map2, int i) {
        super(map, map2, i);
    }

    static <N, E> DirectedMultiNetworkConnections<N, E> g() {
        return new DirectedMultiNetworkConnections<>(new HashMap(2, 1.0f), new HashMap(2, 1.0f), 0);
    }

    static <N, E> DirectedMultiNetworkConnections<N, E> a(Map<E, N> map, Map<E, N> map2, int i) {
        return new DirectedMultiNetworkConnections<>(ImmutableMap.copyOf(map), ImmutableMap.copyOf(map2), i);
    }

    public Set<N> e() {
        return Collections.unmodifiableSet(h().elementSet());
    }

    private Multiset<N> h() {
        Multiset<N> multiset = (Multiset) a(this.c);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create((Iterable<? extends E>) this.a.values());
        this.c = new SoftReference(create);
        return create;
    }

    public Set<N> f() {
        return Collections.unmodifiableSet(i().elementSet());
    }

    /* access modifiers changed from: private */
    public Multiset<N> i() {
        Multiset<N> multiset = (Multiset) a(this.d);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create((Iterable<? extends E>) this.b.values());
        this.d = new SoftReference(create);
        return create;
    }

    public Set<E> c(final Object obj) {
        return new MultiEdgesConnecting<E>(this.b, obj) {
            public int size() {
                return DirectedMultiNetworkConnections.this.i().count(obj);
            }
        };
    }

    public N a(Object obj, boolean z) {
        N a = super.a(obj, z);
        Multiset multiset = (Multiset) a(this.c);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(a));
        }
        return a;
    }

    public N b(Object obj) {
        N b = super.b(obj);
        Multiset multiset = (Multiset) a(this.d);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(b));
        }
        return b;
    }

    public void a(E e, N n, boolean z) {
        super.a(e, n, z);
        Multiset multiset = (Multiset) a(this.c);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    public void a(E e, N n) {
        super.a(e, n);
        Multiset multiset = (Multiset) a(this.d);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Nullable
    private static <T> T a(@Nullable Reference<T> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }
}
