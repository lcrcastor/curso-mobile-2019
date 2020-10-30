package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowTimed<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final long f;
    final int g;
    final boolean h;

    static final class WindowExactBoundedSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final long a;
        final TimeUnit b;
        final Scheduler c;
        final int d;
        final boolean e;
        final long f;
        final Worker g;
        long h;
        long i;
        Subscription j;
        UnicastProcessor<T> k;
        volatile boolean l;
        final SequentialDisposable m = new SequentialDisposable();

        static final class ConsumerIndexHolder implements Runnable {
            final long a;
            final WindowExactBoundedSubscriber<?> b;

            ConsumerIndexHolder(long j, WindowExactBoundedSubscriber<?> windowExactBoundedSubscriber) {
                this.a = j;
                this.b = windowExactBoundedSubscriber;
            }

            public void run() {
                WindowExactBoundedSubscriber<?> windowExactBoundedSubscriber = this.b;
                if (!windowExactBoundedSubscriber.cancelled) {
                    windowExactBoundedSubscriber.queue.offer(this);
                } else {
                    windowExactBoundedSubscriber.l = true;
                    windowExactBoundedSubscriber.a();
                }
                if (windowExactBoundedSubscriber.enter()) {
                    windowExactBoundedSubscriber.b();
                }
            }
        }

        WindowExactBoundedSubscriber(Subscriber<? super Flowable<T>> subscriber, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, long j3, boolean z) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j2;
            this.b = timeUnit;
            this.c = scheduler;
            this.d = i2;
            this.f = j3;
            this.e = z;
            if (z) {
                this.g = scheduler.createWorker();
            } else {
                this.g = null;
            }
        }

        public void onSubscribe(Subscription subscription) {
            Disposable disposable;
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                Subscriber subscriber = this.actual;
                subscriber.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastProcessor<T> create = UnicastProcessor.create(this.d);
                    this.k = create;
                    long requested = requested();
                    if (requested != 0) {
                        subscriber.onNext(create);
                        if (requested != Long.MAX_VALUE) {
                            produced(1);
                        }
                        ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.i, this);
                        if (this.e) {
                            disposable = this.g.schedulePeriodically(consumerIndexHolder, this.a, this.a, this.b);
                        } else {
                            disposable = this.c.schedulePeriodicallyDirect(consumerIndexHolder, this.a, this.a, this.b);
                        }
                        if (this.m.replace(disposable)) {
                            subscription.request(Long.MAX_VALUE);
                        }
                    } else {
                        this.cancelled = true;
                        subscription.cancel();
                        subscriber.onError(new MissingBackpressureException("Could not deliver initial window due to lack of requests."));
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.l) {
                if (fastEnter()) {
                    UnicastProcessor<T> unicastProcessor = this.k;
                    unicastProcessor.onNext(t);
                    long j2 = this.h + 1;
                    if (j2 >= this.f) {
                        this.i++;
                        this.h = 0;
                        unicastProcessor.onComplete();
                        long requested = requested();
                        if (requested != 0) {
                            UnicastProcessor<T> create = UnicastProcessor.create(this.d);
                            this.k = create;
                            this.actual.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1);
                            }
                            if (this.e) {
                                Disposable disposable = (Disposable) this.m.get();
                                disposable.dispose();
                                Disposable schedulePeriodically = this.g.schedulePeriodically(new ConsumerIndexHolder(this.i, this), this.a, this.a, this.b);
                                if (!this.m.compareAndSet(disposable, schedulePeriodically)) {
                                    schedulePeriodically.dispose();
                                }
                            }
                        } else {
                            this.k = null;
                            this.j.cancel();
                            this.actual.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                            a();
                            return;
                        }
                    } else {
                        this.h = j2;
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onError(th);
            a();
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onComplete();
            a();
        }

        public void request(long j2) {
            requested(j2);
        }

        public void cancel() {
            this.cancelled = true;
        }

        public void a() {
            DisposableHelper.dispose(this.m);
            Worker worker = this.g;
            if (worker != null) {
                worker.dispose();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.actual;
            UnicastProcessor<T> unicastProcessor = this.k;
            int i2 = 1;
            while (!this.l) {
                boolean z = this.done;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof ConsumerIndexHolder;
                if (z && (z2 || z3)) {
                    this.k = null;
                    simplePlainQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastProcessor.onError(th);
                    } else {
                        unicastProcessor.onComplete();
                    }
                    a();
                    return;
                } else if (z2) {
                    i2 = leave(-i2);
                    if (i2 == 0) {
                        return;
                    }
                } else {
                    int i3 = i2;
                    if (z3) {
                        ConsumerIndexHolder consumerIndexHolder = (ConsumerIndexHolder) poll;
                        if (this.e || this.i == consumerIndexHolder.a) {
                            unicastProcessor.onComplete();
                            this.h = 0;
                            unicastProcessor = UnicastProcessor.create(this.d);
                            this.k = unicastProcessor;
                            long requested = requested();
                            if (requested != 0) {
                                subscriber.onNext(unicastProcessor);
                                if (requested != Long.MAX_VALUE) {
                                    produced(1);
                                }
                            } else {
                                this.k = null;
                                this.queue.clear();
                                this.j.cancel();
                                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                                a();
                                return;
                            }
                        }
                    } else {
                        unicastProcessor.onNext(NotificationLite.getValue(poll));
                        long j2 = this.h + 1;
                        if (j2 >= this.f) {
                            this.i++;
                            this.h = 0;
                            unicastProcessor.onComplete();
                            long requested2 = requested();
                            if (requested2 != 0) {
                                unicastProcessor = UnicastProcessor.create(this.d);
                                this.k = unicastProcessor;
                                this.actual.onNext(unicastProcessor);
                                if (requested2 != Long.MAX_VALUE) {
                                    produced(1);
                                }
                                if (this.e) {
                                    Disposable disposable = (Disposable) this.m.get();
                                    disposable.dispose();
                                    Disposable schedulePeriodically = this.g.schedulePeriodically(new ConsumerIndexHolder(this.i, this), this.a, this.a, this.b);
                                    if (!this.m.compareAndSet(disposable, schedulePeriodically)) {
                                        schedulePeriodically.dispose();
                                    }
                                }
                            } else {
                                this.k = null;
                                this.j.cancel();
                                this.actual.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                                a();
                                return;
                            }
                        } else {
                            this.h = j2;
                        }
                    }
                    i2 = i3;
                }
            }
            this.j.cancel();
            simplePlainQueue.clear();
            a();
        }
    }

    static final class WindowExactUnboundedSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements FlowableSubscriber<T>, Runnable, Subscription {
        static final Object h = new Object();
        final long a;
        final TimeUnit b;
        final Scheduler c;
        final int d;
        Subscription e;
        UnicastProcessor<T> f;
        final SequentialDisposable g = new SequentialDisposable();
        volatile boolean i;

        WindowExactUnboundedSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i2) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j;
            this.b = timeUnit;
            this.c = scheduler;
            this.d = i2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.f = UnicastProcessor.create(this.d);
                Subscriber subscriber = this.actual;
                subscriber.onSubscribe(this);
                long requested = requested();
                if (requested != 0) {
                    subscriber.onNext(this.f);
                    if (requested != Long.MAX_VALUE) {
                        produced(1);
                    }
                    if (!this.cancelled) {
                        if (this.g.replace(this.c.schedulePeriodicallyDirect(this, this.a, this.a, this.b))) {
                            subscription.request(Long.MAX_VALUE);
                        }
                    }
                } else {
                    this.cancelled = true;
                    subscription.cancel();
                    subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                }
            }
        }

        public void onNext(T t) {
            if (!this.i) {
                if (fastEnter()) {
                    this.f.onNext(t);
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onError(th);
            a();
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onComplete();
            a();
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            this.cancelled = true;
        }

        public void a() {
            DisposableHelper.dispose(this.g);
        }

        public void run() {
            if (this.cancelled) {
                this.i = true;
                a();
            }
            this.queue.offer(h);
            if (enter()) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.actual;
            UnicastProcessor<T> unicastProcessor = this.f;
            int i2 = 1;
            while (true) {
                boolean z = this.i;
                boolean z2 = this.done;
                Object poll = simplePlainQueue.poll();
                if (!z2 || !(poll == null || poll == h)) {
                    if (poll == null) {
                        i2 = leave(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else if (poll == h) {
                        unicastProcessor.onComplete();
                        if (!z) {
                            unicastProcessor = UnicastProcessor.create(this.d);
                            this.f = unicastProcessor;
                            long requested = requested();
                            if (requested != 0) {
                                subscriber.onNext(unicastProcessor);
                                if (requested != Long.MAX_VALUE) {
                                    produced(1);
                                }
                            } else {
                                this.f = null;
                                this.queue.clear();
                                this.e.cancel();
                                a();
                                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                                return;
                            }
                        } else {
                            this.e.cancel();
                        }
                    } else {
                        unicastProcessor.onNext(NotificationLite.getValue(poll));
                    }
                }
            }
            this.f = null;
            simplePlainQueue.clear();
            a();
            Throwable th = this.error;
            if (th != null) {
                unicastProcessor.onError(th);
            } else {
                unicastProcessor.onComplete();
            }
        }
    }

    static final class WindowSkipSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Runnable, Subscription {
        final long a;
        final long b;
        final TimeUnit c;
        final Worker d;
        final int e;
        final List<UnicastProcessor<T>> f = new LinkedList();
        Subscription g;
        volatile boolean h;

        final class Completion implements Runnable {
            private final UnicastProcessor<T> b;

            Completion(UnicastProcessor<T> unicastProcessor) {
                this.b = unicastProcessor;
            }

            public void run() {
                WindowSkipSubscriber.this.a(this.b);
            }
        }

        static final class SubjectWork<T> {
            final UnicastProcessor<T> a;
            final boolean b;

            SubjectWork(UnicastProcessor<T> unicastProcessor, boolean z) {
                this.a = unicastProcessor;
                this.b = z;
            }
        }

        WindowSkipSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, long j2, TimeUnit timeUnit, Worker worker, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j;
            this.b = j2;
            this.c = timeUnit;
            this.d = worker;
            this.e = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.actual.onSubscribe(this);
                if (!this.cancelled) {
                    long requested = requested();
                    if (requested != 0) {
                        UnicastProcessor create = UnicastProcessor.create(this.e);
                        this.f.add(create);
                        this.actual.onNext(create);
                        if (requested != Long.MAX_VALUE) {
                            produced(1);
                        }
                        this.d.schedule(new Completion(create), this.a, this.c);
                        this.d.schedulePeriodically(this, this.b, this.b, this.c);
                        subscription.request(Long.MAX_VALUE);
                    } else {
                        subscription.cancel();
                        this.actual.onError(new MissingBackpressureException("Could not emit the first window due to lack of requests"));
                    }
                }
            }
        }

        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastProcessor onNext : this.f) {
                    onNext.onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            b();
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onError(th);
            a();
        }

        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.actual.onComplete();
            a();
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            this.cancelled = true;
        }

        public void a() {
            this.d.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void a(UnicastProcessor<T> unicastProcessor) {
            this.queue.offer(new SubjectWork(unicastProcessor, false));
            if (enter()) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.actual;
            List<UnicastProcessor<T>> list = this.f;
            int i = 1;
            while (!this.h) {
                boolean z = this.done;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof SubjectWork;
                if (z && (z2 || z3)) {
                    simplePlainQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastProcessor onError : list) {
                            onError.onError(th);
                        }
                    } else {
                        for (UnicastProcessor onComplete : list) {
                            onComplete.onComplete();
                        }
                    }
                    list.clear();
                    a();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (z3) {
                    SubjectWork subjectWork = (SubjectWork) poll;
                    if (!subjectWork.b) {
                        list.remove(subjectWork.a);
                        subjectWork.a.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.h = true;
                        }
                    } else if (!this.cancelled) {
                        long requested = requested();
                        if (requested != 0) {
                            UnicastProcessor create = UnicastProcessor.create(this.e);
                            list.add(create);
                            subscriber.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1);
                            }
                            this.d.schedule(new Completion(create), this.a, this.c);
                        } else {
                            subscriber.onError(new MissingBackpressureException("Can't emit window due to lack of requests"));
                        }
                    }
                } else {
                    for (UnicastProcessor onNext : list) {
                        onNext.onNext(poll);
                    }
                }
            }
            this.g.cancel();
            a();
            simplePlainQueue.clear();
            list.clear();
        }

        public void run() {
            SubjectWork subjectWork = new SubjectWork(UnicastProcessor.create(this.e), true);
            if (!this.cancelled) {
                this.queue.offer(subjectWork);
            }
            if (enter()) {
                b();
            }
        }
    }

    public FlowableWindowTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = j3;
        this.g = i;
        this.h = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.b != this.c) {
            Flowable flowable = this.source;
            WindowSkipSubscriber windowSkipSubscriber = new WindowSkipSubscriber(serializedSubscriber, this.b, this.c, this.d, this.e.createWorker(), this.g);
            flowable.subscribe((FlowableSubscriber<? super T>) windowSkipSubscriber);
        } else if (this.f == Long.MAX_VALUE) {
            Flowable flowable2 = this.source;
            WindowExactUnboundedSubscriber windowExactUnboundedSubscriber = new WindowExactUnboundedSubscriber(serializedSubscriber, this.b, this.d, this.e, this.g);
            flowable2.subscribe((FlowableSubscriber<? super T>) windowExactUnboundedSubscriber);
        } else {
            Flowable flowable3 = this.source;
            WindowExactBoundedSubscriber windowExactBoundedSubscriber = new WindowExactBoundedSubscriber(serializedSubscriber, this.b, this.d, this.e, this.g, this.f, this.h);
            flowable3.subscribe((FlowableSubscriber<? super T>) windowExactBoundedSubscriber);
        }
    }
}
