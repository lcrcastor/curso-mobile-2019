package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeTimeoutPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> a;
    final MaybeSource<? extends T> b;

    static final class TimeoutFallbackMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 8663801314800248617L;
        final MaybeObserver<? super T> a;

        TimeoutFallbackMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }
    }

    static final class TimeoutMainMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -5955289211445418871L;
        final MaybeObserver<? super T> a;
        final TimeoutOtherMaybeObserver<T, U> b = new TimeoutOtherMaybeObserver<>(this);
        final MaybeSource<? extends T> c;
        final TimeoutFallbackMaybeObserver<T> d;

        TimeoutMainMaybeObserver(MaybeObserver<? super T> maybeObserver, MaybeSource<? extends T> maybeSource) {
            this.a = maybeObserver;
            this.c = maybeSource;
            this.d = maybeSource != null ? new TimeoutFallbackMaybeObserver<>(maybeObserver) : null;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            SubscriptionHelper.cancel(this.b);
            TimeoutFallbackMaybeObserver<T> timeoutFallbackMaybeObserver = this.d;
            if (timeoutFallbackMaybeObserver != null) {
                DisposableHelper.dispose(timeoutFallbackMaybeObserver);
            }
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.b);
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.a.onComplete();
            }
        }

        public void a(Throwable th) {
            if (DisposableHelper.dispose(this)) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void a() {
            if (!DisposableHelper.dispose(this)) {
                return;
            }
            if (this.c == null) {
                this.a.onError(new TimeoutException());
            } else {
                this.c.subscribe(this.d);
            }
        }
    }

    static final class TimeoutOtherMaybeObserver<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = 8663801314800248617L;
        final TimeoutMainMaybeObserver<T, U> a;

        TimeoutOtherMaybeObserver(TimeoutMainMaybeObserver<T, U> timeoutMainMaybeObserver) {
            this.a = timeoutMainMaybeObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            ((Subscription) get()).cancel();
            this.a.a();
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.a();
        }
    }

    public MaybeTimeoutPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher, MaybeSource<? extends T> maybeSource2) {
        super(maybeSource);
        this.a = publisher;
        this.b = maybeSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        TimeoutMainMaybeObserver timeoutMainMaybeObserver = new TimeoutMainMaybeObserver(maybeObserver, this.b);
        maybeObserver.onSubscribe(timeoutMainMaybeObserver);
        this.a.subscribe(timeoutMainMaybeObserver.b);
        this.source.subscribe(timeoutMainMaybeObserver);
    }
}
