package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncTask<Params, Progress, Result> {
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    public static final Executor THREAD_POOL_EXECUTOR;
    private static final int a = Runtime.getRuntime().availableProcessors();
    private static final int b = (a + 1);
    private static final int c = ((a * 2) + 1);
    private static final ThreadFactory d = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("AsyncTask #");
            sb.append(this.a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };
    private static final BlockingQueue<Runnable> e = new LinkedBlockingQueue(128);
    private static final InternalHandler f = new InternalHandler();
    private static volatile Executor g = SERIAL_EXECUTOR;
    private final WorkerRunnable<Params, Result> h = new WorkerRunnable<Params, Result>() {
        public Result call() {
            AsyncTask.this.l.set(true);
            Process.setThreadPriority(10);
            return AsyncTask.this.b(AsyncTask.this.doInBackground(this.b));
        }
    };
    private final FutureTask<Result> i = new FutureTask<Result>(this.h) {
        /* access modifiers changed from: protected */
        public void done() {
            try {
                AsyncTask.this.a(get());
            } catch (InterruptedException e) {
                Log.w("AsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException unused) {
                AsyncTask.this.a(null);
            }
        }
    };
    private volatile Status j = Status.PENDING;
    private final AtomicBoolean k = new AtomicBoolean();
    /* access modifiers changed from: private */
    public final AtomicBoolean l = new AtomicBoolean();

    static class AsyncTaskResult<Data> {
        final AsyncTask a;
        final Data[] b;

        AsyncTaskResult(AsyncTask asyncTask, Data... dataArr) {
            this.a = asyncTask;
            this.b = dataArr;
        }
    }

    static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            switch (message.what) {
                case 1:
                    asyncTaskResult.a.c(asyncTaskResult.b[0]);
                    return;
                case 2:
                    asyncTaskResult.a.onProgressUpdate(asyncTaskResult.b);
                    return;
                default:
                    return;
            }
        }
    }

    static class SerialExecutor implements Executor {
        final LinkedList<Runnable> a;
        Runnable b;

        private SerialExecutor() {
            this.a = new LinkedList<>();
        }

        public synchronized void execute(final Runnable runnable) {
            this.a.offer(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.a();
                    }
                }
            });
            if (this.b == null) {
                a();
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void a() {
            Runnable runnable = (Runnable) this.a.poll();
            this.b = runnable;
            if (runnable != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.b);
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] b;

        private WorkerRunnable() {
        }
    }

    /* access modifiers changed from: protected */
    public abstract Result doInBackground(Params... paramsArr);

    /* access modifiers changed from: protected */
    public void onCancelled() {
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Result result) {
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Progress... progressArr) {
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(b, c, 1, TimeUnit.SECONDS, e, d);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    public static void init() {
        f.getLooper();
    }

    public static void setDefaultExecutor(Executor executor) {
        g = executor;
    }

    /* access modifiers changed from: private */
    public void a(Result result) {
        if (!this.l.get()) {
            b(result);
        }
    }

    /* access modifiers changed from: private */
    public Result b(Result result) {
        f.obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.j;
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Result result) {
        onCancelled();
    }

    public final boolean isCancelled() {
        return this.k.get();
    }

    public final boolean cancel(boolean z) {
        this.k.set(true);
        return this.i.cancel(z);
    }

    public final Result get() {
        return this.i.get();
    }

    public final Result get(long j2, TimeUnit timeUnit) {
        return this.i.get(j2, timeUnit);
    }

    public final AsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        return executeOnExecutor(g, paramsArr);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramsArr) {
        if (this.j != Status.PENDING) {
            switch (this.j) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.j = Status.RUNNING;
        onPreExecute();
        this.h.b = paramsArr;
        executor.execute(this.i);
        return this;
    }

    public static void execute(Runnable runnable) {
        g.execute(runnable);
    }

    /* access modifiers changed from: protected */
    public final void publishProgress(Progress... progressArr) {
        if (!isCancelled()) {
            f.obtainMessage(2, new AsyncTaskResult(this, progressArr)).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void c(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.j = Status.FINISHED;
    }
}
