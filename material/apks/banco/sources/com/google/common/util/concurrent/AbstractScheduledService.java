package com.google.common.util.concurrent;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public abstract class AbstractScheduledService implements Service {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(AbstractScheduledService.class.getName());
    /* access modifiers changed from: private */
    public final AbstractService b = new ServiceDelegate();

    @Beta
    public static abstract class CustomScheduler extends Scheduler {

        @Beta
        public static final class Schedule {
            private final long a;
            private final TimeUnit b;

            public Schedule(long j, TimeUnit timeUnit) {
                this.a = j;
                this.b = (TimeUnit) Preconditions.checkNotNull(timeUnit);
            }
        }

        /* access modifiers changed from: protected */
        public abstract Schedule getNextSchedule();

        public CustomScheduler() {
            super();
        }
    }

    public static abstract class Scheduler {
        /* access modifiers changed from: 0000 */
        public abstract Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        public static Scheduler newFixedDelaySchedule(long j, long j2, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "delay must be > 0, found %s", j2);
            final long j3 = j;
            final long j4 = j2;
            final TimeUnit timeUnit2 = timeUnit;
            AnonymousClass1 r2 = new Scheduler() {
                public Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable, j3, j4, timeUnit2);
                }
            };
            return r2;
        }

        public static Scheduler newFixedRateSchedule(long j, long j2, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "period must be > 0, found %s", j2);
            final long j3 = j;
            final long j4 = j2;
            final TimeUnit timeUnit2 = timeUnit;
            AnonymousClass2 r2 = new Scheduler() {
                public Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable, j3, j4, timeUnit2);
                }
            };
            return r2;
        }

        private Scheduler() {
        }
    }

    final class ServiceDelegate extends AbstractService {
        /* access modifiers changed from: private */
        public volatile Future<?> b;
        /* access modifiers changed from: private */
        public volatile ScheduledExecutorService c;
        /* access modifiers changed from: private */
        public final ReentrantLock d;
        /* access modifiers changed from: private */
        public final Runnable e;

        class Task implements Runnable {
            Task() {
            }

            public void run() {
                ServiceDelegate.this.d.lock();
                try {
                    if (ServiceDelegate.this.b.isCancelled()) {
                        ServiceDelegate.this.d.unlock();
                        return;
                    }
                    AbstractScheduledService.this.runOneIteration();
                    ServiceDelegate.this.d.unlock();
                } catch (Throwable th) {
                    ServiceDelegate.this.d.unlock();
                    throw th;
                }
            }
        }

        private ServiceDelegate() {
            this.d = new ReentrantLock();
            this.e = new Task();
        }

        /* access modifiers changed from: protected */
        public final void doStart() {
            this.c = MoreExecutors.a(AbstractScheduledService.this.executor(), (Supplier<String>) new Supplier<String>() {
                /* renamed from: a */
                public String get() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(AbstractScheduledService.this.serviceName());
                    sb.append(UtilsCuentas.SEPARAOR2);
                    sb.append(ServiceDelegate.this.state());
                    return sb.toString();
                }
            });
            this.c.execute(new Runnable() {
                public void run() {
                    ServiceDelegate.this.d.lock();
                    try {
                        AbstractScheduledService.this.startUp();
                        ServiceDelegate.this.b = AbstractScheduledService.this.scheduler().a(AbstractScheduledService.this.b, ServiceDelegate.this.c, ServiceDelegate.this.e);
                        ServiceDelegate.this.notifyStarted();
                    } catch (Throwable th) {
                        ServiceDelegate.this.d.unlock();
                        throw th;
                    }
                    ServiceDelegate.this.d.unlock();
                }
            });
        }

        /* access modifiers changed from: protected */
        public final void doStop() {
            this.b.cancel(false);
            this.c.execute(new Runnable() {
                public void run() {
                    try {
                        ServiceDelegate.this.d.lock();
                        if (ServiceDelegate.this.state() != State.STOPPING) {
                            ServiceDelegate.this.d.unlock();
                            return;
                        }
                        AbstractScheduledService.this.shutDown();
                        ServiceDelegate.this.d.unlock();
                        ServiceDelegate.this.notifyStopped();
                    } catch (Throwable th) {
                        ServiceDelegate.this.notifyFailed(th);
                    }
                }
            });
        }

        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void runOneIteration();

    /* access modifiers changed from: protected */
    public abstract Scheduler scheduler();

    /* access modifiers changed from: protected */
    public void shutDown() {
    }

    /* access modifiers changed from: protected */
    public void startUp() {
    }

    protected AbstractScheduledService() {
    }

    /* access modifiers changed from: protected */
    public ScheduledExecutorService executor() {
        final ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.a(AbstractScheduledService.this.serviceName(), runnable);
            }
        });
        addListener(new Listener() {
            public void terminated(State state) {
                newSingleThreadScheduledExecutor.shutdown();
            }

            public void failed(State state, Throwable th) {
                newSingleThreadScheduledExecutor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return newSingleThreadScheduledExecutor;
    }

    /* access modifiers changed from: protected */
    public String serviceName() {
        return getClass().getSimpleName();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceName());
        sb.append(" [");
        sb.append(state());
        sb.append("]");
        return sb.toString();
    }

    public final boolean isRunning() {
        return this.b.isRunning();
    }

    public final State state() {
        return this.b.state();
    }

    public final void addListener(Listener listener, Executor executor) {
        this.b.addListener(listener, executor);
    }

    public final Throwable failureCause() {
        return this.b.failureCause();
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.b.startAsync();
        return this;
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.b.stopAsync();
        return this;
    }

    public final void awaitRunning() {
        this.b.awaitRunning();
    }

    public final void awaitRunning(long j, TimeUnit timeUnit) {
        this.b.awaitRunning(j, timeUnit);
    }

    public final void awaitTerminated() {
        this.b.awaitTerminated();
    }

    public final void awaitTerminated(long j, TimeUnit timeUnit) {
        this.b.awaitTerminated(j, timeUnit);
    }
}
