package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ConsultarTitularCuentaBodyResponseBean extends ErrorBodyBean implements Parcelable {
    public static final Creator<ConsultarTitularCuentaBodyResponseBean> CREATOR = new Creator<ConsultarTitularCuentaBodyResponseBean>() {
        public ConsultarTitularCuentaBodyResponseBean createFromParcel(Parcel parcel) {
            return new ConsultarTitularCuentaBodyResponseBean(parcel);
        }

        public ConsultarTitularCuentaBodyResponseBean[] newArray(int i) {
            return new ConsultarTitularCuentaBodyResponseBean[i];
        }
    };
    @SerializedName("banco")
    private String banco;
    @SerializedName("cbu")
    private String cbu;
    @SerializedName("codMonedaOperacion")
    private String codMonedaOperacion;
    @SerializedName("cuit")
    private String cuit;
    @SerializedName("numeroCuenta")
    private String numeroCuenta;
    @SerializedName("tipoCuenta")
    private String tipoCuenta;
    @SerializedName("titular")
    private String titular;

    public int describeContents() {
        return 0;
    }

    public ConsultarTitularCuentaBodyResponseBean() {
    }

    public ConsultarTitularCuentaBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.titular = str;
        this.cuit = str2;
        this.numeroCuenta = str3;
        this.tipoCuenta = str4;
        this.cbu = str5;
        this.banco = str6;
        this.codMonedaOperacion = str7;
    }

    protected ConsultarTitularCuentaBodyResponseBean(Parcel parcel) {
        this.titular = parcel.readString();
        this.cuit = parcel.readString();
        this.numeroCuenta = parcel.readString();
        this.tipoCuenta = parcel.readString();
        this.cbu = parcel.readString();
        this.banco = parcel.readString();
        this.codMonedaOperacion = parcel.readString();
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String str) {
        this.titular = str;
    }

    public String getCuit() {
        return this.cuit;
    }

    public void setCuit(String str) {
        this.cuit = str;
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

    public String getCodMonedaOperacion() {
        return this.codMonedaOperacion;
    }

    public void setCodMonedaOperacion(String str) {
        this.codMonedaOperacion = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.titular);
        parcel.writeString(this.cuit);
        parcel.writeString(this.numeroCuenta);
        parcel.writeString(this.tipoCuenta);
        parcel.writeString(this.cbu);
        parcel.writeString(this.banco);
        parcel.writeString(this.codMonedaOperacion);
    }
}
