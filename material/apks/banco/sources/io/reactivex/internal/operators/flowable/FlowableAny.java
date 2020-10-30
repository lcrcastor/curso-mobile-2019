package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAny<T> extends AbstractFlowableWithUpstream<T, Boolean> {
    final Predicate<? super T> b;

    static final class AnySubscriber<T> extends DeferredScalarSubscription<Boolean> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -2311252482644620661L;
        final Predicate<? super T> a;
        Subscription b;
        boolean c;

        AnySubscriber(Subscriber<? super Boolean> subscriber, Predicate<? super T> predicate) {
            super(subscriber);
            this.a = predicate;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.c) {
                try {
                    if (this.a.test(t)) {
                        this.c = true;
                        this.b.cancel();
                        complete(Boolean.valueOf(true));
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.b.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                complete(Boolean.valueOf(false));
            }
        }

        public void cancel() {
            super.cancel();
            this.b.cancel();
        }
    }

    public FlowableAny(Flowable<T> flowable, Predicate<? super T> predicate) {
        super(flowable);
        this.b = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Boolean> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new AnySubscriber<Object>(subscriber, this.b));
    }
}
