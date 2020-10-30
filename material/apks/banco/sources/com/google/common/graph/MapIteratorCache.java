package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

class MapIteratorCache<K, V> {
    /* access modifiers changed from: private */
    public final Map<K, V> a;
    /* access modifiers changed from: private */
    @Nullable
    public transient Entry<K, V> b;

    MapIteratorCache(Map<K, V> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    @CanIgnoreReturnValue
    public V a(@Nullable K k, @Nullable V v) {
        b();
        return this.a.put(k, v);
    }

    @CanIgnoreReturnValue
    public V a(@Nullable Object obj) {
        b();
        return this.a.remove(obj);
    }

    public V b(@Nullable Object obj) {
        V e = e(obj);
        return e != null ? e : c(obj);
    }

    public final V c(@Nullable Object obj) {
        return this.a.get(obj);
    }

    public final boolean d(@Nullable Object obj) {
        return e(obj) != null || this.a.containsKey(obj);
    }

    public final Set<K> a() {
        return new AbstractSet<K>() {
            /* renamed from: a */
            public UnmodifiableIterator<K> iterator() {
                final Iterator it = MapIteratorCache.this.a.entrySet().iterator();
                return new UnmodifiableIterator<K>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public K next() {
                        Entry entry = (Entry) it.next();
                        MapIteratorCache.this.b = entry;
                        return entry.getKey();
                    }
                };
            }

            public int size() {
                return MapIteratorCache.this.a.size();
            }

            public boolean contains(@Nullable Object obj) {
                return MapIteratorCache.this.d(obj);
            }
        };
    }

    /* access modifiers changed from: protected */
    public V e(@Nullable Object obj) {
        Entry<K, V> entry = this.b;
        if (entry == null || entry.getKey() != obj) {
            return null;
        }
        return entry.getValue();
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.b = null;
    }
}
