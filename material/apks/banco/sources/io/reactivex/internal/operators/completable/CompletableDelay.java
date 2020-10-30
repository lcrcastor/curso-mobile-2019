package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

public final class CompletableDelay extends Completable {
    final CompletableSource a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final boolean e;

    final class Delay implements CompletableObserver {
        final CompletableObserver a;
        private final CompositeDisposable c;

        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                Delay.this.a.onComplete();
            }
        }

        final class OnError implements Runnable {
            private final Throwable b;

            OnError(Throwable th) {
                this.b = th;
            }

            public void run() {
                Delay.this.a.onError(this.b);
            }
        }

        Delay(CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.c = compositeDisposable;
            this.a = completableObserver;
        }

        public void onComplete() {
            this.c.add(CompletableDelay.this.d.scheduleDirect(new OnComplete(), CompletableDelay.this.b, CompletableDelay.this.c));
        }

        public void onError(Throwable th) {
            this.c.add(CompletableDelay.this.d.scheduleDirect(new OnError(th), CompletableDelay.this.e ? CompletableDelay.this.b : 0, CompletableDelay.this.c));
        }

        public void onSubscribe(Disposable disposable) {
            this.c.add(disposable);
            this.a.onSubscribe(this.c);
        }
    }

    public CompletableDelay(CompletableSource completableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        this.a = completableSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new Delay(new CompositeDisposable(), completableObserver));
    }
}
