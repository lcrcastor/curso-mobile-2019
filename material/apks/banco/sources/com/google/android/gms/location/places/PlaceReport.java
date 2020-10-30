package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.os.EnvironmentCompat;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzab.zza;
import com.google.android.gms.common.internal.zzac;

public class PlaceReport extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR = new zzi();
    final int a;
    private final String b;
    private final String c;
    private final String d;

    PlaceReport(int i, String str, String str2, String str3) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r3) {
        /*
            int r0 = r3.hashCode()
            r1 = 1
            r2 = 0
            switch(r0) {
                case -1436706272: goto L_0x003c;
                case -1194968642: goto L_0x0032;
                case -284840886: goto L_0x0028;
                case -262743844: goto L_0x001e;
                case 1164924125: goto L_0x0014;
                case 1287171955: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0046
        L_0x000a:
            java.lang.String r0 = "inferredRadioSignals"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 3
            goto L_0x0047
        L_0x0014:
            java.lang.String r0 = "inferredSnappedToRoad"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 5
            goto L_0x0047
        L_0x001e:
            java.lang.String r0 = "inferredReverseGeocoding"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 4
            goto L_0x0047
        L_0x0028:
            java.lang.String r0 = "unknown"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 0
            goto L_0x0047
        L_0x0032:
            java.lang.String r0 = "userReported"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 1
            goto L_0x0047
        L_0x003c:
            java.lang.String r0 = "inferredGeofencing"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0046
            r3 = 2
            goto L_0x0047
        L_0x0046:
            r3 = -1
        L_0x0047:
            switch(r3) {
                case 0: goto L_0x004b;
                case 1: goto L_0x004b;
                case 2: goto L_0x004b;
                case 3: goto L_0x004b;
                case 4: goto L_0x004b;
                case 5: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            return r2
        L_0x004b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.places.PlaceReport.a(java.lang.String):boolean");
    }

    public static PlaceReport create(String str, String str2) {
        return zzj(str, str2, EnvironmentCompat.MEDIA_UNKNOWN);
    }

    public static PlaceReport zzj(String str, String str2, String str3) {
        zzac.zzy(str);
        zzac.zzhz(str2);
        zzac.zzhz(str3);
        zzac.zzb(a(str3), (Object) "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        if (zzab.equal(this.b, placeReport.b) && zzab.equal(this.c, placeReport.c) && zzab.equal(this.d, placeReport.d)) {
            z = true;
        }
        return z;
    }

    public String getPlaceId() {
        return this.b;
    }

    public String getSource() {
        return this.d;
    }

    public String getTag() {
        return this.c;
    }

    public int hashCode() {
        return zzab.hashCode(this.b, this.c, this.d);
    }

    public String toString() {
        zza zzx = zzab.zzx(this);
        zzx.zzg("placeId", this.b);
        zzx.zzg("tag", this.c);
        if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(this.d)) {
            zzx.zzg("source", this.d);
        }
        return zzx.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.a(this, parcel, i);
    }
}
