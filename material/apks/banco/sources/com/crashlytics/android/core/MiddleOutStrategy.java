package com.crashlytics.android.core;

class MiddleOutStrategy implements StackTraceTrimmingStrategy {
    private final int a;

    public MiddleOutStrategy(int i) {
        this.a = i;
    }

    public StackTraceElement[] a(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr.length <= this.a) {
            return stackTraceElementArr;
        }
        int i = this.a / 2;
        int i2 = this.a - i;
        StackTraceElement[] stackTraceElementArr2 = new StackTraceElement[this.a];
        System.arraycopy(stackTraceElementArr, 0, stackTraceElementArr2, 0, i2);
        System.arraycopy(stackTraceElementArr, stackTraceElementArr.length - i, stackTraceElementArr2, i2, i);
        return stackTraceElementArr2;
    }
}
