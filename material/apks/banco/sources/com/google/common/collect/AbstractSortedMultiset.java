package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
    final Comparator<? super E> a;
    private transient SortedMultiset<E> b;

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Entry<E>> e();

    AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    AbstractSortedMultiset(Comparator<? super E> comparator) {
        this.a = (Comparator) Preconditions.checkNotNull(comparator);
    }

    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public NavigableSet<E> c() {
        return new NavigableElementSet(this);
    }

    public Comparator<? super E> comparator() {
        return this.a;
    }

    public Entry<E> firstEntry() {
        Iterator a2 = a();
        if (a2.hasNext()) {
            return (Entry) a2.next();
        }
        return null;
    }

    public Entry<E> lastEntry() {
        Iterator e = e();
        if (e.hasNext()) {
            return (Entry) e.next();
        }
        return null;
    }

    public Entry<E> pollFirstEntry() {
        Iterator a2 = a();
        if (!a2.hasNext()) {
            return null;
        }
        Entry entry = (Entry) a2.next();
        Entry<E> immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        a2.remove();
        return immutableEntry;
    }

    public Entry<E> pollLastEntry() {
        Iterator e = e();
        if (!e.hasNext()) {
            return null;
        }
        Entry entry = (Entry) e.next();
        Entry<E> immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        e.remove();
        return immutableEntry;
    }

    public SortedMultiset<E> subMultiset(@Nullable E e, BoundType boundType, @Nullable E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    /* access modifiers changed from: 0000 */
    public Iterator<E> f() {
        return Multisets.a((Multiset<E>) descendingMultiset());
    }

    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.b;
        if (sortedMultiset != null) {
            return sortedMultiset;
        }
        SortedMultiset<E> g = g();
        this.b = g;
        return g;
    }

    /* access modifiers changed from: 0000 */
    public SortedMultiset<E> g() {
        return new DescendingMultiset<E>() {
            /* access modifiers changed from: 0000 */
            public SortedMultiset<E> a() {
                return AbstractSortedMultiset.this;
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> b() {
                return AbstractSortedMultiset.this.e();
            }

            public Iterator<E> iterator() {
                return AbstractSortedMultiset.this.f();
            }
        };
    }
}
