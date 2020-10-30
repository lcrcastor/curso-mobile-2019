package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class VendedorBean implements Parcelable {
    public static final Creator<VendedorBean> CREATOR = new Creator<VendedorBean>() {
        public VendedorBean createFromParcel(Parcel parcel) {
            return new VendedorBean(parcel);
        }

        public VendedorBean[] newArray(int i) {
            return new VendedorBean[i];
        }
    };
    @SerializedName("cuentaVendedor")
    private CuentaVendedor cuentaVendedor;
    @SerializedName("cuit")
    private String cuit = "";
    @SerializedName("titular")
    private String titular = "";

    public int describeContents() {
        return 0;
    }

    public VendedorBean() {
    }

    public VendedorBean(String str, String str2, CuentaVendedor cuentaVendedor2) {
        this.titular = str;
        this.cuit = str2;
        this.cuentaVendedor = cuentaVendedor2;
    }

    protected VendedorBean(Parcel parcel) {
        this.titular = parcel.readString();
        this.cuit = parcel.readString();
        this.cuentaVendedor = (CuentaVendedor) parcel.readParcelable(CuentaVendedor.class.getClassLoader());
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String str) {
        this.cuit = str;
    }

    public CuentaVendedor getCuentaVendedor() {
        return this.cuentaVendedor;
    }

    public void setCuentaVendedor(CuentaVendedor cuentaVendedor2) {
        this.cuentaVendedor = cuentaVendedor2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.titular);
        parcel.writeString(this.cuit);
        parcel.writeParcelable(this.cuentaVendedor, i);
    }
}
