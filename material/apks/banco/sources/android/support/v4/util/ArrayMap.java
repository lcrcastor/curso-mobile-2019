package android.support.v4.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    MapCollections<K, V> a;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    private MapCollections<K, V> b() {
        if (this.a == null) {
            this.a = new MapCollections<K, V>() {
                /* access modifiers changed from: protected */
                public int a() {
                    return ArrayMap.this.h;
                }

                /* access modifiers changed from: protected */
                public Object a(int i, int i2) {
                    return ArrayMap.this.g[(i << 1) + i2];
                }

                /* access modifiers changed from: protected */
                public int a(Object obj) {
                    return ArrayMap.this.indexOfKey(obj);
                }

                /* access modifiers changed from: protected */
                public int b(Object obj) {
                    return ArrayMap.this.a(obj);
                }

                /* access modifiers changed from: protected */
                public Map<K, V> b() {
                    return ArrayMap.this;
                }

                /* access modifiers changed from: protected */
                public void a(K k, V v) {
                    ArrayMap.this.put(k, v);
                }

                /* access modifiers changed from: protected */
                public V a(int i, V v) {
                    return ArrayMap.this.setValueAt(i, v);
                }

                /* access modifiers changed from: protected */
                public void a(int i) {
                    ArrayMap.this.removeAt(i);
                }

                /* access modifiers changed from: protected */
                public void c() {
                    ArrayMap.this.clear();
                }
            };
        }
        return this.a;
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.a((Map<K, V>) this, collection);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.h + map.size());
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.b(this, collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.c(this, collection);
    }

    public Set<Entry<K, V>> entrySet() {
        return b().d();
    }

    public Set<K> keySet() {
        return b().e();
    }

    public Collection<V> values() {
        return b().f();
    }
}
