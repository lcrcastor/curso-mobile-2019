package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Range<C extends Comparable> implements Predicate<C>, Serializable {
    static final Ordering<Range<?>> a = new RangeLexOrdering();
    private static final Function<Range, Cut> d = new Function<Range, Cut>() {
        /* renamed from: a */
        public Cut apply(Range range) {
            return range.b;
        }
    };
    private static final Function<Range, Cut> e = new Function<Range, Cut>() {
        /* renamed from: a */
        public Cut apply(Range range) {
            return range.c;
        }
    };
    private static final Range<Comparable> f = new Range<>(Cut.d(), Cut.e());
    private static final long serialVersionUID = 0;
    final Cut<C> b;
    final Cut<C> c;

    static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
        private static final long serialVersionUID = 0;

        private RangeLexOrdering() {
        }

        /* renamed from: a */
        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare((Comparable<?>) range.b, (Comparable<?>) range2.b).compare((Comparable<?>) range.c, (Comparable<?>) range2.c).result();
        }
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> a() {
        return d;
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> b() {
        return e;
    }

    static <C extends Comparable<?>> Range<C> a(Cut<C> cut, Cut<C> cut2) {
        return new Range<>(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> open(C c2, C c3) {
        return a(Cut.c(c2), Cut.b(c3));
    }

    public static <C extends Comparable<?>> Range<C> closed(C c2, C c3) {
        return a(Cut.b(c2), Cut.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c2, C c3) {
        return a(Cut.b(c2), Cut.b(c3));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c2, C c3) {
        return a(Cut.c(c2), Cut.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> range(C c2, BoundType boundType, C c3, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return a(boundType == BoundType.OPEN ? Cut.c(c2) : Cut.b(c2), boundType2 == BoundType.OPEN ? Cut.b(c3) : Cut.c(c3));
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c2) {
        return a(Cut.d(), Cut.b(c2));
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c2) {
        return a(Cut.d(), Cut.c(c2));
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c2, BoundType boundType) {
        switch (boundType) {
            case OPEN:
                return lessThan(c2);
            case CLOSED:
                return atMost(c2);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c2) {
        return a(Cut.c(c2), Cut.e());
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c2) {
        return a(Cut.b(c2), Cut.e());
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c2, BoundType boundType) {
        switch (boundType) {
            case OPEN:
                return greaterThan(c2);
            case CLOSED:
                return atLeast(c2);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return f;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c2) {
        return closed(c2, c2);
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof ContiguousSet) {
            return ((ContiguousSet) iterable).range();
        }
        Iterator it = iterable.iterator();
        Comparable comparable = (Comparable) Preconditions.checkNotNull(it.next());
        Comparable comparable2 = comparable;
        while (it.hasNext()) {
            Comparable comparable3 = (Comparable) Preconditions.checkNotNull(it.next());
            comparable = (Comparable) Ordering.natural().min(comparable, comparable3);
            comparable2 = (Comparable) Ordering.natural().max(comparable2, comparable3);
        }
        return closed(comparable, comparable2);
    }

    private Range(Cut<C> cut, Cut<C> cut2) {
        this.b = (Cut) Preconditions.checkNotNull(cut);
        this.c = (Cut) Preconditions.checkNotNull(cut2);
        if (cut.compareTo(cut2) > 0 || cut == Cut.e() || cut2 == Cut.d()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid range: ");
            sb.append(b(cut, cut2));
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean hasLowerBound() {
        return this.b != Cut.d();
    }

    public C lowerEndpoint() {
        return this.b.c();
    }

    public BoundType lowerBoundType() {
        return this.b.a();
    }

    public boolean hasUpperBound() {
        return this.c != Cut.e();
    }

    public C upperEndpoint() {
        return this.c.c();
    }

    public BoundType upperBoundType() {
        return this.c.b();
    }

    public boolean isEmpty() {
        return this.b.equals(this.c);
    }

    public boolean contains(C c2) {
        Preconditions.checkNotNull(c2);
        return this.b.a(c2) && !this.c.a(c2);
    }

    @Deprecated
    public boolean apply(C c2) {
        return contains(c2);
    }

    public boolean containsAll(Iterable<? extends C> iterable) {
        boolean z = true;
        if (Iterables.isEmpty(iterable)) {
            return true;
        }
        if (iterable instanceof SortedSet) {
            SortedSet a2 = a(iterable);
            Comparator comparator = a2.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                if (!contains((Comparable) a2.first()) || !contains((Comparable) a2.last())) {
                    z = false;
                }
                return z;
            }
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            if (!contains((Comparable) it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> range) {
        return this.b.compareTo(range.b) <= 0 && this.c.compareTo(range.c) >= 0;
    }

    public boolean isConnected(Range<C> range) {
        return this.b.compareTo(range.c) <= 0 && range.b.compareTo(this.c) <= 0;
    }

    public Range<C> intersection(Range<C> range) {
        int a2 = this.b.compareTo(range.b);
        int a3 = this.c.compareTo(range.c);
        if (a2 >= 0 && a3 <= 0) {
            return this;
        }
        if (a2 <= 0 && a3 >= 0) {
            return range;
        }
        return a(a2 >= 0 ? this.b : range.b, a3 <= 0 ? this.c : range.c);
    }

    public Range<C> span(Range<C> range) {
        int a2 = this.b.compareTo(range.b);
        int a3 = this.c.compareTo(range.c);
        if (a2 <= 0 && a3 >= 0) {
            return this;
        }
        if (a2 >= 0 && a3 <= 0) {
            return range;
        }
        return a(a2 <= 0 ? this.b : range.b, a3 >= 0 ? this.c : range.c);
    }

    public Range<C> canonical(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        Cut<C> c2 = this.b.c(discreteDomain);
        Cut<C> c3 = this.c.c(discreteDomain);
        return (c2 == this.b && c3 == this.c) ? this : a(c2, c3);
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Range)) {
            return false;
        }
        Range range = (Range) obj;
        if (this.b.equals(range.b) && this.c.equals(range.c)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.c.hashCode();
    }

    public String toString() {
        return b(this.b, this.c);
    }

    private static String b(Cut<?> cut, Cut<?> cut2) {
        StringBuilder sb = new StringBuilder(16);
        cut.a(sb);
        sb.append("..");
        cut2.b(sb);
        return sb.toString();
    }

    private static <T> SortedSet<T> a(Iterable<T> iterable) {
        return (SortedSet) iterable;
    }

    /* access modifiers changed from: 0000 */
    public Object readResolve() {
        return equals(f) ? all() : this;
    }

    static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }
}
