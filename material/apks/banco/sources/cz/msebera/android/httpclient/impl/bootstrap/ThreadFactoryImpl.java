package cz.msebera.android.httpclient.impl.bootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

class ThreadFactoryImpl implements ThreadFactory {
    private final String a;
    private final ThreadGroup b;
    private final AtomicLong c;

    ThreadFactoryImpl(String str, ThreadGroup threadGroup) {
        this.a = str;
        this.b = threadGroup;
        this.c = new AtomicLong();
    }

    ThreadFactoryImpl(String str) {
        this(str, null);
    }

    public Thread newThread(Runnable runnable) {
        ThreadGroup threadGroup = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("-");
        sb.append(this.c.incrementAndGet());
        return new Thread(threadGroup, runnable, sb.toString());
    }
}
