package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleObserveOn<T> extends Single<T> {
    final SingleSource<T> a;
    final Scheduler b;

    static final class ObserveOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3528003840217436037L;
        final SingleObserver<? super T> a;
        final Scheduler b;
        T c;
        Throwable d;

        ObserveOnSingleObserver(SingleObserver<? super T> singleObserver, Scheduler scheduler) {
            this.a = singleObserver;
            this.b = scheduler;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.c = t;
            DisposableHelper.replace(this, this.b.scheduleDirect(this));
        }

        public void onError(Throwable th) {
            this.d = th;
            DisposableHelper.replace(this, this.b.scheduleDirect(this));
        }

        public void run() {
            Throwable th = this.d;
            if (th != null) {
                this.a.onError(th);
            } else {
                this.a.onSuccess(this.c);
            }
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public SingleObserveOn(SingleSource<T> singleSource, Scheduler scheduler) {
        this.a = singleSource;
        this.b = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new ObserveOnSingleObserver(singleObserver, this.b));
    }
}
