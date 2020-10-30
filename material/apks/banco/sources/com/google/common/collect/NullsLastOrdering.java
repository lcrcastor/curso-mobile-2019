package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class NullsLastOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final Ordering<? super T> a;

    public <S extends T> Ordering<S> nullsLast() {
        return this;
    }

    NullsLastOrdering(Ordering<? super T> ordering) {
        this.a = ordering;
    }

    public int compare(@Nullable T t, @Nullable T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return 1;
        }
        if (t2 == null) {
            return -1;
        }
        return this.a.compare(t, t2);
    }

    public <S extends T> Ordering<S> reverse() {
        return this.a.reverse().nullsFirst();
    }

    public <S extends T> Ordering<S> nullsFirst() {
        return this.a.nullsFirst();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NullsLastOrdering)) {
            return false;
        }
        return this.a.equals(((NullsLastOrdering) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode() ^ -921210296;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(".nullsLast()");
        return sb.toString();
    }
}
