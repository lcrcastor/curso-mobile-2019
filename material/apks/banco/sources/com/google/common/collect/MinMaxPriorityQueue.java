package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@GwtCompatible
@Beta
public final class MinMaxPriorityQueue<E> extends AbstractQueue<E> {
    @VisibleForTesting
    final int a;
    private final Heap b;
    private final Heap c;
    /* access modifiers changed from: private */
    public Object[] d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public int f;

    @Beta
    public static final class Builder<B> {
        private final Comparator<B> a;
        private int b;
        /* access modifiers changed from: private */
        public int c;

        private Builder(Comparator<B> comparator) {
            this.b = -1;
            this.c = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            this.a = (Comparator) Preconditions.checkNotNull(comparator);
        }

        @CanIgnoreReturnValue
        public Builder<B> expectedSize(int i) {
            Preconditions.checkArgument(i >= 0);
            this.b = i;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<B> maximumSize(int i) {
            Preconditions.checkArgument(i > 0);
            this.c = i;
            return this;
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return create(Collections.emptySet());
        }

        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> iterable) {
            MinMaxPriorityQueue<T> minMaxPriorityQueue = new MinMaxPriorityQueue<>(this, MinMaxPriorityQueue.a(this.b, this.c, iterable));
            for (Object offer : iterable) {
                minMaxPriorityQueue.offer(offer);
            }
            return minMaxPriorityQueue;
        }

        /* access modifiers changed from: private */
        public <T extends B> Ordering<T> a() {
            return Ordering.from(this.a);
        }
    }

    class Heap {
        final Ordering<E> a;
        @Weak
        Heap b;

        private int d(int i) {
            return (i * 2) + 1;
        }

        private int e(int i) {
            return (i * 2) + 2;
        }

        Heap(Ordering<E> ordering) {
            this.a = ordering;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i, int i2) {
            return this.a.compare(MinMaxPriorityQueue.this.a(i), MinMaxPriorityQueue.this.a(i2));
        }

        /* access modifiers changed from: 0000 */
        public MoveDesc<E> a(int i, int i2, E e) {
            Object obj;
            int d = d(i2, e);
            if (d == i2) {
                return null;
            }
            if (d < i) {
                obj = MinMaxPriorityQueue.this.a(i);
            } else {
                obj = MinMaxPriorityQueue.this.a(f(i));
            }
            if (this.b.b(d, e) < i) {
                return new MoveDesc<>(e, obj);
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, E e) {
            Heap heap;
            int c2 = c(i, e);
            if (c2 == i) {
                c2 = i;
                heap = this;
            } else {
                heap = this.b;
            }
            heap.b(c2, e);
        }

        /* access modifiers changed from: 0000 */
        @CanIgnoreReturnValue
        public int b(int i, E e) {
            while (i > 2) {
                int g = g(i);
                Object a2 = MinMaxPriorityQueue.this.a(g);
                if (this.a.compare(a2, e) <= 0) {
                    break;
                }
                MinMaxPriorityQueue.this.d[i] = a2;
                i = g;
            }
            MinMaxPriorityQueue.this.d[i] = e;
            return i;
        }

        /* access modifiers changed from: 0000 */
        public int b(int i, int i2) {
            if (i >= MinMaxPriorityQueue.this.e) {
                return -1;
            }
            Preconditions.checkState(i > 0);
            int min = Math.min(i, MinMaxPriorityQueue.this.e - i2) + i2;
            for (int i3 = i + 1; i3 < min; i3++) {
                if (a(i3, i) < 0) {
                    i = i3;
                }
            }
            return i;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            return b(d(i), 2);
        }

        /* access modifiers changed from: 0000 */
        public int b(int i) {
            int d = d(i);
            if (d < 0) {
                return -1;
            }
            return b(d(d), 4);
        }

        /* access modifiers changed from: 0000 */
        public int c(int i, E e) {
            if (i == 0) {
                MinMaxPriorityQueue.this.d[0] = e;
                return 0;
            }
            int f = f(i);
            Object a2 = MinMaxPriorityQueue.this.a(f);
            if (f != 0) {
                int e2 = e(f(f));
                if (e2 != f && d(e2) >= MinMaxPriorityQueue.this.e) {
                    Object a3 = MinMaxPriorityQueue.this.a(e2);
                    if (this.a.compare(a3, a2) < 0) {
                        f = e2;
                        a2 = a3;
                    }
                }
            }
            if (this.a.compare(a2, e) < 0) {
                MinMaxPriorityQueue.this.d[i] = a2;
                MinMaxPriorityQueue.this.d[f] = e;
                return f;
            }
            MinMaxPriorityQueue.this.d[i] = e;
            return i;
        }

        /* access modifiers changed from: 0000 */
        public int a(E e) {
            int f = f(MinMaxPriorityQueue.this.e);
            if (f != 0) {
                int e2 = e(f(f));
                if (e2 != f && d(e2) >= MinMaxPriorityQueue.this.e) {
                    Object a2 = MinMaxPriorityQueue.this.a(e2);
                    if (this.a.compare(a2, e) < 0) {
                        MinMaxPriorityQueue.this.d[e2] = e;
                        MinMaxPriorityQueue.this.d[MinMaxPriorityQueue.this.e] = a2;
                        return e2;
                    }
                }
            }
            return MinMaxPriorityQueue.this.e;
        }

        /* access modifiers changed from: 0000 */
        public int d(int i, E e) {
            int a2 = a(i);
            if (a2 <= 0 || this.a.compare(MinMaxPriorityQueue.this.a(a2), e) >= 0) {
                return c(i, e);
            }
            MinMaxPriorityQueue.this.d[i] = MinMaxPriorityQueue.this.a(a2);
            MinMaxPriorityQueue.this.d[a2] = e;
            return a2;
        }

        /* access modifiers changed from: 0000 */
        public int c(int i) {
            while (true) {
                int b2 = b(i);
                if (b2 <= 0) {
                    return i;
                }
                MinMaxPriorityQueue.this.d[i] = MinMaxPriorityQueue.this.a(b2);
                i = b2;
            }
        }

        private int f(int i) {
            return (i - 1) / 2;
        }

        private int g(int i) {
            return f(f(i));
        }
    }

