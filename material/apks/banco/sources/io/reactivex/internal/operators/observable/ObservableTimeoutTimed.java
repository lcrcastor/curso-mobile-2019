package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ObserverFullArbiter;
import io.reactivex.internal.observers.FullArbiterObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeoutTimed<T> extends AbstractObservableWithUpstream<T, T> {
    static final Disposable e = new EmptyDisposable();
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final ObservableSource<? extends T> d;

    static final class EmptyDisposable implements Disposable {
        public void dispose() {
        }

        public boolean isDisposed() {
            return true;
        }

        EmptyDisposable() {
        }
    }

    static final class TimeoutTimedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8387234228317808253L;
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        Disposable e;
        volatile long f;
        volatile boolean g;

        final class TimeoutTask implements Runnable {
            private final long b;

            TimeoutTask(long j) {
                this.b = j;
            }

            public void run() {
                if (this.b == TimeoutTimedObserver.this.f) {
                    TimeoutTimedObserver.this.g = true;
                    TimeoutTimedObserver.this.e.dispose();
                    DisposableHelper.dispose(TimeoutTimedObserver.this);
                    TimeoutTimedObserver.this.a.onError(new TimeoutException());
                    TimeoutTimedObserver.this.d.dispose();
                }
            }
        }

        TimeoutTimedObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
                a(0);
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                long j = this.f + 1;
                this.f = j;
                this.a.onNext(t);
                a(j);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            Disposable disposable = (Disposable) get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (compareAndSet(disposable, ObservableTimeoutTimed.e)) {
                DisposableHelper.replace(this, this.d.schedule(new TimeoutTask(j), this.b, this.c));
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            this.a.onError(th);
            dispose();
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                this.a.onComplete();
                dispose();
            }
        }

        public void dispose() {
            this.e.dispose();
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }

    static final class TimeoutTimedOtherObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -4619702551964128179L;
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        final ObservableSource<? extends T> e;
        Disposable f;
        final ObserverFullArbiter<T> g;
        volatile long h;
        volatile boolean i;

        final class SubscribeNext implements Runnable {
            private final long b;

            SubscribeNext(long j) {
                this.b = j;
            }

            public void run() {
                if (this.b == TimeoutTimedOtherObserver.this.h) {
                    TimeoutTimedOtherObserver.this.i = true;
                    TimeoutTimedOtherObserver.this.f.dispose();
                    DisposableHelper.dispose(TimeoutTimedOtherObserver.this);
                    TimeoutTimedOtherObserver.this.a();
                    TimeoutTimedOtherObserver.this.d.dispose();
                }
            }
        }

        TimeoutTimedOtherObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker, ObservableSource<? extends T> observableSource) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = observableSource;
            this.g = new ObserverFullArbiter<>(observer, this, 8);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                if (this.g.setDisposable(disposable)) {
                    this.a.onSubscribe(this.g);
                    a(0);
                }
            }
        }

        public void onNext(T t) {
            if (!this.i) {
                long j = this.h + 1;
                this.h = j;
                if (this.g.onNext(t, this.f)) {
                    a(j);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            Disposable disposable = (Disposable) get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (compareAndSet(disposable, ObservableTimeoutTimed.e)) {
                DisposableHelper.replace(this, this.d.schedule(new SubscribeNext(j), this.b, this.c));
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.e.subscribe(new FullArbiterObserver(this.g));
        }

        public void onError(Throwable th) {
            if (this.i) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.i = true;
            this.g.onError(th, this.f);
            this.d.dispose();
        }

        public void onComplete() {
            if (!this.i) {
                this.i = true;
                this.g.onComplete(this.f);
                this.d.dispose();
            }
        }

        public void dispose() {
            this.f.dispose();
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }

    public ObservableTimeoutTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, ObservableSource<? extends T> observableSource2) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        if (this.d == null) {
            ObservableSource observableSource = this.source;
            TimeoutTimedObserver timeoutTimedObserver = new TimeoutTimedObserver(new SerializedObserver(observer), this.a, this.b, this.c.createWorker());
            observableSource.subscribe(timeoutTimedObserver);
            return;
        }
        ObservableSource observableSource2 = this.source;
        TimeoutTimedOtherObserver timeoutTimedOtherObserver = new TimeoutTimedOtherObserver(observer, this.a, this.b, this.c.createWorker(), this.d);
        observableSource2.subscribe(timeoutTimedOtherObserver);
    }
}
