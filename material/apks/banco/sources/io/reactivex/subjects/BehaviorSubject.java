package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class BehaviorSubject<T> extends Subject<T> {
    static final BehaviorDisposable[] c = new BehaviorDisposable[0];
    static final BehaviorDisposable[] d = new BehaviorDisposable[0];
    private static final Object[] j = new Object[0];
    final AtomicReference<Object> a;
    final AtomicReference<BehaviorDisposable<T>[]> b;
    final ReadWriteLock e;
    final Lock f;
    final Lock g;
    final AtomicReference<Throwable> h;
    long i;

    static final class BehaviorDisposable<T> implements Disposable, NonThrowingPredicate<Object> {
        final Observer<? super T> a;
        final BehaviorSubject<T> b;
        boolean c;
        boolean d;
        AppendOnlyLinkedArrayList<Object> e;
        boolean f;
        volatile boolean g;
        long h;

        BehaviorDisposable(Observer<? super T> observer, BehaviorSubject<T> behaviorSubject) {
            this.a = observer;
            this.b = behaviorSubject;
        }

        public void dispose() {
            if (!this.g) {
                this.g = true;
                this.b.b(this);
            }
        }

        public boolean isDisposed() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            if (r0 == null) goto L_0x003d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
            if (test(r0) == false) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x003a, code lost:
            b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a() {
            /*
                r4 = this;
                boolean r0 = r4.g
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                monitor-enter(r4)
                boolean r0 = r4.g     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x000c
                monitor-exit(r4)     // Catch:{ all -> 0x003e }
                return
            L_0x000c:
                boolean r0 = r4.c     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x0012
                monitor-exit(r4)     // Catch:{ all -> 0x003e }
                return
            L_0x0012:
                io.reactivex.subjects.BehaviorSubject<T> r0 = r4.b     // Catch:{ all -> 0x003e }
                java.util.concurrent.locks.Lock r1 = r0.f     // Catch:{ all -> 0x003e }
                r1.lock()     // Catch:{ all -> 0x003e }
                long r2 = r0.i     // Catch:{ all -> 0x003e }
                r4.h = r2     // Catch:{ all -> 0x003e }
                java.util.concurrent.atomic.AtomicReference<java.lang.Object> r0 = r0.a     // Catch:{ all -> 0x003e }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x003e }
                r1.unlock()     // Catch:{ all -> 0x003e }
                r1 = 1
                if (r0 == 0) goto L_0x002b
                r2 = 1
                goto L_0x002c
            L_0x002b:
                r2 = 0
            L_0x002c:
                r4.d = r2     // Catch:{ all -> 0x003e }
                r4.c = r1     // Catch:{ all -> 0x003e }
                monitor-exit(r4)     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x003d
                boolean r0 = r4.test(r0)
                if (r0 == 0) goto L_0x003a
                return
            L_0x003a:
                r4.b()
            L_0x003d:
                return
            L_0x003e:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x003e }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.BehaviorSubject.BehaviorDisposable.a():void");
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0031, code lost:
            r3.f = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(java.lang.Object r4, long r5) {
            /*
                r3 = this;
                boolean r0 = r3.g
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                boolean r0 = r3.f
                if (r0 != 0) goto L_0x0037
                monitor-enter(r3)
                boolean r0 = r3.g     // Catch:{ all -> 0x0034 }
                if (r0 == 0) goto L_0x0010
                monitor-exit(r3)     // Catch:{ all -> 0x0034 }
                return
            L_0x0010:
                long r0 = r3.h     // Catch:{ all -> 0x0034 }
                int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
                if (r2 != 0) goto L_0x0018
                monitor-exit(r3)     // Catch:{ all -> 0x0034 }
                return
            L_0x0018:
                boolean r5 = r3.d     // Catch:{ all -> 0x0034 }
                if (r5 == 0) goto L_0x002d
                io.reactivex.internal.util.AppendOnlyLinkedArrayList<java.lang.Object> r5 = r3.e     // Catch:{ all -> 0x0034 }
                if (r5 != 0) goto L_0x0028
                io.reactivex.internal.util.AppendOnlyLinkedArrayList r5 = new io.reactivex.internal.util.AppendOnlyLinkedArrayList     // Catch:{ all -> 0x0034 }
                r6 = 4
                r5.<init>(r6)     // Catch:{ all -> 0x0034 }
                r3.e = r5     // Catch:{ all -> 0x0034 }
            L_0x0028:
                r5.add(r4)     // Catch:{ all -> 0x0034 }
                monitor-exit(r3)     // Catch:{ all -> 0x0034 }
                return
            L_0x002d:
                r5 = 1
                r3.c = r5     // Catch:{ all -> 0x0034 }
                monitor-exit(r3)     // Catch:{ all -> 0x0034 }
                r3.f = r5
                goto L_0x0037
            L_0x0034:
                r4 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0034 }
                throw r4
            L_0x0037:
                r3.test(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.BehaviorSubject.BehaviorDisposable.a(java.lang.Object, long):void");
        }

        public boolean test(Object obj) {
            return this.g || NotificationLite.accept(obj, this.a);
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
            r0.forEachWhile(r2);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r2 = this;
            L_0x0000:
                boolean r0 = r2.g
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                monitor-enter(r2)
                io.reactivex.internal.util.AppendOnlyLinkedArrayList<java.lang.Object> r0 = r2.e     // Catch:{ all -> 0x0017 }
                if (r0 != 0) goto L_0x000f
                r0 = 0
                r2.d = r0     // Catch:{ all -> 0x0017 }
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                return
            L_0x000f:
                r1 = 0
                r2.e = r1     // Catch:{ all -> 0x0017 }
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                r0.forEachWhile(r2)
                goto L_0x0000
            L_0x0017:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.BehaviorSubject.BehaviorDisposable.b():void");
        }
    }

    @CheckReturnValue
    public static <T> BehaviorSubject<T> create() {
        return new BehaviorSubject<>();
    }

    @CheckReturnValue
    public static <T> BehaviorSubject<T> createDefault(T t) {
        return new BehaviorSubject<>(t);
    }

    BehaviorSubject() {
        this.e = new ReentrantReadWriteLock();
        this.f = this.e.readLock();
        this.g = this.e.writeLock();
        this.b = new AtomicReference<>(c);
        this.a = new AtomicReference<>();
        this.h = new AtomicReference<>();
    }

    BehaviorSubject(T t) {
        this();
        this.a.lazySet(ObjectHelper.requireNonNull(t, "defaultValue is null"));
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        BehaviorDisposable behaviorDisposable = new BehaviorDisposable(observer, this);
        observer.onSubscribe(behaviorDisposable);
        if (!a(behaviorDisposable)) {
            Throwable th = (Throwable) this.h.get();
            if (th == ExceptionHelper.TERMINATED) {
                observer.onComplete();
            } else {
                observer.onError(th);
            }
        } else if (behaviorDisposable.g) {
            b(behaviorDisposable);
        } else {
            behaviorDisposable.a();
        }
    }

    public void onSubscribe(Disposable disposable) {
        if (this.h.get() != null) {
            disposable.dispose();
        }
    }

    public void onNext(T t) {
        if (t == null) {
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        } else if (this.h.get() == null) {
            Object next = NotificationLite.next(t);
            b(next);
            for (BehaviorDisposable a2 : (BehaviorDisposable[]) this.b.get()) {
                a2.a(next, this.i);
            }
        }
    }

    public void onError(Throwable th) {
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (!this.h.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object error = NotificationLite.error(th);
        for (BehaviorDisposable a2 : a(error)) {
            a2.a(error, this.i);
        }
    }

    public void onComplete() {
        if (this.h.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object complete = NotificationLite.complete();
            for (BehaviorDisposable a2 : a(complete)) {
                a2.a(complete, this.i);
            }
        }
    }

    public boolean hasObservers() {
        return ((BehaviorDisposable[]) this.b.get()).length != 0;
    }

    public Throwable getThrowable() {
        Object obj = this.a.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    public T getValue() {
        Object obj = this.a.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return NotificationLite.getValue(obj);
    }

    public Object[] getValues() {
        Object[] values = getValues(j);
        return values == j ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        Object obj = this.a.get();
        if (obj == null || NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }
        T value = NotificationLite.getValue(obj);
        if (tArr.length != 0) {
            tArr[0] = value;
            if (tArr.length != 1) {
                tArr[1] = null;
            }
        } else {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1);
            tArr[0] = value;
        }
        return tArr;
    }

    public boolean hasComplete() {
        return NotificationLite.isComplete(this.a.get());
    }

    public boolean hasThrowable() {
        return NotificationLite.isError(this.a.get());
    }

    public boolean hasValue() {
        Object obj = this.a.get();
        return obj != null && !NotificationLite.isComplete(obj) && !NotificationLite.isError(obj);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = (BehaviorDisposable[]) this.b.get();
            if (behaviorDisposableArr == d) {
                return false;
            }
            int length = behaviorDisposableArr.length;
            behaviorDisposableArr2 = new BehaviorDisposable[(length + 1)];
            System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr2, 0, length);
            behaviorDisposableArr2[length] = behaviorDisposable;
        } while (!this.b.compareAndSet(behaviorDisposableArr, behaviorDisposableArr2));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable<T>[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = (BehaviorDisposable[]) this.b.get();
            if (behaviorDisposableArr != d && behaviorDisposableArr != c) {
                int length = behaviorDisposableArr.length;
                int i2 = -1;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    } else if (behaviorDisposableArr[i3] == behaviorDisposable) {
                        i2 = i3;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i2 >= 0) {
                    if (length == 1) {
                        behaviorDisposableArr2 = c;
                    } else {
                        BehaviorDisposable[] behaviorDisposableArr3 = new BehaviorDisposable[(length - 1)];
                        System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr3, 0, i2);
                        System.arraycopy(behaviorDisposableArr, i2 + 1, behaviorDisposableArr3, i2, (length - i2) - 1);
                        behaviorDisposableArr2 = behaviorDisposableArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.b.compareAndSet(behaviorDisposableArr, behaviorDisposableArr2));
    }

    /* access modifiers changed from: 0000 */
    public BehaviorDisposable<T>[] a(Object obj) {
        BehaviorDisposable<T>[] behaviorDisposableArr = (BehaviorDisposable[]) this.b.get();
        if (behaviorDisposableArr != d) {
            behaviorDisposableArr = (BehaviorDisposable[]) this.b.getAndSet(d);
            if (behaviorDisposableArr != d) {
                b(obj);
            }
        }
        return behaviorDisposableArr;
    }

    /* access modifiers changed from: 0000 */
    public void b(Object obj) {
        this.g.lock();
        try {
            this.i++;
            this.a.lazySet(obj);
        } finally {
            this.g.unlock();
        }
    }
}
