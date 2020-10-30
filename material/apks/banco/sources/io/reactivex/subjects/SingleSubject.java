package io.reactivex.subjects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubject<T> extends Single<T> implements SingleObserver<T> {
    static final SingleDisposable[] b = new SingleDisposable[0];
    static final SingleDisposable[] c = new SingleDisposable[0];
    final AtomicReference<SingleDisposable<T>[]> a = new AtomicReference<>(b);
    final AtomicBoolean d = new AtomicBoolean();
    T e;
    Throwable f;

    static final class SingleDisposable<T> extends AtomicReference<SingleSubject<T>> implements Disposable {
        private static final long serialVersionUID = -7650903191002190468L;
        final SingleObserver<? super T> a;

        SingleDisposable(SingleObserver<? super T> singleObserver, SingleSubject<T> singleSubject) {
            this.a = singleObserver;
            lazySet(singleSubject);
        }

        public void dispose() {
            SingleSubject singleSubject = (SingleSubject) getAndSet(null);
            if (singleSubject != null) {
                singleSubject.b(this);
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> SingleSubject<T> create() {
        return new SingleSubject<>();
    }

    SingleSubject() {
    }

    public void onSubscribe(@NonNull Disposable disposable) {
        if (this.a.get() == c) {
            disposable.dispose();
        }
    }

    public void onSuccess(@NonNull T t) {
        if (t == null) {
            onError(new NullPointerException("Null values are not allowed in 2.x"));
            return;
        }
        if (this.d.compareAndSet(false, true)) {
            this.e = t;
            for (SingleDisposable singleDisposable : (SingleDisposable[]) this.a.getAndSet(c)) {
                singleDisposable.a.onSuccess(t);
            }
        }
    }

    public void onError(@NonNull Throwable th) {
        if (th == null) {
            th = new NullPointerException("Null errors are not allowed in 2.x");
        }
        if (this.d.compareAndSet(false, true)) {
            this.f = th;
            for (SingleDisposable singleDisposable : (SingleDisposable[]) this.a.getAndSet(c)) {
                singleDisposable.a.onError(th);
            }
            return;
        }
        RxJavaPlugins.onError(th);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(@NonNull SingleObserver<? super T> singleObserver) {
        SingleDisposable singleDisposable = new SingleDisposable(singleObserver, this);
        singleObserver.onSubscribe(singleDisposable);
        if (!a(singleDisposable)) {
            Throwable th = this.f;
            if (th != null) {
                singleObserver.onError(th);
            } else {
                singleObserver.onSuccess(this.e);
            }
        } else if (singleDisposable.isDisposed()) {
            b(singleDisposable);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(@NonNull SingleDisposable<T> singleDisposable) {
        SingleDisposable[] singleDisposableArr;
        SingleDisposable[] singleDisposableArr2;
        do {
            singleDisposableArr = (SingleDisposable[]) this.a.get();
            if (singleDisposableArr == c) {
                return false;
            }
            int length = singleDisposableArr.length;
            singleDisposableArr2 = new SingleDisposable[(length + 1)];
            System.arraycopy(singleDisposableArr, 0, singleDisposableArr2, 0, length);
            singleDisposableArr2[length] = singleDisposable;
        } while (!this.a.compareAndSet(singleDisposableArr, singleDisposableArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(@NonNull SingleDisposable<T> singleDisposable) {
        SingleDisposable<T>[] singleDisposableArr;
        SingleDisposable[] singleDisposableArr2;
        do {
            singleDisposableArr = (SingleDisposable[]) this.a.get();
            int length = singleDisposableArr.length;
            if (length != 0) {
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (singleDisposableArr[i2] == singleDisposable) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        singleDisposableArr2 = b;
                    } else {
                        SingleDisposable[] singleDisposableArr3 = new SingleDisposable[(length - 1)];
                        System.arraycopy(singleDisposableArr, 0, singleDisposableArr3, 0, i);
                        System.arraycopy(singleDisposableArr, i + 1, singleDisposableArr3, i, (length - i) - 1);
                        singleDisposableArr2 = singleDisposableArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.a.compareAndSet(singleDisposableArr, singleDisposableArr2));
    }

    @Nullable
    public T getValue() {
        if (this.a.get() == c) {
            return this.e;
        }
        return null;
    }

    public boolean hasValue() {
        return this.a.get() == c && this.e != null;
    }

    @Nullable
    public Throwable getThrowable() {
        if (this.a.get() == c) {
            return this.f;
        }
        return null;
    }

    public boolean hasThrowable() {
        return this.a.get() == c && this.f != null;
    }

    public boolean hasObservers() {
        return ((SingleDisposable[]) this.a.get()).length != 0;
    }
}
