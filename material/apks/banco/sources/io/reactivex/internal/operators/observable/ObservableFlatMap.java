package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends ObservableSource<? extends U>> a;
    final boolean b;
    final int c;
    final int d;

    static final class InnerObserver<T, U> extends AtomicReference<Disposable> implements Observer<U> {
        private static final long serialVersionUID = -4606175640614850599L;
        final long a;
        final MergeObserver<T, U> b;
        volatile boolean c;
        volatile SimpleQueue<U> d;
        int e;

        InnerObserver(MergeObserver<T, U> mergeObserver, long j) {
            this.a = j;
            this.b = mergeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable) && (disposable instanceof QueueDisposable)) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int requestFusion = queueDisposable.requestFusion(7);
                if (requestFusion == 1) {
                    this.e = requestFusion;
                    this.d = queueDisposable;
                    this.c = true;
                    this.b.a();
                } else if (requestFusion == 2) {
                    this.e = requestFusion;
                    this.d = queueDisposable;
                }
            }
        }

        public void onNext(U u) {
            if (this.e == 0) {
                this.b.a(u, this);
            } else {
                this.b.a();
            }
        }

        public void onError(Throwable th) {
            if (this.b.h.addThrowable(th)) {
                if (!this.b.c) {
                    this.b.d();
                }
                this.c = true;
                this.b.a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.c = true;
            this.b.a();
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }

    static final class MergeObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        static final InnerObserver<?, ?>[] k = new InnerObserver[0];
        static final InnerObserver<?, ?>[] l = new InnerObserver[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final Observer<? super U> a;
        final Function<? super T, ? extends ObservableSource<? extends U>> b;
        final boolean c;
        final int d;
        final int e;
        volatile SimplePlainQueue<U> f;
        volatile boolean g;
        final AtomicThrowable h = new AtomicThrowable();
        volatile boolean i;
        final AtomicReference<InnerObserver<?, ?>[]> j;
        Disposable m;
        long n;
        long o;
        int p;
        Queue<ObservableSource<? extends U>> q;
        int r;

        MergeObserver(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i2, int i3) {
            this.a = observer;
            this.b = function;
            this.c = z;
            this.d = i2;
            this.e = i3;
            if (i2 != Integer.MAX_VALUE) {
                this.q = new ArrayDeque(i2);
            }
            this.j = new AtomicReference<>(k);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.m, disposable)) {
                this.m = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.g) {
                try {
                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null ObservableSource");
                    if (this.d != Integer.MAX_VALUE) {
                        synchronized (this) {
                            if (this.r == this.d) {
                                this.q.offer(observableSource);
                                return;
                            }
                            this.r++;
                        }
                    }
                    a(observableSource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.m.dispose();
                    onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ObservableSource<? extends U> observableSource) {
            while (observableSource instanceof Callable) {
                a((Callable) observableSource);
                if (this.d != Integer.MAX_VALUE) {
                    synchronized (this) {
                        observableSource = (ObservableSource) this.q.poll();
                        if (observableSource == null) {
                            this.r--;
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
            long j2 = this.n;
            this.n = j2 + 1;
            InnerObserver innerObserver = new InnerObserver(this, j2);
            if (a(innerObserver)) {
                observableSource.subscribe(innerObserver);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerObserver<T, U> innerObserver) {
            InnerObserver<?, ?>[] innerObserverArr;
            InnerObserver[] innerObserverArr2;
            do {
                innerObserverArr = (InnerObserver[]) this.j.get();
                if (innerObserverArr == l) {
                    innerObserver.a();
                    return false;
                }
                int length = innerObserverArr.length;
                innerObserverArr2 = new InnerObserver[(length + 1)];
                System.arraycopy(innerObserverArr, 0, innerObserverArr2, 0, length);
                innerObserverArr2[length] = innerObserver;
            } while (!this.j.compareAndSet(innerObserverArr, innerObserverArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerObserver<T, U> innerObserver) {
            InnerObserver<T, U>[] innerObserverArr;
            Object obj;
            do {
                innerObserverArr = (InnerObserver[]) this.j.get();
                int length = innerObserverArr.length;
                if (length != 0) {
                    int i2 = -1;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        } else if (innerObserverArr[i3] == innerObserver) {
                            i2 = i3;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i2 >= 0) {
                        if (length == 1) {
                            obj = k;
                        } else {
                            InnerObserver[] innerObserverArr2 = new InnerObserver[(length - 1)];
                            System.arraycopy(innerObserverArr, 0, innerObserverArr2, 0, i2);
                            System.arraycopy(innerObserverArr, i2 + 1, innerObserverArr2, i2, (length - i2) - 1);
                            obj = innerObserverArr2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.j.compareAndSet(innerObserverArr, obj));
        }

        /* access modifiers changed from: 0000 */
        public void a(Callable<? extends U> callable) {
            try {
                Object call = callable.call();
                if (call != null) {
                    if (get() != 0 || !compareAndSet(0, 1)) {
                        SimplePlainQueue<U> simplePlainQueue = this.f;
                        if (simplePlainQueue == null) {
                            if (this.d == Integer.MAX_VALUE) {
                                simplePlainQueue = new SpscLinkedArrayQueue<>(this.e);
                            } else {
                                simplePlainQueue = new SpscArrayQueue<>(this.d);
                            }
                            this.f = simplePlainQueue;
                        }
                        if (!simplePlainQueue.offer(call)) {
                            onError(new IllegalStateException("Scalar queue full?!"));
                            return;
                        } else if (getAndIncrement() != 0) {
                            return;
                        }
                    } else {
                        this.a.onNext(call);
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    }
                    b();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.addThrowable(th);
                a();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(U u, InnerObserver<T, U> innerObserver) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue simpleQueue = innerObserver.d;
                if (simpleQueue == null) {
                    simpleQueue = new SpscLinkedArrayQueue(this.e);
                    innerObserver.d = simpleQueue;
                }
                simpleQueue.offer(u);
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                this.a.onNext(u);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            b();
        }

        public void onError(Throwable th) {
            if (this.g) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.h.addThrowable(th)) {
                this.g = true;
                a();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!this.g) {
                this.g = true;
                a();
            }
        }

        public void dispose() {
            if (!this.i) {
                this.i = true;
                if (d()) {
                    Throwable terminate = this.h.terminate();
                    if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
                        RxJavaPlugins.onError(terminate);
                    }
                }
            }
        }

        public boolean isDisposed() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (getAndIncrement() == 0) {
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a2, code lost:
            if (r11 != null) goto L_0x0090;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r13 = this;
                io.reactivex.Observer<? super U> r0 = r13.a
                r1 = 1
                r2 = 1
            L_0x0004:
                boolean r3 = r13.c()
                if (r3 == 0) goto L_0x000b
                return
            L_0x000b:
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r3 = r13.f
                if (r3 == 0) goto L_0x0023
            L_0x000f:
                boolean r4 = r13.c()
                if (r4 == 0) goto L_0x0016
                return
            L_0x0016:
                java.lang.Object r4 = r3.poll()
                if (r4 != 0) goto L_0x001f
                if (r4 != 0) goto L_0x000f
                goto L_0x0023
            L_0x001f:
                r0.onNext(r4)
                goto L_0x000f
            L_0x0023:
                boolean r3 = r13.g
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r4 = r13.f
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver<?, ?>[]> r5 = r13.j
                java.lang.Object r5 = r5.get()
                io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver[] r5 = (io.reactivex.internal.operators.observable.ObservableFlatMap.InnerObserver[]) r5
                int r6 = r5.length
                if (r3 == 0) goto L_0x0050
                if (r4 == 0) goto L_0x003a
                boolean r3 = r4.isEmpty()
                if (r3 == 0) goto L_0x0050
            L_0x003a:
                if (r6 != 0) goto L_0x0050
                io.reactivex.internal.util.AtomicThrowable r1 = r13.h
                java.lang.Throwable r1 = r1.terminate()
                java.lang.Throwable r2 = io.reactivex.internal.util.ExceptionHelper.TERMINATED
                if (r1 == r2) goto L_0x004f
                if (r1 != 0) goto L_0x004c
                r0.onComplete()
                goto L_0x004f
            L_0x004c:
                r0.onError(r1)
            L_0x004f:
                return
            L_0x0050:
                r3 = 0
                if (r6 == 0) goto L_0x00f1
                long r7 = r13.o
                int r4 = r13.p
                if (r6 <= r4) goto L_0x0061
                r9 = r5[r4]
                long r9 = r9.a
                int r11 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r11 == 0) goto L_0x0082
            L_0x0061:
                if (r6 > r4) goto L_0x0064
                r4 = 0
            L_0x0064:
                r9 = r4
                r4 = 0
            L_0x0066:
                if (r4 >= r6) goto L_0x0079
                r10 = r5[r9]
                long r10 = r10.a
                int r12 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
                if (r12 != 0) goto L_0x0071
                goto L_0x0079
            L_0x0071:
                int r9 = r9 + 1
                if (r9 != r6) goto L_0x0076
                r9 = 0
            L_0x0076:
                int r4 = r4 + 1
                goto L_0x0066
            L_0x0079:
                r13.p = r9
                r4 = r5[r9]
                long r7 = r4.a
                r13.o = r7
                r4 = r9
            L_0x0082:
                r7 = r4
                r4 = 0
                r8 = 0
            L_0x0085:
                if (r4 >= r6) goto L_0x00e8
                boolean r9 = r13.c()
                if (r9 == 0) goto L_0x008e
                return
            L_0x008e:
                r9 = r5[r7]
            L_0x0090:
                boolean r10 = r13.c()
                if (r10 == 0) goto L_0x0097
                return
            L_0x0097:
                io.reactivex.internal.fuseable.SimpleQueue<U> r10 = r9.d
                if (r10 != 0) goto L_0x009c
                goto L_0x00a4
            L_0x009c:
                java.lang.Object r11 = r10.poll()     // Catch:{ Throwable -> 0x00cd }
                if (r11 != 0) goto L_0x00c3
                if (r11 != 0) goto L_0x0090
            L_0x00a4:
                boolean r10 = r9.c
                io.reactivex.internal.fuseable.SimpleQueue<U> r11 = r9.d
                if (r10 == 0) goto L_0x00bd
                if (r11 == 0) goto L_0x00b2
                boolean r10 = r11.isEmpty()
                if (r10 == 0) goto L_0x00bd
            L_0x00b2:
                r13.b(r9)
                boolean r8 = r13.c()
                if (r8 == 0) goto L_0x00bc
                return
            L_0x00bc:
                r8 = 1
            L_0x00bd:
                int r7 = r7 + 1
                if (r7 != r6) goto L_0x00e6
                r7 = 0
                goto L_0x00e6
            L_0x00c3:
                r0.onNext(r11)
                boolean r11 = r13.c()
                if (r11 == 0) goto L_0x009c
                return
            L_0x00cd:
                r8 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r8)
                r9.a()
                io.reactivex.internal.util.AtomicThrowable r10 = r13.h
                r10.addThrowable(r8)
                boolean r8 = r13.c()
                if (r8 == 0) goto L_0x00e0
                return
            L_0x00e0:
                r13.b(r9)
                int r4 = r4 + 1
                r8 = 1
            L_0x00e6:
                int r4 = r4 + r1
                goto L_0x0085
            L_0x00e8:
                r13.p = r7
                r3 = r5[r7]
                long r3 = r3.a
                r13.o = r3
                r3 = r8
            L_0x00f1:
                if (r3 == 0) goto L_0x0116
                int r3 = r13.d
                r4 = 2147483647(0x7fffffff, float:NaN)
                if (r3 == r4) goto L_0x0004
                monitor-enter(r13)
                java.util.Queue<io.reactivex.ObservableSource<? extends U>> r3 = r13.q     // Catch:{ all -> 0x0113 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0113 }
                io.reactivex.ObservableSource r3 = (io.reactivex.ObservableSource) r3     // Catch:{ all -> 0x0113 }
                if (r3 != 0) goto L_0x010d
                int r3 = r13.r     // Catch:{ all -> 0x0113 }
                int r3 = r3 - r1
                r13.r = r3     // Catch:{ all -> 0x0113 }
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                goto L_0x0004
            L_0x010d:
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                r13.a(r3)
                goto L_0x0004
            L_0x0113:
                r0 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                throw r0
            L_0x0116:
                int r2 = -r2
                int r2 = r13.addAndGet(r2)
                if (r2 != 0) goto L_0x0004
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableFlatMap.MergeObserver.b():void");
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            if (this.i) {
                return true;
            }
            Throwable th = (Throwable) this.h.get();
            if (this.c || th == null) {
                return false;
            }
            d();
            Throwable terminate = this.h.terminate();
            if (terminate != ExceptionHelper.TERMINATED) {
                this.a.onError(terminate);
            }
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            this.m.dispose();
            if (((InnerObserver[]) this.j.get()) != l) {
                InnerObserver<?, ?>[] innerObserverArr = (InnerObserver[]) this.j.getAndSet(l);
                if (innerObserverArr != l) {
                    for (InnerObserver<?, ?> a2 : innerObserverArr) {
                        a2.a();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public ObservableFlatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
        super(observableSource);
        this.a = function;
        this.b = z;
        this.c = i;
        this.d = i2;
    }

    public void subscribeActual(Observer<? super U> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            ObservableSource observableSource = this.source;
            MergeObserver mergeObserver = new MergeObserver(observer, this.a, this.b, this.c, this.d);
            observableSource.subscribe(mergeObserver);
        }
    }
}
