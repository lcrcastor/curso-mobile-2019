package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class UnicastSubject<T> extends Subject<T> {
    final SpscLinkedArrayQueue<T> a;
    final AtomicReference<Observer<? super T>> b;
    final AtomicReference<Runnable> c;
    final boolean d;
    volatile boolean e;
    volatile boolean f;
    Throwable g;
    final AtomicBoolean h;
    final BasicIntQueueDisposable<T> i;
    boolean j;

    final class UnicastQueueDisposable extends BasicIntQueueDisposable<T> {
        private static final long serialVersionUID = 7926949470189395511L;

        UnicastQueueDisposable() {
        }

        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastSubject.this.j = true;
            return 2;
        }

        @Nullable
        public T poll() {
            return UnicastSubject.this.a.poll();
        }

        public boolean isEmpty() {
            return UnicastSubject.this.a.isEmpty();
        }

        public void clear() {
            UnicastSubject.this.a.clear();
        }

        public void dispose() {
            if (!UnicastSubject.this.e) {
                UnicastSubject.this.e = true;
                UnicastSubject.this.a();
                UnicastSubject.this.b.lazySet(null);
                if (UnicastSubject.this.i.getAndIncrement() == 0) {
                    UnicastSubject.this.b.lazySet(null);
                    UnicastSubject.this.a.clear();
                }
            }
        }

        public boolean isDisposed() {
            return UnicastSubject.this.e;
        }
    }

    @CheckReturnValue
    public static <T> UnicastSubject<T> create() {
        return new UnicastSubject<>(bufferSize(), true);
    }

    @CheckReturnValue
    public static <T> UnicastSubject<T> create(int i2) {
        return new UnicastSubject<>(i2, true);
    }

    @CheckReturnValue
    public static <T> UnicastSubject<T> create(int i2, Runnable runnable) {
        return new UnicastSubject<>(i2, runnable, true);
    }

    @CheckReturnValue
    @Experimental
    public static <T> UnicastSubject<T> create(int i2, Runnable runnable, boolean z) {
        return new UnicastSubject<>(i2, runnable, z);
    }

    @CheckReturnValue
    @Experimental
    public static <T> UnicastSubject<T> create(boolean z) {
        return new UnicastSubject<>(bufferSize(), z);
    }

    UnicastSubject(int i2, boolean z) {
        this.a = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.c = new AtomicReference<>();
        this.d = z;
        this.b = new AtomicReference<>();
        this.h = new AtomicBoolean();
        this.i = new UnicastQueueDisposable();
    }

    UnicastSubject(int i2, Runnable runnable, boolean z) {
        this.a = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.c = new AtomicReference<>(ObjectHelper.requireNonNull(runnable, "onTerminate"));
        this.d = z;
        this.b = new AtomicReference<>();
        this.h = new AtomicBoolean();
        this.i = new UnicastQueueDisposable();
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        if (this.h.get() || !this.h.compareAndSet(false, true)) {
            EmptyDisposable.error((Throwable) new IllegalStateException("Only a single observer allowed."), observer);
        } else {
            observer.onSubscribe(this.i);
            this.b.lazySet(observer);
            if (this.e) {
                this.b.lazySet(null);
                return;
            }
            b();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        Runnable runnable = (Runnable) this.c.get();
        if (runnable != null && this.c.compareAndSet(runnable, null)) {
            runnable.run();
        }
    }

    public void onSubscribe(Disposable disposable) {
        if (this.f || this.e) {
            disposable.dispose();
        }
    }

    public void onNext(T t) {
        if (!this.f && !this.e) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            this.a.offer(t);
            b();
        }
    }

    public void onError(Throwable th) {
        if (this.f || this.e) {
            RxJavaPlugins.onError(th);
            return;
        }
        if (th == null) {
            th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.g = th;
        this.f = true;
        a();
        b();
    }

    public void onComplete() {
        if (!this.f && !this.e) {
            this.f = true;
            a();
            b();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.a;
        boolean z = !this.d;
        boolean z2 = true;
        int i2 = 1;
        while (!this.e) {
            boolean z3 = this.f;
            Object poll = this.a.poll();
            boolean z4 = poll == null;
            if (z3) {
                if (z && z2) {
                    if (!a(spscLinkedArrayQueue, observer)) {
                        z2 = false;
                    } else {
                        return;
                    }
                }
                if (z4) {
                    c(observer);
                    return;
                }
            }
            if (z4) {
                i2 = this.i.addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            } else {
                observer.onNext(poll);
            }
        }
        this.b.lazySet(null);
        spscLinkedArrayQueue.clear();
    }

    /* access modifiers changed from: 0000 */
    public void b(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.a;
        int i2 = 1;
        boolean z = !this.d;
        while (!this.e) {
            boolean z2 = this.f;
            if (!z || !z2 || !a(spscLinkedArrayQueue, observer)) {
                observer.onNext(null);
                if (z2) {
                    c(observer);
                    return;
                }
                i2 = this.i.addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            } else {
                return;
            }
        }
        this.b.lazySet(null);
        spscLinkedArrayQueue.clear();
    }

    /* access modifiers changed from: 0000 */
    public void c(Observer<? super T> observer) {
        this.b.lazySet(null);
        Throwable th = this.g;
        if (th != null) {
            observer.onError(th);
        } else {
            observer.onComplete();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(SimpleQueue<T> simpleQueue, Observer<? super T> observer) {
        Throwable th = this.g;
        if (th == null) {
            return false;
        }
        this.b.lazySet(null);
        simpleQueue.clear();
        observer.onError(th);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.i.getAndIncrement() == 0) {
            Observer observer = (Observer) this.b.get();
            int i2 = 1;
            while (observer == null) {
                i2 = this.i.addAndGet(-i2);
                if (i2 != 0) {
                    observer = (Observer) this.b.get();
                } else {
                    return;
                }
            }
            if (this.j) {
                b(observer);
            } else {
                a(observer);
            }
        }
    }

    public boolean hasObservers() {
        return this.b.get() != null;
    }

    public Throwable getThrowable() {
        if (this.f) {
            return this.g;
        }
        return null;
    }

    public boolean hasThrowable() {
        return this.f && this.g != null;
    }

    public boolean hasComplete() {
        return this.f && this.g == null;
    }
}
