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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

public final class BlockingFlowableNext<T> implements Iterable<T> {
    final Publisher<? extends T> a;

    static final class NextIterator<T> implements Iterator<T> {
        private final NextSubscriber<T> a;
        private final Publisher<? extends T> b;
        private T c;
        private boolean d = true;
        private boolean e = true;
        private Throwable f;
        private boolean g;

        NextIterator(Publisher<? extends T> publisher, NextSubscriber<T> nextSubscriber) {
            this.b = publisher;
            this.a = nextSubscriber;
        }

        public boolean hasNext() {
            if (this.f != null) {
                throw ExceptionHelper.wrapOrThrow(this.f);
            }
            boolean z = false;
            if (!this.d) {
                return false;
            }
            if (!this.e || a()) {
                z = true;
            }
            return z;
        }

        private boolean a() {
            try {
                if (!this.g) {
                    this.g = true;
                    this.a.b();
                    Flowable.fromPublisher(this.b).materialize().subscribe((FlowableSubscriber<? super T>) this.a);
                }
                Notification a2 = this.a.a();
                if (a2.isOnNext()) {
                    this.e = false;
                    this.c = a2.getValue();
                    return true;
                }
                this.d = false;
                if (a2.isOnComplete()) {
                    return false;
                }
                if (a2.isOnError()) {
                    this.f = a2.getError();
                    throw ExceptionHelper.wrapOrThrow(this.f);
                }
                throw new IllegalStateException("Should not reach here");
            } catch (InterruptedException e2) {
                this.a.dispose();
                this.f = e2;
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }

        public T next() {
            if (this.f != null) {
                throw ExceptionHelper.wrapOrThrow(this.f);
            } else if (hasNext()) {
                this.e = true;
                return this.c;
            } else {
                throw new NoSuchElementException("No more elements");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    static final class NextSubscriber<T> extends DisposableSubscriber<Notification<T>> {
        final AtomicInteger a = new AtomicInteger();
        private final BlockingQueue<Notification<T>> b = new ArrayBlockingQueue(1);

        public void onComplete() {
        }

        NextSubscriber() {
        }

        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        /* renamed from: a */
        public void onNext(Notification<T> notification) {
            if (this.a.getAndSet(0) == 1 || !notification.isOnNext()) {
                while (!this.b.offer(notification)) {
                    Notification<T> notification2 = (Notification) this.b.poll();
                    if (notification2 != null && !notification2.isOnNext()) {
                        notification = notification2;
                    }
                }
            }
        }

        public Notification<T> a() {
            b();
            BlockingHelper.verifyNonBlocking();
            return (Notification) this.b.take();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a.set(1);
        }
    }

    public BlockingFlowableNext(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    public Iterator<T> iterator() {
        return new NextIterator(this.a, new NextSubscriber());
    }
}
