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

public final class FlowableElementAtMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final Flowable<T> a;
    final long b;

    static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> a;
        final long b;
        Subscription c;
        long d;
        boolean e;

        ElementAtSubscriber(MaybeObserver<? super T> maybeObserver, long j) {
            this.a = maybeObserver;
            this.b = j;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.e) {
                long j = this.d;
                if (j == this.b) {
                    this.e = true;
                    this.c.cancel();
                    this.c = SubscriptionHelper.CANCELLED;
                    this.a.onSuccess(t);
                    return;
                }
                this.d = j + 1;
            }
        }

        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.c = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            this.c = SubscriptionHelper.CANCELLED;
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
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

    public FlowableElementAtMaybe(Flowable<T> flowable, long j) {
        this.a = flowable;
        this.b = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new ElementAtSubscriber<Object>(maybeObserver, this.b));
    }

    public Flowable<T> fuseToFlowable() {
        FlowableElementAt flowableElementAt = new FlowableElementAt(this.a, this.b, null, false);
        return RxJavaPlugins.onAssembly((Flowable<T>) flowableElementAt);
    }
}
