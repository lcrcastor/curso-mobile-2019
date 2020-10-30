package ar.com.santander.rio.mbanking.services.soap.beans.body;

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
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("idLeyenda")
    public String idLeyenda;
    @SerializedName("titulo")
    public String titulo;

    public int describeContents() {
        return 0;
    }

    public Leyenda() {
        this.descripcion = "";
    }

    public Leyenda(String str, String str2, String str3) {
        this.descripcion = "";
        this.idLeyenda = str;
        this.titulo = str2;
        this.descripcion = str3;
    }

    private Leyenda(Parcel parcel) {
        this.descripcion = "";
        this.idLeyenda = parcel.readString();
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
    }

    public static Leyenda LeyendaEmpty() {
        return new Leyenda("", "", "");
    }

    public static Creator<Leyenda> getCREATOR() {
        return CREATOR;
    }

    public String getIdLeyenda() {
        return this.idLeyenda;
    }

    public void setIdLeyenda(String str) {
        this.idLeyenda = str;
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
        parcel.writeString(this.idLeyenda);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }
}
