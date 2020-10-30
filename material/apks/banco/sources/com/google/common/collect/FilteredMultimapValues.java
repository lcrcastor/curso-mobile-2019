package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredMultimapValues<K, V> extends AbstractCollection<V> {
    @Weak
    private final FilteredMultimap<K, V> a;

    FilteredMultimapValues(FilteredMultimap<K, V> filteredMultimap) {
        this.a = (FilteredMultimap) Preconditions.checkNotNull(filteredMultimap);
    }

    public Iterator<V> iterator() {
        return Maps.b(this.a.entries().iterator());
    }

    public boolean contains(@Nullable Object obj) {
        return this.a.containsValue(obj);
    }

    public int size() {
        return this.a.size();
    }

    public boolean remove(@Nullable Object obj) {
        Predicate b = this.a.b();
        Iterator it = this.a.a().entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (b.apply(entry) && Objects.equal(entry.getValue(), obj)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        return Iterables.removeIf(this.a.a().entries(), Predicates.and(this.a.b(), Maps.b(Predicates.in(collection))));
    }

    public boolean retainAll(Collection<?> collection) {
        return Iterables.removeIf(this.a.a().entries(), Predicates.and(this.a.b(), Maps.b(Predicates.not(Predicates.in(collection)))));
    }

    public void clear() {
        this.a.clear();
    }
}
