package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.List;

public class GeofencingRequest extends AbstractSafeParcelable {
    public static final Creator<GeofencingRequest> CREATOR = new zzd();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final int a;
    private final List<ParcelableGeofence> b;
    private final int c;

    public static final class Builder {
        private final List<ParcelableGeofence> a = new ArrayList();
        private int b = 5;

        public static int zzua(int i) {
            return i & 7;
        }

        public Builder addGeofence(Geofence geofence) {
            zzac.zzb(geofence, (Object) "geofence can't be null.");
            zzac.zzb(geofence instanceof ParcelableGeofence, (Object) "Geofence must be created using Geofence.Builder.");
            this.a.add((ParcelableGeofence) geofence);
            return this;
        }

        public Builder addGeofences(List<Geofence> list) {
            if (list == null || list.isEmpty()) {
                return this;
            }
            for (Geofence geofence : list) {
                if (geofence != null) {
                    addGeofence(geofence);
                }
            }
            return this;
        }

        public GeofencingRequest build() {
            zzac.zzb(!this.a.isEmpty(), (Object) "No geofence has been added to this request.");
            return new GeofencingRequest((List) this.a, this.b);
        }

        public Builder setInitialTrigger(int i) {
            this.b = zzua(i);
            return this;
        }
    }

    GeofencingRequest(int i, List<ParcelableGeofence> list, int i2) {
        this.a = i;
        this.b = list;
        this.c = i2;
    }

    private GeofencingRequest(List<ParcelableGeofence> list, int i) {
        this(1, list, i);
    }

    public List<Geofence> getGeofences() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.b);
        return arrayList;
    }

    public int getInitialTrigger() {
        return this.c;
    }

    public int getVersionCode() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.a(this, parcel, i);
    }

    public List<ParcelableGeofence> zzbpf() {
        return this.b;
    }
}
