package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRefCount<T> extends AbstractFlowableWithUpstream<T, T> {
    final ConnectableFlowable<T> b;
    volatile CompositeDisposable c = new CompositeDisposable();
    final AtomicInteger d = new AtomicInteger();
    final ReentrantLock e = new ReentrantLock();

    final class ConnectionSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 152064694420235350L;
        final Subscriber<? super T> a;
        final CompositeDisposable b;
        final Disposable c;
        final AtomicLong d = new AtomicLong();

        ConnectionSubscriber(Subscriber<? super T> subscriber, CompositeDisposable compositeDisposable, Disposable disposable) {
            this.a = subscriber;
            this.b = compositeDisposable;
            this.c = disposable;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this, this.d, subscription);
        }

        public void onError(Throwable th) {
            a();
            this.a.onError(th);
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onComplete() {
            a();
            this.a.onComplete();
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this, this.d, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
            this.c.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            FlowableRefCount.this.e.lock();
            try {
                if (FlowableRefCount.this.c == this.b) {
                    if (FlowableRefCount.this.b instanceof Disposable) {
                        ((Disposable) FlowableRefCount.this.b).dispose();
                    }
                    FlowableRefCount.this.c.dispose();
                    FlowableRefCount.this.c = new CompositeDisposable();
                    FlowableRefCount.this.d.set(0);
                }
            } finally {
                FlowableRefCount.this.e.unlock();
            }
        }
    }

    final class DisposeConsumer implements Consumer<Disposable> {
        private final Subscriber<? super T> b;
        private final AtomicBoolean c;

        DisposeConsumer(Subscriber<? super T> subscriber, AtomicBoolean atomicBoolean) {
            this.b = subscriber;
            this.c = atomicBoolean;
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            try {
                FlowableRefCount.this.c.add(disposable);
                FlowableRefCount.this.a(this.b, FlowableRefCount.this.c);
            } finally {
                FlowableRefCount.this.e.unlock();
                this.c.set(false);
            }
        }
    }

    final class DisposeTask implements Runnable {
        private final CompositeDisposable b;

        DisposeTask(CompositeDisposable compositeDisposable) {
            this.b = compositeDisposable;
        }

        public void run() {
            FlowableRefCount.this.e.lock();
            try {
                if (FlowableRefCount.this.c == this.b && FlowableRefCount.this.d.decrementAndGet() == 0) {
                    if (FlowableRefCount.this.b instanceof Disposable) {
                        ((Disposable) FlowableRefCount.this.b).dispose();
                    }
                    FlowableRefCount.this.c.dispose();
                    FlowableRefCount.this.c = new CompositeDisposable();
                }
            } finally {
                FlowableRefCount.this.e.unlock();
            }
        }
    }

    public FlowableRefCount(ConnectableFlowable<T> connectableFlowable) {
        super(connectableFlowable);
        this.b = connectableFlowable;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.lock();
        if (this.d.incrementAndGet() == 1) {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            try {
                this.b.connect(a(subscriber, atomicBoolean));
            } finally {
                if (atomicBoolean.get()) {
                    this.e.unlock();
                }
            }
        } else {
            try {
                a(subscriber, this.c);
            } finally {
                this.e.unlock();
            }
        }
    }

    private Consumer<Disposable> a(Subscriber<? super T> subscriber, AtomicBoolean atomicBoolean) {
        return new DisposeConsumer(subscriber, atomicBoolean);
    }

    /* access modifiers changed from: 0000 */
    public void a(Subscriber<? super T> subscriber, CompositeDisposable compositeDisposable) {
        ConnectionSubscriber connectionSubscriber = new ConnectionSubscriber(subscriber, compositeDisposable, a(compositeDisposable));
        subscriber.onSubscribe(connectionSubscriber);
        this.b.subscribe((FlowableSubscriber<? super T>) connectionSubscriber);
    }

    private Disposable a(CompositeDisposable compositeDisposable) {
        return Disposables.fromRunnable(new DisposeTask(compositeDisposable));
    }
}
