package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C> {

    @GwtIncompatible
    static final class SerializedForm<C extends Comparable> implements Serializable {
        private static final long serialVersionUID = 0;
        private final DiscreteDomain<C> a;

        private SerializedForm(DiscreteDomain<C> discreteDomain) {
            this.a = discreteDomain;
        }

        private Object readResolve() {
            return new EmptyContiguousSet(this.a);
        }
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public int a(Object obj) {
        return -1;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public ContiguousSet<C> b(C c, boolean z) {
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ContiguousSet<C> a(C c, boolean z, C c2, boolean z2) {
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public ContiguousSet<C> a(C c, boolean z) {
        return this;
    }

    public boolean contains(Object obj) {
        return false;
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public boolean e() {
        return true;
    }

    public int hashCode() {
        return 0;
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    public String toString() {
        return "[]";
    }

    EmptyContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
    }

    /* renamed from: c_ */
    public C first() {
        throw new NoSuchElementException();
    }

    /* renamed from: d */
    public C last() {
        throw new NoSuchElementException();
    }

    public Range<C> range() {
        throw new NoSuchElementException();
    }

    public Range<C> range(BoundType boundType, BoundType boundType2) {
        throw new NoSuchElementException();
    }

    public UnmodifiableIterator<C> iterator() {
        return Iterators.a();
    }

    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.a();
    }

    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Set) {
            return ((Set) obj).isEmpty();
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.a);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public ImmutableSortedSet<C> b() {
        return ImmutableSortedSet.a((Comparator<? super E>) Ordering.natural().reverse());
    }
}
