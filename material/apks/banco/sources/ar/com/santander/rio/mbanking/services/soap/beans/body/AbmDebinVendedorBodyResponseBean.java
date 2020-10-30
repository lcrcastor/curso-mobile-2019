package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class AbmDebinVendedorBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<AbmDebinVendedorBodyResponseBean> CREATOR = new Creator<AbmDebinVendedorBodyResponseBean>() {
        public AbmDebinVendedorBodyResponseBean createFromParcel(Parcel parcel) {
            return new AbmDebinVendedorBodyResponseBean(parcel);
        }

        public AbmDebinVendedorBodyResponseBean[] newArray(int i) {
            return new AbmDebinVendedorBodyResponseBean[i];
        }
    };
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("idDebin")
    private String idDebin;
    @SerializedName("leyendaComp")
    private String leyendaComp;
    @SerializedName("nroComprobante")
    private String nroComprobante;

    public int describeContents() {
        return 0;
    }

    public String getIdDebin() {
        return this.idDebin;
    }

    public void setIdDebin(String str) {
        this.idDebin = str;
    }

    public AbmDebinVendedorBodyResponseBean() {
    }

    public AbmDebinVendedorBodyResponseBean(String str, String str2, String str3) {
        this.nroComprobante = str;
        this.fechaOperacion = str2;
        this.leyendaComp = str3;
    }

    protected AbmDebinVendedorBodyResponseBean(Parcel parcel) {
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
