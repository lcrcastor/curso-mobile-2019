package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
public abstract class ForwardingBlockingDeque<E> extends ForwardingDeque<E> implements BlockingDeque<E> {
    /* access modifiers changed from: protected */
    public abstract BlockingDeque<E> delegate();

    protected ForwardingBlockingDeque() {
    }

    public int remainingCapacity() {
        return delegate().remainingCapacity();
    }

    public void putFirst(E e) {
        delegate().putFirst(e);
    }

    public void putLast(E e) {
        delegate().putLast(e);
    }

    public boolean offerFirst(E e, long j, TimeUnit timeUnit) {
        return delegate().offerFirst(e, j, timeUnit);
    }

    public boolean offerLast(E e, long j, TimeUnit timeUnit) {
        return delegate().offerLast(e, j, timeUnit);
    }

    public E takeFirst() {
        return delegate().takeFirst();
    }

    public E takeLast() {
        return delegate().takeLast();
    }

    public E pollFirst(long j, TimeUnit timeUnit) {
        return delegate().pollFirst(j, timeUnit);
    }

    public E pollLast(long j, TimeUnit timeUnit) {
        return delegate().pollLast(j, timeUnit);
    }

    public void put(E e) {
        delegate().put(e);
    }

    public boolean offer(E e, long j, TimeUnit timeUnit) {
        return delegate().offer(e, j, timeUnit);
    }

    public E take() {
        return delegate().take();
    }

    public E poll(long j, TimeUnit timeUnit) {
        return delegate().poll(j, timeUnit);
    }

    public int drainTo(Collection<? super E> collection) {
        return delegate().drainTo(collection);
    }

    public int drainTo(Collection<? super E> collection, int i) {
        return delegate().drainTo(collection, i);
    }
}
