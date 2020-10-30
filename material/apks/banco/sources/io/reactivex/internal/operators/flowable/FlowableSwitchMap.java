package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final boolean d;

    static final class SwitchMapInnerSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final SwitchMapSubscriber<T, R> a;
        final long b;
        final int c;
        volatile SimpleQueue<R> d;
        volatile boolean e;
        int f;

        SwitchMapInnerSubscriber(SwitchMapSubscriber<T, R> switchMapSubscriber, long j, int i) {
            this.a = switchMapSubscriber;
            this.b = j;
            this.c = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.f = requestFusion;
                        this.d = queueSubscription;
                        this.e = true;
                        this.a.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.f = requestFusion;
                        this.d = queueSubscription;
                        subscription.request((long) this.c);
                        return;
                    }
                }
                this.d = new SpscArrayQueue(this.c);
                subscription.request((long) this.c);
            }
        }

        public void onNext(R r) {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.a;
            if (this.b == switchMapSubscriber.l) {
                if (this.f != 0 || this.d.offer(r)) {
                    switchMapSubscriber.b();
                } else {
                    onError(new MissingBackpressureException("Queue full?!"));
                }
            }
        }

        public void onError(Throwable th) {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.a;
            if (this.b != switchMapSubscriber.l || !switchMapSubscriber.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!switchMapSubscriber.d) {
                switchMapSubscriber.h.cancel();
            }
            this.e = true;
            switchMapSubscriber.b();
        }

        public void onComplete() {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.a;
            if (this.b == switchMapSubscriber.l) {
                this.e = true;
                switchMapSubscriber.b();
            }
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    static final class SwitchMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final SwitchMapInnerSubscriber<Object, Object> k = new SwitchMapInnerSubscriber<>(null, -1, 1);
        private static final long serialVersionUID = -3491074160481096299L;
        final Subscriber<? super R> a;
        final Function<? super T, ? extends Publisher<? extends R>> b;
        final int c;
        final boolean d;
        volatile boolean e;
        final AtomicThrowable f;
        volatile boolean g;
        Subscription h;
        final AtomicReference<SwitchMapInnerSubscriber<T, R>> i = new AtomicReference<>();
        final AtomicLong j = new AtomicLong();
        volatile long l;

        static {
            k.a();
        }

        SwitchMapSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i2, boolean z) {
            this.a = subscriber;
            this.b = function;
            this.c = i2;
            this.d = z;
            this.f = new AtomicThrowable();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.h, subscription)) {
                this.h = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.e) {
                long j2 = this.l + 1;
                this.l = j2;
                SwitchMapInnerSubscriber switchMapInnerSubscriber = (SwitchMapInnerSubscriber) this.i.get();
                if (switchMapInnerSubscriber != null) {
                    switchMapInnerSubscriber.a();
                }
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The publisher returned is null");
                    SwitchMapInnerSubscriber switchMapInnerSubscriber2 = new SwitchMapInnerSubscriber(this, j2, this.c);
                    while (true) {
                        SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber3 = (SwitchMapInnerSubscriber) this.i.get();
                        if (switchMapInnerSubscriber3 != k) {
                            if (this.i.compareAndSet(switchMapInnerSubscriber3, switchMapInnerSubscriber2)) {
                                publisher.subscribe(switchMapInnerSubscriber2);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.h.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.e || !this.f.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.d) {
                a();
            }
            this.e = true;
            b();
        }

        public void onComplete() {
            if (!this.e) {
                this.e = true;
                b();
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.j, j2);
                if (this.l == 0) {
                    this.h.request(Long.MAX_VALUE);
                } else {
                    b();
                }
            }
        }

        public void cancel() {
            if (!this.g) {
                this.g = true;
                this.h.cancel();
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (((SwitchMapInnerSubscriber) this.i.get()) != k) {
                SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber = (SwitchMapInnerSubscriber) this.i.getAndSet(k);
                if (switchMapInnerSubscriber != k && switchMapInnerSubscriber != null) {
                    switchMapInnerSubscriber.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e9, code lost:
            r15 = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r19 = this;
                r1 = r19
                int r2 = r19.getAndIncrement()
                if (r2 == 0) goto L_0x0009
                return
            L_0x0009:
                org.reactivestreams.Subscriber<? super R> r2 = r1.a
                r4 = 1
            L_0x000c:
                boolean r5 = r1.g
                r6 = 0
                if (r5 == 0) goto L_0x0017
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r2 = r1.i
                r2.lazySet(r6)
                return
            L_0x0017:
                boolean r5 = r1.e
                if (r5 == 0) goto L_0x0062
                boolean r5 = r1.d
                if (r5 == 0) goto L_0x003f
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r5 = r1.i
                java.lang.Object r5 = r5.get()
                if (r5 != 0) goto L_0x0062
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Object r3 = r3.get()
                java.lang.Throwable r3 = (java.lang.Throwable) r3
                if (r3 == 0) goto L_0x003b
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Throwable r3 = r3.terminate()
                r2.onError(r3)
                goto L_0x003e
            L_0x003b:
                r2.onComplete()
            L_0x003e:
                return
            L_0x003f:
                io.reactivex.internal.util.AtomicThrowable r5 = r1.f
                java.lang.Object r5 = r5.get()
                java.lang.Throwable r5 = (java.lang.Throwable) r5
                if (r5 == 0) goto L_0x0056
                r19.a()
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Throwable r3 = r3.terminate()
                r2.onError(r3)
                return
            L_0x0056:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r5 = r1.i
                java.lang.Object r5 = r5.get()
                if (r5 != 0) goto L_0x0062
                r2.onComplete()
                return
            L_0x0062:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r5 = r1.i
                java.lang.Object r5 = r5.get()
                io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber r5 = (io.reactivex.internal.operators.flowable.FlowableSwitchMap.SwitchMapInnerSubscriber) r5
                if (r5 == 0) goto L_0x006f
                io.reactivex.internal.fuseable.SimpleQueue<R> r7 = r5.d
                goto L_0x0070
            L_0x006f:
                r7 = r6
            L_0x0070:
                if (r7 == 0) goto L_0x0146
                boolean r8 = r5.e
                if (r8 == 0) goto L_0x00ab
                boolean r8 = r1.d
                if (r8 != 0) goto L_0x009e
                io.reactivex.internal.util.AtomicThrowable r8 = r1.f
                java.lang.Object r8 = r8.get()
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                if (r8 == 0) goto L_0x0091
                r19.a()
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Throwable r3 = r3.terminate()
                r2.onError(r3)
                return
            L_0x0091:
                boolean r8 = r7.isEmpty()
                if (r8 == 0) goto L_0x00ab
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r7 = r1.i
                r7.compareAndSet(r5, r6)
                goto L_0x000c
            L_0x009e:
                boolean r8 = r7.isEmpty()
                if (r8 == 0) goto L_0x00ab
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r7 = r1.i
                r7.compareAndSet(r5, r6)
                goto L_0x000c
            L_0x00ab:
                java.util.concurrent.atomic.AtomicLong r8 = r1.j
                long r8 = r8.get()
                r10 = 0
                r12 = r10
            L_0x00b4:
                int r14 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
                r15 = 0
                if (r14 == 0) goto L_0x0122
                boolean r14 = r1.g
                if (r14 == 0) goto L_0x00be
                return
            L_0x00be:
                boolean r14 = r5.e
                java.lang.Object r16 = r7.poll()     // Catch:{ Throwable -> 0x00cb }
                r18 = r16
                r16 = r14
                r14 = r18
                goto L_0x00da
            L_0x00cb:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r5.a()
                io.reactivex.internal.util.AtomicThrowable r14 = r1.f
                r14.addThrowable(r0)
                r14 = r6
                r16 = 1
            L_0x00da:
                if (r14 != 0) goto L_0x00df
                r17 = 1
                goto L_0x00e1
            L_0x00df:
                r17 = 0
            L_0x00e1:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r3 = r1.i
                java.lang.Object r3 = r3.get()
                if (r5 == r3) goto L_0x00eb
            L_0x00e9:
                r15 = 1
                goto L_0x0122
            L_0x00eb:
                if (r16 == 0) goto L_0x0115
                boolean r3 = r1.d
                if (r3 != 0) goto L_0x010d
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Object r3 = r3.get()
                java.lang.Throwable r3 = (java.lang.Throwable) r3
                if (r3 == 0) goto L_0x0105
                io.reactivex.internal.util.AtomicThrowable r3 = r1.f
                java.lang.Throwable r3 = r3.terminate()
                r2.onError(r3)
                return
            L_0x0105:
                if (r17 == 0) goto L_0x0115
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r3 = r1.i
                r3.compareAndSet(r5, r6)
                goto L_0x00e9
            L_0x010d:
                if (r17 == 0) goto L_0x0115
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableSwitchMap$SwitchMapInnerSubscriber<T, R>> r3 = r1.i
                r3.compareAndSet(r5, r6)
                goto L_0x00e9
            L_0x0115:
                if (r17 == 0) goto L_0x0118
                goto L_0x0122
            L_0x0118:
                r2.onNext(r14)
                r14 = 1
                long r16 = r12 + r14
                r12 = r16
                goto L_0x00b4
            L_0x0122:
                int r3 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
                if (r3 == 0) goto L_0x0142
                boolean r3 = r1.g
                if (r3 != 0) goto L_0x0142
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r3 == 0) goto L_0x0139
                java.util.concurrent.atomic.AtomicLong r3 = r1.j
                long r6 = -r12
                r3.addAndGet(r6)
            L_0x0139:
                java.lang.Object r3 = r5.get()
                org.reactivestreams.Subscription r3 = (org.reactivestreams.Subscription) r3
                r3.request(r12)
            L_0x0142:
                if (r15 == 0) goto L_0x0146
                goto L_0x000c
            L_0x0146:
                int r3 = -r4
                int r4 = r1.addAndGet(r3)
                if (r4 != 0) goto L_0x000c
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableSwitchMap.SwitchMapSubscriber.b():void");
        }
    }

    public FlowableSwitchMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe((FlowableSubscriber<? super T>) new SwitchMapSubscriber<Object>(subscriber, this.b, this.c, this.d));
        }
    }
}
