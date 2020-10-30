package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindow<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final long b;
    final long c;
    final int d;

    static final class WindowExactSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -2365647875069161133L;
        final Subscriber<? super Flowable<T>> a;
        final long b;
        final AtomicBoolean c = new AtomicBoolean();
        final int d;
        long e;
        Subscription f;
        UnicastProcessor<T> g;

        WindowExactSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, int i) {
            super(1);
            this.a = subscriber;
            this.b = j;
            this.d = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.e;
            UnicastProcessor<T> unicastProcessor = this.g;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.d, this);
                this.g = unicastProcessor;
                this.a.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            unicastProcessor.onNext(t);
            if (j2 == this.b) {
                this.e = 0;
                this.g = null;
                unicastProcessor.onComplete();
                return;
            }
            this.e = j2;
        }

        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.g;
            if (unicastProcessor != null) {
                this.g = null;
                unicastProcessor.onError(th);
            }
            this.a.onError(th);
        }

        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.g;
            if (unicastProcessor != null) {
                this.g = null;
                unicastProcessor.onComplete();
            }
            this.a.onComplete();
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.f.request(BackpressureHelper.multiplyCap(this.b, j));
            }
        }

        public void cancel() {
            if (this.c.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.f.cancel();
            }
        }
    }

    static final class WindowOverlapSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 2428527070996323976L;
        final Subscriber<? super Flowable<T>> a;
        final SpscLinkedArrayQueue<UnicastProcessor<T>> b;
        final long c;
        final long d;
        final ArrayDeque<UnicastProcessor<T>> e = new ArrayDeque<>();
        final AtomicBoolean f = new AtomicBoolean();
        final AtomicBoolean g = new AtomicBoolean();
        final AtomicLong h = new AtomicLong();
        final AtomicInteger i = new AtomicInteger();
        final int j;
        long k;
        long l;
        Subscription m;
        volatile boolean n;
        Throwable o;
        volatile boolean p;

        WindowOverlapSubscriber(Subscriber<? super Flowable<T>> subscriber, long j2, long j3, int i2) {
            super(1);
            this.a = subscriber;
            this.c = j2;
            this.d = j3;
            this.b = new SpscLinkedArrayQueue<>(i2);
            this.j = i2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.m, subscription)) {
                this.m = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.n) {
                long j2 = this.k;
                if (j2 == 0 && !this.p) {
                    getAndIncrement();
                    UnicastProcessor create = UnicastProcessor.create(this.j, this);
                    this.e.offer(create);
                    this.b.offer(create);
                    a();
                }
                long j3 = j2 + 1;
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    ((Processor) it.next()).onNext(t);
                }
                long j4 = this.l + 1;
                if (j4 == this.c) {
                    this.l = j4 - this.d;
                    Processor processor = (Processor) this.e.poll();
                    if (processor != null) {
                        processor.onComplete();
                    }
                } else {
                    this.l = j4;
                }
                if (j3 == this.d) {
                    this.k = 0;
                } else {
                    this.k = j3;
                }
            }
        }

        public void onError(Throwable th) {
            if (this.n) {
                RxJavaPlugins.onError(th);
                return;
            }
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                ((Processor) it.next()).onError(th);
            }
            this.e.clear();
            this.o = th;
            this.n = true;
            a();
        }

        public void onComplete() {
            if (!this.n) {
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    ((Processor) it.next()).onComplete();
                }
                this.e.clear();
                this.n = true;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.i.getAndIncrement() == 0) {
                Subscriber<? super Flowable<T>> subscriber = this.a;
                SpscLinkedArrayQueue<UnicastProcessor<T>> spscLinkedArrayQueue = this.b;
                int i2 = 1;
                do {
                    long j2 = this.h.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        boolean z = this.n;
                        UnicastProcessor unicastProcessor = (UnicastProcessor) spscLinkedArrayQueue.poll();
                        boolean z2 = unicastProcessor == null;
                        if (!a(z, z2, subscriber, spscLinkedArrayQueue)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(unicastProcessor);
                            j3++;
                        } else {
                            return;
                        }
                    }
                    if (j3 != j2 || !a(this.n, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                        if (!(j3 == 0 || j2 == Long.MAX_VALUE)) {
                            this.h.addAndGet(-j3);
                        }
                        i2 = this.i.addAndGet(-i2);
                    } else {
                        return;
                    }
                } while (i2 != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.p) {
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (z) {
                Throwable th = this.o;
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (z2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.h, j2);
                if (this.g.get() || !this.g.compareAndSet(false, true)) {
                    this.m.request(BackpressureHelper.multiplyCap(this.d, j2));
                } else {
                    this.m.request(BackpressureHelper.addCap(this.c, BackpressureHelper.multiplyCap(this.d, j2 - 1)));
                }
                a();
            }
        }

        public void cancel() {
            this.p = true;
            if (this.f.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.m.cancel();
            }
        }
    }

    static final class WindowSkipSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -8792836352386833856L;
        final Subscriber<? super Flowable<T>> a;
        final long b;
        final long c;
        final AtomicBoolean d = new AtomicBoolean();
        final AtomicBoolean e = new AtomicBoolean();
        final int f;
        long g;
        Subscription h;
        UnicastProcessor<T> i;

        WindowSkipSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i2) {
            super(1);
            this.a = subscriber;
            this.b = j;
            this.c = j2;
            this.f = i2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.g;
            UnicastProcessor<T> unicastProcessor = this.i;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.f, this);
                this.i = unicastProcessor;
                this.a.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            if (unicastProcessor != null) {
                unicastProcessor.onNext(t);
            }
            if (j2 == this.b) {
                this.i = null;
                unicastProcessor.onComplete();
            }
            if (j2 == this.c) {
                this.g = 0;
            } else {
                this.g = j2;
            }
        }

        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.i;
            if (unicastProcessor != null) {
                this.i = null;
                unicastProcessor.onError(th);
            }
            this.a.onError(th);
        }

        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.i;
            if (unicastProcessor != null) {
                this.i = null;
                unicastProcessor.onComplete();
            }
            this.a.onComplete();
        }

        public void request(long j) {
            if (!SubscriptionHelper.validate(j)) {
                return;
            }
            if (this.e.get() || !this.e.compareAndSet(false, true)) {
                this.h.request(BackpressureHelper.multiplyCap(this.c, j));
                return;
            }
            this.h.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.b, j), BackpressureHelper.multiplyCap(this.c - this.b, j - 1)));
        }

        public void cancel() {
            if (this.d.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.h.cancel();
            }
        }
    }

    public FlowableWindow(Flowable<T> flowable, long j, long j2, int i) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = i;
    }

    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        if (this.c == this.b) {
            this.source.subscribe((FlowableSubscriber<? super T>) new WindowExactSubscriber<Object>(subscriber, this.b, this.d));
        } else if (this.c > this.b) {
            Flowable flowable = this.source;
            WindowSkipSubscriber windowSkipSubscriber = new WindowSkipSubscriber(subscriber, this.b, this.c, this.d);
            flowable.subscribe((FlowableSubscriber<? super T>) windowSkipSubscriber);
        } else {
            Flowable flowable2 = this.source;
            WindowOverlapSubscriber windowOverlapSubscriber = new WindowOverlapSubscriber(subscriber, this.b, this.c, this.d);
            flowable2.subscribe((FlowableSubscriber<? super T>) windowOverlapSubscriber);
        }
    }
}
