package com.google.android.gms.internal;

import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmp extends zzg<zzmp> {
    private String a;
    private String b;
    private String c;
    private long d;

    public String getAction() {
        return this.b;
    }

    public String getCategory() {
        return this.a;
    }

    public String getLabel() {
        return this.c;
    }

    public long getValue() {
        return this.d;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("category", this.a);
        hashMap.put("action", this.b);
        hashMap.put("label", this.c);
        hashMap.put(TarjetasConstants.VALUE, Long.valueOf(this.d));
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmp zzmp) {
        if (!TextUtils.isEmpty(this.a)) {
            zzmp.zzeb(this.a);
        }
        if (!TextUtils.isEmpty(this.b)) {
            zzmp.zzec(this.b);
        }
        if (!TextUtils.isEmpty(this.c)) {
            zzmp.zzed(this.c);
        }
        if (this.d != 0) {
            zzmp.zzq(this.d);
        }
    }

    public void zzeb(String str) {
        this.a = str;
    }

    public void zzec(String str) {
        this.b = str;
    }

    public void zzed(String str) {
        this.c = str;
    }

    public void zzq(long j) {
        this.d = j;
    }
}
