package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimularPagosBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacionCredito")
    @Expose
    private String cotizacionCredito;
    @SerializedName("importeCuota")
    @Expose
    private String importeCuota;
    @SerializedName("res")
    @Expose
    private String res;

    public String getRes() {
        return this.res;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getImporteCuota() {
        return this.importeCuota;
    }

    public void setImporteCuota(String str) {
        this.importeCuota = str;
    }

    public String getCotizacionCredito() {
        return this.cotizacionCredito;
    }

    public void setCotizacionCredito(String str) {
        this.cotizacionCredito = str;
    }
}
