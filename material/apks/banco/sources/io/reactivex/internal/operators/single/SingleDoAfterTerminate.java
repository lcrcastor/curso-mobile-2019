package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleDoAfterTerminate<T> extends Single<T> {
    final SingleSource<T> a;
    final Action b;

    static final class DoAfterTerminateObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> a;
        final Action b;
        Disposable c;

        DoAfterTerminateObserver(SingleObserver<? super T> singleObserver, Action action) {
            this.a = singleObserver;
            this.b = action;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
            a();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        private void a() {
            try {
                this.b.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    public SingleDoAfterTerminate(SingleSource<T> singleSource, Action action) {
        this.a = singleSource;
        this.b = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new DoAfterTerminateObserver(singleObserver, this.b));
    }
}
