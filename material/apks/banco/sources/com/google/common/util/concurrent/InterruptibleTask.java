package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible(emulated = true)
abstract class InterruptibleTask implements Runnable {
    private static final AtomicHelper c;
    private static final Logger d = Logger.getLogger(InterruptibleTask.class.getName());
    /* access modifiers changed from: private */
    public volatile Thread a;
    private volatile boolean b;

    static abstract class AtomicHelper {
        /* access modifiers changed from: 0000 */
        public abstract boolean a(InterruptibleTask interruptibleTask, Thread thread, Thread thread2);

        private AtomicHelper() {
        }
    }

    static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<InterruptibleTask, Thread> a;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater) {
            super();
            this.a = atomicReferenceFieldUpdater;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InterruptibleTask interruptibleTask, Thread thread, Thread thread2) {
            return this.a.compareAndSet(interruptibleTask, thread, thread2);
        }
    }

    static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(InterruptibleTask interruptibleTask, Thread thread, Thread thread2) {
            synchronized (interruptibleTask) {
                if (interruptibleTask.a == thread) {
                    interruptibleTask.a = thread2;
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void b();

    /* access modifiers changed from: 0000 */
    public abstract boolean c();

    InterruptibleTask() {
    }

    static {
        AtomicHelper atomicHelper;
        try {
            atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(InterruptibleTask.class, Thread.class, "a"));
        } catch (Throwable th) {
            d.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
            atomicHelper = new SynchronizedAtomicHelper();
        }
        c = atomicHelper;
    }

    public final void run() {
        if (c.a(this, null, Thread.currentThread())) {
            try {
                b();
            } finally {
                if (c()) {
                    while (!this.b) {
                        Thread.yield();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        Thread thread = this.a;
        if (thread != null) {
            thread.interrupt();
        }
        this.b = true;
    }
}
