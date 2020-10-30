package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CancelaMandatoExtEnvBodyResponseBean extends ErrorBodyBean {
    @SerializedName("comprobante")
    public ComprobanteCancelacionMandatoBean comprobante;

    public CancelaMandatoExtEnvBodyResponseBean() {
    }

    public CancelaMandatoExtEnvBodyResponseBean(ComprobanteCancelacionMandatoBean comprobanteCancelacionMandatoBean) {
        this.comprobante = comprobanteCancelacionMandatoBean;
    }
}
