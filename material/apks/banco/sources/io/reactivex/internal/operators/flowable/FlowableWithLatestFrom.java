package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFrom<T, U, R> extends AbstractFlowableWithUpstream<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> b;
    final Publisher<? extends U> c;

    final class FlowableWithLatestSubscriber implements FlowableSubscriber<U> {
        private final WithLatestFromSubscriber<T, U, R> b;

        public void onComplete() {
        }

        FlowableWithLatestSubscriber(WithLatestFromSubscriber<T, U, R> withLatestFromSubscriber) {
            this.b = withLatestFromSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (this.b.a(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U u) {
            this.b.lazySet(u);
        }

        public void onError(Throwable th) {
            this.b.a(th);
        }
    }

    static final class WithLatestFromSubscriber<T, U, R> extends AtomicReference<U> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -312246233408980075L;
        final Subscriber<? super R> a;
        final BiFunction<? super T, ? super U, ? extends R> b;
        final AtomicReference<Subscription> c = new AtomicReference<>();
        final AtomicLong d = new AtomicLong();
        final AtomicReference<Subscription> e = new AtomicReference<>();

        WithLatestFromSubscriber(Subscriber<? super R> subscriber, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.a = subscriber;
            this.b = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.c, this.d, subscription);
        }

        public void onNext(T t) {
            Object obj = get();
            if (obj != null) {
                try {
                    this.a.onNext(ObjectHelper.requireNonNull(this.b.apply(t, obj), "The combiner returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.a.onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.e);
            this.a.onError(th);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.e);
            this.a.onComplete();
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.c, this.d, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.c);
            SubscriptionHelper.cancel(this.e);
        }

        public boolean a(Subscription subscription) {
            return SubscriptionHelper.setOnce(this.e, subscription);
        }

        public void a(Throwable th) {
            SubscriptionHelper.cancel(this.c);
            this.a.onError(th);
        }
    }

    public FlowableWithLatestFrom(Flowable<T> flowable, BiFunction<? super T, ? super U, ? extends R> biFunction, Publisher<? extends U> publisher) {
        super(flowable);
        this.b = biFunction;
        this.c = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(serializedSubscriber, this.b);
        serializedSubscriber.onSubscribe(withLatestFromSubscriber);
        this.c.subscribe(new FlowableWithLatestSubscriber(withLatestFromSubscriber));
        this.source.subscribe((FlowableSubscriber<? super T>) withLatestFromSubscriber);
    }
}
