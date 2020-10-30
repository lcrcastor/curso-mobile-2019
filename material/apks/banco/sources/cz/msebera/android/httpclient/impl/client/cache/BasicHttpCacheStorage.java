package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback;

@ThreadSafe
public class BasicHttpCacheStorage implements HttpCacheStorage {
    private final CacheMap a;

    public BasicHttpCacheStorage(CacheConfig cacheConfig) {
        this.a = new CacheMap(cacheConfig.getMaxCacheEntries());
    }

    public synchronized void putEntry(String str, HttpCacheEntry httpCacheEntry) {
        this.a.put(str, httpCacheEntry);
    }

    public synchronized HttpCacheEntry getEntry(String str) {
        return (HttpCacheEntry) this.a.get(str);
    }

    public synchronized void removeEntry(String str) {
        this.a.remove(str);
    }

    public synchronized void updateEntry(String str, HttpCacheUpdateCallback httpCacheUpdateCallback) {
        this.a.put(str, httpCacheUpdateCallback.update((HttpCacheEntry) this.a.get(str)));
    }
}
