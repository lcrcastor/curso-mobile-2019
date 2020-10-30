package com.google.common.util.concurrent;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@Beta
public abstract class AbstractIdleService implements Service {
    /* access modifiers changed from: private */
    public final Supplier<String> a = new ThreadNameSupplier();
    private final Service b = new DelegateService();

    final class DelegateService extends AbstractService {
        private DelegateService() {
        }

        /* access modifiers changed from: protected */
        public final void doStart() {
            MoreExecutors.a(AbstractIdleService.this.executor(), AbstractIdleService.this.a).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        DelegateService.this.notifyStarted();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public final void doStop() {
            MoreExecutors.a(AbstractIdleService.this.executor(), AbstractIdleService.this.a).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        DelegateService.this.notifyStopped();
                    } catch (Throwable th) {
                        DelegateService.this.notifyFailed(th);
                    }
                }
            });
        }

        public String toString() {
            return AbstractIdleService.this.toString();
        }
    }

    final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        /* renamed from: a */
        public String get() {
            StringBuilder sb = new StringBuilder();
            sb.append(AbstractIdleService.this.serviceName());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(AbstractIdleService.this.state());
            return sb.toString();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void shutDown();

    /* access modifiers changed from: protected */
    public abstract void startUp();

    protected AbstractIdleService() {
    }

    /* access modifiers changed from: protected */
    public Executor executor() {
        return new Executor() {
            public void execute(Runnable runnable) {
                MoreExecutors.a((String) AbstractIdleService.this.a.get(), runnable).start();
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
