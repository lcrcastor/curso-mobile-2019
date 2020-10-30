package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ValorRecargaBean implements Parcelable {
    public static final Creator<ValorRecargaBean> CREATOR = new Creator<ValorRecargaBean>() {
        public ValorRecargaBean createFromParcel(Parcel parcel) {
            return new ValorRecargaBean(parcel);
        }

        public ValorRecargaBean[] newArray(int i) {
            return new ValorRecargaBean[i];
        }
    };
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("importe")
    private String importe;
    @SerializedName("moneda")
    private String moneda;

    public int describeContents() {
        return 0;
    }

    public ValorRecargaBean() {
    }

    protected ValorRecargaBean(Parcel parcel) {
        this.moneda = parcel.readString();
        this.importe = parcel.readString();
        this.descripcion = parcel.readString();
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.moneda);
        parcel.writeString(this.importe);
        parcel.writeString(this.descripcion);
    }

    public String toString() {
        return this.descripcion;
    }
}
