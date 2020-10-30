package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScan<T> extends AbstractFlowableWithUpstream<T, T> {
    final BiFunction<T, T, T> b;

    static final class ScanSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final BiFunction<T, T, T> b;
        Subscription c;
        T d;
        boolean e;

        ScanSubscriber(Subscriber<? super T> subscriber, BiFunction<T, T, T> biFunction) {
            this.a = subscriber;
            this.b = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.e) {
                Subscriber<? super T> subscriber = this.a;
                T t2 = this.d;
                if (t2 == null) {
                    this.d = t;
                    subscriber.onNext(t);
                } else {
                    try {
                        T requireNonNull = ObjectHelper.requireNonNull(this.b.apply(t2, t), "The value returned by the accumulator is null");
                        this.d = requireNonNull;
                        subscriber.onNext(requireNonNull);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.c.cancel();
                        onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.e) {
                this.e = true;
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

    public FlowableScan(Flowable<T> flowable, BiFunction<T, T, T> biFunction) {
        super(flowable);
        this.b = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new ScanSubscriber<Object>(subscriber, this.b));
    }
}
