package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.Maps.EntryTransformer;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Multimaps {

    static abstract class Entries<K, V> extends AbstractCollection<Entry<K, V>> {
        /* access modifiers changed from: 0000 */
        public abstract Multimap<K, V> a();

        Entries() {
        }

        public int size() {
            return a().size();
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return a().containsEntry(entry.getKey(), entry.getValue());
        }

        public boolean remove(@Nullable Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return a().remove(entry.getKey(), entry.getValue());
        }

        public void clear() {
            a().clear();
        }
    }

    static class Keys<K, V> extends AbstractMultiset<K> {
        @Weak
        final Multimap<K, V> b;

        class KeysEntrySet extends EntrySet<K> {
            KeysEntrySet() {
            }

            /* access modifiers changed from: 0000 */
            public Multiset<K> a() {
                return Keys.this;
            }

            public Iterator<Multiset.Entry<K>> iterator() {
                return Keys.this.a();
            }

            public int size() {
                return Keys.this.b();
            }

            public boolean isEmpty() {
                return Keys.this.b.isEmpty();
            }

            public boolean contains(@Nullable Object obj) {
                boolean z = false;
                if (!(obj instanceof Multiset.Entry)) {
                    return false;
                }
                Multiset.Entry entry = (Multiset.Entry) obj;
                Collection collection = (Collection) Keys.this.b.asMap().get(entry.getElement());
                if (collection != null && collection.size() == entry.getCount()) {
                    z = true;
                }
                return z;
            }

            public boolean remove(@Nullable Object obj) {
                if (obj instanceof Multiset.Entry) {
                    Multiset.Entry entry = (Multiset.Entry) obj;
                    Collection collection = (Collection) Keys.this.b.asMap().get(entry.getElement());
                    if (collection != null && collection.size() == entry.getCount()) {
                        collection.clear();
                        return true;
                    }
                }
                return false;
            }
        }

        Keys(Multimap<K, V> multimap) {
            this.b = multimap;
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Multiset.Entry<K>> a() {
            return new TransformedIterator<Entry<K, Collection<V>>, Multiset.Entry<K>>(this.b.asMap().entrySet().iterator()) {
                /* access modifiers changed from: 0000 */
                public Multiset.Entry<K> a(final Entry<K, Collection<V>> entry) {
                    return new AbstractEntry<K>() {
                        public K getElement() {
                            return entry.getKey();
                        }

                        public int getCount() {
                            return ((Collection) entry.getValue()).size();
                        }
                    };
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return this.b.asMap().size();
        }

        /* access modifiers changed from: 0000 */
        public Set<Multiset.Entry<K>> createEntrySet() {
            return new KeysEntrySet();
        }

        public boolean contains(@Nullable Object obj) {
            return this.b.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return Maps.a(this.b.entries().iterator());
        }

        public int count(@Nullable Object obj) {
            Collection collection = (Collection) Maps.a(this.b.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            return collection.size();
        }

        public int remove(@Nullable Object obj, int i) {
            CollectPreconditions.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            Collection collection = (Collection) Maps.a(this.b.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            int size = collection.size();
            if (i >= size) {
                collection.clear();
            } else {
                Iterator it = collection.iterator();
                for (int i2 = 0; i2 < i; i2++) {
                    it.next();
                    it.remove();
                }
            }
            return size;
        }

        public void clear() {
            this.b.clear();
        }

        public Set<K> elementSet() {
            return this.b.keySet();
        }
    }

    static final class AsMap<K, V> extends ViewCachingAbstractMap<K, Collection<V>> {
        /* access modifiers changed from: private */
        @Weak
        public final Multimap<K, V> a;

        class EntrySet extends EntrySet<K, Collection<V>> {
            EntrySet() {
            }

            /* access modifiers changed from: 0000 */
            public Map<K, Collection<V>> a() {
                return AsMap.this;
            }

            public Iterator<Entry<K, Collection<V>>> iterator() {
                return Maps.a(AsMap.this.a.keySet(), (Function<? super K, V>) new Function<K, Collection<V>>() {
                    /* renamed from: a */
                    public Collection<V> apply(K k) {
                        return AsMap.this.a.get(k);
                    }
                });
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                AsMap.this.a(((Entry) obj).getKey());
                return true;
            }
        }

        AsMap(Multimap<K, V> multimap) {
            this.a = (Multimap) Preconditions.checkNotNull(multimap);
        }

        public int size() {
            return this.a.keySet().size();
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, Collection<V>>> a() {
            return new EntrySet();
        }

        /* access modifiers changed from: 0000 */
        public void a(Object obj) {
            this.a.keySet().remove(obj);
        }

        /* renamed from: b */
        public Collection<V> get(Object obj) {
            if (containsKey(obj)) {
                return this.a.get(obj);
            }
            return null;
        }

        /* renamed from: c */
        public Collection<V> remove(Object obj) {
            if (containsKey(obj)) {
                return this.a.removeAll(obj);
            }
            return null;
        }

        public Set<K> keySet() {
            return this.a.keySet();
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        public void clear() {
            this.a.clear();
        }
    }

    static class CustomListMultimap<K, V> extends AbstractListMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends List<V>> a;

        CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public List<V> c() {
            return (List) this.a.get();
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) objectInputStream.readObject());
        }
    }

    static class CustomMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Collection<V>> a;

        CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        /* access modifiers changed from: protected */
        public Collection<V> c() {
            return (Collection) this.a.get();
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) objectInputStream.readObject());
        }
    }

    static class CustomSetMultimap<K, V> extends AbstractSetMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends Set<V>> a;

        CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Set<V> c() {
            return (Set) this.a.get();
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            a((Map) objectInputStream.readObject());
        }
    }

    static class CustomSortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        transient Supplier<? extends SortedSet<V>> a;
        transient Comparator<? super V> b;

        CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
            super(map);
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
            this.b = ((SortedSet) supplier.get()).comparator();
        }

        /* access modifiers changed from: protected */
        /* renamed from: n */
        public SortedSet<V> c() {
            return (SortedSet) this.a.get();
        }

        public Comparator<? super V> valueComparator() {
            return this.b;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(e());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.a = (Supplier) objectInputStream.readObject();
            this.b = ((SortedSet) this.a.get()).comparator();
            a((Map) objectInputStream.readObject());
        }
    }

    static class MapMultimap<K, V> extends AbstractMultimap<K, V> implements SetMultimap<K, V>, Serializable {
        private static final long serialVersionUID = 7845222491160860175L;
        final Map<K, V> a;

        MapMultimap(Map<K, V> map) {
            this.a = (Map) Preconditions.checkNotNull(map);
        }

        public int size() {
            return this.a.size();
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        public boolean containsValue(Object obj) {
            return this.a.containsValue(obj);
        }

        public boolean containsEntry(Object obj, Object obj2) {
            return this.a.entrySet().contains(Maps.immutableEntry(obj, obj2));
        }

        public Set<V> get(final K k) {
            return new ImprovedAbstractSet<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        int a;

                        public boolean hasNext() {
                            return this.a == 0 && MapMultimap.this.a.containsKey(k);
                        }

                        public V next() {
                            if (!hasNext()) {
                                throw new NoSuchElementException();
                            }
                            this.a++;
                            return MapMultimap.this.a.get(k);
                        }

                        public void remove() {
                            boolean z = true;
                            if (this.a != 1) {
                                z = false;
                            }
                            CollectPreconditions.a(z);
                            this.a = -1;
                            MapMultimap.this.a.remove(k);
                        }
                    };
                }

                public int size() {
                    return MapMultimap.this.a.containsKey(k) ? 1 : 0;
                }
            };
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            return this.a.entrySet().remove(Maps.immutableEntry(obj, obj2));
        }

        public Set<V> removeAll(Object obj) {
            HashSet hashSet = new HashSet(2);
            if (!this.a.containsKey(obj)) {
                return hashSet;
            }
            hashSet.add(this.a.remove(obj));
            return hashSet;
        }

        public void clear() {
            this.a.clear();
        }

        public Set<K> keySet() {
            return this.a.keySet();
        }

        public Collection<V> values() {
            return this.a.values();
        }

        public Set<Entry<K, V>> entries() {
            return this.a.entrySet();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> h() {
            return this.a.entrySet().iterator();
        }

        /* access modifiers changed from: 0000 */
        public Map<K, Collection<V>> i() {
            return new AsMap(this);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    static final class TransformedEntriesListMultimap<K, V1, V2> extends TransformedEntriesMultimap<K, V1, V2> implements ListMultimap<K, V2> {
        TransformedEntriesListMultimap(ListMultimap<K, V1> listMultimap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(listMultimap, entryTransformer);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public List<V2> b(K k, Collection<V1> collection) {
            return Lists.transform((List) collection, Maps.a(this.b, k));
        }

        public List<V2> get(K k) {
            return b(k, this.a.get(k));
        }

        public List<V2> removeAll(Object obj) {
            return b(obj, this.a.removeAll(obj));
        }

        public List<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    static class TransformedEntriesMultimap<K, V1, V2> extends AbstractMultimap<K, V2> {
        final Multimap<K, V1> a;
        final EntryTransformer<? super K, ? super V1, V2> b;

        TransformedEntriesMultimap(Multimap<K, V1> multimap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.a = (Multimap) Preconditions.checkNotNull(multimap);
            this.b = (EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        /* access modifiers changed from: 0000 */
        public Collection<V2> b(K k, Collection<V1> collection) {
            Function a2 = Maps.a(this.b, k);
            if (collection instanceof List) {
                return Lists.transform((List) collection, a2);
            }
            return Collections2.transform(collection, a2);
        }

        /* access modifiers changed from: 0000 */
        public Map<K, Collection<V2>> i() {
            return Maps.transformEntries(this.a.asMap(), (EntryTransformer<? super K, ? super V1, V2>) new EntryTransformer<K, Collection<V1>, Collection<V2>>() {
                /* renamed from: a */
                public Collection<V2> transformEntry(K k, Collection<V1> collection) {
                    return TransformedEntriesMultimap.this.b(k, collection);
                }
            });
        }

        public void clear() {
            this.a.clear();
        }

        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V2>> h() {
            return Iterators.transform(this.a.entries().iterator(), Maps.b(this.b));
        }

        public Collection<V2> get(K k) {
            return b(k, this.a.get(k));
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public Set<K> keySet() {
            return this.a.keySet();
        }

        public Multiset<K> keys() {
            return this.a.keys();
        }

        public boolean put(K k, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            return get(obj).remove(obj2);
        }

        public Collection<V2> removeAll(Object obj) {
            return b(obj, this.a.removeAll(obj));
        }

        public Collection<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.a.size();
        }

        /* access modifiers changed from: 0000 */
        public Collection<V2> l() {
            return Collections2.transform(this.a.entries(), Maps.a(this.b));
        }
    }

    static class UnmodifiableListMultimap<K, V> extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
            super(listMultimap);
        }

        /* renamed from: a */
        public ListMultimap<K, V> delegate() {
            return (ListMultimap) super.delegate();
        }

        public List<V> get(K k) {
            return Collections.unmodifiableList(delegate().get(k));
        }

        public List<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    static class UnmodifiableMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Multimap<K, V> a;
        transient Collection<Entry<K, V>> b;
        transient Multiset<K> c;
        transient Set<K> d;
        transient Collection<V> e;
        transient Map<K, Collection<V>> f;

        UnmodifiableMultimap(Multimap<K, V> multimap) {
            this.a = (Multimap) Preconditions.checkNotNull(multimap);
        }

        /* access modifiers changed from: protected */
        public Multimap<K, V> delegate() {
            return this.a;
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map = this.f;
            if (map != null) {
                return map;
            }
            Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap(Maps.transformValues(this.a.asMap(), (Function<? super V1, V2>) new Function<Collection<V>, Collection<V>>() {
                /* renamed from: a */
                public Collection<V> apply(Collection<V> collection) {
                    return Multimaps.c(collection);
                }
            }));
            this.f = unmodifiableMap;
            return unmodifiableMap;
        }

        public Collection<Entry<K, V>> entries() {
            Collection<Entry<K, V>> collection = this.b;
            if (collection != null) {
                return collection;
            }
            Collection<Entry<K, V>> b2 = Multimaps.d(this.a.entries());
            this.b = b2;
            return b2;
        }

        public Collection<V> get(K k) {
            return Multimaps.c(this.a.get(k));
        }

        public Multiset<K> keys() {
            Multiset<K> multiset = this.c;
            if (multiset != null) {
                return multiset;
            }
            Multiset<K> unmodifiableMultiset = Multisets.unmodifiableMultiset(this.a.keys());
            this.c = unmodifiableMultiset;
            return unmodifiableMultiset;
        }

        public Set<K> keySet() {
            Set<K> set = this.d;
            if (set != null) {
                return set;
            }
            Set<K> unmodifiableSet = Collections.unmodifiableSet(this.a.keySet());
            this.d = unmodifiableSet;
            return unmodifiableSet;
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> values() {
            Collection<V> collection = this.e;
            if (collection != null) {
                return collection;
            }
            Collection<V> unmodifiableCollection = Collections.unmodifiableCollection(this.a.values());
            this.e = unmodifiableCollection;
            return unmodifiableCollection;
        }
    }

    static class UnmodifiableSetMultimap<K, V> extends UnmodifiableMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
            super(setMultimap);
        }

        /* renamed from: a */
        public SetMultimap<K, V> delegate() {
            return (SetMultimap) super.delegate();
        }

        public Set<V> get(K k) {
            return Collections.unmodifiableSet(delegate().get(k));
        }

        public Set<Entry<K, V>> entries() {
            return Maps.a(delegate().entries());
        }

        public Set<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    static class UnmodifiableSortedSetMultimap<K, V> extends UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        /* renamed from: b */
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }

        public SortedSet<V> get(K k) {
            return Collections.unmodifiableSortedSet(delegate().get(k));
        }

        public SortedSet<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public Comparator<? super V> valueComparator() {
            return delegate().valueComparator();
        }
    }

    private Multimaps() {
    }

    public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
        return new CustomMultimap(map, supplier);
    }

    public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
        return new CustomListMultimap(map, supplier);
    }

    public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
        return new CustomSetMultimap(map, supplier);
    }

    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
        return new CustomSortedSetMultimap(map, supplier);
    }

    @CanIgnoreReturnValue
    public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> multimap, M m) {
        Preconditions.checkNotNull(m);
        for (Entry entry : multimap.entries()) {
            m.put(entry.getValue(), entry.getKey());
        }
        return m;
    }

    public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
        return Synchronized.a(multimap, (Object) null);
    }

    public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> multimap) {
        return ((multimap instanceof UnmodifiableMultimap) || (multimap instanceof ImmutableMultimap)) ? multimap : new UnmodifiableMultimap(multimap);
    }

    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> immutableMultimap) {
        return (Multimap) Preconditions.checkNotNull(immutableMultimap);
    }

    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> setMultimap) {
        return Synchronized.a(setMultimap, (Object) null);
    }

    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
        return ((setMultimap instanceof UnmodifiableSetMultimap) || (setMultimap instanceof ImmutableSetMultimap)) ? setMultimap : new UnmodifiableSetMultimap(setMultimap);
    }

    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> immutableSetMultimap) {
        return (SetMultimap) Preconditions.checkNotNull(immutableSetMultimap);
    }

    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return Synchronized.a(sortedSetMultimap, (Object) null);
    }

    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        if (sortedSetMultimap instanceof UnmodifiableSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new UnmodifiableSortedSetMultimap(sortedSetMultimap);
    }

    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> listMultimap) {
        return Synchronized.a(listMultimap, (Object) null);
    }

    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
        return ((listMultimap instanceof UnmodifiableListMultimap) || (listMultimap instanceof ImmutableListMultimap)) ? listMultimap : new UnmodifiableListMultimap(listMultimap);
    }

    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> immutableListMultimap) {
        return (ListMultimap) Preconditions.checkNotNull(immutableListMultimap);
    }

    /* access modifiers changed from: private */
    public static <V> Collection<V> c(Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet) collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set) collection);
        }
        if (collection instanceof List) {
            return Collections.unmodifiableList((List) collection);
        }
        return Collections.unmodifiableCollection(collection);
    }

    /* access modifiers changed from: private */
    public static <K, V> Collection<Entry<K, V>> d(Collection<Entry<K, V>> collection) {
        if (collection instanceof Set) {
            return Maps.a((Set) collection);
        }
        return new UnmodifiableEntries(Collections.unmodifiableCollection(collection));
    }

    @Beta
    public static <K, V> Map<K, List<V>> asMap(ListMultimap<K, V> listMultimap) {
        return listMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, Set<V>> asMap(SetMultimap<K, V> setMultimap) {
        return setMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, SortedSet<V>> asMap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return sortedSetMultimap.asMap();
    }

    @Beta
    public static <K, V> Map<K, Collection<V>> asMap(Multimap<K, V> multimap) {
        return multimap.asMap();
    }

    public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
        return new MapMultimap(map);
    }

    public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> multimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries(multimap, Maps.a(function));
    }

    public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> multimap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMultimap(multimap, entryTransformer);
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> listMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return transformEntries(listMultimap, Maps.a(function));
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> listMultimap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesListMultimap(listMultimap, entryTransformer);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> iterable, Function<? super V, K> function) {
        return index(iterable.iterator(), function);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        Builder builder = ImmutableListMultimap.builder();
        while (it.hasNext()) {
            Object next = it.next();
            Preconditions.checkNotNull(next, it);
            builder.put(function.apply(next), next);
        }
        return builder.build();
    }

    public static <K, V> Multimap<K, V> filterKeys(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        if (multimap instanceof SetMultimap) {
            return filterKeys((SetMultimap) multimap, predicate);
        }
        if (multimap instanceof ListMultimap) {
            return filterKeys((ListMultimap) multimap, predicate);
        }
        if (multimap instanceof FilteredKeyMultimap) {
            FilteredKeyMultimap filteredKeyMultimap = (FilteredKeyMultimap) multimap;
            return new FilteredKeyMultimap(filteredKeyMultimap.a, Predicates.and(filteredKeyMultimap.b, predicate));
        } else if (multimap instanceof FilteredMultimap) {
            return a((FilteredMultimap) multimap, Maps.a(predicate));
        } else {
            return new FilteredKeyMultimap(multimap, predicate);
        }
    }

    public static <K, V> SetMultimap<K, V> filterKeys(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        if (setMultimap instanceof FilteredKeySetMultimap) {
            FilteredKeySetMultimap filteredKeySetMultimap = (FilteredKeySetMultimap) setMultimap;
            return new FilteredKeySetMultimap(filteredKeySetMultimap.a(), Predicates.and(filteredKeySetMultimap.b, predicate));
        } else if (setMultimap instanceof FilteredSetMultimap) {
            return a((FilteredSetMultimap) setMultimap, Maps.a(predicate));
        } else {
            return new FilteredKeySetMultimap(setMultimap, predicate);
        }
    }

    public static <K, V> ListMultimap<K, V> filterKeys(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        if (!(listMultimap instanceof FilteredKeyListMultimap)) {
            return new FilteredKeyListMultimap(listMultimap, predicate);
        }
        FilteredKeyListMultimap filteredKeyListMultimap = (FilteredKeyListMultimap) listMultimap;
        return new FilteredKeyListMultimap(filteredKeyListMultimap.a(), Predicates.and(filteredKeyListMultimap.b, predicate));
    }

    public static <K, V> Multimap<K, V> filterValues(Multimap<K, V> multimap, Predicate<? super V> predicate) {
        return filterEntries(multimap, Maps.b(predicate));
    }

    public static <K, V> SetMultimap<K, V> filterValues(SetMultimap<K, V> setMultimap, Predicate<? super V> predicate) {
        return filterEntries(setMultimap, Maps.b(predicate));
    }

    public static <K, V> Multimap<K, V> filterEntries(Multimap<K, V> multimap, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (multimap instanceof SetMultimap) {
            return filterEntries((SetMultimap) multimap, predicate);
        }
        return multimap instanceof FilteredMultimap ? a((FilteredMultimap) multimap, predicate) : new FilteredEntryMultimap<>((Multimap) Preconditions.checkNotNull(multimap), predicate);
    }

    public static <K, V> SetMultimap<K, V> filterEntries(SetMultimap<K, V> setMultimap, Predicate<? super Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        return setMultimap instanceof FilteredSetMultimap ? a((FilteredSetMultimap) setMultimap, predicate) : new FilteredEntrySetMultimap((SetMultimap) Preconditions.checkNotNull(setMultimap), predicate);
    }

    private static <K, V> Multimap<K, V> a(FilteredMultimap<K, V> filteredMultimap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntryMultimap(filteredMultimap.a(), Predicates.and(filteredMultimap.b(), predicate));
    }

    private static <K, V> SetMultimap<K, V> a(FilteredSetMultimap<K, V> filteredSetMultimap, Predicate<? super Entry<K, V>> predicate) {
        return new FilteredEntrySetMultimap(filteredSetMultimap.d(), Predicates.and(filteredSetMultimap.b(), predicate));
    }

    static boolean a(Multimap<?, ?> multimap, @Nullable Object obj) {
        if (obj == multimap) {
            return true;
        }
        if (!(obj instanceof Multimap)) {
            return false;
        }
        return multimap.asMap().equals(((Multimap) obj).asMap());
    }
}
