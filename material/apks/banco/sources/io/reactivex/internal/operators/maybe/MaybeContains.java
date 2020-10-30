package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

public final class MaybeContains<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> a;
    final Object b;

    static final class ContainsMaybeObserver implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Boolean> a;
        final Object b;
        Disposable c;

        ContainsMaybeObserver(SingleObserver<? super Boolean> singleObserver, Object obj) {
            this.a = singleObserver;
            this.b = obj;
        }

        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(Object obj) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onSuccess(Boolean.valueOf(ObjectHelper.equals(obj, this.b)));
        }

        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            this.a.onSuccess(Boolean.valueOf(false));
        }
    }

    public MaybeContains(MaybeSource<T> maybeSource, Object obj) {
        this.a = maybeSource;
        this.b = obj;
    }

    public MaybeSource<T> source() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new ContainsMaybeObserver(singleObserver, this.b));
    }
}
