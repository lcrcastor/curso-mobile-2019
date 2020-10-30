package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubscribeOn<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final Scheduler b;

    static final class SubscribeOnObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 7000911171163930287L;
        final SingleObserver<? super T> a;
        final SequentialDisposable b = new SequentialDisposable();
        final SingleSource<? extends T> c;

        SubscribeOnObserver(SingleObserver<? super T> singleObserver, SingleSource<? extends T> singleSource) {
            this.a = singleObserver;
            this.c = singleSource;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            this.b.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void run() {
            this.c.subscribe(this);
        }
    }

    public SingleSubscribeOn(SingleSource<? extends T> singleSource, Scheduler scheduler) {
        this.a = singleSource;
        this.b = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        SubscribeOnObserver subscribeOnObserver = new SubscribeOnObserver(singleObserver, this.a);
        singleObserver.onSubscribe(subscribeOnObserver);
        subscribeOnObserver.b.replace(this.b.scheduleDirect(subscribeOnObserver));
    }
}
