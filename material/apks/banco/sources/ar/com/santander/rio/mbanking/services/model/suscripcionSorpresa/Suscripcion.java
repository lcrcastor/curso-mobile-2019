package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class Suscripcion {
    @SerializedName("codOperacion")
    String codOperacion;
    @SerializedName("codigoDAP")
    String codigoDAP;
    @SerializedName("codigoFrecuencia")
    String codigoFrecuencia;
    @SerializedName("dapDefault")
    String dapDefault;
    @SerializedName("dapVinculada")
    String dapVinculada;
    @SerializedName("destinoVinculado")
    DestinoVinculado destinoVinculado;
    @SerializedName("frecuenciaDefault")
    String frecuenciaDefault;
    @SerializedName("frecuenciaVinculada")
    String frecuenciaVinculada;
    @SerializedName("_id")

    /* renamed from: id reason: collision with root package name */
    String f252id;
    @SerializedName("nroMensaje")
    String nroMensaje;
    @SerializedName("nroSuscripcion")
    String nroSuscripcion;
    @SerializedName("suscripto")
    String suscripto;

    public String getNroSuscripcion() {
        return this.nroSuscripcion;
    }

    public void setNroSuscripcion(String str) {
        this.nroSuscripcion = str;
    }

    public String getFrecuenciaVinculada() {
        return this.frecuenciaVinculada;
    }

    public void setFrecuenciaVinculada(String str) {
        this.frecuenciaVinculada = str;
    }

    public String getDapVinculada() {
        return this.dapVinculada;
    }

    public void setDapVinculada(String str) {
        this.dapVinculada = str;
    }

    public DestinoVinculado getDestinoVinculado() {
        return this.destinoVinculado;
    }

    public void setDestinoVinculado(DestinoVinculado destinoVinculado2) {
        this.destinoVinculado = destinoVinculado2;
    }

    public String getCodOperacion() {
        return this.codOperacion;
    }

    public void setCodOperacion(String str) {
        this.codOperacion = str;
    }

    public String getNroMensaje() {
        return this.nroMensaje;
    }

    public void setNroMensaje(String str) {
        this.nroMensaje = str;
    }

    public String getCodigoFrecuencia() {
        return this.codigoFrecuencia;
    }

    public void setCodigoFrecuencia(String str) {
        this.codigoFrecuencia = str;
    }

    public String getCodigoDAP() {
        return this.codigoDAP;
    }

    public void setCodigoDAP(String str) {
        this.codigoDAP = str;
    }

    public String getId() {
        return this.f252id;
    }

    public void setId(String str) {
        this.f252id = str;
    }

    public String getFrecuenciaDefault() {
        return this.frecuenciaDefault;
    }

    public void setFrecuenciaDefault(String str) {
        this.frecuenciaDefault = str;
    }

    public String getDapDefault() {
        return this.dapDefault;
    }

    public void setDapDefault(String str) {
        this.dapDefault = str;
    }

    public String getSuscripto() {
        return this.suscripto;
    }

    public void setSuscripto(String str) {
        this.suscripto = str;
    }
}
