package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeInterval<T> extends AbstractFlowableWithUpstream<T, Timed<T>> {
    final Scheduler b;
    final TimeUnit c;

    static final class TimeIntervalSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super Timed<T>> a;
        final TimeUnit b;
        final Scheduler c;
        Subscription d;
        long e;

        TimeIntervalSubscriber(Subscriber<? super Timed<T>> subscriber, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = subscriber;
            this.c = scheduler;
            this.b = timeUnit;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.e = this.c.now(this.b);
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long now = this.c.now(this.b);
            long j = this.e;
            this.e = now;
            this.a.onNext(new Timed(t, now - j, this.b));
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }

        public void request(long j) {
            this.d.request(j);
        }

        public void cancel() {
            this.d.cancel();
        }
    }

    public FlowableTimeInterval(Flowable<T> flowable, TimeUnit timeUnit, Scheduler scheduler) {
        super(flowable);
        this.b = scheduler;
        this.c = timeUnit;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Timed<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new TimeIntervalSubscriber<Object>(subscriber, this.c, this.b));
    }
}
