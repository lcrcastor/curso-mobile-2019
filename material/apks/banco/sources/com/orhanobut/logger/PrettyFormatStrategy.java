package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.message.TokenParser;

public class PrettyFormatStrategy implements FormatStrategy {
    private final int a;
    private final int b;
    private final boolean c;
    @NonNull
    private final LogStrategy d;
    @Nullable
    private final String e;

    public static class Builder {
        int a;
        int b;
        boolean c;
        @Nullable
        LogStrategy d;
        @Nullable
        String e;

        private Builder() {
            this.a = 2;
            this.b = 0;
            this.c = true;
            this.e = "PRETTY_LOGGER";
        }

        @NonNull
        public Builder methodCount(int i) {
            this.a = i;
            return this;
        }

        @NonNull
        public Builder methodOffset(int i) {
            this.b = i;
            return this;
        }

        @NonNull
        public Builder showThreadInfo(boolean z) {
            this.c = z;
            return this;
        }

        @NonNull
        public Builder logStrategy(@Nullable LogStrategy logStrategy) {
            this.d = logStrategy;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String str) {
            this.e = str;
            return this;
        }

        @NonNull
        public PrettyFormatStrategy build() {
            if (this.d == null) {
                this.d = new LogcatLogStrategy();
            }
            return new PrettyFormatStrategy(this);
        }
    }

    private PrettyFormatStrategy(@NonNull Builder builder) {
        Utils.b(builder);
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public void log(int i, @Nullable String str, @NonNull String str2) {
        Utils.b(str2);
        String b2 = b(str);
        a(i, b2);
        a(i, b2, this.a);
        byte[] bytes = str2.getBytes();
        int length = bytes.length;
        if (length <= 4000) {
            if (this.a > 0) {
                c(i, b2);
            }
            a(i, b2, str2);
            b(i, b2);
            return;
        }
        if (this.a > 0) {
            c(i, b2);
        }
        for (int i2 = 0; i2 < length; i2 += 4000) {
            a(i, b2, new String(bytes, i2, Math.min(length - i2, 4000)));
        }
        b(i, b2);
    }

    private void a(int i, @Nullable String str) {
        b(i, str, "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
    }

    private void a(int i, @Nullable String str, int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (this.c) {
            StringBuilder sb = new StringBuilder();
            sb.append("│ Thread: ");
            sb.append(Thread.currentThread().getName());
            b(i, str, sb.toString());
            c(i, str);
        }
        String str2 = "";
        int a2 = a(stackTrace) + this.b;
        if (i2 + a2 > stackTrace.length) {
            i2 = (stackTrace.length - a2) - 1;
        }
        while (i2 > 0) {
            int i3 = i2 + a2;
            if (i3 < stackTrace.length) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(9474);
                sb2.append(TokenParser.SP);
                sb2.append(str2);
                sb2.append(a(stackTrace[i3].getClassName()));
                sb2.append(".");
                sb2.append(stackTrace[i3].getMethodName());
                sb2.append(UtilsCuentas.SEPARAOR2);
                sb2.append(" (");
                sb2.append(stackTrace[i3].getFileName());
                sb2.append(":");
                sb2.append(stackTrace[i3].getLineNumber());
                sb2.append(")");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append("   ");
                str2 = sb3.toString();
                b(i, str, sb2.toString());
            }
            i2--;
        }
    }

    private void b(int i, @Nullable String str) {
        b(i, str, "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
    }

    private void c(int i, @Nullable String str) {
        b(i, str, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
    }

    private void a(int i, @Nullable String str, @NonNull String str2) {
        String[] split;
        Utils.b(str2);
        for (String str3 : str2.split(System.getProperty("line.separator"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("│ ");
            sb.append(str3);
            b(i, str, sb.toString());
        }
    }

    private void b(int i, @Nullable String str, @NonNull String str2) {
        Utils.b(str2);
        this.d.log(i, str, str2);
    }

    private String a(@NonNull String str) {
        Utils.b(str);
        return str.substring(str.lastIndexOf(".") + 1);
    }

    private int a(@NonNull StackTraceElement[] stackTraceElementArr) {
        Utils.b(stackTraceElementArr);
        for (int i = 5; i < stackTraceElementArr.length; i++) {
            String className = stackTraceElementArr[i].getClassName();
            if (!className.equals(LoggerPrinter.class.getName()) && !className.equals(Logger.class.getName())) {
                return i - 1;
            }
        }
        return -1;
    }

    @Nullable
    private String b(@Nullable String str) {
        if (Utils.a((CharSequence) str) || Utils.a(this.e, str)) {
            return this.e;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.e);
        sb.append("-");
        sb.append(str);
        return sb.toString();
    }
}
