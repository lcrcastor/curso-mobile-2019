package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<U> b;

    static final class SkipUntilMainSubscriber<T> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        private static final long serialVersionUID = -6270983465606289181L;
        final Subscriber<? super T> a;
        final AtomicReference<Subscription> b = new AtomicReference<>();
        final AtomicLong c = new AtomicLong();
        final OtherSubscriber d = new OtherSubscriber<>();
        final AtomicThrowable e = new AtomicThrowable();
        volatile boolean f;

        final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -5592042965931999169L;

            OtherSubscriber() {
            }

            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            public void onNext(Object obj) {
                SkipUntilMainSubscriber.this.f = true;
                ((Subscription) get()).cancel();
            }

            public void onError(Throwable th) {
                SubscriptionHelper.cancel(SkipUntilMainSubscriber.this.b);
                HalfSerializer.onError(SkipUntilMainSubscriber.this.a, th, (AtomicInteger) SkipUntilMainSubscriber.this, SkipUntilMainSubscriber.this.e);
            }

            public void onComplete() {
                SkipUntilMainSubscriber.this.f = true;
            }
        }

        SkipUntilMainSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.b, this.c, subscription);
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                ((Subscription) this.b.get()).request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.f) {
                return false;
            }
            HalfSerializer.onNext(this.a, t, (AtomicInteger) this, this.e);
            return true;
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.d);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.e);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.d);
            HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.e);
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.b, this.c, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.b);
            SubscriptionHelper.cancel(this.d);
        }
    }

    public FlowableSkipUntil(Flowable<T> flowable, Publisher<U> publisher) {
        super(flowable);
        this.b = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SkipUntilMainSubscriber skipUntilMainSubscriber = new SkipUntilMainSubscriber(subscriber);
        subscriber.onSubscribe(skipUntilMainSubscriber);
        this.b.subscribe(skipUntilMainSubscriber.d);
        this.source.subscribe((FlowableSubscriber<? super T>) skipUntilMainSubscriber);
    }
}
