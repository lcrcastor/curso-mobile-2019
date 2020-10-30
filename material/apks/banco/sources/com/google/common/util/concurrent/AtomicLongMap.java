package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@GwtCompatible
public final class AtomicLongMap<K> {
    private final ConcurrentHashMap<K, AtomicLong> a;
    private transient Map<K, Long> b;

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> concurrentHashMap) {
        this.a = (ConcurrentHashMap) Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap<>(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<K> create = create();
        create.putAll(map);
        return create;
    }

    public long get(K k) {
        AtomicLong atomicLong = (AtomicLong) this.a.get(k);
        if (atomicLong == null) {
            return 0;
        }
        return atomicLong.get();
    }

    @CanIgnoreReturnValue
    public long incrementAndGet(K k) {
        return addAndGet(k, 1);
    }

    @CanIgnoreReturnValue
    public long decrementAndGet(K k) {
        return addAndGet(k, -1);
    }

    @CanIgnoreReturnValue
    public long addAndGet(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = (AtomicLong) this.a.get(k);
            if (atomicLong == null) {
                atomicLong = (AtomicLong) this.a.putIfAbsent(k, new AtomicLong(j));
                if (atomicLong == null) {
                    return j;
                }
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 != 0) {
                    long j3 = j2 + j;
                    if (atomicLong.compareAndSet(j2, j3)) {
                        return j3;
                    }
                }
            }
        } while (!this.a.replace(k, atomicLong, new AtomicLong(j)));
        return j;
    }

    @CanIgnoreReturnValue
    public long getAndIncrement(K k) {
        return getAndAdd(k, 1);
    }

    @CanIgnoreReturnValue
    public long getAndDecrement(K k) {
        return getAndAdd(k, -1);
    }

    @CanIgnoreReturnValue
    public long getAndAdd(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = (AtomicLong) this.a.get(k);
            if (atomicLong == null) {
                atomicLong = (AtomicLong) this.a.putIfAbsent(k, new AtomicLong(j));
                if (atomicLong == null) {
                    return 0;
                }
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 != 0) {
                    if (atomicLong.compareAndSet(j2, j2 + j)) {
                        return j2;
                    }
                }
            }
        } while (!this.a.replace(k, atomicLong, new AtomicLong(j)));
        return 0;
    }

    @CanIgnoreReturnValue
    public long put(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = (AtomicLong) this.a.get(k);
            if (atomicLong == null) {
                atomicLong = (AtomicLong) this.a.putIfAbsent(k, new AtomicLong(j));
                if (atomicLong == null) {
                    return 0;
                }
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 != 0) {
                    if (atomicLong.compareAndSet(j2, j)) {
                        return j2;
                    }
                }
            }
        } while (!this.a.replace(k, atomicLong, new AtomicLong(j)));
        return 0;
    }

    public void putAll(Map<? extends K, ? extends Long> map) {
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), ((Long) entry.getValue()).longValue());
        }
    }

    @CanIgnoreReturnValue
    public long remove(K k) {
        long j;
        AtomicLong atomicLong = (AtomicLong) this.a.get(k);
        if (atomicLong == null) {
            return 0;
        }
        do {
            j = atomicLong.get();
            if (j == 0) {
                break;
            }
        } while (!atomicLong.compareAndSet(j, 0));
        this.a.remove(k, atomicLong);
        return j;
    }

    @CanIgnoreReturnValue
    @Beta
    public boolean removeIfZero(K k) {
        return a(k, 0);
    }

    public void removeAllZeros() {
        Iterator it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            AtomicLong atomicLong = (AtomicLong) ((Entry) it.next()).getValue();
            if (atomicLong != null && atomicLong.get() == 0) {
                it.remove();
            }
        }
    }

    public long sum() {
        long j = 0;
        for (AtomicLong atomicLong : this.a.values()) {
            j += atomicLong.get();
        }
        return j;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.b;
        if (map != null) {
            return map;
        }
        Map<K, Long> a2 = a();
        this.b = a2;
        return a2;
    }

    private Map<K, Long> a() {
        return Collections.unmodifiableMap(Maps.transformValues((Map<K, V1>) this.a, (Function<? super V1, V2>) new Function<AtomicLong, Long>() {
            /* renamed from: a */
            public Long apply(AtomicLong atomicLong) {
                return Long.valueOf(atomicLong.get());
            }
        }));
    }

    public boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    public int size() {
        return this.a.size();
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public void clear() {
        this.a.clear();
    }

    public String toString() {
        return this.a.toString();
    }

    /* access modifiers changed from: 0000 */
    public boolean a(K k, long j) {
        AtomicLong atomicLong = (AtomicLong) this.a.get(k);
        if (atomicLong == null) {
            return false;
        }
        long j2 = atomicLong.get();
        if (j2 != j) {
            return false;
        }
        if (j2 != 0 && !atomicLong.compareAndSet(j2, 0)) {
            return false;
        }
        this.a.remove(k, atomicLong);
        return true;
    }
}
