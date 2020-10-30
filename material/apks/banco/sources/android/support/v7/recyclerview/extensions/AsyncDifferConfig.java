package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.util.DiffUtil.ItemCallback;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncDifferConfig<T> {
    @NonNull
    private final Executor a;
    @NonNull
    private final Executor b;
    @NonNull
    private final ItemCallback<T> c;

    public static final class Builder<T> {
        private static final Object d = new Object();
        private static Executor e;
        private static final Executor f = new MainThreadExecutor();
        private Executor a;
        private Executor b;
        private final ItemCallback<T> c;

        static class MainThreadExecutor implements Executor {
            final Handler a;

            private MainThreadExecutor() {
                this.a = new Handler(Looper.getMainLooper());
            }

            public void execute(@NonNull Runnable runnable) {
                this.a.post(runnable);
            }
        }

        public Builder(@NonNull ItemCallback<T> itemCallback) {
            this.c = itemCallback;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        @NonNull
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.a = executor;
            return this;
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.b = executor;
            return this;
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.a == null) {
                this.a = f;
            }
            if (this.b == null) {
                synchronized (d) {
                    if (e == null) {
                        e = Executors.newFixedThreadPool(2);
                    }
                }
                this.b = e;
            }
            return new AsyncDifferConfig<>(this.a, this.b, this.c);
        }
    }

    private AsyncDifferConfig(@NonNull Executor executor, @NonNull Executor executor2, @NonNull ItemCallback<T> itemCallback) {
        this.a = executor;
        this.b = executor2;
        this.c = itemCallback;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @NonNull
    public Executor getMainThreadExecutor() {
        return this.a;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.b;
    }

    @NonNull
    public ItemCallback<T> getDiffCallback() {
        return this.c;
    }
}
