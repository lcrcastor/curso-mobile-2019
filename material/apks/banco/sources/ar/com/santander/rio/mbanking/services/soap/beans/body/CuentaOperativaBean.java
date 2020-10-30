package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuentaOperativaBean implements Parcelable {
    public static final Creator<CuentaOperativaBean> CREATOR = new Creator<CuentaOperativaBean>() {
        public CuentaOperativaBean createFromParcel(Parcel parcel) {
            return new CuentaOperativaBean(parcel);
        }

        public CuentaOperativaBean[] newArray(int i) {
            return new CuentaOperativaBean[i];
        }
    };
    @SerializedName("descCtaDebito")
    public String descCtaDebito;
    @SerializedName("descCtaDestino")
    public String descCtaDestino;
    @SerializedName("idMoneda")
    public String idMoneda;
    @SerializedName("moneda")
    public String moneda;
    @SerializedName("numero")
    public String numero;
    @SerializedName("sucursal")
    public String sucursal;
    @SerializedName("tipoCta")
    public String tipoCta;
    @SerializedName("tipoDescripcion")
    public String tipoDescripcion;

    public int describeContents() {
        return 0;
    }

    public CuentaOperativaBean() {
    }

    public CuentaOperativaBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.tipoCta = str;
        this.descCtaDebito = str2;
        this.descCtaDestino = str3;
        this.numero = str4;
        this.sucursal = str5;
        this.moneda = str6;
        this.idMoneda = str7;
        this.tipoDescripcion = str8;
    }

    protected CuentaOperativaBean(Parcel parcel) {
        this.tipoCta = parcel.readString();
        this.descCtaDebito = parcel.readString();
        this.descCtaDestino = parcel.readString();
        this.numero = parcel.readString();
        this.sucursal = parcel.readString();
        this.moneda = parcel.readString();
        this.idMoneda = parcel.readString();
        this.tipoDescripcion = parcel.readString();
    }

    public String getTipoCta() {
        return this.tipoCta;
    }

    public void setTipoCta(String str) {
        this.tipoCta = str;
    }

    public String getDescCtaDebito() {
        return this.descCtaDebito;
    }

    public void setDescCtaDebito(String str) {
        this.descCtaDebito = str;
    }

    public String getDescCtaDestino() {
        return this.descCtaDestino;
    }

    public void setDescCtaDestino(String str) {
        this.descCtaDestino = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getIdMoneda() {
        return this.idMoneda;
    }

    public void setIdMoneda(String str) {
        this.idMoneda = str;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public void setTipoDescripcion(String str) {
        this.tipoDescripcion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoCta);
        parcel.writeString(this.descCtaDebito);
        parcel.writeString(this.descCtaDestino);
        parcel.writeString(this.numero);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.moneda);
        parcel.writeString(this.idMoneda);
        parcel.writeString(this.tipoDescripcion);
    }
}
