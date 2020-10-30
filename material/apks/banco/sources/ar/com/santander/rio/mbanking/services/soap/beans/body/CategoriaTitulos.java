package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CategoriaTitulos implements Parcelable {
    public static final Creator<CategoriaTitulos> CREATOR = new Creator<CategoriaTitulos>() {
        public CategoriaTitulos createFromParcel(Parcel parcel) {
            return new CategoriaTitulos(parcel);
        }

        public CategoriaTitulos[] newArray(int i) {
            return new CategoriaTitulos[i];
        }
    };
    private String idCategoria;
    private String nombreCategoria;
    private Titulos titulos;

    public int describeContents() {
        return 0;
    }

    protected CategoriaTitulos(Parcel parcel) {
        this.idCategoria = parcel.readString();
        this.nombreCategoria = parcel.readString();
        this.titulos = (Titulos) parcel.readParcelable(Titulos.class.getClassLoader());
    }

    public String getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(String str) {
        this.idCategoria = str;
    }

    public String getNombreCategoria() {
        return this.nombreCategoria;
    }

    public void setNombreCategoria(String str) {
        this.nombreCategoria = str;
    }

    public Titulos getTitulos() {
        return this.titulos;
    }

    public void setTitulos(Titulos titulos2) {
        this.titulos = titulos2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idCategoria);
        parcel.writeString(this.nombreCategoria);
        parcel.writeParcelable(this.titulos, i);
    }
}
