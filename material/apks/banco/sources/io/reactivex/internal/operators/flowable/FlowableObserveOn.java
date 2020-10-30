package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableObserveOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler b;
    final boolean c;
    final int d;

    static abstract class BaseObserveOnSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T>, Runnable {
        private static final long serialVersionUID = -8241002408341274697L;
        final Worker a;
        final boolean b;
        final int c;
        final int d;
        final AtomicLong e = new AtomicLong();
        Subscription f;
        SimpleQueue<T> g;
        volatile boolean h;
        volatile boolean i;
        Throwable j;
        int k;
        long l;
        boolean m;

        /* access modifiers changed from: 0000 */
        public abstract void b();

        /* access modifiers changed from: 0000 */
        public abstract void c();

        /* access modifiers changed from: 0000 */
        public abstract void d();

        BaseObserveOnSubscriber(Worker worker, boolean z, int i2) {
            this.a = worker;
            this.b = z;
            this.c = i2;
            this.d = i2 - (i2 >> 2);
        }

        public final void onNext(T t) {
            if (!this.i) {
                if (this.k == 2) {
                    a();
                    return;
                }
                if (!this.g.offer(t)) {
                    this.f.cancel();
                    this.j = new MissingBackpressureException("Queue is full?!");
                    this.i = true;
                }
                a();
            }
        }

        public final void onError(Throwable th) {
            if (this.i) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.j = th;
            this.i = true;
            a();
        }

        public final void onComplete() {
            if (!this.i) {
                this.i = true;
                a();
            }
        }

        public final void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.e, j2);
                a();
            }
        }

        public final void cancel() {
            if (!this.h) {
                this.h = true;
                this.f.cancel();
                this.a.dispose();
                if (getAndIncrement() == 0) {
                    this.g.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (getAndIncrement() == 0) {
                this.a.schedule(this);
            }
        }

        public final void run() {
            if (this.m) {
                b();
            } else if (this.k == 1) {
                c();
            } else {
                d();
            }
        }

        /* access modifiers changed from: 0000 */
        public final boolean a(boolean z, boolean z2, Subscriber<?> subscriber) {
            if (this.h) {
                clear();
                return true;
            }
            if (z) {
                if (!this.b) {
                    Throwable th = this.j;
                    if (th != null) {
                        clear();
                        subscriber.onError(th);
                        this.a.dispose();
                        return true;
                    } else if (z2) {
                        subscriber.onComplete();
                        this.a.dispose();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = this.j;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    this.a.dispose();
                    return true;
                }
            }
            return false;
        }

        public final int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            this.m = true;
            return 2;
        }

        public final void clear() {
            this.g.clear();
        }

        public final boolean isEmpty() {
            return this.g.isEmpty();
        }
    }

    static final class ObserveOnConditionalSubscriber<T> extends BaseObserveOnSubscriber<T> {
        private static final long serialVersionUID = 644624475404284533L;
        final ConditionalSubscriber<? super T> n;
        long o;

        ObserveOnConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Worker worker, boolean z, int i) {
            super(worker, z, i);
            this.n = conditionalSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.k = 1;
                        this.g = queueSubscription;
                        this.i = true;
                        this.n.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.k = 2;
                        this.g = queueSubscription;
                        this.n.onSubscribe(this);
                        subscription.request((long) this.c);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.c);
                this.n.onSubscribe(this);
                subscription.request((long) this.c);
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            ConditionalSubscriber<? super T> conditionalSubscriber = this.n;
            SimpleQueue simpleQueue = this.g;
            long j = this.l;
            int i = 1;
            while (true) {
                long j2 = this.e.get();
                while (j != j2) {
                    try {
                        Object poll = simpleQueue.poll();
                        if (!this.h) {
                            if (poll == null) {
                                conditionalSubscriber.onComplete();
                                this.a.dispose();
                                return;
                            } else if (conditionalSubscriber.tryOnNext(poll)) {
                                j++;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f.cancel();
                        conditionalSubscriber.onError(th);
                        this.a.dispose();
                        return;
                    }
                }
                if (!this.h) {
                    if (simpleQueue.isEmpty()) {
                        conditionalSubscriber.onComplete();
                        this.a.dispose();
                        return;
                    }
                    int i2 = get();
                    if (i == i2) {
                        this.l = j;
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        i = i2;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            ConditionalSubscriber<? super T> conditionalSubscriber = this.n;
            SimpleQueue simpleQueue = this.g;
            long j = this.l;
            long j2 = this.o;
            int i = 1;
            while (true) {
                long j3 = this.e.get();
                while (j != j3) {
                    boolean z = this.i;
                    try {
                        Object poll = simpleQueue.poll();
                        boolean z2 = poll == null;
                        if (!a(z, z2, conditionalSubscriber)) {
                            if (z2) {
                                break;
                            }
                            if (conditionalSubscriber.tryOnNext(poll)) {
                                j++;
                            }
                            long j4 = j2 + 1;
                            if (j4 == ((long) this.d)) {
                                this.f.request(j4);
                                j2 = 0;
                            } else {
                                j2 = j4;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f.cancel();
                        simpleQueue.clear();
                        conditionalSubscriber.onError(th);
                        this.a.dispose();
                        return;
                    }
                }
                if (j != j3 || !a(this.i, simpleQueue.isEmpty(), conditionalSubscriber)) {
                    int i2 = get();
                    if (i == i2) {
                        this.l = j;
                        this.o = j2;
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        i = i2;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i = 1;
            while (!this.h) {
                boolean z = this.i;
                this.n.onNext(null);
                if (z) {
                    Throwable th = this.j;
                    if (th != null) {
                        this.n.onError(th);
                    } else {
                        this.n.onComplete();
                    }
                    this.a.dispose();
                    return;
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
        }

        @Nullable
        public T poll() {
            T poll = this.g.poll();
            if (!(poll == null || this.k == 1)) {
                long j = this.o + 1;
                if (j == ((long) this.d)) {
                    this.o = 0;
                    this.f.request(j);
                } else {
                    this.o = j;
                }
            }
            return poll;
        }
    }

    static final class ObserveOnSubscriber<T> extends BaseObserveOnSubscriber<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4547113800637756442L;
        final Subscriber<? super T> n;

        ObserveOnSubscriber(Subscriber<? super T> subscriber, Worker worker, boolean z, int i) {
            super(worker, z, i);
            this.n = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.k = 1;
                        this.g = queueSubscription;
                        this.i = true;
                        this.n.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.k = 2;
                        this.g = queueSubscription;
                        this.n.onSubscribe(this);
                        subscription.request((long) this.c);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.c);
                this.n.onSubscribe(this);
                subscription.request((long) this.c);
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Subscriber<? super T> subscriber = this.n;
            SimpleQueue simpleQueue = this.g;
            long j = this.l;
            int i = 1;
            while (true) {
                long j2 = this.e.get();
                while (j != j2) {
                    try {
                        Object poll = simpleQueue.poll();
                        if (!this.h) {
                            if (poll == null) {
                                subscriber.onComplete();
                                this.a.dispose();
                                return;
                            }
                            subscriber.onNext(poll);
                            j++;
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f.cancel();
                        subscriber.onError(th);
                        this.a.dispose();
                        return;
                    }
                }
                if (!this.h) {
                    if (simpleQueue.isEmpty()) {
                        subscriber.onComplete();
                        this.a.dispose();
                        return;
                    }
                    int i2 = get();
                    if (i == i2) {
                        this.l = j;
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        i = i2;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            Subscriber<? super T> subscriber = this.n;
            SimpleQueue simpleQueue = this.g;
            long j = this.l;
            int i = 1;
            while (true) {
                long j2 = this.e.get();
                while (j != j2) {
                    boolean z = this.i;
                    try {
                        Object poll = simpleQueue.poll();
                        boolean z2 = poll == null;
                        if (!a(z, z2, subscriber)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(poll);
                            long j3 = j + 1;
                            if (j3 == ((long) this.d)) {
                                if (j2 != Long.MAX_VALUE) {
                                    j2 = this.e.addAndGet(-j3);
                                }
                                this.f.request(j3);
                                j = 0;
                            } else {
                                j = j3;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f.cancel();
                        simpleQueue.clear();
                        subscriber.onError(th);
                        this.a.dispose();
                        return;
                    }
                }
                if (j != j2 || !a(this.i, simpleQueue.isEmpty(), subscriber)) {
                    int i2 = get();
                    if (i == i2) {
                        this.l = j;
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        i = i2;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i = 1;
            while (!this.h) {
                boolean z = this.i;
                this.n.onNext(null);
                if (z) {
                    Throwable th = this.j;
                    if (th != null) {
                        this.n.onError(th);
                    } else {
                        this.n.onComplete();
                    }
                    this.a.dispose();
                    return;
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
        }

        @Nullable
        public T poll() {
            T poll = this.g.poll();
            if (!(poll == null || this.k == 1)) {
                long j = this.l + 1;
                if (j == ((long) this.d)) {
                    this.l = 0;
                    this.f.request(j);
                } else {
                    this.l = j;
                }
            }
            return poll;
        }
    }

    public FlowableObserveOn(Flowable<T> flowable, Scheduler scheduler, boolean z, int i) {
        super(flowable);
        this.b = scheduler;
        this.c = z;
        this.d = i;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        Worker createWorker = this.b.createWorker();
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new ObserveOnConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, createWorker, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new ObserveOnSubscriber<Object>(subscriber, createWorker, this.c, this.d));
        }
    }
}
