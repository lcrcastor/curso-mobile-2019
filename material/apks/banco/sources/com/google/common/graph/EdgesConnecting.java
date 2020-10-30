package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Map;
import javax.annotation.Nullable;

final class EdgesConnecting<E> extends AbstractSet<E> {
    private final Map<?, E> a;
    private final Object b;

    EdgesConnecting(Map<?, E> map, Object obj) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = Preconditions.checkNotNull(obj);
    }

    /* renamed from: a */
    public UnmodifiableIterator<E> iterator() {
        Object b2 = b();
        return b2 == null ? ImmutableSet.of().iterator() : Iterators.singletonIterator(b2);
    }

    public int size() {
        return b() == null ? 0 : 1;
    }

    public boolean contains(@Nullable Object obj) {
        Object b2 = b();
        return b2 != null && b2.equals(obj);
    }

    @Nullable
    private E b() {
        return this.a.get(this.b);
    }
}
