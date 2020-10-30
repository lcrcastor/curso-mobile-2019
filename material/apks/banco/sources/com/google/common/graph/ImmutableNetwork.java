package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

@Beta
public final class ImmutableNetwork<N, E> extends ConfigurableNetwork<N, E> {
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    public /* bridge */ /* synthetic */ boolean allowsParallelEdges() {
        return super.allowsParallelEdges();
    }

    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    public /* bridge */ /* synthetic */ ElementOrder edgeOrder() {
        return super.edgeOrder();
    }

    public /* bridge */ /* synthetic */ Set edges() {
        return super.edges();
    }

    public /* bridge */ /* synthetic */ Set edgesConnecting(Object obj, Object obj2) {
        return super.edgesConnecting(obj, obj2);
    }

    public /* bridge */ /* synthetic */ Set inEdges(Object obj) {
        return super.inEdges(obj);
    }

    public /* bridge */ /* synthetic */ Set incidentEdges(Object obj) {
        return super.incidentEdges(obj);
    }

    public /* bridge */ /* synthetic */ EndpointPair incidentNodes(Object obj) {
        return super.incidentNodes(obj);
    }

    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    public /* bridge */ /* synthetic */ Set outEdges(Object obj) {
        return super.outEdges(obj);
    }

    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors(obj);
    }

    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors(obj);
    }

    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), a(network), b(network));
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork) network : new ImmutableNetwork(network);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return (ImmutableNetwork) Preconditions.checkNotNull(immutableNetwork);
    }

    public ImmutableGraph<N> asGraph() {
        final Graph asGraph = super.asGraph();
        return new ImmutableGraph<N>() {
            /* access modifiers changed from: protected */
            public Graph<N> delegate() {
                return asGraph;
            }
        };
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> a(Network<N, E> network) {
        Builder builder = ImmutableMap.builder();
        for (Object next : network.nodes()) {
            builder.put(next, a(network, next));
        }
        return builder.build();
    }

    private static <N, E> Map<E, N> b(Network<N, E> network) {
        Builder builder = ImmutableMap.builder();
        for (Object next : network.edges()) {
            builder.put(next, network.incidentNodes(next).nodeU());
        }
        return builder.build();
    }

    private static <N, E> NetworkConnections<N, E> a(Network<N, E> network, N n) {
        if (network.isDirected()) {
            Map asMap = Maps.asMap(network.inEdges(n), c(network));
            Map asMap2 = Maps.asMap(network.outEdges(n), d(network));
            int size = network.edgesConnecting(n, n).size();
            return network.allowsParallelEdges() ? DirectedMultiNetworkConnections.a(asMap, asMap2, size) : DirectedNetworkConnections.a(asMap, asMap2, size);
        }
        Map asMap3 = Maps.asMap(network.incidentEdges(n), b(network, n));
        return network.allowsParallelEdges() ? UndirectedMultiNetworkConnections.a(asMap3) : UndirectedNetworkConnections.a(asMap3);
    }

    private static <N, E> Function<E, N> c(final Network<N, E> network) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> d(final Network<N, E> network) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).target();
            }
        };
    }

    private static <N, E> Function<E, N> b(final Network<N, E> network, final N n) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).adjacentNode(n);
            }
        };
    }
}
