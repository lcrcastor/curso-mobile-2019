package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Lists {

    static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final E a;
        final E[] b;

        OnePlusArrayList(@Nullable E e, E[] eArr) {
            this.a = e;
            this.b = (Object[]) Preconditions.checkNotNull(eArr);
        }

        public int size() {
            return IntMath.saturatedAdd(this.b.length, 1);
        }

        public E get(int i) {
            Preconditions.checkElementIndex(i, size());
            return i == 0 ? this.a : this.b[i - 1];
        }
    }

    static class ReverseList<T> extends AbstractList<T> {
        private final List<T> a;

        ReverseList(List<T> list) {
            this.a = (List) Preconditions.checkNotNull(list);
        }

        /* access modifiers changed from: 0000 */
        public List<T> a() {
            return this.a;
        }

        private int a(int i) {
            int size = size();
            Preconditions.checkElementIndex(i, size);
            return (size - 1) - i;
        }

        /* access modifiers changed from: private */
        public int b(int i) {
            int size = size();
            Preconditions.checkPositionIndex(i, size);
            return size - i;
        }

        public void add(int i, @Nullable T t) {
            this.a.add(b(i), t);
        }

        public void clear() {
            this.a.clear();
        }

        public T remove(int i) {
            return this.a.remove(a(i));
        }

        /* access modifiers changed from: protected */
        public void removeRange(int i, int i2) {
            subList(i, i2).clear();
        }

        public T set(int i, @Nullable T t) {
            return this.a.set(a(i), t);
        }

        public T get(int i) {
            return this.a.get(a(i));
        }

        public int size() {
            return this.a.size();
        }

        public List<T> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return Lists.reverse(this.a.subList(b(i2), b(i)));
        }

        public Iterator<T> iterator() {
            return listIterator();
        }

        public ListIterator<T> listIterator(int i) {
            final ListIterator listIterator = this.a.listIterator(b(i));
            return new ListIterator<T>() {
                boolean a;

                public void add(T t) {
                    listIterator.add(t);
                    listIterator.previous();
                    this.a = false;
                }

                public boolean hasNext() {
                    return listIterator.hasPrevious();
                }

                public boolean hasPrevious() {
                    return listIterator.hasNext();
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.a = true;
                    return listIterator.previous();
                }

                public int nextIndex() {
                    return ReverseList.this.b(listIterator.nextIndex());
                }

                public T previous() {
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.a = true;
                    return listIterator.next();
                }

                public int previousIndex() {
                    return nextIndex() - 1;
                }

                public void remove() {
                    CollectPreconditions.a(this.a);
                    listIterator.remove();
                    this.a = false;
                }

                public void set(T t) {
                    Preconditions.checkState(this.a);
                    listIterator.set(t);
                }
            };
        }
    }

    static final class StringAsImmutableList extends ImmutableList<Character> {
        private final String a;

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return false;
        }

        StringAsImmutableList(String str) {
            this.a = str;
        }

        public int indexOf(@Nullable Object obj) {
            if (obj instanceof Character) {
                return this.a.indexOf(((Character) obj).charValue());
            }
            return -1;
        }

        public int lastIndexOf(@Nullable Object obj) {
            if (obj instanceof Character) {
                return this.a.lastIndexOf(((Character) obj).charValue());
            }
            return -1;
        }

        public ImmutableList<Character> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return Lists.charactersOf(this.a.substring(i, i2));
        }

        /* renamed from: a */
        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.a.charAt(i));
        }

        public int size() {
            return this.a.length();
        }
    }

    static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final E a;
        final E b;
        final E[] c;

        TwoPlusArrayList(@Nullable E e, @Nullable E e2, E[] eArr) {
            this.a = e;
            this.b = e2;
            this.c = (Object[]) Preconditions.checkNotNull(eArr);
        }

        public int size() {
            return IntMath.saturatedAdd(this.c.length, 2);
        }

        public E get(int i) {
            switch (i) {
                case 0:
                    return this.a;
                case 1:
                    return this.b;
                default:
                    Preconditions.checkElementIndex(i, size());
                    return this.c[i - 2];
            }
        }
    }

    static class AbstractListWrapper<E> extends AbstractList<E> {
        final List<E> a;

        AbstractListWrapper(List<E> list) {
            this.a = (List) Preconditions.checkNotNull(list);
        }

        public void add(int i, E e) {
            this.a.add(i, e);
        }

        public boolean addAll(int i, Collection<? extends E> collection) {
            return this.a.addAll(i, collection);
        }

        public E get(int i) {
            return this.a.get(i);
        }

        public E remove(int i) {
            return this.a.remove(i);
        }

        public E set(int i, E e) {
            return this.a.set(i, e);
        }

        public boolean contains(Object obj) {
            return this.a.contains(obj);
        }

        public int size() {
            return this.a.size();
        }
    }

    static final class CharSequenceAsList extends AbstractList<Character> {
        private final CharSequence a;

        CharSequenceAsList(CharSequence charSequence) {
            this.a = charSequence;
        }

        /* renamed from: a */
        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.a.charAt(i));
        }

        public int size() {
            return this.a.length();
        }
    }

    static class Partition<T> extends AbstractList<List<T>> {
        final List<T> a;
        final int b;

        Partition(List<T> list, int i) {
            this.a = list;
            this.b = i;
        }

        /* renamed from: a */
        public List<T> get(int i) {
            Preconditions.checkElementIndex(i, size());
            int i2 = i * this.b;
            return this.a.subList(i2, Math.min(this.b + i2, this.a.size()));
        }

        public int size() {
            return IntMath.divide(this.a.size(), this.b, RoundingMode.CEILING);
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }
    }

    static class RandomAccessListWrapper<E> extends AbstractListWrapper<E> implements RandomAccess {
        RandomAccessListWrapper(List<E> list) {
            super(list);
        }
    }

    static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
        RandomAccessPartition(List<T> list, int i) {
            super(list, i);
        }
    }

    static class RandomAccessReverseList<T> extends ReverseList<T> implements RandomAccess {
        RandomAccessReverseList(List<T> list) {
            super(list);
        }
    }

    static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final List<F> a;
        final Function<? super F, ? extends T> b;

        TransformingRandomAccessList(List<F> list, Function<? super F, ? extends T> function) {
            this.a = (List) Preconditions.checkNotNull(list);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.a.clear();
        }

        public T get(int i) {
            return this.b.apply(this.a.get(i));
        }

        public Iterator<T> iterator() {
            return listIterator();
        }

        public ListIterator<T> listIterator(int i) {
            return new TransformedListIterator<F, T>(this.a.listIterator(i)) {
                /* access modifiers changed from: 0000 */
                public T a(F f) {
                    return TransformingRandomAccessList.this.b.apply(f);
                }
            };
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public T remove(int i) {
            return this.b.apply(this.a.remove(i));
        }

        public int size() {
            return this.a.size();
        }
    }

    static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
        private static final long serialVersionUID = 0;
        final List<F> a;
        final Function<? super F, ? extends T> b;

        TransformingSequentialList(List<F> list, Function<? super F, ? extends T> function) {
            this.a = (List) Preconditions.checkNotNull(list);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.a.clear();
        }

        public int size() {
            return this.a.size();
        }

        public ListIterator<T> listIterator(int i) {
            return new TransformedListIterator<F, T>(this.a.listIterator(i)) {
                /* access modifiers changed from: 0000 */
                public T a(F f) {
                    return TransformingSequentialList.this.b.apply(f);
                }
            };
        }
    }

    private Lists() {
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    @GwtCompatible(serializable = true)
    @CanIgnoreReturnValue
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        Preconditions.checkNotNull(eArr);
        ArrayList<E> arrayList = new ArrayList<>(a(eArr.length));
        Collections.addAll(arrayList, eArr);
        return arrayList;
    }

    @VisibleForTesting
    static int a(int i) {
        CollectPreconditions.a(i, "arraySize");
        return Ints.saturatedCast(((long) i) + 5 + ((long) (i / 10)));
    }

    @GwtCompatible(serializable = true)
    @CanIgnoreReturnValue
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        return iterable instanceof Collection ? new ArrayList<>(Collections2.a(iterable)) : newArrayList(iterable.iterator());
    }

    @GwtCompatible(serializable = true)
    @CanIgnoreReturnValue
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> it) {
        ArrayList<E> newArrayList = newArrayList();
        Iterators.addAll(newArrayList, it);
        return newArrayList;
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithCapacity(int i) {
        CollectPreconditions.a(i, "initialArraySize");
        return new ArrayList<>(i);
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int i) {
        return new ArrayList<>(a(i));
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> newLinkedList = newLinkedList();
        Iterables.addAll(newLinkedList, iterable);
        return newLinkedList;
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> iterable) {
        return new CopyOnWriteArrayList<>(iterable instanceof Collection ? Collections2.a(iterable) : newArrayList(iterable));
    }

    public static <E> List<E> asList(@Nullable E e, E[] eArr) {
        return new OnePlusArrayList(e, eArr);
    }

    public static <E> List<E> asList(@Nullable E e, @Nullable E e2, E[] eArr) {
        return new TwoPlusArrayList(e, e2, eArr);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        return CartesianList.a(list);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends B>... listArr) {
        return cartesianProduct(Arrays.asList(listArr));
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list instanceof RandomAccess ? new TransformingRandomAccessList(list, function) : new TransformingSequentialList(list, function);
    }

    public static <T> List<List<T>> partition(List<T> list, int i) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(i > 0);
        return list instanceof RandomAccess ? new RandomAccessPartition(list, i) : new Partition(list, i);
    }

    public static ImmutableList<Character> charactersOf(String str) {
        return new StringAsImmutableList((String) Preconditions.checkNotNull(str));
    }

    @Beta
    public static List<Character> charactersOf(CharSequence charSequence) {
        return new CharSequenceAsList((CharSequence) Preconditions.checkNotNull(charSequence));
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList) list).reverse();
        }
        if (list instanceof ReverseList) {
            return ((ReverseList) list).a();
        }
        if (list instanceof RandomAccess) {
            return new RandomAccessReverseList(list);
        }
        return new ReverseList(list);
    }

    static int a(List<?> list) {
        int i;
        int i2 = 1;
        for (Object next : list) {
            int i3 = i2 * 31;
            if (next == null) {
                i = 0;
            } else {
                i = next.hashCode();
            }
            i2 = ((i3 + i) ^ -1) ^ -1;
        }
        return i2;
    }

    static boolean a(List<?> list, @Nullable Object obj) {
        if (obj == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list2 = (List) obj;
        int size = list.size();
        if (size != list2.size()) {
            return false;
        }
        if (!(list instanceof RandomAccess) || !(list2 instanceof RandomAccess)) {
            return Iterators.elementsEqual(list.iterator(), list2.iterator());
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equal(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    static <E> boolean a(List<E> list, int i, Iterable<? extends E> iterable) {
        ListIterator listIterator = list.listIterator(i);
        boolean z = false;
        for (Object add : iterable) {
            listIterator.add(add);
            z = true;
        }
        return z;
    }

    static int b(List<?> list, @Nullable Object obj) {
        if (list instanceof RandomAccess) {
            return d(list, obj);
        }
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    private static int d(List<?> list, @Nullable Object obj) {
        int size = list.size();
        int i = 0;
        if (obj == null) {
            while (i < size) {
                if (list.get(i) == null) {
                    return i;
                }
                i++;
            }
        } else {
            while (i < size) {
                if (obj.equals(list.get(i))) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    static int c(List<?> list, @Nullable Object obj) {
        if (list instanceof RandomAccess) {
            return e(list, obj);
        }
        ListIterator listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    private static int e(List<?> list, @Nullable Object obj) {
        if (obj == null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size) == null) {
                    return size;
                }
            }
        } else {
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                if (obj.equals(list.get(size2))) {
                    return size2;
                }
            }
        }
        return -1;
    }

    static <E> ListIterator<E> a(List<E> list, int i) {
        return new AbstractListWrapper(list).listIterator(i);
    }

    static <E> List<E> a(List<E> list, int i, int i2) {
        List list2;
        if (list instanceof RandomAccess) {
            list2 = new RandomAccessListWrapper<E>(list) {
                public ListIterator<E> listIterator(int i) {
                    return this.a.listIterator(i);
                }
            };
        } else {
            list2 = new AbstractListWrapper<E>(list) {
                public ListIterator<E> listIterator(int i) {
                    return this.a.listIterator(i);
                }
            };
        }
        return list2.subList(i, i2);
    }

    static <T> List<T> a(Iterable<T> iterable) {
        return (List) iterable;
    }
}
