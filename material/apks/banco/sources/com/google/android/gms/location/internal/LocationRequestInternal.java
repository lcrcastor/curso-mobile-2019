package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class LocationRequestInternal extends AbstractSafeParcelable {
    public static final zzm CREATOR = new zzm();
    static final List<ClientIdentity> a = Collections.emptyList();
    LocationRequest b;
    boolean c;
    List<ClientIdentity> d;
    @Nullable
    String e;
    boolean f;
    boolean g;
    private final int h;

    LocationRequestInternal(int i, LocationRequest locationRequest, boolean z, List<ClientIdentity> list, @Nullable String str, boolean z2, boolean z3) {
        this.h = i;
        this.b = locationRequest;
        this.c = z;
        this.d = list;
        this.e = str;
        this.f = z2;
        this.g = z3;
    }

    public static LocationRequestInternal zza(@Nullable String str, LocationRequest locationRequest) {
        LocationRequestInternal locationRequestInternal = new LocationRequestInternal(1, locationRequest, true, a, str, false, false);
        return locationRequestInternal;
    }

    @Deprecated
    public static LocationRequestInternal zzb(LocationRequest locationRequest) {
        return zza(null, locationRequest);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.h;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof LocationRequestInternal)) {
            return false;
        }
        LocationRequestInternal locationRequestInternal = (LocationRequestInternal) obj;
        if (zzab.equal(this.b, locationRequestInternal.b) && this.c == locationRequestInternal.c && this.f == locationRequestInternal.f && zzab.equal(this.d, locationRequestInternal.d) && this.g == locationRequestInternal.g) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.toString());
        if (this.e != null) {
            sb.append(" tag=");
            sb.append(this.e);
        }
        sb.append(" trigger=");
        sb.append(this.c);
        sb.append(" hideAppOps=");
        sb.append(this.f);
        sb.append(" clients=");
        sb.append(this.d);
        sb.append(" forceCoarseLocation=");
        sb.append(this.g);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.a(this, parcel, i);
    }
}
