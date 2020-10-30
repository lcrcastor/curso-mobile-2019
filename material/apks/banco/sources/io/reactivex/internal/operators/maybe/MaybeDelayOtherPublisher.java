package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeDelayOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> a;

    static final class DelayMaybeObserver<T, U> implements MaybeObserver<T>, Disposable {
        final OtherSubscriber<T> a;
        final Publisher<U> b;
        Disposable c;

        DelayMaybeObserver(MaybeObserver<? super T> maybeObserver, Publisher<U> publisher) {
            this.a = new OtherSubscriber<>(maybeObserver);
            this.b = publisher;
        }

        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
            SubscriptionHelper.cancel(this.a);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) this.a.get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.c = DisposableHelper.DISPOSED;
            this.a.b = t;
            a();
        }

        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.a.c = th;
            a();
        }

        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b.subscribe(this.a);
        }
    }

    static final class OtherSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = -1215060610805418006L;
        final MaybeObserver<? super T> a;
        T b;
        Throwable c;

        OtherSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            Subscription subscription = (Subscription) get();
            if (subscription != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                subscription.cancel();
                onComplete();
            }
        }

        public void onError(Throwable th) {
            Throwable th2 = this.c;
            if (th2 == null) {
                this.a.onError(th);
                return;
            }
            this.a.onError(new CompositeException(th2, th));
        }

        public void onComplete() {
            Throwable th = this.c;
            if (th != null) {
                this.a.onError(th);
                return;
            }
            T t = this.b;
            if (t != null) {
                this.a.onSuccess(t);
            } else {
                this.a.onComplete();
            }
        }
    }

    public MaybeDelayOtherPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher) {
        super(maybeSource);
        this.a = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new DelayMaybeObserver(maybeObserver, this.a));
    }
}
