package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> b;

    static class SubscriberObserver<T> implements Observer<T>, Subscription {
        private final Subscriber<? super T> a;
        private Disposable b;

        public void request(long j) {
        }

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        public void onComplete() {
            this.a.onComplete();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onNext(T t) {
            this.a.onNext(t);
        }

        public void onSubscribe(Disposable disposable) {
            this.b = disposable;
            this.a.onSubscribe(this);
        }

        public void cancel() {
            this.b.dispose();
        }
    }

    public FlowableFromObservable(Observable<T> observable) {
        this.b = observable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe((Observer<? super T>) new SubscriberObserver<Object>(subscriber));
    }
}
