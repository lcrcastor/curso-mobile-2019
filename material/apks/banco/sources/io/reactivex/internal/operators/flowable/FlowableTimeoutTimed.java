package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscribers.FullArbiterSubscriber;
import io.reactivex.internal.subscriptions.FullArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeoutTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    static final Disposable f = new EmptyDispose();
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final Publisher<? extends T> e;

    static final class EmptyDispose implements Disposable {
        public void dispose() {
        }

        public boolean isDisposed() {
            return true;
        }

        EmptyDispose() {
        }
    }

    static final class TimeoutTimedOtherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        final Publisher<? extends T> e;
        Subscription f;
        final FullArbiter<T> g;
        final AtomicReference<Disposable> h = new AtomicReference<>();
        volatile long i;
        volatile boolean j;

        final class TimeoutTask implements Runnable {
            private final long b;

            TimeoutTask(long j) {
                this.b = j;
            }

            public void run() {
                if (this.b == TimeoutTimedOtherSubscriber.this.i) {
                    TimeoutTimedOtherSubscriber.this.j = true;
                    TimeoutTimedOtherSubscriber.this.f.cancel();
                    DisposableHelper.dispose(TimeoutTimedOtherSubscriber.this.h);
                    TimeoutTimedOtherSubscriber.this.a();
                    TimeoutTimedOtherSubscriber.this.d.dispose();
                }
            }
        }

        TimeoutTimedOtherSubscriber(Subscriber<? super T> subscriber, long j2, TimeUnit timeUnit, Worker worker, Publisher<? extends T> publisher) {
            this.a = subscriber;
            this.b = j2;
            this.c = timeUnit;
            this.d = worker;
            this.e = publisher;
            this.g = new FullArbiter<>(subscriber, this, 8);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (this.g.setSubscription(subscription)) {
                    this.a.onSubscribe(this.g);
                    a(0);
                }
            }
        }

        public void onNext(T t) {
            if (!this.j) {
                long j2 = this.i + 1;
                this.i = j2;
                if (this.g.onNext(t, this.f)) {
                    a(j2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j2) {
            Disposable disposable = (Disposable) this.h.get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (this.h.compareAndSet(disposable, FlowableTimeoutTimed.f)) {
                DisposableHelper.replace(this.h, this.d.schedule(new TimeoutTask(j2), this.b, this.c));
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.e.subscribe(new FullArbiterSubscriber(this.g));
        }

        public void onError(Throwable th) {
            if (this.j) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.j = true;
            this.g.onError(th, this.f);
            this.d.dispose();
        }

        public void onComplete() {
            if (!this.j) {
                this.j = true;
                this.g.onComplete(this.f);
                this.d.dispose();
            }
        }

        public void dispose() {
            this.f.cancel();
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }

    static final class TimeoutTimedSubscriber<T> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        Subscription e;
        final AtomicReference<Disposable> f = new AtomicReference<>();
        volatile long g;
        volatile boolean h;

        final class TimeoutTask implements Runnable {
            private final long b;

            TimeoutTask(long j) {
                this.b = j;
            }

            public void run() {
                if (this.b == TimeoutTimedSubscriber.this.g) {
                    TimeoutTimedSubscriber.this.h = true;
                    TimeoutTimedSubscriber.this.dispose();
                    TimeoutTimedSubscriber.this.a.onError(new TimeoutException());
                }
            }
        }

        TimeoutTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
                a(0);
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                long j = this.g + 1;
                this.g = j;
                this.a.onNext(t);
                a(j);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            Disposable disposable = (Disposable) this.f.get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (this.f.compareAndSet(disposable, FlowableTimeoutTimed.f)) {
                DisposableHelper.replace(this.f, this.d.schedule(new TimeoutTask(j), this.b, this.c));
            }
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

        public void dispose() {
            this.e.cancel();
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void request(long j) {
            this.e.request(j);
        }

        public void cancel() {
            dispose();
        }
    }

    public FlowableTimeoutTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, Publisher<? extends T> publisher) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.e == null) {
            Flowable flowable = this.source;
            TimeoutTimedSubscriber timeoutTimedSubscriber = new TimeoutTimedSubscriber(new SerializedSubscriber(subscriber), this.b, this.c, this.d.createWorker());
            flowable.subscribe((FlowableSubscriber<? super T>) timeoutTimedSubscriber);
            return;
        }
        Flowable flowable2 = this.source;
        TimeoutTimedOtherSubscriber timeoutTimedOtherSubscriber = new TimeoutTimedOtherSubscriber(subscriber, this.b, this.c, this.d.createWorker(), this.e);
        flowable2.subscribe((FlowableSubscriber<? super T>) timeoutTimedOtherSubscriber);
    }
}
