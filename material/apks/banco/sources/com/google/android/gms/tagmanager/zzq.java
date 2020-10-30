package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.zzac;
import java.util.Random;

public class zzq {
    private final Context a;
    private final Random b;
    private final String c;

    public zzq(Context context, String str) {
        this(context, str, new Random());
    }

    @VisibleForTesting
    zzq(Context context, String str, Random random) {
        this.a = (Context) zzac.zzy(context);
        this.c = (String) zzac.zzy(str);
        this.b = random;
    }

    private long a(long j, long j2) {
        SharedPreferences a2 = a();
        long max = Math.max(0, a2.getLong("FORBIDDEN_COUNT", 0));
        return (long) (this.b.nextFloat() * ((float) (j + ((long) ((((float) max) / ((float) ((max + Math.max(0, a2.getLong("SUCCESSFUL_COUNT", 0))) + 1))) * ((float) (j2 - j)))))));
    }

    private SharedPreferences a() {
        Context context = this.a;
        String valueOf = String.valueOf("_gtmContainerRefreshPolicy_");
        String valueOf2 = String.valueOf(this.c);
        return context.getSharedPreferences(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), 0);
    }

    public long zzcei() {
        return a(7200000, 259200000) + 43200000;
    }

    public long zzcej() {
        return a(600000, 86400000) + 3600000;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void zzcek() {
        SharedPreferences a2 = a();
        long j = a2.getLong("FORBIDDEN_COUNT", 0);
        long j2 = a2.getLong("SUCCESSFUL_COUNT", 0);
        Editor edit = a2.edit();
        long min = j == 0 ? 3 : Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - min));
        edit.putLong("FORBIDDEN_COUNT", min);
        edit.putLong("SUCCESSFUL_COUNT", max);
        zzdd.a(edit);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void zzcel() {
        SharedPreferences a2 = a();
        long j = a2.getLong("SUCCESSFUL_COUNT", 0);
        long j2 = a2.getLong("FORBIDDEN_COUNT", 0);
        long min = Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - min));
        Editor edit = a2.edit();
        edit.putLong("SUCCESSFUL_COUNT", min);
        edit.putLong("FORBIDDEN_COUNT", max);
        zzdd.a(edit);
    }
}
