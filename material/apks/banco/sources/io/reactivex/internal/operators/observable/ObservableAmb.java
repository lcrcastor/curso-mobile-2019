package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableAmb<T> extends Observable<T> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;

    static final class AmbCoordinator<T> implements Disposable {
        final Observer<? super T> a;
        final AmbInnerObserver<T>[] b;
        final AtomicInteger c = new AtomicInteger();

        AmbCoordinator(Observer<? super T> observer, int i) {
            this.a = observer;
            this.b = new AmbInnerObserver[i];
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr) {
            AmbInnerObserver<T>[] ambInnerObserverArr = this.b;
            int length = ambInnerObserverArr.length;
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                ambInnerObserverArr[i] = new AmbInnerObserver<>(this, i2, this.a);
                i = i2;
            }
            this.c.lazySet(0);
            this.a.onSubscribe(this);
            for (int i3 = 0; i3 < length && this.c.get() == 0; i3++) {
                observableSourceArr[i3].subscribe(ambInnerObserverArr[i3]);
            }
        }

        public boolean a(int i) {
            int i2 = this.c.get();
            boolean z = true;
            int i3 = 0;
            if (i2 != 0) {
                if (i2 != i) {
                    z = false;
                }
                return z;
            } else if (!this.c.compareAndSet(0, i)) {
                return false;
            } else {
                AmbInnerObserver<T>[] ambInnerObserverArr = this.b;
                int length = ambInnerObserverArr.length;
                while (i3 < length) {
                    int i4 = i3 + 1;
                    if (i4 != i) {
                        ambInnerObserverArr[i3].a();
                    }
                    i3 = i4;
                }
                return true;
            }
        }

        public void dispose() {
            if (this.c.get() != -1) {
                this.c.lazySet(-1);
                for (AmbInnerObserver<T> a2 : this.b) {
                    a2.a();
                }
            }
        }

        public boolean isDisposed() {
            return this.c.get() == -1;
        }
    }

    static final class AmbInnerObserver<T> extends AtomicReference<Disposable> implements Observer<T> {
        private static final long serialVersionUID = -1185974347409665484L;
        final AmbCoordinator<T> a;
        final int b;
        final Observer<? super T> c;
        boolean d;

        AmbInnerObserver(AmbCoordinator<T> ambCoordinator, int i, Observer<? super T> observer) {
            this.a = ambCoordinator;
            this.b = i;
            this.c = observer;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(T t) {
            if (this.d) {
                this.c.onNext(t);
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onNext(t);
            } else {
                ((Disposable) get()).dispose();
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                this.c.onError(th);
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (this.d) {
                this.c.onComplete();
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onComplete();
            }
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }

    public ObservableAmb(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable) {
        this.a = observableSourceArr;
        this.b = iterable;
    }

    public void subscribeActual(Observer<? super T> observer) {
        int i;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new Observable[8];
            try {
                i = 0;
                for (ObservableSource<? extends T> observableSource : this.b) {
                    if (observableSource == null) {
                        EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), observer);
                        return;
                    }
                    if (i == observableSourceArr.length) {
                        ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[((i >> 2) + i)];
                        System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, i);
                        observableSourceArr = observableSourceArr2;
                    }
                    int i2 = i + 1;
                    observableSourceArr[i] = observableSource;
                    i = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
        } else if (i == 1) {
            observableSourceArr[0].subscribe(observer);
        } else {
            new AmbCoordinator(observer, i).a(observableSourceArr);
        }
    }
}
