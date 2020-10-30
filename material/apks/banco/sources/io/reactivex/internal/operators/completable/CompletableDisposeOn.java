package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableDisposeOn extends Completable {
    final CompletableSource a;
    final Scheduler b;

    static final class CompletableObserverImplementation implements CompletableObserver, Disposable, Runnable {
        final CompletableObserver a;
        final Scheduler b;
        Disposable c;
        volatile boolean d;

        CompletableObserverImplementation(CompletableObserver completableObserver, Scheduler scheduler) {
            this.a = completableObserver;
            this.b = scheduler;
        }

        public void onComplete() {
            if (!this.d) {
                this.a.onComplete();
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.d = true;
            this.b.scheduleDirect(this);
        }

        public boolean isDisposed() {
            return this.d;
        }

        public void run() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }
    }

    public CompletableDisposeOn(CompletableSource completableSource, Scheduler scheduler) {
        this.a = completableSource;
        this.b = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableObserverImplementation(completableObserver, this.b));
    }
}
