package io.reactivex.disposables;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

final class FutureDisposable extends AtomicReference<Future<?>> implements Disposable {
    private static final long serialVersionUID = 6545242830671168775L;
    private final boolean a;

    FutureDisposable(Future<?> future, boolean z) {
        super(future);
        this.a = z;
    }

    public boolean isDisposed() {
        Future future = (Future) get();
        return future == null || future.isDone();
    }

    public void dispose() {
        Future future = (Future) getAndSet(null);
        if (future != null) {
            future.cancel(this.a);
        }
    }
}
