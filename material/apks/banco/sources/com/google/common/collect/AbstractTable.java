package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
    private transient Set<Cell<R, C, V>> a;
    private transient Collection<V> b;

    class CellSet extends AbstractSet<Cell<R, C, V>> {
        CellSet() {
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) obj;
            Map map = (Map) Maps.a(AbstractTable.this.rowMap(), cell.getRowKey());
            if (map != null && Collections2.a((Collection<?>) map.entrySet(), (Object) Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) {
                z = true;
            }
            return z;
        }

        public boolean remove(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) obj;
            Map map = (Map) Maps.a(AbstractTable.this.rowMap(), cell.getRowKey());
            if (map != null && Collections2.b((Collection<?>) map.entrySet(), (Object) Maps.immutableEntry(cell.getColumnKey(), cell.getValue()))) {
                z = true;
            }
            return z;
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public Iterator<Cell<R, C, V>> iterator() {
            return AbstractTable.this.b();
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }

    class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return AbstractTable.this.d();
        }

        public boolean contains(Object obj) {
            return AbstractTable.this.containsValue(obj);
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Cell<R, C, V>> b();

    AbstractTable() {
    }

    public boolean containsRow(@Nullable Object obj) {
        return Maps.b(rowMap(), obj);
    }

    public boolean containsColumn(@Nullable Object obj) {
        return Maps.b(columnMap(), obj);
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public Set<C> columnKeySet() {
        return columnMap().keySet();
    }

    public boolean containsValue(@Nullable Object obj) {
        for (Map containsValue : rowMap().values()) {
            if (containsValue.containsValue(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(@Nullable Object obj, @Nullable Object obj2) {
        Map map = (Map) Maps.a(rowMap(), obj);
        return map != null && Maps.b(map, obj2);
    }

    public V get(@Nullable Object obj, @Nullable Object obj2) {
        Map map = (Map) Maps.a(rowMap(), obj);
        if (map == null) {
            return null;
        }
        return Maps.a(map, obj2);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        Iterators.b(cellSet().iterator());
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj, @Nullable Object obj2) {
        Map map = (Map) Maps.a(rowMap(), obj);
        if (map == null) {
            return null;
        }
        return Maps.c(map, obj2);
    }

    @CanIgnoreReturnValue
    public V put(R r, C c, V v) {
        return row(r).put(c, v);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Cell cell : table.cellSet()) {
            put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> set = this.a;
        if (set != null) {
            return set;
        }
        Set<Cell<R, C, V>> a2 = a();
        this.a = a2;
        return a2;
    }

    /* access modifiers changed from: 0000 */
    public Set<Cell<R, C, V>> a() {
        return new CellSet();
    }

    public Collection<V> values() {
        Collection<V> collection = this.b;
        if (collection != null) {
            return collection;
        }
        Collection<V> c = c();
        this.b = c;
        return c;
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> c() {
        return new Values();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<V> d() {
        return new TransformedIterator<Cell<R, C, V>, V>(cellSet().iterator()) {
            /* access modifiers changed from: 0000 */
            public V a(Cell<R, C, V> cell) {
                return cell.getValue();
            }
        };
    }

    public boolean equals(@Nullable Object obj) {
        return Tables.a(this, obj);
    }

    public int hashCode() {
        return cellSet().hashCode();
    }

    public String toString() {
        return rowMap().toString();
    }
}
