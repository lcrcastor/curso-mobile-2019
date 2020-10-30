package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
public final class Collections2 {
    static final Joiner a = Joiner.on(", ").useForNull("null");

    static class FilteredCollection<E> extends AbstractCollection<E> {
        final Collection<E> a;
        final Predicate<? super E> b;

        FilteredCollection(Collection<E> collection, Predicate<? super E> predicate) {
            this.a = collection;
            this.b = predicate;
        }

        /* access modifiers changed from: 0000 */
        public FilteredCollection<E> a(Predicate<? super E> predicate) {
            return new FilteredCollection<>(this.a, Predicates.and(this.b, predicate));
        }

        public boolean add(E e) {
            Preconditions.checkArgument(this.b.apply(e));
            return this.a.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            for (Object apply : collection) {
                Preconditions.checkArgument(this.b.apply(apply));
            }
            return this.a.addAll(collection);
        }

        public void clear() {
            Iterables.removeIf(this.a, this.b);
        }

        public boolean contains(@Nullable Object obj) {
            if (Collections2.a(this.a, obj)) {
                return this.b.apply(obj);
            }
            return false;
        }

        public boolean containsAll(Collection<?> collection) {
            return Collections2.a((Collection<?>) this, collection);
        }

        public boolean isEmpty() {
            return !Iterables.any(this.a, this.b);
        }

        public Iterator<E> iterator() {
            return Iterators.filter(this.a.iterator(), this.b);
        }

        public boolean remove(Object obj) {
            return contains(obj) && this.a.remove(obj);
        }

        public boolean removeAll(Collection<?> collection) {
            return Iterables.removeIf(this.a, Predicates.and(this.b, Predicates.in(collection)));
        }

        public boolean retainAll(Collection<?> collection) {
            return Iterables.removeIf(this.a, Predicates.and(this.b, Predicates.not(Predicates.in(collection))));
        }

