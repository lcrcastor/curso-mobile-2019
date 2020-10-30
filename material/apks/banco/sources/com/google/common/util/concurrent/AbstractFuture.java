package com.google.common.util.concurrent;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import sun.misc.Unsafe;

@GwtCompatible(emulated = true)
public abstract class AbstractFuture<V> implements ListenableFuture<V> {
    private static final boolean a = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", Reintento.Reintento_Falso));
    private static final Logger b = Logger.getLogger(AbstractFuture.class.getName());
    /* access modifiers changed from: private */
    public static final AtomicHelper c;
    private static final Object d = new Object();
    /* access modifiers changed from: private */
    public volatile Object e;
    /* access modifiers changed from: private */
    public volatile Listener f;
    /* access modifiers changed from: private */
    public volatile Waiter g;

    static final class Cancellation {
        final boolean a;
        @Nullable
        final Throwable b;

        Cancellation(boolean z, @Nullable Throwable th) {
            this.a = z;
            this.b = th;
        }
    }

    static final class Listener {
        static final Listener a = new Listener(null, null);
        final Runnable b;
        final Executor c;
        @Nullable
        Listener d;

        Listener(Runnable runnable, Executor executor) {
            this.b = runnable;
            this.c = executor;
        }
    }

    static abstract class TrustedFuture<V> extends AbstractFuture<V> {
        TrustedFuture() {
        }

        @CanIgnoreReturnValue
        public final V get() {
            return AbstractFuture.super.get();
        }

        @CanIgnoreReturnValue
        public final V get(long j, TimeUnit timeUnit) {
            return AbstractFuture.super.get(j, timeUnit);
        }

        public final boolean isDone() {
            return AbstractFuture.super.isDone();
        }

        public final boolean isCancelled() {
            return AbstractFuture.super.isCancelled();
        }

        public final void addListener(Runnable runnable, Executor executor) {
            AbstractFuture.super.addListener(runnable, executor);
        }

        @CanIgnoreReturnValue
        public final boolean cancel(boolean z) {
            return AbstractFuture.super.cancel(z);
        }
    }

    static final class Waiter {
        static final Waiter a = new Waiter(false);
        @Nullable
        volatile Thread b;
        @Nullable
        volatile Waiter c;

        Waiter(boolean z) {
        }

        Waiter() {
            AbstractFuture.c.a(this, Thread.currentThread());
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter) {
            AbstractFuture.c.a(this, waiter);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            Thread thread = this.b;
            if (thread != null) {
                this.b = null;
                LockSupport.unpark(thread);
            }
        }
    }

    static abstract class AtomicHelper {
        /* access modifiers changed from: 0000 */
        public abstract void a(Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: 0000 */
        public abstract void a(Waiter waiter, Thread thread);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        private AtomicHelper() {
        }
    }

    static final class Failure {
        static final Failure a = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable b;

        Failure(Throwable th) {
            this.b = (Throwable) Preconditions.checkNotNull(th);
        }
    }

