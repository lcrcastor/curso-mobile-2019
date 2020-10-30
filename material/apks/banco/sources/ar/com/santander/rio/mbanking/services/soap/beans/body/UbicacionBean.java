package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class UbicacionBean implements Parcelable {
    public static final Creator<UbicacionBean> CREATOR = new Creator<UbicacionBean>() {
        public UbicacionBean createFromParcel(Parcel parcel) {
            return new UbicacionBean(parcel);
        }

        public UbicacionBean[] newArray(int i) {
            return new UbicacionBean[i];
        }
    };
    @SerializedName("latitud")
    private String latitud;
    @SerializedName("longitud")
    private String longitud;
    @SerializedName("nombre")
    private String nombre;

    public int describeContents() {
        return 0;
    }

    public UbicacionBean() {
    }

    public UbicacionBean(String str, String str2, String str3) {
        this.nombre = str;
        this.latitud = str2;
        this.longitud = str3;
    }

    protected UbicacionBean(Parcel parcel) {
        this.nombre = parcel.readString();
        this.latitud = parcel.readString();
        this.longitud = parcel.readString();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String str) {
        this.latitud = str;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String str) {
        this.longitud = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombre);
        parcel.writeString(this.latitud);
        parcel.writeString(this.longitud);
    }
}
