package android.support.v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

abstract class MapCollections<K, V> {
    EntrySet b;
    KeySet c;
    ValuesCollection d;

    final class ArrayIterator<T> implements Iterator<T> {
        final int a;
        int b;
        int c;
        boolean d = false;

        ArrayIterator(int i) {
            this.a = i;
            this.b = MapCollections.this.a();
        }

        public boolean hasNext() {
            return this.c < this.b;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T a2 = MapCollections.this.a(this.c, this.a);
            this.c++;
            this.d = true;
            return a2;
        }

        public void remove() {
            if (!this.d) {
                throw new IllegalStateException();
            }
            this.c--;
            this.b--;
            this.d = false;
            MapCollections.this.a(this.c);
        }
    }

    final class EntrySet implements Set<Entry<K, V>> {
        EntrySet() {
        }

        /* renamed from: a */
        public boolean add(Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends Entry<K, V>> collection) {
            int a2 = MapCollections.this.a();
            for (Entry entry : collection) {
                MapCollections.this.a(entry.getKey(), entry.getValue());
            }
            return a2 != MapCollections.this.a();
        }

        public void clear() {
            MapCollections.this.c();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            int a2 = MapCollections.this.a(entry.getKey());
            if (a2 < 0) {
                return false;
            }
            return ContainerHelpers.a(MapCollections.this.a(a2, 1), entry.getValue());
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new MapIterator();
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return MapCollections.this.a();
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object obj) {
            return MapCollections.a((Set<T>) this, obj);
        }

        public int hashCode() {
            int i;
            int i2;
            int i3 = 0;
            for (int a2 = MapCollections.this.a() - 1; a2 >= 0; a2--) {
                Object a3 = MapCollections.this.a(a2, 0);
                Object a4 = MapCollections.this.a(a2, 1);
                if (a3 == null) {
                    i = 0;
                } else {
                    i = a3.hashCode();
                }
                if (a4 == null) {
                    i2 = 0;
                } else {
                    i2 = a4.hashCode();
                }
                i3 += i ^ i2;
            }
            return i3;
        }
    }

    final class KeySet implements Set<K> {
        KeySet() {
        }

        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            MapCollections.this.c();
        }

        public boolean contains(Object obj) {
            return MapCollections.this.a(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            return MapCollections.a(MapCollections.this.b(), collection);
        }

        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public Iterator<K> iterator() {
            return new ArrayIterator(0);
        }

        public boolean remove(Object obj) {
            int a2 = MapCollections.this.a(obj);
            if (a2 < 0) {
                return false;
            }
            MapCollections.this.a(a2);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return MapCollections.b(MapCollections.this.b(), collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return MapCollections.c(MapCollections.this.b(), collection);
        }

        public int size() {
            return MapCollections.this.a();
        }

        public Object[] toArray() {
            return MapCollections.this.b(0);
        }

        public <T> T[] toArray(T[] tArr) {
            return MapCollections.this.a(tArr, 0);
        }

        public boolean equals(Object obj) {
            return MapCollections.a((Set<T>) this, obj);
        }

        public int hashCode() {
            int i;
            int i2 = 0;
            for (int a2 = MapCollections.this.a() - 1; a2 >= 0; a2--) {
                Object a3 = MapCollections.this.a(a2, 0);
                if (a3 == null) {
                    i = 0;
                } else {
                    i = a3.hashCode();
                }
                i2 += i;
            }
            return i2;
        }
    }

    final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
        int a;
        int b;
        boolean c = false;

        MapIterator() {
            this.a = MapCollections.this.a() - 1;
            this.b = -1;
        }

        public boolean hasNext() {
            return this.b < this.a;
        }

        /* renamed from: a */
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.b++;
            this.c = true;
            return this;
        }

        public void remove() {
            if (!this.c) {
                throw new IllegalStateException();
            }
            MapCollections.this.a(this.b);
            this.b--;
            this.a--;
            this.c = false;
        }

