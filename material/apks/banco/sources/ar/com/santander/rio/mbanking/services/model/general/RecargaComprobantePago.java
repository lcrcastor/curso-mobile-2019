package ar.com.santander.rio.mbanking.services.model.general;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RecargaComprobantePago implements Parcelable {
    public static final Creator<RecargaComprobantePago> CREATOR = new Creator<RecargaComprobantePago>() {
        public RecargaComprobantePago createFromParcel(Parcel parcel) {
            return new RecargaComprobantePago(parcel);
        }

        public RecargaComprobantePago[] newArray(int i) {
            return new RecargaComprobantePago[i];
        }
    };
    private String fecha;
    private String medioPago;
    private String monto;
    private String nombreTarjeta;
    private String nroComprobante;
    private String nroTarjeta;

    public int describeContents() {
        return 0;
    }

    public RecargaComprobantePago(String str, String str2, String str3, String str4, String str5, String str6) {
        this.monto = str;
        this.nroTarjeta = str2;
        this.nombreTarjeta = str3;
        this.medioPago = str4;
        this.fecha = str5;
        this.nroComprobante = str6;
    }

    protected RecargaComprobantePago(Parcel parcel) {
        this.monto = parcel.readString();
        this.medioPago = parcel.readString();
        this.fecha = parcel.readString();
        this.nroComprobante = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.monto);
        parcel.writeString(this.medioPago);
        parcel.writeString(this.fecha);
        parcel.writeString(this.nroComprobante);
    }

    public String getMonto() {
        return this.monto;
    }

    public void setMonto(String str) {
        this.monto = str;
    }

    public String getNroTarjeta() {
        return this.nroTarjeta;
    }

    public void setNroTarjeta(String str) {
        this.nroTarjeta = str;
    }

    public String getNombreTarjeta() {
        return this.nombreTarjeta;
    }

    public void setNombreTarjeta(String str) {
        this.nombreTarjeta = str;
    }

    public String getMedioPago() {
        return this.medioPago;
    }

    public void setMedioPago(String str) {
        this.medioPago = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }
}
