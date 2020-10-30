package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

@Experimental
public final class SingleDoFinally<T> extends Single<T> {
    final SingleSource<T> a;
    final Action b;

    static final class DoFinallyObserver<T> extends AtomicInteger implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 4109457741734051389L;
        final SingleObserver<? super T> a;
        final Action b;
        Disposable c;

        DoFinallyObserver(SingleObserver<? super T> singleObserver, Action action) {
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
            a();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public SingleDoFinally(SingleSource<T> singleSource, Action action) {
        this.a = singleSource;
        this.b = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new DoFinallyObserver(singleObserver, this.b));
    }
}
