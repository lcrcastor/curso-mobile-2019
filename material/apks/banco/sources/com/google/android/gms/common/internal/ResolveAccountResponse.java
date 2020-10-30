package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzr.zza;

public class ResolveAccountResponse extends AbstractSafeParcelable {
    public static final Creator<ResolveAccountResponse> CREATOR = new zzae();
    final int a;
    IBinder b;
    private ConnectionResult c;
    private boolean d;
    private boolean e;

    ResolveAccountResponse(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.a = i;
        this.b = iBinder;
        this.c = connectionResult;
        this.d = z;
        this.e = z2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResolveAccountResponse)) {
            return false;
        }
        ResolveAccountResponse resolveAccountResponse = (ResolveAccountResponse) obj;
        return this.c.equals(resolveAccountResponse.c) && zzavd().equals(resolveAccountResponse.zzavd());
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzae.a(this, parcel, i);
    }

    public zzr zzavd() {
        return zza.zzdr(this.b);
    }

    public ConnectionResult zzave() {
        return this.c;
    }

    public boolean zzavf() {
        return this.d;
    }

    public boolean zzavg() {
        return this.e;
    }
}
