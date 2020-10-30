package io.reactivex.internal.operators.flowable;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableReplay<T> extends ConnectableFlowable<T> implements Disposable, HasUpstreamPublisher<T> {
    static final Callable f = new DefaultUnboundedFactory();
    final Flowable<T> b;
    final AtomicReference<ReplaySubscriber<T>> c;
    final Callable<? extends ReplayBuffer<T>> d;
    final Publisher<T> e;

    static class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        Node a;
        int b;
        long c;

        /* access modifiers changed from: 0000 */
        public Object b(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public Object c(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
        }

        /* access modifiers changed from: 0000 */
        public void d() {
        }

        BoundedReplayBuffer() {
            Node node = new Node(null, 0);
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
            if (node == null) {
                throw new IllegalStateException("Empty list!");
            }
            this.b--;
            b(node);
        }

        /* access modifiers changed from: 0000 */
        public final void b(Node node) {
            set(node);
        }

        public final void a(T t) {
            Object b2 = b(NotificationLite.next(t));
            long j = this.c + 1;
            this.c = j;
            a(new Node(b2, j));
            c();
        }

        public final void a(Throwable th) {
            Object b2 = b(NotificationLite.error(th));
            long j = this.c + 1;
            this.c = j;
            a(new Node(b2, j));
            d();
        }

        public final void b() {
            Object b2 = b(NotificationLite.complete());
            long j = this.c + 1;
            this.c = j;
            a(new Node(b2, j));
            d();
        }

        /* JADX INFO: finally extract failed */
        /* JADX INFO: used method not loaded: io.reactivex.internal.util.NotificationLite.accept(java.lang.Object, org.reactivestreams.Subscriber):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
            if (r19.isDisposed() == false) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
            r4 = r19.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
            if (r4 != Long.MAX_VALUE) goto L_0x0026;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0024, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0027, code lost:
            r7 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r19.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
            if (r7 != null) goto L_0x003e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            r7 = e();
            r1.c = r7;
            io.reactivex.internal.util.BackpressureHelper.add(r1.d, r7.b);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003e, code lost:
            r10 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
            if (r4 == 0) goto L_0x008d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
            r12 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r7.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0049, code lost:
            if (r12 == null) goto L_0x008d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
            r7 = c(r12.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
            if (io.reactivex.internal.util.NotificationLite.accept(r7, (org.reactivestreams.Subscriber) r1.b) == false) goto L_0x005f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005c, code lost:
            r1.c = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x005e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x005f, code lost:
            r16 = r10 + 1;
            r10 = r4 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0069, code lost:
            if (r19.isDisposed() == false) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x006b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x006c, code lost:
            r4 = r10;
            r7 = r12;
            r10 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0071, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0072, code lost:
            r2 = r0;
            io.reactivex.exceptions.Exceptions.throwIfFatal(r2);
            r1.c = null;
            r19.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0087, code lost:
            r1.b.onError(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x008c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x008d, code lost:
            r13 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0091, code lost:
            if (r10 == 0) goto L_0x009a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0093, code lost:
            r1.c = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x0095, code lost:
            if (r2 != false) goto L_0x009a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0097, code lost:
            r1.a(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x009a, code lost:
            monitor-enter(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x009d, code lost:
            if (r1.f != false) goto L_0x00a3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x009f, code lost:
            r1.e = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a1, code lost:
            monitor-exit(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00a2, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a3, code lost:
            r1.f = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a5, code lost:
            monitor-exit(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x00a8, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x00ab, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x00b2, code lost:
            r0 = th;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(io.reactivex.internal.operators.flowable.FlowableReplay.InnerSubscription<T> r19) {
            /*
                r18 = this;
                r1 = r19
                monitor-enter(r19)
                boolean r2 = r1.e     // Catch:{ all -> 0x00ac }
                r3 = 1
                if (r2 == 0) goto L_0x000c
                r1.f = r3     // Catch:{ all -> 0x00ac }
                monitor-exit(r19)     // Catch:{ all -> 0x00ac }
                return
            L_0x000c:
                r1.e = r3     // Catch:{ all -> 0x00ac }
                monitor-exit(r19)     // Catch:{ all -> 0x00ac }
            L_0x000f:
                boolean r2 = r19.isDisposed()
                if (r2 == 0) goto L_0x0016
                return
            L_0x0016:
                long r4 = r19.get()
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                r6 = 0
                if (r2 != 0) goto L_0x0026
                r2 = 1
                goto L_0x0027
            L_0x0026:
                r2 = 0
            L_0x0027:
                java.lang.Object r7 = r19.a()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r7 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r7
                r8 = 0
                if (r7 != 0) goto L_0x003e
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r7 = r18.e()
                r1.c = r7
                java.util.concurrent.atomic.AtomicLong r10 = r1.d
                long r11 = r7.b
                io.reactivex.internal.util.BackpressureHelper.add(r10, r11)
            L_0x003e:
                r10 = r8
            L_0x003f:
                int r12 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r12 == 0) goto L_0x008d
                java.lang.Object r12 = r7.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r12 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r12
                if (r12 == 0) goto L_0x008d
                java.lang.Object r7 = r12.a
                r13 = r18
                java.lang.Object r7 = r13.c(r7)
                r14 = 0
                org.reactivestreams.Subscriber<? super T> r15 = r1.b     // Catch:{ Throwable -> 0x0071 }
                boolean r15 = io.reactivex.internal.util.NotificationLite.accept(r7, r15)     // Catch:{ Throwable -> 0x0071 }
                if (r15 == 0) goto L_0x005f
                r1.c = r14     // Catch:{ Throwable -> 0x0071 }
                return
            L_0x005f:
                r14 = 1
                long r16 = r10 + r14
                long r10 = r4 - r14
                boolean r4 = r19.isDisposed()
                if (r4 == 0) goto L_0x006c
                return
            L_0x006c:
                r4 = r10
                r7 = r12
                r10 = r16
                goto L_0x003f
            L_0x0071:
                r0 = move-exception
                r2 = r0
                io.reactivex.exceptions.Exceptions.throwIfFatal(r2)
                r1.c = r14
                r19.dispose()
                boolean r3 = io.reactivex.internal.util.NotificationLite.isError(r7)
                if (r3 != 0) goto L_0x008c
                boolean r3 = io.reactivex.internal.util.NotificationLite.isComplete(r7)
                if (r3 != 0) goto L_0x008c
                org.reactivestreams.Subscriber<? super T> r1 = r1.b
                r1.onError(r2)
            L_0x008c:
                return
            L_0x008d:
                r13 = r18
                int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x009a
                r1.c = r7
                if (r2 != 0) goto L_0x009a
                r1.a(r10)
            L_0x009a:
                monitor-enter(r19)
                boolean r2 = r1.f     // Catch:{ all -> 0x00a8 }
                if (r2 != 0) goto L_0x00a3
                r1.e = r6     // Catch:{ all -> 0x00a8 }
                monitor-exit(r19)     // Catch:{ all -> 0x00a8 }
                return
            L_0x00a3:
                r1.f = r6     // Catch:{ all -> 0x00a8 }
                monitor-exit(r19)     // Catch:{ all -> 0x00a8 }
                goto L_0x000f
            L_0x00a8:
                r0 = move-exception
                r2 = r0
                monitor-exit(r19)     // Catch:{ all -> 0x00a8 }
                throw r2
            L_0x00ac:
                r0 = move-exception
                r13 = r18
            L_0x00af:
                r2 = r0
                monitor-exit(r19)     // Catch:{ all -> 0x00b2 }
                throw r2
            L_0x00b2:
                r0 = move-exception
                goto L_0x00af
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer.a(io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription):void");
        }

        /* access modifiers changed from: 0000 */
        public Node e() {
            return (Node) get();
        }
    }

    static final class ConnectableFlowableReplay<T> extends ConnectableFlowable<T> {
        private final ConnectableFlowable<T> b;
        private final Flowable<T> c;

        ConnectableFlowableReplay(ConnectableFlowable<T> connectableFlowable, Flowable<T> flowable) {
            this.b = connectableFlowable;
            this.c = flowable;
        }

        public void connect(Consumer<? super Disposable> consumer) {
            this.b.connect(consumer);
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Subscriber<? super T> subscriber) {
            this.c.subscribe(subscriber);
        }
    }

    static final class DefaultUnboundedFactory implements Callable<Object> {
        DefaultUnboundedFactory() {
        }

        public Object call() {
            return new UnboundedReplayBuffer(16);
        }
    }

    static final class InnerSubscription<T> extends AtomicLong implements Disposable, Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final ReplaySubscriber<T> a;
        final Subscriber<? super T> b;
        Object c;
        final AtomicLong d = new AtomicLong();
        boolean e;
        boolean f;

        InnerSubscription(ReplaySubscriber<T> replaySubscriber, Subscriber<? super T> subscriber) {
            this.a = replaySubscriber;
            this.b = subscriber;
        }

        public void request(long j) {
            long j2;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 != Long.MIN_VALUE) {
                        if (j2 >= 0 && j == 0) {
                            return;
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, BackpressureHelper.addCap(j2, j)));
                BackpressureHelper.add(this.d, j);
                this.a.a();
                this.a.a.a(this);
            }
        }

        public long a(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        public void cancel() {
            dispose();
        }

        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.a.b(this);
                this.a.a();
            }
        }

        /* access modifiers changed from: 0000 */
        public <U> U a() {
            return this.c;
        }
    }

    static final class MultiCastPublisher<R, U> implements Publisher<R> {
        private final Callable<? extends ConnectableFlowable<U>> a;
        private final Function<? super Flowable<U>, ? extends Publisher<R>> b;

        final class DisposableConsumer implements Consumer<Disposable> {
            private final SubscriberResourceWrapper<R> b;

            DisposableConsumer(SubscriberResourceWrapper<R> subscriberResourceWrapper) {
                this.b = subscriberResourceWrapper;
            }

            /* renamed from: a */
            public void accept(Disposable disposable) {
                this.b.setResource(disposable);
            }
        }

        MultiCastPublisher(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
            this.a = callable;
            this.b = function;
        }

        public void subscribe(Subscriber<? super R> subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ObjectHelper.requireNonNull(this.a.call(), "The connectableFactory returned null");
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(connectableFlowable), "The selector returned a null Publisher");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(subscriber);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new DisposableConsumer(subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }
    }

    static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final Object a;
        final long b;

        Node(Object obj, long j) {
            this.a = obj;
            this.b = j;
        }
    }

    interface ReplayBuffer<T> {
        void a(InnerSubscription<T> innerSubscription);

        void a(T t);

        void a(Throwable th);

        void b();
    }

    static final class ReplayBufferTask<T> implements Callable<ReplayBuffer<T>> {
        private final int a;

        ReplayBufferTask(int i) {
            this.a = i;
        }

        /* renamed from: a */
        public ReplayBuffer<T> call() {
            return new SizeBoundReplayBuffer(this.a);
        }
    }

    static final class ReplayPublisher<T> implements Publisher<T> {
        private final AtomicReference<ReplaySubscriber<T>> a;
        private final Callable<? extends ReplayBuffer<T>> b;

        ReplayPublisher(AtomicReference<ReplaySubscriber<T>> atomicReference, Callable<? extends ReplayBuffer<T>> callable) {
            this.a = atomicReference;
            this.b = callable;
        }

        /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void subscribe(org.reactivestreams.Subscriber<? super T> r4) {
            /*
                r3 = this;
            L_0x0000:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r3.a
                java.lang.Object r0 = r0.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r0 = (io.reactivex.internal.operators.flowable.FlowableReplay.ReplaySubscriber) r0
                if (r0 != 0) goto L_0x002c
                java.util.concurrent.Callable<? extends io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T>> r0 = r3.b     // Catch:{ Throwable -> 0x0023 }
                java.lang.Object r0 = r0.call()     // Catch:{ Throwable -> 0x0023 }
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer r0 = (io.reactivex.internal.operators.flowable.FlowableReplay.ReplayBuffer) r0     // Catch:{ Throwable -> 0x0023 }
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r1 = new io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber
                r1.<init>(r0)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r3.a
                r2 = 0
                boolean r0 = r0.compareAndSet(r2, r1)
                if (r0 != 0) goto L_0x0021
                goto L_0x0000
            L_0x0021:
                r0 = r1
                goto L_0x002c
            L_0x0023:
                r4 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                java.lang.RuntimeException r4 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r4)
                throw r4
            L_0x002c:
                io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription r1 = new io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription
                r1.<init>(r0, r4)
                r4.onSubscribe(r1)
                r0.a(r1)
                boolean r4 = r1.isDisposed()
                if (r4 == 0) goto L_0x0041
                r0.b(r1)
                return
            L_0x0041:
                r0.a()
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T> r4 = r0.a
                r4.a(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.ReplayPublisher.subscribe(org.reactivestreams.Subscriber):void");
        }
    }

    static final class ReplaySubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscription[] c = new InnerSubscription[0];
        static final InnerSubscription[] d = new InnerSubscription[0];
        private static final long serialVersionUID = 7224554242710036740L;
        final ReplayBuffer<T> a;
        boolean b;
        final AtomicReference<InnerSubscription<T>[]> e = new AtomicReference<>(c);
        final AtomicBoolean f = new AtomicBoolean();
        final AtomicInteger g = new AtomicInteger();
        long h;
        long i;

        ReplaySubscriber(ReplayBuffer<T> replayBuffer) {
            this.a = replayBuffer;
        }

        public boolean isDisposed() {
            return this.e.get() == d;
        }

        public void dispose() {
            this.e.set(d);
            SubscriptionHelper.cancel(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InnerSubscription<T> innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            if (innerSubscription == null) {
                throw new NullPointerException();
            }
            do {
                innerSubscriptionArr = (InnerSubscription[]) this.e.get();
                if (innerSubscriptionArr == d) {
                    return false;
                }
                int length = innerSubscriptionArr.length;
                innerSubscriptionArr2 = new InnerSubscription[(length + 1)];
                System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr2, 0, length);
                innerSubscriptionArr2[length] = innerSubscription;
            } while (!this.e.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void b(InnerSubscription<T> innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = (InnerSubscription[]) this.e.get();
                int length = innerSubscriptionArr.length;
                if (length != 0) {
                    int i2 = -1;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        } else if (innerSubscriptionArr[i3].equals(innerSubscription)) {
                            i2 = i3;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i2 >= 0) {
                        if (length == 1) {
                            innerSubscriptionArr2 = c;
                        } else {
                            InnerSubscription[] innerSubscriptionArr3 = new InnerSubscription[(length - 1)];
                            System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr3, 0, i2);
                            System.arraycopy(innerSubscriptionArr, i2 + 1, innerSubscriptionArr3, i2, (length - i2) - 1);
                            innerSubscriptionArr2 = innerSubscriptionArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.e.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                a();
                for (InnerSubscription a2 : (InnerSubscription[]) this.e.get()) {
                    this.a.a(a2);
                }
            }
        }

        public void onNext(T t) {
            if (!this.b) {
                this.a.a(t);
                for (InnerSubscription a2 : (InnerSubscription[]) this.e.get()) {
                    this.a.a(a2);
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.b) {
                this.b = true;
                this.a.a(th);
                for (InnerSubscription a2 : (InnerSubscription[]) this.e.getAndSet(d)) {
                    this.a.a(a2);
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.b();
                for (InnerSubscription a2 : (InnerSubscription[]) this.e.getAndSet(d)) {
                    this.a.a(a2);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.g.getAndIncrement() == 0) {
                int i2 = 1;
                while (!isDisposed()) {
                    InnerSubscription[] innerSubscriptionArr = (InnerSubscription[]) this.e.get();
                    long j = this.h;
                    long j2 = j;
                    for (InnerSubscription innerSubscription : innerSubscriptionArr) {
                        j2 = Math.max(j2, innerSubscription.d.get());
                    }
                    long j3 = this.i;
                    Subscription subscription = (Subscription) get();
                    long j4 = j2 - j;
                    if (j4 != 0) {
                        this.h = j2;
                        if (subscription == null) {
                            long j5 = j3 + j4;
                            if (j5 < 0) {
                                j5 = Long.MAX_VALUE;
                            }
                            this.i = j5;
                        } else if (j3 != 0) {
                            this.i = 0;
                            subscription.request(j3 + j4);
                        } else {
                            subscription.request(j4);
                        }
                    } else if (!(j3 == 0 || subscription == null)) {
                        this.i = 0;
                        subscription.request(j3);
                    }
                    i2 = this.g.addAndGet(-i2);
                    if (i2 == 0) {
                        return;
                    }
                }
            }
        }
    }

    static final class ScheduledReplayBufferTask<T> implements Callable<ReplayBuffer<T>> {
        private final int a;
        private final long b;
        private final TimeUnit c;
        private final Scheduler d;

        ScheduledReplayBufferTask(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = i;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        /* renamed from: a */
        public ReplayBuffer<T> call() {
            SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(this.a, this.b, this.c, this.d);
            return sizeAndTimeBoundReplayBuffer;
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final Scheduler d;
        final long e;
        final TimeUnit f;
        final int g;

        SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.d = scheduler;
            this.g = i;
            this.e = j;
            this.f = timeUnit;
        }

        /* access modifiers changed from: 0000 */
        public Object b(Object obj) {
            return new Timed(obj, this.d.now(this.f), this.f);
        }

        /* access modifiers changed from: 0000 */
        public Object c(Object obj) {
            return ((Timed) obj).value();
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            Node node;
            long now = this.d.now(this.f) - this.e;
            Node node2 = (Node) get();
            Node node3 = (Node) node2.get();
            int i = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (node2 != null) {
                    if (this.b <= this.g) {
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
                io.reactivex.Scheduler r0 = r10.d
                java.util.concurrent.TimeUnit r1 = r10.f
                long r0 = r0.now(r1)
                long r2 = r10.e
                long r4 = r0 - r2
                java.lang.Object r0 = r10.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r0 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r0
                java.lang.Object r1 = r0.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r1 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r1
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
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r1 = (io.reactivex.internal.operators.flowable.FlowableReplay.Node) r1
                goto L_0x0019
            L_0x003d:
                if (r2 == 0) goto L_0x0042
                r10.b(r1)
            L_0x0042:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.SizeAndTimeBoundReplayBuffer.d():void");
        }

        /* access modifiers changed from: 0000 */
        public Node e() {
            Node node;
            long now = this.d.now(this.f) - this.e;
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
        final int d;

        SizeBoundReplayBuffer(int i) {
            this.d = i;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.b > this.d) {
                a();
            }
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

        /* JADX INFO: used method not loaded: io.reactivex.internal.util.NotificationLite.accept(java.lang.Object, org.reactivestreams.Subscriber):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0011, code lost:
            r3 = r2.b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0017, code lost:
            if (r20.isDisposed() == false) goto L_0x001a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
            r4 = r1.a;
            r5 = (java.lang.Integer) r20.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
            if (r5 == null) goto L_0x002a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
            r5 = r5.intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
            r5 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
            r7 = r20.get();
            r9 = 0;
            r11 = r7;
            r13 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
            if (r11 == r9) goto L_0x006e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
            if (r5 >= r4) goto L_0x006e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
            r15 = get(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0041, code lost:
            if (io.reactivex.internal.util.NotificationLite.accept(r15, (org.reactivestreams.Subscriber) r3) == false) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0048, code lost:
            if (r20.isDisposed() == false) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x004a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x004b, code lost:
            r5 = r5 + 1;
            r13 = r13 + 1;
            r11 = r11 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0057, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0058, code lost:
            io.reactivex.exceptions.Exceptions.throwIfFatal(r0);
            r20.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x006a, code lost:
            r3.onError(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x006d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0070, code lost:
            if (r13 == r9) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0072, code lost:
            r2.c = java.lang.Integer.valueOf(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x007f, code lost:
            if (r7 == Long.MAX_VALUE) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0081, code lost:
            r2.a(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0084, code lost:
            monitor-enter(r20);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0087, code lost:
            if (r2.f != false) goto L_0x008d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0089, code lost:
            r2.e = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x008b, code lost:
            monitor-exit(r20);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x008c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x008d, code lost:
            r2.f = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x008f, code lost:
            monitor-exit(r20);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0091, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0094, code lost:
            throw r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(io.reactivex.internal.operators.flowable.FlowableReplay.InnerSubscription<T> r20) {
            /*
                r19 = this;
                r1 = r19
                r2 = r20
                monitor-enter(r20)
                boolean r3 = r2.e     // Catch:{ all -> 0x0095 }
                r4 = 1
                if (r3 == 0) goto L_0x000e
                r2.f = r4     // Catch:{ all -> 0x0095 }
                monitor-exit(r20)     // Catch:{ all -> 0x0095 }
                return
            L_0x000e:
                r2.e = r4     // Catch:{ all -> 0x0095 }
                monitor-exit(r20)     // Catch:{ all -> 0x0095 }
                org.reactivestreams.Subscriber<? super T> r3 = r2.b
            L_0x0013:
                boolean r4 = r20.isDisposed()
                if (r4 == 0) goto L_0x001a
                return
            L_0x001a:
                int r4 = r1.a
                java.lang.Object r5 = r20.a()
                java.lang.Integer r5 = (java.lang.Integer) r5
                r6 = 0
                if (r5 == 0) goto L_0x002a
                int r5 = r5.intValue()
                goto L_0x002b
            L_0x002a:
                r5 = 0
            L_0x002b:
                long r7 = r20.get()
                r9 = 0
                r11 = r7
                r13 = r9
            L_0x0033:
                int r15 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r15 == 0) goto L_0x006e
                if (r5 >= r4) goto L_0x006e
                java.lang.Object r15 = r1.get(r5)
                boolean r16 = io.reactivex.internal.util.NotificationLite.accept(r15, r3)     // Catch:{ Throwable -> 0x0057 }
                if (r16 == 0) goto L_0x0044
                return
            L_0x0044:
                boolean r15 = r20.isDisposed()
                if (r15 == 0) goto L_0x004b
                return
            L_0x004b:
                int r5 = r5 + 1
                r15 = 1
                long r17 = r11 - r15
                long r11 = r13 + r15
                r13 = r11
                r11 = r17
                goto L_0x0033
            L_0x0057:
                r0 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r20.dispose()
                boolean r2 = io.reactivex.internal.util.NotificationLite.isError(r15)
                if (r2 != 0) goto L_0x006d
                boolean r2 = io.reactivex.internal.util.NotificationLite.isComplete(r15)
                if (r2 != 0) goto L_0x006d
                r3.onError(r0)
            L_0x006d:
                return
            L_0x006e:
                int r4 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
                if (r4 == 0) goto L_0x0084
                java.lang.Integer r4 = java.lang.Integer.valueOf(r5)
                r2.c = r4
                r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
                if (r9 == 0) goto L_0x0084
                r2.a(r13)
            L_0x0084:
                monitor-enter(r20)
                boolean r4 = r2.f     // Catch:{ all -> 0x0091 }
                if (r4 != 0) goto L_0x008d
                r2.e = r6     // Catch:{ all -> 0x0091 }
                monitor-exit(r20)     // Catch:{ all -> 0x0091 }
                return
            L_0x008d:
                r2.f = r6     // Catch:{ all -> 0x0091 }
                monitor-exit(r20)     // Catch:{ all -> 0x0091 }
                goto L_0x0013
            L_0x0091:
                r0 = move-exception
                r3 = r0
                monitor-exit(r20)     // Catch:{ all -> 0x0091 }
                throw r3
            L_0x0095:
                r0 = move-exception
                r3 = r0
                monitor-exit(r20)     // Catch:{ all -> 0x0095 }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.UnboundedReplayBuffer.a(io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription):void");
        }
    }

    public static <U, R> Flowable<R> multicastSelector(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
        return Flowable.unsafeCreate(new MultiCastPublisher(callable, function));
    }

    public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> connectableFlowable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new ConnectableFlowableReplay<T>(connectableFlowable, connectableFlowable.observeOn(scheduler)));
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> flowable) {
        return a(flowable, f);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        if (i == Integer.MAX_VALUE) {
            return createFrom(flowable);
        }
        return a(flowable, new ReplayBufferTask(i));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(flowable, j, timeUnit, scheduler, SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        ScheduledReplayBufferTask scheduledReplayBufferTask = new ScheduledReplayBufferTask(i, j, timeUnit, scheduler);
        return a(flowable, scheduledReplayBufferTask);
    }

    static <T> ConnectableFlowable<T> a(Flowable<T> flowable, Callable<? extends ReplayBuffer<T>> callable) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new FlowableReplay<T>(new ReplayPublisher(atomicReference, callable), flowable, atomicReference, callable));
    }

    private FlowableReplay(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<ReplaySubscriber<T>> atomicReference, Callable<? extends ReplayBuffer<T>> callable) {
        this.e = publisher;
        this.b = flowable;
        this.c = atomicReference;
        this.d = callable;
    }

    public Publisher<T> source() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.e.subscribe(subscriber);
    }

    public void dispose() {
        this.c.lazySet(null);
    }

    public boolean isDisposed() {
        Disposable disposable = (Disposable) this.c.get();
        return disposable == null || disposable.isDisposed();
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(io.reactivex.functions.Consumer<? super io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r4.c
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r0 = (io.reactivex.internal.operators.flowable.FlowableReplay.ReplaySubscriber) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0027
        L_0x0010:
            java.util.concurrent.Callable<? extends io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T>> r1 = r4.d     // Catch:{ Throwable -> 0x0057 }
            java.lang.Object r1 = r1.call()     // Catch:{ Throwable -> 0x0057 }
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer r1 = (io.reactivex.internal.operators.flowable.FlowableReplay.ReplayBuffer) r1     // Catch:{ Throwable -> 0x0057 }
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r2 = new io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber
            r2.<init>(r1)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r1 = r4.c
            boolean r0 = r1.compareAndSet(r0, r2)
            if (r0 != 0) goto L_0x0026
            goto L_0x0000
        L_0x0026:
            r0 = r2
        L_0x0027:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x003b
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.f
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x003b
            r1 = 1
            goto L_0x003c
        L_0x003b:
            r1 = 0
        L_0x003c:
            r5.accept(r0)     // Catch:{ Throwable -> 0x0047 }
            if (r1 == 0) goto L_0x0046
            io.reactivex.Flowable<T> r5 = r4.b
            r5.subscribe(r0)
        L_0x0046:
            return
        L_0x0047:
            r5 = move-exception
            if (r1 == 0) goto L_0x004f
            java.util.concurrent.atomic.AtomicBoolean r0 = r0.f
            r0.compareAndSet(r2, r3)
        L_0x004f:
            io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        L_0x0057:
            r5 = move-exception
            io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableReplay.connect(io.reactivex.functions.Consumer):void");
    }
}
