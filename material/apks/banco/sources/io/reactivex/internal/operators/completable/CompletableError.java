package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableError extends Completable {
    final Throwable a;

    public CompletableError(Throwable th) {
        this.a = th;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        EmptyDisposable.error(this.a, completableObserver);
    }
}
