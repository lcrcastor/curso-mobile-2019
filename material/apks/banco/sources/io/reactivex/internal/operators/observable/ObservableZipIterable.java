package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;

public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    final Observable<? extends T> a;
    final Iterable<U> b;
    final BiFunction<? super T, ? super U, ? extends V> c;

    static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
        final Observer<? super V> a;
        final Iterator<U> b;
        final BiFunction<? super T, ? super U, ? extends V> c;
        Disposable d;
        boolean e;

        ZipIterableObserver(Observer<? super V> observer, Iterator<U> it, BiFunction<? super T, ? super U, ? extends V> biFunction) {
            this.a = observer;
            this.b = it;
            this.c = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void onNext(T t) {
            if (!this.e) {
                try {
                    try {
                        this.a.onNext(ObjectHelper.requireNonNull(this.c.apply(t, ObjectHelper.requireNonNull(this.b.next(), "The iterator returned a null value")), "The zipper function returned a null value"));
                        try {
                            if (!this.b.hasNext()) {
                                this.e = true;
                                this.d.dispose();
                                this.a.onComplete();
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            a(th);
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        a(th2);
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    a(th3);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            this.e = true;
            this.d.dispose();
            this.a.onError(th);
        }

        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }

    public ObservableZipIterable(Observable<? extends T> observable, Iterable<U> iterable, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        this.a = observable;
        this.b = iterable;
        this.c = biFunction;
    }

    public void subscribeActual(Observer<? super V> observer) {
        try {
            Iterator it = (Iterator) ObjectHelper.requireNonNull(this.b.iterator(), "The iterator returned by other is null");
            try {
                if (!it.hasNext()) {
                    EmptyDisposable.complete(observer);
                } else {
                    this.a.subscribe((Observer<? super T>) new ZipIterableObserver<Object>(observer, it, this.c));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }
}
