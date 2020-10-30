package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAmb<T> extends Flowable<T> {
    final Publisher<? extends T>[] b;
    final Iterable<? extends Publisher<? extends T>> c;

    static final class AmbCoordinator<T> implements Subscription {
        final Subscriber<? super T> a;
        final AmbInnerSubscriber<T>[] b;
        final AtomicInteger c = new AtomicInteger();

        AmbCoordinator(Subscriber<? super T> subscriber, int i) {
            this.a = subscriber;
            this.b = new AmbInnerSubscriber[i];
        }

        public void a(Publisher<? extends T>[] publisherArr) {
            AmbInnerSubscriber<T>[] ambInnerSubscriberArr = this.b;
            int length = ambInnerSubscriberArr.length;
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                ambInnerSubscriberArr[i] = new AmbInnerSubscriber<>(this, i2, this.a);
                i = i2;
            }
            this.c.lazySet(0);
            this.a.onSubscribe(this);
            for (int i3 = 0; i3 < length && this.c.get() == 0; i3++) {
                publisherArr[i3].subscribe(ambInnerSubscriberArr[i3]);
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                int i = this.c.get();
                if (i > 0) {
                    this.b[i - 1].request(j);
                } else if (i == 0) {
                    for (AmbInnerSubscriber<T> request : this.b) {
                        request.request(j);
                    }
                }
            }
        }

        public boolean a(int i) {
            int i2 = 0;
            if (this.c.get() != 0 || !this.c.compareAndSet(0, i)) {
                return false;
            }
            AmbInnerSubscriber<T>[] ambInnerSubscriberArr = this.b;
            int length = ambInnerSubscriberArr.length;
            while (i2 < length) {
                int i3 = i2 + 1;
                if (i3 != i) {
                    ambInnerSubscriberArr[i2].cancel();
                }
                i2 = i3;
            }
            return true;
        }

        public void cancel() {
            if (this.c.get() != -1) {
                this.c.lazySet(-1);
                for (AmbInnerSubscriber<T> cancel : this.b) {
                    cancel.cancel();
                }
            }
        }
    }

    static final class AmbInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -1185974347409665484L;
        final AmbCoordinator<T> a;
        final int b;
        final Subscriber<? super T> c;
        boolean d;
        final AtomicLong e = new AtomicLong();

        AmbInnerSubscriber(AmbCoordinator<T> ambCoordinator, int i, Subscriber<? super T> subscriber) {
            this.a = ambCoordinator;
            this.b = i;
            this.c = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this, this.e, subscription);
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this, this.e, j);
        }

        public void onNext(T t) {
            if (this.d) {
                this.c.onNext(t);
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onNext(t);
            } else {
                ((Subscription) get()).cancel();
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                this.c.onError(th);
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onError(th);
            } else {
                ((Subscription) get()).cancel();
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (this.d) {
                this.c.onComplete();
            } else if (this.a.a(this.b)) {
                this.d = true;
                this.c.onComplete();
            } else {
                ((Subscription) get()).cancel();
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }
    }

    public FlowableAmb(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable) {
        this.b = publisherArr;
        this.c = iterable;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        int i;
        Publisher<? extends T>[] publisherArr = this.b;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                i = 0;
                for (Publisher<? extends T> publisher : this.c) {
                    if (publisher == null) {
                        EmptySubscription.error(new NullPointerException("One of the sources is null"), subscriber);
                        return;
                    }
                    if (i == publisherArr.length) {
                        Publisher<? extends T>[] publisherArr2 = new Publisher[((i >> 2) + i)];
                        System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                        publisherArr = publisherArr2;
                    }
                    int i2 = i + 1;
                    publisherArr[i] = publisher;
                    i = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
                return;
            }
        } else {
            i = publisherArr.length;
        }
        if (i == 0) {
            EmptySubscription.complete(subscriber);
        } else if (i == 1) {
            publisherArr[0].subscribe(subscriber);
        } else {
            new AmbCoordinator(subscriber, i).a(publisherArr);
        }
    }
}
