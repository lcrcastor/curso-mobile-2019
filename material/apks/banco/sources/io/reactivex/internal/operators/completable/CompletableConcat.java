package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableConcat extends Completable {
    final Publisher<? extends CompletableSource> a;
    final int b;

    static final class CompletableConcatSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = 9032184911934499404L;
        final CompletableObserver a;
        final int b;
        final int c;
        final ConcatInnerObserver d = new ConcatInnerObserver(this);
        final AtomicBoolean e = new AtomicBoolean();
        int f;
        int g;
        SimpleQueue<CompletableSource> h;
        Subscription i;
        volatile boolean j;
        volatile boolean k;

        static final class ConcatInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -5454794857847146511L;
            final CompletableConcatSubscriber a;

            ConcatInnerObserver(CompletableConcatSubscriber completableConcatSubscriber) {
                this.a = completableConcatSubscriber;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            public void onError(Throwable th) {
                this.a.a(th);
            }

            public void onComplete() {
                this.a.c();
            }
        }

        CompletableConcatSubscriber(CompletableObserver completableObserver, int i2) {
            this.a = completableObserver;
            this.b = i2;
            this.c = i2 - (i2 >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                long j2 = this.b == Integer.MAX_VALUE ? Long.MAX_VALUE : (long) this.b;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.f = requestFusion;
                        this.h = queueSubscription;
                        this.j = true;
                        this.a.onSubscribe(this);
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.f = requestFusion;
                        this.h = queueSubscription;
                        this.a.onSubscribe(this);
                        subscription.request(j2);
                        return;
                    }
                }
                if (this.b == Integer.MAX_VALUE) {
                    this.h = new SpscLinkedArrayQueue(Flowable.bufferSize());
                } else {
                    this.h = new SpscArrayQueue(this.b);
                }
                this.a.onSubscribe(this);
                subscription.request(j2);
            }
        }

        /* renamed from: a */
        public void onNext(CompletableSource completableSource) {
            if (this.f != 0 || this.h.offer(completableSource)) {
                a();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        public void onError(Throwable th) {
            if (this.e.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.d);
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.j = true;
            a();
        }

        public void dispose() {
            this.i.cancel();
            DisposableHelper.dispose(this.d);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.d.get());
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.k) {
                        boolean z = this.j;
                        try {
                            CompletableSource completableSource = (CompletableSource) this.h.poll();
                            boolean z2 = completableSource == null;
                            if (z && z2) {
                                if (this.e.compareAndSet(false, true)) {
                                    this.a.onComplete();
                                }
                                return;
                            } else if (!z2) {
                                this.k = true;
                                completableSource.subscribe(this.d);
                                b();
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            a(th);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (this.f != 1) {
                int i2 = this.g + 1;
                if (i2 == this.c) {
                    this.g = 0;
                    this.i.request((long) i2);
                    return;
                }
                this.g = i2;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (this.e.compareAndSet(false, true)) {
                this.i.cancel();
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            this.k = false;
            a();
        }
    }

    public CompletableConcat(Publisher<? extends CompletableSource> publisher, int i) {
        this.a = publisher;
        this.b = i;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableConcatSubscriber(completableObserver, this.b));
    }
}
