package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final transient Entry<K, V>[] a;
    private final transient ImmutableMapEntry<K, V>[] c;
    private final transient int d;

    @GwtCompatible(emulated = true)
    static final class KeySet<K, V> extends Indexed<K> {
        @Weak
        private final RegularImmutableMap<K, V> a;

        @GwtIncompatible
        static class SerializedForm<K> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<K, ?> a;

            SerializedForm(ImmutableMap<K, ?> immutableMap) {
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

        KeySet(RegularImmutableMap<K, V> regularImmutableMap) {
            this.a = regularImmutableMap;
        }

        /* access modifiers changed from: 0000 */
        public K a(int i) {
            return this.a.a[i].getKey();
        }

        public boolean contains(Object obj) {
            return this.a.containsKey(obj);
        }

        public int size() {
            return this.a.size();
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public Object writeReplace() {
            return new SerializedForm(this.a);
        }
    }

    @GwtCompatible(emulated = true)
    static final class Values<K, V> extends ImmutableList<V> {
        @Weak
        final RegularImmutableMap<K, V> a;

        @GwtIncompatible
        static class SerializedForm<V> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<?, V> a;

            SerializedForm(ImmutableMap<?, V> immutableMap) {
                this.a = immutableMap;
            }

            /* access modifiers changed from: 0000 */
            public Object readResolve() {
                return this.a.values();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return true;
        }

        Values(RegularImmutableMap<K, V> regularImmutableMap) {
            this.a = regularImmutableMap;
        }

        public V get(int i) {
            return this.a.a[i].getValue();
        }

        public int size() {
            return this.a.size();
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public Object writeReplace() {
            return new SerializedForm(this.a);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return false;
    }

    static <K, V> RegularImmutableMap<K, V> a(Entry<K, V>... entryArr) {
        return a(entryArr.length, entryArr);
    }

    static <K, V> RegularImmutableMap<K, V> a(int i, Entry<K, V>[] entryArr) {
        Entry<K, V>[] entryArr2;
        ImmutableMapEntry immutableMapEntry;
        Preconditions.checkPositionIndex(i, entryArr.length);
        if (i == entryArr.length) {
            entryArr2 = entryArr;
        } else {
            entryArr2 = ImmutableMapEntry.a(i);
        }
        int a2 = Hashing.a(i, 1.2d);
        ImmutableMapEntry[] a3 = ImmutableMapEntry.a(a2);
        int i2 = a2 - 1;
        for (int i3 = 0; i3 < i; i3++) {
            ImmutableMapEntry immutableMapEntry2 = entryArr[i3];
            Object key = immutableMapEntry2.getKey();
            Object value = immutableMapEntry2.getValue();
            CollectPreconditions.a(key, value);
            int a4 = Hashing.a(key.hashCode()) & i2;
            ImmutableMapEntry immutableMapEntry3 = a3[a4];
            if (immutableMapEntry3 == null) {
                immutableMapEntry = (immutableMapEntry2 instanceof ImmutableMapEntry) && immutableMapEntry2.c() ? immutableMapEntry2 : new ImmutableMapEntry(key, value);
            } else {
                immutableMapEntry = new NonTerminalImmutableMapEntry(key, value, immutableMapEntry3);
            }
            a3[a4] = immutableMapEntry;
            entryArr2[i3] = immutableMapEntry;
            a(key, (Entry<?, ?>) immutableMapEntry, immutableMapEntry3);
        }
        return new RegularImmutableMap<>(entryArr2, a3, i2);
    }

    private RegularImmutableMap(Entry<K, V>[] entryArr, ImmutableMapEntry<K, V>[] immutableMapEntryArr, int i) {
        this.a = entryArr;
        this.c = immutableMapEntryArr;
        this.d = i;
    }

    static void a(Object obj, Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> immutableMapEntry) {
        while (immutableMapEntry != null) {
            a(!obj.equals(immutableMapEntry.getKey()), "key", entry, immutableMapEntry);
            immutableMapEntry = immutableMapEntry.a();
        }
    }

    public V get(@Nullable Object obj) {
        return a(obj, (ImmutableMapEntry<?, V>[]) this.c, this.d);
    }

    @Nullable
    static <V> V a(@Nullable Object obj, ImmutableMapEntry<?, V>[] immutableMapEntryArr, int i) {
        if (obj == null) {
            return null;
        }
        for (ImmutableMapEntry<?, V> immutableMapEntry = immutableMapEntryArr[i & Hashing.a(obj.hashCode())]; immutableMapEntry != null; immutableMapEntry = immutableMapEntry.a()) {
            if (obj.equals(immutableMapEntry.getKey())) {
                return immutableMapEntry.getValue();
            }
        }
        return null;
    }

    public int size() {
        return this.a.length;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<Entry<K, V>> e() {
        return new RegularEntrySet(this, this.a);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<K> c() {
        return new KeySet(this);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableCollection<V> f() {
        return new Values(this);
    }
}
