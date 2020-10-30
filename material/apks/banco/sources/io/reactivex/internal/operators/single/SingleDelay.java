package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.TimeUnit;

public final class SingleDelay<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final long b;
    final TimeUnit c;
    final Scheduler d;

    final class Delay implements SingleObserver<T> {
        final SingleObserver<? super T> a;
        private final SequentialDisposable c;

        final class OnError implements Runnable {
            private final Throwable b;

            OnError(Throwable th) {
                this.b = th;
            }

            public void run() {
                Delay.this.a.onError(this.b);
            }
        }

        final class OnSuccess implements Runnable {
            private final T b;

            OnSuccess(T t) {
                this.b = t;
            }

            public void run() {
                Delay.this.a.onSuccess(this.b);
            }
        }

        Delay(SequentialDisposable sequentialDisposable, SingleObserver<? super T> singleObserver) {
            this.c = sequentialDisposable;
            this.a = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.c.replace(disposable);
        }

        public void onSuccess(T t) {
            this.c.replace(SingleDelay.this.d.scheduleDirect(new OnSuccess(t), SingleDelay.this.b, SingleDelay.this.c));
        }

        public void onError(Throwable th) {
            this.c.replace(SingleDelay.this.d.scheduleDirect(new OnError(th), 0, SingleDelay.this.c));
        }
    }

    public SingleDelay(SingleSource<? extends T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.a = singleSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        singleObserver.onSubscribe(sequentialDisposable);
        this.a.subscribe(new Delay(sequentialDisposable, singleObserver));
    }
}
