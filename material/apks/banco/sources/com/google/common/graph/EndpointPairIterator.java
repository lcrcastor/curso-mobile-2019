package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

abstract class EndpointPairIterator<N> extends AbstractIterator<EndpointPair<N>> {
    protected N a;
    protected Iterator<N> b;
    private final Graph<N> c;
    private final Iterator<N> d;

    static final class Directed<N> extends EndpointPairIterator<N> {
        private Directed(Graph<N> graph) {
            super(graph);
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public EndpointPair<N> computeNext() {
            while (!this.b.hasNext()) {
                if (!a()) {
                    return (EndpointPair) endOfData();
                }
            }
            return EndpointPair.ordered(this.a, this.b.next());
        }
    }

    static final class Undirected<N> extends EndpointPairIterator<N> {
        private Set<N> c;

        private Undirected(Graph<N> graph) {
            super(graph);
            this.c = Sets.newHashSetWithExpectedSize(graph.nodes().size());
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public EndpointPair<N> computeNext() {
            while (true) {
                if (this.b.hasNext()) {
                    Object next = this.b.next();
                    if (!this.c.contains(next)) {
                        return EndpointPair.unordered(this.a, next);
                    }
                } else {
                    this.c.add(this.a);
                    if (!a()) {
                        this.c = null;
                        return (EndpointPair) endOfData();
                    }
                }
            }
        }
    }

    static <N> EndpointPairIterator<N> a(Graph<N> graph) {
        return graph.isDirected() ? new Directed(graph) : new Undirected(graph);
    }

    private EndpointPairIterator(Graph<N> graph) {
        this.a = null;
        this.b = ImmutableSet.of().iterator();
        this.c = graph;
        this.d = graph.nodes().iterator();
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        Preconditions.checkState(!this.b.hasNext());
        if (!this.d.hasNext()) {
            return false;
        }
        this.a = this.d.next();
        this.b = this.c.successors(this.a).iterator();
        return true;
    }
}
