package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelMapTry<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, ? extends R> b;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;

    static final class ParallelMapTryConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        final ConditionalSubscriber<? super R> a;
        final Function<? super T, ? extends R> b;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        Subscription d;
        boolean e;

        ParallelMapTryConditionalSubscriber(ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = conditionalSubscriber;
            this.b = function;
            this.c = biFunction;
        }

        public void request(long j) {
            this.d.request(j);
        }

        public void cancel() {
            this.d.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t) && !this.e) {
                this.d.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    return this.a.tryOnNext(ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(new CompositeException(th, th));
                    return false;
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
    }

    static final class ParallelMapTrySubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
        final Subscriber<? super R> a;
        final Function<? super T, ? extends R> b;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        Subscription d;
        boolean e;

        ParallelMapTrySubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = subscriber;
            this.b = function;
            this.c = biFunction;
        }

        public void request(long j) {
            this.d.request(j);
        }

        public void cancel() {
            this.d.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!tryOnNext(t) && !this.e) {
                this.d.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    this.a.onNext(ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null value"));
                    return true;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(new CompositeException(th, th));
                    return false;
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
    }

    public ParallelMapTry(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.a = parallelFlowable;
        this.b = function;
        this.c = biFunction;
    }

    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelMapTryConditionalSubscriber(conditionalSubscriber, this.b, this.c);
                } else {
                    subscriberArr2[i] = new ParallelMapTrySubscriber(conditionalSubscriber, this.b, this.c);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.a.parallelism();
    }
}
