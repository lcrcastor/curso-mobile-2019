package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.AbstractMap.SimpleImmutableEntry;
import javax.annotation.Nullable;

@GwtCompatible
public final class RemovalNotification<K, V> extends SimpleImmutableEntry<K, V> {
    private static final long serialVersionUID = 0;
    private final RemovalCause a;

    public static <K, V> RemovalNotification<K, V> create(@Nullable K k, @Nullable V v, RemovalCause removalCause) {
        return new RemovalNotification<>(k, v, removalCause);
    }

    private RemovalNotification(@Nullable K k, @Nullable V v, RemovalCause removalCause) {
        super(k, v);
        this.a = (RemovalCause) Preconditions.checkNotNull(removalCause);
    }

    public RemovalCause getCause() {
        return this.a;
    }

    public boolean wasEvicted() {
        return this.a.a();
    }
}
