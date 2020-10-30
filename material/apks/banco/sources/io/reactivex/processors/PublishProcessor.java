package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class PublishProcessor<T> extends FlowableProcessor<T> {
    static final PublishSubscription[] b = new PublishSubscription[0];
    static final PublishSubscription[] c = new PublishSubscription[0];
    final AtomicReference<PublishSubscription<T>[]> d = new AtomicReference<>(c);
    Throwable e;

    static final class PublishSubscription<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 3562861878281475070L;
        final Subscriber<? super T> a;
        final PublishProcessor<T> b;

        PublishSubscription(Subscriber<? super T> subscriber, PublishProcessor<T> publishProcessor) {
            this.a = subscriber;
            this.b = publishProcessor;
        }

        public void a(T t) {
            long j = get();
            if (j != Long.MIN_VALUE) {
                if (j != 0) {
                    this.a.onNext(t);
                    if (j != Long.MAX_VALUE) {
                        decrementAndGet();
                    }
                } else {
                    cancel();
                    this.a.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
                }
            }
        }

        public void a(Throwable th) {
            if (get() != Long.MIN_VALUE) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void a() {
            if (get() != Long.MIN_VALUE) {
                this.a.onComplete();
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
            }
        }

        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.b.b(this);
            }
        }

        public boolean b() {
            return get() == Long.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return get() == 0;
        }
    }

    @CheckReturnValue
    public static <T> PublishProcessor<T> create() {
        return new PublishProcessor<>();
    }

    PublishProcessor() {
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        PublishSubscription publishSubscription = new PublishSubscription(subscriber, this);
        subscriber.onSubscribe(publishSubscription);
        if (!a(publishSubscription)) {
            Throwable th = this.e;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
        } else if (publishSubscription.b()) {
            b(publishSubscription);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(PublishSubscription<T> publishSubscription) {
        PublishSubscription[] publishSubscriptionArr;
        PublishSubscription[] publishSubscriptionArr2;
        do {
            publishSubscriptionArr = (PublishSubscription[]) this.d.get();
            if (publishSubscriptionArr == b) {
                return false;
            }
            int length = publishSubscriptionArr.length;
            publishSubscriptionArr2 = new PublishSubscription[(length + 1)];
            System.arraycopy(publishSubscriptionArr, 0, publishSubscriptionArr2, 0, length);
            publishSubscriptionArr2[length] = publishSubscription;
        } while (!this.d.compareAndSet(publishSubscriptionArr, publishSubscriptionArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(PublishSubscription<T> publishSubscription) {
        PublishSubscription<T>[] publishSubscriptionArr;
        PublishSubscription[] publishSubscriptionArr2;
        do {
            publishSubscriptionArr = (PublishSubscription[]) this.d.get();
            if (publishSubscriptionArr != b && publishSubscriptionArr != c) {
                int length = publishSubscriptionArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (publishSubscriptionArr[i2] == publishSubscription) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        publishSubscriptionArr2 = c;
                    } else {
                        PublishSubscription[] publishSubscriptionArr3 = new PublishSubscription[(length - 1)];
                        System.arraycopy(publishSubscriptionArr, 0, publishSubscriptionArr3, 0, i);
                        System.arraycopy(publishSubscriptionArr, i + 1, publishSubscriptionArr3, i, (length - i) - 1);
                        publishSubscriptionArr2 = publishSubscriptionArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(publishSubscriptionArr, publishSubscriptionArr2));
    }

    public void onSubscribe(Subscription subscription) {
        if (this.d.get() == b) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (this.d.get() != b) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            for (PublishSubscription a : (PublishSubscription[]) this.d.get()) {
                a.a(t);
            }
        }
    }

    public void onError(Throwable th) {
        if (this.d.get() == b) {
            RxJavaPlugins.onError(th);
            return;
        }
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.e = th;
        for (PublishSubscription a : (PublishSubscription[]) this.d.getAndSet(b)) {
            a.a(th);
        }
    }

    public void onComplete() {
        if (this.d.get() != b) {
            for (PublishSubscription a : (PublishSubscription[]) this.d.getAndSet(b)) {
                a.a();
            }
        }
    }

    @Experimental
    public boolean offer(T t) {
        if (t == null) {
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return true;
        }
        PublishSubscription[] publishSubscriptionArr = (PublishSubscription[]) this.d.get();
        for (PublishSubscription c2 : publishSubscriptionArr) {
            if (c2.c()) {
                return false;
            }
        }
        for (PublishSubscription a : publishSubscriptionArr) {
            a.a(t);
        }
        return true;
    }

    public boolean hasSubscribers() {
        return ((PublishSubscription[]) this.d.get()).length != 0;
    }

    public Throwable getThrowable() {
        if (this.d.get() == b) {
            return this.e;
        }
        return null;
    }

    public boolean hasThrowable() {
        return this.d.get() == b && this.e != null;
    }

    public boolean hasComplete() {
        return this.d.get() == b && this.e == null;
    }
}
