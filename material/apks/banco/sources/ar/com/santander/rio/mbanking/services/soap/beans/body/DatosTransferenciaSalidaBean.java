package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosTransferenciaSalidaBean implements Parcelable {
    public static final Creator<DatosTransferenciaSalidaBean> CREATOR = new Creator<DatosTransferenciaSalidaBean>() {
        public DatosTransferenciaSalidaBean createFromParcel(Parcel parcel) {
            return new DatosTransferenciaSalidaBean(parcel);
        }

        public DatosTransferenciaSalidaBean[] newArray(int i) {
            return new DatosTransferenciaSalidaBean[i];
        }
    };
    @SerializedName("ctaDestino")
    private String ctaDestino;
    @SerializedName("cuentaOrigen")
    private String cuentaOrigen;
    @SerializedName("fechaTransferencia")
    private String fechaTransferencia;
    @SerializedName("importeOrigen")
    private String importeOrigen;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nroComprobante")
    private String nroComprobante;
    @SerializedName("nroComprobanteAltaDest")
    private String nroComprobanteAltaDest;
    @SerializedName("plazoAcreditacion")
    private String plazoAcreditacion;

    public int describeContents() {
        return 0;
    }

    public DatosTransferenciaSalidaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.nroComprobante = str;
        this.importeOrigen = str2;
        this.fechaTransferencia = str3;
        this.moneda = str4;
        this.ctaDestino = str5;
        this.cuentaOrigen = str6;
        this.nroComprobanteAltaDest = str7;
        this.plazoAcreditacion = str8;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public String getImporteOrigen() {
        return this.importeOrigen;
    }

    public String getFechaTransferencia() {
        return this.fechaTransferencia;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public String getCtaDestino() {
        return this.ctaDestino;
    }

    public String getCuentaOrigen() {
        return this.cuentaOrigen;
    }

    public String getNroComprobanteAltaDest() {
        return this.nroComprobanteAltaDest;
    }

    public String getPlazoAcreditacion() {
        return this.plazoAcreditacion;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
        parcel.writeString(this.importeOrigen);
        parcel.writeString(this.fechaTransferencia);
        parcel.writeString(this.moneda);
        parcel.writeString(this.ctaDestino);
        parcel.writeString(this.cuentaOrigen);
        parcel.writeString(this.nroComprobanteAltaDest);
        parcel.writeString(this.plazoAcreditacion);
    }

    private DatosTransferenciaSalidaBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
        this.importeOrigen = parcel.readString();
        this.fechaTransferencia = parcel.readString();
        this.moneda = parcel.readString();
        this.ctaDestino = parcel.readString();
        this.cuentaOrigen = parcel.readString();
        this.nroComprobanteAltaDest = parcel.readString();
        this.plazoAcreditacion = parcel.readString();
    }
}
