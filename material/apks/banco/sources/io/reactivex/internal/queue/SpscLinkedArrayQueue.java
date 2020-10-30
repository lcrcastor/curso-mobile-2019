package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    static final int a = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    private static final Object j = new Object();
    final AtomicLong b = new AtomicLong();
    int c;
    long d;
    final int e;
    AtomicReferenceArray<Object> f;
    final int g;
    AtomicReferenceArray<Object> h;
    final AtomicLong i = new AtomicLong();

    private static int b(int i2) {
        return i2;
    }

    public SpscLinkedArrayQueue(int i2) {
        int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i2));
        int i3 = roundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(roundToPowerOfTwo + 1);
        this.f = atomicReferenceArray;
        this.e = i3;
        a(roundToPowerOfTwo);
        this.h = atomicReferenceArray;
        this.g = i3;
        this.d = (long) (i3 - 1);
        a(0);
    }

    public boolean offer(T t) {
        if (t == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<Object> atomicReferenceArray = this.f;
        long c2 = c();
        int i2 = this.e;
        int a2 = a(c2, i2);
        if (c2 < this.d) {
            return a(atomicReferenceArray, t, c2, a2);
        }
        long j2 = c2 + ((long) this.c);
        if (a(atomicReferenceArray, a(j2, i2)) == null) {
            this.d = j2 - 1;
            return a(atomicReferenceArray, t, c2, a2);
        } else if (a(atomicReferenceArray, a(c2 + 1, i2)) == null) {
            return a(atomicReferenceArray, t, c2, a2);
        } else {
            a(atomicReferenceArray, c2, a2, t, (long) i2);
            return true;
        }
    }

    private boolean a(AtomicReferenceArray<Object> atomicReferenceArray, T t, long j2, int i2) {
        a(atomicReferenceArray, i2, (Object) t);
        a(j2 + 1);
        return true;
    }

    private void a(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2, T t, long j3) {
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.f = atomicReferenceArray2;
        this.d = (j2 + j3) - 1;
        a(atomicReferenceArray2, i2, (Object) t);
        a(atomicReferenceArray, atomicReferenceArray2);
        a(atomicReferenceArray, i2, j);
        a(j2 + 1);
    }

    private void a(AtomicReferenceArray<Object> atomicReferenceArray, AtomicReferenceArray<Object> atomicReferenceArray2) {
        a(atomicReferenceArray, b(atomicReferenceArray.length() - 1), (Object) atomicReferenceArray2);
    }

    private AtomicReferenceArray<Object> a(AtomicReferenceArray<Object> atomicReferenceArray) {
        return (AtomicReferenceArray) a(atomicReferenceArray, b(atomicReferenceArray.length() - 1));
    }

    @Nullable
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.h;
        long d2 = d();
        int i2 = this.g;
        int a2 = a(d2, i2);
        T a3 = a(atomicReferenceArray, a2);
        boolean z = a3 == j;
        if (a3 != null && !z) {
            a(atomicReferenceArray, a2, (Object) null);
            b(d2 + 1);
            return a3;
        } else if (z) {
            return a(a(atomicReferenceArray), d2, i2);
        } else {
            return null;
        }
    }

    private T a(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2) {
        this.h = atomicReferenceArray;
        int a2 = a(j2, i2);
        T a3 = a(atomicReferenceArray, a2);
        if (a3 != null) {
            a(atomicReferenceArray, a2, (Object) null);
            b(j2 + 1);
        }
        return a3;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.h;
        long d2 = d();
        int i2 = this.g;
        T a2 = a(atomicReferenceArray, a(d2, i2));
        return a2 == j ? b(a(atomicReferenceArray), d2, i2) : a2;
    }

    private T b(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i2) {
        this.h = atomicReferenceArray;
        return a(atomicReferenceArray, a(j2, i2));
    }

    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public int size() {
        long b2 = b();
        while (true) {
            long a2 = a();
            long b3 = b();
            if (b2 == b3) {
                return (int) (a2 - b3);
            }
            b2 = b3;
        }
    }

    public boolean isEmpty() {
        return a() == b();
    }

    private void a(int i2) {
        this.c = Math.min(i2 / 4, a);
    }

    private long a() {
        return this.b.get();
    }

    private long b() {
        return this.i.get();
    }

    private long c() {
        return this.b.get();
    }

    private long d() {
        return this.i.get();
    }

    private void a(long j2) {
        this.b.lazySet(j2);
    }

    private void b(long j2) {
        this.i.lazySet(j2);
    }

    private static int a(long j2, int i2) {
        return b(((int) j2) & i2);
    }

    private static void a(AtomicReferenceArray<Object> atomicReferenceArray, int i2, Object obj) {
        atomicReferenceArray.lazySet(i2, obj);
    }

    private static <E> Object a(AtomicReferenceArray<Object> atomicReferenceArray, int i2) {
        return atomicReferenceArray.get(i2);
    }

    public boolean offer(T t, T t2) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.f;
        long a2 = a();
        int i2 = this.e;
        long j2 = a2 + 2;
        if (a(atomicReferenceArray, a(j2, i2)) == null) {
            int a3 = a(a2, i2);
            a(atomicReferenceArray, a3 + 1, (Object) t2);
            a(atomicReferenceArray, a3, (Object) t);
            a(j2);
        } else {
            AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
            this.f = atomicReferenceArray2;
            int a4 = a(a2, i2);
            a(atomicReferenceArray2, a4 + 1, (Object) t2);
            a(atomicReferenceArray2, a4, (Object) t);
            a(atomicReferenceArray, atomicReferenceArray2);
            a(atomicReferenceArray, a4, j);
            a(j2);
        }
        return true;
    }
}
