package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;

final class ConfigurableMutableNetwork<N, E> extends ConfigurableNetwork<N, E> implements MutableNetwork<N, E> {
    ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
    }

    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (containsNode(n)) {
            return false;
        }
        a(n);
        return true;
    }

    @CanIgnoreReturnValue
    private NetworkConnections<N, E> a(N n) {
        NetworkConnections<N, E> a = a();
        Preconditions.checkState(this.nodeConnections.a(n, a) == null);
        return a;
    }

    @CanIgnoreReturnValue
    public boolean addEdge(N n, N n2, E e) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(e, "edge");
        boolean z = false;
        if (containsEdge(e)) {
            EndpointPair incidentNodes = incidentNodes(e);
            EndpointPair a = EndpointPair.a(this, n, n2);
            Preconditions.checkArgument(incidentNodes.equals(a), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e, incidentNodes, a);
            return false;
        }
        NetworkConnections networkConnections = (NetworkConnections) this.nodeConnections.b(n);
        if (!allowsParallelEdges()) {
            if (networkConnections == null || !networkConnections.f().contains(n2)) {
                z = true;
            }
            Preconditions.checkArgument(z, "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", (Object) n, (Object) n2);
        }
        boolean equals = n.equals(n2);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!equals, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", (Object) n);
        }
        if (networkConnections == null) {
            networkConnections = a(n);
        }
        networkConnections.a(e, n2);
        NetworkConnections networkConnections2 = (NetworkConnections) this.nodeConnections.b(n2);
        if (networkConnections2 == null) {
            networkConnections2 = a(n2);
        }
        networkConnections2.a(e, n, equals);
        this.edgeToReferenceNode.a(e, n);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean removeNode(Object obj) {
        Preconditions.checkNotNull(obj, "node");
        NetworkConnections networkConnections = (NetworkConnections) this.nodeConnections.b(obj);
        if (networkConnections == null) {
            return false;
        }
        Iterator it = ImmutableList.copyOf((Collection<? extends E>) networkConnections.b()).iterator();
        while (it.hasNext()) {
            removeEdge(it.next());
        }
        this.nodeConnections.a(obj);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean removeEdge(Object obj) {
        Preconditions.checkNotNull(obj, "edge");
        Object b = this.edgeToReferenceNode.b(obj);
        boolean z = false;
        if (b == null) {
            return false;
        }
        NetworkConnections networkConnections = (NetworkConnections) this.nodeConnections.b(b);
        Object a = networkConnections.a(obj);
        NetworkConnections networkConnections2 = (NetworkConnections) this.nodeConnections.b(a);
        networkConnections.b(obj);
        if (allowsSelfLoops() && b.equals(a)) {
            z = true;
        }
        networkConnections2.a(obj, z);
        this.edgeToReferenceNode.a(obj);
        return true;
    }

    private NetworkConnections<N, E> a() {
        return isDirected() ? allowsParallelEdges() ? DirectedMultiNetworkConnections.g() : DirectedNetworkConnections.g() : allowsParallelEdges() ? UndirectedMultiNetworkConnections.g() : UndirectedNetworkConnections.g();
    }
}
