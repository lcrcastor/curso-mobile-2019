package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuentaCompradorBean implements Parcelable {
    public static final Creator<CuentaCompradorBean> CREATOR = new Creator<CuentaCompradorBean>() {
        public CuentaCompradorBean createFromParcel(Parcel parcel) {
            return new CuentaCompradorBean(parcel);
        }

        public CuentaCompradorBean[] newArray(int i) {
            return new CuentaCompradorBean[i];
        }
    };
    @SerializedName("adheridaDebin")
    private String adheridaDebin;
    @SerializedName("alias")
    private String alias;
    @SerializedName("banco")
    private String banco;
    @SerializedName("cbu")
    private String cbu;
    @SerializedName("numero")
    private String numero;
    @SerializedName("numeroCuenta")
    private String numeroCuenta;
    @SerializedName("saldo")
    private String saldo;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoCuenta")
    private String tipoCuenta;

    public int describeContents() {
        return 0;
    }

    public CuentaCompradorBean() {
    }

    public CuentaCompradorBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.saldo = str4;
        this.adheridaDebin = str5;
        this.numeroCuenta = str6;
        this.tipoCuenta = str7;
        this.cbu = str8;
        this.banco = str9;
        this.alias = str10;
    }

    protected CuentaCompradorBean(Parcel parcel) {
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.saldo = parcel.readString();
        this.adheridaDebin = parcel.readString();
        this.numeroCuenta = parcel.readString();
        this.tipoCuenta = parcel.readString();
        this.cbu = parcel.readString();
        this.banco = parcel.readString();
        this.alias = parcel.readString();
    }

    public void updateCuentaMigradas(CtaMigradaBean ctaMigradaBean) {
        this.sucursal = ctaMigradaBean.getSucursal();
        this.numero = ctaMigradaBean.getNumero();
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getSaldo() {
        return this.saldo;
    }

    public void setSaldo(String str) {
        this.saldo = str;
    }

    public String getAdheridaDebin() {
        return this.adheridaDebin;
    }

    public void setAdheridaDebin(String str) {
        this.adheridaDebin = str;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public void setNumeroCuenta(String str) {
        this.numeroCuenta = str;
    }

    public String getTipoCuenta() {
        return this.tipoCuenta;
    }

    public void setTipoCuenta(String str) {
        this.tipoCuenta = str;
    }

    public String getCbu() {
        return this.cbu;
    }

    public void setCbu(String str) {
        this.cbu = str;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String str) {
        this.banco = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeString(this.saldo);
        parcel.writeString(this.adheridaDebin);
        parcel.writeString(this.numeroCuenta);
        parcel.writeString(this.tipoCuenta);
        parcel.writeString(this.cbu);
        parcel.writeString(this.banco);
        parcel.writeString(this.alias);
    }
}
