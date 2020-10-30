package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtIncompatible
final class RegularImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    static final ImmutableSortedMultiset<Comparable> b = new RegularImmutableSortedMultiset(Ordering.natural());
    private static final long[] c = {0};
    private final transient RegularImmutableSortedSet<E> d;
    private final transient long[] e;
    private final transient int f;
    private final transient int g;

    RegularImmutableSortedMultiset(Comparator<? super E> comparator) {
        this.d = ImmutableSortedSet.a(comparator);
        this.e = c;
        this.f = 0;
        this.g = 0;
    }

    RegularImmutableSortedMultiset(RegularImmutableSortedSet<E> regularImmutableSortedSet, long[] jArr, int i, int i2) {
        this.d = regularImmutableSortedSet;
        this.e = jArr;
        this.f = i;
        this.g = i2;
    }

    private int b(int i) {
        return (int) (this.e[(this.f + i) + 1] - this.e[this.f + i]);
    }

    /* access modifiers changed from: 0000 */
    public Entry<E> a(int i) {
        return Multisets.immutableEntry(this.d.asList().get(i), b(i));
    }

    public Entry<E> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return a(0);
    }

    public Entry<E> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return a(this.g - 1);
    }

    public int count(@Nullable Object obj) {
        int a = this.d.a(obj);
        if (a >= 0) {
            return b(a);
        }
        return 0;
    }

    public int size() {
        return Ints.saturatedCast(this.e[this.f + this.g] - this.e[this.f]);
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.d;
    }

    public ImmutableSortedMultiset<E> headMultiset(E e2, BoundType boundType) {
        return a(0, this.d.c(e2, Preconditions.checkNotNull(boundType) == BoundType.CLOSED));
    }

    public ImmutableSortedMultiset<E> tailMultiset(E e2, BoundType boundType) {
        return a(this.d.d(e2, Preconditions.checkNotNull(boundType) == BoundType.CLOSED), this.g);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedMultiset<E> a(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, this.g);
        if (i == i2) {
            return a(comparator());
        }
        if (i == 0 && i2 == this.g) {
            return this;
        }
        return new RegularImmutableSortedMultiset(this.d.a(i, i2), this.e, this.f + i, i2 - i);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.f > 0 || this.g < this.e.length - 1;
    }
}
