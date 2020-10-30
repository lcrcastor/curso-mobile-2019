package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

@GwtCompatible(emulated = true)
abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
    private transient Comparator<? super E> a;
    private transient NavigableSet<E> b;
    private transient Set<Entry<E>> c;

    /* access modifiers changed from: 0000 */
    public abstract SortedMultiset<E> a();

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Entry<E>> b();

    DescendingMultiset() {
    }

    public Comparator<? super E> comparator() {
        Comparator<? super E> comparator = this.a;
        if (comparator != null) {
            return comparator;
        }
        Ordering reverse = Ordering.from(a().comparator()).reverse();
        this.a = reverse;
        return reverse;
    }

    public NavigableSet<E> elementSet() {
        NavigableSet<E> navigableSet = this.b;
        if (navigableSet != null) {
            return navigableSet;
        }
        NavigableElementSet navigableElementSet = new NavigableElementSet(this);
        this.b = navigableElementSet;
        return navigableElementSet;
    }

    public Entry<E> pollFirstEntry() {
        return a().pollLastEntry();
    }

    public Entry<E> pollLastEntry() {
        return a().pollFirstEntry();
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return a().tailMultiset(e, boundType).descendingMultiset();
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return a().subMultiset(e2, boundType2, e, boundType).descendingMultiset();
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return a().headMultiset(e, boundType).descendingMultiset();
    }

    /* access modifiers changed from: protected */
    public Multiset<E> delegate() {
        return a();
    }

    public SortedMultiset<E> descendingMultiset() {
        return a();
    }

    public Entry<E> firstEntry() {
        return a().lastEntry();
    }

    public Entry<E> lastEntry() {
        return a().firstEntry();
    }

    public Set<Entry<E>> entrySet() {
        Set<Entry<E>> set = this.c;
        if (set != null) {
            return set;
        }
        Set<Entry<E>> c2 = c();
        this.c = c2;
        return c2;
    }

    /* access modifiers changed from: 0000 */
    public Set<Entry<E>> c() {
        return new EntrySet<E>() {
            /* access modifiers changed from: 0000 */
            public Multiset<E> a() {
                return DescendingMultiset.this;
            }

            public Iterator<Entry<E>> iterator() {
                return DescendingMultiset.this.b();
            }

            public int size() {
                return DescendingMultiset.this.a().entrySet().size();
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.a((Multiset<E>) this);
    }

    public Object[] toArray() {
        return standardToArray();
    }

    public <T> T[] toArray(T[] tArr) {
        return standardToArray(tArr);
    }

    public String toString() {
        return entrySet().toString();
    }
}
