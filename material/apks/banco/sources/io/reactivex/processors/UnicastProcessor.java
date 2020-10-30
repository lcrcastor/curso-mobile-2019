package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class UnicastProcessor<T> extends FlowableProcessor<T> {
    final SpscLinkedArrayQueue<T> b;
    final AtomicReference<Runnable> c;
    final boolean d;
    volatile boolean e;
    Throwable f;
    final AtomicReference<Subscriber<? super T>> g;
    volatile boolean h;
    final AtomicBoolean i;
    final BasicIntQueueSubscription<T> j;
    final AtomicLong k;
    boolean l;

    final class UnicastQueueSubscription extends BasicIntQueueSubscription<T> {
        private static final long serialVersionUID = -4896760517184205454L;

        UnicastQueueSubscription() {
        }

        @Nullable
        public T poll() {
            return UnicastProcessor.this.b.poll();
        }

        public boolean isEmpty() {
            return UnicastProcessor.this.b.isEmpty();
        }

        public void clear() {
            UnicastProcessor.this.b.clear();
        }

        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.l = true;
            return 2;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(UnicastProcessor.this.k, j);
                UnicastProcessor.this.b();
            }
        }

        public void cancel() {
            if (!UnicastProcessor.this.h) {
                UnicastProcessor.this.h = true;
                UnicastProcessor.this.a();
                if (!UnicastProcessor.this.l && UnicastProcessor.this.j.getAndIncrement() == 0) {
                    UnicastProcessor.this.b.clear();
                    UnicastProcessor.this.g.lazySet(null);
                }
            }
        }
    }

    @CheckReturnValue
    public static <T> UnicastProcessor<T> create() {
        return new UnicastProcessor<>(bufferSize());
    }

    @CheckReturnValue
    public static <T> UnicastProcessor<T> create(int i2) {
        return new UnicastProcessor<>(i2);
    }

    @CheckReturnValue
    @Experimental
    public static <T> UnicastProcessor<T> create(boolean z) {
        return new UnicastProcessor<>(bufferSize(), null, z);
    }

    @CheckReturnValue
    public static <T> UnicastProcessor<T> create(int i2, Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i2, runnable);
    }

    @CheckReturnValue
    @Experimental
    public static <T> UnicastProcessor<T> create(int i2, Runnable runnable, boolean z) {
        ObjectHelper.requireNonNull(runnable, "onTerminate");
        return new UnicastProcessor<>(i2, runnable, z);
    }

    UnicastProcessor(int i2) {
        this(i2, null, true);
    }

    UnicastProcessor(int i2, Runnable runnable) {
        this(i2, runnable, true);
    }

    UnicastProcessor(int i2, Runnable runnable, boolean z) {
        this.b = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.c = new AtomicReference<>(runnable);
        this.d = z;
        this.g = new AtomicReference<>();
        this.i = new AtomicBoolean();
        this.j = new UnicastQueueSubscription();
        this.k = new AtomicLong();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        Runnable runnable = (Runnable) this.c.get();
        if (runnable != null && this.c.compareAndSet(runnable, null)) {
            runnable.run();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Subscriber<? super T> subscriber) {
        long j2;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
        boolean z = !this.d;
        int i2 = 1;
        do {
            long j3 = this.k.get();
            long j4 = 0;
            while (true) {
                if (j3 == j4) {
                    j2 = j4;
                    break;
                }
                boolean z2 = this.e;
                Object poll = spscLinkedArrayQueue.poll();
                boolean z3 = poll == null;
                j2 = j4;
                if (!a(z, z2, z3, subscriber, spscLinkedArrayQueue)) {
                    if (z3) {
                        break;
                    }
                    subscriber.onNext(poll);
                    j4 = j2 + 1;
                } else {
                    return;
                }
            }
            Subscriber<? super T> subscriber2 = subscriber;
            if (j3 == j2) {
                if (a(z, this.e, spscLinkedArrayQueue.isEmpty(), subscriber2, spscLinkedArrayQueue)) {
                    return;
                }
            }
            if (!(j2 == 0 || j3 == Long.MAX_VALUE)) {
                this.k.addAndGet(-j2);
            }
            i2 = this.j.addAndGet(-i2);
        } while (i2 != 0);
    }

    /* access modifiers changed from: 0000 */
    public void b(Subscriber<? super T> subscriber) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
        int i2 = 1;
        boolean z = !this.d;
        while (!this.h) {
            boolean z2 = this.e;
            if (!z || !z2 || this.f == null) {
                subscriber.onNext(null);
                if (z2) {
                    this.g.lazySet(null);
                    Throwable th = this.f;
                    if (th != null) {
                        subscriber.onError(th);
                    } else {
                        subscriber.onComplete();
                    }
                    return;
                }
                i2 = this.j.addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            } else {
                spscLinkedArrayQueue.clear();
                this.g.lazySet(null);
                subscriber.onError(this.f);
                return;
            }
        }
        spscLinkedArrayQueue.clear();
        this.g.lazySet(null);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.j.getAndIncrement() == 0) {
            int i2 = 1;
            Subscriber subscriber = (Subscriber) this.g.get();
            while (subscriber == null) {
                i2 = this.j.addAndGet(-i2);
                if (i2 != 0) {
                    subscriber = (Subscriber) this.g.get();
                } else {
                    return;
                }
            }
            if (this.l) {
                b(subscriber);
            } else {
                a(subscriber);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(boolean z, boolean z2, boolean z3, Subscriber<? super T> subscriber, SpscLinkedArrayQueue<T> spscLinkedArrayQueue) {
        if (this.h) {
            spscLinkedArrayQueue.clear();
            this.g.lazySet(null);
            return true;
        }
        if (z2) {
            if (z && this.f != null) {
                spscLinkedArrayQueue.clear();
                this.g.lazySet(null);
                subscriber.onError(this.f);
                return true;
            } else if (z3) {
                Throwable th = this.f;
                this.g.lazySet(null);
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    public void onSubscribe(Subscription subscription) {
        if (this.e || this.h) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (!this.e && !this.h) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            this.b.offer(t);
            b();
        }
    }

    public void onError(Throwable th) {
        if (this.e || this.h) {
            RxJavaPlugins.onError(th);
            return;
        }
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.f = th;
        this.e = true;
        a();
        b();
    }

    public void onComplete() {
        if (!this.e && !this.h) {
            this.e = true;
            a();
            b();
        }
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.i.get() || !this.i.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
            return;
        }
        subscriber.onSubscribe(this.j);
        this.g.set(subscriber);
        if (this.h) {
            this.g.lazySet(null);
        } else {
            b();
        }
    }

    public boolean hasSubscribers() {
        return this.g.get() != null;
    }

    public Throwable getThrowable() {
        if (this.e) {
            return this.f;
        }
        return null;
    }

    public boolean hasComplete() {
        return this.e && this.f == null;
    }

    public boolean hasThrowable() {
        return this.e && this.f != null;
    }
}
