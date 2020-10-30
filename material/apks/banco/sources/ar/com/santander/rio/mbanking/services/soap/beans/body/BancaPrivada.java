package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BancaPrivada implements Parcelable {
    public static final Creator<BancaPrivada> CREATOR = new Creator<BancaPrivada>() {
        public BancaPrivada createFromParcel(Parcel parcel) {
            return new BancaPrivada(parcel);
        }

        public BancaPrivada[] newArray(int i) {
            return new BancaPrivada[i];
        }
    };
    private AgrupadorCuenta agrupadorCuenta;
    private String msjeError;
    private TenConsolidada tenConsolidada;

    public int describeContents() {
        return 0;
    }

    protected BancaPrivada(Parcel parcel) {
        this.msjeError = parcel.readString();
        this.tenConsolidada = (TenConsolidada) parcel.readParcelable(TenConsolidada.class.getClassLoader());
        this.agrupadorCuenta = (AgrupadorCuenta) parcel.readParcelable(AgrupadorCuenta.class.getClassLoader());
    }

    public String getMsjeError() {
        return this.msjeError;
    }

    public void setMsjeError(String str) {
        this.msjeError = str;
    }

    public TenConsolidada getTenConsolidada() {
        return this.tenConsolidada;
    }

    public void setTenConsolidada(TenConsolidada tenConsolidada2) {
        this.tenConsolidada = tenConsolidada2;
    }

    public AgrupadorCuenta getAgrupadorCuenta() {
        return this.agrupadorCuenta;
    }

    public void setAgrupadorCuenta(AgrupadorCuenta agrupadorCuenta2) {
        this.agrupadorCuenta = agrupadorCuenta2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.msjeError);
        parcel.writeParcelable(this.tenConsolidada, i);
        parcel.writeParcelable(this.agrupadorCuenta, i);
    }
}
