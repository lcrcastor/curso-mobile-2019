package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
    private static final long serialVersionUID = 0;
    final Function<F, ? extends T> a;
    final Ordering<T> b;

    ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
        this.a = (Function) Preconditions.checkNotNull(function);
        this.b = (Ordering) Preconditions.checkNotNull(ordering);
    }

    public int compare(F f, F f2) {
        return this.b.compare(this.a.apply(f), this.a.apply(f2));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByFunctionOrdering)) {
            return false;
        }
        ByFunctionOrdering byFunctionOrdering = (ByFunctionOrdering) obj;
        if (!this.a.equals(byFunctionOrdering.a) || !this.b.equals(byFunctionOrdering.b)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(".onResultOf(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
