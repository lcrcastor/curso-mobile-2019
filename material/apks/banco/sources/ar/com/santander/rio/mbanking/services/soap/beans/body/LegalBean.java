package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class LegalBean implements Parcelable {
    public static final Creator<LegalBean> CREATOR = new Creator<LegalBean>() {
        public LegalBean createFromParcel(Parcel parcel) {
            return new LegalBean(parcel);
        }

        public LegalBean[] newArray(int i) {
            return new LegalBean[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("idLeyenda")
    private String idLegal;
    @SerializedName("titulo")
    private String titulo;

    public int describeContents() {
        return 0;
    }

    public LegalBean(String str, String str2, String str3) {
        this.idLegal = str;
        this.titulo = str2;
        this.descripcion = str3;
    }

    public LegalBean() {
    }

    protected LegalBean(Parcel parcel) {
        this.idLegal = parcel.readString();
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
    }

    public String getIdLegal() {
        return this.idLegal;
    }

    public void setIdLegal(String str) {
        this.idLegal = str;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idLegal);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }
}
