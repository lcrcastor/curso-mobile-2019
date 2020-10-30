package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multiset.Entry;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

@GwtCompatible
public final class Multisets {
    private static final Ordering<Entry<?>> a = new Ordering<Entry<?>>() {
        /* renamed from: a */
        public int compare(Entry<?> entry, Entry<?> entry2) {
            return Ints.compare(entry2.getCount(), entry.getCount());
        }
    };

    static abstract class AbstractEntry<E> implements Entry<E> {
        AbstractEntry() {
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (getCount() == entry.getCount() && Objects.equal(getElement(), entry.getElement())) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i;
            Object element = getElement();
            if (element == null) {
                i = 0;
            } else {
                i = element.hashCode();
            }
            return i ^ getCount();
        }

        public String toString() {
            String valueOf = String.valueOf(getElement());
            int count = getCount();
            if (count == 1) {
                return valueOf;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(valueOf);
            sb.append(" x ");
            sb.append(count);
            return sb.toString();
        }
    }

    static abstract class EntrySet<E> extends ImprovedAbstractSet<Entry<E>> {
        /* access modifiers changed from: 0000 */
        public abstract Multiset<E> a();

        EntrySet() {
        }

        public boolean contains(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (entry.getCount() <= 0) {
                return false;
            }
            if (a().count(entry.getElement()) == entry.getCount()) {
                z = true;
            }
            return z;
        }

        public boolean remove(Object obj) {
            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;
                Object element = entry.getElement();
                int count = entry.getCount();
                if (count != 0) {
                    return a().setCount(element, count, 0);
                }
            }
            return false;
        }

        public void clear() {
            a().clear();
        }
    }

    static final class FilteredMultiset<E> extends AbstractMultiset<E> {
        final Multiset<E> a;
        final Predicate<? super E> b;

        FilteredMultiset(Multiset<E> multiset, Predicate<? super E> predicate) {
            this.a = (Multiset) Preconditions.checkNotNull(multiset);
            this.b = (Predicate) Preconditions.checkNotNull(predicate);
        }

        /* renamed from: d */
        public UnmodifiableIterator<E> iterator() {
            return Iterators.filter(this.a.iterator(), this.b);
        }

        /* access modifiers changed from: 0000 */
        public Set<E> c() {
            return Sets.filter(this.a.elementSet(), this.b);
        }

        /* access modifiers changed from: 0000 */
        public Set<Entry<E>> createEntrySet() {
            return Sets.filter(this.a.entrySet(), (Predicate<? super E>) new Predicate<Entry<E>>() {
                /* renamed from: a */
                public boolean apply(Entry<E> entry) {
                    return FilteredMultiset.this.b.apply(entry.getElement());
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<E>> a() {
            throw new AssertionError("should never be called");
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return elementSet().size();
        }

        public int count(@Nullable Object obj) {
            int count = this.a.count(obj);
            if (count <= 0) {
                return 0;
            }
            if (!this.b.apply(obj)) {
                count = 0;
            }
            return count;
        }

        public int add(@Nullable E e, int i) {
            Preconditions.checkArgument(this.b.apply(e), "Element %s does not match predicate %s", (Object) e, (Object) this.b);
            return this.a.add(e, i);
        }

        public int remove(@Nullable Object obj, int i) {
            CollectPreconditions.a(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            return contains(obj) ? this.a.remove(obj, i) : 0;
        }

        public void clear() {
            elementSet().clear();
        }
    }

    static class ImmutableEntry<E> extends AbstractEntry<E> implements Serializable {
        private static final long serialVersionUID = 0;
        @Nullable
        private final E a;
        private final int b;

        public ImmutableEntry<E> a() {
            return null;
        }

        ImmutableEntry(@Nullable E e, int i) {
            this.a = e;
            this.b = i;
            CollectPreconditions.a(i, NewHtcHomeBadger.COUNT);
        }

        @Nullable
        public final E getElement() {
            return this.a;
        }

        public final int getCount() {
            return this.b;
        }
    }

    static abstract class ElementSet<E> extends ImprovedAbstractSet<E> {
        /* access modifiers changed from: 0000 */
        public abstract Multiset<E> a();

        ElementSet() {
        }

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            return a().contains(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            return a().containsAll(collection);
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public Iterator<E> iterator() {
            return new TransformedIterator<Entry<E>, E>(a().entrySet().iterator()) {
                /* access modifiers changed from: 0000 */
                public E a(Entry<E> entry) {
                    return entry.getElement();
                }
            };
        }

        public boolean remove(Object obj) {
            return a().remove(obj, SubsamplingScaleImageView.TILE_SIZE_AUTO) > 0;
        }

        public int size() {
            return a().entrySet().size();
        }
    }

    static final class MultisetIteratorImpl<E> implements Iterator<E> {
        private final Multiset<E> a;
        private final Iterator<Entry<E>> b;
        private Entry<E> c;
        private int d;
        private int e;
        private boolean f;

        MultisetIteratorImpl(Multiset<E> multiset, Iterator<Entry<E>> it) {
            this.a = multiset;
            this.b = it;
        }

        public boolean hasNext() {
            return this.d > 0 || this.b.hasNext();
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.d == 0) {
                this.c = (Entry) this.b.next();
                int count = this.c.getCount();
                this.d = count;
                this.e = count;
            }
            this.d--;
            this.f = true;
            return this.c.getElement();
        }

        public void remove() {
            CollectPreconditions.a(this.f);
            if (this.e == 1) {
                this.b.remove();
            } else {
                this.a.remove(this.c.getElement());
            }
            this.e--;
            this.f = false;
        }
    }

    static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
        private static final long serialVersionUID = 0;
        final Multiset<? extends E> a;
        transient Set<E> b;
        transient Set<Entry<E>> c;

        UnmodifiableMultiset(Multiset<? extends E> multiset) {
            this.a = multiset;
        }

        /* access modifiers changed from: protected */
        public Multiset<E> delegate() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public Set<E> a() {
            return Collections.unmodifiableSet(this.a.elementSet());
        }

        public Set<E> elementSet() {
            Set<E> set = this.b;
            if (set != null) {
                return set;
            }
            Set<E> a2 = a();
            this.b = a2;
            return a2;
        }

        public Set<Entry<E>> entrySet() {
            Set<Entry<E>> set = this.c;
            if (set != null) {
                return set;
            }
            Set<Entry<E>> unmodifiableSet = Collections.unmodifiableSet(this.a.entrySet());
            this.c = unmodifiableSet;
            return unmodifiableSet;
        }

        public Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.a.iterator());
        }

        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        public int add(E e, int i) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public int remove(Object obj, int i) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public int setCount(E e, int i) {
            throw new UnsupportedOperationException();
        }

        public boolean setCount(E e, int i, int i2) {
            throw new UnsupportedOperationException();
        }
    }

    private Multisets() {
    }

    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        return ((multiset instanceof UnmodifiableMultiset) || (multiset instanceof ImmutableMultiset)) ? multiset : new UnmodifiableMultiset((Multiset) Preconditions.checkNotNull(multiset));
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> immutableMultiset) {
        return (Multiset) Preconditions.checkNotNull(immutableMultiset);
    }

