package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.os.OperationCanceledException;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AsyncTaskLoader<D> extends Loader<D> {
    volatile LoadTask a;
    volatile LoadTask b;
    long c;
    long d;
    Handler e;
    private final Executor f;

    final class LoadTask extends ModernAsyncTask<Void, Void, D> implements Runnable {
        boolean a;
        private final CountDownLatch d = new CountDownLatch(1);

        LoadTask() {
        }

        /* access modifiers changed from: protected */
        public D a(Void... voidArr) {
            try {
                return AsyncTaskLoader.this.onLoadInBackground();
            } catch (OperationCanceledException e) {
                if (d()) {
                    return null;
                }
                throw e;
            }
        }

        /* access modifiers changed from: protected */
        public void a(D d2) {
            try {
                AsyncTaskLoader.this.b(this, d2);
            } finally {
                this.d.countDown();
            }
        }

        /* access modifiers changed from: protected */
        public void b(D d2) {
            try {
                AsyncTaskLoader.this.a(this, d2);
            } finally {
                this.d.countDown();
            }
        }

        public void run() {
            this.a = false;
            AsyncTaskLoader.this.a();
        }

        public void a() {
            try {
                this.d.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    public void cancelLoadInBackground() {
    }

    @Nullable
    public abstract D loadInBackground();

    public void onCanceled(@Nullable D d2) {
    }

    public AsyncTaskLoader(@NonNull Context context) {
        this(context, ModernAsyncTask.c);
    }

    private AsyncTaskLoader(@NonNull Context context, @NonNull Executor executor) {
        super(context);
        this.d = -10000;
        this.f = executor;
    }

    public void setUpdateThrottle(long j) {
        this.c = j;
        if (j != 0) {
            this.e = new Handler();
        }
    }

    /* access modifiers changed from: protected */
    public void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.a = new LoadTask<>();
        a();
    }

    /* access modifiers changed from: protected */
    public boolean onCancelLoad() {
        if (this.a == null) {
            return false;
        }
        if (!this.r) {
            this.u = true;
        }
        if (this.b != null) {
            if (this.a.a) {
                this.a.a = false;
                this.e.removeCallbacks(this.a);
            }
            this.a = null;
            return false;
        } else if (this.a.a) {
            this.a.a = false;
            this.e.removeCallbacks(this.a);
            this.a = null;
            return false;
        } else {
            boolean a2 = this.a.a(false);
            if (a2) {
                this.b = this.a;
                cancelLoadInBackground();
            }
            this.a = null;
            return a2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.b == null && this.a != null) {
            if (this.a.a) {
                this.a.a = false;
                this.e.removeCallbacks(this.a);
            }
            if (this.c <= 0 || SystemClock.uptimeMillis() >= this.d + this.c) {
                this.a.a(this.f, null);
            } else {
                this.a.a = true;
                this.e.postAtTime(this.a, this.d + this.c);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(LoadTask loadTask, D d2) {
        onCanceled(d2);
        if (this.b == loadTask) {
            rollbackContentChanged();
            this.d = SystemClock.uptimeMillis();
            this.b = null;
            deliverCancellation();
            a();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(LoadTask loadTask, D d2) {
        if (this.a != loadTask) {
            a(loadTask, d2);
        } else if (isAbandoned()) {
            onCanceled(d2);
        } else {
            commitContentChanged();
            this.d = SystemClock.uptimeMillis();
            this.a = null;
            deliverResult(d2);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public D onLoadInBackground() {
        return loadInBackground();
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.b != null;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void waitForLoader() {
        LoadTask loadTask = this.a;
        if (loadTask != null) {
            loadTask.a();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.a != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.a);
            printWriter.print(" waiting=");
            printWriter.println(this.a.a);
        }
        if (this.b != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.b);
            printWriter.print(" waiting=");
            printWriter.println(this.b.a);
        }
        if (this.c != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.c, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.d, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }
}
