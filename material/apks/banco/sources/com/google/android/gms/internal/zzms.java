package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.UUID;

public final class zzms extends zzg<zzms> {
    private String a;
    private int b;
    private int c;
    private String d;
    private String e;
    private boolean f;
    private boolean g;

    public zzms() {
        this(false);
    }

    public zzms(boolean z) {
        this(z, a());
    }

    public zzms(boolean z, int i) {
        zzac.zzgq(i);
        this.b = i;
        this.g = z;
    }

    static int a() {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        int mostSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L);
        if (mostSignificantBits != 0) {
            return mostSignificantBits;
        }
        Log.e("GAv4", "UUID.randomUUID() returned 0.");
        return SubsamplingScaleImageView.TILE_SIZE_AUTO;
    }

    private void b() {
    }

    public void setScreenName(String str) {
        b();
        this.a = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("screenName", this.a);
        hashMap.put("interstitial", Boolean.valueOf(this.f));
        hashMap.put("automatic", Boolean.valueOf(this.g));
        hashMap.put("screenId", Integer.valueOf(this.b));
        hashMap.put("referrerScreenId", Integer.valueOf(this.c));
        hashMap.put("referrerScreenName", this.d);
        hashMap.put("referrerUri", this.e);
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzms zzms) {
        if (!TextUtils.isEmpty(this.a)) {
            zzms.setScreenName(this.a);
        }
        if (this.b != 0) {
            zzms.zzca(this.b);
        }
        if (this.c != 0) {
            zzms.zzcb(this.c);
        }
        if (!TextUtils.isEmpty(this.d)) {
            zzms.zzeh(this.d);
        }
        if (!TextUtils.isEmpty(this.e)) {
            zzms.zzei(this.e);
        }
        if (this.f) {
            zzms.zzau(this.f);
        }
        if (this.g) {
            zzms.zzat(this.g);
        }
    }

    public void zzat(boolean z) {
        b();
        this.g = z;
    }

    public void zzau(boolean z) {
        b();
        this.f = z;
    }

    public void zzca(int i) {
        b();
        this.b = i;
    }

    public void zzcb(int i) {
        b();
        this.c = i;
    }

    public void zzeh(String str) {
        b();
        this.d = str;
    }

    public void zzei(String str) {
        b();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.e = str;
    }

    public String zzzv() {
        return this.a;
    }

    public int zzzw() {
        return this.b;
    }

    public String zzzx() {
        return this.e;
    }
}
