package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class InnerQueuedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -5417183359794346637L;
    final InnerQueuedObserverSupport<T> a;
    final int b;
    SimpleQueue<T> c;
    volatile boolean d;
    int e;

    public InnerQueuedObserver(InnerQueuedObserverSupport<T> innerQueuedObserverSupport, int i) {
        this.a = innerQueuedObserverSupport;
        this.b = i;
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this, disposable)) {
            if (disposable instanceof QueueDisposable) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int requestFusion = queueDisposable.requestFusion(3);
                if (requestFusion == 1) {
                    this.e = requestFusion;
                    this.c = queueDisposable;
                    this.d = true;
                    this.a.innerComplete(this);
                    return;
                } else if (requestFusion == 2) {
                    this.e = requestFusion;
                    this.c = queueDisposable;
                    return;
                }
            }
            this.c = QueueDrainHelper.createQueue(-this.b);
        }
    }

    public void onNext(T t) {
        if (this.e == 0) {
            this.a.innerNext(this, t);
        } else {
            this.a.drain();
        }
    }

    public void onError(Throwable th) {
        this.a.innerError(this, th);
    }

    public void onComplete() {
        this.a.innerComplete(this);
    }

    public void dispose() {
        DisposableHelper.dispose(this);
    }

    public boolean isDisposed() {
        return DisposableHelper.isDisposed((Disposable) get());
    }

    public boolean isDone() {
        return this.d;
    }

    public void setDone() {
        this.d = true;
    }

    public SimpleQueue<T> queue() {
        return this.c;
    }

    public int fusionMode() {
        return this.e;
    }
}
