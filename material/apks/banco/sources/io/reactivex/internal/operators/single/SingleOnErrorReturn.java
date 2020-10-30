package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

public final class SingleOnErrorReturn<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final Function<? super Throwable, ? extends T> b;
    final T c;

    final class OnErrorReturn implements SingleObserver<T> {
        private final SingleObserver<? super T> b;

        OnErrorReturn(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        public void onError(Throwable th) {
            T t;
            if (SingleOnErrorReturn.this.b != null) {
                try {
                    t = SingleOnErrorReturn.this.b.apply(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.b.onError(new CompositeException(th, th2));
                    return;
                }
            } else {
                t = SingleOnErrorReturn.this.c;
            }
            if (t == null) {
                NullPointerException nullPointerException = new NullPointerException("Value supplied was null");
                nullPointerException.initCause(th);
                this.b.onError(nullPointerException);
                return;
            }
            this.b.onSuccess(t);
        }

        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }
    }

    public SingleOnErrorReturn(SingleSource<? extends T> singleSource, Function<? super Throwable, ? extends T> function, T t) {
        this.a = singleSource;
        this.b = function;
        this.c = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new OnErrorReturn(singleObserver));
    }
}
