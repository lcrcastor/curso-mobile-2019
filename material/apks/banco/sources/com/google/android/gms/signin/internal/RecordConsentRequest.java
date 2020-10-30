package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class RecordConsentRequest extends AbstractSafeParcelable {
    public static final Creator<RecordConsentRequest> CREATOR = new zzf();
    final int a;
    private final Account b;
    private final Scope[] c;
    private final String d;

    RecordConsentRequest(int i, Account account, Scope[] scopeArr, String str) {
        this.a = i;
        this.b = account;
        this.c = scopeArr;
        this.d = str;
    }

    public Account getAccount() {
        return this.b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.a(this, parcel, i);
    }

    public String zzahn() {
        return this.d;
    }

    public Scope[] zzcdi() {
        return this.c;
    }
}
