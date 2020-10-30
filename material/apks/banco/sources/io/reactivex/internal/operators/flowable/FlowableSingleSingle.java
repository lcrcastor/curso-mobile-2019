package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

public final class FlowableSingleSingle<T> extends Single<T> implements FuseToFlowable<T> {
    final Flowable<T> a;
    final T b;

    static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        final T b;
        Subscription c;
        boolean d;
        T e;

        SingleElementSubscriber(SingleObserver<? super T> singleObserver, T t) {
            this.a = singleObserver;
            this.b = t;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                if (this.e != null) {
                    this.d = true;
                    this.c.cancel();
                    this.c = SubscriptionHelper.CANCELLED;
                    this.a.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.e = t;
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.c = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.c = SubscriptionHelper.CANCELLED;
                T t = this.e;
                this.e = null;
                if (t == null) {
                    t = this.b;
                }
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onError(new NoSuchElementException());
                }
            }
        }

        public void dispose() {
            this.c.cancel();
            this.c = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.c == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableSingleSingle(Flowable<T> flowable, T t) {
        this.a = flowable;
        this.b = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new SingleElementSubscriber<Object>(singleObserver, this.b));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableSingle<T>(this.a, this.b));
    }
}
