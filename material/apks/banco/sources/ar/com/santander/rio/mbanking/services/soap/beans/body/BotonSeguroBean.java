package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class BotonSeguroBean implements Parcelable {
    public static final Creator<BotonSeguroBean> CREATOR = new Creator<BotonSeguroBean>() {
        public BotonSeguroBean createFromParcel(Parcel parcel) {
            return new BotonSeguroBean(parcel);
        }

        public BotonSeguroBean[] newArray(int i) {
            return new BotonSeguroBean[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("url")
    private String url;

    public int describeContents() {
        return 0;
    }

    public BotonSeguroBean() {
    }

    public BotonSeguroBean(String str, String str2, String str3) {
        this.descripcion = str;
        this.nombre = str2;
        this.url = str3;
    }

    public static Creator<BotonSeguroBean> getCREATOR() {
        return CREATOR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.descripcion);
        parcel.writeString(this.nombre);
        parcel.writeString(this.url);
    }

    protected BotonSeguroBean(Parcel parcel) {
        this.descripcion = parcel.readString();
        this.nombre = parcel.readString();
        this.url = parcel.readString();
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String str) {
        this.nombre = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
