package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

public final class TaskExecutors {
    public static final Executor MAIN_THREAD = new zza();
    static final Executor a = new Executor() {
        public void execute(@NonNull Runnable runnable) {
            runnable.run();
        }
    };

    static final class zza implements Executor {
        private final Handler a = new Handler(Looper.getMainLooper());

        public void execute(@NonNull Runnable runnable) {
            this.a.post(runnable);
        }
    }

    private TaskExecutors() {
    }
}
