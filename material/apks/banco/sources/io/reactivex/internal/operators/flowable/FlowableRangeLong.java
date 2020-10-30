package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRangeLong extends Flowable<Long> {
    final long b;
    final long c;

    static abstract class BaseRangeSubscription extends BasicQueueSubscription<Long> {
        private static final long serialVersionUID = -2252972430506210021L;
        final long a;
        long b;
        volatile boolean c;

        /* access modifiers changed from: 0000 */
        public abstract void a(long j);

        /* access modifiers changed from: 0000 */
        public abstract void b();

        public final int requestFusion(int i) {
            return i & 1;
        }

        BaseRangeSubscription(long j, long j2) {
            this.b = j;
            this.a = j2;
        }

        @Nullable
        /* renamed from: a */
        public final Long poll() {
            long j = this.b;
            if (j == this.a) {
                return null;
            }
            this.b = j + 1;
            return Long.valueOf(j);
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
        final ConditionalSubscriber<? super Long> d;

        RangeConditionalSubscription(ConditionalSubscriber<? super Long> conditionalSubscriber, long j, long j2) {
            super(j, j2);
            this.d = conditionalSubscriber;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            long j = this.a;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.d;
            long j2 = this.b;
            while (j2 != j) {
                if (!this.c) {
                    conditionalSubscriber.tryOnNext(Long.valueOf(j2));
                    j2++;
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
            long j2 = this.a;
            long j3 = this.b;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.d;
            long j4 = j;
            long j5 = j3;
            do {
                long j6 = 0;
                while (true) {
                    if (j6 == j4 || j5 == j2) {
                        if (j5 == j2) {
                            if (!this.c) {
                                conditionalSubscriber.onComplete();
                            }
                            return;
                        }
                        j4 = get();
                        if (j6 == j4) {
                            this.b = j5;
                            j4 = addAndGet(-j6);
                        }
                    } else if (!this.c) {
                        if (conditionalSubscriber.tryOnNext(Long.valueOf(j5))) {
                            j6++;
                        }
                        j5++;
                    } else {
                        return;
                    }
                }
            } while (j4 != 0);
        }
    }

    static final class RangeSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Long> d;

        RangeSubscription(Subscriber<? super Long> subscriber, long j, long j2) {
            super(j, j2);
            this.d = subscriber;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            long j = this.a;
            Subscriber<? super Long> subscriber = this.d;
            long j2 = this.b;
            while (j2 != j) {
                if (!this.c) {
                    subscriber.onNext(Long.valueOf(j2));
                    j2++;
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
            long j2 = this.a;
            long j3 = this.b;
            Subscriber<? super Long> subscriber = this.d;
            long j4 = j3;
            long j5 = j;
            do {
                long j6 = 0;
                while (true) {
                    if (j6 == j5 || j4 == j2) {
                        if (j4 == j2) {
                            if (!this.c) {
                                subscriber.onComplete();
                            }
                            return;
                        }
                        j5 = get();
                        if (j6 == j5) {
                            this.b = j4;
                            j5 = addAndGet(-j6);
                        }
                    } else if (!this.c) {
                        subscriber.onNext(Long.valueOf(j4));
                        j4++;
                        j6++;
                    } else {
                        return;
                    }
                }
            } while (j5 != 0);
        }
    }

    public FlowableRangeLong(long j, long j2) {
        this.b = j;
        this.c = j + j2;
    }

    public void subscribeActual(Subscriber<? super Long> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            RangeConditionalSubscription rangeConditionalSubscription = new RangeConditionalSubscription((ConditionalSubscriber) subscriber, this.b, this.c);
            subscriber.onSubscribe(rangeConditionalSubscription);
            return;
        }
        RangeSubscription rangeSubscription = new RangeSubscription(subscriber, this.b, this.c);
        subscriber.onSubscribe(rangeSubscription);
    }
}
