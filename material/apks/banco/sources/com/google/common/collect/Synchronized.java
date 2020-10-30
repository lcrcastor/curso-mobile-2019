package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.RetainedWith;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class Synchronized {

    static class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0;
        transient Set<Entry<K, Collection<V>>> a;
        transient Collection<Collection<V>> b;

        SynchronizedAsMap(Map<K, Collection<V>> map, @Nullable Object obj) {
            super(map, obj);
        }

        /* renamed from: a */
        public Collection<V> get(Object obj) {
            Collection<V> collection;
            synchronized (this.h) {
                Collection collection2 = (Collection) super.get(obj);
                if (collection2 == null) {
                    collection = null;
                } else {
                    collection = Synchronized.d(collection2, this.h);
                }
            }
            return collection;
        }

        public Set<Entry<K, Collection<V>>> entrySet() {
            Set<Entry<K, Collection<V>>> set;
            synchronized (this.h) {
                if (this.a == null) {
                    this.a = new SynchronizedAsMapEntries(c().entrySet(), this.h);
                }
                set = this.a;
            }
            return set;
        }

        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.h) {
                if (this.b == null) {
                    this.b = new SynchronizedAsMapValues(c().values(), this.h);
                }
                collection = this.b;
            }
            return collection;
        }

        public boolean containsValue(Object obj) {
            return values().contains(obj);
        }
    }

    static class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapEntries(Set<Entry<K, Collection<V>>> set, @Nullable Object obj) {
            super(set, obj);
        }

        public Iterator<Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Entry<K, Collection<V>>, Entry<K, Collection<V>>>(super.iterator()) {
                /* access modifiers changed from: 0000 */
                public Entry<K, Collection<V>> a(final Entry<K, Collection<V>> entry) {
                    return new ForwardingMapEntry<K, Collection<V>>() {
                        /* access modifiers changed from: protected */
                        public Entry<K, Collection<V>> delegate() {
                            return entry;
                        }

                        /* renamed from: a */
                        public Collection<V> getValue() {
                            return Synchronized.d((Collection) entry.getValue(), SynchronizedAsMapEntries.this.h);
                        }
                    };
                }
            };
        }

        public Object[] toArray() {
            Object[] a;
            synchronized (this.h) {
                a = ObjectArrays.a((Collection<?>) c());
            }
            return a;
        }

        public <T> T[] toArray(T[] tArr) {
            T[] a;
            synchronized (this.h) {
                a = ObjectArrays.a((Collection<?>) c(), tArr);
            }
            return a;
        }

        public boolean contains(Object obj) {
            boolean a;
            synchronized (this.h) {
                a = Maps.a((Collection<Entry<K, V>>) c(), obj);
            }
            return a;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean a;
            synchronized (this.h) {
                a = Collections2.a((Collection<?>) c(), collection);
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean a;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                a = Sets.a(c(), obj);
            }
            return a;
        }

        public boolean remove(Object obj) {
            boolean b;
            synchronized (this.h) {
                b = Maps.b((Collection<Entry<K, V>>) c(), obj);
            }
            return b;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.h) {
                removeAll = Iterators.removeAll(c().iterator(), collection);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.h) {
                retainAll = Iterators.retainAll(c().iterator(), collection);
            }
            return retainAll;
        }
    }

    static class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0;

        SynchronizedAsMapValues(Collection<Collection<V>> collection, @Nullable Object obj) {
            super(collection, obj);
        }

        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()) {
                /* access modifiers changed from: 0000 */
                public Collection<V> a(Collection<V> collection) {
                    return Synchronized.d(collection, SynchronizedAsMapValues.this.h);
                }
            };
        }
    }

    @VisibleForTesting
    static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        private transient Set<V> a;
        @RetainedWith
        private transient BiMap<V, K> b;

        private SynchronizedBiMap(BiMap<K, V> biMap, @Nullable Object obj, @Nullable BiMap<V, K> biMap2) {
            super(biMap, obj);
            this.b = biMap2;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public BiMap<K, V> c() {
            return (BiMap) super.c();
        }

        public Set<V> values() {
            Set<V> set;
            synchronized (this.h) {
                if (this.a == null) {
                    this.a = Synchronized.a(c().values(), this.h);
                }
                set = this.a;
            }
            return set;
        }

        public V forcePut(K k, V v) {
            V forcePut;
            synchronized (this.h) {
                forcePut = c().forcePut(k, v);
            }
            return forcePut;
        }

        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.h) {
                if (this.b == null) {
                    this.b = new SynchronizedBiMap(c().inverse(), this.h, this);
                }
                biMap = this.b;
            }
            return biMap;
        }
    }

    @VisibleForTesting
    static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        private SynchronizedCollection(Collection<E> collection, @Nullable Object obj) {
            super(collection, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public Collection<E> c() {
            return (Collection) super.c();
        }

        public boolean add(E e) {
            boolean add;
            synchronized (this.h) {
                add = c().add(e);
            }
            return add;
        }

        public boolean addAll(Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.h) {
                addAll = c().addAll(collection);
            }
            return addAll;
        }

        public void clear() {
            synchronized (this.h) {
                c().clear();
            }
        }

        public boolean contains(Object obj) {
            boolean contains;
            synchronized (this.h) {
                contains = c().contains(obj);
            }
            return contains;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean containsAll;
            synchronized (this.h) {
                containsAll = c().containsAll(collection);
            }
            return containsAll;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.h) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        public Iterator<E> iterator() {
            return c().iterator();
        }

        public boolean remove(Object obj) {
            boolean remove;
            synchronized (this.h) {
                remove = c().remove(obj);
            }
            return remove;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.h) {
                removeAll = c().removeAll(collection);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.h) {
                retainAll = c().retainAll(collection);
            }
            return retainAll;
        }

        public int size() {
            int size;
            synchronized (this.h) {
                size = c().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.h) {
                array = c().toArray();
            }
            return array;
        }

        public <T> T[] toArray(T[] tArr) {
            T[] array;
            synchronized (this.h) {
                array = c().toArray(tArr);
            }
            return array;
        }
    }

    static final class SynchronizedDeque<E> extends SynchronizedQueue<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        SynchronizedDeque(Deque<E> deque, @Nullable Object obj) {
            super(deque, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Deque<E> d() {
            return (Deque) super.c();
        }

        public void addFirst(E e) {
            synchronized (this.h) {
                d().addFirst(e);
            }
        }

        public void addLast(E e) {
            synchronized (this.h) {
                d().addLast(e);
            }
        }

        public boolean offerFirst(E e) {
            boolean offerFirst;
            synchronized (this.h) {
                offerFirst = d().offerFirst(e);
            }
            return offerFirst;
        }

        public boolean offerLast(E e) {
            boolean offerLast;
            synchronized (this.h) {
                offerLast = d().offerLast(e);
            }
            return offerLast;
        }

        public E removeFirst() {
            E removeFirst;
            synchronized (this.h) {
                removeFirst = d().removeFirst();
            }
            return removeFirst;
        }

        public E removeLast() {
            E removeLast;
            synchronized (this.h) {
                removeLast = d().removeLast();
            }
            return removeLast;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.h) {
                pollFirst = d().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.h) {
                pollLast = d().pollLast();
            }
            return pollLast;
        }

        public E getFirst() {
            E first;
            synchronized (this.h) {
                first = d().getFirst();
            }
            return first;
        }

        public E getLast() {
            E last;
            synchronized (this.h) {
                last = d().getLast();
            }
            return last;
        }

        public E peekFirst() {
            E peekFirst;
            synchronized (this.h) {
                peekFirst = d().peekFirst();
            }
            return peekFirst;
        }

        public E peekLast() {
            E peekLast;
            synchronized (this.h) {
                peekLast = d().peekLast();
            }
            return peekLast;
        }

        public boolean removeFirstOccurrence(Object obj) {
            boolean removeFirstOccurrence;
            synchronized (this.h) {
                removeFirstOccurrence = d().removeFirstOccurrence(obj);
            }
            return removeFirstOccurrence;
        }

        public boolean removeLastOccurrence(Object obj) {
            boolean removeLastOccurrence;
            synchronized (this.h) {
                removeLastOccurrence = d().removeLastOccurrence(obj);
            }
            return removeLastOccurrence;
        }

        public void push(E e) {
            synchronized (this.h) {
                d().push(e);
            }
        }

        public E pop() {
            E pop;
            synchronized (this.h) {
                pop = d().pop();
            }
            return pop;
        }

        public Iterator<E> descendingIterator() {
            Iterator<E> descendingIterator;
            synchronized (this.h) {
                descendingIterator = d().descendingIterator();
            }
            return descendingIterator;
        }
    }

    @GwtIncompatible
    static class SynchronizedEntry<K, V> extends SynchronizedObject implements Entry<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedEntry(Entry<K, V> entry, @Nullable Object obj) {
            super(entry, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public Entry<K, V> c() {
            return (Entry) super.c();
        }

        public boolean equals(Object obj) {
            boolean equals;
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }

        public K getKey() {
            K key;
            synchronized (this.h) {
                key = c().getKey();
            }
            return key;
        }

        public V getValue() {
            V value;
            synchronized (this.h) {
                value = c().getValue();
            }
            return value;
        }

        public V setValue(V v) {
            V value;
            synchronized (this.h) {
                value = c().setValue(v);
            }
            return value;
        }
    }

    static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
        private static final long serialVersionUID = 0;

        SynchronizedList(List<E> list, @Nullable Object obj) {
            super(list, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public List<E> c() {
            return (List) super.c();
        }

        public void add(int i, E e) {
            synchronized (this.h) {
                c().add(i, e);
            }
        }

        public boolean addAll(int i, Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.h) {
                addAll = c().addAll(i, collection);
            }
            return addAll;
        }

        public E get(int i) {
            E e;
            synchronized (this.h) {
                e = c().get(i);
            }
            return e;
        }

        public int indexOf(Object obj) {
            int indexOf;
            synchronized (this.h) {
                indexOf = c().indexOf(obj);
            }
            return indexOf;
        }

        public int lastIndexOf(Object obj) {
            int lastIndexOf;
            synchronized (this.h) {
                lastIndexOf = c().lastIndexOf(obj);
            }
            return lastIndexOf;
        }

        public ListIterator<E> listIterator() {
            return c().listIterator();
        }

        public ListIterator<E> listIterator(int i) {
            return c().listIterator(i);
        }

        public E remove(int i) {
            E remove;
            synchronized (this.h) {
                remove = c().remove(i);
            }
            return remove;
        }

        public E set(int i, E e) {
            E e2;
            synchronized (this.h) {
                e2 = c().set(i, e);
            }
            return e2;
        }

        public List<E> subList(int i, int i2) {
            List<E> a;
            synchronized (this.h) {
                a = Synchronized.b(c().subList(i, i2), this.h);
            }
            return a;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    static class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedListMultimap(ListMultimap<K, V> listMultimap, @Nullable Object obj) {
            super(listMultimap, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public ListMultimap<K, V> c() {
            return (ListMultimap) super.c();
        }

        public List<V> get(K k) {
            List<V> a;
            synchronized (this.h) {
                a = Synchronized.b(c().get(k), this.h);
            }
            return a;
        }

        public List<V> removeAll(Object obj) {
            List<V> removeAll;
            synchronized (this.h) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            List<V> replaceValues;
            synchronized (this.h) {
                replaceValues = c().replaceValues(k, iterable);
            }
            return replaceValues;
        }
    }

    static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;
        transient Set<K> c;
        transient Collection<V> d;
        transient Set<Entry<K, V>> e;

        SynchronizedMap(Map<K, V> map, @Nullable Object obj) {
            super(map, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Map<K, V> c() {
            return (Map) super.c();
        }

        public void clear() {
            synchronized (this.h) {
                c().clear();
            }
        }

        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.h) {
                containsKey = c().containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.h) {
                containsValue = c().containsValue(obj);
            }
            return containsValue;
        }

        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> set;
            synchronized (this.h) {
                if (this.e == null) {
                    this.e = Synchronized.a(c().entrySet(), this.h);
                }
                set = this.e;
            }
            return set;
        }

        public V get(Object obj) {
            V v;
            synchronized (this.h) {
                v = c().get(obj);
            }
            return v;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.h) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.h) {
                if (this.c == null) {
                    this.c = Synchronized.a(c().keySet(), this.h);
                }
                set = this.c;
            }
            return set;
        }

        public V put(K k, V v) {
            V put;
            synchronized (this.h) {
                put = c().put(k, v);
            }
            return put;
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.h) {
                c().putAll(map);
            }
        }

        public V remove(Object obj) {
            V remove;
            synchronized (this.h) {
                remove = c().remove(obj);
            }
            return remove;
        }

        public int size() {
            int size;
            synchronized (this.h) {
                size = c().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.h) {
                if (this.d == null) {
                    this.d = Synchronized.c(c().values(), this.h);
                }
                collection = this.d;
            }
            return collection;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V> {
        private static final long serialVersionUID = 0;
        transient Set<K> a;
        transient Collection<V> b;
        transient Collection<Entry<K, V>> c;
        transient Map<K, Collection<V>> d;
        transient Multiset<K> e;

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Multimap<K, V> c() {
            return (Multimap) super.c();
        }

        SynchronizedMultimap(Multimap<K, V> multimap, @Nullable Object obj) {
            super(multimap, obj);
        }

        public int size() {
            int size;
            synchronized (this.h) {
                size = c().size();
            }
            return size;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.h) {
                isEmpty = c().isEmpty();
            }
            return isEmpty;
        }

        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.h) {
                containsKey = c().containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.h) {
                containsValue = c().containsValue(obj);
            }
            return containsValue;
        }

        public boolean containsEntry(Object obj, Object obj2) {
            boolean containsEntry;
            synchronized (this.h) {
                containsEntry = c().containsEntry(obj, obj2);
            }
            return containsEntry;
        }

        public Collection<V> get(K k) {
            Collection<V> a2;
            synchronized (this.h) {
                a2 = Synchronized.d(c().get(k), this.h);
            }
            return a2;
        }

        public boolean put(K k, V v) {
            boolean put;
            synchronized (this.h) {
                put = c().put(k, v);
            }
            return put;
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            boolean putAll;
            synchronized (this.h) {
                putAll = c().putAll(k, iterable);
            }
            return putAll;
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean putAll;
            synchronized (this.h) {
                putAll = c().putAll(multimap);
            }
            return putAll;
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Collection<V> replaceValues;
            synchronized (this.h) {
                replaceValues = c().replaceValues(k, iterable);
            }
            return replaceValues;
        }

        public boolean remove(Object obj, Object obj2) {
            boolean remove;
            synchronized (this.h) {
                remove = c().remove(obj, obj2);
            }
            return remove;
        }

        public Collection<V> removeAll(Object obj) {
            Collection<V> removeAll;
            synchronized (this.h) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        public void clear() {
            synchronized (this.h) {
                c().clear();
            }
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.h) {
                if (this.a == null) {
                    this.a = Synchronized.c(c().keySet(), this.h);
                }
                set = this.a;
            }
            return set;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.h) {
                if (this.b == null) {
                    this.b = Synchronized.c(c().values(), this.h);
                }
                collection = this.b;
            }
            return collection;
        }

        public Collection<Entry<K, V>> entries() {
            Collection<Entry<K, V>> collection;
            synchronized (this.h) {
                if (this.c == null) {
                    this.c = Synchronized.d(c().entries(), this.h);
                }
                collection = this.c;
            }
            return collection;
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.h) {
                if (this.d == null) {
                    this.d = new SynchronizedAsMap(c().asMap(), this.h);
                }
                map = this.d;
            }
            return map;
        }

        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.h) {
                if (this.e == null) {
                    this.e = Synchronized.a(c().keys(), this.h);
                }
                multiset = this.e;
            }
            return multiset;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    static class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;
        transient Set<E> a;
        transient Set<Multiset.Entry<E>> b;

        SynchronizedMultiset(Multiset<E> multiset, @Nullable Object obj) {
            super(multiset, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Multiset<E> c() {
            return (Multiset) super.c();
        }

        public int count(Object obj) {
            int count;
            synchronized (this.h) {
                count = c().count(obj);
            }
            return count;
        }

        public int add(E e, int i) {
            int add;
            synchronized (this.h) {
                add = c().add(e, i);
            }
            return add;
        }

        public int remove(Object obj, int i) {
            int remove;
            synchronized (this.h) {
                remove = c().remove(obj, i);
            }
            return remove;
        }

        public int setCount(E e, int i) {
            int count;
            synchronized (this.h) {
                count = c().setCount(e, i);
            }
            return count;
        }

        public boolean setCount(E e, int i, int i2) {
            boolean count;
            synchronized (this.h) {
                count = c().setCount(e, i, i2);
            }
            return count;
        }

        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.h) {
                if (this.a == null) {
                    this.a = Synchronized.c(c().elementSet(), this.h);
                }
                set = this.a;
            }
            return set;
        }

        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.h) {
                if (this.b == null) {
                    this.b = Synchronized.c(c().entrySet(), this.h);
                }
                set = this.b;
            }
            return set;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;
        transient NavigableSet<K> a;
        transient NavigableMap<K, V> b;
        transient NavigableSet<K> f;

        SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, @Nullable Object obj) {
            super(navigableMap, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public NavigableMap<K, V> d() {
            return (NavigableMap) super.c();
        }

        public Entry<K, V> ceilingEntry(K k) {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().ceilingEntry(k), this.h);
            }
            return a2;
        }

        public K ceilingKey(K k) {
            K ceilingKey;
            synchronized (this.h) {
                ceilingKey = d().ceilingKey(k);
            }
            return ceilingKey;
        }

        public NavigableSet<K> descendingKeySet() {
            synchronized (this.h) {
                if (this.a == null) {
                    NavigableSet<K> a2 = Synchronized.a(d().descendingKeySet(), this.h);
                    this.a = a2;
                    return a2;
                }
                NavigableSet<K> navigableSet = this.a;
                return navigableSet;
            }
        }

        public NavigableMap<K, V> descendingMap() {
            synchronized (this.h) {
                if (this.b == null) {
                    NavigableMap<K, V> a2 = Synchronized.a(d().descendingMap(), this.h);
                    this.b = a2;
                    return a2;
                }
                NavigableMap<K, V> navigableMap = this.b;
                return navigableMap;
            }
        }

        public Entry<K, V> firstEntry() {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().firstEntry(), this.h);
            }
            return a2;
        }

        public Entry<K, V> floorEntry(K k) {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().floorEntry(k), this.h);
            }
            return a2;
        }

        public K floorKey(K k) {
            K floorKey;
            synchronized (this.h) {
                floorKey = d().floorKey(k);
            }
            return floorKey;
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            NavigableMap<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(d().headMap(k, z), this.h);
            }
            return a2;
        }

        public Entry<K, V> higherEntry(K k) {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().higherEntry(k), this.h);
            }
            return a2;
        }

        public K higherKey(K k) {
            K higherKey;
            synchronized (this.h) {
                higherKey = d().higherKey(k);
            }
            return higherKey;
        }

        public Entry<K, V> lastEntry() {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().lastEntry(), this.h);
            }
            return a2;
        }

        public Entry<K, V> lowerEntry(K k) {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().lowerEntry(k), this.h);
            }
            return a2;
        }

        public K lowerKey(K k) {
            K lowerKey;
            synchronized (this.h) {
                lowerKey = d().lowerKey(k);
            }
            return lowerKey;
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            synchronized (this.h) {
                if (this.f == null) {
                    NavigableSet<K> a2 = Synchronized.a(d().navigableKeySet(), this.h);
                    this.f = a2;
                    return a2;
                }
                NavigableSet<K> navigableSet = this.f;
                return navigableSet;
            }
        }

        public Entry<K, V> pollFirstEntry() {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().pollFirstEntry(), this.h);
            }
            return a2;
        }

        public Entry<K, V> pollLastEntry() {
            Entry<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.b(d().pollLastEntry(), this.h);
            }
            return a2;
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            NavigableMap<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(d().subMap(k, z, k2, z2), this.h);
            }
            return a2;
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            NavigableMap<K, V> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(d().tailMap(k, z), this.h);
            }
            return a2;
        }

        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;
        transient NavigableSet<E> a;

        SynchronizedNavigableSet(NavigableSet<E> navigableSet, @Nullable Object obj) {
            super(navigableSet, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public NavigableSet<E> e() {
            return (NavigableSet) super.e();
        }

        public E ceiling(E e) {
            E ceiling;
            synchronized (this.h) {
                ceiling = e().ceiling(e);
            }
            return ceiling;
        }

        public Iterator<E> descendingIterator() {
            return e().descendingIterator();
        }

        public NavigableSet<E> descendingSet() {
            synchronized (this.h) {
                if (this.a == null) {
                    NavigableSet<E> a2 = Synchronized.a(e().descendingSet(), this.h);
                    this.a = a2;
                    return a2;
                }
                NavigableSet<E> navigableSet = this.a;
                return navigableSet;
            }
        }

        public E floor(E e) {
            E floor;
            synchronized (this.h) {
                floor = e().floor(e);
            }
            return floor;
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            NavigableSet<E> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(e().headSet(e, z), this.h);
            }
            return a2;
        }

        public E higher(E e) {
            E higher;
            synchronized (this.h) {
                higher = e().higher(e);
            }
            return higher;
        }

        public E lower(E e) {
            E lower;
            synchronized (this.h) {
                lower = e().lower(e);
            }
            return lower;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.h) {
                pollFirst = e().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.h) {
                pollLast = e().pollLast();
            }
            return pollLast;
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            NavigableSet<E> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(e().subSet(e, z, e2, z2), this.h);
            }
            return a2;
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            NavigableSet<E> a2;
            synchronized (this.h) {
                a2 = Synchronized.a(e().tailSet(e, z), this.h);
            }
            return a2;
        }

        public SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        public SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        public SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }
    }

    static class SynchronizedObject implements Serializable {
        @GwtIncompatible
        private static final long serialVersionUID = 0;
        final Object g;
        final Object h;

        SynchronizedObject(Object obj, @Nullable Object obj2) {
            this.g = Preconditions.checkNotNull(obj);
            if (obj2 == 0) {
                obj2 = this;
            }
            this.h = obj2;
        }

        /* access modifiers changed from: 0000 */
        public Object c() {
            return this.g;
        }

        public String toString() {
            String obj;
            synchronized (this.h) {
                obj = this.g.toString();
            }
            return obj;
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            synchronized (this.h) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }

    static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        SynchronizedQueue(Queue<E> queue, @Nullable Object obj) {
            super(queue, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public Queue<E> c() {
            return (Queue) super.c();
        }

        public E element() {
            E element;
            synchronized (this.h) {
                element = c().element();
            }
            return element;
        }

        public boolean offer(E e) {
            boolean offer;
            synchronized (this.h) {
                offer = c().offer(e);
            }
            return offer;
        }

        public E peek() {
            E peek;
            synchronized (this.h) {
                peek = c().peek();
            }
            return peek;
        }

        public E poll() {
            E poll;
            synchronized (this.h) {
                poll = c().poll();
            }
            return poll;
        }

        public E remove() {
            E remove;
            synchronized (this.h) {
                remove = c().remove();
            }
            return remove;
        }
    }

    static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        SynchronizedRandomAccessList(List<E> list, @Nullable Object obj) {
            super(list, obj);
        }
    }

    static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSet(Set<E> set, @Nullable Object obj) {
            super(set, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: e */
        public Set<E> c() {
            return (Set) super.c();
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.h) {
                equals = c().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.h) {
                hashCode = c().hashCode();
            }
            return hashCode;
        }
    }

    static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        transient Set<Entry<K, V>> f;

        SynchronizedSetMultimap(SetMultimap<K, V> setMultimap, @Nullable Object obj) {
            super(setMultimap, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public SetMultimap<K, V> c() {
            return (SetMultimap) super.c();
        }

        public Set<V> get(K k) {
            Set<V> a;
            synchronized (this.h) {
                a = Synchronized.a(c().get(k), this.h);
            }
            return a;
        }

        public Set<V> removeAll(Object obj) {
            Set<V> removeAll;
            synchronized (this.h) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Set<V> replaceValues;
            synchronized (this.h) {
                replaceValues = c().replaceValues(k, iterable);
            }
            return replaceValues;
        }

        public Set<Entry<K, V>> entries() {
            Set<Entry<K, V>> set;
            synchronized (this.h) {
                if (this.f == null) {
                    this.f = Synchronized.a(c().entries(), this.h);
                }
                set = this.f;
            }
            return set;
        }
    }

    static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedMap(SortedMap<K, V> sortedMap, @Nullable Object obj) {
            super(sortedMap, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public SortedMap<K, V> c() {
            return (SortedMap) super.c();
        }

        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.h) {
                comparator = c().comparator();
            }
            return comparator;
        }

        public K firstKey() {
            K firstKey;
            synchronized (this.h) {
                firstKey = c().firstKey();
            }
            return firstKey;
        }

        public SortedMap<K, V> headMap(K k) {
            SortedMap<K, V> a;
            synchronized (this.h) {
                a = Synchronized.a(c().headMap(k), this.h);
            }
            return a;
        }

        public K lastKey() {
            K lastKey;
            synchronized (this.h) {
                lastKey = c().lastKey();
            }
            return lastKey;
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            SortedMap<K, V> a;
            synchronized (this.h) {
                a = Synchronized.a(c().subMap(k, k2), this.h);
            }
            return a;
        }

        public SortedMap<K, V> tailMap(K k) {
            SortedMap<K, V> a;
            synchronized (this.h) {
                a = Synchronized.a(c().tailMap(k), this.h);
            }
            return a;
        }
    }

    static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSet(SortedSet<E> sortedSet, @Nullable Object obj) {
            super(sortedSet, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public SortedSet<E> e() {
            return (SortedSet) super.c();
        }

        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.h) {
                comparator = e().comparator();
            }
            return comparator;
        }

        public SortedSet<E> subSet(E e, E e2) {
            SortedSet<E> a;
            synchronized (this.h) {
                a = Synchronized.b(e().subSet(e, e2), this.h);
            }
            return a;
        }

        public SortedSet<E> headSet(E e) {
            SortedSet<E> a;
            synchronized (this.h) {
                a = Synchronized.b(e().headSet(e), this.h);
            }
            return a;
        }

        public SortedSet<E> tailSet(E e) {
            SortedSet<E> a;
            synchronized (this.h) {
                a = Synchronized.b(e().tailSet(e), this.h);
            }
            return a;
        }

        public E first() {
            E first;
            synchronized (this.h) {
                first = e().first();
            }
            return first;
        }

        public E last() {
            E last;
            synchronized (this.h) {
                last = e().last();
            }
            return last;
        }
    }

    static class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap, @Nullable Object obj) {
            super(sortedSetMultimap, obj);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public SortedSetMultimap<K, V> c() {
            return (SortedSetMultimap) super.c();
        }

        public SortedSet<V> get(K k) {
            SortedSet<V> a;
            synchronized (this.h) {
                a = Synchronized.b(c().get(k), this.h);
            }
            return a;
        }

        public SortedSet<V> removeAll(Object obj) {
            SortedSet<V> removeAll;
            synchronized (this.h) {
                removeAll = c().removeAll(obj);
            }
            return removeAll;
        }

        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            SortedSet<V> replaceValues;
            synchronized (this.h) {
                replaceValues = c().replaceValues(k, iterable);
            }
            return replaceValues;
        }

        public Comparator<? super V> valueComparator() {
            Comparator<? super V> valueComparator;
            synchronized (this.h) {
                valueComparator = c().valueComparator();
            }
            return valueComparator;
        }
    }

    private Synchronized() {
    }

    /* access modifiers changed from: private */
    public static <E> Collection<E> c(Collection<E> collection, @Nullable Object obj) {
        return new SynchronizedCollection(collection, obj);
    }

    @VisibleForTesting
    static <E> Set<E> a(Set<E> set, @Nullable Object obj) {
        return new SynchronizedSet(set, obj);
    }

    /* access modifiers changed from: private */
    public static <E> SortedSet<E> b(SortedSet<E> sortedSet, @Nullable Object obj) {
        return new SynchronizedSortedSet(sortedSet, obj);
    }

    /* access modifiers changed from: private */
    public static <E> List<E> b(List<E> list, @Nullable Object obj) {
        return list instanceof RandomAccess ? new SynchronizedRandomAccessList(list, obj) : new SynchronizedList(list, obj);
    }

    static <E> Multiset<E> a(Multiset<E> multiset, @Nullable Object obj) {
        return ((multiset instanceof SynchronizedMultiset) || (multiset instanceof ImmutableMultiset)) ? multiset : new SynchronizedMultiset(multiset, obj);
    }

    static <K, V> Multimap<K, V> a(Multimap<K, V> multimap, @Nullable Object obj) {
        return ((multimap instanceof SynchronizedMultimap) || (multimap instanceof ImmutableMultimap)) ? multimap : new SynchronizedMultimap(multimap, obj);
    }

    static <K, V> ListMultimap<K, V> a(ListMultimap<K, V> listMultimap, @Nullable Object obj) {
        return ((listMultimap instanceof SynchronizedListMultimap) || (listMultimap instanceof ImmutableListMultimap)) ? listMultimap : new SynchronizedListMultimap(listMultimap, obj);
    }

    static <K, V> SetMultimap<K, V> a(SetMultimap<K, V> setMultimap, @Nullable Object obj) {
        return ((setMultimap instanceof SynchronizedSetMultimap) || (setMultimap instanceof ImmutableSetMultimap)) ? setMultimap : new SynchronizedSetMultimap(setMultimap, obj);
    }

    static <K, V> SortedSetMultimap<K, V> a(SortedSetMultimap<K, V> sortedSetMultimap, @Nullable Object obj) {
        if (sortedSetMultimap instanceof SynchronizedSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new SynchronizedSortedSetMultimap(sortedSetMultimap, obj);
    }

    /* access modifiers changed from: private */
    public static <E> Collection<E> d(Collection<E> collection, @Nullable Object obj) {
        if (collection instanceof SortedSet) {
            return b((SortedSet) collection, obj);
        }
        if (collection instanceof Set) {
            return a((Set) collection, obj);
        }
        if (collection instanceof List) {
            return b((List) collection, obj);
        }
        return c(collection, obj);
    }

    /* access modifiers changed from: private */
    public static <E> Set<E> c(Set<E> set, @Nullable Object obj) {
        if (set instanceof SortedSet) {
            return b((SortedSet) set, obj);
        }
        return a(set, obj);
    }

    static <K, V> SortedMap<K, V> a(SortedMap<K, V> sortedMap, @Nullable Object obj) {
        return new SynchronizedSortedMap(sortedMap, obj);
    }

    static <K, V> BiMap<K, V> a(BiMap<K, V> biMap, @Nullable Object obj) {
        return ((biMap instanceof SynchronizedBiMap) || (biMap instanceof ImmutableBiMap)) ? biMap : new SynchronizedBiMap(biMap, obj, null);
    }

    @GwtIncompatible
    static <E> NavigableSet<E> a(NavigableSet<E> navigableSet, @Nullable Object obj) {
        return new SynchronizedNavigableSet(navigableSet, obj);
    }

    @GwtIncompatible
    static <E> NavigableSet<E> a(NavigableSet<E> navigableSet) {
        return a(navigableSet, (Object) null);
    }

    @GwtIncompatible
    static <K, V> NavigableMap<K, V> a(NavigableMap<K, V> navigableMap) {
        return a(navigableMap, (Object) null);
    }

    @GwtIncompatible
    static <K, V> NavigableMap<K, V> a(NavigableMap<K, V> navigableMap, @Nullable Object obj) {
        return new SynchronizedNavigableMap(navigableMap, obj);
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static <K, V> Entry<K, V> b(@Nullable Entry<K, V> entry, @Nullable Object obj) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, obj);
    }

    static <E> Queue<E> a(Queue<E> queue, @Nullable Object obj) {
        return queue instanceof SynchronizedQueue ? queue : new SynchronizedQueue(queue, obj);
    }

    static <E> Deque<E> a(Deque<E> deque, @Nullable Object obj) {
        return new SynchronizedDeque(deque, obj);
    }
}
