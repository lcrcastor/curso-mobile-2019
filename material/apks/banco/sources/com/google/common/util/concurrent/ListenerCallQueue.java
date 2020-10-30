package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
final class ListenerCallQueue<L> implements Runnable {
    private static final Logger a = Logger.getLogger(ListenerCallQueue.class.getName());
    private final L b;
    private final Executor c;
    @GuardedBy("this")
    private final Queue<Callback<L>> d = Queues.newArrayDeque();
    @GuardedBy("this")
    private boolean e;

    static abstract class Callback<L> {
        /* access modifiers changed from: private */
        public final String a;

        /* access modifiers changed from: 0000 */
        public abstract void a(L l);

        Callback(String str) {
            this.a = str;
        }

        /* access modifiers changed from: 0000 */
        public void a(Iterable<ListenerCallQueue<L>> iterable) {
            for (ListenerCallQueue a2 : iterable) {
                a2.a(this);
            }
        }
    }

    ListenerCallQueue(L l, Executor executor) {
        this.b = Preconditions.checkNotNull(l);
        this.c = (Executor) Preconditions.checkNotNull(executor);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(Callback<L> callback) {
        this.d.add(callback);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        boolean z;
        synchronized (this) {
            z = true;
            if (!this.e) {
                this.e = true;
            } else {
                z = false;
            }
        }
        if (z) {
            try {
                this.c.execute(this);
            } catch (RuntimeException e2) {
                synchronized (this) {
                    this.e = false;
                    Logger logger = a;
                    Level level = Level.SEVERE;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Exception while running callbacks for ");
                    sb.append(this.b);
                    sb.append(" on ");
                    sb.append(this.c);
                    logger.log(level, sb.toString(), e2);
                    throw e2;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2.a(r8.b);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r8 = this;
        L_0x0000:
            r0 = 0
            r1 = 1
            monitor-enter(r8)     // Catch:{ all -> 0x004c }
            boolean r2 = r8.e     // Catch:{ all -> 0x0049 }
            com.google.common.base.Preconditions.checkState(r2)     // Catch:{ all -> 0x0049 }
            java.util.Queue<com.google.common.util.concurrent.ListenerCallQueue$Callback<L>> r2 = r8.d     // Catch:{ all -> 0x0049 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0049 }
            com.google.common.util.concurrent.ListenerCallQueue$Callback r2 = (com.google.common.util.concurrent.ListenerCallQueue.Callback) r2     // Catch:{ all -> 0x0049 }
            if (r2 != 0) goto L_0x001a
            r8.e = r0     // Catch:{ all -> 0x0049 }
            monitor-exit(r8)     // Catch:{ all -> 0x0016 }
            return
        L_0x0016:
            r1 = move-exception
            r2 = r1
            r1 = 0
            goto L_0x004a
        L_0x001a:
            monitor-exit(r8)     // Catch:{ all -> 0x0049 }
            L r3 = r8.b     // Catch:{ RuntimeException -> 0x0021 }
            r2.a(r3)     // Catch:{ RuntimeException -> 0x0021 }
            goto L_0x0000
        L_0x0021:
            r3 = move-exception
            java.util.logging.Logger r4 = a     // Catch:{ all -> 0x004c }
            java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch:{ all -> 0x004c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x004c }
            r6.<init>()     // Catch:{ all -> 0x004c }
            java.lang.String r7 = "Exception while executing callback: "
            r6.append(r7)     // Catch:{ all -> 0x004c }
            L r7 = r8.b     // Catch:{ all -> 0x004c }
            r6.append(r7)     // Catch:{ all -> 0x004c }
            java.lang.String r7 = "."
            r6.append(r7)     // Catch:{ all -> 0x004c }
            java.lang.String r2 = r2.a     // Catch:{ all -> 0x004c }
            r6.append(r2)     // Catch:{ all -> 0x004c }
            java.lang.String r2 = r6.toString()     // Catch:{ all -> 0x004c }
            r4.log(r5, r2, r3)     // Catch:{ all -> 0x004c }
            goto L_0x0000
        L_0x0049:
            r2 = move-exception
        L_0x004a:
            monitor-exit(r8)     // Catch:{ all -> 0x0049 }
            throw r2     // Catch:{ all -> 0x004c }
        L_0x004c:
            r2 = move-exception
            if (r1 == 0) goto L_0x0057
            monitor-enter(r8)
            r8.e = r0     // Catch:{ all -> 0x0054 }
            monitor-exit(r8)     // Catch:{ all -> 0x0054 }
            goto L_0x0057
        L_0x0054:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.run():void");
    }
}
