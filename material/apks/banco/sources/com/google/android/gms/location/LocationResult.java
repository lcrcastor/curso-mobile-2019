package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<LocationResult> CREATOR = new zzh();
    static final List<Location> a = Collections.emptyList();
    private final int b;
    private final List<Location> c;

    LocationResult(int i, List<Location> list) {
        this.b = i;
        this.c = list;
    }

    public static LocationResult create(List<Location> list) {
        if (list == null) {
            list = a;
        }
        return new LocationResult(2, list);
    }

    public static LocationResult extractResult(Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        return (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.c.size() != this.c.size()) {
            return false;
        }
        Iterator it = this.c.iterator();
        for (Location time : locationResult.c) {
            if (((Location) it.next()).getTime() != time.getTime()) {
                return false;
            }
        }
        return true;
    }

    public Location getLastLocation() {
        int size = this.c.size();
        if (size == 0) {
            return null;
        }
        return (Location) this.c.get(size - 1);
    }

    @NonNull
    public List<Location> getLocations() {
        return this.c;
    }

    public int hashCode() {
        int i = 17;
        for (Location time : this.c) {
            long time2 = time.getTime();
            i = (i * 31) + ((int) (time2 ^ (time2 >>> 32)));
        }
        return i;
    }

    public String toString() {
        String valueOf = String.valueOf(this.c);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
        sb.append("LocationResult[locations: ");
        sb.append(valueOf);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.a(this, parcel, i);
    }
}
