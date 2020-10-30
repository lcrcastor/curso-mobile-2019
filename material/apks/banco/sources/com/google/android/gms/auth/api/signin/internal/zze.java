package com.google.android.gms.auth.api.signin.internal;

public class zze {
    static int a = 31;
    private int b = 1;

    public int zzahv() {
        return this.b;
    }

    public zze zzbd(boolean z) {
        this.b = (a * this.b) + (z ? 1 : 0);
        return this;
    }

    public zze zzq(Object obj) {
        this.b = (a * this.b) + (obj == null ? 0 : obj.hashCode());
        return this;
    }
}
