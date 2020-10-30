package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
    final Publisher<? extends T> a;
    final int b;
    final int c;

    static final class ParallelDispatcher<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4470634016609963609L;
        final Subscriber<? super T>[] a;
        final AtomicLongArray b;
        final long[] c;
        final int d;
        final int e;
        Subscription f;
        SimpleQueue<T> g;
        Throwable h;
        volatile boolean i;
        int j;
        volatile boolean k;
        final AtomicInteger l = new AtomicInteger();
        int m;
        int n;

        final class RailSubscription implements Subscription {
            final int a;
            final int b;

            RailSubscription(int i, int i2) {
                this.a = i;
                this.b = i2;
            }

            public void request(long j) {
                long j2;
                if (SubscriptionHelper.validate(j)) {
                    AtomicLongArray atomicLongArray = ParallelDispatcher.this.b;
                    do {
                        j2 = atomicLongArray.get(this.a);
                        if (j2 != Long.MAX_VALUE) {
                        } else {
                            return;
                        }
                    } while (!atomicLongArray.compareAndSet(this.a, j2, BackpressureHelper.addCap(j2, j)));
                    if (ParallelDispatcher.this.l.get() == this.b) {
                        ParallelDispatcher.this.d();
                    }
                }
            }

            public void cancel() {
                if (ParallelDispatcher.this.b.compareAndSet(this.a + this.b, 0, 1)) {
                    ParallelDispatcher.this.a(this.b + this.b);
                }
            }
        }

        ParallelDispatcher(Subscriber<? super T>[] subscriberArr, int i2) {
            this.a = subscriberArr;
            this.d = i2;
            this.e = i2 - (i2 >> 2);
            int length = subscriberArr.length;
            int i3 = length + length;
            this.b = new AtomicLongArray(i3 + 1);
            this.b.lazySet(i3, (long) length);
            this.c = new long[length];
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.n = requestFusion;
                        this.g = queueSubscription;
                        this.i = true;
                        a();
                        d();
                        return;
                    } else if (requestFusion == 2) {
                        this.n = requestFusion;
                        this.g = queueSubscription;
                        a();
                        subscription.request((long) this.d);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.d);
                a();
                subscription.request((long) this.d);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Subscriber<? super T>[] subscriberArr = this.a;
            int length = subscriberArr.length;
            int i2 = 0;
            while (i2 < length && !this.k) {
                int i3 = i2 + 1;
                this.l.lazySet(i3);
                subscriberArr[i2].onSubscribe(new RailSubscription(i2, length));
                i2 = i3;
            }
        }

        public void onNext(T t) {
            if (this.n != 0 || this.g.offer(t)) {
                d();
                return;
            }
            this.f.cancel();
            onError(new MissingBackpressureException("Queue is full?"));
        }

        public void onError(Throwable th) {
            this.h = th;
            this.i = true;
            d();
        }

        public void onComplete() {
            this.i = true;
            d();
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            if (this.b.decrementAndGet(i2) == 0) {
                this.k = true;
                this.f.cancel();
                if (getAndIncrement() == 0) {
                    this.g.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SimpleQueue<T> simpleQueue = this.g;
            Subscriber<? super T>[] subscriberArr = this.a;
            AtomicLongArray atomicLongArray = this.b;
            long[] jArr = this.c;
            int length = jArr.length;
            int i2 = this.j;
            int i3 = this.m;
            int i4 = 1;
            while (true) {
                int i5 = 0;
                int i6 = i3;
                int i7 = 0;
                while (!this.k) {
                    boolean z = this.i;
                    if (z) {
                        Throwable th = this.h;
                        if (th != null) {
                            simpleQueue.clear();
                            int length2 = subscriberArr.length;
                            while (i5 < length2) {
                                subscriberArr[i5].onError(th);
                                i5++;
                            }
                            return;
                        }
                    }
                    boolean isEmpty = simpleQueue.isEmpty();
                    if (!z || !isEmpty) {
                        if (!isEmpty) {
                            long j2 = atomicLongArray.get(i2);
                            long j3 = jArr[i2];
                            if (j2 == j3 || atomicLongArray.get(length + i2) != 0) {
                                i7++;
                            } else {
                                try {
                                    Object poll = simpleQueue.poll();
                                    if (poll != null) {
                                        subscriberArr[i2].onNext(poll);
                                        jArr[i2] = j3 + 1;
                                        int i8 = i6 + 1;
                                        if (i8 == this.e) {
                                            this.f.request((long) i8);
                                            i8 = 0;
                                        }
                                        i6 = i8;
                                        i7 = 0;
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.f.cancel();
                                    int length3 = subscriberArr.length;
                                    while (i5 < length3) {
                                        subscriberArr[i5].onError(th2);
                                        i5++;
                                    }
                                    return;
                                }
                            }
                            i2++;
                            if (i2 == length) {
                                i2 = 0;
                                continue;
                            }
                            if (i7 == length) {
                            }
                        }
                        i3 = i6;
                        int i9 = get();
                        if (i9 == i4) {
                            this.j = i2;
                            this.m = i3;
                            i4 = addAndGet(-i4);
                            if (i4 == 0) {
                                return;
                            }
                        } else {
                            i4 = i9;
                        }
                    } else {
                        int length4 = subscriberArr.length;
                        while (i5 < length4) {
                            subscriberArr[i5].onComplete();
                            i5++;
                        }
                        return;
                    }
                }
                simpleQueue.clear();
                return;
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SimpleQueue<T> simpleQueue = this.g;
            Subscriber<? super T>[] subscriberArr = this.a;
            AtomicLongArray atomicLongArray = this.b;
            long[] jArr = this.c;
            int length = jArr.length;
            int i2 = this.j;
            int i3 = 1;
            while (true) {
                int i4 = 0;
                int i5 = 0;
                while (!this.k) {
                    if (simpleQueue.isEmpty()) {
                        int length2 = subscriberArr.length;
                        while (i4 < length2) {
                            subscriberArr[i4].onComplete();
                            i4++;
                        }
                        return;
                    }
                    long j2 = atomicLongArray.get(i2);
                    long j3 = jArr[i2];
                    if (j2 == j3 || atomicLongArray.get(length + i2) != 0) {
                        i5++;
                    } else {
                        try {
                            Object poll = simpleQueue.poll();
                            if (poll == null) {
                                int length3 = subscriberArr.length;
                                while (i4 < length3) {
                                    subscriberArr[i4].onComplete();
                                    i4++;
                                }
                                return;
                            }
                            subscriberArr[i2].onNext(poll);
                            jArr[i2] = j3 + 1;
                            i5 = 0;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.f.cancel();
                            int length4 = subscriberArr.length;
                            while (i4 < length4) {
                                subscriberArr[i4].onError(th);
                                i4++;
                            }
                            return;
                        }
                    }
                    i2++;
                    if (i2 == length) {
                        i2 = 0;
                        continue;
                    }
                    if (i5 == length) {
                        int i6 = get();
                        if (i6 == i3) {
                            this.j = i2;
                            i3 = addAndGet(-i3);
                            if (i3 == 0) {
                                return;
                            }
                        } else {
                            i3 = i6;
                        }
                    }
                }
                simpleQueue.clear();
                return;
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (getAndIncrement() == 0) {
                if (this.n == 1) {
                    c();
                } else {
                    b();
                }
            }
        }
    }

    public ParallelFromPublisher(Publisher<? extends T> publisher, int i, int i2) {
        this.a = publisher;
        this.b = i;
        this.c = i2;
    }

    public int parallelism() {
        return this.b;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            this.a.subscribe(new ParallelDispatcher(subscriberArr, this.c));
        }
    }
}
