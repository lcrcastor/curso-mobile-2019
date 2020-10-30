package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BlockingFlowableMostRecent<T> implements Iterable<T> {
    final Flowable<T> a;
    final T b;

    static final class MostRecentSubscriber<T> extends DefaultSubscriber<T> {
        volatile Object a;

        final class Iterator implements java.util.Iterator<T> {
            private Object b;

            Iterator() {
            }

            public boolean hasNext() {
                this.b = MostRecentSubscriber.this.a;
                return !NotificationLite.isComplete(this.b);
            }

            public T next() {
                Object obj = null;
                try {
                    if (this.b == null) {
                        obj = MostRecentSubscriber.this.a;
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

        MostRecentSubscriber(T t) {
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

    public BlockingFlowableMostRecent(Flowable<T> flowable, T t) {
        this.a = flowable;
        this.b = t;
    }

    public Iterator<T> iterator() {
        MostRecentSubscriber mostRecentSubscriber = new MostRecentSubscriber(this.b);
        this.a.subscribe((FlowableSubscriber<? super T>) mostRecentSubscriber);
        return mostRecentSubscriber.a();
    }
}
