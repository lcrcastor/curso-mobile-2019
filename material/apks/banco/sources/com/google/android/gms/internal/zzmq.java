package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmq extends zzg<zzmq> {
    public String Y;
    public boolean Z;

    public String getDescription() {
        return this.Y;
    }

    public void setDescription(String str) {
        this.Y = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("description", this.Y);
        hashMap.put("fatal", Boolean.valueOf(this.Z));
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmq zzmq) {
        if (!TextUtils.isEmpty(this.Y)) {
            zzmq.setDescription(this.Y);
        }
        if (this.Z) {
            zzmq.zzaq(this.Z);
        }
    }

    public void zzaq(boolean z) {
        this.Z = z;
    }

    public boolean zzzn() {
        return this.Z;
    }
}
