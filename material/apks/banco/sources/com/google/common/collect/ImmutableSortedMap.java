package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public final class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
    private static final Comparator<Comparable> a = Ordering.natural();
    private static final ImmutableSortedMap<Comparable, Object> c = new ImmutableSortedMap<>(ImmutableSortedSet.a((Comparator<? super E>) Ordering.natural()), ImmutableList.of());
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final transient RegularImmutableSortedSet<K> d;
    /* access modifiers changed from: private */
    public final transient ImmutableList<V> e;
    private transient ImmutableSortedMap<K, V> f;

    public static class Builder<K, V> extends com.google.common.collect.ImmutableMap.Builder<K, V> {
        private final Comparator<? super K> e;

        public Builder(Comparator<? super K> comparator) {
            this.e = (Comparator) Preconditions.checkNotNull(comparator);
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            super.put(k, v);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        public ImmutableSortedMap<K, V> build() {
            switch (this.c) {
                case 0:
                    return ImmutableSortedMap.a(this.e);
                case 1:
                    return ImmutableSortedMap.b(this.e, this.b[0].getKey(), this.b[0].getValue());
                default:
                    return ImmutableSortedMap.b(this.e, false, this.b, this.c);
            }
        }
    }

    static class SerializedForm extends SerializedForm {
        private static final long serialVersionUID = 0;
        private final Comparator<Object> a;

        SerializedForm(ImmutableSortedMap<?, ?> immutableSortedMap) {
            super(immutableSortedMap);
            this.a = immutableSortedMap.comparator();
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return a(new Builder(this.a));
        }
    }

    static <K, V> ImmutableSortedMap<K, V> a(Comparator<? super K> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return of();
        }
        return new ImmutableSortedMap<>(ImmutableSortedSet.a(comparator), ImmutableList.of());
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return c;
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v) {
        return b(Ordering.natural(), k, v);
    }

    /* access modifiers changed from: private */
    public static <K, V> ImmutableSortedMap<K, V> b(Comparator<? super K> comparator, K k, V v) {
        return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.of(k), (Comparator) Preconditions.checkNotNull(comparator)), ImmutableList.of(v));
    }

    private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> a(ImmutableMapEntry<K, V>... immutableMapEntryArr) {
        return b(Ordering.natural(), false, immutableMapEntryArr, immutableMapEntryArr.length);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2) {
        return a((ImmutableMapEntry<K, V>[]) new ImmutableMapEntry[]{a(k, v), a(k2, v2)});
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return a((ImmutableMapEntry<K, V>[]) new ImmutableMapEntry[]{a(k, v), a(k2, v2), a(k3, v3)});
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return a((ImmutableMapEntry<K, V>[]) new ImmutableMapEntry[]{a(k, v), a(k2, v2), a(k3, v3), a(k4, v4)});
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return a((ImmutableMapEntry<K, V>[]) new ImmutableMapEntry[]{a(k, v), a(k2, v2), a(k3, v3), a(k4, v4), a(k5, v5)});
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return a(map, (Comparator<? super K>) (Ordering) a);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return a(map, (Comparator) Preconditions.checkNotNull(comparator));
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
        return copyOf(iterable, (Comparator<? super K>) (Ordering) a);
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> iterable, Comparator<? super K> comparator) {
        return a((Comparator) Preconditions.checkNotNull(comparator), false, iterable);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> sortedMap) {
        Comparator<Comparable> comparator = sortedMap.comparator();
        if (comparator == null) {
            comparator = a;
        }
        if (sortedMap instanceof ImmutableSortedMap) {
            ImmutableSortedMap<K, V> immutableSortedMap = (ImmutableSortedMap) sortedMap;
            if (!immutableSortedMap.b()) {
                return immutableSortedMap;
            }
        }
        return a(comparator, true, (Iterable<? extends Entry<? extends K, ? extends V>>) sortedMap.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> a(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        boolean z = false;
        if (map instanceof SortedMap) {
            Comparator comparator2 = ((SortedMap) map).comparator();
            if (comparator2 != null) {
                z = comparator.equals(comparator2);
            } else if (comparator == a) {
                z = true;
            }
        }
        if (z && (map instanceof ImmutableSortedMap)) {
            ImmutableSortedMap<K, V> immutableSortedMap = (ImmutableSortedMap) map;
            if (!immutableSortedMap.b()) {
                return immutableSortedMap;
            }
        }
        return a(comparator, z, (Iterable<? extends Entry<? extends K, ? extends V>>) map.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> a(Comparator<? super K> comparator, boolean z, Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
        Entry[] entryArr = (Entry[]) Iterables.a(iterable, (T[]) b);
        return b(comparator, z, entryArr, entryArr.length);
    }

    /* access modifiers changed from: private */
    public static <K, V> ImmutableSortedMap<K, V> b(Comparator<? super K> comparator, boolean z, Entry<K, V>[] entryArr, int i) {
        switch (i) {
            case 0:
                return a(comparator);
            case 1:
                return b(comparator, entryArr[0].getKey(), entryArr[0].getValue());
            default:
                Object[] objArr = new Object[i];
                Object[] objArr2 = new Object[i];
                if (z) {
                    for (int i2 = 0; i2 < i; i2++) {
                        Object key = entryArr[i2].getKey();
                        Object value = entryArr[i2].getValue();
                        CollectPreconditions.a(key, value);
                        objArr[i2] = key;
                        objArr2[i2] = value;
                    }
                } else {
                    Arrays.sort(entryArr, 0, i, Ordering.from(comparator).a());
                    Object key2 = entryArr[0].getKey();
                    objArr[0] = key2;
                    objArr2[0] = entryArr[0].getValue();
                    Object obj = key2;
                    int i3 = 1;
                    while (i3 < i) {
                        Object key3 = entryArr[i3].getKey();
                        Object value2 = entryArr[i3].getValue();
                        CollectPreconditions.a(key3, value2);
                        objArr[i3] = key3;
                        objArr2[i3] = value2;
                        a(comparator.compare(obj, key3) != 0, "key", entryArr[i3 - 1], entryArr[i3]);
                        i3++;
                        obj = key3;
                    }
                }
                return new ImmutableSortedMap<>(new RegularImmutableSortedSet(new RegularImmutableList(objArr), comparator), new RegularImmutableList(objArr2));
        }
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder<>(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList) {
        this(regularImmutableSortedSet, immutableList, null);
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.d = regularImmutableSortedSet;
        this.e = immutableList;
        this.f = immutableSortedMap;
    }

    public int size() {
        return this.e.size();
    }

    public V get(@Nullable Object obj) {
        int a2 = this.d.a(obj);
        if (a2 == -1) {
            return null;
        }
        return this.e.get(a2);
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.d.a() || this.e.a();
    }

    public ImmutableSet<Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<Entry<K, V>> e() {
        return isEmpty() ? ImmutableSet.of() : new ImmutableMapEntrySet<K, V>() {
            public UnmodifiableIterator<Entry<K, V>> iterator() {
                return asList().iterator();
            }

            /* access modifiers changed from: 0000 */
            public ImmutableList<Entry<K, V>> c() {
                return new ImmutableAsList<Entry<K, V>>() {
                    /* renamed from: a */
                    public Entry<K, V> get(int i) {
                        return Maps.immutableEntry(ImmutableSortedMap.this.d.asList().get(i), ImmutableSortedMap.this.e.get(i));
                    }

                    /* access modifiers changed from: 0000 */
                    public ImmutableCollection<Entry<K, V>> b() {
                        return AnonymousClass1EntrySet.this;
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public ImmutableMap<K, V> b() {
                return ImmutableSortedMap.this;
            }
        };
    }

    public ImmutableSortedSet<K> keySet() {
        return this.d;
    }

    public ImmutableCollection<V> values() {
        return this.e;
    }

    public Comparator<? super K> comparator() {
        return keySet().comparator();
    }

    public K firstKey() {
        return keySet().first();
    }

    public K lastKey() {
        return keySet().last();
    }

    private ImmutableSortedMap<K, V> a(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i == i2) {
            return a(comparator());
        }
        return new ImmutableSortedMap<>(this.d.a(i, i2), this.e.subList(i, i2));
    }

    public ImmutableSortedMap<K, V> headMap(K k) {
        return headMap(k, false);
    }

    public ImmutableSortedMap<K, V> headMap(K k, boolean z) {
        return a(0, this.d.c(Preconditions.checkNotNull(k), z));
    }

    public ImmutableSortedMap<K, V> subMap(K k, K k2) {
        return subMap(k, true, k2, false);
    }

    public ImmutableSortedMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(k2);
        Preconditions.checkArgument(comparator().compare(k, k2) <= 0, "expected fromKey <= toKey but %s > %s", (Object) k, (Object) k2);
        return headMap(k2, z2).tailMap(k, z);
    }

    public ImmutableSortedMap<K, V> tailMap(K k) {
        return tailMap(k, true);
    }

    public ImmutableSortedMap<K, V> tailMap(K k, boolean z) {
        return a(this.d.d(Preconditions.checkNotNull(k), z), size());
    }

    public Entry<K, V> lowerEntry(K k) {
        return headMap(k, false).lastEntry();
    }

    public K lowerKey(K k) {
        return Maps.b(lowerEntry(k));
    }

    public Entry<K, V> floorEntry(K k) {
        return headMap(k, true).lastEntry();
    }

    public K floorKey(K k) {
        return Maps.b(floorEntry(k));
    }

    public Entry<K, V> ceilingEntry(K k) {
        return tailMap(k, true).firstEntry();
    }

    public K ceilingKey(K k) {
        return Maps.b(ceilingEntry(k));
    }

    public Entry<K, V> higherEntry(K k) {
        return tailMap(k, false).firstEntry();
    }

    public K higherKey(K k) {
        return Maps.b(higherEntry(k));
    }

    public Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Entry) entrySet().asList().get(0);
    }

    public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Entry) entrySet().asList().get(size() - 1);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> immutableSortedMap = this.f;
        if (immutableSortedMap != null) {
            return immutableSortedMap;
        }
        if (isEmpty()) {
            return a((Comparator<? super K>) Ordering.from(comparator()).reverse());
        }
        return new ImmutableSortedMap<>((RegularImmutableSortedSet) this.d.descendingSet(), this.e.reverse(), this);
    }

    public ImmutableSortedSet<K> navigableKeySet() {
        return this.d;
    }

    public ImmutableSortedSet<K> descendingKeySet() {
        return this.d.descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
