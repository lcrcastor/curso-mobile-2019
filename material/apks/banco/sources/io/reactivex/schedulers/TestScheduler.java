package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class TestScheduler extends Scheduler {
    final Queue<TimedRunnable> b = new PriorityBlockingQueue(11);
    long c;
    volatile long d;

    final class TestWorker extends Worker {
        volatile boolean a;

        final class QueueRemove implements Runnable {
            final TimedRunnable a;

            QueueRemove(TimedRunnable timedRunnable) {
                this.a = timedRunnable;
            }

            public void run() {
                TestScheduler.this.b.remove(this.a);
            }
        }

        TestWorker() {
        }

        public void dispose() {
            this.a = true;
        }

        public boolean isDisposed() {
            return this.a;
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            long nanos = TestScheduler.this.d + timeUnit.toNanos(j);
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.c;
            testScheduler.c = j2 + 1;
            TimedRunnable timedRunnable = new TimedRunnable(this, nanos, runnable, j2);
            TestScheduler.this.b.add(timedRunnable);
            return Disposables.fromRunnable(new QueueRemove(timedRunnable));
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j = testScheduler.c;
            testScheduler.c = j + 1;
            TimedRunnable timedRunnable = new TimedRunnable(this, 0, runnable, j);
            TestScheduler.this.b.add(timedRunnable);
            return Disposables.fromRunnable(new QueueRemove(timedRunnable));
        }

        public long now(@NonNull TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }
    }

    static final class TimedRunnable implements Comparable<TimedRunnable> {
        final long a;
        final Runnable b;
        final TestWorker c;
        final long d;

        TimedRunnable(TestWorker testWorker, long j, Runnable runnable, long j2) {
            this.a = j;
            this.b = runnable;
            this.c = testWorker;
            this.d = j2;
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", new Object[]{Long.valueOf(this.a), this.b.toString()});
        }

        /* renamed from: a */
        public int compareTo(TimedRunnable timedRunnable) {
            if (this.a == timedRunnable.a) {
                return ObjectHelper.compare(this.d, timedRunnable.d);
            }
            return ObjectHelper.compare(this.a, timedRunnable.a);
        }
    }

    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.d, TimeUnit.NANOSECONDS);
    }

    public void advanceTimeBy(long j, TimeUnit timeUnit) {
        advanceTimeTo(this.d + timeUnit.toNanos(j), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j, TimeUnit timeUnit) {
        a(timeUnit.toNanos(j));
    }

    public void triggerActions() {
        a(this.d);
    }

    private void a(long j) {
        while (!this.b.isEmpty()) {
            TimedRunnable timedRunnable = (TimedRunnable) this.b.peek();
            if (timedRunnable.a > j) {
                break;
            }
            this.d = timedRunnable.a == 0 ? this.d : timedRunnable.a;
            this.b.remove();
            if (!timedRunnable.c.a) {
                timedRunnable.b.run();
            }
        }
        this.d = j;
    }

    @NonNull
    public Worker createWorker() {
        return new TestWorker();
    }
}
