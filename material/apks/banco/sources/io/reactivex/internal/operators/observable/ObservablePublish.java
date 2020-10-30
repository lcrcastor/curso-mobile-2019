package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublish<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T> {
    final ObservableSource<T> a;
    final AtomicReference<PublishObserver<T>> b;
    final ObservableSource<T> c;

    static final class InnerDisposable<T> extends AtomicReference<Object> implements Disposable {
        private static final long serialVersionUID = -1100270633763673112L;
        final Observer<? super T> a;

        InnerDisposable(Observer<? super T> observer) {
            this.a = observer;
        }

        public boolean isDisposed() {
            return get() == this;
        }

        public void dispose() {
            Object andSet = getAndSet(this);
            if (andSet != null && andSet != this) {
                ((PublishObserver) andSet).b(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(PublishObserver<T> publishObserver) {
            if (!compareAndSet(null, publishObserver)) {
                publishObserver.b(this);
            }
        }
    }

    static final class PublishObserver<T> implements Observer<T>, Disposable {
        static final InnerDisposable[] b = new InnerDisposable[0];
        static final InnerDisposable[] c = new InnerDisposable[0];
        final AtomicReference<PublishObserver<T>> a;
        final AtomicReference<InnerDisposable<T>[]> d = new AtomicReference<>(b);
        final AtomicBoolean e;
        final AtomicReference<Disposable> f = new AtomicReference<>();

        PublishObserver(AtomicReference<PublishObserver<T>> atomicReference) {
            this.a = atomicReference;
            this.e = new AtomicBoolean();
        }

        public void dispose() {
            if (this.d.get() != c && ((InnerDisposable[]) this.d.getAndSet(c)) != c) {
                this.a.compareAndSet(this, null);
                DisposableHelper.dispose(this.f);
            }
        }

        public boolean isDisposed() {
            return this.d.get() == c;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.f, disposable);
        }

        public void onNext(T t) {
            for (InnerDisposable innerDisposable : (InnerDisposable[]) this.d.get()) {
                innerDisposable.a.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.a.compareAndSet(this, null);
            InnerDisposable[] innerDisposableArr = (InnerDisposable[]) this.d.getAndSet(c);
            if (innerDisposableArr.length != 0) {
                for (InnerDisposable innerDisposable : innerDisposableArr) {
                    innerDisposable.a.onError(th);
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.a.compareAndSet(this, null);
            for (InnerDisposable innerDisposable : (InnerDisposable[]) this.d.getAndSet(c)) {
                innerDisposable.a.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerDisposable<T> innerDisposable) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = (InnerDisposable[]) this.d.get();
                if (innerDisposableArr == c) {
                    return false;
                }
                int length = innerDisposableArr.length;
                innerDisposableArr2 = new InnerDisposable[(length + 1)];
                System.arraycopy(innerDisposableArr, 0, innerDisposableArr2, 0, length);
                innerDisposableArr2[length] = innerDisposable;
            } while (!this.d.compareAndSet(innerDisposableArr, innerDisposableArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerDisposable<T> innerDisposable) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = (InnerDisposable[]) this.d.get();
                int length = innerDisposableArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (innerDisposableArr[i2].equals(innerDisposable)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            innerDisposableArr2 = b;
                        } else {
                            InnerDisposable[] innerDisposableArr3 = new InnerDisposable[(length - 1)];
                            System.arraycopy(innerDisposableArr, 0, innerDisposableArr3, 0, i);
                            System.arraycopy(innerDisposableArr, i + 1, innerDisposableArr3, i, (length - i) - 1);
                            innerDisposableArr2 = innerDisposableArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.d.compareAndSet(innerDisposableArr, innerDisposableArr2));
        }
    }

    static final class PublishSource<T> implements ObservableSource<T> {
        private final AtomicReference<PublishObserver<T>> a;

        PublishSource(AtomicReference<PublishObserver<T>> atomicReference) {
            this.a = atomicReference;
        }

        public void subscribe(Observer<? super T> observer) {
            InnerDisposable innerDisposable = new InnerDisposable(observer);
            observer.onSubscribe(innerDisposable);
            while (true) {
                PublishObserver publishObserver = (PublishObserver) this.a.get();
                if (publishObserver == null || publishObserver.isDisposed()) {
                    PublishObserver publishObserver2 = new PublishObserver(this.a);
                    if (!this.a.compareAndSet(publishObserver, publishObserver2)) {
                        continue;
                    } else {
                        publishObserver = publishObserver2;
                    }
                }
                if (publishObserver.a(innerDisposable)) {
                    innerDisposable.a(publishObserver);
                    return;
                }
            }
        }
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable<T>) new ObservablePublish<T>(new PublishSource(atomicReference), observableSource, atomicReference));
    }

    private ObservablePublish(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<PublishObserver<T>> atomicReference) {
        this.c = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
    }

    public ObservableSource<T> source() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.c.subscribe(observer);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(io.reactivex.functions.Consumer<? super io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver<T>> r0 = r4.b
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver r0 = (io.reactivex.internal.operators.observable.ObservablePublish.PublishObserver) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0021
        L_0x0010:
            io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver r1 = new io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver<T>> r2 = r4.b
            r1.<init>(r2)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservablePublish$PublishObserver<T>> r2 = r4.b
            boolean r0 = r2.compareAndSet(r0, r1)
            if (r0 != 0) goto L_0x0020
            goto L_0x0000
        L_0x0020:
            r0 = r1
        L_0x0021:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.e
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0034
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.e
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            r2 = 0
        L_0x0035:
            r5.accept(r0)     // Catch:{ Throwable -> 0x0040 }
            if (r2 == 0) goto L_0x003f
            io.reactivex.ObservableSource<T> r5 = r4.a
            r5.subscribe(r0)
        L_0x003f:
            return
        L_0x0040:
            r5 = move-exception
            io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservablePublish.connect(io.reactivex.functions.Consumer):void");
    }
}
