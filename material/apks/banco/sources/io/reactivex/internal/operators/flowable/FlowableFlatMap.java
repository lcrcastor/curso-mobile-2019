package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends Publisher<? extends U>> b;
    final boolean c;
    final int d;
    final int e;

    static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final long a;
        final MergeSubscriber<T, U> b;
        final int c = (this.d >> 2);
        final int d;
        volatile boolean e;
        volatile SimpleQueue<U> f;
        long g;
        int h;

        InnerSubscriber(MergeSubscriber<T, U> mergeSubscriber, long j) {
            this.a = j;
            this.b = mergeSubscriber;
            this.d = mergeSubscriber.e;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.h = requestFusion;
                        this.f = queueSubscription;
                        this.e = true;
                        this.b.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.h = requestFusion;
                        this.f = queueSubscription;
                    }
                }
                subscription.request((long) this.d);
            }
        }

        public void onNext(U u) {
            if (this.h != 2) {
                this.b.a(u, this);
            } else {
                this.b.b();
            }
        }

        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.b.a(this, th);
        }

        public void onComplete() {
            this.e = true;
            this.b.b();
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            if (this.h != 1) {
                long j2 = this.g + j;
                if (j2 >= ((long) this.c)) {
                    this.g = 0;
                    ((Subscription) get()).request(j2);
                    return;
                }
                this.g = j2;
            }
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }

    static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final InnerSubscriber<?, ?>[] k = new InnerSubscriber[0];
        static final InnerSubscriber<?, ?>[] l = new InnerSubscriber[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final Subscriber<? super U> a;
        final Function<? super T, ? extends Publisher<? extends U>> b;
        final boolean c;
        final int d;
        final int e;
        volatile SimplePlainQueue<U> f;
        volatile boolean g;
        final AtomicThrowable h = new AtomicThrowable();
        volatile boolean i;
        final AtomicReference<InnerSubscriber<?, ?>[]> j = new AtomicReference<>();
        final AtomicLong m = new AtomicLong();
        Subscription n;
        long o;
        long p;
        int q;
        int r;
        final int s;

        MergeSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i2, int i3) {
            this.a = subscriber;
            this.b = function;
            this.c = z;
            this.d = i2;
            this.e = i3;
            this.s = Math.max(1, i2 >> 1);
            this.j.lazySet(k);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.n, subscription)) {
                this.n = subscription;
                this.a.onSubscribe(this);
                if (this.i) {
                    return;
                }
                if (this.d == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request((long) this.d);
                }
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null Publisher");
                    if (publisher instanceof Callable) {
                        try {
                            Object call = ((Callable) publisher).call();
                            if (call != null) {
                                a((U) call);
                            } else if (this.d != Integer.MAX_VALUE && !this.i) {
                                int i2 = this.r + 1;
                                this.r = i2;
                                if (i2 == this.s) {
                                    this.r = 0;
                                    this.n.request((long) this.s);
                                }
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.h.addThrowable(th);
                            b();
                        }
                    } else {
                        long j2 = this.o;
                        this.o = j2 + 1;
                        InnerSubscriber innerSubscriber = new InnerSubscriber(this, j2);
                        if (a(innerSubscriber)) {
                            publisher.subscribe(innerSubscriber);
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.n.cancel();
                    onError(th2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.j.get();
                if (innerSubscriberArr == l) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[(length + 1)];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.j.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            Object obj;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.j.get();
                if (innerSubscriberArr != l && innerSubscriberArr != k) {
                    int length = innerSubscriberArr.length;
                    int i2 = -1;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        } else if (innerSubscriberArr[i3] == innerSubscriber) {
                            i2 = i3;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i2 >= 0) {
                        if (length == 1) {
                            obj = k;
                        } else {
                            InnerSubscriber[] innerSubscriberArr2 = new InnerSubscriber[(length - 1)];
                            System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, i2);
                            System.arraycopy(innerSubscriberArr, i2 + 1, innerSubscriberArr2, i2, (length - i2) - 1);
                            obj = innerSubscriberArr2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.j.compareAndSet(innerSubscriberArr, obj));
        }

        /* access modifiers changed from: 0000 */
        public SimpleQueue<U> a() {
            SimplePlainQueue<U> simplePlainQueue = this.f;
            if (simplePlainQueue == null) {
                if (this.d == Integer.MAX_VALUE) {
                    simplePlainQueue = new SpscLinkedArrayQueue<>(this.e);
                } else {
                    simplePlainQueue = new SpscArrayQueue<>(this.d);
                }
                this.f = simplePlainQueue;
            }
            return simplePlainQueue;
        }

        /* access modifiers changed from: 0000 */
        public void a(U u) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j2 = this.m.get();
                SimpleQueue simpleQueue = this.f;
                if (j2 == 0 || (simpleQueue != null && !simpleQueue.isEmpty())) {
                    if (simpleQueue == null) {
                        simpleQueue = a();
                    }
                    if (!simpleQueue.offer(u)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                } else {
                    this.a.onNext(u);
                    if (j2 != Long.MAX_VALUE) {
                        this.m.decrementAndGet();
                    }
                    if (this.d != Integer.MAX_VALUE && !this.i) {
                        int i2 = this.r + 1;
                        this.r = i2;
                        if (i2 == this.s) {
                            this.r = 0;
                            this.n.request((long) this.s);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!a().offer(u)) {
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            c();
        }

        /* access modifiers changed from: 0000 */
        public SimpleQueue<U> c(InnerSubscriber<T, U> innerSubscriber) {
            SimpleQueue<U> simpleQueue = innerSubscriber.f;
            if (simpleQueue != null) {
                return simpleQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.e);
            innerSubscriber.f = spscArrayQueue;
            return spscArrayQueue;
        }

        /* access modifiers changed from: 0000 */
        public void a(U u, InnerSubscriber<T, U> innerSubscriber) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue simpleQueue = innerSubscriber.f;
                if (simpleQueue == null) {
                    simpleQueue = new SpscArrayQueue(this.e);
                    innerSubscriber.f = simpleQueue;
                }
                if (!simpleQueue.offer(u)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                long j2 = this.m.get();
                SimpleQueue<U> simpleQueue2 = innerSubscriber.f;
                if (j2 == 0 || (simpleQueue2 != null && !simpleQueue2.isEmpty())) {
                    if (simpleQueue2 == null) {
                        simpleQueue2 = c(innerSubscriber);
                    }
                    if (!simpleQueue2.offer(u)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                } else {
                    this.a.onNext(u);
                    if (j2 != Long.MAX_VALUE) {
                        this.m.decrementAndGet();
                    }
                    innerSubscriber.a(1);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            c();
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.h.addThrowable(th)) {
                this.g = true;
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                b();
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.m, j2);
                b();
            }
        }

        public void cancel() {
            if (!this.i) {
                this.i = true;
                this.n.cancel();
                f();
                if (getAndIncrement() == 0) {
                    SimplePlainQueue<U> simplePlainQueue = this.f;
                    if (simplePlainQueue != null) {
                        simplePlainQueue.clear();
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            long j2;
            Subscriber<? super U> subscriber;
            int i2;
            boolean z;
            long j3;
            int i3;
            int i4;
            int i5;
            long j4;
            Subscriber<? super U> subscriber2 = this.a;
            int i6 = 1;
            while (!d()) {
                SimplePlainQueue<U> simplePlainQueue = this.f;
                long j5 = this.m.get();
                boolean z2 = j5 == Long.MAX_VALUE;
                if (simplePlainQueue != null) {
                    j2 = 0;
                    while (true) {
                        long j6 = 0;
                        Object obj = null;
                        while (true) {
                            if (j5 == 0) {
                                break;
                            }
                            Object poll = simplePlainQueue.poll();
                            if (!d()) {
                                if (poll == null) {
                                    obj = poll;
                                    break;
                                }
                                subscriber2.onNext(poll);
                                j5--;
                                j6++;
                                j2++;
                                obj = poll;
                            } else {
                                return;
                            }
                        }
                        if (j6 != 0) {
                            if (z2) {
                                j5 = Long.MAX_VALUE;
                            } else {
                                j5 = this.m.addAndGet(-j6);
                            }
                        }
                        if (j5 == 0 || obj == null) {
                            break;
                        }
                    }
                } else {
                    j2 = 0;
                }
                boolean z3 = this.g;
                SimplePlainQueue<U> simplePlainQueue2 = this.f;
                InnerSubscriber[] innerSubscriberArr = (InnerSubscriber[]) this.j.get();
                int length = innerSubscriberArr.length;
                if (!z3 || ((simplePlainQueue2 != null && !simplePlainQueue2.isEmpty()) || length != 0)) {
                    if (length != 0) {
                        i2 = i6;
                        long j7 = this.p;
                        int i7 = this.q;
                        if (length <= i7 || innerSubscriberArr[i7].a != j7) {
                            if (length <= i7) {
                                i7 = 0;
                            }
                            int i8 = i7;
                            for (int i9 = 0; i9 < length && innerSubscriberArr[i8].a != j7; i9++) {
                                i8++;
                                if (i8 == length) {
                                    i8 = 0;
                                }
                            }
                            this.q = i8;
                            this.p = innerSubscriberArr[i8].a;
                            i7 = i8;
                        }
                        int i10 = 0;
                        z = false;
                        while (true) {
                            if (i10 >= length) {
                                subscriber = subscriber2;
                                break;
                            } else if (!d()) {
                                InnerSubscriber innerSubscriber = innerSubscriberArr[i7];
                                Object obj2 = null;
                                while (!d()) {
                                    SimpleQueue<U> simpleQueue = innerSubscriber.f;
                                    if (simpleQueue == null) {
                                        subscriber = subscriber2;
                                        i3 = i10;
                                        i4 = length;
                                    } else {
                                        i4 = length;
                                        Object obj3 = obj2;
                                        long j8 = 0;
                                        while (j5 != 0) {
                                            try {
                                                obj3 = simpleQueue.poll();
                                                if (obj3 == null) {
                                                    break;
                                                }
                                                subscriber2.onNext(obj3);
                                                if (!d()) {
                                                    j8++;
                                                    j5--;
                                                } else {
                                                    return;
                                                }
                                            } catch (Throwable th) {
                                                Exceptions.throwIfFatal(th);
                                                innerSubscriber.dispose();
                                                this.h.addThrowable(th);
                                                if (!d()) {
                                                    b(innerSubscriber);
                                                    subscriber = subscriber2;
                                                    i3 = i10 + 1;
                                                    i5 = i4;
                                                    z = true;
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                        if (j8 != 0) {
                                            if (!z2) {
                                                subscriber = subscriber2;
                                                i3 = i10;
                                                j4 = this.m.addAndGet(-j8);
                                            } else {
                                                subscriber = subscriber2;
                                                i3 = i10;
                                                j4 = Long.MAX_VALUE;
                                            }
                                            innerSubscriber.a(j8);
                                            j5 = j4;
                                        } else {
                                            subscriber = subscriber2;
                                            i3 = i10;
                                        }
                                        if (!(j5 == 0 || obj3 == null)) {
                                            obj2 = obj3;
                                            length = i4;
                                            subscriber2 = subscriber;
                                            i10 = i3;
                                        }
                                    }
                                    boolean z4 = innerSubscriber.e;
                                    SimpleQueue<U> simpleQueue2 = innerSubscriber.f;
                                    if (z4 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                        b(innerSubscriber);
                                        if (!d()) {
                                            j2++;
                                            z = true;
                                        } else {
                                            return;
                                        }
                                    }
                                    if (j5 == 0) {
                                        break;
                                    }
                                    int i11 = i7 + 1;
                                    i5 = i4;
                                    i7 = i11 == i5 ? 0 : i11;
                                    length = i5;
                                    i10 = i3 + 1;
                                    subscriber2 = subscriber;
                                }
                                return;
                            } else {
                                return;
                            }
                        }
                        this.q = i7;
                        this.p = innerSubscriberArr[i7].a;
                        j3 = j2;
                    } else {
                        subscriber = subscriber2;
                        i2 = i6;
                        j3 = j2;
                        z = false;
                    }
                    if (j3 != 0 && !this.i) {
                        this.n.request(j3);
                    }
                    if (z) {
                        i6 = i2;
                    } else {
                        i6 = addAndGet(-i2);
                        if (i6 == 0) {
                            return;
                        }
                    }
                    subscriber2 = subscriber;
                } else {
                    Throwable terminate = this.h.terminate();
                    if (terminate != ExceptionHelper.TERMINATED) {
                        if (terminate == null) {
                            subscriber2.onComplete();
                        } else {
                            subscriber2.onError(terminate);
                        }
                    }
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            if (this.i) {
                e();
                return true;
            } else if (this.c || this.h.get() == null) {
                return false;
            } else {
                e();
                Throwable terminate = this.h.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.a.onError(terminate);
                }
                return true;
            }
        }

        /* access modifiers changed from: 0000 */
        public void e() {
            SimplePlainQueue<U> simplePlainQueue = this.f;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            if (((InnerSubscriber[]) this.j.get()) != l) {
                InnerSubscriber<?, ?>[] innerSubscriberArr = (InnerSubscriber[]) this.j.getAndSet(l);
                if (innerSubscriberArr != l) {
                    for (InnerSubscriber<?, ?> dispose : innerSubscriberArr) {
                        dispose.dispose();
                    }
                    Throwable terminate = this.h.terminate();
                    if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
                        RxJavaPlugins.onError(terminate);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerSubscriber<T, U> innerSubscriber, Throwable th) {
            if (this.h.addThrowable(th)) {
                innerSubscriber.e = true;
                if (!this.c) {
                    this.n.cancel();
                    for (InnerSubscriber dispose : (InnerSubscriber[]) this.j.getAndSet(l)) {
                        dispose.dispose();
                    }
                }
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe(subscribe(subscriber, this.b, this.c, this.d, this.e));
        }
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        MergeSubscriber mergeSubscriber = new MergeSubscriber(subscriber, function, z, i, i2);
        return mergeSubscriber;
    }
}
