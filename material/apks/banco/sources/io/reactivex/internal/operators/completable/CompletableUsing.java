package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
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

public final class CompletableUsing<R> extends Completable {
    final Callable<R> a;
    final Function<? super R, ? extends CompletableSource> b;
    final Consumer<? super R> c;
    final boolean d;

    static final class UsingObserver<R> extends AtomicReference<Object> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -674404550052917487L;
        final CompletableObserver a;
        final Consumer<? super R> b;
        final boolean c;
        Disposable d;

        UsingObserver(CompletableObserver completableObserver, R r, Consumer<? super R> consumer, boolean z) {
            super(r);
            this.a = completableObserver;
            this.b = consumer;
            this.c = z;
        }

        public void dispose() {
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
            a();
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

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
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

        public void onComplete() {
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
            this.a.onComplete();
            if (!this.c) {
                a();
            }
        }
    }

    public CompletableUsing(Callable<R> callable, Function<? super R, ? extends CompletableSource> function, Consumer<? super R> consumer, boolean z) {
        this.a = callable;
        this.b = function;
        this.c = consumer;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        try {
            Object call = this.a.call();
            try {
                ((CompletableSource) ObjectHelper.requireNonNull(this.b.apply(call), "The completableFunction returned a null CompletableSource")).subscribe(new UsingObserver(completableObserver, call, this.c, this.d));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, completableObserver);
        }
    }
}
