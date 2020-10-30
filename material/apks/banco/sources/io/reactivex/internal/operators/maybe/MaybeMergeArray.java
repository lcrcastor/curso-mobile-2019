package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;

public final class MaybeMergeArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] b;

    static final class ClqSimpleQueue<T> extends ConcurrentLinkedQueue<T> implements SimpleQueueWithConsumerIndex<T> {
        private static final long serialVersionUID = -4025173261791142821L;
        int a;
        final AtomicInteger b = new AtomicInteger();

        ClqSimpleQueue() {
        }

        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        public boolean offer(T t) {
            this.b.getAndIncrement();
            return super.offer(t);
        }

        @Nullable
        public T poll() {
            T poll = super.poll();
            if (poll != null) {
                this.a++;
            }
            return poll;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b.get();
        }

        public void c() {
            poll();
        }
    }

    static final class MergeMaybeObserver<T> extends BasicIntQueueSubscription<T> implements MaybeObserver<T> {
        private static final long serialVersionUID = -660395290758764731L;
        final Subscriber<? super T> a;
        final CompositeDisposable b = new CompositeDisposable();
        final AtomicLong c = new AtomicLong();
        final SimpleQueueWithConsumerIndex<Object> d;
        final AtomicThrowable e = new AtomicThrowable();
        final int f;
        volatile boolean g;
        boolean h;
        long i;

        MergeMaybeObserver(Subscriber<? super T> subscriber, int i2, SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex) {
            this.a = subscriber;
            this.f = i2;
            this.d = simpleQueueWithConsumerIndex;
        }

        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            this.h = true;
            return 2;
        }

        @Nullable
        public T poll() {
            T poll;
            do {
                poll = this.d.poll();
            } while (poll == NotificationLite.COMPLETE);
            return poll;
        }

        public boolean isEmpty() {
            return this.d.isEmpty();
        }

        public void clear() {
            this.d.clear();
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.c, j);
                d();
            }
        }

        public void cancel() {
            if (!this.g) {
                this.g = true;
                this.b.dispose();
                if (getAndIncrement() == 0) {
                    this.d.clear();
                }
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.b.add(disposable);
        }

        public void onSuccess(T t) {
            this.d.offer(t);
            d();
        }

        public void onError(Throwable th) {
            if (this.e.addThrowable(th)) {
                this.b.dispose();
                this.d.offer(NotificationLite.COMPLETE);
                d();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.d.offer(NotificationLite.COMPLETE);
            d();
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            Subscriber<? super T> subscriber = this.a;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.d;
            long j = this.i;
            int i2 = 1;
            do {
                long j2 = this.c.get();
                while (j != j2) {
                    if (this.g) {
                        simpleQueueWithConsumerIndex.clear();
                        return;
                    } else if (((Throwable) this.e.get()) != null) {
                        simpleQueueWithConsumerIndex.clear();
                        subscriber.onError(this.e.terminate());
                        return;
                    } else if (simpleQueueWithConsumerIndex.a() == this.f) {
                        subscriber.onComplete();
                        return;
                    } else {
                        Object poll = simpleQueueWithConsumerIndex.poll();
                        if (poll == null) {
                            break;
                        } else if (poll != NotificationLite.COMPLETE) {
                            subscriber.onNext(poll);
                            j++;
                        }
                    }
                }
                if (j == j2) {
                    if (((Throwable) this.e.get()) != null) {
                        simpleQueueWithConsumerIndex.clear();
                        subscriber.onError(this.e.terminate());
                        return;
                    }
                    while (simpleQueueWithConsumerIndex.peek() == NotificationLite.COMPLETE) {
                        simpleQueueWithConsumerIndex.c();
                    }
                    if (simpleQueueWithConsumerIndex.a() == this.f) {
                        subscriber.onComplete();
                        return;
                    }
                }
                this.i = j;
                i2 = addAndGet(-i2);
            } while (i2 != 0);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Subscriber<? super T> subscriber = this.a;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.d;
            int i2 = 1;
            while (!this.g) {
                Throwable th = (Throwable) this.e.get();
                if (th != null) {
                    simpleQueueWithConsumerIndex.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z = simpleQueueWithConsumerIndex.b() == this.f;
                if (!simpleQueueWithConsumerIndex.isEmpty()) {
                    subscriber.onNext(null);
                }
                if (z) {
                    subscriber.onComplete();
                    return;
                }
                i2 = addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            }
            simpleQueueWithConsumerIndex.clear();
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (getAndIncrement() == 0) {
                if (this.h) {
                    c();
                } else {
                    b();
                }
            }
        }
    }

    static final class MpscFillOnceSimpleQueue<T> extends AtomicReferenceArray<T> implements SimpleQueueWithConsumerIndex<T> {
        private static final long serialVersionUID = -7969063454040569579L;
        final AtomicInteger a = new AtomicInteger();
        int b;

        MpscFillOnceSimpleQueue(int i) {
            super(i);
        }

        public boolean offer(T t) {
            ObjectHelper.requireNonNull(t, "value is null");
            int andIncrement = this.a.getAndIncrement();
            if (andIncrement >= length()) {
                return false;
            }
            lazySet(andIncrement, t);
            return true;
        }

        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        @Nullable
        public T poll() {
            int i = this.b;
            if (i == length()) {
                return null;
            }
            AtomicInteger atomicInteger = this.a;
            do {
                T t = get(i);
                if (t != null) {
                    this.b = i + 1;
                    lazySet(i, null);
                    return t;
                }
            } while (atomicInteger.get() != i);
            return null;
        }

        public T peek() {
            int i = this.b;
            if (i == length()) {
                return null;
            }
            return get(i);
        }

        public void c() {
            int i = this.b;
            lazySet(i, null);
            this.b = i + 1;
        }

        public boolean isEmpty() {
            return this.b == b();
        }

        public void clear() {
            while (poll() != null) {
                if (isEmpty()) {
                    return;
                }
            }
        }

        public int a() {
            return this.b;
        }

        public int b() {
            return this.a.get();
        }
    }

    interface SimpleQueueWithConsumerIndex<T> extends SimpleQueue<T> {
        int a();

        int b();

        void c();

        T peek();

        @Nullable
        T poll();
    }

    public MaybeMergeArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.b = maybeSourceArr;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SimpleQueueWithConsumerIndex simpleQueueWithConsumerIndex;
        MaybeSource<? extends T>[] maybeSourceArr = this.b;
        int length = maybeSourceArr.length;
        if (length <= bufferSize()) {
            simpleQueueWithConsumerIndex = new MpscFillOnceSimpleQueue(length);
        } else {
            simpleQueueWithConsumerIndex = new ClqSimpleQueue();
        }
        MergeMaybeObserver mergeMaybeObserver = new MergeMaybeObserver(subscriber, length, simpleQueueWithConsumerIndex);
        subscriber.onSubscribe(mergeMaybeObserver);
        AtomicThrowable atomicThrowable = mergeMaybeObserver.e;
        int length2 = maybeSourceArr.length;
        int i = 0;
        while (i < length2) {
            MaybeSource<? extends T> maybeSource = maybeSourceArr[i];
            if (!mergeMaybeObserver.a() && atomicThrowable.get() == null) {
                maybeSource.subscribe(mergeMaybeObserver);
                i++;
            } else {
                return;
            }
        }
    }
}
