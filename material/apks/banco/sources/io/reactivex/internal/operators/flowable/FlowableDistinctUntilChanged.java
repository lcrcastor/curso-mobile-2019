package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableDistinctUntilChanged<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super T, K> b;
    final BiPredicate<? super K, ? super K> c;

    static final class DistinctUntilChangedConditionalSubscriber<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        DistinctUntilChangedConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(conditionalSubscriber);
            this.a = function;
            this.b = biPredicate;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.actual.tryOnNext(t);
            }
            try {
                K apply = this.a.apply(t);
                if (this.d) {
                    boolean test = this.b.test(this.c, apply);
                    this.c = apply;
                    if (test) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = apply;
                }
                this.actual.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test(this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                    if (this.sourceMode != 1) {
                        this.s.request(1);
                    }
                }
            }
        }
    }

    static final class DistinctUntilChangedSubscriber<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final Function<? super T, K> a;
        final BiPredicate<? super K, ? super K> b;
        K c;
        boolean d;

        DistinctUntilChangedSubscriber(Subscriber<? super T> subscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            super(subscriber);
            this.a = function;
            this.b = biPredicate;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(t);
                return true;
            }
            try {
                K apply = this.a.apply(t);
                if (this.d) {
                    boolean test = this.b.test(this.c, apply);
                    this.c = apply;
                    if (test) {
                        return false;
                    }
                } else {
                    this.d = true;
                    this.c = apply;
                }
                this.actual.onNext(t);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Nullable
        public T poll() {
            while (true) {
                T poll = this.qs.poll();
                if (poll == null) {
                    return null;
                }
                K apply = this.a.apply(poll);
                if (!this.d) {
                    this.d = true;
                    this.c = apply;
                    return poll;
                } else if (!this.b.test(this.c, apply)) {
                    this.c = apply;
                    return poll;
                } else {
                    this.c = apply;
                    if (this.sourceMode != 1) {
                        this.s.request(1);
                    }
                }
            }
        }
    }

    public FlowableDistinctUntilChanged(Flowable<T> flowable, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(flowable);
        this.b = function;
        this.c = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new DistinctUntilChangedConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, this.b, this.c));
            return;
        }
        this.source.subscribe((FlowableSubscriber<? super T>) new DistinctUntilChangedSubscriber<Object>(subscriber, this.b, this.c));
    }
}
