package io.reactivex.internal.operators.observable;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableReplay<T> extends ConnectableObservable<T> implements Disposable, HasUpstreamObservableSource<T> {
    static final BufferSupplier e = new UnBoundedFactory();
    final ObservableSource<T> a;
    final AtomicReference<ReplayObserver<T>> b;
    final BufferSupplier<T> c;
    final ObservableSource<T> d;

    static abstract class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        Node a;
        int b;

        /* access modifiers changed from: 0000 */
        public Object b(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public Object c(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public abstract void c();

        /* access modifiers changed from: 0000 */
        public void d() {
        }

        BoundedReplayBuffer() {
            Node node = new Node(null);
            this.a = node;
            set(node);
        }

        /* access modifiers changed from: 0000 */
        public final void a(Node node) {
            this.a.set(node);
            this.a = node;
            this.b++;
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            Node node = (Node) ((Node) get()).get();
            this.b--;
            b(node);
        }

        /* access modifiers changed from: 0000 */
        public final void b(Node node) {
            set(node);
        }

        public final void a(T t) {
            a(new Node(b(NotificationLite.next(t))));
            c();
        }

        public final void a(Throwable th) {
            a(new Node(b(NotificationLite.error(th))));
            d();
        }

        public final void b() {
            a(new Node(b(NotificationLite.complete())));
            d();
        }

        public final void a(InnerDisposable<T> innerDisposable) {
            if (innerDisposable.getAndIncrement() == 0) {
                int i = 1;
                do {
                    Node node = (Node) innerDisposable.a();
                    if (node == null) {
                        node = e();
                        innerDisposable.c = node;
                    }
                    while (!innerDisposable.isDisposed()) {
                        Node node2 = (Node) node.get();
                        if (node2 == null) {
                            innerDisposable.c = node;
                            i = innerDisposable.addAndGet(-i);
                        } else if (NotificationLite.accept(c(node2.a), innerDisposable.b)) {
                            innerDisposable.c = null;
                            return;
                        } else {
                            node = node2;
                        }
                    }
                    return;
                } while (i != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public Node e() {
            return (Node) get();
        }
    }

    interface BufferSupplier<T> {
        ReplayBuffer<T> a();
    }

    static final class DisposeConsumer<R> implements Consumer<Disposable> {
        private final ObserverResourceWrapper<R> a;

        DisposeConsumer(ObserverResourceWrapper<R> observerResourceWrapper) {
            this.a = observerResourceWrapper;
        }

        /* renamed from: a */
        public void accept(Disposable disposable) {
            this.a.setResource(disposable);
        }
    }

    static final class InnerDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2728361546769921047L;
        final ReplayObserver<T> a;
        final Observer<? super T> b;
        Object c;
        volatile boolean d;

        InnerDisposable(ReplayObserver<T> replayObserver, Observer<? super T> observer) {
            this.a = replayObserver;
            this.b = observer;
        }

        public boolean isDisposed() {
            return this.d;
        }

        public void dispose() {
            if (!this.d) {
                this.d = true;
                this.a.b(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public <U> U a() {
            return this.c;
        }
    }

    static final class MulticastReplay<R, U> extends Observable<R> {
        private final Callable<? extends ConnectableObservable<U>> a;
        private final Function<? super Observable<U>, ? extends ObservableSource<R>> b;

        MulticastReplay(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
            this.a = callable;
            this.b = function;
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Observer<? super R> observer) {
            try {
                ConnectableObservable connectableObservable = (ConnectableObservable) this.a.call();
                ObservableSource observableSource = (ObservableSource) this.b.apply(connectableObservable);
                ObserverResourceWrapper observerResourceWrapper = new ObserverResourceWrapper(observer);
                observableSource.subscribe(observerResourceWrapper);
                connectableObservable.connect(new DisposeConsumer(observerResourceWrapper));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        }
    }

    static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final Object a;

        Node(Object obj) {
            this.a = obj;
        }
    }

    static final class Replay<T> extends ConnectableObservable<T> {
        private final ConnectableObservable<T> a;
        private final Observable<T> b;

        Replay(ConnectableObservable<T> connectableObservable, Observable<T> observable) {
            this.a = connectableObservable;
            this.b = observable;
        }

        public void connect(Consumer<? super Disposable> consumer) {
            this.a.connect(consumer);
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Observer<? super T> observer) {
            this.b.subscribe(observer);
        }
    }

    interface ReplayBuffer<T> {
        void a(InnerDisposable<T> innerDisposable);

        void a(T t);

        void a(Throwable th);

        void b();
    }

    static final class ReplayBufferSupplier<T> implements BufferSupplier<T> {
        private final int a;

        ReplayBufferSupplier(int i) {
            this.a = i;
        }

        public ReplayBuffer<T> a() {
            return new SizeBoundReplayBuffer(this.a);
        }
    }

    static final class ReplayObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        static final InnerDisposable[] c = new InnerDisposable[0];
        static final InnerDisposable[] d = new InnerDisposable[0];
        private static final long serialVersionUID = -533785617179540163L;
        final ReplayBuffer<T> a;
        boolean b;
        final AtomicReference<InnerDisposable[]> e = new AtomicReference<>(c);
        final AtomicBoolean f = new AtomicBoolean();

        ReplayObserver(ReplayBuffer<T> replayBuffer) {
            this.a = replayBuffer;
        }

        public boolean isDisposed() {
            return this.e.get() == d;
        }

        public void dispose() {
            this.e.set(d);
            DisposableHelper.dispose(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerDisposable<T> innerDisposable) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = (InnerDisposable[]) this.e.get();
                if (innerDisposableArr == d) {
                    return false;
                }
                int length = innerDisposableArr.length;
                innerDisposableArr2 = new InnerDisposable[(length + 1)];
                System.arraycopy(innerDisposableArr, 0, innerDisposableArr2, 0, length);
                innerDisposableArr2[length] = innerDisposable;
            } while (!this.e.compareAndSet(innerDisposableArr, innerDisposableArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerDisposable<T> innerDisposable) {
            InnerDisposable[] innerDisposableArr;
            InnerDisposable[] innerDisposableArr2;
            do {
                innerDisposableArr = (InnerDisposable[]) this.e.get();
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
                            innerDisposableArr2 = c;
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
            } while (!this.e.compareAndSet(innerDisposableArr, innerDisposableArr2));
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                a();
            }
        }

        public void onNext(T t) {
            if (!this.b) {
                this.a.a(t);
                a();
            }
        }

        public void onError(Throwable th) {
            if (!this.b) {
                this.b = true;
                this.a.a(th);
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.b();
                b();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            for (InnerDisposable a2 : (InnerDisposable[]) this.e.get()) {
                this.a.a(a2);
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            for (InnerDisposable a2 : (InnerDisposable[]) this.e.getAndSet(d)) {
                this.a.a(a2);
            }
        }
    }

    static final class ReplaySource<T> implements ObservableSource<T> {
        private final AtomicReference<ReplayObserver<T>> a;
        private final BufferSupplier<T> b;

        ReplaySource(AtomicReference<ReplayObserver<T>> atomicReference, BufferSupplier<T> bufferSupplier) {
            this.a = atomicReference;
            this.b = bufferSupplier;
        }

        /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void subscribe(io.reactivex.Observer<? super T> r4) {
            /*
                r3 = this;
            L_0x0000:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver<T>> r0 = r3.a
                java.lang.Object r0 = r0.get()
                io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver r0 = (io.reactivex.internal.operators.observable.ObservableReplay.ReplayObserver) r0
                if (r0 != 0) goto L_0x0020
                io.reactivex.internal.operators.observable.ObservableReplay$BufferSupplier<T> r0 = r3.b
                io.reactivex.internal.operators.observable.ObservableReplay$ReplayBuffer r0 = r0.a()
                io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver r1 = new io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver
                r1.<init>(r0)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver<T>> r0 = r3.a
                r2 = 0
                boolean r0 = r0.compareAndSet(r2, r1)
                if (r0 != 0) goto L_0x001f
                goto L_0x0000
            L_0x001f:
                r0 = r1
            L_0x0020:
                io.reactivex.internal.operators.observable.ObservableReplay$InnerDisposable r1 = new io.reactivex.internal.operators.observable.ObservableReplay$InnerDisposable
                r1.<init>(r0, r4)
                r4.onSubscribe(r1)
                r0.a(r1)
                boolean r4 = r1.isDisposed()
                if (r4 == 0) goto L_0x0035
                r0.b(r1)
                return
            L_0x0035:
                io.reactivex.internal.operators.observable.ObservableReplay$ReplayBuffer<T> r4 = r0.a
                r4.a(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableReplay.ReplaySource.subscribe(io.reactivex.Observer):void");
        }
    }

    static final class ScheduledReplaySupplier<T> implements BufferSupplier<T> {
        private final int a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        ScheduledReplaySupplier(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        public ReplayBuffer<T> a() {
            SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(this.a, this.b, this.c, this.d);
            return sizeAndTimeBoundReplayBuffer;
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final Scheduler c;
        final long d;
        final TimeUnit e;
        final int f;

        SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.c = scheduler;
            this.f = i;
            this.d = j;
            this.e = timeUnit;
        }

        /* access modifiers changed from: 0000 */
        public Object b(Object obj) {
            return new Timed(obj, this.c.now(this.e), this.e);
        }

        /* access modifiers changed from: 0000 */
        public Object c(Object obj) {
            return ((Timed) obj).value();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Node node;
            long now = this.c.now(this.e) - this.d;
            Node node2 = (Node) get();
            Node node3 = (Node) node2.get();
            int i = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (node2 != null) {
                    if (this.b <= this.f) {
                        if (((Timed) node2.a).time() > now) {
                            break;
                        }
                        i++;
                        this.b--;
                        node3 = (Node) node2.get();
                    } else {
                        i++;
                        this.b--;
                        node3 = (Node) node2.get();
                    }
                } else {
                    break;
                }
            }
            if (i != 0) {
                b(node);
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x003f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void d() {
            /*
                r10 = this;
                io.reactivex.Scheduler r0 = r10.c
                java.util.concurrent.TimeUnit r1 = r10.e
                long r0 = r0.now(r1)
                long r2 = r10.d
                long r4 = r0 - r2
                java.lang.Object r0 = r10.get()
                io.reactivex.internal.operators.observable.ObservableReplay$Node r0 = (io.reactivex.internal.operators.observable.ObservableReplay.Node) r0
                java.lang.Object r1 = r0.get()
                io.reactivex.internal.operators.observable.ObservableReplay$Node r1 = (io.reactivex.internal.operators.observable.ObservableReplay.Node) r1
                r2 = 0
            L_0x0019:
                r9 = r1
                r1 = r0
                r0 = r9
                if (r0 == 0) goto L_0x003d
                int r3 = r10.b
                r6 = 1
                if (r3 <= r6) goto L_0x003d
                java.lang.Object r3 = r0.a
                io.reactivex.schedulers.Timed r3 = (io.reactivex.schedulers.Timed) r3
                long r7 = r3.time()
                int r3 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
                if (r3 > 0) goto L_0x003d
                int r2 = r2 + 1
                int r1 = r10.b
                int r1 = r1 - r6
                r10.b = r1
                java.lang.Object r1 = r0.get()
                io.reactivex.internal.operators.observable.ObservableReplay$Node r1 = (io.reactivex.internal.operators.observable.ObservableReplay.Node) r1
                goto L_0x0019
            L_0x003d:
                if (r2 == 0) goto L_0x0042
                r10.b(r1)
            L_0x0042:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableReplay.SizeAndTimeBoundReplayBuffer.d():void");
        }

        /* access modifiers changed from: 0000 */
        public Node e() {
            Node node;
            long now = this.c.now(this.e) - this.d;
            Node node2 = (Node) get();
            Object obj = node2.get();
            while (true) {
                Node node3 = (Node) obj;
                node = node2;
                node2 = node3;
                if (node2 != null) {
                    Timed timed = (Timed) node2.a;
                    if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > now) {
                        break;
                    }
                    obj = node2.get();
                } else {
                    break;
                }
            }
            return node;
        }
    }

    static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int c;

        SizeBoundReplayBuffer(int i) {
            this.c = i;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.b > this.c) {
                a();
            }
        }
    }

    static final class UnBoundedFactory implements BufferSupplier<Object> {
        UnBoundedFactory() {
        }

        public ReplayBuffer<Object> a() {
            return new UnboundedReplayBuffer(16);
        }
    }

    static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int a;

        UnboundedReplayBuffer(int i) {
            super(i);
        }

        public void a(T t) {
            add(NotificationLite.next(t));
            this.a++;
        }

        public void a(Throwable th) {
            add(NotificationLite.error(th));
            this.a++;
        }

        public void b() {
            add(NotificationLite.complete());
            this.a++;
        }

        public void a(InnerDisposable<T> innerDisposable) {
            if (innerDisposable.getAndIncrement() == 0) {
                Observer<? super T> observer = innerDisposable.b;
                int i = 1;
                while (!innerDisposable.isDisposed()) {
                    int i2 = this.a;
                    Integer num = (Integer) innerDisposable.a();
                    int intValue = num != null ? num.intValue() : 0;
                    while (intValue < i2) {
                        if (!NotificationLite.accept(get(intValue), observer) && !innerDisposable.isDisposed()) {
                            intValue++;
                        } else {
                            return;
                        }
                    }
                    innerDisposable.c = Integer.valueOf(intValue);
                    i = innerDisposable.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    public static <U, R> Observable<R> multicastSelector(Callable<? extends ConnectableObservable<U>> callable, Function<? super Observable<U>, ? extends ObservableSource<R>> function) {
        return RxJavaPlugins.onAssembly((Observable<T>) new MulticastReplay<T>(callable, function));
    }

    public static <T> ConnectableObservable<T> observeOn(ConnectableObservable<T> connectableObservable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableObservable<T>) new Replay<T>(connectableObservable, connectableObservable.observeOn(scheduler)));
    }

    public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> observableSource) {
        return a(observableSource, e);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, int i) {
        if (i == Integer.MAX_VALUE) {
            return createFrom(observableSource);
        }
        return a(observableSource, new ReplayBufferSupplier(i));
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(observableSource, j, timeUnit, scheduler, SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    public static <T> ConnectableObservable<T> create(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        ScheduledReplaySupplier scheduledReplaySupplier = new ScheduledReplaySupplier(i, j, timeUnit, scheduler);
        return a(observableSource, scheduledReplaySupplier);
    }

    static <T> ConnectableObservable<T> a(ObservableSource<T> observableSource, BufferSupplier<T> bufferSupplier) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableObservable<T>) new ObservableReplay<T>(new ReplaySource(atomicReference, bufferSupplier), observableSource, atomicReference, bufferSupplier));
    }

    private ObservableReplay(ObservableSource<T> observableSource, ObservableSource<T> observableSource2, AtomicReference<ReplayObserver<T>> atomicReference, BufferSupplier<T> bufferSupplier) {
        this.d = observableSource;
        this.a = observableSource2;
        this.b = atomicReference;
        this.c = bufferSupplier;
    }

    public ObservableSource<T> source() {
        return this.a;
    }

    public void dispose() {
        this.b.lazySet(null);
    }

    public boolean isDisposed() {
        Disposable disposable = (Disposable) this.b.get();
        return disposable == null || disposable.isDisposed();
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.d.subscribe(observer);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(io.reactivex.functions.Consumer<? super io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver<T>> r0 = r4.b
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver r0 = (io.reactivex.internal.operators.observable.ObservableReplay.ReplayObserver) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0025
        L_0x0010:
            io.reactivex.internal.operators.observable.ObservableReplay$BufferSupplier<T> r1 = r4.c
            io.reactivex.internal.operators.observable.ObservableReplay$ReplayBuffer r1 = r1.a()
            io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver r2 = new io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver
            r2.<init>(r1)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableReplay$ReplayObserver<T>> r1 = r4.b
            boolean r0 = r1.compareAndSet(r0, r2)
            if (r0 != 0) goto L_0x0024
            goto L_0x0000
        L_0x0024:
            r0 = r2
        L_0x0025:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0039
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0039
            r1 = 1
            goto L_0x003a
        L_0x0039:
            r1 = 0
        L_0x003a:
            r5.accept(r0)     // Catch:{ Throwable -> 0x0045 }
            if (r1 == 0) goto L_0x0044
            io.reactivex.ObservableSource<T> r5 = r4.a
            r5.subscribe(r0)
        L_0x0044:
            return
        L_0x0045:
            r5 = move-exception
            if (r1 == 0) goto L_0x004d
            java.util.concurrent.atomic.AtomicBoolean r0 = r0.f
            r0.compareAndSet(r2, r3)
        L_0x004d:
            io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableReplay.connect(io.reactivex.functions.Consumer):void");
    }
}
