package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleTimed<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final boolean d;

    static final class SampleTimedEmitLast<T> extends SampleTimedObserver<T> {
        private static final long serialVersionUID = -7139995637533111443L;
        final AtomicInteger a = new AtomicInteger(1);

        SampleTimedEmitLast(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            c();
            if (this.a.decrementAndGet() == 0) {
                this.b.onComplete();
            }
        }

        public void run() {
            if (this.a.incrementAndGet() == 2) {
                c();
                if (this.a.decrementAndGet() == 0) {
                    this.b.onComplete();
                }
            }
        }
    }

    static final class SampleTimedNoLast<T> extends SampleTimedObserver<T> {
        private static final long serialVersionUID = -7139995637533111443L;

        SampleTimedNoLast(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, j, timeUnit, scheduler);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.onComplete();
        }

        public void run() {
            c();
        }
    }

    static abstract class SampleTimedObserver<T> extends AtomicReference<T> implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -3517602651313910099L;
        final Observer<? super T> b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final AtomicReference<Disposable> f = new AtomicReference<>();
        Disposable g;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        SampleTimedObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.b = observer;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.b.onSubscribe(this);
                DisposableHelper.replace(this.f, this.e.schedulePeriodicallyDirect(this, this.c, this.c, this.d));
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable th) {
            b();
            this.b.onError(th);
        }

        public void onComplete() {
            b();
            a();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            DisposableHelper.dispose(this.f);
        }

        public void dispose() {
            b();
            this.g.dispose();
        }

        public boolean isDisposed() {
            return this.g.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Object andSet = getAndSet(null);
            if (andSet != null) {
                this.b.onNext(andSet);
            }
        }
    }

    public ObservableSampleTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.d) {
            ObservableSource observableSource = this.source;
            SampleTimedEmitLast sampleTimedEmitLast = new SampleTimedEmitLast(serializedObserver, this.a, this.b, this.c);
            observableSource.subscribe(sampleTimedEmitLast);
            return;
        }
        ObservableSource observableSource2 = this.source;
        SampleTimedNoLast sampleTimedNoLast = new SampleTimedNoLast(serializedObserver, this.a, this.b, this.c);
        observableSource2.subscribe(sampleTimedNoLast);
    }
}
