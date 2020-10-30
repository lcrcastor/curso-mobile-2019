package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySupplier<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final Callable<? extends ObservableSource<B>> a;
    final int b;

    static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
        final WindowBoundaryMainObserver<T, B> a;
        boolean b;

        WindowBoundaryInnerObserver(WindowBoundaryMainObserver<T, B> windowBoundaryMainObserver) {
            this.a = windowBoundaryMainObserver;
        }

        public void onNext(B b2) {
            if (!this.b) {
                this.b = true;
                dispose();
                this.a.b();
            }
        }

        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.onComplete();
            }
        }
    }

    static final class WindowBoundaryMainObserver<T, B> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        static final Object g = new Object();
        final Callable<? extends ObservableSource<B>> b;
        final int c;
        Disposable d;
        final AtomicReference<Disposable> e = new AtomicReference<>();
        UnicastSubject<T> f;
        final AtomicLong h = new AtomicLong();

        WindowBoundaryMainObserver(Observer<? super Observable<T>> observer, Callable<? extends ObservableSource<B>> callable, int i) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = i;
            this.h.lazySet(1);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                Observer observer = this.actual;
                observer.onSubscribe(this);
                if (!this.cancelled) {
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.call(), "The first window ObservableSource supplied is null");
                        UnicastSubject<T> create = UnicastSubject.create(this.c);
                        this.f = create;
                        observer.onNext(create);
                        WindowBoundaryInnerObserver windowBoundaryInnerObserver = new WindowBoundaryInnerObserver(this);
                        if (this.e.compareAndSet(null, windowBoundaryInnerObserver)) {
                            this.h.getAndIncrement();
                            observableSource.subscribe(windowBoundaryInnerObserver);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        disposable.dispose();
                        observer.onError(th);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (fastEnter()) {
                this.f.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            if (this.h.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.e);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    a();
                }
                if (this.h.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.e);
                }
                this.actual.onComplete();
            }
        }

        public void dispose() {
            this.cancelled = true;
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.actual;
            UnicastSubject<T> unicastSubject = this.f;
            int i = 1;
            while (true) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    DisposableHelper.dispose(this.e);
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                    } else {
                        unicastSubject.onComplete();
                    }
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll == g) {
                    unicastSubject.onComplete();
                    if (this.h.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.e);
                        return;
                    } else if (!this.cancelled) {
                        try {
                            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.call(), "The ObservableSource supplied is null");
                            UnicastSubject<T> create = UnicastSubject.create(this.c);
                            this.h.getAndIncrement();
                            this.f = create;
                            observer.onNext(create);
                            WindowBoundaryInnerObserver windowBoundaryInnerObserver = new WindowBoundaryInnerObserver(this);
                            if (this.e.compareAndSet(this.e.get(), windowBoundaryInnerObserver)) {
                                observableSource.subscribe(windowBoundaryInnerObserver);
                            }
                            unicastSubject = create;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            DisposableHelper.dispose(this.e);
                            observer.onError(th2);
                            return;
                        }
                    }
                } else {
                    unicastSubject.onNext(NotificationLite.getValue(poll));
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.queue.offer(g);
            if (enter()) {
                a();
            }
        }
    }

    public ObservableWindowBoundarySupplier(ObservableSource<T> observableSource, Callable<? extends ObservableSource<B>> callable, int i) {
        super(observableSource);
        this.a = callable;
        this.b = i;
    }

    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new WindowBoundaryMainObserver(new SerializedObserver(observer), this.a, this.b));
    }
}
