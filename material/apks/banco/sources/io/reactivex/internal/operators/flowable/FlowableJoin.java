package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
    final Publisher<? extends TRight> b;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> e;

    static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements JoinSupport, Subscription {
        static final Integer o = Integer.valueOf(1);
        static final Integer p = Integer.valueOf(2);
        static final Integer q = Integer.valueOf(3);
        static final Integer r = Integer.valueOf(4);
        private static final long serialVersionUID = -6071216598687999801L;
        final Subscriber<? super R> a;
        final AtomicLong b = new AtomicLong();
        final SpscLinkedArrayQueue<Object> c = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final CompositeDisposable d = new CompositeDisposable();
        final Map<Integer, TLeft> e = new LinkedHashMap();
        final Map<Integer, TRight> f = new LinkedHashMap();
        final AtomicReference<Throwable> g = new AtomicReference<>();
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> h;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> i;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> j;
        final AtomicInteger k;
        int l;
        int m;
        volatile boolean n;

        JoinSubscription(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.a = subscriber;
            this.h = function;
            this.i = function2;
            this.j = biFunction;
            this.k = new AtomicInteger(2);
        }

        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.b, j2);
            }
        }

        public void cancel() {
            if (!this.n) {
                this.n = true;
                a();
                if (getAndIncrement() == 0) {
                    this.c.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.d.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a(Subscriber<?> subscriber) {
            Throwable terminate = ExceptionHelper.terminate(this.g);
            this.e.clear();
            this.f.clear();
            subscriber.onError(terminate);
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th, Subscriber<?> subscriber, SimpleQueue<?> simpleQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.g, th);
            simpleQueue.clear();
            a();
            a(subscriber);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.c;
                Subscriber<? super R> subscriber = this.a;
                boolean z = true;
                int i2 = 1;
                while (!this.n) {
                    if (((Throwable) this.g.get()) != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(subscriber);
                        return;
                    }
                    boolean z2 = this.k.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z3 = num == null;
                    if (z2 && z3) {
                        this.e.clear();
                        this.f.clear();
                        this.d.dispose();
                        subscriber.onComplete();
                        return;
                    } else if (z3) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == o) {
                            int i3 = this.l;
                            this.l = i3 + 1;
                            this.e.put(Integer.valueOf(i3), poll);
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.h.apply(poll), "The leftEnd returned a null Publisher");
                                LeftRightEndSubscriber leftRightEndSubscriber = new LeftRightEndSubscriber(this, z, i3);
                                this.d.add(leftRightEndSubscriber);
                                publisher.subscribe(leftRightEndSubscriber);
                                if (((Throwable) this.g.get()) != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                long j2 = this.b.get();
                                long j3 = 0;
                                for (Object apply : this.f.values()) {
                                    try {
                                        Object requireNonNull = ObjectHelper.requireNonNull(this.j.apply(poll, apply), "The resultSelector returned a null value");
                                        if (j3 != j2) {
                                            subscriber.onNext(requireNonNull);
                                            j3++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.g, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            a();
                                            a(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th) {
                                        a(th, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j3 != 0) {
                                    BackpressureHelper.produced(this.b, j3);
                                }
                            } catch (Throwable th2) {
                                a(th2, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == p) {
                            int i4 = this.m;
                            this.m = i4 + 1;
                            this.f.put(Integer.valueOf(i4), poll);
                            try {
                                Publisher publisher2 = (Publisher) ObjectHelper.requireNonNull(this.i.apply(poll), "The rightEnd returned a null Publisher");
                                LeftRightEndSubscriber leftRightEndSubscriber2 = new LeftRightEndSubscriber(this, false, i4);
                                this.d.add(leftRightEndSubscriber2);
                                publisher2.subscribe(leftRightEndSubscriber2);
                                if (((Throwable) this.g.get()) != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                long j4 = this.b.get();
                                long j5 = 0;
                                for (Object apply2 : this.e.values()) {
                                    try {
                                        Object requireNonNull2 = ObjectHelper.requireNonNull(this.j.apply(apply2, poll), "The resultSelector returned a null value");
                                        if (j5 != j4) {
                                            subscriber.onNext(requireNonNull2);
                                            j5++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.g, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            a();
                                            a(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th3) {
                                        a(th3, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j5 != 0) {
                                    BackpressureHelper.produced(this.b, j5);
                                }
                            } catch (Throwable th4) {
                                a(th4, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == q) {
                            LeftRightEndSubscriber leftRightEndSubscriber3 = (LeftRightEndSubscriber) poll;
                            this.e.remove(Integer.valueOf(leftRightEndSubscriber3.c));
                            this.d.remove(leftRightEndSubscriber3);
                        } else if (num == r) {
                            LeftRightEndSubscriber leftRightEndSubscriber4 = (LeftRightEndSubscriber) poll;
                            this.f.remove(Integer.valueOf(leftRightEndSubscriber4.c));
                            this.d.remove(leftRightEndSubscriber4);
                        }
                        z = true;
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        public void a(Throwable th) {
            if (ExceptionHelper.addThrowable(this.g, th)) {
                this.k.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void a(LeftRightSubscriber leftRightSubscriber) {
            this.d.delete(leftRightSubscriber);
            this.k.decrementAndGet();
            b();
        }

        public void a(boolean z, Object obj) {
            synchronized (this) {
                this.c.offer(z ? o : p, obj);
            }
            b();
        }

        public void a(boolean z, LeftRightEndSubscriber leftRightEndSubscriber) {
            synchronized (this) {
                this.c.offer(z ? q : r, leftRightEndSubscriber);
            }
            b();
        }

        public void b(Throwable th) {
            if (ExceptionHelper.addThrowable(this.g, th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public FlowableJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = function2;
        this.e = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        JoinSubscription joinSubscription = new JoinSubscription(subscriber, this.c, this.d, this.e);
        subscriber.onSubscribe(joinSubscription);
        LeftRightSubscriber leftRightSubscriber = new LeftRightSubscriber(joinSubscription, true);
        joinSubscription.d.add(leftRightSubscriber);
        LeftRightSubscriber leftRightSubscriber2 = new LeftRightSubscriber(joinSubscription, false);
        joinSubscription.d.add(leftRightSubscriber2);
        this.source.subscribe((FlowableSubscriber<? super T>) leftRightSubscriber);
        this.b.subscribe(leftRightSubscriber2);
    }
}
