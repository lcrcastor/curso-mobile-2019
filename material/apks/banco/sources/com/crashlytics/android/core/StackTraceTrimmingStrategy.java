package com.crashlytics.android.core;

interface StackTraceTrimmingStrategy {
    StackTraceElement[] a(StackTraceElement[] stackTraceElementArr);
}
