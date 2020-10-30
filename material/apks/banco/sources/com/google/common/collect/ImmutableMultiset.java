package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMultiset<E> extends ImmutableCollection<E> implements Multiset<E> {
    @LazyInit
    private transient ImmutableList<E> a;
    @LazyInit
    private transient ImmutableSet<Entry<E>> b;

    public static class Builder<E> extends com.google.common.collect.ImmutableCollection.Builder<E> {
        final Multiset<E> a;

        public Builder() {
            this(LinkedHashMultiset.create());
        }

        Builder(Multiset<E> multiset) {
            this.a = multiset;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            this.a.add(Preconditions.checkNotNull(e));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e, int i) {
            this.a.add(Preconditions.checkNotNull(e), i);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> setCount(E e, int i) {
            this.a.setCount(Preconditions.checkNotNull(e), i);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            super.add(eArr);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Multiset) {
                for (Entry entry : Multisets.b(iterable).entrySet()) {
                    addCopies(entry.getElement(), entry.getCount());
                }
            } else {
                super.addAll(iterable);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll(it);
            return this;
        }

        public ImmutableMultiset<E> build() {
            return ImmutableMultiset.copyOf((Iterable<? extends E>) this.a);
        }
    }

    final class EntrySet extends Indexed<Entry<E>> {
        private static final long serialVersionUID = 0;

        private EntrySet() {
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return ImmutableMultiset.this.a();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Entry<E> a(int i) {
            return ImmutableMultiset.this.a(i);
        }

        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (entry.getCount() <= 0) {
                return false;
            }
            if (ImmutableMultiset.this.count(entry.getElement()) == entry.getCount()) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }
    }

    static class EntrySetSerializedForm<E> implements Serializable {
        final ImmutableMultiset<E> a;

        EntrySetSerializedForm(ImmutableMultiset<E> immutableMultiset) {
            this.a = immutableMultiset;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.entrySet();
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] a;
        final int[] b;

        SerializedForm(Multiset<?> multiset) {
            int size = multiset.entrySet().size();
            this.a = new Object[size];
            this.b = new int[size];
            int i = 0;
            for (Entry entry : multiset.entrySet()) {
                this.a[i] = entry.getElement();
                this.b[i] = entry.getCount();
                i++;
            }
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            LinkedHashMultiset create = LinkedHashMultiset.create(this.a.length);
            for (int i = 0; i < this.a.length; i++) {
                create.add(this.a[i], this.b[i]);
            }
            return ImmutableMultiset.copyOf((Iterable<? extends E>) create);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Entry<E> a(int i);

    public static <E> ImmutableMultiset<E> of() {
        return RegularImmutableMultiset.a;
    }

    public static <E> ImmutableMultiset<E> of(E e) {
        return a((E[]) new Object[]{e});
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2) {
        return a((E[]) new Object[]{e, e2});
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3) {
        return a((E[]) new Object[]{e, e2, e3});
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4) {
        return a((E[]) new Object[]{e, e2, e3, e4});
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return a((E[]) new Object[]{e, e2, e3, e4, e5});
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        return new Builder().add((Object) e).add((Object) e2).add((Object) e3).add((Object) e4).add((Object) e5).add((Object) e6).add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] eArr) {
        return a(eArr);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableMultiset) {
            ImmutableMultiset<E> immutableMultiset = (ImmutableMultiset) iterable;
            if (!immutableMultiset.a()) {
                return immutableMultiset;
            }
        }
        return a((Collection<? extends Entry<? extends E>>) (iterable instanceof Multiset ? Multisets.b(iterable) : LinkedHashMultiset.create(iterable)).entrySet());
    }

    private static <E> ImmutableMultiset<E> a(E... eArr) {
        LinkedHashMultiset create = LinkedHashMultiset.create();
        Collections.addAll(create, eArr);
        return a((Collection<? extends Entry<? extends E>>) create.entrySet());
    }

    static <E> ImmutableMultiset<E> a(Collection<? extends Entry<? extends E>> collection) {
        if (collection.isEmpty()) {
            return of();
        }
        return new RegularImmutableMultiset(collection);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> it) {
        LinkedHashMultiset create = LinkedHashMultiset.create();
        Iterators.addAll(create, it);
        return a((Collection<? extends Entry<? extends E>>) create.entrySet());
    }

    ImmutableMultiset() {
    }

    public UnmodifiableIterator<E> iterator() {
        final UnmodifiableIterator it = entrySet().iterator();
        return new UnmodifiableIterator<E>() {
            int a;
            E b;

            public boolean hasNext() {
                return this.a > 0 || it.hasNext();
            }

            public E next() {
                if (this.a <= 0) {
                    Entry entry = (Entry) it.next();
                    this.b = entry.getElement();
                    this.a = entry.getCount();
                }
                this.a--;
                return this.b;
            }
        };
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.a;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> b2 = b();
        this.a = b2;
        return b2;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> b() {
        if (isEmpty()) {
            return ImmutableList.of();
        }
        return new RegularImmutableAsList((ImmutableCollection<E>) this, toArray());
    }

    public boolean contains(@Nullable Object obj) {
        return count(obj) > 0;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int add(E e, int i) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int remove(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int setCount(E e, int i) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean setCount(E e, int i, int i2) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public int a(Object[] objArr, int i) {
        Iterator it = entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Arrays.fill(objArr, i, entry.getCount() + i, entry.getElement());
            i += entry.getCount();
        }
        return i;
    }

    public boolean equals(@Nullable Object obj) {
        return Multisets.a((Multiset<?>) this, obj);
    }

    public int hashCode() {
        return Sets.a(entrySet());
    }

    public String toString() {
        return entrySet().toString();
    }

    public ImmutableSet<Entry<E>> entrySet() {
        ImmutableSet<Entry<E>> immutableSet = this.b;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Entry<E>> c = c();
        this.b = c;
        return c;
    }

    private final ImmutableSet<Entry<E>> c() {
        return isEmpty() ? ImmutableSet.of() : new EntrySet();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }
}
