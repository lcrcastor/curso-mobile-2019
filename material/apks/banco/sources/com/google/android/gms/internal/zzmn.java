package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmn extends zzg<zzmn> {
    public int S;
    public int T;
    public int U;
    private String a;
    public int zzbvw;
    public int zzbvx;

    public String getLanguage() {
        return this.a;
    }

    public void setLanguage(String str) {
        this.a = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("language", this.a);
        hashMap.put("screenColors", Integer.valueOf(this.S));
        hashMap.put("screenWidth", Integer.valueOf(this.zzbvw));
        hashMap.put("screenHeight", Integer.valueOf(this.zzbvx));
        hashMap.put("viewportWidth", Integer.valueOf(this.T));
        hashMap.put("viewportHeight", Integer.valueOf(this.U));
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmn zzmn) {
        if (this.S != 0) {
            zzmn.zzbv(this.S);
        }
        if (this.zzbvw != 0) {
            zzmn.zzbw(this.zzbvw);
        }
        if (this.zzbvx != 0) {
            zzmn.zzbx(this.zzbvx);
        }
        if (this.T != 0) {
            zzmn.zzby(this.T);
        }
        if (this.U != 0) {
            zzmn.zzbz(this.U);
        }
        if (!TextUtils.isEmpty(this.a)) {
            zzmn.setLanguage(this.a);
        }
    }

    public void zzbv(int i) {
        this.S = i;
    }

    public void zzbw(int i) {
        this.zzbvw = i;
    }

    public void zzbx(int i) {
        this.zzbvx = i;
    }

    public void zzby(int i) {
        this.T = i;
    }

    public void zzbz(int i) {
        this.U = i;
    }

    public int zzze() {
        return this.S;
    }

    public int zzzf() {
        return this.zzbvw;
    }

    public int zzzg() {
        return this.zzbvx;
    }

    public int zzzh() {
        return this.T;
    }

    public int zzzi() {
        return this.U;
    }
}
