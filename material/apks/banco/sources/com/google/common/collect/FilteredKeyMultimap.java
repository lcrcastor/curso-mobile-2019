package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    final Multimap<K, V> a;
    final Predicate<? super K> b;

    class Entries extends ForwardingCollection<Entry<K, V>> {
        Entries() {
        }

        /* access modifiers changed from: protected */
        public Collection<Entry<K, V>> delegate() {
            return Collections2.filter(FilteredKeyMultimap.this.a.entries(), FilteredKeyMultimap.this.b());
        }

        public boolean remove(@Nullable Object obj) {
            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;
                if (FilteredKeyMultimap.this.a.containsKey(entry.getKey()) && FilteredKeyMultimap.this.b.apply(entry.getKey())) {
                    return FilteredKeyMultimap.this.a.remove(entry.getKey(), entry.getValue());
                }
            }
            return false;
        }
    }

    static class AddRejectingList<K, V> extends ForwardingList<V> {
        final K a;

        AddRejectingList(K k) {
            this.a = k;
        }

        public boolean add(V v) {
            add(0, v);
            return true;
        }

        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        public void add(int i, V v) {
            Preconditions.checkPositionIndex(i, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("Key does not satisfy predicate: ");
            sb.append(this.a);
            throw new IllegalArgumentException(sb.toString());
        }

        @CanIgnoreReturnValue
        public boolean addAll(int i, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(i, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("Key does not satisfy predicate: ");
            sb.append(this.a);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public List<V> delegate() {
            return Collections.emptyList();
        }
    }

    static class AddRejectingSet<K, V> extends ForwardingSet<V> {
        final K a;

        AddRejectingSet(K k) {
            this.a = k;
        }

        public boolean add(V v) {
            StringBuilder sb = new StringBuilder();
            sb.append("Key does not satisfy predicate: ");
            sb.append(this.a);
            throw new IllegalArgumentException(sb.toString());
        }

        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            StringBuilder sb = new StringBuilder();
            sb.append("Key does not satisfy predicate: ");
            sb.append(this.a);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public Set<V> delegate() {
            return Collections.emptySet();
        }
    }

    FilteredKeyMultimap(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.a = (Multimap) Preconditions.checkNotNull(multimap);
        this.b = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public Multimap<K, V> a() {
        return this.a;
    }

    public Predicate<? super Entry<K, V>> b() {
        return Maps.a(this.b);
    }

    public int size() {
        int i = 0;
        for (Collection size : asMap().values()) {
            i += size.size();
        }
        return i;
    }

    public boolean containsKey(@Nullable Object obj) {
        if (this.a.containsKey(obj)) {
            return this.b.apply(obj);
        }
        return false;
    }

    public Collection<V> removeAll(Object obj) {
        return containsKey(obj) ? this.a.removeAll(obj) : e();
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> e() {
        if (this.a instanceof SetMultimap) {
            return ImmutableSet.of();
        }
        return ImmutableList.of();
    }

    public void clear() {
        keySet().clear();
    }

    /* access modifiers changed from: 0000 */
    public Set<K> f() {
        return Sets.filter(this.a.keySet(), this.b);
    }

    public Collection<V> get(K k) {
        if (this.b.apply(k)) {
            return this.a.get(k);
        }
        if (this.a instanceof SetMultimap) {
            return new AddRejectingSet(k);
        }
        return new AddRejectingList(k);
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> h() {
        throw new AssertionError("should never be called");
    }

    /* access modifiers changed from: 0000 */
    public Collection<Entry<K, V>> j() {
        return new Entries();
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> l() {
        return new FilteredMultimapValues(this);
    }

    /* access modifiers changed from: 0000 */
    public Map<K, Collection<V>> i() {
        return Maps.filterKeys(this.a.asMap(), this.b);
    }

    /* access modifiers changed from: 0000 */
    public Multiset<K> k() {
        return Multisets.filter(this.a.keys(), this.b);
    }
}
