package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

@Experimental
public final class FlowableDoAfterNext<T> extends AbstractFlowableWithUpstream<T, T> {
    final Consumer<? super T> b;

    static final class DoAfterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Consumer<? super T> a;

        DoAfterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer) {
            super(conditionalSubscriber);
            this.a = consumer;
        }

        public void onNext(T t) {
            this.actual.onNext(t);
            if (this.sourceMode == 0) {
                try {
                    this.a.accept(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        public boolean tryOnNext(T t) {
            boolean tryOnNext = this.actual.tryOnNext(t);
            try {
                this.a.accept(t);
            } catch (Throwable th) {
                fail(th);
            }
            return tryOnNext;
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            T poll = this.qs.poll();
            if (poll != null) {
                this.a.accept(poll);
            }
            return poll;
        }
    }

    static final class DoAfterSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        final Consumer<? super T> a;

        DoAfterSubscriber(Subscriber<? super T> subscriber, Consumer<? super T> consumer) {
            super(subscriber);
            this.a = consumer;
        }

        public void onNext(T t) {
            if (!this.done) {
                this.actual.onNext(t);
                if (this.sourceMode == 0) {
                    try {
                        this.a.accept(t);
                    } catch (Throwable th) {
                        fail(th);
                    }
                }
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            T poll = this.qs.poll();
            if (poll != null) {
                this.a.accept(poll);
            }
            return poll;
        }
    }

    public FlowableDoAfterNext(Flowable<T> flowable, Consumer<? super T> consumer) {
        super(flowable);
        this.b = consumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoAfterConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, this.b));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoAfterSubscriber<Object>(subscriber, this.b));
        }
    }
}
