package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenesysChatBodyRequestBean implements Parcelable {
    public static final Creator<GenesysChatBodyRequestBean> CREATOR = new Creator<GenesysChatBodyRequestBean>() {
        public GenesysChatBodyRequestBean createFromParcel(Parcel parcel) {
            return new GenesysChatBodyRequestBean(parcel);
        }

        public GenesysChatBodyRequestBean[] newArray(int i) {
            return new GenesysChatBodyRequestBean[i];
        }
    };
    @SerializedName("indAccion")
    @Expose
    private String indAccion;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public int describeContents() {
        return 0;
    }

    private GenesysChatBodyRequestBean(Parcel parcel) {
        this.mensaje = "";
        this.mensaje = (String) parcel.readValue(String.class.getClassLoader());
        this.indAccion = (String) parcel.readValue(String.class.getClassLoader());
    }

    public GenesysChatBodyRequestBean() {
        this.mensaje = "";
    }

    public GenesysChatBodyRequestBean(String str, String str2) {
        this.mensaje = "";
        this.mensaje = str2;
        this.indAccion = str;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public String getIndAccion() {
        return this.indAccion;
    }

    public void setIndAccion(String str) {
        this.indAccion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.mensaje);
        parcel.writeValue(this.indAccion);
    }
}
