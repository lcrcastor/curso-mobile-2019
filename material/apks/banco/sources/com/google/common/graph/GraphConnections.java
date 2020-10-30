package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import javax.annotation.Nullable;

interface GraphConnections<N, V> {
    @Nullable
    V a(Object obj);

    void a(N n, V v);

    @CanIgnoreReturnValue
    V b(N n, V v);

    Set<N> b();

    void b(Object obj);

    @CanIgnoreReturnValue
    V c(Object obj);

    Set<N> c();

    Set<N> d();
}
