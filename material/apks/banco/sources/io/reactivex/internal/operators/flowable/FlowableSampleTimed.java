package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSampleTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final boolean e;

    static final class SampleTimedEmitLast<T> extends SampleTimedSubscriber<T> {
        private static final long serialVersionUID = -7139995637533111443L;
        final AtomicInteger a = new AtomicInteger(1);

        SampleTimedEmitLast(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(subscriber, j, timeUnit, scheduler);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            c();
            if (this.a.decrementAndGet() == 0) {
                this.b.onComplete();
            }
        }

        public void run() {
            if (this.a.incrementAndGet() == 2) {
                c();
                if (this.a.decrementAndGet() == 0) {
                    this.b.onComplete();
                }
            }
        }
    }

    static final class SampleTimedNoLast<T> extends SampleTimedSubscriber<T> {
        private static final long serialVersionUID = -7139995637533111443L;

        SampleTimedNoLast(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(subscriber, j, timeUnit, scheduler);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.onComplete();
        }

        public void run() {
            c();
        }
    }

    static abstract class SampleTimedSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final AtomicLong f = new AtomicLong();
        final SequentialDisposable g = new SequentialDisposable();
        Subscription h;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        SampleTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.b = subscriber;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.b.onSubscribe(this);
                this.g.replace(this.e.schedulePeriodicallyDirect(this, this.c, this.c, this.d));
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable th) {
            b();
            this.b.onError(th);
        }

        public void onComplete() {
            b();
            a();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            DisposableHelper.dispose(this.g);
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.f, j);
            }
        }

        public void cancel() {
            b();
            this.h.cancel();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Object andSet = getAndSet(null);
            if (andSet == null) {
                return;
            }
            if (this.f.get() != 0) {
                this.b.onNext(andSet);
                BackpressureHelper.produced(this.f, 1);
                return;
            }
            cancel();
            this.b.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
    }

    public FlowableSampleTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.e) {
            Flowable flowable = this.source;
            SampleTimedEmitLast sampleTimedEmitLast = new SampleTimedEmitLast(serializedSubscriber, this.b, this.c, this.d);
            flowable.subscribe((FlowableSubscriber<? super T>) sampleTimedEmitLast);
            return;
        }
        Flowable flowable2 = this.source;
        SampleTimedNoLast sampleTimedNoLast = new SampleTimedNoLast(serializedSubscriber, this.b, this.c, this.d);
        flowable2.subscribe((FlowableSubscriber<? super T>) sampleTimedNoLast);
    }
}
