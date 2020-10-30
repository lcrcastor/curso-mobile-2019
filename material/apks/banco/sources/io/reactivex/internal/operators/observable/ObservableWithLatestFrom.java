package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWithLatestFrom<T, U, R> extends AbstractObservableWithUpstream<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> a;
    final ObservableSource<? extends U> b;

    final class WithLastFrom implements Observer<U> {
        private final WithLatestFromObserver<T, U, R> b;

        public void onComplete() {
        }

        WithLastFrom(WithLatestFromObserver<T, U, R> withLatestFromObserver) {
            this.b = withLatestFromObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.a(disposable);
        }

        public void onNext(U u) {
            this.b.lazySet(u);
        }

        public void onError(Throwable th) {
            this.b.a(th);
        }
    }

    static final class WithLatestFromObserver<T, U, R> extends AtomicReference<U> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -312246233408980075L;
        final Observer<? super R> a;
        final BiFunction<? super T, ? super U, ? extends R> b;
        final AtomicReference<Disposable> c = new AtomicReference<>();
        final AtomicReference<Disposable> d = new AtomicReference<>();

        WithLatestFromObserver(Observer<? super R> observer, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.a = observer;
            this.b = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.c, disposable);
        }

        public void onNext(T t) {
            Object obj = get();
            if (obj != null) {
                try {
                    this.a.onNext(ObjectHelper.requireNonNull(this.b.apply(t, obj), "The combiner returned a null value"));
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
            DisposableHelper.dispose(this.d);
            this.a.onComplete();
        }

        public void dispose() {
            DisposableHelper.dispose(this.c);
            DisposableHelper.dispose(this.d);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.c.get());
        }

        public boolean a(Disposable disposable) {
            return DisposableHelper.setOnce(this.d, disposable);
        }

        public void a(Throwable th) {
            DisposableHelper.dispose(this.c);
            this.a.onError(th);
        }
    }

    public ObservableWithLatestFrom(ObservableSource<T> observableSource, BiFunction<? super T, ? super U, ? extends R> biFunction, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.a = biFunction;
        this.b = observableSource2;
    }

    public void subscribeActual(Observer<? super R> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        WithLatestFromObserver withLatestFromObserver = new WithLatestFromObserver(serializedObserver, this.a);
        serializedObserver.onSubscribe(withLatestFromObserver);
        this.b.subscribe(new WithLastFrom(withLatestFromObserver));
        this.source.subscribe(withLatestFromObserver);
    }
}
