package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableDistinctUntilChanged<T, K> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super T, K> a;
    final BiPredicate<? super K, ? super K> b;

    static final class DistinctUntilChangedObserver<T, K> extends BasicFuseableObserver<T, T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        DistinctUntilChangedObserver(Observer<? super T> observer, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(observer);
            this.a = function;
            this.b = biPredicate;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.actual.onNext(t);
                    return;
                }
                try {
                    K apply = this.a.apply(t);
                    if (this.d) {
                        boolean test = this.b.test(this.c, apply);
                        this.c = apply;
                        if (test) {
                            return;
                        }
                    } else {
                        this.d = true;
                        this.c = apply;
                    }
                    this.actual.onNext(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test(this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                }
            }
        }
    }

    public ObservableDistinctUntilChanged(ObservableSource<T> observableSource, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(observableSource);
        this.a = function;
        this.b = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DistinctUntilChangedObserver(observer, this.a, this.b));
    }
}
