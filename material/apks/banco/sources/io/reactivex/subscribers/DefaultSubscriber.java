package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    private Subscription a;

    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.validate(this.a, subscription, getClass())) {
            this.a = subscription;
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public final void request(long j) {
        Subscription subscription = this.a;
        if (subscription != null) {
            subscription.request(j);
        }
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        Subscription subscription = this.a;
        this.a = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        request(Long.MAX_VALUE);
    }
}
