package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.util.zzo;
import java.io.IOException;

public final class MapStyleOptions extends AbstractSafeParcelable {
    public static final zzf CREATOR = new zzf();
    private static final String a = "MapStyleOptions";
    private final int b;
    private String c;

    MapStyleOptions(int i, String str) {
        this.b = i;
        this.c = str;
    }

    public MapStyleOptions(String str) {
        this.b = 1;
        this.c = str;
    }

    public static MapStyleOptions loadRawResourceStyle(Context context, int i) {
        try {
            return new MapStyleOptions(new String(zzo.zzl(context.getResources().openRawResource(i)), "UTF-8"));
        } catch (IOException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 37);
            sb.append("Failed to read resource ");
            sb.append(i);
            sb.append(": ");
            sb.append(valueOf);
            throw new NotFoundException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.a(this, parcel, i);
    }

    public String zzbsi() {
        return this.c;
    }
}
