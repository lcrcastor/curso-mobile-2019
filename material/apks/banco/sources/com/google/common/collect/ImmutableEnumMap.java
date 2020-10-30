package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class ImmutableEnumMap<K extends Enum<K>, V> extends IteratorBasedImmutableMap<K, V> {
    private final transient EnumMap<K, V> a;

    static class EnumSerializedForm<K extends Enum<K>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumMap<K, V> a;

        EnumSerializedForm(EnumMap<K, V> enumMap) {
            this.a = enumMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return new ImmutableEnumMap(this.a);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return false;
    }

    static <K extends Enum<K>, V> ImmutableMap<K, V> a(EnumMap<K, V> enumMap) {
        switch (enumMap.size()) {
            case 0:
                return ImmutableMap.of();
            case 1:
                Entry entry = (Entry) Iterables.getOnlyElement(enumMap.entrySet());
                return ImmutableMap.of(entry.getKey(), entry.getValue());
            default:
                return new ImmutableEnumMap(enumMap);
        }
    }

    private ImmutableEnumMap(EnumMap<K, V> enumMap) {
        this.a = enumMap;
        Preconditions.checkArgument(!enumMap.isEmpty());
    }

    /* access modifiers changed from: 0000 */
    public UnmodifiableIterator<K> a() {
        return Iterators.unmodifiableIterator(this.a.keySet().iterator());
    }

    public int size() {
        return this.a.size();
    }

    public boolean containsKey(@Nullable Object obj) {
        return this.a.containsKey(obj);
    }

    public V get(Object obj) {
        return this.a.get(obj);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ImmutableEnumMap) {
            obj = ((ImmutableEnumMap) obj).a;
        }
        return this.a.equals(obj);
    }

    /* access modifiers changed from: 0000 */
    public UnmodifiableIterator<Entry<K, V>> d() {
        return Maps.c(this.a.entrySet().iterator());
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new EnumSerializedForm(this.a);
    }
}
