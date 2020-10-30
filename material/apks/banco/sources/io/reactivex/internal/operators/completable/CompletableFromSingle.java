package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;

public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> a;

    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {
        final CompletableObserver a;

        CompletableFromSingleObserver(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.a.onComplete();
        }
    }

    public CompletableFromSingle(SingleSource<T> singleSource) {
        this.a = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableFromSingleObserver(completableObserver));
    }
}