    static class MoveDesc<E> {
        final E a;
        final E b;

        MoveDesc(E e, E e2) {
            this.a = e;
            this.b = e2;
        }
    }

    class QueueIterator implements Iterator<E> {
        private int b;
        private int c;
        private Queue<E> d;
        private List<E> e;
        private E f;
        private boolean g;

        private QueueIterator() {
            this.b = -1;
            this.c = MinMaxPriorityQueue.this.f;
        }

        public boolean hasNext() {
            a();
            if (a(this.b + 1) >= MinMaxPriorityQueue.this.size()) {
                return this.d != null && !this.d.isEmpty();
            }
            return true;
        }

        public E next() {
            a();
            int a2 = a(this.b + 1);
            if (a2 < MinMaxPriorityQueue.this.size()) {
                this.b = a2;
                this.g = true;
                return MinMaxPriorityQueue.this.a(this.b);
            }
            if (this.d != null) {
                this.b = MinMaxPriorityQueue.this.size();
                this.f = this.d.poll();
                if (this.f != null) {
                    this.g = true;
                    return this.f;
                }
            }
            throw new NoSuchElementException("iterator moved past last element in queue.");
        }

        public void remove() {
            CollectPreconditions.a(this.g);
            a();
            this.g = false;
            this.c++;
            if (this.b < MinMaxPriorityQueue.this.size()) {
                MoveDesc b2 = MinMaxPriorityQueue.this.b(this.b);
                if (b2 != null) {
                    if (this.d == null) {
                        this.d = new ArrayDeque();
                        this.e = new ArrayList(3);
                    }
                    this.d.add(b2.a);
                    this.e.add(b2.b);
                }
                this.b--;
                return;
            }
            Preconditions.checkState(a((Object) this.f));
            this.f = null;
        }

