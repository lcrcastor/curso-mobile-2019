package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class MovimientoFondosBean implements Parcelable {
    public static final Creator<MovimientoFondosBean> CREATOR = new Creator<MovimientoFondosBean>() {
        public MovimientoFondosBean createFromParcel(Parcel parcel) {
            return new MovimientoFondosBean(parcel);
        }

        public MovimientoFondosBean[] newArray(int i) {
            return new MovimientoFondosBean[i];
        }
    };
    @SerializedName("certificado")
    private String certificado;
    @SerializedName("concepto")
    private String concepto;
    @SerializedName("cotizacion")
    private String cotizacion;
    @SerializedName("cuotaPartes")
    private String cuotapartes;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("importe")
    private String importe;

    public int describeContents() {
        return 0;
    }

    public MovimientoFondosBean() {
    }

    public MovimientoFondosBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.fecha = str;
        this.concepto = str2;
        this.importe = str3;
        this.certificado = str4;
        this.cuotapartes = str5;
        this.cotizacion = str6;
    }

    protected MovimientoFondosBean(Parcel parcel) {
        this.fecha = parcel.readString();
        this.concepto = parcel.readString();
        this.importe = parcel.readString();
        this.certificado = parcel.readString();
        this.cuotapartes = parcel.readString();
        this.cotizacion = parcel.readString();
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getConcepto() {
        return this.concepto;
    }

    public void setConcepto(String str) {
        this.concepto = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getCertificado() {
        return this.certificado;
    }

    public void setCertificado(String str) {
        this.certificado = str;
    }

    public String getCuotapartes() {
        return this.cuotapartes;
    }

    public void setCuotapartes(String str) {
        this.cuotapartes = str;
    }

    public String getCotizacion() {
        return this.cotizacion;
    }

    public void setCotizacion(String str) {
        this.cotizacion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fecha);
        parcel.writeString(this.concepto);
        parcel.writeString(this.importe);
        parcel.writeString(this.certificado);
        parcel.writeString(this.cuotapartes);
        parcel.writeString(this.cotizacion);
    }
}
