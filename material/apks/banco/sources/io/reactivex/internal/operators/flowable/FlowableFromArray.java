package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableFromArray<T> extends Flowable<T> {
    final T[] b;

    static final class ArrayConditionalSubscription<T> extends BaseArraySubscription<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super T> a;

        ArrayConditionalSubscription(ConditionalSubscriber<? super T> conditionalSubscriber, T[] tArr) {
            super(tArr);
            this.a = conditionalSubscriber;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Object[] objArr = this.b;
            int length = objArr.length;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.a;
            int i = this.c;
            while (i != length) {
                if (!this.d) {
                    Object obj = objArr[i];
                    if (obj == null) {
                        conditionalSubscriber.onError(new NullPointerException("array element is null"));
                        return;
                    } else {
                        conditionalSubscriber.tryOnNext(obj);
                        i++;
                    }
                } else {
                    return;
                }
            }
            if (!this.d) {
                conditionalSubscriber.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            Object[] objArr = this.b;
            int length = objArr.length;
            int i = this.c;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.a;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i == length) {
                        if (i == length) {
                            if (!this.d) {
                                conditionalSubscriber.onComplete();
                            }
                            return;
                        }
                        j2 = get();
                        if (j3 == j2) {
                            this.c = i;
                            j2 = addAndGet(-j3);
                        }
                    } else if (!this.d) {
                        Object obj = objArr[i];
                        if (obj == null) {
                            conditionalSubscriber.onError(new NullPointerException("array element is null"));
                            return;
                        }
                        if (conditionalSubscriber.tryOnNext(obj)) {
                            j3++;
                        }
                        i++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    static final class ArraySubscription<T> extends BaseArraySubscription<T> {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super T> a;

        ArraySubscription(Subscriber<? super T> subscriber, T[] tArr) {
            super(tArr);
            this.a = subscriber;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Object[] objArr = this.b;
            int length = objArr.length;
            Subscriber<? super T> subscriber = this.a;
            int i = this.c;
            while (i != length) {
                if (!this.d) {
                    Object obj = objArr[i];
                    if (obj == null) {
                        subscriber.onError(new NullPointerException("array element is null"));
                        return;
                    } else {
                        subscriber.onNext(obj);
                        i++;
                    }
                } else {
                    return;
                }
            }
            if (!this.d) {
                subscriber.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            Object[] objArr = this.b;
            int length = objArr.length;
            int i = this.c;
            Subscriber<? super T> subscriber = this.a;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i == length) {
                        if (i == length) {
                            if (!this.d) {
                                subscriber.onComplete();
                            }
                            return;
                        }
                        j2 = get();
                        if (j3 == j2) {
                            this.c = i;
                            j2 = addAndGet(-j3);
                        }
                    } else if (!this.d) {
                        Object obj = objArr[i];
                        if (obj == null) {
                            subscriber.onError(new NullPointerException("array element is null"));
                            return;
                        }
                        subscriber.onNext(obj);
                        i++;
                        j3++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    static abstract class BaseArraySubscription<T> extends BasicQueueSubscription<T> {
        private static final long serialVersionUID = -2252972430506210021L;
        final T[] b;
        int c;
        volatile boolean d;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        /* access modifiers changed from: 0000 */
        public abstract void a(long j);

        public final int requestFusion(int i) {
            return i & 1;
        }

        BaseArraySubscription(T[] tArr) {
            this.b = tArr;
        }

        @Nullable
        public final T poll() {
            int i = this.c;
            T[] tArr = this.b;
            if (i == tArr.length) {
                return null;
            }
            this.c = i + 1;
            return ObjectHelper.requireNonNull(tArr[i], "array element is null");
        }

        public final boolean isEmpty() {
            return this.c == this.b.length;
        }

        public final void clear() {
            this.c = this.b.length;
        }

        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    a();
                } else {
                    a(j);
                }
            }
        }

        public final void cancel() {
            this.d = true;
        }
    }

    public FlowableFromArray(T[] tArr) {
        this.b = tArr;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new ArrayConditionalSubscription((ConditionalSubscriber) subscriber, this.b));
        } else {
            subscriber.onSubscribe(new ArraySubscription(subscriber, this.b));
        }
    }
}
