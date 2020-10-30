package android.arch.core.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestrictTo({Scope.LIBRARY_GROUP})
public class DefaultTaskExecutor extends TaskExecutor {
    private final Object a = new Object();
    private ExecutorService b = Executors.newFixedThreadPool(2);
    @Nullable
    private volatile Handler c;

    public void executeOnDiskIO(Runnable runnable) {
        this.b.execute(runnable);
    }

    public void postToMainThread(Runnable runnable) {
        if (this.c == null) {
            synchronized (this.a) {
                if (this.c == null) {
                    this.c = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.c.post(runnable);
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
