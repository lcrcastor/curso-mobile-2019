package cz.msebera.android.httpclient.concurrent;

import cz.msebera.android.httpclient.util.Args;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BasicFuture<T> implements Cancellable, Future<T> {
    private final FutureCallback<T> a;
    private volatile boolean b;
    private volatile boolean c;
    private volatile T d;
    private volatile Exception e;

    public BasicFuture(FutureCallback<T> futureCallback) {
        this.a = futureCallback;
    }

    public boolean isCancelled() {
        return this.c;
    }

    public boolean isDone() {
        return this.b;
    }

    private T a() {
        if (this.e == null) {
            return this.d;
        }
        throw new ExecutionException(this.e);
    }

    public synchronized T get() {
        while (!this.b) {
            wait();
        }
        return a();
    }

    public synchronized T get(long j, TimeUnit timeUnit) {
        long j2;
        Args.notNull(timeUnit, "Time unit");
        long millis = timeUnit.toMillis(j);
        if (millis <= 0) {
            j2 = 0;
        } else {
            j2 = System.currentTimeMillis();
        }
        if (this.b) {
            return a();
        } else if (millis <= 0) {
            throw new TimeoutException();
        } else {
            long j3 = millis;
            do {
                wait(j3);
                if (this.b) {
                    return a();
                }
                j3 = millis - (System.currentTimeMillis() - j2);
            } while (j3 > 0);
            throw new TimeoutException();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        if (r2.a == null) goto L_0x001a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        r2.a.completed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean completed(T r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.b     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0008
            r3 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            return r3
        L_0x0008:
            r0 = 1
            r2.b = r0     // Catch:{ all -> 0x001b }
            r2.d = r3     // Catch:{ all -> 0x001b }
            r2.notifyAll()     // Catch:{ all -> 0x001b }
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r1 = r2.a
            if (r1 == 0) goto L_0x001a
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r1 = r2.a
            r1.completed(r3)
        L_0x001a:
            return r0
        L_0x001b:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.concurrent.BasicFuture.completed(java.lang.Object):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        if (r2.a == null) goto L_0x001a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        r2.a.failed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean failed(java.lang.Exception r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.b     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0008
            r3 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            return r3
        L_0x0008:
            r0 = 1
            r2.b = r0     // Catch:{ all -> 0x001b }
            r2.e = r3     // Catch:{ all -> 0x001b }
            r2.notifyAll()     // Catch:{ all -> 0x001b }
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r1 = r2.a
            if (r1 == 0) goto L_0x001a
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r1 = r2.a
            r1.failed(r3)
        L_0x001a:
            return r0
        L_0x001b:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.concurrent.BasicFuture.failed(java.lang.Exception):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        if (r1.a == null) goto L_0x001a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        r1.a.cancelled();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean cancel(boolean r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r2 = r1.b     // Catch:{ all -> 0x001b }
            if (r2 == 0) goto L_0x0008
            r2 = 0
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            return r2
        L_0x0008:
            r2 = 1
            r1.b = r2     // Catch:{ all -> 0x001b }
            r1.c = r2     // Catch:{ all -> 0x001b }
            r1.notifyAll()     // Catch:{ all -> 0x001b }
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r0 = r1.a
            if (r0 == 0) goto L_0x001a
            cz.msebera.android.httpclient.concurrent.FutureCallback<T> r0 = r1.a
            r0.cancelled()
        L_0x001a:
            return r2
        L_0x001b:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.concurrent.BasicFuture.cancel(boolean):boolean");
    }

    public boolean cancel() {
        return cancel(true);
    }
}
