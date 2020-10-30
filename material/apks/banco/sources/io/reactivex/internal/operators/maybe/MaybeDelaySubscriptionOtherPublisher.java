package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeDelaySubscriptionOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> a;

    static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 706635022205076709L;
        final MaybeObserver<? super T> a;

        DelayMaybeObserver(MaybeObserver<? super T> maybeObserver) {
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

    static final class OtherSubscriber<T> implements FlowableSubscriber<Object>, Disposable {
        final DelayMaybeObserver<T> a;
        MaybeSource<T> b;
        Subscription c;

        OtherSubscriber(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.a = new DelayMaybeObserver<>(maybeObserver);
            this.b = maybeSource;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c.cancel();
                this.c = SubscriptionHelper.CANCELLED;
                a();
            }
        }

        public void onError(Throwable th) {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c = SubscriptionHelper.CANCELLED;
                this.a.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c = SubscriptionHelper.CANCELLED;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            MaybeSource<T> maybeSource = this.b;
            this.b = null;
            maybeSource.subscribe(this.a);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.a.get());
        }

        public void dispose() {
            this.c.cancel();
            this.c = SubscriptionHelper.CANCELLED;
            DisposableHelper.dispose(this.a);
        }
    }

    public MaybeDelaySubscriptionOtherPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher) {
        super(maybeSource);
        this.a = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new OtherSubscriber(maybeObserver, this.source));
    }
}