        private boolean a(Iterable<E> iterable, E e2) {
            for (E e3 : iterable) {
                if (e3 == e2) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Object obj) {
            for (int i = 0; i < MinMaxPriorityQueue.this.e; i++) {
                if (MinMaxPriorityQueue.this.d[i] == obj) {
                    MinMaxPriorityQueue.this.b(i);
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (MinMaxPriorityQueue.this.f != this.c) {
                throw new ConcurrentModificationException();
            }
        }

        private int a(int i) {
            if (this.e != null) {
                while (i < MinMaxPriorityQueue.this.size() && a(this.e, MinMaxPriorityQueue.this.a(i))) {
                    i++;
                }
            }
            return i;
        }
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural()).create();
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> iterable) {
        return new Builder(Ordering.natural()).create(iterable);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder<>(comparator);
    }

    public static Builder<Comparable> expectedSize(int i) {
        return new Builder(Ordering.natural()).expectedSize(i);
    }

    public static Builder<Comparable> maximumSize(int i) {
        return new Builder(Ordering.natural()).maximumSize(i);
    }

    private MinMaxPriorityQueue(Builder<? super E> builder, int i) {
        Ordering a2 = builder.a();
        this.b = new Heap<>(a2);
        this.c = new Heap<>(a2.reverse());
        this.b.b = this.c;
        this.c.b = this.b;
        this.a = builder.c;
        this.d = new Object[i];
    }

    public int size() {
        return this.e;
    }

    @CanIgnoreReturnValue
    public boolean add(E e2) {
        offer(e2);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        boolean z = false;
        for (Object offer : collection) {
            offer(offer);
            z = true;
        }
        return z;
    }

    @CanIgnoreReturnValue
    public boolean offer(E e2) {
        Preconditions.checkNotNull(e2);
        this.f++;
        int i = this.e;
        this.e = i + 1;
        b();
        e(i).a(i, e2);
        if (this.e <= this.a || pollLast() != e2) {
            return true;
        }
        return false;
    }

    @CanIgnoreReturnValue
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return d(0);
    }

    /* access modifiers changed from: 0000 */
    public E a(int i) {
        return this.d[i];
    }

    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return a(0);
    }

    private int a() {
        int i = 1;
        switch (this.e) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                if (this.c.a(1, 2) > 0) {
                    i = 2;
                }
                return i;
        }
    }

    @CanIgnoreReturnValue
    public E pollFirst() {
        return poll();
    }

    @CanIgnoreReturnValue
    public E removeFirst() {
        return remove();
    }

    public E peekFirst() {
        return peek();
    }

    @CanIgnoreReturnValue
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return d(a());
    }

    @CanIgnoreReturnValue
    public E removeLast() {
        if (!isEmpty()) {
            return d(a());
        }
        throw new NoSuchElementException();
    }

    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return a(a());
    }

    /* access modifiers changed from: 0000 */
    @CanIgnoreReturnValue
    @VisibleForTesting
    public MoveDesc<E> b(int i) {
        Preconditions.checkPositionIndex(i, this.e);
        this.f++;
        this.e--;
        if (this.e == i) {
            this.d[this.e] = null;
            return null;
        }
        Object a2 = a(this.e);
        int a3 = e(this.e).a(a2);
        Object a4 = a(this.e);
        this.d[this.e] = null;
        MoveDesc<E> a5 = a(i, (E) a4);
        if (a3 >= i) {
            return a5;
        }
        if (a5 == null) {
            return new MoveDesc<>(a2, a4);
        }
        return new MoveDesc<>(a2, a5.b);
    }

    private MoveDesc<E> a(int i, E e2) {
        Heap e3 = e(i);
        int c2 = e3.c(i);
        int b2 = e3.b(c2, e2);
        if (b2 == c2) {
            return e3.a(i, c2, e2);
        }
        return b2 < i ? new MoveDesc<>(e2, a(i)) : null;
    }

    private E d(int i) {
        E a2 = a(i);
        b(i);
        return a2;
    }

    private Heap e(int i) {
        return c(i) ? this.b : this.c;
    }

    @VisibleForTesting
    static boolean c(int i) {
        int i2 = ((i + 1) ^ -1) ^ -1;
        Preconditions.checkState(i2 > 0, "negative index");
        return (1431655765 & i2) > (i2 & -1431655766);
    }

    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    public void clear() {
        for (int i = 0; i < this.e; i++) {
            this.d[i] = null;
        }
        this.e = 0;
    }

    public Object[] toArray() {
        Object[] objArr = new Object[this.e];
        System.arraycopy(this.d, 0, objArr, 0, this.e);
        return objArr;
    }

    public Comparator<? super E> comparator() {
        return this.b.a;
    }

    @VisibleForTesting
    static int a(int i, int i2, Iterable<?> iterable) {
        if (i == -1) {
            i = 11;
        }
        if (iterable instanceof Collection) {
            i = Math.max(i, ((Collection) iterable).size());
        }
        return a(i, i2);
    }

    private void b() {
        if (this.e > this.d.length) {
            Object[] objArr = new Object[c()];
            System.arraycopy(this.d, 0, objArr, 0, this.d.length);
            this.d = objArr;
        }
    }

    private int c() {
        int i;
        int length = this.d.length;
        if (length < 64) {
            i = (length + 1) * 2;
        } else {
            i = IntMath.checkedMultiply(length / 2, 3);
        }
        return a(i, this.a);
    }

    private static int a(int i, int i2) {
        return Math.min(i - 1, i2) + 1;
    }
}
