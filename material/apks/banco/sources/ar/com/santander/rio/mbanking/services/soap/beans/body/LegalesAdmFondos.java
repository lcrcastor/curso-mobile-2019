package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LegalesAdmFondos implements Parcelable {
    public static final Creator<LegalesAdmFondos> CREATOR = new Creator<LegalesAdmFondos>() {
        public LegalesAdmFondos createFromParcel(Parcel parcel) {
            return new LegalesAdmFondos(parcel);
        }

        public LegalesAdmFondos[] newArray(int i) {
            return new LegalesAdmFondos[i];
        }
    };
    @SerializedName("legalHonorarios")
    private String legalHonorarios;
    @SerializedName("legalHorarios")
    private String legalHorarios;

    public int describeContents() {
        return 0;
    }

    public LegalesAdmFondos() {
    }

    public LegalesAdmFondos(String str, String str2) {
        this.legalHonorarios = str;
        this.legalHorarios = str2;
    }

    protected LegalesAdmFondos(Parcel parcel) {
        this.legalHonorarios = parcel.readString();
        this.legalHorarios = parcel.readString();
    }

    public String getLegalHonorarios() {
        return this.legalHonorarios;
    }

    public void setLegalHonorarios(String str) {
        this.legalHonorarios = str;
    }

    public String getLegalHorarios() {
        return this.legalHorarios;
    }

    public void setLegalHorarios(String str) {
        this.legalHorarios = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.legalHonorarios);
        parcel.writeString(this.legalHorarios);
    }
}
