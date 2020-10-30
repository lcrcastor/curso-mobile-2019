package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    final K g;
    final V h;

    ImmutableEntry(@Nullable K k, @Nullable V v) {
        this.g = k;
        this.h = v;
    }

    @Nullable
    public final K getKey() {
        return this.g;
    }

    @Nullable
    public final V getValue() {
        return this.h;
    }

    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
