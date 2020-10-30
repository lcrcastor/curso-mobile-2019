package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletablePeek extends Completable {
    final CompletableSource a;
    final Consumer<? super Disposable> b;
    final Consumer<? super Throwable> c;
    final Action d;
    final Action e;
    final Action f;
    final Action g;

    final class CompletableObserverImplementation implements CompletableObserver, Disposable {
        final CompletableObserver a;
        Disposable b;

        CompletableObserverImplementation(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            try {
                CompletablePeek.this.b.accept(disposable);
                if (DisposableHelper.validate(this.b, disposable)) {
                    this.b = disposable;
                    this.a.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                disposable.dispose();
                this.b = DisposableHelper.DISPOSED;
                EmptyDisposable.error(th, this.a);
            }
        }

        public void onError(Throwable th) {
            if (this.b == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            try {
                CompletablePeek.this.c.accept(th);
                CompletablePeek.this.e.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            if (this.b != DisposableHelper.DISPOSED) {
                try {
                    CompletablePeek.this.d.run();
                    CompletablePeek.this.e.run();
                    this.a.onComplete();
                    a();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            try {
                CompletablePeek.this.f.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        public void dispose() {
            try {
                CompletablePeek.this.g.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.b.dispose();
        }

        public boolean isDisposed() {
            return this.b.isDisposed();
        }
    }

    public CompletablePeek(CompletableSource completableSource, Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        this.a = completableSource;
        this.b = consumer;
        this.c = consumer2;
        this.d = action;
        this.e = action2;
        this.f = action3;
        this.g = action4;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableObserverImplementation(completableObserver));
    }
}
