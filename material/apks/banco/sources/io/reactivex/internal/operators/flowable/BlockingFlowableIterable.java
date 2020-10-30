package io.reactivex.internal.operators.flowable;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscription;

public final class BlockingFlowableIterable<T> implements Iterable<T> {
    final Flowable<T> a;
    final int b;

    static final class BlockingFlowableIterator<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable, Runnable, Iterator<T> {
        private static final long serialVersionUID = 6695226475494099826L;
        final SpscArrayQueue<T> a;
        final long b;
        final long c;
        final Lock d = new ReentrantLock();
        final Condition e = this.d.newCondition();
        long f;
        volatile boolean g;
        Throwable h;

        BlockingFlowableIterator(int i) {
            this.a = new SpscArrayQueue<>(i);
            this.b = (long) i;
            this.c = (long) (i - (i >> 2));
        }

        public boolean hasNext() {
            while (true) {
                boolean z = this.g;
                boolean isEmpty = this.a.isEmpty();
                if (z) {
                    Throwable th = this.h;
                    if (th != null) {
                        throw ExceptionHelper.wrapOrThrow(th);
                    } else if (isEmpty) {
                        return false;
                    }
                }
                if (!isEmpty) {
                    return true;
                }
                BlockingHelper.verifyNonBlocking();
                this.d.lock();
                while (!this.g && this.a.isEmpty()) {
                    try {
                        this.e.await();
                    } catch (InterruptedException e2) {
                        run();
                        throw ExceptionHelper.wrapOrThrow(e2);
                    } catch (Throwable th2) {
                        this.d.unlock();
                        throw th2;
                    }
                }
                this.d.unlock();
            }
        }

        public T next() {
            if (hasNext()) {
                T poll = this.a.poll();
                long j = this.f + 1;
                if (j == this.c) {
                    this.f = 0;
                    ((Subscription) get()).request(j);
                } else {
                    this.f = j;
                }
                return poll;
            }
            throw new NoSuchElementException();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(this.b);
            }
        }

        public void onNext(T t) {
            if (!this.a.offer(t)) {
                SubscriptionHelper.cancel(this);
                onError(new MissingBackpressureException("Queue full?!"));
                return;
            }
            a();
        }

        public void onError(Throwable th) {
            this.h = th;
            this.g = true;
            a();
        }

        public void onComplete() {
            this.g = true;
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.d.lock();
            try {
                this.e.signalAll();
            } finally {
                this.d.unlock();
            }
        }

        public void run() {
            SubscriptionHelper.cancel(this);
            a();
        }

        public void remove() {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) get());
        }
    }

    public BlockingFlowableIterable(Flowable<T> flowable, int i) {
        this.a = flowable;
        this.b = i;
    }

    public Iterator<T> iterator() {
        BlockingFlowableIterator blockingFlowableIterator = new BlockingFlowableIterator(this.b);
        this.a.subscribe((FlowableSubscriber<? super T>) blockingFlowableIterator);
        return blockingFlowableIterator;
    }
}
