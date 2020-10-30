package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

@GwtIncompatible
final class TimeoutFuture<V> extends TrustedFuture<V> {
    /* access modifiers changed from: private */
    @Nullable
    public ListenableFuture<V> a;
    @Nullable
    private Future<?> b;

    static final class Fire<V> implements Runnable {
        @Nullable
        TimeoutFuture<V> a;

        Fire(TimeoutFuture<V> timeoutFuture) {
            this.a = timeoutFuture;
        }

        public void run() {
            TimeoutFuture<V> timeoutFuture = this.a;
            if (timeoutFuture != null) {
                ListenableFuture a2 = timeoutFuture.a;
                if (a2 != null) {
                    this.a = null;
                    if (a2.isDone()) {
                        timeoutFuture.setFuture(a2);
                    } else {
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Future timed out: ");
                            sb.append(a2);
                            timeoutFuture.setException(new TimeoutException(sb.toString()));
                        } finally {
                            a2.cancel(true);
                        }
                    }
                }
            }
        }
    }

    static <V> ListenableFuture<V> a(ListenableFuture<V> listenableFuture, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        TimeoutFuture timeoutFuture = new TimeoutFuture(listenableFuture);
        Fire fire = new Fire(timeoutFuture);
        timeoutFuture.b = scheduledExecutorService.schedule(fire, j, timeUnit);
        listenableFuture.addListener(fire, MoreExecutors.directExecutor());
        return timeoutFuture;
    }

    private TimeoutFuture(ListenableFuture<V> listenableFuture) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
        a((Future<?>) this.a);
        Future<?> future = this.b;
        if (future != null) {
            future.cancel(false);
        }
        this.a = null;
        this.b = null;
    }
}
