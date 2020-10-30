package io.reactivex.schedulers;

import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

public final class Timed<T> {
    final T a;
    final long b;
    final TimeUnit c;

    public Timed(@NonNull T t, long j, @NonNull TimeUnit timeUnit) {
        this.a = t;
        this.b = j;
        this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
    }

    @NonNull
    public T value() {
        return this.a;
    }

    @NonNull
    public TimeUnit unit() {
        return this.c;
    }

    public long time() {
        return this.b;
    }

    public long time(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.b, this.c);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) obj;
        if (ObjectHelper.equals(this.a, timed.a) && this.b == timed.b && ObjectHelper.equals(this.c, timed.c)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((this.a != null ? this.a.hashCode() : 0) * 31) + ((int) ((this.b >>> 31) ^ this.b))) * 31) + this.c.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Timed[time=");
        sb.append(this.b);
        sb.append(", unit=");
        sb.append(this.c);
        sb.append(", value=");
        sb.append(this.a);
        sb.append("]");
        return sb.toString();
    }
}
