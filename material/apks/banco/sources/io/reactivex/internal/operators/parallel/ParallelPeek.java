package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelPeek<T> extends ParallelFlowable<T> {
    final ParallelFlowable<T> a;
    final Consumer<? super T> b;
    final Consumer<? super T> c;
    final Consumer<? super Throwable> d;
    final Action e;
    final Action f;
    final Consumer<? super Subscription> g;
    final LongConsumer h;
    final Action i;

    static final class ParallelPeekSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final ParallelPeek<T> b;
        Subscription c;
        boolean d;

        ParallelPeekSubscriber(Subscriber<? super T> subscriber, ParallelPeek<T> parallelPeek) {
            this.a = subscriber;
            this.b = parallelPeek;
        }

        public void request(long j) {
            try {
                this.b.h.accept(j);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.c.request(j);
        }

        public void cancel() {
            try {
                this.b.i.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.c.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                try {
                    this.b.g.accept(subscription);
                    this.a.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    subscription.cancel();
                    this.a.onSubscribe(EmptySubscription.INSTANCE);
                    onError(th);
                }
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                try {
                    this.b.b.accept(t);
                    this.a.onNext(t);
                    try {
                        this.b.c.accept(t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            try {
                this.b.d.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
            try {
                this.b.f.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                try {
                    this.b.e.run();
                    this.a.onComplete();
                    try {
                        this.b.f.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.a.onError(th2);
                }
            }
        }
    }

    public ParallelPeek(ParallelFlowable<T> parallelFlowable, Consumer<? super T> consumer, Consumer<? super T> consumer2, Consumer<? super Throwable> consumer3, Action action, Action action2, Consumer<? super Subscription> consumer4, LongConsumer longConsumer, Action action3) {
        this.a = parallelFlowable;
        this.b = (Consumer) ObjectHelper.requireNonNull(consumer, "onNext is null");
        this.c = (Consumer) ObjectHelper.requireNonNull(consumer2, "onAfterNext is null");
        this.d = (Consumer) ObjectHelper.requireNonNull(consumer3, "onError is null");
        this.e = (Action) ObjectHelper.requireNonNull(action, "onComplete is null");
        this.f = (Action) ObjectHelper.requireNonNull(action2, "onAfterTerminated is null");
        this.g = (Consumer) ObjectHelper.requireNonNull(consumer4, "onSubscribe is null");
        this.h = (LongConsumer) ObjectHelper.requireNonNull(longConsumer, "onRequest is null");
        this.i = (Action) ObjectHelper.requireNonNull(action3, "onCancel is null");
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i2 = 0; i2 < length; i2++) {
                subscriberArr2[i2] = new ParallelPeekSubscriber(subscriberArr[i2], this);
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.a.parallelism();
    }
}
