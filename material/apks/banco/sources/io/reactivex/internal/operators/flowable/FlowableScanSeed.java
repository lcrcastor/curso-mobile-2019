package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScanSeed<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final BiFunction<R, ? super T, R> b;
    final Callable<R> c;

    static final class ScanSeedSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -1776795561228106469L;
        final Subscriber<? super R> a;
        final BiFunction<R, ? super T, R> b;
        final SimplePlainQueue<R> c;
        final AtomicLong d = new AtomicLong();
        final int e;
        final int f;
        volatile boolean g;
        volatile boolean h;
        Throwable i;
        Subscription j;
        R k;
        int l;

        ScanSeedSubscriber(Subscriber<? super R> subscriber, BiFunction<R, ? super T, R> biFunction, R r, int i2) {
            this.a = subscriber;
            this.b = biFunction;
            this.k = r;
            this.e = i2;
            this.f = i2 - (i2 >> 2);
            this.c = new SpscArrayQueue(i2);
            this.c.offer(r);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                this.a.onSubscribe(this);
                subscription.request((long) (this.e - 1));
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                try {
                    R requireNonNull = ObjectHelper.requireNonNull(this.b.apply(this.k, t), "The accumulator returned a null value");
                    this.k = requireNonNull;
                    this.c.offer(requireNonNull);
                    a();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.j.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.i = th;
            this.h = true;
            a();
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                a();
            }
        }

        public void cancel() {
            this.g = true;
            this.j.cancel();
            if (getAndIncrement() == 0) {
                this.c.clear();
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.d, j2);
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.a;
                SimplePlainQueue<R> simplePlainQueue = this.c;
                int i2 = this.f;
                int i3 = this.l;
                int i4 = 1;
                do {
                    long j2 = this.d.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        if (this.g) {
                            simplePlainQueue.clear();
                            return;
                        }
                        boolean z = this.h;
                        if (z) {
                            Throwable th = this.i;
                            if (th != null) {
                                simplePlainQueue.clear();
                                subscriber.onError(th);
                                return;
                            }
                        }
                        Object poll = simplePlainQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            subscriber.onComplete();
                            return;
                        } else if (z2) {
                            break;
                        } else {
                            subscriber.onNext(poll);
                            long j4 = j3 + 1;
                            i3++;
                            if (i3 == i2) {
                                this.j.request((long) i2);
                                i3 = 0;
                            }
                            j3 = j4;
                        }
                    }
                    if (j3 == j2 && this.h) {
                        Throwable th2 = this.i;
                        if (th2 != null) {
                            simplePlainQueue.clear();
                            subscriber.onError(th2);
                            return;
                        } else if (simplePlainQueue.isEmpty()) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (j3 != 0) {
                        BackpressureHelper.produced(this.d, j3);
                    }
                    this.l = i3;
                    i4 = addAndGet(-i4);
                } while (i4 != 0);
            }
        }
    }

    public FlowableScanSeed(Flowable<T> flowable, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        super(flowable);
        this.b = biFunction;
        this.c = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber<? super T>) new ScanSeedSubscriber<Object>(subscriber, this.b, ObjectHelper.requireNonNull(this.c.call(), "The seed supplied is null"), bufferSize()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
