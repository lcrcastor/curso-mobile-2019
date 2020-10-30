package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    public static final class SimpleStatsCounter implements StatsCounter {
        private final LongAddable a = LongAddables.a();
        private final LongAddable b = LongAddables.a();
        private final LongAddable c = LongAddables.a();
        private final LongAddable d = LongAddables.a();
        private final LongAddable e = LongAddables.a();
        private final LongAddable f = LongAddables.a();

        public void recordHits(int i) {
            this.a.a((long) i);
        }

        public void recordMisses(int i) {
            this.b.a((long) i);
        }

        public void recordLoadSuccess(long j) {
            this.c.a();
            this.e.a(j);
        }

        public void recordLoadException(long j) {
            this.d.a();
            this.e.a(j);
        }

        public void recordEviction() {
            this.f.a();
        }

        public CacheStats snapshot() {
            CacheStats cacheStats = new CacheStats(this.a.b(), this.b.b(), this.c.b(), this.d.b(), this.e.b(), this.f.b());
            return cacheStats;
        }

        public void incrementBy(StatsCounter statsCounter) {
            CacheStats snapshot = statsCounter.snapshot();
            this.a.a(snapshot.hitCount());
            this.b.a(snapshot.missCount());
            this.c.a(snapshot.loadSuccessCount());
            this.d.a(snapshot.loadExceptionCount());
            this.e.a(snapshot.totalLoadTime());
            this.f.a(snapshot.evictionCount());
        }
    }

    public interface StatsCounter {
        void recordEviction();

        void recordHits(int i);

        void recordLoadException(long j);

        void recordLoadSuccess(long j);

        void recordMisses(int i);

        CacheStats snapshot();
    }

    public void cleanUp() {
    }

    protected AbstractCache() {
    }

    public V get(K k, Callable<? extends V> callable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        for (Object next : iterable) {
            if (!newLinkedHashMap.containsKey(next)) {
                Object ifPresent = getIfPresent(next);
                if (ifPresent != null) {
                    newLinkedHashMap.put(next, ifPresent);
                }
            }
        }
        return ImmutableMap.copyOf((Map<? extends K, ? extends V>) newLinkedHashMap);
    }

    public void put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public long size() {
        throw new UnsupportedOperationException();
    }

    public void invalidate(Object obj) {
        throw new UnsupportedOperationException();
    }

    public void invalidateAll(Iterable<?> iterable) {
        for (Object invalidate : iterable) {
            invalidate(invalidate);
        }
    }

    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }

    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }

    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }
}
