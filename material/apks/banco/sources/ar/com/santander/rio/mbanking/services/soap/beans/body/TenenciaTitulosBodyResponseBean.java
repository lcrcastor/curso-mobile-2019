package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TenenciaTitulosBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<TenenciaTitulosBodyResponseBean> CREATOR = new Creator<TenenciaTitulosBodyResponseBean>() {
        public TenenciaTitulosBodyResponseBean createFromParcel(Parcel parcel) {
            return new TenenciaTitulosBodyResponseBean(parcel);
        }

        public TenenciaTitulosBodyResponseBean[] newArray(int i) {
            return new TenenciaTitulosBodyResponseBean[i];
        }
    };
    private Cuenta cuenta;
    private ListaLeyendas listaLeyendas;
    private String msjeError;

    public int describeContents() {
        return 0;
    }

    protected TenenciaTitulosBodyResponseBean(Parcel parcel) {
        super(parcel);
        this.cuenta = (Cuenta) parcel.readParcelable(Cuenta.class.getClassLoader());
        this.listaLeyendas = (ListaLeyendas) parcel.readParcelable(ListaLeyendas.class.getClassLoader());
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta2) {
        this.cuenta = cuenta2;
    }

    public ListaLeyendas getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyendas listaLeyendas2) {
        this.listaLeyendas = listaLeyendas2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.cuenta, i);
        parcel.writeParcelable(this.listaLeyendas, i);
    }

    public String getMsjeError() {
        return this.msjeError;
    }

    public void setMsjeError(String str) {
        this.msjeError = str;
    }
}
