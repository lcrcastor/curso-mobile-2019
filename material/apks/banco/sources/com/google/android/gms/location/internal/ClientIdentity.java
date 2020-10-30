package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

public class ClientIdentity extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    private final int a;
    public final String packageName;
    public final int uid;

    ClientIdentity(int i, int i2, String str) {
        this.a = i;
        this.uid = i2;
        this.packageName = str;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof ClientIdentity)) {
            return false;
        }
        ClientIdentity clientIdentity = (ClientIdentity) obj;
        return clientIdentity.uid == this.uid && zzab.equal(clientIdentity.packageName, this.packageName);
    }

    public int hashCode() {
        return this.uid;
    }

    public String toString() {
        return String.format("%d:%s", new Object[]{Integer.valueOf(this.uid), this.packageName});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.a(this, parcel, i);
    }
}
