package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UrlSorpresa implements Parcelable {
    public static final Creator<UrlSorpresa> CREATOR = new Creator<UrlSorpresa>() {
        public UrlSorpresa createFromParcel(Parcel parcel) {
            return new UrlSorpresa(parcel);
        }

        public UrlSorpresa[] newArray(int i) {
            return new UrlSorpresa[i];
        }
    };
    public String descripcionCorta;
    public String descripcionLarga;

    public int describeContents() {
        return 0;
    }

    public UrlSorpresa() {
    }

    public UrlSorpresa(String str, String str2) {
        this.descripcionCorta = str;
        this.descripcionLarga = str2;
    }

    protected UrlSorpresa(Parcel parcel) {
        this.descripcionCorta = parcel.readString();
        this.descripcionLarga = parcel.readString();
    }

    public String getDescripcionCorta() {
        return this.descripcionCorta;
    }

    public void setDescripcionCorta(String str) {
        this.descripcionCorta = str;
    }

    public String getDescripcionLarga() {
        return this.descripcionLarga;
    }

    public void setDescripcionLarga(String str) {
        this.descripcionLarga = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.descripcionCorta);
        parcel.writeString(this.descripcionLarga);
    }
}
