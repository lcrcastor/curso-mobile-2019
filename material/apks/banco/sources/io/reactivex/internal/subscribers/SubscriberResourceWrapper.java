package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SubscriberResourceWrapper<T> extends AtomicReference<Disposable> implements FlowableSubscriber<T>, Disposable, Subscription {
    private static final long serialVersionUID = -8612022020200669122L;
    final Subscriber<? super T> a;
    final AtomicReference<Subscription> b = new AtomicReference<>();

    public SubscriberResourceWrapper(Subscriber<? super T> subscriber) {
        this.a = subscriber;
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.b, subscription)) {
            this.a.onSubscribe(this);
        }
    }

    public void onNext(T t) {
        this.a.onNext(t);
    }

    public void onError(Throwable th) {
        DisposableHelper.dispose(this);
        this.a.onError(th);
    }

    public void onComplete() {
        DisposableHelper.dispose(this);
        this.a.onComplete();
    }

    public void request(long j) {
        if (SubscriptionHelper.validate(j)) {
            ((Subscription) this.b.get()).request(j);
        }
    }

    public void dispose() {
        SubscriptionHelper.cancel(this.b);
        DisposableHelper.dispose(this);
    }

    public boolean isDisposed() {
        return this.b.get() == SubscriptionHelper.CANCELLED;
    }

    public void cancel() {
        dispose();
    }

    public void setResource(Disposable disposable) {
        DisposableHelper.set(this, disposable);
    }
}
