package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCollect<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<? extends U> b;
    final BiConsumer<? super U, ? super T> c;

    static final class CollectSubscriber<T, U> extends DeferredScalarSubscription<U> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -3589550218733891694L;
        final BiConsumer<? super U, ? super T> a;
        final U b;
        Subscription c;
        boolean d;

        CollectSubscriber(Subscriber<? super U> subscriber, U u, BiConsumer<? super U, ? super T> biConsumer) {
            super(subscriber);
            this.a = biConsumer;
            this.b = u;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                try {
                    this.a.accept(this.b, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.c.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                complete(this.b);
            }
        }

        public void cancel() {
            super.cancel();
            this.c.cancel();
        }
    }

    public FlowableCollect(Flowable<T> flowable, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        super(flowable);
        this.b = callable;
        this.c = biConsumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber<? super T>) new CollectSubscriber<Object>(subscriber, ObjectHelper.requireNonNull(this.b.call(), "The initial value supplied is null"), this.c));
        } catch (Throwable th) {
            EmptySubscription.error(th, subscriber);
        }
    }
}
