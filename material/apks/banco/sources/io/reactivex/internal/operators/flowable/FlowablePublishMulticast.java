package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublishMulticast<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super Flowable<T>, ? extends Publisher<? extends R>> b;
    final int c;
    final boolean d;

    static final class MulticastProcessor<T> extends Flowable<T> implements FlowableSubscriber<T>, Disposable {
        static final MulticastSubscription[] b = new MulticastSubscription[0];
        static final MulticastSubscription[] c = new MulticastSubscription[0];
        final AtomicInteger d = new AtomicInteger();
        final AtomicReference<MulticastSubscription<T>[]> e = new AtomicReference<>(b);
        final int f;
        final int g;
        final boolean h;
        final AtomicReference<Subscription> i = new AtomicReference<>();
        volatile SimpleQueue<T> j;
        int k;
        volatile boolean l;
        Throwable m;
        int n;

        MulticastProcessor(int i2, boolean z) {
            this.f = i2;
            this.g = i2 - (i2 >> 2);
            this.h = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.i, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.k = requestFusion;
                        this.j = queueSubscription;
                        this.l = true;
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.k = requestFusion;
                        this.j = queueSubscription;
                        QueueDrainHelper.request(subscription, this.f);
                        return;
                    }
                }
                this.j = QueueDrainHelper.createQueue(this.f);
                QueueDrainHelper.request(subscription, this.f);
            }
        }

        public void dispose() {
            SubscriptionHelper.cancel(this.i);
            if (this.d.getAndIncrement() == 0) {
                SimpleQueue<T> simpleQueue = this.j;
                if (simpleQueue != null) {
                    simpleQueue.clear();
                }
            }
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) this.i.get());
        }

        public void onNext(T t) {
            if (!this.l) {
                if (this.k != 0 || this.j.offer(t)) {
                    a();
                    return;
                }
                ((Subscription) this.i.get()).cancel();
                onError(new MissingBackpressureException());
            }
        }

        public void onError(Throwable th) {
            if (this.l) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.m = th;
            this.l = true;
            a();
        }

        public void onComplete() {
            if (!this.l) {
                this.l = true;
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(MulticastSubscription<T> multicastSubscription) {
            MulticastSubscription[] multicastSubscriptionArr;
            MulticastSubscription[] multicastSubscriptionArr2;
            do {
                multicastSubscriptionArr = (MulticastSubscription[]) this.e.get();
                if (multicastSubscriptionArr == c) {
                    return false;
                }
                int length = multicastSubscriptionArr.length;
                multicastSubscriptionArr2 = new MulticastSubscription[(length + 1)];
                System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr2, 0, length);
                multicastSubscriptionArr2[length] = multicastSubscription;
            } while (!this.e.compareAndSet(multicastSubscriptionArr, multicastSubscriptionArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(MulticastSubscription<T> multicastSubscription) {
            MulticastSubscription<T>[] multicastSubscriptionArr;
            MulticastSubscription[] multicastSubscriptionArr2;
            do {
                multicastSubscriptionArr = (MulticastSubscription[]) this.e.get();
                if (multicastSubscriptionArr != c && multicastSubscriptionArr != b) {
                    int length = multicastSubscriptionArr.length;
                    int i2 = -1;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        } else if (multicastSubscriptionArr[i3] == multicastSubscription) {
                            i2 = i3;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i2 >= 0) {
                        if (length == 1) {
                            multicastSubscriptionArr2 = b;
                        } else {
                            MulticastSubscription[] multicastSubscriptionArr3 = new MulticastSubscription[(length - 1)];
                            System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr3, 0, i2);
                            System.arraycopy(multicastSubscriptionArr, i2 + 1, multicastSubscriptionArr3, i2, (length - i2) - 1);
                            multicastSubscriptionArr2 = multicastSubscriptionArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.e.compareAndSet(multicastSubscriptionArr, multicastSubscriptionArr2));
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Subscriber<? super T> subscriber) {
            MulticastSubscription multicastSubscription = new MulticastSubscription(subscriber, this);
            subscriber.onSubscribe(multicastSubscription);
            if (!a(multicastSubscription)) {
                Throwable th = this.m;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
            } else if (multicastSubscription.a()) {
                b(multicastSubscription);
            } else {
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.d.getAndIncrement() == 0) {
                SimpleQueue<T> simpleQueue = this.j;
                int i2 = this.n;
                int i3 = this.g;
                boolean z = this.k != 1;
                int i4 = i2;
                int i5 = 1;
                while (true) {
                    MulticastSubscription[] multicastSubscriptionArr = (MulticastSubscription[]) this.e.get();
                    int length = multicastSubscriptionArr.length;
                    if (!(simpleQueue == null || length == 0)) {
                        long j2 = Long.MAX_VALUE;
                        for (MulticastSubscription multicastSubscription : multicastSubscriptionArr) {
                            long j3 = multicastSubscription.get();
                            if (j3 != Long.MIN_VALUE && j2 > j3) {
                                j2 = j3;
                            }
                        }
                        long j4 = 0;
                        while (j4 != j2) {
                            if (isDisposed()) {
                                simpleQueue.clear();
                                return;
                            }
                            boolean z2 = this.l;
                            if (z2 && !this.h) {
                                Throwable th = this.m;
                                if (th != null) {
                                    a(th);
                                    return;
                                }
                            }
                            try {
                                Object poll = simpleQueue.poll();
                                boolean z3 = poll == null;
                                if (z2 && z3) {
                                    Throwable th2 = this.m;
                                    if (th2 != null) {
                                        a(th2);
                                    } else {
                                        b();
                                    }
                                    return;
                                } else if (z3) {
                                    break;
                                } else {
                                    int length2 = multicastSubscriptionArr.length;
                                    int i6 = 0;
                                    while (i6 < length2) {
                                        int i7 = length2;
                                        MulticastSubscription multicastSubscription2 = multicastSubscriptionArr[i6];
                                        if (multicastSubscription2.get() != Long.MIN_VALUE) {
                                            multicastSubscription2.a.onNext(poll);
                                        }
                                        i6++;
                                        length2 = i7;
                                    }
                                    long j5 = j4 + 1;
                                    if (z) {
                                        int i8 = i4 + 1;
                                        if (i8 == i3) {
                                            ((Subscription) this.i.get()).request((long) i3);
                                            i4 = 0;
                                        } else {
                                            i4 = i8;
                                        }
                                    }
                                    j4 = j5;
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                SubscriptionHelper.cancel(this.i);
                                a(th3);
                                return;
                            }
                        }
                        if (j4 == j2) {
                            if (isDisposed()) {
                                simpleQueue.clear();
                                return;
                            }
                            boolean z4 = this.l;
                            if (z4 && !this.h) {
                                Throwable th4 = this.m;
                                if (th4 != null) {
                                    a(th4);
                                    return;
                                }
                            }
                            if (z4 && simpleQueue.isEmpty()) {
                                Throwable th5 = this.m;
                                if (th5 != null) {
                                    a(th5);
                                } else {
                                    b();
                                }
                                return;
                            }
                        }
                        for (MulticastSubscription produced : multicastSubscriptionArr) {
                            BackpressureHelper.produced(produced, j4);
                        }
                    }
                    this.n = i4;
                    i5 = this.d.addAndGet(-i5);
                    if (i5 != 0) {
                        if (simpleQueue == null) {
                            simpleQueue = this.j;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            MulticastSubscription[] multicastSubscriptionArr;
            for (MulticastSubscription multicastSubscription : (MulticastSubscription[]) this.e.getAndSet(c)) {
                if (multicastSubscription.get() != Long.MIN_VALUE) {
                    multicastSubscription.a.onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            MulticastSubscription[] multicastSubscriptionArr;
            for (MulticastSubscription multicastSubscription : (MulticastSubscription[]) this.e.getAndSet(c)) {
                if (multicastSubscription.get() != Long.MIN_VALUE) {
                    multicastSubscription.a.onComplete();
                }
            }
        }
    }

    static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 8664815189257569791L;
        final Subscriber<? super T> a;
        final MulticastProcessor<T> b;

        MulticastSubscription(Subscriber<? super T> subscriber, MulticastProcessor<T> multicastProcessor) {
            this.a = subscriber;
            this.b = multicastProcessor;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                this.b.a();
            }
        }

        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.b.b(this);
                this.b.a();
            }
        }

        public boolean a() {
            return get() == Long.MIN_VALUE;
        }
    }

    static final class OutputCanceller<R> implements FlowableSubscriber<R>, Subscription {
        final Subscriber<? super R> a;
        final MulticastProcessor<?> b;
        Subscription c;

        OutputCanceller(Subscriber<? super R> subscriber, MulticastProcessor<?> multicastProcessor) {
            this.a = subscriber;
            this.b = multicastProcessor;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(R r) {
            this.a.onNext(r);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            this.b.dispose();
        }

        public void onComplete() {
            this.a.onComplete();
            this.b.dispose();
        }

        public void request(long j) {
            this.c.request(j);
        }

        public void cancel() {
            this.c.cancel();
            this.b.dispose();
        }
    }

    public FlowablePublishMulticast(Flowable<T> flowable, Function<? super Flowable<T>, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        MulticastProcessor multicastProcessor = new MulticastProcessor(this.c, this.d);
        try {
            ((Publisher) ObjectHelper.requireNonNull(this.b.apply(multicastProcessor), "selector returned a null Publisher")).subscribe(new OutputCanceller(subscriber, multicastProcessor));
            this.source.subscribe((FlowableSubscriber<? super T>) multicastProcessor);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
