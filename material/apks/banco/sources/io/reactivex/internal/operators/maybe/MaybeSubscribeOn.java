package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSubscribeOn<T> extends AbstractMaybeWithUpstream<T, T> {
    final Scheduler a;

    static final class SubscribeOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 8571289934935992137L;
        final SequentialDisposable a = new SequentialDisposable();
        final MaybeObserver<? super T> b;

        SubscribeOnMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.b = maybeObserver;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            this.a.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
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

    static final class SubscribeTask<T> implements Runnable {
        final MaybeObserver<? super T> a;
        final MaybeSource<T> b;

        SubscribeTask(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.a = maybeObserver;
            this.b = maybeSource;
        }

        public void run() {
            this.b.subscribe(this.a);
        }
    }

    public MaybeSubscribeOn(MaybeSource<T> maybeSource, Scheduler scheduler) {
        super(maybeSource);
        this.a = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        SubscribeOnMaybeObserver subscribeOnMaybeObserver = new SubscribeOnMaybeObserver(maybeObserver);
        maybeObserver.onSubscribe(subscribeOnMaybeObserver);
        subscribeOnMaybeObserver.a.replace(this.a.scheduleDirect(new SubscribeTask(subscribeOnMaybeObserver, this.source)));
    }
}
