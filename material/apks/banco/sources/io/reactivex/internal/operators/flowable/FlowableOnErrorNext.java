package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnErrorNext<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> b;
    final boolean c;

    static final class OnErrorNextSubscriber<T> implements FlowableSubscriber<T> {
        final Subscriber<? super T> a;
        final Function<? super Throwable, ? extends Publisher<? extends T>> b;
        final boolean c;
        final SubscriptionArbiter d = new SubscriptionArbiter();
        boolean e;
        boolean f;

        OnErrorNextSubscriber(Subscriber<? super T> subscriber, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
            this.a = subscriber;
            this.b = function;
            this.c = z;
        }

        public void onSubscribe(Subscription subscription) {
            this.d.setSubscription(subscription);
        }

        public void onNext(T t) {
            if (!this.f) {
                this.a.onNext(t);
                if (!this.e) {
                    this.d.produced(1);
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.e) {
                this.e = true;
                if (!this.c || (th instanceof Exception)) {
                    try {
                        Publisher publisher = (Publisher) this.b.apply(th);
                        if (publisher == null) {
                            NullPointerException nullPointerException = new NullPointerException("Publisher is null");
                            nullPointerException.initCause(th);
                            this.a.onError(nullPointerException);
                            return;
                        }
                        publisher.subscribe(this);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.a.onError(new CompositeException(th, th2));
                    }
                } else {
                    this.a.onError(th);
                }
            } else if (this.f) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                this.e = true;
                this.a.onComplete();
            }
        }
    }

    public FlowableOnErrorNext(Flowable<T> flowable, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
        super(flowable);
        this.b = function;
        this.c = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        OnErrorNextSubscriber onErrorNextSubscriber = new OnErrorNextSubscriber(subscriber, this.b, this.c);
        subscriber.onSubscribe(onErrorNextSubscriber.d);
        this.source.subscribe((FlowableSubscriber<? super T>) onErrorNextSubscriber);
    }
}
