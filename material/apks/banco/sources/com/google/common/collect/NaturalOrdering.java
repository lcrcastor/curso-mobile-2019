package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class NaturalOrdering extends Ordering<Comparable> implements Serializable {
    static final NaturalOrdering a = new NaturalOrdering();
    private static final long serialVersionUID = 0;
    private transient Ordering<Comparable> b;
    private transient Ordering<Comparable> c;

    public String toString() {
        return "Ordering.natural()";
    }

    /* renamed from: a */
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }

    public <S extends Comparable> Ordering<S> nullsFirst() {
        Ordering<Comparable> ordering = this.b;
        if (ordering != null) {
            return ordering;
        }
        Ordering<Comparable> nullsFirst = super.nullsFirst();
        this.b = nullsFirst;
        return nullsFirst;
    }

    public <S extends Comparable> Ordering<S> nullsLast() {
        Ordering<Comparable> ordering = this.c;
        if (ordering != null) {
            return ordering;
        }
        Ordering<Comparable> nullsLast = super.nullsLast();
        this.c = nullsLast;
        return nullsLast;
    }

    public <S extends Comparable> Ordering<S> reverse() {
        return ReverseNaturalOrdering.a;
    }

    private Object readResolve() {
        return a;
    }

    private NaturalOrdering() {
    }
}
