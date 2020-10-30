package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> {
    final Consumer<? super T> a;
    final Consumer<? super Throwable> b;
    final Action c;
    final Action d;

    static final class DoOnEachObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final Consumer<? super T> b;
        final Consumer<? super Throwable> c;
        final Action d;
        final Action e;
        Disposable f;
        boolean g;

        DoOnEachObserver(Observer<? super T> observer, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
            this.a = observer;
            this.b = consumer;
            this.c = consumer2;
            this.d = action;
            this.e = action2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        public void onNext(T t) {
            if (!this.g) {
                try {
                    this.b.accept(t);
                    this.a.onNext(t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f.dispose();
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
            try {
                this.c.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
            try {
                this.e.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        public void onComplete() {
            if (!this.g) {
                try {
                    this.d.run();
                    this.g = true;
                    this.a.onComplete();
                    try {
                        this.e.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }
    }

    public ObservableDoOnEach(ObservableSource<T> observableSource, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        super(observableSource);
        this.a = consumer;
        this.b = consumer2;
        this.c = action;
        this.d = action2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        ObservableSource observableSource = this.source;
        DoOnEachObserver doOnEachObserver = new DoOnEachObserver(observer, this.a, this.b, this.c, this.d);
        observableSource.subscribe(doOnEachObserver);
    }
}
