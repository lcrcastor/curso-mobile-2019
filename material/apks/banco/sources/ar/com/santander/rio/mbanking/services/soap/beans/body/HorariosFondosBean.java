package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class HorariosFondosBean implements Parcelable {
    public static final Creator<HorariosFondosBean> CREATOR = new Creator<HorariosFondosBean>() {
        public HorariosFondosBean createFromParcel(Parcel parcel) {
            return new HorariosFondosBean(parcel);
        }

        public HorariosFondosBean[] newArray(int i) {
            return new HorariosFondosBean[i];
        }
    };
    @SerializedName("apertura")
    private String apertura;
    @SerializedName("cierre")
    private String cierre;

    public int describeContents() {
        return 0;
    }

    public HorariosFondosBean() {
        this.apertura = "";
        this.cierre = "";
    }

    public HorariosFondosBean(String str, String str2) {
        this.apertura = str;
        this.cierre = str2;
    }

    protected HorariosFondosBean(Parcel parcel) {
        this.apertura = parcel.readString();
        this.cierre = parcel.readString();
    }

    public String getApertura() {
        return this.apertura;
    }

    public void setApertura(String str) {
        this.apertura = str;
    }

    public String getCierre() {
        return this.cierre;
    }

    public void setCierre(String str) {
        this.cierre = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.apertura);
        parcel.writeString(this.cierre);
    }
}
