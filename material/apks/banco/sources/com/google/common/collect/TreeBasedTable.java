package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;
    private final Comparator<? super C> c;

    class TreeRow extends Row implements SortedMap<C, V> {
        @Nullable
        final C d;
        @Nullable
        final C e;
        transient SortedMap<C, V> f;

        TreeRow(TreeBasedTable treeBasedTable, R r) {
            this(r, null, null);
        }

        TreeRow(R r, C c, @Nullable C c2) {
            super(r);
            this.d = c;
            this.e = c2;
            Preconditions.checkArgument(c == null || c2 == null || a(c, c2) <= 0);
        }

        /* renamed from: e */
        public SortedSet<C> keySet() {
            return new SortedKeySet(this);
        }

        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }

        /* access modifiers changed from: 0000 */
        public int a(Object obj, Object obj2) {
            return comparator().compare(obj, obj2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(@Nullable Object obj) {
            return obj != null && (this.d == null || a(this.d, obj) <= 0) && (this.e == null || a(this.e, obj) > 0);
        }

        public SortedMap<C, V> subMap(C c, C c2) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)) && a(Preconditions.checkNotNull(c2)));
            return new TreeRow(this.a, c, c2);
        }

        public SortedMap<C, V> headMap(C c) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return new TreeRow(this.a, this.d, c);
        }

        public SortedMap<C, V> tailMap(C c) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return new TreeRow(this.a, c, this.e);
        }

        public C firstKey() {
            if (a() != null) {
                return a().firstKey();
            }
            throw new NoSuchElementException();
        }

        public C lastKey() {
            if (a() != null) {
                return a().lastKey();
            }
            throw new NoSuchElementException();
        }

        /* access modifiers changed from: 0000 */
        public SortedMap<C, V> f() {
            if (this.f == null || (this.f.isEmpty() && TreeBasedTable.this.a.containsKey(this.a))) {
                this.f = (SortedMap) TreeBasedTable.this.a.get(this.a);
            }
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: g */
        public SortedMap<C, V> a() {
            return (SortedMap) super.a();
        }

        /* access modifiers changed from: 0000 */
        /* renamed from: h */
        public SortedMap<C, V> c() {
            SortedMap<C, V> f2 = f();
            if (f2 == null) {
                return null;
            }
            if (this.d != null) {
                f2 = f2.tailMap(this.d);
            }
            if (this.e != null) {
                f2 = f2.headMap(this.e);
            }
            return f2;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (f() != null && this.f.isEmpty()) {
                TreeBasedTable.this.a.remove(this.a);
                this.f = null;
                this.b = null;
            }
        }

        public boolean containsKey(Object obj) {
            return a(obj) && super.containsKey(obj);
        }

        public V put(C c, V v) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return super.put(c, v);
        }
    }

    static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        final Comparator<? super C> a;

        Factory(Comparator<? super C> comparator) {
            this.a = comparator;
        }

        /* renamed from: a */
        public TreeMap<C, V> get() {
            return new TreeMap<>(this.a);
        }
    }

    public /* bridge */ /* synthetic */ Set cellSet() {
        return super.cellSet();
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Map column(Object obj) {
        return super.column(obj);
    }

    public /* bridge */ /* synthetic */ Set columnKeySet() {
        return super.columnKeySet();
    }

    public /* bridge */ /* synthetic */ Map columnMap() {
        return super.columnMap();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj, Object obj2) {
        return super.contains(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsColumn(Object obj) {
        return super.containsColumn(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(Object obj) {
        return super.containsRow(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
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

    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2, Object obj3) {
        return super.put(obj, obj2, obj3);
    }

    public /* bridge */ /* synthetic */ void putAll(Table table) {
        super.putAll(table);
    }

    public /* bridge */ /* synthetic */ Object remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable<>(Ordering.natural(), Ordering.natural());
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable<>(comparator, comparator2);
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> treeBasedTable) {
        TreeBasedTable<R, C, V> treeBasedTable2 = new TreeBasedTable<>(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll(treeBasedTable);
        return treeBasedTable2;
    }

    TreeBasedTable(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        super(new TreeMap(comparator), new Factory(comparator2));
        this.c = comparator2;
    }

    public Comparator<? super R> rowComparator() {
        return rowKeySet().comparator();
    }

    public Comparator<? super C> columnComparator() {
        return this.c;
    }

    public SortedMap<C, V> row(R r) {
        return new TreeRow(this, r);
    }

    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<C> g() {
        final Comparator columnComparator = columnComparator();
        final UnmodifiableIterator mergeSorted = Iterators.mergeSorted(Iterables.transform(this.a.values(), new Function<Map<C, V>, Iterator<C>>() {
            /* renamed from: a */
            public Iterator<C> apply(Map<C, V> map) {
                return map.keySet().iterator();
            }
        }), columnComparator);
        return new AbstractIterator<C>() {
            C a;

            /* access modifiers changed from: protected */
            public C computeNext() {
                boolean z;
                while (mergeSorted.hasNext()) {
                    C next = mergeSorted.next();
                    if (this.a == null || columnComparator.compare(next, this.a) != 0) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (!z) {
                        this.a = next;
                        return this.a;
                    }
                }
                this.a = null;
                return endOfData();
            }
        };
    }
}
