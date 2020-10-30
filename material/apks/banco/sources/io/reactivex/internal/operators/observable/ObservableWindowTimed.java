package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowTimed<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final long e;
    final int f;
    final boolean g;

    static final class WindowExactBoundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final int e;
        final boolean f;
        final long g;
        final Worker h;
        long i;
        long j;
        Disposable k;
        UnicastSubject<T> l;
        volatile boolean m;
        final AtomicReference<Disposable> n = new AtomicReference<>();

        static final class ConsumerIndexHolder implements Runnable {
            final long a;
            final WindowExactBoundedObserver<?> b;

            ConsumerIndexHolder(long j, WindowExactBoundedObserver<?> windowExactBoundedObserver) {
                this.a = j;
                this.b = windowExactBoundedObserver;
            }

            public void run() {
                WindowExactBoundedObserver<?> windowExactBoundedObserver = this.b;
                if (!windowExactBoundedObserver.cancelled) {
                    windowExactBoundedObserver.queue.offer(this);
                } else {
                    windowExactBoundedObserver.m = true;
                    windowExactBoundedObserver.a();
                }
                if (windowExactBoundedObserver.enter()) {
                    windowExactBoundedObserver.b();
                }
            }
        }

        WindowExactBoundedObserver(Observer<? super Observable<T>> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, long j3, boolean z) {
            super(observer, new MpscLinkedQueue());
            this.b = j2;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i2;
            this.g = j3;
            this.f = z;
            if (z) {
                this.h = scheduler.createWorker();
            } else {
                this.h = null;
            }
        }

        public void onSubscribe(Disposable disposable) {
            Disposable disposable2;
            if (DisposableHelper.validate(this.k, disposable)) {
                this.k = disposable;
                Observer observer = this.actual;
                observer.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastSubject<T> create = UnicastSubject.create(this.e);
                    this.l = create;
                    observer.onNext(create);
                    ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.j, this);
                    if (this.f) {
                        disposable2 = this.h.schedulePeriodically(consumerIndexHolder, this.b, this.b, this.c);
                    } else {
                        disposable2 = this.d.schedulePeriodicallyDirect(consumerIndexHolder, this.b, this.b, this.c);
                    }
                    DisposableHelper.replace(this.n, disposable2);
                }
            }
        }

        public void onNext(T t) {
            if (!this.m) {
                if (fastEnter()) {
                    UnicastSubject<T> unicastSubject = this.l;
                    unicastSubject.onNext(t);
                    long j2 = this.i + 1;
                    if (j2 >= this.g) {
                        this.j++;
                        this.i = 0;
                        unicastSubject.onComplete();
                        UnicastSubject<T> create = UnicastSubject.create(this.e);
                        this.l = create;
                        this.actual.onNext(create);
                        if (this.f) {
                            ((Disposable) this.n.get()).dispose();
                            DisposableHelper.replace(this.n, this.h.schedulePeriodically(new ConsumerIndexHolder(this.j, this), this.b, this.b, this.c));
                        }
                    } else {
                        this.i = j2;
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onError(th);
            a();
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onComplete();
            a();
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            DisposableHelper.dispose(this.n);
            Worker worker = this.h;
            if (worker != null) {
                worker.dispose();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.actual;
            UnicastSubject<T> unicastSubject = this.l;
            int i2 = 1;
            while (!this.m) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof ConsumerIndexHolder;
                if (z && (z2 || z3)) {
                    this.l = null;
                    mpscLinkedQueue.clear();
                    a();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                    } else {
                        unicastSubject.onComplete();
                    }
                    return;
                } else if (z2) {
                    i2 = leave(-i2);
                    if (i2 == 0) {
                        return;
                    }
                } else if (z3) {
                    ConsumerIndexHolder consumerIndexHolder = (ConsumerIndexHolder) poll;
                    if (this.f || this.j == consumerIndexHolder.a) {
                        unicastSubject.onComplete();
                        this.i = 0;
                        unicastSubject = UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        observer.onNext(unicastSubject);
                    }
                } else {
                    unicastSubject.onNext(NotificationLite.getValue(poll));
                    long j2 = this.i + 1;
                    if (j2 >= this.g) {
                        this.j++;
                        this.i = 0;
                        unicastSubject.onComplete();
                        unicastSubject = UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        this.actual.onNext(unicastSubject);
                        if (this.f) {
                            Disposable disposable = (Disposable) this.n.get();
                            disposable.dispose();
                            Disposable schedulePeriodically = this.h.schedulePeriodically(new ConsumerIndexHolder(this.j, this), this.b, this.b, this.c);
                            if (!this.n.compareAndSet(disposable, schedulePeriodically)) {
                                schedulePeriodically.dispose();
                            }
                        }
                    } else {
                        this.i = j2;
                    }
                }
            }
            this.k.dispose();
            mpscLinkedQueue.clear();
            a();
        }
    }

    static final class WindowExactUnboundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Observer<T>, Disposable, Runnable {
        static final Object i = new Object();
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final int e;
        Disposable f;
        UnicastSubject<T> g;
        final AtomicReference<Disposable> h = new AtomicReference<>();
        volatile boolean j;

        WindowExactUnboundedObserver(Observer<? super Observable<T>> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
            super(observer, new MpscLinkedQueue());
            this.b = j2;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.g = UnicastSubject.create(this.e);
                Observer observer = this.actual;
                observer.onSubscribe(this);
                observer.onNext(this.g);
                if (!this.cancelled) {
                    DisposableHelper.replace(this.h, this.d.schedulePeriodicallyDirect(this, this.b, this.b, this.c));
                }
            }
        }

        public void onNext(T t) {
            if (!this.j) {
                if (fastEnter()) {
                    this.g.onNext(t);
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            a();
            this.actual.onError(th);
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            a();
            this.actual.onComplete();
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            DisposableHelper.dispose(this.h);
        }

        public void run() {
            if (this.cancelled) {
                this.j = true;
                a();
            }
            this.queue.offer(i);
            if (enter()) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.actual;
            UnicastSubject<T> unicastSubject = this.g;
            int i2 = 1;
            while (true) {
                boolean z = this.j;
                boolean z2 = this.done;
                Object poll = mpscLinkedQueue.poll();
                if (!z2 || !(poll == null || poll == i)) {
                    if (poll == null) {
                        i2 = leave(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else if (poll == i) {
                        unicastSubject.onComplete();
                        if (!z) {
                            unicastSubject = UnicastSubject.create(this.e);
                            this.g = unicastSubject;
                            observer.onNext(unicastSubject);
                        } else {
                            this.f.dispose();
                        }
                    } else {
                        unicastSubject.onNext(NotificationLite.getValue(poll));
                    }
                }
            }
            this.g = null;
            mpscLinkedQueue.clear();
            a();
            Throwable th = this.error;
            if (th != null) {
                unicastSubject.onError(th);
            } else {
                unicastSubject.onComplete();
            }
        }
    }

    static final class WindowSkipObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable, Runnable {
        final long b;
        final long c;
        final TimeUnit d;
        final Worker e;
        final int f;
        final List<UnicastSubject<T>> g = new LinkedList();
        Disposable h;
        volatile boolean i;

        final class CompletionTask implements Runnable {
            private final UnicastSubject<T> b;

            CompletionTask(UnicastSubject<T> unicastSubject) {
                this.b = unicastSubject;
            }

            public void run() {
                WindowSkipObserver.this.a(this.b);
            }
        }

        static final class SubjectWork<T> {
            final UnicastSubject<T> a;
            final boolean b;

            SubjectWork(UnicastSubject<T> unicastSubject, boolean z) {
                this.a = unicastSubject;
                this.b = z;
            }
        }

        WindowSkipObserver(Observer<? super Observable<T>> observer, long j, long j2, TimeUnit timeUnit, Worker worker, int i2) {
            super(observer, new MpscLinkedQueue());
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = worker;
            this.f = i2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.actual.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastSubject create = UnicastSubject.create(this.f);
                    this.g.add(create);
                    this.actual.onNext(create);
                    this.e.schedule(new CompletionTask(create), this.b, this.d);
                    this.e.schedulePeriodically(this, this.c, this.c, this.d);
                }
            }
        }

        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastSubject onNext : this.g) {
                    onNext.onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            b();
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onError(th);
            a();
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onComplete();
            a();
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.e.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a(UnicastSubject<T> unicastSubject) {
            this.queue.offer(new SubjectWork(unicastSubject, false));
            if (enter()) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.actual;
            List<UnicastSubject<T>> list = this.g;
            int i2 = 1;
            while (!this.i) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof SubjectWork;
                if (z && (z2 || z3)) {
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastSubject onError : list) {
                            onError.onError(th);
                        }
                    } else {
                        for (UnicastSubject onComplete : list) {
                            onComplete.onComplete();
                        }
                    }
                    a();
                    list.clear();
                    return;
                } else if (z2) {
                    i2 = leave(-i2);
                    if (i2 == 0) {
                        return;
                    }
                } else if (z3) {
                    SubjectWork subjectWork = (SubjectWork) poll;
                    if (!subjectWork.b) {
                        list.remove(subjectWork.a);
                        subjectWork.a.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.i = true;
                        }
                    } else if (!this.cancelled) {
                        UnicastSubject create = UnicastSubject.create(this.f);
                        list.add(create);
                        observer.onNext(create);
                        this.e.schedule(new CompletionTask(create), this.b, this.d);
                    }
                } else {
                    for (UnicastSubject onNext : list) {
                        onNext.onNext(poll);
                    }
                }
            }
            this.h.dispose();
            a();
            mpscLinkedQueue.clear();
            list.clear();
        }

        public void run() {
            SubjectWork subjectWork = new SubjectWork(UnicastSubject.create(this.f), true);
            if (!this.cancelled) {
                this.queue.offer(subjectWork);
            }
            if (enter()) {
                b();
            }
        }
    }

    public ObservableWindowTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = j3;
        this.f = i;
        this.g = z;
    }

    public void subscribeActual(Observer<? super Observable<T>> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.a != this.b) {
            ObservableSource observableSource = this.source;
            WindowSkipObserver windowSkipObserver = new WindowSkipObserver(serializedObserver, this.a, this.b, this.c, this.d.createWorker(), this.f);
            observableSource.subscribe(windowSkipObserver);
        } else if (this.e == Long.MAX_VALUE) {
            ObservableSource observableSource2 = this.source;
            WindowExactUnboundedObserver windowExactUnboundedObserver = new WindowExactUnboundedObserver(serializedObserver, this.a, this.c, this.d, this.f);
            observableSource2.subscribe(windowExactUnboundedObserver);
        } else {
            ObservableSource observableSource3 = this.source;
            WindowExactBoundedObserver windowExactBoundedObserver = new WindowExactBoundedObserver(serializedObserver, this.a, this.c, this.d, this.f, this.e, this.g);
            observableSource3.subscribe(windowExactBoundedObserver);
        }
    }
}
