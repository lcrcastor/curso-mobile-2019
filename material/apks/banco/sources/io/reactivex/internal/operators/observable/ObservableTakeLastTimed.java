package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableTakeLastTimed<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final int e;
    final boolean f;

    static final class TakeLastTimedObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -5677354903406201275L;
        final Observer<? super T> a;
        final long b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final SpscLinkedArrayQueue<Object> f;
        final boolean g;
        Disposable h;
        volatile boolean i;
        Throwable j;

        TakeLastTimedObserver(Observer<? super T> observer, long j2, long j3, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
            this.a = observer;
            this.b = j2;
            this.c = j3;
            this.d = timeUnit;
            this.e = scheduler;
            this.f = new SpscLinkedArrayQueue<>(i2);
            this.g = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
            long now = this.e.now(this.d);
            long j2 = this.c;
            long j3 = this.b;
            boolean z = j3 == Long.MAX_VALUE;
            spscLinkedArrayQueue.offer(Long.valueOf(now), t);
            while (!spscLinkedArrayQueue.isEmpty()) {
                if (((Long) spscLinkedArrayQueue.peek()).longValue() <= now - j2 || (!z && ((long) (spscLinkedArrayQueue.size() >> 1)) > j3)) {
                    spscLinkedArrayQueue.poll();
                    spscLinkedArrayQueue.poll();
                } else {
                    return;
                }
            }
        }

        public void onError(Throwable th) {
            this.j = th;
            a();
        }

        public void onComplete() {
            a();
        }

        public void dispose() {
            if (!this.i) {
                this.i = true;
                this.h.dispose();
                if (compareAndSet(false, true)) {
                    this.f.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(false, true)) {
                Observer<? super T> observer = this.a;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
                boolean z = this.g;
                while (!this.i) {
                    if (!z) {
                        Throwable th = this.j;
                        if (th != null) {
                            spscLinkedArrayQueue.clear();
                            observer.onError(th);
                            return;
                        }
                    }
                    Object poll = spscLinkedArrayQueue.poll();
                    if (poll == null) {
                        Throwable th2 = this.j;
                        if (th2 != null) {
                            observer.onError(th2);
                        } else {
                            observer.onComplete();
                        }
                        return;
                    }
                    Object poll2 = spscLinkedArrayQueue.poll();
                    if (((Long) poll).longValue() >= this.e.now(this.d) - this.c) {
                        observer.onNext(poll2);
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }
    }

    public ObservableTakeLastTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = i;
        this.f = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        ObservableSource observableSource = this.source;
        TakeLastTimedObserver takeLastTimedObserver = new TakeLastTimedObserver(observer, this.a, this.b, this.c, this.d, this.e, this.f);
        observableSource.subscribe(takeLastTimedObserver);
    }
}
