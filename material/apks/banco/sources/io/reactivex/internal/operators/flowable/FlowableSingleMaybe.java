package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableSingleMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final Flowable<T> a;

    static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> a;
        Subscription b;
        boolean c;
        T d;

        SingleElementSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.c) {
                if (this.d != null) {
                    this.c = true;
                    this.b.cancel();
                    this.b = SubscriptionHelper.CANCELLED;
                    this.a.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.d = t;
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.b = SubscriptionHelper.CANCELLED;
                T t = this.d;
                this.d = null;
                if (t == null) {
                    this.a.onComplete();
                } else {
                    this.a.onSuccess(t);
                }
            }
        }

        public void dispose() {
            this.b.cancel();
            this.b = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.b == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableSingleMaybe(Flowable<T> flowable) {
        this.a = flowable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new SingleElementSubscriber<Object>(maybeObserver));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableSingle<T>(this.a, null));
    }
}
