package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DatosCuentasDestBSRBean implements Parcelable {
    public static final Creator<DatosCuentasDestBSRBean> CREATOR = new Creator<DatosCuentasDestBSRBean>() {
        public DatosCuentasDestBSRBean createFromParcel(Parcel parcel) {
            return new DatosCuentasDestBSRBean(parcel);
        }

        public DatosCuentasDestBSRBean[] newArray(int i) {
            return new DatosCuentasDestBSRBean[i];
        }
    };
    @SerializedName("alias")
    private String alias;
    @SerializedName("cbuDestino")
    private String cbuDestino;
    @SerializedName("descCtaDestinoBSR")
    private String descCtaDestinoBSR;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("descripcionModificada")
    private String descripcionModificada;
    @SerializedName("diasAgValido")
    private String diasAgValido;
    @SerializedName("email")
    private String email;
    @SerializedName("idMoneda")
    private String idMoneda;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nombreCuenta")
    private String nombreCuenta;
    @SerializedName("nombreTitular")
    private String nombreTitular;
    @SerializedName("numero")
    private String numero;
    @SerializedName("sucursal")
    private String sucursal;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("tipoDescripcion")
    private String tipoDescripcion;
    @SerializedName("tipoDestino")
    private String tipoDestino;

    public int describeContents() {
        return 0;
    }

    public DatosCuentasDestBSRBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16) {
        this.tipoDestino = str;
        this.tipo = str2;
        this.numero = str3;
        this.sucursal = str4;
        this.diasAgValido = str5;
        this.moneda = str6;
        this.idMoneda = str7;
        this.tipoDescripcion = str8;
        this.descCtaDestinoBSR = str9;
        this.descripcion = str10;
        this.nombreTitular = str11;
        this.nombreCuenta = str12;
        this.email = str13;
        this.cbuDestino = str14;
        this.descripcionModificada = str15;
        this.alias = str16;
    }

    public DatosCuentasDestBSRBean() {
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public String getDiasAgValido() {
        return this.diasAgValido;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public String getIdMoneda() {
        return this.idMoneda;
    }

    public String getTipoDescripcion() {
        return this.tipoDescripcion;
    }

    public String getDescCtaDestinoBSR() {
        return this.descCtaDestinoBSR;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getNombreTitular() {
        return this.nombreTitular;
    }

    public String getNombreCuenta() {
        return this.nombreCuenta;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCbuDestino() {
        return this.cbuDestino;
    }

    public String getDescripcionModificada() {
        return this.descripcionModificada;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setTipoDestino(String str) {
        this.tipoDestino = str;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public void setSucursal(String str) {
        this.sucursal = str;
    }

    public void setDiasAgValido(String str) {
        this.diasAgValido = str;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public void setIdMoneda(String str) {
        this.idMoneda = str;
    }

    public void setTipoDescripcion(String str) {
        this.tipoDescripcion = str;
    }

    public void setDescCtaDestinoBSR(String str) {
        this.descCtaDestinoBSR = str;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public void setNombreTitular(String str) {
        this.nombreTitular = str;
    }

    public void setNombreCuenta(String str) {
        this.nombreCuenta = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void setCbuDestino(String str) {
        this.cbuDestino = str;
    }

    public void setDescripcionModificada(String str) {
        this.descripcionModificada = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipoDestino);
        parcel.writeString(this.tipo);
        parcel.writeString(this.numero);
        parcel.writeString(this.sucursal);
        parcel.writeString(this.diasAgValido);
        parcel.writeString(this.moneda);
        parcel.writeString(this.idMoneda);
        parcel.writeString(this.tipoDescripcion);
        parcel.writeString(this.descCtaDestinoBSR);
        parcel.writeString(this.descripcion);
        parcel.writeString(this.nombreTitular);
        parcel.writeString(this.nombreCuenta);
        parcel.writeString(this.email);
        parcel.writeString(this.cbuDestino);
        parcel.writeString(this.descripcionModificada);
        parcel.writeString(this.alias);
    }

    private DatosCuentasDestBSRBean(Parcel parcel) {
        this.tipoDestino = parcel.readString();
        this.tipo = parcel.readString();
        this.numero = parcel.readString();
        this.sucursal = parcel.readString();
        this.diasAgValido = parcel.readString();
        this.moneda = parcel.readString();
        this.idMoneda = parcel.readString();
        this.tipoDescripcion = parcel.readString();
        this.descCtaDestinoBSR = parcel.readString();
        this.descripcion = parcel.readString();
        this.nombreTitular = parcel.readString();
        this.nombreCuenta = parcel.readString();
        this.email = parcel.readString();
        this.cbuDestino = parcel.readString();
        this.descripcionModificada = parcel.readString();
        this.alias = parcel.readString();
    }
}
