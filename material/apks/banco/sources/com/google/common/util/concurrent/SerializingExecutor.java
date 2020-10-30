package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
final class SerializingExecutor implements Executor {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(SerializingExecutor.class.getName());
    private final Executor b;
    /* access modifiers changed from: private */
    @GuardedBy("internalLock")
    public final Deque<Runnable> c = new ArrayDeque();
    /* access modifiers changed from: private */
    @GuardedBy("internalLock")
    public boolean d = false;
    /* access modifiers changed from: private */
    @GuardedBy("internalLock")
    public int e = 0;
    /* access modifiers changed from: private */
    public final Object f = new Object();

    final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        public void run() {
            try {
                a();
            } catch (Error e) {
                synchronized (SerializingExecutor.this.f) {
                    SerializingExecutor.this.d = false;
                    throw e;
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r0.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
            r2 = com.google.common.util.concurrent.SerializingExecutor.a();
            r3 = java.util.logging.Level.SEVERE;
            r4 = new java.lang.StringBuilder();
            r4.append("Exception while executing runnable ");
            r4.append(r0);
            r2.log(r3, r4.toString(), r1);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void a() {
            /*
                r6 = this;
            L_0x0000:
                r0 = 0
                com.google.common.util.concurrent.SerializingExecutor r1 = com.google.common.util.concurrent.SerializingExecutor.this
                java.lang.Object r1 = r1.f
                monitor-enter(r1)
                com.google.common.util.concurrent.SerializingExecutor r2 = com.google.common.util.concurrent.SerializingExecutor.this     // Catch:{ all -> 0x0047 }
                int r2 = r2.e     // Catch:{ all -> 0x0047 }
                if (r2 != 0) goto L_0x001c
                com.google.common.util.concurrent.SerializingExecutor r0 = com.google.common.util.concurrent.SerializingExecutor.this     // Catch:{ all -> 0x0047 }
                java.util.Deque r0 = r0.c     // Catch:{ all -> 0x0047 }
                java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0047 }
                java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0047 }
            L_0x001c:
                if (r0 != 0) goto L_0x0026
                com.google.common.util.concurrent.SerializingExecutor r0 = com.google.common.util.concurrent.SerializingExecutor.this     // Catch:{ all -> 0x0047 }
                r2 = 0
                r0.d = r2     // Catch:{ all -> 0x0047 }
                monitor-exit(r1)     // Catch:{ all -> 0x0047 }
                return
            L_0x0026:
                monitor-exit(r1)     // Catch:{ all -> 0x0047 }
                r0.run()     // Catch:{ RuntimeException -> 0x002b }
                goto L_0x0000
            L_0x002b:
                r1 = move-exception
                java.util.logging.Logger r2 = com.google.common.util.concurrent.SerializingExecutor.a
                java.util.logging.Level r3 = java.util.logging.Level.SEVERE
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Exception while executing runnable "
                r4.append(r5)
                r4.append(r0)
                java.lang.String r0 = r4.toString()
                r2.log(r3, r0, r1)
                goto L_0x0000
            L_0x0047:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0047 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SerializingExecutor.QueueWorker.a():void");
        }
    }

    public SerializingExecutor(Executor executor) {
        this.b = (Executor) Preconditions.checkNotNull(executor);
    }

    public void execute(Runnable runnable) {
        synchronized (this.f) {
            this.c.add(runnable);
        }
        b();
    }

    private void b() {
        synchronized (this.f) {
            if (this.c.peek() != null) {
                if (this.e <= 0) {
                    if (!this.d) {
                        this.d = true;
                        try {
                            this.b.execute(new QueueWorker());
                        } catch (Throwable th) {
                            synchronized (this.f) {
                                this.d = false;
                                throw th;
                            }
                        }
                    }
                }
            }
        }
    }
}
