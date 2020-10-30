package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class SignInButtonConfig extends AbstractSafeParcelable {
    public static final Creator<SignInButtonConfig> CREATOR = new zzaf();
    final int a;
    private final int b;
    private final int c;
    @Deprecated
    private final Scope[] d;

    SignInButtonConfig(int i, int i2, int i3, Scope[] scopeArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = scopeArr;
    }

    public SignInButtonConfig(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, null);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaf.a(this, parcel, i);
    }

    public int zzavh() {
        return this.b;
    }

    public int zzavi() {
        return this.c;
    }

    @Deprecated
    public Scope[] zzavj() {
        return this.d;
    }
}
