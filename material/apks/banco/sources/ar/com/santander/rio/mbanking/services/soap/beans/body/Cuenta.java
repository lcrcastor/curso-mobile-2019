package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.Html;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;

public class Cuenta implements Parcelable {
    public static final Creator<Cuenta> CREATOR = new Creator<Cuenta>() {
        public Cuenta createFromParcel(Parcel parcel) {
            return new Cuenta(parcel);
        }

        public Cuenta[] newArray(int i) {
            return new Cuenta[i];
        }
    };
    private String intervinientes;
    private String mensajeCta;
    private String nroCta;
    private Moneda otraMoneda;
    private Moneda pesos;
    private Productos productos;
    private TenReexpresada tenReexpresada;
    private TitulosValores titulosValores;
    private String totalDolares;
    private String totalPesos;

    public int describeContents() {
        return 0;
    }

    public Cuenta(String str, String str2, String str3, String str4) {
        this.nroCta = str;
        this.intervinientes = str2;
        this.totalPesos = str3;
        this.totalDolares = str4;
    }

    protected Cuenta(Parcel parcel) {
        this.nroCta = parcel.readString();
        this.mensajeCta = parcel.readString();
        this.intervinientes = parcel.readString();
        this.totalPesos = parcel.readString();
        this.totalDolares = parcel.readString();
        this.pesos = (Moneda) parcel.readParcelable(Moneda.class.getClassLoader());
        this.otraMoneda = (Moneda) parcel.readParcelable(Moneda.class.getClassLoader());
    }

    public String getNroCta() {
        return Html.fromHtml(this.nroCta).toString();
    }

    public void setNroCta(String str) {
        this.nroCta = str;
    }

    public String getMensajeCta() {
        return this.mensajeCta;
    }

    public void setMensajeCta(String str) {
        this.mensajeCta = str;
    }

    public String getIntervinientes() {
        return this.intervinientes;
    }

    public void setIntervinientes(String str) {
        this.intervinientes = str;
    }

    public String getTotalPesos() {
        return this.totalPesos;
    }

    public void setTotalPesos(String str) {
        this.totalPesos = str;
    }

    public String getTotalDolares() {
        return this.totalDolares;
    }

    public void setTotalDolares(String str) {
        this.totalDolares = str;
    }

    public Productos getProductos() {
        return this.productos;
    }

    public void setProductos(Productos productos2) {
        this.productos = productos2;
    }

    public TenReexpresada getTenReexpresada() {
        return this.tenReexpresada;
    }

    public void setTenReexpresada(TenReexpresada tenReexpresada2) {
        this.tenReexpresada = tenReexpresada2;
    }

    public TitulosValores getTitulosValores() {
        return this.titulosValores;
    }

    public void setTitulosValores(TitulosValores titulosValores2) {
        this.titulosValores = titulosValores2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroCta);
        parcel.writeString(this.mensajeCta);
        parcel.writeString(this.intervinientes);
        parcel.writeString(this.totalPesos);
        parcel.writeString(this.totalDolares);
        parcel.writeParcelable(this.pesos, i);
        parcel.writeParcelable(this.otraMoneda, i);
    }

    public int getCuenta() {
        return CuentasUtils.getCuentaFromString(this.nroCta);
    }

    public int getSucInt() {
        return CuentasUtils.getSucCuentaFromString(this.nroCta);
    }

    public Moneda getOtraMoneda() {
        return this.otraMoneda;
    }

    public void setOtraMoneda(Moneda moneda) {
        this.otraMoneda = moneda;
    }

    public Moneda getPesos() {
        return this.pesos;
    }

    public void setPesos(Moneda moneda) {
        this.pesos = moneda;
    }
}
