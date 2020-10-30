package io.reactivex.disposables;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SerialDisposable implements Disposable {
    final AtomicReference<Disposable> a;

    public SerialDisposable() {
        this.a = new AtomicReference<>();
    }

    public SerialDisposable(@Nullable Disposable disposable) {
        this.a = new AtomicReference<>(disposable);
    }

    public boolean set(@Nullable Disposable disposable) {
        return DisposableHelper.set(this.a, disposable);
    }

    public boolean replace(@Nullable Disposable disposable) {
        return DisposableHelper.replace(this.a, disposable);
    }

    @Nullable
    public Disposable get() {
        Disposable disposable = (Disposable) this.a.get();
        return disposable == DisposableHelper.DISPOSED ? Disposables.disposed() : disposable;
    }

    public void dispose() {
        DisposableHelper.dispose(this.a);
    }

    public boolean isDisposed() {
        return DisposableHelper.isDisposed((Disposable) this.a.get());
    }
}
