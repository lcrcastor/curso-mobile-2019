package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    private static final RangeMap b = new RangeMap() {
        public void clear() {
        }

        @Nullable
        public Object get(Comparable comparable) {
            return null;
        }

        @Nullable
        public Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public void put(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot insert range ");
            sb.append(range);
            sb.append(" into an empty subRangeMap");
            throw new IllegalArgumentException(sb.toString());
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    /* access modifiers changed from: private */
    public final NavigableMap<Cut<K>, RangeMapEntry<K, V>> a = Maps.newTreeMap();

    final class AsMapOfRanges extends IteratorBasedAbstractMap<Range<K>, V> {
        final Iterable<Entry<Range<K>, V>> a;

        AsMapOfRanges(Iterable<RangeMapEntry<K, V>> iterable) {
            this.a = iterable;
        }

        public boolean containsKey(@Nullable Object obj) {
            return get(obj) != null;
        }

        public V get(@Nullable Object obj) {
            if (obj instanceof Range) {
                Range range = (Range) obj;
                RangeMapEntry rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.a.get(range.b);
                if (rangeMapEntry != null && rangeMapEntry.getKey().equals(range)) {
                    return rangeMapEntry.getValue();
                }
            }
            return null;
        }

        public int size() {
            return TreeRangeMap.this.a.size();
        }

        /* access modifiers changed from: 0000 */
        public Iterator<Entry<Range<K>, V>> b() {
            return this.a.iterator();
        }
    }

    class SubRangeMap implements RangeMap<K, V> {
        /* access modifiers changed from: private */
        public final Range<K> b;

        class SubRangeMapAsMap extends AbstractMap<Range<K>, V> {
            SubRangeMapAsMap() {
            }

            public boolean containsKey(Object obj) {
                return get(obj) != null;
            }

            public V get(Object obj) {
                RangeMapEntry rangeMapEntry;
                try {
                    if (obj instanceof Range) {
                        Range range = (Range) obj;
                        if (SubRangeMap.this.b.encloses(range)) {
                            if (!range.isEmpty()) {
                                if (range.b.compareTo(SubRangeMap.this.b.b) == 0) {
                                    Entry floorEntry = TreeRangeMap.this.a.floorEntry(range.b);
                                    rangeMapEntry = floorEntry != null ? (RangeMapEntry) floorEntry.getValue() : null;
                                } else {
                                    rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.a.get(range.b);
                                }
                                if (rangeMapEntry != null && rangeMapEntry.getKey().isConnected(SubRangeMap.this.b) && rangeMapEntry.getKey().intersection(SubRangeMap.this.b).equals(range)) {
                                    return rangeMapEntry.getValue();
                                }
                            }
                        }
                        return null;
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }

            public V remove(Object obj) {
                V v = get(obj);
                if (v == null) {
                    return null;
                }
                TreeRangeMap.this.remove((Range) obj);
                return v;
            }

            public void clear() {
                SubRangeMap.this.clear();
            }

            /* access modifiers changed from: private */
            public boolean a(Predicate<? super Entry<Range<K>, V>> predicate) {
                ArrayList<Range> newArrayList = Lists.newArrayList();
                for (Entry entry : entrySet()) {
                    if (predicate.apply(entry)) {
                        newArrayList.add(entry.getKey());
                    }
                }
                for (Range remove : newArrayList) {
                    TreeRangeMap.this.remove(remove);
                }
                return !newArrayList.isEmpty();
            }

            public Set<Range<K>> keySet() {
                return new KeySet<Range<K>, V>(this) {
                    public boolean remove(@Nullable Object obj) {
                        return SubRangeMapAsMap.this.remove(obj) != null;
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.a(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.a()));
                    }
                };
            }

            public Set<Entry<Range<K>, V>> entrySet() {
                return new EntrySet<Range<K>, V>() {
                    /* access modifiers changed from: 0000 */
                    public Map<Range<K>, V> a() {
                        return SubRangeMapAsMap.this;
                    }

                    public Iterator<Entry<Range<K>, V>> iterator() {
                        return SubRangeMapAsMap.this.a();
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.a(Predicates.not(Predicates.in(collection)));
                    }

                    public int size() {
                        return Iterators.size(iterator());
                    }

                    public boolean isEmpty() {
                        return !iterator().hasNext();
                    }
                };
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<Range<K>, V>> a() {
                if (SubRangeMap.this.b.isEmpty()) {
                    return Iterators.a();
                }
                final Iterator it = TreeRangeMap.this.a.tailMap((Cut) MoreObjects.firstNonNull(TreeRangeMap.this.a.floorKey(SubRangeMap.this.b.b), SubRangeMap.this.b.b), true).values().iterator();
                return new AbstractIterator<Entry<Range<K>, V>>() {
                    /* access modifiers changed from: protected */
                    /* renamed from: a */
                    public Entry<Range<K>, V> computeNext() {
                        while (it.hasNext()) {
                            RangeMapEntry rangeMapEntry = (RangeMapEntry) it.next();
                            if (rangeMapEntry.b().compareTo(SubRangeMap.this.b.c) >= 0) {
                                return (Entry) endOfData();
                            }
                            if (rangeMapEntry.c().compareTo(SubRangeMap.this.b.b) > 0) {
                                return Maps.immutableEntry(rangeMapEntry.getKey().intersection(SubRangeMap.this.b), rangeMapEntry.getValue());
                            }
                        }
                        return (Entry) endOfData();
                    }
                };
            }

            public Collection<V> values() {
                return new Values<Range<K>, V>(this) {
                    public boolean removeAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.a(Predicates.compose(Predicates.in(collection), Maps.b()));
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.a(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.b()));
                    }
                };
            }
        }

        SubRangeMap(Range<K> range) {
            this.b = range;
        }

        @Nullable
        public V get(K k) {
            if (this.b.contains(k)) {
                return TreeRangeMap.this.get(k);
            }
            return null;
        }

        @Nullable
        public Entry<Range<K>, V> getEntry(K k) {
            if (this.b.contains(k)) {
                Entry entry = TreeRangeMap.this.getEntry(k);
                if (entry != null) {
                    return Maps.immutableEntry(((Range) entry.getKey()).intersection(this.b), entry.getValue());
                }
            }
            return null;
        }

        public Range<K> span() {
            Cut<C> cut;
            Cut<C> cut2;
            Entry floorEntry = TreeRangeMap.this.a.floorEntry(this.b.b);
            if (floorEntry == null || ((RangeMapEntry) floorEntry.getValue()).c().compareTo(this.b.b) <= 0) {
                cut = (Cut) TreeRangeMap.this.a.ceilingKey(this.b.b);
                if (cut == null || cut.compareTo(this.b.c) >= 0) {
                    throw new NoSuchElementException();
                }
            } else {
                cut = this.b.b;
            }
            Entry lowerEntry = TreeRangeMap.this.a.lowerEntry(this.b.c);
            if (lowerEntry == null) {
                throw new NoSuchElementException();
            }
            if (((RangeMapEntry) lowerEntry.getValue()).c().compareTo(this.b.c) >= 0) {
                cut2 = this.b.c;
            } else {
                cut2 = ((RangeMapEntry) lowerEntry.getValue()).c();
            }
            return Range.a(cut, cut2);
        }

        public void put(Range<K> range, V v) {
            Preconditions.checkArgument(this.b.encloses(range), "Cannot put range %s into a subRangeMap(%s)", (Object) range, (Object) this.b);
            TreeRangeMap.this.put(range, v);
        }

        public void putAll(RangeMap<K, V> rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                Range span = rangeMap.span();
                Preconditions.checkArgument(this.b.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", (Object) span, (Object) this.b);
                TreeRangeMap.this.putAll(rangeMap);
            }
        }

        public void clear() {
            TreeRangeMap.this.remove(this.b);
        }

        public void remove(Range<K> range) {
            if (range.isConnected(this.b)) {
                TreeRangeMap.this.remove(range.intersection(this.b));
            }
        }

        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (!range.isConnected(this.b)) {
                return TreeRangeMap.this.a();
            }
            return TreeRangeMap.this.subRangeMap(range.intersection(this.b));
        }

        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap();
        }

        /* JADX WARNING: type inference failed for: r0v0, types: [java.util.Map<com.google.common.collect.Range<K>, V>, com.google.common.collect.TreeRangeMap$SubRangeMap$1] */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [java.util.Map<com.google.common.collect.Range<K>, V>, com.google.common.collect.TreeRangeMap$SubRangeMap$1]
          assigns: [com.google.common.collect.TreeRangeMap$SubRangeMap$1]
          uses: [java.util.Map<com.google.common.collect.Range<K>, V>]
          mth insns count: 2
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.Map<com.google.common.collect.Range<K>, V> asDescendingMapOfRanges() {
            /*
                r1 = this;
                com.google.common.collect.TreeRangeMap$SubRangeMap$1 r0 = new com.google.common.collect.TreeRangeMap$SubRangeMap$1
                r0.<init>()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeRangeMap.SubRangeMap.asDescendingMapOfRanges():java.util.Map");
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof RangeMap)) {
                return false;
            }
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }

        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        public String toString() {
            return asMapOfRanges().toString();
        }
    }

    static final class RangeMapEntry<K extends Comparable, V> extends AbstractMapEntry<Range<K>, V> {
        private final Range<K> a;
        private final V b;

        RangeMapEntry(Cut<K> cut, Cut<K> cut2, V v) {
            this(Range.a(cut, cut2), v);
        }

        RangeMapEntry(Range<K> range, V v) {
            this.a = range;
            this.b = v;
        }

        /* renamed from: a */
        public Range<K> getKey() {
            return this.a;
        }

        public V getValue() {
            return this.b;
        }

        public boolean a(K k) {
            return this.a.contains(k);
        }

        /* access modifiers changed from: 0000 */
        public Cut<K> b() {
            return this.a.b;
        }

        /* access modifiers changed from: 0000 */
        public Cut<K> c() {
            return this.a.c;
        }
    }

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<>();
    }

    private TreeRangeMap() {
    }

    @Nullable
    public V get(K k) {
        Entry entry = getEntry(k);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Nullable
    public Entry<Range<K>, V> getEntry(K k) {
        Entry floorEntry = this.a.floorEntry(Cut.b(k));
        if (floorEntry == null || !((RangeMapEntry) floorEntry.getValue()).a(k)) {
            return null;
        }
        return (Entry) floorEntry.getValue();
    }

    public void put(Range<K> range, V v) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(v);
            remove(range);
            this.a.put(range.b, new RangeMapEntry(range, v));
        }
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        for (Entry entry : rangeMap.asMapOfRanges().entrySet()) {
            put((Range) entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.a.clear();
    }

    public Range<K> span() {
        Entry firstEntry = this.a.firstEntry();
        Entry lastEntry = this.a.lastEntry();
        if (firstEntry != null) {
            return Range.a(((RangeMapEntry) firstEntry.getValue()).getKey().b, ((RangeMapEntry) lastEntry.getValue()).getKey().c);
        }
        throw new NoSuchElementException();
    }

    private void a(Cut<K> cut, Cut<K> cut2, V v) {
        this.a.put(cut, new RangeMapEntry(cut, cut2, v));
    }

    public void remove(Range<K> range) {
        if (!range.isEmpty()) {
            Entry lowerEntry = this.a.lowerEntry(range.b);
            if (lowerEntry != null) {
                RangeMapEntry rangeMapEntry = (RangeMapEntry) lowerEntry.getValue();
                if (rangeMapEntry.c().compareTo(range.b) > 0) {
                    if (rangeMapEntry.c().compareTo(range.c) > 0) {
                        a(range.c, rangeMapEntry.c(), ((RangeMapEntry) lowerEntry.getValue()).getValue());
                    }
                    a(rangeMapEntry.b(), range.b, ((RangeMapEntry) lowerEntry.getValue()).getValue());
                }
            }
            Entry lowerEntry2 = this.a.lowerEntry(range.c);
            if (lowerEntry2 != null) {
                RangeMapEntry rangeMapEntry2 = (RangeMapEntry) lowerEntry2.getValue();
                if (rangeMapEntry2.c().compareTo(range.c) > 0) {
                    a(range.c, rangeMapEntry2.c(), ((RangeMapEntry) lowerEntry2.getValue()).getValue());
                    this.a.remove(range.b);
                }
            }
            this.a.subMap(range.b, range.c).clear();
        }
    }

    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this.a.values());
    }

    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this.a.descendingMap().values());
    }

    public RangeMap<K, V> subRangeMap(Range<K> range) {
        if (range.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(range);
    }

    /* access modifiers changed from: private */
    public RangeMap<K, V> a() {
        return b;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof RangeMap)) {
            return false;
        }
        return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public String toString() {
        return this.a.values().toString();
    }
}
