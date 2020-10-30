package com.scottyab.rootbeer.util;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class QLog {
    public static final int ALL = 5;
    public static final int ERRORS_ONLY = 1;
    public static final int ERRORS_WARNINGS = 2;
    public static final int ERRORS_WARNINGS_INFO = 3;
    public static final int ERRORS_WARNINGS_INFO_DEBUG = 4;
    public static int LOGGING_LEVEL = 5;
    public static final int NONE = 0;

    public static void e(Object obj, Throwable th) {
        if (isELoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.e("RootBeer", sb.toString());
            Log.e("RootBeer", a(th));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a());
            sb2.append(String.valueOf(obj));
            Log.e("QLog", sb2.toString());
            Log.e("QLog", a(th));
        }
    }

    public static void e(Object obj) {
        if (isELoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.e("RootBeer", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a());
            sb2.append(String.valueOf(obj));
            Log.e("QLog", sb2.toString());
        }
    }

    public static void w(Object obj, Throwable th) {
        if (isWLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.w("RootBeer", sb.toString());
            Log.w("RootBeer", a(th));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a());
            sb2.append(String.valueOf(obj));
            Log.w("QLog", sb2.toString());
            Log.w("QLog", a(th));
        }
    }

    public static void w(Object obj) {
        if (isWLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.w("RootBeer", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a());
            sb2.append(String.valueOf(obj));
            Log.w("QLog", sb2.toString());
        }
    }

    public static void i(Object obj) {
        if (isILoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.i("RootBeer", sb.toString());
        }
    }

    public static void d(Object obj) {
        if (isDLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.d("RootBeer", sb.toString());
        }
    }

    public static void v(Object obj) {
        if (isVLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append(String.valueOf(obj));
            Log.v("RootBeer", sb.toString());
        }
    }

    public static boolean isVLoggable() {
        return LOGGING_LEVEL > 4;
    }

    public static boolean isDLoggable() {
        return LOGGING_LEVEL > 3;
    }

    public static boolean isILoggable() {
        return LOGGING_LEVEL > 2;
    }

    public static boolean isWLoggable() {
        return LOGGING_LEVEL > 1;
    }

    public static boolean isELoggable() {
        return LOGGING_LEVEL > 0;
    }

    private static String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static String a() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        String className = stackTrace[2].getClassName();
        int lineNumber = stackTrace[2].getLineNumber();
        String substring = className.substring(className.lastIndexOf(46) + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(": ");
        sb.append(methodName);
        sb.append("() [");
        sb.append(lineNumber);
        sb.append("] - ");
        return sb.toString();
    }

    public static void handleException(Exception exc) {
        e(exc.toString());
        exc.printStackTrace();
    }

    private QLog() {
    }
}
