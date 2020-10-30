package com.google.android.gms.tagmanager;

import android.content.Context;

public class zzaa implements zzat {
    private static zzaa a;
    private static final Object b = new Object();
    private zzcl c;
    private zzau d;

    private zzaa(Context context) {
        this(zzav.a(context), new zzda());
    }

    zzaa(zzau zzau, zzcl zzcl) {
        this.d = zzau;
        this.c = zzcl;
    }

    public static zzat zzea(Context context) {
        zzaa zzaa;
        synchronized (b) {
            if (a == null) {
                a = new zzaa(context);
            }
            zzaa = a;
        }
        return zzaa;
    }

    public boolean zzph(String str) {
        if (!this.c.a()) {
            zzbo.zzdf("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        this.d.a(str);
        return true;
    }
}
