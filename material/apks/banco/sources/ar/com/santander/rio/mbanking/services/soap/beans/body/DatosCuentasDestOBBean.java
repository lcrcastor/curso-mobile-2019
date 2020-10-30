package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosCuentasDestOBBean implements Parcelable {
    public static final Creator<DatosCuentasDestOBBean> CREATOR = new Creator<DatosCuentasDestOBBean>() {
        public DatosCuentasDestOBBean createFromParcel(Parcel parcel) {
            return new DatosCuentasDestOBBean(parcel);
        }

        public DatosCuentasDestOBBean[] newArray(int i) {
            return new DatosCuentasDestOBBean[i];
        }
    };
    @SerializedName("alias")
    private String alias;
    @SerializedName("banco")
    private String banco;
    @SerializedName("bancoDestino")
    private String bancoDestino;
    @SerializedName("beneficiario")
    private String beneficiario;
    @SerializedName("caracteristica")
    private String caracteristica;
    @SerializedName("cbu")
    private String cbu;
    @SerializedName("cbuDestino")
    private String cbuDestino;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("descripcionModificada")
    private String descripcionModificada;
    @SerializedName("diasAgValido")
    private String diasAgValido;
    @SerializedName("diferido")
    private String diferido;
    @SerializedName("email")
    private String email;
    @SerializedName("nombreTitular")
    private String nombreTitular;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public int describeContents() {
        return 0;
    }

    public DatosCuentasDestOBBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.tipoDestino = str;
        this.cbu = str2;
        this.beneficiario = str3;
        this.diasAgValido = str4;
        this.caracteristica = str5;
        this.descripcion = str6;
        this.nombreTitular = str7;
        this.banco = str8;
        this.bancoDestino = str9;
        this.email = str10;
        this.cbuDestino = str11;
        this.descripcionModificada = str12;
        this.alias = str13;
        this.diferido = str14;
    }

    public DatosCuentasDestOBBean() {
    }

    private DatosCuentasDestOBBean(Parcel parcel) {
        this.tipoDestino = parcel.readString();
        this.cbu = parcel.readString();
        this.beneficiario = parcel.readString();
        this.diasAgValido = parcel.readString();
        this.caracteristica = parcel.readString();
        this.descripcion = parcel.readString();
        this.nombreTitular = parcel.readString();
        this.banco = parcel.readString();
        this.bancoDestino = parcel.readString();
        this.email = parcel.readString();
        this.cbuDestino = parcel.readString();
        this.descripcionModificada = parcel.readString();
        this.alias = parcel.readString();
        this.diferido = parcel.readString();
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public void setTipoDestino(String str) {
        this.tipoDestino = str;
    }

    public String getCbu() {
        return this.cbu;
    }

    public void setCbu(String str) {
        this.cbu = str;
    }

    public String getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(String str) {
        this.beneficiario = str;
    }

    public String getDiasAgValido() {
        return this.diasAgValido;
    }

    public void setDiasAgValido(String str) {
        this.diasAgValido = str;
    }

    public String getCaracteristica() {
        return this.caracteristica;
    }

    public void setCaracteristica(String str) {
        this.caracteristica = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getNombreTitular() {
        return this.nombreTitular;
    }

    public void setNombreTitular(String str) {
        this.nombreTitular = str;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String str) {
        this.banco = str;
    }

    public String getBancoDestino() {
        return this.bancoDestino;
    }

    public void setBancoDestino(String str) {
        this.bancoDestino = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getCbuDestino() {
        return this.cbuDestino;
    }

    public void setCbuDestino(String str) {
        this.cbuDestino = str;
    }

    public String getDescripcionModificada() {
        return this.descripcionModificada;
    }

    public void setDescripcionModificada(String str) {
        this.descripcionModificada = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public String getDiferido() {
        return this.diferido;
    }

    public void setDiferido(String str) {
        this.diferido = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoDestino);
        parcel.writeString(this.cbu);
        parcel.writeString(this.beneficiario);
        parcel.writeString(this.diasAgValido);
        parcel.writeString(this.caracteristica);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.nombreTitular);
        parcel.writeString(this.banco);
        parcel.writeString(this.bancoDestino);
        parcel.writeString(this.email);
        parcel.writeString(this.cbuDestino);
        parcel.writeString(this.descripcionModificada);
        parcel.writeString(this.alias);
        parcel.writeString(this.diferido);
    }
}
