package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaDatosInicialesTransfBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cargaInfoTransf")
    private CargaInfoTransfBean cargaInfoTransfBean;

    public ConsultaDatosInicialesTransfBodyResponseBean(CargaInfoTransfBean cargaInfoTransfBean2) {
        this.cargaInfoTransfBean = cargaInfoTransfBean2;
    }

    public CargaInfoTransfBean getCargaInfoTransfBean() {
        return this.cargaInfoTransfBean;
    }
}
