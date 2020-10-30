package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableTakePublisher<T> extends Flowable<T> {
    final Publisher<T> b;
    final long c;

    public FlowableTakePublisher(Publisher<T> publisher, long j) {
        this.b = publisher;
        this.c = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new TakeSubscriber(subscriber, this.c));
    }
}
