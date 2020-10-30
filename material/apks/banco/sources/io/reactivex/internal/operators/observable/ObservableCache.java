package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.LinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCache<T> extends AbstractObservableWithUpstream<T, T> {
    final CacheState<T> a;
    final AtomicBoolean b = new AtomicBoolean();

    static final class CacheState<T> extends LinkedArrayList implements Observer<T> {
        static final ReplayDisposable[] d = new ReplayDisposable[0];
        static final ReplayDisposable[] e = new ReplayDisposable[0];
        final Observable<? extends T> a;
        final SequentialDisposable b = new SequentialDisposable();
        final AtomicReference<ReplayDisposable<T>[]> c = new AtomicReference<>(d);
        volatile boolean f;
        boolean g;

        CacheState(Observable<? extends T> observable, int i) {
            super(i);
            this.a = observable;
        }

        public boolean a(ReplayDisposable<T> replayDisposable) {
            ReplayDisposable[] replayDisposableArr;
            ReplayDisposable[] replayDisposableArr2;
            do {
                replayDisposableArr = (ReplayDisposable[]) this.c.get();
                if (replayDisposableArr == e) {
                    return false;
                }
                int length = replayDisposableArr.length;
                replayDisposableArr2 = new ReplayDisposable[(length + 1)];
                System.arraycopy(replayDisposableArr, 0, replayDisposableArr2, 0, length);
                replayDisposableArr2[length] = replayDisposable;
            } while (!this.c.compareAndSet(replayDisposableArr, replayDisposableArr2));
            return true;
        }

        public void b(ReplayDisposable<T> replayDisposable) {
            ReplayDisposable[] replayDisposableArr;
            ReplayDisposable[] replayDisposableArr2;
            do {
                replayDisposableArr = (ReplayDisposable[]) this.c.get();
                int length = replayDisposableArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (replayDisposableArr[i2].equals(replayDisposable)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            replayDisposableArr2 = d;
                        } else {
                            ReplayDisposable[] replayDisposableArr3 = new ReplayDisposable[(length - 1)];
                            System.arraycopy(replayDisposableArr, 0, replayDisposableArr3, 0, i);
                            System.arraycopy(replayDisposableArr, i + 1, replayDisposableArr3, i, (length - i) - 1);
                            replayDisposableArr2 = replayDisposableArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.c.compareAndSet(replayDisposableArr, replayDisposableArr2));
        }

        public void onSubscribe(Disposable disposable) {
            this.b.update(disposable);
        }

        public void a() {
            this.a.subscribe((Observer<? super T>) this);
            this.f = true;
        }

        public void onNext(T t) {
            if (!this.g) {
                add(NotificationLite.next(t));
                for (ReplayDisposable a2 : (ReplayDisposable[]) this.c.get()) {
                    a2.a();
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.g) {
                this.g = true;
                add(NotificationLite.error(th));
                this.b.dispose();
                for (ReplayDisposable a2 : (ReplayDisposable[]) this.c.getAndSet(e)) {
                    a2.a();
                }
            }
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                add(NotificationLite.complete());
                this.b.dispose();
                for (ReplayDisposable a2 : (ReplayDisposable[]) this.c.getAndSet(e)) {
                    a2.a();
                }
            }
        }
    }

    static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 7058506693698832024L;
        final Observer<? super T> a;
        final CacheState<T> b;
        Object[] c;
        int d;
        int e;
        volatile boolean f;

        ReplayDisposable(Observer<? super T> observer, CacheState<T> cacheState) {
            this.a = observer;
            this.b = cacheState;
        }

        public boolean isDisposed() {
            return this.f;
        }

        public void dispose() {
            if (!this.f) {
                this.f = true;
                this.b.b(this);
            }
        }

        public void a() {
            if (getAndIncrement() == 0) {
                Observer<? super T> observer = this.a;
                int i = 1;
                while (!this.f) {
                    int size = this.b.size();
                    if (size != 0) {
                        Object[] objArr = this.c;
                        if (objArr == null) {
                            objArr = this.b.head();
                            this.c = objArr;
                        }
                        int length = objArr.length - 1;
                        int i2 = this.e;
                        int i3 = this.d;
                        while (i2 < size) {
                            if (!this.f) {
                                if (i3 == length) {
                                    objArr = (Object[]) objArr[length];
                                    i3 = 0;
                                }
                                if (!NotificationLite.accept(objArr[i3], observer)) {
                                    i3++;
                                    i2++;
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                        if (!this.f) {
                            this.e = i2;
                            this.d = i3;
                            this.c = objArr;
                        } else {
                            return;
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    public static <T> Observable<T> from(Observable<T> observable) {
        return from(observable, 16);
    }

    public static <T> Observable<T> from(Observable<T> observable, int i) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableCache<T>(observable, new CacheState(observable, i)));
    }

    private ObservableCache(Observable<T> observable, CacheState<T> cacheState) {
        super(observable);
        this.a = cacheState;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        ReplayDisposable replayDisposable = new ReplayDisposable(observer, this.a);
        observer.onSubscribe(replayDisposable);
        this.a.a(replayDisposable);
        if (!this.b.get() && this.b.compareAndSet(false, true)) {
            this.a.a();
        }
        replayDisposable.a();
    }
}
