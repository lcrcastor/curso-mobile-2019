package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableDebounce<T, U> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super T, ? extends ObservableSource<U>> a;

    static final class DebounceObserver<T, U> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final Function<? super T, ? extends ObservableSource<U>> b;
        Disposable c;
        final AtomicReference<Disposable> d = new AtomicReference<>();
        volatile long e;
        boolean f;

        static final class DebounceInnerObserver<T, U> extends DisposableObserver<U> {
            final DebounceObserver<T, U> a;
            final long b;
            final T c;
            boolean d;
            final AtomicBoolean e = new AtomicBoolean();

            DebounceInnerObserver(DebounceObserver<T, U> debounceObserver, long j, T t) {
                this.a = debounceObserver;
                this.b = j;
                this.c = t;
            }

            public void onNext(U u) {
                if (!this.d) {
                    this.d = true;
                    dispose();
                    a();
                }
            }

            /* access modifiers changed from: 0000 */
            public void a() {
                if (this.e.compareAndSet(false, true)) {
                    this.a.a(this.b, this.c);
                }
            }

            public void onError(Throwable th) {
                if (this.d) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                this.d = true;
                this.a.onError(th);
            }

            public void onComplete() {
                if (!this.d) {
                    this.d = true;
                    a();
                }
            }
        }

        DebounceObserver(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = observer;
            this.b = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.f) {
                long j = this.e + 1;
                this.e = j;
                Disposable disposable = (Disposable) this.d.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                try {
                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The ObservableSource supplied is null");
                    DebounceInnerObserver debounceInnerObserver = new DebounceInnerObserver(this, j, t);
                    if (this.d.compareAndSet(disposable, debounceInnerObserver)) {
                        observableSource.subscribe(debounceInnerObserver);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    this.a.onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.d);
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                Disposable disposable = (Disposable) this.d.get();
                if (disposable != DisposableHelper.DISPOSED) {
                    ((DebounceInnerObserver) disposable).a();
                    DisposableHelper.dispose(this.d);
                    this.a.onComplete();
                }
            }
        }

        public void dispose() {
            this.c.dispose();
            DisposableHelper.dispose(this.d);
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void a(long j, T t) {
            if (j == this.e) {
                this.a.onNext(t);
            }
        }
    }

    public ObservableDebounce(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<U>> function) {
        super(observableSource);
        this.a = function;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DebounceObserver(new SerializedObserver(observer), this.a));
    }
}
