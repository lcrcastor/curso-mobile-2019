package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableDistinct<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super T, K> b;
    final Callable<? extends Collection<? super K>> c;

    static final class DistinctSubscriber<T, K> extends BasicFuseableSubscriber<T, T> {
        final Collection<? super K> a;
        final Function<? super T, K> b;

        DistinctSubscriber(Subscriber<? super T> subscriber, Function<? super T, K> function, Collection<? super K> collection) {
            super(subscriber);
            this.b = function;
            this.a = collection;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 0) {
                    try {
                        if (this.a.add(ObjectHelper.requireNonNull(this.b.apply(t), "The keySelector returned a null key"))) {
                            this.actual.onNext(t);
                        } else {
                            this.s.request(1);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    this.actual.onNext(null);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.a.clear();
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.a.clear();
                this.actual.onComplete();
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            T poll;
            while (true) {
                poll = this.qs.poll();
                if (poll == null || this.a.add(ObjectHelper.requireNonNull(this.b.apply(poll), "The keySelector returned a null key"))) {
                    return poll;
                }
                if (this.sourceMode == 2) {
                    this.s.request(1);
                }
            }
            return poll;
        }

        public void clear() {
            this.a.clear();
            super.clear();
        }
    }

    public FlowableDistinct(Flowable<T> flowable, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(flowable);
        this.b = function;
        this.c = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber<? super T>) new DistinctSubscriber<Object>(subscriber, this.b, (Collection) ObjectHelper.requireNonNull(this.c.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
