package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeDetach<T> extends AbstractMaybeWithUpstream<T, T> {

    static final class DetachMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        MaybeObserver<? super T> a;
        Disposable b;

        DetachMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void dispose() {
            this.a = null;
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

        public void onSuccess(T t) {
            this.b = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.a;
            if (maybeObserver != null) {
                maybeObserver.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.a;
            if (maybeObserver != null) {
                maybeObserver.onError(th);
            }
        }

        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.a;
            if (maybeObserver != null) {
                maybeObserver.onComplete();
            }
        }
    }

    public MaybeDetach(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new DetachMaybeObserver(maybeObserver));
    }
}
