package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final int d;
    final ErrorMode e;

    static final class ConcatMapEagerDelayErrorSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, InnerQueuedSubscriberSupport<R>, Subscription {
        private static final long serialVersionUID = -4255299542215038287L;
        final Subscriber<? super R> a;
        final Function<? super T, ? extends Publisher<? extends R>> b;
        final int c;
        final int d;
        final ErrorMode e;
        final AtomicThrowable f = new AtomicThrowable();
        final AtomicLong g = new AtomicLong();
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> h;
        Subscription i;
        volatile boolean j;
        volatile boolean k;
        volatile InnerQueuedSubscriber<R> l;

        ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i2, int i3, ErrorMode errorMode) {
            this.a = subscriber;
            this.b = function;
            this.c = i2;
            this.d = i3;
            this.e = errorMode;
            this.h = new SpscLinkedArrayQueue<>(Math.min(i3, i2));
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                this.a.onSubscribe(this);
                subscription.request(this.c == Integer.MAX_VALUE ? Long.MAX_VALUE : (long) this.c);
            }
        }

        public void onNext(T t) {
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher");
                InnerQueuedSubscriber innerQueuedSubscriber = new InnerQueuedSubscriber(this, this.d);
                if (!this.j) {
                    this.h.offer(innerQueuedSubscriber);
                    if (!this.j) {
                        publisher.subscribe(innerQueuedSubscriber);
                        if (this.j) {
                            innerQueuedSubscriber.cancel();
                            a();
                        }
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.i.cancel();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            if (this.f.addThrowable(th)) {
                this.k = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.k = true;
            drain();
        }

        public void cancel() {
            if (!this.j) {
                this.j = true;
                this.i.cancel();
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                do {
                    b();
                } while (decrementAndGet() != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            while (true) {
                InnerQueuedSubscriber innerQueuedSubscriber = (InnerQueuedSubscriber) this.h.poll();
                if (innerQueuedSubscriber != null) {
                    innerQueuedSubscriber.cancel();
                } else {
                    return;
                }
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.g, j2);
                drain();
            }
        }

        public void innerNext(InnerQueuedSubscriber<R> innerQueuedSubscriber, R r) {
            if (innerQueuedSubscriber.queue().offer(r)) {
                drain();
                return;
            }
            innerQueuedSubscriber.cancel();
            innerError(innerQueuedSubscriber, new MissingBackpressureException());
        }

        public void innerError(InnerQueuedSubscriber<R> innerQueuedSubscriber, Throwable th) {
            if (this.f.addThrowable(th)) {
                innerQueuedSubscriber.setDone();
                if (this.e != ErrorMode.END) {
                    this.i.cancel();
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void innerComplete(InnerQueuedSubscriber<R> innerQueuedSubscriber) {
            innerQueuedSubscriber.setDone();
            drain();
        }

        /* JADX WARNING: Removed duplicated region for block: B:79:0x0133  */
        /* JADX WARNING: Removed duplicated region for block: B:80:0x0137  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void drain() {
            /*
                r20 = this;
                r1 = r20
                int r2 = r20.getAndIncrement()
                if (r2 == 0) goto L_0x0009
                return
            L_0x0009:
                io.reactivex.internal.subscribers.InnerQueuedSubscriber<R> r2 = r1.l
                org.reactivestreams.Subscriber<? super R> r3 = r1.a
                io.reactivex.internal.util.ErrorMode r4 = r1.e
                r6 = 1
            L_0x0010:
                java.util.concurrent.atomic.AtomicLong r7 = r1.g
                long r7 = r7.get()
                if (r2 != 0) goto L_0x0056
                io.reactivex.internal.util.ErrorMode r2 = io.reactivex.internal.util.ErrorMode.END
                if (r4 == r2) goto L_0x0033
                io.reactivex.internal.util.AtomicThrowable r2 = r1.f
                java.lang.Object r2 = r2.get()
                java.lang.Throwable r2 = (java.lang.Throwable) r2
                if (r2 == 0) goto L_0x0033
                r20.b()
                io.reactivex.internal.util.AtomicThrowable r2 = r1.f
                java.lang.Throwable r2 = r2.terminate()
                r3.onError(r2)
                return
            L_0x0033:
                boolean r2 = r1.k
                io.reactivex.internal.queue.SpscLinkedArrayQueue<io.reactivex.internal.subscribers.InnerQueuedSubscriber<R>> r9 = r1.h
                java.lang.Object r9 = r9.poll()
                io.reactivex.internal.subscribers.InnerQueuedSubscriber r9 = (io.reactivex.internal.subscribers.InnerQueuedSubscriber) r9
                if (r2 == 0) goto L_0x0051
                if (r9 != 0) goto L_0x0051
                io.reactivex.internal.util.AtomicThrowable r2 = r1.f
                java.lang.Throwable r2 = r2.terminate()
                if (r2 == 0) goto L_0x004d
                r3.onError(r2)
                goto L_0x0050
            L_0x004d:
                r3.onComplete()
            L_0x0050:
                return
            L_0x0051:
                if (r9 == 0) goto L_0x0055
                r1.l = r9
            L_0x0055:
                r2 = r9
            L_0x0056:
                r12 = 0
                if (r2 == 0) goto L_0x0116
                io.reactivex.internal.fuseable.SimpleQueue r13 = r2.queue()
                if (r13 == 0) goto L_0x0116
                r14 = 0
            L_0x0061:
                int r16 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
                r17 = r6
                r5 = 1
                if (r16 == 0) goto L_0x00cc
                boolean r9 = r1.j
                if (r9 == 0) goto L_0x0071
                r20.b()
                return
            L_0x0071:
                io.reactivex.internal.util.ErrorMode r9 = io.reactivex.internal.util.ErrorMode.IMMEDIATE
                if (r4 != r9) goto L_0x0091
                io.reactivex.internal.util.AtomicThrowable r9 = r1.f
                java.lang.Object r9 = r9.get()
                java.lang.Throwable r9 = (java.lang.Throwable) r9
                if (r9 == 0) goto L_0x0091
                r1.l = r12
                r2.cancel()
                r20.b()
                io.reactivex.internal.util.AtomicThrowable r2 = r1.f
                java.lang.Throwable r2 = r2.terminate()
                r3.onError(r2)
                return
            L_0x0091:
                boolean r9 = r2.isDone()
                java.lang.Object r10 = r13.poll()     // Catch:{ Throwable -> 0x00bc }
                if (r10 != 0) goto L_0x009d
                r11 = 1
                goto L_0x009e
            L_0x009d:
                r11 = 0
            L_0x009e:
                if (r9 == 0) goto L_0x00ad
                if (r11 == 0) goto L_0x00ad
                r1.l = r12
                org.reactivestreams.Subscription r2 = r1.i
                r2.request(r5)
                r2 = r12
                r18 = 1
                goto L_0x00ce
            L_0x00ad:
                if (r11 == 0) goto L_0x00b0
                goto L_0x00cc
            L_0x00b0:
                r3.onNext(r10)
                long r9 = r14 + r5
                r2.requestOne()
                r14 = r9
                r6 = r17
                goto L_0x0061
            L_0x00bc:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r1.l = r12
                r2.cancel()
                r20.b()
                r3.onError(r0)
                return
            L_0x00cc:
                r18 = 0
            L_0x00ce:
                int r9 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
                if (r9 != 0) goto L_0x0113
                boolean r9 = r1.j
                if (r9 == 0) goto L_0x00da
                r20.b()
                return
            L_0x00da:
                io.reactivex.internal.util.ErrorMode r9 = io.reactivex.internal.util.ErrorMode.IMMEDIATE
                if (r4 != r9) goto L_0x00fa
                io.reactivex.internal.util.AtomicThrowable r9 = r1.f
                java.lang.Object r9 = r9.get()
                java.lang.Throwable r9 = (java.lang.Throwable) r9
                if (r9 == 0) goto L_0x00fa
                r1.l = r12
                r2.cancel()
                r20.b()
                io.reactivex.internal.util.AtomicThrowable r2 = r1.f
                java.lang.Throwable r2 = r2.terminate()
                r3.onError(r2)
                return
            L_0x00fa:
                boolean r9 = r2.isDone()
                boolean r10 = r13.isEmpty()
                if (r9 == 0) goto L_0x0113
                if (r10 == 0) goto L_0x0113
                r1.l = r12
                org.reactivestreams.Subscription r2 = r1.i
                r2.request(r5)
                r2 = r12
                r5 = 0
                r18 = 1
                goto L_0x011e
            L_0x0113:
                r5 = 0
                goto L_0x011e
            L_0x0116:
                r17 = r6
                r5 = 0
                r14 = 0
                r18 = 0
            L_0x011e:
                int r9 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
                if (r9 == 0) goto L_0x0131
                r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
                if (r9 == 0) goto L_0x0131
                java.util.concurrent.atomic.AtomicLong r5 = r1.g
                long r6 = -r14
                r5.addAndGet(r6)
            L_0x0131:
                if (r18 == 0) goto L_0x0137
                r6 = r17
                goto L_0x0010
            L_0x0137:
                r5 = r17
                int r5 = -r5
                int r6 = r1.addAndGet(r5)
                if (r6 != 0) goto L_0x0010
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber.drain():void");
        }
    }

    public FlowableConcatMapEager(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = i2;
        this.e = errorMode;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        Flowable flowable = this.source;
        ConcatMapEagerDelayErrorSubscriber concatMapEagerDelayErrorSubscriber = new ConcatMapEagerDelayErrorSubscriber(subscriber, this.b, this.c, this.d, this.e);
        flowable.subscribe((FlowableSubscriber<? super T>) concatMapEagerDelayErrorSubscriber);
    }
}
