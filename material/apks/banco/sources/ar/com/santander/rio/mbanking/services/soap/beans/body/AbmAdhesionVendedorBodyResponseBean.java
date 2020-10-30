package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmAdhesionVendedorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<AbmAdhesionVendedorBodyResponseBean> CREATOR = new Creator<AbmAdhesionVendedorBodyResponseBean>() {
        public AbmAdhesionVendedorBodyResponseBean createFromParcel(Parcel parcel) {
            return new AbmAdhesionVendedorBodyResponseBean(parcel);
        }

        public AbmAdhesionVendedorBodyResponseBean[] newArray(int i) {
            return new AbmAdhesionVendedorBodyResponseBean[i];
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

    public AbmAdhesionVendedorBodyResponseBean() {
    }

    public AbmAdhesionVendedorBodyResponseBean(String str, String str2, String str3) {
        this.nroComprobante = str;
        this.fechaOperacion = str2;
        this.leyendaComp = str3;
    }

    protected AbmAdhesionVendedorBodyResponseBean(Parcel parcel) {
        this.nroComprobante = parcel.readString();
        this.fechaOperacion = parcel.readString();
        this.leyendaComp = parcel.readString();
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public void setNroComprobante(String str) {
        this.nroComprobante = str;
    }

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public String getLeyendaComp() {
        return this.leyendaComp;
    }

    public void setLeyendaComp(String str) {
        this.leyendaComp = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nroComprobante);
        parcel.writeString(this.fechaOperacion);
        parcel.writeString(this.leyendaComp);
    }
}
