package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

@GwtCompatible
class StandardRowSortedTable<R, C, V> extends StandardTable<R, C, V> implements RowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;

    class RowSortedMap extends RowMap implements SortedMap<R, Map<C, V>> {
        private RowSortedMap() {
            super();
        }

        /* renamed from: c */
        public SortedSet<R> keySet() {
            return (SortedSet) super.keySet();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: d */
        public SortedSet<R> h() {
            return new SortedKeySet(this);
        }

        public Comparator<? super R> comparator() {
            return StandardRowSortedTable.this.h().comparator();
        }

        public R firstKey() {
            return StandardRowSortedTable.this.h().firstKey();
        }

        public R lastKey() {
            return StandardRowSortedTable.this.h().lastKey();
        }

        public SortedMap<R, Map<C, V>> headMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.this.h().headMap(r), StandardRowSortedTable.this.b).rowMap();
        }

        public SortedMap<R, Map<C, V>> subMap(R r, R r2) {
            Preconditions.checkNotNull(r);
            Preconditions.checkNotNull(r2);
            return new StandardRowSortedTable(StandardRowSortedTable.this.h().subMap(r, r2), StandardRowSortedTable.this.b).rowMap();
        }

        public SortedMap<R, Map<C, V>> tailMap(R r) {
            Preconditions.checkNotNull(r);
            return new StandardRowSortedTable(StandardRowSortedTable.this.h().tailMap(r), StandardRowSortedTable.this.b).rowMap();
        }
    }

    StandardRowSortedTable(SortedMap<R, Map<C, V>> sortedMap, Supplier<? extends Map<C, V>> supplier) {
        super(sortedMap, supplier);
    }

    /* access modifiers changed from: private */
    public SortedMap<R, Map<C, V>> h() {
        return (SortedMap) this.a;
    }

    public SortedSet<R> rowKeySet() {
        return (SortedSet) rowMap().keySet();
    }

    public SortedMap<R, Map<C, V>> rowMap() {
        return (SortedMap) super.rowMap();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public SortedMap<R, Map<C, V>> f() {
        return new RowSortedMap();
    }
}
