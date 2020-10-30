package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

public class ActivityTransition extends AbstractSafeParcelable {
    public static final zzb CREATOR = new zzb();
    private final int a;
    private final int b;
    private final int c;

    ActivityTransition(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityTransition)) {
            return false;
        }
        ActivityTransition activityTransition = (ActivityTransition) obj;
        return this.b == activityTransition.b && this.c == activityTransition.c;
    }

    public int getVersionCode() {
        return this.a;
    }

    public int hashCode() {
        return zzab.hashCode(Integer.valueOf(this.b), Integer.valueOf(this.c));
    }

    public String toString() {
        int i = this.b;
        int i2 = this.c;
        StringBuilder sb = new StringBuilder(75);
        sb.append("ActivityTransition [mActivityType=");
        sb.append(i);
        sb.append(", mTransitionType=");
        sb.append(i2);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.a(this, parcel, i);
    }

    public int zzbeb() {
        return this.b;
    }

    public int zzbpc() {
        return this.c;
    }
}
