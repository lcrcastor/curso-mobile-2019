package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObserverResourceWrapper<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -8612022020200669122L;
    final Observer<? super T> a;
    final AtomicReference<Disposable> b = new AtomicReference<>();

    public ObserverResourceWrapper(Observer<? super T> observer) {
        this.a = observer;
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this.b, disposable)) {
            this.a.onSubscribe(this);
        }
    }

    public void onNext(T t) {
        this.a.onNext(t);
    }

    public void onError(Throwable th) {
        dispose();
        this.a.onError(th);
    }

    public void onComplete() {
        dispose();
        this.a.onComplete();
    }

    public void dispose() {
        DisposableHelper.dispose(this.b);
        DisposableHelper.dispose(this);
    }

    public boolean isDisposed() {
        return this.b.get() == DisposableHelper.DISPOSED;
    }

    public void setResource(Disposable disposable) {
        DisposableHelper.set(this, disposable);
    }
}
