package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferTimed<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final Callable<U> f;
    final int g;
    final boolean h;

    static final class BufferExactBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Disposable, Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final TimeUnit c;
        final int d;
        final boolean e;
        final Worker f;
        U g;
        Disposable h;
        Subscription i;
        long j;
        long k;

        BufferExactBoundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j2, TimeUnit timeUnit, int i2, boolean z, Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j2;
            this.c = timeUnit;
            this.d = i2;
            this.e = z;
            this.f = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                try {
                    this.g = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    this.actual.onSubscribe(this);
                    this.h = this.f.schedulePeriodically(this, this.b, this.b, this.c);
                    subscription.request(Long.MAX_VALUE);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
            if (r14.e == false) goto L_0x0029;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
            r14.g = null;
            r14.j++;
            r14.h.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
            fastPathOrderedEmitMax(r0, false, r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r15 = (java.util.Collection) io.reactivex.internal.functions.ObjectHelper.requireNonNull(r14.a.call(), "The supplied buffer is null");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
            if (r14.e == false) goto L_0x005d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003f, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r14.g = r15;
            r14.k++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            r14.h = r14.f.schedulePeriodically(r14, r14.b, r14.b, r14.c);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x005d, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r14.g = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0061, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0065, code lost:
            r15 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0066, code lost:
            io.reactivex.exceptions.Exceptions.throwIfFatal(r15);
            cancel();
            r14.actual.onError(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0071, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNext(T r15) {
            /*
                r14 = this;
                monitor-enter(r14)
                U r0 = r14.g     // Catch:{ all -> 0x0072 }
                if (r0 != 0) goto L_0x0007
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                return
            L_0x0007:
                r0.add(r15)     // Catch:{ all -> 0x0072 }
                int r15 = r0.size()     // Catch:{ all -> 0x0072 }
                int r1 = r14.d     // Catch:{ all -> 0x0072 }
                if (r15 >= r1) goto L_0x0014
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                return
            L_0x0014:
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                boolean r15 = r14.e
                r1 = 1
                if (r15 == 0) goto L_0x0029
                r15 = 0
                r14.g = r15
                long r3 = r14.j
                long r5 = r3 + r1
                r14.j = r5
                io.reactivex.disposables.Disposable r15 = r14.h
                r15.dispose()
            L_0x0029:
                r15 = 0
                r14.fastPathOrderedEmitMax(r0, r15, r14)
                java.util.concurrent.Callable<U> r15 = r14.a     // Catch:{ Throwable -> 0x0065 }
                java.lang.Object r15 = r15.call()     // Catch:{ Throwable -> 0x0065 }
                java.lang.String r0 = "The supplied buffer is null"
                java.lang.Object r15 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r15, r0)     // Catch:{ Throwable -> 0x0065 }
                java.util.Collection r15 = (java.util.Collection) r15     // Catch:{ Throwable -> 0x0065 }
                boolean r0 = r14.e
                if (r0 == 0) goto L_0x005d
                monitor-enter(r14)
                r14.g = r15     // Catch:{ all -> 0x005a }
                long r3 = r14.k     // Catch:{ all -> 0x005a }
                r15 = 0
                long r5 = r3 + r1
                r14.k = r5     // Catch:{ all -> 0x005a }
                monitor-exit(r14)     // Catch:{ all -> 0x005a }
                io.reactivex.Scheduler$Worker r7 = r14.f
                long r9 = r14.b
                long r11 = r14.b
                java.util.concurrent.TimeUnit r13 = r14.c
                r8 = r14
                io.reactivex.disposables.Disposable r15 = r7.schedulePeriodically(r8, r9, r11, r13)
                r14.h = r15
                goto L_0x0061
            L_0x005a:
                r15 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x005a }
                throw r15
            L_0x005d:
                monitor-enter(r14)
                r14.g = r15     // Catch:{ all -> 0x0062 }
                monitor-exit(r14)     // Catch:{ all -> 0x0062 }
            L_0x0061:
                return
            L_0x0062:
                r15 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x0062 }
                throw r15
            L_0x0065:
                r15 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r15)
                r14.cancel()
                org.reactivestreams.Subscriber r0 = r14.actual
                r0.onError(r15)
                return
            L_0x0072:
                r15 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                throw r15
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableBufferTimed.BufferExactBoundedSubscriber.onNext(java.lang.Object):void");
        }

        public void onError(Throwable th) {
            synchronized (this) {
                this.g = null;
            }
            this.actual.onError(th);
            this.f.dispose();
        }

        public void onComplete() {
            U u;
            synchronized (this) {
                u = this.g;
                this.g = null;
            }
            this.queue.offer(u);
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(this.queue, this.actual, false, this, this);
            }
            this.f.dispose();
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }

        public void request(long j2) {
            requested(j2);
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                dispose();
            }
        }

        public void dispose() {
            synchronized (this) {
                this.g = null;
            }
            this.i.cancel();
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        public void run() {
            try {
                U u = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                synchronized (this) {
                    U u2 = this.g;
                    if (u2 != null) {
                        if (this.j == this.k) {
                            this.g = u;
                            fastPathOrderedEmitMax(u2, false, this);
                        }
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }
    }

    static final class BufferExactUnboundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Disposable, Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        Subscription e;
        U f;
        final AtomicReference<Disposable> g = new AtomicReference<>();

        BufferExactUnboundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                try {
                    this.f = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    this.actual.onSubscribe(this);
                    if (!this.cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        Disposable schedulePeriodicallyDirect = this.d.schedulePeriodicallyDirect(this, this.b, this.b, this.c);
                        if (!this.g.compareAndSet(null, schedulePeriodicallyDirect)) {
                            schedulePeriodicallyDirect.dispose();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                U u = this.f;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.g);
            synchronized (this) {
                this.f = null;
            }
            this.actual.onError(th);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
            if (enter() == false) goto L_0x0026;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
            io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r3.queue, r3.actual, false, r3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
            r3.queue.offer(r0);
            r3.done = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onComplete() {
            /*
                r3 = this;
                java.util.concurrent.atomic.AtomicReference<io.reactivex.disposables.Disposable> r0 = r3.g
                io.reactivex.internal.disposables.DisposableHelper.dispose(r0)
                monitor-enter(r3)
                U r0 = r3.f     // Catch:{ all -> 0x0027 }
                if (r0 != 0) goto L_0x000c
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                return
            L_0x000c:
                r1 = 0
                r3.f = r1     // Catch:{ all -> 0x0027 }
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                io.reactivex.internal.fuseable.SimplePlainQueue r1 = r3.queue
                r1.offer(r0)
                r0 = 1
                r3.done = r0
                boolean r0 = r3.enter()
                if (r0 == 0) goto L_0x0026
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r3.queue
                org.reactivestreams.Subscriber r1 = r3.actual
                r2 = 0
                io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r0, r1, r2, r3, r3)
            L_0x0026:
                return
            L_0x0027:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableBufferTimed.BufferExactUnboundedSubscriber.onComplete():void");
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            this.e.cancel();
            DisposableHelper.dispose(this.g);
        }

        public void run() {
            U u;
            try {
                U u2 = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                synchronized (this) {
                    u = this.f;
                    if (u != null) {
                        this.f = u2;
                    }
                }
                if (u == null) {
                    DisposableHelper.dispose(this.g);
                } else {
                    fastPathEmitMax(u, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.actual.onNext(u);
            return true;
        }

        public void dispose() {
            cancel();
        }

        public boolean isDisposed() {
            return this.g.get() == DisposableHelper.DISPOSED;
        }
    }

    static final class BufferSkipBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final long c;
        final TimeUnit d;
        final Worker e;
        final List<U> f = new LinkedList();
        Subscription g;

        final class RemoveFromBuffer implements Runnable {
            private final U b;

            RemoveFromBuffer(U u) {
                this.b = u;
            }

            public void run() {
                synchronized (BufferSkipBoundedSubscriber.this) {
                    BufferSkipBoundedSubscriber.this.f.remove(this.b);
                }
                BufferSkipBoundedSubscriber.this.fastPathOrderedEmitMax(this.b, false, BufferSkipBoundedSubscriber.this.e);
            }
        }

        BufferSkipBoundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j, long j2, TimeUnit timeUnit, Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    this.f.add(collection);
                    this.actual.onSubscribe(this);
                    subscription.request(Long.MAX_VALUE);
                    this.e.schedulePeriodically(this, this.c, this.c, this.d);
                    this.e.schedule(new RemoveFromBuffer(collection), this.b, this.d);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.e.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                for (U add : this.f) {
                    add.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            this.done = true;
            this.e.dispose();
            a();
            this.actual.onError(th);
        }

        public void onComplete() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(this.f);
                this.f.clear();
            }
            for (Collection offer : arrayList) {
                this.queue.offer(offer);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(this.queue, this.actual, false, this.e, this);
            }
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            a();
            this.g.cancel();
            this.e.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            synchronized (this) {
                this.f.clear();
            }
        }

        public void run() {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    synchronized (this) {
                        if (!this.cancelled) {
                            this.f.add(collection);
                            this.e.schedule(new RemoveFromBuffer(collection), this.b, this.d);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.actual.onError(th);
                }
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }
    }

    public FlowableBufferTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, Callable<U> callable, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = callable;
        this.g = i;
        this.h = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (this.b == this.c && this.g == Integer.MAX_VALUE) {
            Flowable flowable = this.source;
            BufferExactUnboundedSubscriber bufferExactUnboundedSubscriber = new BufferExactUnboundedSubscriber(new SerializedSubscriber(subscriber), this.f, this.b, this.d, this.e);
            flowable.subscribe((FlowableSubscriber<? super T>) bufferExactUnboundedSubscriber);
            return;
        }
        Worker createWorker = this.e.createWorker();
        if (this.b == this.c) {
            Flowable flowable2 = this.source;
            BufferExactBoundedSubscriber bufferExactBoundedSubscriber = new BufferExactBoundedSubscriber(new SerializedSubscriber(subscriber), this.f, this.b, this.d, this.g, this.h, createWorker);
            flowable2.subscribe((FlowableSubscriber<? super T>) bufferExactBoundedSubscriber);
            return;
        }
        Flowable flowable3 = this.source;
        BufferSkipBoundedSubscriber bufferSkipBoundedSubscriber = new BufferSkipBoundedSubscriber(new SerializedSubscriber(subscriber), this.f, this.b, this.c, this.d, createWorker);
        flowable3.subscribe((FlowableSubscriber<? super T>) bufferSkipBoundedSubscriber);
    }
}
