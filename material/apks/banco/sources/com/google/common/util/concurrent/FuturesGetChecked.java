package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

@GwtIncompatible
final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> a = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
        /* renamed from: a */
        public Boolean apply(Constructor<?> constructor) {
            return Boolean.valueOf(Arrays.asList(constructor.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    @VisibleForTesting
    interface GetCheckedTypeValidator {
        void a(Class<? extends Exception> cls);
    }

    @VisibleForTesting
    static class GetCheckedTypeValidatorHolder {
        static final String a;
        static final GetCheckedTypeValidator b = a();

        enum WeakSetValidator implements GetCheckedTypeValidator {
            INSTANCE;
            
            private static final Set<WeakReference<Class<? extends Exception>>> b = null;

            static {
                b = new CopyOnWriteArraySet();
            }

            public void a(Class<? extends Exception> cls) {
                for (WeakReference weakReference : b) {
                    if (cls.equals(weakReference.get())) {
                        return;
                    }
                }
                FuturesGetChecked.b(cls);
                if (b.size() > 1000) {
                    b.clear();
                }
                b.add(new WeakReference(cls));
            }
        }

        GetCheckedTypeValidatorHolder() {
        }

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(GetCheckedTypeValidatorHolder.class.getName());
            sb.append("$ClassValueValidator");
            a = sb.toString();
        }

        static GetCheckedTypeValidator a() {
            try {
                return (GetCheckedTypeValidator) Class.forName(a).getEnumConstants()[0];
            } catch (Throwable unused) {
                return FuturesGetChecked.a();
            }
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V a(Future<V> future, Class<X> cls) {
        return a(b(), future, cls);
    }

    @CanIgnoreReturnValue
    @VisibleForTesting
    static <V, X extends Exception> V a(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> cls) {
        getCheckedTypeValidator.a(cls);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw a(cls, (Throwable) e);
        } catch (ExecutionException e2) {
            a(e2.getCause(), cls);
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V a(Future<V> future, Class<X> cls, long j, TimeUnit timeUnit) {
        b().a(cls);
        try {
            return future.get(j, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw a(cls, (Throwable) e);
        } catch (TimeoutException e2) {
            throw a(cls, (Throwable) e2);
        } catch (ExecutionException e3) {
            a(e3.getCause(), cls);
            throw new AssertionError();
        }
    }

    private static GetCheckedTypeValidator b() {
        return GetCheckedTypeValidatorHolder.b;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator a() {
        return WeakSetValidator.INSTANCE;
    }

    private static <X extends Exception> void a(Throwable th, Class<X> cls) {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        } else if (th instanceof RuntimeException) {
            throw new UncheckedExecutionException(th);
        } else {
            throw a(cls, th);
        }
    }

    private static boolean c(Class<? extends Exception> cls) {
        try {
            a(cls, (Throwable) new Exception());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static <X extends Exception> X a(Class<X> cls, Throwable th) {
        for (Constructor a2 : a(Arrays.asList(cls.getConstructors()))) {
            X x = (Exception) a(a2, th);
            if (x != null) {
                if (x.getCause() == null) {
                    x.initCause(th);
                }
                return x;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No appropriate constructor for exception of type ");
        sb.append(cls);
        sb.append(" in response to chained exception");
        throw new IllegalArgumentException(sb.toString(), th);
    }

    private static <X extends Exception> List<Constructor<X>> a(List<Constructor<X>> list) {
        return a.sortedCopy(list);
    }

    @Nullable
    private static <X> X a(Constructor<X> constructor, Throwable th) {
        Class[] parameterTypes = constructor.getParameterTypes();
        Object[] objArr = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class cls = parameterTypes[i];
            if (cls.equals(String.class)) {
                objArr[i] = th.toString();
            } else if (!cls.equals(Throwable.class)) {
                return null;
            } else {
                objArr[i] = th;
            }
        }
        try {
            return constructor.newInstance(objArr);
        } catch (IllegalArgumentException unused) {
            return null;
        } catch (InstantiationException unused2) {
            return null;
        } catch (IllegalAccessException unused3) {
            return null;
        } catch (InvocationTargetException unused4) {
            return null;
        }
    }

    @VisibleForTesting
    static boolean a(Class<? extends Exception> cls) {
        return !RuntimeException.class.isAssignableFrom(cls);
    }

    @VisibleForTesting
    static void b(Class<? extends Exception> cls) {
        Preconditions.checkArgument(a(cls), "Futures.getChecked exception type (%s) must not be a RuntimeException", (Object) cls);
        Preconditions.checkArgument(c(cls), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", (Object) cls);
    }

    private FuturesGetChecked() {
    }
}
