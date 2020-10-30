package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public abstract class AbstractExecutionThreadService implements Service {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service b = new AbstractService() {
        /* access modifiers changed from: protected */
        public final void doStart() {
            MoreExecutors.a(AbstractExecutionThreadService.this.executor(), (Supplier<String>) new Supplier<String>() {
                /* renamed from: a */
                public String get() {
                    return AbstractExecutionThreadService.this.serviceName();
                }
            }).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractExecutionThreadService.this.startUp();
                        AnonymousClass1.this.notifyStarted();
                        if (AnonymousClass1.this.isRunning()) {
                            AbstractExecutionThreadService.this.run();
                        }
                        AbstractExecutionThreadService.this.shutDown();
                        AnonymousClass1.this.notifyStopped();
                    } catch (Throwable th) {
                        AnonymousClass1.this.notifyFailed(th);
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    };

    /* access modifiers changed from: protected */
    public abstract void run();

    /* access modifiers changed from: protected */
    public void shutDown() {
    }

    /* access modifiers changed from: protected */
    public void startUp() {
    }

    /* access modifiers changed from: protected */
    public void triggerShutdown() {
    }

    protected AbstractExecutionThreadService() {
    }

    /* access modifiers changed from: protected */
    public Executor executor() {
        return new Executor() {
            public void execute(Runnable runnable) {
                MoreExecutors.a(AbstractExecutionThreadService.this.serviceName(), runnable).start();
            }
        };
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

    /* access modifiers changed from: protected */
    public String serviceName() {
        return getClass().getSimpleName();
    }
}
