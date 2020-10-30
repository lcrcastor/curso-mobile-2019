package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
public final class ExecutionList {
    private static final Logger a = Logger.getLogger(ExecutionList.class.getName());
    @GuardedBy("this")
    private RunnableExecutorPair b;
    @GuardedBy("this")
    private boolean c;

    static final class RunnableExecutorPair {
        final Runnable a;
        final Executor b;
        @Nullable
        RunnableExecutorPair c;

        RunnableExecutorPair(Runnable runnable, Executor executor, RunnableExecutorPair runnableExecutorPair) {
            this.a = runnable;
            this.b = executor;
            this.c = runnableExecutorPair;
        }
    }

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (!this.c) {
                this.b = new RunnableExecutorPair(runnable, executor, this.b);
            } else {
                a(runnable, executor);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        r2 = r1.c;
        r1.c = r0;
        r0 = r1;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        if (r0 == null) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        a(r0.a, r0.b);
        r0 = r0.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
        r1 = r0;
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        if (r1 == null) goto L_0x001c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.c     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0029 }
            return
        L_0x0007:
            r0 = 1
            r4.c = r0     // Catch:{ all -> 0x0029 }
            com.google.common.util.concurrent.ExecutionList$RunnableExecutorPair r0 = r4.b     // Catch:{ all -> 0x0029 }
            r1 = 0
            r4.b = r1     // Catch:{ all -> 0x0029 }
            monitor-exit(r4)     // Catch:{ all -> 0x0029 }
            r3 = r1
            r1 = r0
            r0 = r3
        L_0x0013:
            if (r1 == 0) goto L_0x001c
            com.google.common.util.concurrent.ExecutionList$RunnableExecutorPair r2 = r1.c
            r1.c = r0
            r0 = r1
            r1 = r2
            goto L_0x0013
        L_0x001c:
            if (r0 == 0) goto L_0x0028
            java.lang.Runnable r1 = r0.a
            java.util.concurrent.Executor r2 = r0.b
            a(r1, r2)
            com.google.common.util.concurrent.ExecutionList$RunnableExecutorPair r0 = r0.c
            goto L_0x001c
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0029 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ExecutionList.execute():void");
    }

    private static void a(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = a;
            Level level = Level.SEVERE;
            StringBuilder sb = new StringBuilder();
            sb.append("RuntimeException while executing runnable ");
            sb.append(runnable);
            sb.append(" with executor ");
            sb.append(executor);
            logger.log(level, sb.toString(), e);
        }
    }
}
