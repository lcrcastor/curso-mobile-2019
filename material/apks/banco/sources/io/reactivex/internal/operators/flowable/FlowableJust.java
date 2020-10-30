package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import org.reactivestreams.Subscriber;

public final class FlowableJust<T> extends Flowable<T> implements ScalarCallable<T> {
    private final T b;

    public FlowableJust(T t) {
        this.b = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new ScalarSubscription(subscriber, this.b));
    }

    public T call() {
        return this.b;
    }
}
