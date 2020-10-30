package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible(emulated = true)
abstract class AggregateFutureState {
    private static final AtomicHelper c;
    private static final Logger d = Logger.getLogger(AggregateFutureState.class.getName());
    /* access modifiers changed from: private */
    public volatile Set<Throwable> a = null;
    /* access modifiers changed from: private */
    public volatile int b;

    static abstract class AtomicHelper {
        /* access modifiers changed from: 0000 */
        public abstract int a(AggregateFutureState aggregateFutureState);

        /* access modifiers changed from: 0000 */
        public abstract void a(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2);

        private AtomicHelper() {
        }
    }

    static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> a;
        final AtomicIntegerFieldUpdater<AggregateFutureState> b;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            super();
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicIntegerFieldUpdater;
        }

        /* access modifiers changed from: 0000 */
        public void a(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            this.a.compareAndSet(aggregateFutureState, set, set2);
        }

        /* access modifiers changed from: 0000 */
        public int a(AggregateFutureState aggregateFutureState) {
            return this.b.decrementAndGet(aggregateFutureState);
        }
    }

    static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public void a(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            synchronized (aggregateFutureState) {
                if (aggregateFutureState.a == set) {
                    aggregateFutureState.a = set2;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int a(AggregateFutureState aggregateFutureState) {
            int c;
            synchronized (aggregateFutureState) {
                aggregateFutureState.b = aggregateFutureState.b - 1;
                c = aggregateFutureState.b;
            }
            return c;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(Set<Throwable> set);

    static {
        AtomicHelper atomicHelper;
        try {
            atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "a"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "b"));
        } catch (Throwable th) {
            d.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
            atomicHelper = new SynchronizedAtomicHelper();
        }
        c = atomicHelper;
    }

    AggregateFutureState(int i) {
        this.b = i;
    }

    /* access modifiers changed from: 0000 */
    public final Set<Throwable> d() {
        Set<Throwable> set = this.a;
        if (set != null) {
            return set;
        }
        Set newConcurrentHashSet = Sets.newConcurrentHashSet();
        a(newConcurrentHashSet);
        c.a(this, null, newConcurrentHashSet);
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public final int e() {
        return c.a(this);
    }
}
