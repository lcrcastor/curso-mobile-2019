package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Sets {

    static final class CartesianSet<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
        private final transient ImmutableList<ImmutableSet<E>> a;
        private final transient CartesianList<E> b;

        static <E> Set<List<E>> a(List<? extends Set<? extends E>> list) {
            Builder builder = new Builder(list.size());
            for (Set copyOf : list) {
                ImmutableSet copyOf2 = ImmutableSet.copyOf((Collection<? extends E>) copyOf);
                if (copyOf2.isEmpty()) {
                    return ImmutableSet.of();
                }
                builder.add((Object) copyOf2);
            }
            final ImmutableList build = builder.build();
            return new CartesianSet(build, new CartesianList(new ImmutableList<List<E>>() {
                /* access modifiers changed from: 0000 */
                public boolean a() {
                    return true;
                }

                public int size() {
                    return build.size();
                }

                /* renamed from: a */
                public List<E> get(int i) {
                    return ((ImmutableSet) build.get(i)).asList();
                }
            }));
        }

        private CartesianSet(ImmutableList<ImmutableSet<E>> immutableList, CartesianList<E> cartesianList) {
            this.a = immutableList;
            this.b = cartesianList;
        }

        /* access modifiers changed from: protected */
        public Collection<List<E>> delegate() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof CartesianSet)) {
                return super.equals(obj);
            }
            return this.a.equals(((CartesianSet) obj).a);
        }

        public int hashCode() {
            int i = 1;
            int size = size() - 1;
            for (int i2 = 0; i2 < this.a.size(); i2++) {
                size = ((size * 31) ^ -1) ^ -1;
            }
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                Set set = (Set) it.next();
                i = (((i * 31) + ((size() / set.size()) * set.hashCode())) ^ -1) ^ -1;
            }
            return ((i + size) ^ -1) ^ -1;
        }
    }

    @GwtIncompatible
    static class FilteredNavigableSet<E> extends FilteredSortedSet<E> implements NavigableSet<E> {
        FilteredNavigableSet(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
            super(navigableSet, predicate);
        }

        /* access modifiers changed from: 0000 */
        public NavigableSet<E> a() {
            return (NavigableSet) this.a;
        }

        @Nullable
        public E lower(E e) {
            return Iterators.getNext(headSet(e, false).descendingIterator(), null);
        }

        @Nullable
        public E floor(E e) {
            return Iterators.getNext(headSet(e, true).descendingIterator(), null);
        }

        public E ceiling(E e) {
            return Iterables.getFirst(tailSet(e, true), null);
        }

        public E higher(E e) {
            return Iterables.getFirst(tailSet(e, false), null);
        }

        public E pollFirst() {
            return Iterables.a((Iterable<T>) a(), this.b);
        }

        public E pollLast() {
            return Iterables.a((Iterable<T>) a().descendingSet(), this.b);
        }

        public NavigableSet<E> descendingSet() {
            return Sets.filter(a().descendingSet(), this.b);
        }

        public Iterator<E> descendingIterator() {
            return Iterators.filter(a().descendingIterator(), this.b);
        }

        public E last() {
            return descendingIterator().next();
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.filter(a().subSet(e, z, e2, z2), this.b);
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.filter(a().headSet(e, z), this.b);
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.filter(a().tailSet(e, z), this.b);
        }
    }

    static class FilteredSet<E> extends FilteredCollection<E> implements Set<E> {
        FilteredSet(Set<E> set, Predicate<? super E> predicate) {
            super(set, predicate);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }

        public int hashCode() {
            return Sets.a(this);
        }
    }

    static class FilteredSortedSet<E> extends FilteredSet<E> implements SortedSet<E> {
        FilteredSortedSet(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
            super(sortedSet, predicate);
        }

        public Comparator<? super E> comparator() {
            return ((SortedSet) this.a).comparator();
        }

        public SortedSet<E> subSet(E e, E e2) {
            return new FilteredSortedSet(((SortedSet) this.a).subSet(e, e2), this.b);
        }

        public SortedSet<E> headSet(E e) {
            return new FilteredSortedSet(((SortedSet) this.a).headSet(e), this.b);
        }

        public SortedSet<E> tailSet(E e) {
            return new FilteredSortedSet(((SortedSet) this.a).tailSet(e), this.b);
        }

        public E first() {
            return iterator().next();
        }

        public E last() {
            SortedSet sortedSet = (SortedSet) this.a;
            while (true) {
                E last = sortedSet.last();
                if (this.b.apply(last)) {
                    return last;
                }
                sortedSet = sortedSet.headSet(last);
            }
        }
    }

    static abstract class ImprovedAbstractSet<E> extends AbstractSet<E> {
        ImprovedAbstractSet() {
        }

        public boolean removeAll(Collection<?> collection) {
            return Sets.a((Set<?>) this, collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return super.retainAll((Collection) Preconditions.checkNotNull(collection));
        }
    }

    static final class PowerSet<E> extends AbstractSet<Set<E>> {
        final ImmutableMap<E, Integer> a;

        public boolean isEmpty() {
            return false;
        }

        PowerSet(Set<E> set) {
            this.a = Maps.a((Collection<E>) set);
            Preconditions.checkArgument(this.a.size() <= 30, "Too many elements to create power set: %s > 30", this.a.size());
        }

        public int size() {
            return 1 << this.a.size();
        }

        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(size()) {
                /* access modifiers changed from: protected */
                /* renamed from: b */
                public Set<E> a(int i) {
                    return new SubSet(PowerSet.this.a, i);
                }
            };
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            return this.a.keySet().containsAll((Set) obj);
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof PowerSet)) {
                return super.equals(obj);
            }
            return this.a.equals(((PowerSet) obj).a);
        }

        public int hashCode() {
            return this.a.keySet().hashCode() << (this.a.size() - 1);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("powerSet(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class SubSet<E> extends AbstractSet<E> {
        /* access modifiers changed from: private */
        public final ImmutableMap<E, Integer> a;
        /* access modifiers changed from: private */
        public final int b;

        SubSet(ImmutableMap<E, Integer> immutableMap, int i) {
            this.a = immutableMap;
            this.b = i;
        }

        public Iterator<E> iterator() {
            return new UnmodifiableIterator<E>() {
                final ImmutableList<E> a = SubSet.this.a.keySet().asList();
                int b = SubSet.this.b;

                public boolean hasNext() {
                    return this.b != 0;
                }

                public E next() {
                    int numberOfTrailingZeros = Integer.numberOfTrailingZeros(this.b);
                    if (numberOfTrailingZeros == 32) {
                        throw new NoSuchElementException();
                    }
                    this.b &= (1 << numberOfTrailingZeros) ^ -1;
                    return this.a.get(numberOfTrailingZeros);
                }
            };
        }

        public int size() {
            return Integer.bitCount(this.b);
        }

        public boolean contains(@Nullable Object obj) {
            Integer num = (Integer) this.a.get(obj);
            if (num != null) {
                if (((1 << num.intValue()) & this.b) != 0) {
                    return true;
                }
            }
            return false;
        }
    }

    @GwtIncompatible
    static class DescendingSet<E> extends ForwardingNavigableSet<E> {
        private final NavigableSet<E> a;

        DescendingSet(NavigableSet<E> navigableSet) {
            this.a = navigableSet;
        }

        /* access modifiers changed from: protected */
        public NavigableSet<E> delegate() {
            return this.a;
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

        public E pollFirst() {
            return this.a.pollLast();
        }

        public E pollLast() {
            return this.a.pollFirst();
        }

        public NavigableSet<E> descendingSet() {
            return this.a;
        }

        public Iterator<E> descendingIterator() {
            return this.a.iterator();
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return this.a.subSet(e2, z2, e, z).descendingSet();
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return this.a.tailSet(e, z).descendingSet();
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return this.a.headSet(e, z).descendingSet();
        }

        public Comparator<? super E> comparator() {
            Comparator comparator = this.a.comparator();
            if (comparator == null) {
                return Ordering.natural().reverse();
            }
            return a(comparator);
        }

        private static <T> Ordering<T> a(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        public E first() {
            return this.a.last();
        }

        public SortedSet<E> headSet(E e) {
            return standardHeadSet(e);
        }

        public E last() {
            return this.a.first();
        }

        public SortedSet<E> subSet(E e, E e2) {
            return standardSubSet(e, e2);
        }

        public SortedSet<E> tailSet(E e) {
            return standardTailSet(e);
        }

        public Iterator<E> iterator() {
            return this.a.descendingIterator();
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return standardToArray(tArr);
        }

        public String toString() {
            return standardToString();
        }
    }

    public static abstract class SetView<E> extends AbstractSet<E> {
        public abstract UnmodifiableIterator<E> iterator();

        private SetView() {
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf((Collection<? extends E>) this);
        }

        @CanIgnoreReturnValue
        public <S extends Set<E>> S copyInto(S s) {
            s.addAll(this);
            return s;
        }
    }

    @GwtIncompatible
    static final class UnmodifiableNavigableSet<E> extends ForwardingSortedSet<E> implements Serializable, NavigableSet<E> {
        private static final long serialVersionUID = 0;
        private final NavigableSet<E> a;
        private transient UnmodifiableNavigableSet<E> b;

        UnmodifiableNavigableSet(NavigableSet<E> navigableSet) {
            this.a = (NavigableSet) Preconditions.checkNotNull(navigableSet);
        }

        /* access modifiers changed from: protected */
        public SortedSet<E> delegate() {
            return Collections.unmodifiableSortedSet(this.a);
        }

        public E lower(E e) {
            return this.a.lower(e);
        }

        public E floor(E e) {
            return this.a.floor(e);
        }

        public E ceiling(E e) {
            return this.a.ceiling(e);
        }

        public E higher(E e) {
            return this.a.higher(e);
        }

        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        public NavigableSet<E> descendingSet() {
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet = this.b;
            if (unmodifiableNavigableSet != null) {
                return unmodifiableNavigableSet;
            }
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet2 = new UnmodifiableNavigableSet<>(this.a.descendingSet());
            this.b = unmodifiableNavigableSet2;
            unmodifiableNavigableSet2.b = this;
            return unmodifiableNavigableSet2;
        }

        public Iterator<E> descendingIterator() {
            return Iterators.unmodifiableIterator(this.a.descendingIterator());
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.unmodifiableNavigableSet(this.a.subSet(e, z, e2, z2));
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.a.headSet(e, z));
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.a.tailSet(e, z));
        }
    }

    private Sets() {
    }

    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E e, E... eArr) {
        return ImmutableEnumSet.a(EnumSet.of(e, eArr));
    }

    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> iterable) {
        if (iterable instanceof ImmutableEnumSet) {
            return (ImmutableEnumSet) iterable;
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.isEmpty()) {
                return ImmutableSet.of();
            }
            return ImmutableEnumSet.a(EnumSet.copyOf(collection));
        }
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return ImmutableSet.of();
        }
        EnumSet of = EnumSet.of((Enum) it.next());
        Iterators.addAll(of, it);
        return ImmutableEnumSet.a(of);
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> cls) {
        EnumSet<E> noneOf = EnumSet.noneOf(cls);
        Iterables.addAll(noneOf, iterable);
        return noneOf;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    public static <E> HashSet<E> newHashSet(E... eArr) {
        HashSet<E> newHashSetWithExpectedSize = newHashSetWithExpectedSize(eArr.length);
        Collections.addAll(newHashSetWithExpectedSize, eArr);
        return newHashSetWithExpectedSize;
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int i) {
        return new HashSet<>(Maps.a(i));
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? new HashSet<>(Collections2.a(iterable)) : newHashSet(iterable.iterator());
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> it) {
        HashSet<E> newHashSet = newHashSet();
        Iterators.addAll(newHashSet, it);
        return newHashSet;
    }

    public static <E> Set<E> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> iterable) {
        Set<E> newConcurrentHashSet = newConcurrentHashSet();
        Iterables.addAll(newConcurrentHashSet, iterable);
        return newConcurrentHashSet;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int i) {
        return new LinkedHashSet<>(Maps.a(i));
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet<>(Collections2.a(iterable));
        }
        LinkedHashSet<E> newLinkedHashSet = newLinkedHashSet();
        Iterables.addAll(newLinkedHashSet, iterable);
        return newLinkedHashSet;
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet<>();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> iterable) {
        TreeSet<E> newTreeSet = newTreeSet();
        Iterables.addAll(newTreeSet, iterable);
        return newTreeSet;
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<>((Comparator) Preconditions.checkNotNull(comparator));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> iterable) {
        return new CopyOnWriteArraySet<>(iterable instanceof Collection ? Collections2.a(iterable) : Lists.newArrayList(iterable));
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet) collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
        return a(collection, ((Enum) collection.iterator().next()).getDeclaringClass());
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> cls) {
        Preconditions.checkNotNull(collection);
        return collection instanceof EnumSet ? EnumSet.complementOf((EnumSet) collection) : a(collection, cls);
    }

    private static <E extends Enum<E>> EnumSet<E> a(Collection<E> collection, Class<E> cls) {
        EnumSet<E> allOf = EnumSet.allOf(cls);
        allOf.removeAll(collection);
        return allOf;
    }

    @Deprecated
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    public static <E> SetView<E> union(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final SetView difference = difference(set2, set);
        return new SetView<E>() {
            public int size() {
                return IntMath.saturatedAdd(set.size(), difference.size());
            }

            public boolean isEmpty() {
                return set.isEmpty() && set2.isEmpty();
            }

            public UnmodifiableIterator<E> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(set.iterator(), difference.iterator()));
            }

            public boolean contains(Object obj) {
                return set.contains(obj) || set2.contains(obj);
            }

            public <S extends Set<E>> S copyInto(S s) {
                s.addAll(set);
                s.addAll(set2);
                return s;
            }

            public ImmutableSet<E> immutableCopy() {
                return new ImmutableSet.Builder().addAll((Iterable) set).addAll((Iterable) set2).build();
            }
        };
    }

    public static <E> SetView<E> intersection(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final Predicate in = Predicates.in(set2);
        return new SetView<E>() {
            public UnmodifiableIterator<E> iterator() {
                return Iterators.filter(set.iterator(), in);
            }

            public int size() {
                return Iterators.size(iterator());
            }

            public boolean isEmpty() {
                return !iterator().hasNext();
            }

            public boolean contains(Object obj) {
                return set.contains(obj) && set2.contains(obj);
            }

            public boolean containsAll(Collection<?> collection) {
                return set.containsAll(collection) && set2.containsAll(collection);
            }
        };
    }

    public static <E> SetView<E> difference(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final Predicate not = Predicates.not(Predicates.in(set2));
        return new SetView<E>() {
            public UnmodifiableIterator<E> iterator() {
                return Iterators.filter(set.iterator(), not);
            }

            public int size() {
                return Iterators.size(iterator());
            }

            public boolean isEmpty() {
                return set2.containsAll(set);
            }

            public boolean contains(Object obj) {
                return set.contains(obj) && !set2.contains(obj);
            }
        };
    }

    public static <E> SetView<E> symmetricDifference(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() {
            public UnmodifiableIterator<E> iterator() {
                final Iterator it = set.iterator();
                final Iterator it2 = set2.iterator();
                return new AbstractIterator<E>() {
                    public E computeNext() {
                        while (it.hasNext()) {
                            E next = it.next();
                            if (!set2.contains(next)) {
                                return next;
                            }
                        }
                        while (it2.hasNext()) {
                            E next2 = it2.next();
                            if (!set.contains(next2)) {
                                return next2;
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return Iterators.size(iterator());
            }

            public boolean isEmpty() {
                return set.equals(set2);
            }

            public boolean contains(Object obj) {
                return set2.contains(obj) ^ set.contains(obj);
            }
        };
    }

    public static <E> Set<E> filter(Set<E> set, Predicate<? super E> predicate) {
        if (set instanceof SortedSet) {
            return filter((SortedSet) set, predicate);
        }
        if (!(set instanceof FilteredSet)) {
            return new FilteredSet((Set) Preconditions.checkNotNull(set), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) set;
        return new FilteredSet((Set) filteredSet.a, Predicates.and(filteredSet.b, predicate));
    }

    public static <E> SortedSet<E> filter(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
        if (!(sortedSet instanceof FilteredSet)) {
            return new FilteredSortedSet((SortedSet) Preconditions.checkNotNull(sortedSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) sortedSet;
        return new FilteredSortedSet((SortedSet) filteredSet.a, Predicates.and(filteredSet.b, predicate));
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> filter(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
        if (!(navigableSet instanceof FilteredSet)) {
            return new FilteredNavigableSet((NavigableSet) Preconditions.checkNotNull(navigableSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) navigableSet;
        return new FilteredNavigableSet((NavigableSet) filteredSet.a, Predicates.and(filteredSet.b, predicate));
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> list) {
        return CartesianSet.a(list);
    }

    public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... setArr) {
        return cartesianProduct(Arrays.asList(setArr));
    }

    @GwtCompatible(serializable = false)
    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        return new PowerSet(set);
    }

    static int a(Set<?> set) {
        int i = 0;
        for (Object next : set) {
            i = ((i + (next != null ? next.hashCode() : 0)) ^ -1) ^ -1;
        }
        return i;
    }

    static boolean a(Set<?> set, @Nullable Object obj) {
        boolean z = true;
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() != set2.size() || !set.containsAll(set2)) {
                z = false;
            }
            return z;
        } catch (NullPointerException unused) {
            return false;
        } catch (ClassCastException unused2) {
            return false;
        }
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> navigableSet) {
        return ((navigableSet instanceof ImmutableSortedSet) || (navigableSet instanceof UnmodifiableNavigableSet)) ? navigableSet : new UnmodifiableNavigableSet(navigableSet);
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
        return Synchronized.a(navigableSet);
    }

    static boolean a(Set<?> set, Iterator<?> it) {
        boolean z = false;
        while (it.hasNext()) {
            z |= set.remove(it.next());
        }
        return z;
    }

    static boolean a(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!(collection instanceof Set) || collection.size() <= set.size()) {
            return a(set, collection.iterator());
        }
        return Iterators.removeAll(set.iterator(), collection);
    }

    @GwtIncompatible
    @Beta
    public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> navigableSet, Range<K> range) {
        boolean z = false;
        if (navigableSet.comparator() != null && navigableSet.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableSet.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "set is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            Comparable lowerEndpoint = range.lowerEndpoint();
            boolean z2 = range.lowerBoundType() == BoundType.CLOSED;
            Comparable upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableSet.subSet(lowerEndpoint, z2, upperEndpoint, z);
        } else if (range.hasLowerBound()) {
            Comparable lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableSet.tailSet(lowerEndpoint2, z);
        } else if (!range.hasUpperBound()) {
            return (NavigableSet) Preconditions.checkNotNull(navigableSet);
        } else {
            Comparable upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                z = true;
            }
            return navigableSet.headSet(upperEndpoint2, z);
        }
    }
}
