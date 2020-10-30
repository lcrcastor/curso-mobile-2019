package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
import io.reactivex.processors.UnicastProcessor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
    final Publisher<? extends TRight> b;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> c;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> d;
    final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> e;

    static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements JoinSupport, Subscription {
        static final Integer o = Integer.valueOf(1);
        static final Integer p = Integer.valueOf(2);
        static final Integer q = Integer.valueOf(3);
        static final Integer r = Integer.valueOf(4);
        private static final long serialVersionUID = -6071216598687999801L;
        final Subscriber<? super R> a;
        final AtomicLong b = new AtomicLong();
        final SpscLinkedArrayQueue<Object> c = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final CompositeDisposable d = new CompositeDisposable();
        final Map<Integer, UnicastProcessor<TRight>> e = new LinkedHashMap();
        final Map<Integer, TRight> f = new LinkedHashMap();
        final AtomicReference<Throwable> g = new AtomicReference<>();
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> h;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> i;
        final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> j;
        final AtomicInteger k;
        int l;
        int m;
        volatile boolean n;

        GroupJoinSubscription(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
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
            for (UnicastProcessor onError : this.e.values()) {
                onError.onError(terminate);
            }
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
                int i2 = 1;
                while (!this.n) {
                    if (((Throwable) this.g.get()) != null) {
                        spscLinkedArrayQueue.clear();
                        a();
                        a(subscriber);
                        return;
                    }
                    boolean z = this.k.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z2 = num == null;
                    if (z && z2) {
                        for (UnicastProcessor onComplete : this.e.values()) {
                            onComplete.onComplete();
                        }
                        this.e.clear();
                        this.f.clear();
                        this.d.dispose();
                        subscriber.onComplete();
                        return;
                    } else if (z2) {
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == o) {
                            UnicastProcessor create = UnicastProcessor.create();
                            int i3 = this.l;
                            this.l = i3 + 1;
                            this.e.put(Integer.valueOf(i3), create);
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.h.apply(poll), "The leftEnd returned a null Publisher");
                                LeftRightEndSubscriber leftRightEndSubscriber = new LeftRightEndSubscriber(this, true, i3);
                                this.d.add(leftRightEndSubscriber);
                                publisher.subscribe(leftRightEndSubscriber);
                                if (((Throwable) this.g.get()) != null) {
                                    spscLinkedArrayQueue.clear();
                                    a();
                                    a(subscriber);
                                    return;
                                }
                                try {
                                    Object requireNonNull = ObjectHelper.requireNonNull(this.j.apply(poll, create), "The resultSelector returned a null value");
                                    if (this.b.get() != 0) {
                                        subscriber.onNext(requireNonNull);
                                        BackpressureHelper.produced(this.b, 1);
                                        for (Object onNext : this.f.values()) {
                                            create.onNext(onNext);
                                        }
                                    } else {
                                        a(new MissingBackpressureException("Could not emit value due to lack of requests"), subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    a(th, subscriber, spscLinkedArrayQueue);
                                    return;
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
                                for (UnicastProcessor onNext2 : this.e.values()) {
                                    onNext2.onNext(poll);
                                }
                            } catch (Throwable th3) {
                                a(th3, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == q) {
                            LeftRightEndSubscriber leftRightEndSubscriber3 = (LeftRightEndSubscriber) poll;
                            UnicastProcessor unicastProcessor = (UnicastProcessor) this.e.remove(Integer.valueOf(leftRightEndSubscriber3.c));
                            this.d.remove(leftRightEndSubscriber3);
                            if (unicastProcessor != null) {
                                unicastProcessor.onComplete();
                            }
                        } else if (num == r) {
                            LeftRightEndSubscriber leftRightEndSubscriber4 = (LeftRightEndSubscriber) poll;
                            this.f.remove(Integer.valueOf(leftRightEndSubscriber4.c));
                            this.d.remove(leftRightEndSubscriber4);
                        }
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

    interface JoinSupport {
        void a(LeftRightSubscriber leftRightSubscriber);

        void a(Throwable th);

        void a(boolean z, LeftRightEndSubscriber leftRightEndSubscriber);

        void a(boolean z, Object obj);

        void b(Throwable th);
    }

    static final class LeftRightEndSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final JoinSupport a;
        final boolean b;
        final int c;

        LeftRightEndSubscriber(JoinSupport joinSupport, boolean z, int i) {
            this.a = joinSupport;
            this.b = z;
            this.c = i;
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) get());
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            if (SubscriptionHelper.cancel(this)) {
                this.a.a(this.b, this);
            }
        }

        public void onError(Throwable th) {
            this.a.b(th);
        }

        public void onComplete() {
            this.a.a(this.b, this);
        }
    }

    static final class LeftRightSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 1883890389173668373L;
        final JoinSupport a;
        final boolean b;

        LeftRightSubscriber(JoinSupport joinSupport, boolean z) {
            this.a = joinSupport;
            this.b = z;
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) get());
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            this.a.a(this.b, obj);
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            this.a.a(this);
        }
    }

    public FlowableGroupJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> biFunction) {
        super(flowable);
        this.b = publisher;
        this.c = function;
        this.d = function2;
        this.e = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        GroupJoinSubscription groupJoinSubscription = new GroupJoinSubscription(subscriber, this.c, this.d, this.e);
        subscriber.onSubscribe(groupJoinSubscription);
        LeftRightSubscriber leftRightSubscriber = new LeftRightSubscriber(groupJoinSubscription, true);
        groupJoinSubscription.d.add(leftRightSubscriber);
        LeftRightSubscriber leftRightSubscriber2 = new LeftRightSubscriber(groupJoinSubscription, false);
        groupJoinSubscription.d.add(leftRightSubscriber2);
        this.source.subscribe((FlowableSubscriber<? super T>) leftRightSubscriber);
        this.b.subscribe(leftRightSubscriber2);
    }
}
