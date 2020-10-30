package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
@Deprecated
@Beta
public final class MapConstraints {

    static class ConstrainedAsMapEntries<K, V> extends ForwardingSet<Entry<K, Collection<V>>> {
        /* access modifiers changed from: private */
        public final MapConstraint<? super K, ? super V> a;
        private final Set<Entry<K, Collection<V>>> b;

        ConstrainedAsMapEntries(Set<Entry<K, Collection<V>>> set, MapConstraint<? super K, ? super V> mapConstraint) {
            this.b = set;
            this.a = mapConstraint;
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, Collection<V>>> delegate() {
            return this.b;
        }

        public Iterator<Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Entry<K, Collection<V>>, Entry<K, Collection<V>>>(this.b.iterator()) {
                /* access modifiers changed from: 0000 */
                public Entry<K, Collection<V>> a(Entry<K, Collection<V>> entry) {
                    return MapConstraints.d(entry, ConstrainedAsMapEntries.this.a);
                }
            };
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }

        public boolean contains(Object obj) {
            return Maps.a((Collection<Entry<K, V>>) delegate(), obj);
        }

        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        public boolean equals(@Nullable Object obj) {
            return standardEquals(obj);
        }

        public int hashCode() {
            return standardHashCode();
        }

        public boolean remove(Object obj) {
            return Maps.b((Collection<Entry<K, V>>) delegate(), obj);
        }

        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }
    }

    static class ConstrainedEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
        final MapConstraint<? super K, ? super V> a;
        final Collection<Entry<K, V>> b;

        ConstrainedEntries(Collection<Entry<K, V>> collection, MapConstraint<? super K, ? super V> mapConstraint) {
            this.b = collection;
            this.a = mapConstraint;
        }

        /* access modifiers changed from: protected */
        public Collection<Entry<K, V>> delegate() {
            return this.b;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new TransformedIterator<Entry<K, V>, Entry<K, V>>(this.b.iterator()) {
                /* access modifiers changed from: 0000 */
                public Entry<K, V> a(Entry<K, V> entry) {
                    return MapConstraints.c(entry, ConstrainedEntries.this.a);
                }
            };
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }

        public boolean contains(Object obj) {
            return Maps.a(delegate(), obj);
        }

        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        public boolean remove(Object obj) {
            return Maps.b(delegate(), obj);
        }

        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }
    }

    static class ConstrainedEntrySet<K, V> extends ConstrainedEntries<K, V> implements Set<Entry<K, V>> {
        ConstrainedEntrySet(Set<Entry<K, V>> set, MapConstraint<? super K, ? super V> mapConstraint) {
            super(set, mapConstraint);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }

        public int hashCode() {
            return Sets.a(this);
        }
    }

    static class ConstrainedAsMapValues<K, V> extends ForwardingCollection<Collection<V>> {
        final Collection<Collection<V>> a;
        final Set<Entry<K, Collection<V>>> b;

        ConstrainedAsMapValues(Collection<Collection<V>> collection, Set<Entry<K, Collection<V>>> set) {
            this.a = collection;
            this.b = set;
        }

        /* access modifiers changed from: protected */
        public Collection<Collection<V>> delegate() {
            return this.a;
        }

        public Iterator<Collection<V>> iterator() {
            final Iterator it = this.b.iterator();
            return new Iterator<Collection<V>>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                /* renamed from: a */
                public Collection<V> next() {
                    return (Collection) ((Entry) it.next()).getValue();
                }

                public void remove() {
                    it.remove();
                }
            };
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }

        public boolean contains(Object obj) {
            return standardContains(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        public boolean remove(Object obj) {
            return standardRemove(obj);
        }

        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }
    }

    static class ConstrainedListMultimap<K, V> extends ConstrainedMultimap<K, V> implements ListMultimap<K, V> {
        ConstrainedListMultimap(ListMultimap<K, V> listMultimap, MapConstraint<? super K, ? super V> mapConstraint) {
            super(listMultimap, mapConstraint);
        }

        public List<V> get(K k) {
            return (List) super.get(k);
        }

        public List<V> removeAll(Object obj) {
            return (List) super.removeAll(obj);
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            return (List) super.replaceValues(k, iterable);
        }
    }

    static class ConstrainedMap<K, V> extends ForwardingMap<K, V> {
        final MapConstraint<? super K, ? super V> a;
        private final Map<K, V> b;
        private transient Set<Entry<K, V>> c;

        ConstrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> mapConstraint) {
            this.b = (Map) Preconditions.checkNotNull(map);
            this.a = (MapConstraint) Preconditions.checkNotNull(mapConstraint);
        }

        /* access modifiers changed from: protected */
        public Map<K, V> delegate() {
            return this.b;
        }

        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> set = this.c;
            if (set != null) {
                return set;
            }
            Set<Entry<K, V>> a2 = MapConstraints.d(this.b.entrySet(), this.a);
            this.c = a2;
            return a2;
        }

        @CanIgnoreReturnValue
        public V put(K k, V v) {
            this.a.checkKeyValue(k, v);
            return this.b.put(k, v);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            this.b.putAll(MapConstraints.b(map, this.a));
        }
    }

    static class ConstrainedMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        final MapConstraint<? super K, ? super V> a;
        final Multimap<K, V> b;
        transient Collection<Entry<K, V>> c;
        transient Map<K, Collection<V>> d;

        public ConstrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> mapConstraint) {
            this.b = (Multimap) Preconditions.checkNotNull(multimap);
            this.a = (MapConstraint) Preconditions.checkNotNull(mapConstraint);
        }

        /* access modifiers changed from: protected */
        public Multimap<K, V> delegate() {
            return this.b;
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map = this.d;
            if (map != null) {
                return map;
            }
            final Map asMap = this.b.asMap();
            AnonymousClass1AsMap r1 = new ForwardingMap<K, Collection<V>>() {
                Set<Entry<K, Collection<V>>> a;
                Collection<Collection<V>> b;

                /* access modifiers changed from: protected */
                public Map<K, Collection<V>> delegate() {
                    return asMap;
                }

                public Set<Entry<K, Collection<V>>> entrySet() {
                    Set<Entry<K, Collection<V>>> set = this.a;
                    if (set != null) {
                        return set;
                    }
                    Set<Entry<K, Collection<V>>> b2 = MapConstraints.c(asMap.entrySet(), ConstrainedMultimap.this.a);
                    this.a = b2;
                    return b2;
                }

                /* renamed from: a */
                public Collection<V> get(Object obj) {
                    try {
                        Collection<V> collection = ConstrainedMultimap.this.get(obj);
                        if (collection.isEmpty()) {
                            collection = null;
                        }
                        return collection;
                    } catch (ClassCastException unused) {
                        return null;
                    }
                }

                public Collection<Collection<V>> values() {
                    Collection<Collection<V>> collection = this.b;
                    if (collection != null) {
                        return collection;
                    }
                    ConstrainedAsMapValues constrainedAsMapValues = new ConstrainedAsMapValues(delegate().values(), entrySet());
                    this.b = constrainedAsMapValues;
                    return constrainedAsMapValues;
                }

                public boolean containsValue(Object obj) {
                    return values().contains(obj);
                }
            };
            this.d = r1;
            return r1;
        }

        public Collection<Entry<K, V>> entries() {
            Collection<Entry<K, V>> collection = this.c;
            if (collection != null) {
                return collection;
            }
            Collection<Entry<K, V>> a2 = MapConstraints.b(this.b.entries(), this.a);
            this.c = a2;
            return a2;
        }

        public Collection<V> get(final K k) {
            return Constraints.b(this.b.get(k), (Constraint<E>) new Constraint<V>() {
                public V a(V v) {
                    ConstrainedMultimap.this.a.checkKeyValue(k, v);
                    return v;
                }
            });
        }

        public boolean put(K k, V v) {
            this.a.checkKeyValue(k, v);
            return this.b.put(k, v);
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            return this.b.putAll(k, MapConstraints.b(k, iterable, this.a));
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean z = false;
            for (Entry entry : multimap.entries()) {
                z |= put(entry.getKey(), entry.getValue());
            }
            return z;
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            return this.b.replaceValues(k, MapConstraints.b(k, iterable, this.a));
        }
    }

    private MapConstraints() {
    }

    public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedMap(map, mapConstraint);
    }

    public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> listMultimap, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedListMultimap(listMultimap, mapConstraint);
    }

    /* access modifiers changed from: private */
    public static <K, V> Entry<K, V> c(final Entry<K, V> entry, final MapConstraint<? super K, ? super V> mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry<K, V>() {
            /* access modifiers changed from: protected */
            public Entry<K, V> delegate() {
                return entry;
            }

            public V setValue(V v) {
                mapConstraint.checkKeyValue(getKey(), v);
                return entry.setValue(v);
            }
        };
    }

    /* access modifiers changed from: private */
    public static <K, V> Entry<K, Collection<V>> d(final Entry<K, Collection<V>> entry, final MapConstraint<? super K, ? super V> mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry<K, Collection<V>>() {
            /* access modifiers changed from: protected */
            public Entry<K, Collection<V>> delegate() {
                return entry;
            }

            /* renamed from: a */
            public Collection<V> getValue() {
                return Constraints.b((Collection) entry.getValue(), (Constraint<E>) new Constraint<V>() {
                    public V a(V v) {
                        mapConstraint.checkKeyValue(AnonymousClass2.this.getKey(), v);
                        return v;
                    }
                });
            }
        };
    }

    /* access modifiers changed from: private */
    public static <K, V> Set<Entry<K, Collection<V>>> c(Set<Entry<K, Collection<V>>> set, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedAsMapEntries(set, mapConstraint);
    }

    /* access modifiers changed from: private */
    public static <K, V> Collection<Entry<K, V>> b(Collection<Entry<K, V>> collection, MapConstraint<? super K, ? super V> mapConstraint) {
        if (collection instanceof Set) {
            return d((Set) collection, mapConstraint);
        }
        return new ConstrainedEntries(collection, mapConstraint);
    }

    /* access modifiers changed from: private */
    public static <K, V> Set<Entry<K, V>> d(Set<Entry<K, V>> set, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedEntrySet(set, mapConstraint);
    }

    /* access modifiers changed from: private */
    public static <K, V> Collection<V> b(K k, Iterable<? extends V> iterable, MapConstraint<? super K, ? super V> mapConstraint) {
        ArrayList<Object> newArrayList = Lists.newArrayList(iterable);
        for (Object checkKeyValue : newArrayList) {
            mapConstraint.checkKeyValue(k, checkKeyValue);
        }
        return newArrayList;
    }

    /* access modifiers changed from: private */
    public static <K, V> Map<K, V> b(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> mapConstraint) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        for (Entry entry : linkedHashMap.entrySet()) {
            mapConstraint.checkKeyValue(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }
}
