package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmPreautorizacionCompradorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<AbmPreautorizacionCompradorBodyResponseBean> CREATOR = new Creator<AbmPreautorizacionCompradorBodyResponseBean>() {
        public AbmPreautorizacionCompradorBodyResponseBean createFromParcel(Parcel parcel) {
            return new AbmPreautorizacionCompradorBodyResponseBean(parcel);
        }

        public AbmPreautorizacionCompradorBodyResponseBean[] newArray(int i) {
            return new AbmPreautorizacionCompradorBodyResponseBean[i];
        }
    };
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("leyendaComp")
    private String leyendaComp;
    @SerializedName("nroComprobante")
    private String nroComprobante;

    public int describeContents() {
        return 0;
    }

    protected AbmPreautorizacionCompradorBodyResponseBean(Parcel parcel) {
        this.res = (String) parcel.readValue(String.class.getClassLoader());
        this.nroComprobante = (String) parcel.readValue(String.class.getClassLoader());
        this.fechaOperacion = (String) parcel.readValue(String.class.getClassLoader());
        this.leyendaComp = (String) parcel.readValue(String.class.getClassLoader());
    }

    public AbmPreautorizacionCompradorBodyResponseBean() {
    }

    public AbmPreautorizacionCompradorBodyResponseBean(String str, String str2, String str3, String str4) {
        this.res = str;
        this.nroComprobante = str2;
        this.fechaOperacion = str3;
        this.leyendaComp = str4;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public AbmPreautorizacionCompradorBodyResponseBean withRes(String str) {
        this.res = str;
        return this;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public AbmPreautorizacionCompradorBodyResponseBean withNroComprobante(String str) {
        this.nroComprobante = str;
        return this;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public AbmPreautorizacionCompradorBodyResponseBean withFechaOperacion(String str) {
        this.fechaOperacion = str;
        return this;
    }

    public String getLeyendaComp() {
        return this.leyendaComp;
    }

    public void setLeyendaComp(String str) {
        this.leyendaComp = str;
    }

    public AbmPreautorizacionCompradorBodyResponseBean withLeyendaComp(String str) {
        this.leyendaComp = str;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.res);
        parcel.writeValue(this.nroComprobante);
        parcel.writeValue(this.fechaOperacion);
        parcel.writeValue(this.leyendaComp);
    }
}
