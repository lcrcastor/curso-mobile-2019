package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> a = new ImmutableRangeMap<>(ImmutableList.of(), ImmutableList.of());
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final transient ImmutableList<Range<K>> b;
    private final transient ImmutableList<V> c;

    public static final class Builder<K extends Comparable<?>, V> {
        private final RangeSet<K> a = TreeRangeSet.create();
        private final RangeMap<K, V> b = TreeRangeMap.create();

        @CanIgnoreReturnValue
        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(v);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", (Object) range);
            if (!this.a.complement().encloses(range)) {
                for (Entry entry : this.b.asMapOfRanges().entrySet()) {
                    Range range2 = (Range) entry.getKey();
                    if (range2.isConnected(range) && !range2.intersection(range).isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Overlapping ranges: range ");
                        sb.append(range);
                        sb.append(" overlaps with entry ");
                        sb.append(entry);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            }
            this.a.add(range);
            this.b.put(range, v);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Entry entry : rangeMap.asMapOfRanges().entrySet()) {
                put((Range) entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableRangeMap<K, V> build() {
            Map asMapOfRanges = this.b.asMapOfRanges();
            com.google.common.collect.ImmutableList.Builder builder = new com.google.common.collect.ImmutableList.Builder(asMapOfRanges.size());
            com.google.common.collect.ImmutableList.Builder builder2 = new com.google.common.collect.ImmutableList.Builder(asMapOfRanges.size());
            for (Entry entry : asMapOfRanges.entrySet()) {
                builder.add(entry.getKey());
                builder2.add(entry.getValue());
            }
            return new ImmutableRangeMap<>(builder.build(), builder2.build());
        }
    }

    static class SerializedForm<K extends Comparable<?>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableMap<Range<K>, V> a;

        SerializedForm(ImmutableMap<Range<K>, V> immutableMap) {
            this.a = immutableMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            if (this.a.isEmpty()) {
                return ImmutableRangeMap.of();
            }
            return a();
        }

        /* access modifiers changed from: 0000 */
        public Object a() {
            Builder builder = new Builder();
            Iterator it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                builder.put((Range) entry.getKey(), entry.getValue());
            }
            return builder.build();
        }
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return a;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V v) {
        return new ImmutableRangeMap<>(ImmutableList.of(range), ImmutableList.of(v));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map asMapOfRanges = rangeMap.asMapOfRanges();
        com.google.common.collect.ImmutableList.Builder builder = new com.google.common.collect.ImmutableList.Builder(asMapOfRanges.size());
        com.google.common.collect.ImmutableList.Builder builder2 = new com.google.common.collect.ImmutableList.Builder(asMapOfRanges.size());
        for (Entry entry : asMapOfRanges.entrySet()) {
            builder.add(entry.getKey());
            builder2.add(entry.getValue());
        }
        return new ImmutableRangeMap<>(builder.build(), builder2.build());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    ImmutableRangeMap(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.b = immutableList;
        this.c = immutableList2;
    }

    @Nullable
    public V get(K k) {
        int a2 = SortedLists.a((List<E>) this.b, Range.a(), Cut.b(k), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        V v = null;
        if (a2 == -1) {
            return null;
        }
        if (((Range) this.b.get(a2)).contains(k)) {
            v = this.c.get(a2);
        }
        return v;
    }

    @Nullable
    public Entry<Range<K>, V> getEntry(K k) {
        int a2 = SortedLists.a((List<E>) this.b, Range.a(), Cut.b(k), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        Entry<Range<K>, V> entry = null;
        if (a2 == -1) {
            return null;
        }
        Range range = (Range) this.b.get(a2);
        if (range.contains(k)) {
            entry = Maps.immutableEntry(range, this.c.get(a2));
        }
        return entry;
    }

    public Range<K> span() {
        if (this.b.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.a(((Range) this.b.get(0)).b, ((Range) this.b.get(this.b.size() - 1)).c);
    }

    @Deprecated
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.b.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet(this.b, Range.a), this.c);
    }

    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (this.b.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet(this.b.reverse(), Range.a.reverse()), this.c.reverse());
    }

    public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        if (this.b.isEmpty() || range.encloses(span())) {
            return this;
        }
        final int a2 = SortedLists.a((List<E>) this.b, Range.b(), range.b, KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
        int a3 = SortedLists.a((List<E>) this.b, Range.a(), range.c, KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
        if (a2 >= a3) {
            return of();
        }
        final int i = a3 - a2;
        final Range<K> range2 = range;
        AnonymousClass2 r3 = new ImmutableRangeMap<K, V>(new ImmutableList<Range<K>>() {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return true;
            }

            public int size() {
                return i;
            }

            /* renamed from: a */
            public Range<K> get(int i) {
                Preconditions.checkElementIndex(i, i);
                if (i == 0 || i == i - 1) {
                    return ((Range) ImmutableRangeMap.this.b.get(i + a2)).intersection(range);
                }
                return (Range) ImmutableRangeMap.this.b.get(i + a2);
            }
        }, this.c.subList(a2, a3)) {
            public /* bridge */ /* synthetic */ Map asDescendingMapOfRanges() {
                return ImmutableRangeMap.super.asDescendingMapOfRanges();
            }

            public /* bridge */ /* synthetic */ Map asMapOfRanges() {
                return ImmutableRangeMap.super.asMapOfRanges();
            }

            public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
                if (range2.isConnected(range)) {
                    return this.subRangeMap(range.intersection(range2));
                }
                return ImmutableRangeMap.of();
            }
        };
        return r3;
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof RangeMap)) {
            return false;
        }
        return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
    }

    public String toString() {
        return asMapOfRanges().toString();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(asMapOfRanges());
    }
}
