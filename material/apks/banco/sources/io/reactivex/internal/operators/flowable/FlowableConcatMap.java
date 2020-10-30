package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final ErrorMode d;

    static abstract class BaseConcatMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, ConcatMapSupport<R>, Subscription {
        private static final long serialVersionUID = -3511336836796789179L;
        final ConcatMapInner<R> a = new ConcatMapInner<>(this);
        final Function<? super T, ? extends Publisher<? extends R>> b;
        final int c;
        final int d;
        Subscription e;
        int f;
        SimpleQueue<T> g;
        volatile boolean h;
        volatile boolean i;
        final AtomicThrowable j = new AtomicThrowable();
        volatile boolean k;
        int l;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void b();

        BaseConcatMapSubscriber(Function<? super T, ? extends Publisher<? extends R>> function, int i2) {
            this.b = function;
            this.c = i2;
            this.d = i2 - (i2 >> 2);
        }

        public final void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.l = requestFusion;
                        this.g = queueSubscription;
                        this.h = true;
                        b();
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.l = requestFusion;
                        this.g = queueSubscription;
                        b();
                        subscription.request((long) this.c);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.c);
                b();
                subscription.request((long) this.c);
            }
        }

        public final void onNext(T t) {
            if (this.l == 2 || this.g.offer(t)) {
                a();
                return;
            }
            this.e.cancel();
            onError(new IllegalStateException("Queue full?!"));
        }

        public final void onComplete() {
            this.h = true;
            a();
        }

        public final void c() {
            this.k = false;
            a();
        }
    }

    static final class ConcatMapDelayed<T, R> extends BaseConcatMapSubscriber<T, R> {
        private static final long serialVersionUID = -2945777694260521066L;
        final Subscriber<? super R> m;
        final boolean n;

        ConcatMapDelayed(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
            super(function, i);
            this.m = subscriber;
            this.n = z;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.m.onSubscribe(this);
        }

        public void onError(Throwable th) {
            if (this.j.addThrowable(th)) {
                this.h = true;
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void a(R r) {
            this.m.onNext(r);
        }

        public void a(Throwable th) {
            if (this.j.addThrowable(th)) {
                if (!this.n) {
                    this.e.cancel();
                    this.h = true;
                }
                this.k = false;
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void request(long j) {
            this.a.request(j);
        }

        public void cancel() {
            if (!this.i) {
                this.i = true;
                this.a.cancel();
                this.e.cancel();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                while (!this.i) {
                    if (!this.k) {
                        boolean z = this.h;
                        if (!z || this.n || ((Throwable) this.j.get()) == null) {
                            try {
                                Object poll = this.g.poll();
                                boolean z2 = poll == null;
                                if (z && z2) {
                                    Throwable terminate = this.j.terminate();
                                    if (terminate != null) {
                                        this.m.onError(terminate);
                                    } else {
                                        this.m.onComplete();
                                    }
                                    return;
                                } else if (!z2) {
                                    try {
                                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(poll), "The mapper returned a null Publisher");
                                        if (this.l != 1) {
                                            int i = this.f + 1;
                                            if (i == this.d) {
                                                this.f = 0;
                                                this.e.request((long) i);
                                            } else {
                                                this.f = i;
                                            }
                                        }
                                        if (publisher instanceof Callable) {
                                            try {
                                                Object call = ((Callable) publisher).call();
                                                if (call == null) {
                                                    continue;
                                                } else if (this.a.isUnbounded()) {
                                                    this.m.onNext(call);
                                                } else {
                                                    this.k = true;
                                                    this.a.setSubscription(new WeakScalarSubscription(call, this.a));
                                                }
                                            } catch (Throwable th) {
                                                Exceptions.throwIfFatal(th);
                                                this.e.cancel();
                                                this.j.addThrowable(th);
                                                this.m.onError(this.j.terminate());
                                                return;
                                            }
                                        } else {
                                            this.k = true;
                                            publisher.subscribe(this.a);
                                        }
                                    } catch (Throwable th2) {
                                        Exceptions.throwIfFatal(th2);
                                        this.e.cancel();
                                        this.j.addThrowable(th2);
                                        this.m.onError(this.j.terminate());
                                        return;
                                    }
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                this.e.cancel();
                                this.j.addThrowable(th3);
                                this.m.onError(this.j.terminate());
                                return;
                            }
                        } else {
                            this.m.onError(this.j.terminate());
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                    }
                }
            }
        }
    }

    static final class ConcatMapImmediate<T, R> extends BaseConcatMapSubscriber<T, R> {
        private static final long serialVersionUID = 7898995095634264146L;
        final Subscriber<? super R> m;
        final AtomicInteger n = new AtomicInteger();

        ConcatMapImmediate(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i) {
            super(function, i);
            this.m = subscriber;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.m.onSubscribe(this);
        }

        public void onError(Throwable th) {
            if (this.j.addThrowable(th)) {
                this.a.cancel();
                if (getAndIncrement() == 0) {
                    this.m.onError(this.j.terminate());
                    return;
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void a(R r) {
            if (get() == 0 && compareAndSet(0, 1)) {
                this.m.onNext(r);
                if (!compareAndSet(1, 0)) {
                    this.m.onError(this.j.terminate());
                }
            }
        }

        public void a(Throwable th) {
            if (this.j.addThrowable(th)) {
                this.e.cancel();
                if (getAndIncrement() == 0) {
                    this.m.onError(this.j.terminate());
                    return;
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void request(long j) {
            this.a.request(j);
        }

        public void cancel() {
            if (!this.i) {
                this.i = true;
                this.a.cancel();
                this.e.cancel();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.n.getAndIncrement() == 0) {
                while (!this.i) {
                    if (!this.k) {
                        boolean z = this.h;
                        try {
                            Object poll = this.g.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                this.m.onComplete();
                                return;
                            } else if (!z2) {
                                try {
                                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(poll), "The mapper returned a null Publisher");
                                    if (this.l != 1) {
                                        int i = this.f + 1;
                                        if (i == this.d) {
                                            this.f = 0;
                                            this.e.request((long) i);
                                        } else {
                                            this.f = i;
                                        }
                                    }
                                    if (publisher instanceof Callable) {
                                        try {
                                            Object call = ((Callable) publisher).call();
                                            if (call == null) {
                                                continue;
                                            } else if (!this.a.isUnbounded()) {
                                                this.k = true;
                                                this.a.setSubscription(new WeakScalarSubscription(call, this.a));
                                            } else if (get() == 0 && compareAndSet(0, 1)) {
                                                this.m.onNext(call);
                                                if (!compareAndSet(1, 0)) {
                                                    this.m.onError(this.j.terminate());
                                                    return;
                                                }
                                            }
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            this.e.cancel();
                                            this.j.addThrowable(th);
                                            this.m.onError(this.j.terminate());
                                            return;
                                        }
                                    } else {
                                        this.k = true;
                                        publisher.subscribe(this.a);
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.e.cancel();
                                    this.j.addThrowable(th2);
                                    this.m.onError(this.j.terminate());
                                    return;
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            this.e.cancel();
                            this.j.addThrowable(th3);
                            this.m.onError(this.j.terminate());
                            return;
                        }
                    }
                    if (this.n.decrementAndGet() == 0) {
                    }
                }
            }
        }
    }

    static final class ConcatMapInner<R> extends SubscriptionArbiter implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 897683679971470653L;
        final ConcatMapSupport<R> a;
        long b;

        ConcatMapInner(ConcatMapSupport<R> concatMapSupport) {
            this.a = concatMapSupport;
        }

        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        public void onNext(R r) {
            this.b++;
            this.a.a(r);
        }

        public void onError(Throwable th) {
            long j = this.b;
            if (j != 0) {
                this.b = 0;
                produced(j);
            }
            this.a.a(th);
        }

        public void onComplete() {
            long j = this.b;
            if (j != 0) {
                this.b = 0;
                produced(j);
            }
            this.a.c();
        }
    }

    interface ConcatMapSupport<T> {
        void a(T t);

        void a(Throwable th);

        void c();
    }

    static final class WeakScalarSubscription<T> implements Subscription {
        final Subscriber<? super T> a;
        final T b;
        boolean c;

        public void cancel() {
        }

        WeakScalarSubscription(T t, Subscriber<? super T> subscriber) {
            this.b = t;
            this.a = subscriber;
        }

        public void request(long j) {
            if (j > 0 && !this.c) {
                this.c = true;
                Subscriber<? super T> subscriber = this.a;
                subscriber.onNext(this.b);
                subscriber.onComplete();
            }
        }
    }

    public FlowableConcatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = errorMode;
    }

    public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        switch (errorMode) {
            case BOUNDARY:
                return new ConcatMapDelayed(subscriber, function, i, false);
            case END:
                return new ConcatMapDelayed(subscriber, function, i, true);
            default:
                return new ConcatMapImmediate(subscriber, function, i);
        }
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe(subscribe(subscriber, this.b, this.c, this.d));
        }
    }
}
