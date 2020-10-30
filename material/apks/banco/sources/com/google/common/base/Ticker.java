package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
@Beta
public abstract class Ticker {
    private static final Ticker a = new Ticker() {
        public long read() {
            return Platform.a();
        }
    };

    @CanIgnoreReturnValue
    public abstract long read();

    protected Ticker() {
    }

    public static Ticker systemTicker() {
        return a;
    }
}
