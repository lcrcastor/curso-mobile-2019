package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFromMany<T, R> extends AbstractFlowableWithUpstream<T, R> {
    @Nullable
    final Publisher<?>[] b;
    @Nullable
    final Iterable<? extends Publisher<?>> c;
    final Function<? super Object[], R> d;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return FlowableWithLatestFromMany.this.d.apply(new Object[]{t});
        }
    }

    static final class WithLatestFromSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 1577321883966341961L;
        final Subscriber<? super R> a;
        final Function<? super Object[], R> b;
        final WithLatestInnerSubscriber[] c;
        final AtomicReferenceArray<Object> d;
        final AtomicReference<Subscription> e;
        final AtomicLong f;
        final AtomicThrowable g;
        volatile boolean h;

        WithLatestFromSubscriber(Subscriber<? super R> subscriber, Function<? super Object[], R> function, int i) {
            this.a = subscriber;
            this.b = function;
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = new WithLatestInnerSubscriber[i];
            for (int i2 = 0; i2 < i; i2++) {
                withLatestInnerSubscriberArr[i2] = new WithLatestInnerSubscriber(this, i2);
            }
            this.c = withLatestInnerSubscriberArr;
            this.d = new AtomicReferenceArray<>(i);
            this.e = new AtomicReference<>();
            this.f = new AtomicLong();
            this.g = new AtomicThrowable();
        }

        /* access modifiers changed from: 0000 */
        public void a(Publisher<?>[] publisherArr, int i) {
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = this.c;
            AtomicReference<Subscription> atomicReference = this.e;
            for (int i2 = 0; i2 < i && !SubscriptionHelper.isCancelled((Subscription) atomicReference.get()) && !this.h; i2++) {
                publisherArr[i2].subscribe(withLatestInnerSubscriberArr[i2]);
            }
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.e, this.f, subscription);
        }

        public void onNext(T t) {
            if (!this.h) {
                AtomicReferenceArray<Object> atomicReferenceArray = this.d;
                int length = atomicReferenceArray.length();
                Object[] objArr = new Object[(length + 1)];
                int i = 0;
                objArr[0] = t;
                while (i < length) {
                    Object obj = atomicReferenceArray.get(i);
                    if (obj == null) {
                        ((Subscription) this.e.get()).request(1);
                        return;
                    } else {
                        i++;
                        objArr[i] = obj;
                    }
                }
                try {
                    HalfSerializer.onNext(this.a, ObjectHelper.requireNonNull(this.b.apply(objArr), "combiner returned a null value"), (AtomicInteger) this, this.g);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.h) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.h = true;
            a(-1);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.g);
        }

        public void onComplete() {
            if (!this.h) {
                this.h = true;
                a(-1);
                HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.g);
            }
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.e, this.f, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.e);
            for (WithLatestInnerSubscriber dispose : this.c) {
                dispose.dispose();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Object obj) {
            this.d.set(i, obj);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Throwable th) {
            this.h = true;
            SubscriptionHelper.cancel(this.e);
            a(i);
            HalfSerializer.onError(this.a, th, (AtomicInteger) this, this.g);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, boolean z) {
            if (!z) {
                this.h = true;
                a(i);
                HalfSerializer.onComplete(this.a, (AtomicInteger) this, this.g);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = this.c;
            for (int i2 = 0; i2 < withLatestInnerSubscriberArr.length; i2++) {
                if (i2 != i) {
                    withLatestInnerSubscriberArr[i2].dispose();
                }
            }
        }
    }

    static final class WithLatestInnerSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 3256684027868224024L;
        final WithLatestFromSubscriber<?, ?> a;
        final int b;
        boolean c;

        WithLatestInnerSubscriber(WithLatestFromSubscriber<?, ?> withLatestFromSubscriber, int i) {
            this.a = withLatestFromSubscriber;
            this.b = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            if (!this.c) {
                this.c = true;
            }
            this.a.a(this.b, obj);
        }

        public void onError(Throwable th) {
            this.a.a(this.b, th);
        }

        public void onComplete() {
            this.a.a(this.b, this.c);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) get());
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Publisher<?>[] publisherArr, Function<? super Object[], R> function) {
        super(flowable);
        this.b = publisherArr;
        this.c = null;
        this.d = function;
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> flowable, @NonNull Iterable<? extends Publisher<?>> iterable, @NonNull Function<? super Object[], R> function) {
        super(flowable);
        this.b = null;
        this.c = iterable;
        this.d = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int i;
        Publisher<?>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                i = 0;
                for (Publisher<?> publisher : this.c) {
                    if (i == publisherArr.length) {
                        publisherArr = (Publisher[]) Arrays.copyOf(publisherArr, (i >> 1) + i);
                    }
                    int i2 = i + 1;
                    publisherArr[i] = publisher;
                    i = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            new FlowableMap(this.source, new SingletonArrayFunc()).subscribeActual(subscriber);
            return;
        }
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(subscriber, this.d, i);
        subscriber.onSubscribe(withLatestFromSubscriber);
        withLatestFromSubscriber.a(publisherArr, i);
        this.source.subscribe((FlowableSubscriber<? super T>) withLatestFromSubscriber);
    }
}
