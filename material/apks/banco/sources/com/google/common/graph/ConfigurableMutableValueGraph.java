package com.google.common.graph;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

final class ConfigurableMutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> implements MutableValueGraph<N, V> {
    ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
    }

    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (b(n)) {
            return false;
        }
        c(n);
        return true;
    }

    @CanIgnoreReturnValue
    private GraphConnections<N, V> c(N n) {
        GraphConnections<N, V> a = a();
        Preconditions.checkState(this.a.a(n, a) == null);
        return a;
    }

    @CanIgnoreReturnValue
    public V putEdgeValue(N n, N n2, V v) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(v, TarjetasConstants.VALUE);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!n.equals(n2), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", (Object) n);
        }
        GraphConnections graphConnections = (GraphConnections) this.a.b(n);
        if (graphConnections == null) {
            graphConnections = c(n);
        }
        V b = graphConnections.b(n2, v);
        GraphConnections graphConnections2 = (GraphConnections) this.a.b(n2);
        if (graphConnections2 == null) {
            graphConnections2 = c(n2);
        }
        graphConnections2.a(n, v);
        if (b == null) {
            long j = this.b + 1;
            this.b = j;
            Graphs.b(j);
        }
        return b;
    }

    @CanIgnoreReturnValue
    public boolean removeNode(Object obj) {
        Preconditions.checkNotNull(obj, "node");
        GraphConnections graphConnections = (GraphConnections) this.a.b(obj);
        if (graphConnections == null) {
            return false;
        }
        if (allowsSelfLoops() && graphConnections.c(obj) != null) {
            graphConnections.b(obj);
            this.b--;
        }
        for (Object c : graphConnections.d()) {
            ((GraphConnections) this.a.c(c)).b(obj);
            this.b--;
        }
        if (isDirected()) {
            for (Object c2 : graphConnections.c()) {
                Preconditions.checkState(((GraphConnections) this.a.c(c2)).c(obj) != null);
                this.b--;
            }
        }
        this.a.a(obj);
        Graphs.a(this.b);
        return true;
    }

    @CanIgnoreReturnValue
    public V removeEdge(Object obj, Object obj2) {
        Preconditions.checkNotNull(obj, "nodeU");
        Preconditions.checkNotNull(obj2, "nodeV");
        GraphConnections graphConnections = (GraphConnections) this.a.b(obj);
        GraphConnections graphConnections2 = (GraphConnections) this.a.b(obj2);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        V c = graphConnections.c(obj2);
        if (c != null) {
            graphConnections2.b(obj);
            long j = this.b - 1;
            this.b = j;
            Graphs.a(j);
        }
        return c;
    }

    private GraphConnections<N, V> a() {
        return isDirected() ? DirectedGraphConnections.a() : UndirectedGraphConnections.a();
    }
}
