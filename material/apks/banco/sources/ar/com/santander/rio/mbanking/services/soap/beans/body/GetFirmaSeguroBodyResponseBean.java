package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetFirmaSeguroBodyResponseBean extends ErrorBodyBean {
    @SerializedName("firmaSeguros")
    private FirmaSeguroBean firmaSeguros;

    public GetFirmaSeguroBodyResponseBean() {
    }

    public GetFirmaSeguroBodyResponseBean(FirmaSeguroBean firmaSeguroBean) {
        this.firmaSeguros = firmaSeguroBean;
    }

    public FirmaSeguroBean getFirmaSeguros() {
        return this.firmaSeguros;
    }

    public void setFirmaSeguros(FirmaSeguroBean firmaSeguroBean) {
        this.firmaSeguros = firmaSeguroBean;
    }
}
