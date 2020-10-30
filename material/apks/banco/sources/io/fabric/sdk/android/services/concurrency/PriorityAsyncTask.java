package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask.Status;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class PriorityAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements DelegateProvider, Dependency<Task>, PriorityProvider, Task {
    private final PriorityTask a = new PriorityTask();

    static class ProxyExecutor<Result> implements Executor {
        private final Executor a;
        /* access modifiers changed from: private */
        public final PriorityAsyncTask b;

        public ProxyExecutor(Executor executor, PriorityAsyncTask priorityAsyncTask) {
            this.a = executor;
            this.b = priorityAsyncTask;
        }

        public void execute(Runnable runnable) {
            this.a.execute(new PriorityFutureTask<Result>(runnable, null) {
                public <T extends Dependency<Task> & PriorityProvider & Task> T getDelegate() {
                    return ProxyExecutor.this.b;
                }
            });
        }
    }

    public final void executeOnExecutor(ExecutorService executorService, Params... paramsArr) {
        super.executeOnExecutor(new ProxyExecutor(executorService, this), paramsArr);
    }

    public int compareTo(Object obj) {
        return Priority.a(this, obj);
    }

    public void addDependency(Task task) {
        if (getStatus() != Status.PENDING) {
            throw new IllegalStateException("Must not add Dependency after task is running");
        }
        ((Dependency) ((PriorityProvider) getDelegate())).addDependency(task);
    }

    public Collection<Task> getDependencies() {
        return ((Dependency) ((PriorityProvider) getDelegate())).getDependencies();
    }

    public boolean areDependenciesMet() {
        return ((Dependency) ((PriorityProvider) getDelegate())).areDependenciesMet();
    }

    public Priority getPriority() {
        return ((PriorityProvider) getDelegate()).getPriority();
    }

    public void setFinished(boolean z) {
        ((Task) ((PriorityProvider) getDelegate())).setFinished(z);
    }

    public boolean isFinished() {
        return ((Task) ((PriorityProvider) getDelegate())).isFinished();
    }

    public void setError(Throwable th) {
        ((Task) ((PriorityProvider) getDelegate())).setError(th);
    }

    public Throwable getError() {
        return ((Task) ((PriorityProvider) getDelegate())).getError();
    }

    public <T extends Dependency<Task> & PriorityProvider & Task> T getDelegate() {
        return this.a;
    }
}
