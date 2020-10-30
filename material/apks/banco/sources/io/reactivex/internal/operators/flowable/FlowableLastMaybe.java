package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableLastMaybe<T> extends Maybe<T> {
    final Publisher<T> a;

    static final class LastSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> a;
        Subscription b;
        T c;

        LastSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void dispose() {
            this.b.cancel();
            this.b = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.b == SubscriptionHelper.CANCELLED;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            this.c = t;
        }

        public void onError(Throwable th) {
            this.b = SubscriptionHelper.CANCELLED;
            this.c = null;
            this.a.onError(th);
        }

        public void onComplete() {
            this.b = SubscriptionHelper.CANCELLED;
            T t = this.c;
            if (t != null) {
                this.c = null;
                this.a.onSuccess(t);
                return;
            }
            this.a.onComplete();
        }
    }

    public FlowableLastMaybe(Publisher<T> publisher) {
        this.a = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new LastSubscriber(maybeObserver));
    }
}
