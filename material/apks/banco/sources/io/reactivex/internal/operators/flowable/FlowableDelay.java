package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelay<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final boolean e;

    static final class DelaySubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        final boolean e;
        Subscription f;

        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelaySubscriber.this.a.onComplete();
                } finally {
                    DelaySubscriber.this.d.dispose();
                }
            }
        }

        final class OnError implements Runnable {
            private final Throwable b;

            OnError(Throwable th) {
                this.b = th;
            }

            public void run() {
                try {
                    DelaySubscriber.this.a.onError(this.b);
                } finally {
                    DelaySubscriber.this.d.dispose();
                }
            }
        }

        final class OnNext implements Runnable {
            private final T b;

            OnNext(T t) {
                this.b = t;
            }

            public void run() {
                DelaySubscriber.this.a.onNext(this.b);
            }
        }

        DelaySubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker, boolean z) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.d.schedule(new OnNext(t), this.b, this.c);
        }

        public void onError(Throwable th) {
            this.d.schedule(new OnError(th), this.e ? this.b : 0, this.c);
        }

        public void onComplete() {
            this.d.schedule(new OnComplete(), this.b, this.c);
        }

        public void request(long j) {
            this.f.request(j);
        }

        public void cancel() {
            this.f.cancel();
            this.d.dispose();
        }
    }

    public FlowableDelay(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Subscriber<? super T> subscriber2;
        if (this.e) {
            subscriber2 = subscriber;
        } else {
            subscriber2 = new SerializedSubscriber<>(subscriber);
        }
        Worker createWorker = this.d.createWorker();
        Flowable flowable = this.source;
        DelaySubscriber delaySubscriber = new DelaySubscriber(subscriber2, this.b, this.c, createWorker, this.e);
        flowable.subscribe((FlowableSubscriber<? super T>) delaySubscriber);
    }
}
