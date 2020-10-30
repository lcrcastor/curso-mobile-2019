package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class OcupacionBean implements Parcelable {
    public static final Creator<OcupacionBean> CREATOR = new Creator<OcupacionBean>() {
        public OcupacionBean createFromParcel(Parcel parcel) {
            return new OcupacionBean(parcel);
        }

        public OcupacionBean[] newArray(int i) {
            return new OcupacionBean[i];
        }
    };
    @SerializedName("codOcupacion")
    private String codOcupacion;
    @SerializedName("descOcupacion")
    private String descOcupacion;

    public int describeContents() {
        return 0;
    }

    public OcupacionBean() {
    }

    public OcupacionBean(String str, String str2) {
        this.codOcupacion = str;
        this.descOcupacion = str2;
    }

    protected OcupacionBean(Parcel parcel) {
        this.codOcupacion = parcel.readString();
        this.descOcupacion = parcel.readString();
    }

    public String getCodOcupacion() {
        return this.codOcupacion;
    }

    public void setCodOcupacion(String str) {
        this.codOcupacion = str;
    }

    public String getDescOcupacion() {
        return this.descOcupacion;
    }

    public void setDescOcupacion(String str) {
        this.descOcupacion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codOcupacion);
        parcel.writeString(this.descOcupacion);
    }
}
