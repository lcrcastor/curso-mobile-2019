package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class Closer implements Closeable {
    private static final Suppressor b = (SuppressingSuppressor.a() ? SuppressingSuppressor.a : LoggingSuppressor.a);
    @VisibleForTesting
    final Suppressor a;
    private final Deque<Closeable> c = new ArrayDeque(4);
    private Throwable d;

    @VisibleForTesting
    static final class LoggingSuppressor implements Suppressor {
        static final LoggingSuppressor a = new LoggingSuppressor();

        LoggingSuppressor() {
        }

        public void a(Closeable closeable, Throwable th, Throwable th2) {
            Logger logger = Closeables.a;
            Level level = Level.WARNING;
            StringBuilder sb = new StringBuilder();
            sb.append("Suppressing exception thrown when closing ");
            sb.append(closeable);
            logger.log(level, sb.toString(), th2);
        }
    }

    @VisibleForTesting
    static final class SuppressingSuppressor implements Suppressor {
        static final SuppressingSuppressor a = new SuppressingSuppressor();
        static final Method b = b();

        SuppressingSuppressor() {
        }

        static boolean a() {
            return b != null;
        }

        private static Method b() {
            try {
                return Throwable.class.getMethod("addSuppressed", new Class[]{Throwable.class});
            } catch (Throwable unused) {
                return null;
            }
        }

        public void a(Closeable closeable, Throwable th, Throwable th2) {
            if (th != th2) {
                try {
                    b.invoke(th, new Object[]{th2});
                } catch (Throwable unused) {
                    LoggingSuppressor.a.a(closeable, th, th2);
                }
            }
        }
    }

    @VisibleForTesting
    interface Suppressor {
        void a(Closeable closeable, Throwable th, Throwable th2);
    }

    public static Closer create() {
        return new Closer(b);
    }

    @VisibleForTesting
    Closer(Suppressor suppressor) {
        this.a = (Suppressor) Preconditions.checkNotNull(suppressor);
    }

    @CanIgnoreReturnValue
    public <C extends Closeable> C register(@Nullable C c2) {
        if (c2 != null) {
            this.c.addFirst(c2);
        }
        return c2;
    }

    public RuntimeException rethrow(Throwable th) {
        Preconditions.checkNotNull(th);
        this.d = th;
        Throwables.propagateIfPossible(th, IOException.class);
        throw new RuntimeException(th);
    }

    public <X extends Exception> RuntimeException rethrow(Throwable th, Class<X> cls) {
        Preconditions.checkNotNull(th);
        this.d = th;
        Throwables.propagateIfPossible(th, IOException.class);
        Throwables.propagateIfPossible(th, cls);
        throw new RuntimeException(th);
    }

    public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable th, Class<X1> cls, Class<X2> cls2) {
        Preconditions.checkNotNull(th);
        this.d = th;
        Throwables.propagateIfPossible(th, IOException.class);
        Throwables.propagateIfPossible(th, cls, cls2);
        throw new RuntimeException(th);
    }

    public void close() {
        Throwable th = this.d;
        while (!this.c.isEmpty()) {
            Closeable closeable = (Closeable) this.c.removeFirst();
            try {
                closeable.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    this.a.a(closeable, th, th2);
                }
            }
        }
        if (this.d == null && th != null) {
            Throwables.propagateIfPossible(th, IOException.class);
            throw new AssertionError(th);
        }
    }
}
