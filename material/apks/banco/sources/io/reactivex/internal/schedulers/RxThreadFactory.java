package io.reactivex.internal.schedulers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class RxThreadFactory extends AtomicLong implements ThreadFactory {
    private static final long serialVersionUID = -7789753024099756196L;
    final String a;
    final int b;
    final boolean c;

    static final class RxCustomThread extends Thread implements NonBlockingThread {
        RxCustomThread(Runnable runnable, String str) {
            super(runnable, str);
        }
    }

    public RxThreadFactory(String str) {
        this(str, 5, false);
    }

    public RxThreadFactory(String str, int i) {
        this(str, i, false);
    }

    public RxThreadFactory(String str, int i, boolean z) {
        this.a = str;
        this.b = i;
        this.c = z;
    }

    public Thread newThread(Runnable runnable) {
        StringBuilder sb = new StringBuilder(this.a);
        sb.append('-');
        sb.append(incrementAndGet());
        String sb2 = sb.toString();
        Thread rxCustomThread = this.c ? new RxCustomThread(runnable, sb2) : new Thread(runnable, sb2);
        rxCustomThread.setPriority(this.b);
        rxCustomThread.setDaemon(true);
        return rxCustomThread;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RxThreadFactory[");
        sb.append(this.a);
        sb.append("]");
        return sb.toString();
    }
}
