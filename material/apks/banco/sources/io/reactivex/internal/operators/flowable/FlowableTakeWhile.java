package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeWhile<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super T> b;

    static final class TakeWhileSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final Predicate<? super T> b;
        Subscription c;
        boolean d;

        TakeWhileSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            this.a = subscriber;
            this.b = predicate;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                try {
                    if (!this.b.test(t)) {
                        this.d = true;
                        this.c.cancel();
                        this.a.onComplete();
                        return;
                    }
                    this.a.onNext(t);
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
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
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

    public FlowableTakeWhile(Flowable<T> flowable, Predicate<? super T> predicate) {
        super(flowable);
        this.b = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new TakeWhileSubscriber<Object>(subscriber, this.b));
    }
}
