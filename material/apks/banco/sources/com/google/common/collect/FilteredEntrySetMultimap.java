package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
final class FilteredEntrySetMultimap<K, V> extends FilteredEntryMultimap<K, V> implements FilteredSetMultimap<K, V> {
    FilteredEntrySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super Entry<K, V>> predicate) {
        super(setMultimap, predicate);
    }

    /* renamed from: d */
    public SetMultimap<K, V> a() {
        return (SetMultimap) this.a;
    }

    public Set<V> get(K k) {
        return (Set) super.get(k);
    }

    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues(k, iterable);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public Set<Entry<K, V>> j() {
        return Sets.filter(a().entries(), b());
    }

    public Set<Entry<K, V>> entries() {
        return (Set) super.entries();
    }
}
