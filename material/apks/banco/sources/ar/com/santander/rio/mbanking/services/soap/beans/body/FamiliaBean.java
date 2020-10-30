package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class FamiliaBean implements Parcelable {
    public static final Creator<FamiliaBean> CREATOR = new Creator<FamiliaBean>() {
        public FamiliaBean createFromParcel(Parcel parcel) {
            return new FamiliaBean(parcel);
        }

        public FamiliaBean[] newArray(int i) {
            return new FamiliaBean[i];
        }
    };
    @SerializedName("idFamilia")
    private String idFamilia;
    @SerializedName("imagenDetalle")
    private String imagenDetalle;
    @SerializedName("imagenLista")
    private String imagenLista;
    @SerializedName("nombreFamilia")
    private String nombreFamilia;

    public int describeContents() {
        return 0;
    }

    protected FamiliaBean(Parcel parcel) {
        this.idFamilia = (String) parcel.readValue(String.class.getClassLoader());
        this.nombreFamilia = (String) parcel.readValue(String.class.getClassLoader());
        this.imagenLista = (String) parcel.readValue(String.class.getClassLoader());
        this.imagenDetalle = (String) parcel.readValue(String.class.getClassLoader());
    }

    public FamiliaBean() {
    }

    public FamiliaBean(String str, String str2, String str3, String str4) {
        this.idFamilia = str;
        this.nombreFamilia = str2;
        this.imagenLista = str3;
        this.imagenDetalle = str4;
    }

    public String getIdFamilia() {
        return this.idFamilia;
    }

    public void setIdFamilia(String str) {
        this.idFamilia = str;
    }

    public String getNombreFamilia() {
        return this.nombreFamilia;
    }

    public void setNombreFamilia(String str) {
        this.nombreFamilia = str;
    }

    public String getImagenLista() {
        return this.imagenLista;
    }

    public void setImagenLista(String str) {
        this.imagenLista = str;
    }

    public String getImagenDetalle() {
        return this.imagenDetalle;
    }

    public void setImagenDetalle(String str) {
        this.imagenDetalle = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.idFamilia);
        parcel.writeValue(this.nombreFamilia);
        parcel.writeValue(this.imagenLista);
        parcel.writeValue(this.imagenDetalle);
    }
}
