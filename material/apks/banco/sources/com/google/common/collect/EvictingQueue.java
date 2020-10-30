package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

@GwtCompatible
@Beta
public final class EvictingQueue<E> extends ForwardingQueue<E> implements Serializable {
    private static final long serialVersionUID = 0;
    @VisibleForTesting
    final int a;
    private final Queue<E> b;

    private EvictingQueue(int i) {
        Preconditions.checkArgument(i >= 0, "maxSize (%s) must >= 0", i);
        this.b = new ArrayDeque(i);
        this.a = i;
    }

    public static <E> EvictingQueue<E> create(int i) {
        return new EvictingQueue<>(i);
    }

    public int remainingCapacity() {
        return this.a - size();
    }

    /* access modifiers changed from: protected */
    public Queue<E> delegate() {
        return this.b;
    }

    @CanIgnoreReturnValue
    public boolean offer(E e) {
        return add(e);
    }

    @CanIgnoreReturnValue
    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        if (this.a == 0) {
            return true;
        }
        if (size() == this.a) {
            this.b.remove();
        }
        this.b.add(e);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        if (size < this.a) {
            return standardAddAll(collection);
        }
        clear();
        return Iterables.addAll(this, Iterables.skip(collection, size - this.a));
    }

    public boolean contains(Object obj) {
        return delegate().contains(Preconditions.checkNotNull(obj));
    }

    @CanIgnoreReturnValue
    public boolean remove(Object obj) {
        return delegate().remove(Preconditions.checkNotNull(obj));
    }
}
