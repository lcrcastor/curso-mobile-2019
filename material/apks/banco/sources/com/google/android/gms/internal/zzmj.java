package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;

public final class zzmj extends zzg<zzmj> {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;

    public String getContent() {
        return this.e;
    }

    public String getId() {
        return this.f;
    }

    public String getName() {
        return this.a;
    }

    public String getSource() {
        return this.b;
    }

    public void setName(String str) {
        this.a = str;
    }

    public String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("name", this.a);
        hashMap.put("source", this.b);
        hashMap.put("medium", this.c);
        hashMap.put("keyword", this.d);
        hashMap.put("content", this.e);
        hashMap.put("id", this.f);
        hashMap.put("adNetworkId", this.g);
        hashMap.put("gclid", this.h);
        hashMap.put("dclid", this.i);
        hashMap.put("aclid", this.j);
        return zzj(hashMap);
    }

    /* renamed from: zza */
    public void zzb(zzmj zzmj) {
        if (!TextUtils.isEmpty(this.a)) {
            zzmj.setName(this.a);
        }
        if (!TextUtils.isEmpty(this.b)) {
            zzmj.zzdr(this.b);
        }
        if (!TextUtils.isEmpty(this.c)) {
            zzmj.zzds(this.c);
        }
        if (!TextUtils.isEmpty(this.d)) {
            zzmj.zzdt(this.d);
        }
        if (!TextUtils.isEmpty(this.e)) {
            zzmj.zzdu(this.e);
        }
        if (!TextUtils.isEmpty(this.f)) {
            zzmj.zzdv(this.f);
        }
        if (!TextUtils.isEmpty(this.g)) {
            zzmj.zzdw(this.g);
        }
        if (!TextUtils.isEmpty(this.h)) {
            zzmj.zzdx(this.h);
        }
        if (!TextUtils.isEmpty(this.i)) {
            zzmj.zzdy(this.i);
        }
        if (!TextUtils.isEmpty(this.j)) {
            zzmj.zzdz(this.j);
        }
    }

    public void zzdr(String str) {
        this.b = str;
    }

    public void zzds(String str) {
        this.c = str;
    }

    public void zzdt(String str) {
        this.d = str;
    }

    public void zzdu(String str) {
        this.e = str;
    }

    public void zzdv(String str) {
        this.f = str;
    }

    public void zzdw(String str) {
        this.g = str;
    }

    public void zzdx(String str) {
        this.h = str;
    }

    public void zzdy(String str) {
        this.i = str;
    }

    public void zzdz(String str) {
        this.j = str;
    }

    public String zzyv() {
        return this.c;
    }

    public String zzyw() {
        return this.d;
    }

    public String zzyx() {
        return this.g;
    }

    public String zzyy() {
        return this.h;
    }

    public String zzyz() {
        return this.i;
    }

    public String zzza() {
        return this.j;
    }
}
