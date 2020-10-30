package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler b;
    final boolean c;

    static final class SubscribeOnSubscriber<T> extends AtomicReference<Thread> implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 8094547886072529208L;
        final Subscriber<? super T> a;
        final Worker b;
        final AtomicReference<Subscription> c = new AtomicReference<>();
        final AtomicLong d = new AtomicLong();
        final boolean e;
        Publisher<T> f;

        static final class Request implements Runnable {
            private final Subscription a;
            private final long b;

            Request(Subscription subscription, long j) {
                this.a = subscription;
                this.b = j;
            }

            public void run() {
                this.a.request(this.b);
            }
        }

        SubscribeOnSubscriber(Subscriber<? super T> subscriber, Worker worker, Publisher<T> publisher, boolean z) {
            this.a = subscriber;
            this.b = worker;
            this.f = publisher;
            this.e = z;
        }

        public void run() {
            lazySet(Thread.currentThread());
            Publisher<T> publisher = this.f;
            this.f = null;
            publisher.subscribe(this);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.c, subscription)) {
                long andSet = this.d.getAndSet(0);
                if (andSet != 0) {
                    a(andSet, subscription);
                }
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            this.b.dispose();
        }

        public void onComplete() {
            this.a.onComplete();
            this.b.dispose();
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                Subscription subscription = (Subscription) this.c.get();
                if (subscription != null) {
                    a(j, subscription);
                    return;
                }
                BackpressureHelper.add(this.d, j);
                Subscription subscription2 = (Subscription) this.c.get();
                if (subscription2 != null) {
                    long andSet = this.d.getAndSet(0);
                    if (andSet != 0) {
                        a(andSet, subscription2);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j, Subscription subscription) {
            if (this.e || Thread.currentThread() == get()) {
                subscription.request(j);
            } else {
                this.b.schedule(new Request(subscription, j));
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.c);
            this.b.dispose();
        }
    }

    public FlowableSubscribeOn(Flowable<T> flowable, Scheduler scheduler, boolean z) {
        super(flowable);
        this.b = scheduler;
        this.c = z;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        Worker createWorker = this.b.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(subscriber, createWorker, this.source, this.c);
        subscriber.onSubscribe(subscribeOnSubscriber);
        createWorker.schedule(subscribeOnSubscriber);
    }
}
