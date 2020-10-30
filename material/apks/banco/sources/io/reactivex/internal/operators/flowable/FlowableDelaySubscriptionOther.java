package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelaySubscriptionOther<T, U> extends Flowable<T> {
    final Publisher<? extends T> b;
    final Publisher<U> c;

    final class DelaySubscriber implements FlowableSubscriber<U> {
        final SubscriptionArbiter a;
        final Subscriber<? super T> b;
        boolean c;

        final class DelaySubscription implements Subscription {
            private final Subscription b;

            public void request(long j) {
            }

            DelaySubscription(Subscription subscription) {
                this.b = subscription;
            }

            public void cancel() {
                this.b.cancel();
            }
        }

        final class OnCompleteSubscriber implements FlowableSubscriber<T> {
            OnCompleteSubscriber() {
            }

            public void onSubscribe(Subscription subscription) {
                DelaySubscriber.this.a.setSubscription(subscription);
            }

            public void onNext(T t) {
                DelaySubscriber.this.b.onNext(t);
            }

            public void onError(Throwable th) {
                DelaySubscriber.this.b.onError(th);
            }

            public void onComplete() {
                DelaySubscriber.this.b.onComplete();
            }
        }

        DelaySubscriber(SubscriptionArbiter subscriptionArbiter, Subscriber<? super T> subscriber) {
            this.a = subscriptionArbiter;
            this.b = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            this.a.setSubscription(new DelaySubscription(subscription));
            subscription.request(Long.MAX_VALUE);
        }

        public void onNext(U u) {
            onComplete();
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.b.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                FlowableDelaySubscriptionOther.this.b.subscribe(new OnCompleteSubscriber());
            }
        }
    }

    public FlowableDelaySubscriptionOther(Publisher<? extends T> publisher, Publisher<U> publisher2) {
        this.b = publisher;
        this.c = publisher2;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter();
        subscriber.onSubscribe(subscriptionArbiter);
        this.c.subscribe(new DelaySubscriber(subscriptionArbiter, subscriber));
    }
}
