package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Iterators {
    static final UnmodifiableListIterator<Object> a = new UnmodifiableListIterator<Object>() {
        public boolean hasNext() {
            return false;
        }

        public boolean hasPrevious() {
            return false;
        }

        public int nextIndex() {
            return 0;
        }

        public int previousIndex() {
            return -1;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public Object previous() {
            throw new NoSuchElementException();
        }
    };
    private static final Iterator<Object> b = new Iterator<Object>() {
        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.a(false);
        }
    };

    static class ConcatenatedIterator<T> extends MultitransformedIterator<Iterator<? extends T>, T> {
        /* access modifiers changed from: 0000 */
        public Iterator<? extends T> a(Iterator<? extends T> it) {
            return it;
        }

        public ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> it) {
            super(c(it));
        }

        /* access modifiers changed from: private */
        public static <T> Iterator<Iterator<? extends T>> c(Iterator<? extends Iterator<? extends T>> it) {
            return new MultitransformedIterator<Iterator<? extends T>, Iterator<? extends T>>(it) {
                /* access modifiers changed from: 0000 */
                public Iterator<? extends Iterator<? extends T>> a(Iterator<? extends T> it) {
                    if (it instanceof ConcatenatedIterator) {
                        return ConcatenatedIterator.c(((ConcatenatedIterator) it).a);
                    }
                    return Iterators.singletonIterator(it);
                }
            };
        }
    }

    static class MergingIterator<T> extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> a;

        public MergingIterator(Iterable<? extends Iterator<? extends T>> iterable, final Comparator<? super T> comparator) {
            this.a = new PriorityQueue(2, new Comparator<PeekingIterator<T>>() {
                /* renamed from: a */
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return comparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
            });
            for (Iterator it : iterable) {
                if (it.hasNext()) {
                    this.a.add(Iterators.peekingIterator(it));
                }
            }
        }

        public boolean hasNext() {
            return !this.a.isEmpty();
        }

        public T next() {
            PeekingIterator peekingIterator = (PeekingIterator) this.a.remove();
            T next = peekingIterator.next();
            if (peekingIterator.hasNext()) {
                this.a.add(peekingIterator);
            }
            return next;
        }
    }

    static class PeekingImpl<E> implements PeekingIterator<E> {
        private final Iterator<? extends E> a;
        private boolean b;
        private E c;

        public PeekingImpl(Iterator<? extends E> it) {
            this.a = (Iterator) Preconditions.checkNotNull(it);
        }

        public boolean hasNext() {
            return this.b || this.a.hasNext();
        }

        public E next() {
            if (!this.b) {
                return this.a.next();
            }
            E e = this.c;
            this.b = false;
            this.c = null;
            return e;
        }

        public void remove() {
            Preconditions.checkState(!this.b, "Can't remove after you've peeked at next");
            this.a.remove();
        }

        public E peek() {
            if (!this.b) {
                this.c = this.a.next();
                this.b = true;
            }
            return this.c;
        }
    }

    private Iterators() {
    }

    static <T> UnmodifiableIterator<T> a() {
        return b();
    }

    static <T> UnmodifiableListIterator<T> b() {
        return a;
    }

    static <T> Iterator<T> c() {
        return b;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<? extends T> it) {
        Preconditions.checkNotNull(it);
        if (it instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator) it;
        }
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                return it.next();
            }
        };
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static int size(Iterator<?> it) {
        long j = 0;
        while (it.hasNext()) {
            it.next();
            j++;
        }
        return Ints.saturatedCast(j);
    }

    public static boolean contains(Iterator<?> it, @Nullable Object obj) {
        return any(it, Predicates.equalTo(obj));
    }

    @CanIgnoreReturnValue
    public static boolean removeAll(Iterator<?> it, Collection<?> collection) {
        return removeIf(it, Predicates.in(collection));
    }

    @CanIgnoreReturnValue
    public static <T> boolean removeIf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean z = false;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @CanIgnoreReturnValue
    public static boolean retainAll(Iterator<?> it, Collection<?> collection) {
        return removeIf(it, Predicates.not(Predicates.in(collection)));
    }

    public static boolean elementsEqual(Iterator<?> it, Iterator<?> it2) {
        while (it.hasNext()) {
            if (!it2.hasNext()) {
                return false;
            }
            if (!Objects.equal(it.next(), it2.next())) {
                return false;
            }
        }
        return !it2.hasNext();
    }

    public static String toString(Iterator<?> it) {
        Joiner joiner = Collections2.a;
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        StringBuilder appendTo = joiner.appendTo(sb, it);
        appendTo.append(']');
        return appendTo.toString();
    }

    @CanIgnoreReturnValue
    public static <T> T getOnlyElement(Iterator<T> it) {
        T next = it.next();
        if (!it.hasNext()) {
            return next;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <");
        sb.append(next);
        for (int i = 0; i < 4 && it.hasNext(); i++) {
            sb.append(", ");
            sb.append(it.next());
        }
        if (it.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }

    @CanIgnoreReturnValue
    @Nullable
    public static <T> T getOnlyElement(Iterator<? extends T> it, @Nullable T t) {
        return it.hasNext() ? getOnlyElement(it) : t;
    }

    @GwtIncompatible
    public static <T> T[] toArray(Iterator<? extends T> it, Class<T> cls) {
        return Iterables.toArray(Lists.newArrayList(it), cls);
    }

    @CanIgnoreReturnValue
    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> it) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(it);
        boolean z = false;
        while (it.hasNext()) {
            z |= collection.add(it.next());
        }
        return z;
    }

    public static int frequency(Iterator<?> it, @Nullable Object obj) {
        return size(filter(it, Predicates.equalTo(obj)));
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>() {
            Iterator<T> a = Iterators.c();

            public boolean hasNext() {
                return this.a.hasNext() || iterable.iterator().hasNext();
            }

            public T next() {
                if (!this.a.hasNext()) {
                    this.a = iterable.iterator();
                    if (!this.a.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.a.next();
            }

            public void remove() {
                this.a.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T... tArr) {
        return cycle((Iterable<T>) Lists.newArrayList((E[]) tArr));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        return concat((Iterator<? extends Iterator<? extends T>>) new ConsumingQueueIterator<Object>((T[]) new Iterator[]{it, it2}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        return concat((Iterator<? extends Iterator<? extends T>>) new ConsumingQueueIterator<Object>((T[]) new Iterator[]{it, it2, it3}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2, Iterator<? extends T> it3, Iterator<? extends T> it4) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        Preconditions.checkNotNull(it4);
        return concat((Iterator<? extends Iterator<? extends T>>) new ConsumingQueueIterator<Object>((T[]) new Iterator[]{it, it2, it3, it4}));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T>... itArr) {
        for (Iterator checkNotNull : (Iterator[]) Preconditions.checkNotNull(itArr)) {
            Preconditions.checkNotNull(checkNotNull);
        }
        return concat((Iterator<? extends Iterator<? extends T>>) new ConsumingQueueIterator<Object>((T[]) itArr));
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> it) {
        return new ConcatenatedIterator(it);
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> it, int i) {
        return a(it, i, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> it, int i) {
        return a(it, i, true);
    }

    private static <T> UnmodifiableIterator<List<T>> a(final Iterator<T> it, final int i, final boolean z) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i > 0);
        return new UnmodifiableIterator<List<T>>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public List<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object[] objArr = new Object[i];
                int i = 0;
                while (i < i && it.hasNext()) {
                    objArr[i] = it.next();
                    i++;
                }
                for (int i2 = i; i2 < i; i2++) {
                    objArr[i2] = null;
                }
                List<T> unmodifiableList = Collections.unmodifiableList(Arrays.asList(objArr));
                return (z || i == i) ? unmodifiableList : unmodifiableList.subList(0, i);
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> it, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() {
            /* access modifiers changed from: protected */
            public T computeNext() {
                while (it.hasNext()) {
                    T next = it.next();
                    if (predicate.apply(next)) {
                        return next;
                    }
                }
                return endOfData();
            }
        };
    }

    @GwtIncompatible
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> it, Class<T> cls) {
        return filter(it, Predicates.instanceOf(cls));
    }

    public static <T> boolean any(Iterator<T> it, Predicate<? super T> predicate) {
        return indexOf(it, predicate) != -1;
    }

    public static <T> boolean all(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            if (!predicate.apply(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(Iterator<T> it, Predicate<? super T> predicate) {
        return filter(it, predicate).next();
    }

    @Nullable
    public static <T> T find(Iterator<? extends T> it, Predicate<? super T> predicate, @Nullable T t) {
        return getNext(filter(it, predicate), t);
    }

    public static <T> Optional<T> tryFind(Iterator<T> it, Predicate<? super T> predicate) {
        UnmodifiableIterator filter = filter(it, predicate);
        return filter.hasNext() ? Optional.of(filter.next()) : Optional.absent();
    }

    public static <T> int indexOf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <F, T> Iterator<T> transform(Iterator<F> it, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(it) {
            /* access modifiers changed from: 0000 */
            public T a(F f) {
                return function.apply(f);
            }
        };
    }

    public static <T> T get(Iterator<T> it, int i) {
        a(i);
        int advance = advance(it, i);
        if (it.hasNext()) {
            return it.next();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("position (");
        sb.append(i);
        sb.append(") must be less than the number of elements that remained (");
        sb.append(advance);
        sb.append(")");
        throw new IndexOutOfBoundsException(sb.toString());
    }

    static void a(int i) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("position (");
            sb.append(i);
            sb.append(") must not be negative");
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    @Nullable
    public static <T> T get(Iterator<? extends T> it, int i, @Nullable T t) {
        a(i);
        advance(it, i);
        return getNext(it, t);
    }

    @Nullable
    public static <T> T getNext(Iterator<? extends T> it, @Nullable T t) {
        return it.hasNext() ? it.next() : t;
    }

    public static <T> T getLast(Iterator<T> it) {
        T next;
        do {
            next = it.next();
        } while (it.hasNext());
        return next;
    }

    @Nullable
    public static <T> T getLast(Iterator<? extends T> it, @Nullable T t) {
        return it.hasNext() ? getLast(it) : t;
    }

    @CanIgnoreReturnValue
    public static int advance(Iterator<?> it, int i) {
        Preconditions.checkNotNull(it);
        int i2 = 0;
        Preconditions.checkArgument(i >= 0, "numberToAdvance must be nonnegative");
        while (i2 < i && it.hasNext()) {
            it.next();
            i2++;
        }
        return i2;
    }

    public static <T> Iterator<T> limit(final Iterator<T> it, final int i) {
        Preconditions.checkNotNull(it);
        Preconditions.checkArgument(i >= 0, "limit is negative");
        return new Iterator<T>() {
            private int c;

            public boolean hasNext() {
                return this.c < i && it.hasNext();
            }

            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.c++;
                return it.next();
            }

            public void remove() {
                it.remove();
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new UnmodifiableIterator<T>() {
            public String toString() {
                return "Iterators.consumingIterator(...)";
            }

            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                T next = it.next();
                it.remove();
                return next;
            }
        };
    }

    @Nullable
    static <T> T a(Iterator<T> it) {
        if (!it.hasNext()) {
            return null;
        }
        T next = it.next();
        it.remove();
        return next;
    }

    static void b(Iterator<?> it) {
        Preconditions.checkNotNull(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T... tArr) {
        return a(tArr, 0, tArr.length, 0);
    }

    static <T> UnmodifiableListIterator<T> a(final T[] tArr, final int i, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0);
        Preconditions.checkPositionIndexes(i, i + i2, tArr.length);
        Preconditions.checkPositionIndex(i3, i2);
        if (i2 == 0) {
            return b();
        }
        return new AbstractIndexedListIterator<T>(i2, i3) {
            /* access modifiers changed from: protected */
            public T a(int i) {
                return tArr[i + i];
            }
        };
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable final T t) {
        return new UnmodifiableIterator<T>() {
            boolean a;

            public boolean hasNext() {
                return !this.a;
            }

            public T next() {
                if (this.a) {
                    throw new NoSuchElementException();
                }
                this.a = true;
                return t;
            }
        };
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            public T next() {
                return enumeration.nextElement();
            }
        };
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new Enumeration<T>() {
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public T nextElement() {
                return it.next();
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> it) {
        if (it instanceof PeekingImpl) {
            return (PeekingImpl) it;
        }
        return new PeekingImpl(it);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return (PeekingIterator) Preconditions.checkNotNull(peekingIterator);
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator(iterable, comparator);
    }

    static <T> ListIterator<T> c(Iterator<T> it) {
        return (ListIterator) it;
    }
}
