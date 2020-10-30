package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCombineLatest<T, R> extends Observable<R> {
    final ObservableSource<? extends T>[] a;
    final Iterable<? extends ObservableSource<? extends T>> b;
    final Function<? super Object[], ? extends R> c;
    final int d;
    final boolean e;

    static final class CombinerObserver<T, R> implements Observer<T> {
        final LatestCoordinator<T, R> a;
        final int b;
        final AtomicReference<Disposable> c = new AtomicReference<>();

        CombinerObserver(LatestCoordinator<T, R> latestCoordinator, int i) {
            this.a = latestCoordinator;
            this.b = i;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.c, disposable);
        }

        public void onNext(T t) {
            this.a.a(t, this.b);
        }

        public void onError(Throwable th) {
            this.a.a(th);
            this.a.a(null, this.b);
        }

        public void onComplete() {
            this.a.a(null, this.b);
        }

        public void a() {
            DisposableHelper.dispose(this.c);
        }
    }

    static final class LatestCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 8567835998786448817L;
        final Observer<? super R> a;
        final Function<? super Object[], ? extends R> b;
        final CombinerObserver<T, R>[] c;
        final T[] d;
        final SpscLinkedArrayQueue<Object> e;
        final boolean f;
        volatile boolean g;
        volatile boolean h;
        final AtomicThrowable i = new AtomicThrowable();
        int j;
        int k;

        LatestCoordinator(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i2, int i3, boolean z) {
            this.a = observer;
            this.b = function;
            this.f = z;
            this.d = (Object[]) new Object[i2];
            this.c = new CombinerObserver[i2];
            this.e = new SpscLinkedArrayQueue<>(i3);
        }

        public void a(ObservableSource<? extends T>[] observableSourceArr) {
            CombinerObserver<T, R>[] combinerObserverArr = this.c;
            int length = combinerObserverArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                combinerObserverArr[i2] = new CombinerObserver<>(this, i2);
            }
            lazySet(0);
            this.a.onSubscribe(this);
            for (int i3 = 0; i3 < length && !this.h && !this.g; i3++) {
                observableSourceArr[i3].subscribe(combinerObserverArr[i3]);
            }
        }

        public void dispose() {
            if (!this.g) {
                this.g = true;
                a();
                if (getAndIncrement() == 0) {
                    b(this.e);
                }
            }
        }

        public boolean isDisposed() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void a(SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            b(spscLinkedArrayQueue);
            a();
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            for (CombinerObserver<T, R> a2 : this.c) {
                a2.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            synchronized (this) {
                Arrays.fill(this.d, null);
            }
            spscLinkedArrayQueue.clear();
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0057, code lost:
            if (r3 != false) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0059, code lost:
            if (r7 == null) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x005b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x005c, code lost:
            b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x005f, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(T r7, int r8) {
            /*
                r6 = this;
                io.reactivex.internal.operators.observable.ObservableCombineLatest$CombinerObserver<T, R>[] r0 = r6.c
                r0 = r0[r8]
                monitor-enter(r6)
                boolean r1 = r6.g     // Catch:{ all -> 0x0060 }
                if (r1 == 0) goto L_0x000b
                monitor-exit(r6)     // Catch:{ all -> 0x0060 }
                return
            L_0x000b:
                T[] r1 = r6.d     // Catch:{ all -> 0x0060 }
                int r1 = r1.length     // Catch:{ all -> 0x0060 }
                T[] r2 = r6.d     // Catch:{ all -> 0x0060 }
                r2 = r2[r8]     // Catch:{ all -> 0x0060 }
                int r3 = r6.j     // Catch:{ all -> 0x0060 }
                if (r2 != 0) goto L_0x001a
                int r3 = r3 + 1
                r6.j = r3     // Catch:{ all -> 0x0060 }
            L_0x001a:
                int r4 = r6.k     // Catch:{ all -> 0x0060 }
                if (r7 != 0) goto L_0x0023
                int r4 = r4 + 1
                r6.k = r4     // Catch:{ all -> 0x0060 }
                goto L_0x0027
            L_0x0023:
                T[] r5 = r6.d     // Catch:{ all -> 0x0060 }
                r5[r8] = r7     // Catch:{ all -> 0x0060 }
            L_0x0027:
                r8 = 0
                r5 = 1
                if (r3 != r1) goto L_0x002d
                r3 = 1
                goto L_0x002e
            L_0x002d:
                r3 = 0
            L_0x002e:
                if (r4 == r1) goto L_0x0034
                if (r7 != 0) goto L_0x0035
                if (r2 != 0) goto L_0x0035
            L_0x0034:
                r8 = 1
            L_0x0035:
                if (r8 != 0) goto L_0x0054
                if (r7 == 0) goto L_0x0047
                if (r3 == 0) goto L_0x0047
                io.reactivex.internal.queue.SpscLinkedArrayQueue<java.lang.Object> r8 = r6.e     // Catch:{ all -> 0x0060 }
                T[] r1 = r6.d     // Catch:{ all -> 0x0060 }
                java.lang.Object r1 = r1.clone()     // Catch:{ all -> 0x0060 }
                r8.offer(r0, r1)     // Catch:{ all -> 0x0060 }
                goto L_0x0056
            L_0x0047:
                if (r7 != 0) goto L_0x0056
                io.reactivex.internal.util.AtomicThrowable r8 = r6.i     // Catch:{ all -> 0x0060 }
                java.lang.Object r8 = r8.get()     // Catch:{ all -> 0x0060 }
                if (r8 == 0) goto L_0x0056
                r6.h = r5     // Catch:{ all -> 0x0060 }
                goto L_0x0056
            L_0x0054:
                r6.h = r5     // Catch:{ all -> 0x0060 }
            L_0x0056:
                monitor-exit(r6)     // Catch:{ all -> 0x0060 }
                if (r3 != 0) goto L_0x005c
                if (r7 == 0) goto L_0x005c
                return
            L_0x005c:
                r6.b()
                return
            L_0x0060:
                r7 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0060 }
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableCombineLatest.LatestCoordinator.a(java.lang.Object, int):void");
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.e;
                Observer<? super R> observer = this.a;
                boolean z = this.f;
                int i2 = 1;
                do {
                    if (!a(this.h, spscLinkedArrayQueue.isEmpty(), observer, spscLinkedArrayQueue, z)) {
                        while (true) {
                            boolean z2 = this.h;
                            boolean z3 = ((CombinerObserver) spscLinkedArrayQueue.poll()) == null;
                            if (!a(z2, z3, observer, spscLinkedArrayQueue, z)) {
                                if (z3) {
                                    i2 = addAndGet(-i2);
                                } else {
                                    try {
                                        observer.onNext(ObjectHelper.requireNonNull(this.b.apply((Object[]) spscLinkedArrayQueue.poll()), "The combiner returned a null"));
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.g = true;
                                        a(spscLinkedArrayQueue);
                                        observer.onError(th);
                                        return;
                                    }
                                }
                            } else {
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                } while (i2 != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z, boolean z2, Observer<?> observer, SpscLinkedArrayQueue<?> spscLinkedArrayQueue, boolean z3) {
            if (this.g) {
                a(spscLinkedArrayQueue);
                return true;
            }
            if (z) {
                if (z3) {
                    if (z2) {
                        a(spscLinkedArrayQueue);
                        Throwable terminate = this.i.terminate();
                        if (terminate != null) {
                            observer.onError(terminate);
                        } else {
                            observer.onComplete();
                        }
                        return true;
                    }
                } else if (((Throwable) this.i.get()) != null) {
                    a(spscLinkedArrayQueue);
                    observer.onError(this.i.terminate());
                    return true;
                } else if (z2) {
                    b(this.e);
                    observer.onComplete();
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (!this.i.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public ObservableCombineLatest(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i, boolean z) {
        this.a = observableSourceArr;
        this.b = iterable;
        this.c = function;
        this.d = i;
        this.e = z;
    }

    public void subscribeActual(Observer<? super R> observer) {
        int i;
        ObservableSource<? extends T>[] observableSourceArr = this.a;
        if (observableSourceArr == null) {
            Observable[] observableArr = new Observable[8];
            ObservableSource<? extends T>[] observableSourceArr2 = observableArr;
            int i2 = 0;
            for (ObservableSource<? extends T> observableSource : this.b) {
                if (i2 == observableSourceArr2.length) {
                    ObservableSource<? extends T>[] observableSourceArr3 = new ObservableSource[((i2 >> 2) + i2)];
                    System.arraycopy(observableSourceArr2, 0, observableSourceArr3, 0, i2);
                    observableSourceArr2 = observableSourceArr3;
                }
                int i3 = i2 + 1;
                observableSourceArr2[i2] = observableSource;
                i2 = i3;
            }
            i = i2;
            observableSourceArr = observableSourceArr2;
        } else {
            i = observableSourceArr.length;
        }
        if (i == 0) {
            EmptyDisposable.complete(observer);
            return;
        }
        LatestCoordinator latestCoordinator = new LatestCoordinator(observer, this.c, i, this.d, this.e);
        latestCoordinator.a(observableSourceArr);
    }
}
