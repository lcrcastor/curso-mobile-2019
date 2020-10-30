package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class Leyenda implements Parcelable {
    public static final Creator<Leyenda> CREATOR = new Creator<Leyenda>() {
        public Leyenda createFromParcel(Parcel parcel) {
            return new Leyenda(parcel);
        }

        public Leyenda[] newArray(int i) {
            return new Leyenda[i];
        }
    };
    @SerializedName("descLeyenda")
    String descLeyenda;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("idLeyenda")
    String idLeyenda;

    public int describeContents() {
        return 0;
    }

    public Leyenda(String str) {
        this.idLeyenda = str;
    }

    protected Leyenda(Parcel parcel) {
        this.idLeyenda = parcel.readString();
        this.descripcion = parcel.readString();
        this.descLeyenda = parcel.readString();
    }

    public String getIdLeyenda() {
        return this.idLeyenda;
    }

    public void setIdLeyenda(String str) {
        this.idLeyenda = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getDescLeyenda() {
        return this.descLeyenda;
    }

    public void setDescLeyenda(String str) {
        this.descLeyenda = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idLeyenda);
        parcel.writeString(this.descLeyenda);
        parcel.writeString(this.descripcion);
    }
}
