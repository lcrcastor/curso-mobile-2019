package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

@Experimental
public final class CompletableDoFinally extends Completable {
    final CompletableSource a;
    final Action b;

    static final class DoFinallyObserver extends AtomicInteger implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 4109457741734051389L;
        final CompletableObserver a;
        final Action b;
        Disposable c;

        DoFinallyObserver(CompletableObserver completableObserver, Action action) {
            this.a = completableObserver;
            this.b = action;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            this.a.onComplete();
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

    public CompletableDoFinally(CompletableSource completableSource, Action action) {
        this.a = completableSource;
        this.b = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new DoFinallyObserver(completableObserver, this.b));
    }
}
