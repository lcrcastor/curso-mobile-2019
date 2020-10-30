package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Producto implements Parcelable {
    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        public Producto createFromParcel(Parcel parcel) {
            return new Producto(parcel);
        }

        public Producto[] newArray(int i) {
            return new Producto[i];
        }
    };
    private String codProducto;
    private String descripcion;
    private String importeDolares;
    private String importePesos;

    public int describeContents() {
        return 0;
    }

    protected Producto(Parcel parcel) {
        this.descripcion = parcel.readString();
        this.importePesos = parcel.readString();
        this.codProducto = parcel.readString();
        this.importeDolares = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.descripcion);
        parcel.writeString(this.importePesos);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.importeDolares);
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getImportePesos() {
        return this.importePesos;
    }

    public void setImportePesos(String str) {
        this.importePesos = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }
}
