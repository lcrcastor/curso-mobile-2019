package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDematerialize<T> extends AbstractFlowableWithUpstream<Notification<T>, T> {

    static final class DematerializeSubscriber<T> implements FlowableSubscriber<Notification<T>>, Subscription {
        final Subscriber<? super T> a;
        boolean b;
        Subscription c;

        DematerializeSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.b) {
                if (notification.isOnError()) {
                    RxJavaPlugins.onError(notification.getError());
                }
                return;
            }
            if (notification.isOnError()) {
                this.c.cancel();
                onError(notification.getError());
            } else if (notification.isOnComplete()) {
                this.c.cancel();
                onComplete();
            } else {
                this.a.onNext(notification.getValue());
            }
        }

        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.onComplete();
            }
        }

        public void request(long j) {
            this.c.request(j);
        }

        public void cancel() {
            this.c.cancel();
        }
    }

    public FlowableDematerialize(Flowable<Notification<T>> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new DematerializeSubscriber<Object>(subscriber));
    }
}
