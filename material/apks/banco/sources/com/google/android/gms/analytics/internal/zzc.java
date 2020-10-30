package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;

public class zzc {
    private final zzf a;

    protected zzc(zzf zzf) {
        zzac.zzy(zzf);
        this.a = zzf;
    }

    private static String a(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj instanceof Boolean ? obj == Boolean.TRUE ? "true" : Reintento.Reintento_Falso : obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
    }

    private void a(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaf zzaba = this.a != null ? this.a.zzaba() : null;
        if (zzaba != null) {
            zzaba.zza(i, str, obj, obj2, obj3);
            return;
        }
        String str2 = (String) zzy.cg.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String a2 = a(obj);
        String a3 = a(obj2);
        String a4 = a(obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(a2)) {
            sb.append(str2);
            sb.append(a2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(a3)) {
            sb.append(str2);
            sb.append(a3);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(a4)) {
            sb.append(str2);
            sb.append(a4);
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.a.getContext();
    }

    public void zza(String str, Object obj) {
        a(2, str, obj, null, null);
    }

    public void zza(String str, Object obj, Object obj2) {
        a(2, str, obj, obj2, null);
    }

    public void zza(String str, Object obj, Object obj2, Object obj3) {
        a(3, str, obj, obj2, obj3);
    }

    public zzf zzaal() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void zzaam() {
        if (zzaap().zzact()) {
            throw new IllegalStateException("Call only supported on the client side");
        }
    }

    /* access modifiers changed from: protected */
    public zze zzaan() {
        return this.a.zzaan();
    }

    /* access modifiers changed from: protected */
    public zzaf zzaao() {
        return this.a.zzaao();
    }

    /* access modifiers changed from: protected */
    public zzr zzaap() {
        return this.a.zzaap();
    }

    /* access modifiers changed from: protected */
    public zzi zzaaq() {
        return this.a.zzaaq();
    }

    /* access modifiers changed from: protected */
    public zzv zzaar() {
        return this.a.zzaar();
    }

    /* access modifiers changed from: protected */
    public zzai zzaas() {
        return this.a.zzaas();
    }

    /* access modifiers changed from: protected */
    public zzn zzaat() {
        return this.a.zzabe();
    }

    /* access modifiers changed from: protected */
    public zza zzaau() {
        return this.a.zzabd();
    }

    /* access modifiers changed from: protected */
    public zzk zzaav() {
        return this.a.zzaav();
    }

    /* access modifiers changed from: protected */
    public zzu zzaaw() {
        return this.a.zzaaw();
    }

    public void zzb(String str, Object obj) {
        a(3, str, obj, null, null);
    }

    public void zzb(String str, Object obj, Object obj2) {
        a(3, str, obj, obj2, null);
    }

    public void zzb(String str, Object obj, Object obj2, Object obj3) {
        a(5, str, obj, obj2, obj3);
    }

    public void zzc(String str, Object obj) {
        a(4, str, obj, null, null);
    }

    public void zzc(String str, Object obj, Object obj2) {
        a(5, str, obj, obj2, null);
    }

    public void zzd(String str, Object obj) {
        a(5, str, obj, null, null);
    }

    public void zzd(String str, Object obj, Object obj2) {
        a(6, str, obj, obj2, null);
    }

    public void zze(String str, Object obj) {
        a(6, str, obj, null, null);
    }

    public void zzep(String str) {
        a(2, str, null, null, null);
    }

    public void zzeq(String str) {
        a(3, str, null, null, null);
    }

    public void zzer(String str) {
        a(4, str, null, null, null);
    }

    public void zzes(String str) {
        a(5, str, null, null, null);
    }

    public void zzet(String str) {
        a(6, str, null, null, null);
    }

    public boolean zzue() {
        return Log.isLoggable((String) zzy.cg.get(), 2);
    }

    public GoogleAnalytics zzxo() {
        return this.a.zzabb();
    }

    /* access modifiers changed from: protected */
    public zzb zzxu() {
        return this.a.zzxu();
    }

    /* access modifiers changed from: protected */
    public zzap zzxv() {
        return this.a.zzxv();
    }

    /* access modifiers changed from: protected */
    public void zzyl() {
        this.a.zzyl();
    }
}
