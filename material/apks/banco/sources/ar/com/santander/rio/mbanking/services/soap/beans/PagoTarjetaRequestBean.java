package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class PagoTarjetaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    public PagoTarjetaBodyRequestBean pagoTarjetaBodyRequestBean;

    public PagoTarjetaRequestBean() {
        this.headerBean = new HeaderBean();
        this.pagoTarjetaBodyRequestBean = new PagoTarjetaBodyRequestBean();
    }

    public PagoTarjetaRequestBean(PagoTarjetaBodyRequestBean pagoTarjetaBodyRequestBean2) {
        this.pagoTarjetaBodyRequestBean = pagoTarjetaBodyRequestBean2;
    }

    public PagoTarjetaRequestBean(HeaderBean headerBean2, PagoTarjetaBodyRequestBean pagoTarjetaBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.pagoTarjetaBodyRequestBean = pagoTarjetaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
