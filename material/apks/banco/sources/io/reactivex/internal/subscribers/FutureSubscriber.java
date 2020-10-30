package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
    T a;
    Throwable b;
    final AtomicReference<Subscription> c = new AtomicReference<>();

    public void cancel() {
    }

    public void request(long j) {
    }

    public FutureSubscriber() {
        super(1);
    }

    public boolean cancel(boolean z) {
        Subscription subscription;
        do {
            subscription = (Subscription) this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                return false;
            }
        } while (!this.c.compareAndSet(subscription, SubscriptionHelper.CANCELLED));
        if (subscription != null) {
            subscription.cancel();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return SubscriptionHelper.isCancelled((Subscription) this.c.get());
    }

    public boolean isDone() {
        return getCount() == 0;
    }

    public T get() {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw new ExecutionException(th);
    }

    public T get(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(j, timeUnit)) {
                throw new TimeoutException();
            }
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw new ExecutionException(th);
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.c, subscription)) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (this.a != null) {
            ((Subscription) this.c.get()).cancel();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.a = t;
    }

    public void onError(Throwable th) {
        Subscription subscription;
        do {
            subscription = (Subscription) this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = th;
        } while (!this.c.compareAndSet(subscription, this));
        countDown();
    }

    public void onComplete() {
        Subscription subscription;
        if (this.a == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        do {
            subscription = (Subscription) this.c.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                return;
            }
        } while (!this.c.compareAndSet(subscription, this));
        countDown();
    }
}
