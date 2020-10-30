package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBuffer<T> extends AbstractFlowableWithUpstream<T, T> {
    final int b;
    final boolean c;
    final boolean d;
    final Action e;

    static final class BackpressureBufferSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -2514538129242366402L;
        final Subscriber<? super T> a;
        final SimplePlainQueue<T> b;
        final boolean c;
        final Action d;
        Subscription e;
        volatile boolean f;
        volatile boolean g;
        Throwable h;
        final AtomicLong i = new AtomicLong();
        boolean j;

        BackpressureBufferSubscriber(Subscriber<? super T> subscriber, int i2, boolean z, boolean z2, Action action) {
            SimplePlainQueue<T> simplePlainQueue;
            this.a = subscriber;
            this.d = action;
            this.c = z2;
            if (z) {
                simplePlainQueue = new SpscLinkedArrayQueue<>(i2);
            } else {
                simplePlainQueue = new SpscArrayQueue<>(i2);
            }
            this.b = simplePlainQueue;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.b.offer(t)) {
                this.e.cancel();
                MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Buffer is full");
                try {
                    this.d.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    missingBackpressureException.initCause(th);
                }
                onError(missingBackpressureException);
                return;
            }
            if (this.j) {
                this.a.onNext(null);
            } else {
                a();
            }
        }

        public void onError(Throwable th) {
            this.h = th;
            this.g = true;
            if (this.j) {
                this.a.onError(th);
            } else {
                a();
            }
        }

        public void onComplete() {
            this.g = true;
            if (this.j) {
                this.a.onComplete();
            } else {
                a();
            }
        }

        public void request(long j2) {
            if (!this.j && SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.i, j2);
                a();
            }
        }

        public void cancel() {
            if (!this.f) {
                this.f = true;
                this.e.cancel();
                if (getAndIncrement() == 0) {
                    this.b.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue<T> simplePlainQueue = this.b;
                Subscriber<? super T> subscriber = this.a;
                int i2 = 1;
                while (!a(this.g, simplePlainQueue.isEmpty(), subscriber)) {
                    long j2 = this.i.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        boolean z = this.g;
                        Object poll = simplePlainQueue.poll();
                        boolean z2 = poll == null;
                        if (!a(z, z2, subscriber)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(poll);
                            j3++;
                        } else {
                            return;
                        }
                    }
                    if (j3 != j2 || !a(this.g, simplePlainQueue.isEmpty(), subscriber)) {
                        if (!(j3 == 0 || j2 == Long.MAX_VALUE)) {
                            this.i.addAndGet(-j3);
                        }
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<? super T> subscriber) {
            if (this.f) {
                this.b.clear();
                return true;
            }
            if (z) {
                if (!this.c) {
                    Throwable th = this.h;
                    if (th != null) {
                        this.b.clear();
                        subscriber.onError(th);
                        return true;
                    } else if (z2) {
                        subscriber.onComplete();
                        return true;
                    }
                } else if (z2) {
                    Throwable th2 = this.h;
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
            this.j = true;
            return 2;
        }

        @Nullable
        public T poll() {
            return this.b.poll();
        }

        public void clear() {
            this.b.clear();
        }

        public boolean isEmpty() {
            return this.b.isEmpty();
        }
    }

    public FlowableOnBackpressureBuffer(Flowable<T> flowable, int i, boolean z, boolean z2, Action action) {
        super(flowable);
        this.b = i;
        this.c = z;
        this.d = z2;
        this.e = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        BackpressureBufferSubscriber backpressureBufferSubscriber = new BackpressureBufferSubscriber(subscriber, this.b, this.c, this.d, this.e);
        flowable.subscribe((FlowableSubscriber<? super T>) backpressureBufferSubscriber);
    }
}
