package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

@GwtCompatible(emulated = true)
public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    private final transient Reference<AvlNode<E>> b;
    /* access modifiers changed from: private */
    public final transient GeneralRange<E> c;
    /* access modifiers changed from: private */
    public final transient AvlNode<E> d;

    enum Aggregate {
        SIZE {
            /* access modifiers changed from: 0000 */
            public int a(AvlNode<?> avlNode) {
                return avlNode.b;
            }

            /* access modifiers changed from: 0000 */
            public long b(@Nullable AvlNode<?> avlNode) {
                if (avlNode == null) {
                    return 0;
                }
                return avlNode.d;
            }
        },
        DISTINCT {
            /* access modifiers changed from: 0000 */
            public int a(AvlNode<?> avlNode) {
                return 1;
            }

            /* access modifiers changed from: 0000 */
            public long b(@Nullable AvlNode<?> avlNode) {
                if (avlNode == null) {
                    return 0;
                }
                return (long) avlNode.c;
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract int a(AvlNode<?> avlNode);

        /* access modifiers changed from: 0000 */
        public abstract long b(@Nullable AvlNode<?> avlNode);
    }

    static final class AvlNode<E> extends AbstractEntry<E> {
        /* access modifiers changed from: private */
        @Nullable
        public final E a;
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public long d;
        private int e;
        /* access modifiers changed from: private */
        public AvlNode<E> f;
        /* access modifiers changed from: private */
        public AvlNode<E> g;
        /* access modifiers changed from: private */
        public AvlNode<E> h;
        /* access modifiers changed from: private */
        public AvlNode<E> i;

        AvlNode(@Nullable E e2, int i2) {
            Preconditions.checkArgument(i2 > 0);
            this.a = e2;
            this.b = i2;
            this.d = (long) i2;
            this.c = 1;
            this.e = 1;
            this.f = null;
            this.g = null;
        }

        public int a(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            int i2 = 0;
            if (compare < 0) {
                if (this.f != null) {
                    i2 = this.f.a(comparator, e2);
                }
                return i2;
            } else if (compare <= 0) {
                return this.b;
            } else {
                if (this.g != null) {
                    i2 = this.g.a(comparator, e2);
                }
                return i2;
            }
        }

        private AvlNode<E> a(E e2, int i2) {
            this.g = new AvlNode<>(e2, i2);
            TreeMultiset.b(this, this.g, this.i);
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += (long) i2;
            return this;
        }

        private AvlNode<E> b(E e2, int i2) {
            this.f = new AvlNode<>(e2, i2);
            TreeMultiset.b(this.h, this.f, this);
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += (long) i2;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> a(Comparator<? super E> comparator, @Nullable E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            boolean z = true;
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return b(e2, i2);
                }
                int i3 = avlNode.e;
                this.f = avlNode.a(comparator, e2, i2, iArr);
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) i2;
                return this.f.e == i3 ? this : e();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    return a(e2, i2);
                }
                int i4 = avlNode2.e;
                this.g = avlNode2.a(comparator, e2, i2, iArr);
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) i2;
                return this.g.e == i4 ? this : e();
            } else {
                iArr[0] = this.b;
                long j = (long) i2;
                if (((long) this.b) + j > 2147483647L) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                this.b += i2;
                this.d += j;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> b(Comparator<? super E> comparator, @Nullable E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.f = avlNode.b(comparator, e2, i2, iArr);
                if (iArr[0] > 0) {
                    if (i2 >= iArr[0]) {
                        this.c--;
                        this.d -= (long) iArr[0];
                    } else {
                        this.d -= (long) i2;
                    }
                }
                return iArr[0] == 0 ? this : e();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.g = avlNode2.b(comparator, e2, i2, iArr);
                if (iArr[0] > 0) {
                    if (i2 >= iArr[0]) {
                        this.c--;
                        this.d -= (long) iArr[0];
                    } else {
                        this.d -= (long) i2;
                    }
                }
                return e();
            } else {
                iArr[0] = this.b;
                if (i2 >= this.b) {
                    return a();
                }
                this.b -= i2;
                this.d -= (long) i2;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> c(Comparator<? super E> comparator, @Nullable E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return i2 > 0 ? b(e2, i2) : this;
                }
                this.f = avlNode.c(comparator, e2, i2, iArr);
                if (i2 == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i2 > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) (i2 - iArr[0]);
                return e();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    return i2 > 0 ? a(e2, i2) : this;
                }
                this.g = avlNode2.c(comparator, e2, i2, iArr);
                if (i2 == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i2 > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) (i2 - iArr[0]);
                return e();
            } else {
                iArr[0] = this.b;
                if (i2 == 0) {
                    return a();
                }
                this.d += (long) (i2 - this.b);
                this.b = i2;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> a(Comparator<? super E> comparator, @Nullable E e2, int i2, int i3, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return (i2 != 0 || i3 <= 0) ? this : b(e2, i3);
                }
                this.f = avlNode.a(comparator, e2, i2, i3, iArr);
                if (iArr[0] == i2) {
                    if (i3 == 0 && iArr[0] != 0) {
                        this.c--;
                    } else if (i3 > 0 && iArr[0] == 0) {
                        this.c++;
                    }
                    this.d += (long) (i3 - iArr[0]);
                }
                return e();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    return (i2 != 0 || i3 <= 0) ? this : a(e2, i3);
                }
                this.g = avlNode2.a(comparator, e2, i2, i3, iArr);
                if (iArr[0] == i2) {
                    if (i3 == 0 && iArr[0] != 0) {
                        this.c--;
                    } else if (i3 > 0 && iArr[0] == 0) {
                        this.c++;
                    }
                    this.d += (long) (i3 - iArr[0]);
                }
                return e();
            } else {
                iArr[0] = this.b;
                if (i2 == this.b) {
                    if (i3 == 0) {
                        return a();
                    }
                    this.d += (long) (i3 - this.b);
                    this.b = i3;
                }
                return this;
            }
        }

        private AvlNode<E> a() {
            int i2 = this.b;
            this.b = 0;
            TreeMultiset.b(this.h, this.i);
            if (this.f == null) {
                return this.g;
            }
            if (this.g == null) {
                return this.f;
            }
            if (this.f.e >= this.g.e) {
                AvlNode<E> avlNode = this.h;
                avlNode.f = this.f.j(avlNode);
                avlNode.g = this.g;
                avlNode.c = this.c - 1;
                avlNode.d = this.d - ((long) i2);
                return avlNode.e();
            }
            AvlNode<E> avlNode2 = this.i;
            avlNode2.g = this.g.i(avlNode2);
            avlNode2.f = this.f;
            avlNode2.c = this.c - 1;
            avlNode2.d = this.d - ((long) i2);
            return avlNode2.e();
        }

        private AvlNode<E> i(AvlNode<E> avlNode) {
            if (this.f == null) {
                return this.g;
            }
            this.f = this.f.i(avlNode);
            this.c--;
            this.d -= (long) avlNode.b;
            return e();
        }

        private AvlNode<E> j(AvlNode<E> avlNode) {
            if (this.g == null) {
                return this.f;
            }
            this.g = this.g.j(avlNode);
            this.c--;
            this.d -= (long) avlNode.b;
            return e();
        }

        private void b() {
            this.c = TreeMultiset.a(this.f) + 1 + TreeMultiset.a(this.g);
            this.d = ((long) this.b) + k(this.f) + k(this.g);
        }

        private void c() {
            this.e = Math.max(l(this.f), l(this.g)) + 1;
        }

        private void d() {
            b();
            c();
        }

        private AvlNode<E> e() {
            int f2 = f();
            if (f2 == -2) {
                if (this.g.f() > 0) {
                    this.g = this.g.h();
                }
                return g();
            } else if (f2 != 2) {
                c();
                return this;
            } else {
                if (this.f.f() < 0) {
                    this.f = this.f.g();
                }
                return h();
            }
        }

        private int f() {
            return l(this.f) - l(this.g);
        }

        private AvlNode<E> g() {
            Preconditions.checkState(this.g != null);
            AvlNode<E> avlNode = this.g;
            this.g = avlNode.f;
            avlNode.f = this;
            avlNode.d = this.d;
            avlNode.c = this.c;
            d();
            avlNode.c();
            return avlNode;
        }

        private AvlNode<E> h() {
            Preconditions.checkState(this.f != null);
            AvlNode<E> avlNode = this.f;
            this.f = avlNode.g;
            avlNode.g = this;
            avlNode.d = this.d;
            avlNode.c = this.c;
            d();
            avlNode.c();
            return avlNode;
        }

        private static long k(@Nullable AvlNode<?> avlNode) {
            if (avlNode == null) {
                return 0;
            }
            return avlNode.d;
        }

        private static int l(@Nullable AvlNode<?> avlNode) {
            if (avlNode == null) {
                return 0;
            }
            return avlNode.e;
        }

        /* access modifiers changed from: private */
        @Nullable
        public AvlNode<E> b(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                return this.f == null ? this : (AvlNode) MoreObjects.firstNonNull(this.f.b(comparator, e2), this);
            } else if (compare == 0) {
                return this;
            } else {
                return this.g == null ? null : this.g.b(comparator, e2);
            }
        }

        /* access modifiers changed from: private */
        @Nullable
        public AvlNode<E> c(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            if (compare > 0) {
                return this.g == null ? this : (AvlNode) MoreObjects.firstNonNull(this.g.c(comparator, e2), this);
            } else if (compare == 0) {
                return this;
            } else {
                return this.f == null ? null : this.f.c(comparator, e2);
            }
        }

        public E getElement() {
            return this.a;
        }

        public int getCount() {
            return this.b;
        }

        public String toString() {
            return Multisets.immutableEntry(getElement(), getCount()).toString();
        }
    }

    static final class Reference<T> {
        @Nullable
        private T a;

        private Reference() {
        }

        @Nullable
        public T a() {
            return this.a;
        }

        public void a(@Nullable T t, T t2) {
            if (this.a != t) {
                throw new ConcurrentModificationException();
            }
            this.a = t2;
        }
    }

    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj) {
        return super.contains(obj);
    }

    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    public /* bridge */ /* synthetic */ NavigableSet elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Entry firstEntry() {
        return super.firstEntry();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ Entry lastEntry() {
        return super.lastEntry();
    }

    public /* bridge */ /* synthetic */ Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    public /* bridge */ /* synthetic */ Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(Object obj, BoundType boundType, Object obj2, BoundType boundType2) {
        return super.subMultiset(obj, boundType, obj2, boundType2);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<>(Ordering.natural()) : new TreeMultiset(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.a());
        this.b = reference;
        this.c = generalRange;
        this.d = avlNode;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.c = GeneralRange.a(comparator);
        this.d = new AvlNode<>(null, 1);
        b(this.d, this.d);
        this.b = new Reference<>();
    }

    private long a(Aggregate aggregate) {
        AvlNode avlNode = (AvlNode) this.b.a();
        long b2 = aggregate.b(avlNode);
        if (this.c.b()) {
            b2 -= a(aggregate, avlNode);
        }
        return this.c.c() ? b2 - b(aggregate, avlNode) : b2;
    }

    private long a(Aggregate aggregate, @Nullable AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        int compare = comparator().compare(this.c.d(), avlNode.a);
        if (compare < 0) {
            return a(aggregate, avlNode.f);
        }
        if (compare != 0) {
            return aggregate.b(avlNode.f) + ((long) aggregate.a(avlNode)) + a(aggregate, avlNode.g);
        }
        switch (this.c.e()) {
            case OPEN:
                return ((long) aggregate.a(avlNode)) + aggregate.b(avlNode.f);
            case CLOSED:
                return aggregate.b(avlNode.f);
            default:
                throw new AssertionError();
        }
    }

    private long b(Aggregate aggregate, @Nullable AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        int compare = comparator().compare(this.c.f(), avlNode.a);
        if (compare > 0) {
            return b(aggregate, avlNode.g);
        }
        if (compare != 0) {
            return aggregate.b(avlNode.g) + ((long) aggregate.a(avlNode)) + b(aggregate, avlNode.f);
        }
        switch (this.c.g()) {
            case OPEN:
                return ((long) aggregate.a(avlNode)) + aggregate.b(avlNode.g);
            case CLOSED:
                return aggregate.b(avlNode.g);
            default:
                throw new AssertionError();
        }
    }

    public int size() {
        return Ints.saturatedCast(a(Aggregate.SIZE));
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return Ints.saturatedCast(a(Aggregate.DISTINCT));
    }

    public int count(@Nullable Object obj) {
        try {
            AvlNode avlNode = (AvlNode) this.b.a();
            if (this.c.c(obj)) {
                if (avlNode != null) {
                    return avlNode.a(comparator(), obj);
                }
            }
            return 0;
        } catch (ClassCastException unused) {
            return 0;
        } catch (NullPointerException unused2) {
            return 0;
        }
    }

    @CanIgnoreReturnValue
    public int add(@Nullable E e, int i) {
        CollectPreconditions.a(i, "occurrences");
        if (i == 0) {
            return count(e);
        }
        Preconditions.checkArgument(this.c.c(e));
        AvlNode avlNode = (AvlNode) this.b.a();
        if (avlNode == null) {
            comparator().compare(e, e);
            AvlNode avlNode2 = new AvlNode(e, i);
            b(this.d, avlNode2, this.d);
            this.b.a(avlNode, avlNode2);
            return 0;
        }
        int[] iArr = new int[1];
        this.b.a(avlNode, avlNode.a(comparator(), e, i, iArr));
        return iArr[0];
    }

    @CanIgnoreReturnValue
    public int remove(@Nullable Object obj, int i) {
        CollectPreconditions.a(i, "occurrences");
        if (i == 0) {
            return count(obj);
        }
        AvlNode avlNode = (AvlNode) this.b.a();
        int[] iArr = new int[1];
        try {
            if (this.c.c(obj)) {
                if (avlNode != null) {
                    this.b.a(avlNode, avlNode.b(comparator(), obj, i, iArr));
                    return iArr[0];
                }
            }
            return 0;
        } catch (ClassCastException unused) {
            return 0;
        } catch (NullPointerException unused2) {
            return 0;
        }
    }

    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int i) {
        CollectPreconditions.a(i, NewHtcHomeBadger.COUNT);
        boolean z = true;
        if (!this.c.c(e)) {
            if (i != 0) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return 0;
        }
        AvlNode avlNode = (AvlNode) this.b.a();
        if (avlNode == null) {
            if (i > 0) {
                add(e, i);
            }
            return 0;
        }
        int[] iArr = new int[1];
        this.b.a(avlNode, avlNode.c(comparator(), e, i, iArr));
        return iArr[0];
    }

    @CanIgnoreReturnValue
    public boolean setCount(@Nullable E e, int i, int i2) {
        CollectPreconditions.a(i2, "newCount");
        CollectPreconditions.a(i, "oldCount");
        Preconditions.checkArgument(this.c.c(e));
        AvlNode avlNode = (AvlNode) this.b.a();
        boolean z = false;
        if (avlNode != null) {
            int[] iArr = new int[1];
            this.b.a(avlNode, avlNode.a(comparator(), e, i, i2, iArr));
            if (iArr[0] == i) {
                z = true;
            }
            return z;
        } else if (i != 0) {
            return false;
        } else {
            if (i2 > 0) {
                add(e, i2);
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    public Entry<E> b(final AvlNode<E> avlNode) {
        return new AbstractEntry<E>() {
            public E getElement() {
                return avlNode.getElement();
            }

            public int getCount() {
                int count = avlNode.getCount();
                return count == 0 ? TreeMultiset.this.count(getElement()) : count;
            }
        };
    }

    /* access modifiers changed from: private */
    @Nullable
    public AvlNode<E> h() {
        AvlNode<E> avlNode;
        if (((AvlNode) this.b.a()) == null) {
            return null;
        }
        if (this.c.b()) {
            Object d2 = this.c.d();
            AvlNode<E> a = ((AvlNode) this.b.a()).b(comparator(), d2);
            if (a == null) {
                return null;
            }
            if (this.c.e() == BoundType.OPEN && comparator().compare(d2, a.getElement()) == 0) {
                a = a.i;
            }
            avlNode = a;
        } else {
            avlNode = this.d.i;
        }
        if (avlNode == this.d || !this.c.c(avlNode.getElement())) {
            avlNode = null;
        }
        return avlNode;
    }

    /* access modifiers changed from: private */
    @Nullable
    public AvlNode<E> i() {
        AvlNode<E> avlNode;
        if (((AvlNode) this.b.a()) == null) {
            return null;
        }
        if (this.c.c()) {
            Object f = this.c.f();
            AvlNode<E> b2 = ((AvlNode) this.b.a()).c(comparator(), f);
            if (b2 == null) {
                return null;
            }
            if (this.c.g() == BoundType.OPEN && comparator().compare(f, b2.getElement()) == 0) {
                b2 = b2.h;
            }
            avlNode = b2;
        } else {
            avlNode = this.d.h;
        }
        if (avlNode == this.d || !this.c.c(avlNode.getElement())) {
            avlNode = null;
        }
        return avlNode;
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<E>> a() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> a = TreeMultiset.this.h();
            Entry<E> b;

            public boolean hasNext() {
                if (this.a == null) {
                    return false;
                }
                if (!TreeMultiset.this.c.b(this.a.getElement())) {
                    return true;
                }
                this.a = null;
                return false;
            }

            /* renamed from: a */
            public Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<E> a2 = TreeMultiset.this.b(this.a);
                this.b = a2;
                if (this.a.i == TreeMultiset.this.d) {
                    this.a = null;
                } else {
                    this.a = this.a.i;
                }
                return a2;
            }

            public void remove() {
                CollectPreconditions.a(this.b != null);
                TreeMultiset.this.setCount(this.b.getElement(), 0);
                this.b = null;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<E>> e() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> a = TreeMultiset.this.i();
            Entry<E> b = null;

            public boolean hasNext() {
                if (this.a == null) {
                    return false;
                }
                if (!TreeMultiset.this.c.a(this.a.getElement())) {
                    return true;
                }
                this.a = null;
                return false;
            }

            /* renamed from: a */
            public Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<E> a2 = TreeMultiset.this.b(this.a);
                this.b = a2;
                if (this.a.h == TreeMultiset.this.d) {
                    this.a = null;
                } else {
                    this.a = this.a.h;
                }
                return a2;
            }

            public void remove() {
                CollectPreconditions.a(this.b != null);
                TreeMultiset.this.setCount(this.b.getElement(), 0);
                this.b = null;
            }
        };
    }

    public SortedMultiset<E> headMultiset(@Nullable E e, BoundType boundType) {
        return new TreeMultiset(this.b, this.c.a(GeneralRange.b(comparator(), e, boundType)), this.d);
    }

    public SortedMultiset<E> tailMultiset(@Nullable E e, BoundType boundType) {
        return new TreeMultiset(this.b, this.c.a(GeneralRange.a(comparator(), e, boundType)), this.d);
    }

    static int a(@Nullable AvlNode<?> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        return avlNode.c;
    }

    /* access modifiers changed from: private */
    public static <T> void b(AvlNode<T> avlNode, AvlNode<T> avlNode2) {
        avlNode.i = avlNode2;
        avlNode2.h = avlNode;
    }

    /* access modifiers changed from: private */
    public static <T> void b(AvlNode<T> avlNode, AvlNode<T> avlNode2, AvlNode<T> avlNode3) {
        b(avlNode, avlNode2);
        b(avlNode2, avlNode3);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(elementSet().comparator());
        Serialization.a((Multiset<E>) this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator) objectInputStream.readObject();
        Serialization.a(AbstractSortedMultiset.class, "comparator").a(this, (Object) comparator);
        Serialization.a(TreeMultiset.class, "range").a(this, (Object) GeneralRange.a(comparator));
        Serialization.a(TreeMultiset.class, "rootReference").a(this, (Object) new Reference());
        AvlNode avlNode = new AvlNode(null, 1);
        Serialization.a(TreeMultiset.class, "header").a(this, (Object) avlNode);
        b(avlNode, avlNode);
        Serialization.a((Multiset<E>) this, objectInputStream);
    }
}
