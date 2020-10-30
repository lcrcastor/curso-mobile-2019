package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class CompletableHide extends Completable {
    final CompletableSource a;

    static final class HideCompletableObserver implements CompletableObserver, Disposable {
        final CompletableObserver a;
        Disposable b;

        HideCompletableObserver(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        public void dispose() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }
    }

    public CompletableHide(CompletableSource completableSource) {
        this.a = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new HideCompletableObserver(completableObserver));
    }
}
