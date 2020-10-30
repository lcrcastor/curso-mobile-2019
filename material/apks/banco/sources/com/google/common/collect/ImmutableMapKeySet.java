package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class ImmutableMapKeySet<K, V> extends Indexed<K> {
    @Weak
    private final ImmutableMap<K, V> a;

    @GwtIncompatible
    static class KeySetSerializedForm<K> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, ?> a;

        KeySetSerializedForm(ImmutableMap<K, ?> immutableMap) {
            this.a = immutableMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.keySet();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return true;
    }

    ImmutableMapKeySet(ImmutableMap<K, V> immutableMap) {
        this.a = immutableMap;
    }

    public int size() {
        return this.a.size();
    }

    public UnmodifiableIterator<K> iterator() {
        return this.a.a();
    }

    public boolean contains(@Nullable Object obj) {
        return this.a.containsKey(obj);
    }

    /* access modifiers changed from: 0000 */
    public K a(int i) {
        return ((Entry) this.a.entrySet().asList().get(i)).getKey();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new KeySetSerializedForm(this.a);
    }
}
