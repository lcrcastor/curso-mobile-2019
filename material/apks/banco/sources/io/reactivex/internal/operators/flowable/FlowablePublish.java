package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    final Flowable<T> b;
    final AtomicReference<PublishSubscriber<T>> c;
    final int d;
    final Publisher<T> e;

    static final class FlowablePublisher<T> implements Publisher<T> {
        private final AtomicReference<PublishSubscriber<T>> a;
        private final int b;

        FlowablePublisher(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.a = atomicReference;
            this.b = i;
        }

        public void subscribe(Subscriber<? super T> subscriber) {
            PublishSubscriber<T> publishSubscriber;
            InnerSubscriber innerSubscriber = new InnerSubscriber(subscriber);
            subscriber.onSubscribe(innerSubscriber);
            while (true) {
                publishSubscriber = (PublishSubscriber) this.a.get();
                if (publishSubscriber == null || publishSubscriber.isDisposed()) {
                    PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.a, this.b);
                    if (!this.a.compareAndSet(publishSubscriber, publishSubscriber2)) {
                        continue;
                    } else {
                        publishSubscriber = publishSubscriber2;
                    }
                }
                if (publishSubscriber.a(innerSubscriber)) {
                    break;
                }
            }
            if (innerSubscriber.get() == Long.MIN_VALUE) {
                publishSubscriber.b(innerSubscriber);
            } else {
                innerSubscriber.b = publishSubscriber;
            }
            publishSubscriber.a();
        }
    }

    static final class InnerSubscriber<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> a;
        volatile PublishSubscriber<T> b;

        InnerSubscriber(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                PublishSubscriber<T> publishSubscriber = this.b;
                if (publishSubscriber != null) {
                    publishSubscriber.a();
                }
            }
        }

        public long a(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        public void cancel() {
            if (get() != Long.MIN_VALUE && getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                PublishSubscriber<T> publishSubscriber = this.b;
                if (publishSubscriber != null) {
                    publishSubscriber.b(this);
                    publishSubscriber.a();
                }
            }
        }
    }

    static final class PublishSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscriber[] a = new InnerSubscriber[0];
        static final InnerSubscriber[] b = new InnerSubscriber[0];
        private static final long serialVersionUID = -202316842419149694L;
        final AtomicReference<PublishSubscriber<T>> c;
        final int d;
        final AtomicReference<InnerSubscriber[]> e = new AtomicReference<>(a);
        final AtomicBoolean f;
        final AtomicReference<Subscription> g = new AtomicReference<>();
        volatile Object h;
        int i;
        volatile SimpleQueue<T> j;

        PublishSubscriber(AtomicReference<PublishSubscriber<T>> atomicReference, int i2) {
            this.c = atomicReference;
            this.f = new AtomicBoolean();
            this.d = i2;
        }

        public void dispose() {
            if (this.e.get() != b && ((InnerSubscriber[]) this.e.getAndSet(b)) != b) {
                this.c.compareAndSet(this, null);
                SubscriptionHelper.cancel(this.g);
            }
        }

        public boolean isDisposed() {
            return this.e.get() == b;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.g, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.i = requestFusion;
                        this.j = queueSubscription;
                        this.h = NotificationLite.complete();
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.i = requestFusion;
                        this.j = queueSubscription;
                        subscription.request((long) this.d);
                        return;
                    }
                }
                this.j = new SpscArrayQueue(this.d);
                subscription.request((long) this.d);
            }
        }

        public void onNext(T t) {
            if (this.i != 0 || this.j.offer(t)) {
                a();
            } else {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            }
        }

        public void onError(Throwable th) {
            if (this.h == null) {
                this.h = NotificationLite.error(th);
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (this.h == null) {
                this.h = NotificationLite.complete();
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.e.get();
                if (innerSubscriberArr == b) {
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[(length + 1)];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.e.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.e.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    break;
                }
                int i2 = -1;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    } else if (innerSubscriberArr[i3].equals(innerSubscriber)) {
                        i2 = i3;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i2 >= 0) {
                    if (length == 1) {
                        innerSubscriberArr2 = a;
                    } else {
                        InnerSubscriber[] innerSubscriberArr3 = new InnerSubscriber[(length - 1)];
                        System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i2);
                        System.arraycopy(innerSubscriberArr, i2 + 1, innerSubscriberArr3, i2, (length - i2) - 1);
                        innerSubscriberArr2 = innerSubscriberArr3;
                    }
                } else {
                    return;
                }
            } while (!this.e.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Object obj, boolean z) {
            int i2 = 0;
            if (obj != null) {
                if (!NotificationLite.isComplete(obj)) {
                    Throwable error = NotificationLite.getError(obj);
                    this.c.compareAndSet(this, null);
                    InnerSubscriber[] innerSubscriberArr = (InnerSubscriber[]) this.e.getAndSet(b);
                    if (innerSubscriberArr.length != 0) {
                        int length = innerSubscriberArr.length;
                        while (i2 < length) {
                            innerSubscriberArr[i2].a.onError(error);
                            i2++;
                        }
                    } else {
                        RxJavaPlugins.onError(error);
                    }
                    return true;
                } else if (z) {
                    this.c.compareAndSet(this, null);
                    InnerSubscriber[] innerSubscriberArr2 = (InnerSubscriber[]) this.e.getAndSet(b);
                    int length2 = innerSubscriberArr2.length;
                    while (i2 < length2) {
                        innerSubscriberArr2[i2].a.onComplete();
                        i2++;
                    }
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x010d, code lost:
            if (r6 == false) goto L_0x010f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a() {
            /*
                r21 = this;
                r1 = r21
                int r2 = r21.getAndIncrement()
                if (r2 == 0) goto L_0x0009
                return
            L_0x0009:
                r3 = 1
            L_0x000a:
                java.lang.Object r4 = r1.h
                io.reactivex.internal.fuseable.SimpleQueue<T> r5 = r1.j
                if (r5 == 0) goto L_0x0019
                boolean r7 = r5.isEmpty()
                if (r7 == 0) goto L_0x0017
                goto L_0x0019
            L_0x0017:
                r7 = 0
                goto L_0x001a
            L_0x0019:
                r7 = 1
            L_0x001a:
                boolean r4 = r1.a(r4, r7)
                if (r4 == 0) goto L_0x0021
                return
            L_0x0021:
                if (r7 != 0) goto L_0x0113
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[]> r4 = r1.e
                java.lang.Object r4 = r4.get()
                io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[] r4 = (io.reactivex.internal.operators.flowable.FlowablePublish.InnerSubscriber[]) r4
                int r8 = r4.length
                r9 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r11 = r4.length
                r12 = r9
                r9 = 0
                r10 = 0
            L_0x0035:
                r14 = 0
                if (r9 >= r11) goto L_0x0058
                r6 = r4[r9]
                r16 = r3
                long r2 = r6.get()
                int r6 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
                if (r6 < 0) goto L_0x004b
                long r2 = java.lang.Math.min(r12, r2)
                r12 = r2
                goto L_0x0053
            L_0x004b:
                r14 = -9223372036854775808
                int r6 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
                if (r6 != 0) goto L_0x0053
                int r10 = r10 + 1
            L_0x0053:
                int r9 = r9 + 1
                r3 = r16
                goto L_0x0035
            L_0x0058:
                r16 = r3
                r2 = 1
                if (r8 != r10) goto L_0x009a
                java.lang.Object r4 = r1.h
                java.lang.Object r5 = r5.poll()     // Catch:{ Throwable -> 0x0065 }
                goto L_0x007b
            L_0x0065:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r4 = r1.g
                java.lang.Object r4 = r4.get()
                org.reactivestreams.Subscription r4 = (org.reactivestreams.Subscription) r4
                r4.cancel()
                java.lang.Object r4 = io.reactivex.internal.util.NotificationLite.error(r0)
                r1.h = r4
                r5 = 0
            L_0x007b:
                if (r5 != 0) goto L_0x007f
                r5 = 1
                goto L_0x0080
            L_0x007f:
                r5 = 0
            L_0x0080:
                boolean r4 = r1.a(r4, r5)
                if (r4 == 0) goto L_0x0087
                return
            L_0x0087:
                int r4 = r1.i
                r5 = 1
                if (r4 == r5) goto L_0x0097
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r4 = r1.g
                java.lang.Object r4 = r4.get()
                org.reactivestreams.Subscription r4 = (org.reactivestreams.Subscription) r4
                r4.request(r2)
            L_0x0097:
                r3 = 1
                goto L_0x010f
            L_0x009a:
                r8 = r7
                r7 = 0
            L_0x009c:
                long r9 = (long) r7
                int r11 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1))
                if (r11 >= 0) goto L_0x00f2
                java.lang.Object r8 = r1.h
                java.lang.Object r11 = r5.poll()     // Catch:{ Throwable -> 0x00a8 }
                goto L_0x00be
            L_0x00a8:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r8 = r1.g
                java.lang.Object r8 = r8.get()
                org.reactivestreams.Subscription r8 = (org.reactivestreams.Subscription) r8
                r8.cancel()
                java.lang.Object r8 = io.reactivex.internal.util.NotificationLite.error(r0)
                r1.h = r8
                r11 = 0
            L_0x00be:
                if (r11 != 0) goto L_0x00c2
                r6 = 1
                goto L_0x00c3
            L_0x00c2:
                r6 = 0
            L_0x00c3:
                boolean r8 = r1.a(r8, r6)
                if (r8 == 0) goto L_0x00ca
                return
            L_0x00ca:
                if (r6 == 0) goto L_0x00cd
                goto L_0x00f3
            L_0x00cd:
                java.lang.Object r8 = io.reactivex.internal.util.NotificationLite.getValue(r11)
                int r9 = r4.length
                r10 = 0
            L_0x00d3:
                if (r10 >= r9) goto L_0x00ec
                r11 = r4[r10]
                long r17 = r11.get()
                int r19 = (r17 > r14 ? 1 : (r17 == r14 ? 0 : -1))
                if (r19 <= 0) goto L_0x00e7
                org.reactivestreams.Subscriber<? super T> r14 = r11.a
                r14.onNext(r8)
                r11.a(r2)
            L_0x00e7:
                int r10 = r10 + 1
                r14 = 0
                goto L_0x00d3
            L_0x00ec:
                int r7 = r7 + 1
                r8 = r6
                r14 = 0
                goto L_0x009c
            L_0x00f2:
                r6 = r8
            L_0x00f3:
                if (r7 <= 0) goto L_0x0106
                int r2 = r1.i
                r3 = 1
                if (r2 == r3) goto L_0x0107
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r2 = r1.g
                java.lang.Object r2 = r2.get()
                org.reactivestreams.Subscription r2 = (org.reactivestreams.Subscription) r2
                r2.request(r9)
                goto L_0x0107
            L_0x0106:
                r3 = 1
            L_0x0107:
                r4 = 0
                int r2 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
                if (r2 == 0) goto L_0x0116
                if (r6 != 0) goto L_0x0116
            L_0x010f:
                r3 = r16
                goto L_0x000a
            L_0x0113:
                r16 = r3
                r3 = 1
            L_0x0116:
                r2 = r16
                int r2 = -r2
                int r2 = r1.addAndGet(r2)
                if (r2 != 0) goto L_0x0120
                return
            L_0x0120:
                r3 = r2
                goto L_0x000a
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber.a():void");
        }
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new FlowablePublish<T>(new FlowablePublisher(atomicReference, i), flowable, atomicReference, i));
    }

    private FlowablePublish(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = i;
    }

    public Publisher<T> source() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(io.reactivex.functions.Consumer<? super io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r0 = r4.c
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r0 = (io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0023
        L_0x0010:
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r1 = new io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.c
            int r3 = r4.d
            r1.<init>(r2, r3)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.c
            boolean r0 = r2.compareAndSet(r0, r1)
            if (r0 != 0) goto L_0x0022
            goto L_0x0000
        L_0x0022:
            r0 = r1
        L_0x0023:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0036
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0036
            goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            r5.accept(r0)     // Catch:{ Throwable -> 0x0042 }
            if (r2 == 0) goto L_0x0041
            io.reactivex.Flowable<T> r5 = r4.b
            r5.subscribe(r0)
        L_0x0041:
            return
        L_0x0042:
            r5 = move-exception
            io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowablePublish.connect(io.reactivex.functions.Consumer):void");
    }
}
