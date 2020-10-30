package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    private transient Collection<Entry<K, V>> a;
    private transient Set<K> b;
    private transient Multiset<K> c;
    private transient Collection<V> d;
    private transient Map<K, Collection<V>> e;

    class Entries extends Entries<K, V> {
        private Entries() {
        }

        /* access modifiers changed from: 0000 */
        public Multimap<K, V> a() {
            return AbstractMultimap.this;
        }

        public Iterator<Entry<K, V>> iterator() {
            return AbstractMultimap.this.h();
        }
    }

    class EntrySet extends Entries implements Set<Entry<K, V>> {
        private EntrySet() {
            super();
        }

        public int hashCode() {
            return Sets.a(this);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }
    }

    class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return AbstractMultimap.this.g();
        }

        public int size() {
            return AbstractMultimap.this.size();
        }

        public boolean contains(@Nullable Object obj) {
            return AbstractMultimap.this.containsValue(obj);
        }

        public void clear() {
            AbstractMultimap.this.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Entry<K, V>> h();

    /* access modifiers changed from: 0000 */
    public abstract Map<K, Collection<V>> i();

    AbstractMultimap() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsValue(@Nullable Object obj) {
        for (Collection contains : asMap().values()) {
            if (contains.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEntry(@Nullable Object obj, @Nullable Object obj2) {
        Collection collection = (Collection) asMap().get(obj);
        return collection != null && collection.contains(obj2);
    }

    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object obj, @Nullable Object obj2) {
        Collection collection = (Collection) asMap().get(obj);
        return collection != null && collection.remove(obj2);
    }

    @CanIgnoreReturnValue
    public boolean put(@Nullable K k, @Nullable V v) {
        return get(k).add(v);
    }

    @CanIgnoreReturnValue
    public boolean putAll(@Nullable K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        boolean z = false;
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (!collection.isEmpty() && get(k).addAll(collection)) {
                z = true;
            }
            return z;
        }
        Iterator it = iterable.iterator();
        if (it.hasNext() && Iterators.addAll(get(k), it)) {
            z = true;
        }
        return z;
    }

    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean z = false;
        for (Entry entry : multimap.entries()) {
            z |= put(entry.getKey(), entry.getValue());
        }
        return z;
    }

    @CanIgnoreReturnValue
    public Collection<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        Collection<V> removeAll = removeAll(k);
        putAll(k, iterable);
        return removeAll;
    }

    public Collection<Entry<K, V>> entries() {
        Collection<Entry<K, V>> collection = this.a;
        if (collection != null) {
            return collection;
        }
        Collection<Entry<K, V>> j = j();
        this.a = j;
        return j;
    }

    /* access modifiers changed from: 0000 */
    public Collection<Entry<K, V>> j() {
        if (this instanceof SetMultimap) {
            return new EntrySet();
        }
        return new Entries();
    }

    public Set<K> keySet() {
        Set<K> set = this.b;
        if (set != null) {
            return set;
        }
        Set<K> f = f();
        this.b = f;
        return f;
    }

    /* access modifiers changed from: 0000 */
    public Set<K> f() {
        return new KeySet(asMap());
    }

    public Multiset<K> keys() {
        Multiset<K> multiset = this.c;
        if (multiset != null) {
            return multiset;
        }
        Multiset<K> k = k();
        this.c = k;
        return k;
    }

    /* access modifiers changed from: 0000 */
    public Multiset<K> k() {
        return new Keys(this);
    }

    public Collection<V> values() {
        Collection<V> collection = this.d;
        if (collection != null) {
            return collection;
        }
        Collection<V> l = l();
        this.d = l;
        return l;
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> l() {
        return new Values();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<V> g() {
        return Maps.b(entries().iterator());
    }

    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> map = this.e;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> i = i();
        this.e = i;
        return i;
    }

    public boolean equals(@Nullable Object obj) {
        return Multimaps.a((Multimap<?, ?>) this, obj);
    }

    public int hashCode() {
        return asMap().hashCode();
    }

    public String toString() {
        return asMap().toString();
    }
}
