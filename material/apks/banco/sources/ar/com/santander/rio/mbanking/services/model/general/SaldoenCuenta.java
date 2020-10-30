package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class SaldoenCuenta {
    @SerializedName("limites")
    private Limites limites;
    @SerializedName("limitesUnificados")
    private String limitesUnificados;

    public SaldoenCuenta(Limites limites2) {
        this.limites = limites2;
    }

    public SaldoenCuenta(String str, Limites limites2) {
        this.limitesUnificados = str;
        this.limites = limites2;
    }

    public SaldoenCuenta() {
    }

    public String getLimitesUnificados() {
        return this.limitesUnificados;
    }

    public void setLimitesUnificados(String str) {
        this.limitesUnificados = str;
    }

    public Limites getLimites() {
        return this.limites;
    }

    public void setLimites(Limites limites2) {
        this.limites = limites2;
    }
}
