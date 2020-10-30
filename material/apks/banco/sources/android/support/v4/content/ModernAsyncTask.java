package android.support.v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
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

abstract class ModernAsyncTask<Params, Progress, Result> {
    private static final ThreadFactory a = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("ModernAsyncTask #");
            sb.append(this.a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };
    private static final BlockingQueue<Runnable> b = new LinkedBlockingQueue(10);
    public static final Executor c;
    private static InternalHandler d;
    private static volatile Executor e = c;
    private final WorkerRunnable<Params, Result> f = new WorkerRunnable<Params, Result>() {
        public Result call() {
            ModernAsyncTask.this.j.set(true);
            Object obj = null;
            try {
                Process.setThreadPriority(10);
                Result a2 = ModernAsyncTask.this.a((Params[]) this.b);
                try {
                    Binder.flushPendingCommands();
                    ModernAsyncTask.this.d(a2);
                    return a2;
                } catch (Throwable th) {
                    th = th;
                    obj = a2;
                    ModernAsyncTask.this.d(obj);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                ModernAsyncTask.this.i.set(true);
                throw th;
            }
        }
    };
    private final FutureTask<Result> g = new FutureTask<Result>(this.f) {
        /* access modifiers changed from: protected */
        public void done() {
            try {
                ModernAsyncTask.this.c(get());
            } catch (InterruptedException e) {
                Log.w("AsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occurred while executing doInBackground()", e2.getCause());
            } catch (CancellationException unused) {
                ModernAsyncTask.this.c(null);
            } catch (Throwable th) {
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    };
    private volatile Status h = Status.PENDING;
    /* access modifiers changed from: private */
    public final AtomicBoolean i = new AtomicBoolean();
    /* access modifiers changed from: private */
    public final AtomicBoolean j = new AtomicBoolean();

    static class AsyncTaskResult<Data> {
        final ModernAsyncTask a;
        final Data[] b;

        AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data... dataArr) {
            this.a = modernAsyncTask;
            this.b = dataArr;
        }
    }

    static class InternalHandler extends Handler {
        InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            switch (message.what) {
                case 1:
                    asyncTaskResult.a.e(asyncTaskResult.b[0]);
                    return;
                case 2:
                    asyncTaskResult.a.b((Progress[]) asyncTaskResult.b);
                    return;
                default:
                    return;
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

        WorkerRunnable() {
        }
    }

    /* access modifiers changed from: protected */
    public abstract Result a(Params... paramsArr);

    /* access modifiers changed from: protected */
    public void a(Result result) {
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    /* access modifiers changed from: protected */
    public void b(Progress... progressArr) {
    }

    /* access modifiers changed from: protected */
    public void c() {
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, b, a);
        c = threadPoolExecutor;
    }

    private static Handler a() {
        InternalHandler internalHandler;
        synchronized (ModernAsyncTask.class) {
            if (d == null) {
                d = new InternalHandler();
            }
            internalHandler = d;
        }
        return internalHandler;
    }

    ModernAsyncTask() {
    }

    /* access modifiers changed from: 0000 */
    public void c(Result result) {
        if (!this.j.get()) {
            d(result);
        }
    }

    /* access modifiers changed from: 0000 */
    public Result d(Result result) {
        a().obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    /* access modifiers changed from: protected */
    public void b(Result result) {
        c();
    }

    public final boolean d() {
        return this.i.get();
    }

    public final boolean a(boolean z) {
        this.i.set(true);
        return this.g.cancel(z);
    }

    public final ModernAsyncTask<Params, Progress, Result> a(Executor executor, Params... paramsArr) {
        if (this.h != Status.PENDING) {
            switch (this.h) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                default:
                    throw new IllegalStateException("We should never reach this state");
            }
        } else {
            this.h = Status.RUNNING;
            b();
            this.f.b = paramsArr;
            executor.execute(this.g);
            return this;
        }
    }

    /* access modifiers changed from: 0000 */
    public void e(Result result) {
        if (d()) {
            b(result);
        } else {
            a(result);
        }
        this.h = Status.FINISHED;
    }
}
