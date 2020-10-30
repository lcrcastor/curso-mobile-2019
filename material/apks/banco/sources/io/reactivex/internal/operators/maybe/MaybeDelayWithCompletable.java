package io.reactivex.internal.operators.maybe;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeDelayWithCompletable<T> extends Maybe<T> {
    final MaybeSource<T> a;
    final CompletableSource b;

    static final class DelayWithMainObserver<T> implements MaybeObserver<T> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super T> b;

        DelayWithMainObserver(AtomicReference<Disposable> atomicReference, MaybeObserver<? super T> maybeObserver) {
            this.a = atomicReference;
            this.b = maybeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.a, disposable);
        }

        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.b.onError(th);
        }

        public void onComplete() {
            this.b.onComplete();
        }
    }

    static final class OtherObserver<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 703409937383992161L;
        final MaybeObserver<? super T> a;
        final MaybeSource<T> b;

        OtherObserver(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.a = maybeObserver;
            this.b = maybeSource;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.b.subscribe(new DelayWithMainObserver(this, this.a));
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public MaybeDelayWithCompletable(MaybeSource<T> maybeSource, CompletableSource completableSource) {
        this.a = maybeSource;
        this.b = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.b.subscribe(new OtherObserver(maybeObserver, this.a));
    }
}
