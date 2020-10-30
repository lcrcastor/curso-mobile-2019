package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
final class WellBehavedMap<K, V> extends ForwardingMap<K, V> {
    private final Map<K, V> a;
    private Set<Entry<K, V>> b;

    final class EntrySet extends EntrySet<K, V> {
        private EntrySet() {
        }

        /* access modifiers changed from: 0000 */
        public Map<K, V> a() {
            return WellBehavedMap.this;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new TransformedIterator<K, Entry<K, V>>(WellBehavedMap.this.keySet().iterator()) {
                /* access modifiers changed from: 0000 */
                /* renamed from: b */
                public Entry<K, V> a(final K k) {
                    return new AbstractMapEntry<K, V>() {
                        public K getKey() {
                            return k;
                        }

                        public V getValue() {
                            return WellBehavedMap.this.get(k);
                        }

                        public V setValue(V v) {
                            return WellBehavedMap.this.put(k, v);
                        }
                    };
                }
            };
        }
    }

    private WellBehavedMap(Map<K, V> map) {
        this.a = map;
    }

    static <K, V> WellBehavedMap<K, V> a(Map<K, V> map) {
        return new WellBehavedMap<>(map);
    }

    /* access modifiers changed from: protected */
    public Map<K, V> delegate() {
        return this.a;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.b;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.b = entrySet;
        return entrySet;
    }
}