    static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<Waiter, Thread> a;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> b;
        final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> c;
        final AtomicReferenceFieldUpdater<AbstractFuture, Listener> d;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> e;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicReferenceFieldUpdater2;
            this.c = atomicReferenceFieldUpdater3;
            this.d = atomicReferenceFieldUpdater4;
            this.e = atomicReferenceFieldUpdater5;
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Thread thread) {
            this.a.lazySet(waiter, thread);
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Waiter waiter2) {
            this.b.lazySet(waiter, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.c.compareAndSet(abstractFuture, waiter, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return this.d.compareAndSet(abstractFuture, listener, listener2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return this.e.compareAndSet(abstractFuture, obj, obj2);
        }
    }

    static final class SetFuture<V> implements Runnable {
        final AbstractFuture<V> a;
        final ListenableFuture<? extends V> b;

        SetFuture(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.a = abstractFuture;
            this.b = listenableFuture;
        }

        public void run() {
            if (this.a.e == this) {
                if (AbstractFuture.c.a(this.a, (Object) this, AbstractFuture.a(this.b))) {
                    AbstractFuture.e(this.a);
                }
            }
        }
    }

    static final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Thread thread) {
            waiter.b = thread;
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Waiter waiter2) {
            waiter.c = waiter2;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                if (abstractFuture.g != waiter) {
                    return false;
                }
                abstractFuture.g = waiter2;
                return true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                if (abstractFuture.f != listener) {
                    return false;
                }
                abstractFuture.f = listener2;
                return true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                if (abstractFuture.e != obj) {
                    return false;
                }
                abstractFuture.e = obj2;
                return true;
            }
        }
    }

    static final class UnsafeAtomicHelper extends AtomicHelper {
        static final Unsafe a;
        static final long b;
        static final long c;
        static final long d;
        static final long e;
        static final long f;

        private UnsafeAtomicHelper() {
            super();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x005f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x006b, code lost:
            throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:3:?, code lost:
            r0 = (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.AnonymousClass1());
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0005 */
        static {
            /*
                sun.misc.Unsafe r0 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0005 }
                goto L_0x0010
            L_0x0005:
                com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1 r0 = new com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1     // Catch:{ PrivilegedActionException -> 0x005f }
                r0.<init>()     // Catch:{ PrivilegedActionException -> 0x005f }
                java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ PrivilegedActionException -> 0x005f }
                sun.misc.Unsafe r0 = (sun.misc.Unsafe) r0     // Catch:{ PrivilegedActionException -> 0x005f }
            L_0x0010:
                java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r1 = com.google.common.util.concurrent.AbstractFuture.class
                java.lang.String r2 = "waiters"
                java.lang.reflect.Field r2 = r1.getDeclaredField(r2)     // Catch:{ Exception -> 0x0055 }
                long r2 = r0.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0055 }
                c = r2     // Catch:{ Exception -> 0x0055 }
                java.lang.String r2 = "listeners"
                java.lang.reflect.Field r2 = r1.getDeclaredField(r2)     // Catch:{ Exception -> 0x0055 }
                long r2 = r0.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0055 }
                b = r2     // Catch:{ Exception -> 0x0055 }
                java.lang.String r2 = "value"
                java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ Exception -> 0x0055 }
                long r1 = r0.objectFieldOffset(r1)     // Catch:{ Exception -> 0x0055 }
                d = r1     // Catch:{ Exception -> 0x0055 }
                java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r1 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
                java.lang.String r2 = "b"
                java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ Exception -> 0x0055 }
                long r1 = r0.objectFieldOffset(r1)     // Catch:{ Exception -> 0x0055 }
                e = r1     // Catch:{ Exception -> 0x0055 }
                java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r1 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
                java.lang.String r2 = "c"
                java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ Exception -> 0x0055 }
                long r1 = r0.objectFieldOffset(r1)     // Catch:{ Exception -> 0x0055 }
                f = r1     // Catch:{ Exception -> 0x0055 }
                a = r0     // Catch:{ Exception -> 0x0055 }
                return
            L_0x0055:
                r0 = move-exception
                com.google.common.base.Throwables.throwIfUnchecked(r0)
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                r1.<init>(r0)
                throw r1
            L_0x005f:
                r0 = move-exception
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                java.lang.String r2 = "Could not initialize intrinsics"
                java.lang.Throwable r0 = r0.getCause()
                r1.<init>(r2, r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.<clinit>():void");
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Thread thread) {
            a.putObject(waiter, e, thread);
        }

        /* access modifiers changed from: 0000 */
        public void a(Waiter waiter, Waiter waiter2) {
            a.putObject(waiter, f, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return a.compareAndSwapObject(abstractFuture, c, waiter, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return a.compareAndSwapObject(abstractFuture, b, listener, listener2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return a.compareAndSwapObject(abstractFuture, d, obj, obj2);
        }
    }

    /* access modifiers changed from: protected */
    @Beta
    public void afterDone() {
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    static {
        AtomicHelper atomicHelper;
        try {
            atomicHelper = new UnsafeAtomicHelper();
        } catch (Throwable th) {
            b.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", th);
            b.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
            atomicHelper = new SynchronizedHelper();
        }
        c = atomicHelper;
        Class<LockSupport> cls = LockSupport.class;
    }

    private void a(Waiter waiter) {
        waiter.b = null;
        while (true) {
            Waiter waiter2 = this.g;
            if (waiter2 != Waiter.a) {
                Waiter waiter3 = null;
                while (waiter2 != null) {
                    Waiter waiter4 = waiter2.c;
                    if (waiter2.b != null) {
                        waiter3 = waiter2;
                    } else if (waiter3 != null) {
                        waiter3.c = waiter4;
                        if (waiter3.b == null) {
                        }
                    } else if (!c.a(this, waiter2, waiter4)) {
                    }
                    waiter2 = waiter4;
                }
                return;
            }
            return;
        }
    }

    protected AbstractFuture() {
    }

    @CanIgnoreReturnValue
    public V get(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.e;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return a(obj);
        }
        long nanoTime = nanos > 0 ? System.nanoTime() + nanos : 0;
        if (nanos >= 1000) {
            Waiter waiter = this.g;
            if (waiter != Waiter.a) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.a(waiter);
                    if (c.a(this, waiter, waiter2)) {
                        while (true) {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                a(waiter2);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.e;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return a(obj2);
                            }
                            long nanoTime2 = nanoTime - System.nanoTime();
                            if (nanoTime2 < 1000) {
                                a(waiter2);
                                nanos = nanoTime2;
                                break;
                            }
                            nanos = nanoTime2;
                        }
                    } else {
                        waiter = this.g;
                    }
                } while (waiter != Waiter.a);
            }
            return a(this.e);
        }
        while (nanos > 0) {
            Object obj3 = this.e;
            if ((obj3 != null) && (!(obj3 instanceof SetFuture))) {
                return a(obj3);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = nanoTime - System.nanoTime();
        }
        throw new TimeoutException();
    }

    @CanIgnoreReturnValue
    public V get() {
        Object obj;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj2 = this.e;
        if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
            return a(obj2);
        }
        Waiter waiter = this.g;
        if (waiter != Waiter.a) {
            Waiter waiter2 = new Waiter();
            do {
                waiter2.a(waiter);
                if (c.a(this, waiter, waiter2)) {
                    do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                            a(waiter2);
                            throw new InterruptedException();
                        }
                        obj = this.e;
                    } while (!((obj != null) & (!(obj instanceof SetFuture))));
                    return a(obj);
                }
                waiter = this.g;
            } while (waiter != Waiter.a);
        }
        return a(this.e);
    }

    private V a(Object obj) {
        if (obj instanceof Cancellation) {
            throw a("Task was cancelled.", ((Cancellation) obj).b);
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).b);
        } else if (obj == d) {
            return null;
        } else {
            return obj;
        }
    }

    public boolean isDone() {
        Object obj = this.e;
        boolean z = false;
        boolean z2 = obj != null;
        if (!(obj instanceof SetFuture)) {
            z = true;
        }
        return z2 & z;
    }

    public boolean isCancelled() {
        return this.e instanceof Cancellation;
    }

    @CanIgnoreReturnValue
    public boolean cancel(boolean z) {
        Object obj = this.e;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        Cancellation cancellation = new Cancellation(z, a ? new CancellationException("Future.cancel() was called.") : null);
        Object obj2 = obj;
        boolean z2 = false;
        AbstractFuture abstractFuture = this;
        while (true) {
            if (c.a(abstractFuture, obj2, (Object) cancellation)) {
                if (z) {
                    abstractFuture.interruptTask();
                }
                e(abstractFuture);
                if (!(obj2 instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture<? extends V> listenableFuture = ((SetFuture) obj2).b;
                if (listenableFuture instanceof TrustedFuture) {
                    abstractFuture = (AbstractFuture) listenableFuture;
                    obj2 = abstractFuture.e;
                    if (!(obj2 == null) && !(obj2 instanceof SetFuture)) {
                        return true;
                    }
                    z2 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            } else {
                obj2 = abstractFuture.e;
                if (!(obj2 instanceof SetFuture)) {
                    return z2;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        Object obj = this.e;
        return (obj instanceof Cancellation) && ((Cancellation) obj).a;
    }

    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        Listener listener = this.f;
        if (listener != Listener.a) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.d = listener;
                if (!c.a(this, listener, listener2)) {
                    listener = this.f;
                } else {
                    return;
                }
            } while (listener != Listener.a);
        }
        a(runnable, executor);
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    public boolean set(@Nullable V v) {
        if (v == null) {
            v = d;
        }
        if (!c.a(this, (Object) null, (Object) v)) {
            return false;
        }
        e(this);
        return true;
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    public boolean setException(Throwable th) {
        if (!c.a(this, (Object) null, (Object) new Failure((Throwable) Preconditions.checkNotNull(th)))) {
            return false;
        }
        e(this);
        return true;
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    @Beta
    public boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        SetFuture setFuture;
        Failure failure;
        Preconditions.checkNotNull(listenableFuture);
        Object obj = this.e;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!c.a(this, (Object) null, a(listenableFuture))) {
                    return false;
                }
                e(this);
                return true;
            }
            setFuture = new SetFuture(this, listenableFuture);
            if (c.a(this, (Object) null, (Object) setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, MoreExecutors.directExecutor());
                } catch (Throwable unused) {
                    failure = Failure.a;
                }
                return true;
            }
            obj = this.e;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).a);
        }
        return false;
        c.a(this, (Object) setFuture, (Object) failure);
        return true;
    }

    /* access modifiers changed from: private */
    public static Object a(ListenableFuture<?> listenableFuture) {
        Object obj;
        if (listenableFuture instanceof TrustedFuture) {
            return ((AbstractFuture) listenableFuture).e;
        }
        try {
            Object done = Futures.getDone(listenableFuture);
            if (done == null) {
                done = d;
            }
            obj = done;
        } catch (ExecutionException e2) {
            obj = new Failure(e2.getCause());
        } catch (CancellationException e3) {
            obj = new Cancellation(false, e3);
        } catch (Throwable th) {
            obj = new Failure(th);
        }
        return obj;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.util.concurrent.AbstractFuture<?>, code=com.google.common.util.concurrent.AbstractFuture, for r4v0, types: [com.google.common.util.concurrent.AbstractFuture<?>, com.google.common.util.concurrent.AbstractFuture] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void e(com.google.common.util.concurrent.AbstractFuture r4) {
        /*
            r0 = 0
        L_0x0001:
            r4.c()
            r4.afterDone()
            com.google.common.util.concurrent.AbstractFuture$Listener r4 = r4.a(r0)
        L_0x000b:
            if (r4 == 0) goto L_0x0033
            com.google.common.util.concurrent.AbstractFuture$Listener r0 = r4.d
            java.lang.Runnable r1 = r4.b
            boolean r2 = r1 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            if (r2 == 0) goto L_0x002c
            com.google.common.util.concurrent.AbstractFuture$SetFuture r1 = (com.google.common.util.concurrent.AbstractFuture.SetFuture) r1
            com.google.common.util.concurrent.AbstractFuture<V> r4 = r1.a
            java.lang.Object r2 = r4.e
            if (r2 != r1) goto L_0x0031
            com.google.common.util.concurrent.ListenableFuture<? extends V> r2 = r1.b
            java.lang.Object r2 = a(r2)
            com.google.common.util.concurrent.AbstractFuture$AtomicHelper r3 = c
            boolean r1 = r3.a(r4, r1, r2)
            if (r1 == 0) goto L_0x0031
            goto L_0x0001
        L_0x002c:
            java.util.concurrent.Executor r4 = r4.c
            a(r1, r4)
        L_0x0031:
            r4 = r0
            goto L_0x000b
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.e(com.google.common.util.concurrent.AbstractFuture):void");
    }

    /* access modifiers changed from: 0000 */
    public final Throwable a() {
        return ((Failure) this.e).b;
    }

    /* access modifiers changed from: 0000 */
    public final void a(@Nullable Future<?> future) {
        if ((future != null) && isCancelled()) {
            future.cancel(wasInterrupted());
        }
    }

    private void c() {
        Waiter waiter;
        do {
            waiter = this.g;
        } while (!c.a(this, waiter, Waiter.a));
        while (waiter != null) {
            waiter.a();
            waiter = waiter.c;
        }
    }

    private Listener a(Listener listener) {
        Listener listener2;
        do {
            listener2 = this.f;
        } while (!c.a(this, listener2, Listener.a));
        Listener listener3 = listener2;
        Listener listener4 = listener;
        Listener listener5 = listener3;
        while (listener5 != null) {
            Listener listener6 = listener5.d;
            listener5.d = listener4;
            listener4 = listener5;
            listener5 = listener6;
        }
        return listener4;
    }

    private static void a(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            Logger logger = b;
            Level level = Level.SEVERE;
            StringBuilder sb = new StringBuilder();
            sb.append("RuntimeException while executing runnable ");
            sb.append(runnable);
            sb.append(" with executor ");
            sb.append(executor);
            logger.log(level, sb.toString(), e2);
        }
    }

    private static CancellationException a(@Nullable String str, @Nullable Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }
}
