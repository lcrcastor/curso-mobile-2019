package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRange extends Flowable<Integer> {
    final int b;
    final int c;

    static abstract class BaseRangeSubscription extends BasicQueueSubscription<Integer> {
        private static final long serialVersionUID = -2252972430506210021L;
        final int a;
        int b;
        volatile boolean c;

        /* access modifiers changed from: 0000 */
        public abstract void a(long j);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        public final int requestFusion(int i) {
            return i & 1;
        }

        BaseRangeSubscription(int i, int i2) {
            this.b = i;
            this.a = i2;
        }

        @Nullable
        /* renamed from: a */
        public final Integer poll() {
            int i = this.b;
            if (i == this.a) {
                return null;
            }
            this.b = i + 1;
            return Integer.valueOf(i);
        }

        public final boolean isEmpty() {
            return this.b == this.a;
        }

        public final void clear() {
            this.b = this.a;
        }

        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    b();
                } else {
                    a(j);
                }
            }
        }

        public final void cancel() {
            this.c = true;
        }
    }

    static final class RangeConditionalSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super Integer> d;

        RangeConditionalSubscription(ConditionalSubscriber<? super Integer> conditionalSubscriber, int i, int i2) {
            super(i, i2);
            this.d = conditionalSubscriber;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i = this.a;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.d;
            int i2 = this.b;
            while (i2 != i) {
                if (!this.c) {
                    conditionalSubscriber.tryOnNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!this.c) {
                conditionalSubscriber.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            int i = this.a;
            int i2 = this.b;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.d;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i2 == i) {
                        if (i2 == i) {
                            if (!this.c) {
                                conditionalSubscriber.onComplete();
                            }
                            return;
                        }
                        j2 = get();
                        if (j3 == j2) {
                            this.b = i2;
                            j2 = addAndGet(-j3);
                        }
                    } else if (!this.c) {
                        if (conditionalSubscriber.tryOnNext(Integer.valueOf(i2))) {
                            j3++;
                        }
                        i2++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    static final class RangeSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Integer> d;

        RangeSubscription(Subscriber<? super Integer> subscriber, int i, int i2) {
            super(i, i2);
            this.d = subscriber;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            int i = this.a;
            Subscriber<? super Integer> subscriber = this.d;
            int i2 = this.b;
            while (i2 != i) {
                if (!this.c) {
                    subscriber.onNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!this.c) {
                subscriber.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            int i = this.a;
            int i2 = this.b;
            Subscriber<? super Integer> subscriber = this.d;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i2 == i) {
                        if (i2 == i) {
                            if (!this.c) {
                                subscriber.onComplete();
                            }
                            return;
                        }
                        j2 = get();
                        if (j3 == j2) {
                            this.b = i2;
                            j2 = addAndGet(-j3);
                        }
                    } else if (!this.c) {
                        subscriber.onNext(Integer.valueOf(i2));
                        i2++;
                        j3++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    public FlowableRange(int i, int i2) {
        this.b = i;
        this.c = i + i2;
    }

    public void subscribeActual(Subscriber<? super Integer> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber) subscriber, this.b, this.c));
        } else {
            subscriber.onSubscribe(new RangeSubscription(subscriber, this.b, this.c));
        }
    }
}
