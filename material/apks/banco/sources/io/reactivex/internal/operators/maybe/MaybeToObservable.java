package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import io.reactivex.internal.observers.DeferredScalarDisposable;

public final class MaybeToObservable<T> extends Observable<T> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;

    static final class MaybeToFlowableSubscriber<T> extends DeferredScalarDisposable<T> implements MaybeObserver<T> {
        private static final long serialVersionUID = 7603343402964826922L;
        Disposable a;

        MaybeToFlowableSubscriber(Observer<? super T> observer) {
            super(observer);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.a, disposable)) {
                this.a = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            complete(t);
        }

        public void onError(Throwable th) {
            error(th);
        }

        public void onComplete() {
            complete();
        }

        public void dispose() {
            super.dispose();
            this.a.dispose();
        }
    }

    public MaybeToObservable(MaybeSource<T> maybeSource) {
        this.a = maybeSource;
    }

    public MaybeSource<T> source() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new MaybeToFlowableSubscriber(observer));
    }
}
