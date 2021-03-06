package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
public final class UncaughtExceptionHandlers {

    @VisibleForTesting
    static final class Exiter implements UncaughtExceptionHandler {
        private static final Logger a = Logger.getLogger(Exiter.class.getName());
        private final Runtime b;

        Exiter(Runtime runtime) {
            this.b = runtime;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            try {
                a.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", new Object[]{thread}), th);
            } catch (Throwable th2) {
                this.b.exit(1);
                throw th2;
            }
            this.b.exit(1);
        }
    }

    private UncaughtExceptionHandlers() {
    }

    public static UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }
}
