package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeConcatArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] b;

    static final class ConcatMaybeObserver<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
        private static final long serialVersionUID = 3520831347801429610L;
        final Subscriber<? super T> a;
        final AtomicLong b = new AtomicLong();
        final AtomicReference<Object> c = new AtomicReference<>(NotificationLite.COMPLETE);
        final SequentialDisposable d = new SequentialDisposable();
        final MaybeSource<? extends T>[] e;
        int f;
        long g;

        ConcatMaybeObserver(Subscriber<? super T> subscriber, MaybeSource<? extends T>[] maybeSourceArr) {
            this.a = subscriber;
            this.e = maybeSourceArr;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.b, j);
                a();
            }
        }

        public void cancel() {
            this.d.dispose();
        }

        public void onSubscribe(Disposable disposable) {
            this.d.replace(disposable);
        }

        public void onSuccess(T t) {
            this.c.lazySet(t);
            a();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.c.lazySet(NotificationLite.COMPLETE);
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                AtomicReference<Object> atomicReference = this.c;
                Subscriber<? super T> subscriber = this.a;
                SequentialDisposable sequentialDisposable = this.d;
                while (!sequentialDisposable.isDisposed()) {
                    Object obj = atomicReference.get();
                    if (obj != null) {
                        boolean z = true;
                        if (obj != NotificationLite.COMPLETE) {
                            long j = this.g;
                            if (j != this.b.get()) {
                                this.g = j + 1;
                                atomicReference.lazySet(null);
                                subscriber.onNext(obj);
                            } else {
                                z = false;
                            }
                        } else {
                            atomicReference.lazySet(null);
                        }
                        if (z && !sequentialDisposable.isDisposed()) {
                            int i = this.f;
                            if (i == this.e.length) {
                                subscriber.onComplete();
                                return;
                            } else {
                                this.f = i + 1;
                                this.e[i].subscribe(this);
                            }
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                atomicReference.lazySet(null);
            }
        }
    }

    public MaybeConcatArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.b = maybeSourceArr;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ConcatMaybeObserver concatMaybeObserver = new ConcatMaybeObserver(subscriber, this.b);
        subscriber.onSubscribe(concatMaybeObserver);
        concatMaybeObserver.a();
    }
}
