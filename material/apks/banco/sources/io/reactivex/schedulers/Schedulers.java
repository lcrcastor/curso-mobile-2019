package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SchedulerPoolFactory;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public final class Schedulers {
    @NonNull
    static final Scheduler a = RxJavaPlugins.initSingleScheduler(new SingleTask());
    @NonNull
    static final Scheduler b = RxJavaPlugins.initComputationScheduler(new ComputationTask());
    @NonNull
    static final Scheduler c = RxJavaPlugins.initIoScheduler(new IOTask());
    @NonNull
    static final Scheduler d = TrampolineScheduler.instance();
    @NonNull
    static final Scheduler e = RxJavaPlugins.initNewThreadScheduler(new NewThreadTask());

    static final class ComputationHolder {
        static final Scheduler a = new ComputationScheduler();

        ComputationHolder() {
        }
    }

    static final class ComputationTask implements Callable<Scheduler> {
        ComputationTask() {
        }

        /* renamed from: a */
        public Scheduler call() {
            return ComputationHolder.a;
        }
    }

    static final class IOTask implements Callable<Scheduler> {
        IOTask() {
        }

        /* renamed from: a */
        public Scheduler call() {
            return IoHolder.a;
        }
    }

    static final class IoHolder {
        static final Scheduler a = new IoScheduler();

        IoHolder() {
        }
    }

    static final class NewThreadHolder {
        static final Scheduler a = new NewThreadScheduler();

        NewThreadHolder() {
        }
    }

    static final class NewThreadTask implements Callable<Scheduler> {
        NewThreadTask() {
        }

        /* renamed from: a */
        public Scheduler call() {
            return NewThreadHolder.a;
        }
    }

    static final class SingleHolder {
        static final Scheduler a = new SingleScheduler();

        SingleHolder() {
        }
    }

    static final class SingleTask implements Callable<Scheduler> {
        SingleTask() {
        }

        /* renamed from: a */
        public Scheduler call() {
            return SingleHolder.a;
        }
    }

    private Schedulers() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static Scheduler computation() {
        return RxJavaPlugins.onComputationScheduler(b);
    }

    @NonNull
    public static Scheduler io() {
        return RxJavaPlugins.onIoScheduler(c);
    }

    @NonNull
    public static Scheduler trampoline() {
        return d;
    }

    @NonNull
    public static Scheduler newThread() {
        return RxJavaPlugins.onNewThreadScheduler(e);
    }

    @NonNull
    public static Scheduler single() {
        return RxJavaPlugins.onSingleScheduler(a);
    }

    @NonNull
    public static Scheduler from(@NonNull Executor executor) {
        return new ExecutorScheduler(executor);
    }

    public static void shutdown() {
        computation().shutdown();
        io().shutdown();
        newThread().shutdown();
        single().shutdown();
        trampoline().shutdown();
        SchedulerPoolFactory.shutdown();
    }

    public static void start() {
        computation().start();
        io().start();
        newThread().start();
        single().start();
        trampoline().start();
        SchedulerPoolFactory.start();
    }
}
