package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatUntil<T> extends AbstractFlowableWithUpstream<T, T> {
    final BooleanSupplier b;

    static final class RepeatSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Subscriber<? super T> a;
        final SubscriptionArbiter b;
        final Publisher<? extends T> c;
        final BooleanSupplier d;

        RepeatSubscriber(Subscriber<? super T> subscriber, BooleanSupplier booleanSupplier, SubscriptionArbiter subscriptionArbiter, Publisher<? extends T> publisher) {
            this.a = subscriber;
            this.b = subscriptionArbiter;
            this.c = publisher;
            this.d = booleanSupplier;
        }

        public void onSubscribe(Subscription subscription) {
            this.b.setSubscription(subscription);
        }

        public void onNext(T t) {
            this.a.onNext(t);
            this.b.produced(1);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            try {
                if (this.d.getAsBoolean()) {
                    this.a.onComplete();
                } else {
                    a();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                int i = 1;
                do {
                    this.c.subscribe(this);
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }
    }

    public FlowableRepeatUntil(Flowable<T> flowable, BooleanSupplier booleanSupplier) {
        super(flowable);
        this.b = booleanSupplier;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter();
        subscriber.onSubscribe(subscriptionArbiter);
        new RepeatSubscriber(subscriber, this.b, subscriptionArbiter, this.source).a();
    }
}
