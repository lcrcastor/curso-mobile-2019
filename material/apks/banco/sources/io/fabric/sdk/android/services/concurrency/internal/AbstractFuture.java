package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public abstract class AbstractFuture<V> implements Future<V> {
    private final Sync<V> a = new Sync<>();

    static final class Sync<V> extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 0;
        private V a;
        private Throwable b;

        Sync() {
        }

        /* access modifiers changed from: protected */
        public int tryAcquireShared(int i) {
            return b() ? 1 : -1;
        }

        /* access modifiers changed from: protected */
        public boolean tryReleaseShared(int i) {
            setState(i);
            return true;
        }

        /* access modifiers changed from: 0000 */
        public V a(long j) {
            if (tryAcquireSharedNanos(-1, j)) {
                return e();
            }
            throw new TimeoutException("Timeout waiting for task.");
        }

        /* access modifiers changed from: 0000 */
        public V a() {
            acquireSharedInterruptibly(-1);
            return e();
        }

        private V e() {
            int state = getState();
            if (state != 2) {
                if (state == 4 || state == 8) {
                    throw AbstractFuture.a("Task was cancelled.", this.b);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Error, synchronizer in invalid state: ");
                sb.append(state);
                throw new IllegalStateException(sb.toString());
            } else if (this.b == null) {
                return this.a;
            } else {
                throw new ExecutionException(this.b);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return (getState() & 14) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return (getState() & 12) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return getState() == 8;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(V v) {
            return a(v, null, 2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Throwable th) {
            return a(null, th, 2);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(boolean z) {
            return a(null, null, z ? 8 : 4);
        }

        private boolean a(V v, Throwable th, int i) {
            boolean compareAndSetState = compareAndSetState(0, 1);
            if (compareAndSetState) {
                this.a = v;
                if ((i & 12) != 0) {
                    th = new CancellationException("Future.cancel() was called.");
                }
                this.b = th;
                releaseShared(i);
            } else if (getState() == 1) {
                acquireShared(-1);
            }
            return compareAndSetState;
        }
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    protected AbstractFuture() {
    }

    static final CancellationException a(String str, Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    public V get(long j, TimeUnit timeUnit) {
        return this.a.a(timeUnit.toNanos(j));
    }

    public V get() {
        return this.a.a();
    }

    public boolean isDone() {
        return this.a.b();
    }

    public boolean isCancelled() {
        return this.a.c();
    }

    public boolean cancel(boolean z) {
        if (!this.a.a(z)) {
            return false;
        }
        if (z) {
            interruptTask();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        return this.a.d();
    }

    /* access modifiers changed from: protected */
    public boolean set(V v) {
        return this.a.a(v);
    }

    /* access modifiers changed from: protected */
    public boolean setException(Throwable th) {
        if (th != null) {
            return this.a.a(th);
        }
        throw new NullPointerException();
    }
}
