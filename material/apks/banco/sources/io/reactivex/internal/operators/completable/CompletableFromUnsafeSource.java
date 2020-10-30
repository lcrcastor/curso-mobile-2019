package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;

public final class CompletableFromUnsafeSource extends Completable {
    final CompletableSource a;

    public CompletableFromUnsafeSource(CompletableSource completableSource) {
        this.a = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(completableObserver);
    }
}
