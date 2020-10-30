package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleFromPublisher<T> extends Single<T> {
    final Publisher<? extends T> a;

    static final class ToSingleObserver<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        Subscription b;
        T c;
        boolean d;
        volatile boolean e;

        ToSingleObserver(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                if (this.c != null) {
                    this.b.cancel();
                    this.d = true;
                    this.c = null;
                    this.a.onError(new IndexOutOfBoundsException("Too many elements in the Publisher"));
                } else {
                    this.c = t;
                }
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.c = null;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                T t = this.c;
                this.c = null;
                if (t == null) {
                    this.a.onError(new NoSuchElementException("The source Publisher is empty"));
                } else {
                    this.a.onSuccess(t);
                }
            }
        }

        public boolean isDisposed() {
            return this.e;
        }

        public void dispose() {
            this.e = true;
            this.b.cancel();
        }
    }

    public SingleFromPublisher(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new ToSingleObserver(singleObserver));
    }
}
