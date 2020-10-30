package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableIntervalRange extends Flowable<Long> {
    final Scheduler b;
    final long c;
    final long d;
    final long e;
    final long f;
    final TimeUnit g;

    static final class IntervalRangeSubscriber extends AtomicLong implements Runnable, Subscription {
        private static final long serialVersionUID = -2809475196591179431L;
        final Subscriber<? super Long> a;
        final long b;
        long c;
        final AtomicReference<Disposable> d = new AtomicReference<>();

        IntervalRangeSubscriber(Subscriber<? super Long> subscriber, long j, long j2) {
            this.a = subscriber;
            this.c = j;
            this.b = j2;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void cancel() {
            DisposableHelper.dispose(this.d);
        }

        public void run() {
            if (this.d.get() != DisposableHelper.DISPOSED) {
                long j = get();
                if (j != 0) {
                    long j2 = this.c;
                    this.a.onNext(Long.valueOf(j2));
                    if (j2 == this.b) {
                        if (this.d.get() != DisposableHelper.DISPOSED) {
                            this.a.onComplete();
                        }
                        DisposableHelper.dispose(this.d);
                        return;
                    }
                    this.c = j2 + 1;
                    if (j != Long.MAX_VALUE) {
                        decrementAndGet();
                    }
                } else {
                    Subscriber<? super Long> subscriber = this.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Can't deliver value ");
                    sb.append(this.c);
                    sb.append(" due to lack of requests");
                    subscriber.onError(new MissingBackpressureException(sb.toString()));
                    DisposableHelper.dispose(this.d);
                }
            }
        }

        public void a(Disposable disposable) {
            DisposableHelper.setOnce(this.d, disposable);
        }
    }

    public FlowableIntervalRange(long j, long j2, long j3, long j4, TimeUnit timeUnit, Scheduler scheduler) {
        this.e = j3;
        this.f = j4;
        this.g = timeUnit;
        this.b = scheduler;
        this.c = j;
        this.d = j2;
    }

    public void subscribeActual(Subscriber<? super Long> subscriber) {
        IntervalRangeSubscriber intervalRangeSubscriber = new IntervalRangeSubscriber(subscriber, this.c, this.d);
        subscriber.onSubscribe(intervalRangeSubscriber);
        intervalRangeSubscriber.a(this.b.schedulePeriodicallyDirect(intervalRangeSubscriber, this.e, this.f, this.g));
    }
}
