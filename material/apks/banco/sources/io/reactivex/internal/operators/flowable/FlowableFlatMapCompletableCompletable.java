package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapCompletableCompletable<T> extends Completable implements FuseToFlowable<T> {
    final Flowable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final int c;
    final boolean d;

    static final class FlatMapCompletableMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        private static final long serialVersionUID = 8443155186132538303L;
        final CompletableObserver a;
        final AtomicThrowable b = new AtomicThrowable();
        final Function<? super T, ? extends CompletableSource> c;
        final boolean d;
        final CompositeDisposable e = new CompositeDisposable();
        final int f;
        Subscription g;

        final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onComplete() {
                FlatMapCompletableMainSubscriber.this.a(this);
            }

            public void onError(Throwable th) {
                FlatMapCompletableMainSubscriber.this.a(this, th);
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }
        }

        FlatMapCompletableMainSubscriber(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z, int i) {
            this.a = completableObserver;
            this.c = function;
            this.d = z;
            this.f = i;
            lazySet(1);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.a.onSubscribe(this);
                int i = this.f;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request((long) i);
                }
            }
        }

        public void onNext(T t) {
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.c.apply(t), "The mapper returned a null CompletableSource");
                getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.e.add(innerObserver)) {
                    completableSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.g.cancel();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            if (!this.b.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.d) {
                dispose();
                if (getAndSet(0) > 0) {
                    this.a.onError(this.b.terminate());
                }
            } else if (decrementAndGet() == 0) {
                this.a.onError(this.b.terminate());
            } else if (this.f != Integer.MAX_VALUE) {
                this.g.request(1);
            }
        }

        public void onComplete() {
            if (decrementAndGet() == 0) {
                Throwable terminate = this.b.terminate();
                if (terminate != null) {
                    this.a.onError(terminate);
                } else {
                    this.a.onComplete();
                }
            } else if (this.f != Integer.MAX_VALUE) {
                this.g.request(1);
            }
        }

        public void dispose() {
            this.g.cancel();
            this.e.dispose();
        }

        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver) {
            this.e.delete(innerObserver);
            onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void a(InnerObserver innerObserver, Throwable th) {
            this.e.delete(innerObserver);
            onError(th);
        }
    }

    public FlowableFlatMapCompletableCompletable(Flowable<T> flowable, Function<? super T, ? extends CompletableSource> function, boolean z, int i) {
        this.a = flowable;
        this.b = function;
        this.d = z;
        this.c = i;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe((FlowableSubscriber<? super T>) new FlatMapCompletableMainSubscriber<Object>(completableObserver, this.b, this.d, this.c));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableFlatMapCompletable<T>(this.a, this.b, this.d, this.c));
    }
}
