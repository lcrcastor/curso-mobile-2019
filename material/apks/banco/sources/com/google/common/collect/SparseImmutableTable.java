package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Table.Cell;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    static final ImmutableTable<Object, Object, Object> a = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
    private final ImmutableMap<R, Map<C, V>> b;
    private final ImmutableMap<C, Map<R, V>> c;
    private final int[] d;
    private final int[] e;

    SparseImmutableTable(ImmutableList<Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        ImmutableMap a2 = Maps.a((Collection<E>) immutableSet);
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        Iterator it = immutableSet.iterator();
        while (it.hasNext()) {
            newLinkedHashMap.put(it.next(), new LinkedHashMap());
        }
        LinkedHashMap newLinkedHashMap2 = Maps.newLinkedHashMap();
        Iterator it2 = immutableSet2.iterator();
        while (it2.hasNext()) {
            newLinkedHashMap2.put(it2.next(), new LinkedHashMap());
        }
        int[] iArr = new int[immutableList.size()];
        int[] iArr2 = new int[immutableList.size()];
        for (int i = 0; i < immutableList.size(); i++) {
            Cell cell = (Cell) immutableList.get(i);
            Object rowKey = cell.getRowKey();
            Object columnKey = cell.getColumnKey();
            Object value = cell.getValue();
            iArr[i] = ((Integer) a2.get(rowKey)).intValue();
            Map map = (Map) newLinkedHashMap.get(rowKey);
            iArr2[i] = map.size();
            Object put = map.put(columnKey, value);
            if (put != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Duplicate value for row=");
                sb.append(rowKey);
                sb.append(", column=");
                sb.append(columnKey);
                sb.append(": ");
                sb.append(value);
                sb.append(", ");
                sb.append(put);
                throw new IllegalArgumentException(sb.toString());
            }
            ((Map) newLinkedHashMap2.get(columnKey)).put(rowKey, value);
        }
        this.d = iArr;
        this.e = iArr2;
        Builder builder = new Builder(newLinkedHashMap.size());
        for (Entry entry : newLinkedHashMap.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf((Map) entry.getValue()));
        }
        this.b = builder.build();
        Builder builder2 = new Builder(newLinkedHashMap2.size());
        for (Entry entry2 : newLinkedHashMap2.entrySet()) {
            builder2.put(entry2.getKey(), ImmutableMap.copyOf((Map) entry2.getValue()));
        }
        this.c = builder2.build();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.c;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.b;
    }

    public int size() {
        return this.d.length;
    }

    /* access modifiers changed from: 0000 */
    public Cell<R, C, V> a(int i) {
        Entry entry = (Entry) this.b.entrySet().asList().get(this.d[i]);
        ImmutableMap immutableMap = (ImmutableMap) entry.getValue();
        Entry entry2 = (Entry) immutableMap.entrySet().asList().get(this.e[i]);
        return a(entry.getKey(), entry2.getKey(), entry2.getValue());
    }

    /* access modifiers changed from: 0000 */
    public V b(int i) {
        ImmutableMap immutableMap = (ImmutableMap) this.b.values().asList().get(this.d[i]);
        return immutableMap.values().asList().get(this.e[i]);
    }

    /* access modifiers changed from: 0000 */
    public SerializedForm e() {
        ImmutableMap a2 = Maps.a((Collection<E>) columnKeySet());
        int[] iArr = new int[cellSet().size()];
        Iterator it = cellSet().iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            iArr[i] = ((Integer) a2.get(((Cell) it.next()).getColumnKey())).intValue();
            i = i2;
        }
        return SerializedForm.a(this, this.d, iArr);
    }
}
