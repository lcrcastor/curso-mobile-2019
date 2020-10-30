package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Beta
public final class SimpleTimeLimiter implements TimeLimiter {
    private final ExecutorService a;

    public SimpleTimeLimiter(ExecutorService executorService) {
        this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
    }

    public SimpleTimeLimiter() {
        this(Executors.newCachedThreadPool());
    }

    public <T> T newProxy(T t, Class<T> cls, long j, TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(j > 0, "bad timeout: %s", j);
        Preconditions.checkArgument(cls.isInterface(), "interfaceType must be an interface type");
        final Set a2 = a(cls);
        final T t2 = t;
        final long j2 = j;
        final TimeUnit timeUnit2 = timeUnit;
        AnonymousClass1 r2 = new InvocationHandler() {
            public Object invoke(Object obj, final Method method, final Object[] objArr) {
                return SimpleTimeLimiter.this.callWithTimeout(new Callable<Object>() {
                    public Object call() {
                        try {
                            return method.invoke(t2, objArr);
                        } catch (InvocationTargetException e) {
                            throw SimpleTimeLimiter.b(e, false);
                        }
                    }
                }, j2, timeUnit2, a2.contains(method));
            }
        };
        return a(cls, (InvocationHandler) r2);
    }

    @CanIgnoreReturnValue
    public <T> T callWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit, boolean z) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(j > 0, "timeout must be positive: %s", j);
        Future submit = this.a.submit(callable);
        if (!z) {
            return Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
        }
        try {
            return submit.get(j, timeUnit);
        } catch (InterruptedException e) {
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            throw b(e2, true);
        } catch (TimeoutException e3) {
            submit.cancel(true);
            throw new UncheckedTimeoutException((Throwable) e3);
        }
    }

    /* access modifiers changed from: private */
    public static Exception b(Exception exc, boolean z) {
        Throwable cause = exc.getCause();
        if (cause == null) {
            throw exc;
        }
        if (z) {
            cause.setStackTrace((StackTraceElement[]) ObjectArrays.concat(cause.getStackTrace(), exc.getStackTrace(), StackTraceElement.class));
        }
        if (cause instanceof Exception) {
            throw ((Exception) cause);
        } else if (cause instanceof Error) {
            throw ((Error) cause);
        } else {
            throw exc;
        }
    }

    private static Set<Method> a(Class<?> cls) {
        Method[] methods;
        HashSet newHashSet = Sets.newHashSet();
        for (Method method : cls.getMethods()) {
            if (a(method)) {
                newHashSet.add(method);
            }
        }
        return newHashSet;
    }

    private static boolean a(Method method) {
        for (Class<InterruptedException> cls : method.getExceptionTypes()) {
            if (cls == InterruptedException.class) {
                return true;
            }
        }
        return false;
    }

    private static <T> T a(Class<T> cls, InvocationHandler invocationHandler) {
        return cls.cast(Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler));
    }
}
