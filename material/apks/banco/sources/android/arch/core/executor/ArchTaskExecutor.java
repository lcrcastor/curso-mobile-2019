package android.arch.core.executor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.concurrent.Executor;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor a;
    @NonNull
    private static final Executor d = new Executor() {
        public void execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().postToMainThread(runnable);
        }
    };
    @NonNull
    private static final Executor e = new Executor() {
        public void execute(Runnable runnable) {
            ArchTaskExecutor.getInstance().executeOnDiskIO(runnable);
        }
    };
    @NonNull
    private TaskExecutor b = this.c;
    @NonNull
    private TaskExecutor c = new DefaultTaskExecutor();

    private ArchTaskExecutor() {
    }

    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (ArchTaskExecutor.class) {
            if (a == null) {
                a = new ArchTaskExecutor();
            }
        }
        return a;
    }

    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        if (taskExecutor == null) {
            taskExecutor = this.c;
        }
        this.b = taskExecutor;
    }

    public void executeOnDiskIO(Runnable runnable) {
        this.b.executeOnDiskIO(runnable);
    }

    public void postToMainThread(Runnable runnable) {
        this.b.postToMainThread(runnable);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return d;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return e;
    }

    public boolean isMainThread() {
        return this.b.isMainThread();
    }
}
