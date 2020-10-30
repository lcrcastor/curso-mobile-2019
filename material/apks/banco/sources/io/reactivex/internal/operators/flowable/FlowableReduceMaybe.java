package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceMaybe<T> extends Maybe<T> implements FuseToFlowable<T>, HasUpstreamPublisher<T> {
    final Flowable<T> a;
    final BiFunction<T, T, T> b;

    static final class ReduceSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> a;
        final BiFunction<T, T, T> b;
        T c;
        Subscription d;
        boolean e;

        ReduceSubscriber(MaybeObserver<? super T> maybeObserver, BiFunction<T, T, T> biFunction) {
            this.a = maybeObserver;
            this.b = biFunction;
        }

        public void dispose() {
            this.d.cancel();
            this.e = true;
        }

        public boolean isDisposed() {
            return this.e;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.e) {
                T t2 = this.c;
                if (t2 == null) {
                    this.c = t;
                } else {
                    try {
                        this.c = ObjectHelper.requireNonNull(this.b.apply(t2, t), "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.d.cancel();
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
                T t = this.c;
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onComplete();
                }
            }
        }
    }

    public FlowableReduceMaybe(Flowable<T> flowable, BiFunction<T, T, T> biFunction) {
        this.a = flowable;
        this.b = biFunction;
    }

    public Publisher<T> source() {
        return this.a;
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableReduce<T>(this.a, this.b));
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new ReduceSubscriber<Object>(maybeObserver, this.b));
    }
}
