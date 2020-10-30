package com.google.common.graph;

final class ConfigurableMutableGraph<N> extends ForwardingGraph<N> implements MutableGraph<N> {
    private final MutableValueGraph<N, Presence> a;

    ConfigurableMutableGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this.a = new ConfigurableMutableValueGraph(abstractGraphBuilder);
    }

    /* access modifiers changed from: protected */
    public Graph<N> delegate() {
        return this.a;
    }

    public boolean addNode(N n) {
        return this.a.addNode(n);
    }

    public boolean putEdge(N n, N n2) {
        return this.a.putEdgeValue(n, n2, Presence.EDGE_EXISTS) == null;
    }

    public boolean removeNode(Object obj) {
        return this.a.removeNode(obj);
    }

    public boolean removeEdge(Object obj, Object obj2) {
        return this.a.removeEdge(obj, obj2) != null;
    }
}
