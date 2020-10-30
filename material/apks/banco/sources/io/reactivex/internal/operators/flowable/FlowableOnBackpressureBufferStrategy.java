package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBufferStrategy<T> extends AbstractFlowableWithUpstream<T, T> {
    final long b;
    final Action c;
    final BackpressureOverflowStrategy d;

    static final class OnBackpressureBufferStrategySubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 3240706908776709697L;
        final Subscriber<? super T> a;
        final Action b;
        final BackpressureOverflowStrategy c;
        final long d;
        final AtomicLong e = new AtomicLong();
        final Deque<T> f = new ArrayDeque();
        Subscription g;
        volatile boolean h;
        volatile boolean i;
        Throwable j;

        OnBackpressureBufferStrategySubscriber(Subscriber<? super T> subscriber, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy, long j2) {
            this.a = subscriber;
            this.b = action;
            this.c = backpressureOverflowStrategy;
            this.d = j2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
            r1 = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNext(T r7) {
            /*
                r6 = this;
                boolean r0 = r6.i
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                java.util.Deque<T> r0 = r6.f
                monitor-enter(r0)
                int r1 = r0.size()     // Catch:{ all -> 0x0064 }
                long r1 = (long) r1     // Catch:{ all -> 0x0064 }
                long r3 = r6.d     // Catch:{ all -> 0x0064 }
                int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                r1 = 0
                r2 = 1
                if (r5 != 0) goto L_0x0032
                int[] r3 = io.reactivex.internal.operators.flowable.FlowableOnBackpressureBufferStrategy.AnonymousClass1.a     // Catch:{ all -> 0x0064 }
                io.reactivex.BackpressureOverflowStrategy r4 = r6.c     // Catch:{ all -> 0x0064 }
                int r4 = r4.ordinal()     // Catch:{ all -> 0x0064 }
                r3 = r3[r4]     // Catch:{ all -> 0x0064 }
                switch(r3) {
                    case 1: goto L_0x002a;
                    case 2: goto L_0x0023;
                    default: goto L_0x0022;
                }     // Catch:{ all -> 0x0064 }
            L_0x0022:
                goto L_0x0036
            L_0x0023:
                r0.poll()     // Catch:{ all -> 0x0064 }
                r0.offer(r7)     // Catch:{ all -> 0x0064 }
                goto L_0x0030
            L_0x002a:
                r0.pollLast()     // Catch:{ all -> 0x0064 }
                r0.offer(r7)     // Catch:{ all -> 0x0064 }
            L_0x0030:
                r1 = 1
                goto L_0x0035
            L_0x0032:
                r0.offer(r7)     // Catch:{ all -> 0x0064 }
            L_0x0035:
                r2 = 0
            L_0x0036:
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                if (r1 == 0) goto L_0x0050
                io.reactivex.functions.Action r7 = r6.b
                if (r7 == 0) goto L_0x0063
                io.reactivex.functions.Action r7 = r6.b     // Catch:{ Throwable -> 0x0043 }
                r7.run()     // Catch:{ Throwable -> 0x0043 }
                goto L_0x0063
            L_0x0043:
                r7 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r7)
                org.reactivestreams.Subscription r0 = r6.g
                r0.cancel()
                r6.onError(r7)
                goto L_0x0063
            L_0x0050:
                if (r2 == 0) goto L_0x0060
                org.reactivestreams.Subscription r7 = r6.g
                r7.cancel()
                io.reactivex.exceptions.MissingBackpressureException r7 = new io.reactivex.exceptions.MissingBackpressureException
                r7.<init>()
                r6.onError(r7)
                goto L_0x0063
            L_0x0060:
                r6.a()
            L_0x0063:
                return
            L_0x0064:
                r7 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableOnBackpressureBufferStrategy.OnBackpressureBufferStrategySubscriber.onNext(java.lang.Object):void");
        }

        public void onError(Throwable th) {
            if (this.i) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.j = th;
            this.i = true;
            a();
        }

        public void onComplete() {
            this.i = true;
            a();
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.e, j2);
                a();
            }
        }

        public void cancel() {
            this.h = true;
            this.g.cancel();
            if (getAndIncrement() == 0) {
                a(this.f);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Deque<T> deque) {
            synchronized (deque) {
                deque.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            boolean isEmpty;
            Object poll;
            if (getAndIncrement() == 0) {
                Deque<T> deque = this.f;
                Subscriber<? super T> subscriber = this.a;
                int i2 = 1;
                do {
                    long j2 = this.e.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        if (this.h) {
                            a(deque);
                            return;
                        }
                        boolean z = this.i;
                        synchronized (deque) {
                            poll = deque.poll();
                        }
                        boolean z2 = poll == null;
                        if (z) {
                            Throwable th = this.j;
                            if (th != null) {
                                a(deque);
                                subscriber.onError(th);
                                return;
                            } else if (z2) {
                                subscriber.onComplete();
                                return;
                            }
                        }
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(poll);
                        j3++;
                    }
                    if (j3 == j2) {
                        if (this.h) {
                            a(deque);
                            return;
                        }
                        boolean z3 = this.i;
                        synchronized (deque) {
                            isEmpty = deque.isEmpty();
                        }
                        if (z3) {
                            Throwable th2 = this.j;
                            if (th2 != null) {
                                a(deque);
                                subscriber.onError(th2);
                                return;
                            } else if (isEmpty) {
                                subscriber.onComplete();
                                return;
                            }
                        }
                    }
                    if (j3 != 0) {
                        BackpressureHelper.produced(this.e, j3);
                    }
                    i2 = addAndGet(-i2);
                } while (i2 != 0);
            }
        }
    }

    public FlowableOnBackpressureBufferStrategy(Flowable<T> flowable, long j, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy) {
        super(flowable);
        this.b = j;
        this.c = action;
        this.d = backpressureOverflowStrategy;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        OnBackpressureBufferStrategySubscriber onBackpressureBufferStrategySubscriber = new OnBackpressureBufferStrategySubscriber(subscriber, this.c, this.d, this.b);
        flowable.subscribe((FlowableSubscriber<? super T>) onBackpressureBufferStrategySubscriber);
    }
}
