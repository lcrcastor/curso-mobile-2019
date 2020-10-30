package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableUsing<T, D> extends Flowable<T> {
    final Callable<? extends D> b;
    final Function<? super D, ? extends Publisher<? extends T>> c;
    final Consumer<? super D> d;
    final boolean e;

    static final class UsingSubscriber<T, D> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 5904473792286235046L;
        final Subscriber<? super T> a;
        final D b;
        final Consumer<? super D> c;
        final boolean d;
        Subscription e;

        UsingSubscriber(Subscriber<? super T> subscriber, D d2, Consumer<? super D> consumer, boolean z) {
            this.a = subscriber;
            this.b = d2;
            this.c = consumer;
            this.d = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            if (this.d) {
                Throwable th2 = null;
                if (compareAndSet(false, true)) {
                    try {
                        this.c.accept(this.b);
                    } catch (Throwable th3) {
                        th2 = th3;
                        Exceptions.throwIfFatal(th2);
                    }
                }
                this.e.cancel();
                if (th2 != null) {
                    this.a.onError(new CompositeException(th, th2));
                    return;
                }
                this.a.onError(th);
                return;
            }
            this.a.onError(th);
            this.e.cancel();
            a();
        }

        public void onComplete() {
            if (this.d) {
                if (compareAndSet(false, true)) {
                    try {
                        this.c.accept(this.b);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.a.onError(th);
                        return;
                    }
                }
                this.e.cancel();
                this.a.onComplete();
            } else {
                this.a.onComplete();
                this.e.cancel();
                a();
            }
        }

        public void request(long j) {
            this.e.request(j);
        }

        public void cancel() {
            a();
            this.e.cancel();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(false, true)) {
                try {
                    this.c.accept(this.b);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public FlowableUsing(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, Consumer<? super D> consumer, boolean z) {
        this.b = callable;
        this.c = function;
        this.d = consumer;
        this.e = z;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            Object call = this.b.call();
            try {
                ((Publisher) this.c.apply(call)).subscribe(new UsingSubscriber(subscriber, call, this.d, this.e));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(new CompositeException(th, th), subscriber);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptySubscription.error(th2, subscriber);
        }
    }
}
