package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    final Multimap<K, V> a;
    final Predicate<? super Entry<K, V>> b;

    class AsMap extends ViewCachingAbstractMap<K, Collection<V>> {
        AsMap() {
        }

        public boolean containsKey(@Nullable Object obj) {
            return get(obj) != null;
        }

        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        /* renamed from: a */
        public Collection<V> get(@Nullable Object obj) {
            Collection collection = (Collection) FilteredEntryMultimap.this.a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> a2 = FilteredEntryMultimap.a(collection, (Predicate<? super E>) new ValuePredicate<Object>(obj));
            if (a2.isEmpty()) {
                a2 = null;
            }
            return a2;
        }

        /* renamed from: b */
        public Collection<V> remove(@Nullable Object obj) {
            Collection collection = (Collection) FilteredEntryMultimap.this.a.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            ArrayList newArrayList = Lists.newArrayList();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (FilteredEntryMultimap.this.a(obj, next)) {
                    it.remove();
                    newArrayList.add(next);
                }
            }
            if (newArrayList.isEmpty()) {
                return null;
            }
            if (FilteredEntryMultimap.this.a instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(newArrayList));
            }
            return Collections.unmodifiableList(newArrayList);
        }

        /* access modifiers changed from: 0000 */
        public Set<K> h() {
            return new KeySet<K, Collection<V>>() {
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Maps.a(Predicates.in(collection)));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Maps.a(Predicates.not(Predicates.in(collection))));
                }

                public boolean remove(@Nullable Object obj) {
                    return AsMap.this.remove(obj) != null;
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Set<Entry<K, Collection<V>>> a() {
            return new EntrySet<K, Collection<V>>() {
                /* access modifiers changed from: 0000 */
                public Map<K, Collection<V>> a() {
                    return AsMap.this;
                }

                public Iterator<Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Entry<K, Collection<V>>>() {
                        final Iterator<Entry<K, Collection<V>>> a = FilteredEntryMultimap.this.a.asMap().entrySet().iterator();

                        /* access modifiers changed from: protected */
                        /* renamed from: a */
                        public Entry<K, Collection<V>> computeNext() {
                            while (this.a.hasNext()) {
                                Entry entry = (Entry) this.a.next();
                                Object key = entry.getKey();
                                Collection a2 = FilteredEntryMultimap.a((Collection) entry.getValue(), (Predicate<? super E>) new ValuePredicate<Object>(key));
                                if (!a2.isEmpty()) {
                                    return Maps.immutableEntry(key, a2);
                                }
                            }
                            return (Entry) endOfData();
                        }
                    };
                }

                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Predicates.in(collection));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Predicates.not(Predicates.in(collection)));
                }

                public int size() {
                    return Iterators.size(iterator());
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Collection<Collection<V>> b() {
            return new Values<K, Collection<V>>() {
                public boolean remove(@Nullable Object obj) {
                    if (obj instanceof Collection) {
                        Collection collection = (Collection) obj;
                        Iterator it = FilteredEntryMultimap.this.a.asMap().entrySet().iterator();
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            Collection a2 = FilteredEntryMultimap.a((Collection) entry.getValue(), (Predicate<? super E>) new ValuePredicate<Object>(entry.getKey()));
                            if (!a2.isEmpty() && collection.equals(a2)) {
                                if (a2.size() == ((Collection) entry.getValue()).size()) {
                                    it.remove();
                                } else {
                                    a2.clear();
                                }
                                return true;
                            }
                        }
                    }
                    return false;
                }

                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Maps.b(Predicates.in(collection)));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.a(Maps.b(Predicates.not(Predicates.in(collection))));
                }
            };
        }
    }

    class Keys extends Keys<K, V> {
        Keys() {
            super(FilteredEntryMultimap.this);
        }

        public int remove(@Nullable Object obj, int i) {
            CollectPreconditions.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            Collection collection = (Collection) FilteredEntryMultimap.this.a.asMap().get(obj);
            int i2 = 0;
            if (collection == null) {
                return 0;
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (FilteredEntryMultimap.this.a(obj, it.next())) {
                    i2++;
                    if (i2 <= i) {
                        it.remove();
                    }
                }
            }
            return i2;
        }

        public Set<Multiset.Entry<K>> entrySet() {
            return new EntrySet<K>() {
                /* access modifiers changed from: 0000 */
                public Multiset<K> a() {
                    return Keys.this;
                }

                public Iterator<Multiset.Entry<K>> iterator() {
                    return Keys.this.a();
                }

                public int size() {
                    return FilteredEntryMultimap.this.keySet().size();
                }

                private boolean a(final Predicate<? super Multiset.Entry<K>> predicate) {
                    return FilteredEntryMultimap.this.a(new Predicate<Entry<K, Collection<V>>>() {
                        /* renamed from: a */
                        public boolean apply(Entry<K, Collection<V>> entry) {
                            return predicate.apply(Multisets.immutableEntry(entry.getKey(), ((Collection) entry.getValue()).size()));
                        }
                    });
                }

                public boolean removeAll(Collection<?> collection) {
                    return a(Predicates.in(collection));
                }

                public boolean retainAll(Collection<?> collection) {
                    return a(Predicates.not(Predicates.in(collection)));
                }
            };
        }
    }

    final class ValuePredicate implements Predicate<V> {
        private final K b;

        ValuePredicate(K k) {
            this.b = k;
        }

        public boolean apply(@Nullable V v) {
            return FilteredEntryMultimap.this.a(this.b, v);
        }
    }

    FilteredEntryMultimap(Multimap<K, V> multimap, Predicate<? super Entry<K, V>> predicate) {
        this.a = (Multimap) Preconditions.checkNotNull(multimap);
        this.b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public Multimap<K, V> a() {
        return this.a;
    }

    public Predicate<? super Entry<K, V>> b() {
        return this.b;
    }

    public int size() {
        return entries().size();
    }

    /* access modifiers changed from: private */
    public boolean a(K k, V v) {
        return this.b.apply(Maps.immutableEntry(k, v));
    }

    static <E> Collection<E> a(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set) collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }

    public boolean containsKey(@Nullable Object obj) {
        return asMap().get(obj) != null;
    }

    public Collection<V> removeAll(@Nullable Object obj) {
        return (Collection) MoreObjects.firstNonNull(asMap().remove(obj), c());
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> c() {
        return this.a instanceof SetMultimap ? Collections.emptySet() : Collections.emptyList();
    }

    public void clear() {
        entries().clear();
    }

    public Collection<V> get(K k) {
        return a(this.a.get(k), (Predicate<? super E>) new ValuePredicate<Object>(k));
    }

    /* access modifiers changed from: 0000 */
    public Collection<Entry<K, V>> j() {
        return a(this.a.entries(), this.b);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> l() {
        return new FilteredMultimapValues(this);
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> h() {
        throw new AssertionError("should never be called");
    }

    /* access modifiers changed from: 0000 */
    public Map<K, Collection<V>> i() {
        return new AsMap();
    }

    public Set<K> keySet() {
        return asMap().keySet();
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Predicate<? super Entry<K, Collection<V>>> predicate) {
        Iterator it = this.a.asMap().entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Object key = entry.getKey();
            Collection a2 = a((Collection) entry.getValue(), (Predicate<? super E>) new ValuePredicate<Object>(key));
            if (!a2.isEmpty() && predicate.apply(Maps.immutableEntry(key, a2))) {
                if (a2.size() == ((Collection) entry.getValue()).size()) {
                    it.remove();
                } else {
                    a2.clear();
                }
                z = true;
            }
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public Multiset<K> k() {
        return new Keys();
    }
}
