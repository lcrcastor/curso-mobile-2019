package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

public final class ObservableFromIterable<T> extends Observable<T> {
    final Iterable<? extends T> a;

    static final class FromIterableDisposable<T> extends BasicQueueDisposable<T> {
        final Observer<? super T> a;
        final Iterator<? extends T> b;
        volatile boolean c;
        boolean d;
        boolean e;
        boolean f;

        FromIterableDisposable(Observer<? super T> observer, Iterator<? extends T> it) {
            this.a = observer;
            this.b = it;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            while (!isDisposed()) {
                try {
                    this.a.onNext(ObjectHelper.requireNonNull(this.b.next(), "The iterator returned a null value"));
                    if (!isDisposed()) {
                        try {
                            if (!this.b.hasNext()) {
                                if (!isDisposed()) {
                                    this.a.onComplete();
                                }
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.a.onError(th);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.a.onError(th2);
                    return;
                }
            }
        }

        public int requestFusion(int i) {
            if ((i & 1) == 0) {
                return 0;
            }
            this.d = true;
            return 1;
        }

        @Nullable
        public T poll() {
            if (this.e) {
                return null;
            }
            if (!this.f) {
                this.f = true;
            } else if (!this.b.hasNext()) {
                this.e = true;
                return null;
            }
            return ObjectHelper.requireNonNull(this.b.next(), "The iterator returned a null value");
        }

        public boolean isEmpty() {
            return this.e;
        }

        public void clear() {
            this.e = true;
        }

        public void dispose() {
            this.c = true;
        }

        public boolean isDisposed() {
            return this.c;
        }
    }

    public ObservableFromIterable(Iterable<? extends T> iterable) {
        this.a = iterable;
    }

    public void subscribeActual(Observer<? super T> observer) {
        try {
            Iterator it = this.a.iterator();
            try {
                if (!it.hasNext()) {
                    EmptyDisposable.complete(observer);
                    return;
                }
                FromIterableDisposable fromIterableDisposable = new FromIterableDisposable(observer, it);
                observer.onSubscribe(fromIterableDisposable);
                if (!fromIterableDisposable.d) {
                    fromIterableDisposable.a();
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
