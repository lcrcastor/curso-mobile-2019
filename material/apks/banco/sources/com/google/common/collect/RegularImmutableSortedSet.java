package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    static final RegularImmutableSortedSet<Comparable> a = new RegularImmutableSortedSet<>(ImmutableList.of(), Ordering.natural());
    private final transient ImmutableList<E> d;

    RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.d = immutableList;
    }

    public UnmodifiableIterator<E> iterator() {
        return this.d.iterator();
    }

    @GwtIncompatible
    public UnmodifiableIterator<E> descendingIterator() {
        return this.d.reverse().iterator();
    }

    public int size() {
        return this.d.size();
    }

    public boolean contains(@Nullable Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                if (b(obj) >= 0) {
                    z = true;
                }
            } catch (ClassCastException unused) {
                return false;
            }
        }
        return z;
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!SortedIterables.a(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        PeekingIterator peekingIterator = Iterators.peekingIterator((Iterator<? extends T>) iterator());
        Iterator it = collection.iterator();
        Object next = it.next();
        while (peekingIterator.hasNext()) {
            try {
                int a2 = a(peekingIterator.peek(), next);
                if (a2 < 0) {
                    peekingIterator.next();
                } else if (a2 == 0) {
                    if (!it.hasNext()) {
                        return true;
                    }
                    next = it.next();
                } else if (a2 > 0) {
                    return false;
                }
            } catch (NullPointerException unused) {
                return false;
            } catch (ClassCastException unused2) {
                return false;
            }
        }
        return false;
    }

    private int b(Object obj) {
        return Collections.binarySearch(this.d, obj, d());
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.d.a();
    }

    /* access modifiers changed from: 0000 */
    public int a(Object[] objArr, int i) {
        return this.d.a(objArr, i);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        if (isEmpty()) {
            return true;
        }
        if (!SortedIterables.a(this.b, set)) {
            return containsAll(set);
        }
        Iterator it = set.iterator();
        try {
            UnmodifiableIterator it2 = iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                Object next2 = it.next();
                if (next2 != null) {
                    if (a(next, next2) != 0) {
                    }
                }
                return false;
            }
            return true;
        } catch (ClassCastException unused) {
            return false;
        } catch (NoSuchElementException unused2) {
            return false;
        }
    }

    public E first() {
        if (!isEmpty()) {
            return this.d.get(0);
        }
        throw new NoSuchElementException();
    }

    public E last() {
        if (!isEmpty()) {
            return this.d.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public E lower(E e) {
        int c = c(e, false) - 1;
        if (c == -1) {
            return null;
        }
        return this.d.get(c);
    }

    public E floor(E e) {
        int c = c(e, true) - 1;
        if (c == -1) {
            return null;
        }
        return this.d.get(c);
    }

    public E ceiling(E e) {
        int d2 = d(e, true);
        if (d2 == size()) {
            return null;
        }
        return this.d.get(d2);
    }

    public E higher(E e) {
        int d2 = d(e, false);
        if (d2 == size()) {
            return null;
        }
        return this.d.get(d2);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> b(E e, boolean z) {
        return a(0, c(e, z));
    }

    /* access modifiers changed from: 0000 */
    public int c(E e, boolean z) {
        return SortedLists.a((List<? extends E>) this.d, Preconditions.checkNotNull(e), comparator(), z ? KeyPresentBehavior.FIRST_AFTER : KeyPresentBehavior.FIRST_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> a(E e, boolean z, E e2, boolean z2) {
        return a(e, z).b(e2, z2);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> a(E e, boolean z) {
        return a(d(e, z), size());
    }

    /* access modifiers changed from: 0000 */
    public int d(E e, boolean z) {
        return SortedLists.a((List<? extends E>) this.d, Preconditions.checkNotNull(e), comparator(), z ? KeyPresentBehavior.FIRST_PRESENT : KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
    }

    /* access modifiers changed from: 0000 */
    public Comparator<Object> d() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public RegularImmutableSortedSet<E> a(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i < i2) {
            return new RegularImmutableSortedSet<>(this.d.subList(i, i2), this.b);
        }
        return a(this.b);
    }

    /* access modifiers changed from: 0000 */
    public int a(@Nullable Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            int a2 = SortedLists.a((List<? extends E>) this.d, obj, d(), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
            if (a2 < 0) {
                a2 = -1;
            }
            return a2;
        } catch (ClassCastException unused) {
            return -1;
        }
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> c() {
        return size() <= 1 ? this.d : new ImmutableSortedAsList(this, this.d);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> b() {
        Ordering reverse = Ordering.from(this.b).reverse();
        return isEmpty() ? a((Comparator<? super E>) reverse) : new RegularImmutableSortedSet(this.d.reverse(), reverse);
    }
}
