package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzr.zza;
import com.google.android.gms.common.zzc;
import java.util.Collection;

public class GetServiceRequest extends AbstractSafeParcelable {
    public static final Creator<GetServiceRequest> CREATOR = new zzk();
    final int a;
    final int b;
    int c;
    String d;
    IBinder e;
    Scope[] f;
    Bundle g;
    Account h;
    long i;

    public GetServiceRequest(int i2) {
        this.a = 3;
        this.c = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.b = i2;
    }

    GetServiceRequest(int i2, int i3, int i4, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, long j) {
        this.a = i2;
        this.b = i3;
        this.c = i4;
        this.d = str;
        if (i2 < 2) {
            this.h = a(iBinder);
        } else {
            this.e = iBinder;
            this.h = account;
        }
        this.f = scopeArr;
        this.g = bundle;
        this.i = j;
    }

    private Account a(IBinder iBinder) {
        if (iBinder != null) {
            return zza.zza(zza.zzdr(iBinder));
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zzk.a(this, parcel, i2);
    }

    public GetServiceRequest zzb(zzr zzr) {
        if (zzr != null) {
            this.e = zzr.asBinder();
        }
        return this;
    }

    public GetServiceRequest zzd(Account account) {
        this.h = account;
        return this;
    }

    public GetServiceRequest zzf(Collection<Scope> collection) {
        this.f = (Scope[]) collection.toArray(new Scope[collection.size()]);
        return this;
    }

    public GetServiceRequest zzht(String str) {
        this.d = str;
        return this;
    }

    public GetServiceRequest zzo(Bundle bundle) {
        this.g = bundle;
        return this;
    }
}
