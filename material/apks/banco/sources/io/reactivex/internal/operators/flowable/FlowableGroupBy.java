package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupBy<T, K, V> extends AbstractFlowableWithUpstream<T, GroupedFlowable<K, V>> {
    final Function<? super T, ? extends K> b;
    final Function<? super T, ? extends V> c;
    final int d;
    final boolean e;

    public static final class GroupBySubscriber<T, K, V> extends BasicIntQueueSubscription<GroupedFlowable<K, V>> implements FlowableSubscriber<T> {
        static final Object h = new Object();
        private static final long serialVersionUID = -3688291656102519502L;
        final Subscriber<? super GroupedFlowable<K, V>> a;
        final Function<? super T, ? extends K> b;
        final Function<? super T, ? extends V> c;
        final int d;
        final boolean e;
        final Map<Object, GroupedUnicast<K, V>> f;
        final SpscLinkedArrayQueue<GroupedFlowable<K, V>> g;
        Subscription i;
        final AtomicBoolean j = new AtomicBoolean();
        final AtomicLong k = new AtomicLong();
        final AtomicInteger l = new AtomicInteger(1);
        Throwable m;
        volatile boolean n;
        boolean o;

        public GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> subscriber, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i2, boolean z) {
            this.a = subscriber;
            this.b = function;
            this.c = function2;
            this.d = i2;
            this.e = z;
            this.f = new ConcurrentHashMap();
            this.g = new SpscLinkedArrayQueue<>(i2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                this.a.onSubscribe(this);
                subscription.request((long) this.d);
            }
        }

        public void onNext(T t) {
            Object obj;
            if (!this.n) {
                SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.g;
                try {
                    Object apply = this.b.apply(t);
                    boolean z = false;
                    if (apply != null) {
                        obj = apply;
                    } else {
                        obj = h;
                    }
                    GroupedUnicast groupedUnicast = (GroupedUnicast) this.f.get(obj);
                    if (groupedUnicast == null) {
                        if (!this.j.get()) {
                            groupedUnicast = GroupedUnicast.a(apply, this.d, this, this.e);
                            this.f.put(obj, groupedUnicast);
                            this.l.getAndIncrement();
                            z = true;
                        } else {
                            return;
                        }
                    }
                    try {
                        groupedUnicast.a(ObjectHelper.requireNonNull(this.c.apply(t), "The valueSelector returned null"));
                        if (z) {
                            spscLinkedArrayQueue.offer(groupedUnicast);
                            a();
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.i.cancel();
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.i.cancel();
                    onError(th2);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.n) {
                RxJavaPlugins.onError(th);
                return;
            }
            for (GroupedUnicast a2 : this.f.values()) {
                a2.a(th);
            }
            this.f.clear();
            this.m = th;
            this.n = true;
            a();
        }

        public void onComplete() {
            if (!this.n) {
                for (GroupedUnicast a2 : this.f.values()) {
                    a2.a();
                }
                this.f.clear();
                this.n = true;
                a();
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.k, j2);
                a();
            }
        }

        public void cancel() {
            if (this.j.compareAndSet(false, true) && this.l.decrementAndGet() == 0) {
                this.i.cancel();
            }
        }

        public void cancel(K k2) {
            if (k2 == null) {
                k2 = h;
            }
            this.f.remove(k2);
            if (this.l.decrementAndGet() == 0) {
                this.i.cancel();
                if (getAndIncrement() == 0) {
                    this.g.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                if (this.o) {
                    b();
                } else {
                    c();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.g;
            Subscriber<? super GroupedFlowable<K, V>> subscriber = this.a;
            int i2 = 1;
            while (!this.j.get()) {
                boolean z = this.n;
                if (z && !this.e) {
                    Throwable th = this.m;
                    if (th != null) {
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(th);
                        return;
                    }
                }
                subscriber.onNext(null);
                if (z) {
                    Throwable th2 = this.m;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    return;
                }
                i2 = addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            }
            spscLinkedArrayQueue.clear();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.g;
            Subscriber<? super GroupedFlowable<K, V>> subscriber = this.a;
            int i2 = 1;
            do {
                long j2 = this.k.get();
                long j3 = 0;
                while (j3 != j2) {
                    boolean z = this.n;
                    GroupedFlowable groupedFlowable = (GroupedFlowable) spscLinkedArrayQueue.poll();
                    boolean z2 = groupedFlowable == null;
                    if (!a(z, z2, subscriber, spscLinkedArrayQueue)) {
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(groupedFlowable);
                        j3++;
                    } else {
                        return;
                    }
                }
                if (j3 != j2 || !a(this.n, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    if (j3 != 0) {
                        if (j2 != Long.MAX_VALUE) {
                            this.k.addAndGet(-j3);
                        }
                        this.i.request(j3);
                    }
                    i2 = addAndGet(-i2);
                } else {
                    return;
                }
            } while (i2 != 0);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.j.get()) {
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (this.e) {
                if (z && z2) {
                    Throwable th = this.m;
                    if (th != null) {
                        subscriber.onError(th);
                    } else {
                        subscriber.onComplete();
                    }
                    return true;
                }
            } else if (z) {
                Throwable th2 = this.m;
                if (th2 != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th2);
                    return true;
                } else if (z2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }

        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            this.o = true;
            return 2;
        }

        @Nullable
        public GroupedFlowable<K, V> poll() {
            return (GroupedFlowable) this.g.poll();
        }

        public void clear() {
            this.g.clear();
        }

        public boolean isEmpty() {
            return this.g.isEmpty();
        }
    }

    static final class GroupedUnicast<K, T> extends GroupedFlowable<K, T> {
        final State<T, K> c;

        public static <T, K> GroupedUnicast<K, T> a(K k, int i, GroupBySubscriber<?, K, T> groupBySubscriber, boolean z) {
            return new GroupedUnicast<>(k, new State(i, groupBySubscriber, k, z));
        }

        protected GroupedUnicast(K k, State<T, K> state) {
            super(k);
            this.c = state;
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }

        public void a(T t) {
            this.c.a(t);
        }

        public void a(Throwable th) {
            this.c.a(th);
        }

        public void a() {
            this.c.a();
        }
    }

    static final class State<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
        private static final long serialVersionUID = -3852313036005250360L;
        final K a;
        final SpscLinkedArrayQueue<T> b;
        final GroupBySubscriber<?, K, T> c;
        final boolean d;
        final AtomicLong e = new AtomicLong();
        volatile boolean f;
        Throwable g;
        final AtomicBoolean h = new AtomicBoolean();
        final AtomicReference<Subscriber<? super T>> i = new AtomicReference<>();
        final AtomicBoolean j = new AtomicBoolean();
        boolean k;
        int l;

        State(int i2, GroupBySubscriber<?, K, T> groupBySubscriber, K k2, boolean z) {
            this.b = new SpscLinkedArrayQueue<>(i2);
            this.c = groupBySubscriber;
            this.a = k2;
            this.d = z;
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.e, j2);
                b();
            }
        }

        public void cancel() {
            if (this.h.compareAndSet(false, true)) {
                this.c.cancel(this.a);
            }
        }

        public void subscribe(Subscriber<? super T> subscriber) {
            if (this.j.compareAndSet(false, true)) {
                subscriber.onSubscribe(this);
                this.i.lazySet(subscriber);
                b();
                return;
            }
            EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
        }

        public void a(T t) {
            this.b.offer(t);
            b();
        }

        public void a(Throwable th) {
            this.g = th;
            this.f = true;
            b();
        }

        public void a() {
            this.f = true;
            b();
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                if (this.k) {
                    c();
                } else {
                    d();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
            Subscriber subscriber = (Subscriber) this.i.get();
            int i2 = 1;
            while (true) {
                if (subscriber != null) {
                    if (this.h.get()) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z = this.f;
                    if (z && !this.d) {
                        Throwable th = this.g;
                        if (th != null) {
                            spscLinkedArrayQueue.clear();
                            subscriber.onError(th);
                            return;
                        }
                    }
                    subscriber.onNext(null);
                    if (z) {
                        Throwable th2 = this.g;
                        if (th2 != null) {
                            subscriber.onError(th2);
                        } else {
                            subscriber.onComplete();
                        }
                        return;
                    }
                }
                i2 = addAndGet(-i2);
                if (i2 != 0) {
                    if (subscriber == null) {
                        subscriber = (Subscriber) this.i.get();
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.b;
            boolean z = this.d;
            Subscriber subscriber = (Subscriber) this.i.get();
            int i2 = 1;
            while (true) {
                if (subscriber != null) {
                    long j2 = this.e.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        boolean z2 = this.f;
                        Object poll = spscLinkedArrayQueue.poll();
                        boolean z3 = poll == null;
                        if (!a(z2, z3, subscriber, z)) {
                            if (z3) {
                                break;
                            }
                            subscriber.onNext(poll);
                            j3++;
                        } else {
                            return;
                        }
                    }
                    if (j3 == j2 && a(this.f, spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                        return;
                    }
                    if (j3 != 0) {
                        if (j2 != Long.MAX_VALUE) {
                            this.e.addAndGet(-j3);
                        }
                        this.c.i.request(j3);
                    }
                }
                i2 = addAndGet(-i2);
                if (i2 != 0) {
                    if (subscriber == null) {
                        subscriber = (Subscriber) this.i.get();
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3) {
            if (this.h.get()) {
                this.b.clear();
                return true;
            }
            if (z) {
                if (!z3) {
                    Throwable th = this.g;
                    if (th != null) {
                        this.b.clear();
                        subscriber.onError(th);
                        return true;
                    } else if (z2) {
                        subscriber.onComplete();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = this.g;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    return true;
                }
            }
            return false;
        }

        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            this.k = true;
            return 2;
        }

        @Nullable
        public T poll() {
            T poll = this.b.poll();
            if (poll != null) {
                this.l++;
                return poll;
            }
            int i2 = this.l;
            if (i2 != 0) {
                this.l = 0;
                this.c.i.request((long) i2);
            }
            return null;
        }

        public boolean isEmpty() {
            return this.b.isEmpty();
        }

        public void clear() {
            this.b.clear();
        }
    }

    public FlowableGroupBy(Flowable<T> flowable, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = function2;
        this.d = i;
        this.e = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super GroupedFlowable<K, V>> subscriber) {
        Flowable flowable = this.source;
        GroupBySubscriber groupBySubscriber = new GroupBySubscriber(subscriber, this.b, this.c, this.d, this.e);
        flowable.subscribe((FlowableSubscriber<? super T>) groupBySubscriber);
    }
}
