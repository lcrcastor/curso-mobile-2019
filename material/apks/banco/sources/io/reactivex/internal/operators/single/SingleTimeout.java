package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SingleTimeout<T> extends Single<T> {
    final SingleSource<T> a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final SingleSource<? extends T> e;

    final class TimeoutDispose implements Runnable {
        final CompositeDisposable a;
        final SingleObserver<? super T> b;
        private final AtomicBoolean d;

        final class TimeoutObserver implements SingleObserver<T> {
            TimeoutObserver() {
            }

            public void onError(Throwable th) {
                TimeoutDispose.this.a.dispose();
                TimeoutDispose.this.b.onError(th);
            }

            public void onSubscribe(Disposable disposable) {
                TimeoutDispose.this.a.add(disposable);
            }

            public void onSuccess(T t) {
                TimeoutDispose.this.a.dispose();
                TimeoutDispose.this.b.onSuccess(t);
            }
        }

        TimeoutDispose(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, SingleObserver<? super T> singleObserver) {
            this.d = atomicBoolean;
            this.a = compositeDisposable;
            this.b = singleObserver;
        }

        public void run() {
            if (!this.d.compareAndSet(false, true)) {
                return;
            }
            if (SingleTimeout.this.e != null) {
                this.a.clear();
                SingleTimeout.this.e.subscribe(new TimeoutObserver());
                return;
            }
            this.a.dispose();
            this.b.onError(new TimeoutException());
        }
    }

    final class TimeoutObserver implements SingleObserver<T> {
        private final AtomicBoolean b;
        private final CompositeDisposable c;
        private final SingleObserver<? super T> d;

        TimeoutObserver(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, SingleObserver<? super T> singleObserver) {
            this.b = atomicBoolean;
            this.c = compositeDisposable;
            this.d = singleObserver;
        }

        public void onError(Throwable th) {
            if (this.b.compareAndSet(false, true)) {
                this.c.dispose();
                this.d.onError(th);
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.c.add(disposable);
        }

        public void onSuccess(T t) {
            if (this.b.compareAndSet(false, true)) {
                this.c.dispose();
                this.d.onSuccess(t);
            }
        }
    }

    public SingleTimeout(SingleSource<T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler, SingleSource<? extends T> singleSource2) {
        this.a = singleSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = singleSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        singleObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        compositeDisposable.add(this.d.scheduleDirect(new TimeoutDispose(atomicBoolean, compositeDisposable, singleObserver), this.b, this.c));
        this.a.subscribe(new TimeoutObserver(atomicBoolean, compositeDisposable, singleObserver));
    }
}
