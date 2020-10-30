package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

class ConfigurableNetwork<N, E> extends AbstractNetwork<N, E> {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final ElementOrder<N> d;
    private final ElementOrder<E> e;
    protected final MapIteratorCache<E, N> edgeToReferenceNode;
    protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.c.a(((Integer) networkBuilder.d.or(Integer.valueOf(10))).intValue()), networkBuilder.f.a(((Integer) networkBuilder.g.or(Integer.valueOf(20))).intValue()));
    }

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.a = networkBuilder.a;
        this.b = networkBuilder.e;
        this.c = networkBuilder.b;
        this.d = networkBuilder.c.a();
        this.e = networkBuilder.f.a();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeToReferenceNode = new MapIteratorCache<>(map2);
    }

    public Set<N> nodes() {
        return this.nodeConnections.a();
    }

    public Set<E> edges() {
        return this.edgeToReferenceNode.a();
    }

    public boolean isDirected() {
        return this.a;
    }

    public boolean allowsParallelEdges() {
        return this.b;
    }

    public boolean allowsSelfLoops() {
        return this.c;
    }

    public ElementOrder<N> nodeOrder() {
        return this.d;
    }

    public ElementOrder<E> edgeOrder() {
        return this.e;
    }

    public Set<E> incidentEdges(Object obj) {
        return checkedConnections(obj).b();
    }

    public EndpointPair<N> incidentNodes(Object obj) {
        Object checkedReferenceNode = checkedReferenceNode(obj);
        return EndpointPair.a(this, checkedReferenceNode, ((NetworkConnections) this.nodeConnections.b(checkedReferenceNode)).a(obj));
    }

    public Set<N> adjacentNodes(Object obj) {
        return checkedConnections(obj).a();
    }

    public Set<E> edgesConnecting(Object obj, Object obj2) {
        NetworkConnections checkedConnections = checkedConnections(obj);
        if (!this.c && obj == obj2) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(containsNode(obj2), "Node %s is not an element of this graph.", obj2);
        return checkedConnections.c(obj2);
    }

    public Set<E> inEdges(Object obj) {
        return checkedConnections(obj).c();
    }

    public Set<E> outEdges(Object obj) {
        return checkedConnections(obj).d();
    }

    public Set<N> predecessors(Object obj) {
        return checkedConnections(obj).e();
    }

    public Set<N> successors(Object obj) {
        return checkedConnections(obj).f();
    }

    /* access modifiers changed from: protected */
    public final NetworkConnections<N, E> checkedConnections(Object obj) {
        NetworkConnections<N, E> networkConnections = (NetworkConnections) this.nodeConnections.b(obj);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", new Object[]{obj}));
    }

    /* access modifiers changed from: protected */
    public final N checkedReferenceNode(Object obj) {
        N b2 = this.edgeToReferenceNode.b(obj);
        if (b2 != null) {
            return b2;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", new Object[]{obj}));
    }

    /* access modifiers changed from: protected */
    public final boolean containsNode(@Nullable Object obj) {
        return this.nodeConnections.d(obj);
    }

    /* access modifiers changed from: protected */
    public final boolean containsEdge(@Nullable Object obj) {
        return this.edgeToReferenceNode.d(obj);
    }
}
