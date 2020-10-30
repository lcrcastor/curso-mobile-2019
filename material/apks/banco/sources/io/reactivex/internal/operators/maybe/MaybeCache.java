package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeCache<T> extends Maybe<T> implements MaybeObserver<T> {
    static final CacheDisposable[] a = new CacheDisposable[0];
    static final CacheDisposable[] b = new CacheDisposable[0];
    final AtomicReference<MaybeSource<T>> c;
    final AtomicReference<CacheDisposable<T>[]> d = new AtomicReference<>(a);
    T e;
    Throwable f;

    static final class CacheDisposable<T> extends AtomicReference<MaybeCache<T>> implements Disposable {
        private static final long serialVersionUID = -5791853038359966195L;
        final MaybeObserver<? super T> a;

        CacheDisposable(MaybeObserver<? super T> maybeObserver, MaybeCache<T> maybeCache) {
            super(maybeCache);
            this.a = maybeObserver;
        }

        public void dispose() {
            MaybeCache maybeCache = (MaybeCache) getAndSet(null);
            if (maybeCache != null) {
                maybeCache.b(this);
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }
    }

    public void onSubscribe(Disposable disposable) {
    }

    public MaybeCache(MaybeSource<T> maybeSource) {
        this.c = new AtomicReference<>(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        CacheDisposable cacheDisposable = new CacheDisposable(maybeObserver, this);
        maybeObserver.onSubscribe(cacheDisposable);
        if (!a(cacheDisposable)) {
            if (!cacheDisposable.isDisposed()) {
                Throwable th = this.f;
                if (th != null) {
                    maybeObserver.onError(th);
                } else {
                    T t = this.e;
                    if (t != null) {
                        maybeObserver.onSuccess(t);
                    } else {
                        maybeObserver.onComplete();
                    }
                }
            }
        } else if (cacheDisposable.isDisposed()) {
            b(cacheDisposable);
        } else {
            MaybeSource maybeSource = (MaybeSource) this.c.getAndSet(null);
            if (maybeSource != null) {
                maybeSource.subscribe(this);
            }
        }
    }

    public void onSuccess(T t) {
        CacheDisposable[] cacheDisposableArr;
        this.e = t;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.d.getAndSet(b)) {
            if (!cacheDisposable.isDisposed()) {
                cacheDisposable.a.onSuccess(t);
            }
        }
    }

    public void onError(Throwable th) {
        CacheDisposable[] cacheDisposableArr;
        this.f = th;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.d.getAndSet(b)) {
            if (!cacheDisposable.isDisposed()) {
                cacheDisposable.a.onError(th);
            }
        }
    }

    public void onComplete() {
        CacheDisposable[] cacheDisposableArr;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.d.getAndSet(b)) {
            if (!cacheDisposable.isDisposed()) {
                cacheDisposable.a.onComplete();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(CacheDisposable<T> cacheDisposable) {
        CacheDisposable[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.d.get();
            if (cacheDisposableArr == b) {
                return false;
            }
            int length = cacheDisposableArr.length;
            cacheDisposableArr2 = new CacheDisposable[(length + 1)];
            System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr2, 0, length);
            cacheDisposableArr2[length] = cacheDisposable;
        } while (!this.d.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(CacheDisposable<T> cacheDisposable) {
        CacheDisposable<T>[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.d.get();
            int length = cacheDisposableArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (cacheDisposableArr[i2] == cacheDisposable) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        cacheDisposableArr2 = a;
                    } else {
                        CacheDisposable[] cacheDisposableArr3 = new CacheDisposable[(length - 1)];
                        System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr3, 0, i);
                        System.arraycopy(cacheDisposableArr, i + 1, cacheDisposableArr3, i, (length - i) - 1);
                        cacheDisposableArr2 = cacheDisposableArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
    }
}
