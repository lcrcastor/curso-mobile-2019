package com.google.android.gms.internal;

import android.content.Context;

public class zzsi {
    private static zzsi b = new zzsi();
    private zzsh a = null;

    public static zzsh zzcr(Context context) {
        return b.zzcq(context);
    }

    public synchronized zzsh zzcq(Context context) {
        if (this.a == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.a = new zzsh(context);
        }
        return this.a;
    }
}
