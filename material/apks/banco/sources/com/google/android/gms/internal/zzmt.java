package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmt extends zzg<zzmt> {
    public String V;
    public String ap;
    public String aq;

    public String getAction() {
        return this.V;
    }

    public String getTarget() {
        return this.aq;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("network", this.ap);
        hashMap.put("action", this.V);
        hashMap.put("target", this.aq);
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmt zzmt) {
        if (!TextUtils.isEmpty(this.ap)) {
            zzmt.zzej(this.ap);
        }
        if (!TextUtils.isEmpty(this.V)) {
            zzmt.zzec(this.V);
        }
        if (!TextUtils.isEmpty(this.aq)) {
            zzmt.zzek(this.aq);
        }
    }

    public void zzec(String str) {
        this.V = str;
    }

    public void zzej(String str) {
        this.ap = str;
    }

    public void zzek(String str) {
        this.aq = str;
    }

    public String zzzz() {
        return this.ap;
    }
}
