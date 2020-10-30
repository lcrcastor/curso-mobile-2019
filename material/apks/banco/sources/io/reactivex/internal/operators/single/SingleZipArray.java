package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleZipArray<T, R> extends Single<R> {
    final SingleSource<? extends T>[] a;
    final Function<? super Object[], ? extends R> b;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return SingleZipArray.this.b.apply(new Object[]{t});
        }
    }

    static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final SingleObserver<? super R> a;
        final Function<? super Object[], ? extends R> b;
        final ZipSingleObserver<T>[] c;
        final Object[] d;

        ZipCoordinator(SingleObserver<? super R> singleObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.a = singleObserver;
            this.b = function;
            ZipSingleObserver<T>[] zipSingleObserverArr = new ZipSingleObserver[i];
            for (int i2 = 0; i2 < i; i2++) {
                zipSingleObserverArr[i2] = new ZipSingleObserver<>(this, i2);
            }
            this.c = zipSingleObserverArr;
            this.d = new Object[i];
        }

        public boolean isDisposed() {
            return get() <= 0;
        }

        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipSingleObserver<T> a2 : this.c) {
                    a2.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(T t, int i) {
            this.d[i] = t;
            if (decrementAndGet() == 0) {
                try {
                    this.a.onSuccess(ObjectHelper.requireNonNull(this.b.apply(this.d), "The zipper returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            ZipSingleObserver<T>[] zipSingleObserverArr = this.c;
            int length = zipSingleObserverArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                zipSingleObserverArr[i2].a();
            }
            while (true) {
                i++;
                if (i < length) {
                    zipSingleObserverArr[i].a();
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th, int i) {
            if (getAndSet(0) > 0) {
                a(i);
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    static final class ZipSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
        private static final long serialVersionUID = 3323743579927613702L;
        final ZipCoordinator<T, ?> a;
        final int b;

        ZipSingleObserver(ZipCoordinator<T, ?> zipCoordinator, int i) {
            this.a = zipCoordinator;
            this.b = i;
        }

        public void a() {
            DisposableHelper.dispose(this);
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.a.a(t, this.b);
        }

        public void onError(Throwable th) {
            this.a.a(th, this.b);
        }
    }

    public SingleZipArray(SingleSource<? extends T>[] singleSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = singleSourceArr;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        SingleSource<? extends T>[] singleSourceArr = this.a;
        int length = singleSourceArr.length;
        int i = 0;
        if (length == 1) {
            singleSourceArr[0].subscribe(new MapSingleObserver(singleObserver, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(singleObserver, length, this.b);
        singleObserver.onSubscribe(zipCoordinator);
        while (i < length && !zipCoordinator.isDisposed()) {
            SingleSource<? extends T> singleSource = singleSourceArr[i];
            if (singleSource == null) {
                zipCoordinator.a((Throwable) new NullPointerException("One of the sources is null"), i);
                return;
            } else {
                singleSource.subscribe(zipCoordinator.c[i]);
                i++;
            }
        }
    }
}
