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

final class UndirectedMultiNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> b;

    private UndirectedMultiNetworkConnections(Map<E, N> map) {
        super(map);
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> g() {
        return new UndirectedMultiNetworkConnections<>(new HashMap(2, 1.0f));
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> a(Map<E, N> map) {
        return new UndirectedMultiNetworkConnections<>(ImmutableMap.copyOf(map));
    }

    public Set<N> a() {
        return Collections.unmodifiableSet(h().elementSet());
    }

    /* access modifiers changed from: private */
    public Multiset<N> h() {
        Multiset<N> multiset = (Multiset) a(this.b);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset create = HashMultiset.create((Iterable<? extends E>) this.a.values());
        this.b = new SoftReference(create);
        return create;
    }

    public Set<E> c(final Object obj) {
        return new MultiEdgesConnecting<E>(this.a, obj) {
            public int size() {
                return UndirectedMultiNetworkConnections.this.h().count(obj);
            }
        };
    }

    public N a(Object obj, boolean z) {
        if (!z) {
            return b(obj);
        }
        return null;
    }

    public N b(Object obj) {
        N b2 = super.b(obj);
        Multiset multiset = (Multiset) a(this.b);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(b2));
        }
        return b2;
    }

    public void a(E e, N n, boolean z) {
        if (!z) {
            a(e, n);
        }
    }

    public void a(E e, N n) {
        super.a(e, n);
        Multiset multiset = (Multiset) a(this.b);
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
