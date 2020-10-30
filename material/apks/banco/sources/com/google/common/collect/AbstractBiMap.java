package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    @RetainedWith
    transient AbstractBiMap<V, K> a;
    /* access modifiers changed from: private */
    public transient Map<K, V> b;
    private transient Set<K> c;
    private transient Set<V> d;
    private transient Set<Entry<K, V>> e;

    class BiMapEntry extends ForwardingMapEntry<K, V> {
        private final Entry<K, V> b;

        BiMapEntry(Entry<K, V> entry) {
            this.b = entry;
        }

        /* access modifiers changed from: protected */
        public Entry<K, V> delegate() {
            return this.b;
        }

        public V setValue(V v) {
            Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v, getValue())) {
                return v;
            }
            Preconditions.checkArgument(!AbstractBiMap.this.containsValue(v), "value already present: %s", (Object) v);
            V value = this.b.setValue(v);
            Preconditions.checkState(Objects.equal(v, AbstractBiMap.this.get(getKey())), "entry no longer in map");
            AbstractBiMap.this.a(getKey(), true, value, v);
            return value;
        }
    }

    class EntrySet extends ForwardingSet<Entry<K, V>> {
        final Set<Entry<K, V>> a;

        private EntrySet() {
            this.a = AbstractBiMap.this.b.entrySet();
        }

        /* access modifiers changed from: protected */
        public Set<Entry<K, V>> delegate() {
            return this.a;
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        public boolean remove(Object obj) {
            if (!this.a.contains(obj)) {
                return false;
            }
            Entry entry = (Entry) obj;
            AbstractBiMap.this.a.b.remove(entry.getValue());
            this.a.remove(entry);
            return true;
        }

        public Iterator<Entry<K, V>> iterator() {
            return AbstractBiMap.this.a();
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

        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }
    }

    static class Inverse<K, V> extends AbstractBiMap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;

        /* access modifiers changed from: protected */
        public /* bridge */ /* synthetic */ Object delegate() {
            return AbstractBiMap.super.delegate();
        }

        public /* bridge */ /* synthetic */ Collection values() {
            return AbstractBiMap.super.values();
        }

        Inverse(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
            super(map, abstractBiMap);
        }

        /* access modifiers changed from: 0000 */
        public K a(K k) {
            return this.a.b(k);
        }

        /* access modifiers changed from: 0000 */
        public V b(V v) {
            return this.a.a(v);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(inverse());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            a((AbstractBiMap) objectInputStream.readObject());
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public Object readResolve() {
            return inverse().inverse();
        }
    }

    class KeySet extends ForwardingSet<K> {
        private KeySet() {
        }

        /* access modifiers changed from: protected */
        public Set<K> delegate() {
            return AbstractBiMap.this.b.keySet();
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            AbstractBiMap.this.c(obj);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        public Iterator<K> iterator() {
            return Maps.a(AbstractBiMap.this.entrySet().iterator());
        }
    }

    class ValueSet extends ForwardingSet<V> {
        final Set<V> a;

        private ValueSet() {
            this.a = AbstractBiMap.this.a.keySet();
        }

        /* access modifiers changed from: protected */
        public Set<V> delegate() {
            return this.a;
        }

        public Iterator<V> iterator() {
            return Maps.b(AbstractBiMap.this.entrySet().iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }

        public String toString() {
            return standardToString();
        }
    }

    /* access modifiers changed from: 0000 */
    @CanIgnoreReturnValue
    public K a(@Nullable K k) {
        return k;
    }

    /* access modifiers changed from: 0000 */
    @CanIgnoreReturnValue
    public V b(@Nullable V v) {
        return v;
    }

    AbstractBiMap(Map<K, V> map, Map<V, K> map2) {
        a(map, map2);
    }

    private AbstractBiMap(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
        this.b = map;
        this.a = abstractBiMap;
    }

    /* access modifiers changed from: protected */
    public Map<K, V> delegate() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(Map<K, V> map, Map<V, K> map2) {
        boolean z = false;
        Preconditions.checkState(this.b == null);
        Preconditions.checkState(this.a == null);
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(map2.isEmpty());
        if (map != map2) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.b = map;
        this.a = a(map2);
    }

    /* access modifiers changed from: 0000 */
    public AbstractBiMap<V, K> a(Map<V, K> map) {
        return new Inverse(map, this);
    }

    /* access modifiers changed from: 0000 */
    public void a(AbstractBiMap<V, K> abstractBiMap) {
        this.a = abstractBiMap;
    }

    public boolean containsValue(@Nullable Object obj) {
        return this.a.containsKey(obj);
    }

    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        return a(k, v, false);
    }

    @CanIgnoreReturnValue
    public V forcePut(@Nullable K k, @Nullable V v) {
        return a(k, v, true);
    }

    private V a(@Nullable K k, @Nullable V v, boolean z) {
        a(k);
        b(v);
        boolean containsKey = containsKey(k);
        if (containsKey && Objects.equal(v, get(k))) {
            return v;
        }
        if (z) {
            inverse().remove(v);
        } else {
            Preconditions.checkArgument(!containsValue(v), "value already present: %s", (Object) v);
        }
        V put = this.b.put(k, v);
        a(k, containsKey, put, v);
        return put;
    }

    /* access modifiers changed from: private */
    public void a(K k, boolean z, V v, V v2) {
        if (z) {
            d(v);
        }
        this.a.b.put(v2, k);
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj) {
        if (containsKey(obj)) {
            return c(obj);
        }
        return null;
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V c(Object obj) {
        V remove = this.b.remove(obj);
        d(remove);
        return remove;
    }

    /* access modifiers changed from: private */
    public void d(V v) {
        this.a.b.remove(v);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.b.clear();
        this.a.b.clear();
    }

    public BiMap<V, K> inverse() {
        return this.a;
    }

    public Set<K> keySet() {
        Set<K> set = this.c;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.c = keySet;
        return keySet;
    }

    public Set<V> values() {
        Set<V> set = this.d;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.d = valueSet;
        return valueSet;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.e;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.e = entrySet;
        return entrySet;
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> a() {
        final Iterator it = this.b.entrySet().iterator();
        return new Iterator<Entry<K, V>>() {
            Entry<K, V> a;

            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public Entry<K, V> next() {
                this.a = (Entry) it.next();
                return new BiMapEntry(this.a);
            }

            public void remove() {
                CollectPreconditions.a(this.a != null);
                Object value = this.a.getValue();
                it.remove();
                AbstractBiMap.this.d(value);
            }
        };
    }
}
