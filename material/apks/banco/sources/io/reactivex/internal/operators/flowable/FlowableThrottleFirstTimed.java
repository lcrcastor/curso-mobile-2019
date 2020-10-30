package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableThrottleFirstTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;

    static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -9102637559663639004L;
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        Subscription e;
        final SequentialDisposable f = new SequentialDisposable();
        volatile boolean g;
        boolean h;

        DebounceTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.h && !this.g) {
                this.g = true;
                if (get() != 0) {
                    this.a.onNext(t);
                    BackpressureHelper.produced(this, 1);
                    Disposable disposable = (Disposable) this.f.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.f.replace(this.d.schedule(this, this.b, this.c));
                } else {
                    this.h = true;
                    cancel();
                    this.a.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
                }
            }
        }

        public void run() {
            this.g = false;
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            this.a.onError(th);
            this.d.dispose();
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                this.a.onComplete();
                this.d.dispose();
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void cancel() {
            this.e.cancel();
            this.d.dispose();
        }
    }

    public FlowableThrottleFirstTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        DebounceTimedSubscriber debounceTimedSubscriber = new DebounceTimedSubscriber(new SerializedSubscriber(subscriber), this.b, this.c, this.d.createWorker());
        flowable.subscribe((FlowableSubscriber<? super T>) debounceTimedSubscriber);
    }
}
