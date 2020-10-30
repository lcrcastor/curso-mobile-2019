package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

@GwtCompatible(emulated = true)
public final class MoreExecutors {

    /* renamed from: com.google.common.util.concurrent.MoreExecutors$1 reason: invalid class name */
    static class AnonymousClass1 implements Runnable {
        final /* synthetic */ BlockingQueue a;
        final /* synthetic */ ListenableFuture b;

        public void run() {
            this.a.add(this.b);
        }
    }

    @GwtIncompatible
    @VisibleForTesting
    static class Application {
        Application() {
        }

        /* access modifiers changed from: 0000 */
        public final ExecutorService a(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.b(threadPoolExecutor);
            ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            a(unconfigurableExecutorService, j, timeUnit);
            return unconfigurableExecutorService;
        }

        /* access modifiers changed from: 0000 */
        public final ScheduledExecutorService a(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.b(scheduledThreadPoolExecutor);
            ScheduledExecutorService unconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            a((ExecutorService) unconfigurableScheduledExecutorService, j, timeUnit);
            return unconfigurableScheduledExecutorService;
        }

        /* access modifiers changed from: 0000 */
        public final void a(ExecutorService executorService, long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            StringBuilder sb = new StringBuilder();
            sb.append("DelayedShutdownHook-for-");
            sb.append(executorService);
            String sb2 = sb.toString();
            final ExecutorService executorService2 = executorService;
            final long j2 = j;
            final TimeUnit timeUnit2 = timeUnit;
            AnonymousClass1 r1 = new Runnable() {
                public void run() {
                    try {
                        executorService2.shutdown();
                        executorService2.awaitTermination(j2, timeUnit2);
                    } catch (InterruptedException unused) {
                    }
                }
            };
            a(MoreExecutors.a(sb2, (Runnable) r1));
        }

        /* access modifiers changed from: 0000 */
        public final ExecutorService a(ThreadPoolExecutor threadPoolExecutor) {
            return a(threadPoolExecutor, 120, TimeUnit.SECONDS);
        }

        /* access modifiers changed from: 0000 */
        public final ScheduledExecutorService a(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return a(scheduledThreadPoolExecutor, 120, TimeUnit.SECONDS);
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public void a(Thread thread) {
            Runtime.getRuntime().addShutdownHook(thread);
        }
    }

    enum DirectExecutor implements Executor {
        INSTANCE;

        public String toString() {
            return "MoreExecutors.directExecutor()";
        }

        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    @GwtIncompatible
    static final class DirectExecutorService extends AbstractListeningExecutorService {
        private final Object a;
        @GuardedBy("lock")
        private int b;
        @GuardedBy("lock")
        private boolean c;

        private DirectExecutorService() {
            this.a = new Object();
            this.b = 0;
            this.c = false;
        }

        /* synthetic */ DirectExecutorService(AnonymousClass1 r1) {
            this();
        }

        public void execute(Runnable runnable) {
            a();
            try {
                runnable.run();
            } finally {
                b();
            }
        }

        public boolean isShutdown() {
            boolean z;
            synchronized (this.a) {
                z = this.c;
            }
            return z;
        }

        public void shutdown() {
            synchronized (this.a) {
                this.c = true;
                if (this.b == 0) {
                    this.a.notifyAll();
                }
            }
        }

        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        public boolean isTerminated() {
            boolean z;
            synchronized (this.a) {
                z = this.c && this.b == 0;
            }
            return z;
        }

        public boolean awaitTermination(long j, TimeUnit timeUnit) {
            long nanos = timeUnit.toNanos(j);
            synchronized (this.a) {
                while (true) {
                    if (this.c && this.b == 0) {
                        return true;
                    }
                    if (nanos <= 0) {
                        return false;
                    }
                    long nanoTime = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.a, nanos);
                    nanos -= System.nanoTime() - nanoTime;
                }
            }
        }

        private void a() {
            synchronized (this.a) {
                if (this.c) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                this.b++;
            }
        }

        private void b() {
            synchronized (this.a) {
                int i = this.b - 1;
                this.b = i;
                if (i == 0) {
                    this.a.notifyAll();
                }
            }
        }
    }

    @GwtIncompatible
    static class ListeningDecorator extends AbstractListeningExecutorService {
        private final ExecutorService a;

        ListeningDecorator(ExecutorService executorService) {
            this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
        }

        public final boolean awaitTermination(long j, TimeUnit timeUnit) {
            return this.a.awaitTermination(j, timeUnit);
        }

        public final boolean isShutdown() {
            return this.a.isShutdown();
        }

        public final boolean isTerminated() {
            return this.a.isTerminated();
        }

        public final void shutdown() {
            this.a.shutdown();
        }

        public final List<Runnable> shutdownNow() {
            return this.a.shutdownNow();
        }

        public final void execute(Runnable runnable) {
            this.a.execute(runnable);
        }
    }

    @GwtIncompatible
    static final class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService {
        final ScheduledExecutorService a;

        static final class ListenableScheduledTask<V> extends SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> a;

            public ListenableScheduledTask(ListenableFuture<V> listenableFuture, ScheduledFuture<?> scheduledFuture) {
                super(listenableFuture);
                this.a = scheduledFuture;
            }

