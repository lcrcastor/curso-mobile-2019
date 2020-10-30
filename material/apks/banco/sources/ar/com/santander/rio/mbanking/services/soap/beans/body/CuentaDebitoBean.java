package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;

public class CuentaDebitoBean implements Parcelable {
    public static final Creator<CuentaDebitoBean> CREATOR = new Creator<CuentaDebitoBean>() {
        public CuentaDebitoBean createFromParcel(Parcel parcel) {
            return new CuentaDebitoBean(parcel);
        }

        public CuentaDebitoBean[] newArray(int i) {
            return new CuentaDebitoBean[i];
        }
    };
    public String descCtaDebito;
    public String numero;
    public String sucursal;
    public String tipo;

    public int describeContents() {
        return 0;
    }

    public CuentaDebitoBean() {
    }

    public CuentaDebitoBean(String str, String str2, String str3, String str4) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.descCtaDebito = str4;
    }

    public CuentaDebitoBean(Cuenta cuenta) {
        this.tipo = cuenta.getTipo();
        this.sucursal = cuenta.getNroSuc();
        this.numero = cuenta.getNumero();
    }

    protected CuentaDebitoBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.descCtaDebito = parcel.readString();
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getDescCtaDebito() {
        return this.descCtaDebito;
    }

    public void setDescCtaDebito(String str) {
        this.descCtaDebito = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.descCtaDebito);
    }
}
