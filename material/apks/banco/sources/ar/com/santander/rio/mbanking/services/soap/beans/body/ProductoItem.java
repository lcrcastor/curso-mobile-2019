package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProductoItem implements Parcelable {
    public static final Creator<ProductoItem> CREATOR = new Creator<ProductoItem>() {
        public ProductoItem createFromParcel(Parcel parcel) {
            return new ProductoItem(parcel);
        }

        public ProductoItem[] newArray(int i) {
            return new ProductoItem[i];
        }
    };
    private String codProducto;
    private String codSubProducto;
    private String idPaquete;
    private String idProducto;
    private String nombreProducto;
    private String nroCtaProducto;
    private String sucProducto;

    public int describeContents() {
        return 0;
    }

    public ProductoItem(Parcel parcel) {
        this.idProducto = parcel.readString();
        this.nombreProducto = parcel.readString();
        this.idPaquete = parcel.readString();
        this.codProducto = parcel.readString();
        this.codSubProducto = parcel.readString();
        this.sucProducto = parcel.readString();
        this.nroCtaProducto = parcel.readString();
    }

    public ProductoItem() {
    }

    public String getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(String str) {
        this.idProducto = str;
    }

    public String getNombreProducto() {
        return this.nombreProducto;
    }

    public void setNombreProducto(String str) {
        this.nombreProducto = str;
    }

    public String getIdPaquete() {
        return this.idPaquete;
    }

    public void setIdPaquete(String str) {
        this.idPaquete = str;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String str) {
        this.codProducto = str;
    }

    public String getCodSubProducto() {
        return this.codSubProducto;
    }

    public void setCodSubProducto(String str) {
        this.codSubProducto = str;
    }

    public String getSucProducto() {
        return this.sucProducto;
    }

    public void setSucProducto(String str) {
        this.sucProducto = str;
    }

    public String getNroCtaProducto() {
        return this.nroCtaProducto;
    }

    public void setNroCtaProducto(String str) {
        this.nroCtaProducto = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idProducto);
        parcel.writeString(this.nombreProducto);
        parcel.writeString(this.idPaquete);
        parcel.writeString(this.codProducto);
        parcel.writeString(this.codSubProducto);
        parcel.writeString(this.sucProducto);
        parcel.writeString(this.nroCtaProducto);
    }
}
