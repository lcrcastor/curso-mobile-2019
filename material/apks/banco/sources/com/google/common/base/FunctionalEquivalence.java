package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Function<F, ? extends T> a;
    private final Equivalence<T> b;

    FunctionalEquivalence(Function<F, ? extends T> function, Equivalence<T> equivalence) {
        this.a = (Function) Preconditions.checkNotNull(function);
        this.b = (Equivalence) Preconditions.checkNotNull(equivalence);
    }

    /* access modifiers changed from: protected */
    public boolean doEquivalent(F f, F f2) {
        return this.b.equivalent(this.a.apply(f), this.a.apply(f2));
    }

    /* access modifiers changed from: protected */
    public int doHash(F f) {
        return this.b.hash(this.a.apply(f));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FunctionalEquivalence)) {
            return false;
        }
        FunctionalEquivalence functionalEquivalence = (FunctionalEquivalence) obj;
        if (!this.a.equals(functionalEquivalence.a) || !this.b.equals(functionalEquivalence.b)) {
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
