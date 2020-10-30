package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {

    final class CellSet extends Indexed<Cell<R, C, V>> {
        /* access modifiers changed from: 0000 */
        public boolean a() {
            return false;
        }

        private CellSet() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Cell<R, C, V> a(int i) {
            return RegularImmutableTable.this.a(i);
        }

        public boolean contains(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) obj;
            Object obj2 = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            if (obj2 != null && obj2.equals(cell.getValue())) {
                z = true;
            }
            return z;
        }
    }

    final class Values extends ImmutableList<V> {
        /* access modifiers changed from: 0000 */
        public boolean a() {
            return true;
        }

        private Values() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        public V get(int i) {
            return RegularImmutableTable.this.b(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Cell<R, C, V> a(int i);

    /* access modifiers changed from: 0000 */
    public abstract V b(int i);

    RegularImmutableTable() {
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: f */
    public final ImmutableSet<Cell<R, C, V>> a() {
        return isEmpty() ? ImmutableSet.of() : new CellSet();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: h */
    public final ImmutableCollection<V> c() {
        return isEmpty() ? ImmutableList.of() : new Values();
    }

    static <R, C, V> RegularImmutableTable<R, C, V> a(List<Cell<R, C, V>> list, @Nullable final Comparator<? super R> comparator, @Nullable final Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(list);
        if (!(comparator == null && comparator2 == null)) {
            Collections.sort(list, new Comparator<Cell<R, C, V>>() {
                /* renamed from: a */
                public int compare(Cell<R, C, V> cell, Cell<R, C, V> cell2) {
                    int i = 0;
                    int compare = comparator == null ? 0 : comparator.compare(cell.getRowKey(), cell2.getRowKey());
                    if (compare != 0) {
                        return compare;
                    }
                    if (comparator2 != null) {
                        i = comparator2.compare(cell.getColumnKey(), cell2.getColumnKey());
                    }
                    return i;
                }
            });
        }
        return a((Iterable<Cell<R, C, V>>) list, comparator, comparator2);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> a(Iterable<Cell<R, C, V>> iterable) {
        return a(iterable, null, null);
    }

    private static final <R, C, V> RegularImmutableTable<R, C, V> a(Iterable<Cell<R, C, V>> iterable, @Nullable Comparator<? super R> comparator, @Nullable Comparator<? super C> comparator2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ImmutableList copyOf = ImmutableList.copyOf(iterable);
        for (Cell cell : iterable) {
            linkedHashSet.add(cell.getRowKey());
            linkedHashSet2.add(cell.getColumnKey());
        }
        return a(copyOf, comparator == null ? ImmutableSet.copyOf((Collection<? extends E>) linkedHashSet) : ImmutableSet.copyOf((Collection<? extends E>) Ordering.from(comparator).immutableSortedCopy(linkedHashSet)), comparator2 == null ? ImmutableSet.copyOf((Collection<? extends E>) linkedHashSet2) : ImmutableSet.copyOf((Collection<? extends E>) Ordering.from(comparator2).immutableSortedCopy(linkedHashSet2)));
    }

    static <R, C, V> RegularImmutableTable<R, C, V> a(ImmutableList<Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        return ((long) immutableList.size()) > (((long) immutableSet.size()) * ((long) immutableSet2.size())) / 2 ? new DenseImmutableTable(immutableList, immutableSet, immutableSet2) : new SparseImmutableTable(immutableList, immutableSet, immutableSet2);
    }
}
