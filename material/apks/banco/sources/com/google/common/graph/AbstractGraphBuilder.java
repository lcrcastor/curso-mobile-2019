package com.google.common.graph;

import com.google.common.base.Optional;

abstract class AbstractGraphBuilder<N> {
    final boolean a;
    boolean b = false;
    ElementOrder<N> c = ElementOrder.insertion();
    Optional<Integer> d = Optional.absent();

    AbstractGraphBuilder(boolean z) {
        this.a = z;
    }
}
