package com.google.common.collect;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    static final RegularImmutableBiMap<Object, Object> a;
    private final transient ImmutableMapEntry<K, V>[] c;
    /* access modifiers changed from: private */
    public final transient ImmutableMapEntry<K, V>[] d;
    /* access modifiers changed from: private */
    public final transient Entry<K, V>[] e;
    /* access modifiers changed from: private */
    public final transient int f;
    /* access modifiers changed from: private */
    public final transient int g;
    @RetainedWith
    @LazyInit
    private transient ImmutableBiMap<V, K> h;

    final class Inverse extends ImmutableBiMap<V, K> {

        final class InverseEntrySet extends ImmutableMapEntrySet<V, K> {
            /* access modifiers changed from: 0000 */
            public boolean e() {
                return true;
            }

            InverseEntrySet() {
            }

            /* access modifiers changed from: 0000 */
            public ImmutableMap<V, K> b() {
                return Inverse.this;
            }

            public int hashCode() {
                return RegularImmutableBiMap.this.g;
            }

            public UnmodifiableIterator<Entry<V, K>> iterator() {
                return asList().iterator();
            }

            /* access modifiers changed from: 0000 */
            public ImmutableList<Entry<V, K>> c() {
                return new ImmutableAsList<Entry<V, K>>() {
                    /* renamed from: a */
                    public Entry<V, K> get(int i) {
                        Entry entry = RegularImmutableBiMap.this.e[i];
                        return Maps.immutableEntry(entry.getValue(), entry.getKey());
                    }

                    /* access modifiers changed from: 0000 */
                    public ImmutableCollection<Entry<V, K>> b() {
                        return InverseEntrySet.this;
                    }
                };
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return false;
        }

        private Inverse() {
        }

        public int size() {
            return inverse().size();
        }

        public ImmutableBiMap<K, V> inverse() {
            return RegularImmutableBiMap.this;
        }

        public K get(@Nullable Object obj) {
            if (obj == null || RegularImmutableBiMap.this.d == null) {
                return null;
            }
            for (ImmutableMapEntry immutableMapEntry = RegularImmutableBiMap.this.d[Hashing.a(obj.hashCode()) & RegularImmutableBiMap.this.f]; immutableMapEntry != null; immutableMapEntry = immutableMapEntry.b()) {
                if (obj.equals(immutableMapEntry.getValue())) {
                    return immutableMapEntry.getKey();
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<Entry<V, K>> e() {
            return new InverseEntrySet();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new InverseSerializedForm(RegularImmutableBiMap.this);
        }
    }

    static class InverseSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final ImmutableBiMap<K, V> a;

        InverseSerializedForm(ImmutableBiMap<K, V> immutableBiMap) {
            this.a = immutableBiMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.inverse();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean g() {
        return true;
    }

    static {
        RegularImmutableBiMap regularImmutableBiMap = new RegularImmutableBiMap(null, null, (Entry[]) ImmutableMap.b, 0, 0);
        a = regularImmutableBiMap;
    }

    static <K, V> RegularImmutableBiMap<K, V> a(Entry<K, V>... entryArr) {
        return a(entryArr.length, entryArr);
    }

    static <K, V> RegularImmutableBiMap<K, V> a(int i, Entry<K, V>[] entryArr) {
        Entry<K, V>[] entryArr2;
        ImmutableMapEntry immutableMapEntry;
        int i2 = i;
        Entry<K, V>[] entryArr3 = entryArr;
        Preconditions.checkPositionIndex(i2, entryArr3.length);
        int a2 = Hashing.a(i2, 1.2d);
        int i3 = a2 - 1;
        ImmutableMapEntry[] a3 = ImmutableMapEntry.a(a2);
        ImmutableMapEntry[] a4 = ImmutableMapEntry.a(a2);
        if (i2 == entryArr3.length) {
            entryArr2 = entryArr3;
        } else {
            entryArr2 = ImmutableMapEntry.a(i);
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            Entry<K, V> entry = entryArr3[i4];
            Object key = entry.getKey();
            Object value = entry.getValue();
            CollectPreconditions.a(key, value);
            int hashCode = key.hashCode();
            int hashCode2 = value.hashCode();
            int a5 = Hashing.a(hashCode) & i3;
            int a6 = Hashing.a(hashCode2) & i3;
            ImmutableMapEntry immutableMapEntry2 = a3[a5];
            RegularImmutableMap.a(key, entry, immutableMapEntry2);
            ImmutableMapEntry immutableMapEntry3 = a4[a6];
            a(value, entry, immutableMapEntry3);
            if (immutableMapEntry3 == null && immutableMapEntry2 == null) {
                immutableMapEntry = (entry instanceof ImmutableMapEntry) && ((ImmutableMapEntry) entry).c() ? (ImmutableMapEntry) entry : new ImmutableMapEntry(key, value);
            } else {
                immutableMapEntry = new NonTerminalImmutableBiMapEntry(key, value, immutableMapEntry2, immutableMapEntry3);
            }
            a3[a5] = immutableMapEntry;
            a4[a6] = immutableMapEntry;
            entryArr2[i4] = immutableMapEntry;
            i5 += hashCode ^ hashCode2;
            i4++;
            i2 = i;
        }
        RegularImmutableBiMap regularImmutableBiMap = new RegularImmutableBiMap(a3, a4, entryArr2, i3, i5);
        return regularImmutableBiMap;
    }

    private RegularImmutableBiMap(ImmutableMapEntry<K, V>[] immutableMapEntryArr, ImmutableMapEntry<K, V>[] immutableMapEntryArr2, Entry<K, V>[] entryArr, int i, int i2) {
        this.c = immutableMapEntryArr;
        this.d = immutableMapEntryArr2;
        this.e = entryArr;
        this.f = i;
        this.g = i2;
    }

    private static void a(Object obj, Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> immutableMapEntry) {
        while (immutableMapEntry != null) {
            a(!obj.equals(immutableMapEntry.getValue()), TarjetasConstants.VALUE, entry, immutableMapEntry);
            immutableMapEntry = immutableMapEntry.b();
        }
    }

    @Nullable
    public V get(@Nullable Object obj) {
        if (this.c == null) {
            return null;
        }
        return RegularImmutableMap.a(obj, (ImmutableMapEntry<?, V>[]) this.c, this.f);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<Entry<K, V>> e() {
        return isEmpty() ? ImmutableSet.of() : new RegularEntrySet(this, this.e);
    }

    public int hashCode() {
        return this.g;
    }

    public int size() {
        return this.e.length;
    }

    public ImmutableBiMap<V, K> inverse() {
        if (isEmpty()) {
            return ImmutableBiMap.of();
        }
        ImmutableBiMap<V, K> immutableBiMap = this.h;
        if (immutableBiMap == null) {
            immutableBiMap = new Inverse<>();
            this.h = immutableBiMap;
        }
        return immutableBiMap;
    }
}
