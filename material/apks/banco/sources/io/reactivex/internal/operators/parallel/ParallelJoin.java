package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelJoin<T> extends Flowable<T> {
    final ParallelFlowable<? extends T> b;
    final int c;
    final boolean d;

    static final class JoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 8410034718427740355L;
        final JoinSubscriptionBase<T> a;
        final int b;
        final int c;
        long d;
        volatile SimplePlainQueue<T> e;

        JoinInnerSubscriber(JoinSubscriptionBase<T> joinSubscriptionBase, int i) {
            this.a = joinSubscriptionBase;
            this.b = i;
            this.c = i - (i >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request((long) this.b);
            }
        }

        public void onNext(T t) {
            this.a.a(this, t);
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.a();
        }

        public void a() {
            long j = this.d + 1;
            if (j == ((long) this.c)) {
                this.d = 0;
                ((Subscription) get()).request(j);
                return;
            }
            this.d = j;
        }

        public void a(long j) {
            long j2 = this.d + j;
            if (j2 >= ((long) this.c)) {
                this.d = 0;
                ((Subscription) get()).request(j2);
                return;
            }
            this.d = j2;
        }

        public boolean b() {
            return SubscriptionHelper.cancel(this);
        }

        /* access modifiers changed from: 0000 */
        public SimplePlainQueue<T> c() {
            SimplePlainQueue<T> simplePlainQueue = this.e;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.b);
            this.e = spscArrayQueue;
            return spscArrayQueue;
        }
    }

    static final class JoinSubscription<T> extends JoinSubscriptionBase<T> {
        private static final long serialVersionUID = 6312374661811000451L;

        JoinSubscription(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        public void a(JoinInnerSubscriber<T> joinInnerSubscriber, T t) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.d.get() != 0) {
                    this.a.onNext(t);
                    if (this.d.get() != Long.MAX_VALUE) {
                        this.d.decrementAndGet();
                    }
                    joinInnerSubscriber.a(1);
                } else if (!joinInnerSubscriber.c().offer(t)) {
                    d();
                    MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Queue full?!");
                    if (this.c.compareAndSet(null, missingBackpressureException)) {
                        this.a.onError(missingBackpressureException);
                    } else {
                        RxJavaPlugins.onError(missingBackpressureException);
                    }
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!joinInnerSubscriber.c().offer(t)) {
                d();
                a(new MissingBackpressureException("Queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            c();
        }

        public void a(Throwable th) {
            if (this.c.compareAndSet(null, th)) {
                d();
                b();
            } else if (th != this.c.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        public void a() {
            this.f.decrementAndGet();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
            if (r12 == false) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
            if (r11 == false) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0066, code lost:
            r3.onComplete();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0069, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x006a, code lost:
            if (r11 == false) goto L_0x006d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void c() {
            /*
                r21 = this;
                r0 = r21
                io.reactivex.internal.operators.parallel.ParallelJoin$JoinInnerSubscriber[] r1 = r0.b
                int r2 = r1.length
                org.reactivestreams.Subscriber r3 = r0.a
                r5 = 1
            L_0x0008:
                java.util.concurrent.atomic.AtomicLong r6 = r0.d
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L_0x0011:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L_0x006f
                boolean r12 = r0.e
                if (r12 == 0) goto L_0x001d
                r21.e()
                return
            L_0x001d:
                io.reactivex.internal.util.AtomicThrowable r12 = r0.c
                java.lang.Object r12 = r12.get()
                java.lang.Throwable r12 = (java.lang.Throwable) r12
                if (r12 == 0) goto L_0x002e
                r21.e()
                r3.onError(r12)
                return
            L_0x002e:
                java.util.concurrent.atomic.AtomicInteger r12 = r0.f
                int r12 = r12.get()
                if (r12 != 0) goto L_0x0038
                r12 = 1
                goto L_0x0039
            L_0x0038:
                r12 = 0
            L_0x0039:
                r14 = r10
                r10 = 0
                r11 = 1
            L_0x003c:
                int r4 = r1.length
                if (r10 >= r4) goto L_0x0062
                r4 = r1[r10]
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r13 = r4.e
                if (r13 == 0) goto L_0x005f
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L_0x005f
                r3.onNext(r13)
                r4.a()
                r17 = 1
                long r19 = r14 + r17
                int r4 = (r19 > r6 ? 1 : (r19 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x005c
                r14 = r19
                goto L_0x0070
            L_0x005c:
                r14 = r19
                r11 = 0
            L_0x005f:
                int r10 = r10 + 1
                goto L_0x003c
            L_0x0062:
                if (r12 == 0) goto L_0x006a
                if (r11 == 0) goto L_0x006a
                r3.onComplete()
                return
            L_0x006a:
                if (r11 == 0) goto L_0x006d
                goto L_0x0070
            L_0x006d:
                r10 = r14
                goto L_0x0011
            L_0x006f:
                r14 = r10
            L_0x0070:
                int r4 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x00b7
                boolean r4 = r0.e
                if (r4 == 0) goto L_0x007c
                r21.e()
                return
            L_0x007c:
                io.reactivex.internal.util.AtomicThrowable r4 = r0.c
                java.lang.Object r4 = r4.get()
                java.lang.Throwable r4 = (java.lang.Throwable) r4
                if (r4 == 0) goto L_0x008d
                r21.e()
                r3.onError(r4)
                return
            L_0x008d:
                java.util.concurrent.atomic.AtomicInteger r4 = r0.f
                int r4 = r4.get()
                if (r4 != 0) goto L_0x0097
                r4 = 1
                goto L_0x0098
            L_0x0097:
                r4 = 0
            L_0x0098:
                r10 = 0
            L_0x0099:
                if (r10 >= r2) goto L_0x00ad
                r11 = r1[r10]
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r11 = r11.e
                if (r11 == 0) goto L_0x00aa
                boolean r11 = r11.isEmpty()
                if (r11 != 0) goto L_0x00aa
                r16 = 0
                goto L_0x00af
            L_0x00aa:
                int r10 = r10 + 1
                goto L_0x0099
            L_0x00ad:
                r16 = 1
            L_0x00af:
                if (r4 == 0) goto L_0x00b7
                if (r16 == 0) goto L_0x00b7
                r3.onComplete()
                return
            L_0x00b7:
                int r4 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x00ca
                r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x00ca
                java.util.concurrent.atomic.AtomicLong r4 = r0.d
                long r6 = -r14
                r4.addAndGet(r6)
            L_0x00ca:
                int r4 = r21.get()
                if (r4 != r5) goto L_0x00d8
                int r4 = -r5
                int r4 = r0.addAndGet(r4)
                if (r4 != 0) goto L_0x00d8
                return
            L_0x00d8:
                r5 = r4
                goto L_0x0008
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscription.c():void");
        }
    }

    static abstract class JoinSubscriptionBase<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3100232009247827843L;
        final Subscriber<? super T> a;
        final JoinInnerSubscriber<T>[] b;
        final AtomicThrowable c = new AtomicThrowable();
        final AtomicLong d = new AtomicLong();
        volatile boolean e;
        final AtomicInteger f = new AtomicInteger();

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void a(JoinInnerSubscriber<T> joinInnerSubscriber, T t);

        /* access modifiers changed from: 0000 */
        public abstract void a(Throwable th);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        JoinSubscriptionBase(Subscriber<? super T> subscriber, int i, int i2) {
            this.a = subscriber;
            JoinInnerSubscriber<T>[] joinInnerSubscriberArr = new JoinInnerSubscriber[i];
            for (int i3 = 0; i3 < i; i3++) {
                joinInnerSubscriberArr[i3] = new JoinInnerSubscriber<>(this, i2);
            }
            this.b = joinInnerSubscriberArr;
            this.f.lazySet(i);
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                b();
            }
        }

        public void cancel() {
            if (!this.e) {
                this.e = true;
                d();
                if (getAndIncrement() == 0) {
                    e();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            for (JoinInnerSubscriber<T> b2 : this.b) {
                b2.b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            for (JoinInnerSubscriber<T> joinInnerSubscriber : this.b) {
                joinInnerSubscriber.e = null;
            }
        }
    }

    static final class JoinSubscriptionDelayError<T> extends JoinSubscriptionBase<T> {
        private static final long serialVersionUID = -5737965195918321883L;

        JoinSubscriptionDelayError(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        /* access modifiers changed from: 0000 */
        public void a(JoinInnerSubscriber<T> joinInnerSubscriber, T t) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                if (!joinInnerSubscriber.c().offer(t) && joinInnerSubscriber.b()) {
                    this.c.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.f.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                if (this.d.get() != 0) {
                    this.a.onNext(t);
                    if (this.d.get() != Long.MAX_VALUE) {
                        this.d.decrementAndGet();
                    }
                    joinInnerSubscriber.a(1);
                } else if (!joinInnerSubscriber.c().offer(t)) {
                    joinInnerSubscriber.b();
                    this.c.addThrowable(new MissingBackpressureException("Queue full?!"));
                    this.f.decrementAndGet();
                    c();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            c();
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            this.c.addThrowable(th);
            this.f.decrementAndGet();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.f.decrementAndGet();
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0050, code lost:
            if (r12 == false) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0052, code lost:
            if (r11 == false) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
            if (((java.lang.Throwable) r0.c.get()) == null) goto L_0x0068;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005e, code lost:
            r3.onError(r0.c.terminate());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0068, code lost:
            r3.onComplete();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x006b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x006c, code lost:
            if (r11 == false) goto L_0x006f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void c() {
            /*
                r21 = this;
                r0 = r21
                io.reactivex.internal.operators.parallel.ParallelJoin$JoinInnerSubscriber[] r1 = r0.b
                int r2 = r1.length
                org.reactivestreams.Subscriber r3 = r0.a
                r5 = 1
            L_0x0008:
                java.util.concurrent.atomic.AtomicLong r6 = r0.d
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L_0x0011:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L_0x0071
                boolean r12 = r0.e
                if (r12 == 0) goto L_0x001d
                r21.e()
                return
            L_0x001d:
                java.util.concurrent.atomic.AtomicInteger r12 = r0.f
                int r12 = r12.get()
                if (r12 != 0) goto L_0x0027
                r12 = 1
                goto L_0x0028
            L_0x0027:
                r12 = 0
            L_0x0028:
                r14 = r10
                r10 = 0
                r11 = 1
            L_0x002b:
                if (r10 >= r2) goto L_0x0050
                r4 = r1[r10]
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r13 = r4.e
                if (r13 == 0) goto L_0x004d
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L_0x004d
                r3.onNext(r13)
                r4.a()
                r17 = 1
                long r19 = r14 + r17
                int r4 = (r19 > r6 ? 1 : (r19 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x004a
                r14 = r19
                goto L_0x0072
            L_0x004a:
                r14 = r19
                r11 = 0
            L_0x004d:
                int r10 = r10 + 1
                goto L_0x002b
            L_0x0050:
                if (r12 == 0) goto L_0x006c
                if (r11 == 0) goto L_0x006c
                io.reactivex.internal.util.AtomicThrowable r1 = r0.c
                java.lang.Object r1 = r1.get()
                java.lang.Throwable r1 = (java.lang.Throwable) r1
                if (r1 == 0) goto L_0x0068
                io.reactivex.internal.util.AtomicThrowable r1 = r0.c
                java.lang.Throwable r1 = r1.terminate()
                r3.onError(r1)
                goto L_0x006b
            L_0x0068:
                r3.onComplete()
            L_0x006b:
                return
            L_0x006c:
                if (r11 == 0) goto L_0x006f
                goto L_0x0072
            L_0x006f:
                r10 = r14
                goto L_0x0011
            L_0x0071:
                r14 = r10
            L_0x0072:
                int r4 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x00bc
                boolean r4 = r0.e
                if (r4 == 0) goto L_0x007e
                r21.e()
                return
            L_0x007e:
                java.util.concurrent.atomic.AtomicInteger r4 = r0.f
                int r4 = r4.get()
                if (r4 != 0) goto L_0x0088
                r4 = 1
                goto L_0x0089
            L_0x0088:
                r4 = 0
            L_0x0089:
                r10 = 0
            L_0x008a:
                if (r10 >= r2) goto L_0x009e
                r11 = r1[r10]
                io.reactivex.internal.fuseable.SimplePlainQueue<T> r11 = r11.e
                if (r11 == 0) goto L_0x009b
                boolean r11 = r11.isEmpty()
                if (r11 != 0) goto L_0x009b
                r16 = 0
                goto L_0x00a0
            L_0x009b:
                int r10 = r10 + 1
                goto L_0x008a
            L_0x009e:
                r16 = 1
            L_0x00a0:
                if (r4 == 0) goto L_0x00bc
                if (r16 == 0) goto L_0x00bc
                io.reactivex.internal.util.AtomicThrowable r1 = r0.c
                java.lang.Object r1 = r1.get()
                java.lang.Throwable r1 = (java.lang.Throwable) r1
                if (r1 == 0) goto L_0x00b8
                io.reactivex.internal.util.AtomicThrowable r1 = r0.c
                java.lang.Throwable r1 = r1.terminate()
                r3.onError(r1)
                goto L_0x00bb
            L_0x00b8:
                r3.onComplete()
            L_0x00bb:
                return
            L_0x00bc:
                int r4 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x00cf
                r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x00cf
                java.util.concurrent.atomic.AtomicLong r4 = r0.d
                long r6 = -r14
                r4.addAndGet(r6)
            L_0x00cf:
                int r4 = r21.get()
                if (r4 != r5) goto L_0x00dd
                int r4 = -r5
                int r4 = r0.addAndGet(r4)
                if (r4 != 0) goto L_0x00dd
                return
            L_0x00dd:
                r5 = r4
                goto L_0x0008
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelJoin.JoinSubscriptionDelayError.c():void");
        }
    }

    public ParallelJoin(ParallelFlowable<? extends T> parallelFlowable, int i, boolean z) {
        this.b = parallelFlowable;
        this.c = i;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        JoinSubscriptionBase joinSubscriptionBase;
        if (this.d) {
            joinSubscriptionBase = new JoinSubscriptionDelayError(subscriber, this.b.parallelism(), this.c);
        } else {
            joinSubscriptionBase = new JoinSubscription(subscriber, this.b.parallelism(), this.c);
        }
        subscriber.onSubscribe(joinSubscriptionBase);
        this.b.subscribe(joinSubscriptionBase.b);
    }
}
