package cz.msebera.android.httpclient.pool;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.concurrent.FutureCallback;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@ThreadSafe
abstract class PoolEntryFuture<T> implements Future<T> {
    private final Lock a;
    private final FutureCallback<T> b;
    private final Condition c;
    private volatile boolean d;
    private volatile boolean e;
    private T f;

    /* access modifiers changed from: protected */
    public abstract T b(long j, TimeUnit timeUnit);

    PoolEntryFuture(Lock lock, FutureCallback<T> futureCallback) {
        this.a = lock;
        this.c = lock.newCondition();
        this.b = futureCallback;
    }

    public boolean cancel(boolean z) {
        boolean z2;
        this.a.lock();
        try {
            if (this.e) {
                z2 = false;
            } else {
                z2 = true;
                this.e = true;
                this.d = true;
                if (this.b != null) {
                    this.b.cancelled();
                }
                this.c.signalAll();
            }
            return z2;
        } finally {
            this.a.unlock();
        }
    }

    public boolean isCancelled() {
        return this.d;
    }

    public boolean isDone() {
        return this.e;
    }

    public T get() {
        try {
            return get(0, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e2) {
            throw new ExecutionException(e2);
        }
    }

    public T get(long j, TimeUnit timeUnit) {
        T t;
        Args.notNull(timeUnit, "Time unit");
        this.a.lock();
        try {
            if (this.e) {
                t = this.f;
            } else {
                this.f = b(j, timeUnit);
                this.e = true;
                if (this.b != null) {
                    this.b.completed(this.f);
                }
                t = this.f;
            }
            this.a.unlock();
            return t;
        } catch (IOException e2) {
            this.e = true;
            this.f = null;
            if (this.b != null) {
                this.b.failed(e2);
            }
            throw new ExecutionException(e2);
        } catch (Throwable th) {
            this.a.unlock();
            throw th;
        }
    }

    public boolean a(Date date) {
        boolean z;
        this.a.lock();
        try {
            if (this.d) {
                throw new InterruptedException("Operation interrupted");
            }
            if (date != null) {
                z = this.c.awaitUntil(date);
            } else {
                this.c.await();
                z = true;
            }
            if (!this.d) {
                return z;
            }
            throw new InterruptedException("Operation interrupted");
        } finally {
            this.a.unlock();
        }
    }

    public void a() {
        this.a.lock();
        try {
            this.c.signalAll();
        } finally {
            this.a.unlock();
        }
    }
}
