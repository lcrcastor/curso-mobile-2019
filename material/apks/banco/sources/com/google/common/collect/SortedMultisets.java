package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset.Entry;
import com.google.j2objc.annotations.Weak;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class SortedMultisets {

    static class ElementSet<E> extends ElementSet<E> implements SortedSet<E> {
        @Weak
        private final SortedMultiset<E> a;

        ElementSet(SortedMultiset<E> sortedMultiset) {
            this.a = sortedMultiset;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public final SortedMultiset<E> a() {
            return this.a;
        }

        public Comparator<? super E> comparator() {
            return a().comparator();
        }

        public SortedSet<E> subSet(E e, E e2) {
            return a().subMultiset(e, BoundType.CLOSED, e2, BoundType.OPEN).elementSet();
        }

        public SortedSet<E> headSet(E e) {
            return a().headMultiset(e, BoundType.OPEN).elementSet();
        }

        public SortedSet<E> tailSet(E e) {
            return a().tailMultiset(e, BoundType.CLOSED).elementSet();
        }

        public E first() {
            return SortedMultisets.c(a().firstEntry());
        }

        public E last() {
            return SortedMultisets.c(a().lastEntry());
        }
    }

    @GwtIncompatible
    static class NavigableElementSet<E> extends ElementSet<E> implements NavigableSet<E> {
        NavigableElementSet(SortedMultiset<E> sortedMultiset) {
            super(sortedMultiset);
        }

        public E lower(E e) {
            return SortedMultisets.d(a().headMultiset(e, BoundType.OPEN).lastEntry());
        }

        public E floor(E e) {
            return SortedMultisets.d(a().headMultiset(e, BoundType.CLOSED).lastEntry());
        }

        public E ceiling(E e) {
            return SortedMultisets.d(a().tailMultiset(e, BoundType.CLOSED).firstEntry());
        }

        public E higher(E e) {
            return SortedMultisets.d(a().tailMultiset(e, BoundType.OPEN).firstEntry());
        }

        public NavigableSet<E> descendingSet() {
            return new NavigableElementSet(a().descendingMultiset());
        }

        public Iterator<E> descendingIterator() {
            return descendingSet().iterator();
        }

        public E pollFirst() {
            return SortedMultisets.d(a().pollFirstEntry());
        }

        public E pollLast() {
            return SortedMultisets.d(a().pollLastEntry());
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return new NavigableElementSet(a().subMultiset(e, BoundType.a(z), e2, BoundType.a(z2)));
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return new NavigableElementSet(a().headMultiset(e, BoundType.a(z)));
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return new NavigableElementSet(a().tailMultiset(e, BoundType.a(z)));
        }
    }

    private SortedMultisets() {
    }

    /* access modifiers changed from: private */
    public static <E> E c(Entry<E> entry) {
        if (entry != null) {
            return entry.getElement();
        }
        throw new NoSuchElementException();
    }

    /* access modifiers changed from: private */
    public static <E> E d(@Nullable Entry<E> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getElement();
    }
}
