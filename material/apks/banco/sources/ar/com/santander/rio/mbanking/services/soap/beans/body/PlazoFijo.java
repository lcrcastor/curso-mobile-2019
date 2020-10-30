package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PlazoFijo implements Parcelable {
    public static final Creator<PlazoFijo> CREATOR = new Creator<PlazoFijo>() {
        public PlazoFijo createFromParcel(Parcel parcel) {
            return new PlazoFijo(parcel);
        }

        public PlazoFijo[] newArray(int i) {
            return new PlazoFijo[i];
        }
    };
    private String importeDolares;
    private String importePesos;

    public int describeContents() {
        return 0;
    }

    protected PlazoFijo(Parcel parcel) {
        this.importePesos = parcel.readString();
        this.importeDolares = parcel.readString();
    }

    public String getImportePesos() {
        return this.importePesos;
    }

    public void setImportePesos(String str) {
        this.importePesos = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.importePesos);
        parcel.writeString(this.importeDolares);
    }
}
