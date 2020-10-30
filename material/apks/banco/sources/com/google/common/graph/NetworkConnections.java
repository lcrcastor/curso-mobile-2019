package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;

interface NetworkConnections<N, E> {
    N a(Object obj);

    @CanIgnoreReturnValue
    N a(Object obj, boolean z);

    Set<N> a();

    void a(E e, N n);

    void a(E e, N n, boolean z);

    @CanIgnoreReturnValue
    N b(Object obj);

    Set<E> b();

    Set<E> c();

    Set<E> c(Object obj);

    Set<E> d();

    Set<N> e();

    Set<N> f();
}
