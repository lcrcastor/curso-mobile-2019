package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class AsyncSubscription extends AtomicLong implements Disposable, Subscription {
    private static final long serialVersionUID = 7028635084060361255L;
    final AtomicReference<Subscription> a;
    final AtomicReference<Disposable> b;

    public AsyncSubscription() {
        this.b = new AtomicReference<>();
        this.a = new AtomicReference<>();
    }

    public AsyncSubscription(Disposable disposable) {
        this();
        this.b.lazySet(disposable);
    }

    public void request(long j) {
        SubscriptionHelper.deferredRequest(this.a, this, j);
    }

    public void cancel() {
        dispose();
    }

    public void dispose() {
        SubscriptionHelper.cancel(this.a);
        DisposableHelper.dispose(this.b);
    }

    public boolean isDisposed() {
        return this.a.get() == SubscriptionHelper.CANCELLED;
    }

    public boolean setResource(Disposable disposable) {
        return DisposableHelper.set(this.b, disposable);
    }

    public boolean replaceResource(Disposable disposable) {
        return DisposableHelper.replace(this.b, disposable);
    }

    public void setSubscription(Subscription subscription) {
        SubscriptionHelper.deferredSetOnce(this.a, this, subscription);
    }
}
