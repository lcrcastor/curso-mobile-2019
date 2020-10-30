package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class UndirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private final Map<N, V> a;

    private UndirectedGraphConnections(Map<N, V> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    static <N, V> UndirectedGraphConnections<N, V> a() {
        return new UndirectedGraphConnections<>(new HashMap(2, 1.0f));
    }

    static <N, V> UndirectedGraphConnections<N, V> a(Map<N, V> map) {
        return new UndirectedGraphConnections<>(ImmutableMap.copyOf(map));
    }

    public Set<N> b() {
        return Collections.unmodifiableSet(this.a.keySet());
    }

    public Set<N> c() {
        return b();
    }

    public Set<N> d() {
        return b();
    }

    public V a(Object obj) {
        return this.a.get(obj);
    }

    public void b(Object obj) {
        c(obj);
    }

    public V c(Object obj) {
        return this.a.remove(obj);
    }

    public void a(N n, V v) {
        b(n, v);
    }

    public V b(N n, V v) {
        return this.a.put(n, v);
    }
}
