package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class SingleEquals<T> extends Single<Boolean> {
    final SingleSource<? extends T> a;
    final SingleSource<? extends T> b;

    static class InnerObserver<T> implements SingleObserver<T> {
        final int a;
        final CompositeDisposable b;
        final Object[] c;
        final SingleObserver<? super Boolean> d;
        final AtomicInteger e;

        InnerObserver(int i, CompositeDisposable compositeDisposable, Object[] objArr, SingleObserver<? super Boolean> singleObserver, AtomicInteger atomicInteger) {
            this.a = i;
            this.b = compositeDisposable;
            this.c = objArr;
            this.d = singleObserver;
            this.e = atomicInteger;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.add(disposable);
        }

        public void onSuccess(T t) {
            this.c[this.a] = t;
            if (this.e.incrementAndGet() == 2) {
                this.d.onSuccess(Boolean.valueOf(ObjectHelper.equals(this.c[0], this.c[1])));
            }
        }

        public void onError(Throwable th) {
            int i;
            do {
                i = this.e.get();
                if (i >= 2) {
                    RxJavaPlugins.onError(th);
                    return;
                }
            } while (!this.e.compareAndSet(i, 2));
            this.b.dispose();
            this.d.onError(th);
        }
    }

    public SingleEquals(SingleSource<? extends T> singleSource, SingleSource<? extends T> singleSource2) {
        this.a = singleSource;
        this.b = singleSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        AtomicInteger atomicInteger = new AtomicInteger();
        Object[] objArr = {null, null};
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        singleObserver.onSubscribe(compositeDisposable);
        SingleSource<? extends T> singleSource = this.a;
        CompositeDisposable compositeDisposable2 = compositeDisposable;
        Object[] objArr2 = objArr;
        SingleObserver<? super Boolean> singleObserver2 = singleObserver;
        AtomicInteger atomicInteger2 = atomicInteger;
        InnerObserver innerObserver = new InnerObserver(0, compositeDisposable2, objArr2, singleObserver2, atomicInteger2);
        singleSource.subscribe(innerObserver);
        SingleSource<? extends T> singleSource2 = this.b;
        InnerObserver innerObserver2 = new InnerObserver(1, compositeDisposable2, objArr2, singleObserver2, atomicInteger2);
        singleSource2.subscribe(innerObserver2);
    }
}
