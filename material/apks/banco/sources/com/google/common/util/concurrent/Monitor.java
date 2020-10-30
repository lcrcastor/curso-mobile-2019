package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
@Beta
public final class Monitor {
    private final boolean a;
    /* access modifiers changed from: private */
    public final ReentrantLock b;
    @GuardedBy("lock")
    private Guard c;

    @Beta
    public static abstract class Guard {
        @Weak
        final Monitor b;
        final Condition c;
        @GuardedBy("monitor.lock")
        int d = 0;
        @GuardedBy("monitor.lock")
        Guard e;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor) {
            this.b = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.c = monitor.b.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean z) {
        this.c = null;
        this.a = z;
        this.b = new ReentrantLock(z);
    }

    public void enter() {
        this.b.lock();
    }

    public void enterInterruptibly() {
        this.b.lockInterruptibly();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:14|15|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r4 = a(java.lang.System.nanoTime(), r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r8 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x002b */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0037  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enter(long r8, java.util.concurrent.TimeUnit r10) {
        /*
            r7 = this;
            long r8 = a(r8, r10)
            java.util.concurrent.locks.ReentrantLock r10 = r7.b
            boolean r0 = r7.a
            r1 = 1
            if (r0 != 0) goto L_0x0012
            boolean r0 = r10.tryLock()
            if (r0 == 0) goto L_0x0012
            return r1
        L_0x0012:
            boolean r0 = java.lang.Thread.interrupted()
            long r2 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0034 }
            r4 = r8
        L_0x001b:
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x002b }
            boolean r4 = r10.tryLock(r4, r6)     // Catch:{ InterruptedException -> 0x002b }
            if (r0 == 0) goto L_0x002a
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
        L_0x002a:
            return r4
        L_0x002b:
            long r4 = a(r2, r8)     // Catch:{ all -> 0x0031 }
            r0 = 1
            goto L_0x001b
        L_0x0031:
            r8 = move-exception
            r0 = 1
            goto L_0x0035
        L_0x0034:
            r8 = move-exception
        L_0x0035:
            if (r0 == 0) goto L_0x003e
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L_0x003e:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enter(long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterInterruptibly(long j, TimeUnit timeUnit) {
        return this.b.tryLock(j, timeUnit);
    }

    public boolean tryEnter() {
        return this.b.tryLock();
    }

    public void enterWhen(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lockInterruptibly();
        try {
            if (!guard.isSatisfied()) {
                a(guard, isHeldByCurrentThread);
            }
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lock();
        try {
            if (!guard.isSatisfied()) {
                b(guard, isHeldByCurrentThread);
            }
        } catch (Throwable th) {
            leave();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        if (a(r11, r0, r3) != false) goto L_0x004f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0040 A[Catch:{ all -> 0x0056, all -> 0x005d }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0052 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r11, long r12, java.util.concurrent.TimeUnit r14) {
        /*
            r10 = this;
            long r0 = a(r12, r14)
            com.google.common.util.concurrent.Monitor r2 = r11.b
            if (r2 == r10) goto L_0x000e
            java.lang.IllegalMonitorStateException r11 = new java.lang.IllegalMonitorStateException
            r11.<init>()
            throw r11
        L_0x000e:
            java.util.concurrent.locks.ReentrantLock r2 = r10.b
            boolean r3 = r2.isHeldByCurrentThread()
            boolean r4 = r10.a
            r5 = 0
            r6 = 0
            if (r4 != 0) goto L_0x002f
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 == 0) goto L_0x0027
            java.lang.InterruptedException r11 = new java.lang.InterruptedException
            r11.<init>()
            throw r11
        L_0x0027:
            boolean r4 = r2.tryLock()
            if (r4 == 0) goto L_0x002f
            r8 = r6
            goto L_0x003a
        L_0x002f:
            long r8 = a(r0)
            boolean r12 = r2.tryLock(r12, r14)
            if (r12 != 0) goto L_0x003a
            return r5
        L_0x003a:
            boolean r12 = r11.isSatisfied()     // Catch:{ all -> 0x0056 }
            if (r12 != 0) goto L_0x004f
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 != 0) goto L_0x0045
            goto L_0x0049
        L_0x0045:
            long r0 = a(r8, r0)     // Catch:{ all -> 0x0056 }
        L_0x0049:
            boolean r11 = r10.a(r11, r0, r3)     // Catch:{ all -> 0x0056 }
            if (r11 == 0) goto L_0x0050
        L_0x004f:
            r5 = 1
        L_0x0050:
            if (r5 != 0) goto L_0x0055
            r2.unlock()
        L_0x0055:
            return r5
        L_0x0056:
            r11 = move-exception
            if (r3 != 0) goto L_0x0062
            r10.a()     // Catch:{ all -> 0x005d }
            goto L_0x0062
        L_0x005d:
            r11 = move-exception
            r2.unlock()
            throw r11
        L_0x0062:
            r2.unlock()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r9 = a(r7, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0078, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0079, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x007e, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x0072 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003c A[Catch:{ InterruptedException -> 0x0065, all -> 0x0060, all -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x003e A[Catch:{ InterruptedException -> 0x0065, all -> 0x0060, all -> 0x007b }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0053 A[SYNTHETIC, Splitter:B:27:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x007e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r12, long r13, java.util.concurrent.TimeUnit r15) {
        /*
            r11 = this;
            long r13 = a(r13, r15)
            com.google.common.util.concurrent.Monitor r15 = r12.b
            if (r15 == r11) goto L_0x000e
            java.lang.IllegalMonitorStateException r12 = new java.lang.IllegalMonitorStateException
            r12.<init>()
            throw r12
        L_0x000e:
            java.util.concurrent.locks.ReentrantLock r15 = r11.b
            boolean r0 = r15.isHeldByCurrentThread()
            boolean r1 = java.lang.Thread.interrupted()
            r2 = 1
            boolean r3 = r11.a     // Catch:{ all -> 0x007b }
            r4 = 0
            r5 = 0
            if (r3 != 0) goto L_0x0029
            boolean r3 = r15.tryLock()     // Catch:{ all -> 0x007b }
            if (r3 != 0) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r7 = r5
            goto L_0x0036
        L_0x0029:
            long r7 = a(r13)     // Catch:{ all -> 0x007b }
            r9 = r13
        L_0x002e:
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0072 }
            boolean r3 = r15.tryLock(r9, r3)     // Catch:{ InterruptedException -> 0x0072 }
            if (r3 == 0) goto L_0x0068
        L_0x0036:
            boolean r3 = r12.isSatisfied()     // Catch:{ InterruptedException -> 0x0065, all -> 0x0060 }
            if (r3 == 0) goto L_0x003e
            r0 = 1
            goto L_0x0051
        L_0x003e:
            int r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0049
            long r9 = a(r13)     // Catch:{ InterruptedException -> 0x0065, all -> 0x0060 }
            r7 = r9
            r9 = r13
            goto L_0x004d
        L_0x0049:
            long r9 = a(r7, r13)     // Catch:{ InterruptedException -> 0x0065, all -> 0x0060 }
        L_0x004d:
            boolean r0 = r11.a(r12, r9, r0)     // Catch:{ InterruptedException -> 0x0065, all -> 0x0060 }
        L_0x0051:
            if (r0 != 0) goto L_0x0056
            r15.unlock()     // Catch:{ all -> 0x007b }
        L_0x0056:
            if (r1 == 0) goto L_0x005f
            java.lang.Thread r12 = java.lang.Thread.currentThread()
            r12.interrupt()
        L_0x005f:
            return r0
        L_0x0060:
            r12 = move-exception
            r15.unlock()     // Catch:{ all -> 0x007b }
            throw r12     // Catch:{ all -> 0x007b }
        L_0x0065:
            r0 = 0
            r1 = 1
            goto L_0x0036
        L_0x0068:
            if (r1 == 0) goto L_0x0071
            java.lang.Thread r12 = java.lang.Thread.currentThread()
            r12.interrupt()
        L_0x0071:
            return r4
        L_0x0072:
            long r9 = a(r7, r13)     // Catch:{ all -> 0x0078 }
            r1 = 1
            goto L_0x002e
        L_0x0078:
            r12 = move-exception
            r1 = 1
            goto L_0x007c
        L_0x007b:
            r12 = move-exception
        L_0x007c:
            if (r1 == 0) goto L_0x0085
            java.lang.Thread r13 = java.lang.Thread.currentThread()
            r13.interrupt()
        L_0x0085:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterIf(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lock();
        try {
            boolean isSatisfied = guard.isSatisfied();
            if (!isSatisfied) {
            }
            return isSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean enterIfInterruptibly(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lockInterruptibly();
        try {
            boolean isSatisfied = guard.isSatisfied();
            if (!isSatisfied) {
            }
            return isSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean enterIf(Guard guard, long j, TimeUnit timeUnit) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(j, timeUnit)) {
            return false;
        } else {
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                this.b.unlock();
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long j, TimeUnit timeUnit) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        if (!reentrantLock.tryLock(j, timeUnit)) {
            return false;
        }
        try {
            boolean isSatisfied = guard.isSatisfied();
            if (!isSatisfied) {
            }
            return isSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.b;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        try {
            boolean isSatisfied = guard.isSatisfied();
            if (!isSatisfied) {
            }
            return isSatisfied;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void waitFor(Guard guard) {
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            a(guard, true);
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            b(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long j, TimeUnit timeUnit) {
        long a2 = a(j, timeUnit);
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            if (!Thread.interrupted()) {
                return a(guard, a2, true);
            }
            throw new InterruptedException();
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x003f */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
            r7 = this;
            long r9 = a(r9, r11)
            com.google.common.util.concurrent.Monitor r11 = r8.b
            r0 = 0
            r1 = 1
            if (r11 != r7) goto L_0x000c
            r11 = 1
            goto L_0x000d
        L_0x000c:
            r11 = 0
        L_0x000d:
            java.util.concurrent.locks.ReentrantLock r2 = r7.b
            boolean r2 = r2.isHeldByCurrentThread()
            r11 = r11 & r2
            if (r11 != 0) goto L_0x001c
            java.lang.IllegalMonitorStateException r8 = new java.lang.IllegalMonitorStateException
            r8.<init>()
            throw r8
        L_0x001c:
            boolean r11 = r8.isSatisfied()
            if (r11 == 0) goto L_0x0023
            return r1
        L_0x0023:
            long r2 = a(r9)
            boolean r11 = java.lang.Thread.interrupted()
            r4 = r9
            r6 = r11
            r11 = 1
        L_0x002e:
            boolean r11 = r7.a(r8, r4, r11)     // Catch:{ InterruptedException -> 0x003f, all -> 0x003c }
            if (r6 == 0) goto L_0x003b
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
        L_0x003b:
            return r11
        L_0x003c:
            r8 = move-exception
            r1 = r6
            goto L_0x0055
        L_0x003f:
            boolean r11 = r8.isSatisfied()     // Catch:{ all -> 0x0054 }
            if (r11 == 0) goto L_0x004d
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
            return r1
        L_0x004d:
            long r4 = a(r2, r9)     // Catch:{ all -> 0x0054 }
            r11 = 0
            r6 = 1
            goto L_0x002e
        L_0x0054:
            r8 = move-exception
        L_0x0055:
            if (r1 == 0) goto L_0x005e
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L_0x005e:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void leave() {
        ReentrantLock reentrantLock = this.b;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                a();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean isFair() {
        return this.a;
    }

    public boolean isOccupied() {
        return this.b.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.b.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.b.getHoldCount();
    }

    public int getQueueLength() {
        return this.b.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.b.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.b.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        }
        this.b.lock();
        try {
            return guard.d;
        } finally {
            this.b.unlock();
        }
    }

    private static long a(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        if (nanos <= 0) {
            return 0;
        }
        if (nanos > 6917529027641081853L) {
            return 6917529027641081853L;
        }
        return nanos;
    }

    private static long a(long j) {
        if (j <= 0) {
            return 0;
        }
        long nanoTime = System.nanoTime();
        if (nanoTime == 0) {
            nanoTime = 1;
        }
        return nanoTime;
    }

    private static long a(long j, long j2) {
        if (j2 <= 0) {
            return 0;
        }
        return j2 - (System.nanoTime() - j);
    }

    @GuardedBy("lock")
    private void a() {
        for (Guard guard = this.c; guard != null; guard = guard.e) {
            if (a(guard)) {
                guard.c.signal();
                return;
            }
        }
    }

    @GuardedBy("lock")
    private boolean a(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable th) {
            b();
            throw Throwables.propagate(th);
        }
    }

    @GuardedBy("lock")
    private void b() {
        for (Guard guard = this.c; guard != null; guard = guard.e) {
            guard.c.signalAll();
        }
    }

    @GuardedBy("lock")
    private void b(Guard guard) {
        int i = guard.d;
        guard.d = i + 1;
        if (i == 0) {
            guard.e = this.c;
            this.c = guard;
        }
    }

    @GuardedBy("lock")
    private void c(Guard guard) {
        int i = guard.d - 1;
        guard.d = i;
        if (i == 0) {
            Guard guard2 = this.c;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.e;
            }
            if (guard3 == null) {
                this.c = guard2.e;
            } else {
                guard3.e = guard2.e;
            }
            guard2.e = null;
        }
    }

    @GuardedBy("lock")
    private void a(Guard guard, boolean z) {
        if (z) {
            a();
        }
        b(guard);
        do {
            try {
                guard.c.await();
            } finally {
                c(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private void b(Guard guard, boolean z) {
        if (z) {
            a();
        }
        b(guard);
        do {
            try {
                guard.c.awaitUninterruptibly();
            } finally {
                c(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private boolean a(Guard guard, long j, boolean z) {
        boolean z2 = true;
        while (j > 0) {
            if (z2) {
                if (z) {
                    try {
                        a();
                    } catch (Throwable th) {
                        if (!z2) {
                            c(guard);
                        }
                        throw th;
                    }
                }
                b(guard);
                z2 = false;
            }
            j = guard.c.awaitNanos(j);
            if (guard.isSatisfied()) {
                if (!z2) {
                    c(guard);
                }
                return true;
            }
        }
        if (!z2) {
            c(guard);
        }
        return false;
    }
}
