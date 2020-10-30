package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;

@GwtCompatible
final class Constraints {

    static class ConstrainedCollection<E> extends ForwardingCollection<E> {
        private final Collection<E> a;
        private final Constraint<? super E> b;

        public ConstrainedCollection(Collection<E> collection, Constraint<? super E> constraint) {
            this.a = (Collection) Preconditions.checkNotNull(collection);
            this.b = (Constraint) Preconditions.checkNotNull(constraint);
        }

        /* access modifiers changed from: protected */
        public Collection<E> delegate() {
            return this.a;
        }

        public boolean add(E e) {
            this.b.a(e);
            return this.a.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            return this.a.addAll(Constraints.d(collection, this.b));
        }
    }

    @GwtCompatible
    static class ConstrainedList<E> extends ForwardingList<E> {
        final List<E> a;
        final Constraint<? super E> b;

        ConstrainedList(List<E> list, Constraint<? super E> constraint) {
            this.a = (List) Preconditions.checkNotNull(list);
            this.b = (Constraint) Preconditions.checkNotNull(constraint);
        }

        /* access modifiers changed from: protected */
        public List<E> delegate() {
            return this.a;
        }

        public boolean add(E e) {
            this.b.a(e);
            return this.a.add(e);
        }

        public void add(int i, E e) {
            this.b.a(e);
            this.a.add(i, e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            return this.a.addAll(Constraints.d(collection, this.b));
        }

        public boolean addAll(int i, Collection<? extends E> collection) {
            return this.a.addAll(i, Constraints.d(collection, this.b));
        }

        public ListIterator<E> listIterator() {
            return Constraints.b(this.a.listIterator(), this.b);
        }

        public ListIterator<E> listIterator(int i) {
            return Constraints.b(this.a.listIterator(i), this.b);
        }

        public E set(int i, E e) {
            this.b.a(e);
            return this.a.set(i, e);
        }

        public List<E> subList(int i, int i2) {
            return Constraints.a(this.a.subList(i, i2), this.b);
        }
    }

    static class ConstrainedListIterator<E> extends ForwardingListIterator<E> {
        private final ListIterator<E> a;
        private final Constraint<? super E> b;

        public ConstrainedListIterator(ListIterator<E> listIterator, Constraint<? super E> constraint) {
            this.a = listIterator;
            this.b = constraint;
        }

        /* access modifiers changed from: protected */
        public ListIterator<E> delegate() {
            return this.a;
        }

        public void add(E e) {
            this.b.a(e);
            this.a.add(e);
        }

        public void set(E e) {
            this.b.a(e);
            this.a.set(e);
        }
    }

    static class ConstrainedRandomAccessList<E> extends ConstrainedList<E> implements RandomAccess {
        ConstrainedRandomAccessList(List<E> list, Constraint<? super E> constraint) {
            super(list, constraint);
        }
    }

    static class ConstrainedSet<E> extends ForwardingSet<E> {
        private final Set<E> a;
        private final Constraint<? super E> b;

        public ConstrainedSet(Set<E> set, Constraint<? super E> constraint) {
            this.a = (Set) Preconditions.checkNotNull(set);
            this.b = (Constraint) Preconditions.checkNotNull(constraint);
        }

        /* access modifiers changed from: protected */
        public Set<E> delegate() {
            return this.a;
        }

        public boolean add(E e) {
            this.b.a(e);
            return this.a.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            return this.a.addAll(Constraints.d(collection, this.b));
        }
    }

    static class ConstrainedSortedSet<E> extends ForwardingSortedSet<E> {
        final SortedSet<E> a;
        final Constraint<? super E> b;

        ConstrainedSortedSet(SortedSet<E> sortedSet, Constraint<? super E> constraint) {
            this.a = (SortedSet) Preconditions.checkNotNull(sortedSet);
            this.b = (Constraint) Preconditions.checkNotNull(constraint);
        }

        /* access modifiers changed from: protected */
        public SortedSet<E> delegate() {
            return this.a;
        }

        public SortedSet<E> headSet(E e) {
            return Constraints.a(this.a.headSet(e), this.b);
        }

        public SortedSet<E> subSet(E e, E e2) {
            return Constraints.a(this.a.subSet(e, e2), this.b);
        }

        public SortedSet<E> tailSet(E e) {
            return Constraints.a(this.a.tailSet(e), this.b);
        }

        public boolean add(E e) {
            this.b.a(e);
            return this.a.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            return this.a.addAll(Constraints.d(collection, this.b));
        }
    }

    private Constraints() {
    }

    public static <E> Collection<E> a(Collection<E> collection, Constraint<? super E> constraint) {
        return new ConstrainedCollection(collection, constraint);
    }

    public static <E> Set<E> a(Set<E> set, Constraint<? super E> constraint) {
        return new ConstrainedSet(set, constraint);
    }

    public static <E> SortedSet<E> a(SortedSet<E> sortedSet, Constraint<? super E> constraint) {
        return new ConstrainedSortedSet(sortedSet, constraint);
    }

    public static <E> List<E> a(List<E> list, Constraint<? super E> constraint) {
        return list instanceof RandomAccess ? new ConstrainedRandomAccessList(list, constraint) : new ConstrainedList(list, constraint);
    }

    /* access modifiers changed from: private */
    public static <E> ListIterator<E> b(ListIterator<E> listIterator, Constraint<? super E> constraint) {
        return new ConstrainedListIterator(listIterator, constraint);
    }

    static <E> Collection<E> b(Collection<E> collection, Constraint<E> constraint) {
        if (collection instanceof SortedSet) {
            return a((SortedSet) collection, constraint);
        }
        if (collection instanceof Set) {
            return a((Set) collection, constraint);
        }
        if (collection instanceof List) {
            return a((List) collection, constraint);
        }
        return a(collection, constraint);
    }

    /* access modifiers changed from: private */
    public static <E> Collection<E> d(Collection<E> collection, Constraint<? super E> constraint) {
        ArrayList<Object> newArrayList = Lists.newArrayList((Iterable<? extends E>) collection);
        for (Object a : newArrayList) {
            constraint.a(a);
        }
        return newArrayList;
    }
}
