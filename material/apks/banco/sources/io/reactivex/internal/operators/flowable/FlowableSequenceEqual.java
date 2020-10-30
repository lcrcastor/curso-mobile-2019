package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
    final Publisher<? extends T> b;
    final Publisher<? extends T> c;
    final BiPredicate<? super T, ? super T> d;
    final int e;

    static final class EqualCoordinator<T> extends DeferredScalarSubscription<Boolean> implements EqualCoordinatorHelper {
        private static final long serialVersionUID = -6178010334400373240L;
        final BiPredicate<? super T, ? super T> a;
        final EqualSubscriber<T> b;
        final EqualSubscriber<T> c;
        final AtomicThrowable d;
        final AtomicInteger e = new AtomicInteger();
        T f;
        T g;

        EqualCoordinator(Subscriber<? super Boolean> subscriber, int i, BiPredicate<? super T, ? super T> biPredicate) {
            super(subscriber);
            this.a = biPredicate;
            this.b = new EqualSubscriber<>(this, i);
            this.c = new EqualSubscriber<>(this, i);
            this.d = new AtomicThrowable();
        }

        /* access modifiers changed from: 0000 */
        public void a(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
            publisher.subscribe(this.b);
            publisher2.subscribe(this.c);
        }

        public void cancel() {
            super.cancel();
            this.b.b();
            this.c.b();
            if (this.e.getAndIncrement() == 0) {
                this.b.c();
                this.c.c();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.b();
            this.b.c();
            this.c.b();
            this.c.c();
        }

        public void b() {
            if (this.e.getAndIncrement() == 0) {
                int i = 1;
                do {
                    SimpleQueue<T> simpleQueue = this.b.e;
                    SimpleQueue<T> simpleQueue2 = this.c.e;
                    if (simpleQueue != null && simpleQueue2 != null) {
                        while (!isCancelled()) {
                            if (((Throwable) this.d.get()) != null) {
                                a();
                                this.actual.onError(this.d.terminate());
                                return;
                            }
                            boolean z = this.b.f;
                            T t = this.f;
                            if (t == null) {
                                try {
                                    t = simpleQueue.poll();
                                    this.f = t;
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    a();
                                    this.d.addThrowable(th);
                                    this.actual.onError(this.d.terminate());
                                    return;
                                }
                            }
                            boolean z2 = t == null;
                            boolean z3 = this.c.f;
                            T t2 = this.g;
                            if (t2 == null) {
                                try {
                                    t2 = simpleQueue2.poll();
                                    this.g = t2;
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a();
                                    this.d.addThrowable(th2);
                                    this.actual.onError(this.d.terminate());
                                    return;
                                }
                            }
                            boolean z4 = t2 == null;
                            if (z && z3 && z2 && z4) {
                                complete(Boolean.valueOf(true));
                                return;
                            } else if (z && z3 && z2 != z4) {
                                a();
                                complete(Boolean.valueOf(false));
                                return;
                            } else if (!z2 && !z4) {
                                try {
                                    if (!this.a.test(t, t2)) {
                                        a();
                                        complete(Boolean.valueOf(false));
                                        return;
                                    }
                                    this.f = null;
                                    this.g = null;
                                    this.b.a();
                                    this.c.a();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    a();
                                    this.d.addThrowable(th3);
                                    this.actual.onError(this.d.terminate());
                                    return;
                                }
                            }
                        }
                        this.b.c();
                        this.c.c();
                        return;
                    } else if (isCancelled()) {
                        this.b.c();
                        this.c.c();
                        return;
                    } else if (((Throwable) this.d.get()) != null) {
                        a();
                        this.actual.onError(this.d.terminate());
                        return;
                    }
                    i = this.e.addAndGet(-i);
                } while (i != 0);
            }
        }

        public void a(Throwable th) {
            if (this.d.addThrowable(th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    interface EqualCoordinatorHelper {
        void a(Throwable th);

        void b();
    }

    static final class EqualSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4804128302091633067L;
        final EqualCoordinatorHelper a;
        final int b;
        final int c;
        long d;
        volatile SimpleQueue<T> e;
        volatile boolean f;
        int g;

        EqualSubscriber(EqualCoordinatorHelper equalCoordinatorHelper, int i) {
            this.a = equalCoordinatorHelper;
            this.c = i - (i >> 2);
            this.b = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.g = requestFusion;
                        this.e = queueSubscription;
                        this.f = true;
                        this.a.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.g = requestFusion;
                        this.e = queueSubscription;
                        subscription.request((long) this.b);
                        return;
                    }
                }
                this.e = new SpscArrayQueue(this.b);
                subscription.request((long) this.b);
            }
        }

        public void onNext(T t) {
            if (this.g != 0 || this.e.offer(t)) {
                this.a.b();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.f = true;
            this.a.b();
        }

        public void a() {
            if (this.g != 1) {
                long j = this.d + 1;
                if (j >= ((long) this.c)) {
                    this.d = 0;
                    ((Subscription) get()).request(j);
                    return;
                }
                this.d = j;
            }
        }

        public void b() {
            SubscriptionHelper.cancel(this);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SimpleQueue<T> simpleQueue = this.e;
            if (simpleQueue != null) {
                simpleQueue.clear();
            }
        }
    }

    public FlowableSequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.b = publisher;
        this.c = publisher2;
        this.d = biPredicate;
        this.e = i;
    }

    public void subscribeActual(Subscriber<? super Boolean> subscriber) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(subscriber, this.e, this.d);
        subscriber.onSubscribe(equalCoordinator);
        equalCoordinator.a(this.b, this.c);
    }
}
