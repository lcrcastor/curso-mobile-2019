package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class DatosPrestamosProd {
    @SerializedName("maxImpPrest")
    String maxImpPrest;
    @SerializedName("minImpPrest")
    String minImpPrest;
    @SerializedName("valorDivisa")
    String valorDivisa;

    public String getMinImpPrest() {
        return this.minImpPrest;
    }

    public void setMinImpPrest(String str) {
        this.minImpPrest = str;
    }

    public String getMaxImpPrest() {
        return this.maxImpPrest;
    }

    public void setMaxImpPrest(String str) {
        this.maxImpPrest = str;
    }

    public String getValorDivisa() {
        return this.valorDivisa;
    }

    public void setValorDivisa(String str) {
        this.valorDivisa = str;
    }
}
