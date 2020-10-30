package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
public class ValidateAccountRequest extends AbstractSafeParcelable {
    public static final Creator<ValidateAccountRequest> CREATOR = new zzak();
    final int a;
    final IBinder b;
    private final int c;
    private final Scope[] d;
    private final Bundle e;
    private final String f;

    ValidateAccountRequest(int i, int i2, IBinder iBinder, Scope[] scopeArr, Bundle bundle, String str) {
        this.a = i;
        this.c = i2;
        this.b = iBinder;
        this.d = scopeArr;
        this.e = bundle;
        this.f = str;
    }

    public String getCallingPackage() {
        return this.f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzak.a(this, parcel, i);
    }

    public Scope[] zzavj() {
        return this.d;
    }

    public int zzavl() {
        return this.c;
    }

    public Bundle zzavm() {
        return this.e;
    }
}
