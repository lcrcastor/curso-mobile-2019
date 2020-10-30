package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuentaRecargaBean implements Parcelable {
    public static final Creator<CuentaRecargaBean> CREATOR = new Creator<CuentaRecargaBean>() {
        public CuentaRecargaBean createFromParcel(Parcel parcel) {
            return new CuentaRecargaBean(parcel);
        }

        public CuentaRecargaBean[] newArray(int i) {
            return new CuentaRecargaBean[i];
        }
    };
    @SerializedName("descripcionCuenta")
    private String descripcionCuenta;
    @SerializedName("numeroCuenta")
    private String numero;
    @SerializedName("sucursalCuenta")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;

    public int describeContents() {
        return 0;
    }

    public CuentaRecargaBean() {
    }

    protected CuentaRecargaBean(Parcel parcel) {
        this.sucursal = parcel.readString();
        this.tipo = parcel.readString();
        this.numero = parcel.readString();
        this.descripcionCuenta = parcel.readString();
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getDescripcionCuenta() {
        return this.descripcionCuenta;
    }

    public void setDescripcionCuenta(String str) {
        this.descripcionCuenta = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sucursal);
        parcel.writeString(this.tipo);
        parcel.writeString(this.numero);
        parcel.writeString(this.descripcionCuenta);
    }

    public String toString() {
        return this.descripcionCuenta;
    }
}
