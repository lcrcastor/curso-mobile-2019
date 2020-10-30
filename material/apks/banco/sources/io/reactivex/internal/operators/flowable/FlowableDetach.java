package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EmptyComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDetach<T> extends AbstractFlowableWithUpstream<T, T> {

    static final class DetachSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        Subscriber<? super T> a;
        Subscription b;

        DetachSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void request(long j) {
            this.b.request(j);
        }

        public void cancel() {
            Subscription subscription = this.b;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscription.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            Subscriber<? super T> subscriber = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscriber.onError(th);
        }

        public void onComplete() {
            Subscriber<? super T> subscriber = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscriber.onComplete();
        }
    }

    public FlowableDetach(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new DetachSubscriber<Object>(subscriber));
    }
}
