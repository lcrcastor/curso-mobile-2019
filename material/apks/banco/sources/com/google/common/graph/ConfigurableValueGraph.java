package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

class ConfigurableValueGraph<N, V> extends AbstractValueGraph<N, V> {
    protected final MapIteratorCache<N, GraphConnections<N, V>> a;
    protected long b;
    private final boolean c;
    private final boolean d;
    private final ElementOrder<N> e;

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.c.a(((Integer) abstractGraphBuilder.d.or(Integer.valueOf(10))).intValue()), 0);
    }

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder, Map<N, GraphConnections<N, V>> map, long j) {
        this.c = abstractGraphBuilder.a;
        this.d = abstractGraphBuilder.b;
        this.e = abstractGraphBuilder.c.a();
        this.a = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.b = Graphs.a(j);
    }

    public Set<N> nodes() {
        return this.a.a();
    }

    public boolean isDirected() {
        return this.c;
    }

    public boolean allowsSelfLoops() {
        return this.d;
    }

    public ElementOrder<N> nodeOrder() {
        return this.e;
    }

    public Set<N> adjacentNodes(Object obj) {
        return a(obj).b();
    }

    public Set<N> predecessors(Object obj) {
        return a(obj).c();
    }

    public Set<N> successors(Object obj) {
        return a(obj).d();
    }

    public V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v) {
        GraphConnections graphConnections = (GraphConnections) this.a.b(obj);
        if (graphConnections == null) {
            return v;
        }
        V a2 = graphConnections.a(obj2);
        return a2 == null ? v : a2;
    }

    /* access modifiers changed from: protected */
    public long edgeCount() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public final GraphConnections<N, V> a(Object obj) {
        GraphConnections<N, V> graphConnections = (GraphConnections) this.a.b(obj);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", new Object[]{obj}));
    }

    /* access modifiers changed from: protected */
    public final boolean b(@Nullable Object obj) {
        return this.a.d(obj);
    }
}
