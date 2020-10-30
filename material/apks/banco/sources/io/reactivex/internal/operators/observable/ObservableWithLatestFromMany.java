package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ObservableWithLatestFromMany<T, R> extends AbstractObservableWithUpstream<T, R> {
    @Nullable
    final ObservableSource<?>[] a;
    @Nullable
    final Iterable<? extends ObservableSource<?>> b;
    @NonNull
    final Function<? super Object[], R> c;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return ObservableWithLatestFromMany.this.c.apply(new Object[]{t});
        }
    }

    static final class WithLatestFromObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1577321883966341961L;
        final Observer<? super R> a;
        final Function<? super Object[], R> b;
        final WithLatestInnerObserver[] c;
        final AtomicReferenceArray<Object> d;
        final AtomicReference<Disposable> e;
        final AtomicThrowable f;
        volatile boolean g;

        WithLatestFromObserver(Observer<? super R> observer, Function<? super Object[], R> function, int i) {
            this.a = observer;
            this.b = function;
            WithLatestInnerObserver[] withLatestInnerObserverArr = new WithLatestInnerObserver[i];
            for (int i2 = 0; i2 < i; i2++) {
                withLatestInnerObserverArr[i2] = new WithLatestInnerObserver(this, i2);
            }
            this.c = withLatestInnerObserverArr;
            this.d = new AtomicReferenceArray<>(i);
            this.e = new AtomicReference<>();
            this.f = new AtomicThrowable();
        }

        /* access modifiers changed from: 0000 */
        public void a(ObservableSource<?>[] observableSourceArr, int i) {
            WithLatestInnerObserver[] withLatestInnerObserverArr = this.c;
            AtomicReference<Disposable> atomicReference = this.e;
            for (int i2 = 0; i2 < i && !DisposableHelper.isDisposed((Disposable) atomicReference.get()) && !this.g; i2++) {
                observableSourceArr[i2].subscribe(withLatestInnerObserverArr[i2]);
            }
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.e, disposable);
        }

        public void onNext(T t) {
            if (!this.g) {
                AtomicReferenceArray<Object> atomicReferenceArray = this.d;
                int length = atomicReferenceArray.length();
                Object[] objArr = new Object[(length + 1)];
                int i = 0;
                objArr[0] = t;
                while (i < length) {
                    Object obj = atomicReferenceArray.get(i);
                    if (obj != null) {
                        i++;
                        objArr[i] = obj;
                    } else {
                        return;
                    }
                }
                try {
                    HalfSerializer.onNext(this.a, ObjectHelper.requireNonNull(this.b.apply(objArr), "combiner returned a null value"), (AtomicInteger) this, this.f);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            a(-1);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.f);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                a(-1);
                HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.f);
            }
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.e.get());
        }

        public void dispose() {
            DisposableHelper.dispose(this.e);
            for (WithLatestInnerObserver a2 : this.c) {
                a2.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Object obj) {
            this.d.set(i, obj);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Throwable th) {
            this.g = true;
            DisposableHelper.dispose(this.e);
            a(i);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.f);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, boolean z) {
            if (!z) {
                this.g = true;
                a(i);
                HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.f);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            WithLatestInnerObserver[] withLatestInnerObserverArr = this.c;
            for (int i2 = 0; i2 < withLatestInnerObserverArr.length; i2++) {
                if (i2 != i) {
                    withLatestInnerObserverArr[i2].a();
                }
            }
        }
    }

    static final class WithLatestInnerObserver extends AtomicReference<Disposable> implements Observer<Object> {
        private static final long serialVersionUID = 3256684027868224024L;
        final WithLatestFromObserver<?, ?> a;
        final int b;
        boolean c;

        WithLatestInnerObserver(WithLatestFromObserver<?, ?> withLatestFromObserver, int i) {
            this.a = withLatestFromObserver;
            this.b = i;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
            }
            this.a.a(this.b, obj);
        }

        public void onError(Throwable th) {
            this.a.a(this.b, th);
        }

        public void onComplete() {
            this.a.a(this.b, this.c);
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull ObservableSource<?>[] observableSourceArr, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = observableSourceArr;
        this.b = null;
        this.c = function;
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> observableSource, @NonNull Iterable<? extends ObservableSource<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(observableSource);
        this.a = null;
        this.b = iterable;
        this.c = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        int i;
        ObservableSource<?>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                i = 0;
                for (ObservableSource<?> observableSource : this.b) {
                    if (i == observableSourceArr.length) {
                        observableSourceArr = (ObservableSource[]) Arrays.copyOf(observableSourceArr, (i >> 1) + i);
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
            new ObservableMap(this.source, new SingletonArrayFunc()).subscribeActual(observer);
            return;
        }
        WithLatestFromObserver withLatestFromObserver = new WithLatestFromObserver(observer, this.c, i);
        observer.onSubscribe(withLatestFromObserver);
        withLatestFromObserver.a(observableSourceArr, i);
        this.source.subscribe(withLatestFromObserver);
    }
}
