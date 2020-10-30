package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToFlowable<Boolean> {
    final Publisher<? extends T> a;
    final Publisher<? extends T> b;
    final BiPredicate<? super T, ? super T> c;
    final int d;

    static final class EqualCoordinator<T> extends AtomicInteger implements Disposable, EqualCoordinatorHelper {
        private static final long serialVersionUID = -6178010334400373240L;
        final SingleObserver<? super Boolean> a;
        final BiPredicate<? super T, ? super T> b;
        final EqualSubscriber<T> c;
        final EqualSubscriber<T> d;
        final AtomicThrowable e = new AtomicThrowable();
        T f;
        T g;

        EqualCoordinator(SingleObserver<? super Boolean> singleObserver, int i, BiPredicate<? super T, ? super T> biPredicate) {
            this.a = singleObserver;
            this.b = biPredicate;
            this.c = new EqualSubscriber<>(this, i);
            this.d = new EqualSubscriber<>(this, i);
        }

        /* access modifiers changed from: 0000 */
        public void a(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
            publisher.subscribe(this.c);
            publisher2.subscribe(this.d);
        }

        public void dispose() {
            this.c.b();
            this.d.b();
            if (getAndIncrement() == 0) {
                this.c.c();
                this.d.c();
            }
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) this.c.get());
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.c.b();
            this.c.c();
            this.d.b();
            this.d.c();
        }

        public void b() {
            if (getAndIncrement() == 0) {
                int i = 1;
                do {
                    SimpleQueue<T> simpleQueue = this.c.e;
                    SimpleQueue<T> simpleQueue2 = this.d.e;
                    if (simpleQueue != null && simpleQueue2 != null) {
                        while (!isDisposed()) {
                            if (((Throwable) this.e.get()) != null) {
                                a();
                                this.a.onError(this.e.terminate());
                                return;
                            }
                            boolean z = this.c.f;
                            T t = this.f;
                            if (t == null) {
                                try {
                                    t = simpleQueue.poll();
                                    this.f = t;
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    a();
                                    this.e.addThrowable(th);
                                    this.a.onError(this.e.terminate());
                                    return;
                                }
                            }
                            boolean z2 = t == null;
                            boolean z3 = this.d.f;
                            T t2 = this.g;
                            if (t2 == null) {
                                try {
                                    t2 = simpleQueue2.poll();
                                    this.g = t2;
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a();
                                    this.e.addThrowable(th2);
                                    this.a.onError(this.e.terminate());
                                    return;
                                }
                            }
                            boolean z4 = t2 == null;
                            if (z && z3 && z2 && z4) {
                                this.a.onSuccess(Boolean.valueOf(true));
                                return;
                            } else if (z && z3 && z2 != z4) {
                                a();
                                this.a.onSuccess(Boolean.valueOf(false));
                                return;
                            } else if (!z2 && !z4) {
                                try {
                                    if (!this.b.test(t, t2)) {
                                        a();
                                        this.a.onSuccess(Boolean.valueOf(false));
                                        return;
                                    }
                                    this.f = null;
                                    this.g = null;
                                    this.c.a();
                                    this.d.a();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    a();
                                    this.e.addThrowable(th3);
                                    this.a.onError(this.e.terminate());
                                    return;
                                }
                            }
                        }
                        this.c.c();
                        this.d.c();
                        return;
                    } else if (isDisposed()) {
                        this.c.c();
                        this.d.c();
                        return;
                    } else if (((Throwable) this.e.get()) != null) {
                        a();
                        this.a.onError(this.e.terminate());
                        return;
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }

        public void a(Throwable th) {
            if (this.e.addThrowable(th)) {
                b();
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public FlowableSequenceEqualSingle(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = publisher;
        this.b = publisher2;
        this.c = biPredicate;
        this.d = i;
    }

    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(singleObserver, this.d, this.c);
        singleObserver.onSubscribe(equalCoordinator);
        equalCoordinator.a(this.a, this.b);
    }

    public Flowable<Boolean> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableSequenceEqual<T>(this.a, this.b, this.c, this.d));
    }
}
