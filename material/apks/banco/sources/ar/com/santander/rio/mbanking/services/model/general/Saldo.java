package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Saldo {
    @SerializedName("total")
    private String total;
    @SerializedName("usdTotal")
    private String usdTotal;

    public Saldo(String str, String str2) {
        this.total = str;
        this.usdTotal = str2;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String str) {
        this.total = str;
    }

    public String getUsdTotal() {
        return this.usdTotal;
    }

    public void setUsdTotal(String str) {
        this.usdTotal = str;
    }
}
