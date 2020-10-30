package io.reactivex.subjects;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ReplaySubject<T> extends Subject<T> {
    static final ReplayDisposable[] c = new ReplayDisposable[0];
    static final ReplayDisposable[] d = new ReplayDisposable[0];
    private static final Object[] f = new Object[0];
    final ReplayBuffer<T> a;
    final AtomicReference<ReplayDisposable<T>[]> b = new AtomicReference<>(c);
    boolean e;

    static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T a;

        Node(T t) {
            this.a = t;
        }
    }

    interface ReplayBuffer<T> {
        int a();

        void a(ReplayDisposable<T> replayDisposable);

        void a(T t);

        T[] a(T[] tArr);

        T b();

        void b(Object obj);

        boolean compareAndSet(Object obj, Object obj2);

        Object get();
    }

    static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 466549804534799122L;
        final Observer<? super T> a;
        final ReplaySubject<T> b;
        Object c;
        volatile boolean d;

        ReplayDisposable(Observer<? super T> observer, ReplaySubject<T> replaySubject) {
            this.a = observer;
            this.b = replaySubject;
        }

        public void dispose() {
            if (!this.d) {
                this.d = true;
                this.b.b(this);
            }
        }

        public boolean isDisposed() {
            return this.d;
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = -8056260896137901749L;
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
            TimedNode<Object> timedNode = new TimedNode<>(obj, Long.MAX_VALUE);
            TimedNode<Object> timedNode2 = this.g;
            this.g = timedNode;
            this.e++;
            timedNode2.lazySet(timedNode);
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

        public void a(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() == 0) {
                Observer<? super T> observer = replayDisposable.a;
                TimedNode timedNode = (TimedNode) replayDisposable.c;
                if (timedNode == null) {
                    timedNode = e();
                }
                int i = 1;
                while (!replayDisposable.d) {
                    while (!replayDisposable.d) {
                        TimedNode timedNode2 = (TimedNode) timedNode.get();
                        if (timedNode2 != null) {
                            T t = timedNode2.a;
                            if (!this.h || timedNode2.get() != null) {
                                observer.onNext(t);
                                timedNode = timedNode2;
                            } else {
                                if (NotificationLite.isComplete(t)) {
                                    observer.onComplete();
                                } else {
                                    observer.onError(NotificationLite.getError(t));
                                }
                                replayDisposable.c = null;
                                replayDisposable.d = true;
                                return;
                            }
                        } else if (timedNode.get() == null) {
                            replayDisposable.c = timedNode;
                            i = replayDisposable.addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        }
                    }
                    replayDisposable.c = null;
                    return;
                }
                replayDisposable.c = null;
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
        private static final long serialVersionUID = 1107649250281456395L;
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
            Node<Object> node = new Node<>(obj);
            Node<Object> node2 = this.d;
            this.d = node;
            this.b++;
            node2.lazySet(node);
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

        public void a(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() == 0) {
                Observer<? super T> observer = replayDisposable.a;
                Node<Object> node = (Node) replayDisposable.c;
                if (node == null) {
                    node = this.c;
                }
                int i = 1;
                while (!replayDisposable.d) {
                    Node<Object> node2 = (Node) node.get();
                    if (node2 != null) {
                        T t = node2.a;
                        if (!this.e || node2.get() != null) {
                            observer.onNext(t);
                            node = node2;
                        } else {
                            if (NotificationLite.isComplete(t)) {
                                observer.onComplete();
                            } else {
                                observer.onError(NotificationLite.getError(t));
                            }
                            replayDisposable.c = null;
                            replayDisposable.d = true;
                            return;
                        }
                    } else if (node.get() != null) {
                        continue;
                    } else {
                        replayDisposable.c = node;
                        i = replayDisposable.addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    }
                }
                replayDisposable.c = null;
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
        private static final long serialVersionUID = -733876083048047795L;
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

        public void a(ReplayDisposable<T> replayDisposable) {
            if (replayDisposable.getAndIncrement() == 0) {
                List<Object> list = this.a;
                Observer<? super T> observer = replayDisposable.a;
                Integer num = (Integer) replayDisposable.c;
                int i = 0;
                if (num != null) {
                    i = num.intValue();
                } else {
                    replayDisposable.c = Integer.valueOf(0);
                }
                int i2 = 1;
                while (!replayDisposable.d) {
                    int i3 = this.c;
                    while (i3 != i) {
                        if (replayDisposable.d) {
                            replayDisposable.c = null;
                            return;
                        }
                        Object obj = list.get(i);
                        if (this.b) {
                            int i4 = i + 1;
                            if (i4 == i3) {
                                i3 = this.c;
                                if (i4 == i3) {
                                    if (NotificationLite.isComplete(obj)) {
                                        observer.onComplete();
                                    } else {
                                        observer.onError(NotificationLite.getError(obj));
                                    }
                                    replayDisposable.c = null;
                                    replayDisposable.d = true;
                                    return;
                                }
                            } else {
                                continue;
                            }
                        }
                        observer.onNext(obj);
                        i++;
                    }
                    if (i == this.c) {
                        replayDisposable.c = Integer.valueOf(i);
                        i2 = replayDisposable.addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    }
                }
                replayDisposable.c = null;
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
    public static <T> ReplaySubject<T> create() {
        return new ReplaySubject<>(new UnboundedReplayBuffer(16));
    }

    @CheckReturnValue
    public static <T> ReplaySubject<T> create(int i) {
        return new ReplaySubject<>(new UnboundedReplayBuffer(i));
    }

    @CheckReturnValue
    public static <T> ReplaySubject<T> createWithSize(int i) {
        return new ReplaySubject<>(new SizeBoundReplayBuffer(i));
    }

    @CheckReturnValue
    public static <T> ReplaySubject<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(SubsamplingScaleImageView.TILE_SIZE_AUTO, j, timeUnit, scheduler);
        return new ReplaySubject<>(sizeAndTimeBoundReplayBuffer);
    }

    @CheckReturnValue
    public static <T> ReplaySubject<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(i, j, timeUnit, scheduler);
        return new ReplaySubject<>(sizeAndTimeBoundReplayBuffer);
    }

    ReplaySubject(ReplayBuffer<T> replayBuffer) {
        this.a = replayBuffer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        ReplayDisposable replayDisposable = new ReplayDisposable(observer, this);
        observer.onSubscribe(replayDisposable);
        if (!replayDisposable.d) {
            if (!a(replayDisposable) || !replayDisposable.d) {
                this.a.a(replayDisposable);
            } else {
                b(replayDisposable);
            }
        }
    }

    public void onSubscribe(Disposable disposable) {
        if (this.e) {
            disposable.dispose();
        }
    }

    public void onNext(T t) {
        if (t == null) {
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        } else if (!this.e) {
            ReplayBuffer<T> replayBuffer = this.a;
            replayBuffer.a(t);
            for (ReplayDisposable a2 : (ReplayDisposable[]) this.b.get()) {
                replayBuffer.a(a2);
            }
        }
    }

    public void onError(Throwable th) {
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (this.e) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.e = true;
        Object error = NotificationLite.error(th);
        ReplayBuffer<T> replayBuffer = this.a;
        replayBuffer.b(error);
        for (ReplayDisposable a2 : a(error)) {
            replayBuffer.a(a2);
        }
    }

    public void onComplete() {
        if (!this.e) {
            this.e = true;
            Object complete = NotificationLite.complete();
            ReplayBuffer<T> replayBuffer = this.a;
            replayBuffer.b(complete);
            for (ReplayDisposable a2 : a(complete)) {
                replayBuffer.a(a2);
            }
        }
    }

    public boolean hasObservers() {
        return ((ReplayDisposable[]) this.b.get()).length != 0;
    }

    public Throwable getThrowable() {
        Object obj = this.a.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    public T getValue() {
        return this.a.b();
    }

    public Object[] getValues() {
        Object[] values = getValues(f);
        return values == f ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        return this.a.a(tArr);
    }

    public boolean hasComplete() {
        return NotificationLite.isComplete(this.a.get());
    }

    public boolean hasThrowable() {
        return NotificationLite.isError(this.a.get());
    }

    public boolean hasValue() {
        return this.a.a() != 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = (ReplayDisposable[]) this.b.get();
            if (replayDisposableArr == d) {
                return false;
            }
            int length = replayDisposableArr.length;
            replayDisposableArr2 = new ReplayDisposable[(length + 1)];
            System.arraycopy(replayDisposableArr, 0, replayDisposableArr2, 0, length);
            replayDisposableArr2[length] = replayDisposable;
        } while (!this.b.compareAndSet(replayDisposableArr, replayDisposableArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(ReplayDisposable<T> replayDisposable) {
        ReplayDisposable<T>[] replayDisposableArr;
        ReplayDisposable[] replayDisposableArr2;
        do {
            replayDisposableArr = (ReplayDisposable[]) this.b.get();
            if (replayDisposableArr != d && replayDisposableArr != c) {
                int length = replayDisposableArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (replayDisposableArr[i2] == replayDisposable) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        replayDisposableArr2 = c;
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
        } while (!this.b.compareAndSet(replayDisposableArr, replayDisposableArr2));
    }

    /* access modifiers changed from: 0000 */
    public ReplayDisposable<T>[] a(Object obj) {
        if (this.a.compareAndSet(null, obj)) {
            return (ReplayDisposable[]) this.b.getAndSet(d);
        }
        return d;
    }
}
