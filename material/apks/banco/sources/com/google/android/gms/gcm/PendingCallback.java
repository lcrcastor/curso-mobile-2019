package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class PendingCallback implements Parcelable, ReflectedParcelable {
    public static final Creator<PendingCallback> CREATOR = new Creator<PendingCallback>() {
        /* renamed from: a */
        public PendingCallback createFromParcel(Parcel parcel) {
            return new PendingCallback(parcel);
        }

        /* renamed from: a */
        public PendingCallback[] newArray(int i) {
            return new PendingCallback[i];
        }
    };
    final IBinder a;

    public PendingCallback(Parcel parcel) {
        this.a = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public IBinder getIBinder() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.a);
    }
}
