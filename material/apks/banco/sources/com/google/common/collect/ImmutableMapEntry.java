package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import javax.annotation.Nullable;

@GwtIncompatible
class ImmutableMapEntry<K, V> extends ImmutableEntry<K, V> {

    static final class NonTerminalImmutableBiMapEntry<K, V> extends NonTerminalImmutableMapEntry<K, V> {
        private final transient ImmutableMapEntry<K, V> a;

        NonTerminalImmutableBiMapEntry(K k, V v, ImmutableMapEntry<K, V> immutableMapEntry, ImmutableMapEntry<K, V> immutableMapEntry2) {
            super(k, v, immutableMapEntry);
            this.a = immutableMapEntry2;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ImmutableMapEntry<K, V> b() {
            return this.a;
        }
    }

    static class NonTerminalImmutableMapEntry<K, V> extends ImmutableMapEntry<K, V> {
        private final transient ImmutableMapEntry<K, V> a;

        /* access modifiers changed from: 0000 */
        public final boolean c() {
            return false;
        }

        NonTerminalImmutableMapEntry(K k, V v, ImmutableMapEntry<K, V> immutableMapEntry) {
            super(k, v);
            this.a = immutableMapEntry;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public final ImmutableMapEntry<K, V> a() {
            return this.a;
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ImmutableMapEntry<K, V> a() {
        return null;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public ImmutableMapEntry<K, V> b() {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return true;
    }

    static <K, V> ImmutableMapEntry<K, V>[] a(int i) {
        return new ImmutableMapEntry[i];
    }

    ImmutableMapEntry(K k, V v) {
        super(k, v);
        CollectPreconditions.a((Object) k, (Object) v);
    }
}
