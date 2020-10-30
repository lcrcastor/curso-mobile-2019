package com.crashlytics.android.core;

class MiddleOutFallbackStrategy implements StackTraceTrimmingStrategy {
    private final int a;
    private final StackTraceTrimmingStrategy[] b;
    private final MiddleOutStrategy c;

    public MiddleOutFallbackStrategy(int i, StackTraceTrimmingStrategy... stackTraceTrimmingStrategyArr) {
        this.a = i;
        this.b = stackTraceTrimmingStrategyArr;
        this.c = new MiddleOutStrategy(i);
    }

    public StackTraceElement[] a(StackTraceElement[] stackTraceElementArr) {
        StackTraceTrimmingStrategy[] stackTraceTrimmingStrategyArr;
        if (stackTraceElementArr.length <= this.a) {
            return stackTraceElementArr;
        }
        StackTraceElement[] stackTraceElementArr2 = stackTraceElementArr;
        for (StackTraceTrimmingStrategy stackTraceTrimmingStrategy : this.b) {
            if (stackTraceElementArr2.length <= this.a) {
                break;
            }
            stackTraceElementArr2 = stackTraceTrimmingStrategy.a(stackTraceElementArr);
        }
        if (stackTraceElementArr2.length > this.a) {
            stackTraceElementArr2 = this.c.a(stackTraceElementArr2);
        }
        return stackTraceElementArr2;
    }
}
