package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchIfEmpty<T> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends T> b;

    static final class SwitchIfEmptySubscriber<T> implements FlowableSubscriber<T> {
        final Subscriber<? super T> a;
        final Publisher<? extends T> b;
        final SubscriptionArbiter c = new SubscriptionArbiter();
        boolean d = true;

        SwitchIfEmptySubscriber(Subscriber<? super T> subscriber, Publisher<? extends T> publisher) {
            this.a = subscriber;
            this.b = publisher;
        }

        public void onSubscribe(Subscription subscription) {
            this.c.setSubscription(subscription);
        }

        public void onNext(T t) {
            if (this.d) {
                this.d = false;
            }
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            if (this.d) {
                this.d = false;
                this.b.subscribe(this);
                return;
            }
            this.a.onComplete();
        }
    }

    public FlowableSwitchIfEmpty(Flowable<T> flowable, Publisher<? extends T> publisher) {
        super(flowable);
        this.b = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SwitchIfEmptySubscriber switchIfEmptySubscriber = new SwitchIfEmptySubscriber(subscriber, this.b);
        subscriber.onSubscribe(switchIfEmptySubscriber.c);
        this.source.subscribe((FlowableSubscriber<? super T>) switchIfEmptySubscriber);
    }
}
