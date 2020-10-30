package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import javax.annotation.Nullable;

@GwtIncompatible
class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> a;

    DescendingImmutableSortedSet(ImmutableSortedSet<E> immutableSortedSet) {
        super(Ordering.from(immutableSortedSet.comparator()).reverse());
        this.a = immutableSortedSet;
    }

    public boolean contains(@Nullable Object obj) {
        return this.a.contains(obj);
    }

    public int size() {
        return this.a.size();
    }

    public UnmodifiableIterator<E> iterator() {
        return this.a.descendingIterator();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> b(E e, boolean z) {
        return this.a.tailSet(e, z).descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> a(E e, boolean z, E e2, boolean z2) {
        return this.a.subSet(e2, z2, e, z).descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> a(E e, boolean z) {
        return this.a.headSet(e, z).descendingSet();
    }

    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> descendingSet() {
        return this.a;
    }

    @GwtIncompatible("NavigableSet")
    public UnmodifiableIterator<E> descendingIterator() {
        return this.a.iterator();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> b() {
        throw new AssertionError("should never be called");
    }

    public E lower(E e) {
        return this.a.higher(e);
    }

    public E floor(E e) {
        return this.a.ceiling(e);
    }

    public E ceiling(E e) {
        return this.a.floor(e);
    }

    public E higher(E e) {
        return this.a.lower(e);
    }

    /* access modifiers changed from: 0000 */
    public int a(@Nullable Object obj) {
        int a2 = this.a.a(obj);
        if (a2 == -1) {
            return a2;
        }
        return (size() - 1) - a2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a.a();
    }
}
