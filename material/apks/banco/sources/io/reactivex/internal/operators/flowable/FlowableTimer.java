package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimer extends Flowable<Long> {
    final Scheduler b;
    final long c;
    final TimeUnit d;

    static final class TimerSubscriber extends AtomicReference<Disposable> implements Runnable, Subscription {
        private static final long serialVersionUID = -2809475196591179431L;
        final Subscriber<? super Long> a;
        volatile boolean b;

        TimerSubscriber(Subscriber<? super Long> subscriber) {
            this.a = subscriber;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.b = true;
            }
        }

        public void cancel() {
            DisposableHelper.dispose(this);
        }

        public void run() {
            if (get() == DisposableHelper.DISPOSED) {
                return;
            }
            if (this.b) {
                this.a.onNext(Long.valueOf(0));
                lazySet(EmptyDisposable.INSTANCE);
                this.a.onComplete();
                return;
            }
            lazySet(EmptyDisposable.INSTANCE);
            this.a.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
        }

        public void a(Disposable disposable) {
            DisposableHelper.trySet(this, disposable);
        }
    }

    public FlowableTimer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.c = j;
        this.d = timeUnit;
        this.b = scheduler;
    }

    public void subscribeActual(Subscriber<? super Long> subscriber) {
        TimerSubscriber timerSubscriber = new TimerSubscriber(subscriber);
        subscriber.onSubscribe(timerSubscriber);
        timerSubscriber.a(this.b.scheduleDirect(timerSubscriber, this.c, this.d));
    }
}
