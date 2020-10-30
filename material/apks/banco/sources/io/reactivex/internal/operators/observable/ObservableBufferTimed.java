package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferTimed<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final Callable<U> e;
    final int f;
    final boolean g;

    static final class BufferExactBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final TimeUnit d;
        final int e;
        final boolean f;
        final Worker g;
        U h;
        Disposable i;
        Disposable j;
        long k;
        long l;

        BufferExactBoundedObserver(Observer<? super U> observer, Callable<U> callable, long j2, TimeUnit timeUnit, int i2, boolean z, Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j2;
            this.d = timeUnit;
            this.e = i2;
            this.f = z;
            this.g = worker;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.j, disposable)) {
                this.j = disposable;
                try {
                    this.h = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null");
                    this.actual.onSubscribe(this);
                    this.i = this.g.schedulePeriodically(this, this.c, this.c, this.d);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.actual);
                    this.g.dispose();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
            if (r14.f == false) goto L_0x0029;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
            r14.h = null;
            r14.k++;
            r14.i.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
            fastPathOrderedEmit(r0, false, r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r15 = (java.util.Collection) io.reactivex.internal.functions.ObjectHelper.requireNonNull(r14.b.call(), "The buffer supplied is null");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
            if (r14.f == false) goto L_0x005d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003f, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r14.h = r15;
            r14.l++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            r14.i = r14.g.schedulePeriodically(r14, r14.c, r14.c, r14.d);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x005d, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r14.h = r15;
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
            r14.actual.onError(r15);
            dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0071, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNext(T r15) {
            /*
                r14 = this;
                monitor-enter(r14)
                U r0 = r14.h     // Catch:{ all -> 0x0072 }
                if (r0 != 0) goto L_0x0007
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                return
            L_0x0007:
                r0.add(r15)     // Catch:{ all -> 0x0072 }
                int r15 = r0.size()     // Catch:{ all -> 0x0072 }
                int r1 = r14.e     // Catch:{ all -> 0x0072 }
                if (r15 >= r1) goto L_0x0014
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                return
            L_0x0014:
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                boolean r15 = r14.f
                r1 = 1
                if (r15 == 0) goto L_0x0029
                r15 = 0
                r14.h = r15
                long r3 = r14.k
                long r5 = r3 + r1
                r14.k = r5
                io.reactivex.disposables.Disposable r15 = r14.i
                r15.dispose()
            L_0x0029:
                r15 = 0
                r14.fastPathOrderedEmit(r0, r15, r14)
                java.util.concurrent.Callable<U> r15 = r14.b     // Catch:{ Throwable -> 0x0065 }
                java.lang.Object r15 = r15.call()     // Catch:{ Throwable -> 0x0065 }
                java.lang.String r0 = "The buffer supplied is null"
                java.lang.Object r15 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r15, r0)     // Catch:{ Throwable -> 0x0065 }
                java.util.Collection r15 = (java.util.Collection) r15     // Catch:{ Throwable -> 0x0065 }
                boolean r0 = r14.f
                if (r0 == 0) goto L_0x005d
                monitor-enter(r14)
                r14.h = r15     // Catch:{ all -> 0x005a }
                long r3 = r14.l     // Catch:{ all -> 0x005a }
                r15 = 0
                long r5 = r3 + r1
                r14.l = r5     // Catch:{ all -> 0x005a }
                monitor-exit(r14)     // Catch:{ all -> 0x005a }
                io.reactivex.Scheduler$Worker r7 = r14.g
                long r9 = r14.c
                long r11 = r14.c
                java.util.concurrent.TimeUnit r13 = r14.d
                r8 = r14
                io.reactivex.disposables.Disposable r15 = r7.schedulePeriodically(r8, r9, r11, r13)
                r14.i = r15
                goto L_0x0061
            L_0x005a:
                r15 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x005a }
                throw r15
            L_0x005d:
                monitor-enter(r14)
                r14.h = r15     // Catch:{ all -> 0x0062 }
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
                io.reactivex.Observer r0 = r14.actual
                r0.onError(r15)
                r14.dispose()
                return
            L_0x0072:
                r15 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x0072 }
                throw r15
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableBufferTimed.BufferExactBoundedObserver.onNext(java.lang.Object):void");
        }

        public void onError(Throwable th) {
            synchronized (this) {
                this.h = null;
            }
            this.actual.onError(th);
            this.g.dispose();
        }

        public void onComplete() {
            U u;
            this.g.dispose();
            synchronized (this) {
                u = this.h;
                this.h = null;
            }
            this.queue.offer(u);
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(this.queue, this.actual, false, this, this);
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            observer.onNext(u);
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.j.dispose();
                this.g.dispose();
                synchronized (this) {
                    this.h = null;
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        public void run() {
            try {
                U u = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                synchronized (this) {
                    U u2 = this.h;
                    if (u2 != null) {
                        if (this.k == this.l) {
                            this.h = u;
                            fastPathOrderedEmit(u2, false, this);
                        }
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.actual.onError(th);
            }
        }
    }

    static final class BufferExactUnboundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        Disposable f;
        U g;
        final AtomicReference<Disposable> h = new AtomicReference<>();

        BufferExactUnboundedObserver(Observer<? super U> observer, Callable<U> callable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                try {
                    this.g = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null");
                    this.actual.onSubscribe(this);
                    if (!this.cancelled) {
                        Disposable schedulePeriodicallyDirect = this.e.schedulePeriodicallyDirect(this, this.c, this.c, this.d);
                        if (!this.h.compareAndSet(null, schedulePeriodicallyDirect)) {
                            schedulePeriodicallyDirect.dispose();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    EmptyDisposable.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                U u = this.g;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            synchronized (this) {
                this.g = null;
            }
            this.actual.onError(th);
            DisposableHelper.dispose(this.h);
        }

        public void onComplete() {
            U u;
            synchronized (this) {
                u = this.g;
                this.g = null;
            }
            if (u != null) {
                this.queue.offer(u);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.actual, false, this, this);
                }
            }
            DisposableHelper.dispose(this.h);
        }

        public void dispose() {
            DisposableHelper.dispose(this.h);
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.h.get() == DisposableHelper.DISPOSED;
        }

        public void run() {
            U u;
            try {
                U u2 = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                synchronized (this) {
                    u = this.g;
                    if (u != null) {
                        this.g = u2;
                    }
                }
                if (u == null) {
                    DisposableHelper.dispose(this.h);
                } else {
                    fastPathEmit(u, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.actual.onError(th);
                dispose();
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            this.actual.onNext(u);
        }
    }

    static final class BufferSkipBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final long d;
        final TimeUnit e;
        final Worker f;
        final List<U> g = new LinkedList();
        Disposable h;

        final class RemoveFromBuffer implements Runnable {
            private final U b;

            RemoveFromBuffer(U u) {
                this.b = u;
            }

            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.g.remove(this.b);
                }
                BufferSkipBoundedObserver.this.fastPathOrderedEmit(this.b, false, BufferSkipBoundedObserver.this.f);
            }
        }

        final class RemoveFromBufferEmit implements Runnable {
            private final U b;

            RemoveFromBufferEmit(U u) {
                this.b = u;
            }

            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.g.remove(this.b);
                }
                BufferSkipBoundedObserver.this.fastPathOrderedEmit(this.b, false, BufferSkipBoundedObserver.this.f);
            }
        }

        BufferSkipBoundedObserver(Observer<? super U> observer, Callable<U> callable, long j, long j2, TimeUnit timeUnit, Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j;
            this.d = j2;
            this.e = timeUnit;
            this.f = worker;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null");
                    this.g.add(collection);
                    this.actual.onSubscribe(this);
                    this.f.schedulePeriodically(this, this.d, this.d, this.e);
                    this.f.schedule(new RemoveFromBufferEmit(collection), this.c, this.e);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.actual);
                    this.f.dispose();
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                for (U add : this.g) {
                    add.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            this.done = true;
            a();
            this.actual.onError(th);
            this.f.dispose();
        }

        public void onComplete() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(this.g);
                this.g.clear();
            }
            for (Collection offer : arrayList) {
                this.queue.offer(offer);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(this.queue, this.actual, false, this.f, this);
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                this.h.dispose();
                this.f.dispose();
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            synchronized (this) {
                this.g.clear();
            }
        }

        public void run() {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                    synchronized (this) {
                        if (!this.cancelled) {
                            this.g.add(collection);
                            this.f.schedule(new RemoveFromBuffer(collection), this.c, this.e);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.actual.onError(th);
                    dispose();
                }
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            observer.onNext(u);
        }
    }

    public ObservableBufferTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, Callable<U> callable, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = callable;
        this.f = i;
        this.g = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> observer) {
        if (this.a == this.b && this.f == Integer.MAX_VALUE) {
            ObservableSource observableSource = this.source;
            BufferExactUnboundedObserver bufferExactUnboundedObserver = new BufferExactUnboundedObserver(new SerializedObserver(observer), this.e, this.a, this.c, this.d);
            observableSource.subscribe(bufferExactUnboundedObserver);
            return;
        }
        Worker createWorker = this.d.createWorker();
        if (this.a == this.b) {
            ObservableSource observableSource2 = this.source;
            BufferExactBoundedObserver bufferExactBoundedObserver = new BufferExactBoundedObserver(new SerializedObserver(observer), this.e, this.a, this.c, this.f, this.g, createWorker);
            observableSource2.subscribe(bufferExactBoundedObserver);
            return;
        }
        ObservableSource observableSource3 = this.source;
        BufferSkipBoundedObserver bufferSkipBoundedObserver = new BufferSkipBoundedObserver(new SerializedObserver(observer), this.e, this.a, this.b, this.c, createWorker);
        observableSource3.subscribe(bufferSkipBoundedObserver);
    }
}
