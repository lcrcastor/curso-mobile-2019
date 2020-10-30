package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class Graphs {

    static class TransposedValueGraph<N, V> extends AbstractValueGraph<N, V> {
        /* access modifiers changed from: private */
        public final ValueGraph<N, V> a;

        TransposedValueGraph(ValueGraph<N, V> valueGraph) {
            this.a = valueGraph;
        }

        public Set<N> nodes() {
            return this.a.nodes();
        }

        /* access modifiers changed from: protected */
        public long edgeCount() {
            return (long) this.a.edges().size();
        }

        public boolean isDirected() {
            return this.a.isDirected();
        }

        public boolean allowsSelfLoops() {
            return this.a.allowsSelfLoops();
        }

        public ElementOrder<N> nodeOrder() {
            return this.a.nodeOrder();
        }

        public Set<N> adjacentNodes(Object obj) {
            return this.a.adjacentNodes(obj);
        }

        public Set<N> predecessors(Object obj) {
            return this.a.successors(obj);
        }

        public Set<N> successors(Object obj) {
            return this.a.predecessors(obj);
        }

        public V edgeValue(Object obj, Object obj2) {
            return this.a.edgeValue(obj2, obj);
        }

        public V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v) {
            return this.a.edgeValueOrDefault(obj2, obj, v);
        }
    }

    enum NodeVisitState {
        PENDING,
        COMPLETE
    }

    static class TransposedGraph<N> extends AbstractGraph<N> {
        /* access modifiers changed from: private */
        public final Graph<N> a;

        TransposedGraph(Graph<N> graph) {
            this.a = graph;
        }

        public Set<N> nodes() {
            return this.a.nodes();
        }

        /* access modifiers changed from: protected */
        public long edgeCount() {
            return (long) this.a.edges().size();
        }

        public boolean isDirected() {
            return this.a.isDirected();
        }

        public boolean allowsSelfLoops() {
            return this.a.allowsSelfLoops();
        }

        public ElementOrder<N> nodeOrder() {
            return this.a.nodeOrder();
        }

        public Set<N> adjacentNodes(Object obj) {
            return this.a.adjacentNodes(obj);
        }

        public Set<N> predecessors(Object obj) {
            return this.a.successors(obj);
        }

        public Set<N> successors(Object obj) {
            return this.a.predecessors(obj);
        }
    }

    static class TransposedNetwork<N, E> extends AbstractNetwork<N, E> {
        /* access modifiers changed from: private */
        public final Network<N, E> a;

        TransposedNetwork(Network<N, E> network) {
            this.a = network;
        }

        public Set<N> nodes() {
            return this.a.nodes();
        }

        public Set<E> edges() {
            return this.a.edges();
        }

        public boolean isDirected() {
            return this.a.isDirected();
        }

        public boolean allowsParallelEdges() {
            return this.a.allowsParallelEdges();
        }

        public boolean allowsSelfLoops() {
            return this.a.allowsSelfLoops();
        }

        public ElementOrder<N> nodeOrder() {
            return this.a.nodeOrder();
        }

        public ElementOrder<E> edgeOrder() {
            return this.a.edgeOrder();
        }

        public Set<N> adjacentNodes(Object obj) {
            return this.a.adjacentNodes(obj);
        }

        public Set<N> predecessors(Object obj) {
            return this.a.successors(obj);
        }

        public Set<N> successors(Object obj) {
            return this.a.predecessors(obj);
        }

        public Set<E> incidentEdges(Object obj) {
            return this.a.incidentEdges(obj);
        }

        public Set<E> inEdges(Object obj) {
            return this.a.outEdges(obj);
        }

        public Set<E> outEdges(Object obj) {
            return this.a.inEdges(obj);
        }

        public EndpointPair<N> incidentNodes(Object obj) {
            EndpointPair incidentNodes = this.a.incidentNodes(obj);
            return EndpointPair.a(this.a, incidentNodes.nodeV(), incidentNodes.nodeU());
        }

        public Set<E> adjacentEdges(Object obj) {
            return this.a.adjacentEdges(obj);
        }

        public Set<E> edgesConnecting(Object obj, Object obj2) {
            return this.a.edgesConnecting(obj2, obj);
        }
    }

    private Graphs() {
    }

    public static boolean hasCycle(Graph<?> graph) {
        int size = graph.edges().size();
        if (size == 0) {
            return false;
        }
        if (!graph.isDirected() && size >= graph.nodes().size()) {
            return true;
        }
        HashMap newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        for (Object a : graph.nodes()) {
            if (a(graph, newHashMapWithExpectedSize, a, null)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected() || !network.allowsParallelEdges() || network.edges().size() <= network.asGraph().edges().size()) {
            return hasCycle(network.asGraph());
        }
        return true;
    }

    private static boolean a(Graph<?> graph, Map<Object, NodeVisitState> map, Object obj, @Nullable Object obj2) {
        NodeVisitState nodeVisitState = (NodeVisitState) map.get(obj);
        if (nodeVisitState == NodeVisitState.COMPLETE) {
            return false;
        }
        if (nodeVisitState == NodeVisitState.PENDING) {
            return true;
        }
        map.put(obj, NodeVisitState.PENDING);
        for (Object next : graph.successors(obj)) {
            if (a(graph, next, obj2) && a(graph, map, next, obj)) {
                return true;
            }
        }
        map.put(obj, NodeVisitState.COMPLETE);
        return false;
    }

    private static boolean a(Graph<?> graph, Object obj, @Nullable Object obj2) {
        return graph.isDirected() || !Objects.equal(obj2, obj);
    }

    public static <N> Graph<N> transitiveClosure(Graph<N> graph) {
        MutableGraph build = GraphBuilder.from(graph).allowsSelfLoops(true).build();
        if (graph.isDirected()) {
            for (Object next : graph.nodes()) {
                for (Object putEdge : reachableNodes(graph, next)) {
                    build.putEdge(next, putEdge);
                }
            }
        } else {
            HashSet hashSet = new HashSet();
            for (Object next2 : graph.nodes()) {
                if (!hashSet.contains(next2)) {
                    Set reachableNodes = reachableNodes(graph, next2);
                    hashSet.addAll(reachableNodes);
                    int i = 1;
                    for (Object next3 : reachableNodes) {
                        int i2 = i + 1;
                        for (Object putEdge2 : Iterables.limit(reachableNodes, i)) {
                            build.putEdge(next3, putEdge2);
                        }
                        i = i2;
                    }
                }
            }
        }
        return build;
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, Object obj) {
        Preconditions.checkArgument(graph.nodes().contains(obj), "Node %s is not an element of this graph.", obj);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        linkedHashSet.add(obj);
        arrayDeque.add(obj);
        while (!arrayDeque.isEmpty()) {
            for (Object next : graph.successors(arrayDeque.remove())) {
                if (linkedHashSet.add(next)) {
                    arrayDeque.add(next);
                }
            }
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public static boolean equivalent(@Nullable Graph<?> graph, @Nullable Graph<?> graph2) {
        boolean z = true;
        if (graph == graph2) {
            return true;
        }
        if (graph == null || graph2 == null) {
            return false;
        }
        if (graph.isDirected() != graph2.isDirected() || !graph.nodes().equals(graph2.nodes()) || !graph.edges().equals(graph2.edges())) {
            z = false;
        }
        return z;
    }

    public static boolean equivalent(@Nullable ValueGraph<?, ?> valueGraph, @Nullable ValueGraph<?, ?> valueGraph2) {
        if (valueGraph == valueGraph2) {
            return true;
        }
        if (valueGraph == null || valueGraph2 == null || valueGraph.isDirected() != valueGraph2.isDirected() || !valueGraph.nodes().equals(valueGraph2.nodes()) || !valueGraph.edges().equals(valueGraph2.edges())) {
            return false;
        }
        for (EndpointPair endpointPair : valueGraph.edges()) {
            if (!valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()).equals(valueGraph2.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equivalent(@Nullable Network<?, ?> network, @Nullable Network<?, ?> network2) {
        if (network == network2) {
            return true;
        }
        if (network == null || network2 == null || network.isDirected() != network2.isDirected() || !network.nodes().equals(network2.nodes()) || !network.edges().equals(network2.edges())) {
            return false;
        }
        for (Object next : network.edges()) {
            if (!network.incidentNodes(next).equals(network2.incidentNodes(next))) {
                return false;
            }
        }
        return true;
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (!graph.isDirected()) {
            return graph;
        }
        if (graph instanceof TransposedGraph) {
            return ((TransposedGraph) graph).a;
        }
        return new TransposedGraph(graph);
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (!valueGraph.isDirected()) {
            return valueGraph;
        }
        if (valueGraph instanceof TransposedValueGraph) {
            return ((TransposedValueGraph) valueGraph).a;
        }
        return new TransposedValueGraph(valueGraph);
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (!network.isDirected()) {
            return network;
        }
        if (network instanceof TransposedNetwork) {
            return ((TransposedNetwork) network).a;
        }
        return new TransposedNetwork(network);
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> iterable) {
        MutableGraph<N> build = GraphBuilder.from(graph).build();
        for (Object addNode : iterable) {
            build.addNode(addNode);
        }
        for (Object next : build.nodes()) {
            for (Object next2 : graph.successors(next)) {
                if (build.nodes().contains(next2)) {
                    build.putEdge(next, next2);
                }
            }
        }
        return build;
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterable) {
        MutableValueGraph<N, V> build = ValueGraphBuilder.from(valueGraph).build();
        for (Object addNode : iterable) {
            build.addNode(addNode);
        }
        for (Object next : build.nodes()) {
            for (Object next2 : valueGraph.successors(next)) {
                if (build.nodes().contains(next2)) {
                    build.putEdgeValue(next, next2, valueGraph.edgeValue(next, next2));
                }
            }
        }
        return build;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterable) {
        MutableNetwork<N, E> build = NetworkBuilder.from(network).build();
        for (Object addNode : iterable) {
            build.addNode(addNode);
        }
        for (Object next : build.nodes()) {
            for (Object next2 : network.outEdges(next)) {
                Object adjacentNode = network.incidentNodes(next2).adjacentNode(next);
                if (build.nodes().contains(adjacentNode)) {
                    build.addEdge(next, adjacentNode, next2);
                }
            }
        }
        return build;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph<N> build = GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        for (Object addNode : graph.nodes()) {
            build.addNode(addNode);
        }
        for (EndpointPair endpointPair : graph.edges()) {
            build.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return build;
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph<N, V> build = ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        for (Object addNode : valueGraph.nodes()) {
            build.addNode(addNode);
        }
        for (EndpointPair endpointPair : valueGraph.edges()) {
            build.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()));
        }
        return build;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork<N, E> build = NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        for (Object addNode : network.nodes()) {
            build.addNode(addNode);
        }
        for (Object next : network.edges()) {
            EndpointPair incidentNodes = network.incidentNodes(next);
            build.addEdge(incidentNodes.nodeU(), incidentNodes.nodeV(), next);
        }
        return build;
    }

    @CanIgnoreReturnValue
    static int a(int i) {
        Preconditions.checkArgument(i >= 0, "Not true that %s is non-negative.", i);
        return i;
    }

    @CanIgnoreReturnValue
    static int b(int i) {
        Preconditions.checkArgument(i > 0, "Not true that %s is positive.", i);
        return i;
    }

    @CanIgnoreReturnValue
    static long a(long j) {
        Preconditions.checkArgument(j >= 0, "Not true that %s is non-negative.", j);
        return j;
    }

    @CanIgnoreReturnValue
    static long b(long j) {
        Preconditions.checkArgument(j > 0, "Not true that %s is positive.", j);
        return j;
    }
}
