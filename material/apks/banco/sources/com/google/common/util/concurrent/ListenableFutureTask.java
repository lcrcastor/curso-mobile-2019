package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;

@GwtIncompatible
public class ListenableFutureTask<V> extends FutureTask<V> implements ListenableFuture<V> {
    private final ExecutionList a = new ExecutionList();

    public static <V> ListenableFutureTask<V> create(Callable<V> callable) {
        return new ListenableFutureTask<>(callable);
    }

    public static <V> ListenableFutureTask<V> create(Runnable runnable, @Nullable V v) {
        return new ListenableFutureTask<>(runnable, v);
    }

    ListenableFutureTask(Callable<V> callable) {
        super(callable);
    }

    ListenableFutureTask(Runnable runnable, @Nullable V v) {
        super(runnable, v);
    }

    public void addListener(Runnable runnable, Executor executor) {
        this.a.add(runnable, executor);
    }

    /* access modifiers changed from: protected */
    public void done() {
        this.a.execute();
    }
}
