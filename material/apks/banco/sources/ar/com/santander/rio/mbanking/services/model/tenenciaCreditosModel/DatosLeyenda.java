package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosLeyenda implements Parcelable {
    public static final Creator<DatosLeyenda> CREATOR = new Creator<DatosLeyenda>() {
        public DatosLeyenda createFromParcel(Parcel parcel) {
            return new DatosLeyenda(parcel);
        }

        public DatosLeyenda[] newArray(int i) {
            return new DatosLeyenda[i];
        }
    };
    private String descripcion;
    @SerializedName("idLeyenda")
    private String idleyenda;
    private String titulo;

    public int describeContents() {
        return 0;
    }

    protected DatosLeyenda(Parcel parcel) {
        this.idleyenda = parcel.readString();
        this.titulo = parcel.readString();
        this.descripcion = parcel.readString();
    }

    public void setIdleyenda(String str) {
        this.idleyenda = str;
    }

    public String getIdleyenda() {
        return this.idleyenda;
    }

    public void setTitulo(String str) {
        this.titulo = str;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idleyenda);
        parcel.writeString(this.titulo);
        parcel.writeString(this.descripcion);
    }
}
