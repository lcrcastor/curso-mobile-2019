package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableAnySingle<T> extends Single<Boolean> implements FuseToFlowable<Boolean> {
    final Flowable<T> a;
    final Predicate<? super T> b;

    static final class AnySubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super Boolean> a;
        final Predicate<? super T> b;
        Subscription c;
        boolean d;

        AnySubscriber(SingleObserver<? super Boolean> singleObserver, Predicate<? super T> predicate) {
            this.a = singleObserver;
            this.b = predicate;
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
                try {
                    if (this.b.test(t)) {
                        this.d = true;
                        this.c.cancel();
                        this.c = SubscriptionHelper.CANCELLED;
                        this.a.onSuccess(Boolean.valueOf(true));
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.c.cancel();
                    this.c = SubscriptionHelper.CANCELLED;
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
            this.c = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.c = SubscriptionHelper.CANCELLED;
                this.a.onSuccess(Boolean.valueOf(false));
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

    public FlowableAnySingle(Flowable<T> flowable, Predicate<? super T> predicate) {
        this.a = flowable;
        this.b = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new AnySubscriber<Object>(singleObserver, this.b));
    }

    public Flowable<Boolean> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableAny<T>(this.a, this.b));
    }
}
