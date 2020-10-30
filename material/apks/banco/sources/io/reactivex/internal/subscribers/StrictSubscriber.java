package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class StrictSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
    private static final long serialVersionUID = -4945028590049415624L;
    final Subscriber<? super T> a;
    final AtomicThrowable b = new AtomicThrowable();
    final AtomicLong c = new AtomicLong();
    final AtomicReference<Subscription> d = new AtomicReference<>();
    final AtomicBoolean e = new AtomicBoolean();
    volatile boolean f;

    public StrictSubscriber(Subscriber<? super T> subscriber) {
        this.a = subscriber;
    }

    public void request(long j) {
        if (j <= 0) {
            cancel();
            StringBuilder sb = new StringBuilder();
            sb.append("§3.9 violated: positive request amount required but it was ");
            sb.append(j);
            onError(new IllegalArgumentException(sb.toString()));
            return;
        }
        SubscriptionHelper.deferredRequest(this.d, this.c, j);
    }

    public void cancel() {
        if (!this.f) {
            SubscriptionHelper.cancel(this.d);
        }
    }

    public void onSubscribe(Subscription subscription) {
        if (this.e.compareAndSet(false, true)) {
            this.a.onSubscribe(this);
            SubscriptionHelper.deferredSetOnce(this.d, this.c, subscription);
            return;
        }
        subscription.cancel();
        cancel();
        onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
    }

    public void onNext(T t) {
        HalfSerializer.onNext(this.a, t, (AtomicInteger) this, this.b);
    }

    public void onError(Throwable th) {
        this.f = true;
        HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.b);
    }

    public void onComplete() {
        this.f = true;
        HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.b);
    }
}
