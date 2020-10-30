package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableConcatIterable extends Completable {
    final Iterable<? extends CompletableSource> a;

    static final class ConcatInnerObserver extends AtomicInteger implements CompletableObserver {
        private static final long serialVersionUID = -7965400327305809232L;
        final CompletableObserver a;
        final Iterator<? extends CompletableSource> b;
        final SequentialDisposable c = new SequentialDisposable();

        ConcatInnerObserver(CompletableObserver completableObserver, Iterator<? extends CompletableSource> it) {
            this.a = completableObserver;
            this.b = it;
        }

        public void onSubscribe(Disposable disposable) {
            this.c.update(disposable);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (!this.c.isDisposed() && getAndIncrement() == 0) {
                Iterator<? extends CompletableSource> it = this.b;
                while (!this.c.isDisposed()) {
                    try {
                        if (!it.hasNext()) {
                            this.a.onComplete();
                            return;
                        }
                        try {
                            ((CompletableSource) ObjectHelper.requireNonNull(it.next(), "The CompletableSource returned is null")).subscribe(this);
                            if (decrementAndGet() == 0) {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.a.onError(th);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.a.onError(th2);
                        return;
                    }
                }
            }
        }
    }

    public CompletableConcatIterable(Iterable<? extends CompletableSource> iterable) {
        this.a = iterable;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        try {
            ConcatInnerObserver concatInnerObserver = new ConcatInnerObserver(completableObserver, (Iterator) ObjectHelper.requireNonNull(this.a.iterator(), "The iterator returned is null"));
            completableObserver.onSubscribe(concatInnerObserver.c);
            concatInnerObserver.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
        }
    }
}
