package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAt<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;
    final T b;
    final boolean c;

    static final class ElementAtObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final long b;
        final T c;
        final boolean d;
        Disposable e;
        long f;
        boolean g;

        ElementAtObserver(Observer<? super T> observer, long j, T t, boolean z) {
            this.a = observer;
            this.b = j;
            this.c = t;
            this.d = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.e.dispose();
        }

        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        public void onNext(T t) {
            if (!this.g) {
                long j = this.f;
                if (j == this.b) {
                    this.g = true;
                    this.e.dispose();
                    this.a.onNext(t);
                    this.a.onComplete();
                    return;
                }
                this.f = j + 1;
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                T t = this.c;
                if (t != null || !this.d) {
                    if (t != null) {
                        this.a.onNext(t);
                    }
                    this.a.onComplete();
                    return;
                }
                this.a.onError(new NoSuchElementException());
            }
        }
    }

    public ObservableElementAt(ObservableSource<T> observableSource, long j, T t, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = t;
        this.c = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        ObservableSource observableSource = this.source;
        ElementAtObserver elementAtObserver = new ElementAtObserver(observer, this.a, this.b, this.c);
        observableSource.subscribe(elementAtObserver);
    }
}
