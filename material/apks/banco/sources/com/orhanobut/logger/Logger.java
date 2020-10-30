package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Logger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    @NonNull
    private static Printer a = new LoggerPrinter();

    private Logger() {
    }

    public static void printer(@NonNull Printer printer) {
        a = (Printer) Utils.b(printer);
    }

    public static void addLogAdapter(@NonNull LogAdapter logAdapter) {
        a.addAdapter((LogAdapter) Utils.b(logAdapter));
    }

    public static void clearLogAdapters() {
        a.clearLogAdapters();
    }

    public static Printer t(@Nullable String str) {
        return a.t(str);
    }

    public static void log(int i, @Nullable String str, @Nullable String str2, @Nullable Throwable th) {
        a.log(i, str, str2, th);
    }

    public static void d(@NonNull String str, @Nullable Object... objArr) {
        a.d(str, objArr);
    }

    public static void d(@Nullable Object obj) {
        a.d(obj);
    }

    public static void e(@NonNull String str, @Nullable Object... objArr) {
        a.e(null, str, objArr);
    }

    public static void e(@Nullable Throwable th, @NonNull String str, @Nullable Object... objArr) {
        a.e(th, str, objArr);
    }

    public static void i(@NonNull String str, @Nullable Object... objArr) {
        a.i(str, objArr);
    }

    public static void v(@NonNull String str, @Nullable Object... objArr) {
        a.v(str, objArr);
    }

    public static void w(@NonNull String str, @Nullable Object... objArr) {
        a.w(str, objArr);
    }

    public static void wtf(@NonNull String str, @Nullable Object... objArr) {
        a.wtf(str, objArr);
    }

    public static void json(@Nullable String str) {
        a.json(str);
    }

    public static void xml(@Nullable String str) {
        a.xml(str);
    }
}
