package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DatosCajeros implements Parcelable {
    public static final Creator<DatosCajeros> CREATOR = new Creator<DatosCajeros>() {
        public DatosCajeros createFromParcel(Parcel parcel) {
            return new DatosCajeros(parcel);
        }

        public DatosCajeros[] newArray(int i) {
            return new DatosCajeros[i];
        }
    };
    public String expendeDolares;
    public String habilitado;

    public int describeContents() {
        return 0;
    }

    public DatosCajeros(String str, String str2) {
        this.habilitado = str;
        this.expendeDolares = str2;
    }

    protected DatosCajeros(Parcel parcel) {
        this.habilitado = parcel.readString();
        this.expendeDolares = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.habilitado);
        parcel.writeString(this.expendeDolares);
    }
}
