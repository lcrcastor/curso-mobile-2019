package com.google.common.graph;

import java.util.Map;
import javax.annotation.Nullable;

class MapRetrievalCache<K, V> extends MapIteratorCache<K, V> {
    @Nullable
    private transient CacheEntry<K, V> a;
    @Nullable
    private transient CacheEntry<K, V> b;

    static final class CacheEntry<K, V> {
        final K a;
        final V b;

        CacheEntry(K k, V v) {
            this.a = k;
            this.b = v;
        }
    }

    MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    public V b(@Nullable Object obj) {
        V e = e(obj);
        if (e != null) {
            return e;
        }
        V c = c(obj);
        if (c != null) {
            b(obj, c);
        }
        return c;
    }

    /* access modifiers changed from: protected */
    public V e(@Nullable Object obj) {
        V e = super.e(obj);
        if (e != null) {
            return e;
        }
        CacheEntry<K, V> cacheEntry = this.a;
        if (cacheEntry != null && cacheEntry.a == obj) {
            return cacheEntry.b;
        }
        CacheEntry<K, V> cacheEntry2 = this.b;
        if (cacheEntry2 == null || cacheEntry2.a != obj) {
            return null;
        }
        a(cacheEntry2);
        return cacheEntry2.b;
    }

    /* access modifiers changed from: protected */
    public void b() {
        super.b();
        this.a = null;
        this.b = null;
    }

    private void b(K k, V v) {
        a(new CacheEntry(k, v));
    }

    private void a(CacheEntry<K, V> cacheEntry) {
        this.b = this.a;
        this.a = cacheEntry;
    }
}
