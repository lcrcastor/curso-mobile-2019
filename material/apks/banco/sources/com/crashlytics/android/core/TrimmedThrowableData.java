package com.crashlytics.android.core;

class TrimmedThrowableData {
    public final String a;
    public final String b;
    public final StackTraceElement[] c;
    public final TrimmedThrowableData d;

    public TrimmedThrowableData(Throwable th, StackTraceTrimmingStrategy stackTraceTrimmingStrategy) {
        this.a = th.getLocalizedMessage();
        this.b = th.getClass().getName();
        this.c = stackTraceTrimmingStrategy.a(th.getStackTrace());
        Throwable cause = th.getCause();
        this.d = cause != null ? new TrimmedThrowableData(cause, stackTraceTrimmingStrategy) : null;
    }
}
