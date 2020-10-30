package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset.Entry;
import javax.annotation.Nullable;

@GwtIncompatible
final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> b;

    DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> immutableSortedMultiset) {
        this.b = immutableSortedMultiset;
    }

    public int count(@Nullable Object obj) {
        return this.b.count(obj);
    }

    public Entry<E> firstEntry() {
        return this.b.lastEntry();
    }

    public Entry<E> lastEntry() {
        return this.b.firstEntry();
    }

    public int size() {
        return this.b.size();
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.b.elementSet().descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public Entry<E> a(int i) {
        return (Entry) this.b.entrySet().asList().reverse().get(i);
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.b;
    }

    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.b.tailMultiset(e, boundType).descendingMultiset();
    }

    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.b.headMultiset(e, boundType).descendingMultiset();
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.b.a();
    }
}
