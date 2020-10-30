package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ListDebinesBean implements Parcelable {
    public static final Creator<ListDebinesBean> CREATOR = new Creator<ListDebinesBean>() {
        public ListDebinesBean createFromParcel(Parcel parcel) {
            return new ListDebinesBean(parcel);
        }

        public ListDebinesBean[] newArray(int i) {
            return new ListDebinesBean[i];
        }
    };
    @SerializedName("codEstado")
    private String codEstado;
    @SerializedName("fechaVencimiento")
    private String fechaVencimiento;
    @SerializedName("idDebin")
    private String idDebin;
    @SerializedName("importe")
    private String importe;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("titular")
    private String titular;

    public int describeContents() {
        return 0;
    }

    public ListDebinesBean() {
    }

    public ListDebinesBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.idDebin = str;
        this.codEstado = str2;
        this.importe = str3;
        this.moneda = str4;
        this.titular = str5;
        this.fechaVencimiento = str6;
    }

    protected ListDebinesBean(Parcel parcel) {
        this.idDebin = parcel.readString();
        this.codEstado = parcel.readString();
        this.importe = parcel.readString();
        this.moneda = parcel.readString();
        this.titular = parcel.readString();
        this.fechaVencimiento = parcel.readString();
    }

    public String getIdDebin() {
        return this.idDebin;
    }

    public void setIdDebin(String str) {
        this.idDebin = str;
    }

    public String getCodEstado() {
        return this.codEstado;
    }

    public void setCodEstado(String str) {
        this.codEstado = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String str) {
        this.fechaVencimiento = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.idDebin);
        parcel.writeString(this.codEstado);
        parcel.writeString(this.importe);
        parcel.writeString(this.moneda);
        parcel.writeString(this.titular);
        parcel.writeString(this.fechaVencimiento);
    }
}
