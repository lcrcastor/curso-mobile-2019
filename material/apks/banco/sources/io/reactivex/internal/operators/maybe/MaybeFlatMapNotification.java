package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatMapNotification<T, R> extends AbstractMaybeWithUpstream<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> a;
    final Function<? super Throwable, ? extends MaybeSource<? extends R>> b;
    final Callable<? extends MaybeSource<? extends R>> c;

    static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4375739915521278546L;
        final MaybeObserver<? super R> a;
        final Function<? super T, ? extends MaybeSource<? extends R>> b;
        final Function<? super Throwable, ? extends MaybeSource<? extends R>> c;
        final Callable<? extends MaybeSource<? extends R>> d;
        Disposable e;

        final class InnerObserver implements MaybeObserver<R> {
            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(FlatMapMaybeObserver.this, disposable);
            }

            public void onSuccess(R r) {
                FlatMapMaybeObserver.this.a.onSuccess(r);
            }

            public void onError(Throwable th) {
                FlatMapMaybeObserver.this.a.onError(th);
            }

            public void onComplete() {
                FlatMapMaybeObserver.this.a.onComplete();
            }
        }

        FlatMapMaybeObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
            this.a = maybeObserver;
            this.b = function;
            this.c = function2;
            this.d = callable;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            this.e.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.b.apply(t), "The onSuccessMapper returned a null MaybeSource")).subscribe(new InnerObserver());
            } catch (Exception e2) {
                Exceptions.throwIfFatal(e2);
                this.a.onError(e2);
            }
        }

        public void onError(Throwable th) {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.c.apply(th), "The onErrorMapper returned a null MaybeSource")).subscribe(new InnerObserver());
            } catch (Exception e2) {
                Exceptions.throwIfFatal(e2);
                this.a.onError(new CompositeException(th, e2));
            }
        }

        public void onComplete() {
            try {
                ((MaybeSource) ObjectHelper.requireNonNull(this.d.call(), "The onCompleteSupplier returned a null MaybeSource")).subscribe(new InnerObserver());
            } catch (Exception e2) {
                Exceptions.throwIfFatal(e2);
                this.a.onError(e2);
            }
        }
    }

    public MaybeFlatMapNotification(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends R>> function, Function<? super Throwable, ? extends MaybeSource<? extends R>> function2, Callable<? extends MaybeSource<? extends R>> callable) {
        super(maybeSource);
        this.a = function;
        this.b = function2;
        this.c = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new FlatMapMaybeObserver(maybeObserver, this.a, this.b, this.c));
    }
}