    @Beta
    public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        return new UnmodifiableSortedMultiset((SortedMultiset) Preconditions.checkNotNull(sortedMultiset));
    }

    public static <E> Entry<E> immutableEntry(@Nullable E e, int i) {
        return new ImmutableEntry(e, i);
    }

    @Beta
    public static <E> Multiset<E> filter(Multiset<E> multiset, Predicate<? super E> predicate) {
        if (!(multiset instanceof FilteredMultiset)) {
            return new FilteredMultiset(multiset, predicate);
        }
        FilteredMultiset filteredMultiset = (FilteredMultiset) multiset;
        return new FilteredMultiset(filteredMultiset.a, Predicates.and(filteredMultiset.b, predicate));
    }

    static int a(Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return ((Multiset) iterable).elementSet().size();
        }
        return 11;
    }

    @Beta
    public static <E> Multiset<E> union(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>() {
            public boolean contains(@Nullable Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            public int count(Object obj) {
                return Math.max(multiset.count(obj), multiset2.count(obj));
            }

            /* access modifiers changed from: 0000 */
            public Set<E> c() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> a() {
                final Iterator it = multiset.entrySet().iterator();
                final Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Entry<E>>() {
                    /* access modifiers changed from: protected */
                    /* renamed from: a */
                    public Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, Math.max(entry.getCount(), multiset2.count(element)));
                        }
                        while (it2.hasNext()) {
                            Entry entry2 = (Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Entry) endOfData();
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public int b() {
                return elementSet().size();
            }
        };
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>() {
            public int count(Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.min(count, multiset2.count(obj));
            }

            /* access modifiers changed from: 0000 */
            public Set<E> c() {
                return Sets.intersection(multiset.elementSet(), multiset2.elementSet());
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> a() {
                final Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Entry<E>>() {
                    /* access modifiers changed from: protected */
                    /* renamed from: a */
                    public Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            Object element = entry.getElement();
                            int min = Math.min(entry.getCount(), multiset2.count(element));
                            if (min > 0) {
                                return Multisets.immutableEntry(element, min);
                            }
                        }
                        return (Entry) endOfData();
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public int b() {
                return elementSet().size();
            }
        };
    }

    @Beta
    public static <E> Multiset<E> sum(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>() {
            public boolean contains(@Nullable Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            public int size() {
                return IntMath.saturatedAdd(multiset.size(), multiset2.size());
            }

            public int count(Object obj) {
                return multiset.count(obj) + multiset2.count(obj);
            }

            /* access modifiers changed from: 0000 */
            public Set<E> c() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> a() {
                final Iterator it = multiset.entrySet().iterator();
                final Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Entry<E>>() {
                    /* access modifiers changed from: protected */
                    /* renamed from: a */
                    public Entry<E> computeNext() {
                        if (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            Object element = entry.getElement();
                            return Multisets.immutableEntry(element, entry.getCount() + multiset2.count(element));
                        }
                        while (it2.hasNext()) {
                            Entry entry2 = (Entry) it2.next();
                            Object element2 = entry2.getElement();
                            if (!multiset.contains(element2)) {
                                return Multisets.immutableEntry(element2, entry2.getCount());
                            }
                        }
                        return (Entry) endOfData();
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public int b() {
                return elementSet().size();
            }
        };
    }

    @Beta
    public static <E> Multiset<E> difference(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>() {
            public int count(@Nullable Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.max(0, count - multiset2.count(obj));
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> a() {
                final Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Entry<E>>() {
                    /* access modifiers changed from: protected */
                    /* renamed from: a */
                    public Entry<E> computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            Object element = entry.getElement();
                            int count = entry.getCount() - multiset2.count(element);
                            if (count > 0) {
                                return Multisets.immutableEntry(element, count);
                            }
                        }
                        return (Entry) endOfData();
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public int b() {
                return Iterators.size(a());
            }
        };
    }

    @CanIgnoreReturnValue
    public static boolean containsOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        for (Entry entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) < entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    @CanIgnoreReturnValue
    public static boolean retainOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        return a(multiset, multiset2);
    }

    private static <E> boolean a(Multiset<E> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int count = multiset2.count(entry.getElement());
            if (count == 0) {
                it.remove();
            } else if (count < entry.getCount()) {
                multiset.setCount(entry.getElement(), count);
            }
            z = true;
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return removeOccurrences(multiset, (Multiset) iterable);
        }
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(iterable);
        boolean z = false;
        for (Object remove : iterable) {
            z |= multiset.remove(remove);
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean removeOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int count = multiset2.count(entry.getElement());
            if (count >= entry.getCount()) {
                it.remove();
            } else if (count > 0) {
                multiset.remove(entry.getElement(), count);
            }
            z = true;
        }
        return z;
    }

    static boolean a(Multiset<?> multiset, @Nullable Object obj) {
        if (obj == multiset) {
            return true;
        }
        if (!(obj instanceof Multiset)) {
            return false;
        }
        Multiset multiset2 = (Multiset) obj;
        if (multiset.size() != multiset2.size() || multiset.entrySet().size() != multiset2.entrySet().size()) {
            return false;
        }
        for (Entry entry : multiset2.entrySet()) {
            if (multiset.count(entry.getElement()) != entry.getCount()) {
                return false;
            }
        }
        return true;
    }

    static <E> boolean a(Multiset<E> multiset, Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        if (collection instanceof Multiset) {
            for (Entry entry : b((Iterable<T>) collection).entrySet()) {
                multiset.add(entry.getElement(), entry.getCount());
            }
        } else {
            Iterators.addAll(multiset, collection.iterator());
        }
        return true;
    }

    static boolean b(Multiset<?> multiset, Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().removeAll(collection);
    }

    static boolean c(Multiset<?> multiset, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return multiset.elementSet().retainAll(collection);
    }

    static <E> int a(Multiset<E> multiset, E e, int i) {
        CollectPreconditions.a(i, NewHtcHomeBadger.COUNT);
        int count = multiset.count(e);
        int i2 = i - count;
        if (i2 > 0) {
            multiset.add(e, i2);
        } else if (i2 < 0) {
            multiset.remove(e, -i2);
        }
        return count;
    }

    static <E> boolean a(Multiset<E> multiset, E e, int i, int i2) {
        CollectPreconditions.a(i, "oldCount");
        CollectPreconditions.a(i2, "newCount");
        if (multiset.count(e) != i) {
            return false;
        }
        multiset.setCount(e, i2);
        return true;
    }

    static <E> Iterator<E> a(Multiset<E> multiset) {
        return new MultisetIteratorImpl(multiset, multiset.entrySet().iterator());
    }

    static int b(Multiset<?> multiset) {
        long j = 0;
        for (Entry count : multiset.entrySet()) {
            j += (long) count.getCount();
        }
        return Ints.saturatedCast(j);
    }

    static <T> Multiset<T> b(Iterable<T> iterable) {
        return (Multiset) iterable;
    }

    @Beta
    public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> multiset) {
        return ImmutableMultiset.a((Collection<? extends Entry<? extends E>>) a.immutableSortedCopy(multiset.entrySet()));
    }
}
