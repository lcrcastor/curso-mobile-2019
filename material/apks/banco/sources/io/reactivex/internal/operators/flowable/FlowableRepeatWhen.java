package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatWhen<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Object>, ? extends Publisher<?>> b;

    static final class RepeatWhenSubscriber<T> extends WhenSourceSubscriber<T, Object> {
        private static final long serialVersionUID = -2680129890138081029L;

        RepeatWhenSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<Object> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        public void onError(Throwable th) {
            this.c.cancel();
            this.a.onError(th);
        }

        public void onComplete() {
            a(Integer.valueOf(0));
        }
    }

    static final class WhenReceiver<T, U> extends AtomicInteger implements FlowableSubscriber<Object>, Subscription {
        private static final long serialVersionUID = 2827772011130406689L;
        final Publisher<T> a;
        final AtomicReference<Subscription> b = new AtomicReference<>();
        final AtomicLong c = new AtomicLong();
        WhenSourceSubscriber<T, U> d;

        WhenReceiver(Publisher<T> publisher) {
            this.a = publisher;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.b, this.c, subscription);
        }

        public void onNext(Object obj) {
            if (getAndIncrement() == 0) {
                while (!SubscriptionHelper.isCancelled((Subscription) this.b.get())) {
                    this.a.subscribe(this.d);
                    if (decrementAndGet() == 0) {
                    }
                }
            }
        }

        public void onError(Throwable th) {
            this.d.cancel();
            this.d.a.onError(th);
        }

        public void onComplete() {
            this.d.cancel();
            this.d.a.onComplete();
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.b, this.c, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.b);
        }
    }

    static abstract class WhenSourceSubscriber<T, U> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -5604623027276966720L;
        protected final Subscriber<? super T> a;
        protected final FlowableProcessor<U> b;
        protected final Subscription c;
        private long d;

        WhenSourceSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<U> flowableProcessor, Subscription subscription) {
            this.a = subscriber;
            this.b = flowableProcessor;
            this.c = subscription;
        }

        public final void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        public final void onNext(T t) {
            this.d++;
            this.a.onNext(t);
        }

        /* access modifiers changed from: protected */
        public final void a(U u) {
            long j = this.d;
            if (j != 0) {
                this.d = 0;
                produced(j);
            }
            this.c.request(1);
            this.b.onNext(u);
        }

        public final void cancel() {
            super.cancel();
            this.c.cancel();
        }
    }

    public FlowableRepeatWhen(Flowable<T> flowable, Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        super(flowable);
        this.b = function;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        FlowableProcessor serialized = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(serialized), "handler returned a null Publisher");
            WhenReceiver whenReceiver = new WhenReceiver(this.source);
            RepeatWhenSubscriber repeatWhenSubscriber = new RepeatWhenSubscriber(serializedSubscriber, serialized, whenReceiver);
            whenReceiver.d = repeatWhenSubscriber;
            subscriber.onSubscribe(repeatWhenSubscriber);
            publisher.subscribe(whenReceiver);
            whenReceiver.onNext(Integer.valueOf(0));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
