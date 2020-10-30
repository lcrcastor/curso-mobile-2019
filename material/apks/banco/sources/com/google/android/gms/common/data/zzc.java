package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzac;

public abstract class zzc {
    private int a;
    protected final DataHolder xi;
    protected int zK;

    public zzc(DataHolder dataHolder, int i) {
        this.xi = (DataHolder) zzac.zzy(dataHolder);
        zzfz(i);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc zzc = (zzc) obj;
        return zzab.equal(Integer.valueOf(zzc.zK), Integer.valueOf(this.zK)) && zzab.equal(Integer.valueOf(zzc.a), Integer.valueOf(this.a)) && zzc.xi == this.xi;
    }

    /* access modifiers changed from: protected */
    public boolean getBoolean(String str) {
        return this.xi.zze(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public byte[] getByteArray(String str) {
        return this.xi.zzg(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public float getFloat(String str) {
        return this.xi.zzf(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public int getInteger(String str) {
        return this.xi.zzc(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public long getLong(String str) {
        return this.xi.zzb(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public String getString(String str) {
        return this.xi.zzd(str, this.zK, this.a);
    }

    public int hashCode() {
        return zzab.hashCode(Integer.valueOf(this.zK), Integer.valueOf(this.a), this.xi);
    }

    public boolean isDataValid() {
        return !this.xi.isClosed();
    }

    /* access modifiers changed from: protected */
    public void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.xi.zza(str, this.zK, this.a, charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public int zzatc() {
        return this.zK;
    }

    /* access modifiers changed from: protected */
    public void zzfz(int i) {
        zzac.zzbr(i >= 0 && i < this.xi.getCount());
        this.zK = i;
        this.a = this.xi.zzgb(this.zK);
    }

    public boolean zzhm(String str) {
        return this.xi.zzhm(str);
    }

    /* access modifiers changed from: protected */
    public Uri zzhn(String str) {
        return this.xi.zzh(str, this.zK, this.a);
    }

    /* access modifiers changed from: protected */
    public boolean zzho(String str) {
        return this.xi.zzi(str, this.zK, this.a);
    }
}
