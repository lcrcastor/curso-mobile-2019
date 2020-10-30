package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FullArbiter<T> extends FullArbiterPad2 implements Subscription {
    static final Subscription e = new InitialSubscription();
    static final Object h = new Object();
    final Subscriber<? super T> a;
    final SpscLinkedArrayQueue<Object> b;
    long c;
    volatile Subscription d = e;
    Disposable f;
    volatile boolean g;

    static final class InitialSubscription implements Subscription {
        public void cancel() {
        }

        public void request(long j) {
        }

        InitialSubscription() {
        }
    }

    public FullArbiter(Subscriber<? super T> subscriber, Disposable disposable, int i) {
        this.a = subscriber;
        this.f = disposable;
        this.b = new SpscLinkedArrayQueue<>(i);
    }

    public void request(long j) {
        if (SubscriptionHelper.validate(j)) {
            BackpressureHelper.add(this.i, j);
            this.b.offer(h, h);
            b();
        }
    }

    public void cancel() {
        if (!this.g) {
            this.g = true;
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        Disposable disposable = this.f;
        this.f = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public boolean setSubscription(Subscription subscription) {
        if (this.g) {
            if (subscription != null) {
                subscription.cancel();
            }
            return false;
        }
        ObjectHelper.requireNonNull(subscription, "s is null");
        this.b.offer(this.d, NotificationLite.subscription(subscription));
        b();
        return true;
    }

    public boolean onNext(T t, Subscription subscription) {
        if (this.g) {
            return false;
        }
        this.b.offer(subscription, NotificationLite.next(t));
        b();
        return true;
    }

    public void onError(Throwable th, Subscription subscription) {
        if (this.g) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.b.offer(subscription, NotificationLite.error(th));
        b();
    }

    public void onComplete(Subscription subscription) {
        this.b.offer(subscription, NotificationLite.complete());
        b();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.j.getAndIncrement() == 0) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.b;
            Subscriber<? super T> subscriber = this.a;
            int i = 1;
            while (true) {
                Object poll = spscLinkedArrayQueue.poll();
                if (poll == null) {
                    i = this.j.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                } else {
                    Object poll2 = spscLinkedArrayQueue.poll();
                    if (poll == h) {
                        long andSet = this.i.getAndSet(0);
                        if (andSet != 0) {
                            this.c = BackpressureHelper.addCap(this.c, andSet);
                            this.d.request(andSet);
                        }
                    } else if (poll == this.d) {
                        if (NotificationLite.isSubscription(poll2)) {
                            Subscription subscription = NotificationLite.getSubscription(poll2);
                            if (!this.g) {
                                this.d = subscription;
                                long j = this.c;
                                if (j != 0) {
                                    subscription.request(j);
                                }
                            } else {
                                subscription.cancel();
                            }
                        } else if (NotificationLite.isError(poll2)) {
                            spscLinkedArrayQueue.clear();
                            a();
                            Throwable error = NotificationLite.getError(poll2);
                            if (!this.g) {
                                this.g = true;
                                subscriber.onError(error);
                            } else {
                                RxJavaPlugins.onError(error);
                            }
                        } else if (NotificationLite.isComplete(poll2)) {
                            spscLinkedArrayQueue.clear();
                            a();
                            if (!this.g) {
                                this.g = true;
                                subscriber.onComplete();
                            }
                        } else {
                            long j2 = this.c;
                            if (j2 != 0) {
                                subscriber.onNext(NotificationLite.getValue(poll2));
                                this.c = j2 - 1;
                            }
                        }
                    }
                }
            }
        }
    }
}
