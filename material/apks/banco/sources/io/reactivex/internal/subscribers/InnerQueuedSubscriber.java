package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class InnerQueuedSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
    private static final long serialVersionUID = 22876611072430776L;
    final InnerQueuedSubscriberSupport<T> a;
    final int b;
    final int c;
    volatile SimpleQueue<T> d;
    volatile boolean e;
    long f;
    int g;

    public InnerQueuedSubscriber(InnerQueuedSubscriberSupport<T> innerQueuedSubscriberSupport, int i) {
        this.a = innerQueuedSubscriberSupport;
        this.b = i;
        this.c = i - (i >> 2);
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int requestFusion = queueSubscription.requestFusion(3);
                if (requestFusion == 1) {
                    this.g = requestFusion;
                    this.d = queueSubscription;
                    this.e = true;
                    this.a.innerComplete(this);
                    return;
                } else if (requestFusion == 2) {
                    this.g = requestFusion;
                    this.d = queueSubscription;
                    QueueDrainHelper.request(subscription, this.b);
                    return;
                }
            }
            this.d = QueueDrainHelper.createQueue(this.b);
            QueueDrainHelper.request(subscription, this.b);
        }
    }

    public void onNext(T t) {
        if (this.g == 0) {
            this.a.innerNext(this, t);
        } else {
            this.a.drain();
        }
    }

    public void onError(Throwable th) {
        this.a.innerError(this, th);
    }

    public void onComplete() {
        this.a.innerComplete(this);
    }

    public void request(long j) {
        if (this.g != 1) {
            long j2 = this.f + j;
            if (j2 >= ((long) this.c)) {
                this.f = 0;
                ((Subscription) get()).request(j2);
                return;
            }
            this.f = j2;
        }
    }

    public void requestOne() {
        if (this.g != 1) {
            long j = this.f + 1;
            if (j == ((long) this.c)) {
                this.f = 0;
                ((Subscription) get()).request(j);
                return;
            }
            this.f = j;
        }
    }

    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean isDone() {
        return this.e;
    }

    public void setDone() {
        this.e = true;
    }

    public SimpleQueue<T> queue() {
        return this.d;
    }
}
