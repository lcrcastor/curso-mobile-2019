package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class SolicitudLimiteTransfBodyRequestBean implements Parcelable {
    public static final Creator<SolicitudLimiteTransfBodyRequestBean> CREATOR = new Creator<SolicitudLimiteTransfBodyRequestBean>() {
        public SolicitudLimiteTransfBodyRequestBean createFromParcel(Parcel parcel) {
            return new SolicitudLimiteTransfBodyRequestBean(parcel);
        }

        public SolicitudLimiteTransfBodyRequestBean[] newArray(int i) {
            return new SolicitudLimiteTransfBodyRequestBean[i];
        }
    };
    @SerializedName("fechaTransf")
    private String fechaTransf;
    @SerializedName("importeTransf")
    private String importeTransf;
    @SerializedName("monedaImporte")
    private String monedaImporte;
    @SerializedName("nroCtaDestino")
    private String nroCtaDestino;
    @SerializedName("nroCtaOrigen")
    private String nroCtaOrigen;
    @SerializedName("sucCtaDestino")
    private String sucCtaDestino;
    @SerializedName("sucCtaOrigen")
    private String sucCtaOrigen;
    @SerializedName("tipoCtaDestino")
    private String tipoCtaDestino;
    @SerializedName("tipoCtaOrigen")
    private String tipoCtaOrigen;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public int describeContents() {
        return 0;
    }

    public SolicitudLimiteTransfBodyRequestBean() {
    }

    public SolicitudLimiteTransfBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.tipoCtaOrigen = str;
        this.sucCtaOrigen = str2;
        this.nroCtaOrigen = str3;
        this.importeTransf = str4;
        this.monedaImporte = str5;
        this.tipoDestino = str6;
        this.tipoCtaDestino = str7;
        this.sucCtaDestino = str8;
        this.nroCtaDestino = str9;
        this.fechaTransf = str10;
    }

    protected SolicitudLimiteTransfBodyRequestBean(Parcel parcel) {
        this.tipoCtaOrigen = parcel.readString();
        this.sucCtaOrigen = parcel.readString();
        this.nroCtaOrigen = parcel.readString();
        this.importeTransf = parcel.readString();
        this.monedaImporte = parcel.readString();
        this.tipoDestino = parcel.readString();
        this.tipoCtaDestino = parcel.readString();
        this.sucCtaDestino = parcel.readString();
        this.nroCtaDestino = parcel.readString();
        this.fechaTransf = parcel.readString();
    }

    public String getTipoCtaOrigen() {
        return this.tipoCtaOrigen;
    }

    public void setTipoCtaOrigen(String str) {
        this.tipoCtaOrigen = str;
    }

    public String getSucCtaOrigen() {
        return this.sucCtaOrigen;
    }

    public void setSucCtaOrigen(String str) {
        this.sucCtaOrigen = str;
    }

    public String getNroCtaOrigen() {
        return this.nroCtaOrigen;
    }

    public void setNroCtaOrigen(String str) {
        this.nroCtaOrigen = str;
    }

    public String getImporteTransf() {
        return this.importeTransf;
    }

    public void setImporteTransf(String str) {
        this.importeTransf = str;
    }

    public String getMonedaImporte() {
        return this.monedaImporte;
    }

    public void setMonedaImporte(String str) {
        this.monedaImporte = str;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public void setTipoDestino(String str) {
        this.tipoDestino = str;
    }

    public String getTipoCtaDestino() {
        return this.tipoCtaDestino;
    }

    public void setTipoCtaDestino(String str) {
        this.tipoCtaDestino = str;
    }

    public String getSucCtaDestino() {
        return this.sucCtaDestino;
    }

    public void setSucCtaDestino(String str) {
        this.sucCtaDestino = str;
    }

    public String getNroCtaDestino() {
        return this.nroCtaDestino;
    }

    public void setNroCtaDestino(String str) {
        this.nroCtaDestino = str;
    }

    public String getFechaTransf() {
        return this.fechaTransf;
    }

    public void setFechaTransf(String str) {
        this.fechaTransf = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoCtaOrigen);
        parcel.writeString(this.sucCtaOrigen);
        parcel.writeString(this.nroCtaOrigen);
        parcel.writeString(this.importeTransf);
        parcel.writeString(this.monedaImporte);
        parcel.writeString(this.tipoDestino);
        parcel.writeString(this.tipoCtaDestino);
        parcel.writeString(this.sucCtaDestino);
        parcel.writeString(this.nroCtaDestino);
        parcel.writeString(this.fechaTransf);
    }
}
