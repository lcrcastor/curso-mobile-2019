package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class PagoTarjetaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public HeaderBean headerBean;
    @SerializedName("body")
    private PagoTarjetaBodyResponseBean pagoTarjetaBodyResponseBean;

    public Class getClassBean() {
        return null;
    }

    public Object getErrorBeanObject() {
        return null;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public PagoTarjetaBodyResponseBean getPagoTarjetaBodyResponseBean() {
        return this.pagoTarjetaBodyResponseBean;
    }

    public void setPagoTarjetaBodyResponseBean(PagoTarjetaBodyResponseBean pagoTarjetaBodyResponseBean2) {
        this.pagoTarjetaBodyResponseBean = pagoTarjetaBodyResponseBean2;
    }
}
