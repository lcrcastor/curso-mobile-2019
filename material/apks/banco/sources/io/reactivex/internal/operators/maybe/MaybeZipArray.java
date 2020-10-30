package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeZipArray<T, R> extends Maybe<R> {
    final MaybeSource<? extends T>[] a;
    final Function<? super Object[], ? extends R> b;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return MaybeZipArray.this.b.apply(new Object[]{t});
        }
    }

    static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final MaybeObserver<? super R> a;
        final Function<? super Object[], ? extends R> b;
        final ZipMaybeObserver<T>[] c;
        final Object[] d;

        ZipCoordinator(MaybeObserver<? super R> maybeObserver, int i, Function<? super Object[], ? extends R> function) {
            super(i);
            this.a = maybeObserver;
            this.b = function;
            ZipMaybeObserver<T>[] zipMaybeObserverArr = new ZipMaybeObserver[i];
            for (int i2 = 0; i2 < i; i2++) {
                zipMaybeObserverArr[i2] = new ZipMaybeObserver<>(this, i2);
            }
            this.c = zipMaybeObserverArr;
            this.d = new Object[i];
        }

        public boolean isDisposed() {
            return get() <= 0;
        }

        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipMaybeObserver<T> a2 : this.c) {
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
            ZipMaybeObserver<T>[] zipMaybeObserverArr = this.c;
            int length = zipMaybeObserverArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                zipMaybeObserverArr[i2].a();
            }
            while (true) {
                i++;
                if (i < length) {
                    zipMaybeObserverArr[i].a();
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

        /* access modifiers changed from: 0000 */
        public void b(int i) {
            if (getAndSet(0) > 0) {
                a(i);
                this.a.onComplete();
            }
        }
    }

    static final class ZipMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 3323743579927613702L;
        final ZipCoordinator<T, ?> a;
        final int b;

        ZipMaybeObserver(ZipCoordinator<T, ?> zipCoordinator, int i) {
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

        public void onComplete() {
            this.a.b(this.b);
        }
    }

    public MaybeZipArray(MaybeSource<? extends T>[] maybeSourceArr, Function<? super Object[], ? extends R> function) {
        this.a = maybeSourceArr;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource<? extends T>[] maybeSourceArr = this.a;
        int length = maybeSourceArr.length;
        int i = 0;
        if (length == 1) {
            maybeSourceArr[0].subscribe(new MapMaybeObserver(maybeObserver, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(maybeObserver, length, this.b);
        maybeObserver.onSubscribe(zipCoordinator);
        while (i < length && !zipCoordinator.isDisposed()) {
            MaybeSource<? extends T> maybeSource = maybeSourceArr[i];
            if (maybeSource == null) {
                zipCoordinator.a((Throwable) new NullPointerException("One of the sources is null"), i);
                return;
            } else {
                maybeSource.subscribe(zipCoordinator.c[i]);
                i++;
            }
        }
    }
}
