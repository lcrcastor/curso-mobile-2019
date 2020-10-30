package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultarAdhesionVendedorResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultarAdhesionVendedorResponseBean() {
    }

    public ConsultarAdhesionVendedorResponseBean(PrivateHeaderBean privateHeaderBean, ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultarAdhesionVendedorBodyResponseBean = consultarAdhesionVendedorBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public ConsultarAdhesionVendedorBodyResponseBean getConsultarAdhesionVendedorBodyResponseBean() {
        return this.consultarAdhesionVendedorBodyResponseBean;
    }

    public void setConsultarAdhesionVendedorBodyResponseBean(ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean2) {
        this.consultarAdhesionVendedorBodyResponseBean = consultarAdhesionVendedorBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.consultarAdhesionVendedorBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
