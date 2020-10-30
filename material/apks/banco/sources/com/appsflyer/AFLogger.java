package com.appsflyer;

import android.support.annotation.NonNull;
import android.support.media.ExifInterface;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AFLogger {
    private static long a = System.currentTimeMillis();

    public enum LogLevel {
        NONE(0),
        ERROR(1),
        WARNING(2),
        INFO(3),
        DEBUG(4),
        VERBOSE(5);
        
        private int a;

        private LogLevel(int i) {
            this.a = i;
        }

        public final int getLevel() {
            return this.a;
        }
    }

    public static void afInfoLog(String str, boolean z) {
        if (LogLevel.INFO.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            Log.i(AppsFlyerLib.LOG_TAG, a(str, false));
        }
        if (z) {
            y.a().b("I", a(str, true));
        }
    }

    public static void resetDeltaTime() {
        a = System.currentTimeMillis();
    }

    @NonNull
    private static String a(String str, boolean z) {
        if (!z && LogLevel.VERBOSE.getLevel() != AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            return str;
        }
        StringBuilder sb = new StringBuilder("(");
        sb.append(a(System.currentTimeMillis() - a));
        sb.append(") ");
        sb.append(str);
        return sb.toString();
    }

    private static void a(String str, Throwable th, boolean z) {
        if ((LogLevel.ERROR.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) && z) {
            Log.e(AppsFlyerLib.LOG_TAG, a(str, false), th);
        }
        y.a().a(th);
    }

    public static void afRDLog(String str) {
        if (LogLevel.VERBOSE.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            Log.v(AppsFlyerLib.LOG_TAG, a(str, false));
        }
        y.a().b("V", a(str, true));
    }

    public static void afInfoLog(String str) {
        afInfoLog(str, true);
    }

    public static void afErrorLog(String str, Throwable th) {
        a(str, th, false);
    }

    public static void afErrorLog(String str, Throwable th, boolean z) {
        a(str, th, z);
    }

    private static String a(long j) {
        long hours = TimeUnit.MILLISECONDS.toHours(j);
        long millis = j - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long millis2 = millis - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis2);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d:%03d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(TimeUnit.MILLISECONDS.toMillis(millis2 - TimeUnit.SECONDS.toMillis(seconds)))});
    }

    static void a(String str) {
        if (!AppsFlyerProperties.getInstance().isLogsDisabledCompletely()) {
            Log.d(AppsFlyerLib.LOG_TAG, a(str, false));
        }
        y.a().b("F", str);
    }

    public static void afDebugLog(String str) {
        if (LogLevel.DEBUG.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            Log.d(AppsFlyerLib.LOG_TAG, a(str, false));
        }
        y.a().b("D", a(str, true));
    }

    public static void afWarnLog(String str) {
        if (LogLevel.WARNING.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            Log.w(AppsFlyerLib.LOG_TAG, a(str, false));
        }
        y.a().b(ExifInterface.LONGITUDE_WEST, a(str, true));
    }
}
