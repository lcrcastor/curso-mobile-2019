package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUnsubscribeOn<T> extends Single<T> {
    final SingleSource<T> a;
    final Scheduler b;

    static final class UnsubscribeOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3256698449646456986L;
        final SingleObserver<? super T> a;
        final Scheduler b;
        Disposable c;

        UnsubscribeOnSingleObserver(SingleObserver<? super T> singleObserver, Scheduler scheduler) {
            this.a = singleObserver;
            this.b = scheduler;
        }

        public void dispose() {
            Disposable disposable = (Disposable) getAndSet(DisposableHelper.DISPOSED);
            if (disposable != DisposableHelper.DISPOSED) {
                this.c = disposable;
                this.b.scheduleDirect(this);
            }
        }

        public void run() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }

    public SingleUnsubscribeOn(SingleSource<T> singleSource, Scheduler scheduler) {
        this.a = singleSource;
        this.b = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new UnsubscribeOnSingleObserver(singleObserver, this.b));
    }
}
