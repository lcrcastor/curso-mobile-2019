package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class ImmutableRangeSet<C extends Comparable> extends AbstractRangeSet<C> implements Serializable {
    private static final ImmutableRangeSet<Comparable<?>> a = new ImmutableRangeSet<>(ImmutableList.of());
    private static final ImmutableRangeSet<Comparable<?>> b = new ImmutableRangeSet<>(ImmutableList.of(Range.all()));
    /* access modifiers changed from: private */
    public final transient ImmutableList<Range<C>> c;
    @LazyInit
    private transient ImmutableRangeSet<C> d;

    final class AsSet extends ImmutableSortedSet<C> {
        /* access modifiers changed from: private */
        public final DiscreteDomain<C> d;
        private transient Integer e;

        AsSet(DiscreteDomain<C> discreteDomain) {
            super(Ordering.natural());
            this.d = discreteDomain;
        }

        public int size() {
            Integer num = this.e;
            if (num == null) {
                long j = 0;
                Iterator it = ImmutableRangeSet.this.c.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    long size = j + ((long) ContiguousSet.create((Range) it.next(), this.d).size());
                    if (size >= 2147483647L) {
                        j = size;
                        break;
                    }
                    j = size;
                }
                num = Integer.valueOf(Ints.saturatedCast(j));
                this.e = num;
            }
            return num.intValue();
        }

        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>() {
                final Iterator<Range<C>> a = ImmutableRangeSet.this.c.iterator();
                Iterator<C> b = Iterators.a();

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public C computeNext() {
                    while (!this.b.hasNext()) {
                        if (!this.a.hasNext()) {
                            return (Comparable) endOfData();
                        }
                        this.b = ContiguousSet.create((Range) this.a.next(), AsSet.this.d).iterator();
                    }
                    return (Comparable) this.b.next();
                }
            };
        }

        @GwtIncompatible("NavigableSet")
        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>() {
                final Iterator<Range<C>> a = ImmutableRangeSet.this.c.reverse().iterator();
                Iterator<C> b = Iterators.a();

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public C computeNext() {
                    while (!this.b.hasNext()) {
                        if (!this.a.hasNext()) {
                            return (Comparable) endOfData();
                        }
                        this.b = ContiguousSet.create((Range) this.a.next(), AsSet.this.d).descendingIterator();
                    }
                    return (Comparable) this.b.next();
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSortedSet<C> a(Range<C> range) {
            return ImmutableRangeSet.this.subRangeSet(range).asSet(this.d);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: a */
        public ImmutableSortedSet<C> b(C c, boolean z) {
            return a(Range.upTo(c, BoundType.a(z)));
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSortedSet<C> a(C c, boolean z, C c2, boolean z2) {
            if (z || z2 || Range.a((Comparable) c, (Comparable) c2) != 0) {
                return a(Range.range(c, BoundType.a(z), c2, BoundType.a(z2)));
            }
            return ImmutableSortedSet.of();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public ImmutableSortedSet<C> a(C c, boolean z) {
            return a(Range.downTo(c, BoundType.a(z)));
        }

        public boolean contains(@Nullable Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                return ImmutableRangeSet.this.contains((Comparable) obj);
            } catch (ClassCastException unused) {
                return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public int a(Object obj) {
            if (!contains(obj)) {
                return -1;
            }
            Comparable comparable = (Comparable) obj;
            long j = 0;
            Iterator it = ImmutableRangeSet.this.c.iterator();
            while (it.hasNext()) {
                Range range = (Range) it.next();
                if (range.contains(comparable)) {
                    return Ints.saturatedCast(j + ((long) ContiguousSet.create(range, this.d).a((Object) comparable)));
                }
                j += (long) ContiguousSet.create(range, this.d).size();
            }
            throw new AssertionError("impossible");
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return ImmutableRangeSet.this.c.a();
        }

        public String toString() {
            return ImmutableRangeSet.this.c.toString();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new AsSetSerializedForm(ImmutableRangeSet.this.c, this.d);
        }
    }

    static class AsSetSerializedForm<C extends Comparable> implements Serializable {
        private final ImmutableList<Range<C>> a;
        private final DiscreteDomain<C> b;

        AsSetSerializedForm(ImmutableList<Range<C>> immutableList, DiscreteDomain<C> discreteDomain) {
            this.a = immutableList;
            this.b = discreteDomain;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return new ImmutableRangeSet(this.a).asSet(this.b);
        }
    }

    public static class Builder<C extends Comparable<?>> {
        private final RangeSet<C> a = TreeRangeSet.create();

        @CanIgnoreReturnValue
        public Builder<C> add(Range<C> range) {
            if (range.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("range must not be empty, but was ");
                sb.append(range);
                throw new IllegalArgumentException(sb.toString());
            } else if (!this.a.complement().encloses(range)) {
                for (Range range2 : this.a.asRanges()) {
                    Preconditions.checkArgument(!range2.isConnected(range) || range2.intersection(range).isEmpty(), "Ranges may not overlap, but received %s and %s", (Object) range2, (Object) range);
                }
                throw new AssertionError("should have thrown an IAE above");
            } else {
                this.a.add(range);
                return this;
            }
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(RangeSet<C> rangeSet) {
            for (Range add : rangeSet.asRanges()) {
                add(add);
            }
            return this;
        }

        public ImmutableRangeSet<C> build() {
            return ImmutableRangeSet.copyOf(this.a);
        }
    }

    final class ComplementRanges extends ImmutableList<Range<C>> {
        private final boolean b;
        private final boolean c;
        private final int d;

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return true;
        }

        ComplementRanges() {
            this.b = ((Range) ImmutableRangeSet.this.c.get(0)).hasLowerBound();
            this.c = ((Range) Iterables.getLast(ImmutableRangeSet.this.c)).hasUpperBound();
            int size = ImmutableRangeSet.this.c.size() - 1;
            if (this.b) {
                size++;
            }
            if (this.c) {
                size++;
            }
            this.d = size;
        }

        public int size() {
            return this.d;
        }

        /* renamed from: a */
        public Range<C> get(int i) {
            Cut<C> cut;
            Preconditions.checkElementIndex(i, this.d);
            Cut<C> cut2 = this.b ? i == 0 ? Cut.d() : ((Range) ImmutableRangeSet.this.c.get(i - 1)).c : ((Range) ImmutableRangeSet.this.c.get(i)).c;
            if (!this.c || i != this.d - 1) {
                cut = ((Range) ImmutableRangeSet.this.c.get(i + (this.b ^ true ? 1 : 0))).b;
            } else {
                cut = Cut.e();
            }
            return Range.a(cut2, cut);
        }
    }

    static final class SerializedForm<C extends Comparable> implements Serializable {
        private final ImmutableList<Range<C>> a;

        SerializedForm(ImmutableList<Range<C>> immutableList) {
            this.a = immutableList;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            if (this.a.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (this.a.equals(ImmutableList.of(Range.all()))) {
                return ImmutableRangeSet.a();
            }
            return new ImmutableRangeSet(this.a);
        }
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of() {
        return a;
    }

    static <C extends Comparable> ImmutableRangeSet<C> a() {
        return b;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return of();
        }
        if (range.equals(Range.all())) {
            return a();
        }
        return new ImmutableRangeSet<>(ImmutableList.of(range));
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return of();
        }
        if (rangeSet.encloses(Range.all())) {
            return a();
        }
        if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet<C> immutableRangeSet = (ImmutableRangeSet) rangeSet;
            if (!immutableRangeSet.b()) {
                return immutableRangeSet;
            }
        }
        return new ImmutableRangeSet<>(ImmutableList.copyOf((Collection<? extends E>) rangeSet.asRanges()));
    }

    ImmutableRangeSet(ImmutableList<Range<C>> immutableList) {
        this.c = immutableList;
    }

    private ImmutableRangeSet(ImmutableList<Range<C>> immutableList, ImmutableRangeSet<C> immutableRangeSet) {
        this.c = immutableList;
        this.d = immutableRangeSet;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005f, code lost:
        if (((com.google.common.collect.Range) r6.c.get(r0)).intersection(r7).isEmpty() == false) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean intersects(com.google.common.collect.Range<C> r7) {
        /*
            r6 = this;
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r0 = r6.c
            com.google.common.base.Function r1 = com.google.common.collect.Range.a()
            com.google.common.collect.Cut<C> r2 = r7.b
            com.google.common.collect.Ordering r3 = com.google.common.collect.Ordering.natural()
            com.google.common.collect.SortedLists$KeyPresentBehavior r4 = com.google.common.collect.SortedLists.KeyPresentBehavior.ANY_PRESENT
            com.google.common.collect.SortedLists$KeyAbsentBehavior r5 = com.google.common.collect.SortedLists.KeyAbsentBehavior.NEXT_HIGHER
            int r0 = com.google.common.collect.SortedLists.a(r0, r1, r2, r3, r4, r5)
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r1 = r6.c
            int r1 = r1.size()
            r2 = 1
            if (r0 >= r1) goto L_0x003e
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r1 = r6.c
            java.lang.Object r1 = r1.get(r0)
            com.google.common.collect.Range r1 = (com.google.common.collect.Range) r1
            boolean r1 = r1.isConnected(r7)
            if (r1 == 0) goto L_0x003e
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r1 = r6.c
            java.lang.Object r1 = r1.get(r0)
            com.google.common.collect.Range r1 = (com.google.common.collect.Range) r1
            com.google.common.collect.Range r1 = r1.intersection(r7)
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x003e
            return r2
        L_0x003e:
            if (r0 <= 0) goto L_0x0062
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r1 = r6.c
            int r0 = r0 - r2
            java.lang.Object r1 = r1.get(r0)
            com.google.common.collect.Range r1 = (com.google.common.collect.Range) r1
            boolean r1 = r1.isConnected(r7)
            if (r1 == 0) goto L_0x0062
            com.google.common.collect.ImmutableList<com.google.common.collect.Range<C>> r1 = r6.c
            java.lang.Object r0 = r1.get(r0)
            com.google.common.collect.Range r0 = (com.google.common.collect.Range) r0
            com.google.common.collect.Range r7 = r0.intersection(r7)
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L_0x0062
            goto L_0x0063
        L_0x0062:
            r2 = 0
        L_0x0063:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableRangeSet.intersects(com.google.common.collect.Range):boolean");
    }

    public boolean encloses(Range<C> range) {
        int a2 = SortedLists.a(this.c, Range.a(), range.b, Ordering.natural(), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        return a2 != -1 && ((Range) this.c.get(a2)).encloses(range);
    }

    public Range<C> rangeContaining(C c2) {
        int a2 = SortedLists.a(this.c, Range.a(), Cut.b(c2), Ordering.natural(), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        if (a2 == -1) {
            return null;
        }
        Range<C> range = (Range) this.c.get(a2);
        if (!range.contains(c2)) {
            range = null;
        }
        return range;
    }

    public Range<C> span() {
        if (!this.c.isEmpty()) {
            return Range.a(((Range) this.c.get(0)).b, ((Range) this.c.get(this.c.size() - 1)).c);
        }
        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Deprecated
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Range<C>> asRanges() {
        if (this.c.isEmpty()) {
            return ImmutableSet.of();
        }
        return new RegularImmutableSortedSet(this.c, Range.a);
    }

    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        if (this.c.isEmpty()) {
            return ImmutableSet.of();
        }
        return new RegularImmutableSortedSet(this.c.reverse(), Range.a.reverse());
    }

    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> immutableRangeSet = this.d;
        if (immutableRangeSet != null) {
            return immutableRangeSet;
        }
        if (this.c.isEmpty()) {
            ImmutableRangeSet<C> a2 = a();
            this.d = a2;
            return a2;
        } else if (this.c.size() != 1 || !((Range) this.c.get(0)).equals(Range.all())) {
            ImmutableRangeSet<C> immutableRangeSet2 = new ImmutableRangeSet<>(new ComplementRanges(), this);
            this.d = immutableRangeSet2;
            return immutableRangeSet2;
        } else {
            ImmutableRangeSet<C> of = of();
            this.d = of;
            return of;
        }
    }

    private ImmutableList<Range<C>> a(final Range<C> range) {
        int i;
        if (this.c.isEmpty() || range.isEmpty()) {
            return ImmutableList.of();
        }
        if (range.encloses(span())) {
            return this.c;
        }
        final int a2 = range.hasLowerBound() ? SortedLists.a((List<E>) this.c, Range.b(), range.b, KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER) : 0;
        if (range.hasUpperBound()) {
            i = SortedLists.a((List<E>) this.c, Range.a(), range.c, KeyPresentBehavior.FIRST_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
        } else {
            i = this.c.size();
        }
        final int i2 = i - a2;
        if (i2 == 0) {
            return ImmutableList.of();
        }
        return new ImmutableList<Range<C>>() {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return true;
            }

            public int size() {
                return i2;
            }

            /* renamed from: a */
            public Range<C> get(int i) {
                Preconditions.checkElementIndex(i, i2);
                if (i == 0 || i == i2 - 1) {
                    return ((Range) ImmutableRangeSet.this.c.get(i + a2)).intersection(range);
                }
                return (Range) ImmutableRangeSet.this.c.get(i + a2);
            }
        };
    }

    public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
        if (!isEmpty()) {
            Range span = span();
            if (range.encloses(span)) {
                return this;
            }
            if (range.isConnected(span)) {
                return new ImmutableRangeSet<>(a(range));
            }
        }
        return of();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (isEmpty()) {
            return ImmutableSortedSet.of();
        }
        Range canonical = span().canonical(discreteDomain);
        if (!canonical.hasLowerBound()) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
        }
        if (!canonical.hasUpperBound()) {
            try {
                discreteDomain.maxValue();
            } catch (NoSuchElementException unused) {
                throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
            }
        }
        return new AsSet(discreteDomain);
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.c.a();
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder<>();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this.c);
    }
}
