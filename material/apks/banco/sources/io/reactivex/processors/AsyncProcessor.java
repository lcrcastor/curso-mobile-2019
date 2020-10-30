package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class AsyncProcessor<T> extends FlowableProcessor<T> {
    static final AsyncSubscription[] b = new AsyncSubscription[0];
    static final AsyncSubscription[] c = new AsyncSubscription[0];
    final AtomicReference<AsyncSubscription<T>[]> d = new AtomicReference<>(b);
    Throwable e;
    T f;

    static final class AsyncSubscription<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = 5629876084736248016L;
        final AsyncProcessor<T> a;

        AsyncSubscription(Subscriber<? super T> subscriber, AsyncProcessor<T> asyncProcessor) {
            super(subscriber);
            this.a = asyncProcessor;
        }

        public void cancel() {
            if (super.tryCancel()) {
                this.a.b(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (!isCancelled()) {
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (isCancelled()) {
                RxJavaPlugins.onError(th);
            } else {
                this.actual.onError(th);
            }
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> AsyncProcessor<T> create() {
        return new AsyncProcessor<>();
    }

    AsyncProcessor() {
    }

    public void onSubscribe(Subscription subscription) {
        if (this.d.get() == c) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (this.d.get() != c) {
            if (t == null) {
                a();
            } else {
                this.f = t;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.f = null;
        NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        this.e = nullPointerException;
        for (AsyncSubscription a : (AsyncSubscription[]) this.d.getAndSet(c)) {
            a.a(nullPointerException);
        }
    }

    public void onError(Throwable th) {
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (this.d.get() == c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.f = null;
        this.e = th;
        for (AsyncSubscription a : (AsyncSubscription[]) this.d.getAndSet(c)) {
            a.a(th);
        }
    }

    public void onComplete() {
        if (this.d.get() != c) {
            T t = this.f;
            AsyncSubscription[] asyncSubscriptionArr = (AsyncSubscription[]) this.d.getAndSet(c);
            int i = 0;
            if (t == null) {
                int length = asyncSubscriptionArr.length;
                while (i < length) {
                    asyncSubscriptionArr[i].a();
                    i++;
                }
            } else {
                int length2 = asyncSubscriptionArr.length;
                while (i < length2) {
                    asyncSubscriptionArr[i].complete(t);
                    i++;
                }
            }
        }
    }

    public boolean hasSubscribers() {
        return ((AsyncSubscription[]) this.d.get()).length != 0;
    }

    public boolean hasThrowable() {
        return this.d.get() == c && this.e != null;
    }

    public boolean hasComplete() {
        return this.d.get() == c && this.e == null;
    }

    public Throwable getThrowable() {
        if (this.d.get() == c) {
            return this.e;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        AsyncSubscription asyncSubscription = new AsyncSubscription(subscriber, this);
        subscriber.onSubscribe(asyncSubscription);
        if (!a(asyncSubscription)) {
            Throwable th = this.e;
            if (th != null) {
                subscriber.onError(th);
                return;
            }
            T t = this.f;
            if (t != null) {
                asyncSubscription.complete(t);
            } else {
                asyncSubscription.a();
            }
        } else if (asyncSubscription.isCancelled()) {
            b(asyncSubscription);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(AsyncSubscription<T> asyncSubscription) {
        AsyncSubscription[] asyncSubscriptionArr;
        AsyncSubscription[] asyncSubscriptionArr2;
        do {
            asyncSubscriptionArr = (AsyncSubscription[]) this.d.get();
            if (asyncSubscriptionArr == c) {
                return false;
            }
            int length = asyncSubscriptionArr.length;
            asyncSubscriptionArr2 = new AsyncSubscription[(length + 1)];
            System.arraycopy(asyncSubscriptionArr, 0, asyncSubscriptionArr2, 0, length);
            asyncSubscriptionArr2[length] = asyncSubscription;
        } while (!this.d.compareAndSet(asyncSubscriptionArr, asyncSubscriptionArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(AsyncSubscription<T> asyncSubscription) {
        AsyncSubscription<T>[] asyncSubscriptionArr;
        AsyncSubscription[] asyncSubscriptionArr2;
        do {
            asyncSubscriptionArr = (AsyncSubscription[]) this.d.get();
            int length = asyncSubscriptionArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (asyncSubscriptionArr[i2] == asyncSubscription) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        asyncSubscriptionArr2 = b;
                    } else {
                        AsyncSubscription[] asyncSubscriptionArr3 = new AsyncSubscription[(length - 1)];
                        System.arraycopy(asyncSubscriptionArr, 0, asyncSubscriptionArr3, 0, i);
                        System.arraycopy(asyncSubscriptionArr, i + 1, asyncSubscriptionArr3, i, (length - i) - 1);
                        asyncSubscriptionArr2 = asyncSubscriptionArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(asyncSubscriptionArr, asyncSubscriptionArr2));
    }

    public boolean hasValue() {
        return this.d.get() == c && this.f != null;
    }

    public T getValue() {
        if (this.d.get() == c) {
            return this.f;
        }
        return null;
    }

    public Object[] getValues() {
        Object value = getValue();
        if (value == null) {
            return new Object[0];
        }
        return new Object[]{value};
    }

    public T[] getValues(T[] tArr) {
        T value = getValue();
        if (value == null) {
            if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }
        if (tArr.length == 0) {
            tArr = Arrays.copyOf(tArr, 1);
        }
        tArr[0] = value;
        if (tArr.length != 1) {
            tArr[1] = null;
        }
        return tArr;
    }
}
