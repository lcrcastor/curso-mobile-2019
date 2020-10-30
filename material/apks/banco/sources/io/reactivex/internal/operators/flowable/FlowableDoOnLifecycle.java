package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDoOnLifecycle<T> extends AbstractFlowableWithUpstream<T, T> {
    private final Consumer<? super Subscription> b;
    private final LongConsumer c;
    private final Action d;

    static final class SubscriptionLambdaSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final Consumer<? super Subscription> b;
        final LongConsumer c;
        final Action d;
        Subscription e;

        SubscriptionLambdaSubscriber(Subscriber<? super T> subscriber, Consumer<? super Subscription> consumer, LongConsumer longConsumer, Action action) {
            this.a = subscriber;
            this.b = consumer;
            this.d = action;
            this.c = longConsumer;
        }

        public void onSubscribe(Subscription subscription) {
            try {
                this.b.accept(subscription);
                if (SubscriptionHelper.validate(this.e, subscription)) {
                    this.e = subscription;
                    this.a.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                subscription.cancel();
                this.e = SubscriptionHelper.CANCELLED;
                EmptySubscription.error(th, this.a);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            if (this.e != SubscriptionHelper.CANCELLED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (this.e != SubscriptionHelper.CANCELLED) {
                this.a.onComplete();
            }
        }

        public void request(long j) {
            try {
                this.c.accept(j);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.e.request(j);
        }

        public void cancel() {
            try {
                this.d.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.e.cancel();
        }
    }

    public FlowableDoOnLifecycle(Flowable<T> flowable, Consumer<? super Subscription> consumer, LongConsumer longConsumer, Action action) {
        super(flowable);
        this.b = consumer;
        this.c = longConsumer;
        this.d = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new SubscriptionLambdaSubscriber<Object>(subscriber, this.b, this.c, this.d));
    }
}
