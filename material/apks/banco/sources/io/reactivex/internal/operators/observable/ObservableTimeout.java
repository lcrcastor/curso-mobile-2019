package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ObserverFullArbiter;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.FullArbiterObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeout<T, U, V> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<U> a;
    final Function<? super T, ? extends ObservableSource<V>> b;
    final ObservableSource<? extends T> c;

    interface OnTimeout {
        void a(long j);

        void a(Throwable th);
    }

    static final class TimeoutInnerObserver<T, U, V> extends DisposableObserver<Object> {
        final OnTimeout a;
        final long b;
        boolean c;

        TimeoutInnerObserver(OnTimeout onTimeout, long j) {
            this.a = onTimeout;
            this.b = j;
        }

        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
                dispose();
                this.a.a(this.b);
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.a(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.a(this.b);
            }
        }
    }

    static final class TimeoutObserver<T, U, V> extends AtomicReference<Disposable> implements Observer<T>, Disposable, OnTimeout {
        private static final long serialVersionUID = 2672739326310051084L;
        final Observer<? super T> a;
        final ObservableSource<U> b;
        final Function<? super T, ? extends ObservableSource<V>> c;
        Disposable d;
        volatile long e;

        TimeoutObserver(Observer<? super T> observer, ObservableSource<U> observableSource, Function<? super T, ? extends ObservableSource<V>> function) {
            this.a = observer;
            this.b = observableSource;
            this.c = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                Observer<? super T> observer = this.a;
                ObservableSource<U> observableSource = this.b;
                if (observableSource != null) {
                    TimeoutInnerObserver timeoutInnerObserver = new TimeoutInnerObserver(this, 0);
                    if (compareAndSet(null, timeoutInnerObserver)) {
                        observer.onSubscribe(this);
                        observableSource.subscribe(timeoutInnerObserver);
                        return;
                    }
                    return;
                }
                observer.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.e + 1;
            this.e = j;
            this.a.onNext(t);
            Disposable disposable = (Disposable) get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply(t), "The ObservableSource returned is null");
                TimeoutInnerObserver timeoutInnerObserver = new TimeoutInnerObserver(this, j);
                if (compareAndSet(disposable, timeoutInnerObserver)) {
                    observableSource.subscribe(timeoutInnerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.a.onError(th);
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this);
            this.a.onError(th);
        }

        public void onComplete() {
            DisposableHelper.dispose(this);
            this.a.onComplete();
        }

        public void dispose() {
            if (DisposableHelper.dispose(this)) {
                this.d.dispose();
            }
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void a(long j) {
            if (j == this.e) {
                dispose();
                this.a.onError(new TimeoutException());
            }
        }

        public void a(Throwable th) {
            this.d.dispose();
            this.a.onError(th);
        }
    }

    static final class TimeoutOtherObserver<T, U, V> extends AtomicReference<Disposable> implements Observer<T>, Disposable, OnTimeout {
        private static final long serialVersionUID = -1957813281749686898L;
        final Observer<? super T> a;
        final ObservableSource<U> b;
        final Function<? super T, ? extends ObservableSource<V>> c;
        final ObservableSource<? extends T> d;
        final ObserverFullArbiter<T> e;
        Disposable f;
        boolean g;
        volatile long h;

        TimeoutOtherObserver(Observer<? super T> observer, ObservableSource<U> observableSource, Function<? super T, ? extends ObservableSource<V>> function, ObservableSource<? extends T> observableSource2) {
            this.a = observer;
            this.b = observableSource;
            this.c = function;
            this.d = observableSource2;
            this.e = new ObserverFullArbiter<>(observer, this, 8);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.e.setDisposable(disposable);
                Observer<? super T> observer = this.a;
                ObservableSource<U> observableSource = this.b;
                if (observableSource != null) {
                    TimeoutInnerObserver timeoutInnerObserver = new TimeoutInnerObserver(this, 0);
                    if (compareAndSet(null, timeoutInnerObserver)) {
                        observer.onSubscribe(this.e);
                        observableSource.subscribe(timeoutInnerObserver);
                        return;
                    }
                    return;
                }
                observer.onSubscribe(this.e);
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                long j = this.h + 1;
                this.h = j;
                if (this.e.onNext(t, this.f)) {
                    Disposable disposable = (Disposable) get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.apply(t), "The ObservableSource returned is null");
                        TimeoutInnerObserver timeoutInnerObserver = new TimeoutInnerObserver(this, j);
                        if (compareAndSet(disposable, timeoutInnerObserver)) {
                            observableSource.subscribe(timeoutInnerObserver);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.a.onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            dispose();
            this.e.onError(th, this.f);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                dispose();
                this.e.onComplete(this.f);
            }
        }

        public void dispose() {
            if (DisposableHelper.dispose(this)) {
                this.f.dispose();
            }
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        public void a(long j) {
            if (j == this.h) {
                dispose();
                this.d.subscribe(new FullArbiterObserver(this.e));
            }
        }

        public void a(Throwable th) {
            this.f.dispose();
            this.a.onError(th);
        }
    }

    public ObservableTimeout(ObservableSource<T> observableSource, ObservableSource<U> observableSource2, Function<? super T, ? extends ObservableSource<V>> function, ObservableSource<? extends T> observableSource3) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = observableSource3;
    }

    public void subscribeActual(Observer<? super T> observer) {
        if (this.c == null) {
            this.source.subscribe(new TimeoutObserver(new SerializedObserver(observer), this.a, this.b));
        } else {
            this.source.subscribe(new TimeoutOtherObserver(observer, this.a, this.b, this.c));
        }
    }
}
