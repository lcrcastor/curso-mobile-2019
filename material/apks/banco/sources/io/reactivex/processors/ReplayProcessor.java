package io.reactivex.processors;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ReplayProcessor<T> extends FlowableProcessor<T> {
    static final ReplaySubscription[] e = new ReplaySubscription[0];
    static final ReplaySubscription[] f = new ReplaySubscription[0];
    private static final Object[] g = new Object[0];
    final ReplayBuffer<T> b;
    boolean c;
    final AtomicReference<ReplaySubscription<T>[]> d = new AtomicReference<>(e);

    static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T a;

        Node(T t) {
            this.a = t;
        }
    }

    interface ReplayBuffer<T> {
        int a();

        void a(ReplaySubscription<T> replaySubscription);

        void a(T t);

        T[] a(T[] tArr);

        T b();

        void b(Object obj);

        Object get();
    }

    static final class ReplaySubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 466549804534799122L;
        final Subscriber<? super T> a;
        final ReplayProcessor<T> b;
        Object c;
        final AtomicLong d = new AtomicLong();
        volatile boolean e;

        ReplaySubscription(Subscriber<? super T> subscriber, ReplayProcessor<T> replayProcessor) {
            this.a = subscriber;
            this.b = replayProcessor;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.d, j);
                this.b.b.a(this);
            }
        }

        public void cancel() {
            if (!this.e) {
                this.e = true;
                this.b.b(this);
            }
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 1242561386470847675L;
        final int a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        int e;
        volatile TimedNode<Object> f;
        TimedNode<Object> g;
        volatile boolean h;

        SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            this.b = ObjectHelper.verifyPositive(j, "maxAge");
            this.c = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.d = (Scheduler) ObjectHelper.requireNonNull(scheduler, "scheduler is null");
            TimedNode<Object> timedNode = new TimedNode<>(null, 0);
            this.g = timedNode;
            this.f = timedNode;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.e > this.a) {
                this.e--;
                this.f = (TimedNode) this.f.get();
            }
            long now = this.d.now(this.c) - this.b;
            TimedNode<Object> timedNode = this.f;
            while (true) {
                TimedNode<Object> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2 == null) {
                    this.f = timedNode;
                    return;
                } else if (timedNode2.b > now) {
                    this.f = timedNode;
                    return;
                } else {
                    timedNode = timedNode2;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            long now = this.d.now(this.c) - this.b;
            TimedNode<Object> timedNode = this.f;
            while (true) {
                TimedNode<Object> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2.get() == null) {
                    this.f = timedNode;
                    return;
                } else if (timedNode2.b > now) {
                    this.f = timedNode;
                    return;
                } else {
                    timedNode = timedNode2;
                }
            }
        }

        public void a(T t) {
            TimedNode<Object> timedNode = new TimedNode<>(t, this.d.now(this.c));
            TimedNode<Object> timedNode2 = this.g;
            this.g = timedNode;
            this.e++;
            timedNode2.set(timedNode);
            c();
        }

        public void b(Object obj) {
            lazySet(obj);
            TimedNode<Object> timedNode = new TimedNode<>(obj, Long.MAX_VALUE);
            TimedNode<Object> timedNode2 = this.g;
            this.g = timedNode;
            this.e++;
            timedNode2.set(timedNode);
            d();
            this.h = true;
        }

        public T b() {
            TimedNode<Object> timedNode = this.f;
            TimedNode<Object> timedNode2 = null;
            while (true) {
                TimedNode<Object> timedNode3 = (TimedNode) timedNode.get();
                if (timedNode3 == null) {
                    break;
                }
                timedNode2 = timedNode;
                timedNode = timedNode3;
            }
            T t = timedNode.a;
            if (t == null) {
                return null;
            }
            if (NotificationLite.isComplete(t) || NotificationLite.isError(t)) {
                return timedNode2.a;
            }
            return t;
        }

        public T[] a(T[] tArr) {
            TimedNode e2 = e();
            int a2 = a(e2);
            if (a2 != 0) {
                if (tArr.length < a2) {
                    tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), a2);
                }
                for (int i = 0; i != a2; i++) {
                    e2 = (TimedNode) e2.get();
                    tArr[i] = e2.a;
                }
                if (tArr.length > a2) {
                    tArr[a2] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        /* access modifiers changed from: 0000 */
        public TimedNode<Object> e() {
            TimedNode<Object> timedNode;
            TimedNode<Object> timedNode2 = this.f;
            long now = this.d.now(this.c) - this.b;
            Object obj = timedNode2.get();
            while (true) {
                TimedNode<Object> timedNode3 = (TimedNode) obj;
                timedNode = timedNode2;
                timedNode2 = timedNode3;
                if (timedNode2 == null || timedNode2.b > now) {
                    return timedNode;
                }
                obj = timedNode2.get();
            }
            return timedNode;
        }

        public void a(ReplaySubscription<T> replaySubscription) {
            long j;
            ReplaySubscription<T> replaySubscription2 = replaySubscription;
            if (replaySubscription.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = replaySubscription2.a;
                TimedNode timedNode = (TimedNode) replaySubscription2.c;
                if (timedNode == null) {
                    timedNode = e();
                }
                int i = 1;
                do {
                    long j2 = replaySubscription2.d.get();
                    long j3 = 0;
                    while (!replaySubscription2.e) {
                        TimedNode timedNode2 = (TimedNode) timedNode.get();
                        if (timedNode2 != null) {
                            T t = timedNode2.a;
                            if (!this.h || timedNode2.get() != null) {
                                if (j2 == 0) {
                                    j = replaySubscription2.d.get() + j3;
                                    if (j == 0) {
                                    }
                                } else {
                                    j = j2;
                                }
                                subscriber.onNext(t);
                                timedNode = timedNode2;
                                j2 = j - 1;
                                j3--;
                            } else {
                                if (NotificationLite.isComplete(t)) {
                                    subscriber.onComplete();
                                } else {
                                    subscriber.onError(NotificationLite.getError(t));
                                }
                                replaySubscription2.c = null;
                                replaySubscription2.e = true;
                                return;
                            }
                        }
                        if (!(j3 == 0 || replaySubscription2.d.get() == Long.MAX_VALUE)) {
                            replaySubscription2.d.addAndGet(j3);
                        }
                        replaySubscription2.c = timedNode;
                        i = replaySubscription2.addAndGet(-i);
                    }
                    replaySubscription2.c = null;
                    return;
                } while (i != 0);
            }
        }

        public int a() {
            return a(e());
        }

        /* access modifiers changed from: 0000 */
        public int a(TimedNode<Object> timedNode) {
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                TimedNode<Object> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2 == null) {
                    T t = timedNode.a;
                    return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? i - 1 : i;
                }
                i++;
                timedNode = timedNode2;
            }
            return i;
        }
    }

    static final class SizeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 3027920763113911982L;
        final int a;
        int b;
        volatile Node<Object> c;
        Node<Object> d;
        volatile boolean e;

        SizeBoundReplayBuffer(int i) {
            this.a = ObjectHelper.verifyPositive(i, "maxSize");
            Node<Object> node = new Node<>(null);
            this.d = node;
            this.c = node;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            if (this.b > this.a) {
                this.b--;
                this.c = (Node) this.c.get();
            }
        }

        public void a(T t) {
            Node<Object> node = new Node<>(t);
            Node<Object> node2 = this.d;
            this.d = node;
            this.b++;
            node2.set(node);
            c();
        }

        public void b(Object obj) {
            lazySet(obj);
            Node<Object> node = new Node<>(obj);
            Node<Object> node2 = this.d;
            this.d = node;
            this.b++;
            node2.set(node);
            this.e = true;
        }

        public T b() {
            Node<Object> node = this.c;
            Node<Object> node2 = null;
            while (true) {
                Node<Object> node3 = (Node) node.get();
                if (node3 == null) {
                    break;
                }
                node2 = node;
                node = node3;
            }
            T t = node.a;
            if (t == null) {
                return null;
            }
            if (NotificationLite.isComplete(t) || NotificationLite.isError(t)) {
                return node2.a;
            }
            return t;
        }

        public T[] a(T[] tArr) {
            Node<Object> node = this.c;
            int a2 = a();
            if (a2 != 0) {
                if (tArr.length < a2) {
                    tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), a2);
                }
                for (int i = 0; i != a2; i++) {
                    node = (Node) node.get();
                    tArr[i] = node.a;
                }
                if (tArr.length > a2) {
                    tArr[a2] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        public void a(ReplaySubscription<T> replaySubscription) {
            long j;
            ReplaySubscription<T> replaySubscription2 = replaySubscription;
            if (replaySubscription.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = replaySubscription2.a;
                Node<Object> node = (Node) replaySubscription2.c;
                if (node == null) {
                    node = this.c;
                }
                int i = 1;
                do {
                    long j2 = replaySubscription2.d.get();
                    long j3 = 0;
                    while (!replaySubscription2.e) {
                        Node<Object> node2 = (Node) node.get();
                        if (node2 != null) {
                            T t = node2.a;
                            if (!this.e || node2.get() != null) {
                                if (j2 == 0) {
                                    j = replaySubscription2.d.get() + j3;
                                    if (j == 0) {
                                    }
                                } else {
                                    j = j2;
                                }
                                subscriber.onNext(t);
                                node = node2;
                                j2 = j - 1;
                                j3--;
                            } else {
                                if (NotificationLite.isComplete(t)) {
                                    subscriber.onComplete();
                                } else {
                                    subscriber.onError(NotificationLite.getError(t));
                                }
                                replaySubscription2.c = null;
                                replaySubscription2.e = true;
                                return;
                            }
                        }
                        if (!(j3 == 0 || replaySubscription2.d.get() == Long.MAX_VALUE)) {
                            replaySubscription2.d.addAndGet(j3);
                        }
                        replaySubscription2.c = node;
                        i = replaySubscription2.addAndGet(-i);
                    }
                    replaySubscription2.c = null;
                    return;
                } while (i != 0);
            }
        }

        public int a() {
            Node<Object> node = this.c;
            int i = 0;
            while (i != Integer.MAX_VALUE) {
                Node<Object> node2 = (Node) node.get();
                if (node2 == null) {
                    T t = node.a;
                    return (NotificationLite.isComplete(t) || NotificationLite.isError(t)) ? i - 1 : i;
                }
                i++;
                node = node2;
            }
            return i;
        }
    }

    static final class TimedNode<T> extends AtomicReference<TimedNode<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T a;
        final long b;

        TimedNode(T t, long j) {
            this.a = t;
            this.b = j;
        }
    }

    static final class UnboundedReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = -4457200895834877300L;
        final List<Object> a;
        volatile boolean b;
        volatile int c;

        UnboundedReplayBuffer(int i) {
            this.a = new ArrayList(ObjectHelper.verifyPositive(i, "capacityHint"));
        }

        public void a(T t) {
            this.a.add(t);
            this.c++;
        }

        public void b(Object obj) {
            lazySet(obj);
            this.a.add(obj);
            this.c++;
            this.b = true;
        }

        public T b() {
            int i = this.c;
            if (i == 0) {
                return null;
            }
            List<Object> list = this.a;
            T t = list.get(i - 1);
            if (!NotificationLite.isComplete(t) && !NotificationLite.isError(t)) {
                return t;
            }
            if (i == 1) {
                return null;
            }
            return list.get(i - 2);
        }

        public T[] a(T[] tArr) {
            int i = this.c;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<Object> list = this.a;
            Object obj = list.get(i - 1);
            if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
                i--;
                if (i == 0) {
                    if (tArr.length != 0) {
                        tArr[0] = null;
                    }
                    return tArr;
                }
            }
            if (tArr.length < i) {
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
            }
            for (int i2 = 0; i2 < i; i2++) {
                tArr[i2] = list.get(i2);
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        public void a(ReplaySubscription<T> replaySubscription) {
            long j;
            ReplaySubscription<T> replaySubscription2 = replaySubscription;
            if (replaySubscription.getAndIncrement() == 0) {
                List<Object> list = this.a;
                Subscriber<? super T> subscriber = replaySubscription2.a;
                Integer num = (Integer) replaySubscription2.c;
                int i = 0;
                if (num != null) {
                    i = num.intValue();
                } else {
                    replaySubscription2.c = Integer.valueOf(0);
                }
                int i2 = 1;
                while (!replaySubscription2.e) {
                    int i3 = this.c;
                    long j2 = replaySubscription2.d.get();
                    long j3 = 0;
                    while (true) {
                        if (i3 == i) {
                            j = j2;
                            break;
                        } else if (replaySubscription2.e) {
                            replaySubscription2.c = null;
                            return;
                        } else {
                            Object obj = list.get(i);
                            if (this.b) {
                                int i4 = i + 1;
                                if (i4 == i3) {
                                    i3 = this.c;
                                    if (i4 == i3) {
                                        if (NotificationLite.isComplete(obj)) {
                                            subscriber.onComplete();
                                        } else {
                                            subscriber.onError(NotificationLite.getError(obj));
                                        }
                                        replaySubscription2.c = null;
                                        replaySubscription2.e = true;
                                        return;
                                    }
                                }
                            }
                            if (j2 == 0) {
                                j = replaySubscription2.d.get() + j3;
                                if (j == 0) {
                                    break;
                                }
                            } else {
                                j = j2;
                            }
                            subscriber.onNext(obj);
                            i++;
                            j2 = j - 1;
                            j3--;
                        }
                    }
                    if (!(j3 == 0 || replaySubscription2.d.get() == Long.MAX_VALUE)) {
                        j = replaySubscription2.d.addAndGet(j3);
                    }
                    if (i == this.c || j == 0) {
                        replaySubscription2.c = Integer.valueOf(i);
                        i2 = replaySubscription2.addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    }
                }
                replaySubscription2.c = null;
            }
        }

        public int a() {
            int i = this.c;
            if (i == 0) {
                return 0;
            }
            int i2 = i - 1;
            Object obj = this.a.get(i2);
            return (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? i2 : i;
        }
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> create() {
        return new ReplayProcessor<>(new UnboundedReplayBuffer(16));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> create(int i) {
        return new ReplayProcessor<>(new UnboundedReplayBuffer(i));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithSize(int i) {
        return new ReplayProcessor<>(new SizeBoundReplayBuffer(i));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(SubsamplingScaleImageView.TILE_SIZE_AUTO, j, timeUnit, scheduler);
        return new ReplayProcessor<>(sizeAndTimeBoundReplayBuffer);
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(i, j, timeUnit, scheduler);
        return new ReplayProcessor<>(sizeAndTimeBoundReplayBuffer);
    }

    ReplayProcessor(ReplayBuffer<T> replayBuffer) {
        this.b = replayBuffer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ReplaySubscription replaySubscription = new ReplaySubscription(subscriber, this);
        subscriber.onSubscribe(replaySubscription);
        if (!a(replaySubscription) || !replaySubscription.e) {
            this.b.a(replaySubscription);
        } else {
            b(replaySubscription);
        }
    }

    public void onSubscribe(Subscription subscription) {
        if (this.c) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (t == null) {
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        } else if (!this.c) {
            ReplayBuffer<T> replayBuffer = this.b;
            replayBuffer.a(t);
            for (ReplaySubscription a : (ReplaySubscription[]) this.d.get()) {
                replayBuffer.a(a);
            }
        }
    }

    public void onError(Throwable th) {
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        Object error = NotificationLite.error(th);
        ReplayBuffer<T> replayBuffer = this.b;
        replayBuffer.b(error);
        for (ReplaySubscription a : (ReplaySubscription[]) this.d.getAndSet(f)) {
            replayBuffer.a(a);
        }
    }

    public void onComplete() {
        if (!this.c) {
            this.c = true;
            Object complete = NotificationLite.complete();
            ReplayBuffer<T> replayBuffer = this.b;
            replayBuffer.b(complete);
            for (ReplaySubscription a : (ReplaySubscription[]) this.d.getAndSet(f)) {
                replayBuffer.a(a);
            }
        }
    }

    public boolean hasSubscribers() {
        return ((ReplaySubscription[]) this.d.get()).length != 0;
    }

    public Throwable getThrowable() {
        Object obj = this.b.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    public T getValue() {
        return this.b.b();
    }

    public Object[] getValues() {
        Object[] values = getValues(g);
        return values == g ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        return this.b.a(tArr);
    }

    public boolean hasComplete() {
        return NotificationLite.isComplete(this.b.get());
    }

    public boolean hasThrowable() {
        return NotificationLite.isError(this.b.get());
    }

    public boolean hasValue() {
        return this.b.a() != 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ReplaySubscription<T> replaySubscription) {
        ReplaySubscription[] replaySubscriptionArr;
        ReplaySubscription[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = (ReplaySubscription[]) this.d.get();
            if (replaySubscriptionArr == f) {
                return false;
            }
            int length = replaySubscriptionArr.length;
            replaySubscriptionArr2 = new ReplaySubscription[(length + 1)];
            System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr2, 0, length);
            replaySubscriptionArr2[length] = replaySubscription;
        } while (!this.d.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(ReplaySubscription<T> replaySubscription) {
        ReplaySubscription<T>[] replaySubscriptionArr;
        ReplaySubscription[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = (ReplaySubscription[]) this.d.get();
            if (replaySubscriptionArr != f && replaySubscriptionArr != e) {
                int length = replaySubscriptionArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (replaySubscriptionArr[i2] == replaySubscription) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        replaySubscriptionArr2 = e;
                    } else {
                        ReplaySubscription[] replaySubscriptionArr3 = new ReplaySubscription[(length - 1)];
                        System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr3, 0, i);
                        System.arraycopy(replaySubscriptionArr, i + 1, replaySubscriptionArr3, i, (length - i) - 1);
                        replaySubscriptionArr2 = replaySubscriptionArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.d.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
    }
}
