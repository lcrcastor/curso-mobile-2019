package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.CacheLoader.UnsupportedLoadingOperationException;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.j2objc.annotations.Weak;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final Logger a = Logger.getLogger(LocalCache.class.getName());
    static final ValueReference<Object, Object> u = new ValueReference<Object, Object>() {
        public int a() {
            return 0;
        }

        public ValueReference<Object, Object> a(ReferenceQueue<Object> referenceQueue, @Nullable Object obj, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        public void a(Object obj) {
        }

        public ReferenceEntry<Object, Object> b() {
            return null;
        }

        public boolean c() {
            return false;
        }

        public boolean d() {
            return false;
        }

        public Object e() {
            return null;
        }

        public Object get() {
            return null;
        }
    };
    static final Queue<? extends Object> v = new AbstractQueue<Object>() {
        public boolean offer(Object obj) {
            return true;
        }

        public Object peek() {
            return null;
        }

        public Object poll() {
            return null;
        }

        public int size() {
            return 0;
        }

        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }
    };
    final int b;
    final int c;
    final Segment<K, V>[] d;
    final int e;
    final Equivalence<Object> f;
    final Equivalence<Object> g;
    final Strength h;
    final Strength i;
    final long j;
    final Weigher<K, V> k;
    final long l;
    final long m;
    final long n;
    final Queue<RemovalNotification<K, V>> o;
    final RemovalListener<K, V> p;
    final Ticker q;
    final EntryFactory r;
    final StatsCounter s;
    @Nullable
    final CacheLoader<? super K, V> t;
    Set<K> w;
    Collection<V> x;
    Set<Entry<K, V>> y;

    static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        public ValueReference<K, V> a() {
            throw new UnsupportedOperationException();
        }

        public void a(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> b() {
            throw new UnsupportedOperationException();
        }

        public int c() {
            throw new UnsupportedOperationException();
        }

        public K d() {
            throw new UnsupportedOperationException();
        }

        public long e() {
            throw new UnsupportedOperationException();
        }

        public void a(long j) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> f() {
            throw new UnsupportedOperationException();
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> g() {
            throw new UnsupportedOperationException();
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long h() {
            throw new UnsupportedOperationException();
        }

        public void b(long j) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> i() {
            throw new UnsupportedOperationException();
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> j() {
            throw new UnsupportedOperationException();
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }

    enum EntryFactory {
        STRONG {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k, i, referenceEntry);
            }
        },
        STRONG_ACCESS {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                a(referenceEntry, a);
                return a;
            }
        },
        STRONG_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                b(referenceEntry, a);
                return a;
            }
        },
        STRONG_ACCESS_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                a(referenceEntry, a);
                b(referenceEntry, a);
                return a;
            }
        },
        WEAK {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.h, k, i, referenceEntry);
            }
        },
        WEAK_ACCESS {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.h, k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                a(referenceEntry, a);
                return a;
            }
        },
        WEAK_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.h, k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                b(referenceEntry, a);
                return a;
            }
        },
        WEAK_ACCESS_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.h, k, i, referenceEntry);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> a = super.a(segment, referenceEntry, referenceEntry2);
                a(referenceEntry, a);
                b(referenceEntry, a);
                return a;
            }
        };
        
        static final EntryFactory[] i = null;

        /* access modifiers changed from: 0000 */
        public abstract <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, K k, int i2, @Nullable ReferenceEntry<K, V> referenceEntry);

        static {
            i = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};
        }

        static EntryFactory a(Strength strength, boolean z, boolean z2) {
            char c = 0;
            boolean z3 = (strength == Strength.WEAK ? (char) 4 : 0) | z;
            if (z2) {
                c = 2;
            }
            return i[z3 | c];
        }

        /* access modifiers changed from: 0000 */
        public <K, V> ReferenceEntry<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return a(segment, referenceEntry.d(), referenceEntry.c(), referenceEntry2);
        }

        /* access modifiers changed from: 0000 */
        public <K, V> void a(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.a(referenceEntry.e());
            LocalCache.a(referenceEntry.g(), referenceEntry2);
            LocalCache.a(referenceEntry2, referenceEntry.f());
            LocalCache.b(referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        public <K, V> void b(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.b(referenceEntry.h());
            LocalCache.b(referenceEntry.j(), referenceEntry2);
            LocalCache.b(referenceEntry2, referenceEntry.i());
            LocalCache.c(referenceEntry);
        }
    }

    static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        volatile ValueReference<K, V> a;
        final SettableFuture<V> b;
        final Stopwatch c;

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public ReferenceEntry<K, V> b() {
            return null;
        }

        public boolean c() {
            return true;
        }

        public LoadingValueReference() {
            this(LocalCache.o());
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.b = SettableFuture.create();
            this.c = Stopwatch.createUnstarted();
            this.a = valueReference;
        }

        public boolean d() {
            return this.a.d();
        }

        public int a() {
            return this.a.a();
        }

        public boolean b(@Nullable V v) {
            return this.b.set(v);
        }

        public boolean a(Throwable th) {
            return this.b.setException(th);
        }

        private ListenableFuture<V> b(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        public void a(@Nullable V v) {
            if (v != null) {
                b(v);
            } else {
                this.a = LocalCache.o();
            }
        }

        public ListenableFuture<V> a(K k, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.c.start();
                Object obj = this.a.get();
                if (obj == null) {
                    Object load = cacheLoader.load(k);
                    return b((V) load) ? this.b : Futures.immediateFuture(load);
                }
                ListenableFuture reload = cacheLoader.reload(k, obj);
                if (reload == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(reload, new Function<V, V>() {
                    public V apply(V v) {
                        LoadingValueReference.this.b(v);
                        return v;
                    }
                });
            } catch (Throwable th) {
                ListenableFuture<V> b2 = a(th) ? this.b : b(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return b2;
            }
        }

        public long f() {
            return this.c.elapsed(TimeUnit.NANOSECONDS);
        }

        public V e() {
            return Uninterruptibles.getUninterruptibly(this.b);
        }

        public V get() {
            return this.a.get();
        }

        public ValueReference<K, V> g() {
            return this.a;
        }
    }

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> a;

        LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache<>(cacheBuilder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.a = localCache;
        }

        @Nullable
        public V getIfPresent(Object obj) {
            return this.a.b(obj);
        }

        public V get(K k, final Callable<? extends V> callable) {
            Preconditions.checkNotNull(callable);
            return this.a.a(k, (CacheLoader<? super K, V>) new CacheLoader<Object, V>() {
                public V load(Object obj) {
                    return callable.call();
                }
            });
        }

        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.a.a(iterable);
        }

        public void put(K k, V v) {
            this.a.put(k, v);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            this.a.putAll(map);
        }

        public void invalidate(Object obj) {
            Preconditions.checkNotNull(obj);
            this.a.remove(obj);
        }

        public void invalidateAll(Iterable<?> iterable) {
            this.a.c(iterable);
        }

        public void invalidateAll() {
            this.a.clear();
        }

        public long size() {
            return this.a.t();
        }

        public ConcurrentMap<K, V> asMap() {
            return this.a;
        }

        public CacheStats stats() {
            SimpleStatsCounter simpleStatsCounter = new SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.a.s);
            for (Segment<K, V> segment : this.a.d) {
                simpleStatsCounter.incrementBy(segment.n);
            }
            return simpleStatsCounter.snapshot();
        }

        public void cleanUp() {
            this.a.s();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new ManualSerializationProxy(this.a);
        }
    }

    interface ReferenceEntry<K, V> {
        ValueReference<K, V> a();

        void a(long j);

        void a(ReferenceEntry<K, V> referenceEntry);

        void a(ValueReference<K, V> valueReference);

        @Nullable
        ReferenceEntry<K, V> b();

        void b(long j);

        void b(ReferenceEntry<K, V> referenceEntry);

        int c();

        void c(ReferenceEntry<K, V> referenceEntry);

        @Nullable
        K d();

        void d(ReferenceEntry<K, V> referenceEntry);

        long e();

        ReferenceEntry<K, V> f();

        ReferenceEntry<K, V> g();

        long h();

        ReferenceEntry<K, V> i();

        ReferenceEntry<K, V> j();
    }

    static class Segment<K, V> extends ReentrantLock {
        @Weak
        final LocalCache<K, V> a;
        volatile int b;
        @GuardedBy("this")
        long c;
        int d;
        int e;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> f;
        final long g;
        final ReferenceQueue<K> h;
        final ReferenceQueue<V> i;
        final Queue<ReferenceEntry<K, V>> j;
        final AtomicInteger k = new AtomicInteger();
        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> l;
        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> m;
        final StatsCounter n;

        Segment(LocalCache<K, V> localCache, int i2, long j2, StatsCounter statsCounter) {
            this.a = localCache;
            this.g = j2;
            this.n = (StatsCounter) Preconditions.checkNotNull(statsCounter);
            a(a(i2));
            ReferenceQueue<V> referenceQueue = null;
            this.h = localCache.m() ? new ReferenceQueue<>() : null;
            if (localCache.n()) {
                referenceQueue = new ReferenceQueue<>();
            }
            this.i = referenceQueue;
            this.j = localCache.f() ? new ConcurrentLinkedQueue<>() : LocalCache.q();
            this.l = localCache.g() ? new WriteQueue<>() : LocalCache.q();
            this.m = localCache.f() ? new AccessQueue<>() : LocalCache.q();
        }

        /* access modifiers changed from: 0000 */
        public AtomicReferenceArray<ReferenceEntry<K, V>> a(int i2) {
            return new AtomicReferenceArray<>(i2);
        }

        /* access modifiers changed from: 0000 */
        public void a(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            this.e = (atomicReferenceArray.length() * 3) / 4;
            if (!this.a.b() && ((long) this.e) == this.g) {
                this.e++;
            }
            this.f = atomicReferenceArray;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public ReferenceEntry<K, V> a(K k2, int i2, @Nullable ReferenceEntry<K, V> referenceEntry) {
            return this.a.r.a(this, Preconditions.checkNotNull(k2), i2, referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public ReferenceEntry<K, V> a(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.d() == null) {
                return null;
            }
            ValueReference a2 = referenceEntry.a();
            Object obj = a2.get();
            if (obj == null && a2.d()) {
                return null;
            }
            ReferenceEntry<K, V> a3 = this.a.r.a(this, referenceEntry, referenceEntry2);
            a3.a(a2.a(this.i, obj, a3));
            return a3;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void a(ReferenceEntry<K, V> referenceEntry, K k2, V v, long j2) {
            ValueReference a2 = referenceEntry.a();
            int weigh = this.a.k.weigh(k2, v);
            Preconditions.checkState(weigh >= 0, "Weights must be non-negative");
            referenceEntry.a(this.a.i.a(this, referenceEntry, v, weigh));
            a(referenceEntry, weigh, j2);
            a2.a(v);
        }

        /* access modifiers changed from: 0000 */
        public V a(K k2, int i2, CacheLoader<? super K, V> cacheLoader) {
            Preconditions.checkNotNull(k2);
            Preconditions.checkNotNull(cacheLoader);
            try {
                if (this.b != 0) {
                    ReferenceEntry a2 = a((Object) k2, i2);
                    if (a2 != null) {
                        long read = this.a.q.read();
                        Object c2 = c(a2, read);
                        if (c2 != null) {
                            a(a2, read);
                            this.n.recordHits(1);
                            V a3 = a(a2, k2, i2, c2, read, cacheLoader);
                            l();
                            return a3;
                        }
                        ValueReference a4 = a2.a();
                        if (a4.c()) {
                            V a5 = a(a2, k2, a4);
                            l();
                            return a5;
                        }
                    }
                }
                V b2 = b(k2, i2, cacheLoader);
                l();
                return b2;
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                if (cause instanceof Error) {
                    throw new ExecutionError((Error) cause);
                } else if (cause instanceof RuntimeException) {
                    throw new UncheckedExecutionException(cause);
                } else {
                    throw e2;
                }
            } catch (Throwable th) {
                l();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public V b(K k2, int i2, CacheLoader<? super K, V> cacheLoader) {
            ValueReference valueReference;
            boolean z;
            LoadingValueReference loadingValueReference;
            V a2;
            K k3 = k2;
            int i3 = i2;
            lock();
            try {
                long read = this.a.q.read();
                c(read);
                int i4 = this.b - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = i3 & (atomicReferenceArray.length() - 1);
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        valueReference = null;
                        break;
                    }
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i3 || d2 == null || !this.a.f.equivalent(k3, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else {
                        ValueReference a3 = referenceEntry2.a();
                        if (a3.c()) {
                            z = false;
                            valueReference = a3;
                        } else {
                            V v = a3.get();
                            if (v == null) {
                                valueReference = a3;
                                a(d2, i3, v, a3.a(), RemovalCause.COLLECTED);
                            } else {
                                valueReference = a3;
                                if (this.a.b(referenceEntry2, read)) {
                                    a(d2, i3, v, valueReference.a(), RemovalCause.EXPIRED);
                                } else {
                                    b(referenceEntry2, read);
                                    this.n.recordHits(1);
                                    unlock();
                                    m();
                                    return v;
                                }
                            }
                            this.l.remove(referenceEntry2);
                            this.m.remove(referenceEntry2);
                            this.b = i4;
                        }
                    }
                }
                z = true;
                if (z) {
                    loadingValueReference = new LoadingValueReference();
                    if (referenceEntry2 == null) {
                        referenceEntry2 = a(k3, i3, referenceEntry);
                        referenceEntry2.a((ValueReference<K, V>) loadingValueReference);
                        atomicReferenceArray.set(length, referenceEntry2);
                    } else {
                        referenceEntry2.a((ValueReference<K, V>) loadingValueReference);
                    }
                } else {
                    loadingValueReference = null;
                }
                unlock();
                m();
                if (!z) {
                    return a(referenceEntry2, k3, valueReference);
                }
                try {
                    synchronized (referenceEntry2) {
                        a2 = a(k3, i3, loadingValueReference, cacheLoader);
                    }
                    this.n.recordMisses(1);
                    return a2;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    this.n.recordMisses(1);
                    throw th2;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                unlock();
                m();
                throw th4;
            }
        }

        /* access modifiers changed from: 0000 */
        public V a(ReferenceEntry<K, V> referenceEntry, K k2, ValueReference<K, V> valueReference) {
            if (!valueReference.c()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", (Object) k2);
            try {
                V e2 = valueReference.e();
                if (e2 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("CacheLoader returned null for key ");
                    sb.append(k2);
                    sb.append(".");
                    throw new InvalidCacheLoadException(sb.toString());
                }
                a(referenceEntry, this.a.q.read());
                return e2;
            } finally {
                this.n.recordMisses(1);
            }
        }

        /* access modifiers changed from: 0000 */
        public V a(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            return a(k2, i2, loadingValueReference, loadingValueReference.a(k2, cacheLoader));
        }

        /* access modifiers changed from: 0000 */
        public ListenableFuture<V> b(K k2, int i2, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            ListenableFuture<V> a2 = loadingValueReference.a(k2, cacheLoader);
            final K k3 = k2;
            final int i3 = i2;
            final LoadingValueReference<K, V> loadingValueReference2 = loadingValueReference;
            final ListenableFuture<V> listenableFuture = a2;
            AnonymousClass1 r0 = new Runnable() {
                public void run() {
                    try {
                        Segment.this.a(k3, i3, loadingValueReference2, listenableFuture);
                    } catch (Throwable th) {
                        LocalCache.a.log(Level.WARNING, "Exception thrown during refresh", th);
                        loadingValueReference2.a(th);
                    }
                }
            };
            a2.addListener(r0, MoreExecutors.directExecutor());
            return a2;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0043  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V a(K r4, int r5, com.google.common.cache.LocalCache.LoadingValueReference<K, V> r6, com.google.common.util.concurrent.ListenableFuture<V> r7) {
            /*
                r3 = this;
                java.lang.Object r7 = com.google.common.util.concurrent.Uninterruptibles.getUninterruptibly(r7)     // Catch:{ all -> 0x003f }
                if (r7 != 0) goto L_0x0024
                com.google.common.cache.CacheLoader$InvalidCacheLoadException r0 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException     // Catch:{ all -> 0x0022 }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0022 }
                r1.<init>()     // Catch:{ all -> 0x0022 }
                java.lang.String r2 = "CacheLoader returned null for key "
                r1.append(r2)     // Catch:{ all -> 0x0022 }
                r1.append(r4)     // Catch:{ all -> 0x0022 }
                java.lang.String r2 = "."
                r1.append(r2)     // Catch:{ all -> 0x0022 }
                java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0022 }
                r0.<init>(r1)     // Catch:{ all -> 0x0022 }
                throw r0     // Catch:{ all -> 0x0022 }
            L_0x0022:
                r0 = move-exception
                goto L_0x0041
            L_0x0024:
                com.google.common.cache.AbstractCache$StatsCounter r0 = r3.n     // Catch:{ all -> 0x0022 }
                long r1 = r6.f()     // Catch:{ all -> 0x0022 }
                r0.recordLoadSuccess(r1)     // Catch:{ all -> 0x0022 }
                r3.a((K) r4, r5, r6, (V) r7)     // Catch:{ all -> 0x0022 }
                if (r7 != 0) goto L_0x003e
                com.google.common.cache.AbstractCache$StatsCounter r0 = r3.n
                long r1 = r6.f()
                r0.recordLoadException(r1)
                r3.a((K) r4, r5, r6)
            L_0x003e:
                return r7
            L_0x003f:
                r0 = move-exception
                r7 = 0
            L_0x0041:
                if (r7 != 0) goto L_0x004f
                com.google.common.cache.AbstractCache$StatsCounter r7 = r3.n
                long r1 = r6.f()
                r7.recordLoadException(r1)
                r3.a((K) r4, r5, r6)
            L_0x004f:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.a(java.lang.Object, int, com.google.common.cache.LocalCache$LoadingValueReference, com.google.common.util.concurrent.ListenableFuture):java.lang.Object");
        }

        /* access modifiers changed from: 0000 */
        public V a(ReferenceEntry<K, V> referenceEntry, K k2, int i2, V v, long j2, CacheLoader<? super K, V> cacheLoader) {
            if (this.a.e() && j2 - referenceEntry.h() > this.a.n && !referenceEntry.a().c()) {
                V a2 = a(k2, i2, cacheLoader, true);
                if (a2 != null) {
                    return a2;
                }
            }
            return v;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V a(K k2, int i2, CacheLoader<? super K, V> cacheLoader, boolean z) {
            LoadingValueReference a2 = a(k2, i2, z);
            if (a2 == null) {
                return null;
            }
            ListenableFuture b2 = b(k2, i2, a2, cacheLoader);
            if (b2.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(b2);
                } catch (Throwable unused) {
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public LoadingValueReference<K, V> a(K k2, int i2, boolean z) {
            lock();
            try {
                long read = this.a.q.read();
                c(read);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (referenceEntry2 != null) {
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i2 || d2 == null || !this.a.f.equivalent(k2, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else {
                        ValueReference a2 = referenceEntry2.a();
                        if (!a2.c()) {
                            if (!z || read - referenceEntry2.h() >= this.a.n) {
                                this.d++;
                                LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(a2);
                                referenceEntry2.a((ValueReference<K, V>) loadingValueReference);
                                unlock();
                                m();
                                return loadingValueReference;
                            }
                        }
                        return null;
                    }
                }
                this.d++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry a3 = a(k2, i2, referenceEntry);
                a3.a((ValueReference<K, V>) loadingValueReference2);
                atomicReferenceArray.set(length, a3);
                unlock();
                m();
                return loadingValueReference2;
            } finally {
                unlock();
                m();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (tryLock()) {
                try {
                    b();
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b() {
            if (this.a.m()) {
                c();
            }
            if (this.a.n()) {
                d();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void c() {
            int i2 = 0;
            do {
                Reference poll = this.h.poll();
                if (poll != null) {
                    this.a.a((ReferenceEntry) poll);
                    i2++;
                } else {
                    return;
                }
            } while (i2 != 16);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void d() {
            int i2 = 0;
            do {
                Reference poll = this.i.poll();
                if (poll != null) {
                    this.a.a((ValueReference) poll);
                    i2++;
                } else {
                    return;
                }
            } while (i2 != 16);
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            if (this.a.m()) {
                f();
            }
            if (this.a.n()) {
                g();
            }
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            do {
            } while (this.h.poll() != null);
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            do {
            } while (this.i.poll() != null);
        }

        /* access modifiers changed from: 0000 */
        public void a(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.a.i()) {
                referenceEntry.a(j2);
            }
            this.j.add(referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (this.a.i()) {
                referenceEntry.a(j2);
            }
            this.m.add(referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void a(ReferenceEntry<K, V> referenceEntry, int i2, long j2) {
            h();
            this.c += (long) i2;
            if (this.a.i()) {
                referenceEntry.a(j2);
            }
            if (this.a.h()) {
                referenceEntry.b(j2);
            }
            this.m.add(referenceEntry);
            this.l.add(referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void h() {
            while (true) {
                ReferenceEntry referenceEntry = (ReferenceEntry) this.j.poll();
                if (referenceEntry == null) {
                    return;
                }
                if (this.m.contains(referenceEntry)) {
                    this.m.add(referenceEntry);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j2) {
            if (tryLock()) {
                try {
                    b(j2);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b(long j2) {
            ReferenceEntry referenceEntry;
            ReferenceEntry referenceEntry2;
            h();
            do {
                referenceEntry = (ReferenceEntry) this.l.peek();
                if (referenceEntry == null || !this.a.b(referenceEntry, j2)) {
                    do {
                        referenceEntry2 = (ReferenceEntry) this.m.peek();
                        if (referenceEntry2 == null || !this.a.b(referenceEntry2, j2)) {
                            return;
                        }
                    } while (a(referenceEntry2, referenceEntry2.c(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (a(referenceEntry, referenceEntry.c(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void a(@Nullable K k2, int i2, @Nullable V v, int i3, RemovalCause removalCause) {
            this.c -= (long) i3;
            if (removalCause.a()) {
                this.n.recordEviction();
            }
            if (this.a.o != LocalCache.v) {
                this.a.o.offer(RemovalNotification.create(k2, v, removalCause));
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void a(ReferenceEntry<K, V> referenceEntry) {
            if (this.a.a()) {
                h();
                if (((long) referenceEntry.a().a()) <= this.g || a(referenceEntry, referenceEntry.c(), RemovalCause.SIZE)) {
                    while (this.c > this.g) {
                        ReferenceEntry i2 = i();
                        if (!a(i2, i2.c(), RemovalCause.SIZE)) {
                            throw new AssertionError();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public ReferenceEntry<K, V> i() {
            for (ReferenceEntry<K, V> referenceEntry : this.m) {
                if (referenceEntry.a().a() > 0) {
                    return referenceEntry;
                }
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public ReferenceEntry<K, V> b(int i2) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
            return (ReferenceEntry) atomicReferenceArray.get(i2 & (atomicReferenceArray.length() - 1));
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ReferenceEntry<K, V> a(Object obj, int i2) {
            for (ReferenceEntry<K, V> b2 = b(i2); b2 != null; b2 = b2.b()) {
                if (b2.c() == i2) {
                    Object d2 = b2.d();
                    if (d2 == null) {
                        a();
                    } else if (this.a.f.equivalent(obj, d2)) {
                        return b2;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ReferenceEntry<K, V> a(Object obj, int i2, long j2) {
            ReferenceEntry<K, V> a2 = a(obj, i2);
            if (a2 == null) {
                return null;
            }
            if (!this.a.b(a2, j2)) {
                return a2;
            }
            a(j2);
            return null;
        }

        /* access modifiers changed from: 0000 */
        public V c(ReferenceEntry<K, V> referenceEntry, long j2) {
            if (referenceEntry.d() == null) {
                a();
                return null;
            }
            V v = referenceEntry.a().get();
            if (v == null) {
                a();
                return null;
            } else if (!this.a.b(referenceEntry, j2)) {
                return v;
            } else {
                a(j2);
                return null;
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V b(Object obj, int i2) {
            try {
                if (this.b != 0) {
                    long read = this.a.q.read();
                    ReferenceEntry a2 = a(obj, i2, read);
                    if (a2 == null) {
                        return null;
                    }
                    Object obj2 = a2.a().get();
                    if (obj2 != null) {
                        a(a2, read);
                        V a3 = a(a2, a2.d(), i2, obj2, read, this.a.t);
                        l();
                        return a3;
                    }
                    a();
                }
                l();
                return null;
            } finally {
                l();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean c(Object obj, int i2) {
            try {
                boolean z = false;
                if (this.b != 0) {
                    ReferenceEntry a2 = a(obj, i2, this.a.q.read());
                    if (a2 == null) {
                        return false;
                    }
                    if (a2.a().get() != null) {
                        z = true;
                    }
                    l();
                    return z;
                }
                l();
                return false;
            } finally {
                l();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004a, code lost:
            r1 = r13.a();
            r11 = r1.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
            if (r11 != null) goto L_0x0091;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0054, code lost:
            r7.d++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x005e, code lost:
            if (r1.d() == false) goto L_0x0078;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0060, code lost:
            a(r8, r3, r11, r1.a(), com.google.common.cache.RemovalCause.COLLECTED);
            a(r13, r8, r18, r9);
            r1 = r7.b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0078, code lost:
            a(r13, r8, r18, r9);
            r1 = r7.b + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0085, code lost:
            r7.b = r1;
            a(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0091, code lost:
            if (r19 == false) goto L_0x009d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            b(r13, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0096, code lost:
            unlock();
            m();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x009c, code lost:
            return r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            r7.d++;
            a(r8, r3, r11, r1.a(), com.google.common.cache.RemovalCause.REPLACED);
            a(r13, r8, r18, r9);
            a(r13);
         */
        @javax.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V a(K r16, int r17, V r18, boolean r19) {
            /*
                r15 = this;
                r7 = r15
                r8 = r16
                r3 = r17
                r7.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r7.a     // Catch:{ all -> 0x00e2 }
                com.google.common.base.Ticker r1 = r1.q     // Catch:{ all -> 0x00e2 }
                long r9 = r1.read()     // Catch:{ all -> 0x00e2 }
                r7.c(r9)     // Catch:{ all -> 0x00e2 }
                int r1 = r7.b     // Catch:{ all -> 0x00e2 }
                int r1 = r1 + 1
                int r2 = r7.e     // Catch:{ all -> 0x00e2 }
                if (r1 <= r2) goto L_0x0020
                r7.j()     // Catch:{ all -> 0x00e2 }
                int r1 = r7.b     // Catch:{ all -> 0x00e2 }
            L_0x0020:
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r11 = r7.f     // Catch:{ all -> 0x00e2 }
                int r1 = r11.length()     // Catch:{ all -> 0x00e2 }
                int r1 = r1 + -1
                r12 = r3 & r1
                java.lang.Object r1 = r11.get(r12)     // Catch:{ all -> 0x00e2 }
                com.google.common.cache.LocalCache$ReferenceEntry r1 = (com.google.common.cache.LocalCache.ReferenceEntry) r1     // Catch:{ all -> 0x00e2 }
                r13 = r1
            L_0x0031:
                r14 = 0
                if (r13 == 0) goto L_0x00c2
                java.lang.Object r2 = r13.d()     // Catch:{ all -> 0x00e2 }
                int r4 = r13.c()     // Catch:{ all -> 0x00e2 }
                if (r4 != r3) goto L_0x00bc
                if (r2 == 0) goto L_0x00bc
                com.google.common.cache.LocalCache<K, V> r4 = r7.a     // Catch:{ all -> 0x00e2 }
                com.google.common.base.Equivalence<java.lang.Object> r4 = r4.f     // Catch:{ all -> 0x00e2 }
                boolean r2 = r4.equivalent(r8, r2)     // Catch:{ all -> 0x00e2 }
                if (r2 == 0) goto L_0x00bc
                com.google.common.cache.LocalCache$ValueReference r1 = r13.a()     // Catch:{ all -> 0x00e2 }
                java.lang.Object r11 = r1.get()     // Catch:{ all -> 0x00e2 }
                if (r11 != 0) goto L_0x0091
                int r2 = r7.d     // Catch:{ all -> 0x00e2 }
                int r2 = r2 + 1
                r7.d = r2     // Catch:{ all -> 0x00e2 }
                boolean r2 = r1.d()     // Catch:{ all -> 0x00e2 }
                if (r2 == 0) goto L_0x0078
                int r5 = r1.a()     // Catch:{ all -> 0x00e2 }
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00e2 }
                r1 = r7
                r2 = r8
                r4 = r11
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00e2 }
                r1 = r7
                r2 = r13
                r3 = r8
                r4 = r18
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00e2 }
                int r1 = r7.b     // Catch:{ all -> 0x00e2 }
                goto L_0x0085
            L_0x0078:
                r1 = r7
                r2 = r13
                r3 = r8
                r4 = r18
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00e2 }
                int r1 = r7.b     // Catch:{ all -> 0x00e2 }
                int r1 = r1 + 1
            L_0x0085:
                r7.b = r1     // Catch:{ all -> 0x00e2 }
                r7.a(r13)     // Catch:{ all -> 0x00e2 }
            L_0x008a:
                r7.unlock()
                r7.m()
                return r14
            L_0x0091:
                if (r19 == 0) goto L_0x009d
                r7.b(r13, r9)     // Catch:{ all -> 0x00e2 }
            L_0x0096:
                r7.unlock()
                r7.m()
                return r11
            L_0x009d:
                int r2 = r7.d     // Catch:{ all -> 0x00e2 }
                int r2 = r2 + 1
                r7.d = r2     // Catch:{ all -> 0x00e2 }
                int r5 = r1.a()     // Catch:{ all -> 0x00e2 }
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00e2 }
                r1 = r7
                r2 = r8
                r4 = r11
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00e2 }
                r1 = r7
                r2 = r13
                r3 = r8
                r4 = r18
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00e2 }
                r7.a(r13)     // Catch:{ all -> 0x00e2 }
                goto L_0x0096
            L_0x00bc:
                com.google.common.cache.LocalCache$ReferenceEntry r13 = r13.b()     // Catch:{ all -> 0x00e2 }
                goto L_0x0031
            L_0x00c2:
                int r2 = r7.d     // Catch:{ all -> 0x00e2 }
                int r2 = r2 + 1
                r7.d = r2     // Catch:{ all -> 0x00e2 }
                com.google.common.cache.LocalCache$ReferenceEntry r13 = r7.a((K) r8, r3, r1)     // Catch:{ all -> 0x00e2 }
                r1 = r7
                r2 = r13
                r3 = r8
                r4 = r18
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00e2 }
                r11.set(r12, r13)     // Catch:{ all -> 0x00e2 }
                int r1 = r7.b     // Catch:{ all -> 0x00e2 }
                int r1 = r1 + 1
                r7.b = r1     // Catch:{ all -> 0x00e2 }
                r7.a(r13)     // Catch:{ all -> 0x00e2 }
                goto L_0x008a
            L_0x00e2:
                r0 = move-exception
                r1 = r0
                r7.unlock()
                r7.m()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.a(java.lang.Object, int, java.lang.Object, boolean):java.lang.Object");
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void j() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i2 = this.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> a2 = a(length << 1);
                this.e = (a2.length() * 3) / 4;
                int length2 = a2.length() - 1;
                for (int i3 = 0; i3 < length; i3++) {
                    ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i3);
                    if (referenceEntry != null) {
                        ReferenceEntry b2 = referenceEntry.b();
                        int c2 = referenceEntry.c() & length2;
                        if (b2 == null) {
                            a2.set(c2, referenceEntry);
                        } else {
                            ReferenceEntry referenceEntry2 = referenceEntry;
                            while (b2 != null) {
                                int c3 = b2.c() & length2;
                                if (c3 != c2) {
                                    referenceEntry2 = b2;
                                    c2 = c3;
                                }
                                b2 = b2.b();
                            }
                            a2.set(c2, referenceEntry2);
                            while (referenceEntry != referenceEntry2) {
                                int c4 = referenceEntry.c() & length2;
                                ReferenceEntry a3 = a(referenceEntry, (ReferenceEntry) a2.get(c4));
                                if (a3 != null) {
                                    a2.set(c4, a3);
                                } else {
                                    b(referenceEntry);
                                    i2--;
                                }
                                referenceEntry = referenceEntry.b();
                            }
                        }
                    }
                }
                this.f = a2;
                this.b = i2;
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
            r6 = r13.a();
            r3 = r6.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
            if (r3 != null) goto L_0x0071;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
            if (r6.d() == false) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
            r1 = r9.b;
            r9.d++;
            r7 = r3;
            r3 = r13;
            r13 = r6;
            r1 = a(r2, r3, r4, r5, r7, r13, com.google.common.cache.RemovalCause.COLLECTED);
            r2 = r9.b - 1;
            r10.set(r12, r1);
            r9.b = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0071, code lost:
            r4 = r3;
            r1 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x007d, code lost:
            if (r9.a.g.equivalent(r19, r4) == false) goto L_0x00a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x007f, code lost:
            r9.d++;
            a(r15, r5, r4, r1.a(), com.google.common.cache.RemovalCause.REPLACED);
            a(r13, r15, r20, r7);
            a(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x009e, code lost:
            unlock();
            m();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a4, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            b(r13, r7);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(K r17, int r18, V r19, V r20) {
            /*
                r16 = this;
                r9 = r16
                r5 = r18
                r16.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.a     // Catch:{ all -> 0x00b3 }
                com.google.common.base.Ticker r1 = r1.q     // Catch:{ all -> 0x00b3 }
                long r7 = r1.read()     // Catch:{ all -> 0x00b3 }
                r9.c(r7)     // Catch:{ all -> 0x00b3 }
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r10 = r9.f     // Catch:{ all -> 0x00b3 }
                int r1 = r10.length()     // Catch:{ all -> 0x00b3 }
                r11 = 1
                int r1 = r1 - r11
                r12 = r5 & r1
                java.lang.Object r1 = r10.get(r12)     // Catch:{ all -> 0x00b3 }
                r2 = r1
                com.google.common.cache.LocalCache$ReferenceEntry r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2     // Catch:{ all -> 0x00b3 }
                r13 = r2
            L_0x0024:
                r14 = 0
                if (r13 == 0) goto L_0x006a
                java.lang.Object r4 = r13.d()     // Catch:{ all -> 0x00b3 }
                int r1 = r13.c()     // Catch:{ all -> 0x00b3 }
                if (r1 != r5) goto L_0x00a9
                if (r4 == 0) goto L_0x00a9
                com.google.common.cache.LocalCache<K, V> r1 = r9.a     // Catch:{ all -> 0x00b3 }
                com.google.common.base.Equivalence<java.lang.Object> r1 = r1.f     // Catch:{ all -> 0x00b3 }
                r15 = r17
                boolean r1 = r1.equivalent(r15, r4)     // Catch:{ all -> 0x00b3 }
                if (r1 == 0) goto L_0x00ab
                com.google.common.cache.LocalCache$ValueReference r6 = r13.a()     // Catch:{ all -> 0x00b3 }
                java.lang.Object r3 = r6.get()     // Catch:{ all -> 0x00b3 }
                if (r3 != 0) goto L_0x0071
                boolean r1 = r6.d()     // Catch:{ all -> 0x00b3 }
                if (r1 == 0) goto L_0x006a
                int r1 = r9.b     // Catch:{ all -> 0x00b3 }
                int r1 = r9.d     // Catch:{ all -> 0x00b3 }
                int r1 = r1 + r11
                r9.d = r1     // Catch:{ all -> 0x00b3 }
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00b3 }
                r1 = r9
                r7 = r3
                r3 = r13
                r13 = r6
                r6 = r7
                r7 = r13
                com.google.common.cache.LocalCache$ReferenceEntry r1 = r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00b3 }
                int r2 = r9.b     // Catch:{ all -> 0x00b3 }
                int r2 = r2 - r11
                r10.set(r12, r1)     // Catch:{ all -> 0x00b3 }
                r9.b = r2     // Catch:{ all -> 0x00b3 }
            L_0x006a:
                r16.unlock()
                r16.m()
                return r14
            L_0x0071:
                r4 = r3
                r1 = r6
                com.google.common.cache.LocalCache<K, V> r2 = r9.a     // Catch:{ all -> 0x00b3 }
                com.google.common.base.Equivalence<java.lang.Object> r2 = r2.g     // Catch:{ all -> 0x00b3 }
                r3 = r19
                boolean r2 = r2.equivalent(r3, r4)     // Catch:{ all -> 0x00b3 }
                if (r2 == 0) goto L_0x00a5
                int r2 = r9.d     // Catch:{ all -> 0x00b3 }
                int r2 = r2 + r11
                r9.d = r2     // Catch:{ all -> 0x00b3 }
                int r6 = r1.a()     // Catch:{ all -> 0x00b3 }
                com.google.common.cache.RemovalCause r10 = com.google.common.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00b3 }
                r1 = r9
                r2 = r15
                r3 = r5
                r5 = r6
                r6 = r10
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00b3 }
                r1 = r9
                r2 = r13
                r3 = r15
                r4 = r20
                r5 = r7
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00b3 }
                r9.a(r13)     // Catch:{ all -> 0x00b3 }
                r16.unlock()
                r16.m()
                return r11
            L_0x00a5:
                r9.b(r13, r7)     // Catch:{ all -> 0x00b3 }
                goto L_0x006a
            L_0x00a9:
                r15 = r17
            L_0x00ab:
                r3 = r19
                com.google.common.cache.LocalCache$ReferenceEntry r13 = r13.b()     // Catch:{ all -> 0x00b3 }
                goto L_0x0024
            L_0x00b3:
                r0 = move-exception
                r1 = r0
                r16.unlock()
                r16.m()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.a(java.lang.Object, int, java.lang.Object, java.lang.Object):boolean");
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
            r15 = r12.a();
            r16 = r15.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
            if (r16 != null) goto L_0x0072;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
            if (r15.d() == false) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
            r1 = r9.b;
            r9.d++;
            r1 = a(r2, r12, r4, r5, r16, r15, com.google.common.cache.RemovalCause.COLLECTED);
            r2 = r9.b - 1;
            r10.set(r11, r1);
            r9.b = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            r9.d++;
            a(r14, r5, r16, r15.a(), com.google.common.cache.RemovalCause.REPLACED);
            a(r12, r14, r20, r7);
            a(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0094, code lost:
            unlock();
            m();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x009a, code lost:
            return r16;
         */
        @javax.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V a(K r18, int r19, V r20) {
            /*
                r17 = this;
                r9 = r17
                r5 = r19
                r17.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r9.a     // Catch:{ all -> 0x00a2 }
                com.google.common.base.Ticker r1 = r1.q     // Catch:{ all -> 0x00a2 }
                long r7 = r1.read()     // Catch:{ all -> 0x00a2 }
                r9.c(r7)     // Catch:{ all -> 0x00a2 }
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r10 = r9.f     // Catch:{ all -> 0x00a2 }
                int r1 = r10.length()     // Catch:{ all -> 0x00a2 }
                int r1 = r1 + -1
                r11 = r5 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch:{ all -> 0x00a2 }
                r2 = r1
                com.google.common.cache.LocalCache$ReferenceEntry r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2     // Catch:{ all -> 0x00a2 }
                r12 = r2
            L_0x0024:
                r13 = 0
                if (r12 == 0) goto L_0x006b
                java.lang.Object r4 = r12.d()     // Catch:{ all -> 0x00a2 }
                int r1 = r12.c()     // Catch:{ all -> 0x00a2 }
                if (r1 != r5) goto L_0x009b
                if (r4 == 0) goto L_0x009b
                com.google.common.cache.LocalCache<K, V> r1 = r9.a     // Catch:{ all -> 0x00a2 }
                com.google.common.base.Equivalence<java.lang.Object> r1 = r1.f     // Catch:{ all -> 0x00a2 }
                r14 = r18
                boolean r1 = r1.equivalent(r14, r4)     // Catch:{ all -> 0x00a2 }
                if (r1 == 0) goto L_0x009d
                com.google.common.cache.LocalCache$ValueReference r15 = r12.a()     // Catch:{ all -> 0x00a2 }
                java.lang.Object r16 = r15.get()     // Catch:{ all -> 0x00a2 }
                if (r16 != 0) goto L_0x0072
                boolean r1 = r15.d()     // Catch:{ all -> 0x00a2 }
                if (r1 == 0) goto L_0x006b
                int r1 = r9.b     // Catch:{ all -> 0x00a2 }
                int r1 = r9.d     // Catch:{ all -> 0x00a2 }
                int r1 = r1 + 1
                r9.d = r1     // Catch:{ all -> 0x00a2 }
                com.google.common.cache.RemovalCause r8 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00a2 }
                r1 = r9
                r3 = r12
                r6 = r16
                r7 = r15
                com.google.common.cache.LocalCache$ReferenceEntry r1 = r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00a2 }
                int r2 = r9.b     // Catch:{ all -> 0x00a2 }
                int r2 = r2 + -1
                r10.set(r11, r1)     // Catch:{ all -> 0x00a2 }
                r9.b = r2     // Catch:{ all -> 0x00a2 }
            L_0x006b:
                r17.unlock()
                r17.m()
                return r13
            L_0x0072:
                int r1 = r9.d     // Catch:{ all -> 0x00a2 }
                int r1 = r1 + 1
                r9.d = r1     // Catch:{ all -> 0x00a2 }
                int r6 = r15.a()     // Catch:{ all -> 0x00a2 }
                com.google.common.cache.RemovalCause r10 = com.google.common.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00a2 }
                r1 = r9
                r2 = r14
                r3 = r5
                r4 = r16
                r5 = r6
                r6 = r10
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00a2 }
                r1 = r9
                r2 = r12
                r3 = r14
                r4 = r20
                r5 = r7
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00a2 }
                r9.a(r12)     // Catch:{ all -> 0x00a2 }
                r17.unlock()
                r17.m()
                return r16
            L_0x009b:
                r14 = r18
            L_0x009d:
                com.google.common.cache.LocalCache$ReferenceEntry r12 = r12.b()     // Catch:{ all -> 0x00a2 }
                goto L_0x0024
            L_0x00a2:
                r0 = move-exception
                r1 = r0
                r17.unlock()
                r17.m()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.a(java.lang.Object, int, java.lang.Object):java.lang.Object");
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V d(Object obj, int i2) {
            RemovalCause removalCause;
            lock();
            try {
                c(this.a.q.read());
                int i3 = this.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i2 || d2 == null || !this.a.f.equivalent(obj, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else {
                        ValueReference a2 = referenceEntry2.a();
                        V v = a2.get();
                        if (v != null) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (a2.d()) {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        RemovalCause removalCause2 = removalCause;
                        this.d++;
                        int i4 = this.b - 1;
                        atomicReferenceArray.set(length, a(referenceEntry, referenceEntry2, d2, i2, v, a2, removalCause2));
                        this.b = i4;
                        return v;
                    }
                }
                unlock();
                m();
                return null;
            } finally {
                unlock();
                m();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
            r1 = r15.a();
            r4 = r1.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0055, code lost:
            if (r19 == r1) goto L_0x0070;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0057, code lost:
            if (r4 != null) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
            if (r1 == com.google.common.cache.LocalCache.u) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005e, code lost:
            a(r8, r3, r20, 0, com.google.common.cache.RemovalCause.REPLACED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0068, code lost:
            unlock();
            m();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            r7.d++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0079, code lost:
            if (r19.d() == false) goto L_0x008e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x007b, code lost:
            if (r4 != null) goto L_0x0080;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x007d, code lost:
            r1 = com.google.common.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0080, code lost:
            r1 = com.google.common.cache.RemovalCause.REPLACED;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
            r6 = r1;
            a(r8, r3, r4, r19.a(), r6);
            r12 = r12 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
            a(r15, r8, r20, r9);
            r7.b = r12;
            a(r15);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(K r17, int r18, com.google.common.cache.LocalCache.LoadingValueReference<K, V> r19, V r20) {
            /*
                r16 = this;
                r7 = r16
                r8 = r17
                r3 = r18
                r16.lock()
                com.google.common.cache.LocalCache<K, V> r1 = r7.a     // Catch:{ all -> 0x00c5 }
                com.google.common.base.Ticker r1 = r1.q     // Catch:{ all -> 0x00c5 }
                long r9 = r1.read()     // Catch:{ all -> 0x00c5 }
                r7.c(r9)     // Catch:{ all -> 0x00c5 }
                int r1 = r7.b     // Catch:{ all -> 0x00c5 }
                r11 = 1
                int r1 = r1 + r11
                int r2 = r7.e     // Catch:{ all -> 0x00c5 }
                if (r1 <= r2) goto L_0x0022
                r16.j()     // Catch:{ all -> 0x00c5 }
                int r1 = r7.b     // Catch:{ all -> 0x00c5 }
                int r1 = r1 + r11
            L_0x0022:
                r12 = r1
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r13 = r7.f     // Catch:{ all -> 0x00c5 }
                int r1 = r13.length()     // Catch:{ all -> 0x00c5 }
                int r1 = r1 - r11
                r14 = r3 & r1
                java.lang.Object r1 = r13.get(r14)     // Catch:{ all -> 0x00c5 }
                com.google.common.cache.LocalCache$ReferenceEntry r1 = (com.google.common.cache.LocalCache.ReferenceEntry) r1     // Catch:{ all -> 0x00c5 }
                r15 = r1
            L_0x0033:
                if (r15 == 0) goto L_0x00aa
                java.lang.Object r2 = r15.d()     // Catch:{ all -> 0x00c5 }
                int r4 = r15.c()     // Catch:{ all -> 0x00c5 }
                if (r4 != r3) goto L_0x00a3
                if (r2 == 0) goto L_0x00a3
                com.google.common.cache.LocalCache<K, V> r4 = r7.a     // Catch:{ all -> 0x00c5 }
                com.google.common.base.Equivalence<java.lang.Object> r4 = r4.f     // Catch:{ all -> 0x00c5 }
                boolean r2 = r4.equivalent(r8, r2)     // Catch:{ all -> 0x00c5 }
                if (r2 == 0) goto L_0x00a3
                com.google.common.cache.LocalCache$ValueReference r1 = r15.a()     // Catch:{ all -> 0x00c5 }
                java.lang.Object r4 = r1.get()     // Catch:{ all -> 0x00c5 }
                r2 = r19
                if (r2 == r1) goto L_0x0070
                if (r4 != 0) goto L_0x005e
                com.google.common.cache.LocalCache$ValueReference<java.lang.Object, java.lang.Object> r5 = com.google.common.cache.LocalCache.u     // Catch:{ all -> 0x00c5 }
                if (r1 == r5) goto L_0x005e
                goto L_0x0070
            L_0x005e:
                r5 = 0
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00c5 }
                r1 = r7
                r2 = r8
                r4 = r20
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00c5 }
                r1 = 0
                r16.unlock()
                r16.m()
                return r1
            L_0x0070:
                int r1 = r7.d     // Catch:{ all -> 0x00c5 }
                int r1 = r1 + r11
                r7.d = r1     // Catch:{ all -> 0x00c5 }
                boolean r1 = r19.d()     // Catch:{ all -> 0x00c5 }
                if (r1 == 0) goto L_0x008e
                if (r4 != 0) goto L_0x0080
                com.google.common.cache.RemovalCause r1 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00c5 }
                goto L_0x0082
            L_0x0080:
                com.google.common.cache.RemovalCause r1 = com.google.common.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00c5 }
            L_0x0082:
                r6 = r1
                int r5 = r19.a()     // Catch:{ all -> 0x00c5 }
                r1 = r7
                r2 = r8
                r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00c5 }
                int r12 = r12 + -1
            L_0x008e:
                r1 = r7
                r2 = r15
                r3 = r8
                r4 = r20
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00c5 }
                r7.b = r12     // Catch:{ all -> 0x00c5 }
                r7.a(r15)     // Catch:{ all -> 0x00c5 }
            L_0x009c:
                r16.unlock()
                r16.m()
                return r11
            L_0x00a3:
                r2 = r19
                com.google.common.cache.LocalCache$ReferenceEntry r15 = r15.b()     // Catch:{ all -> 0x00c5 }
                goto L_0x0033
            L_0x00aa:
                int r2 = r7.d     // Catch:{ all -> 0x00c5 }
                int r2 = r2 + r11
                r7.d = r2     // Catch:{ all -> 0x00c5 }
                com.google.common.cache.LocalCache$ReferenceEntry r15 = r7.a((K) r8, r3, r1)     // Catch:{ all -> 0x00c5 }
                r1 = r7
                r2 = r15
                r3 = r8
                r4 = r20
                r5 = r9
                r1.a(r2, (K) r3, (V) r4, r5)     // Catch:{ all -> 0x00c5 }
                r13.set(r14, r15)     // Catch:{ all -> 0x00c5 }
                r7.b = r12     // Catch:{ all -> 0x00c5 }
                r7.a(r15)     // Catch:{ all -> 0x00c5 }
                goto L_0x009c
            L_0x00c5:
                r0 = move-exception
                r1 = r0
                r16.unlock()
                r16.m()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.a(java.lang.Object, int, com.google.common.cache.LocalCache$LoadingValueReference, java.lang.Object):boolean");
        }

        /* access modifiers changed from: 0000 */
        public boolean b(Object obj, int i2, Object obj2) {
            RemovalCause removalCause;
            lock();
            try {
                c(this.a.q.read());
                int i3 = this.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                boolean z = true;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i2 || d2 == null || !this.a.f.equivalent(obj, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else {
                        ValueReference a2 = referenceEntry2.a();
                        Object obj3 = a2.get();
                        if (this.a.g.equivalent(obj2, obj3)) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (obj3 == null && a2.d()) {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        this.d++;
                        int i4 = this.b - 1;
                        atomicReferenceArray.set(length, a(referenceEntry, referenceEntry2, d2, i2, obj3, a2, removalCause));
                        this.b = i4;
                        if (removalCause != RemovalCause.EXPLICIT) {
                            z = false;
                        }
                        return z;
                    }
                }
                unlock();
                m();
                return false;
            } finally {
                unlock();
                m();
            }
        }

        /* access modifiers changed from: 0000 */
        public void k() {
            RemovalCause removalCause;
            if (this.b != 0) {
                lock();
                try {
                    c(this.a.q.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        for (ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i2); referenceEntry != null; referenceEntry = referenceEntry.b()) {
                            if (referenceEntry.a().d()) {
                                Object d2 = referenceEntry.d();
                                Object obj = referenceEntry.a().get();
                                if (d2 != null) {
                                    if (obj != null) {
                                        removalCause = RemovalCause.EXPLICIT;
                                        a(d2, referenceEntry.c(), obj, referenceEntry.a().a(), removalCause);
                                    }
                                }
                                removalCause = RemovalCause.COLLECTED;
                                a(d2, referenceEntry.c(), obj, referenceEntry.a().a(), removalCause);
                            }
                        }
                    }
                    for (int i3 = 0; i3 < atomicReferenceArray.length(); i3++) {
                        atomicReferenceArray.set(i3, null);
                    }
                    e();
                    this.l.clear();
                    this.m.clear();
                    this.k.set(0);
                    this.d++;
                    this.b = 0;
                } finally {
                    unlock();
                    m();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        @Nullable
        public ReferenceEntry<K, V> a(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @Nullable K k2, int i2, V v, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            a(k2, i2, v, valueReference.a(), removalCause);
            this.l.remove(referenceEntry2);
            this.m.remove(referenceEntry2);
            if (!valueReference.c()) {
                return b(referenceEntry, referenceEntry2);
            }
            valueReference.a(null);
            return referenceEntry;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        @Nullable
        public ReferenceEntry<K, V> b(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int i2 = this.b;
            ReferenceEntry<K, V> b2 = referenceEntry2.b();
            while (referenceEntry != referenceEntry2) {
                ReferenceEntry<K, V> a2 = a(referenceEntry, b2);
                if (a2 != null) {
                    b2 = a2;
                } else {
                    b(referenceEntry);
                    i2--;
                }
                referenceEntry = referenceEntry.b();
            }
            this.b = i2;
            return b2;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b(ReferenceEntry<K, V> referenceEntry) {
            a(referenceEntry.d(), referenceEntry.c(), referenceEntry.a().get(), referenceEntry.a().a(), RemovalCause.COLLECTED);
            this.l.remove(referenceEntry);
            this.m.remove(referenceEntry);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(ReferenceEntry<K, V> referenceEntry, int i2) {
            lock();
            try {
                int i3 = this.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry<K, V> referenceEntry2 = (ReferenceEntry) atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2; referenceEntry3 != null; referenceEntry3 = referenceEntry3.b()) {
                    if (referenceEntry3 == referenceEntry) {
                        this.d++;
                        int i4 = this.b - 1;
                        atomicReferenceArray.set(length, a(referenceEntry2, referenceEntry3, referenceEntry3.d(), i2, referenceEntry3.a().get(), referenceEntry3.a(), RemovalCause.COLLECTED));
                        this.b = i4;
                        return true;
                    }
                }
                unlock();
                m();
                return false;
            } finally {
                unlock();
                m();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(K k2, int i2, ValueReference<K, V> valueReference) {
            lock();
            try {
                int i3 = this.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (referenceEntry2 != null) {
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i2 || d2 == null || !this.a.f.equivalent(k2, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else if (referenceEntry2.a() == valueReference) {
                        this.d++;
                        int i4 = this.b - 1;
                        atomicReferenceArray.set(length, a(referenceEntry, referenceEntry2, d2, i2, valueReference.get(), valueReference, RemovalCause.COLLECTED));
                        this.b = i4;
                        return true;
                    } else {
                        unlock();
                        if (!isHeldByCurrentThread()) {
                            m();
                        }
                        return false;
                    }
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    m();
                }
                return false;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    m();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(K k2, int i2, LoadingValueReference<K, V> loadingValueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
                int length = (atomicReferenceArray.length() - 1) & i2;
                ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object d2 = referenceEntry2.d();
                    if (referenceEntry2.c() != i2 || d2 == null || !this.a.f.equivalent(k2, d2)) {
                        referenceEntry2 = referenceEntry2.b();
                    } else if (referenceEntry2.a() == loadingValueReference) {
                        if (loadingValueReference.d()) {
                            referenceEntry2.a(loadingValueReference.g());
                        } else {
                            atomicReferenceArray.set(length, b(referenceEntry, referenceEntry2));
                        }
                        return true;
                    }
                }
                unlock();
                m();
                return false;
            } finally {
                unlock();
                m();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        @VisibleForTesting
        public boolean a(ReferenceEntry<K, V> referenceEntry, int i2, RemovalCause removalCause) {
            int i3 = this.b;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.f;
            int length = (atomicReferenceArray.length() - 1) & i2;
            ReferenceEntry<K, V> referenceEntry2 = (ReferenceEntry) atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2; referenceEntry3 != null; referenceEntry3 = referenceEntry3.b()) {
                if (referenceEntry3 == referenceEntry) {
                    this.d++;
                    int i4 = this.b - 1;
                    atomicReferenceArray.set(length, a(referenceEntry2, referenceEntry3, referenceEntry3.d(), i2, referenceEntry3.a().get(), referenceEntry3.a(), removalCause));
                    this.b = i4;
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void l() {
            if ((this.k.incrementAndGet() & 63) == 0) {
                n();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void c(long j2) {
            d(j2);
        }

        /* access modifiers changed from: 0000 */
        public void m() {
            o();
        }

        /* access modifiers changed from: 0000 */
        public void n() {
            d(this.a.q.read());
            o();
        }

        /* access modifiers changed from: 0000 */
        public void d(long j2) {
            if (tryLock()) {
                try {
                    b();
                    b(j2);
                    this.k.set(0);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void o() {
            if (!isHeldByCurrentThread()) {
                this.a.r();
            }
        }
    }

    static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();

        StrongAccessEntry(K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public long e() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> f() {
            return this.b;
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> g() {
            return this.c;
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }
    }

    static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();
        volatile long d = Long.MAX_VALUE;
        ReferenceEntry<K, V> e = LocalCache.p();
        ReferenceEntry<K, V> f = LocalCache.p();

        StrongAccessWriteEntry(K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public long e() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> f() {
            return this.b;
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> g() {
            return this.c;
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }

        public long h() {
            return this.d;
        }

        public void b(long j) {
            this.d = j;
        }

        public ReferenceEntry<K, V> i() {
            return this.e;
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            this.e = referenceEntry;
        }

        public ReferenceEntry<K, V> j() {
            return this.f;
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            this.f = referenceEntry;
        }
    }

    static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        final K g;
        final int h;
        final ReferenceEntry<K, V> i;
        volatile ValueReference<K, V> j = LocalCache.o();

        StrongEntry(K k, int i2, @Nullable ReferenceEntry<K, V> referenceEntry) {
            this.g = k;
            this.h = i2;
            this.i = referenceEntry;
        }

        public K d() {
            return this.g;
        }

        public ValueReference<K, V> a() {
            return this.j;
        }

        public void a(ValueReference<K, V> valueReference) {
            this.j = valueReference;
        }

        public int c() {
            return this.h;
        }

        public ReferenceEntry<K, V> b() {
            return this.i;
        }
    }

    static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();

        StrongWriteEntry(K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public long h() {
            return this.a;
        }

        public void b(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> i() {
            return this.b;
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> j() {
            return this.c;
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }
    }

    interface ValueReference<K, V> {
        int a();

        ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry);

        void a(@Nullable V v);

        @Nullable
        ReferenceEntry<K, V> b();

        boolean c();

        boolean d();

        V e();

        @Nullable
        V get();
    }

    static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();

        WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public long e() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> f() {
            return this.b;
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> g() {
            return this.c;
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }
    }

    static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();
        volatile long d = Long.MAX_VALUE;
        ReferenceEntry<K, V> e = LocalCache.p();
        ReferenceEntry<K, V> f = LocalCache.p();

        WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public long e() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> f() {
            return this.b;
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> g() {
            return this.c;
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }

        public long h() {
            return this.d;
        }

        public void b(long j) {
            this.d = j;
        }

        public ReferenceEntry<K, V> i() {
            return this.e;
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            this.e = referenceEntry;
        }

        public ReferenceEntry<K, V> j() {
            return this.f;
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            this.f = referenceEntry;
        }
    }

    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        final int g;
        final ReferenceEntry<K, V> h;
        volatile ValueReference<K, V> i = LocalCache.o();

        WeakEntry(ReferenceQueue<K> referenceQueue, K k, int i2, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(k, referenceQueue);
            this.g = i2;
            this.h = referenceEntry;
        }

        public K d() {
            return get();
        }

        public long e() {
            throw new UnsupportedOperationException();
        }

        public void a(long j) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> f() {
            throw new UnsupportedOperationException();
        }

        public void a(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> g() {
            throw new UnsupportedOperationException();
        }

        public void b(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long h() {
            throw new UnsupportedOperationException();
        }

        public void b(long j) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> i() {
            throw new UnsupportedOperationException();
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> j() {
            throw new UnsupportedOperationException();
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ValueReference<K, V> a() {
            return this.i;
        }

        public void a(ValueReference<K, V> valueReference) {
            this.i = valueReference;
        }

        public int c() {
            return this.g;
        }

        public ReferenceEntry<K, V> b() {
            return this.h;
        }
    }

    static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long a = Long.MAX_VALUE;
        ReferenceEntry<K, V> b = LocalCache.p();
        ReferenceEntry<K, V> c = LocalCache.p();

        WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public long h() {
            return this.a;
        }

        public void b(long j) {
            this.a = j;
        }

        public ReferenceEntry<K, V> i() {
            return this.b;
        }

        public void c(ReferenceEntry<K, V> referenceEntry) {
            this.b = referenceEntry;
        }

        public ReferenceEntry<K, V> j() {
            return this.c;
        }

        public void d(ReferenceEntry<K, V> referenceEntry) {
            this.c = referenceEntry;
        }
    }

    final class WriteThroughEntry implements Entry<K, V> {
        final K a;
        V b;

        WriteThroughEntry(K k, V v) {
            this.a = k;
            this.b = v;
        }

        public K getKey() {
            return this.a;
        }

        public V getValue() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.a.equals(entry.getKey()) && this.b.equals(entry.getValue())) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public V setValue(V v) {
            V put = LocalCache.this.put(this.a, v);
            this.b = v;
            return put;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append("=");
            sb.append(getValue());
            return sb.toString();
        }
    }

    abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        @Weak
        final ConcurrentMap<?, ?> a;

        AbstractCacheSet(ConcurrentMap<?, ?> concurrentMap) {
            this.a = concurrentMap;
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

        public Object[] toArray() {
            return LocalCache.b((Collection<E>) this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return LocalCache.b((Collection<E>) this).toArray(eArr);
        }
    }

    static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> a = new AbstractReferenceEntry<K, V>() {
            ReferenceEntry<K, V> a = this;
            ReferenceEntry<K, V> b = this;

            public void a(long j) {
            }

            public long e() {
                return Long.MAX_VALUE;
            }

            public ReferenceEntry<K, V> f() {
                return this.a;
            }

            public void a(ReferenceEntry<K, V> referenceEntry) {
                this.a = referenceEntry;
            }

            public ReferenceEntry<K, V> g() {
                return this.b;
            }

            public void b(ReferenceEntry<K, V> referenceEntry) {
                this.b = referenceEntry;
            }
        };

        AccessQueue() {
        }

        /* renamed from: a */
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.a(referenceEntry.g(), referenceEntry.f());
            LocalCache.a(this.a.g(), referenceEntry);
            LocalCache.a(referenceEntry, this.a);
            return true;
        }

        /* renamed from: a */
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> f = this.a.f();
            if (f == this.a) {
                return null;
            }
            return f;
        }

        /* renamed from: b */
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> f = this.a.f();
            if (f == this.a) {
                return null;
            }
            remove(f);
            return f;
        }

        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry g = referenceEntry.g();
            ReferenceEntry f = referenceEntry.f();
            LocalCache.a(g, f);
            LocalCache.b(referenceEntry);
            return f != NullEntry.INSTANCE;
        }

        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).f() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.a.f() == this.a;
        }

        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> f = this.a.f(); f != this.a; f = f.f()) {
                i++;
            }
            return i;
        }

        public void clear() {
            ReferenceEntry<K, V> f = this.a.f();
            while (f != this.a) {
                ReferenceEntry f2 = f.f();
                LocalCache.b(f);
                f = f2;
            }
            this.a.a(this.a);
            this.a.b(this.a);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> f = referenceEntry.f();
                    if (f == AccessQueue.this.a) {
                        return null;
                    }
                    return f;
                }
            };
        }
    }

    final class EntryIterator extends HashIterator<Entry<K, V>> {
        EntryIterator() {
            super();
        }

        /* renamed from: a */
        public Entry<K, V> next() {
            return e();
        }
    }

    final class EntrySet extends AbstractCacheSet<Entry<K, V>> {
        EntrySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            if (key == null) {
                return false;
            }
            Object obj2 = LocalCache.this.get(key);
            if (obj2 != null && LocalCache.this.g.equivalent(entry.getValue(), obj2)) {
                z = true;
            }
            return z;
        }

        public boolean remove(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            if (key != null && LocalCache.this.remove(key, entry.getValue())) {
                z = true;
            }
            return z;
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {
        int b;
        int c = -1;
        Segment<K, V> d;
        AtomicReferenceArray<ReferenceEntry<K, V>> e;
        ReferenceEntry<K, V> f;
        WriteThroughEntry g;
        WriteThroughEntry h;

        HashIterator() {
            this.b = LocalCache.this.d.length - 1;
            b();
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            this.g = null;
            if (!c() && !d()) {
                while (this.b >= 0) {
                    Segment<K, V>[] segmentArr = LocalCache.this.d;
                    int i2 = this.b;
                    this.b = i2 - 1;
                    this.d = segmentArr[i2];
                    if (this.d.b != 0) {
                        this.e = this.d.f;
                        this.c = this.e.length() - 1;
                        if (d()) {
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            if (this.f != null) {
                do {
                    this.f = this.f.b();
                    if (this.f != null) {
                    }
                } while (!a(this.f));
                return true;
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            while (this.c >= 0) {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.e;
                int i2 = this.c;
                this.c = i2 - 1;
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i2);
                this.f = referenceEntry;
                if (referenceEntry != null && (a(this.f) || c())) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(ReferenceEntry<K, V> referenceEntry) {
            boolean z;
            try {
                long read = LocalCache.this.q.read();
                Object d2 = referenceEntry.d();
                Object a = LocalCache.this.a(referenceEntry, read);
                if (a != null) {
                    this.g = new WriteThroughEntry<>(d2, a);
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } finally {
                this.d.l();
            }
        }

        public boolean hasNext() {
            return this.g != null;
        }

        /* access modifiers changed from: 0000 */
        public WriteThroughEntry e() {
            if (this.g == null) {
                throw new NoSuchElementException();
            }
            this.h = this.g;
            b();
            return this.h;
        }

        public void remove() {
            Preconditions.checkState(this.h != null);
            LocalCache.this.remove(this.h.getKey());
            this.h = null;
        }
    }

    final class KeyIterator extends HashIterator<K> {
        KeyIterator() {
            super();
        }

        public K next() {
            return e().getKey();
        }
    }

    final class KeySet extends AbstractCacheSet<K> {
        KeySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        public boolean contains(Object obj) {
            return this.a.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return this.a.remove(obj) != null;
        }
    }

    static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        transient LoadingCache<K, V> a;

        LoadingSerializationProxy(LocalCache<K, V> localCache) {
            super(localCache);
        }

        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.a = a().build(this.m);
        }

        public V get(K k) {
            return this.a.get(k);
        }

        public V getUnchecked(K k) {
            return this.a.getUnchecked(k);
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) {
            return this.a.getAll(iterable);
        }

        public final V apply(K k) {
            return this.a.apply(k);
        }

        public void refresh(K k) {
            this.a.refresh(k);
        }

        private Object readResolve() {
            return this.a;
        }
    }

    static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super();
        }

        public V get(K k) {
            return this.a.c(k);
        }

        public V getUnchecked(K k) {
            try {
                return get(k);
            } catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) {
            return this.a.b(iterable);
        }

        public void refresh(K k) {
            this.a.d(k);
        }

        public final V apply(K k) {
            return getUnchecked(k);
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new LoadingSerializationProxy(this.a);
        }
    }

    static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final Strength b;
        final Strength c;
        final Equivalence<Object> d;
        final Equivalence<Object> e;
        final long f;
        final long g;
        final long h;
        final Weigher<K, V> i;
        final int j;
        final RemovalListener<? super K, ? super V> k;
        final Ticker l;
        final CacheLoader<? super K, V> m;
        transient Cache<K, V> n;

        ManualSerializationProxy(LocalCache<K, V> localCache) {
            LocalCache<K, V> localCache2 = localCache;
            Strength strength = localCache2.h;
            Strength strength2 = localCache2.i;
            Equivalence<Object> equivalence = localCache2.f;
            Equivalence<Object> equivalence2 = localCache2.g;
            long j2 = localCache2.m;
            long j3 = localCache2.l;
            long j4 = localCache2.j;
            Weigher<K, V> weigher = localCache2.k;
            int i2 = localCache2.e;
            RemovalListener<K, V> removalListener = localCache2.p;
            Ticker ticker = localCache2.q;
            CacheLoader<? super K, V> cacheLoader = localCache2.t;
            this(strength, strength2, equivalence, equivalence2, j2, j3, j4, weigher, i2, removalListener, ticker, cacheLoader);
        }

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j2, long j3, long j4, Weigher<K, V> weigher, int i2, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            this.b = strength;
            this.c = strength2;
            this.d = equivalence;
            this.e = equivalence2;
            this.f = j2;
            this.g = j3;
            this.h = j4;
            this.i = weigher;
            this.j = i2;
            this.k = removalListener;
            if (ticker == Ticker.systemTicker() || ticker == CacheBuilder.d) {
                ticker = null;
            }
            this.l = ticker;
            this.m = cacheLoader;
        }

        /* access modifiers changed from: 0000 */
        public CacheBuilder<K, V> a() {
            CacheBuilder<K, V> removalListener = CacheBuilder.newBuilder().a(this.b).b(this.c).a(this.d).b(this.e).concurrencyLevel(this.j).removalListener(this.k);
            removalListener.e = false;
            if (this.f > 0) {
                removalListener.expireAfterWrite(this.f, TimeUnit.NANOSECONDS);
            }
            if (this.g > 0) {
                removalListener.expireAfterAccess(this.g, TimeUnit.NANOSECONDS);
            }
            if (this.i != OneWeigher.INSTANCE) {
                removalListener.weigher(this.i);
                if (this.h != -1) {
                    removalListener.maximumWeight(this.h);
                }
            } else if (this.h != -1) {
                removalListener.maximumSize(this.h);
            }
            if (this.l != null) {
                removalListener.ticker(this.l);
            }
            return removalListener;
        }

        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.n = a().build();
        }

        private Object readResolve() {
            return this.n;
        }

        /* access modifiers changed from: protected */
        public Cache<K, V> delegate() {
            return this.n;
        }
    }

    enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        public ValueReference<Object, Object> a() {
            return null;
        }

        public void a(long j) {
        }

        public void a(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public void a(ValueReference<Object, Object> valueReference) {
        }

        public ReferenceEntry<Object, Object> b() {
            return null;
        }

        public void b(long j) {
        }

        public void b(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public int c() {
            return 0;
        }

        public void c(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public Object d() {
            return null;
        }

        public void d(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public long e() {
            return 0;
        }

        public ReferenceEntry<Object, Object> f() {
            return this;
        }

        public ReferenceEntry<Object, Object> g() {
            return this;
        }

        public long h() {
            return 0;
        }

        public ReferenceEntry<Object, Object> i() {
            return this;
        }

        public ReferenceEntry<Object, Object> j() {
            return this;
        }
    }

    static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> a;

        public int a() {
            return 1;
        }

        public void a(V v) {
        }

        public boolean c() {
            return false;
        }

        public boolean d() {
            return true;
        }

        SoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.a = referenceEntry;
        }

        public ReferenceEntry<K, V> b() {
            return this.a;
        }

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference(referenceQueue, v, referenceEntry);
        }

        public V e() {
            return get();
        }
    }

    enum Strength {
        STRONG {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new StrongValueReference(v) : new WeightedStrongValueReference(v, i);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> a() {
                return Equivalence.equals();
            }
        },
        SOFT {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new SoftValueReference(segment.i, v, referenceEntry) : new WeightedSoftValueReference(segment.i, v, referenceEntry, i);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> a() {
                return Equivalence.identity();
            }
        },
        WEAK {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                return i == 1 ? new WeakValueReference(segment.i, v, referenceEntry) : new WeightedWeakValueReference(segment.i, v, referenceEntry, i);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> a() {
                return Equivalence.identity();
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract Equivalence<Object> a();

        /* access modifiers changed from: 0000 */
        public abstract <K, V> ValueReference<K, V> a(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i);
    }

    static class StrongValueReference<K, V> implements ValueReference<K, V> {
        final V a;

        public int a() {
            return 1;
        }

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public void a(V v) {
        }

        public ReferenceEntry<K, V> b() {
            return null;
        }

        public boolean c() {
            return false;
        }

        public boolean d() {
            return true;
        }

        StrongValueReference(V v) {
            this.a = v;
        }

        public V get() {
            return this.a;
        }

        public V e() {
            return get();
        }
    }

    final class ValueIterator extends HashIterator<V> {
        ValueIterator() {
            super();
        }

        public V next() {
            return e().getValue();
        }
    }

    final class Values extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> b;

        Values(ConcurrentMap<?, ?> concurrentMap) {
            this.b = concurrentMap;
        }

        public int size() {
            return this.b.size();
        }

        public boolean isEmpty() {
            return this.b.isEmpty();
        }

        public void clear() {
            this.b.clear();
        }

        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public boolean contains(Object obj) {
            return this.b.containsValue(obj);
        }

        public Object[] toArray() {
            return LocalCache.b((Collection<E>) this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return LocalCache.b((Collection<E>) this).toArray(eArr);
        }
    }

    static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> a;

        public int a() {
            return 1;
        }

        public void a(V v) {
        }

        public boolean c() {
            return false;
        }

        public boolean d() {
            return true;
        }

        WeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.a = referenceEntry;
        }

        public ReferenceEntry<K, V> b() {
            return this.a;
        }

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference(referenceQueue, v, referenceEntry);
        }

        public V e() {
            return get();
        }
    }

    static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        final int b;

        WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.b = i;
        }

        public int a() {
            return this.b;
        }

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, v, referenceEntry, this.b);
        }
    }

    static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        final int b;

        WeightedStrongValueReference(V v, int i) {
            super(v);
            this.b = i;
        }

        public int a() {
            return this.b;
        }
    }

    static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        final int b;

        WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.b = i;
        }

        public int a() {
            return this.b;
        }

        public ValueReference<K, V> a(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, v, referenceEntry, this.b);
        }
    }

    static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> a = new AbstractReferenceEntry<K, V>() {
            ReferenceEntry<K, V> a = this;
            ReferenceEntry<K, V> b = this;

            public void b(long j) {
            }

            public long h() {
                return Long.MAX_VALUE;
            }

            public ReferenceEntry<K, V> i() {
                return this.a;
            }

            public void c(ReferenceEntry<K, V> referenceEntry) {
                this.a = referenceEntry;
            }

            public ReferenceEntry<K, V> j() {
                return this.b;
            }

            public void d(ReferenceEntry<K, V> referenceEntry) {
                this.b = referenceEntry;
            }
        };

        WriteQueue() {
        }

        /* renamed from: a */
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.b(referenceEntry.j(), referenceEntry.i());
            LocalCache.b(this.a.j(), referenceEntry);
            LocalCache.b(referenceEntry, this.a);
            return true;
        }

        /* renamed from: a */
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> i = this.a.i();
            if (i == this.a) {
                return null;
            }
            return i;
        }

        /* renamed from: b */
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> i = this.a.i();
            if (i == this.a) {
                return null;
            }
            remove(i);
            return i;
        }

        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry j = referenceEntry.j();
            ReferenceEntry i = referenceEntry.i();
            LocalCache.b(j, i);
            LocalCache.c(referenceEntry);
            return i != NullEntry.INSTANCE;
        }

        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).i() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.a.i() == this.a;
        }

        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> i2 = this.a.i(); i2 != this.a; i2 = i2.i()) {
                i++;
            }
            return i;
        }

        public void clear() {
            ReferenceEntry<K, V> i = this.a.i();
            while (i != this.a) {
                ReferenceEntry i2 = i.i();
                LocalCache.c(i);
                i = i2;
            }
            this.a.c(this.a);
            this.a.d(this.a);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> i = referenceEntry.i();
                    if (i == WriteQueue.this.a) {
                        return null;
                    }
                    return i;
                }
            };
        }
    }

    static int a(int i2) {
        int i3 = i2 + ((i2 << 15) ^ -12931);
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, @Nullable CacheLoader<? super K, V> cacheLoader) {
        this.e = Math.min(cacheBuilder.e(), 65536);
        this.h = cacheBuilder.h();
        this.i = cacheBuilder.i();
        this.f = cacheBuilder.b();
        this.g = cacheBuilder.c();
        this.j = cacheBuilder.f();
        this.k = cacheBuilder.g();
        this.l = cacheBuilder.k();
        this.m = cacheBuilder.j();
        this.n = cacheBuilder.l();
        this.p = cacheBuilder.m();
        this.o = this.p == NullListener.INSTANCE ? q() : new ConcurrentLinkedQueue<>();
        this.q = cacheBuilder.a(j());
        this.r = EntryFactory.a(this.h, l(), k());
        this.s = (StatsCounter) cacheBuilder.n().get();
        this.t = cacheLoader;
        int min = Math.min(cacheBuilder.d(), 1073741824);
        if (a() && !b()) {
            min = Math.min(min, (int) this.j);
        }
        int i2 = 0;
        int i3 = 1;
        int i4 = 1;
        int i5 = 0;
        while (i4 < this.e && (!a() || ((long) (i4 * 20)) <= this.j)) {
            i5++;
            i4 <<= 1;
        }
        this.c = 32 - i5;
        this.b = i4 - 1;
        this.d = c(i4);
        int i6 = min / i4;
        if (i6 * i4 < min) {
            i6++;
        }
        while (i3 < i6) {
            i3 <<= 1;
        }
        if (a()) {
            long j2 = (long) i4;
            long j3 = (this.j / j2) + 1;
            long j4 = this.j % j2;
            while (i2 < this.d.length) {
                if (((long) i2) == j4) {
                    j3--;
                }
                this.d[i2] = a(i3, j3, (StatsCounter) cacheBuilder.n().get());
                i2++;
            }
            return;
        }
        while (i2 < this.d.length) {
            this.d[i2] = a(i3, -1, (StatsCounter) cacheBuilder.n().get());
            i2++;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.j >= 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.k != OneWeigher.INSTANCE;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.m > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return this.l > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return this.n > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        return d() || a();
    }

    /* access modifiers changed from: 0000 */
    public boolean g() {
        return c();
    }

    /* access modifiers changed from: 0000 */
    public boolean h() {
        return c() || e();
    }

    /* access modifiers changed from: 0000 */
    public boolean i() {
        return d();
    }

    /* access modifiers changed from: 0000 */
    public boolean j() {
        return h() || i();
    }

    /* access modifiers changed from: 0000 */
    public boolean k() {
        return g() || h();
    }

    /* access modifiers changed from: 0000 */
    public boolean l() {
        return f() || i();
    }

    /* access modifiers changed from: 0000 */
    public boolean m() {
        return this.h != Strength.STRONG;
    }

    /* access modifiers changed from: 0000 */
    public boolean n() {
        return this.i != Strength.STRONG;
    }

    static <K, V> ValueReference<K, V> o() {
        return u;
    }

    static <K, V> ReferenceEntry<K, V> p() {
        return NullEntry.INSTANCE;
    }

    static <E> Queue<E> q() {
        return v;
    }

    /* access modifiers changed from: 0000 */
    public int a(@Nullable Object obj) {
        return a(this.f.hash(obj));
    }

    /* access modifiers changed from: 0000 */
    public void a(ValueReference<K, V> valueReference) {
        ReferenceEntry b2 = valueReference.b();
        int c2 = b2.c();
        b(c2).a(b2.d(), c2, valueReference);
    }

    /* access modifiers changed from: 0000 */
    public void a(ReferenceEntry<K, V> referenceEntry) {
        int c2 = referenceEntry.c();
        b(c2).a(referenceEntry, c2);
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V> b(int i2) {
        return this.d[(i2 >>> this.c) & this.b];
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V> a(int i2, long j2, StatsCounter statsCounter) {
        Segment segment = new Segment(this, i2, j2, statsCounter);
        return segment;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public V a(ReferenceEntry<K, V> referenceEntry, long j2) {
        if (referenceEntry.d() == null) {
            return null;
        }
        V v2 = referenceEntry.a().get();
        if (v2 != null && !b(referenceEntry, j2)) {
            return v2;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(ReferenceEntry<K, V> referenceEntry, long j2) {
        Preconditions.checkNotNull(referenceEntry);
        if (d() && j2 - referenceEntry.e() >= this.l) {
            return true;
        }
        if (!c() || j2 - referenceEntry.h() < this.m) {
            return false;
        }
        return true;
    }

    static <K, V> void a(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.a(referenceEntry2);
        referenceEntry2.b(referenceEntry);
    }

    static <K, V> void b(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry p2 = p();
        referenceEntry.a(p2);
        referenceEntry.b(p2);
    }

    static <K, V> void b(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.c(referenceEntry2);
        referenceEntry2.d(referenceEntry);
    }

    static <K, V> void c(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry p2 = p();
        referenceEntry.c(p2);
        referenceEntry.d(p2);
    }

    /* access modifiers changed from: 0000 */
    public void r() {
        while (true) {
            RemovalNotification removalNotification = (RemovalNotification) this.o.poll();
            if (removalNotification != null) {
                try {
                    this.p.onRemoval(removalNotification);
                } catch (Throwable th) {
                    a.log(Level.WARNING, "Exception thrown by removal listener", th);
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final Segment<K, V>[] c(int i2) {
        return new Segment[i2];
    }

    public void s() {
        for (Segment<K, V> n2 : this.d) {
            n2.n();
        }
    }

    public boolean isEmpty() {
        Segment<K, V>[] segmentArr = this.d;
        long j2 = 0;
        int i2 = 0;
        while (i2 < segmentArr.length) {
            if (segmentArr[i2].b != 0) {
                return false;
            }
            i2++;
            j2 += (long) segmentArr[i2].d;
        }
        if (j2 != 0) {
            int i3 = 0;
            while (i3 < segmentArr.length) {
                if (segmentArr[i3].b != 0) {
                    return false;
                }
                i3++;
                j2 -= (long) segmentArr[i3].d;
            }
            if (j2 != 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public long t() {
        Segment<K, V>[] segmentArr = this.d;
        long j2 = 0;
        int i2 = 0;
        while (i2 < segmentArr.length) {
            i2++;
            j2 += (long) Math.max(0, segmentArr[i2].b);
        }
        return j2;
    }

    public int size() {
        return Ints.saturatedCast(t());
    }

    @Nullable
    public V get(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).b(obj, a2);
    }

    @Nullable
    public V b(Object obj) {
        int a2 = a(Preconditions.checkNotNull(obj));
        V b2 = b(a2).b(obj, a2);
        if (b2 == null) {
            this.s.recordMisses(1);
        } else {
            this.s.recordHits(1);
        }
        return b2;
    }

    @Nullable
    public V getOrDefault(@Nullable Object obj, @Nullable V v2) {
        V v3 = get(obj);
        return v3 != null ? v3 : v2;
    }

    /* access modifiers changed from: 0000 */
    public V a(K k2, CacheLoader<? super K, V> cacheLoader) {
        int a2 = a(Preconditions.checkNotNull(k2));
        return b(a2).a(k2, a2, cacheLoader);
    }

    /* access modifiers changed from: 0000 */
    public V c(K k2) {
        return a(k2, this.t);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableMap<K, V> a(Iterable<?> iterable) {
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        int i2 = 0;
        int i3 = 0;
        for (Object next : iterable) {
            Object obj = get(next);
            if (obj == null) {
                i3++;
            } else {
                newLinkedHashMap.put(next, obj);
                i2++;
            }
        }
        this.s.recordHits(i2);
        this.s.recordMisses(i3);
        return ImmutableMap.copyOf((Map<? extends K, ? extends V>) newLinkedHashMap);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:21|22|(2:25|23)|38) */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r8 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0073, code lost:
        if (r8.hasNext() != false) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0075, code lost:
        r1 = r8.next();
        r2 = r2 - 1;
        r0.put(r1, a((K) r1, r7.t));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x006b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.common.collect.ImmutableMap<K, V> b(java.lang.Iterable<? extends K> r8) {
        /*
            r7 = this;
            java.util.LinkedHashMap r0 = com.google.common.collect.Maps.newLinkedHashMap()
            java.util.LinkedHashSet r1 = com.google.common.collect.Sets.newLinkedHashSet()
            java.util.Iterator r8 = r8.iterator()
            r2 = 0
            r3 = 0
        L_0x000e:
            boolean r4 = r8.hasNext()
            if (r4 == 0) goto L_0x0030
            java.lang.Object r4 = r8.next()
            java.lang.Object r5 = r7.get(r4)
            boolean r6 = r0.containsKey(r4)
            if (r6 != 0) goto L_0x000e
            r0.put(r4, r5)
            if (r5 != 0) goto L_0x002d
            int r2 = r2 + 1
            r1.add(r4)
            goto L_0x000e
        L_0x002d:
            int r3 = r3 + 1
            goto L_0x000e
        L_0x0030:
            boolean r8 = r1.isEmpty()     // Catch:{ all -> 0x0094 }
            if (r8 != 0) goto L_0x0085
            com.google.common.cache.CacheLoader<? super K, V> r8 = r7.t     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.util.Map r8 = r7.a(r1, r8)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.util.Iterator r4 = r1.iterator()     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
        L_0x0040:
            boolean r5 = r4.hasNext()     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            if (r5 == 0) goto L_0x0085
            java.lang.Object r5 = r4.next()     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.lang.Object r6 = r8.get(r5)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            if (r6 != 0) goto L_0x0067
            com.google.common.cache.CacheLoader$InvalidCacheLoadException r8 = new com.google.common.cache.CacheLoader$InvalidCacheLoadException     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            r4.<init>()     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.lang.String r6 = "loadAll failed to return a value for "
            r4.append(r6)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            r4.append(r5)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            java.lang.String r4 = r4.toString()     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            r8.<init>(r4)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            throw r8     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
        L_0x0067:
            r0.put(r5, r6)     // Catch:{ UnsupportedLoadingOperationException -> 0x006b }
            goto L_0x0040
        L_0x006b:
            java.util.Iterator r8 = r1.iterator()     // Catch:{ all -> 0x0094 }
        L_0x006f:
            boolean r1 = r8.hasNext()     // Catch:{ all -> 0x0094 }
            if (r1 == 0) goto L_0x0085
            java.lang.Object r1 = r8.next()     // Catch:{ all -> 0x0094 }
            int r2 = r2 + -1
            com.google.common.cache.CacheLoader<? super K, V> r4 = r7.t     // Catch:{ all -> 0x0094 }
            java.lang.Object r4 = r7.a((K) r1, r4)     // Catch:{ all -> 0x0094 }
            r0.put(r1, r4)     // Catch:{ all -> 0x0094 }
            goto L_0x006f
        L_0x0085:
            com.google.common.collect.ImmutableMap r8 = com.google.common.collect.ImmutableMap.copyOf(r0)     // Catch:{ all -> 0x0094 }
            com.google.common.cache.AbstractCache$StatsCounter r0 = r7.s
            r0.recordHits(r3)
            com.google.common.cache.AbstractCache$StatsCounter r0 = r7.s
            r0.recordMisses(r2)
            return r8
        L_0x0094:
            r8 = move-exception
            com.google.common.cache.AbstractCache$StatsCounter r0 = r7.s
            r0.recordHits(r3)
            com.google.common.cache.AbstractCache$StatsCounter r0 = r7.s
            r0.recordMisses(r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.b(java.lang.Iterable):com.google.common.collect.ImmutableMap");
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Map<K, V> a(Set<? extends K> set, CacheLoader<? super K, V> cacheLoader) {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(set);
        Stopwatch createStarted = Stopwatch.createStarted();
        boolean z = false;
        try {
            Map<K, V> loadAll = cacheLoader.loadAll(set);
            if (loadAll == null) {
                this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
                StringBuilder sb = new StringBuilder();
                sb.append(cacheLoader);
                sb.append(" returned null map from loadAll");
                throw new InvalidCacheLoadException(sb.toString());
            }
            createStarted.stop();
            for (Entry entry : loadAll.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key == null || value == null) {
                    z = true;
                } else {
                    put(key, value);
                }
            }
            if (z) {
                this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(cacheLoader);
                sb2.append(" returned null keys or values from loadAll");
                throw new InvalidCacheLoadException(sb2.toString());
            }
            this.s.recordLoadSuccess(createStarted.elapsed(TimeUnit.NANOSECONDS));
            return loadAll;
        } catch (UnsupportedLoadingOperationException e2) {
            throw e2;
        } catch (InterruptedException e3) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(e3);
        } catch (RuntimeException e4) {
            throw new UncheckedExecutionException((Throwable) e4);
        } catch (Exception e5) {
            throw new ExecutionException(e5);
        } catch (Error e6) {
            throw new ExecutionError(e6);
        } catch (Throwable th) {
            th = th;
            z = true;
        }
        if (!z) {
            this.s.recordLoadException(createStarted.elapsed(TimeUnit.NANOSECONDS));
        }
        throw th;
    }

    /* access modifiers changed from: 0000 */
    public void d(K k2) {
        int a2 = a(Preconditions.checkNotNull(k2));
        b(a2).a(k2, a2, this.t, false);
    }

    public boolean containsKey(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).c(obj, a2);
    }

    public boolean containsValue(@Nullable Object obj) {
        long j2;
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        long read = this.q.read();
        Segment<K, V>[] segmentArr = this.d;
        long j3 = -1;
        int i2 = 0;
        while (i2 < 3) {
            int length = segmentArr.length;
            long j4 = 0;
            int i3 = 0;
            while (i3 < length) {
                Segment<K, V> segment = segmentArr[i3];
                int i4 = segment.b;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.f;
                for (int i5 = 0; i5 < atomicReferenceArray.length(); i5++) {
                    ReferenceEntry referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i5);
                    while (referenceEntry != null) {
                        Segment<K, V>[] segmentArr2 = segmentArr;
                        Object c2 = segment.c(referenceEntry, read);
                        if (c2 != null) {
                            j2 = read;
                            if (this.g.equivalent(obj2, c2)) {
                                return true;
                            }
                        } else {
                            j2 = read;
                        }
                        referenceEntry = referenceEntry.b();
                        segmentArr = segmentArr2;
                        read = j2;
                    }
                    long j5 = read;
                    Segment<K, V>[] segmentArr3 = segmentArr;
                }
                i3++;
                j4 += (long) segment.d;
                segmentArr = segmentArr;
                read = read;
            }
            long j6 = read;
            Segment<K, V>[] segmentArr4 = segmentArr;
            if (j4 == j3) {
                break;
            }
            i2++;
            j3 = j4;
            segmentArr = segmentArr4;
            read = j6;
        }
        return false;
    }

    public V put(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a((Object) k2);
        return b(a2).a(k2, a2, v2, false);
    }

    public V putIfAbsent(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a((Object) k2);
        return b(a2).a(k2, a2, v2, true);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public V remove(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).d(obj, a2);
    }

    public boolean remove(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).b(obj, a2, obj2);
    }

    public boolean replace(K k2, @Nullable V v2, V v3) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v3);
        if (v2 == null) {
            return false;
        }
        int a2 = a((Object) k2);
        return b(a2).a(k2, a2, v2, v3);
    }

    public V replace(K k2, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        int a2 = a((Object) k2);
        return b(a2).a(k2, a2, v2);
    }

    public void clear() {
        for (Segment<K, V> k2 : this.d) {
            k2.k();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Iterable<?> iterable) {
        for (Object remove : iterable) {
            remove(remove);
        }
    }

    public Set<K> keySet() {
        Set<K> set = this.w;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet(this);
        this.w = keySet;
        return keySet;
    }

    public Collection<V> values() {
        Collection<V> collection = this.x;
        if (collection != null) {
            return collection;
        }
        Values values = new Values(this);
        this.x = values;
        return values;
    }

    @GwtIncompatible
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.y;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet(this);
        this.y = entrySet;
        return entrySet;
    }

    /* access modifiers changed from: private */
    public static <E> ArrayList<E> b(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }
}
