package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

final class UndirectedNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {
    protected UndirectedNetworkConnections(Map<E, N> map) {
        super(map);
    }

    static <N, E> UndirectedNetworkConnections<N, E> g() {
        return new UndirectedNetworkConnections<>(HashBiMap.create(2));
    }

    static <N, E> UndirectedNetworkConnections<N, E> a(Map<E, N> map) {
        return new UndirectedNetworkConnections<>(ImmutableBiMap.copyOf(map));
    }

    public Set<N> a() {
        return Collections.unmodifiableSet(((BiMap) this.a).values());
    }

    public Set<E> c(Object obj) {
        return new EdgesConnecting(((BiMap) this.a).inverse(), obj);
    }
}
