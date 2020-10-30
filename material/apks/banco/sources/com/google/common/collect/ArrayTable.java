package com.google.common.collect;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@Beta
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final ImmutableList<R> a;
    /* access modifiers changed from: private */
    public final ImmutableList<C> b;
    /* access modifiers changed from: private */
    public final ImmutableMap<R, Integer> c;
    /* access modifiers changed from: private */
    public final ImmutableMap<C, Integer> d;
    private final V[][] e;
    private transient ColumnMap f;
    private transient RowMap g;

    static abstract class ArrayMap<K, V> extends IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> a;

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract V a(int i, V v);

        /* access modifiers changed from: 0000 */
        public abstract String a();

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract V b(int i);

        private ArrayMap(ImmutableMap<K, Integer> immutableMap) {
            this.a = immutableMap;
        }

        public Set<K> keySet() {
            return this.a.keySet();
        }

        /* access modifiers changed from: 0000 */
        public K a(int i) {
            return this.a.keySet().asList().get(i);
        }

        public int size() {
            return this.a.size();
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<K, V>> b() {
            return new AbstractIndexedListIterator<Entry<K, V>>(size()) {
                /* access modifiers changed from: protected */
                /* renamed from: b */
                public Entry<K, V> a(final int i) {
                    return new AbstractMapEntry<K, V>() {
                        public K getKey() {
                            return ArrayMap.this.a(i);
                        }

                        public V getValue() {
                            return ArrayMap.this.b(i);
                        }

                        public V setValue(V v) {
                            return ArrayMap.this.a(i, v);
                        }
                    };
                }
            };
        }

        public boolean containsKey(@Nullable Object obj) {
            return this.a.containsKey(obj);
        }

        public V get(@Nullable Object obj) {
            Integer num = (Integer) this.a.get(obj);
            if (num == null) {
                return null;
            }
            return b(num.intValue());
        }

        public V put(K k, V v) {
            Integer num = (Integer) this.a.get(k);
            if (num != null) {
                return a(num.intValue(), v);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(k);
            sb.append(" not in ");
            sb.append(this.a.keySet());
            throw new IllegalArgumentException(sb.toString());
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    class Column extends ArrayMap<R, V> {
        final int a;

        /* access modifiers changed from: 0000 */
        public String a() {
            return "Row";
        }

        Column(int i) {
            super(ArrayTable.this.c);
            this.a = i;
        }

        /* access modifiers changed from: 0000 */
        public V b(int i) {
            return ArrayTable.this.at(i, this.a);
        }

        /* access modifiers changed from: 0000 */
        public V a(int i, V v) {
            return ArrayTable.this.set(i, this.a, v);
        }
    }

    class ColumnMap extends ArrayMap<C, Map<R, V>> {
        /* access modifiers changed from: 0000 */
        public String a() {
            return "Column";
        }

        private ColumnMap() {
            super(ArrayTable.this.d);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: c */
        public Map<R, V> b(int i) {
            return new Column(i);
        }

        /* access modifiers changed from: 0000 */
        public Map<R, V> a(int i, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }

        /* renamed from: a */
        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    class Row extends ArrayMap<C, V> {
        final int a;

        /* access modifiers changed from: 0000 */
        public String a() {
            return "Column";
        }

        Row(int i) {
            super(ArrayTable.this.d);
            this.a = i;
        }

        /* access modifiers changed from: 0000 */
        public V b(int i) {
            return ArrayTable.this.at(this.a, i);
        }

        /* access modifiers changed from: 0000 */
        public V a(int i, V v) {
            return ArrayTable.this.set(this.a, i, v);
        }
    }

    class RowMap extends ArrayMap<R, Map<C, V>> {
        /* access modifiers changed from: 0000 */
        public String a() {
            return "Row";
        }

        private RowMap() {
            super(ArrayTable.this.c);
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: c */
        public Map<C, V> b(int i) {
            return new Row(i);
        }

        /* access modifiers changed from: 0000 */
        public Map<C, V> a(int i, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }

        /* renamed from: a */
        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isEmpty() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<>(iterable, iterable2);
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        return table instanceof ArrayTable ? new ArrayTable<>((ArrayTable) table) : new ArrayTable<>(table);
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        this.a = ImmutableList.copyOf(iterable);
        this.b = ImmutableList.copyOf(iterable2);
        Preconditions.checkArgument(!this.a.isEmpty());
        Preconditions.checkArgument(!this.b.isEmpty());
        this.c = Maps.a((Collection<E>) this.a);
        this.d = Maps.a((Collection<E>) this.b);
        this.e = (Object[][]) Array.newInstance(Object.class, new int[]{this.a.size(), this.b.size()});
        eraseAll();
    }

    private ArrayTable(Table<R, C, V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        this.a = arrayTable.a;
        this.b = arrayTable.b;
        this.c = arrayTable.c;
        this.d = arrayTable.d;
        V[][] vArr = (Object[][]) Array.newInstance(Object.class, new int[]{this.a.size(), this.b.size()});
        this.e = vArr;
        eraseAll();
        for (int i = 0; i < this.a.size(); i++) {
            System.arraycopy(arrayTable.e[i], 0, vArr[i], 0, arrayTable.e[i].length);
        }
    }

    public ImmutableList<R> rowKeyList() {
        return this.a;
    }

    public ImmutableList<C> columnKeyList() {
        return this.b;
    }

    public V at(int i, int i2) {
        Preconditions.checkElementIndex(i, this.a.size());
        Preconditions.checkElementIndex(i2, this.b.size());
        return this.e[i][i2];
    }

    @CanIgnoreReturnValue
    public V set(int i, int i2, @Nullable V v) {
        Preconditions.checkElementIndex(i, this.a.size());
        Preconditions.checkElementIndex(i2, this.b.size());
        V v2 = this.e[i][i2];
        this.e[i][i2] = v;
        return v2;
    }

    @GwtIncompatible
    public V[][] toArray(Class<V> cls) {
        V[][] vArr = (Object[][]) Array.newInstance(cls, new int[]{this.a.size(), this.b.size()});
        for (int i = 0; i < this.a.size(); i++) {
            System.arraycopy(this.e[i], 0, vArr[i], 0, this.e[i].length);
        }
        return vArr;
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void eraseAll() {
        for (V[] fill : this.e) {
            Arrays.fill(fill, null);
        }
    }

    public boolean contains(@Nullable Object obj, @Nullable Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    public boolean containsColumn(@Nullable Object obj) {
        return this.d.containsKey(obj);
    }

    public boolean containsRow(@Nullable Object obj) {
        return this.c.containsKey(obj);
    }

    public boolean containsValue(@Nullable Object obj) {
        V[][] vArr;
        for (V[] vArr2 : this.e) {
            for (V equal : vArr[r3]) {
                if (Objects.equal(obj, equal)) {
                    return true;
                }
            }
        }
        return false;
    }

    public V get(@Nullable Object obj, @Nullable Object obj2) {
        Integer num = (Integer) this.c.get(obj);
        Integer num2 = (Integer) this.d.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return at(num.intValue(), num2.intValue());
    }

    @CanIgnoreReturnValue
    public V put(R r, C c2, @Nullable V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c2);
        Integer num = (Integer) this.c.get(r);
        boolean z = false;
        Preconditions.checkArgument(num != null, "Row %s not in %s", (Object) r, (Object) this.a);
        Integer num2 = (Integer) this.d.get(c2);
        if (num2 != null) {
            z = true;
        }
        Preconditions.checkArgument(z, "Column %s not in %s", (Object) c2, (Object) this.b);
        return set(num.intValue(), num2.intValue(), v);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public V erase(@Nullable Object obj, @Nullable Object obj2) {
        Integer num = (Integer) this.c.get(obj);
        Integer num2 = (Integer) this.d.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), null);
    }

    public int size() {
        return this.a.size() * this.b.size();
    }

    public Set<Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Cell<R, C, V>> b() {
        return new AbstractIndexedListIterator<Cell<R, C, V>>(size()) {
            /* access modifiers changed from: protected */
            /* renamed from: b */
            public Cell<R, C, V> a(final int i) {
                return new AbstractCell<R, C, V>() {
                    final int a = (i / ArrayTable.this.b.size());
                    final int b = (i % ArrayTable.this.b.size());

                    public R getRowKey() {
                        return ArrayTable.this.a.get(this.a);
                    }

                    public C getColumnKey() {
                        return ArrayTable.this.b.get(this.b);
                    }

                    public V getValue() {
                        return ArrayTable.this.at(this.a, this.b);
                    }
                };
            }
        };
    }

    public Map<R, V> column(C c2) {
        Preconditions.checkNotNull(c2);
        Integer num = (Integer) this.d.get(c2);
        return num == null ? ImmutableMap.of() : new Column(num.intValue());
    }

    public ImmutableSet<C> columnKeySet() {
        return this.d.keySet();
    }

    public Map<C, Map<R, V>> columnMap() {
        ColumnMap columnMap = this.f;
        if (columnMap != null) {
            return columnMap;
        }
        ColumnMap columnMap2 = new ColumnMap<>();
        this.f = columnMap2;
        return columnMap2;
    }

    public Map<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        Integer num = (Integer) this.c.get(r);
        return num == null ? ImmutableMap.of() : new Row(num.intValue());
    }

    public ImmutableSet<R> rowKeySet() {
        return this.c.keySet();
    }

    public Map<R, Map<C, V>> rowMap() {
        RowMap rowMap = this.g;
        if (rowMap != null) {
            return rowMap;
        }
        RowMap rowMap2 = new RowMap<>();
        this.g = rowMap2;
        return rowMap2;
    }

    public Collection<V> values() {
        return super.values();
    }
}
