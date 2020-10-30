package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;

public final class zzmr extends zzg<zzmr> {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private String f;
    private boolean g;
    private double h;

    public String getUserId() {
        return this.c;
    }

    public void setClientId(String str) {
        this.b = str;
    }

    public void setSampleRate(double d2) {
        zzac.zzb(d2 >= 0.0d && d2 <= 100.0d, (Object) "Sample rate must be between 0% and 100%");
        this.h = d2;
    }

    public void setUserId(String str) {
        this.c = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("hitType", this.a);
        hashMap.put("clientId", this.b);
        hashMap.put("userId", this.c);
        hashMap.put("androidAdId", this.d);
        hashMap.put("AdTargetingEnabled", Boolean.valueOf(this.e));
        hashMap.put("sessionControl", this.f);
        hashMap.put("nonInteraction", Boolean.valueOf(this.g));
        hashMap.put("sampleRate", Double.valueOf(this.h));
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmr zzmr) {
        if (!TextUtils.isEmpty(this.a)) {
            zzmr.zzee(this.a);
        }
        if (!TextUtils.isEmpty(this.b)) {
            zzmr.setClientId(this.b);
        }
        if (!TextUtils.isEmpty(this.c)) {
            zzmr.setUserId(this.c);
        }
        if (!TextUtils.isEmpty(this.d)) {
            zzmr.zzef(this.d);
        }
        if (this.e) {
            zzmr.zzar(true);
        }
        if (!TextUtils.isEmpty(this.f)) {
            zzmr.zzeg(this.f);
        }
        if (this.g) {
            zzmr.zzas(this.g);
        }
        if (this.h != 0.0d) {
            zzmr.setSampleRate(this.h);
        }
    }

    public void zzar(boolean z) {
        this.e = z;
    }

    public void zzas(boolean z) {
        this.g = z;
    }

    public void zzee(String str) {
        this.a = str;
    }

    public void zzef(String str) {
        this.d = str;
    }

    public void zzeg(String str) {
        this.f = str;
    }

    public String zzxs() {
        return this.b;
    }

    public String zzzo() {
        return this.a;
    }

    public String zzzp() {
        return this.d;
    }

    public boolean zzzq() {
        return this.e;
    }

    public String zzzr() {
        return this.f;
    }

    public boolean zzzs() {
        return this.g;
    }

    public double zzzt() {
        return this.h;
    }
}
