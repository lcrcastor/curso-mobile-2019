package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeConcatIterable<T> extends Flowable<T> {
    final Iterable<? extends MaybeSource<? extends T>> b;

    static final class ConcatMaybeObserver<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
        private static final long serialVersionUID = 3520831347801429610L;
        final Subscriber<? super T> a;
        final AtomicLong b = new AtomicLong();
        final AtomicReference<Object> c = new AtomicReference<>(NotificationLite.COMPLETE);
        final SequentialDisposable d = new SequentialDisposable();
        final Iterator<? extends MaybeSource<? extends T>> e;
        long f;

        ConcatMaybeObserver(Subscriber<? super T> subscriber, Iterator<? extends MaybeSource<? extends T>> it) {
            this.a = subscriber;
            this.e = it;
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
                            long j = this.f;
                            if (j != this.b.get()) {
                                this.f = j + 1;
                                atomicReference.lazySet(null);
                                subscriber.onNext(obj);
                            } else {
                                z = false;
                            }
                        } else {
                            atomicReference.lazySet(null);
                        }
                        if (z && !sequentialDisposable.isDisposed()) {
                            try {
                                if (this.e.hasNext()) {
                                    try {
                                        ((MaybeSource) ObjectHelper.requireNonNull(this.e.next(), "The source Iterator returned a null MaybeSource")).subscribe(this);
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        subscriber.onError(th);
                                        return;
                                    }
                                } else {
                                    subscriber.onComplete();
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                subscriber.onError(th2);
                                return;
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

    public MaybeConcatIterable(Iterable<? extends MaybeSource<? extends T>> iterable) {
        this.b = iterable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            ConcatMaybeObserver concatMaybeObserver = new ConcatMaybeObserver(subscriber, (Iterator) ObjectHelper.requireNonNull(this.b.iterator(), "The sources Iterable returned a null Iterator"));
            subscriber.onSubscribe(concatMaybeObserver);
            concatMaybeObserver.a();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
