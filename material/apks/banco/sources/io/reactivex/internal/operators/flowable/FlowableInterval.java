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

public final class FlowableInterval extends Flowable<Long> {
    final Scheduler b;
    final long c;
    final long d;
    final TimeUnit e;

    static final class IntervalSubscriber extends AtomicLong implements Runnable, Subscription {
        private static final long serialVersionUID = -2809475196591179431L;
        final Subscriber<? super Long> a;
        long b;
        final AtomicReference<Disposable> c = new AtomicReference<>();

        IntervalSubscriber(Subscriber<? super Long> subscriber) {
            this.a = subscriber;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void cancel() {
            DisposableHelper.dispose(this.c);
        }

        public void run() {
            if (this.c.get() == DisposableHelper.DISPOSED) {
                return;
            }
            if (get() != 0) {
                Subscriber<? super Long> subscriber = this.a;
                long j = this.b;
                this.b = j + 1;
                subscriber.onNext(Long.valueOf(j));
                BackpressureHelper.produced(this, 1);
                return;
            }
            Subscriber<? super Long> subscriber2 = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Can't deliver value ");
            sb.append(this.b);
            sb.append(" due to lack of requests");
            subscriber2.onError(new MissingBackpressureException(sb.toString()));
            DisposableHelper.dispose(this.c);
        }

        public void a(Disposable disposable) {
            DisposableHelper.setOnce(this.c, disposable);
        }
    }

    public FlowableInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        this.c = j;
        this.d = j2;
        this.e = timeUnit;
        this.b = scheduler;
    }

    public void subscribeActual(Subscriber<? super Long> subscriber) {
        IntervalSubscriber intervalSubscriber = new IntervalSubscriber(subscriber);
        subscriber.onSubscribe(intervalSubscriber);
        intervalSubscriber.a(this.b.schedulePeriodicallyDirect(intervalSubscriber, this.c, this.d, this.e));
    }
}
