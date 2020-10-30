package com.google.android.gms.analytics.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Command implements Parcelable {
    @Deprecated
    public static final Creator<Command> CREATOR = new Creator<Command>() {
        @Deprecated
        /* renamed from: a */
        public Command createFromParcel(Parcel parcel) {
            return new Command(parcel);
        }

        @Deprecated
        /* renamed from: a */
        public Command[] newArray(int i) {
            return new Command[i];
        }
    };
    private String a;
    private String b;
    private String c;

    @Deprecated
    public Command() {
    }

    @Deprecated
    Command(Parcel parcel) {
        a(parcel);
    }

    @Deprecated
    private void a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.a;
    }

    public String getValue() {
        return this.c;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
    }
}
