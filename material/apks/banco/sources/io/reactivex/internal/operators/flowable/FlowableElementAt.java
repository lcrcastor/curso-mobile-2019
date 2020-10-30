package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableElementAt<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final T c;
    final boolean d;

    static final class ElementAtSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4066607327284737757L;
        final long a;
        final T b;
        final boolean c;
        Subscription d;
        long e;
        boolean f;

        ElementAtSubscriber(Subscriber<? super T> subscriber, long j, T t, boolean z) {
            super(subscriber);
            this.a = j;
            this.b = t;
            this.c = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.f) {
                long j = this.e;
                if (j == this.a) {
                    this.f = true;
                    this.d.cancel();
                    complete(t);
                    return;
                }
                this.e = j + 1;
            }
        }

        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                T t = this.b;
                if (t != null) {
                    complete(t);
                } else if (this.c) {
                    this.actual.onError(new NoSuchElementException());
                } else {
                    this.actual.onComplete();
                }
            }
        }

        public void cancel() {
            super.cancel();
            this.d.cancel();
        }
    }

    public FlowableElementAt(Flowable<T> flowable, long j, T t, boolean z) {
        super(flowable);
        this.b = j;
        this.c = t;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        ElementAtSubscriber elementAtSubscriber = new ElementAtSubscriber(subscriber, this.b, this.c, this.d);
        flowable.subscribe((FlowableSubscriber<? super T>) elementAtSubscriber);
    }
}
