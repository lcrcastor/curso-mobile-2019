package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultarAdhesionVendedorRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private ConsultarAdhesionVendedorBodyRequestBean consultarAdhesionVendedorBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public ConsultarAdhesionVendedorRequestBean() {
    }

    public ConsultarAdhesionVendedorRequestBean(PrivateHeaderBean privateHeaderBean, ConsultarAdhesionVendedorBodyRequestBean consultarAdhesionVendedorBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.consultarAdhesionVendedorBodyRequestBean = consultarAdhesionVendedorBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public ConsultarAdhesionVendedorBodyRequestBean getConsultarAdhesionVendedorBodyRequestBean() {
        return this.consultarAdhesionVendedorBodyRequestBean;
    }

    public void setConsultarAdhesionVendedorBodyRequestBean(ConsultarAdhesionVendedorBodyRequestBean consultarAdhesionVendedorBodyRequestBean2) {
        this.consultarAdhesionVendedorBodyRequestBean = consultarAdhesionVendedorBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
