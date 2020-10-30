package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    private static final long serialVersionUID = 0;
    private final Range<C> d;

    @GwtIncompatible
    static final class SerializedForm<C extends Comparable> implements Serializable {
        final Range<C> a;
        final DiscreteDomain<C> b;

        private SerializedForm(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.a = range;
            this.b = discreteDomain;
        }

        private Object readResolve() {
            return new RegularContiguousSet(this.a, this.b);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.d = range;
    }

    private ContiguousSet<C> a(Range<C> range) {
        return this.d.isConnected(range) ? ContiguousSet.create(this.d.intersection(range), this.a) : new EmptyContiguousSet(this.a);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public ContiguousSet<C> b(C c, boolean z) {
        return a(Range.upTo(c, BoundType.a(z)));
    }

    /* access modifiers changed from: 0000 */
    public ContiguousSet<C> a(C c, boolean z, C c2, boolean z2) {
        if (c.compareTo(c2) != 0 || z || z2) {
            return a(Range.range(c, BoundType.a(z), c2, BoundType.a(z2)));
        }
        return new EmptyContiguousSet(this.a);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public ContiguousSet<C> a(C c, boolean z) {
        return a(Range.downTo(c, BoundType.a(z)));
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public int a(Object obj) {
        if (contains(obj)) {
            return (int) this.a.distance(first(), (Comparable) obj);
        }
        return -1;
    }

    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) {
            final C a = RegularContiguousSet.this.last();

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public C computeNext(C c) {
                if (RegularContiguousSet.b((Comparable<?>) c, (Comparable<?>) this.a)) {
                    return null;
                }
                return RegularContiguousSet.this.a.next(c);
            }
        };
    }

    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) {
            final C a = RegularContiguousSet.this.first();

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public C computeNext(C c) {
                if (RegularContiguousSet.b((Comparable<?>) c, (Comparable<?>) this.a)) {
                    return null;
                }
                return RegularContiguousSet.this.a.previous(c);
            }
        };
    }

    /* access modifiers changed from: private */
    public static boolean b(Comparable<?> comparable, @Nullable Comparable<?> comparable2) {
        return comparable2 != null && Range.a((Comparable) comparable, (Comparable) comparable2) == 0;
    }

    /* renamed from: d */
    public C first() {
        return this.d.b.a(this.a);
    }

    /* renamed from: f */
    public C last() {
        return this.d.c.b(this.a);
    }

    public int size() {
        long distance = this.a.distance(first(), last());
        return distance >= 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : ((int) distance) + 1;
    }

    public boolean contains(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.d.contains((Comparable) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> collection) {
        return Collections2.a((Collection<?>) this, collection);
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Preconditions.checkNotNull(contiguousSet);
        Preconditions.checkArgument(this.a.equals(contiguousSet.a));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable = (Comparable) Ordering.natural().max(first(), contiguousSet.first());
        Comparable comparable2 = (Comparable) Ordering.natural().min(last(), contiguousSet.last());
        return comparable.compareTo(comparable2) <= 0 ? ContiguousSet.create(Range.closed(comparable, comparable2), this.a) : new EmptyContiguousSet<>(this.a);
    }

    public Range<C> range() {
        return range(BoundType.CLOSED, BoundType.CLOSED);
    }

    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.a(this.d.b.a(boundType, this.a), this.d.c.b(boundType2, this.a));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (obj instanceof RegularContiguousSet) {
            RegularContiguousSet regularContiguousSet = (RegularContiguousSet) obj;
            if (this.a.equals(regularContiguousSet.a)) {
                if (!first().equals(regularContiguousSet.first()) || !last().equals(regularContiguousSet.last())) {
                    z = false;
                }
                return z;
            }
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Sets.a(this);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.d, this.a);
    }
}
