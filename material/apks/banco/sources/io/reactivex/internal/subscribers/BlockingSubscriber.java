package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class BlockingSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
    public static final Object TERMINATED = new Object();
    private static final long serialVersionUID = -4875965440900746268L;
    final Queue<Object> a;

    public BlockingSubscriber(Queue<Object> queue) {
        this.a = queue;
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this, subscription)) {
            this.a.offer(NotificationLite.subscription(this));
        }
    }

    public void onNext(T t) {
        this.a.offer(NotificationLite.next(t));
    }

    public void onError(Throwable th) {
        this.a.offer(NotificationLite.error(th));
    }

    public void onComplete() {
        this.a.offer(NotificationLite.complete());
    }

    public void request(long j) {
        ((Subscription) get()).request(j);
    }

    public void cancel() {
        if (SubscriptionHelper.cancel(this)) {
            this.a.offer(TERMINATED);
        }
    }

    public boolean isCancelled() {
        return get() == SubscriptionHelper.CANCELLED;
    }
}
