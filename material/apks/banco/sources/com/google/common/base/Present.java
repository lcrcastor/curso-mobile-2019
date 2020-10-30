package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
final class Present<T> extends Optional<T> {
    private static final long serialVersionUID = 0;
    private final T a;

    public boolean isPresent() {
        return true;
    }

    Present(T t) {
        this.a = t;
    }

    public T get() {
        return this.a;
    }

    public T or(T t) {
        Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
        return this.a;
    }

    public Optional<T> or(Optional<? extends T> optional) {
        Preconditions.checkNotNull(optional);
        return this;
    }

    public T or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return this.a;
    }

    public T orNull() {
        return this.a;
    }

    public Set<T> asSet() {
        return Collections.singleton(this.a);
    }

    public <V> Optional<V> transform(Function<? super T, V> function) {
        return new Present(Preconditions.checkNotNull(function.apply(this.a), "the Function passed to Optional.transform() must not return null."));
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Present)) {
            return false;
        }
        return this.a.equals(((Present) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode() + 1502476572;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Optional.of(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
