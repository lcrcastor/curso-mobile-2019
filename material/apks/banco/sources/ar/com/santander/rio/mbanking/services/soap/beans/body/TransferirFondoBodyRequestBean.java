package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferirFondoBodyRequestBean {
    @SerializedName("alertaRiesgo")
    private String alertaRiesgo;
    @SerializedName("cantCuotaOrigen")
    private String cantCuotaOrigen;
    @SerializedName("comisionDestino")
    private String comisionDestino;
    @SerializedName("comisionOrigen")
    private String comisionOrigen;
    @SerializedName("idEvaluacionRiesgo")
    private String idEvaluacionRiesgo;
    @SerializedName("idFondoDestino")
    private String idFondoDestino;
    @SerializedName("idFondoOrigen")
    private String idFondoOrigen;
    @SerializedName("importeTransferencia")
    private String importeTransferir;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("nroCtaTitulo")
    private String nroCtaTitulo;
    @SerializedName("sucCtaTitulo")
    private String sucCtaTitulo;
    @SerializedName("tipoCtaTitulo")
    private String tipoCtaTitulo;

    public TransferirFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        this.importeTransferir = str;
        this.moneda = str2;
        this.idFondoOrigen = str3;
        this.idFondoDestino = str4;
        this.tipoCtaTitulo = str5;
        this.sucCtaTitulo = str6;
        this.nroCtaTitulo = str7;
        this.comisionOrigen = str8;
        this.comisionDestino = str9;
        this.cantCuotaOrigen = str10;
        this.alertaRiesgo = str11;
        this.idEvaluacionRiesgo = str12;
    }

    public TransferirFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.tipoCtaTitulo = str;
        this.sucCtaTitulo = str2;
        this.nroCtaTitulo = str3;
        this.importeTransferir = str4;
        this.moneda = str5;
        this.idFondoOrigen = str6;
        this.idFondoDestino = str7;
    }

    public String getImporteTransferir() {
        return this.importeTransferir;
    }

    public void setImporteTransferir(String str) {
        this.importeTransferir = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getIdFondoOrigen() {
        return this.idFondoOrigen;
    }

    public void setIdFondoOrigen(String str) {
        this.idFondoOrigen = str;
    }

    public String getIdFondoDestino() {
        return this.idFondoDestino;
    }

    public void setIdFondoDestino(String str) {
        this.idFondoDestino = str;
    }

    public String getTipoCtaTitulo() {
        return this.tipoCtaTitulo;
    }

    public void setTipoCtaTitulo(String str) {
        this.tipoCtaTitulo = str;
    }

    public String getSucCtaTitulo() {
        return this.sucCtaTitulo;
    }

    public void setSucCtaTitulo(String str) {
        this.sucCtaTitulo = str;
    }

    public String getNroCtaTitulo() {
        return this.nroCtaTitulo;
    }

    public void setNroCtaTitulo(String str) {
        this.nroCtaTitulo = str;
    }

    public String getComisionOrigen() {
        return this.comisionOrigen;
    }

    public void setComisionOrigen(String str) {
        this.comisionOrigen = str;
    }

    public String getComisionDestino() {
        return this.comisionDestino;
    }

    public void setComisionDestino(String str) {
        this.comisionDestino = str;
    }

    public String getCantCuotaOrigen() {
        return this.cantCuotaOrigen;
    }

    public void setCantCuotaOrigen(String str) {
        this.cantCuotaOrigen = str;
    }

    public String getAlertaRiesgo() {
        return this.alertaRiesgo;
    }

    public void setAlertaRiesgo(String str) {
        this.alertaRiesgo = str;
    }

    public String getIdEvaluacionRiesgo() {
        return this.idEvaluacionRiesgo;
    }

    public void setIdEvaluacionRiesgo(String str) {
        this.idEvaluacionRiesgo = str;
    }
}
