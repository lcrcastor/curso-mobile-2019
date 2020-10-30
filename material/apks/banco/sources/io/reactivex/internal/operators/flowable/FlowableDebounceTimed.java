package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDebounceTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;

    static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 6812032969491025141L;
        final T a;
        final long b;
        final DebounceTimedSubscriber<T> c;
        final AtomicBoolean d = new AtomicBoolean();

        DebounceEmitter(T t, long j, DebounceTimedSubscriber<T> debounceTimedSubscriber) {
            this.a = t;
            this.b = j;
            this.c = debounceTimedSubscriber;
        }

        public void run() {
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.d.compareAndSet(false, true)) {
                this.c.a(this.b, this.a, this);
            }
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        public void a(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }

    static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -9102637559663639004L;
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Worker d;
        Subscription e;
        final SequentialDisposable f = new SequentialDisposable();
        volatile long g;
        boolean h;

        DebounceTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker) {
            this.a = subscriber;
            this.b = j;
            this.c = timeUnit;
            this.d = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                long j = this.g + 1;
                this.g = j;
                Disposable disposable = (Disposable) this.f.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                DebounceEmitter debounceEmitter = new DebounceEmitter(t, j, this);
                if (this.f.replace(debounceEmitter)) {
                    debounceEmitter.a(this.d.schedule(debounceEmitter, this.b, this.c));
                }
            }
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            this.a.onError(th);
            this.d.dispose();
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                Disposable disposable = (Disposable) this.f.get();
                if (!DisposableHelper.isDisposed(disposable)) {
                    DebounceEmitter debounceEmitter = (DebounceEmitter) disposable;
                    if (debounceEmitter != null) {
                        debounceEmitter.a();
                    }
                    DisposableHelper.dispose(this.f);
                    this.a.onComplete();
                    this.d.dispose();
                }
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void cancel() {
            this.e.cancel();
            this.d.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a(long j, T t, DebounceEmitter<T> debounceEmitter) {
            if (j != this.g) {
                return;
            }
            if (get() != 0) {
                this.a.onNext(t);
                BackpressureHelper.produced(this, 1);
                debounceEmitter.dispose();
                return;
            }
            cancel();
            this.a.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
    }

    public FlowableDebounceTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        DebounceTimedSubscriber debounceTimedSubscriber = new DebounceTimedSubscriber(new SerializedSubscriber(subscriber), this.b, this.c, this.d.createWorker());
        flowable.subscribe((FlowableSubscriber<? super T>) debounceTimedSubscriber);
    }
}
