package com.orhanobut.logger;

import android.os.Environment;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CsvFormatStrategy implements FormatStrategy {
    private static final String a = System.getProperty("line.separator");
    @NonNull
    private final Date b;
    @NonNull
    private final SimpleDateFormat c;
    @NonNull
    private final LogStrategy d;
    @Nullable
    private final String e;

    public static final class Builder {
        Date a;
        SimpleDateFormat b;
        LogStrategy c;
        String d;

        private Builder() {
            this.d = "PRETTY_LOGGER";
        }

        @NonNull
        public Builder date(@Nullable Date date) {
            this.a = date;
            return this;
        }

        @NonNull
        public Builder dateFormat(@Nullable SimpleDateFormat simpleDateFormat) {
            this.b = simpleDateFormat;
            return this;
        }

        @NonNull
        public Builder logStrategy(@Nullable LogStrategy logStrategy) {
            this.c = logStrategy;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String str) {
            this.d = str;
            return this;
        }

        @NonNull
        public CsvFormatStrategy build() {
            if (this.a == null) {
                this.a = new Date();
            }
            if (this.b == null) {
                this.b = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
            }
            if (this.c == null) {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                StringBuilder sb = new StringBuilder();
                sb.append(absolutePath);
                sb.append(File.separatorChar);
                sb.append("logger");
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("AndroidFileLogger.");
                sb3.append(sb2);
                HandlerThread handlerThread = new HandlerThread(sb3.toString());
                handlerThread.start();
                this.c = new DiskLogStrategy(new WriteHandler(handlerThread.getLooper(), sb2, 512000));
            }
            return new CsvFormatStrategy(this);
        }
    }

    private CsvFormatStrategy(@NonNull Builder builder) {
        Utils.b(builder);
        this.b = builder.a;
        this.c = builder.b;
        this.d = builder.c;
        this.e = builder.d;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public void log(int i, @Nullable String str, @NonNull String str2) {
        Utils.b(str2);
        String a2 = a(str);
        this.b.setTime(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        sb.append(Long.toString(this.b.getTime()));
        sb.append(",");
        sb.append(this.c.format(this.b));
        sb.append(",");
        sb.append(Utils.a(i));
        sb.append(",");
        sb.append(a2);
        if (str2.contains(a)) {
            str2 = str2.replaceAll(a, " <br> ");
        }
        sb.append(",");
        sb.append(str2);
        sb.append(a);
        this.d.log(i, a2, sb.toString());
    }

    @Nullable
    private String a(@Nullable String str) {
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
