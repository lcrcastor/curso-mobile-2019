package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

final class CacheMap extends LinkedHashMap<String, HttpCacheEntry> {
    private static final long serialVersionUID = -7750025207539768511L;
    private final int a;

    CacheMap(int i) {
        super(20, 0.75f, true);
        this.a = i;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Entry<String, HttpCacheEntry> entry) {
        return size() > this.a;
    }
}
