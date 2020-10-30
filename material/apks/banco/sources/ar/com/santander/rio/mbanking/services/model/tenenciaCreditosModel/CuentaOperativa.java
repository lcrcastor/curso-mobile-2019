package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuentaOperativa implements Parcelable {
    public static final Creator<CuentaOperativa> CREATOR = new Creator<CuentaOperativa>() {
        public CuentaOperativa createFromParcel(Parcel parcel) {
            return new CuentaOperativa(parcel);
        }

        public CuentaOperativa[] newArray(int i) {
            return new CuentaOperativa[i];
        }
    };
    @SerializedName("descCtaDebito")
    private String descCtaDebito;
    @SerializedName("descCtaDestino")
    private String descCtaDestino;
    @SerializedName("idMoneda")
    private String idMoneda;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipoCta")
    private String tipoCta;
    @SerializedName("tipoDescripcion")
    private String tipoDescripcion;

    public int describeContents() {
        return 0;
    }

    protected CuentaOperativa(Parcel parcel) {
        this.tipoCta = (String) parcel.readValue(String.class.getClassLoader());
        this.descCtaDebito = (String) parcel.readValue(String.class.getClassLoader());
        this.descCtaDestino = (String) parcel.readValue(String.class.getClassLoader());
        this.numero = (String) parcel.readValue(String.class.getClassLoader());
        this.sucursal = (String) parcel.readValue(String.class.getClassLoader());
        this.moneda = (String) parcel.readValue(String.class.getClassLoader());
        this.idMoneda = (String) parcel.readValue(String.class.getClassLoader());
        this.tipoDescripcion = (String) parcel.readValue(String.class.getClassLoader());
    }

    public CuentaOperativa() {
    }

    public CuentaOperativa(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.tipoCta = str;
        this.descCtaDebito = str2;
        this.descCtaDestino = str3;
        this.numero = str4;
        this.sucursal = str5;
        this.moneda = str6;
        this.idMoneda = str7;
        this.tipoDescripcion = str8;
    }

    public String getTipoCta() {
        return this.tipoCta;
    }

    public void setTipoCta(String str) {
        this.tipoCta = str;
    }

    public CuentaOperativa withTipoCta(String str) {
        this.tipoCta = str;
        return this;
    }

    public String getDescCtaDebito() {
        return this.descCtaDebito;
    }

    public void setDescCtaDebito(String str) {
        this.descCtaDebito = str;
    }

    public CuentaOperativa withDescCtaDebito(String str) {
        this.descCtaDebito = str;
        return this;
    }

    public String getDescCtaDestino() {
        return this.descCtaDestino;
    }

    public void setDescCtaDestino(String str) {
        this.descCtaDestino = str;
    }

    public CuentaOperativa withDescCtaDestino(String str) {
        this.descCtaDestino = str;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public CuentaOperativa withNumero(String str) {
        this.numero = str;
        return this;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public CuentaOperativa withSucursal(String str) {
        this.sucursal = str;
        return this;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public CuentaOperativa withMoneda(String str) {
        this.moneda = str;
        return this;
    }

    public String getIdMoneda() {
        return this.idMoneda;
    }

    public void setIdMoneda(String str) {
        this.idMoneda = str;
    }

    public CuentaOperativa withIdMoneda(String str) {
        this.idMoneda = str;
        return this;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public void setTipoDescripcion(String str) {
        this.tipoDescripcion = str;
    }

    public CuentaOperativa withTipoDescripcion(String str) {
        this.tipoDescripcion = str;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.tipoCta);
        parcel.writeValue(this.descCtaDebito);
        parcel.writeValue(this.descCtaDestino);
        parcel.writeValue(this.numero);
        parcel.writeValue(this.sucursal);
        parcel.writeValue(this.moneda);
        parcel.writeValue(this.idMoneda);
        parcel.writeValue(this.tipoDescripcion);
    }
}
