package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import java.util.Map;

@GwtCompatible
class SingletonImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
    final R a;
    final C b;
    final V c;

    public int size() {
        return 1;
    }

    SingletonImmutableTable(R r, C c2, V v) {
        this.a = Preconditions.checkNotNull(r);
        this.b = Preconditions.checkNotNull(c2);
        this.c = Preconditions.checkNotNull(v);
    }

    SingletonImmutableTable(Cell<R, C, V> cell) {
        this(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
    }

    public ImmutableMap<R, V> column(C c2) {
        Preconditions.checkNotNull(c2);
        return containsColumn(c2) ? ImmutableMap.of(this.a, this.c) : ImmutableMap.of();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.of(this.b, ImmutableMap.of(this.a, this.c));
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.of(this.a, ImmutableMap.of(this.b, this.c));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: f */
    public ImmutableSet<Cell<R, C, V>> a() {
        return ImmutableSet.of(a(this.a, this.b, this.c));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: h */
    public ImmutableCollection<V> c() {
        return ImmutableSet.of(this.c);
    }

    /* access modifiers changed from: 0000 */
    public SerializedForm e() {
        return SerializedForm.a(this, new int[]{0}, new int[]{0});
    }
}
