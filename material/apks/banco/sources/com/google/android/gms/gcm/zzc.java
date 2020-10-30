package com.google.android.gms.gcm;

import android.os.Bundle;

public class zzc {
    public static final zzc aff = new zzc(0, 30, 3600);
    public static final zzc afg = new zzc(1, 30, 3600);
    private final int a;
    private final int b;
    private final int c;

    private zzc(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc zzc = (zzc) obj;
        return zzc.a == this.a && zzc.b == this.b && zzc.c == this.c;
    }

    public int hashCode() {
        return (((((this.a + 1) ^ 1000003) * 1000003) ^ this.b) * 1000003) ^ this.c;
    }

    public String toString() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        StringBuilder sb = new StringBuilder(74);
        sb.append("policy=");
        sb.append(i);
        sb.append(" initial_backoff=");
        sb.append(i2);
        sb.append(" maximum_backoff=");
        sb.append(i3);
        return sb.toString();
    }

    public Bundle zzaj(Bundle bundle) {
        bundle.putInt("retry_policy", this.a);
        bundle.putInt("initial_backoff_seconds", this.b);
        bundle.putInt("maximum_backoff_seconds", this.c);
        return bundle;
    }

    public int zzboc() {
        return this.a;
    }

    public int zzbod() {
        return this.b;
    }

    public int zzboe() {
        return this.c;
    }
}
