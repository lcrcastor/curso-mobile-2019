package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableZip<T, R> extends Observable<R> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;
    final Function<? super Object[], ? extends R> c;
    final int d;
    final boolean e;

    static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2983708048395377667L;
        final Observer<? super R> a;
        final Function<? super Object[], ? extends R> b;
        final ZipObserver<T, R>[] c;
        final T[] d;
        final boolean e;
        volatile boolean f;

        ZipCoordinator(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = new ZipObserver[i];
            this.d = (Object[]) new Object[i];
            this.e = z;
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr, int i) {
            ZipObserver<T, R>[] zipObserverArr = this.c;
            int length = zipObserverArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                zipObserverArr[i2] = new ZipObserver<>(this, i);
            }
            lazySet(0);
            this.a.onSubscribe(this);
            for (int i3 = 0; i3 < length && !this.f; i3++) {
                observableSourceArr[i3].subscribe(zipObserverArr[i3]);
            }
        }

        public void dispose() {
            if (!this.f) {
                this.f = true;
                b();
                if (getAndIncrement() == 0) {
                    c();
                }
            }
        }

        public boolean isDisposed() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            c();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            for (ZipObserver<T, R> a2 : this.c) {
                a2.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            for (ZipObserver<T, R> zipObserver : this.c) {
                zipObserver.b.clear();
            }
        }

        public void d() {
            int i;
            if (getAndIncrement() == 0) {
                ZipObserver<T, R>[] zipObserverArr = this.c;
                Observer<? super R> observer = this.a;
                T[] tArr = this.d;
                boolean z = this.e;
                int i2 = 1;
                while (true) {
                    int length = zipObserverArr.length;
                    int i3 = 0;
                    int i4 = 0;
                    int i5 = 0;
                    while (i3 < length) {
                        ZipObserver<T, R> zipObserver = zipObserverArr[i3];
                        if (tArr[i4] == null) {
                            boolean z2 = zipObserver.c;
                            T poll = zipObserver.b.poll();
                            boolean z3 = poll == null;
                            i = i3;
                            if (!a(z2, z3, observer, z, zipObserver)) {
                                if (!z3) {
                                    tArr[i4] = poll;
                                } else {
                                    i5++;
                                }
                            } else {
                                return;
                            }
                        } else {
                            ZipObserver<T, R> zipObserver2 = zipObserver;
                            i = i3;
                            if (zipObserver2.c && !z) {
                                Throwable th = zipObserver2.d;
                                if (th != null) {
                                    a();
                                    observer.onError(th);
                                    return;
                                }
                            }
                        }
                        i4++;
                        i3 = i + 1;
                    }
                    if (i5 != 0) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        try {
                            observer.onNext(ObjectHelper.requireNonNull(this.b.apply(tArr.clone()), "The zipper returned a null value"));
                            Arrays.fill(tArr, null);
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            Exceptions.throwIfFatal(th3);
                            a();
                            observer.onError(th3);
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Observer<? super R> observer, boolean z3, ZipObserver<?, ?> zipObserver) {
            if (this.f) {
                a();
                return true;
            }
            if (z) {
                if (!z3) {
                    Throwable th = zipObserver.d;
                    if (th != null) {
                        a();
                        observer.onError(th);
                        return true;
                    } else if (z2) {
                        a();
                        observer.onComplete();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = zipObserver.d;
                    a();
                    if (th2 != null) {
                        observer.onError(th2);
                    } else {
                        observer.onComplete();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    static final class ZipObserver<T, R> implements Observer<T> {
        final ZipCoordinator<T, R> a;
        final SpscLinkedArrayQueue<T> b;
        volatile boolean c;
        Throwable d;
        final AtomicReference<Disposable> e = new AtomicReference<>();

        ZipObserver(ZipCoordinator<T, R> zipCoordinator, int i) {
            this.a = zipCoordinator;
            this.b = new SpscLinkedArrayQueue<>(i);
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }

        public void onNext(T t) {
            this.b.offer(t);
            this.a.d();
        }

        public void onError(Throwable th) {
            this.d = th;
            this.c = true;
            this.a.d();
        }

        public void onComplete() {
            this.c = true;
            this.a.d();
        }

        public void a() {
            DisposableHelper.dispose(this.e);
        }
    }

    public ObservableZip(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.a = observableSourceArr;
        this.b = iterable;
        this.c = function;
        this.d = i;
        this.e = z;
    }

    public void subscribeActual(Observer<? super R> observer) {
        ObservableSource<? extends T>[] observableSourceArr;
        int i;
        ObservableSource<? extends T>[] observableSourceArr2 = this.a;
        if (observableSourceArr2 == null) {
            Observable[] observableArr = new Observable[8];
            observableSourceArr = observableArr;
            i = 0;
            for (ObservableSource<? extends T> observableSource : this.b) {
                if (i == observableSourceArr.length) {
                    ObservableSource<? extends T>[] observableSourceArr3 = new ObservableSource[((i >> 2) + i)];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr3, 0, i);
                    observableSourceArr = observableSourceArr3;
                }
                int i2 = i + 1;
                observableSourceArr[i] = observableSource;
                i = i2;
            }
        } else {
            observableSourceArr = observableSourceArr2;
            i = observableSourceArr2.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new ZipCoordinator(observer, this.c, i, this.e).a(observableSourceArr, this.d);
        }
    }
}
