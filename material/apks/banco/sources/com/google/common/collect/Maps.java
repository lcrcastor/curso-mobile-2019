package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Maps {
    static final MapJoiner a = Collections2.a.withKeyValueSeparator("=");

    static abstract class AbstractFilteredMap<K, V> extends ViewCachingAbstractMap<K, V> {
        final Map<K, V> a;
        final Predicate<? super Entry<K, V>> b;

        AbstractFilteredMap(Map<K, V> map, Predicate<? super Entry<K, V>> predicate) {
            this.a = map;
            this.b = predicate;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(@Nullable Object obj, @Nullable V v) {
            return this.b.apply(Maps.immutableEntry(obj, v));
        }

        public V put(K k, V v) {
            Preconditions.checkArgument(a(k, v));
            return this.a.put(k, v);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            for (Entry entry : map.entrySet()) {
                Preconditions.checkArgument(a(entry.getKey(), entry.getValue()));
            }
            this.a.putAll(map);
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj) && a(obj, this.a.get(obj));
        }

        public V get(Object obj) {
            V v = this.a.get(obj);
            if (v == null || !a(obj, v)) {
                return null;
            }
            return v;
        }

        public boolean isEmpty() {
            return entrySet().isEmpty();
        }

        public V remove(Object obj) {
            if (containsKey(obj)) {
                return this.a.remove(obj);
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public Collection<V> b() {
            return new FilteredMapValues(this, this.a, this.b);
        }
    }

    static class AsMapView<K, V> extends ViewCachingAbstractMap<K, V> {
        final Function<? super K, V> a;
        private final Set<K> b;

        /* access modifiers changed from: 0000 */
        public Set<K> c() {
            return this.b;
        }

        AsMapView(Set<K> set, Function<? super K, V> function) {
            this.b = (Set) Preconditions.checkNotNull(set);
            this.a = (Function) Preconditions.checkNotNull(function);
        }

        public Set<K> h() {
            return Maps.c(c());
        }

        /* access modifiers changed from: 0000 */
        public Collection<V> b() {
            return Collections2.transform(this.b, this.a);
        }

        public int size() {
            return c().size();
        }

        public boolean containsKey(@Nullable Object obj) {
            return c().contains(obj);
        }

        public V get(@Nullable Object obj) {
            if (Collections2.a((Collection<?>) c(), obj)) {
                return this.a.apply(obj);
            }
            return null;
        }

        public V remove(@Nullable Object obj) {
            if (c().remove(obj)) {
                return this.a.apply(obj);
            }
            return null;
        }

        public void clear() {
            c().clear();
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, V>> a() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: 0000 */
                public Map<K, V> a() {
                    return AsMapView.this;
                }

                public Iterator<Entry<K, V>> iterator() {
                    return Maps.a(AsMapView.this.c(), AsMapView.this.a);
                }
            };
        }
    }

    static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final BiMap<A, B> a;

        BiMapConverter(BiMap<A, B> biMap) {
            this.a = (BiMap) Preconditions.checkNotNull(biMap);
        }

        /* access modifiers changed from: protected */
        public B doForward(A a2) {
            return a(this.a, a2);
        }

        /* access modifiers changed from: protected */
        public A doBackward(B b) {
            return a(this.a.inverse(), b);
        }

        private static <X, Y> Y a(BiMap<X, Y> biMap, X x) {
            Y y = biMap.get(x);
            Preconditions.checkArgument(y != null, "No non-null mapping present for input: %s", (Object) x);
            return y;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof BiMapConverter)) {
                return false;
            }
            return this.a.equals(((BiMapConverter) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Maps.asConverter(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum EntryFunction implements Function<Entry<?, ?>, Object> {
        KEY {
            @Nullable
            /* renamed from: a */
            public Object apply(Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE {
            @Nullable
            /* renamed from: a */
            public Object apply(Entry<?, ?> entry) {
                return entry.getValue();
            }
        }
    }

    public interface EntryTransformer<K, V1, V2> {
        V2 transformEntry(@Nullable K k, @Nullable V1 v1);
    }

    static final class FilteredEntryBiMap<K, V> extends FilteredEntryMap<K, V> implements BiMap<K, V> {
        @RetainedWith
        private final BiMap<V, K> d;

        private static <K, V> Predicate<Entry<V, K>> a(final Predicate<? super Entry<K, V>> predicate) {
            return new Predicate<Entry<V, K>>() {
                /* renamed from: a */
                public boolean apply(Entry<V, K> entry) {
                    return predicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
            };
        }

        FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.d = new FilteredEntryBiMap(biMap.inverse(), a(predicate), this);
        }

        private FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.d = biMap2;
        }

        /* access modifiers changed from: 0000 */
        public BiMap<K, V> c() {
            return (BiMap) this.a;
        }

        public V forcePut(@Nullable K k, @Nullable V v) {
            Preconditions.checkArgument(a(k, v));
            return c().forcePut(k, v);
        }

        public BiMap<V, K> inverse() {
            return this.d;
        }

        public Set<V> values() {
            return this.d.keySet();
        }
    }

    static class FilteredEntryMap<K, V> extends AbstractFilteredMap<K, V> {
        final Set<Entry<K, V>> c;

        class EntrySet extends ForwardingSet<Entry<K, V>> {
            private EntrySet() {
            }

            /* access modifiers changed from: protected */
            public Set<Entry<K, V>> delegate() {
                return FilteredEntryMap.this.c;
            }

            public Iterator<Entry<K, V>> iterator() {
                return new TransformedIterator<Entry<K, V>, Entry<K, V>>(FilteredEntryMap.this.c.iterator()) {
                    /* access modifiers changed from: 0000 */
                    public Entry<K, V> a(final Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>() {
                            /* access modifiers changed from: protected */
                            public Entry<K, V> delegate() {
                                return entry;
                            }

                            public V setValue(V v) {
                                Preconditions.checkArgument(FilteredEntryMap.this.a(getKey(), v));
                                return super.setValue(v);
                            }
                        };
                    }
                };
            }
        }

        class KeySet extends KeySet<K, V> {
            KeySet() {
                super(FilteredEntryMap.this);
            }

            public boolean remove(Object obj) {
                if (!FilteredEntryMap.this.containsKey(obj)) {
                    return false;
                }
                FilteredEntryMap.this.a.remove(obj);
                return true;
            }

            private boolean a(Predicate<? super K> predicate) {
                return Iterables.removeIf(FilteredEntryMap.this.a.entrySet(), Predicates.and(FilteredEntryMap.this.b, Maps.a(predicate)));
            }

            public boolean removeAll(Collection<?> collection) {
                return a(Predicates.in(collection));
            }

            public boolean retainAll(Collection<?> collection) {
                return a(Predicates.not(Predicates.in(collection)));
            }

            public Object[] toArray() {
                return Lists.newArrayList(iterator()).toArray();
            }

            public <T> T[] toArray(T[] tArr) {
                return Lists.newArrayList(iterator()).toArray(tArr);
            }
        }

        FilteredEntryMap(Map<K, V> map, Predicate<? super Entry<K, V>> predicate) {
            super(map, predicate);
            this.c = Sets.filter(map.entrySet(), this.b);
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, V>> a() {
            return new EntrySet();
        }

        /* access modifiers changed from: 0000 */
        public Set<K> h() {
            return new KeySet();
        }
    }

    @GwtIncompatible
    static class FilteredEntryNavigableMap<K, V> extends AbstractNavigableMap<K, V> {
        /* access modifiers changed from: private */
        public final NavigableMap<K, V> a;
        /* access modifiers changed from: private */
        public final Predicate<? super Entry<K, V>> b;
        private final Map<K, V> c;

        FilteredEntryNavigableMap(NavigableMap<K, V> navigableMap, Predicate<? super Entry<K, V>> predicate) {
            this.a = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.b = predicate;
            this.c = new FilteredEntryMap(navigableMap, predicate);
        }

        public Comparator<? super K> comparator() {
            return this.a.comparator();
        }

        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this) {
                public boolean removeAll(Collection<?> collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.this.a.entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.this.b, Maps.a(Predicates.in(collection))));
                }

                public boolean retainAll(Collection<?> collection) {
                    return Iterators.removeIf(FilteredEntryNavigableMap.this.a.entrySet().iterator(), Predicates.and(FilteredEntryNavigableMap.this.b, Maps.a(Predicates.not(Predicates.in(collection)))));
                }
            };
        }

        public Collection<V> values() {
            return new FilteredMapValues(this, this.a, this.b);
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> b() {
            return Iterators.filter(this.a.entrySet().iterator(), this.b);
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> a() {
            return Iterators.filter(this.a.descendingMap().entrySet().iterator(), this.b);
        }

        public int size() {
            return this.c.size();
        }

        public boolean isEmpty() {
            return !Iterables.any(this.a.entrySet(), this.b);
        }

        @Nullable
        public V get(@Nullable Object obj) {
            return this.c.get(obj);
        }

        public boolean containsKey(@Nullable Object obj) {
            return this.c.containsKey(obj);
        }

        public V put(K k, V v) {
            return this.c.put(k, v);
        }

        public V remove(@Nullable Object obj) {
            return this.c.remove(obj);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            this.c.putAll(map);
        }

        public void clear() {
            this.c.clear();
        }

        public Set<Entry<K, V>> entrySet() {
            return this.c.entrySet();
        }

        public Entry<K, V> pollFirstEntry() {
            return (Entry) Iterables.a((Iterable<T>) this.a.entrySet(), this.b);
        }

        public Entry<K, V> pollLastEntry() {
            return (Entry) Iterables.a((Iterable<T>) this.a.descendingMap().entrySet(), this.b);
        }

        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries(this.a.descendingMap(), this.b);
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.filterEntries(this.a.subMap(k, z, k2, z2), this.b);
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.filterEntries(this.a.headMap(k, z), this.b);
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.filterEntries(this.a.tailMap(k, z), this.b);
        }
    }

    static abstract class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
        /* access modifiers changed from: 0000 */
        public abstract Iterator<Entry<K, V>> b();

        public abstract int size();

        IteratorBasedAbstractMap() {
        }

        public Set<Entry<K, V>> entrySet() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: 0000 */
                public Map<K, V> a() {
                    return IteratorBasedAbstractMap.this;
                }

                public Iterator<Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.b();
                }
            };
        }

        public void clear() {
            Iterators.b(b());
        }
    }

    static class KeySet<K, V> extends ImprovedAbstractSet<K> {
        @Weak
        final Map<K, V> d;

        KeySet(Map<K, V> map) {
            this.d = (Map) Preconditions.checkNotNull(map);
        }

        /* access modifiers changed from: 0000 */
        public Map<K, V> c() {
            return this.d;
        }

        public Iterator<K> iterator() {
            return Maps.a(c().entrySet().iterator());
        }

        public int size() {
            return c().size();
        }

        public boolean isEmpty() {
            return c().isEmpty();
        }

        public boolean contains(Object obj) {
            return c().containsKey(obj);
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            c().remove(obj);
            return true;
        }

        public void clear() {
            c().clear();
        }
    }

    @GwtIncompatible
    static final class NavigableAsMapView<K, V> extends AbstractNavigableMap<K, V> {
        private final NavigableSet<K> a;
        private final Function<? super K, V> b;

        NavigableAsMapView(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.a = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.asMap(this.a.subSet(k, z, k2, z2), this.b);
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.asMap(this.a.headSet(k, z), this.b);
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.asMap(this.a.tailSet(k, z), this.b);
        }

        public Comparator<? super K> comparator() {
            return this.a.comparator();
        }

        @Nullable
        public V get(@Nullable Object obj) {
            if (Collections2.a((Collection<?>) this.a, obj)) {
                return this.b.apply(obj);
            }
            return null;
        }

        public void clear() {
            this.a.clear();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> b() {
            return Maps.a((Set<K>) this.a, this.b);
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> a() {
            return descendingMap().entrySet().iterator();
        }

        public NavigableSet<K> navigableKeySet() {
            return Maps.b(this.a);
        }

        public int size() {
            return this.a.size();
        }

        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap(this.a.descendingSet(), this.b);
        }
    }

    static class TransformedEntriesMap<K, V1, V2> extends IteratorBasedAbstractMap<K, V2> {
        final Map<K, V1> a;
        final EntryTransformer<? super K, ? super V1, V2> b;

        TransformedEntriesMap(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.a = (Map) Preconditions.checkNotNull(map);
            this.b = (EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        public int size() {
            return this.a.size();
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        public V2 get(Object obj) {
            Object obj2 = this.a.get(obj);
            if (obj2 != null || this.a.containsKey(obj)) {
                return this.b.transformEntry(obj, obj2);
            }
            return null;
        }

        public V2 remove(Object obj) {
            if (this.a.containsKey(obj)) {
                return this.b.transformEntry(obj, this.a.remove(obj));
            }
            return null;
        }

        public void clear() {
            this.a.clear();
        }

        public Set<K> keySet() {
            return this.a.keySet();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V2>> b() {
            return Iterators.transform(this.a.entrySet().iterator(), Maps.b(this.b));
        }

        public Collection<V2> values() {
            return new Values(this);
        }
    }

    @GwtIncompatible
    static class TransformedEntriesNavigableMap<K, V1, V2> extends TransformedEntriesSortedMap<K, V1, V2> implements NavigableMap<K, V2> {
        TransformedEntriesNavigableMap(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(navigableMap, entryTransformer);
        }

        public Entry<K, V2> ceilingEntry(K k) {
            return a(c().ceilingEntry(k));
        }

        public K ceilingKey(K k) {
            return c().ceilingKey(k);
        }

        public NavigableSet<K> descendingKeySet() {
            return c().descendingKeySet();
        }

        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries(c().descendingMap(), this.b);
        }

        public Entry<K, V2> firstEntry() {
            return a(c().firstEntry());
        }

        public Entry<K, V2> floorEntry(K k) {
            return a(c().floorEntry(k));
        }

        public K floorKey(K k) {
            return c().floorKey(k);
        }

        /* renamed from: a */
        public NavigableMap<K, V2> headMap(K k) {
            return headMap(k, false);
        }

        public NavigableMap<K, V2> headMap(K k, boolean z) {
            return Maps.transformEntries(c().headMap(k, z), this.b);
        }

        public Entry<K, V2> higherEntry(K k) {
            return a(c().higherEntry(k));
        }

        public K higherKey(K k) {
            return c().higherKey(k);
        }

        public Entry<K, V2> lastEntry() {
            return a(c().lastEntry());
        }

        public Entry<K, V2> lowerEntry(K k) {
            return a(c().lowerEntry(k));
        }

        public K lowerKey(K k) {
            return c().lowerKey(k);
        }

        public NavigableSet<K> navigableKeySet() {
            return c().navigableKeySet();
        }

        public Entry<K, V2> pollFirstEntry() {
            return a(c().pollFirstEntry());
        }

        public Entry<K, V2> pollLastEntry() {
            return a(c().pollLastEntry());
        }

        public NavigableMap<K, V2> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.transformEntries(c().subMap(k, z, k2, z2), this.b);
        }

        /* renamed from: a */
        public NavigableMap<K, V2> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        /* renamed from: b */
        public NavigableMap<K, V2> tailMap(K k) {
            return tailMap(k, true);
        }

        public NavigableMap<K, V2> tailMap(K k, boolean z) {
            return Maps.transformEntries(c().tailMap(k, z), this.b);
        }

        @Nullable
        private Entry<K, V2> a(@Nullable Entry<K, V1> entry) {
            if (entry == null) {
                return null;
            }
            return Maps.a(this.b, entry);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public NavigableMap<K, V1> c() {
            return (NavigableMap) super.c();
        }
    }

    static class TransformedEntriesSortedMap<K, V1, V2> extends TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2> {
        /* access modifiers changed from: protected */
        public SortedMap<K, V1> c() {
            return (SortedMap) this.a;
        }

        TransformedEntriesSortedMap(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(sortedMap, entryTransformer);
        }

        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        public K firstKey() {
            return c().firstKey();
        }

        public SortedMap<K, V2> headMap(K k) {
            return Maps.transformEntries(c().headMap(k), this.b);
        }

        public K lastKey() {
            return c().lastKey();
        }

        public SortedMap<K, V2> subMap(K k, K k2) {
            return Maps.transformEntries(c().subMap(k, k2), this.b);
        }

        public SortedMap<K, V2> tailMap(K k) {
            return Maps.transformEntries(c().tailMap(k), this.b);
        }
    }

    static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> a;
        final BiMap<? extends K, ? extends V> b;
        @RetainedWith
        BiMap<V, K> c;
        transient Set<V> d;

        UnmodifiableBiMap(BiMap<? extends K, ? extends V> biMap, @Nullable BiMap<V, K> biMap2) {
            this.a = Collections.unmodifiableMap(biMap);
            this.b = biMap;
            this.c = biMap2;
        }

        /* access modifiers changed from: protected */
        public Map<K, V> delegate() {
            return this.a;
        }

        public V forcePut(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap = this.c;
            if (biMap != null) {
                return biMap;
            }
            UnmodifiableBiMap unmodifiableBiMap = new UnmodifiableBiMap(this.b.inverse(), this);
            this.c = unmodifiableBiMap;
            return unmodifiableBiMap;
        }

        public Set<V> values() {
            Set<V> set = this.d;
            if (set != null) {
                return set;
            }
            Set<V> unmodifiableSet = Collections.unmodifiableSet(this.b.values());
            this.d = unmodifiableSet;
            return unmodifiableSet;
        }
    }

    static class UnmodifiableEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
        private final Collection<Entry<K, V>> a;

        UnmodifiableEntries(Collection<Entry<K, V>> collection) {
            this.a = collection;
        }

        /* access modifiers changed from: protected */
        public Collection<Entry<K, V>> delegate() {
            return this.a;
        }

        public Iterator<Entry<K, V>> iterator() {
            return Maps.c(this.a.iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }
    }

    static class UnmodifiableEntrySet<K, V> extends UnmodifiableEntries<K, V> implements Set<Entry<K, V>> {
        UnmodifiableEntrySet(Set<Entry<K, V>> set) {
            super(set);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }

        public int hashCode() {
            return Sets.a(this);
        }
    }

    static class ValueDifferenceImpl<V> implements ValueDifference<V> {
        private final V a;
        private final V b;

        static <V> ValueDifference<V> a(@Nullable V v, @Nullable V v2) {
            return new ValueDifferenceImpl(v, v2);
        }

        private ValueDifferenceImpl(@Nullable V v, @Nullable V v2) {
            this.a = v;
            this.b = v2;
        }

        public V leftValue() {
            return this.a;
        }

        public V rightValue() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof ValueDifference)) {
                return false;
            }
            ValueDifference valueDifference = (ValueDifference) obj;
            if (Objects.equal(this.a, valueDifference.leftValue()) && Objects.equal(this.b, valueDifference.rightValue())) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static class Values<K, V> extends AbstractCollection<V> {
        @Weak
        final Map<K, V> c;

        Values(Map<K, V> map) {
            this.c = (Map) Preconditions.checkNotNull(map);
        }

        /* access modifiers changed from: 0000 */
        public final Map<K, V> a() {
            return this.c;
        }

        public Iterator<V> iterator() {
            return Maps.b(a().entrySet().iterator());
        }

        public boolean remove(Object obj) {
            try {
                return super.remove(obj);
            } catch (UnsupportedOperationException unused) {
                for (Entry entry : a().entrySet()) {
                    if (Objects.equal(obj, entry.getValue())) {
                        a().remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSet = Sets.newHashSet();
                for (Entry entry : a().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        newHashSet.add(entry.getKey());
                    }
                }
                return a().keySet().removeAll(newHashSet);
            }
        }

        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSet = Sets.newHashSet();
                for (Entry entry : a().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        newHashSet.add(entry.getKey());
                    }
                }
                return a().keySet().retainAll(newHashSet);
            }
        }

        public int size() {
            return a().size();
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public boolean contains(@Nullable Object obj) {
            return a().containsValue(obj);
        }

        public void clear() {
            a().clear();
        }
    }

    @GwtCompatible
    static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
        private transient Set<Entry<K, V>> a;
        private transient Set<K> b;
        private transient Collection<V> c;

        /* access modifiers changed from: 0000 */
        public abstract Set<Entry<K, V>> a();

        ViewCachingAbstractMap() {
        }

        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> set = this.a;
            if (set != null) {
                return set;
            }
            Set<Entry<K, V>> a2 = a();
            this.a = a2;
            return a2;
        }

        public Set<K> keySet() {
            Set<K> set = this.b;
            if (set != null) {
                return set;
            }
            Set<K> h = h();
            this.b = h;
            return h;
        }

        /* access modifiers changed from: 0000 */
        public Set<K> h() {
            return new KeySet(this);
        }

        public Collection<V> values() {
            Collection<V> collection = this.c;
            if (collection != null) {
                return collection;
            }
            Collection<V> b2 = b();
            this.c = b2;
            return b2;
        }

        /* access modifiers changed from: 0000 */
        public Collection<V> b() {
            return new Values(this);
        }
    }

    @GwtIncompatible
    static abstract class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
        private transient Comparator<? super K> a;
        private transient Set<Entry<K, V>> b;
        private transient NavigableSet<K> c;

        /* access modifiers changed from: 0000 */
        public abstract NavigableMap<K, V> a();

        /* access modifiers changed from: 0000 */
        public abstract Iterator<Entry<K, V>> entryIterator();

        DescendingMap() {
        }

        /* access modifiers changed from: protected */
        public final Map<K, V> delegate() {
            return a();
        }

        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator = this.a;
            if (comparator != null) {
                return comparator;
            }
            Comparator comparator2 = a().comparator();
            if (comparator2 == null) {
                comparator2 = Ordering.natural();
            }
            Ordering a2 = a(comparator2);
            this.a = a2;
            return a2;
        }

        private static <T> Ordering<T> a(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        public K firstKey() {
            return a().lastKey();
        }

        public K lastKey() {
            return a().firstKey();
        }

        public Entry<K, V> lowerEntry(K k) {
            return a().higherEntry(k);
        }

        public K lowerKey(K k) {
            return a().higherKey(k);
        }

        public Entry<K, V> floorEntry(K k) {
            return a().ceilingEntry(k);
        }

        public K floorKey(K k) {
            return a().ceilingKey(k);
        }

        public Entry<K, V> ceilingEntry(K k) {
            return a().floorEntry(k);
        }

        public K ceilingKey(K k) {
            return a().floorKey(k);
        }

        public Entry<K, V> higherEntry(K k) {
            return a().lowerEntry(k);
        }

        public K higherKey(K k) {
            return a().lowerKey(k);
        }

        public Entry<K, V> firstEntry() {
            return a().lastEntry();
        }

        public Entry<K, V> lastEntry() {
            return a().firstEntry();
        }

        public Entry<K, V> pollFirstEntry() {
            return a().pollLastEntry();
        }

        public Entry<K, V> pollLastEntry() {
            return a().pollFirstEntry();
        }

        public NavigableMap<K, V> descendingMap() {
            return a();
        }

        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> set = this.b;
            if (set != null) {
                return set;
            }
            Set<Entry<K, V>> b2 = b();
            this.b = b2;
            return b2;
        }

        /* access modifiers changed from: 0000 */
        public Set<Entry<K, V>> b() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: 0000 */
                public Map<K, V> a() {
                    return DescendingMap.this;
                }

                public Iterator<Entry<K, V>> iterator() {
                    return DescendingMap.this.entryIterator();
                }
            };
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet = this.c;
            if (navigableSet != null) {
                return navigableSet;
            }
            NavigableKeySet navigableKeySet = new NavigableKeySet(this);
            this.c = navigableKeySet;
            return navigableKeySet;
        }

        public NavigableSet<K> descendingKeySet() {
            return a().navigableKeySet();
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return a().subMap(k2, z2, k, z).descendingMap();
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return a().tailMap(k, z).descendingMap();
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return a().headMap(k, z).descendingMap();
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }

        public Collection<V> values() {
            return new Values(this);
        }

        public String toString() {
            return standardToString();
        }
    }

    static abstract class EntrySet<K, V> extends ImprovedAbstractSet<Entry<K, V>> {
        /* access modifiers changed from: 0000 */
        public abstract Map<K, V> a();

        EntrySet() {
        }

        public int size() {
            return a().size();
        }

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            Object a = Maps.a(a(), key);
            if (Objects.equal(a, entry.getValue()) && (a != null || a().containsKey(key))) {
                z = true;
            }
            return z;
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            return a().keySet().remove(((Entry) obj).getKey());
        }

        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                return Sets.a((Set<?>) this, collection.iterator());
            }
        }

        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(collection.size());
                for (Object next : collection) {
                    if (contains(next)) {
                        newHashSetWithExpectedSize.add(((Entry) next).getKey());
                    }
                }
                return a().keySet().retainAll(newHashSetWithExpectedSize);
            }
        }
    }

    static class FilteredEntrySortedMap<K, V> extends FilteredEntryMap<K, V> implements SortedMap<K, V> {

        class SortedKeySet extends KeySet implements SortedSet<K> {
            SortedKeySet() {
                super();
            }

            public Comparator<? super K> comparator() {
                return FilteredEntrySortedMap.this.c().comparator();
            }

            public SortedSet<K> subSet(K k, K k2) {
                return (SortedSet) FilteredEntrySortedMap.this.subMap(k, k2).keySet();
            }

            public SortedSet<K> headSet(K k) {
                return (SortedSet) FilteredEntrySortedMap.this.headMap(k).keySet();
            }

            public SortedSet<K> tailSet(K k) {
                return (SortedSet) FilteredEntrySortedMap.this.tailMap(k).keySet();
            }

            public K first() {
                return FilteredEntrySortedMap.this.firstKey();
            }

            public K last() {
                return FilteredEntrySortedMap.this.lastKey();
            }
        }

        FilteredEntrySortedMap(SortedMap<K, V> sortedMap, Predicate<? super Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        /* access modifiers changed from: 0000 */
        public SortedMap<K, V> c() {
            return (SortedMap) this.a;
        }

        /* renamed from: d */
        public SortedSet<K> keySet() {
            return (SortedSet) super.keySet();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: e */
        public SortedSet<K> h() {
            return new SortedKeySet();
        }

        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        public K firstKey() {
            return keySet().iterator().next();
        }

        public K lastKey() {
            SortedMap c = c();
            while (true) {
                K lastKey = c.lastKey();
                if (a(lastKey, this.a.get(lastKey))) {
                    return lastKey;
                }
                c = c().headMap(lastKey);
            }
        }

        public SortedMap<K, V> headMap(K k) {
            return new FilteredEntrySortedMap(c().headMap(k), this.b);
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return new FilteredEntrySortedMap(c().subMap(k, k2), this.b);
        }

        public SortedMap<K, V> tailMap(K k) {
            return new FilteredEntrySortedMap(c().tailMap(k), this.b);
        }
    }

    static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
        final Predicate<? super K> c;

        FilteredKeyMap(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.c = predicate;
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, V>> a() {
            return Sets.filter(this.a.entrySet(), this.b);
        }

        /* access modifiers changed from: 0000 */
        public Set<K> h() {
            return Sets.filter(this.a.keySet(), this.c);
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj) && this.c.apply(obj);
        }
    }

    static final class FilteredMapValues<K, V> extends Values<K, V> {
        final Map<K, V> a;
        final Predicate<? super Entry<K, V>> b;

        FilteredMapValues(Map<K, V> map, Map<K, V> map2, Predicate<? super Entry<K, V>> predicate) {
            super(map);
            this.a = map2;
            this.b = predicate;
        }

        public boolean remove(Object obj) {
            return Iterables.a((Iterable<T>) this.a.entrySet(), Predicates.and(this.b, Maps.b(Predicates.equalTo(obj)))) != null;
        }

        private boolean a(Predicate<? super V> predicate) {
            return Iterables.removeIf(this.a.entrySet(), Predicates.and(this.b, Maps.b(predicate)));
        }

        public boolean removeAll(Collection<?> collection) {
            return a(Predicates.in(collection));
        }

        public boolean retainAll(Collection<?> collection) {
            return a(Predicates.not(Predicates.in(collection)));
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    static class MapDifferenceImpl<K, V> implements MapDifference<K, V> {
        final Map<K, V> a;
        final Map<K, V> b;
        final Map<K, V> c;
        final Map<K, ValueDifference<V>> d;

        MapDifferenceImpl(Map<K, V> map, Map<K, V> map2, Map<K, V> map3, Map<K, ValueDifference<V>> map4) {
            this.a = Maps.c(map);
            this.b = Maps.c(map2);
            this.c = Maps.c(map3);
            this.d = Maps.c(map4);
        }

        public boolean areEqual() {
            return this.a.isEmpty() && this.b.isEmpty() && this.d.isEmpty();
        }

        public Map<K, V> entriesOnlyOnLeft() {
            return this.a;
        }

        public Map<K, V> entriesOnlyOnRight() {
            return this.b;
        }

        public Map<K, V> entriesInCommon() {
            return this.c;
        }

        public Map<K, ValueDifference<V>> entriesDiffering() {
            return this.d;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MapDifference)) {
                return false;
            }
            MapDifference mapDifference = (MapDifference) obj;
            if (!entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) || !entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) || !entriesInCommon().equals(mapDifference.entriesInCommon()) || !entriesDiffering().equals(mapDifference.entriesDiffering())) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering());
        }

        public String toString() {
            if (areEqual()) {
                return "equal";
            }
            StringBuilder sb = new StringBuilder("not equal");
            if (!this.a.isEmpty()) {
                sb.append(": only on left=");
                sb.append(this.a);
            }
            if (!this.b.isEmpty()) {
                sb.append(": only on right=");
                sb.append(this.b);
            }
            if (!this.d.isEmpty()) {
                sb.append(": value differences=");
                sb.append(this.d);
            }
            return sb.toString();
        }
    }

    @GwtIncompatible
    static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public NavigableMap<K, V> c() {
            return (NavigableMap) this.d;
        }

        public K lower(K k) {
            return c().lowerKey(k);
        }

        public K floor(K k) {
            return c().floorKey(k);
        }

        public K ceiling(K k) {
            return c().ceilingKey(k);
        }

        public K higher(K k) {
            return c().higherKey(k);
        }

        public K pollFirst() {
            return Maps.b(c().pollFirstEntry());
        }

        public K pollLast() {
            return Maps.b(c().pollLastEntry());
        }

        public NavigableSet<K> descendingSet() {
            return c().descendingKeySet();
        }

        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return c().subMap(k, z, k2, z2).navigableKeySet();
        }

        public NavigableSet<K> headSet(K k, boolean z) {
            return c().headMap(k, z).navigableKeySet();
        }

        public NavigableSet<K> tailSet(K k, boolean z) {
            return c().tailMap(k, z).navigableKeySet();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        public SortedSet<K> headSet(K k) {
            return headSet(k, false);
        }

        public SortedSet<K> tailSet(K k) {
            return tailSet(k, true);
        }
    }

    static class SortedAsMapView<K, V> extends AsMapView<K, V> implements SortedMap<K, V> {
        SortedAsMapView(SortedSet<K> sortedSet, Function<? super K, V> function) {
            super(sortedSet, function);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public SortedSet<K> c() {
            return (SortedSet) super.c();
        }

        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        public Set<K> keySet() {
            return Maps.b(c());
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return Maps.asMap(c().subSet(k, k2), this.a);
        }

        public SortedMap<K, V> headMap(K k) {
            return Maps.asMap(c().headSet(k), this.a);
        }

        public SortedMap<K, V> tailMap(K k) {
            return Maps.asMap(c().tailSet(k), this.a);
        }

        public K firstKey() {
            return c().first();
        }

        public K lastKey() {
            return c().last();
        }
    }

    static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
        SortedKeySet(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public SortedMap<K, V> c() {
            return (SortedMap) super.c();
        }

        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(c().subMap(k, k2));
        }

        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(c().headMap(k));
        }

        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(c().tailMap(k));
        }

        public K first() {
            return c().firstKey();
        }

        public K last() {
            return c().lastKey();
        }
    }

    static class SortedMapDifferenceImpl<K, V> extends MapDifferenceImpl<K, V> implements SortedMapDifference<K, V> {
        SortedMapDifferenceImpl(SortedMap<K, V> sortedMap, SortedMap<K, V> sortedMap2, SortedMap<K, V> sortedMap3, SortedMap<K, ValueDifference<V>> sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }

        public SortedMap<K, ValueDifference<V>> entriesDiffering() {
            return (SortedMap) super.entriesDiffering();
        }

        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap) super.entriesInCommon();
        }

        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap) super.entriesOnlyOnLeft();
        }

        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap) super.entriesOnlyOnRight();
        }
    }

    @GwtIncompatible
    static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements Serializable, NavigableMap<K, V> {
        private final NavigableMap<K, ? extends V> a;
        private transient UnmodifiableNavigableMap<K, V> b;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
            this.a = navigableMap;
        }

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap, UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap) {
            this.a = navigableMap;
            this.b = unmodifiableNavigableMap;
        }

        /* access modifiers changed from: protected */
        public SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.a);
        }

        public Entry<K, V> lowerEntry(K k) {
            return Maps.e(this.a.lowerEntry(k));
        }

        public K lowerKey(K k) {
            return this.a.lowerKey(k);
        }

        public Entry<K, V> floorEntry(K k) {
            return Maps.e(this.a.floorEntry(k));
        }

        public K floorKey(K k) {
            return this.a.floorKey(k);
        }

        public Entry<K, V> ceilingEntry(K k) {
            return Maps.e(this.a.ceilingEntry(k));
        }

        public K ceilingKey(K k) {
            return this.a.ceilingKey(k);
        }

        public Entry<K, V> higherEntry(K k) {
            return Maps.e(this.a.higherEntry(k));
        }

        public K higherKey(K k) {
            return this.a.higherKey(k);
        }

        public Entry<K, V> firstEntry() {
            return Maps.e(this.a.firstEntry());
        }

        public Entry<K, V> lastEntry() {
            return Maps.e(this.a.lastEntry());
        }

        public final Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        public final Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        public NavigableMap<K, V> descendingMap() {
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap = this.b;
            if (unmodifiableNavigableMap != null) {
                return unmodifiableNavigableMap;
            }
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap2 = new UnmodifiableNavigableMap<>(this.a.descendingMap(), this);
            this.b = unmodifiableNavigableMap2;
            return unmodifiableNavigableMap2;
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.a.navigableKeySet());
        }

        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.a.descendingKeySet());
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.unmodifiableNavigableMap(this.a.subMap(k, z, k2, z2));
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.unmodifiableNavigableMap(this.a.headMap(k, z));
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.unmodifiableNavigableMap(this.a.tailMap(k, z));
        }
    }

    private Maps() {
    }

    static <K> Function<Entry<K, ?>, K> a() {
        return EntryFunction.KEY;
    }

    static <V> Function<Entry<?, V>, V> b() {
        return EntryFunction.VALUE;
    }

    static <K, V> Iterator<K> a(Iterator<Entry<K, V>> it) {
        return Iterators.transform(it, a());
    }

    static <K, V> Iterator<V> b(Iterator<Entry<K, V>> it) {
        return Iterators.transform(it, b());
    }

    @GwtCompatible(serializable = true)
    @Beta
    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
        if (map instanceof ImmutableEnumMap) {
            return (ImmutableEnumMap) map;
        }
        if (map.isEmpty()) {
            return ImmutableMap.of();
        }
        for (Entry entry : map.entrySet()) {
            Preconditions.checkNotNull(entry.getKey());
            Preconditions.checkNotNull(entry.getValue());
        }
        return ImmutableEnumMap.a(new EnumMap(map));
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i) {
        return new HashMap<>(a(i));
    }

    static int a(int i) {
        if (i >= 3) {
            return i < 1073741824 ? (int) ((((float) i) / 0.75f) + 1.0f) : SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }
        CollectPreconditions.a(i, "expectedSize");
        return i + 1;
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int i) {
        return new LinkedHashMap<>(a(i));
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<>(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new MapMaker().makeMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
        return new TreeMap<>(sortedMap);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> comparator) {
        return new TreeMap<>(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> cls) {
        return new EnumMap<>((Class) Preconditions.checkNotNull(cls));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
        if (map instanceof SortedMap) {
            return difference((SortedMap) map, map2);
        }
        return difference(map, map2, Equivalence.equals());
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
        Preconditions.checkNotNull(equivalence);
        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap(map2);
        LinkedHashMap newLinkedHashMap2 = newLinkedHashMap();
        LinkedHashMap newLinkedHashMap3 = newLinkedHashMap();
        a(map, map2, equivalence, newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
        return new MapDifferenceImpl(newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
    }

    private static <K, V> void a(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence, Map<K, V> map3, Map<K, V> map4, Map<K, V> map5, Map<K, ValueDifference<V>> map6) {
        for (Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (map2.containsKey(key)) {
                Object remove = map4.remove(key);
                if (equivalence.equivalent(value, remove)) {
                    map5.put(key, value);
                } else {
                    map6.put(key, ValueDifferenceImpl.a(value, remove));
                }
            } else {
                map3.put(key, value);
            }
        }
    }

    /* access modifiers changed from: private */
    public static <K, V> Map<K, V> c(Map<K, ? extends V> map) {
        if (map instanceof SortedMap) {
            return Collections.unmodifiableSortedMap((SortedMap) map);
        }
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        Comparator a2 = a(sortedMap.comparator());
        TreeMap newTreeMap = newTreeMap(a2);
        TreeMap newTreeMap2 = newTreeMap(a2);
        newTreeMap2.putAll(map);
        TreeMap newTreeMap3 = newTreeMap(a2);
        TreeMap newTreeMap4 = newTreeMap(a2);
        a(sortedMap, map, Equivalence.equals(), newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
        return new SortedMapDifferenceImpl(newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
    }

    static <E> Comparator<? super E> a(@Nullable Comparator<? super E> comparator) {
        return comparator != null ? comparator : Ordering.natural();
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new AsMapView(set, function);
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
        return new SortedAsMapView(sortedSet, function);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
        return new NavigableAsMapView(navigableSet, function);
    }

    static <K, V> Iterator<Entry<K, V>> a(Set<K> set, final Function<? super K, V> function) {
        return new TransformedIterator<K, Entry<K, V>>(set.iterator()) {
            /* access modifiers changed from: 0000 */
            /* renamed from: b */
            public Entry<K, V> a(K k) {
                return Maps.immutableEntry(k, function.apply(k));
            }
        };
    }

    /* access modifiers changed from: private */
    public static <E> Set<E> c(final Set<E> set) {
        return new ForwardingSet<E>() {
            /* access modifiers changed from: protected */
            public Set<E> delegate() {
                return set;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }
        };
    }

    /* access modifiers changed from: private */
    public static <E> SortedSet<E> b(final SortedSet<E> sortedSet) {
        return new ForwardingSortedSet<E>() {
            /* access modifiers changed from: protected */
            public SortedSet<E> delegate() {
                return sortedSet;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            public SortedSet<E> headSet(E e) {
                return Maps.b(super.headSet(e));
            }

            public SortedSet<E> subSet(E e, E e2) {
                return Maps.b(super.subSet(e, e2));
            }

            public SortedSet<E> tailSet(E e) {
                return Maps.b(super.tailSet(e));
            }
        };
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static <E> NavigableSet<E> b(final NavigableSet<E> navigableSet) {
        return new ForwardingNavigableSet<E>() {
            /* access modifiers changed from: protected */
            public NavigableSet<E> delegate() {
                return navigableSet;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            public SortedSet<E> headSet(E e) {
                return Maps.b(super.headSet(e));
            }

            public SortedSet<E> subSet(E e, E e2) {
                return Maps.b(super.subSet(e, e2));
            }

            public SortedSet<E> tailSet(E e) {
                return Maps.b(super.tailSet(e));
            }

            public NavigableSet<E> headSet(E e, boolean z) {
                return Maps.b(super.headSet(e, z));
            }

            public NavigableSet<E> tailSet(E e, boolean z) {
                return Maps.b(super.tailSet(e, z));
            }

            public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
                return Maps.b(super.subSet(e, z, e2, z2));
            }

            public NavigableSet<E> descendingSet() {
                return Maps.b(super.descendingSet());
            }
        };
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
        return toMap(iterable.iterator(), function);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> it, Function<? super K, V> function) {
        Preconditions.checkNotNull(function);
        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
        while (it.hasNext()) {
            Object next = it.next();
            newLinkedHashMap.put(next, function.apply(next));
        }
        return ImmutableMap.copyOf((Map<? extends K, ? extends V>) newLinkedHashMap);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
        return uniqueIndex(iterable.iterator(), function);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        Builder builder = ImmutableMap.builder();
        while (it.hasNext()) {
            Object next = it.next();
            builder.put(function.apply(next), next);
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            sb.append(". To index multiple values under a key, use Multimaps.index.");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @GwtIncompatible
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        Builder builder = ImmutableMap.builder();
        Enumeration propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String str = (String) propertyNames.nextElement();
            builder.put(str, properties.getProperty(str));
        }
        return builder.build();
    }

    @GwtCompatible(serializable = true)
    public static <K, V> Entry<K, V> immutableEntry(@Nullable K k, @Nullable V v) {
        return new ImmutableEntry(k, v);
    }

    static <K, V> Set<Entry<K, V>> a(Set<Entry<K, V>> set) {
        return new UnmodifiableEntrySet(Collections.unmodifiableSet(set));
    }

    static <K, V> Entry<K, V> a(final Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>() {
            public K getKey() {
                return entry.getKey();
            }

            public V getValue() {
                return entry.getValue();
            }
        };
    }

    static <K, V> UnmodifiableIterator<Entry<K, V>> c(final Iterator<Entry<K, V>> it) {
        return new UnmodifiableIterator<Entry<K, V>>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public Entry<K, V> next() {
                return Maps.a((Entry) it.next());
            }
        };
    }

    @Beta
    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
        return new BiMapConverter(biMap);
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
        return Synchronized.a(biMap, (Object) null);
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
        return new UnmodifiableBiMap(biMap, null);
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, Function<? super V1, V2> function) {
        return transformEntries(map, a(function));
    }

    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, Function<? super V1, V2> function) {
        return transformEntries(sortedMap, a(function));
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, Function<? super V1, V2> function) {
        return transformEntries(navigableMap, a(function));
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMap(map, entryTransformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesSortedMap(sortedMap, entryTransformer);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesNavigableMap(navigableMap, entryTransformer);
    }

    static <K, V1, V2> EntryTransformer<K, V1, V2> a(final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(K k, V1 v1) {
                return function.apply(v1);
            }
        };
    }

    static <K, V1, V2> Function<V1, V2> a(final EntryTransformer<? super K, V1, V2> entryTransformer, final K k) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<V1, V2>() {
            public V2 apply(@Nullable V1 v1) {
                return entryTransformer.transformEntry(k, v1);
            }
        };
    }

    static <K, V1, V2> Function<Entry<K, V1>, V2> a(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Entry<K, V1>, V2>() {
            /* renamed from: a */
            public V2 apply(Entry<K, V1> entry) {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <V2, K, V1> Entry<K, V2> a(final EntryTransformer<? super K, ? super V1, V2> entryTransformer, final Entry<K, V1> entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V2>() {
            public K getKey() {
                return entry.getKey();
            }

            public V2 getValue() {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <K, V1, V2> Function<Entry<K, V1>, Entry<K, V2>> b(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Entry<K, V1>, Entry<K, V2>>() {
            /* renamed from: a */
            public Entry<K, V2> apply(Entry<K, V1> entry) {
                return Maps.a(entryTransformer, entry);
            }
        };
    }

    static <K> Predicate<Entry<K, ?>> a(Predicate<? super K> predicate) {
        return Predicates.compose(predicate, a());
    }

    static <V> Predicate<Entry<?, V>> b(Predicate<? super V> predicate) {
        return Predicates.compose(predicate, b());
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        Predicate a2 = a(predicate);
        return map instanceof AbstractFilteredMap ? a((AbstractFilteredMap) map, a2) : new FilteredKeyMap((Map) Preconditions.checkNotNull(map), predicate, a2);
    }

    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
        return filterEntries(sortedMap, a(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
        return filterEntries(navigableMap, a(predicate));
    }

    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        return filterEntries(biMap, a(predicate));
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        return filterEntries(map, b(predicate));
    }

    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
        return filterEntries(sortedMap, b(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
        return filterEntries(navigableMap, b(predicate));
    }

    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
        return filterEntries(biMap, b(predicate));
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return map instanceof AbstractFilteredMap ? a((AbstractFilteredMap) map, predicate) : new FilteredEntryMap((Map) Preconditions.checkNotNull(map), predicate);
    }

    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return sortedMap instanceof FilteredEntrySortedMap ? a((FilteredEntrySortedMap) sortedMap, predicate) : new FilteredEntrySortedMap((SortedMap) Preconditions.checkNotNull(sortedMap), predicate);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return navigableMap instanceof FilteredEntryNavigableMap ? a((FilteredEntryNavigableMap) navigableMap, predicate) : new FilteredEntryNavigableMap((NavigableMap) Preconditions.checkNotNull(navigableMap), predicate);
    }

    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        return biMap instanceof FilteredEntryBiMap ? a((FilteredEntryBiMap) biMap, predicate) : new FilteredEntryBiMap(biMap, predicate);
    }

    private static <K, V> Map<K, V> a(AbstractFilteredMap<K, V> abstractFilteredMap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntryMap(abstractFilteredMap.a, Predicates.and(abstractFilteredMap.b, predicate));
    }

    private static <K, V> SortedMap<K, V> a(FilteredEntrySortedMap<K, V> filteredEntrySortedMap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntrySortedMap(filteredEntrySortedMap.c(), Predicates.and(filteredEntrySortedMap.b, predicate));
    }

    @GwtIncompatible
    private static <K, V> NavigableMap<K, V> a(FilteredEntryNavigableMap<K, V> filteredEntryNavigableMap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntryNavigableMap(filteredEntryNavigableMap.a, Predicates.and(filteredEntryNavigableMap.b, predicate));
    }

    private static <K, V> BiMap<K, V> a(FilteredEntryBiMap<K, V> filteredEntryBiMap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntryBiMap(filteredEntryBiMap.c(), Predicates.and(filteredEntryBiMap.b, predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        if (navigableMap instanceof UnmodifiableNavigableMap) {
            return navigableMap;
        }
        return new UnmodifiableNavigableMap(navigableMap);
    }

    /* access modifiers changed from: private */
    @Nullable
    public static <K, V> Entry<K, V> e(@Nullable Entry<K, ? extends V> entry) {
        if (entry == null) {
            return null;
        }
        return a(entry);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.a(navigableMap);
    }

    static <V> V a(Map<?, V> map, @Nullable Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.get(obj);
        } catch (ClassCastException unused) {
            return null;
        } catch (NullPointerException unused2) {
            return null;
        }
    }

    static boolean b(Map<?, ?> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(obj);
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused2) {
            return false;
        }
    }

    static <V> V c(Map<?, V> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.remove(obj);
        } catch (ClassCastException unused) {
            return null;
        } catch (NullPointerException unused2) {
            return null;
        }
    }

    static boolean d(Map<?, ?> map, @Nullable Object obj) {
        return Iterators.contains(a(map.entrySet().iterator()), obj);
    }

    static boolean e(Map<?, ?> map, @Nullable Object obj) {
        return Iterators.contains(b(map.entrySet().iterator()), obj);
    }

    static <K, V> boolean a(Collection<Entry<K, V>> collection, Object obj) {
        if (!(obj instanceof Entry)) {
            return false;
        }
        return collection.contains(a((Entry) obj));
    }

    static <K, V> boolean b(Collection<Entry<K, V>> collection, Object obj) {
        if (!(obj instanceof Entry)) {
            return false;
        }
        return collection.remove(a((Entry) obj));
    }

    static boolean f(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        return map.entrySet().equals(((Map) obj).entrySet());
    }

    static String a(Map<?, ?> map) {
        StringBuilder a2 = Collections2.a(map.size());
        a2.append('{');
        a.appendTo(a2, map);
        a2.append('}');
        return a2.toString();
    }

    static <K, V> void a(Map<K, V> map, Map<? extends K, ? extends V> map2) {
        for (Entry entry : map2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    @Nullable
    static <K> K b(@Nullable Entry<K, ?> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Nullable
    static <V> V c(@Nullable Entry<?, V> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    static <E> ImmutableMap<E, Integer> a(Collection<E> collection) {
        Builder builder = new Builder(collection.size());
        int i = 0;
        for (E put : collection) {
            int i2 = i + 1;
            builder.put(put, Integer.valueOf(i));
            i = i2;
        }
        return builder.build();
    }

    @GwtIncompatible
    @Beta
    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
        boolean z = false;
        if (navigableMap.comparator() != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            Comparable lowerEndpoint = range.lowerEndpoint();
            boolean z2 = range.lowerBoundType() == BoundType.CLOSED;
            Comparable upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableMap.subMap(lowerEndpoint, z2, upperEndpoint, z);
        } else if (range.hasLowerBound()) {
            Comparable lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableMap.tailMap(lowerEndpoint2, z);
        } else if (!range.hasUpperBound()) {
            return (NavigableMap) Preconditions.checkNotNull(navigableMap);
        } else {
            Comparable upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableMap.headMap(upperEndpoint2, z);
        }
    }
}
