package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import java.util.concurrent.ThreadFactory;

public final class NewThreadScheduler extends Scheduler {
    private static final RxThreadFactory c = new RxThreadFactory("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5).intValue())));
    final ThreadFactory b;

    public NewThreadScheduler() {
        this(c);
    }

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.b = threadFactory;
    }

    @NonNull
    public Worker createWorker() {
        return new NewThreadWorker(this.b);
    }
}
