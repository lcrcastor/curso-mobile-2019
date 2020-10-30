package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmAdhesionVendedorBodyRequestBean implements Parcelable {
    public static final Creator<AbmAdhesionVendedorBodyRequestBean> CREATOR = new Creator<AbmAdhesionVendedorBodyRequestBean>() {
        public AbmAdhesionVendedorBodyRequestBean createFromParcel(Parcel parcel) {
            return new AbmAdhesionVendedorBodyRequestBean(parcel);
        }

        public AbmAdhesionVendedorBodyRequestBean[] newArray(int i) {
            return new AbmAdhesionVendedorBodyRequestBean[i];
        }
    };
    @SerializedName("cuentaVendedor")
    private CuentaVendedor cuentaVendedor;
    @SerializedName("tipoOperacion")
    private String tipoOeracion;

    public int describeContents() {
        return 0;
    }

    public AbmAdhesionVendedorBodyRequestBean() {
    }

    public AbmAdhesionVendedorBodyRequestBean(String str, CuentaVendedor cuentaVendedor2) {
        this.tipoOeracion = str;
        this.cuentaVendedor = cuentaVendedor2;
    }

    protected AbmAdhesionVendedorBodyRequestBean(Parcel parcel) {
        this.tipoOeracion = parcel.readString();
        this.cuentaVendedor = (CuentaVendedor) parcel.readParcelable(CuentaVendedor.class.getClassLoader());
    }

    public String getTipoOeracion() {
        return this.tipoOeracion;
    }

    public void setTipoOeracion(String str) {
        this.tipoOeracion = str;
    }

    public CuentaVendedor getVendedorBean() {
        return this.cuentaVendedor;
    }

    public void setVendedorBean(CuentaVendedor cuentaVendedor2) {
        this.cuentaVendedor = cuentaVendedor2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoOeracion);
        parcel.writeParcelable(this.cuentaVendedor, i);
    }
}
