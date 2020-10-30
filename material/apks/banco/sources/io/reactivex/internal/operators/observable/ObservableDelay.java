package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

public final class ObservableDelay<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    static final class DelayObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        final boolean e;
        Disposable f;

        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelayObserver.this.a.onComplete();
                } finally {
                    DelayObserver.this.d.dispose();
                }
            }
        }

        final class OnError implements Runnable {
            private final Throwable b;

            OnError(Throwable th) {
                this.b = th;
            }

            public void run() {
                try {
                    DelayObserver.this.a.onError(this.b);
                } finally {
                    DelayObserver.this.d.dispose();
                }
            }
        }

        final class OnNext implements Runnable {
            private final T b;

            OnNext(T t) {
                this.b = t;
            }

            public void run() {
                DelayObserver.this.a.onNext(this.b);
            }
        }

        DelayObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
            this.e = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.d.schedule(new OnNext(t), this.b, this.c);
        }

        public void onError(Throwable th) {
            this.d.schedule(new OnError(th), this.e ? this.b : 0, this.c);
        }

        public void onComplete() {
            this.d.schedule(new OnComplete(), this.b, this.c);
        }

        public void dispose() {
            this.f.dispose();
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }

    public ObservableDelay(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        Observer<? super T> observer2;
        if (this.d) {
            observer2 = observer;
        } else {
            observer2 = new SerializedObserver<>(observer);
        }
        Worker createWorker = this.c.createWorker();
        ObservableSource observableSource = this.source;
        DelayObserver delayObserver = new DelayObserver(observer2, this.a, this.b, createWorker, this.d);
        observableSource.subscribe(delayObserver);
    }
}
