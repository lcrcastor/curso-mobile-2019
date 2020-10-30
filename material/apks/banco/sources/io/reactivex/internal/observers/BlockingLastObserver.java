package io.reactivex.internal.observers;

public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    public void onNext(T t) {
        this.a = t;
    }

    public void onError(Throwable th) {
        this.a = null;
        this.b = th;
        countDown();
    }
}
