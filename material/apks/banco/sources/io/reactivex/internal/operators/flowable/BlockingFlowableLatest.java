package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

public final class BlockingFlowableLatest<T> implements Iterable<T> {
    final Publisher<? extends T> a;

    static final class LatestSubscriberIterator<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
        final Semaphore a = new Semaphore(0);
        final AtomicReference<Notification<T>> b = new AtomicReference<>();
        Notification<T> c;

        public void onComplete() {
        }

        LatestSubscriberIterator() {
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.b.getAndSet(notification) == null) {
                this.a.release();
            }
        }

        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        public boolean hasNext() {
            if (this.c == null || !this.c.isOnError()) {
                if ((this.c == null || this.c.isOnNext()) && this.c == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.a.acquire();
                        Notification<T> notification = (Notification) this.b.getAndSet(null);
                        this.c = notification;
                        if (notification.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(notification.getError());
                        }
                    } catch (InterruptedException e) {
                        dispose();
                        this.c = Notification.createOnError(e);
                        throw ExceptionHelper.wrapOrThrow(e);
                    }
                }
                return this.c.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.c.getError());
        }

        public T next() {
            if (!hasNext() || !this.c.isOnNext()) {
                throw new NoSuchElementException();
            }
            T value = this.c.getValue();
            this.c = null;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    public BlockingFlowableLatest(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    public Iterator<T> iterator() {
        LatestSubscriberIterator latestSubscriberIterator = new LatestSubscriberIterator();
        Flowable.fromPublisher(this.a).materialize().subscribe((FlowableSubscriber<? super T>) latestSubscriberIterator);
        return latestSubscriberIterator;
    }
}
