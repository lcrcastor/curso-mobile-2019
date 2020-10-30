package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableMap<T, Integer> a;

    ExplicitOrdering(List<T> list) {
        this(Maps.a((Collection<E>) list));
    }

    ExplicitOrdering(ImmutableMap<T, Integer> immutableMap) {
        this.a = immutableMap;
    }

    public int compare(T t, T t2) {
        return a(t) - a(t2);
    }

    private int a(T t) {
        Integer num = (Integer) this.a.get(t);
        if (num != null) {
            return num.intValue();
        }
        throw new IncomparableValueException(t);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ExplicitOrdering)) {
            return false;
        }
        return this.a.equals(((ExplicitOrdering) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordering.explicit(");
        sb.append(this.a.keySet());
        sb.append(")");
        return sb.toString();
    }
}
