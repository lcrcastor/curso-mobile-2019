package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetSegurosBodyResponseBean extends ErrorBodyBean {
    @SerializedName("seguros")
    private SegurosBean seguros;

    public GetSegurosBodyResponseBean() {
    }

    public GetSegurosBodyResponseBean(SegurosBean segurosBean) {
        this.seguros = segurosBean;
    }

    public SegurosBean getSeguros() {
        return this.seguros;
    }

    public void setSeguros(SegurosBean segurosBean) {
        this.seguros = segurosBean;
    }
}
