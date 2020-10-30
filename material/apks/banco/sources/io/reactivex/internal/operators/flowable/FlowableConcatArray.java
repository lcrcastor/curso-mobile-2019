package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatArray<T> extends Flowable<T> {
    final Publisher<? extends T>[] b;
    final boolean c;

    static final class ConcatArraySubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -8158322871608889516L;
        final Subscriber<? super T> a;
        final Publisher<? extends T>[] b;
        final boolean c;
        final AtomicInteger d = new AtomicInteger();
        int e;
        List<Throwable> f;
        long g;

        ConcatArraySubscriber(Publisher<? extends T>[] publisherArr, boolean z, Subscriber<? super T> subscriber) {
            this.a = subscriber;
            this.b = publisherArr;
            this.c = z;
        }

        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        public void onNext(T t) {
            this.g++;
            this.a.onNext(t);
        }

        public void onError(Throwable th) {
            if (this.c) {
                List list = this.f;
                if (list == null) {
                    list = new ArrayList((this.b.length - this.e) + 1);
                    this.f = list;
                }
                list.add(th);
                onComplete();
                return;
            }
            this.a.onError(th);
        }

        public void onComplete() {
            if (this.d.getAndIncrement() == 0) {
                Publisher<? extends T>[] publisherArr = this.b;
                int length = publisherArr.length;
                int i = this.e;
                while (i != length) {
                    Publisher<? extends T> publisher = publisherArr[i];
                    if (publisher == null) {
                        NullPointerException nullPointerException = new NullPointerException("A Publisher entry is null");
                        if (this.c) {
                            List list = this.f;
                            if (list == null) {
                                list = new ArrayList((length - i) + 1);
                                this.f = list;
                            }
                            list.add(nullPointerException);
                            i++;
                        } else {
                            this.a.onError(nullPointerException);
                            return;
                        }
                    } else {
                        long j = this.g;
                        if (j != 0) {
                            this.g = 0;
                            produced(j);
                        }
                        publisher.subscribe(this);
                        i++;
                        this.e = i;
                        if (this.d.decrementAndGet() == 0) {
                        }
                    }
                }
                List<Throwable> list2 = this.f;
                if (list2 == null) {
                    this.a.onComplete();
                } else if (list2.size() == 1) {
                    this.a.onError((Throwable) list2.get(0));
                } else {
                    this.a.onError(new CompositeException((Iterable<? extends Throwable>) list2));
                }
            }
        }
    }

    public FlowableConcatArray(Publisher<? extends T>[] publisherArr, boolean z) {
        this.b = publisherArr;
        this.c = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ConcatArraySubscriber concatArraySubscriber = new ConcatArraySubscriber(this.b, this.c, subscriber);
        subscriber.onSubscribe(concatArraySubscriber);
        concatArraySubscriber.onComplete();
    }
}
