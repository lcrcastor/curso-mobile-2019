package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableMerge extends Completable {
    final Publisher<? extends CompletableSource> a;
    final int b;
    final boolean c;

    static final class CompletableMergeSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = -2108443387387077490L;
        final CompletableObserver a;
        final int b;
        final boolean c;
        final AtomicThrowable d = new AtomicThrowable();
        final CompositeDisposable e = new CompositeDisposable();
        Subscription f;

        final class MergeInnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 251330541679988317L;

            MergeInnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onError(Throwable th) {
                CompletableMergeSubscriber.this.a(this, th);
            }

            public void onComplete() {
                CompletableMergeSubscriber.this.a(this);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }

        CompletableMergeSubscriber(CompletableObserver completableObserver, int i, boolean z) {
            this.a = completableObserver;
            this.b = i;
            this.c = z;
            lazySet(1);
        }

        public void dispose() {
            this.f.cancel();
            this.e.dispose();
        }

        public boolean isDisposed() {
            return this.e.isDisposed();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f, subscription)) {
                this.f = subscription;
                this.a.onSubscribe(this);
                if (this.b == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request((long) this.b);
                }
            }
        }

        /* renamed from: a */
        public void onNext(CompletableSource completableSource) {
            getAndIncrement();
            MergeInnerObserver mergeInnerObserver = new MergeInnerObserver();
            this.e.add(mergeInnerObserver);
            completableSource.subscribe(mergeInnerObserver);
        }

        public void onError(Throwable th) {
            if (!this.c) {
                this.e.dispose();
                if (!this.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                } else if (getAndSet(0) > 0) {
                    this.a.onError(this.d.terminate());
                }
            } else if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (decrementAndGet() == 0) {
                this.a.onError(this.d.terminate());
            }
        }

        public void onComplete() {
            if (decrementAndGet() != 0) {
                return;
            }
            if (((Throwable) this.d.get()) != null) {
                this.a.onError(this.d.terminate());
            } else {
                this.a.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(MergeInnerObserver mergeInnerObserver, Throwable th) {
            this.e.delete(mergeInnerObserver);
            if (!this.c) {
                this.f.cancel();
                this.e.dispose();
                if (!this.d.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                } else if (getAndSet(0) > 0) {
                    this.a.onError(this.d.terminate());
                }
            } else if (!this.d.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (decrementAndGet() == 0) {
                this.a.onError(this.d.terminate());
            } else if (this.b != Integer.MAX_VALUE) {
                this.f.request(1);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(MergeInnerObserver mergeInnerObserver) {
            this.e.delete(mergeInnerObserver);
            if (decrementAndGet() == 0) {
                Throwable th = (Throwable) this.d.get();
                if (th != null) {
                    this.a.onError(th);
                } else {
                    this.a.onComplete();
                }
            } else if (this.b != Integer.MAX_VALUE) {
                this.f.request(1);
            }
        }
    }

    public CompletableMerge(Publisher<? extends CompletableSource> publisher, int i, boolean z) {
        this.a = publisher;
        this.b = i;
        this.c = z;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableMergeSubscriber(completableObserver, this.b, this.c));
    }
}
