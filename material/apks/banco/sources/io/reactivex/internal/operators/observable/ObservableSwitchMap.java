package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSwitchMap<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends ObservableSource<? extends R>> a;
    final int b;
    final boolean c;

    static final class SwitchMapInnerObserver<T, R> extends AtomicReference<Disposable> implements Observer<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final SwitchMapObserver<T, R> a;
        final long b;
        final SpscLinkedArrayQueue<R> c;
        volatile boolean d;

        SwitchMapInnerObserver(SwitchMapObserver<T, R> switchMapObserver, long j, int i) {
            this.a = switchMapObserver;
            this.b = j;
            this.c = new SpscLinkedArrayQueue<>(i);
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onNext(R r) {
            if (this.b == this.a.k) {
                this.c.offer(r);
                this.a.b();
            }
        }

        public void onError(Throwable th) {
            this.a.a(this, th);
        }

        public void onComplete() {
            if (this.b == this.a.k) {
                this.d = true;
                this.a.b();
            }
        }

        public void a() {
            DisposableHelper.dispose(this);
        }
    }

    static final class SwitchMapObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final SwitchMapInnerObserver<Object, Object> j = new SwitchMapInnerObserver<>(null, -1, 1);
        private static final long serialVersionUID = -3491074160481096299L;
        final Observer<? super R> a;
        final Function<? super T, ? extends ObservableSource<? extends R>> b;
        final int c;
        final boolean d;
        final AtomicThrowable e;
        volatile boolean f;
        volatile boolean g;
        Disposable h;
        final AtomicReference<SwitchMapInnerObserver<T, R>> i = new AtomicReference<>();
        volatile long k;

        static {
            j.a();
        }

        SwitchMapObserver(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i2, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = i2;
            this.d = z;
            this.e = new AtomicThrowable();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j2 = this.k + 1;
            this.k = j2;
            SwitchMapInnerObserver switchMapInnerObserver = (SwitchMapInnerObserver) this.i.get();
            if (switchMapInnerObserver != null) {
                switchMapInnerObserver.a();
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The ObservableSource returned is null");
                SwitchMapInnerObserver switchMapInnerObserver2 = new SwitchMapInnerObserver(this, j2, this.c);
                while (true) {
                    SwitchMapInnerObserver<Object, Object> switchMapInnerObserver3 = (SwitchMapInnerObserver) this.i.get();
                    if (switchMapInnerObserver3 != j) {
                        if (this.i.compareAndSet(switchMapInnerObserver3, switchMapInnerObserver2)) {
                            observableSource.subscribe(switchMapInnerObserver2);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.h.dispose();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            if (this.f || !this.e.addThrowable(th)) {
                if (!this.d) {
                    a();
                }
                RxJavaPlugins.onError(th);
                return;
            }
            this.f = true;
            b();
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                b();
            }
        }

        public void dispose() {
            if (!this.g) {
                this.g = true;
                this.h.dispose();
                a();
            }
        }

        public boolean isDisposed() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (((SwitchMapInnerObserver) this.i.get()) != j) {
                SwitchMapInnerObserver<Object, Object> switchMapInnerObserver = (SwitchMapInnerObserver) this.i.getAndSet(j);
                if (switchMapInnerObserver != j && switchMapInnerObserver != null) {
                    switchMapInnerObserver.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x000b A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r10 = this;
                int r0 = r10.getAndIncrement()
                if (r0 == 0) goto L_0x0007
                return
            L_0x0007:
                io.reactivex.Observer<? super R> r0 = r10.a
                r1 = 1
                r2 = 1
            L_0x000b:
                boolean r3 = r10.g
                if (r3 == 0) goto L_0x0010
                return
            L_0x0010:
                boolean r3 = r10.f
                r4 = 0
                if (r3 == 0) goto L_0x0052
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r3 = r10.i
                java.lang.Object r3 = r3.get()
                if (r3 != 0) goto L_0x001f
                r3 = 1
                goto L_0x0020
            L_0x001f:
                r3 = 0
            L_0x0020:
                boolean r5 = r10.d
                if (r5 == 0) goto L_0x0038
                if (r3 == 0) goto L_0x0052
                io.reactivex.internal.util.AtomicThrowable r1 = r10.e
                java.lang.Object r1 = r1.get()
                java.lang.Throwable r1 = (java.lang.Throwable) r1
                if (r1 == 0) goto L_0x0034
                r0.onError(r1)
                goto L_0x0037
            L_0x0034:
                r0.onComplete()
            L_0x0037:
                return
            L_0x0038:
                io.reactivex.internal.util.AtomicThrowable r5 = r10.e
                java.lang.Object r5 = r5.get()
                java.lang.Throwable r5 = (java.lang.Throwable) r5
                if (r5 == 0) goto L_0x004c
                io.reactivex.internal.util.AtomicThrowable r1 = r10.e
                java.lang.Throwable r1 = r1.terminate()
                r0.onError(r1)
                return
            L_0x004c:
                if (r3 == 0) goto L_0x0052
                r0.onComplete()
                return
            L_0x0052:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r3 = r10.i
                java.lang.Object r3 = r3.get()
                io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver r3 = (io.reactivex.internal.operators.observable.ObservableSwitchMap.SwitchMapInnerObserver) r3
                if (r3 == 0) goto L_0x00d6
                io.reactivex.internal.queue.SpscLinkedArrayQueue<R> r5 = r3.c
                boolean r6 = r3.d
                r7 = 0
                if (r6 == 0) goto L_0x0090
                boolean r6 = r5.isEmpty()
                boolean r8 = r10.d
                if (r8 == 0) goto L_0x0073
                if (r6 == 0) goto L_0x0090
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r4 = r10.i
                r4.compareAndSet(r3, r7)
                goto L_0x000b
            L_0x0073:
                io.reactivex.internal.util.AtomicThrowable r8 = r10.e
                java.lang.Object r8 = r8.get()
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                if (r8 == 0) goto L_0x0087
                io.reactivex.internal.util.AtomicThrowable r1 = r10.e
                java.lang.Throwable r1 = r1.terminate()
                r0.onError(r1)
                return
            L_0x0087:
                if (r6 == 0) goto L_0x0090
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r4 = r10.i
                r4.compareAndSet(r3, r7)
                goto L_0x000b
            L_0x0090:
                boolean r6 = r10.g
                if (r6 == 0) goto L_0x0095
                return
            L_0x0095:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r6 = r10.i
                java.lang.Object r6 = r6.get()
                if (r3 == r6) goto L_0x009f
            L_0x009d:
                r4 = 1
                goto L_0x00ce
            L_0x009f:
                boolean r6 = r10.d
                if (r6 != 0) goto L_0x00b7
                io.reactivex.internal.util.AtomicThrowable r6 = r10.e
                java.lang.Object r6 = r6.get()
                java.lang.Throwable r6 = (java.lang.Throwable) r6
                if (r6 == 0) goto L_0x00b7
                io.reactivex.internal.util.AtomicThrowable r1 = r10.e
                java.lang.Throwable r1 = r1.terminate()
                r0.onError(r1)
                return
            L_0x00b7:
                boolean r6 = r3.d
                java.lang.Object r8 = r5.poll()
                if (r8 != 0) goto L_0x00c1
                r9 = 1
                goto L_0x00c2
            L_0x00c1:
                r9 = 0
            L_0x00c2:
                if (r6 == 0) goto L_0x00cc
                if (r9 == 0) goto L_0x00cc
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableSwitchMap$SwitchMapInnerObserver<T, R>> r4 = r10.i
                r4.compareAndSet(r3, r7)
                goto L_0x009d
            L_0x00cc:
                if (r9 == 0) goto L_0x00d2
            L_0x00ce:
                if (r4 == 0) goto L_0x00d6
                goto L_0x000b
            L_0x00d2:
                r0.onNext(r8)
                goto L_0x0090
            L_0x00d6:
                int r2 = -r2
                int r2 = r10.addAndGet(r2)
                if (r2 != 0) goto L_0x000b
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableSwitchMap.SwitchMapObserver.b():void");
        }

        /* access modifiers changed from: 0000 */
        public void a(SwitchMapInnerObserver<T, R> switchMapInnerObserver, Throwable th) {
            if (switchMapInnerObserver.b != this.k || !this.e.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.d) {
                this.h.dispose();
            }
            switchMapInnerObserver.d = true;
            b();
        }
    }

    public ObservableSwitchMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = i;
        this.c = z;
    }

    public void subscribeActual(Observer<? super R> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.a)) {
            this.source.subscribe(new SwitchMapObserver(observer, this.a, this.b, this.c));
        }
    }
}
