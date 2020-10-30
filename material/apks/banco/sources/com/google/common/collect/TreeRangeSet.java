package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public class TreeRangeSet<C extends Comparable<?>> extends AbstractRangeSet<C> implements Serializable {
    @VisibleForTesting
    final NavigableMap<Cut<C>, Range<C>> a;
    private transient Set<Range<C>> b;
    private transient Set<Range<C>> c;
    private transient RangeSet<C> d;

    final class AsRanges extends ForwardingCollection<Range<C>> implements Set<Range<C>> {
        final Collection<Range<C>> a;

        AsRanges(Collection<Range<C>> collection) {
            this.a = collection;
        }

        /* access modifiers changed from: protected */
        public Collection<Range<C>> delegate() {
            return this.a;
        }

        public int hashCode() {
            return Sets.a(this);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }
    }

    static final class ComplementRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final NavigableMap<Cut<C>, Range<C>> a;
        private final NavigableMap<Cut<C>, Range<C>> b;
        /* access modifiers changed from: private */
        public final Range<Cut<C>> c;

        ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this(navigableMap, Range.all());
        }

        private ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.a = navigableMap;
            this.b = new RangesByUpperBound(navigableMap);
            this.c = range;
        }

        private NavigableMap<Cut<C>, Range<C>> a(Range<Cut<C>> range) {
            if (!this.c.isConnected(range)) {
                return ImmutableSortedMap.of();
            }
            return new ComplementRangesByLowerBound(this.a, range.intersection(this.c));
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return a(Range.range(cut, BoundType.a(z), cut2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return a(Range.upTo(cut, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return a(Range.downTo(cut, BoundType.a(z)));
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> b() {
            Collection collection;
            final Cut<C> cut;
            if (this.c.hasLowerBound()) {
                collection = this.b.tailMap(this.c.lowerEndpoint(), this.c.lowerBoundType() == BoundType.CLOSED).values();
            } else {
                collection = this.b.values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (this.c.contains(Cut.d()) && (!peekingIterator.hasNext() || ((Range) peekingIterator.peek()).b != Cut.d())) {
                cut = Cut.d();
            } else if (!peekingIterator.hasNext()) {
                return Iterators.a();
            } else {
                cut = ((Range) peekingIterator.next()).c;
            }
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                Cut<C> a = cut;

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    Range range;
                    if (ComplementRangesByLowerBound.this.c.c.a(this.a) || this.a == Cut.e()) {
                        return (Entry) endOfData();
                    }
                    if (peekingIterator.hasNext()) {
                        Range range2 = (Range) peekingIterator.next();
                        range = Range.a(this.a, range2.b);
                        this.a = range2.c;
                    } else {
                        range = Range.a(this.a, Cut.e());
                        this.a = Cut.e();
                    }
                    return Maps.immutableEntry(range.b, range);
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> a() {
            Cut<C> cut;
            final PeekingIterator peekingIterator = Iterators.peekingIterator(this.b.headMap(this.c.hasUpperBound() ? (Cut) this.c.upperEndpoint() : Cut.e(), this.c.hasUpperBound() && this.c.upperBoundType() == BoundType.CLOSED).descendingMap().values().iterator());
            if (peekingIterator.hasNext()) {
                cut = ((Range) peekingIterator.peek()).c == Cut.e() ? ((Range) peekingIterator.next()).b : (Cut) this.a.higherKey(((Range) peekingIterator.peek()).c);
            } else if (!this.c.contains(Cut.d()) || this.a.containsKey(Cut.d())) {
                return Iterators.a();
            } else {
                cut = (Cut) this.a.higherKey(Cut.d());
            }
            final Cut cut2 = (Cut) MoreObjects.firstNonNull(cut, Cut.e());
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                Cut<C> a = cut2;

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    if (this.a == Cut.d()) {
                        return (Entry) endOfData();
                    }
                    if (peekingIterator.hasNext()) {
                        Range range = (Range) peekingIterator.next();
                        Range a2 = Range.a(range.c, this.a);
                        this.a = range.b;
                        if (ComplementRangesByLowerBound.this.c.b.a(a2.b)) {
                            return Maps.immutableEntry(a2.b, a2);
                        }
                    } else if (ComplementRangesByLowerBound.this.c.b.a(Cut.d())) {
                        Range a3 = Range.a(Cut.d(), this.a);
                        this.a = Cut.d();
                        return Maps.immutableEntry(Cut.d(), a3);
                    }
                    return (Entry) endOfData();
                }
            };
        }

        public int size() {
            return Iterators.size(b());
        }

        @Nullable
        /* renamed from: a */
        public Range<C> get(Object obj) {
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    Entry firstEntry = tailMap(cut, true).firstEntry();
                    if (firstEntry != null && ((Cut) firstEntry.getKey()).equals(cut)) {
                        return (Range) firstEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }
    }

    @VisibleForTesting
    static final class RangesByUpperBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final NavigableMap<Cut<C>, Range<C>> a;
        /* access modifiers changed from: private */
        public final Range<Cut<C>> b;

        RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.a = navigableMap;
            this.b = Range.all();
        }

        private RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.a = navigableMap;
            this.b = range;
        }

        private NavigableMap<Cut<C>, Range<C>> a(Range<Cut<C>> range) {
            if (range.isConnected(this.b)) {
                return new RangesByUpperBound(this.a, range.intersection(this.b));
            }
            return ImmutableSortedMap.of();
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return a(Range.range(cut, BoundType.a(z), cut2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return a(Range.upTo(cut, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return a(Range.downTo(cut, BoundType.a(z)));
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        public boolean containsKey(@Nullable Object obj) {
            return get(obj) != null;
        }

        /* renamed from: a */
        public Range<C> get(@Nullable Object obj) {
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    if (!this.b.contains(cut)) {
                        return null;
                    }
                    Entry lowerEntry = this.a.lowerEntry(cut);
                    if (lowerEntry != null && ((Range) lowerEntry.getValue()).c.equals(cut)) {
                        return (Range) lowerEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> b() {
            final Iterator it;
            if (!this.b.hasLowerBound()) {
                it = this.a.values().iterator();
            } else {
                Entry lowerEntry = this.a.lowerEntry(this.b.lowerEndpoint());
                if (lowerEntry == null) {
                    it = this.a.values().iterator();
                } else if (this.b.b.a(((Range) lowerEntry.getValue()).c)) {
                    it = this.a.tailMap(lowerEntry.getKey(), true).values().iterator();
                } else {
                    it = this.a.tailMap(this.b.lowerEndpoint(), true).values().iterator();
                }
            }
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (RangesByUpperBound.this.b.c.a(range.c)) {
                        return (Entry) endOfData();
                    }
                    return Maps.immutableEntry(range.c, range);
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> a() {
            Collection collection;
            if (this.b.hasUpperBound()) {
                collection = this.a.headMap(this.b.upperEndpoint(), false).descendingMap().values();
            } else {
                collection = this.a.descendingMap().values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (peekingIterator.hasNext() && this.b.c.a(((Range) peekingIterator.peek()).c)) {
                peekingIterator.next();
            }
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    if (!peekingIterator.hasNext()) {
                        return (Entry) endOfData();
                    }
                    Range range = (Range) peekingIterator.next();
                    return RangesByUpperBound.this.b.b.a(range.c) ? Maps.immutableEntry(range.c, range) : (Entry) endOfData();
                }
            };
        }

        public int size() {
            if (this.b.equals(Range.all())) {
                return this.a.size();
            }
            return Iterators.size(b());
        }

        public boolean isEmpty() {
            if (this.b.equals(Range.all())) {
                return this.a.isEmpty();
            }
            return !b().hasNext();
        }
    }

    final class SubRangeSet extends TreeRangeSet<C> {
        private final Range<C> c;

        SubRangeSet(Range<C> range) {
            super(new SubRangeSetRangesByLowerBound(Range.all(), range, TreeRangeSet.this.a));
            this.c = range;
        }

        public boolean encloses(Range<C> range) {
            boolean z = false;
            if (this.c.isEmpty() || !this.c.encloses(range)) {
                return false;
            }
            Range a = TreeRangeSet.this.a(range);
            if (a != null && !a.intersection(this.c).isEmpty()) {
                z = true;
            }
            return z;
        }

        @Nullable
        public Range<C> rangeContaining(C c2) {
            Range<C> range = null;
            if (!this.c.contains(c2)) {
                return null;
            }
            Range rangeContaining = TreeRangeSet.this.rangeContaining(c2);
            if (rangeContaining != null) {
                range = rangeContaining.intersection(this.c);
            }
            return range;
        }

        public void add(Range<C> range) {
            Preconditions.checkArgument(this.c.encloses(range), "Cannot add range %s to subRangeSet(%s)", (Object) range, (Object) this.c);
            TreeRangeSet.super.add(range);
        }

        public void remove(Range<C> range) {
            if (range.isConnected(this.c)) {
                TreeRangeSet.this.remove(range.intersection(this.c));
            }
        }

        public boolean contains(C c2) {
            return this.c.contains(c2) && TreeRangeSet.this.contains(c2);
        }

        public void clear() {
            TreeRangeSet.this.remove(this.c);
        }

        public RangeSet<C> subRangeSet(Range<C> range) {
            if (range.encloses(this.c)) {
                return this;
            }
            if (range.isConnected(this.c)) {
                return new SubRangeSet(this.c.intersection(range));
            }
            return ImmutableRangeSet.of();
        }
    }

    static final class SubRangeSetRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        /* access modifiers changed from: private */
        public final Range<Cut<C>> a;
        /* access modifiers changed from: private */
        public final Range<C> b;
        private final NavigableMap<Cut<C>, Range<C>> c;
        private final NavigableMap<Cut<C>, Range<C>> d;

        private SubRangeSetRangesByLowerBound(Range<Cut<C>> range, Range<C> range2, NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.a = (Range) Preconditions.checkNotNull(range);
            this.b = (Range) Preconditions.checkNotNull(range2);
            this.c = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.d = new RangesByUpperBound(navigableMap);
        }

        private NavigableMap<Cut<C>, Range<C>> a(Range<Cut<C>> range) {
            if (!range.isConnected(this.a)) {
                return ImmutableSortedMap.of();
            }
            return new SubRangeSetRangesByLowerBound(this.a.intersection(range), this.b, this.c);
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return a(Range.range(cut, BoundType.a(z), cut2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return a(Range.upTo(cut, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return a(Range.downTo(cut, BoundType.a(z)));
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        public boolean containsKey(@Nullable Object obj) {
            return get(obj) != null;
        }

        @Nullable
        /* renamed from: a */
        public Range<C> get(@Nullable Object obj) {
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    if (this.a.contains(cut) && cut.compareTo(this.b.b) >= 0) {
                        if (cut.compareTo(this.b.c) < 0) {
                            if (cut.equals(this.b.b)) {
                                Range range = (Range) Maps.c(this.c.floorEntry(cut));
                                if (range != null && range.c.compareTo(this.b.b) > 0) {
                                    return range.intersection(this.b);
                                }
                            } else {
                                Range range2 = (Range) this.c.get(cut);
                                if (range2 != null) {
                                    return range2.intersection(this.b);
                                }
                            }
                        }
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> b() {
            final Iterator it;
            if (this.b.isEmpty()) {
                return Iterators.a();
            }
            if (this.a.c.a(this.b.b)) {
                return Iterators.a();
            }
            boolean z = false;
            if (this.a.b.a(this.b.b)) {
                it = this.d.tailMap(this.b.b, false).values().iterator();
            } else {
                NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
                Comparable c2 = this.a.b.c();
                if (this.a.lowerBoundType() == BoundType.CLOSED) {
                    z = true;
                }
                it = navigableMap.tailMap(c2, z).values().iterator();
            }
            final Cut cut = (Cut) Ordering.natural().min(this.a.c, Cut.b(this.b.c));
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (cut.a(range.b)) {
                        return (Entry) endOfData();
                    }
                    Range intersection = range.intersection(SubRangeSetRangesByLowerBound.this.b);
                    return Maps.immutableEntry(intersection.b, intersection);
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Cut<C>, Range<C>>> a() {
            if (this.b.isEmpty()) {
                return Iterators.a();
            }
            Cut cut = (Cut) Ordering.natural().min(this.a.c, Cut.b(this.b.c));
            final Iterator it = this.c.headMap(cut.c(), cut.b() == BoundType.CLOSED).descendingMap().values().iterator();
            return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<Cut<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (SubRangeSetRangesByLowerBound.this.b.b.compareTo(range.c) >= 0) {
                        return (Entry) endOfData();
                    }
                    Range intersection = range.intersection(SubRangeSetRangesByLowerBound.this.b);
                    if (SubRangeSetRangesByLowerBound.this.a.contains(intersection.b)) {
                        return Maps.immutableEntry(intersection.b, intersection);
                    }
                    return (Entry) endOfData();
                }
            };
        }

        public int size() {
            return Iterators.size(b());
        }
    }

    final class Complement extends TreeRangeSet<C> {
        Complement() {
            super(new ComplementRangesByLowerBound(TreeRangeSet.this.a));
        }

        public void add(Range<C> range) {
            TreeRangeSet.this.remove(range);
        }

        public void remove(Range<C> range) {
            TreeRangeSet.this.add(range);
        }

        public boolean contains(C c) {
            return !TreeRangeSet.this.contains(c);
        }

        public RangeSet<C> complement() {
            return TreeRangeSet.this;
        }
    }

    public /* bridge */ /* synthetic */ void addAll(RangeSet rangeSet) {
        super.addAll(rangeSet);
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

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ void removeAll(RangeSet rangeSet) {
        super.removeAll(rangeSet);
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create() {
        return new TreeRangeSet<>(new TreeMap());
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> rangeSet) {
        TreeRangeSet<C> create = create();
        create.addAll(rangeSet);
        return create;
    }

    private TreeRangeSet(NavigableMap<Cut<C>, Range<C>> navigableMap) {
        this.a = navigableMap;
    }

    public Set<Range<C>> asRanges() {
        Set<Range<C>> set = this.b;
        if (set != null) {
            return set;
        }
        AsRanges asRanges = new AsRanges(this.a.values());
        this.b = asRanges;
        return asRanges;
    }

    public Set<Range<C>> asDescendingSetOfRanges() {
        Set<Range<C>> set = this.c;
        if (set != null) {
            return set;
        }
        AsRanges asRanges = new AsRanges(this.a.descendingMap().values());
        this.c = asRanges;
        return asRanges;
    }

    @Nullable
    public Range<C> rangeContaining(C c2) {
        Preconditions.checkNotNull(c2);
        Entry floorEntry = this.a.floorEntry(Cut.b(c2));
        if (floorEntry == null || !((Range) floorEntry.getValue()).contains(c2)) {
            return null;
        }
        return (Range) floorEntry.getValue();
    }

    public boolean intersects(Range<C> range) {
        Preconditions.checkNotNull(range);
        Entry ceilingEntry = this.a.ceilingEntry(range.b);
        boolean z = true;
        if (ceilingEntry != null && ((Range) ceilingEntry.getValue()).isConnected(range) && !((Range) ceilingEntry.getValue()).intersection(range).isEmpty()) {
            return true;
        }
        Entry lowerEntry = this.a.lowerEntry(range.b);
        if (lowerEntry == null || !((Range) lowerEntry.getValue()).isConnected(range) || ((Range) lowerEntry.getValue()).intersection(range).isEmpty()) {
            z = false;
        }
        return z;
    }

    public boolean encloses(Range<C> range) {
        Preconditions.checkNotNull(range);
        Entry floorEntry = this.a.floorEntry(range.b);
        return floorEntry != null && ((Range) floorEntry.getValue()).encloses(range);
    }

    /* access modifiers changed from: private */
    @Nullable
    public Range<C> a(Range<C> range) {
        Preconditions.checkNotNull(range);
        Entry floorEntry = this.a.floorEntry(range.b);
        if (floorEntry == null || !((Range) floorEntry.getValue()).encloses(range)) {
            return null;
        }
        return (Range) floorEntry.getValue();
    }

    public Range<C> span() {
        Entry firstEntry = this.a.firstEntry();
        Entry lastEntry = this.a.lastEntry();
        if (firstEntry != null) {
            return Range.a(((Range) firstEntry.getValue()).b, ((Range) lastEntry.getValue()).c);
        }
        throw new NoSuchElementException();
    }

    public void add(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            Cut<C> cut = range.b;
            Cut<C> cut2 = range.c;
            Entry lowerEntry = this.a.lowerEntry(cut);
            if (lowerEntry != null) {
                Range range2 = (Range) lowerEntry.getValue();
                if (range2.c.compareTo(cut) >= 0) {
                    if (range2.c.compareTo(cut2) >= 0) {
                        cut2 = range2.c;
                    }
                    cut = range2.b;
                }
            }
            Entry floorEntry = this.a.floorEntry(cut2);
            if (floorEntry != null) {
                Range range3 = (Range) floorEntry.getValue();
                if (range3.c.compareTo(cut2) >= 0) {
                    cut2 = range3.c;
                }
            }
            this.a.subMap(cut, cut2).clear();
            b(Range.a(cut, cut2));
        }
    }

    public void remove(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            Entry lowerEntry = this.a.lowerEntry(range.b);
            if (lowerEntry != null) {
                Range range2 = (Range) lowerEntry.getValue();
                if (range2.c.compareTo(range.b) >= 0) {
                    if (range.hasUpperBound() && range2.c.compareTo(range.c) >= 0) {
                        b(Range.a(range.c, range2.c));
                    }
                    b(Range.a(range2.b, range.b));
                }
            }
            Entry floorEntry = this.a.floorEntry(range.c);
            if (floorEntry != null) {
                Range range3 = (Range) floorEntry.getValue();
                if (range.hasUpperBound() && range3.c.compareTo(range.c) >= 0) {
                    b(Range.a(range.c, range3.c));
                }
            }
            this.a.subMap(range.b, range.c).clear();
        }
    }

    private void b(Range<C> range) {
        if (range.isEmpty()) {
            this.a.remove(range.b);
        } else {
            this.a.put(range.b, range);
        }
    }

    public RangeSet<C> complement() {
        RangeSet<C> rangeSet = this.d;
        if (rangeSet != null) {
            return rangeSet;
        }
        Complement complement = new Complement();
        this.d = complement;
        return complement;
    }

    public RangeSet<C> subRangeSet(Range<C> range) {
        return range.equals(Range.all()) ? this : new SubRangeSet(range);
    }
}
