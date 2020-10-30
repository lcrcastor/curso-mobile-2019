package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

@Beta
public final class ValueGraphBuilder<N, V> extends AbstractGraphBuilder<N> {
    private <N1 extends N, V1 extends V> ValueGraphBuilder<N1, V1> a() {
        return this;
    }

    private ValueGraphBuilder(boolean z) {
        super(z);
    }

    public static ValueGraphBuilder<Object, Object> directed() {
        return new ValueGraphBuilder<>(true);
    }

    public static ValueGraphBuilder<Object, Object> undirected() {
        return new ValueGraphBuilder<>(false);
    }

    public static <N> ValueGraphBuilder<N, Object> from(Graph<N> graph) {
        return new ValueGraphBuilder(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
    }

    public ValueGraphBuilder<N, V> allowsSelfLoops(boolean z) {
        this.b = z;
        return this;
    }

    public ValueGraphBuilder<N, V> expectedNodeCount(int i) {
        this.d = Optional.of(Integer.valueOf(Graphs.a(i)));
        return this;
    }

    public <N1 extends N> ValueGraphBuilder<N1, V> nodeOrder(ElementOrder<N1> elementOrder) {
        ValueGraphBuilder<N1, V> a = a();
        a.c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return a;
    }

    public <N1 extends N, V1 extends V> MutableValueGraph<N1, V1> build() {
        return new ConfigurableMutableValueGraph(this);
    }
}
