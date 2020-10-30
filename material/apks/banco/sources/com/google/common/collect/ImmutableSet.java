package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    @LazyInit
    private transient ImmutableList<E> a;

    static abstract class Indexed<E> extends ImmutableSet<E> {
        /* access modifiers changed from: 0000 */
        public abstract E a(int i);

        Indexed() {
        }

        public UnmodifiableIterator<E> iterator() {
            return asList().iterator();
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<E> c() {
            return new ImmutableAsList<E>() {
                public E get(int i) {
                    return Indexed.this.a(i);
                }

                /* access modifiers changed from: 0000 */
                /* renamed from: c */
                public Indexed<E> b() {
                    return Indexed.this;
                }
            };
        }
    }

    public static class Builder<E> extends ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        Builder(int i) {
            super(i);
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add(e);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            super.add(eArr);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll(iterable);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll(it);
            return this;
        }

        public ImmutableSet<E> build() {
            ImmutableSet<E> a = ImmutableSet.b(this.b, this.a);
            this.b = a.size();
            return a;
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] a;

        SerializedForm(Object[] objArr) {
            this.a = objArr;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return ImmutableSet.copyOf((E[]) this.a);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return false;
    }

    public abstract UnmodifiableIterator<E> iterator();

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.a;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return b(2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return b(3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return b(4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return b(5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        Object[] objArr = new Object[(eArr.length + 6)];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        objArr[3] = e4;
        objArr[4] = e5;
        objArr[5] = e6;
        System.arraycopy(eArr, 0, objArr, 6, eArr.length);
        return b(objArr.length, objArr);
    }

    /* access modifiers changed from: private */
    public static <E> ImmutableSet<E> b(int i, Object... objArr) {
        switch (i) {
            case 0:
                return of();
            case 1:
                return of(objArr[0]);
            default:
                int c = c(i);
                Object[] objArr2 = new Object[c];
                int i2 = c - 1;
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < i; i5++) {
                    Object a2 = ObjectArrays.a(objArr[i5], i5);
                    int hashCode = a2.hashCode();
                    int a3 = Hashing.a(hashCode);
                    while (true) {
                        int i6 = a3 & i2;
                        Object obj = objArr2[i6];
                        if (obj == null) {
                            int i7 = i3 + 1;
                            objArr[i3] = a2;
                            objArr2[i6] = a2;
                            i4 += hashCode;
                            i3 = i7;
                        } else if (!obj.equals(a2)) {
                            a3++;
                        }
                    }
                }
                Arrays.fill(objArr, i3, i, null);
                if (i3 == 1) {
                    return new SingletonImmutableSet(objArr[0], i4);
                }
                if (c != c(i3)) {
                    return b(i3, objArr);
                }
                if (i3 < objArr.length) {
                    objArr = ObjectArrays.a((T[]) objArr, i3);
                }
                return new RegularImmutableSet(objArr, i4, objArr2, i2);
        }
    }

    @VisibleForTesting
    static int c(int i) {
        boolean z = true;
        if (i < 751619276) {
            int highestOneBit = Integer.highestOneBit(i - 1) << 1;
            while (((double) highestOneBit) * 0.7d < ((double) i)) {
                highestOneBit <<= 1;
            }
            return highestOneBit;
        }
        if (i >= 1073741824) {
            z = false;
        }
        Preconditions.checkArgument(z, "collection too large");
        return 1073741824;
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof ImmutableSortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.a()) {
                return immutableSet;
            }
        } else if (collection instanceof EnumSet) {
            return a((EnumSet) collection);
        }
        Object[] array = collection.toArray();
        return b(array.length, array);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? copyOf((Collection) iterable) : copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return of(next);
        }
        return new Builder().add(next).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] eArr) {
        switch (eArr.length) {
            case 0:
                return of();
            case 1:
                return of(eArr[0]);
            default:
                return b(eArr.length, (Object[]) eArr.clone());
        }
    }

    private static ImmutableSet a(EnumSet enumSet) {
        return ImmutableEnumSet.a(EnumSet.copyOf(enumSet));
    }

    ImmutableSet() {
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableSet) || !e() || !((ImmutableSet) obj).e() || hashCode() == obj.hashCode()) {
            return Sets.a((Set<?>) this, obj);
        }
        return false;
    }

    public int hashCode() {
        return Sets.a(this);
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.a;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> c = c();
        this.a = c;
        return c;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> c() {
        return new RegularImmutableAsList((ImmutableCollection<E>) this, toArray());
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }
}