        public int size() {
            return Iterators.size(iterator());
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
        final ImmutableList<E> a;
        final Comparator<? super E> b;
        final int c;

        public boolean isEmpty() {
            return false;
        }

        OrderedPermutationCollection(Iterable<E> iterable, Comparator<? super E> comparator) {
            this.a = Ordering.from(comparator).immutableSortedCopy(iterable);
            this.b = comparator;
            this.c = a(this.a, comparator);
        }

        private static <E> int a(List<E> list, Comparator<? super E> comparator) {
            long j = 1;
            int i = 1;
            int i2 = 1;
            while (i < list.size()) {
                if (comparator.compare(list.get(i - 1), list.get(i)) < 0) {
                    j *= LongMath.binomial(i, i2);
                    i2 = 0;
                    if (!Collections2.b(j)) {
                        return SubsamplingScaleImageView.TILE_SIZE_AUTO;
                    }
                }
                i++;
                i2++;
            }
            long binomial = j * LongMath.binomial(i, i2);
            if (!Collections2.b(binomial)) {
                return SubsamplingScaleImageView.TILE_SIZE_AUTO;
            }
            return (int) binomial;
        }

        public int size() {
            return this.c;
        }

        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator(this.a, this.b);
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.b((List<?>) this.a, (List) obj);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("orderedPermutationCollection(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class PermutationCollection<E> extends AbstractCollection<List<E>> {
        final ImmutableList<E> a;

        public boolean isEmpty() {
            return false;
        }

        PermutationCollection(ImmutableList<E> immutableList) {
            this.a = immutableList;
        }

        public int size() {
            return IntMath.factorial(this.a.size());
        }

        public Iterator<List<E>> iterator() {
            return new PermutationIterator(this.a);
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.b((List<?>) this.a, (List) obj);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("permutations(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
        List<E> a;
        final Comparator<? super E> b;

        OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator) {
            this.a = Lists.newArrayList((Iterable<? extends E>) list);
            this.b = comparator;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public List<E> computeNext() {
            if (this.a == null) {
                return (List) endOfData();
            }
            ImmutableList copyOf = ImmutableList.copyOf((Collection<? extends E>) this.a);
            b();
            return copyOf;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int c = c();
            if (c == -1) {
                this.a = null;
                return;
            }
            Collections.swap(this.a, c, a(c));
            Collections.reverse(this.a.subList(c + 1, this.a.size()));
        }

        /* access modifiers changed from: 0000 */
        public int c() {
            for (int size = this.a.size() - 2; size >= 0; size--) {
                if (this.b.compare(this.a.get(size), this.a.get(size + 1)) < 0) {
                    return size;
                }
            }
            return -1;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            Object obj = this.a.get(i);
            for (int size = this.a.size() - 1; size > i; size--) {
                if (this.b.compare(obj, this.a.get(size)) < 0) {
                    return size;
                }
            }
            throw new AssertionError("this statement should be unreachable");
        }
    }

    static class PermutationIterator<E> extends AbstractIterator<List<E>> {
        final List<E> a;
        final int[] b;
        final int[] c;
        int d = SubsamplingScaleImageView.TILE_SIZE_AUTO;

        PermutationIterator(List<E> list) {
            this.a = new ArrayList(list);
            int size = list.size();
            this.b = new int[size];
            this.c = new int[size];
            Arrays.fill(this.b, 0);
            Arrays.fill(this.c, 1);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public List<E> computeNext() {
            if (this.d <= 0) {
                return (List) endOfData();
            }
            ImmutableList copyOf = ImmutableList.copyOf((Collection<? extends E>) this.a);
            b();
            return copyOf;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.d = this.a.size() - 1;
            if (this.d != -1) {
                int i = 0;
                while (true) {
                    int i2 = this.b[this.d] + this.c[this.d];
                    if (i2 >= 0) {
                        if (i2 != this.d + 1) {
                            Collections.swap(this.a, (this.d - this.b[this.d]) + i, (this.d - i2) + i);
                            this.b[this.d] = i2;
                            break;
                        } else if (this.d == 0) {
                            break;
                        } else {
                            i++;
                            c();
                        }
                    } else {
                        c();
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            this.c[this.d] = -this.c[this.d];
            this.d--;
        }
    }

    static class TransformedCollection<F, T> extends AbstractCollection<T> {
        final Collection<F> a;
        final Function<? super F, ? extends T> b;

        TransformedCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.a = (Collection) Preconditions.checkNotNull(collection);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.a.clear();
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public Iterator<T> iterator() {
            return Iterators.transform(this.a.iterator(), this.b);
        }

        public int size() {
            return this.a.size();
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(long j) {
        return j >= 0 && j <= 2147483647L;
    }

    private Collections2() {
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof FilteredCollection) {
            return ((FilteredCollection) collection).a(predicate);
        }
        return new FilteredCollection((Collection) Preconditions.checkNotNull(collection), (Predicate) Preconditions.checkNotNull(predicate));
    }

    static boolean a(Collection<?> collection, @Nullable Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused2) {
            return false;
        }
    }

    static boolean b(Collection<?> collection, @Nullable Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(obj);
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused2) {
            return false;
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection(collection, function);
    }

    static boolean a(Collection<?> collection, Collection<?> collection2) {
        return Iterables.all(collection2, Predicates.in(collection));
    }

    static String a(final Collection<?> collection) {
        StringBuilder a2 = a(collection.size());
        a2.append('[');
        a.appendTo(a2, Iterables.transform(collection, new Function<Object, Object>() {
            public Object apply(Object obj) {
                return obj == collection ? "(this Collection)" : obj;
            }
        }));
        a2.append(']');
        return a2.toString();
    }

    static StringBuilder a(int i) {
        CollectPreconditions.a(i, "size");
        return new StringBuilder((int) Math.min(((long) i) * 8, 1073741824));
    }

    static <T> Collection<T> a(Iterable<T> iterable) {
        return (Collection) iterable;
    }

    @Beta
    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }

    @Beta
    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection(iterable, comparator);
    }

    @Beta
    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection(ImmutableList.copyOf(collection));
    }

    /* access modifiers changed from: private */
    public static boolean b(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        return HashMultiset.create((Iterable<? extends E>) list).equals(HashMultiset.create((Iterable<? extends E>) list2));
    }
}
