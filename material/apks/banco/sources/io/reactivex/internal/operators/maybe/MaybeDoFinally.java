package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

@Experimental
public final class MaybeDoFinally<T> extends AbstractMaybeWithUpstream<T, T> {
    final Action a;

    static final class DoFinallyObserver<T> extends AtomicInteger implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4109457741734051389L;
        final MaybeObserver<? super T> a;
        final Action b;
        Disposable c;

        DoFinallyObserver(MaybeObserver<? super T> maybeObserver, Action action) {
            this.a = maybeObserver;
            this.b = action;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
            a();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        public void onComplete() {
            this.a.onComplete();
            a();
        }

        public void dispose() {
            this.c.dispose();
            a();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (compareAndSet(0, 1)) {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public MaybeDoFinally(MaybeSource<T> maybeSource, Action action) {
        super(maybeSource);
        this.a = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new DoFinallyObserver(maybeObserver, this.a));
    }
}
