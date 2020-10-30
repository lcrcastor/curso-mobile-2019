package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ImmutableTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {

    public static final class Builder<R, C, V> {
        private final List<Cell<R, C, V>> a = Lists.newArrayList();
        private Comparator<? super R> b;
        private Comparator<? super C> c;

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderRowsBy(Comparator<? super R> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> comparator) {
            this.c = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> put(R r, C c2, V v) {
            this.a.add(ImmutableTable.a(r, c2, v));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> put(Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey());
                Preconditions.checkNotNull(cell.getColumnKey());
                Preconditions.checkNotNull(cell.getValue());
                this.a.add(cell);
            } else {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Cell put : table.cellSet()) {
                put(put);
            }
            return this;
        }

        public ImmutableTable<R, C, V> build() {
            switch (this.a.size()) {
                case 0:
                    return ImmutableTable.of();
                case 1:
                    return new SingletonImmutableTable((Cell) Iterables.getOnlyElement(this.a));
                default:
                    return RegularImmutableTable.a(this.a, this.b, this.c);
            }
        }
    }

    static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] a;
        private final Object[] b;
        private final Object[] c;
        private final int[] d;
        private final int[] e;

        private SerializedForm(Object[] objArr, Object[] objArr2, Object[] objArr3, int[] iArr, int[] iArr2) {
            this.a = objArr;
            this.b = objArr2;
            this.c = objArr3;
            this.d = iArr;
            this.e = iArr2;
        }

        static SerializedForm a(ImmutableTable<?, ?, ?> immutableTable, int[] iArr, int[] iArr2) {
            SerializedForm serializedForm = new SerializedForm(immutableTable.rowKeySet().toArray(), immutableTable.columnKeySet().toArray(), immutableTable.values().toArray(), iArr, iArr2);
            return serializedForm;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            if (this.c.length == 0) {
                return ImmutableTable.of();
            }
            if (this.c.length == 1) {
                return ImmutableTable.of(this.a[0], this.b[0], this.c[0]);
            }
            com.google.common.collect.ImmutableList.Builder builder = new com.google.common.collect.ImmutableList.Builder(this.c.length);
            for (int i = 0; i < this.c.length; i++) {
                builder.add((Object) ImmutableTable.a(this.a[this.d[i]], this.b[this.e[i]], this.c[i]));
            }
            return RegularImmutableTable.a(builder.build(), ImmutableSet.copyOf((E[]) this.a), ImmutableSet.copyOf((E[]) this.b));
        }
    }

    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    /* access modifiers changed from: 0000 */
    public abstract SerializedForm e();

    /* access modifiers changed from: 0000 */
    /* renamed from: f */
    public abstract ImmutableSet<Cell<R, C, V>> a();

    /* access modifiers changed from: 0000 */
    /* renamed from: h */
    public abstract ImmutableCollection<V> c();

    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    public /* bridge */ /* synthetic */ boolean containsColumn(Object obj) {
        return super.containsColumn(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(Object obj) {
        return super.containsRow(obj);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Object get(Object obj, Object obj2) {
        return super.get(obj, obj2);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return SparseImmutableTable.a;
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R r, C c, V v) {
        return new SingletonImmutableTable(r, c, v);
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable) table;
        }
        int size = table.size();
        switch (size) {
            case 0:
                return of();
            case 1:
                Cell cell = (Cell) Iterables.getOnlyElement(table.cellSet());
                return of(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            default:
                com.google.common.collect.ImmutableSet.Builder builder = new com.google.common.collect.ImmutableSet.Builder(size);
                for (Cell cell2 : table.cellSet()) {
                    builder.add((Object) a(cell2.getRowKey(), cell2.getColumnKey(), cell2.getValue()));
                }
                return RegularImmutableTable.a((Iterable<Cell<R, C, V>>) builder.build());
        }
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder<>();
    }

    static <R, C, V> Cell<R, C, V> a(R r, C c, V v) {
        return Tables.immutableCell(Preconditions.checkNotNull(r), Preconditions.checkNotNull(c), Preconditions.checkNotNull(v));
    }

    ImmutableTable() {
    }

    public ImmutableSet<Cell<R, C, V>> cellSet() {
        return (ImmutableSet) super.cellSet();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: g */
    public final UnmodifiableIterator<Cell<R, C, V>> b() {
        throw new AssertionError("should never be called");
    }

    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    /* access modifiers changed from: 0000 */
    public final Iterator<V> d() {
        throw new AssertionError("should never be called");
    }

    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) columnMap().get(c), ImmutableMap.of());
    }

    public ImmutableSet<C> columnKeySet() {
        return columnMap().keySet();
    }

    public ImmutableMap<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) rowMap().get(r), ImmutableMap.of());
    }

    public ImmutableSet<R> rowKeySet() {
        return rowMap().keySet();
    }

    public boolean contains(@Nullable Object obj, @Nullable Object obj2) {
        return get(obj, obj2) != null;
    }

    public boolean containsValue(@Nullable Object obj) {
        return values().contains(obj);
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public final Object writeReplace() {
        return e();
    }
}
