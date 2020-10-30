package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeEqualSingle<T> extends Single<Boolean> {
    final MaybeSource<? extends T> a;
    final MaybeSource<? extends T> b;
    final BiPredicate<? super T, ? super T> c;

    static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        final SingleObserver<? super Boolean> a;
        final EqualObserver<T> b = new EqualObserver<>(this);
        final EqualObserver<T> c = new EqualObserver<>(this);
        final BiPredicate<? super T, ? super T> d;

        EqualCoordinator(SingleObserver<? super Boolean> singleObserver, BiPredicate<? super T, ? super T> biPredicate) {
            super(2);
            this.a = singleObserver;
            this.d = biPredicate;
        }

        /* access modifiers changed from: 0000 */
        public void a(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2) {
            maybeSource.subscribe(this.b);
            maybeSource2.subscribe(this.c);
        }

        public void dispose() {
            this.b.a();
            this.c.a();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.b.get());
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (decrementAndGet() == 0) {
                Object obj = this.b.b;
                Object obj2 = this.c.b;
                if (obj == null || obj2 == null) {
                    this.a.onSuccess(Boolean.valueOf(obj == null && obj2 == null));
                } else {
                    try {
                        this.a.onSuccess(Boolean.valueOf(this.d.test(obj, obj2)));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.a.onError(th);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(EqualObserver<T> equalObserver, Throwable th) {
            if (getAndSet(0) > 0) {
                if (equalObserver == this.b) {
                    this.c.a();
                } else {
                    this.b.a();
                }
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    static final class EqualObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = -3031974433025990931L;
        final EqualCoordinator<T> a;
        Object b;

        EqualObserver(EqualCoordinator<T> equalCoordinator) {
            this.a = equalCoordinator;
        }

        public void a() {
            DisposableHelper.dispose(this);
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.b = t;
            this.a.a();
        }

        public void onError(Throwable th) {
            this.a.a(this, th);
        }

        public void onComplete() {
            this.a.a();
        }
    }

    public MaybeEqualSingle(MaybeSource<? extends T> maybeSource, MaybeSource<? extends T> maybeSource2, BiPredicate<? super T, ? super T> biPredicate) {
        this.a = maybeSource;
        this.b = maybeSource2;
        this.c = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(singleObserver, this.c);
        singleObserver.onSubscribe(equalCoordinator);
        equalCoordinator.a(this.a, this.b);
    }
}
