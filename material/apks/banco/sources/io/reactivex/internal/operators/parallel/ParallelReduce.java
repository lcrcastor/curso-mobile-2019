package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduce<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<? extends T> a;
    final Callable<R> b;
    final BiFunction<R, ? super T, R> c;

    static final class ParallelReduceSubscriber<T, R> extends DeferredScalarSubscriber<T, R> {
        private static final long serialVersionUID = 8200530050639449080L;
        final BiFunction<R, ? super T, R> a;
        R b;
        boolean c;

        ParallelReduceSubscriber(Subscriber<? super R> subscriber, R r, BiFunction<R, ? super T, R> biFunction) {
            super(subscriber);
            this.b = r;
            this.a = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.c) {
                try {
                    this.b = ObjectHelper.requireNonNull(this.a.apply(this.b, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.b = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                R r = this.b;
                this.b = null;
                complete(r);
            }
        }

        public void cancel() {
            super.cancel();
            this.s.cancel();
        }
    }

    public ParallelReduce(ParallelFlowable<? extends T> parallelFlowable, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        this.a = parallelFlowable;
        this.b = callable;
        this.c = biFunction;
    }

    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            int i = 0;
            while (i < length) {
                try {
                    subscriberArr2[i] = new ParallelReduceSubscriber(subscriberArr[i], ObjectHelper.requireNonNull(this.b.call(), "The initialSupplier returned a null value"), this.c);
                    i++;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    a(subscriberArr, th);
                    return;
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Subscriber<?>[] subscriberArr, Throwable th) {
        for (Subscriber<?> error : subscriberArr) {
            EmptySubscription.error(th, error);
        }
    }

    public int parallelism() {
        return this.a.parallelism();
    }
}
