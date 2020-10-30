package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.LinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCache<T> extends AbstractFlowableWithUpstream<T, T> {
    final CacheState<T> b;
    final AtomicBoolean c = new AtomicBoolean();

    static final class CacheState<T> extends LinkedArrayList implements FlowableSubscriber<T> {
        static final ReplaySubscription[] d = new ReplaySubscription[0];
        static final ReplaySubscription[] e = new ReplaySubscription[0];
        final Flowable<T> a;
        final AtomicReference<Subscription> b = new AtomicReference<>();
        final AtomicReference<ReplaySubscription<T>[]> c;
        volatile boolean f;
        boolean g;

        CacheState(Flowable<T> flowable, int i) {
            super(i);
            this.a = flowable;
            this.c = new AtomicReference<>(d);
        }

        public void a(ReplaySubscription<T> replaySubscription) {
            ReplaySubscription[] replaySubscriptionArr;
            ReplaySubscription[] replaySubscriptionArr2;
            do {
                replaySubscriptionArr = (ReplaySubscription[]) this.c.get();
                if (replaySubscriptionArr != e) {
                    int length = replaySubscriptionArr.length;
                    replaySubscriptionArr2 = new ReplaySubscription[(length + 1)];
                    System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr2, 0, length);
                    replaySubscriptionArr2[length] = replaySubscription;
                } else {
                    return;
                }
            } while (!this.c.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
        }

        public void b(ReplaySubscription<T> replaySubscription) {
            ReplaySubscription[] replaySubscriptionArr;
            ReplaySubscription[] replaySubscriptionArr2;
            do {
                replaySubscriptionArr = (ReplaySubscription[]) this.c.get();
                int length = replaySubscriptionArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (replaySubscriptionArr[i2].equals(replaySubscription)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            replaySubscriptionArr2 = d;
                        } else {
                            ReplaySubscription[] replaySubscriptionArr3 = new ReplaySubscription[(length - 1)];
                            System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr3, 0, i);
                            System.arraycopy(replaySubscriptionArr, i + 1, replaySubscriptionArr3, i, (length - i) - 1);
                            replaySubscriptionArr2 = replaySubscriptionArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.c.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.b, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void a() {
            this.a.subscribe((FlowableSubscriber<? super T>) this);
            this.f = true;
        }

        public void onNext(T t) {
            if (!this.g) {
                add(NotificationLite.next(t));
                for (ReplaySubscription a2 : (ReplaySubscription[]) this.c.get()) {
                    a2.a();
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.g) {
                this.g = true;
                add(NotificationLite.error(th));
                SubscriptionHelper.cancel(this.b);
                for (ReplaySubscription a2 : (ReplaySubscription[]) this.c.getAndSet(e)) {
                    a2.a();
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                add(NotificationLite.complete());
                SubscriptionHelper.cancel(this.b);
                for (ReplaySubscription a2 : (ReplaySubscription[]) this.c.getAndSet(e)) {
                    a2.a();
                }
            }
        }
    }

    static final class ReplaySubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = -2557562030197141021L;
        final Subscriber<? super T> a;
        final CacheState<T> b;
        final AtomicLong c = new AtomicLong();
        Object[] d;
        int e;
        int f;

        ReplaySubscription(Subscriber<? super T> subscriber, CacheState<T> cacheState) {
            this.a = subscriber;
            this.b = cacheState;
        }

        public void request(long j) {
            long j2;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = this.c.get();
                    if (j2 != -1) {
                    } else {
                        return;
                    }
                } while (!this.c.compareAndSet(j2, BackpressureHelper.addCap(j2, j)));
                a();
            }
        }

        public void cancel() {
            if (this.c.getAndSet(-1) != -1) {
                this.b.b(this);
            }
        }

        public void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.a;
                AtomicLong atomicLong = this.c;
                int i = 1;
                int i2 = 1;
                while (true) {
                    long j = atomicLong.get();
                    if (j >= 0) {
                        int size = this.b.size();
                        if (size != 0) {
                            Object[] objArr = this.d;
                            if (objArr == null) {
                                objArr = this.b.head();
                                this.d = objArr;
                            }
                            int length = objArr.length - i;
                            int i3 = this.f;
                            int i4 = this.e;
                            int i5 = 0;
                            while (i3 < size && j > 0) {
                                if (atomicLong.get() != -1) {
                                    if (i4 == length) {
                                        objArr = (Object[]) objArr[length];
                                        i4 = 0;
                                    }
                                    if (!NotificationLite.accept(objArr[i4], subscriber)) {
                                        i4++;
                                        i3++;
                                        i5++;
                                        j--;
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                            if (atomicLong.get() != -1) {
                                if (j == 0) {
                                    Object obj = objArr[i4];
                                    if (NotificationLite.isComplete(obj)) {
                                        subscriber.onComplete();
                                        return;
                                    } else if (NotificationLite.isError(obj)) {
                                        subscriber.onError(NotificationLite.getError(obj));
                                        return;
                                    }
                                }
                                if (i5 != 0) {
                                    BackpressureHelper.producedCancel(atomicLong, (long) i5);
                                }
                                this.f = i3;
                                this.e = i4;
                                this.d = objArr;
                            } else {
                                return;
                            }
                        }
                        i2 = addAndGet(-i2);
                        if (i2 != 0) {
                            i = 1;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public FlowableCache(Flowable<T> flowable, int i) {
        super(flowable);
        this.b = new CacheState<>(flowable, i);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ReplaySubscription replaySubscription = new ReplaySubscription(subscriber, this.b);
        this.b.a(replaySubscription);
        subscriber.onSubscribe(replaySubscription);
        if (!this.c.get() && this.c.compareAndSet(false, true)) {
            this.b.a();
        }
    }
}
