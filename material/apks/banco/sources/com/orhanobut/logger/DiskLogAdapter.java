package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DiskLogAdapter implements LogAdapter {
    @NonNull
    private final FormatStrategy a;

    public boolean isLoggable(int i, @Nullable String str) {
        return true;
    }

    public DiskLogAdapter() {
        this.a = CsvFormatStrategy.newBuilder().build();
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.a = (FormatStrategy) Utils.b(formatStrategy);
    }

    public void log(int i, @Nullable String str, @NonNull String str2) {
        this.a.log(i, str, str2);
    }
}
