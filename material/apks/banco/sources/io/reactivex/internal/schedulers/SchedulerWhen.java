package io.reactivex.internal.schedulers;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Experimental
public class SchedulerWhen extends Scheduler implements Disposable {
    static final Disposable b = new SubscribedDisposable();
    static final Disposable c = Disposables.disposed();
    private final Scheduler d;
    private final FlowableProcessor<Flowable<Completable>> e = UnicastProcessor.create().toSerialized();
    private Disposable f;

    static final class CreateWorkerFunction implements Function<ScheduledAction, Completable> {
        final Worker a;

        final class WorkerCompletable extends Completable {
            final ScheduledAction a;

            WorkerCompletable(ScheduledAction scheduledAction) {
                this.a = scheduledAction;
            }

            /* access modifiers changed from: protected */
            public void subscribeActual(CompletableObserver completableObserver) {
                completableObserver.onSubscribe(this.a);
                this.a.b(CreateWorkerFunction.this.a, completableObserver);
            }
        }

        CreateWorkerFunction(Worker worker) {
            this.a = worker;
        }

        /* renamed from: a */
        public Completable apply(ScheduledAction scheduledAction) {
            return new WorkerCompletable(scheduledAction);
        }
    }

    static class DelayedAction extends ScheduledAction {
        private final Runnable a;
        private final long b;
        private final TimeUnit c;

        DelayedAction(Runnable runnable, long j, TimeUnit timeUnit) {
            this.a = runnable;
            this.b = j;
            this.c = timeUnit;
        }

        /* access modifiers changed from: protected */
        public Disposable a(Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new OnCompletedAction(this.a, completableObserver), this.b, this.c);
        }
    }

    static class ImmediateAction extends ScheduledAction {
        private final Runnable a;

        ImmediateAction(Runnable runnable) {
            this.a = runnable;
        }

        /* access modifiers changed from: protected */
        public Disposable a(Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new OnCompletedAction(this.a, completableObserver));
        }
    }

    static class OnCompletedAction implements Runnable {
        final CompletableObserver a;
        final Runnable b;

        OnCompletedAction(Runnable runnable, CompletableObserver completableObserver) {
            this.b = runnable;
            this.a = completableObserver;
        }

        public void run() {
            try {
                this.b.run();
            } finally {
                this.a.onComplete();
            }
        }
    }

    static final class QueueWorker extends Worker {
        private final AtomicBoolean a = new AtomicBoolean();
        private final FlowableProcessor<ScheduledAction> b;
        private final Worker c;

        QueueWorker(FlowableProcessor<ScheduledAction> flowableProcessor, Worker worker) {
            this.b = flowableProcessor;
            this.c = worker;
        }

        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.onComplete();
                this.c.dispose();
            }
        }

        public boolean isDisposed() {
            return this.a.get();
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            DelayedAction delayedAction = new DelayedAction(runnable, j, timeUnit);
            this.b.onNext(delayedAction);
            return delayedAction;
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            ImmediateAction immediateAction = new ImmediateAction(runnable);
            this.b.onNext(immediateAction);
            return immediateAction;
        }
    }

    static abstract class ScheduledAction extends AtomicReference<Disposable> implements Disposable {
        /* access modifiers changed from: protected */
        public abstract Disposable a(Worker worker, CompletableObserver completableObserver);

        ScheduledAction() {
            super(SchedulerWhen.b);
        }

        /* access modifiers changed from: 0000 */
        public void b(Worker worker, CompletableObserver completableObserver) {
            Disposable disposable = (Disposable) get();
            if (disposable != SchedulerWhen.c && disposable == SchedulerWhen.b) {
                Disposable a = a(worker, completableObserver);
                if (!compareAndSet(SchedulerWhen.b, a)) {
                    a.dispose();
                }
            }
        }

        public boolean isDisposed() {
            return ((Disposable) get()).isDisposed();
        }

        public void dispose() {
            Disposable disposable;
            Disposable disposable2 = SchedulerWhen.c;
            do {
                disposable = (Disposable) get();
                if (disposable == SchedulerWhen.c) {
                    return;
                }
            } while (!compareAndSet(disposable, disposable2));
            if (disposable != SchedulerWhen.b) {
                disposable.dispose();
            }
        }
    }

    static final class SubscribedDisposable implements Disposable {
        public void dispose() {
        }

        public boolean isDisposed() {
            return false;
        }

        SubscribedDisposable() {
        }
    }

    public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> function, Scheduler scheduler) {
        this.d = scheduler;
        try {
            this.f = ((Completable) function.apply(this.e)).subscribe();
        } catch (Throwable th) {
            Exceptions.propagate(th);
        }
    }

    public void dispose() {
        this.f.dispose();
    }

    public boolean isDisposed() {
        return this.f.isDisposed();
    }

    @NonNull
    public Worker createWorker() {
        Worker createWorker = this.d.createWorker();
        FlowableProcessor serialized = UnicastProcessor.create().toSerialized();
        Flowable map = serialized.map(new CreateWorkerFunction(createWorker));
        QueueWorker queueWorker = new QueueWorker(serialized, createWorker);
        this.e.onNext(map);
        return queueWorker;
    }
}
