package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible
public interface LoadingCache<K, V> extends Function<K, V>, Cache<K, V> {
    @Deprecated
    V apply(K k);

    ConcurrentMap<K, V> asMap();

    V get(K k);

    ImmutableMap<K, V> getAll(Iterable<? extends K> iterable);

    V getUnchecked(K k);

    void refresh(K k);
}
