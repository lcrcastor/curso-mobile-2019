package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DefaultObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BlockingObservableMostRecent<T> implements Iterable<T> {
    final ObservableSource<T> a;
    final T b;

    static final class MostRecentObserver<T> extends DefaultObserver<T> {
        volatile Object a;

        final class Iterator implements java.util.Iterator<T> {
            private Object b;

            Iterator() {
            }

            public boolean hasNext() {
                this.b = MostRecentObserver.this.a;
                return !NotificationLite.isComplete(this.b);
            }

            public T next() {
                Object obj = null;
                try {
                    if (this.b == null) {
                        obj = MostRecentObserver.this.a;
                    }
                    if (NotificationLite.isComplete(this.b)) {
                        throw new NoSuchElementException();
                    } else if (NotificationLite.isError(this.b)) {
                        throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.b));
                    } else {
                        T value = NotificationLite.getValue(this.b);
                        this.b = obj;
                        return value;
                    }
                } finally {
                    this.b = obj;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException("Read only iterator");
            }
        }

        MostRecentObserver(T t) {
            this.a = NotificationLite.next(t);
        }

        public void onComplete() {
            this.a = NotificationLite.complete();
        }

        public void onError(Throwable th) {
            this.a = NotificationLite.error(th);
        }

        public void onNext(T t) {
            this.a = NotificationLite.next(t);
        }

        public Iterator a() {
            return new Iterator<>();
        }
    }

    public BlockingObservableMostRecent(ObservableSource<T> observableSource, T t) {
        this.a = observableSource;
        this.b = t;
    }

    public Iterator<T> iterator() {
        MostRecentObserver mostRecentObserver = new MostRecentObserver(this.b);
        this.a.subscribe(mostRecentObserver);
        return mostRecentObserver.a();
    }
}
