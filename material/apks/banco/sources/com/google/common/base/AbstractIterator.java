package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractIterator<T> implements Iterator<T> {
    private State a = State.NOT_READY;
    private T b;

    enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    /* access modifiers changed from: protected */
    public abstract T a();

    protected AbstractIterator() {
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    @Nullable
    public final T b() {
        this.a = State.DONE;
        return null;
    }

    public final boolean hasNext() {
        Preconditions.checkState(this.a != State.FAILED);
        switch (this.a) {
            case READY:
                return true;
            case DONE:
                return false;
            default:
                return c();
        }
    }

    private boolean c() {
        this.a = State.FAILED;
        this.b = a();
        if (this.a == State.DONE) {
            return false;
        }
        this.a = State.READY;
        return true;
    }

    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.a = State.NOT_READY;
        T t = this.b;
        this.b = null;
        return t;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
