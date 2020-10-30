package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;

@GwtCompatible
abstract class TransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> c;

    /* access modifiers changed from: 0000 */
    public abstract T a(F f);

    TransformedIterator(Iterator<? extends F> it) {
        this.c = (Iterator) Preconditions.checkNotNull(it);
    }

    public final boolean hasNext() {
        return this.c.hasNext();
    }

    public final T next() {
        return a(this.c.next());
    }

    public final void remove() {
        this.c.remove();
    }
}
