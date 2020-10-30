package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> a;

    static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> a;

        FilterObserver(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.a = predicate;
        }

        public void onNext(T t) {
            if (this.sourceMode == 0) {
                try {
                    if (this.a.test(t)) {
                        this.actual.onNext(t);
                    }
                } catch (Throwable th) {
                    fail(th);
                }
            } else {
                this.actual.onNext(null);
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
            } while (!this.a.test(poll));
            return poll;
        }
    }

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.a = predicate;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FilterObserver(observer, this.a));
    }
}
