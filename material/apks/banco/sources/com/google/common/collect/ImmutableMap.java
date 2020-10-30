package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMap<K, V> implements Serializable, Map<K, V> {
    static final Entry<?, ?>[] b = new Entry[0];
    @LazyInit
    private transient ImmutableSet<Entry<K, V>> a;
    @LazyInit
    private transient ImmutableSet<K> c;
    @LazyInit
    private transient ImmutableCollection<V> d;
    @LazyInit
    private transient ImmutableSetMultimap<K, V> e;

    static abstract class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
        /* access modifiers changed from: 0000 */
        public abstract UnmodifiableIterator<Entry<K, V>> d();

        IteratorBasedImmutableMap() {
        }

        public /* bridge */ /* synthetic */ Set entrySet() {
            return ImmutableMap.super.entrySet();
        }

        public /* bridge */ /* synthetic */ Set keySet() {
            return ImmutableMap.super.keySet();
        }

        public /* bridge */ /* synthetic */ Collection values() {
            return ImmutableMap.super.values();
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<Entry<K, V>> e() {
            return new ImmutableMapEntrySet<K, V>() {
                /* access modifiers changed from: 0000 */
                public ImmutableMap<K, V> b() {
                    return IteratorBasedImmutableMap.this;
                }

                public UnmodifiableIterator<Entry<K, V>> iterator() {
                    return IteratorBasedImmutableMap.this.d();
                }
            };
        }
    }

    final class MapViewOfValuesAsSingletonSets extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        private MapViewOfValuesAsSingletonSets() {
        }

        public int size() {
            return ImmutableMap.this.size();
        }

        public ImmutableSet<K> keySet() {
            return ImmutableMap.this.keySet();
        }

        public boolean containsKey(@Nullable Object obj) {
            return ImmutableMap.this.containsKey(obj);
        }

        /* renamed from: a */
        public ImmutableSet<V> get(@Nullable Object obj) {
            Object obj2 = ImmutableMap.this.get(obj);
            if (obj2 == null) {
                return null;
            }
            return ImmutableSet.of(obj2);
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return ImmutableMap.this.b();
        }

        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        /* access modifiers changed from: 0000 */
        public boolean g() {
            return ImmutableMap.this.g();
        }

        /* access modifiers changed from: 0000 */
        public UnmodifiableIterator<Entry<K, ImmutableSet<V>>> d() {
            final UnmodifiableIterator it = ImmutableMap.this.entrySet().iterator();
            return new UnmodifiableIterator<Entry<K, ImmutableSet<V>>>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                /* renamed from: a */
                public Entry<K, ImmutableSet<V>> next() {
                    final Entry entry = (Entry) it.next();
                    return new AbstractMapEntry<K, ImmutableSet<V>>() {
                        public K getKey() {
                            return entry.getKey();
                        }

                        /* renamed from: a */
                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.of(entry.getValue());
                        }
                    };
                }
            };
        }
    }

    public static class Builder<K, V> {
        Comparator<? super V> a;
        ImmutableMapEntry<K, V>[] b;
        int c;
        boolean d;

        public Builder() {
            this(4);
        }

        Builder(int i) {
            this.b = new ImmutableMapEntry[i];
            this.c = 0;
            this.d = false;
        }

        private void a(int i) {
            if (i > this.b.length) {
                this.b = (ImmutableMapEntry[]) ObjectArrays.a((T[]) this.b, com.google.common.collect.ImmutableCollection.Builder.a(this.b.length, i));
                this.d = false;
            }
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            a(this.c + 1);
            ImmutableMapEntry<K, V> a2 = ImmutableMap.a(k, v);
            ImmutableMapEntry<K, V>[] immutableMapEntryArr = this.b;
            int i = this.c;
            this.c = i + 1;
            immutableMapEntryArr[i] = a2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return putAll((Iterable<? extends Entry<? extends K, ? extends V>>) map.entrySet());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
            if (iterable instanceof Collection) {
                a(this.c + ((Collection) iterable).size());
            }
            for (Entry put : iterable) {
                put(put);
            }
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            Preconditions.checkState(this.a == null, "valueComparator was already set");
            this.a = (Comparator) Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        public ImmutableMap<K, V> build() {
            boolean z = false;
            switch (this.c) {
                case 0:
                    return ImmutableMap.of();
                case 1:
                    return ImmutableMap.of(this.b[0].getKey(), this.b[0].getValue());
                default:
                    if (this.a != null) {
                        if (this.d) {
                            this.b = (ImmutableMapEntry[]) ObjectArrays.a((T[]) this.b, this.c);
                        }
                        Arrays.sort(this.b, 0, this.c, Ordering.from(this.a).onResultOf(Maps.b()));
                    }
                    if (this.c == this.b.length) {
                        z = true;
                    }
                    this.d = z;
                    return RegularImmutableMap.a(this.c, this.b);
            }
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] a;
        private final Object[] b;

        SerializedForm(ImmutableMap<?, ?> immutableMap) {
            this.a = new Object[immutableMap.size()];
            this.b = new Object[immutableMap.size()];
            Iterator it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                this.a[i] = entry.getKey();
                this.b[i] = entry.getValue();
                i++;
            }
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return a(new Builder(this.a.length));
        }

        /* access modifiers changed from: 0000 */
        public Object a(Builder<Object, Object> builder) {
            for (int i = 0; i < this.a.length; i++) {
                builder.put(this.a[i], this.b[i]);
            }
            return builder.build();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean b();

    /* access modifiers changed from: 0000 */
    public abstract ImmutableSet<Entry<K, V>> e();

    /* access modifiers changed from: 0000 */
    public boolean g() {
        return false;
    }

    public abstract V get(@Nullable Object obj);

    public static <K, V> ImmutableMap<K, V> of() {
        return ImmutableBiMap.of();
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v) {
        return ImmutableBiMap.of(k, v);
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2) {
        return RegularImmutableMap.a((Entry<K, V>[]) new Entry[]{a(k, v), a(k2, v2)});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return RegularImmutableMap.a((Entry<K, V>[]) new Entry[]{a(k, v), a(k2, v2), a(k3, v3)});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return RegularImmutableMap.a((Entry<K, V>[]) new Entry[]{a(k, v), a(k2, v2), a(k3, v3), a(k4, v4)});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return RegularImmutableMap.a((Entry<K, V>[]) new Entry[]{a(k, v), a(k2, v2), a(k3, v3), a(k4, v4), a(k5, v5)});
    }

    static <K, V> ImmutableMapEntry<K, V> a(K k, V v) {
        return new ImmutableMapEntry<>(k, v);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    static void a(boolean z, String str, Entry<?, ?> entry, Entry<?, ?> entry2) {
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("Multiple entries with same ");
            sb.append(str);
            sb.append(": ");
            sb.append(entry);
            sb.append(" and ");
            sb.append(entry2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof ImmutableSortedMap)) {
            ImmutableMap<K, V> immutableMap = (ImmutableMap) map;
            if (!immutableMap.b()) {
                return immutableMap;
            }
        } else if (map instanceof EnumMap) {
            return a((EnumMap) map);
        }
        return copyOf((Iterable<? extends Entry<? extends K, ? extends V>>) map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
        Entry[] entryArr = (Entry[]) Iterables.a(iterable, (T[]) b);
        switch (entryArr.length) {
            case 0:
                return of();
            case 1:
                Entry entry = entryArr[0];
                return of(entry.getKey(), entry.getValue());
            default:
                return RegularImmutableMap.a((Entry<K, V>[]) entryArr);
        }
    }

    private static <K extends Enum<K>, V> ImmutableMap<K, V> a(EnumMap<K, ? extends V> enumMap) {
        EnumMap enumMap2 = new EnumMap(enumMap);
        for (Entry entry : enumMap2.entrySet()) {
            CollectPreconditions.a(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.a(enumMap2);
    }

    ImmutableMap() {
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@Nullable Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(@Nullable Object obj) {
        return values().contains(obj);
    }

    public ImmutableSet<Entry<K, V>> entrySet() {
        ImmutableSet<Entry<K, V>> immutableSet = this.a;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Entry<K, V>> e2 = e();
        this.a = e2;
        return e2;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.c;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<K> c2 = c();
        this.c = c2;
        return c2;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<K> c() {
        return isEmpty() ? ImmutableSet.of() : new ImmutableMapKeySet(this);
    }

    /* access modifiers changed from: 0000 */
    public UnmodifiableIterator<K> a() {
        final UnmodifiableIterator it = entrySet().iterator();
        return new UnmodifiableIterator<K>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public K next() {
                return ((Entry) it.next()).getKey();
            }
        };
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.d;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection<V> f = f();
        this.d = f;
        return f;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableCollection<V> f() {
        return new ImmutableMapValues(this);
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.e;
        if (immutableSetMultimap == null) {
            immutableSetMultimap = new ImmutableSetMultimap<>(new MapViewOfValuesAsSingletonSets(), size(), null);
            this.e = immutableSetMultimap;
        }
        return immutableSetMultimap;
    }

    public boolean equals(@Nullable Object obj) {
        return Maps.f(this, obj);
    }

    public int hashCode() {
        return Sets.a(entrySet());
    }

    public String toString() {
        return Maps.a((Map<?, ?>) this);
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
