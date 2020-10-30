package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCombineLatest<T, R> extends Flowable<R> {
    @Nullable
    final Publisher<? extends T>[] b;
    @Nullable
    final Iterable<? extends Publisher<? extends T>> c;
    final Function<? super Object[], ? extends R> d;
    final int e;
    final boolean f;

    static final class CombineLatestCoordinator<T, R> extends BasicIntQueueSubscription<R> {
        private static final long serialVersionUID = -5082275438355852221L;
        final Subscriber<? super R> a;
        final Function<? super Object[], ? extends R> b;
        final CombineLatestInnerSubscriber<T>[] c;
        final SpscLinkedArrayQueue<Object> d;
        final Object[] e;
        final boolean f;
        boolean g;
        int h;
        int i;
        volatile boolean j;
        final AtomicLong k;
        volatile boolean l;
        final AtomicReference<Throwable> m;

        CombineLatestCoordinator(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i2, int i3, boolean z) {
            this.a = subscriber;
            this.b = function;
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = new CombineLatestInnerSubscriber[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                combineLatestInnerSubscriberArr[i4] = new CombineLatestInnerSubscriber<>(this, i4, i3);
            }
            this.c = combineLatestInnerSubscriberArr;
            this.e = new Object[i2];
            this.d = new SpscLinkedArrayQueue<>(i3);
            this.k = new AtomicLong();
            this.m = new AtomicReference<>();
            this.f = z;
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.k, j2);
                c();
            }
        }

        public void cancel() {
            this.j = true;
            d();
        }

        /* access modifiers changed from: 0000 */
        public void a(Publisher<? extends T>[] publisherArr, int i2) {
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = this.c;
            for (int i3 = 0; i3 < i2 && !this.l && !this.j; i3++) {
                publisherArr[i3].subscribe(combineLatestInnerSubscriberArr[i3]);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.e;
                int i3 = this.h;
                if (objArr[i2] == null) {
                    i3++;
                    this.h = i3;
                }
                objArr[i2] = t;
                if (objArr.length == i3) {
                    this.d.offer(this.c[i2], objArr.clone());
                    z = false;
                } else {
                    z = true;
                }
            }
            if (z) {
                this.c[i2].b();
            } else {
                c();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
            c();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(int r3) {
            /*
                r2 = this;
                monitor-enter(r2)
                java.lang.Object[] r0 = r2.e     // Catch:{ all -> 0x001c }
                r3 = r0[r3]     // Catch:{ all -> 0x001c }
                r1 = 1
                if (r3 == 0) goto L_0x0015
                int r3 = r2.i     // Catch:{ all -> 0x001c }
                int r3 = r3 + r1
                int r0 = r0.length     // Catch:{ all -> 0x001c }
                if (r3 != r0) goto L_0x0011
                r2.l = r1     // Catch:{ all -> 0x001c }
                goto L_0x0017
            L_0x0011:
                r2.i = r3     // Catch:{ all -> 0x001c }
                monitor-exit(r2)     // Catch:{ all -> 0x001c }
                return
            L_0x0015:
                r2.l = r1     // Catch:{ all -> 0x001c }
            L_0x0017:
                monitor-exit(r2)     // Catch:{ all -> 0x001c }
                r2.c()
                return
            L_0x001c:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x001c }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableCombineLatest.CombineLatestCoordinator.a(int):void");
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, Throwable th) {
            if (!ExceptionHelper.addThrowable(this.m, th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.f) {
                d();
                this.l = true;
                c();
            } else {
                a(i2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Subscriber<? super R> subscriber = this.a;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.d;
            int i2 = 1;
            while (!this.j) {
                Throwable th = (Throwable) this.m.get();
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z = this.l;
                boolean isEmpty = spscLinkedArrayQueue.isEmpty();
                if (!isEmpty) {
                    subscriber.onNext(null);
                }
                if (!z || !isEmpty) {
                    i2 = addAndGet(-i2);
                    if (i2 == 0) {
                        return;
                    }
                } else {
                    subscriber.onComplete();
                    return;
                }
            }
            spscLinkedArrayQueue.clear();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            Subscriber<? super R> subscriber = this.a;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.d;
            int i2 = 1;
            do {
                long j2 = this.k.get();
                long j3 = 0;
                while (j3 != j2) {
                    boolean z = this.l;
                    Object poll = spscLinkedArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (!a(z, z2, subscriber, spscLinkedArrayQueue)) {
                        if (z2) {
                            break;
                        }
                        try {
                            subscriber.onNext(ObjectHelper.requireNonNull(this.b.apply((Object[]) spscLinkedArrayQueue.poll()), "The combiner returned a null value"));
                            ((CombineLatestInnerSubscriber) poll).b();
                            j3++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            d();
                            ExceptionHelper.addThrowable(this.m, th);
                            subscriber.onError(ExceptionHelper.terminate(this.m));
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if (j3 != j2 || !a(this.l, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    if (!(j3 == 0 || j2 == Long.MAX_VALUE)) {
                        this.k.addAndGet(-j3);
                    }
                    i2 = addAndGet(-i2);
                } else {
                    return;
                }
            } while (i2 != 0);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (getAndIncrement() == 0) {
                if (this.g) {
                    a();
                } else {
                    b();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.j) {
                d();
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (z) {
                if (!this.f) {
                    Throwable terminate = ExceptionHelper.terminate(this.m);
                    if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
                        d();
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(terminate);
                        return true;
                    } else if (z2) {
                        d();
                        subscriber.onComplete();
                        return true;
                    }
                } else if (z2) {
                    d();
                    Throwable terminate2 = ExceptionHelper.terminate(this.m);
                    if (terminate2 == null || terminate2 == ExceptionHelper.TERMINATED) {
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(terminate2);
                    }
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            for (CombineLatestInnerSubscriber<T> a2 : this.c) {
                a2.a();
            }
        }

        public int requestFusion(int i2) {
            boolean z = false;
            if ((i2 & 4) != 0) {
                return 0;
            }
            int i3 = i2 & 2;
            if (i3 != 0) {
                z = true;
            }
            this.g = z;
            return i3;
        }

        @Nullable
        public R poll() {
            Object poll = this.d.poll();
            if (poll == null) {
                return null;
            }
            R apply = this.b.apply((Object[]) this.d.poll());
            ((CombineLatestInnerSubscriber) poll).b();
            return apply;
        }

        public void clear() {
            this.d.clear();
        }

        public boolean isEmpty() {
            return this.d.isEmpty();
        }
    }

    static final class CombineLatestInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -8730235182291002949L;
        final CombineLatestCoordinator<T, ?> a;
        final int b;
        final int c;
        final int d;
        int e;

        CombineLatestInnerSubscriber(CombineLatestCoordinator<T, ?> combineLatestCoordinator, int i, int i2) {
            this.a = combineLatestCoordinator;
            this.b = i;
            this.c = i2;
            this.d = i2 - (i2 >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request((long) this.c);
            }
        }

        public void onNext(T t) {
            this.a.a(this.b, t);
        }

        public void onError(Throwable th) {
            this.a.a(this.b, th);
        }

        public void onComplete() {
            this.a.a(this.b);
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }

        public void b() {
            int i = this.e + 1;
            if (i == this.d) {
                this.e = 0;
                ((Subscription) get()).request((long) i);
                return;
            }
            this.e = i;
        }
    }

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return FlowableCombineLatest.this.d.apply(new Object[]{t});
        }
    }

    public FlowableCombineLatest(@NonNull Publisher<? extends T>[] publisherArr, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = publisherArr;
        this.c = null;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    public FlowableCombineLatest(@NonNull Iterable<? extends Publisher<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.b = null;
        this.c = iterable;
        this.d = function;
        this.e = i;
        this.f = z;
    }

    public void subscribeActual(Subscriber<? super R> subscriber) {
        int i;
        Publisher<? extends T>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator it = (Iterator) ObjectHelper.requireNonNull(this.c.iterator(), "The iterator returned is null");
                i = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) ObjectHelper.requireNonNull(it.next(), "The publisher returned by the iterator is null");
                            if (i == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[((i >> 2) + i)];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                                publisherArr = publisherArr2;
                            }
                            int i2 = i + 1;
                            publisherArr[i] = publisher;
                            i = i2;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            EmptySubscription.error(th, subscriber);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        EmptySubscription.error(th2, subscriber);
                        return;
                    }
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                EmptySubscription.error(th3, subscriber);
                return;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            EmptySubscription.complete(subscriber);
        } else if (i == 1) {
            publisherArr[0].subscribe(new MapSubscriber(subscriber, new SingletonArrayFunc()));
        } else {
            CombineLatestCoordinator combineLatestCoordinator = new CombineLatestCoordinator(subscriber, this.d, i, this.e, this.f);
            subscriber.onSubscribe(combineLatestCoordinator);
            combineLatestCoordinator.a(publisherArr, i);
        }
    }
}
