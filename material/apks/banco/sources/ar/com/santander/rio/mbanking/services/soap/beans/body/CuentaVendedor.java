package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class CuentaVendedor implements Parcelable {
    public static final Creator<CuentaVendedor> CREATOR = new Creator<CuentaVendedor>() {
        public CuentaVendedor createFromParcel(Parcel parcel) {
            return new CuentaVendedor(parcel);
        }

        public CuentaVendedor[] newArray(int i) {
            return new CuentaVendedor[i];
        }
    };
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
    private Integer statusAdhesion = Integer.valueOf(0);
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoCuenta")
    private String tipoCuenta;

    public int describeContents() {
        return 0;
    }

    public CuentaVendedor() {
    }

    public CuentaVendedor(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i) {
        this.numeroCuenta = str;
        this.tipoCuenta = str2;
        this.cbu = str3;
        this.alias = str4;
        this.banco = str5;
        this.tipo = str6;
        this.sucursal = str7;
        this.numero = str8;
        this.statusAdhesion = Integer.valueOf(i);
    }

    protected CuentaVendedor(Parcel parcel) {
        this.numeroCuenta = parcel.readString();
        this.tipoCuenta = parcel.readString();
        this.cbu = parcel.readString();
        this.alias = parcel.readString();
        this.banco = parcel.readString();
        this.tipo = parcel.readString();
        this.sucursal = parcel.readString();
        this.numero = parcel.readString();
        this.statusAdhesion = Integer.valueOf(parcel.readInt());
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

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String str) {
        this.banco = str;
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

    public Integer getStatusAdhesion() {
        return this.statusAdhesion;
    }

    public void setStatusAdhesion(Integer num) {
        this.statusAdhesion = num;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.numeroCuenta);
        parcel.writeString(this.tipoCuenta);
        parcel.writeString(this.cbu);
        parcel.writeString(this.alias);
        parcel.writeString(this.banco);
        parcel.writeString(this.tipo);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.numero);
        parcel.writeInt(this.statusAdhesion.intValue());
    }
}
