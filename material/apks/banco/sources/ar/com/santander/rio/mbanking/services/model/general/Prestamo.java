package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prestamo {
    @SerializedName("clase")
    @Expose
    private String clase;
    @SerializedName("clasePaquete")
    @Expose
    private String clasePaquete;
    @SerializedName("claveBancariaUnificada")
    @Expose
    private String claveBancariaUnificada;
    @SerializedName("codPaquete")
    @Expose
    private String codPaquete;
    @SerializedName("codigoTitularidad")
    @Expose
    private String codigoTitularidad;
    @SerializedName("estadoTarjetaCredito")
    @Expose
    private String estadoTarjetaCredito;
    @SerializedName("indJerarquia")
    @Expose
    private String indJerarquia;
    @SerializedName("nroPaq")
    @Expose
    private String nroPaq;
    @SerializedName("nroSuc")
    @Expose
    private String nroSuc;
    @SerializedName("nroTarjetaCredito")
    @Expose
    private String nroTarjetaCredito;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("sucursalPaq")
    @Expose
    private String sucursalPaq;
    @SerializedName("tipo")
    @Expose
    private String tipo;

    public Prestamo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        this.nroTarjetaCredito = str;
        this.nroSuc = str2;
        this.indJerarquia = str3;
        this.clasePaquete = str4;
        this.tipo = str5;
        this.estadoTarjetaCredito = str6;
        this.clase = str7;
        this.codigoTitularidad = str8;
        this.nroPaq = str9;
        this.sucursalPaq = str10;
        this.claveBancariaUnificada = str11;
        this.numero = str12;
        this.codPaquete = str13;
    }

    public String getNroTarjetaCredito() {
        return this.nroTarjetaCredito;
    }

    public void setNroTarjetaCredito(String str) {
        this.nroTarjetaCredito = str;
    }

    public String getNroSuc() {
        return this.nroSuc;
    }

    public void setNroSuc(String str) {
        this.nroSuc = str;
    }

    public String getIndJerarquia() {
        return this.indJerarquia;
    }

    public void setIndJerarquia(String str) {
        this.indJerarquia = str;
    }

    public String getClasePaquete() {
        return this.clasePaquete;
    }

    public void setClasePaquete(String str) {
        this.clasePaquete = str;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getEstadoTarjetaCredito() {
        return this.estadoTarjetaCredito;
    }

    public void setEstadoTarjetaCredito(String str) {
        this.estadoTarjetaCredito = str;
    }

    public String getClase() {
        return this.clase;
    }

    public void setClase(String str) {
        this.clase = str;
    }

    public String getCodigoTitularidad() {
        return this.codigoTitularidad;
    }

    public void setCodigoTitularidad(String str) {
        this.codigoTitularidad = str;
    }

    public String getNroPaq() {
        return this.nroPaq;
    }

    public void setNroPaq(String str) {
        this.nroPaq = str;
    }

    public String getSucursalPaq() {
        return this.sucursalPaq;
    }

    public void setSucursalPaq(String str) {
        this.sucursalPaq = str;
    }

    public String getClaveBancariaUnificada() {
        return this.claveBancariaUnificada;
    }

    public void setClaveBancariaUnificada(String str) {
        this.claveBancariaUnificada = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getCodPaquete() {
        return this.codPaquete;
    }

    public void setCodPaquete(String str) {
        this.codPaquete = str;
    }
}
