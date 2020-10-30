package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelSortedJoin<T> extends Flowable<T> {
    final ParallelFlowable<List<T>> b;
    final Comparator<? super T> c;

    static final class SortedJoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
        private static final long serialVersionUID = 6751017204873808094L;
        final SortedJoinSubscription<T> a;
        final int b;

        public void onComplete() {
        }

        SortedJoinInnerSubscriber(SortedJoinSubscription<T> sortedJoinSubscription, int i) {
            this.a = sortedJoinSubscription;
            this.b = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        /* renamed from: a */
        public void onNext(List<T> list) {
            this.a.a(list, this.b);
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    static final class SortedJoinSubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3481980673745556697L;
        final Subscriber<? super T> a;
        final SortedJoinInnerSubscriber<T>[] b;
        final List<T>[] c;
        final int[] d;
        final Comparator<? super T> e;
        final AtomicLong f = new AtomicLong();
        volatile boolean g;
        final AtomicInteger h = new AtomicInteger();
        final AtomicReference<Throwable> i = new AtomicReference<>();

        SortedJoinSubscription(Subscriber<? super T> subscriber, int i2, Comparator<? super T> comparator) {
            this.a = subscriber;
            this.e = comparator;
            SortedJoinInnerSubscriber<T>[] sortedJoinInnerSubscriberArr = new SortedJoinInnerSubscriber[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                sortedJoinInnerSubscriberArr[i3] = new SortedJoinInnerSubscriber<>(this, i3);
            }
            this.b = sortedJoinInnerSubscriberArr;
            this.c = new List[i2];
            this.d = new int[i2];
            this.h.lazySet(i2);
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.f, j);
                if (this.h.get() == 0) {
                    b();
                }
            }
        }

        public void cancel() {
            if (!this.g) {
                this.g = true;
                a();
                if (getAndIncrement() == 0) {
                    Arrays.fill(this.c, null);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            for (SortedJoinInnerSubscriber<T> a2 : this.b) {
                a2.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(List<T> list, int i2) {
            this.c[i2] = list;
            if (this.h.decrementAndGet() == 0) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (this.i.compareAndSet(null, th)) {
                b();
            } else if (th != this.i.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            boolean z;
            Object obj;
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                List<T>[] listArr = this.c;
                int[] iArr = this.d;
                int length = iArr.length;
                int i2 = 1;
                while (true) {
                    long j = this.f.get();
                    long j2 = 0;
                    while (j2 != j) {
                        if (this.g) {
                            Arrays.fill(listArr, null);
                            return;
                        }
                        Throwable th = (Throwable) this.i.get();
                        if (th != null) {
                            a();
                            Arrays.fill(listArr, null);
                            subscriber.onError(th);
                            return;
                        }
                        Object obj2 = null;
                        int i3 = -1;
                        int i4 = 0;
                        while (i4 < length) {
                            List<T> list = listArr[i4];
                            int i5 = iArr[i4];
                            int i6 = i2;
                            if (list.size() != i5) {
                                if (obj2 == null) {
                                    obj = list.get(i5);
                                } else {
                                    obj = list.get(i5);
                                    try {
                                        if (!(this.e.compare(obj2, obj) > 0)) {
                                        }
                                    } catch (Throwable th2) {
                                        Throwable th3 = th2;
                                        Exceptions.throwIfFatal(th3);
                                        a();
                                        Arrays.fill(listArr, null);
                                        if (!this.i.compareAndSet(null, th3)) {
                                            RxJavaPlugins.onError(th3);
                                        }
                                        subscriber.onError((Throwable) this.i.get());
                                        return;
                                    }
                                }
                                obj2 = obj;
                                i3 = i4;
                            }
                            i4++;
                            i2 = i6;
                        }
                        int i7 = i2;
                        if (obj2 == null) {
                            Arrays.fill(listArr, null);
                            subscriber.onComplete();
                            return;
                        }
                        subscriber.onNext(obj2);
                        iArr[i3] = iArr[i3] + 1;
                        j2++;
                        i2 = i7;
                    }
                    int i8 = i2;
                    if (j2 == j) {
                        if (this.g) {
                            Arrays.fill(listArr, null);
                            return;
                        }
                        Throwable th4 = (Throwable) this.i.get();
                        if (th4 != null) {
                            a();
                            Arrays.fill(listArr, null);
                            subscriber.onError(th4);
                            return;
                        }
                        int i9 = 0;
                        while (true) {
                            if (i9 >= length) {
                                z = true;
                                break;
                            } else if (iArr[i9] != listArr[i9].size()) {
                                z = false;
                                break;
                            } else {
                                i9++;
                            }
                        }
                        if (z) {
                            Arrays.fill(listArr, null);
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                        this.f.addAndGet(-j2);
                    }
                    int i10 = get();
                    int i11 = i8;
                    if (i10 == i11) {
                        i10 = addAndGet(-i11);
                        if (i10 == 0) {
                            return;
                        }
                    }
                    i2 = i10;
                }
            }
        }
    }

    public ParallelSortedJoin(ParallelFlowable<List<T>> parallelFlowable, Comparator<? super T> comparator) {
        this.b = parallelFlowable;
        this.c = comparator;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SortedJoinSubscription sortedJoinSubscription = new SortedJoinSubscription(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(sortedJoinSubscription);
        this.b.subscribe(sortedJoinSubscription.b);
    }
}
