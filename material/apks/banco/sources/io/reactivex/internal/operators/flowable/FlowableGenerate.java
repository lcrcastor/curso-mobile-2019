package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGenerate<T, S> extends Flowable<T> {
    final Callable<S> b;
    final BiFunction<S, Emitter<T>, S> c;
    final Consumer<? super S> d;

    static final class GeneratorSubscription<T, S> extends AtomicLong implements Emitter<T>, Subscription {
        private static final long serialVersionUID = 7565982551505011832L;
        final Subscriber<? super T> a;
        final BiFunction<S, ? super Emitter<T>, S> b;
        final Consumer<? super S> c;
        S d;
        volatile boolean e;
        boolean f;
        boolean g;

        GeneratorSubscription(Subscriber<? super T> subscriber, BiFunction<S, ? super Emitter<T>, S> biFunction, Consumer<? super S> consumer, S s) {
            this.a = subscriber;
            this.b = biFunction;
            this.c = consumer;
            this.d = s;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                S s = this.d;
                BiFunction<S, ? super Emitter<T>, S> biFunction = this.b;
                long j2 = j;
                do {
                    long j3 = 0;
                    while (true) {
                        if (j3 == j2) {
                            j2 = get();
                            if (j3 == j2) {
                                this.d = s;
                                j2 = addAndGet(-j3);
                            }
                        } else if (this.e) {
                            this.d = null;
                            a(s);
                            return;
                        } else {
                            this.g = false;
                            try {
                                S apply = biFunction.apply(s, this);
                                if (this.f) {
                                    this.e = true;
                                    this.d = null;
                                    a(apply);
                                    return;
                                }
                                s = apply;
                                j3++;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.e = true;
                                this.d = null;
                                onError(th);
                                a(s);
                                return;
                            }
                        }
                    }
                } while (j2 != 0);
            }
        }

        private void a(S s) {
            try {
                this.c.accept(s);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        public void cancel() {
            if (!this.e) {
                this.e = true;
                if (BackpressureHelper.add(this, 1) == 0) {
                    S s = this.d;
                    this.d = null;
                    a(s);
                }
            }
        }

        public void onNext(T t) {
            if (this.f) {
                return;
            }
            if (this.g) {
                onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
                this.g = true;
                this.a.onNext(t);
            }
        }

        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.f = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                this.a.onComplete();
            }
        }
    }

    public FlowableGenerate(Callable<S> callable, BiFunction<S, Emitter<T>, S> biFunction, Consumer<? super S> consumer) {
        this.b = callable;
        this.c = biFunction;
        this.d = consumer;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            subscriber.onSubscribe(new GeneratorSubscription(subscriber, this.c, this.d, this.b.call()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
