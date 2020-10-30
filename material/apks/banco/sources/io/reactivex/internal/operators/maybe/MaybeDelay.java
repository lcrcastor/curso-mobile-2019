package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeDelay<T> extends AbstractMaybeWithUpstream<T, T> {
    final long a;
    final TimeUnit b;
    final Scheduler c;

    static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 5566860102500855068L;
        final MaybeObserver<? super T> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        T e;
        Throwable f;

        DelayMaybeObserver(MaybeObserver<? super T> maybeObserver, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = maybeObserver;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        public void run() {
            Throwable th = this.f;
            if (th != null) {
                this.a.onError(th);
                return;
            }
            T t = this.e;
            if (t != null) {
                this.a.onSuccess(t);
            } else {
                this.a.onComplete();
            }
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.e = t;
            a();
        }

        public void onError(Throwable th) {
            this.f = th;
            a();
        }

        public void onComplete() {
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            DisposableHelper.replace(this, this.d.scheduleDirect(this, this.b, this.c));
        }
    }

    public MaybeDelay(MaybeSource<T> maybeSource, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(maybeSource);
        this.a = j;
        this.b = timeUnit;
        this.c = scheduler;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        MaybeSource maybeSource = this.source;
        DelayMaybeObserver delayMaybeObserver = new DelayMaybeObserver(maybeObserver, this.a, this.b, this.c);
        maybeSource.subscribe(delayMaybeObserver);
    }
}
