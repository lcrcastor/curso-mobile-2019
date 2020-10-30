package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

@GwtCompatible
abstract class MultitransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> a;
    private Iterator<? extends T> b = Iterators.a();
    private Iterator<? extends T> c;

    /* access modifiers changed from: 0000 */
    public abstract Iterator<? extends T> a(F f);

    MultitransformedIterator(Iterator<? extends F> it) {
        this.a = (Iterator) Preconditions.checkNotNull(it);
    }

    public boolean hasNext() {
        Preconditions.checkNotNull(this.b);
        if (this.b.hasNext()) {
            return true;
        }
        while (this.a.hasNext()) {
            Iterator<? extends T> a2 = a(this.a.next());
            this.b = a2;
            Preconditions.checkNotNull(a2);
            if (this.b.hasNext()) {
                return true;
            }
        }
        return false;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.c = this.b;
        return this.b.next();
    }

    public void remove() {
        CollectPreconditions.a(this.c != null);
        this.c.remove();
        this.c = null;
    }
}
