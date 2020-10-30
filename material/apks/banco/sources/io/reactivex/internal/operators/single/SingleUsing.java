package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUsing<T, U> extends Single<T> {
    final Callable<U> a;
    final Function<? super U, ? extends SingleSource<? extends T>> b;
    final Consumer<? super U> c;
    final boolean d;

    static final class UsingSingleObserver<T, U> extends AtomicReference<Object> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -5331524057054083935L;
        final SingleObserver<? super T> a;
        final Consumer<? super U> b;
        final boolean c;
        Disposable d;

        UsingSingleObserver(SingleObserver<? super T> singleObserver, U u, boolean z, Consumer<? super U> consumer) {
            super(u);
            this.a = singleObserver;
            this.c = z;
            this.b = consumer;
        }

        public void dispose() {
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
            a();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.d = DisposableHelper.DISPOSED;
            if (this.c) {
                Object andSet = getAndSet(this);
                if (andSet != this) {
                    try {
                        this.b.accept(andSet);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.a.onError(th);
                        return;
                    }
                } else {
                    return;
                }
            }
            this.a.onSuccess(t);
            if (!this.c) {
                a();
            }
        }

        public void onError(Throwable th) {
            this.d = DisposableHelper.DISPOSED;
            if (this.c) {
                Object andSet = getAndSet(this);
                if (andSet != this) {
                    try {
                        this.b.accept(andSet);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        th = new CompositeException(th, th2);
                    }
                } else {
                    return;
                }
            }
            this.a.onError(th);
            if (!this.c) {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Object andSet = getAndSet(this);
            if (andSet != this) {
                try {
                    this.b.accept(andSet);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public SingleUsing(Callable<U> callable, Function<? super U, ? extends SingleSource<? extends T>> function, Consumer<? super U> consumer, boolean z) {
        this.a = callable;
        this.b = function;
        this.c = consumer;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        try {
            Object call = this.a.call();
            try {
                ((SingleSource) ObjectHelper.requireNonNull(this.b.apply(call), "The singleFunction returned a null SingleSource")).subscribe(new UsingSingleObserver(singleObserver, call, this.d, this.c));
                return;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            EmptyDisposable.error(th, singleObserver);
            if (!this.d) {
                this.c.accept(call);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, singleObserver);
        }
    }
}