            public boolean cancel(boolean z) {
                boolean cancel = super.cancel(z);
                if (cancel) {
                    this.a.cancel(z);
                }
                return cancel;
            }

            public long getDelay(TimeUnit timeUnit) {
                return this.a.getDelay(timeUnit);
            }

            /* renamed from: a */
            public int compareTo(Delayed delayed) {
                return this.a.compareTo(delayed);
            }
        }

        @GwtIncompatible
        static final class NeverSuccessfulListenableFutureTask extends AbstractFuture<Void> implements Runnable {
            private final Runnable a;

            public NeverSuccessfulListenableFutureTask(Runnable runnable) {
                this.a = (Runnable) Preconditions.checkNotNull(runnable);
            }

            public void run() {
                try {
                    this.a.run();
                } catch (Throwable th) {
                    setException(th);
                    throw Throwables.propagate(th);
                }
            }
        }

        ScheduledListeningDecorator(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.a = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService);
        }

        public ListenableScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            TrustedListenableFutureTask a2 = TrustedListenableFutureTask.a(runnable, null);
            return new ListenableScheduledTask(a2, this.a.schedule(a2, j, timeUnit));
        }

        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
            TrustedListenableFutureTask a2 = TrustedListenableFutureTask.a(callable);
            return new ListenableScheduledTask(a2, this.a.schedule(a2, j, timeUnit));
        }

        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.a.scheduleAtFixedRate(neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }

        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.a.scheduleWithFixedDelay(neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }
    }

    private MoreExecutors() {
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
        return new Application().a(threadPoolExecutor, j, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
        return new Application().a(scheduledThreadPoolExecutor, j, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static void addDelayedShutdownHook(ExecutorService executorService, long j, TimeUnit timeUnit) {
        new Application().a(executorService, j, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        return new Application().a(threadPoolExecutor);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new Application().a(scheduledThreadPoolExecutor);
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static void b(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }

    @GwtIncompatible
    @Deprecated
    public static ListeningExecutorService sameThreadExecutor() {
        return new DirectExecutorService(null);
    }

    @GwtIncompatible
    public static ListeningExecutorService newDirectExecutorService() {
        return new DirectExecutorService(null);
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    @GwtIncompatible
    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        if (executorService instanceof ListeningExecutorService) {
            return (ListeningExecutorService) executorService;
        }
        return executorService instanceof ScheduledExecutorService ? new ScheduledListeningDecorator((ScheduledExecutorService) executorService) : new ListeningDecorator(executorService);
    }

    @GwtIncompatible
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService scheduledExecutorService) {
        return scheduledExecutorService instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService) scheduledExecutorService : new ScheduledListeningDecorator(scheduledExecutorService);
    }

    @GwtIncompatible
    @Beta
    public static ThreadFactory platformThreadFactory() {
        if (!a()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e3);
        } catch (InvocationTargetException e4) {
            throw Throwables.propagate(e4.getCause());
        }
    }

    @GwtIncompatible
    private static boolean a() {
        boolean z = false;
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            if (Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null) {
                z = true;
            }
            return z;
        } catch (ClassNotFoundException unused) {
            return false;
        } catch (InvocationTargetException unused2) {
            return false;
        } catch (IllegalAccessException unused3) {
            return false;
        } catch (NoSuchMethodException unused4) {
            return false;
        }
    }

    @GwtIncompatible
    static Thread a(String str, Runnable runnable) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(runnable);
        Thread newThread = platformThreadFactory().newThread(runnable);
        try {
            newThread.setName(str);
        } catch (SecurityException unused) {
        }
        return newThread;
    }

    @GwtIncompatible
    static Executor a(final Executor executor, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(supplier);
        if (a()) {
            return executor;
        }
        return new Executor() {
            public void execute(Runnable runnable) {
                executor.execute(Callables.a(runnable, supplier));
            }
        };
    }

    @GwtIncompatible
    static ScheduledExecutorService a(ScheduledExecutorService scheduledExecutorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(scheduledExecutorService);
        Preconditions.checkNotNull(supplier);
        if (a()) {
            return scheduledExecutorService;
        }
        return new WrappingScheduledExecutorService(scheduledExecutorService) {
            /* access modifiers changed from: protected */
            public <T> Callable<T> a(Callable<T> callable) {
                return Callables.a(callable, supplier);
            }

            /* access modifiers changed from: protected */
            public Runnable a(Runnable runnable) {
                return Callables.a(runnable, supplier);
            }
        };
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j) / 2;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(nanos, TimeUnit.NANOSECONDS)) {
                executorService.shutdownNow();
                executorService.awaitTermination(nanos, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
        return executorService.isTerminated();
    }

    static Executor a(final Executor executor, final AbstractFuture<?> abstractFuture) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(abstractFuture);
        if (executor == directExecutor()) {
            return executor;
        }
        return new Executor() {
            volatile boolean a = true;

            public void execute(final Runnable runnable) {
                try {
                    executor.execute(new Runnable() {
                        public void run() {
                            AnonymousClass5.this.a = false;
                            runnable.run();
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (this.a) {
                        abstractFuture.setException(e);
                    }
                }
            }
        };
    }
}
