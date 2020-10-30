package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelRunOn<T> extends ParallelFlowable<T> {
    final ParallelFlowable<? extends T> a;
    final Scheduler b;
    final int c;

    static abstract class BaseRunOnSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 9222303586456402150L;
        final int a;
        final int b;
        final SpscArrayQueue<T> c;
        final Worker d;
        Subscription e;
        volatile boolean f;
        Throwable g;
        final AtomicLong h = new AtomicLong();
        volatile boolean i;
        int j;

        BaseRunOnSubscriber(int i2, SpscArrayQueue<T> spscArrayQueue, Worker worker) {
            this.a = i2;
            this.c = spscArrayQueue;
            this.b = i2 - (i2 >> 2);
            this.d = worker;
        }

        public final void onNext(T t) {
            if (!this.f) {
                if (!this.c.offer(t)) {
                    this.e.cancel();
                    onError(new MissingBackpressureException("Queue is full?!"));
                    return;
                }
                a();
            }
        }

        public final void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = th;
            this.f = true;
            a();
        }

        public final void onComplete() {
            if (!this.f) {
                this.f = true;
                a();
            }
        }

        public final void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.h, j2);
                a();
            }
        }

        public final void cancel() {
            if (!this.i) {
                this.i = true;
                this.e.cancel();
                this.d.dispose();
                if (getAndIncrement() == 0) {
                    this.c.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (getAndIncrement() == 0) {
                this.d.schedule(this);
            }
        }
    }

    static final class RunOnConditionalSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final ConditionalSubscriber<? super T> k;

        RunOnConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, int i, SpscArrayQueue<T> spscArrayQueue, Worker worker) {
            super(i, spscArrayQueue, worker);
            this.k = conditionalSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.k.onSubscribe(this);
                subscription.request((long) this.a);
            }
        }

        public void run() {
            int i;
            int i2 = this.j;
            SpscArrayQueue spscArrayQueue = this.c;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.k;
            int i3 = this.b;
            int i4 = 1;
            while (true) {
                long j = this.h.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.f;
                    if (z) {
                        Throwable th = this.g;
                        if (th != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th);
                            this.d.dispose();
                            return;
                        }
                    }
                    Object poll = spscArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (z && z2) {
                        conditionalSubscriber.onComplete();
                        this.d.dispose();
                        return;
                    } else if (z2) {
                        break;
                    } else {
                        if (conditionalSubscriber.tryOnNext(poll)) {
                            j2++;
                        }
                        i2++;
                        if (i2 == i3) {
                            i = i4;
                            this.e.request((long) i2);
                            i2 = 0;
                        } else {
                            i = i4;
                        }
                        i4 = i;
                    }
                }
                int i5 = i4;
                if (j2 == j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    } else if (this.f) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th2);
                            this.d.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            conditionalSubscriber.onComplete();
                            this.d.dispose();
                            return;
                        }
                    }
                }
                if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                    this.h.addAndGet(-j2);
                }
                int i6 = get();
                int i7 = i5;
                if (i6 == i7) {
                    this.j = i2;
                    i6 = addAndGet(-i7);
                    if (i6 == 0) {
                        return;
                    }
                }
                i4 = i6;
            }
        }
    }

    static final class RunOnSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final Subscriber<? super T> k;

        RunOnSubscriber(Subscriber<? super T> subscriber, int i, SpscArrayQueue<T> spscArrayQueue, Worker worker) {
            super(i, spscArrayQueue, worker);
            this.k = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.k.onSubscribe(this);
                subscription.request((long) this.a);
            }
        }

        public void run() {
            int i = this.j;
            SpscArrayQueue spscArrayQueue = this.c;
            Subscriber<? super T> subscriber = this.k;
            int i2 = this.b;
            int i3 = 1;
            while (true) {
                long j = this.h.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.f;
                    if (z) {
                        Throwable th = this.g;
                        if (th != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th);
                            this.d.dispose();
                            return;
                        }
                    }
                    Object poll = spscArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (z && z2) {
                        subscriber.onComplete();
                        this.d.dispose();
                        return;
                    } else if (z2) {
                        break;
                    } else {
                        subscriber.onNext(poll);
                        long j3 = j2 + 1;
                        i++;
                        if (i == i2) {
                            this.e.request((long) i);
                            i = 0;
                        }
                        j2 = j3;
                    }
                }
                if (j2 == j) {
                    if (this.i) {
                        spscArrayQueue.clear();
                        return;
                    } else if (this.f) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th2);
                            this.d.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            this.d.dispose();
                            return;
                        }
                    }
                }
                if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                    this.h.addAndGet(-j2);
                }
                int i4 = get();
                if (i4 == i3) {
                    this.j = i;
                    i3 = addAndGet(-i3);
                    if (i3 == 0) {
                        return;
                    }
                } else {
                    i3 = i4;
                }
            }
        }
    }

    public ParallelRunOn(ParallelFlowable<? extends T> parallelFlowable, Scheduler scheduler, int i) {
        this.a = parallelFlowable;
        this.b = scheduler;
        this.c = i;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            int i = this.c;
            for (int i2 = 0; i2 < length; i2++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i2];
                Worker createWorker = this.b.createWorker();
                SpscArrayQueue spscArrayQueue = new SpscArrayQueue(i);
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i2] = new RunOnConditionalSubscriber(conditionalSubscriber, i, spscArrayQueue, createWorker);
                } else {
                    subscriberArr2[i2] = new RunOnSubscriber(conditionalSubscriber, i, spscArrayQueue, createWorker);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.a.parallelism();
    }
}
