package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback;
import cz.msebera.android.httpclient.util.Args;
import java.io.Closeable;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
public class ManagedHttpCacheStorage implements HttpCacheStorage, Closeable {
    private final CacheMap a;
    private final ReferenceQueue<HttpCacheEntry> b = new ReferenceQueue<>();
    private final Set<ResourceReference> c = new HashSet();
    private final AtomicBoolean d = new AtomicBoolean(true);

    public ManagedHttpCacheStorage(CacheConfig cacheConfig) {
        this.a = new CacheMap(cacheConfig.getMaxCacheEntries());
    }

    private void a() {
        if (!this.d.get()) {
            throw new IllegalStateException("Cache has been shut down");
        }
    }

    private void a(HttpCacheEntry httpCacheEntry) {
        if (httpCacheEntry.getResource() != null) {
            this.c.add(new ResourceReference(httpCacheEntry, this.b));
        }
    }

    public void putEntry(String str, HttpCacheEntry httpCacheEntry) {
        Args.notNull(str, "URL");
        Args.notNull(httpCacheEntry, "Cache entry");
        a();
        synchronized (this) {
            this.a.put(str, httpCacheEntry);
            a(httpCacheEntry);
        }
    }

    public HttpCacheEntry getEntry(String str) {
        HttpCacheEntry httpCacheEntry;
        Args.notNull(str, "URL");
        a();
        synchronized (this) {
            httpCacheEntry = (HttpCacheEntry) this.a.get(str);
        }
        return httpCacheEntry;
    }

    public void removeEntry(String str) {
        Args.notNull(str, "URL");
        a();
        synchronized (this) {
            this.a.remove(str);
        }
    }

    public void updateEntry(String str, HttpCacheUpdateCallback httpCacheUpdateCallback) {
        Args.notNull(str, "URL");
        Args.notNull(httpCacheUpdateCallback, "Callback");
        a();
        synchronized (this) {
            HttpCacheEntry httpCacheEntry = (HttpCacheEntry) this.a.get(str);
            HttpCacheEntry update = httpCacheUpdateCallback.update(httpCacheEntry);
            this.a.put(str, update);
            if (httpCacheEntry != update) {
                a(update);
            }
        }
    }

    public void cleanResources() {
        if (this.d.get()) {
            while (true) {
                ResourceReference resourceReference = (ResourceReference) this.b.poll();
                if (resourceReference != null) {
                    synchronized (this) {
                        this.c.remove(resourceReference);
                    }
                    resourceReference.a().dispose();
                } else {
                    return;
                }
            }
            while (true) {
            }
        }
    }

    public void shutdown() {
        if (this.d.compareAndSet(true, false)) {
            synchronized (this) {
                this.a.clear();
                for (ResourceReference a2 : this.c) {
                    a2.a().dispose();
                }
                this.c.clear();
                while (this.b.poll() != null) {
                }
            }
        }
    }

    public void close() {
        if (this.d.compareAndSet(true, false)) {
            synchronized (this) {
                while (true) {
                    ResourceReference resourceReference = (ResourceReference) this.b.poll();
                    if (resourceReference != null) {
                        this.c.remove(resourceReference);
                        resourceReference.a().dispose();
                    }
                }
            }
        }
    }
}
