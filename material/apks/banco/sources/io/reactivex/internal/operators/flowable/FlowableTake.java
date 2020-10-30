package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;

    static final class TakeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5636543848937116287L;
        boolean a;
        Subscription b;
        final Subscriber<? super T> c;
        final long d;
        long e;

        TakeSubscriber(Subscriber<? super T> subscriber, long j) {
            this.c = subscriber;
            this.d = j;
            this.e = j;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                if (this.d == 0) {
                    subscription.cancel();
                    this.a = true;
                    EmptySubscription.complete(this.c);
                    return;
                }
                this.c.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.a) {
                long j = this.e;
                this.e = j - 1;
                if (j > 0) {
                    boolean z = this.e == 0;
                    this.c.onNext(t);
                    if (z) {
                        this.b.cancel();
                        onComplete();
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.a) {
                this.a = true;
                this.b.cancel();
                this.c.onError(th);
            }
        }

        public void onComplete() {
            if (!this.a) {
                this.a = true;
                this.c.onComplete();
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                if (get() || !compareAndSet(false, true) || j < this.d) {
                    this.b.request(j);
                } else {
                    this.b.request(Long.MAX_VALUE);
                }
            }
        }

        public void cancel() {
            this.b.cancel();
        }
    }

    public FlowableTake(Flowable<T> flowable, long j) {
        super(flowable);
        this.b = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new TakeSubscriber<Object>(subscriber, this.b));
    }
}
