package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableFromPublisher<T> extends Flowable<T> {
    final Publisher<? extends T> b;

    public FlowableFromPublisher(Publisher<? extends T> publisher) {
        this.b = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(subscriber);
    }
}
