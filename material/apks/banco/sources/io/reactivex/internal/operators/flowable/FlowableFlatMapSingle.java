package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapSingle<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends SingleSource<? extends R>> b;
    final boolean c;
    final int d;

    static final class FlatMapSingleSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 8600231336733376951L;
        final Subscriber<? super R> a;
        final boolean b;
        final int c;
        final AtomicLong d = new AtomicLong();
        final CompositeDisposable e = new CompositeDisposable();
        final AtomicInteger f = new AtomicInteger(1);
        final AtomicThrowable g = new AtomicThrowable();
        final Function<? super T, ? extends SingleSource<? extends R>> h;
        final AtomicReference<SpscLinkedArrayQueue<R>> i = new AtomicReference<>();
        Subscription j;
        volatile boolean k;

        final class InnerObserver extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(R r) {
                FlatMapSingleSubscriber.this.a(this, r);
            }

            public void onError(Throwable th) {
                FlatMapSingleSubscriber.this.a(this, th);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }

        FlatMapSingleSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i2) {
            this.a = subscriber;
            this.h = function;
            this.b = z;
            this.c = i2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                this.a.onSubscribe(this);
                if (this.c == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request((long) this.c);
                }
            }
        }

        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.h.apply(t), "The mapper returned a null SingleSource");
                this.f.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.e.add(innerObserver)) {
                    singleSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.j.cancel();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.f.decrementAndGet();
            if (this.g.addThrowable(th)) {
                if (!this.b) {
                    this.e.dispose();
                }
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.f.decrementAndGet();
            b();
        }

        public void cancel() {
            this.k = true;
            this.j.cancel();
            this.e.dispose();
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.d, j2);
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver, R r) {
            this.e.delete(innerObserver);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    if (this.f.decrementAndGet() != 0) {
                        z = false;
                    }
                    if (this.d.get() != 0) {
                        this.a.onNext(r);
                        SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.i.get();
                        if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                            BackpressureHelper.produced(this.d, 1);
                            if (this.c != Integer.MAX_VALUE) {
                                this.j.request(1);
                            }
                        } else {
                            Throwable terminate = this.g.terminate();
                            if (terminate != null) {
                                this.a.onError(terminate);
                            } else {
                                this.a.onComplete();
                            }
                            return;
                        }
                    } else {
                        SpscLinkedArrayQueue a2 = a();
                        synchronized (a2) {
                            a2.offer(r);
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                    d();
                }
            }
            SpscLinkedArrayQueue a3 = a();
            synchronized (a3) {
                a3.offer(r);
            }
            this.f.decrementAndGet();
            if (getAndIncrement() != 0) {
                return;
            }
            d();
        }

        /* access modifiers changed from: 0000 */
        public SpscLinkedArrayQueue<R> a() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = (SpscLinkedArrayQueue) this.i.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            } while (!this.i.compareAndSet(null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver, Throwable th) {
            this.e.delete(innerObserver);
            if (this.g.addThrowable(th)) {
                if (!this.b) {
                    this.j.cancel();
                    this.e.dispose();
                } else if (this.c != Integer.MAX_VALUE) {
                    this.j.request(1);
                }
                this.f.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                d();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.i.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            boolean z;
            Subscriber<? super R> subscriber = this.a;
            AtomicInteger atomicInteger = this.f;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.i;
            int i2 = 1;
            do {
                long j2 = this.d.get();
                long j3 = 0;
                while (true) {
                    z = false;
                    if (j3 == j2) {
                        break;
                    } else if (this.k) {
                        c();
                        return;
                    } else if (this.b || ((Throwable) this.g.get()) == null) {
                        boolean z2 = atomicInteger.get() == 0;
                        SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) atomicReference.get();
                        Object poll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                        boolean z3 = poll == null;
                        if (z2 && z3) {
                            Throwable terminate = this.g.terminate();
                            if (terminate != null) {
                                subscriber.onError(terminate);
                            } else {
                                subscriber.onComplete();
                            }
                            return;
                        } else if (z3) {
                            break;
                        } else {
                            subscriber.onNext(poll);
                            j3++;
                        }
                    } else {
                        Throwable terminate2 = this.g.terminate();
                        c();
                        subscriber.onError(terminate2);
                        return;
                    }
                }
                if (j3 == j2) {
                    if (this.k) {
                        c();
                        return;
                    } else if (this.b || ((Throwable) this.g.get()) == null) {
                        boolean z4 = atomicInteger.get() == 0;
                        SpscLinkedArrayQueue spscLinkedArrayQueue2 = (SpscLinkedArrayQueue) atomicReference.get();
                        if (spscLinkedArrayQueue2 == null || spscLinkedArrayQueue2.isEmpty()) {
                            z = true;
                        }
                        if (z4 && z) {
                            Throwable terminate3 = this.g.terminate();
                            if (terminate3 != null) {
                                subscriber.onError(terminate3);
                            } else {
                                subscriber.onComplete();
                            }
                            return;
                        }
                    } else {
                        Throwable terminate4 = this.g.terminate();
                        c();
                        subscriber.onError(terminate4);
                        return;
                    }
                }
                if (j3 != 0) {
                    BackpressureHelper.produced(this.d, j3);
                    if (this.c != Integer.MAX_VALUE) {
                        this.j.request(j3);
                    }
                }
                i2 = addAndGet(-i2);
            } while (i2 != 0);
        }
    }

    public FlowableFlatMapSingle(Flowable<T> flowable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new FlatMapSingleSubscriber<Object>(subscriber, this.b, this.c, this.d));
    }
}
