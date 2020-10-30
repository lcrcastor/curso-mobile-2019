package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableDistinct<T, K> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super T, K> a;
    final Callable<? extends Collection<? super K>> b;

    static final class DistinctObserver<T, K> extends BasicFuseableObserver<T, T> {
        final Collection<? super K> a;
        final Function<? super T, K> b;

        DistinctObserver(Observer<? super T> observer, Function<? super T, K> function, Collection<? super K> collection) {
            super(observer);
            this.b = function;
            this.a = collection;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 0) {
                    try {
                        if (this.a.add(ObjectHelper.requireNonNull(this.b.apply(t), "The keySelector returned a null key"))) {
                            this.actual.onNext(t);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    this.actual.onNext(null);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.a.clear();
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.a.clear();
                this.actual.onComplete();
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            T poll;
            do {
                poll = this.qs.poll();
                if (poll == null) {
                    break;
                }
            } while (!this.a.add(ObjectHelper.requireNonNull(this.b.apply(poll), "The keySelector returned a null key")));
            return poll;
        }

        public void clear() {
            this.a.clear();
            super.clear();
        }
    }

    public ObservableDistinct(ObservableSource<T> observableSource, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(observableSource);
        this.a = function;
        this.b = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        try {
            this.source.subscribe(new DistinctObserver(observer, this.a, (Collection) ObjectHelper.requireNonNull(this.b.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
