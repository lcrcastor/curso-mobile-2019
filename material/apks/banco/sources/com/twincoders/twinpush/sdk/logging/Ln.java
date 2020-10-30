package com.twincoders.twinpush.sdk.logging;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Ln {
    protected static BaseConfig config = new BaseConfig();
    protected static Print print = new Print();

    public static class BaseConfig implements Config {
        protected int minimumLogLevel = 2;
        protected String packageName = "";
        protected String scope = "";

        protected BaseConfig() {
        }

        public BaseConfig(Context context) {
            int i = 2;
            try {
                this.packageName = context.getPackageName();
                if ((context.getPackageManager().getApplicationInfo(this.packageName, 0).flags & 2) == 0) {
                    i = 4;
                }
                this.minimumLogLevel = i;
                this.scope = this.packageName.toUpperCase(Locale.getDefault());
                Ln.d("Configuring Logging, minimum log level is %s", Ln.logLevelToString(this.minimumLogLevel));
            } catch (Exception e) {
                Log.e(this.packageName, "Error configuring logger", e);
            }
        }

        public int getLoggingLevel() {
            return this.minimumLogLevel;
        }

        public void setLoggingLevel(int i) {
            this.minimumLogLevel = i;
        }
    }

    public interface Config {
        int getLoggingLevel();

        void setLoggingLevel(int i);
    }

    public static class Print {
        public int println(int i, String str) {
            return Log.println(i, getScope(), processMessage(str));
        }

        /* access modifiers changed from: protected */
        public String processMessage(String str) {
            if (Ln.config.minimumLogLevel > 3) {
                return str;
            }
            return String.format("%s %s %s", new Object[]{new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis())), Thread.currentThread().getName(), str});
        }

        protected static String getScope() {
            if (Ln.config.minimumLogLevel > 3) {
                return Ln.config.scope;
            }
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[5];
            StringBuilder sb = new StringBuilder();
            sb.append(Ln.config.scope);
            sb.append("/");
            sb.append(stackTraceElement.getFileName());
            sb.append(":");
            sb.append(stackTraceElement.getLineNumber());
            return sb.toString();
        }
    }

    public static String logLevelToString(int i) {
        switch (i) {
            case 2:
                return "VERBOSE";
            case 3:
                return "DEBUG";
            case 4:
                return "INFO";
            case 5:
                return "WARN";
            case 6:
                return ResultValues.ERROR;
            case 7:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    private Ln() {
    }

    public static int v(Throwable th) {
        if (config.minimumLogLevel <= 2) {
            return print.println(2, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int v(Object obj, Object... objArr) {
        if (config.minimumLogLevel > 2) {
            return 0;
        }
        String strings = Strings.toString(obj);
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        return print.println(2, strings);
    }

    public static int v(Throwable th, Object obj, Object... objArr) {
        if (config.minimumLogLevel > 2) {
            return 0;
        }
        String strings = Strings.toString(obj);
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        sb.append(strings);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        return print.println(2, sb.toString());
    }

    public static int d(Throwable th) {
        if (config.minimumLogLevel <= 3) {
            return print.println(3, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int d(Object obj, Object... objArr) {
        if (config.minimumLogLevel > 3) {
            return 0;
        }
        String strings = Strings.toString(obj);
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        return print.println(3, strings);
    }

    public static int d(Throwable th, Object obj, Object... objArr) {
        if (config.minimumLogLevel > 3) {
            return 0;
        }
        String strings = Strings.toString(obj);
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        sb.append(strings);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        return print.println(3, sb.toString());
    }

    public static int i(Throwable th) {
        if (config.minimumLogLevel <= 4) {
            return print.println(4, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int i(Object obj, Object... objArr) {
        if (config.minimumLogLevel > 4) {
            return 0;
        }
        String strings = Strings.toString(obj);
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        return print.println(4, strings);
    }

    public static int i(Throwable th, Object obj, Object... objArr) {
        if (config.minimumLogLevel > 4) {
            return 0;
        }
        String strings = Strings.toString(obj);
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        sb.append(strings);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        return print.println(4, sb.toString());
    }

    public static int w(Throwable th) {
        if (config.minimumLogLevel <= 5) {
            return print.println(5, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int w(Object obj, Object... objArr) {
        if (config.minimumLogLevel > 5) {
            return 0;
        }
        String strings = Strings.toString(obj);
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        return print.println(5, strings);
    }

    public static int w(Throwable th, Object obj, Object... objArr) {
        if (config.minimumLogLevel > 5) {
            return 0;
        }
        String strings = Strings.toString(obj);
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        sb.append(strings);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        return print.println(5, sb.toString());
    }

    public static int e(Throwable th) {
        if (config.minimumLogLevel <= 6) {
            return print.println(6, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int e(Object obj, Object... objArr) {
        if (config.minimumLogLevel > 6) {
            return 0;
        }
        String strings = Strings.toString(obj);
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        return print.println(6, strings);
    }

    public static int e(Throwable th, Object obj, Object... objArr) {
        if (config.minimumLogLevel > 6) {
            return 0;
        }
        String strings = Strings.toString(obj);
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            strings = String.format(strings, objArr);
        }
        sb.append(strings);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        return print.println(6, sb.toString());
    }

    public static boolean isDebugEnabled() {
        return config.minimumLogLevel <= 3;
    }

    public static boolean isVerboseEnabled() {
        return config.minimumLogLevel <= 2;
    }

    public static Config getConfig() {
        return config;
    }
}
