package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBuffer<T, C extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, C> {
    final int b;
    final int c;
    final Callable<C> d;

    static final class PublisherBufferExactSubscriber<T, C extends Collection<? super T>> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super C> a;
        final Callable<C> b;
        final int c;
        C d;
        Subscription e;
        boolean f;
        int g;

        PublisherBufferExactSubscriber(Subscriber<? super C> subscriber, int i, Callable<C> callable) {
            this.a = subscriber;
            this.c = i;
            this.b = callable;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.e.request(BackpressureHelper.multiplyCap(j, (long) this.c));
            }
        }

        public void cancel() {
            this.e.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.f) {
                C c2 = this.d;
                if (c2 == null) {
                    try {
                        c2 = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                        this.d = c2;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                c2.add(t);
                int i = this.g + 1;
                if (i == this.c) {
                    this.g = 0;
                    this.d = null;
                    this.a.onNext(c2);
                } else {
                    this.g = i;
                }
            }
        }

        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                C c2 = this.d;
                if (c2 != null && !c2.isEmpty()) {
                    this.a.onNext(c2);
                }
                this.a.onComplete();
            }
        }
    }

    static final class PublisherBufferOverlappingSubscriber<T, C extends Collection<? super T>> extends AtomicLong implements FlowableSubscriber<T>, BooleanSupplier, Subscription {
        private static final long serialVersionUID = -7370244972039324525L;
        final Subscriber<? super C> a;
        final Callable<C> b;
        final int c;
        final int d;
        final ArrayDeque<C> e = new ArrayDeque<>();
        final AtomicBoolean f = new AtomicBoolean();
        Subscription g;
        boolean h;
        int i;
        volatile boolean j;
        long k;

        PublisherBufferOverlappingSubscriber(Subscriber<? super C> subscriber, int i2, int i3, Callable<C> callable) {
            this.a = subscriber;
            this.c = i2;
            this.d = i3;
            this.b = callable;
        }

        public boolean getAsBoolean() {
            return this.j;
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                if (!QueueDrainHelper.postCompleteRequest(j2, this.a, this.e, this, this)) {
                    if (this.f.get() || !this.f.compareAndSet(false, true)) {
                        this.g.request(BackpressureHelper.multiplyCap((long) this.d, j2));
                    } else {
                        this.g.request(BackpressureHelper.addCap((long) this.c, BackpressureHelper.multiplyCap((long) this.d, j2 - 1)));
                    }
                }
            }
        }

        public void cancel() {
            this.j = true;
            this.g.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                ArrayDeque<C> arrayDeque = this.e;
                int i2 = this.i;
                int i3 = i2 + 1;
                if (i2 == 0) {
                    try {
                        arrayDeque.offer((Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer"));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                Collection collection = (Collection) arrayDeque.peek();
                if (collection != null && collection.size() + 1 == this.c) {
                    arrayDeque.poll();
                    collection.add(t);
                    this.k++;
                    this.a.onNext(collection);
                }
                Iterator it = arrayDeque.iterator();
                while (it.hasNext()) {
                    ((Collection) it.next()).add(t);
                }
                if (i3 == this.d) {
                    i3 = 0;
                }
                this.i = i3;
            }
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            this.e.clear();
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                long j2 = this.k;
                if (j2 != 0) {
                    BackpressureHelper.produced(this, j2);
                }
                QueueDrainHelper.postComplete(this.a, this.e, this, this);
            }
        }
    }

    static final class PublisherBufferSkipSubscriber<T, C extends Collection<? super T>> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5616169793639412593L;
        final Subscriber<? super C> a;
        final Callable<C> b;
        final int c;
        final int d;
        C e;
        Subscription f;
        boolean g;
        int h;

        PublisherBufferSkipSubscriber(Subscriber<? super C> subscriber, int i, int i2, Callable<C> callable) {
            this.a = subscriber;
            this.c = i;
            this.d = i2;
            this.b = callable;
        }

        public void request(long j) {
            if (!SubscriptionHelper.validate(j)) {
                return;
            }
            if (get() != 0 || !compareAndSet(0, 1)) {
                this.f.request(BackpressureHelper.multiplyCap((long) this.d, j));
                return;
            }
            this.f.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(j, (long) this.c), BackpressureHelper.multiplyCap((long) (this.d - this.c), j - 1)));
        }

        public void cancel() {
            this.f.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                C c2 = this.e;
                int i = this.h;
                int i2 = i + 1;
                if (i == 0) {
                    try {
                        c2 = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                        this.e = c2;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancel();
                        onError(th);
                        return;
                    }
                }
                if (c2 != null) {
                    c2.add(t);
                    if (c2.size() == this.c) {
                        this.e = null;
                        this.a.onNext(c2);
                    }
                }
                if (i2 == this.d) {
                    i2 = 0;
                }
                this.h = i2;
            }
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.g = true;
            this.e = null;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                C c2 = this.e;
                this.e = null;
                if (c2 != null) {
                    this.a.onNext(c2);
                }
                this.a.onComplete();
            }
        }
    }

    public FlowableBuffer(Flowable<T> flowable, int i, int i2, Callable<C> callable) {
        super(flowable);
        this.b = i;
        this.c = i2;
        this.d = callable;
    }

    public void subscribeActual(Subscriber<? super C> subscriber) {
        if (this.b == this.c) {
            this.source.subscribe((FlowableSubscriber<? super T>) new PublisherBufferExactSubscriber<Object>(subscriber, this.b, this.d));
        } else if (this.c > this.b) {
            this.source.subscribe((FlowableSubscriber<? super T>) new PublisherBufferSkipSubscriber<Object>(subscriber, this.b, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new PublisherBufferOverlappingSubscriber<Object>(subscriber, this.b, this.c, this.d));
        }
    }
}
