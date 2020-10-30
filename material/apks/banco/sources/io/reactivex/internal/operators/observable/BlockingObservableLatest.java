package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public final class BlockingObservableLatest<T> implements Iterable<T> {
    final ObservableSource<T> a;

    static final class BlockingObservableLatestIterator<T> extends DisposableObserver<Notification<T>> implements Iterator<T> {
        Notification<T> a;
        final Semaphore b = new Semaphore(0);
        final AtomicReference<Notification<T>> c = new AtomicReference<>();

        public void onComplete() {
        }

        BlockingObservableLatestIterator() {
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.c.getAndSet(notification) == null) {
                this.b.release();
            }
        }

        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        public boolean hasNext() {
            if (this.a == null || !this.a.isOnError()) {
                if (this.a == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.b.acquire();
                        Notification<T> notification = (Notification) this.c.getAndSet(null);
                        this.a = notification;
                        if (notification.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(notification.getError());
                        }
                    } catch (InterruptedException e) {
                        dispose();
                        this.a = Notification.createOnError(e);
                        throw ExceptionHelper.wrapOrThrow(e);
                    }
                }
                return this.a.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.a.getError());
        }

        public T next() {
            if (hasNext()) {
                T value = this.a.getValue();
                this.a = null;
                return value;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    public BlockingObservableLatest(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    public Iterator<T> iterator() {
        BlockingObservableLatestIterator blockingObservableLatestIterator = new BlockingObservableLatestIterator();
        Observable.wrap(this.a).materialize().subscribe((Observer<? super T>) blockingObservableLatestIterator);
        return blockingObservableLatestIterator;
    }
}
