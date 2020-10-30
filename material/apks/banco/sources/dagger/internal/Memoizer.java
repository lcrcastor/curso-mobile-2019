package dagger.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

abstract class Memoizer<K, V> {
    private final Map<K, V> a = new LinkedHashMap();
    private final Lock b;
    private final Lock c;

    /* access modifiers changed from: protected */
    public abstract V a(K k);

    public Memoizer() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.b = reentrantReadWriteLock.readLock();
        this.c = reentrantReadWriteLock.writeLock();
    }

    public final V b(K k) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        this.b.lock();
        try {
            V v = this.a.get(k);
            if (v != null) {
                return v;
            }
            this.b.unlock();
            V a2 = a(k);
            if (a2 == null) {
                throw new NullPointerException("create returned null");
            }
            this.c.lock();
            try {
                this.a.put(k, a2);
                return a2;
            } finally {
                this.c.unlock();
            }
        } finally {
            this.b.unlock();
        }
    }

    public final String toString() {
        this.b.lock();
        try {
            return this.a.toString();
        } finally {
            this.b.unlock();
        }
    }
}
