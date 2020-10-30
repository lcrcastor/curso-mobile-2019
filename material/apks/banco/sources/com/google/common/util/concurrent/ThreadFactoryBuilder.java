package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckReturnValue;

@GwtIncompatible
@CanIgnoreReturnValue
public final class ThreadFactoryBuilder {
    private String a = null;
    private Boolean b = null;
    private Integer c = null;
    private UncaughtExceptionHandler d = null;
    private ThreadFactory e = null;

    public ThreadFactoryBuilder setNameFormat(String str) {
        b(str, Integer.valueOf(0));
        this.a = str;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean z) {
        this.b = Boolean.valueOf(z);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int i) {
        boolean z = false;
        Preconditions.checkArgument(i >= 1, "Thread priority (%s) must be >= %s", i, 1);
        if (i <= 10) {
            z = true;
        }
        Preconditions.checkArgument(z, "Thread priority (%s) must be <= %s", i, 10);
        this.c = Integer.valueOf(i);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.d = (UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.e = (ThreadFactory) Preconditions.checkNotNull(threadFactory);
        return this;
    }

    @CheckReturnValue
    public ThreadFactory build() {
        return a(this);
    }

    private static ThreadFactory a(ThreadFactoryBuilder threadFactoryBuilder) {
        final String str = threadFactoryBuilder.a;
        final Boolean bool = threadFactoryBuilder.b;
        final Integer num = threadFactoryBuilder.c;
        final UncaughtExceptionHandler uncaughtExceptionHandler = threadFactoryBuilder.d;
        final ThreadFactory defaultThreadFactory = threadFactoryBuilder.e != null ? threadFactoryBuilder.e : Executors.defaultThreadFactory();
        final AtomicLong atomicLong = str != null ? new AtomicLong(0) : null;
        AnonymousClass1 r0 = new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread newThread = defaultThreadFactory.newThread(runnable);
                if (str != null) {
                    newThread.setName(ThreadFactoryBuilder.b(str, Long.valueOf(atomicLong.getAndIncrement())));
                }
                if (bool != null) {
                    newThread.setDaemon(bool.booleanValue());
                }
                if (num != null) {
                    newThread.setPriority(num.intValue());
                }
                if (uncaughtExceptionHandler != null) {
                    newThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }
                return newThread;
            }
        };
        return r0;
    }

    /* access modifiers changed from: private */
    public static String b(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }
}
