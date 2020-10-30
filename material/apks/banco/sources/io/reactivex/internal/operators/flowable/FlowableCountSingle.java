package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableCountSingle<T> extends Single<Long> implements FuseToFlowable<Long> {
    final Flowable<T> a;

    static final class CountSubscriber implements FlowableSubscriber<Object>, Disposable {
        final SingleObserver<? super Long> a;
        Subscription b;
        long c;

        CountSubscriber(SingleObserver<? super Long> singleObserver) {
            this.a = singleObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            this.c++;
        }

        public void onError(Throwable th) {
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onSuccess(Long.valueOf(this.c));
        }

        public void dispose() {
            this.b.cancel();
            this.b = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.b == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableCountSingle(Flowable<T> flowable) {
        this.a = flowable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new CountSubscriber<Object>(singleObserver));
    }

    public Flowable<Long> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableCount<T>(this.a));
    }
}
