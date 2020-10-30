package io.fabric.sdk.android.services.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask implements Dependency<Task>, PriorityProvider, Task {
    private final List<Task> a = new ArrayList();
    private final AtomicBoolean b = new AtomicBoolean(false);
    private final AtomicReference<Throwable> c = new AtomicReference<>(null);

    public synchronized Collection<Task> getDependencies() {
        return Collections.unmodifiableCollection(this.a);
    }

    public synchronized void addDependency(Task task) {
        this.a.add(task);
    }

    public boolean areDependenciesMet() {
        for (Task isFinished : getDependencies()) {
            if (!isFinished.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void setFinished(boolean z) {
        this.b.set(z);
    }

    public boolean isFinished() {
        return this.b.get();
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void setError(Throwable th) {
        this.c.set(th);
    }

    public Throwable getError() {
        return (Throwable) this.c.get();
    }

    public int compareTo(Object obj) {
        return Priority.a(this, obj);
    }

    public static boolean isProperDelegate(Object obj) {
        boolean z = false;
        try {
            Dependency dependency = (Dependency) obj;
            Task task = (Task) obj;
            PriorityProvider priorityProvider = (PriorityProvider) obj;
            if (!(dependency == null || task == null || priorityProvider == null)) {
                z = true;
            }
            return z;
        } catch (ClassCastException unused) {
            return false;
        }
    }
}
