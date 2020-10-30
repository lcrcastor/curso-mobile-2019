package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CompletableTimeout extends Completable {
    final CompletableSource a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final CompletableSource e;

    final class DisposeTask implements Runnable {
        final CompositeDisposable a;
        final CompletableObserver b;
        private final AtomicBoolean d;

        final class DisposeObserver implements CompletableObserver {
            DisposeObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposeTask.this.a.add(disposable);
            }

            public void onError(Throwable th) {
                DisposeTask.this.a.dispose();
                DisposeTask.this.b.onError(th);
            }

            public void onComplete() {
                DisposeTask.this.a.dispose();
                DisposeTask.this.b.onComplete();
            }
        }

        DisposeTask(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.d = atomicBoolean;
            this.a = compositeDisposable;
            this.b = completableObserver;
        }

        public void run() {
            if (this.d.compareAndSet(false, true)) {
                this.a.clear();
                if (CompletableTimeout.this.e == null) {
                    this.b.onError(new TimeoutException());
                } else {
                    CompletableTimeout.this.e.subscribe(new DisposeObserver());
                }
            }
        }
    }

    static final class TimeOutObserver implements CompletableObserver {
        private final CompositeDisposable a;
        private final AtomicBoolean b;
        private final CompletableObserver c;

        TimeOutObserver(CompositeDisposable compositeDisposable, AtomicBoolean atomicBoolean, CompletableObserver completableObserver) {
            this.a = compositeDisposable;
            this.b = atomicBoolean;
            this.c = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.add(disposable);
        }

        public void onError(Throwable th) {
            if (this.b.compareAndSet(false, true)) {
                this.a.dispose();
                this.c.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (this.b.compareAndSet(false, true)) {
                this.a.dispose();
                this.c.onComplete();
            }
        }
    }

    public CompletableTimeout(CompletableSource completableSource, long j, TimeUnit timeUnit, Scheduler scheduler, CompletableSource completableSource2) {
        this.a = completableSource;
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = completableSource2;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        compositeDisposable.add(this.d.scheduleDirect(new DisposeTask(atomicBoolean, compositeDisposable, completableObserver), this.b, this.c));
        this.a.subscribe(new TimeOutObserver(compositeDisposable, atomicBoolean, completableObserver));
    }
}
