package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferenciaDestinoFondosBean {
    @SerializedName("fondoDestino")
    private String fondoDestino;
    @SerializedName("idFondoDestino")
    private String idFondoDestino;
    @SerializedName("monedaDestino")
    private String moneda;

    public TransferenciaDestinoFondosBean() {
    }

    public TransferenciaDestinoFondosBean(String str, String str2, String str3) {
        this.idFondoDestino = str;
        this.fondoDestino = str2;
        this.moneda = str3;
    }

    public String getIdFondoDestino() {
        return this.idFondoDestino;
    }

    public void setIdFondoDestino(String str) {
        this.idFondoDestino = str;
    }

    public String getFondoDestino() {
        return this.fondoDestino;
    }

    public void setFondoDestino(String str) {
        this.fondoDestino = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }
}
