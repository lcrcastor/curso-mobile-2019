package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler b = new TrampolineScheduler();

    static final class SleepingRunnable implements Runnable {
        private final Runnable a;
        private final TrampolineWorker b;
        private final long c;

        SleepingRunnable(Runnable runnable, TrampolineWorker trampolineWorker, long j) {
            this.a = runnable;
            this.b = trampolineWorker;
            this.c = j;
        }

        public void run() {
            if (!this.b.c) {
                long now = this.b.now(TimeUnit.MILLISECONDS);
                if (this.c > now) {
                    long j = this.c - now;
                    if (j > 0) {
                        try {
                            Thread.sleep(j);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            RxJavaPlugins.onError(e);
                            return;
                        }
                    }
                }
                if (!this.b.c) {
                    this.a.run();
                }
            }
        }
    }

    static final class TimedRunnable implements Comparable<TimedRunnable> {
        final Runnable a;
        final long b;
        final int c;
        volatile boolean d;

        TimedRunnable(Runnable runnable, Long l, int i) {
            this.a = runnable;
            this.b = l.longValue();
            this.c = i;
        }

        /* renamed from: a */
        public int compareTo(TimedRunnable timedRunnable) {
            int compare = ObjectHelper.compare(this.b, timedRunnable.b);
            return compare == 0 ? ObjectHelper.compare(this.c, timedRunnable.c) : compare;
        }
    }

    static final class TrampolineWorker extends Worker implements Disposable {
        final PriorityBlockingQueue<TimedRunnable> a = new PriorityBlockingQueue<>();
        final AtomicInteger b = new AtomicInteger();
        volatile boolean c;
        private final AtomicInteger d = new AtomicInteger();

        final class AppendToQueueTask implements Runnable {
            final TimedRunnable a;

            AppendToQueueTask(TimedRunnable timedRunnable) {
                this.a = timedRunnable;
            }

            public void run() {
                this.a.d = true;
                TrampolineWorker.this.a.remove(this.a);
            }
        }

        TrampolineWorker() {
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return a(runnable, now(TimeUnit.MILLISECONDS));
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            long now = now(TimeUnit.MILLISECONDS) + timeUnit.toMillis(j);
            return a(new SleepingRunnable(runnable, this, now), now);
        }

        /* access modifiers changed from: 0000 */
        public Disposable a(Runnable runnable, long j) {
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            TimedRunnable timedRunnable = new TimedRunnable(runnable, Long.valueOf(j), this.b.incrementAndGet());
            this.a.add(timedRunnable);
            if (this.d.getAndIncrement() != 0) {
                return Disposables.fromRunnable(new AppendToQueueTask(timedRunnable));
            }
            int i = 1;
            while (true) {
                TimedRunnable timedRunnable2 = (TimedRunnable) this.a.poll();
                if (timedRunnable2 == null) {
                    i = this.d.addAndGet(-i);
                    if (i == 0) {
                        return EmptyDisposable.INSTANCE;
                    }
                } else if (!timedRunnable2.d) {
                    timedRunnable2.a.run();
                }
            }
        }

        public void dispose() {
            this.c = true;
        }

        public boolean isDisposed() {
            return this.c;
        }
    }

    public static TrampolineScheduler instance() {
        return b;
    }

    @NonNull
    public Worker createWorker() {
        return new TrampolineWorker();
    }

    TrampolineScheduler() {
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        runnable.run();
        return EmptyDisposable.INSTANCE;
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(j);
            runnable.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(e);
        }
        return EmptyDisposable.INSTANCE;
    }
}
