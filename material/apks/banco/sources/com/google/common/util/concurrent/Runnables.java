package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Runnables {
    private static final Runnable a = new Runnable() {
        public void run() {
        }
    };

    public static Runnable doNothing() {
        return a;
    }

    private Runnables() {
    }
}
