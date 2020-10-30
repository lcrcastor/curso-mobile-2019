package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureLatest<T> extends AbstractFlowableWithUpstream<T, T> {

    static final class BackpressureLatestSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 163080509307634843L;
        final Subscriber<? super T> a;
        Subscription b;
        volatile boolean c;
        Throwable d;
        volatile boolean e;
        final AtomicLong f = new AtomicLong();
        final AtomicReference<T> g = new AtomicReference<>();

        BackpressureLatestSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            this.g.lazySet(t);
            a();
        }

        public void onError(Throwable th) {
            this.d = th;
            this.c = true;
            a();
        }

        public void onComplete() {
            this.c = true;
            a();
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.f, j);
                a();
            }
        }

        public void cancel() {
            if (!this.e) {
                this.e = true;
                this.b.cancel();
                if (getAndIncrement() == 0) {
                    this.g.lazySet(null);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            boolean z;
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                AtomicLong atomicLong = this.f;
                AtomicReference<T> atomicReference = this.g;
                int i = 1;
                do {
                    long j = 0;
                    while (true) {
                        z = false;
                        if (j == atomicLong.get()) {
                            break;
                        }
                        boolean z2 = this.c;
                        Object andSet = atomicReference.getAndSet(null);
                        boolean z3 = andSet == null;
                        if (!a(z2, z3, subscriber, atomicReference)) {
                            if (z3) {
                                break;
                            }
                            subscriber.onNext(andSet);
                            j++;
                        } else {
                            return;
                        }
                    }
                    if (j == atomicLong.get()) {
                        boolean z4 = this.c;
                        if (atomicReference.get() == null) {
                            z = true;
                        }
                        if (a(z4, z, subscriber, atomicReference)) {
                            return;
                        }
                    }
                    if (j != 0) {
                        BackpressureHelper.produced(atomicLong, j);
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, AtomicReference<T> atomicReference) {
            if (this.e) {
                atomicReference.lazySet(null);
                return true;
            }
            if (z) {
                Throwable th = this.d;
                if (th != null) {
                    atomicReference.lazySet(null);
                    subscriber.onError(th);
                    return true;
                } else if (z2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }
    }

    public FlowableOnBackpressureLatest(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new BackpressureLatestSubscriber<Object>(subscriber));
    }
}
