package io.reactivex.internal.operators.parallel;

import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelFromArray<T> extends ParallelFlowable<T> {
    final Publisher<T>[] a;

    public ParallelFromArray(Publisher<T>[] publisherArr) {
        this.a = publisherArr;
    }

    public int parallelism() {
        return this.a.length;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            for (int i = 0; i < length; i++) {
                this.a[i].subscribe(subscriberArr[i]);
            }
        }
    }
}
