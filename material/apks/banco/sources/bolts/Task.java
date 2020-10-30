package bolts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.a();
    public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.b();
    private static final Executor a = BoltsExecutors.b();
    /* access modifiers changed from: private */
    public final Object b = new Object();
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public TResult e;
    /* access modifiers changed from: private */
    public Exception f;
    private List<Continuation<TResult, Void>> g = new ArrayList();

    public class TaskCompletionSource {
        private TaskCompletionSource() {
        }

        public Task<TResult> getTask() {
            return Task.this;
        }

        public boolean trySetCancelled() {
            synchronized (Task.this.b) {
                if (Task.this.c) {
                    return false;
                }
                Task.this.c = true;
                Task.this.d = true;
                Task.this.b.notifyAll();
                Task.this.a();
                return true;
            }
        }

        public boolean trySetResult(TResult tresult) {
            synchronized (Task.this.b) {
                if (Task.this.c) {
                    return false;
                }
                Task.this.c = true;
                Task.this.e = tresult;
                Task.this.b.notifyAll();
                Task.this.a();
                return true;
            }
        }

        public boolean trySetError(Exception exc) {
            synchronized (Task.this.b) {
                if (Task.this.c) {
                    return false;
                }
                Task.this.c = true;
                Task.this.f = exc;
                Task.this.b.notifyAll();
                Task.this.a();
                return true;
            }
        }

        public void setCancelled() {
            if (!trySetCancelled()) {
                throw new IllegalStateException("Cannot cancel a completed task.");
            }
        }

        public void setResult(TResult tresult) {
            if (!trySetResult(tresult)) {
                throw new IllegalStateException("Cannot set the result of a completed task.");
            }
        }

        public void setError(Exception exc) {
            if (!trySetError(exc)) {
                throw new IllegalStateException("Cannot set the error on a completed task.");
            }
        }
    }

    public <TOut> Task<TOut> cast() {
        return this;
    }

    private Task() {
    }

    public static <TResult> TaskCompletionSource create() {
        Task task = new Task();
        task.getClass();
        return new TaskCompletionSource<>();
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.b) {
            z = this.c;
        }
        return z;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.b) {
            z = this.d;
        }
        return z;
    }

    public boolean isFaulted() {
        boolean z;
        synchronized (this.b) {
            z = this.f != null;
        }
        return z;
    }

    public TResult getResult() {
        TResult tresult;
        synchronized (this.b) {
            tresult = this.e;
        }
        return tresult;
    }

    public Exception getError() {
        Exception exc;
        synchronized (this.b) {
            exc = this.f;
        }
        return exc;
    }

    public void waitForCompletion() {
        synchronized (this.b) {
            if (!isCompleted()) {
                this.b.wait();
            }
        }
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        TaskCompletionSource create = create();
        create.setResult(tresult);
        return create.getTask();
    }

    public static <TResult> Task<TResult> forError(Exception exc) {
        TaskCompletionSource create = create();
        create.setError(exc);
        return create.getTask();
    }

    public static <TResult> Task<TResult> cancelled() {
        TaskCompletionSource create = create();
        create.setCancelled();
        return create.getTask();
    }

    public Task<Void> makeVoid() {
        return continueWithTask(new Continuation<TResult, Task<Void>>() {
            /* renamed from: a */
            public Task<Void> then(Task<TResult> task) {
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                return Task.forResult(null);
            }
        });
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable) {
        return call(callable, BACKGROUND_EXECUTOR);
    }

    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor) {
        final TaskCompletionSource create = create();
        executor.execute(new Runnable() {
            public void run() {
                try {
                    create.setResult(callable.call());
                } catch (Exception e) {
                    create.setError(e);
                }
            }
        });
        return create.getTask();
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable) {
        return call(callable, a);
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.size() == 0) {
            return forResult(null);
        }
        TaskCompletionSource create = create();
        ArrayList arrayList = new ArrayList();
        Object obj = new Object();
        AtomicInteger atomicInteger = new AtomicInteger(collection.size());
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        for (Task task : collection) {
            final Object obj2 = obj;
            final ArrayList arrayList2 = arrayList;
            final AtomicBoolean atomicBoolean2 = atomicBoolean;
            final AtomicInteger atomicInteger2 = atomicInteger;
            final TaskCompletionSource taskCompletionSource = create;
            AnonymousClass3 r0 = new Continuation<Object, Void>() {
                /* renamed from: a */
                public Void then(Task<Object> task) {
                    if (task.isFaulted()) {
                        synchronized (obj2) {
                            arrayList2.add(task.getError());
                        }
                    }
                    if (task.isCancelled()) {
                        atomicBoolean2.set(true);
                    }
                    if (atomicInteger2.decrementAndGet() == 0) {
                        if (arrayList2.size() != 0) {
                            if (arrayList2.size() == 1) {
                                taskCompletionSource.setError((Exception) arrayList2.get(0));
                            } else {
                                taskCompletionSource.setError(new AggregateException(arrayList2));
                            }
                        } else if (atomicBoolean2.get()) {
                            taskCompletionSource.setCancelled();
                        } else {
                            taskCompletionSource.setResult(null);
                        }
                    }
                    return null;
                }
            };
            task.continueWith(r0);
        }
        return create.getTask();
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation) {
        return continueWhile(callable, continuation, a);
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation, Executor executor) {
        Capture capture = new Capture();
        final Callable<Boolean> callable2 = callable;
        final Continuation<Void, Task<Void>> continuation2 = continuation;
        final Executor executor2 = executor;
        final Capture capture2 = capture;
        AnonymousClass4 r0 = new Continuation<Void, Task<Void>>() {
            /* renamed from: a */
            public Task<Void> then(Task<Void> task) {
                if (((Boolean) callable2.call()).booleanValue()) {
                    return Task.forResult(null).onSuccessTask(continuation2, executor2).onSuccessTask((Continuation) capture2.get(), executor2);
                }
                return Task.forResult(null);
            }
        };
        capture.set(r0);
        return makeVoid().continueWithTask((Continuation) capture.get(), executor);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> continuation, final Executor executor) {
        boolean isCompleted;
        final TaskCompletionSource create = create();
        synchronized (this.b) {
            isCompleted = isCompleted();
            if (!isCompleted) {
                this.g.add(new Continuation<TResult, Void>() {
                    /* renamed from: a */
                    public Void then(Task<TResult> task) {
                        Task.c(create, continuation, task, executor);
                        return null;
                    }
                });
            }
        }
        if (isCompleted) {
            c(create, continuation, this, executor);
        }
        return create.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(continuation, a);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> continuation, final Executor executor) {
        boolean isCompleted;
        final TaskCompletionSource create = create();
        synchronized (this.b) {
            isCompleted = isCompleted();
            if (!isCompleted) {
                this.g.add(new Continuation<TResult, Void>() {
                    /* renamed from: a */
                    public Void then(Task<TResult> task) {
                        Task.d(create, continuation, task, executor);
                        return null;
                    }
                });
            }
        }
        if (isCompleted) {
            d(create, continuation, this, executor);
        }
        return create.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(continuation, a);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(final Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            /* renamed from: a */
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWith(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation) {
        return onSuccess(continuation, a);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(final Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            /* renamed from: a */
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWithTask(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return onSuccessTask(continuation, a);
    }

    /* access modifiers changed from: private */
    public static <TContinuationResult, TResult> void c(final TaskCompletionSource taskCompletionSource, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    taskCompletionSource.setResult(continuation.then(task));
                } catch (Exception e) {
                    taskCompletionSource.setError(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static <TContinuationResult, TResult> void d(final TaskCompletionSource taskCompletionSource, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Task task = (Task) continuation.then(task);
                    if (task == null) {
                        taskCompletionSource.setResult(null);
                    } else {
                        task.continueWith(new Continuation<TContinuationResult, Void>() {
                            /* renamed from: a */
                            public Void then(Task<TContinuationResult> task) {
                                if (task.isCancelled()) {
                                    taskCompletionSource.setCancelled();
                                } else if (task.isFaulted()) {
                                    taskCompletionSource.setError(task.getError());
                                } else {
                                    taskCompletionSource.setResult(task.getResult());
                                }
                                return null;
                            }
                        });
                    }
                } catch (Exception e) {
                    taskCompletionSource.setError(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        synchronized (this.b) {
            for (Continuation then : this.g) {
                try {
                    then.then(this);
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception e3) {
                    throw new RuntimeException(e3);
                }
            }
            this.g = null;
        }
    }
}