        public K getKey() {
            if (this.c) {
                return MapCollections.this.a(this.b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() {
            if (this.c) {
                return MapCollections.this.a(this.b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V setValue(V v) {
            if (this.c) {
                return MapCollections.this.a(this.b, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public boolean equals(Object obj) {
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (ContainerHelpers.a(entry.getKey(), MapCollections.this.a(this.b, 0)) && ContainerHelpers.a(entry.getValue(), MapCollections.this.a(this.b, 1))) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i;
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            int i2 = 0;
            Object a2 = MapCollections.this.a(this.b, 0);
            Object a3 = MapCollections.this.a(this.b, 1);
            if (a2 == null) {
                i = 0;
            } else {
                i = a2.hashCode();
            }
            if (a3 != null) {
                i2 = a3.hashCode();
            }
            return i ^ i2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append("=");
            sb.append(getValue());
            return sb.toString();
        }
    }

    final class ValuesCollection implements Collection<V> {
        ValuesCollection() {
        }

        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            MapCollections.this.c();
        }

        public boolean contains(Object obj) {
            return MapCollections.this.b(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        public Iterator<V> iterator() {
            return new ArrayIterator(1);
        }

        public boolean remove(Object obj) {
            int b = MapCollections.this.b(obj);
            if (b < 0) {
                return false;
            }
            MapCollections.this.a(b);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            int a2 = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < a2) {
                if (collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    a2--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            int a2 = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < a2) {
                if (!collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    a2--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public int size() {
            return MapCollections.this.a();
        }

        public Object[] toArray() {
            return MapCollections.this.b(1);
        }

        public <T> T[] toArray(T[] tArr) {
            return MapCollections.this.a(tArr, 1);
        }
    }

    /* access modifiers changed from: protected */
    public abstract int a();

    /* access modifiers changed from: protected */
    public abstract int a(Object obj);

    /* access modifiers changed from: protected */
    public abstract Object a(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract V a(int i, V v);

    /* access modifiers changed from: protected */
    public abstract void a(int i);

    /* access modifiers changed from: protected */
    public abstract void a(K k, V v);

    /* access modifiers changed from: protected */
    public abstract int b(Object obj);

    /* access modifiers changed from: protected */
    public abstract Map<K, V> b();

    /* access modifiers changed from: protected */
    public abstract void c();

    MapCollections() {
    }

    public static <K, V> boolean a(Map<K, V> map, Collection<?> collection) {
        for (Object containsKey : collection) {
            if (!map.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean b(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        for (Object remove : collection) {
            map.remove(remove);
        }
        return size != map.size();
    }

    public static <K, V> boolean c(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    public Object[] b(int i) {
        int a = a();
        Object[] objArr = new Object[a];
        for (int i2 = 0; i2 < a; i2++) {
            objArr[i2] = a(i2, i);
        }
        return objArr;
    }

    public <T> T[] a(T[] tArr, int i) {
        int a = a();
        if (tArr.length < a) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), a);
        }
        for (int i2 = 0; i2 < a; i2++) {
            tArr[i2] = a(i2, i);
        }
        if (tArr.length > a) {
            tArr[a] = null;
        }
        return tArr;
    }

    public static <T> boolean a(Set<T> set, Object obj) {
        boolean z = true;
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() != set2.size() || !set.containsAll(set2)) {
                z = false;
            }
            return z;
        } catch (NullPointerException unused) {
            return false;
        } catch (ClassCastException unused2) {
            return false;
        }
    }

    public Set<Entry<K, V>> d() {
        if (this.b == null) {
            this.b = new EntrySet<>();
        }
        return this.b;
    }

    public Set<K> e() {
        if (this.c == null) {
            this.c = new KeySet<>();
        }
        return this.c;
    }

    public Collection<V> f() {
        if (this.d == null) {
            this.d = new ValuesCollection<>();
        }
        return this.d;
    }
}
