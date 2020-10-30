package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscArrayQueue<E> extends AtomicReferenceArray<E> implements SimplePlainQueue<E> {
    private static final Integer f = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
    private static final long serialVersionUID = -1296597691183856449L;
    final int a = (length() - 1);
    final AtomicLong b = new AtomicLong();
    long c;
    final AtomicLong d = new AtomicLong();
    final int e;

    /* access modifiers changed from: 0000 */
    public int a(long j, int i) {
        return ((int) j) & i;
    }

    public SpscArrayQueue(int i) {
        super(Pow2.roundToPowerOfTwo(i));
        this.e = Math.min(i / 4, f.intValue());
    }

    public boolean offer(E e2) {
        if (e2 == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        int i = this.a;
        long j = this.b.get();
        int a2 = a(j, i);
        if (j >= this.c) {
            long j2 = j + ((long) this.e);
            if (a(a(j2, i)) == null) {
                this.c = j2;
            } else if (a(a2) != null) {
                return false;
            }
        }
        a(a2, e2);
        a(j + 1);
        return true;
    }

    public boolean offer(E e2, E e3) {
        return offer(e2) && offer(e3);
    }

    @Nullable
    public E poll() {
        long j = this.d.get();
        int c2 = c(j);
        E a2 = a(c2);
        if (a2 == null) {
            return null;
        }
        b(j + 1);
        a(c2, (E) null);
        return a2;
    }

    public boolean isEmpty() {
        return this.b.get() == this.d.get();
    }

    /* access modifiers changed from: 0000 */
    public void a(long j) {
        this.b.lazySet(j);
    }

    /* access modifiers changed from: 0000 */
    public void b(long j) {
        this.d.lazySet(j);
    }

    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int c(long j) {
        return ((int) j) & this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, E e2) {
        lazySet(i, e2);
    }

    /* access modifiers changed from: 0000 */
    public E a(int i) {
        return get(i);
    }
}
