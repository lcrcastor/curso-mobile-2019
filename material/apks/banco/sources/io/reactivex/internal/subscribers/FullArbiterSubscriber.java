package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.FullArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscription;

public final class FullArbiterSubscriber<T> implements FlowableSubscriber<T> {
    final FullArbiter<T> a;
    Subscription b;

    public FullArbiterSubscriber(FullArbiter<T> fullArbiter) {
        this.a = fullArbiter;
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.b, subscription)) {
            this.b = subscription;
            this.a.setSubscription(subscription);
        }
    }

    public void onNext(T t) {
        this.a.onNext(t, this.b);
    }

    public void onError(Throwable th) {
        this.a.onError(th, this.b);
    }

    public void onComplete() {
        this.a.onComplete(this.b);
    }
}
