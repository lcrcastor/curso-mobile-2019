package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.FullArbiterSubscriber;
import io.reactivex.internal.subscriptions.FullArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeout<T, U, V> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<U> b;
    final Function<? super T, ? extends Publisher<V>> c;
    final Publisher<? extends T> d;

    interface OnTimeout {
        void a(long j);

        void onError(Throwable th);
    }

    static final class TimeoutInnerSubscriber<T, U, V> extends DisposableSubscriber<Object> {
        final OnTimeout a;
        final long b;
        boolean c;

        TimeoutInnerSubscriber(OnTimeout onTimeout, long j) {
            this.a = onTimeout;
            this.b = j;
        }

        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
                cancel();
                this.a.a(this.b);
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a(this.b);
            }
        }
    }

    static final class TimeoutOtherSubscriber<T, U, V> implements FlowableSubscriber<T>, Disposable, OnTimeout {
        final Subscriber<? super T> a;
        final Publisher<U> b;
        final Function<? super T, ? extends Publisher<V>> c;
        final Publisher<? extends T> d;
        final FullArbiter<T> e;
        Subscription f;
        boolean g;
        volatile boolean h;
        volatile long i;
        final AtomicReference<Disposable> j = new AtomicReference<>();

        TimeoutOtherSubscriber(Subscriber<? super T> subscriber, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
            this.a = subscriber;
            this.b = publisher;
            this.c = function;
            this.d = publisher2;
            this.e = new FullArbiter<>(subscriber, this, 8);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (this.e.setSubscription(subscription)) {
                    Subscriber<? super T> subscriber = this.a;
                    Publisher<U> publisher = this.b;
                    if (publisher != null) {
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, 0);
                        if (this.j.compareAndSet(null, timeoutInnerSubscriber)) {
                            subscriber.onSubscribe(this.e);
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } else {
                        subscriber.onSubscribe(this.e);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                long j2 = this.i + 1;
                this.i = j2;
                if (this.e.onNext(t, this.f)) {
                    Disposable disposable = (Disposable) this.j.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.c.apply(t), "The publisher returned is null");
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, j2);
                        if (this.j.compareAndSet(disposable, timeoutInnerSubscriber)) {
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.a.onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            dispose();
            this.e.onError(th, this.f);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                dispose();
                this.e.onComplete(this.f);
            }
        }

        public void dispose() {
            this.h = true;
            this.f.cancel();
            DisposableHelper.dispose(this.j);
        }

        public boolean isDisposed() {
            return this.h;
        }

        public void a(long j2) {
            if (j2 == this.i) {
                dispose();
                this.d.subscribe(new FullArbiterSubscriber(this.e));
            }
        }
    }

    static final class TimeoutSubscriber<T, U, V> implements FlowableSubscriber<T>, OnTimeout, Subscription {
        final Subscriber<? super T> a;
        final Publisher<U> b;
        final Function<? super T, ? extends Publisher<V>> c;
        Subscription d;
        volatile boolean e;
        volatile long f;
        final AtomicReference<Disposable> g = new AtomicReference<>();

        TimeoutSubscriber(Subscriber<? super T> subscriber, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function) {
            this.a = subscriber;
            this.b = publisher;
            this.c = function;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                if (!this.e) {
                    Subscriber<? super T> subscriber = this.a;
                    Publisher<U> publisher = this.b;
                    if (publisher != null) {
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, 0);
                        if (this.g.compareAndSet(null, timeoutInnerSubscriber)) {
                            subscriber.onSubscribe(this);
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } else {
                        subscriber.onSubscribe(this);
                    }
                }
            }
        }

        public void onNext(T t) {
            long j = this.f + 1;
            this.f = j;
            this.a.onNext(t);
            Disposable disposable = (Disposable) this.g.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.c.apply(t), "The publisher returned is null");
                TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, j);
                if (this.g.compareAndSet(disposable, timeoutInnerSubscriber)) {
                    publisher.subscribe(timeoutInnerSubscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.a.onError(th);
            }
        }

        public void onError(Throwable th) {
            cancel();
            this.a.onError(th);
        }

        public void onComplete() {
            cancel();
            this.a.onComplete();
        }

        public void request(long j) {
            this.d.request(j);
        }

        public void cancel() {
            this.e = true;
            this.d.cancel();
            DisposableHelper.dispose(this.g);
        }

        public void a(long j) {
            if (j == this.f) {
                cancel();
                this.a.onError(new TimeoutException());
            }
        }
    }

    public FlowableTimeout(Flowable<T> flowable, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = publisher2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.d == null) {
            this.source.subscribe((FlowableSubscriber<? super T>) new TimeoutSubscriber<Object>(new SerializedSubscriber(subscriber), this.b, this.c));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new TimeoutOtherSubscriber<Object>(subscriber, this.b, this.c, this.d));
        }
    }
}
