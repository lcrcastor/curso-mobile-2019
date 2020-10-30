package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeTakeUntilMaybe<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final MaybeSource<U> a;

    static final class TakeUntilMainMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -2187421758664251153L;
        final MaybeObserver<? super T> a;
        final TakeUntilOtherMaybeObserver<U> b = new TakeUntilOtherMaybeObserver<>(this);

        static final class TakeUntilOtherMaybeObserver<U> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            private static final long serialVersionUID = -1266041316834525931L;
            final TakeUntilMainMaybeObserver<?, U> a;

            TakeUntilOtherMaybeObserver(TakeUntilMainMaybeObserver<?, U> takeUntilMainMaybeObserver) {
                this.a = takeUntilMainMaybeObserver;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(Object obj) {
                this.a.a();
            }

            public void onError(Throwable th) {
                this.a.a(th);
            }

            public void onComplete() {
                this.a.a();
            }
        }

        TakeUntilMainMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.b);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            DisposableHelper.dispose(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (DisposableHelper.dispose(this)) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (DisposableHelper.dispose(this)) {
                this.a.onComplete();
            }
        }
    }

    public MaybeTakeUntilMaybe(MaybeSource<T> maybeSource, MaybeSource<U> maybeSource2) {
        super(maybeSource);
        this.a = maybeSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        TakeUntilMainMaybeObserver takeUntilMainMaybeObserver = new TakeUntilMainMaybeObserver(maybeObserver);
        maybeObserver.onSubscribe(takeUntilMainMaybeObserver);
        this.a.subscribe(takeUntilMainMaybeObserver.b);
        this.source.subscribe(takeUntilMainMaybeObserver);
    }
}
