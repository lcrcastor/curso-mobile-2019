package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Callables {
    private Callables() {
    }

    public static <T> Callable<T> returning(@Nullable final T t) {
        return new Callable<T>() {
            public T call() {
                return t;
            }
        };
    }

    @GwtIncompatible
    @Beta
    public static <T> AsyncCallable<T> asAsyncCallable(final Callable<T> callable, final ListeningExecutorService listeningExecutorService) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(listeningExecutorService);
        return new AsyncCallable<T>() {
            public ListenableFuture<T> call() {
                return listeningExecutorService.submit(callable);
            }
        };
    }

    @GwtIncompatible
    static <T> Callable<T> a(final Callable<T> callable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callable);
        return new Callable<T>() {
            public T call() {
                Thread currentThread = Thread.currentThread();
                String name = currentThread.getName();
                boolean a2 = Callables.b((String) supplier.get(), currentThread);
                try {
                    return callable.call();
                } finally {
                    if (a2) {
                        Callables.b(name, currentThread);
                    }
                }
            }
        };
    }

    @GwtIncompatible
    static Runnable a(final Runnable runnable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable);
        return new Runnable() {
            public void run() {
                Thread currentThread = Thread.currentThread();
                String name = currentThread.getName();
                boolean a2 = Callables.b((String) supplier.get(), currentThread);
                try {
                    runnable.run();
                } finally {
                    if (a2) {
                        Callables.b(name, currentThread);
                    }
                }
            }
        };
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static boolean b(String str, Thread thread) {
        try {
            thread.setName(str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }
}
