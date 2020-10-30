package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public abstract class zzk<T> implements Comparable<zzk<T>> {
    /* access modifiers changed from: private */
    public final zza a;
    private final int b;
    private final String c;
    private final int d;
    private final com.google.android.gms.internal.zzm.zza e;
    private Integer f;
    private zzl g;
    private boolean h;
    private boolean i;
    private boolean j;
    private long k;
    private zzo l;
    private com.google.android.gms.internal.zzb.zza m;

    public enum zza {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public zzk(int i2, String str, com.google.android.gms.internal.zzm.zza zza2) {
        this.a = zza.a ? new zza() : null;
        this.h = true;
        this.i = false;
        this.j = false;
        this.k = 0;
        this.m = null;
        this.b = i2;
        this.c = str;
        this.e = zza2;
        zza((zzo) new zzd());
        this.d = b(str);
    }

    private byte[] a(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Entry entry : map.entrySet()) {
                sb.append(URLEncoder.encode((String) entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode((String) entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString().getBytes(str);
        } catch (UnsupportedEncodingException e2) {
            String str2 = "Encoding not supported: ";
            String valueOf = String.valueOf(str);
            throw new RuntimeException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e2);
        }
    }

    private static int b(String str) {
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(final String str) {
        if (this.g != null) {
            this.g.a(this);
        }
        if (zza.a) {
            final long id2 = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        zzk.this.a.a(str, id2);
                        zzk.this.a.a(toString());
                    }
                });
                return;
            }
            this.a.a(str, id2);
            this.a.a(toString());
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.k;
        if (elapsedRealtime >= 3000) {
            zzs.zzb("%d ms: %s", Long.valueOf(elapsedRealtime), toString());
        }
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    public int getMethod() {
        return this.b;
    }

    public String getUrl() {
        return this.c;
    }

    public boolean isCanceled() {
        return false;
    }

    public String toString() {
        String str = "0x";
        String valueOf = String.valueOf(Integer.toHexString(zzf()));
        String concat = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        String str2 = "[ ] ";
        String valueOf2 = String.valueOf(getUrl());
        String valueOf3 = String.valueOf(zzr());
        String valueOf4 = String.valueOf(this.f);
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(valueOf2).length() + String.valueOf(concat).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length());
        sb.append(str2);
        sb.append(valueOf2);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(concat);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(valueOf3);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(valueOf4);
        return sb.toString();
    }

    public final zzk<?> zza(int i2) {
        this.f = Integer.valueOf(i2);
        return this;
    }

    public zzk<?> zza(com.google.android.gms.internal.zzb.zza zza2) {
        this.m = zza2;
        return this;
    }

    public zzk<?> zza(zzl zzl) {
        this.g = zzl;
        return this;
    }

    public zzk<?> zza(zzo zzo) {
        this.l = zzo;
        return this;
    }

    /* access modifiers changed from: protected */
    public abstract zzm<T> zza(zzi zzi);

    /* access modifiers changed from: protected */
    public abstract void zza(T t);

    /* access modifiers changed from: protected */
    public zzr zzb(zzr zzr) {
        return zzr;
    }

    /* renamed from: zzc */
    public int compareTo(zzk<T> zzk) {
        zza zzr = zzr();
        zza zzr2 = zzk.zzr();
        return zzr == zzr2 ? this.f.intValue() - zzk.f.intValue() : zzr2.ordinal() - zzr.ordinal();
    }

    public void zzc(zzr zzr) {
        if (this.e != null) {
            this.e.zze(zzr);
        }
    }

    public void zzc(String str) {
        if (zza.a) {
            this.a.a(str, Thread.currentThread().getId());
            return;
        }
        if (this.k == 0) {
            this.k = SystemClock.elapsedRealtime();
        }
    }

    public int zzf() {
        return this.d;
    }

    public String zzg() {
        return getUrl();
    }

    public com.google.android.gms.internal.zzb.zza zzh() {
        return this.m;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Map<String, String> zzi() {
        return zzm();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String zzj() {
        return zzn();
    }

    @Deprecated
    public String zzk() {
        return zzo();
    }

    @Deprecated
    public byte[] zzl() {
        Map zzi = zzi();
        if (zzi == null || zzi.size() <= 0) {
            return null;
        }
        return a(zzi, zzj());
    }

    /* access modifiers changed from: protected */
    public Map<String, String> zzm() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String zzn() {
        return "UTF-8";
    }

    public String zzo() {
        String str = "application/x-www-form-urlencoded; charset=";
        String valueOf = String.valueOf(zzn());
        return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }

    public byte[] zzp() {
        Map zzm = zzm();
        if (zzm == null || zzm.size() <= 0) {
            return null;
        }
        return a(zzm, zzn());
    }

    public final boolean zzq() {
        return this.h;
    }

    public zza zzr() {
        return zza.NORMAL;
    }

    public final int zzs() {
        return this.l.zzc();
    }

    public zzo zzt() {
        return this.l;
    }

    public void zzu() {
        this.j = true;
    }

    public boolean zzv() {
        return this.j;
    }
}
