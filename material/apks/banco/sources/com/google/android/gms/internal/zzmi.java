package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmi extends zzg<zzmi> {
    private String a;
    private String b;
    private String c;
    private String d;

    public void setAppId(String str) {
        this.c = str;
    }

    public void setAppInstallerId(String str) {
        this.d = str;
    }

    public void setAppName(String str) {
        this.a = str;
    }

    public void setAppVersion(String str) {
        this.b = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("appName", this.a);
        hashMap.put("appVersion", this.b);
        hashMap.put("appId", this.c);
        hashMap.put("appInstallerId", this.d);
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmi zzmi) {
        if (!TextUtils.isEmpty(this.a)) {
            zzmi.setAppName(this.a);
        }
        if (!TextUtils.isEmpty(this.b)) {
            zzmi.setAppVersion(this.b);
        }
        if (!TextUtils.isEmpty(this.c)) {
            zzmi.setAppId(this.c);
        }
        if (!TextUtils.isEmpty(this.d)) {
            zzmi.setAppInstallerId(this.d);
        }
    }

    public String zzti() {
        return this.c;
    }

    public String zzys() {
        return this.a;
    }

    public String zzyt() {
        return this.b;
    }

    public String zzyu() {
        return this.d;
    }
}
