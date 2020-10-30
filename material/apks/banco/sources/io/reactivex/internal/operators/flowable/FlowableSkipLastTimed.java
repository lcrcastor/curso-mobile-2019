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

public final class FlowableSkipLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final int e;
    final boolean f;

    static final class SkipLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        final Subscriber<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final SpscLinkedArrayQueue<Object> e;
        final boolean f;
        Subscription g;
        final AtomicLong h = new AtomicLong();
        volatile boolean i;
        volatile boolean j;
        Throwable k;

        SkipLastTimedSubscriber(Subscriber<? super T> subscriber, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, boolean z) {
            this.a = subscriber;
            this.b = j2;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = new SpscLinkedArrayQueue<>(i2);
            this.f = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            this.e.offer(Long.valueOf(this.d.now(this.c)), t);
            a();
        }

        public void onError(Throwable th) {
            this.k = th;
            this.j = true;
            a();
        }

        public void onComplete() {
            this.j = true;
            a();
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.h, j2);
                a();
            }
        }

        public void cancel() {
            if (!this.i) {
                this.i = true;
                this.g.cancel();
                if (getAndIncrement() == 0) {
                    this.e.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.e;
                boolean z = this.f;
                TimeUnit timeUnit = this.c;
                Scheduler scheduler = this.d;
                long j2 = this.b;
                int i2 = 1;
                do {
                    long j3 = this.h.get();
                    long j4 = 0;
                    while (j4 != j3) {
                        boolean z2 = this.j;
                        Long l = (Long) spscLinkedArrayQueue.peek();
                        boolean z3 = l == null;
                        long now = scheduler.now(timeUnit);
                        if (!z3 && l.longValue() > now - j2) {
                            z3 = true;
                        }
                        if (!a(z2, z3, subscriber, z)) {
                            if (z3) {
                                break;
                            }
                            spscLinkedArrayQueue.poll();
                            subscriber.onNext(spscLinkedArrayQueue.poll());
                            j4++;
                        } else {
                            return;
                        }
                    }
                    if (j4 != 0) {
                        BackpressureHelper.produced(this.h, j4);
                    }
                    i2 = addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3) {
            if (this.i) {
                this.e.clear();
                return true;
            }
            if (z) {
                if (!z3) {
                    Throwable th = this.k;
                    if (th != null) {
                        this.e.clear();
                        subscriber.onError(th);
                        return true;
                    } else if (z2) {
                        subscriber.onComplete();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = this.k;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public FlowableSkipLastTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = i;
        this.f = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        SkipLastTimedSubscriber skipLastTimedSubscriber = new SkipLastTimedSubscriber(subscriber, this.b, this.c, this.d, this.e, this.f);
        flowable.subscribe((FlowableSubscriber<? super T>) skipLastTimedSubscriber);
    }
}
