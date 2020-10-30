package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends U> b;

    static final class TakeUntilMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4945480365982832967L;
        final Subscriber<? super T> a;
        final AtomicLong b = new AtomicLong();
        final AtomicReference<Subscription> c = new AtomicReference<>();
        final AtomicThrowable d = new AtomicThrowable();
        final OtherSubscriber e = new OtherSubscriber<>();

        final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            OtherSubscriber() {
            }

            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            public void onNext(Object obj) {
                SubscriptionHelper.cancel(this);
                onComplete();
            }

            public void onError(Throwable th) {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.c);
                HalfSerializer.onError(TakeUntilMainSubscriber.this.a, th, (AtomicInteger) TakeUntilMainSubscriber.this, TakeUntilMainSubscriber.this.d);
            }

            public void onComplete() {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.c);
                HalfSerializer.onComplete(TakeUntilMainSubscriber.this.a, (AtomicInteger) TakeUntilMainSubscriber.this, TakeUntilMainSubscriber.this.d);
            }
        }

        TakeUntilMainSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.c, this.b, subscription);
        }

        public void onNext(T t) {
            HalfSerializer.onNext(this.a, t, (AtomicInteger) this, this.d);
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.e);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.d);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.e);
            HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.d);
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.c, this.b, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.c);
            SubscriptionHelper.cancel(this.e);
        }
    }

    public FlowableTakeUntil(Flowable<T> flowable, Publisher<? extends U> publisher) {
        super(flowable);
        this.b = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        TakeUntilMainSubscriber takeUntilMainSubscriber = new TakeUntilMainSubscriber(subscriber);
        subscriber.onSubscribe(takeUntilMainSubscriber);
        this.b.subscribe(takeUntilMainSubscriber.e);
        this.source.subscribe((FlowableSubscriber<? super T>) takeUntilMainSubscriber);
    }
}
