package com.google.common.util.concurrent;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@GwtIncompatible
@Beta
public abstract class Striped<L> {
    private static final Supplier<ReadWriteLock> a = new Supplier<ReadWriteLock>() {
        /* renamed from: a */
        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    };

    static class CompactStriped<L> extends PowerOfTwoStriped<L> {
        private final Object[] a;

        private CompactStriped(int i, Supplier<L> supplier) {
            super(i);
            Preconditions.checkArgument(i <= 1073741824, "Stripes must be <= 2^30)");
            this.a = new Object[(this.d + 1)];
            for (int i2 = 0; i2 < this.a.length; i2++) {
                this.a[i2] = supplier.get();
            }
        }

        public L getAt(int i) {
            return this.a[i];
        }

        public int size() {
            return this.a.length;
        }
    }

    @VisibleForTesting
    static class LargeLazyStriped<L> extends PowerOfTwoStriped<L> {
        final ConcurrentMap<Integer, L> a;
        final Supplier<L> b;
        final int c;

        LargeLazyStriped(int i, Supplier<L> supplier) {
            super(i);
            this.c = this.d == -1 ? SubsamplingScaleImageView.TILE_SIZE_AUTO : this.d + 1;
            this.b = supplier;
            this.a = new MapMaker().weakValues().makeMap();
        }

        public L getAt(int i) {
            if (this.c != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i, size());
            }
            L l = this.a.get(Integer.valueOf(i));
            if (l != null) {
                return l;
            }
            Object obj = this.b.get();
            return MoreObjects.firstNonNull(this.a.putIfAbsent(Integer.valueOf(i), obj), obj);
        }

        public int size() {
            return this.c;
        }
    }

    static class PaddedLock extends ReentrantLock {
        PaddedLock() {
            super(false);
        }
    }

    static class PaddedSemaphore extends Semaphore {
        PaddedSemaphore(int i) {
            super(i, false);
        }
    }

    static abstract class PowerOfTwoStriped<L> extends Striped<L> {
        final int d;

        PowerOfTwoStriped(int i) {
            int i2;
            super();
            Preconditions.checkArgument(i > 0, "Stripes must be positive");
            if (i > 1073741824) {
                i2 = -1;
            } else {
                i2 = Striped.c(i) - 1;
            }
            this.d = i2;
        }

        /* access modifiers changed from: 0000 */
        public final int a(Object obj) {
            return Striped.d(obj.hashCode()) & this.d;
        }

        public final L get(Object obj) {
            return getAt(a(obj));
        }
    }

    @VisibleForTesting
    static class SmallLazyStriped<L> extends PowerOfTwoStriped<L> {
        final AtomicReferenceArray<ArrayReference<? extends L>> a;
        final Supplier<L> b;
        final int c;
        final ReferenceQueue<L> e = new ReferenceQueue<>();

        static final class ArrayReference<L> extends WeakReference<L> {
            final int a;

            ArrayReference(L l, int i, ReferenceQueue<L> referenceQueue) {
                super(l, referenceQueue);
                this.a = i;
            }
        }

        SmallLazyStriped(int i, Supplier<L> supplier) {
            super(i);
            this.c = this.d == -1 ? SubsamplingScaleImageView.TILE_SIZE_AUTO : this.d + 1;
            this.a = new AtomicReferenceArray<>(this.c);
            this.b = supplier;
        }

        public L getAt(int i) {
            L l;
            L l2;
            if (this.c != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i, size());
            }
            ArrayReference arrayReference = (ArrayReference) this.a.get(i);
            if (arrayReference == null) {
                l = null;
            } else {
                l = arrayReference.get();
            }
            if (l != null) {
                return l;
            }
            L l3 = this.b.get();
            ArrayReference arrayReference2 = new ArrayReference(l3, i, this.e);
            while (!this.a.compareAndSet(i, arrayReference, arrayReference2)) {
                arrayReference = (ArrayReference) this.a.get(i);
                if (arrayReference == null) {
                    l2 = null;
                    continue;
                } else {
                    l2 = arrayReference.get();
                    continue;
                }
                if (l2 != null) {
                    return l2;
                }
            }
            a();
            return l3;
        }

        private void a() {
            while (true) {
                Reference poll = this.e.poll();
                if (poll != null) {
                    ArrayReference arrayReference = (ArrayReference) poll;
                    this.a.compareAndSet(arrayReference.a, arrayReference, null);
                } else {
                    return;
                }
            }
        }

        public int size() {
            return this.c;
        }
    }

    /* access modifiers changed from: private */
    public static int d(int i) {
        int i2 = i ^ ((i >>> 20) ^ (i >>> 12));
        return (i2 >>> 4) ^ ((i2 >>> 7) ^ i2);
    }

    /* access modifiers changed from: 0000 */
    public abstract int a(Object obj);

    public abstract L get(Object obj);

    public abstract L getAt(int i);

    public abstract int size();

    private Striped() {
    }

    public Iterable<L> bulkGet(Iterable<?> iterable) {
        Object[] array = Iterables.toArray(iterable, Object.class);
        if (array.length == 0) {
            return ImmutableList.of();
        }
        int[] iArr = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            iArr[i] = a(array[i]);
        }
        Arrays.sort(iArr);
        int i2 = iArr[0];
        array[0] = getAt(i2);
        for (int i3 = 1; i3 < array.length; i3++) {
            int i4 = iArr[i3];
            if (i4 == i2) {
                array[i3] = array[i3 - 1];
            } else {
                array[i3] = getAt(i4);
                i2 = i4;
            }
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    public static Striped<Lock> lock(int i) {
        return new CompactStriped(i, new Supplier<Lock>() {
            /* renamed from: a */
            public Lock get() {
                return new PaddedLock();
            }
        });
    }

    public static Striped<Lock> lazyWeakLock(int i) {
        return a(i, new Supplier<Lock>() {
            /* renamed from: a */
            public Lock get() {
                return new ReentrantLock(false);
            }
        });
    }

    private static <L> Striped<L> a(int i, Supplier<L> supplier) {
        return i < 1024 ? new SmallLazyStriped(i, supplier) : new LargeLazyStriped(i, supplier);
    }

    public static Striped<Semaphore> semaphore(int i, final int i2) {
        return new CompactStriped(i, new Supplier<Semaphore>() {
            /* renamed from: a */
            public Semaphore get() {
                return new PaddedSemaphore(i2);
            }
        });
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int i, final int i2) {
        return a(i, new Supplier<Semaphore>() {
            /* renamed from: a */
            public Semaphore get() {
                return new Semaphore(i2, false);
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int i) {
        return new CompactStriped(i, a);
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int i) {
        return a(i, a);
    }

    /* access modifiers changed from: private */
    public static int c(int i) {
        return 1 << IntMath.log2(i, RoundingMode.CEILING);
    }
}
