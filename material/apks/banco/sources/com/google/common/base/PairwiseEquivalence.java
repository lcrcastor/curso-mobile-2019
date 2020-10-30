package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class PairwiseEquivalence<T> extends Equivalence<Iterable<T>> implements Serializable {
    private static final long serialVersionUID = 1;
    final Equivalence<? super T> a;

    PairwiseEquivalence(Equivalence<? super T> equivalence) {
        this.a = (Equivalence) Preconditions.checkNotNull(equivalence);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean doEquivalent(Iterable<T> iterable, Iterable<T> iterable2) {
        Iterator it = iterable.iterator();
        Iterator it2 = iterable2.iterator();
        do {
            boolean z = false;
            if (!it.hasNext() || !it2.hasNext()) {
                if (!it.hasNext() && !it2.hasNext()) {
                    z = true;
                }
                return z;
            }
        } while (this.a.equivalent(it.next(), it2.next()));
        return false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int doHash(Iterable<T> iterable) {
        int i = 78721;
        for (T hash : iterable) {
            i = (i * 24943) + this.a.hash(hash);
        }
        return i;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof PairwiseEquivalence)) {
            return false;
        }
        return this.a.equals(((PairwiseEquivalence) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1185147655;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(".pairwise()");
        return sb.toString();
    }
}
