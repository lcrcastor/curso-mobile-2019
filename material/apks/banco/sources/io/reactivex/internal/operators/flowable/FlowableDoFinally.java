package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Experimental
public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action b;

    static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final ConditionalSubscriber<? super T> a;
        final Action b;
        Subscription c;
        QueueSubscription<T> d;
        boolean e;

        DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Action action) {
            this.a = conditionalSubscriber;
            this.b = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.d = (QueueSubscription) subscription;
                }
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public boolean tryOnNext(T t) {
            return this.a.tryOnNext(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            this.a.onComplete();
            a();
        }

        public void cancel() {
            this.c.cancel();
            a();
        }

        public void request(long j) {
            this.c.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.d;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.e = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.d.clear();
        }

        public boolean isEmpty() {
            return this.d.isEmpty();
        }

        @Nullable
        public T poll() {
            T poll = this.d.poll();
            if (poll == null && this.e) {
                a();
            }
            return poll;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Subscriber<? super T> a;
        final Action b;
        Subscription c;
        QueueSubscription<T> d;
        boolean e;

        DoFinallySubscriber(Subscriber<? super T> subscriber, Action action) {
            this.a = subscriber;
            this.b = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.d = (QueueSubscription) subscription;
                }
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            this.a.onComplete();
            a();
        }

        public void cancel() {
            this.c.cancel();
            a();
        }

        public void request(long j) {
            this.c.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.d;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.e = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.d.clear();
        }

        public boolean isEmpty() {
            return this.d.isEmpty();
        }

        @Nullable
        public T poll() {
            T poll = this.d.poll();
            if (poll == null && this.e) {
                a();
            }
            return poll;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public FlowableDoFinally(Flowable<T> flowable, Action action) {
        super(flowable);
        this.b = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoFinallyConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, this.b));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoFinallySubscriber<Object>(subscriber, this.b));
        }
    }
}
