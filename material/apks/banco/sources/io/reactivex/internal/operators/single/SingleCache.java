package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCache<T> extends Single<T> implements SingleObserver<T> {
    static final CacheDisposable[] a = new CacheDisposable[0];
    static final CacheDisposable[] b = new CacheDisposable[0];
    final SingleSource<? extends T> c;
    final AtomicInteger d = new AtomicInteger();
    final AtomicReference<CacheDisposable<T>[]> e = new AtomicReference<>(a);
    T f;
    Throwable g;

    static final class CacheDisposable<T> extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 7514387411091976596L;
        final SingleObserver<? super T> a;
        final SingleCache<T> b;

        CacheDisposable(SingleObserver<? super T> singleObserver, SingleCache<T> singleCache) {
            this.a = singleObserver;
            this.b = singleCache;
        }

        public boolean isDisposed() {
            return get();
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                this.b.b(this);
            }
        }
    }

    public void onSubscribe(Disposable disposable) {
    }

    public SingleCache(SingleSource<? extends T> singleSource) {
        this.c = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        CacheDisposable cacheDisposable = new CacheDisposable(singleObserver, this);
        singleObserver.onSubscribe(cacheDisposable);
        if (a(cacheDisposable)) {
            if (cacheDisposable.isDisposed()) {
                b(cacheDisposable);
            }
            if (this.d.getAndIncrement() == 0) {
                this.c.subscribe(this);
            }
            return;
        }
        Throwable th = this.g;
        if (th != null) {
            singleObserver.onError(th);
        } else {
            singleObserver.onSuccess(this.f);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(CacheDisposable<T> cacheDisposable) {
        CacheDisposable[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.e.get();
            if (cacheDisposableArr == b) {
                return false;
            }
            int length = cacheDisposableArr.length;
            cacheDisposableArr2 = new CacheDisposable[(length + 1)];
            System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr2, 0, length);
            cacheDisposableArr2[length] = cacheDisposable;
        } while (!this.e.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(CacheDisposable<T> cacheDisposable) {
        CacheDisposable<T>[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.e.get();
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
        } while (!this.e.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
    }

    public void onSuccess(T t) {
        CacheDisposable[] cacheDisposableArr;
        this.f = t;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.e.getAndSet(b)) {
            if (!cacheDisposable.isDisposed()) {
                cacheDisposable.a.onSuccess(t);
            }
        }
    }

    public void onError(Throwable th) {
        CacheDisposable[] cacheDisposableArr;
        this.g = th;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.e.getAndSet(b)) {
            if (!cacheDisposable.isDisposed()) {
                cacheDisposable.a.onError(th);
            }
        }
    }
}
