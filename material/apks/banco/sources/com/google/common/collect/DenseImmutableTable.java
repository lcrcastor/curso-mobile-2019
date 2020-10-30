package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class DenseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    /* access modifiers changed from: private */
    public final ImmutableMap<R, Integer> a;
    /* access modifiers changed from: private */
    public final ImmutableMap<C, Integer> b;
    private final ImmutableMap<R, Map<C, V>> c;
    private final ImmutableMap<C, Map<R, V>> d;
    /* access modifiers changed from: private */
    public final int[] e = new int[this.a.size()];
    /* access modifiers changed from: private */
    public final int[] f = new int[this.b.size()];
    /* access modifiers changed from: private */
    public final V[][] g;
    private final int[] h;
    private final int[] i;

    static abstract class ImmutableArrayMap<K, V> extends IteratorBasedImmutableMap<K, V> {
        private final int a;

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract V a(int i);

        /* access modifiers changed from: 0000 */
        public abstract ImmutableMap<K, Integer> b_();

        ImmutableArrayMap(int i) {
            this.a = i;
        }

        private boolean h() {
            return this.a == b_().size();
        }

        /* access modifiers changed from: 0000 */
        public K c(int i) {
            return b_().keySet().asList().get(i);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<K> c() {
            return h() ? b_().keySet() : super.c();
        }

        public int size() {
            return this.a;
        }

        public V get(@Nullable Object obj) {
            Integer num = (Integer) b_().get(obj);
            if (num == null) {
                return null;
            }
            return a(num.intValue());
        }

        /* access modifiers changed from: 0000 */
        public UnmodifiableIterator<Entry<K, V>> d() {
            return new AbstractIterator<Entry<K, V>>() {
                private int b = -1;
                private final int c = ImmutableArrayMap.this.b_().size();

                /* access modifiers changed from: protected */
                /* renamed from: a */
                public Entry<K, V> computeNext() {
                    int i = this.b;
                    while (true) {
                        this.b = i + 1;
                        if (this.b >= this.c) {
                            return (Entry) endOfData();
                        }
                        Object a2 = ImmutableArrayMap.this.a(this.b);
                        if (a2 != null) {
                            return Maps.immutableEntry(ImmutableArrayMap.this.c(this.b), a2);
                        }
                        i = this.b;
                    }
                }
            };
        }
    }

    final class Column extends ImmutableArrayMap<R, V> {
        private final int c;

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return true;
        }

        Column(int i) {
            super(DenseImmutableTable.this.f[i]);
            this.c = i;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<R, Integer> b_() {
            return DenseImmutableTable.this.a;
        }

        /* access modifiers changed from: 0000 */
        public V a(int i) {
            return DenseImmutableTable.this.g[i][this.c];
        }
    }

    final class ColumnMap extends ImmutableArrayMap<C, Map<R, V>> {
        /* access modifiers changed from: 0000 */
        public boolean b() {
            return false;
        }

        private ColumnMap() {
            super(DenseImmutableTable.this.f.length);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<C, Integer> b_() {
            return DenseImmutableTable.this.b;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Map<R, V> a(int i) {
            return new Column(i);
        }
    }

    final class Row extends ImmutableArrayMap<C, V> {
        private final int c;

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return true;
        }

        Row(int i) {
            super(DenseImmutableTable.this.e[i]);
            this.c = i;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<C, Integer> b_() {
            return DenseImmutableTable.this.b;
        }

        /* access modifiers changed from: 0000 */
        public V a(int i) {
            return DenseImmutableTable.this.g[this.c][i];
        }
    }

    final class RowMap extends ImmutableArrayMap<R, Map<C, V>> {
        /* access modifiers changed from: 0000 */
        public boolean b() {
            return false;
        }

        private RowMap() {
            super(DenseImmutableTable.this.e.length);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<R, Integer> b_() {
            return DenseImmutableTable.this.a;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: b */
        public Map<C, V> a(int i) {
            return new Row(i);
        }
    }

    DenseImmutableTable(ImmutableList<Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        this.g = (Object[][]) Array.newInstance(Object.class, new int[]{immutableSet.size(), immutableSet2.size()});
        this.a = Maps.a((Collection<E>) immutableSet);
        this.b = Maps.a((Collection<E>) immutableSet2);
        int[] iArr = new int[immutableList.size()];
        int[] iArr2 = new int[immutableList.size()];
        for (int i2 = 0; i2 < immutableList.size(); i2++) {
            Cell cell = (Cell) immutableList.get(i2);
            Object rowKey = cell.getRowKey();
            Object columnKey = cell.getColumnKey();
            int intValue = ((Integer) this.a.get(rowKey)).intValue();
            int intValue2 = ((Integer) this.b.get(columnKey)).intValue();
            Preconditions.checkArgument(this.g[intValue][intValue2] == null, "duplicate key: (%s, %s)", rowKey, columnKey);
            this.g[intValue][intValue2] = cell.getValue();
            int[] iArr3 = this.e;
            iArr3[intValue] = iArr3[intValue] + 1;
            int[] iArr4 = this.f;
            iArr4[intValue2] = iArr4[intValue2] + 1;
            iArr[i2] = intValue;
            iArr2[i2] = intValue2;
        }
        this.h = iArr;
        this.i = iArr2;
        this.c = new RowMap();
        this.d = new ColumnMap();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.d;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.c;
    }

    public V get(@Nullable Object obj, @Nullable Object obj2) {
        Integer num = (Integer) this.a.get(obj);
        Integer num2 = (Integer) this.b.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return this.g[num.intValue()][num2.intValue()];
    }

    public int size() {
        return this.h.length;
    }

    /* access modifiers changed from: 0000 */
    public Cell<R, C, V> a(int i2) {
        int i3 = this.h[i2];
        int i4 = this.i[i2];
        return a(rowKeySet().asList().get(i3), columnKeySet().asList().get(i4), this.g[i3][i4]);
    }

    /* access modifiers changed from: 0000 */
    public V b(int i2) {
        return this.g[this.h[i2]][this.i[i2]];
    }

    /* access modifiers changed from: 0000 */
    public SerializedForm e() {
        return SerializedForm.a(this, this.h, this.i);
    }
}
