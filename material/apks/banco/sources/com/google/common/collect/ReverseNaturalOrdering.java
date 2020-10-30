package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;

@GwtCompatible(serializable = true)
final class ReverseNaturalOrdering extends Ordering<Comparable> implements Serializable {
    static final ReverseNaturalOrdering a = new ReverseNaturalOrdering();
    private static final long serialVersionUID = 0;

    public String toString() {
        return "Ordering.natural().reverse()";
    }

    /* renamed from: a */
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        if (comparable == comparable2) {
            return 0;
        }
        return comparable2.compareTo(comparable);
    }

    public <S extends Comparable> Ordering<S> reverse() {
        return Ordering.natural();
    }

    /* renamed from: b */
    public <E extends Comparable> E min(E e, E e2) {
        return (Comparable) NaturalOrdering.a.max(e, e2);
    }

    /* renamed from: a */
    public <E extends Comparable> E min(E e, E e2, E e3, E... eArr) {
        return (Comparable) NaturalOrdering.a.max(e, e2, e3, eArr);
    }

    /* renamed from: a */
    public <E extends Comparable> E min(Iterator<E> it) {
        return (Comparable) NaturalOrdering.a.max(it);
    }

    /* renamed from: a */
    public <E extends Comparable> E min(Iterable<E> iterable) {
        return (Comparable) NaturalOrdering.a.max(iterable);
    }

    /* renamed from: c */
    public <E extends Comparable> E max(E e, E e2) {
        return (Comparable) NaturalOrdering.a.min(e, e2);
    }

    /* renamed from: b */
    public <E extends Comparable> E max(E e, E e2, E e3, E... eArr) {
        return (Comparable) NaturalOrdering.a.min(e, e2, e3, eArr);
    }

    /* renamed from: b */
    public <E extends Comparable> E max(Iterator<E> it) {
        return (Comparable) NaturalOrdering.a.min(it);
    }

    /* renamed from: b */
    public <E extends Comparable> E max(Iterable<E> iterable) {
        return (Comparable) NaturalOrdering.a.min(iterable);
    }

    private Object readResolve() {
        return a;
    }

    private ReverseNaturalOrdering() {
    }
}
