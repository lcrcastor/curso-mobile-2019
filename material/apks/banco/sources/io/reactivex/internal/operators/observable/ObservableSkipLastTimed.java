package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSkipLastTimed<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;
    final int d;
    final boolean e;

    static final class SkipLastTimedObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -5677354903406201275L;
        final Observer<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final SpscLinkedArrayQueue<Object> e;
        final boolean f;
        Disposable g;
        volatile boolean h;
        volatile boolean i;
        Throwable j;

        SkipLastTimedObserver(Observer<? super T> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
            this.a = observer;
            this.b = j2;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = new SpscLinkedArrayQueue<>(i2);
            this.f = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.g, disposable)) {
                this.g = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.e.offer(Long.valueOf(this.d.now(this.c)), t);
            a();
        }

        public void onError(Throwable th) {
            this.j = th;
            this.i = true;
            a();
        }

        public void onComplete() {
            this.i = true;
            a();
        }

        public void dispose() {
            if (!this.h) {
                this.h = true;
                this.g.dispose();
                if (getAndIncrement() == 0) {
                    this.e.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.h;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                Observer<? super T> observer = this.a;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.e;
                boolean z = this.f;
                TimeUnit timeUnit = this.c;
                Scheduler scheduler = this.d;
                long j2 = this.b;
                int i2 = 1;
                while (!this.h) {
                    boolean z2 = this.i;
                    Long l = (Long) spscLinkedArrayQueue.peek();
                    boolean z3 = l == null;
                    long now = scheduler.now(timeUnit);
                    if (!z3 && l.longValue() > now - j2) {
                        z3 = true;
                    }
                    if (z2) {
                        if (!z) {
                            Throwable th = this.j;
                            if (th != null) {
                                this.e.clear();
                                observer.onError(th);
                                return;
                            } else if (z3) {
                                observer.onComplete();
                                return;
                            }
                        } else if (z3) {
                            Throwable th2 = this.j;
                            if (th2 != null) {
                                observer.onError(th2);
                            } else {
                                observer.onComplete();
                            }
                            return;
                        }
                    }
                    if (z3) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        spscLinkedArrayQueue.poll();
                        observer.onNext(spscLinkedArrayQueue.poll());
                    }
                }
                this.e.clear();
            }
        }
    }

    public ObservableSkipLastTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
        this.d = i;
        this.e = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        ObservableSource observableSource = this.source;
        SkipLastTimedObserver skipLastTimedObserver = new SkipLastTimedObserver(observer, this.a, this.b, this.c, this.d, this.e);
        observableSource.subscribe(skipLastTimedObserver);
    }
}
