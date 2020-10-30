package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
    private T a;

    /* access modifiers changed from: protected */
    public abstract T computeNext(T t);

    protected AbstractSequentialIterator(@Nullable T t) {
        this.a = t;
    }

    public final boolean hasNext() {
        return this.a != null;
    }

    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return this.a;
        } finally {
            this.a = computeNext(this.a);
        }
    }
}
