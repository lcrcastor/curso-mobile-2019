package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    final Map<R, Map<C, V>> a;
    final Supplier<? extends Map<C, V>> b;
    private transient Set<C> c;
    private transient Map<R, Map<C, V>> d;
    private transient ColumnMap e;

    class Row extends IteratorBasedAbstractMap<C, V> {
        final R a;
        Map<C, V> b;

        Row(R r) {
            this.a = Preconditions.checkNotNull(r);
        }

        /* access modifiers changed from: 0000 */
        public Map<C, V> a() {
            if (this.b != null && (!this.b.isEmpty() || !StandardTable.this.a.containsKey(this.a))) {
                return this.b;
            }
            Map<C, V> c2 = c();
            this.b = c2;
            return c2;
        }

        /* access modifiers changed from: 0000 */
        public Map<C, V> c() {
            return (Map) StandardTable.this.a.get(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (a() != null && this.b.isEmpty()) {
                StandardTable.this.a.remove(this.a);
                this.b = null;
            }
        }

        public boolean containsKey(Object obj) {
            Map a2 = a();
            return (obj == null || a2 == null || !Maps.b(a2, obj)) ? false : true;
        }

        public V get(Object obj) {
            Map a2 = a();
            if (obj == null || a2 == null) {
                return null;
            }
            return Maps.a(a2, obj);
        }

        public V put(C c2, V v) {
            Preconditions.checkNotNull(c2);
            Preconditions.checkNotNull(v);
            if (this.b == null || this.b.isEmpty()) {
                return StandardTable.this.put(this.a, c2, v);
            }
            return this.b.put(c2, v);
        }

        public V remove(Object obj) {
            Map a2 = a();
            if (a2 == null) {
                return null;
            }
            V c2 = Maps.c(a2, obj);
            d();
            return c2;
        }

        public void clear() {
            Map a2 = a();
            if (a2 != null) {
                a2.clear();
            }
            d();
        }

        public int size() {
            Map a2 = a();
            if (a2 == null) {
                return 0;
            }
            return a2.size();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<C, V>> b() {
            Map a2 = a();
            if (a2 == null) {
                return Iterators.c();
            }
            final Iterator it = a2.entrySet().iterator();
            return new Iterator<Entry<C, V>>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                /* renamed from: a */
                public Entry<C, V> next() {
                    final Entry entry = (Entry) it.next();
                    return new ForwardingMapEntry<C, V>() {
                        /* access modifiers changed from: protected */
                        public Entry<C, V> delegate() {
                            return entry;
                        }

                        public V setValue(V v) {
                            return super.setValue(Preconditions.checkNotNull(v));
                        }

                        public boolean equals(Object obj) {
                            return standardEquals(obj);
                        }
                    };
                }

                public void remove() {
                    it.remove();
                    Row.this.d();
                }
            };
        }
    }

    class CellIterator implements Iterator<Cell<R, C, V>> {
        final Iterator<Entry<R, Map<C, V>>> a;
        Entry<R, Map<C, V>> b;
        Iterator<Entry<C, V>> c;

        private CellIterator() {
            this.a = StandardTable.this.a.entrySet().iterator();
            this.c = Iterators.c();
        }

        public boolean hasNext() {
            return this.a.hasNext() || this.c.hasNext();
        }

        /* renamed from: a */
        public Cell<R, C, V> next() {
            if (!this.c.hasNext()) {
                this.b = (Entry) this.a.next();
                this.c = ((Map) this.b.getValue()).entrySet().iterator();
            }
            Entry entry = (Entry) this.c.next();
            return Tables.immutableCell(this.b.getKey(), entry.getKey(), entry.getValue());
        }

        public void remove() {
            this.c.remove();
            if (((Map) this.b.getValue()).isEmpty()) {
                this.a.remove();
            }
        }
    }

    class Column extends ViewCachingAbstractMap<R, V> {
        final C a;

        class EntrySet extends ImprovedAbstractSet<Entry<R, V>> {
            private EntrySet() {
            }

            public Iterator<Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            public int size() {
                int i = 0;
                for (Map containsKey : StandardTable.this.a.values()) {
                    if (containsKey.containsKey(Column.this.a)) {
                        i++;
                    }
                }
                return i;
            }

            public boolean isEmpty() {
                return !StandardTable.this.containsColumn(Column.this.a);
            }

            public void clear() {
                Column.this.a(Predicates.alwaysTrue());
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Entry)) {
                    return false;
                }
                Entry entry = (Entry) obj;
                return StandardTable.this.a(entry.getKey(), Column.this.a, entry.getValue());
            }

            public boolean remove(Object obj) {
                if (!(obj instanceof Entry)) {
                    return false;
                }
                Entry entry = (Entry) obj;
                return StandardTable.this.b(entry.getKey(), Column.this.a, entry.getValue());
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.a(Predicates.not(Predicates.in(collection)));
            }
        }

        class EntrySetIterator extends AbstractIterator<Entry<R, V>> {
            final Iterator<Entry<R, Map<C, V>>> a;

            private EntrySetIterator() {
                this.a = StandardTable.this.a.entrySet().iterator();
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Entry<R, V> computeNext() {
                while (this.a.hasNext()) {
                    final Entry entry = (Entry) this.a.next();
                    if (((Map) entry.getValue()).containsKey(Column.this.a)) {
                        return new AbstractMapEntry<R, V>() {
                            public R getKey() {
                                return entry.getKey();
                            }

                            public V getValue() {
                                return ((Map) entry.getValue()).get(Column.this.a);
                            }

                            public V setValue(V v) {
                                return ((Map) entry.getValue()).put(Column.this.a, Preconditions.checkNotNull(v));
                            }
                        };
                    }
                }
                return (Entry) endOfData();
            }
        }

        class KeySet extends KeySet<R, V> {
            KeySet() {
                super(Column.this);
            }

            public boolean contains(Object obj) {
                return StandardTable.this.contains(obj, Column.this.a);
            }

            public boolean remove(Object obj) {
                return StandardTable.this.remove(obj, Column.this.a) != null;
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.a(Maps.a(Predicates.not(Predicates.in(collection))));
            }
        }

        class Values extends Values<R, V> {
            Values() {
                super(Column.this);
            }

            public boolean remove(Object obj) {
                return obj != null && Column.this.a(Maps.b(Predicates.equalTo(obj)));
            }

            public boolean removeAll(Collection<?> collection) {
                return Column.this.a(Maps.b(Predicates.in(collection)));
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.a(Maps.b(Predicates.not(Predicates.in(collection))));
            }
        }

        Column(C c) {
            this.a = Preconditions.checkNotNull(c);
        }

        public V put(R r, V v) {
            return StandardTable.this.put(r, this.a, v);
        }

        public V get(Object obj) {
            return StandardTable.this.get(obj, this.a);
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.contains(obj, this.a);
        }

        public V remove(Object obj) {
            return StandardTable.this.remove(obj, this.a);
        }

        /* access modifiers changed from: 0000 */
        @CanIgnoreReturnValue
        public boolean a(Predicate<? super Entry<R, V>> predicate) {
            Iterator it = StandardTable.this.a.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                Map map = (Map) entry.getValue();
                Object obj = map.get(this.a);
                if (obj != null && predicate.apply(Maps.immutableEntry(entry.getKey(), obj))) {
                    map.remove(this.a);
                    z = true;
                    if (map.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        /* access modifiers changed from: 0000 */
        public Set<Entry<R, V>> a() {
            return new EntrySet();
        }

        /* access modifiers changed from: 0000 */
        public Set<R> h() {
            return new KeySet();
        }

        /* access modifiers changed from: 0000 */
        public Collection<V> b() {
            return new Values();
        }
    }

    class ColumnKeyIterator extends AbstractIterator<C> {
        final Map<C, V> a;
        final Iterator<Map<C, V>> b;
        Iterator<Entry<C, V>> c;

        private ColumnKeyIterator() {
            this.a = (Map) StandardTable.this.b.get();
            this.b = StandardTable.this.a.values().iterator();
            this.c = Iterators.a();
        }

        /* access modifiers changed from: protected */
        public C computeNext() {
            while (true) {
                if (this.c.hasNext()) {
                    Entry entry = (Entry) this.c.next();
                    if (!this.a.containsKey(entry.getKey())) {
                        this.a.put(entry.getKey(), entry.getValue());
                        return entry.getKey();
                    }
                } else if (!this.b.hasNext()) {
                    return endOfData();
                } else {
                    this.c = ((Map) this.b.next()).entrySet().iterator();
                }
            }
        }
    }

    class ColumnKeySet extends TableSet<C> {
        private ColumnKeySet() {
            super();
        }

        public Iterator<C> iterator() {
            return StandardTable.this.g();
        }

        public int size() {
            return Iterators.size(iterator());
        }

        public boolean remove(Object obj) {
            boolean z = false;
            if (obj == null) {
                return false;
            }
            Iterator it = StandardTable.this.a.values().iterator();
            while (it.hasNext()) {
                Map map = (Map) it.next();
                if (map.keySet().remove(obj)) {
                    z = true;
                    if (map.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator it = StandardTable.this.a.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map map = (Map) it.next();
                if (Iterators.removeAll(map.keySet().iterator(), collection)) {
                    z = true;
                    if (map.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator it = StandardTable.this.a.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map map = (Map) it.next();
                if (map.keySet().retainAll(collection)) {
                    z = true;
                    if (map.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        public boolean contains(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }
    }

    class ColumnMap extends ViewCachingAbstractMap<C, Map<R, V>> {

        class ColumnMapEntrySet extends TableSet<Entry<C, Map<R, V>>> {
            ColumnMapEntrySet() {
                super();
            }

            public Iterator<Entry<C, Map<R, V>>> iterator() {
                return Maps.a(StandardTable.this.columnKeySet(), (Function<? super K, V>) new Function<C, Map<R, V>>() {
                    /* renamed from: a */
                    public Map<R, V> apply(C c) {
                        return StandardTable.this.column(c);
                    }
                });
            }

            public int size() {
                return StandardTable.this.columnKeySet().size();
            }

            public boolean contains(Object obj) {
                if (obj instanceof Entry) {
                    Entry entry = (Entry) obj;
                    if (StandardTable.this.containsColumn(entry.getKey())) {
                        return ColumnMap.this.get(entry.getKey()).equals(entry.getValue());
                    }
                }
                return false;
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                StandardTable.this.b(((Entry) obj).getKey());
                return true;
            }

            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.a((Set<?>) this, collection.iterator());
            }

            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(Maps.immutableEntry(next, StandardTable.this.column(next)))) {
                        StandardTable.this.b(next);
                        z = true;
                    }
                }
                return z;
            }
        }

        class ColumnMapValues extends Values<C, Map<R, V>> {
            ColumnMapValues() {
                super(ColumnMap.this);
            }

            public boolean remove(Object obj) {
                for (Entry entry : ColumnMap.this.entrySet()) {
                    if (((Map) entry.getValue()).equals(obj)) {
                        StandardTable.this.b(entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (collection.contains(StandardTable.this.column(next))) {
                        StandardTable.this.b(next);
                        z = true;
                    }
                }
                return z;
            }

            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(StandardTable.this.column(next))) {
                        StandardTable.this.b(next);
                        z = true;
                    }
                }
                return z;
            }
        }

        private ColumnMap() {
        }

        /* renamed from: a */
        public Map<R, V> get(Object obj) {
            if (StandardTable.this.containsColumn(obj)) {
                return StandardTable.this.column(obj);
            }
            return null;
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        /* renamed from: b */
        public Map<R, V> remove(Object obj) {
            if (StandardTable.this.containsColumn(obj)) {
                return StandardTable.this.b(obj);
            }
            return null;
        }

        public Set<Entry<C, Map<R, V>>> a() {
            return new ColumnMapEntrySet();
        }

        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        /* access modifiers changed from: 0000 */
        public Collection<Map<R, V>> b() {
            return new ColumnMapValues();
        }
    }

    class RowMap extends ViewCachingAbstractMap<R, Map<C, V>> {

        class EntrySet extends TableSet<Entry<R, Map<C, V>>> {
            EntrySet() {
                super();
            }

            public Iterator<Entry<R, Map<C, V>>> iterator() {
                return Maps.a(StandardTable.this.a.keySet(), (Function<? super K, V>) new Function<R, Map<C, V>>() {
                    /* renamed from: a */
                    public Map<C, V> apply(R r) {
                        return StandardTable.this.row(r);
                    }
                });
            }

            public int size() {
                return StandardTable.this.a.size();
            }

            public boolean contains(Object obj) {
                boolean z = false;
                if (!(obj instanceof Entry)) {
                    return false;
                }
                Entry entry = (Entry) obj;
                if (entry.getKey() != null && (entry.getValue() instanceof Map) && Collections2.a((Collection<?>) StandardTable.this.a.entrySet(), (Object) entry)) {
                    z = true;
                }
                return z;
            }

            public boolean remove(Object obj) {
                boolean z = false;
                if (!(obj instanceof Entry)) {
                    return false;
                }
                Entry entry = (Entry) obj;
                if (entry.getKey() != null && (entry.getValue() instanceof Map) && StandardTable.this.a.entrySet().remove(entry)) {
                    z = true;
                }
                return z;
            }
        }

        RowMap() {
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.containsRow(obj);
        }

        /* renamed from: a */
        public Map<C, V> get(Object obj) {
            if (StandardTable.this.containsRow(obj)) {
                return StandardTable.this.row(obj);
            }
            return null;
        }

        /* renamed from: b */
        public Map<C, V> remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return (Map) StandardTable.this.a.remove(obj);
        }

        /* access modifiers changed from: protected */
        public Set<Entry<R, Map<C, V>>> a() {
            return new EntrySet();
        }
    }

    abstract class TableSet<T> extends ImprovedAbstractSet<T> {
        private TableSet() {
        }

        public boolean isEmpty() {
            return StandardTable.this.a.isEmpty();
        }

        public void clear() {
            StandardTable.this.a.clear();
        }
    }

    StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.a = map;
        this.b = supplier;
    }

    public boolean contains(@Nullable Object obj, @Nullable Object obj2) {
        return (obj == null || obj2 == null || !super.contains(obj, obj2)) ? false : true;
    }

    public boolean containsColumn(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        for (Map b2 : this.a.values()) {
            if (Maps.b(b2, obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsRow(@Nullable Object obj) {
        return obj != null && Maps.b(this.a, obj);
    }

    public boolean containsValue(@Nullable Object obj) {
        return obj != null && super.containsValue(obj);
    }

    public V get(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return super.get(obj, obj2);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public int size() {
        int i = 0;
        for (Map size : this.a.values()) {
            i += size.size();
        }
        return i;
    }

    public void clear() {
        this.a.clear();
    }

    private Map<C, V> a(R r) {
        Map<C, V> map = (Map) this.a.get(r);
        if (map != null) {
            return map;
        }
        Map<C, V> map2 = (Map) this.b.get();
        this.a.put(r, map2);
        return map2;
    }

    @CanIgnoreReturnValue
    public V put(R r, C c2, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c2);
        Preconditions.checkNotNull(v);
        return a(r).put(c2, v);
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        Map map = (Map) Maps.a(this.a, obj);
        if (map == null) {
            return null;
        }
        V remove = map.remove(obj2);
        if (map.isEmpty()) {
            this.a.remove(obj);
        }
        return remove;
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Map<R, V> b(Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Object remove = ((Map) entry.getValue()).remove(obj);
            if (remove != null) {
                linkedHashMap.put(entry.getKey(), remove);
                if (((Map) entry.getValue()).isEmpty()) {
                    it.remove();
                }
            }
        }
        return linkedHashMap;
    }

    /* access modifiers changed from: private */
    public boolean a(Object obj, Object obj2, Object obj3) {
        return obj3 != null && obj3.equals(get(obj, obj2));
    }

    /* access modifiers changed from: private */
    public boolean b(Object obj, Object obj2, Object obj3) {
        if (!a(obj, obj2, obj3)) {
            return false;
        }
        remove(obj, obj2);
        return true;
    }

    public Set<Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Cell<R, C, V>> b() {
        return new CellIterator();
    }

    public Map<C, V> row(R r) {
        return new Row(r);
    }

    public Map<R, V> column(C c2) {
        return new Column(c2);
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public Set<C> columnKeySet() {
        Set<C> set = this.c;
        if (set != null) {
            return set;
        }
        ColumnKeySet columnKeySet = new ColumnKeySet();
        this.c = columnKeySet;
        return columnKeySet;
    }

    /* access modifiers changed from: 0000 */
    public Iterator<C> g() {
        return new ColumnKeyIterator();
    }

    public Collection<V> values() {
        return super.values();
    }

    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.d;
        if (map != null) {
            return map;
        }
        Map<R, Map<C, V>> f = f();
        this.d = f;
        return f;
    }

    /* access modifiers changed from: 0000 */
    public Map<R, Map<C, V>> f() {
        return new RowMap();
    }

    public Map<C, Map<R, V>> columnMap() {
        ColumnMap columnMap = this.e;
        if (columnMap != null) {
            return columnMap;
        }
        ColumnMap columnMap2 = new ColumnMap<>();
        this.e = columnMap2;
        return columnMap2;
    }
}
