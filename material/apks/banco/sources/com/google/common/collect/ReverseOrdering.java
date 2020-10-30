package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class ReverseOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final Ordering<? super T> a;

    ReverseOrdering(Ordering<? super T> ordering) {
        this.a = (Ordering) Preconditions.checkNotNull(ordering);
    }

    public int compare(T t, T t2) {
        return this.a.compare(t2, t);
    }

    public <S extends T> Ordering<S> reverse() {
        return this.a;
    }

    public <E extends T> E min(E e, E e2) {
        return this.a.max(e, e2);
    }

    public <E extends T> E min(E e, E e2, E e3, E... eArr) {
        return this.a.max(e, e2, e3, eArr);
    }

    public <E extends T> E min(Iterator<E> it) {
        return this.a.max(it);
    }

    public <E extends T> E min(Iterable<E> iterable) {
        return this.a.max(iterable);
    }

    public <E extends T> E max(E e, E e2) {
        return this.a.min(e, e2);
    }

    public <E extends T> E max(E e, E e2, E e3, E... eArr) {
        return this.a.min(e, e2, e3, eArr);
    }

    public <E extends T> E max(Iterator<E> it) {
        return this.a.min(it);
    }

    public <E extends T> E max(Iterable<E> iterable) {
        return this.a.min(iterable);
    }

    public int hashCode() {
        return -this.a.hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ReverseOrdering)) {
            return false;
        }
        return this.a.equals(((ReverseOrdering) obj).a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(".reverse()");
        return sb.toString();
    }
}
