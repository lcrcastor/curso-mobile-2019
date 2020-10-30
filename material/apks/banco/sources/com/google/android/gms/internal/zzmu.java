package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmu extends zzg<zzmu> {
    public String W;

    /* renamed from: ar reason: collision with root package name */
    public String f274ar;
    public long as;
    public String mCategory;

    public String getCategory() {
        return this.mCategory;
    }

    public String getLabel() {
        return this.W;
    }

    public long getTimeInMillis() {
        return this.as;
    }

    public void setTimeInMillis(long j) {
        this.as = j;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("variableName", this.f274ar);
        hashMap.put("timeInMillis", Long.valueOf(this.as));
        hashMap.put("category", this.mCategory);
        hashMap.put("label", this.W);
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmu zzmu) {
        if (!TextUtils.isEmpty(this.f274ar)) {
            zzmu.zzel(this.f274ar);
        }
        if (this.as != 0) {
            zzmu.setTimeInMillis(this.as);
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            zzmu.zzeb(this.mCategory);
        }
        if (!TextUtils.isEmpty(this.W)) {
            zzmu.zzed(this.W);
        }
    }

    public String zzaaa() {
        return this.f274ar;
    }

    public void zzeb(String str) {
        this.mCategory = str;
    }

    public void zzed(String str) {
        this.W = str;
    }

    public void zzel(String str) {
        this.f274ar = str;
    }
}
