package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduceFull<T> extends Flowable<T> {
    final ParallelFlowable<? extends T> b;
    final BiFunction<T, T, T> c;

    static final class ParallelReduceFullInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7954444275102466525L;
        final ParallelReduceFullMainSubscriber<T> a;
        final BiFunction<T, T, T> b;
        T c;
        boolean d;

        ParallelReduceFullInnerSubscriber(ParallelReduceFullMainSubscriber<T> parallelReduceFullMainSubscriber, BiFunction<T, T, T> biFunction) {
            this.a = parallelReduceFullMainSubscriber;
            this.b = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                T t2 = this.c;
                if (t2 == null) {
                    this.c = t;
                } else {
                    try {
                        this.c = ObjectHelper.requireNonNull(this.b.apply(t2, t), "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        ((Subscription) get()).cancel();
                        onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.a.a(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.a.b(this.c);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    static final class ParallelReduceFullMainSubscriber<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = -5370107872170712765L;
        final ParallelReduceFullInnerSubscriber<T>[] a;
        final BiFunction<T, T, T> b;
        final AtomicReference<SlotPair<T>> c = new AtomicReference<>();
        final AtomicInteger d = new AtomicInteger();
        final AtomicReference<Throwable> e = new AtomicReference<>();

        ParallelReduceFullMainSubscriber(Subscriber<? super T> subscriber, int i, BiFunction<T, T, T> biFunction) {
            super(subscriber);
            ParallelReduceFullInnerSubscriber<T>[] parallelReduceFullInnerSubscriberArr = new ParallelReduceFullInnerSubscriber[i];
            for (int i2 = 0; i2 < i; i2++) {
                parallelReduceFullInnerSubscriberArr[i2] = new ParallelReduceFullInnerSubscriber<>(this, biFunction);
            }
            this.a = parallelReduceFullInnerSubscriberArr;
            this.b = biFunction;
            this.d.lazySet(i);
        }

        /* access modifiers changed from: 0000 */
        public SlotPair<T> a(T t) {
            SlotPair<T> slotPair;
            int a2;
            while (true) {
                slotPair = (SlotPair) this.c.get();
                if (slotPair == null) {
                    slotPair = new SlotPair<>();
                    if (!this.c.compareAndSet(null, slotPair)) {
                        continue;
                    }
                }
                a2 = slotPair.a();
                if (a2 >= 0) {
                    break;
                }
                this.c.compareAndSet(slotPair, null);
            }
            if (a2 == 0) {
                slotPair.a = t;
            } else {
                slotPair.b = t;
            }
            if (!slotPair.b()) {
                return null;
            }
            this.c.compareAndSet(slotPair, null);
            return slotPair;
        }

        public void cancel() {
            for (ParallelReduceFullInnerSubscriber<T> a2 : this.a) {
                a2.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (this.e.compareAndSet(null, th)) {
                cancel();
                this.actual.onError(th);
            } else if (th != this.e.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(T t) {
            if (t != null) {
                while (true) {
                    SlotPair a2 = a(t);
                    if (a2 == null) {
                        break;
                    }
                    try {
                        t = ObjectHelper.requireNonNull(this.b.apply(a2.a, a2.b), "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        a(th);
                        return;
                    }
                }
            }
            if (this.d.decrementAndGet() == 0) {
                SlotPair slotPair = (SlotPair) this.c.get();
                this.c.lazySet(null);
                if (slotPair != null) {
                    complete(slotPair.a);
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }

    static final class SlotPair<T> extends AtomicInteger {
        private static final long serialVersionUID = 473971317683868662L;
        T a;
        T b;
        final AtomicInteger c = new AtomicInteger();

        SlotPair() {
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            int i;
            do {
                i = get();
                if (i >= 2) {
                    return -1;
                }
            } while (!compareAndSet(i, i + 1));
            return i;
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.c.incrementAndGet() == 2;
        }
    }

    public ParallelReduceFull(ParallelFlowable<? extends T> parallelFlowable, BiFunction<T, T, T> biFunction) {
        this.b = parallelFlowable;
        this.c = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ParallelReduceFullMainSubscriber parallelReduceFullMainSubscriber = new ParallelReduceFullMainSubscriber(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(parallelReduceFullMainSubscriber);
        this.b.subscribe(parallelReduceFullMainSubscriber.a);
    }
}
