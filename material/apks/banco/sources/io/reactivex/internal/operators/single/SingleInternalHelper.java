package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public final class SingleInternalHelper {

    enum NoSuchElementCallable implements Callable<NoSuchElementException> {
        INSTANCE;

        /* renamed from: a */
        public NoSuchElementException call() {
            return new NoSuchElementException();
        }
    }

    enum ToFlowable implements Function<SingleSource, Publisher> {
        INSTANCE;

        /* renamed from: a */
        public Publisher apply(SingleSource singleSource) {
            return new SingleToFlowable(singleSource);
        }
    }

    static final class ToFlowableIterable<T> implements Iterable<Flowable<T>> {
        private final Iterable<? extends SingleSource<? extends T>> a;

        ToFlowableIterable(Iterable<? extends SingleSource<? extends T>> iterable) {
            this.a = iterable;
        }

        public Iterator<Flowable<T>> iterator() {
            return new ToFlowableIterator(this.a.iterator());
        }
    }

    static final class ToFlowableIterator<T> implements Iterator<Flowable<T>> {
        private final Iterator<? extends SingleSource<? extends T>> a;

        ToFlowableIterator(Iterator<? extends SingleSource<? extends T>> it) {
            this.a = it;
        }

        public boolean hasNext() {
            return this.a.hasNext();
        }

        /* renamed from: a */
        public Flowable<T> next() {
            return new SingleToFlowable((SingleSource) this.a.next());
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    enum ToObservable implements Function<SingleSource, Observable> {
        INSTANCE;

        /* renamed from: a */
        public Observable apply(SingleSource singleSource) {
            return new SingleToObservable(singleSource);
        }
    }

    private SingleInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Callable<NoSuchElementException> emptyThrower() {
        return NoSuchElementCallable.INSTANCE;
    }

    public static <T> Function<SingleSource<? extends T>, Publisher<? extends T>> toFlowable() {
        return ToFlowable.INSTANCE;
    }

    public static <T> Iterable<? extends Flowable<T>> iterableToFlowable(Iterable<? extends SingleSource<? extends T>> iterable) {
        return new ToFlowableIterable(iterable);
    }

    public static <T> Function<SingleSource<? extends T>, Observable<? extends T>> toObservable() {
        return ToObservable.INSTANCE;
    }
}
