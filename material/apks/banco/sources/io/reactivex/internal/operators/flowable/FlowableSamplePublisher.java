package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSamplePublisher<T> extends Flowable<T> {
    final Publisher<T> b;
    final Publisher<?> c;
    final boolean d;

    static final class SampleMainEmitLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        final AtomicInteger a = new AtomicInteger();
        volatile boolean b;

        SampleMainEmitLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = true;
            if (this.a.getAndIncrement() == 0) {
                e();
                this.c.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.b = true;
            if (this.a.getAndIncrement() == 0) {
                e();
                this.c.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.a.getAndIncrement() == 0) {
                do {
                    boolean z = this.b;
                    e();
                    if (z) {
                        this.c.onComplete();
                        return;
                    }
                } while (this.a.decrementAndGet() != 0);
            }
        }
    }

    static final class SampleMainNoLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        SampleMainNoLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.c.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            e();
        }
    }

    static abstract class SamplePublisherSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> c;
        final Publisher<?> d;
        final AtomicLong e = new AtomicLong();
        final AtomicReference<Subscription> f = new AtomicReference<>();
        Subscription g;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void b();

        /* access modifiers changed from: 0000 */
        public abstract void c();

        SamplePublisherSubscriber(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            this.c = subscriber;
            this.d = publisher;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.c.onSubscribe(this);
                if (this.f.get() == null) {
                    this.d.subscribe(new SamplerSubscriber(this));
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.f);
            this.c.onError(th);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.f);
            a();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Subscription subscription) {
            return SubscriptionHelper.setOnce(this.f, subscription);
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.e, j);
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.f);
            this.g.cancel();
        }

        public void a(Throwable th) {
            this.g.cancel();
            this.c.onError(th);
        }

        public void d() {
            this.g.cancel();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            Object andSet = getAndSet(null);
            if (andSet == null) {
                return;
            }
            if (this.e.get() != 0) {
                this.c.onNext(andSet);
                BackpressureHelper.produced(this.e, 1);
                return;
            }
            cancel();
            this.c.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
    }

    static final class SamplerSubscriber<T> implements FlowableSubscriber<Object> {
        final SamplePublisherSubscriber<T> a;

        SamplerSubscriber(SamplePublisherSubscriber<T> samplePublisherSubscriber) {
            this.a = samplePublisherSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (this.a.a(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            this.a.c();
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.d();
        }
    }

    public FlowableSamplePublisher(Publisher<T> publisher, Publisher<?> publisher2, boolean z) {
        this.b = publisher;
        this.c = publisher2;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.d) {
            this.b.subscribe(new SampleMainEmitLast(serializedSubscriber, this.c));
        } else {
            this.b.subscribe(new SampleMainNoLast(serializedSubscriber, this.c));
        }
    }
}
