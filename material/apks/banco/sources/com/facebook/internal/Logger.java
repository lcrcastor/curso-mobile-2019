package com.facebook.internal;

import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.facebook.LoggingBehavior;
import com.facebook.Settings;
import java.util.HashMap;
import java.util.Map.Entry;

public class Logger {
    public static final String LOG_TAG_BASE = "FacebookSDK.";
    private static final HashMap<String, String> a = new HashMap<>();
    private final LoggingBehavior b;
    private final String c;
    private StringBuilder d;
    private int e = 3;

    public static synchronized void registerStringToReplace(String str, String str2) {
        synchronized (Logger.class) {
            a.put(str, str2);
        }
    }

    public static synchronized void registerAccessToken(String str) {
        synchronized (Logger.class) {
            if (!Settings.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
                registerStringToReplace(str, "ACCESS_TOKEN_REMOVED");
            }
        }
    }

    public static void log(LoggingBehavior loggingBehavior, String str, String str2) {
        log(loggingBehavior, 3, str, str2);
    }

    public static void log(LoggingBehavior loggingBehavior, String str, String str2, Object... objArr) {
        if (Settings.isLoggingBehaviorEnabled(loggingBehavior)) {
            log(loggingBehavior, 3, str, String.format(str2, objArr));
        }
    }

    public static void log(LoggingBehavior loggingBehavior, int i, String str, String str2) {
        if (Settings.isLoggingBehaviorEnabled(loggingBehavior)) {
            String a2 = a(str2);
            if (!str.startsWith(LOG_TAG_BASE)) {
                StringBuilder sb = new StringBuilder();
                sb.append(LOG_TAG_BASE);
                sb.append(str);
                str = sb.toString();
            }
            Log.println(i, str, a2);
            if (loggingBehavior == LoggingBehavior.DEVELOPER_ERRORS) {
                new Exception().printStackTrace();
            }
        }
    }

    private static synchronized String a(String str) {
        synchronized (Logger.class) {
            for (Entry entry : a.entrySet()) {
                str = str.replace((CharSequence) entry.getKey(), (CharSequence) entry.getValue());
            }
        }
        return str;
    }

    public Logger(LoggingBehavior loggingBehavior, String str) {
        Validate.notNullOrEmpty(str, "tag");
        this.b = loggingBehavior;
        StringBuilder sb = new StringBuilder();
        sb.append(LOG_TAG_BASE);
        sb.append(str);
        this.c = sb.toString();
        this.d = new StringBuilder();
    }

    public int getPriority() {
        return this.e;
    }

    public void setPriority(int i) {
        Validate.oneOf(Integer.valueOf(i), TarjetasConstants.VALUE, Integer.valueOf(7), Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(5));
        this.e = i;
    }

    public String getContents() {
        return a(this.d.toString());
    }

    public void log() {
        logString(this.d.toString());
        this.d = new StringBuilder();
    }

    public void logString(String str) {
        log(this.b, this.e, this.c, str);
    }

    public void append(StringBuilder sb) {
        if (a()) {
            this.d.append(sb);
        }
    }

    public void append(String str) {
        if (a()) {
            this.d.append(str);
        }
    }

    public void append(String str, Object... objArr) {
        if (a()) {
            this.d.append(String.format(str, objArr));
        }
    }

    public void appendKeyValue(String str, Object obj) {
        append("  %s:\t%s\n", str, obj);
    }

    private boolean a() {
        return Settings.isLoggingBehaviorEnabled(this.b);
    }
}
