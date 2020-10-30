package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlattenIterable<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> b;
    final int c;

    static final class FlattenIterableSubscriber<T, R> extends BasicIntQueueSubscription<R> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -3096000382929934955L;
        final Subscriber<? super R> a;
        final Function<? super T, ? extends Iterable<? extends R>> b;
        final int c;
        final int d;
        final AtomicLong e = new AtomicLong();
        Subscription f;
        SimpleQueue<T> g;
        volatile boolean h;
        volatile boolean i;
        final AtomicReference<Throwable> j = new AtomicReference<>();
        Iterator<? extends R> k;
        int l;
        int m;

        FlattenIterableSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Iterable<? extends R>> function, int i2) {
            this.a = subscriber;
            this.b = function;
            this.c = i2;
            this.d = i2 - (i2 >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.m = requestFusion;
                        this.g = queueSubscription;
                        this.h = true;
                        this.a.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.m = requestFusion;
                        this.g = queueSubscription;
                        this.a.onSubscribe(this);
                        subscription.request((long) this.c);
                        return;
                    }
                }
                this.g = new SpscArrayQueue(this.c);
                this.a.onSubscribe(this);
                subscription.request((long) this.c);
            }
        }

        public void onNext(T t) {
            if (!this.h) {
                if (this.m != 0 || this.g.offer(t)) {
                    a();
                } else {
                    onError(new MissingBackpressureException("Queue is full?!"));
                }
            }
        }

        public void onError(Throwable th) {
            if (this.h || !ExceptionHelper.addThrowable(this.j, th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            a();
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                a();
            }
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.e, j2);
                a();
            }
        }

        public void cancel() {
            if (!this.i) {
                this.i = true;
                this.f.cancel();
                if (getAndIncrement() == 0) {
                    this.g.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0126, code lost:
            if (r7 == null) goto L_0x0131;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a() {
            /*
                r19 = this;
                r1 = r19
                int r2 = r19.getAndIncrement()
                if (r2 == 0) goto L_0x0009
                return
            L_0x0009:
                org.reactivestreams.Subscriber<? super R> r2 = r1.a
                io.reactivex.internal.fuseable.SimpleQueue<T> r3 = r1.g
                int r4 = r1.m
                r5 = 0
                r6 = 1
                if (r4 == r6) goto L_0x0015
                r4 = 1
                goto L_0x0016
            L_0x0015:
                r4 = 0
            L_0x0016:
                java.util.Iterator<? extends R> r7 = r1.k
                r8 = 0
                r9 = 1
            L_0x001a:
                if (r7 != 0) goto L_0x0080
                boolean r10 = r1.h
                java.lang.Object r11 = r3.poll()     // Catch:{ Throwable -> 0x0063 }
                if (r11 != 0) goto L_0x0026
                r12 = 1
                goto L_0x0027
            L_0x0026:
                r12 = 0
            L_0x0027:
                boolean r10 = r1.a(r10, r12, r2, r3)
                if (r10 == 0) goto L_0x002e
                return
            L_0x002e:
                if (r11 == 0) goto L_0x0080
                io.reactivex.functions.Function<? super T, ? extends java.lang.Iterable<? extends R>> r7 = r1.b     // Catch:{ Throwable -> 0x004a }
                java.lang.Object r7 = r7.apply(r11)     // Catch:{ Throwable -> 0x004a }
                java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ Throwable -> 0x004a }
                java.util.Iterator r7 = r7.iterator()     // Catch:{ Throwable -> 0x004a }
                boolean r10 = r7.hasNext()     // Catch:{ Throwable -> 0x004a }
                if (r10 != 0) goto L_0x0047
                r1.a(r4)
                r7 = r8
                goto L_0x001a
            L_0x0047:
                r1.k = r7
                goto L_0x0080
            L_0x004a:
                r0 = move-exception
                r3 = r0
                io.reactivex.exceptions.Exceptions.throwIfFatal(r3)
                org.reactivestreams.Subscription r4 = r1.f
                r4.cancel()
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r4 = r1.j
                io.reactivex.internal.util.ExceptionHelper.addThrowable(r4, r3)
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r3 = r1.j
                java.lang.Throwable r3 = io.reactivex.internal.util.ExceptionHelper.terminate(r3)
                r2.onError(r3)
                return
            L_0x0063:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                org.reactivestreams.Subscription r4 = r1.f
                r4.cancel()
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r4 = r1.j
                io.reactivex.internal.util.ExceptionHelper.addThrowable(r4, r0)
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r4 = r1.j
                java.lang.Throwable r4 = io.reactivex.internal.util.ExceptionHelper.terminate(r4)
                r1.k = r8
                r3.clear()
                r2.onError(r4)
                return
            L_0x0080:
                if (r7 == 0) goto L_0x0129
                java.util.concurrent.atomic.AtomicLong r10 = r1.e
                long r10 = r10.get()
                r14 = 0
            L_0x008a:
                int r16 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
                if (r16 == 0) goto L_0x00f9
                boolean r6 = r1.h
                boolean r6 = r1.a(r6, r5, r2, r3)
                if (r6 == 0) goto L_0x0097
                return
            L_0x0097:
                java.lang.Object r6 = r7.next()     // Catch:{ Throwable -> 0x00de }
                java.lang.String r12 = "The iterator returned a null value"
                java.lang.Object r6 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r12)     // Catch:{ Throwable -> 0x00de }
                r2.onNext(r6)
                boolean r6 = r1.h
                boolean r6 = r1.a(r6, r5, r2, r3)
                if (r6 == 0) goto L_0x00ad
                return
            L_0x00ad:
                r12 = 1
                long r17 = r14 + r12
                boolean r6 = r7.hasNext()     // Catch:{ Throwable -> 0x00c4 }
                if (r6 != 0) goto L_0x00c0
                r1.a(r4)
                r1.k = r8
                r7 = r8
                r14 = r17
                goto L_0x00f9
            L_0x00c0:
                r14 = r17
                r6 = 1
                goto L_0x008a
            L_0x00c4:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r1.k = r8
                org.reactivestreams.Subscription r3 = r1.f
                r3.cancel()
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r3 = r1.j
                io.reactivex.internal.util.ExceptionHelper.addThrowable(r3, r0)
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r3 = r1.j
                java.lang.Throwable r3 = io.reactivex.internal.util.ExceptionHelper.terminate(r3)
                r2.onError(r3)
                return
            L_0x00de:
                r0 = move-exception
                r3 = r0
                io.reactivex.exceptions.Exceptions.throwIfFatal(r3)
                r1.k = r8
                org.reactivestreams.Subscription r4 = r1.f
                r4.cancel()
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r4 = r1.j
                io.reactivex.internal.util.ExceptionHelper.addThrowable(r4, r3)
                java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r3 = r1.j
                java.lang.Throwable r3 = io.reactivex.internal.util.ExceptionHelper.terminate(r3)
                r2.onError(r3)
                return
            L_0x00f9:
                int r6 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
                if (r6 != 0) goto L_0x0111
                boolean r6 = r1.h
                boolean r12 = r3.isEmpty()
                if (r12 == 0) goto L_0x0109
                if (r7 != 0) goto L_0x0109
                r12 = 1
                goto L_0x010a
            L_0x0109:
                r12 = 0
            L_0x010a:
                boolean r6 = r1.a(r6, r12, r2, r3)
                if (r6 == 0) goto L_0x0111
                return
            L_0x0111:
                r12 = 0
                int r6 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                if (r6 == 0) goto L_0x0126
                r12 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r6 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r6 == 0) goto L_0x0126
                java.util.concurrent.atomic.AtomicLong r6 = r1.e
                long r10 = -r14
                r6.addAndGet(r10)
            L_0x0126:
                if (r7 != 0) goto L_0x0129
                goto L_0x0131
            L_0x0129:
                int r6 = -r9
                int r9 = r1.addAndGet(r6)
                if (r9 != 0) goto L_0x0131
                return
            L_0x0131:
                r6 = 1
                goto L_0x001a
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableFlattenIterable.FlattenIterableSubscriber.a():void");
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            if (z) {
                int i2 = this.l + 1;
                if (i2 == this.d) {
                    this.l = 0;
                    this.f.request((long) i2);
                    return;
                }
                this.l = i2;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SimpleQueue<?> simpleQueue) {
            if (this.i) {
                this.k = null;
                simpleQueue.clear();
                return true;
            }
            if (z) {
                if (((Throwable) this.j.get()) != null) {
                    Throwable terminate = ExceptionHelper.terminate(this.j);
                    this.k = null;
                    simpleQueue.clear();
                    subscriber.onError(terminate);
                    return true;
                } else if (z2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }

        public void clear() {
            this.k = null;
            this.g.clear();
        }

        public boolean isEmpty() {
            Iterator<? extends R> it = this.k;
            if (it == null) {
                return this.g.isEmpty();
            }
            return !it.hasNext();
        }

        @Nullable
        public R poll() {
            Iterator<? extends R> it = this.k;
            while (true) {
                if (it != null) {
                    break;
                }
                Object poll = this.g.poll();
                if (poll != null) {
                    it = ((Iterable) this.b.apply(poll)).iterator();
                    if (it.hasNext()) {
                        this.k = it;
                        break;
                    }
                    it = null;
                } else {
                    return null;
                }
            }
            R requireNonNull = ObjectHelper.requireNonNull(it.next(), "The iterator returned a null value");
            if (!it.hasNext()) {
                this.k = null;
            }
            return requireNonNull;
        }

        public int requestFusion(int i2) {
            return ((i2 & 1) == 0 || this.m != 1) ? 0 : 1;
        }
    }

    public FlowableFlattenIterable(Flowable<T> flowable, Function<? super T, ? extends Iterable<? extends R>> function, int i) {
        super(flowable);
        this.b = function;
        this.c = i;
    }

    public void subscribeActual(Subscriber<? super R> subscriber) {
        if (this.source instanceof Callable) {
            try {
                Object call = ((Callable) this.source).call();
                if (call == null) {
                    EmptySubscription.complete(subscriber);
                    return;
                }
                try {
                    FlowableFromIterable.subscribe(subscriber, ((Iterable) this.b.apply(call)).iterator());
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new FlattenIterableSubscriber<Object>(subscriber, this.b, this.c));
        }
    }
}
