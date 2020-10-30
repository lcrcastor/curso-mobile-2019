package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZip<T, R> extends Flowable<R> {
    final Publisher<? extends T>[] b;
    final Iterable<? extends Publisher<? extends T>> c;
    final Function<? super Object[], ? extends R> d;
    final int e;
    final boolean f;

    static final class ZipCoordinator<T, R> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = -2434867452883857743L;
        final Subscriber<? super R> a;
        final ZipSubscriber<T, R>[] b;
        final Function<? super Object[], ? extends R> c;
        final AtomicLong d;
        final AtomicThrowable e;
        final boolean f;
        volatile boolean g;
        final Object[] h;

        ZipCoordinator(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i, int i2, boolean z) {
            this.a = subscriber;
            this.c = function;
            this.f = z;
            ZipSubscriber<T, R>[] zipSubscriberArr = new ZipSubscriber[i];
            for (int i3 = 0; i3 < i; i3++) {
                zipSubscriberArr[i3] = new ZipSubscriber<>(this, i2);
            }
            this.h = new Object[i];
            this.b = zipSubscriberArr;
            this.d = new AtomicLong();
            this.e = new AtomicThrowable();
        }

        /* access modifiers changed from: 0000 */
        public void a(Publisher<? extends T>[] publisherArr, int i) {
            ZipSubscriber<T, R>[] zipSubscriberArr = this.b;
            for (int i2 = 0; i2 < i && !this.g && (this.f || this.e.get() == null); i2++) {
                publisherArr[i2].subscribe(zipSubscriberArr[i2]);
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                b();
            }
        }

        public void cancel() {
            if (!this.g) {
                this.g = true;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ZipSubscriber<T, R> zipSubscriber, Throwable th) {
            if (this.e.addThrowable(th)) {
                zipSubscriber.f = true;
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            for (ZipSubscriber<T, R> cancel : this.b) {
                cancel.cancel();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            boolean z;
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.a;
                ZipSubscriber<T, R>[] zipSubscriberArr = this.b;
                int length = zipSubscriberArr.length;
                Object[] objArr = this.h;
                int i = 1;
                do {
                    long j = this.d.get();
                    long j2 = 0;
                    while (j != j2) {
                        if (!this.g) {
                            if (this.f || this.e.get() == null) {
                                boolean z2 = false;
                                for (int i2 = 0; i2 < length; i2++) {
                                    ZipSubscriber<T, R> zipSubscriber = zipSubscriberArr[i2];
                                    if (objArr[i2] == null) {
                                        try {
                                            boolean z3 = zipSubscriber.f;
                                            SimpleQueue<T> simpleQueue = zipSubscriber.d;
                                            Object poll = simpleQueue != null ? simpleQueue.poll() : null;
                                            boolean z4 = poll == null;
                                            if (!z3 || !z4) {
                                                if (!z4) {
                                                    objArr[i2] = poll;
                                                    z = z2;
                                                } else {
                                                    z = true;
                                                }
                                                z2 = z;
                                            } else {
                                                a();
                                                if (((Throwable) this.e.get()) != null) {
                                                    subscriber.onError(this.e.terminate());
                                                } else {
                                                    subscriber.onComplete();
                                                }
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            Throwable th2 = th;
                                            Exceptions.throwIfFatal(th2);
                                            this.e.addThrowable(th2);
                                            if (!this.f) {
                                                a();
                                                subscriber.onError(this.e.terminate());
                                                return;
                                            }
                                            z2 = true;
                                        }
                                    }
                                }
                                if (z2) {
                                    break;
                                }
                                try {
                                    subscriber.onNext(ObjectHelper.requireNonNull(this.c.apply(objArr.clone()), "The zipper returned a null value"));
                                    long j3 = j2 + 1;
                                    Arrays.fill(objArr, null);
                                    j2 = j3;
                                } catch (Throwable th3) {
                                    Throwable th4 = th3;
                                    Exceptions.throwIfFatal(th4);
                                    a();
                                    this.e.addThrowable(th4);
                                    subscriber.onError(this.e.terminate());
                                    return;
                                }
                            } else {
                                a();
                                subscriber.onError(this.e.terminate());
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (j == j2) {
                        if (!this.g) {
                            if (this.f || this.e.get() == null) {
                                for (int i3 = 0; i3 < length; i3++) {
                                    ZipSubscriber<T, R> zipSubscriber2 = zipSubscriberArr[i3];
                                    if (objArr[i3] == null) {
                                        try {
                                            boolean z5 = zipSubscriber2.f;
                                            SimpleQueue<T> simpleQueue2 = zipSubscriber2.d;
                                            Object poll2 = simpleQueue2 != null ? simpleQueue2.poll() : null;
                                            boolean z6 = poll2 == null;
                                            if (z5 && z6) {
                                                a();
                                                if (((Throwable) this.e.get()) != null) {
                                                    subscriber.onError(this.e.terminate());
                                                } else {
                                                    subscriber.onComplete();
                                                }
                                                return;
                                            } else if (!z6) {
                                                objArr[i3] = poll2;
                                            }
                                        } catch (Throwable th5) {
                                            Throwable th6 = th5;
                                            Exceptions.throwIfFatal(th6);
                                            this.e.addThrowable(th6);
                                            if (!this.f) {
                                                a();
                                                subscriber.onError(this.e.terminate());
                                                return;
                                            }
                                        }
                                    }
                                }
                            } else {
                                a();
                                subscriber.onError(this.e.terminate());
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (j2 != 0) {
                        for (ZipSubscriber<T, R> request : zipSubscriberArr) {
                            request.request(j2);
                        }
                        if (j != Long.MAX_VALUE) {
                            this.d.addAndGet(-j2);
                        }
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }
    }

    static final class ZipSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4627193790118206028L;
        final ZipCoordinator<T, R> a;
        final int b;
        final int c;
        SimpleQueue<T> d;
        long e;
        volatile boolean f;
        int g;

        ZipSubscriber(ZipCoordinator<T, R> zipCoordinator, int i) {
            this.a = zipCoordinator;
            this.b = i;
            this.c = i - (i >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.g = requestFusion;
                        this.d = queueSubscription;
                        this.f = true;
                        this.a.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.g = requestFusion;
                        this.d = queueSubscription;
                        subscription.request((long) this.b);
                        return;
                    }
                }
                this.d = new SpscArrayQueue(this.b);
                subscription.request((long) this.b);
            }
        }

        public void onNext(T t) {
            if (this.g != 2) {
                this.d.offer(t);
            }
            this.a.b();
        }

        public void onError(Throwable th) {
            this.a.a(this, th);
        }

        public void onComplete() {
            this.f = true;
            this.a.b();
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        public void request(long j) {
            if (this.g != 1) {
                long j2 = this.e + j;
                if (j2 >= ((long) this.c)) {
                    this.e = 0;
                    ((Subscription) get()).request(j2);
                    return;
                }
                this.e = j2;
            }
        }
    }

    public FlowableZip(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    public void subscribeActual(Subscriber<? super R> subscriber) {
        Publisher<? extends T>[] publisherArr;
        int i;
        Publisher<? extends T>[] publisherArr2 = this.b;
        if (publisherArr2 == null) {
            Publisher<? extends T>[] publisherArr3 = new Publisher[8];
            Publisher<? extends T>[] publisherArr4 = publisherArr3;
            i = 0;
            for (Publisher<? extends T> publisher : this.c) {
                if (i == publisherArr4.length) {
                    Publisher<? extends T>[] publisherArr5 = new Publisher[((i >> 2) + i)];
                    System.arraycopy(publisherArr4, 0, publisherArr5, 0, i);
                    publisherArr4 = publisherArr5;
                }
                int i2 = i + 1;
                publisherArr4[i] = publisher;
                i = i2;
            }
            publisherArr = publisherArr4;
        } else {
            publisherArr = publisherArr2;
            i = publisherArr2.length;
        }
        if (i == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(subscriber, this.d, i, this.e, this.f);
        subscriber.onSubscribe(zipCoordinator);
        zipCoordinator.a(publisherArr, i);
    }
}
