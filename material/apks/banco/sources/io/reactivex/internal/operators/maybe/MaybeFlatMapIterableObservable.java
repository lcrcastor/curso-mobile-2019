package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

public final class MaybeFlatMapIterableObservable<T, R> extends Observable<R> {
    final MaybeSource<T> a;
    final Function<? super T, ? extends Iterable<? extends R>> b;

    static final class FlatMapIterableObserver<T, R> extends BasicQueueDisposable<R> implements MaybeObserver<T> {
        final Observer<? super R> a;
        final Function<? super T, ? extends Iterable<? extends R>> b;
        Disposable c;
        volatile Iterator<? extends R> d;
        volatile boolean e;
        boolean f;

        FlatMapIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.a = observer;
            this.b = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            Observer<? super R> observer = this.a;
            try {
                Iterator<? extends R> it = ((Iterable) this.b.apply(t)).iterator();
                if (!it.hasNext()) {
                    observer.onComplete();
                    return;
                }
                this.d = it;
                if (this.f) {
                    observer.onNext(null);
                    observer.onComplete();
                    return;
                }
                while (!this.e) {
                    try {
                        observer.onNext(it.next());
                        if (!this.e) {
                            try {
                                if (!it.hasNext()) {
                                    observer.onComplete();
                                    return;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                observer.onError(th);
                                return;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        observer.onError(th2);
                        return;
                    }
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                observer.onError(th3);
            }
        }

        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }

        public void dispose() {
            this.e = true;
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.e;
        }

        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.f = true;
            return 2;
        }

        public void clear() {
            this.d = null;
        }

        public boolean isEmpty() {
            return this.d == null;
        }

        @Nullable
        public R poll() {
            Iterator<? extends R> it = this.d;
            if (it == null) {
                return null;
            }
            R requireNonNull = ObjectHelper.requireNonNull(it.next(), "The iterator returned a null value");
            if (!it.hasNext()) {
                this.d = null;
            }
            return requireNonNull;
        }
    }

    public MaybeFlatMapIterableObservable(MaybeSource<T> maybeSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        this.a = maybeSource;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.a.subscribe(new FlatMapIterableObserver(observer, this.b));
    }
}
