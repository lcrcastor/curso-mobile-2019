package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TenReexpresada implements Parcelable {
    public static final Creator<TenReexpresada> CREATOR = new Creator<TenReexpresada>() {
        public TenReexpresada createFromParcel(Parcel parcel) {
            return new TenReexpresada(parcel);
        }

        public TenReexpresada[] newArray(int i) {
            return new TenReexpresada[i];
        }
    };
    private Dolar dolar;
    private Dolar dolares;
    private Pesos pesos;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    protected TenReexpresada(Parcel parcel) {
    }

    public Pesos getPesos() {
        return this.pesos;
    }

    public void setPesos(Pesos pesos2) {
        this.pesos = pesos2;
    }

    public Dolar getDolar() {
        return this.dolar;
    }

    public void setDolar(Dolar dolar2) {
        this.dolar = dolar2;
    }

    public Dolar getDolares() {
        return this.dolares;
    }

    public void setDolares(Dolar dolar2) {
        this.dolares = dolar2;
    }
}
