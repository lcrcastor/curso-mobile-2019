package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Experimental
public final class CompletableCache extends Completable implements CompletableObserver {
    static final InnerCompletableCache[] a = new InnerCompletableCache[0];
    static final InnerCompletableCache[] b = new InnerCompletableCache[0];
    final CompletableSource c;
    final AtomicReference<InnerCompletableCache[]> d = new AtomicReference<>(a);
    final AtomicBoolean e = new AtomicBoolean();
    Throwable f;

    final class InnerCompletableCache extends AtomicBoolean implements Disposable {
        private static final long serialVersionUID = 8943152917179642732L;
        final CompletableObserver a;

        InnerCompletableCache(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        public boolean isDisposed() {
            return get();
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                CompletableCache.this.b(this);
            }
        }
    }

    public void onSubscribe(Disposable disposable) {
    }

    public CompletableCache(CompletableSource completableSource) {
        this.c = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        InnerCompletableCache innerCompletableCache = new InnerCompletableCache(completableObserver);
        completableObserver.onSubscribe(innerCompletableCache);
        if (a(innerCompletableCache)) {
            if (innerCompletableCache.isDisposed()) {
                b(innerCompletableCache);
            }
            if (this.e.compareAndSet(false, true)) {
                this.c.subscribe(this);
                return;
            }
            return;
        }
        Throwable th = this.f;
        if (th != null) {
            completableObserver.onError(th);
        } else {
            completableObserver.onComplete();
        }
    }

    public void onError(Throwable th) {
        InnerCompletableCache[] innerCompletableCacheArr;
        this.f = th;
        for (InnerCompletableCache innerCompletableCache : (InnerCompletableCache[]) this.d.getAndSet(b)) {
            if (!innerCompletableCache.get()) {
                innerCompletableCache.a.onError(th);
            }
        }
    }

    public void onComplete() {
        InnerCompletableCache[] innerCompletableCacheArr;
        for (InnerCompletableCache innerCompletableCache : (InnerCompletableCache[]) this.d.getAndSet(b)) {
            if (!innerCompletableCache.get()) {
                innerCompletableCache.a.onComplete();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(InnerCompletableCache innerCompletableCache) {
        InnerCompletableCache[] innerCompletableCacheArr;
        InnerCompletableCache[] innerCompletableCacheArr2;
        do {
            innerCompletableCacheArr = (InnerCompletableCache[]) this.d.get();
            if (innerCompletableCacheArr == b) {
                return false;
            }
            int length = innerCompletableCacheArr.length;
            innerCompletableCacheArr2 = new InnerCompletableCache[(length + 1)];
            System.arraycopy(innerCompletableCacheArr, 0, innerCompletableCacheArr2, 0, length);
            innerCompletableCacheArr2[length] = innerCompletableCache;
        } while (!this.d.compareAndSet(innerCompletableCacheArr, innerCompletableCacheArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(InnerCompletableCache innerCompletableCache) {
        InnerCompletableCache[] innerCompletableCacheArr;
        InnerCompletableCache[] innerCompletableCacheArr2;
        do {
            innerCompletableCacheArr = (InnerCompletableCache[]) this.d.get();
            int length = innerCompletableCacheArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (innerCompletableCacheArr[i2] == innerCompletableCache) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        innerCompletableCacheArr2 = a;
                    } else {
                        InnerCompletableCache[] innerCompletableCacheArr3 = new InnerCompletableCache[(length - 1)];
                        System.arraycopy(innerCompletableCacheArr, 0, innerCompletableCacheArr3, 0, i);
                        System.arraycopy(innerCompletableCacheArr, i + 1, innerCompletableCacheArr3, i, (length - i) - 1);
                        innerCompletableCacheArr2 = innerCompletableCacheArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(innerCompletableCacheArr, innerCompletableCacheArr2));
    }
}
