package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Disposable, Runnable, Callable<Object> {
    static final Object b = new Object();
    static final Object c = new Object();
    private static final long serialVersionUID = -6120223772001106981L;
    final Runnable a;

    public ScheduledRunnable(Runnable runnable, DisposableContainer disposableContainer) {
        super(3);
        this.a = runnable;
        lazySet(0, disposableContainer);
    }

    public Object call() {
        run();
        return null;
    }

    public void run() {
        Object obj;
        Object obj2;
        lazySet(2, Thread.currentThread());
        try {
            this.a.run();
        } catch (Throwable th) {
            lazySet(2, null);
            Object obj3 = get(0);
            if (!(obj3 == b || obj3 == null || !compareAndSet(0, obj3, c))) {
                ((DisposableContainer) obj3).delete(this);
            }
            do {
                obj2 = get(1);
                if (obj2 == b) {
                    break;
                }
            } while (!compareAndSet(1, obj2, c));
            throw th;
        }
        lazySet(2, null);
        Object obj4 = get(0);
        if (!(obj4 == b || obj4 == null || !compareAndSet(0, obj4, c))) {
            ((DisposableContainer) obj4).delete(this);
        }
        do {
            obj = get(1);
            if (obj == b) {
                return;
            }
        } while (!compareAndSet(1, obj, c));
    }

    public void setFuture(Future<?> future) {
        Object obj;
        do {
            boolean z = true;
            obj = get(1);
            if (obj != c) {
                if (obj == b) {
                    if (get(2) == Thread.currentThread()) {
                        z = false;
                    }
                    future.cancel(z);
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(1, obj, future));
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispose() {
        /*
            r5 = this;
        L_0x0000:
            r0 = 1
            java.lang.Object r1 = r5.get(r0)
            java.lang.Object r2 = c
            r3 = 0
            if (r1 == r2) goto L_0x002b
            java.lang.Object r2 = b
            if (r1 != r2) goto L_0x000f
            goto L_0x002b
        L_0x000f:
            java.lang.Object r2 = b
            boolean r2 = r5.compareAndSet(r0, r1, r2)
            if (r2 == 0) goto L_0x0000
            if (r1 == 0) goto L_0x002b
            java.util.concurrent.Future r1 = (java.util.concurrent.Future) r1
            r2 = 2
            java.lang.Object r2 = r5.get(r2)
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            if (r2 == r4) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r0 = 0
        L_0x0028:
            r1.cancel(r0)
        L_0x002b:
            java.lang.Object r0 = r5.get(r3)
            java.lang.Object r1 = c
            if (r0 == r1) goto L_0x0048
            java.lang.Object r1 = b
            if (r0 == r1) goto L_0x0048
            if (r0 != 0) goto L_0x003a
            goto L_0x0048
        L_0x003a:
            java.lang.Object r1 = b
            boolean r1 = r5.compareAndSet(r3, r0, r1)
            if (r1 == 0) goto L_0x002b
            io.reactivex.internal.disposables.DisposableContainer r0 = (io.reactivex.internal.disposables.DisposableContainer) r0
            r0.delete(r5)
            return
        L_0x0048:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.schedulers.ScheduledRunnable.dispose():void");
    }

    public boolean isDisposed() {
        Object obj = get(1);
        if (obj == b || obj == c) {
            return true;
        }
        return false;
    }
}
