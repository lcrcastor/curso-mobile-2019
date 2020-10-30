package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultarTitularCuentaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private ConsultarTitularCuentaBodyRequestBean consultarTitularCuentaBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public ConsultarTitularCuentaRequestBean() {
    }

    public ConsultarTitularCuentaRequestBean(PrivateHeaderBean privateHeaderBean, ConsultarTitularCuentaBodyRequestBean consultarTitularCuentaBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.consultarTitularCuentaBodyRequestBean = consultarTitularCuentaBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public ConsultarTitularCuentaBodyRequestBean getConsultarTitularCuentaBodyRequestBean() {
        return this.consultarTitularCuentaBodyRequestBean;
    }

    public void setConsultarTitularCuentaBodyRequestBean(ConsultarTitularCuentaBodyRequestBean consultarTitularCuentaBodyRequestBean2) {
        this.consultarTitularCuentaBodyRequestBean = consultarTitularCuentaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
