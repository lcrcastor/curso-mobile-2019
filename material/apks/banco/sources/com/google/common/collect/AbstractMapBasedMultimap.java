package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultimap$WrappedCollection.WrappedIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    /* access modifiers changed from: private */
    public transient Map<K, Collection<V>> a;
    /* access modifiers changed from: private */
    public transient int b;

    class AsMap extends ViewCachingAbstractMap<K, Collection<V>> {
        final transient Map<K, Collection<V>> a;

        class AsMapEntries extends EntrySet<K, Collection<V>> {
            AsMapEntries() {
            }

            /* access modifiers changed from: 0000 */
            public Map<K, Collection<V>> a() {
                return AsMap.this;
            }

            public Iterator<Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator();
            }

            public boolean contains(Object obj) {
                return Collections2.a((Collection<?>) AsMap.this.a.entrySet(), obj);
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                AbstractMapBasedMultimap.this.c(((Entry) obj).getKey());
                return true;
            }
        }

        class AsMapIterator implements Iterator<Entry<K, Collection<V>>> {
            final Iterator<Entry<K, Collection<V>>> a = AsMap.this.a.entrySet().iterator();
            Collection<V> b;

            AsMapIterator() {
            }

            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public Entry<K, Collection<V>> next() {
                Entry entry = (Entry) this.a.next();
                this.b = (Collection) entry.getValue();
                return AsMap.this.a(entry);
            }

            public void remove() {
                this.a.remove();
                AbstractMapBasedMultimap.b(AbstractMapBasedMultimap.this, this.b.size());
                this.b.clear();
            }
        }

        AsMap(Map<K, Collection<V>> map) {
            this.a = map;
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, Collection<V>>> a() {
            return new AsMapEntries();
        }

        public boolean containsKey(Object obj) {
            return Maps.b(this.a, obj);
        }

        /* renamed from: a */
        public Collection<V> get(Object obj) {
            Collection collection = (Collection) Maps.a(this.a, obj);
            if (collection == null) {
                return null;
            }
            return AbstractMapBasedMultimap.this.a(obj, collection);
        }

        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet();
        }

        public int size() {
            return this.a.size();
        }

        /* renamed from: b */
        public Collection<V> remove(Object obj) {
            Collection collection = (Collection) this.a.remove(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> c = AbstractMapBasedMultimap.this.c();
            c.addAll(collection);
            AbstractMapBasedMultimap.b(AbstractMapBasedMultimap.this, collection.size());
            collection.clear();
            return c;
        }

        public boolean equals(@Nullable Object obj) {
            return this == obj || this.a.equals(obj);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return this.a.toString();
        }

        public void clear() {
            if (this.a == AbstractMapBasedMultimap.this.a) {
                AbstractMapBasedMultimap.this.clear();
            } else {
                Iterators.b(new AsMapIterator());
            }
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, Collection<V>> a(Entry<K, Collection<V>> entry) {
            Object key = entry.getKey();
            return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.a(key, (Collection) entry.getValue()));
        }
    }

    class KeySet extends KeySet<K, Collection<V>> {
        KeySet(Map<K, Collection<V>> map) {
            super(map);
        }

        public Iterator<K> iterator() {
            final Iterator it = c().entrySet().iterator();
            return new Iterator<K>() {
                Entry<K, Collection<V>> a;

                public boolean hasNext() {
                    return it.hasNext();
                }

                public K next() {
                    this.a = (Entry) it.next();
                    return this.a.getKey();
                }

                public void remove() {
                    CollectPreconditions.a(this.a != null);
                    Collection collection = (Collection) this.a.getValue();
                    it.remove();
                    AbstractMapBasedMultimap.b(AbstractMapBasedMultimap.this, collection.size());
                    collection.clear();
                }
            };
        }

        public boolean remove(Object obj) {
            int i;
            Collection collection = (Collection) c().remove(obj);
            if (collection != null) {
                i = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.b(AbstractMapBasedMultimap.this, i);
            } else {
                i = 0;
            }
            if (i > 0) {
                return true;
            }
            return false;
        }

        public void clear() {
            Iterators.b(iterator());
        }

        public boolean containsAll(Collection<?> collection) {
            return c().keySet().containsAll(collection);
        }

        public boolean equals(@Nullable Object obj) {
            return this == obj || c().keySet().equals(obj);
        }

        public int hashCode() {
            return c().keySet().hashCode();
        }
    }

    class RandomAccessWrappedList extends WrappedList implements RandomAccess {
        RandomAccessWrappedList(K k, @Nullable List<V> list, WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection);
        }
    }

    class WrappedCollection extends AbstractCollection<V> {
        final K b;
        Collection<V> c;
        final WrappedCollection d;
        final Collection<V> e;

        class WrappedIterator implements Iterator<V> {
            final Iterator<V> a;
            final Collection<V> b = WrappedCollection.this.c;

            WrappedIterator() {
                this.a = AbstractMapBasedMultimap.this.b(WrappedCollection.this.c);
            }

            WrappedIterator(Iterator<V> it) {
                this.a = it;
            }

            /* access modifiers changed from: 0000 */
            public void a() {
                WrappedCollection.this.a();
                if (WrappedCollection.this.c != this.b) {
                    throw new ConcurrentModificationException();
                }
            }

            public boolean hasNext() {
                a();
                return this.a.hasNext();
            }

            public V next() {
                a();
                return this.a.next();
            }

            public void remove() {
                this.a.remove();
                AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b - 1;
                WrappedCollection.this.b();
            }

            /* access modifiers changed from: 0000 */
            public Iterator<V> b() {
                a();
                return this.a;
            }
        }

        WrappedCollection(K k, @Nullable Collection<V> collection, WrappedCollection wrappedCollection) {
            Collection<V> collection2;
            this.b = k;
            this.c = collection;
            this.d = wrappedCollection;
            if (wrappedCollection == null) {
                collection2 = null;
            } else {
                collection2 = wrappedCollection.e();
            }
            this.e = collection2;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.d != null) {
                this.d.a();
                if (this.d.e() != this.e) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.c.isEmpty()) {
                Collection<V> collection = (Collection) AbstractMapBasedMultimap.this.a.get(this.b);
                if (collection != null) {
                    this.c = collection;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (this.d != null) {
                this.d.b();
            } else if (this.c.isEmpty()) {
                AbstractMapBasedMultimap.this.a.remove(this.b);
            }
        }

        /* access modifiers changed from: 0000 */
        public K c() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (this.d != null) {
                this.d.d();
            } else {
                AbstractMapBasedMultimap.this.a.put(this.b, this.c);
            }
        }

        public int size() {
            a();
            return this.c.size();
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            a();
            return this.c.equals(obj);
        }

        public int hashCode() {
            a();
            return this.c.hashCode();
        }

        public String toString() {
            a();
            return this.c.toString();
        }

        /* access modifiers changed from: 0000 */
        public Collection<V> e() {
            return this.c;
        }

        public Iterator<V> iterator() {
            a();
            return new WrappedIterator();
        }

        public boolean add(V v) {
            a();
            boolean isEmpty = this.c.isEmpty();
            boolean add = this.c.add(v);
            if (add) {
                AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b + 1;
                if (isEmpty) {
                    d();
                }
            }
            return add;
        }

        /* access modifiers changed from: 0000 */
        public WrappedCollection f() {
            return this.d;
        }

        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = this.c.addAll(collection);
            if (addAll) {
                AbstractMapBasedMultimap.a(AbstractMapBasedMultimap.this, this.c.size() - size);
                if (size == 0) {
                    d();
                }
            }
            return addAll;
        }

        public boolean contains(Object obj) {
            a();
            return this.c.contains(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            a();
            return this.c.containsAll(collection);
        }

        public void clear() {
            int size = size();
            if (size != 0) {
                this.c.clear();
                AbstractMapBasedMultimap.b(AbstractMapBasedMultimap.this, size);
                b();
            }
        }

        public boolean remove(Object obj) {
            a();
            boolean remove = this.c.remove(obj);
            if (remove) {
                AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b - 1;
                b();
            }
            return remove;
        }

        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean removeAll = this.c.removeAll(collection);
            if (removeAll) {
                AbstractMapBasedMultimap.a(AbstractMapBasedMultimap.this, this.c.size() - size);
                b();
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            int size = size();
            boolean retainAll = this.c.retainAll(collection);
            if (retainAll) {
                AbstractMapBasedMultimap.a(AbstractMapBasedMultimap.this, this.c.size() - size);
                b();
            }
            return retainAll;
        }
    }

    class WrappedList extends WrappedCollection implements List<V> {

        class WrappedListIterator extends WrappedIterator implements ListIterator<V> {
            WrappedListIterator() {
                super();
            }

            public WrappedListIterator(int i) {
                super(WrappedList.this.g().listIterator(i));
            }

            private ListIterator<V> c() {
                return (ListIterator) b();
            }

            public boolean hasPrevious() {
                return c().hasPrevious();
            }

            public V previous() {
                return c().previous();
            }

            public int nextIndex() {
                return c().nextIndex();
            }

            public int previousIndex() {
                return c().previousIndex();
            }

            public void set(V v) {
                c().set(v);
            }

            public void add(V v) {
                boolean isEmpty = WrappedList.this.isEmpty();
                c().add(v);
                AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b + 1;
                if (isEmpty) {
                    WrappedList.this.d();
                }
            }
        }

        WrappedList(K k, @Nullable List<V> list, WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection);
        }

        /* access modifiers changed from: 0000 */
        public List<V> g() {
            return (List) e();
        }

        public boolean addAll(int i, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = g().addAll(i, collection);
            if (addAll) {
                AbstractMapBasedMultimap.a(AbstractMapBasedMultimap.this, e().size() - size);
                if (size == 0) {
                    d();
                }
            }
            return addAll;
        }

        public V get(int i) {
            a();
            return g().get(i);
        }

        public V set(int i, V v) {
            a();
            return g().set(i, v);
        }

        public void add(int i, V v) {
            a();
            boolean isEmpty = e().isEmpty();
            g().add(i, v);
            AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b + 1;
            if (isEmpty) {
                d();
            }
        }

        public V remove(int i) {
            a();
            V remove = g().remove(i);
            AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b - 1;
            b();
            return remove;
        }

        public int indexOf(Object obj) {
            a();
            return g().indexOf(obj);
        }

        public int lastIndexOf(Object obj) {
            a();
            return g().lastIndexOf(obj);
        }

        public ListIterator<V> listIterator() {
            a();
            return new WrappedListIterator();
        }

        public ListIterator<V> listIterator(int i) {
            a();
            return new WrappedListIterator(i);
        }

        public List<V> subList(int i, int i2) {
            a();
            return AbstractMapBasedMultimap.this.a(c(), g().subList(i, i2), f() == null ? this : f());
        }
    }

    @GwtIncompatible
    class WrappedNavigableSet extends WrappedSortedSet implements NavigableSet<V> {
        WrappedNavigableSet(K k, @Nullable NavigableSet<V> navigableSet, WrappedCollection wrappedCollection) {
            super(k, navigableSet, wrappedCollection);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: g */
        public NavigableSet<V> h() {
            return (NavigableSet) super.h();
        }

        public V lower(V v) {
            return h().lower(v);
        }

        public V floor(V v) {
            return h().floor(v);
        }

        public V ceiling(V v) {
            return h().ceiling(v);
        }

        public V higher(V v) {
            return h().higher(v);
        }

        public V pollFirst() {
            return Iterators.a(iterator());
        }

        public V pollLast() {
            return Iterators.a(descendingIterator());
        }

        private NavigableSet<V> a(NavigableSet<V> navigableSet) {
            return new WrappedNavigableSet(this.b, navigableSet, f() == null ? this : f());
        }

        public NavigableSet<V> descendingSet() {
            return a(h().descendingSet());
        }

        public Iterator<V> descendingIterator() {
            return new WrappedIterator(h().descendingIterator());
        }

        public NavigableSet<V> subSet(V v, boolean z, V v2, boolean z2) {
            return a(h().subSet(v, z, v2, z2));
        }

        public NavigableSet<V> headSet(V v, boolean z) {
            return a(h().headSet(v, z));
        }

        public NavigableSet<V> tailSet(V v, boolean z) {
            return a(h().tailSet(v, z));
        }
    }

    class WrappedSet extends WrappedCollection implements Set<V> {
        WrappedSet(K k, @Nullable Set<V> set) {
            super(k, set, null);
        }

        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean a2 = Sets.a((Set) this.c, collection);
            if (a2) {
                AbstractMapBasedMultimap.a(AbstractMapBasedMultimap.this, this.c.size() - size);
                b();
            }
            return a2;
        }
    }

    class WrappedSortedSet extends WrappedCollection implements SortedSet<V> {
        WrappedSortedSet(K k, @Nullable SortedSet<V> sortedSet, WrappedCollection wrappedCollection) {
            super(k, sortedSet, wrappedCollection);
        }

        /* access modifiers changed from: 0000 */
        public SortedSet<V> h() {
            return (SortedSet) e();
        }

        public Comparator<? super V> comparator() {
            return h().comparator();
        }

        public V first() {
            a();
            return h().first();
        }

        public V last() {
            a();
            return h().last();
        }

        public SortedSet<V> headSet(V v) {
            a();
            return new WrappedSortedSet(c(), h().headSet(v), f() == null ? this : f());
        }

        public SortedSet<V> subSet(V v, V v2) {
            a();
            return new WrappedSortedSet(c(), h().subSet(v, v2), f() == null ? this : f());
        }

        public SortedSet<V> tailSet(V v) {
            a();
            return new WrappedSortedSet(c(), h().tailSet(v), f() == null ? this : f());
        }
    }

    abstract class Itr<T> implements Iterator<T> {
        final Iterator<Entry<K, Collection<V>>> b;
        K c = null;
        Collection<V> d = null;
        Iterator<V> e = Iterators.c();

        /* access modifiers changed from: 0000 */
        public abstract T a(K k, V v);

        Itr() {
            this.b = AbstractMapBasedMultimap.this.a.entrySet().iterator();
        }

        public boolean hasNext() {
            return this.b.hasNext() || this.e.hasNext();
        }

        public T next() {
            if (!this.e.hasNext()) {
                Entry entry = (Entry) this.b.next();
                this.c = entry.getKey();
                this.d = (Collection) entry.getValue();
                this.e = this.d.iterator();
            }
            return a(this.c, this.e.next());
        }

        public void remove() {
            this.e.remove();
            if (this.d.isEmpty()) {
                this.b.remove();
            }
            AbstractMapBasedMultimap.this.b = AbstractMapBasedMultimap.this.b - 1;
        }
    }

    @GwtIncompatible
    class NavigableAsMap extends SortedAsMap implements NavigableMap<K, Collection<V>> {
        NavigableAsMap(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a_ */
        public NavigableMap<K, Collection<V>> g() {
            return (NavigableMap) super.g();
        }

        public Entry<K, Collection<V>> lowerEntry(K k) {
            Entry lowerEntry = g().lowerEntry(k);
            if (lowerEntry == null) {
                return null;
            }
            return a(lowerEntry);
        }

        public K lowerKey(K k) {
            return g().lowerKey(k);
        }

        public Entry<K, Collection<V>> floorEntry(K k) {
            Entry floorEntry = g().floorEntry(k);
            if (floorEntry == null) {
                return null;
            }
            return a(floorEntry);
        }

        public K floorKey(K k) {
            return g().floorKey(k);
        }

        public Entry<K, Collection<V>> ceilingEntry(K k) {
            Entry ceilingEntry = g().ceilingEntry(k);
            if (ceilingEntry == null) {
                return null;
            }
            return a(ceilingEntry);
        }

        public K ceilingKey(K k) {
            return g().ceilingKey(k);
        }

        public Entry<K, Collection<V>> higherEntry(K k) {
            Entry higherEntry = g().higherEntry(k);
            if (higherEntry == null) {
                return null;
            }
            return a(higherEntry);
        }

        public K higherKey(K k) {
            return g().higherKey(k);
        }

        public Entry<K, Collection<V>> firstEntry() {
            Entry firstEntry = g().firstEntry();
            if (firstEntry == null) {
                return null;
            }
            return a(firstEntry);
        }

        public Entry<K, Collection<V>> lastEntry() {
            Entry lastEntry = g().lastEntry();
            if (lastEntry == null) {
                return null;
            }
            return a(lastEntry);
        }

        public Entry<K, Collection<V>> pollFirstEntry() {
            return a(entrySet().iterator());
        }

        public Entry<K, Collection<V>> pollLastEntry() {
            return a(descendingMap().entrySet().iterator());
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, Collection<V>> a(Iterator<Entry<K, Collection<V>>> it) {
            if (!it.hasNext()) {
                return null;
            }
            Entry entry = (Entry) it.next();
            Collection c2 = AbstractMapBasedMultimap.this.c();
            c2.addAll((Collection) entry.getValue());
            it.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.this.a(c2));
        }

        public NavigableMap<K, Collection<V>> descendingMap() {
            return new NavigableAsMap(g().descendingMap());
        }

        /* renamed from: c */
        public NavigableSet<K> keySet() {
            return (NavigableSet) super.keySet();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public NavigableSet<K> h() {
            return new NavigableKeySet(g());
        }

        public NavigableSet<K> navigableKeySet() {
            return keySet();
        }

        public NavigableSet<K> descendingKeySet() {
            return descendingMap().navigableKeySet();
        }

        /* renamed from: a */
        public NavigableMap<K, Collection<V>> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public NavigableMap<K, Collection<V>> subMap(K k, boolean z, K k2, boolean z2) {
            return new NavigableAsMap(g().subMap(k, z, k2, z2));
        }

        /* renamed from: c */
        public NavigableMap<K, Collection<V>> headMap(K k) {
            return headMap(k, false);
        }

        public NavigableMap<K, Collection<V>> headMap(K k, boolean z) {
            return new NavigableAsMap(g().headMap(k, z));
        }

        /* renamed from: d */
        public NavigableMap<K, Collection<V>> tailMap(K k) {
            return tailMap(k, true);
        }

        public NavigableMap<K, Collection<V>> tailMap(K k, boolean z) {
            return new NavigableAsMap(g().tailMap(k, z));
        }
    }

    @GwtIncompatible
    class NavigableKeySet extends SortedKeySet implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public NavigableMap<K, Collection<V>> b() {
            return (NavigableMap) super.b();
        }

        public K lower(K k) {
            return b().lowerKey(k);
        }

        public K floor(K k) {
            return b().floorKey(k);
        }

        public K ceiling(K k) {
            return b().ceilingKey(k);
        }

        public K higher(K k) {
            return b().higherKey(k);
        }

        public K pollFirst() {
            return Iterators.a(iterator());
        }

        public K pollLast() {
            return Iterators.a(descendingIterator());
        }

        public NavigableSet<K> descendingSet() {
            return new NavigableKeySet(b().descendingMap());
        }

        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        /* renamed from: a */
        public NavigableSet<K> headSet(K k) {
            return headSet(k, false);
        }

        public NavigableSet<K> headSet(K k, boolean z) {
            return new NavigableKeySet(b().headMap(k, z));
        }

        /* renamed from: a */
        public NavigableSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return new NavigableKeySet(b().subMap(k, z, k2, z2));
        }

        /* renamed from: b */
        public NavigableSet<K> tailSet(K k) {
            return tailSet(k, true);
        }

        public NavigableSet<K> tailSet(K k, boolean z) {
            return new NavigableKeySet(b().tailMap(k, z));
        }
    }

    class SortedAsMap extends AsMap implements SortedMap<K, Collection<V>> {
        SortedSet<K> d;

        SortedAsMap(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        /* access modifiers changed from: 0000 */
        public SortedMap<K, Collection<V>> g() {
            return (SortedMap) this.a;
        }

        public Comparator<? super K> comparator() {
            return g().comparator();
        }

        public K firstKey() {
            return g().firstKey();
        }

        public K lastKey() {
            return g().lastKey();
        }

        public SortedMap<K, Collection<V>> headMap(K k) {
            return new SortedAsMap(g().headMap(k));
        }

        public SortedMap<K, Collection<V>> subMap(K k, K k2) {
            return new SortedAsMap(g().subMap(k, k2));
        }

        public SortedMap<K, Collection<V>> tailMap(K k) {
            return new SortedAsMap(g().tailMap(k));
        }

        /* renamed from: f */
        public SortedSet<K> keySet() {
            SortedSet<K> sortedSet = this.d;
            if (sortedSet != null) {
                return sortedSet;
            }
            SortedSet<K> e2 = h();
            this.d = e2;
            return e2;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: e */
        public SortedSet<K> h() {
            return new SortedKeySet(g());
        }
    }

    class SortedKeySet extends KeySet implements SortedSet<K> {
        SortedKeySet(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        /* access modifiers changed from: 0000 */
        public SortedMap<K, Collection<V>> b() {
            return (SortedMap) super.c();
        }

        public Comparator<? super K> comparator() {
            return b().comparator();
        }

        public K first() {
            return b().firstKey();
        }

        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(b().headMap(k));
        }

        public K last() {
            return b().lastKey();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(b().subMap(k, k2));
        }

        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(b().tailMap(k));
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Collection<V> c();

    static /* synthetic */ int a(AbstractMapBasedMultimap abstractMapBasedMultimap, int i) {
        int i2 = abstractMapBasedMultimap.b + i;
        abstractMapBasedMultimap.b = i2;
        return i2;
    }

    static /* synthetic */ int b(AbstractMapBasedMultimap abstractMapBasedMultimap, int i) {
        int i2 = abstractMapBasedMultimap.b - i;
        abstractMapBasedMultimap.b = i2;
        return i2;
    }

    protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.a = map;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Map<K, Collection<V>> map) {
        this.a = map;
        this.b = 0;
        for (Collection collection : map.values()) {
            Preconditions.checkArgument(!collection.isEmpty());
            this.b += collection.size();
        }
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> d() {
        return a(c());
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> a(@Nullable K k) {
        return c();
    }

    /* access modifiers changed from: 0000 */
    public Map<K, Collection<V>> e() {
        return this.a;
    }

    public int size() {
        return this.b;
    }

    public boolean containsKey(@Nullable Object obj) {
        return this.a.containsKey(obj);
    }

    public boolean put(@Nullable K k, @Nullable V v) {
        Collection collection = (Collection) this.a.get(k);
        if (collection == null) {
            Collection a2 = a(k);
            if (a2.add(v)) {
                this.b++;
                this.a.put(k, a2);
                return true;
            }
            throw new AssertionError("New Collection violated the Collection spec");
        } else if (!collection.add(v)) {
            return false;
        } else {
            this.b++;
            return true;
        }
    }

    private Collection<V> b(@Nullable K k) {
        Collection<V> collection = (Collection) this.a.get(k);
        if (collection != null) {
            return collection;
        }
        Collection<V> a2 = a(k);
        this.a.put(k, a2);
        return a2;
    }

    public Collection<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return removeAll(k);
        }
        Collection b2 = b(k);
        Collection c = c();
        c.addAll(b2);
        this.b -= b2.size();
        b2.clear();
        while (it.hasNext()) {
            if (b2.add(it.next())) {
                this.b++;
            }
        }
        return a(c);
    }

    public Collection<V> removeAll(@Nullable Object obj) {
        Collection collection = (Collection) this.a.remove(obj);
        if (collection == null) {
            return d();
        }
        Collection c = c();
        c.addAll(collection);
        this.b -= collection.size();
        collection.clear();
        return a(c);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> a(Collection<V> collection) {
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

    public void clear() {
        for (Collection clear : this.a.values()) {
            clear.clear();
        }
        this.a.clear();
        this.b = 0;
    }

    public Collection<V> get(@Nullable K k) {
        Collection collection = (Collection) this.a.get(k);
        if (collection == null) {
            collection = a(k);
        }
        return a(k, collection);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> a(@Nullable K k, Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return new WrappedSortedSet(k, (SortedSet) collection, null);
        }
        if (collection instanceof Set) {
            return new WrappedSet(k, (Set) collection);
        }
        if (collection instanceof List) {
            return a(k, (List) collection, null);
        }
        return new WrappedCollection(k, collection, null);
    }

    /* access modifiers changed from: private */
    public List<V> a(@Nullable K k, List<V> list, @Nullable WrappedCollection wrappedCollection) {
        return list instanceof RandomAccess ? new RandomAccessWrappedList(k, list, wrappedCollection) : new WrappedList(k, list, wrappedCollection);
    }

    /* access modifiers changed from: private */
    public Iterator<V> b(Collection<V> collection) {
        return collection instanceof List ? ((List) collection).listIterator() : collection.iterator();
    }

    /* access modifiers changed from: 0000 */
    public Set<K> f() {
        return this.a instanceof SortedMap ? new SortedKeySet((SortedMap) this.a) : new KeySet(this.a);
    }

    /* access modifiers changed from: private */
    public void c(Object obj) {
        Collection collection = (Collection) Maps.c(this.a, obj);
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            this.b -= size;
        }
    }

    public Collection<V> values() {
        return super.values();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<V> g() {
        return new Itr<V>() {
            /* access modifiers changed from: 0000 */
            public V a(K k, V v) {
                return v;
            }
        };
    }

    public Collection<Entry<K, V>> entries() {
        return super.entries();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> h() {
        return new Itr<Entry<K, V>>() {
            /* access modifiers changed from: 0000 */
            /* renamed from: b */
            public Entry<K, V> a(K k, V v) {
                return Maps.immutableEntry(k, v);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Map<K, Collection<V>> i() {
        return this.a instanceof SortedMap ? new SortedAsMap((SortedMap) this.a) : new AsMap(this.a);
    }
}
