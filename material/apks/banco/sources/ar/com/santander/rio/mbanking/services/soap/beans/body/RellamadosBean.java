package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RellamadosBean implements Parcelable {
    public static final Creator<RellamadosBean> CREATOR = new Creator<RellamadosBean>() {
        public RellamadosBean createFromParcel(Parcel parcel) {
            return new RellamadosBean(parcel);
        }

        public RellamadosBean[] newArray(int i) {
            return new RellamadosBean[i];
        }
    };
    @SerializedName("cantidad")
    @Expose
    private String cantidad;
    @SerializedName("tiempo")
    @Expose
    private String tiempo;

    public int describeContents() {
        return 0;
    }

    private RellamadosBean(Parcel parcel) {
        this.tiempo = parcel.readString();
        this.cantidad = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tiempo);
        parcel.writeString(this.cantidad);
    }

    public long getTiempo() {
        if (this.tiempo != null) {
            return Long.parseLong(this.tiempo);
        }
        return 3;
    }

    public void setTiempo(String str) {
        this.tiempo = str;
    }

    public long getCantidad() {
        if (this.cantidad != null) {
            return Long.parseLong(this.cantidad);
        }
        return 5;
    }

    public void setCantidad(String str) {
        this.cantidad = str;
    }
}
