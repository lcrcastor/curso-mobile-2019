package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public class SubscriptionArbiter extends AtomicInteger implements Subscription {
    private static final long serialVersionUID = -2189523197179400958L;
    Subscription h;
    long i;
    final AtomicReference<Subscription> j = new AtomicReference<>();
    final AtomicLong k = new AtomicLong();
    final AtomicLong l = new AtomicLong();
    volatile boolean m;
    protected boolean unbounded;

    public final void setSubscription(Subscription subscription) {
        if (this.m) {
            subscription.cancel();
            return;
        }
        ObjectHelper.requireNonNull(subscription, "s is null");
        if (get() != 0 || !compareAndSet(0, 1)) {
            Subscription subscription2 = (Subscription) this.j.getAndSet(subscription);
            if (subscription2 != null) {
                subscription2.cancel();
            }
            a();
            return;
        }
        Subscription subscription3 = this.h;
        if (subscription3 != null) {
            subscription3.cancel();
        }
        this.h = subscription;
        long j2 = this.i;
        if (decrementAndGet() != 0) {
            b();
        }
        if (j2 != 0) {
            subscription.request(j2);
        }
    }

    public final void request(long j2) {
        if (SubscriptionHelper.validate(j2) && !this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                BackpressureHelper.add(this.k, j2);
                a();
                return;
            }
            long j3 = this.i;
            if (j3 != Long.MAX_VALUE) {
                long addCap = BackpressureHelper.addCap(j3, j2);
                this.i = addCap;
                if (addCap == Long.MAX_VALUE) {
                    this.unbounded = true;
                }
            }
            Subscription subscription = this.h;
            if (decrementAndGet() != 0) {
                b();
            }
            if (subscription != null) {
                subscription.request(j2);
            }
        }
    }

    public final void produced(long j2) {
        if (!this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                BackpressureHelper.add(this.l, j2);
                a();
                return;
            }
            long j3 = this.i;
            if (j3 != Long.MAX_VALUE) {
                long j4 = j3 - j2;
                long j5 = 0;
                if (j4 < 0) {
                    SubscriptionHelper.reportMoreProduced(j4);
                } else {
                    j5 = j4;
                }
                this.i = j5;
            }
            if (decrementAndGet() != 0) {
                b();
            }
        }
    }

    public void cancel() {
        if (!this.m) {
            this.m = true;
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (getAndIncrement() == 0) {
            b();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        int i2;
        Subscription subscription;
        int i3;
        Subscription subscription2 = null;
        long j2 = 0;
        int i4 = 1;
        do {
            Subscription subscription3 = (Subscription) this.j.get();
            if (subscription3 != null) {
                subscription3 = (Subscription) this.j.getAndSet(null);
            }
            long j3 = this.k.get();
            if (j3 != 0) {
                j3 = this.k.getAndSet(0);
            }
            long j4 = this.l.get();
            if (j4 != 0) {
                j4 = this.l.getAndSet(0);
            }
            Subscription subscription4 = this.h;
            if (this.m) {
                if (subscription4 != null) {
                    subscription4.cancel();
                    this.h = null;
                }
                if (subscription3 != null) {
                    subscription3.cancel();
                }
                i3 = i4;
                subscription = subscription2;
            } else {
                long j5 = this.i;
                if (j5 != Long.MAX_VALUE) {
                    j5 = BackpressureHelper.addCap(j5, j3);
                    if (j5 != Long.MAX_VALUE) {
                        i3 = i4;
                        subscription = subscription2;
                        long j6 = j5 - j4;
                        if (j6 < 0) {
                            SubscriptionHelper.reportMoreProduced(j6);
                            j6 = 0;
                        }
                        j5 = j6;
                    } else {
                        i3 = i4;
                        subscription = subscription2;
                    }
                    this.i = j5;
                } else {
                    i3 = i4;
                    subscription = subscription2;
                }
                if (subscription3 != null) {
                    if (subscription4 != null) {
                        subscription4.cancel();
                    }
                    this.h = subscription3;
                    if (j5 != 0) {
                        j2 = BackpressureHelper.addCap(j2, j5);
                        subscription2 = subscription3;
                    }
                } else if (!(subscription4 == null || j3 == 0)) {
                    j2 = BackpressureHelper.addCap(j2, j3);
                    subscription2 = subscription4;
                }
                i2 = i3;
                i4 = addAndGet(-i2);
            }
            i2 = i3;
            subscription2 = subscription;
            i4 = addAndGet(-i2);
        } while (i4 != 0);
        if (j2 != 0) {
            subscription2.request(j2);
        }
    }

    public final boolean isUnbounded() {
        return this.unbounded;
    }

    public final boolean isCancelled() {
        return this.m;
    }
}
