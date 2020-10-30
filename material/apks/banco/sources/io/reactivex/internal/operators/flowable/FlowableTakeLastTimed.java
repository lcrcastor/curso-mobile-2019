package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final int f;
    final boolean g;

    static final class TakeLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        final Subscriber<? super T> a;
        final long b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        final SpscLinkedArrayQueue<Object> f;
        final boolean g;
        Subscription h;
        final AtomicLong i = new AtomicLong();
        volatile boolean j;
        volatile boolean k;
        Throwable l;

        TakeLastTimedSubscriber(Subscriber<? super T> subscriber, long j2, long j3, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
            this.a = subscriber;
            this.b = j2;
            this.c = j3;
            this.d = timeUnit;
            this.e = scheduler;
            this.f = new SpscLinkedArrayQueue<>(i2);
            this.g = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
            long now = this.e.now(this.d);
            spscLinkedArrayQueue.offer(Long.valueOf(now), t);
            a(now, spscLinkedArrayQueue);
        }

        public void onError(Throwable th) {
            if (this.g) {
                a(this.e.now(this.d), this.f);
            }
            this.l = th;
            this.k = true;
            a();
        }

        public void onComplete() {
            a(this.e.now(this.d), this.f);
            this.k = true;
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a(long j2, SpscLinkedArrayQueue<Object> spscLinkedArrayQueue) {
            long j3 = this.c;
            long j4 = this.b;
            boolean z = j4 == Long.MAX_VALUE;
            while (!spscLinkedArrayQueue.isEmpty()) {
                if (((Long) spscLinkedArrayQueue.peek()).longValue() < j2 - j3 || (!z && ((long) (spscLinkedArrayQueue.size() >> 1)) > j4)) {
                    spscLinkedArrayQueue.poll();
                    spscLinkedArrayQueue.poll();
                } else {
                    return;
                }
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.i, j2);
                a();
            }
        }

        public void cancel() {
            if (!this.j) {
                this.j = true;
                this.h.cancel();
                if (getAndIncrement() == 0) {
                    this.f.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.f;
                boolean z = this.g;
                int i2 = 1;
                do {
                    if (this.k) {
                        if (!a(spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                            long j2 = this.i.get();
                            long j3 = 0;
                            while (true) {
                                if (!a(spscLinkedArrayQueue.peek() == null, subscriber, z)) {
                                    if (j2 != j3) {
                                        spscLinkedArrayQueue.poll();
                                        subscriber.onNext(spscLinkedArrayQueue.poll());
                                        j3++;
                                    } else if (j3 != 0) {
                                        BackpressureHelper.produced(this.i, j3);
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    i2 = addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, Subscriber<? super T> subscriber, boolean z2) {
            if (this.j) {
                this.f.clear();
                return true;
            }
            if (!z2) {
                Throwable th = this.l;
                if (th != null) {
                    this.f.clear();
                    subscriber.onError(th);
                    return true;
                } else if (z) {
                    subscriber.onComplete();
                    return true;
                }
            } else if (z) {
                Throwable th2 = this.l;
                if (th2 != null) {
                    subscriber.onError(th2);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            return false;
        }
    }

    public FlowableTakeLastTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = i;
        this.g = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        TakeLastTimedSubscriber takeLastTimedSubscriber = new TakeLastTimedSubscriber(subscriber, this.b, this.c, this.d, this.e, this.f, this.g);
        flowable.subscribe((FlowableSubscriber<? super T>) takeLastTimedSubscriber);
    }
}
