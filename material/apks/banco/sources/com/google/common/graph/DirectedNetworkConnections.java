package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

final class DirectedNetworkConnections<N, E> extends AbstractDirectedNetworkConnections<N, E> {
    protected DirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int i) {
        super(map, map2, i);
    }

    static <N, E> DirectedNetworkConnections<N, E> g() {
        return new DirectedNetworkConnections<>(HashBiMap.create(2), HashBiMap.create(2), 0);
    }

    static <N, E> DirectedNetworkConnections<N, E> a(Map<E, N> map, Map<E, N> map2, int i) {
        return new DirectedNetworkConnections<>(ImmutableBiMap.copyOf(map), ImmutableBiMap.copyOf(map2), i);
    }

    public Set<N> e() {
        return Collections.unmodifiableSet(((BiMap) this.a).values());
    }

    public Set<N> f() {
        return Collections.unmodifiableSet(((BiMap) this.b).values());
    }

    public Set<E> c(Object obj) {
        return new EdgesConnecting(((BiMap) this.b).inverse(), obj);
    }
}
