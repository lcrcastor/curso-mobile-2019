package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTake<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;

    static final class TakeObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        boolean b;
        Disposable c;
        long d;

        TakeObserver(Observer<? super T> observer, long j) {
            this.a = observer;
            this.d = j;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                if (this.d == 0) {
                    this.b = true;
                    disposable.dispose();
                    EmptyDisposable.complete(this.a);
                    return;
                }
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.b) {
                long j = this.d;
                this.d = j - 1;
                if (j > 0) {
                    boolean z = this.d == 0;
                    this.a.onNext(t);
                    if (z) {
                        onComplete();
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.c.dispose();
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.c.dispose();
                this.a.onComplete();
            }
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }
    }

    public ObservableTake(ObservableSource<T> observableSource, long j) {
        super(observableSource);
        this.a = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeObserver(observer, this.a));
    }
}
