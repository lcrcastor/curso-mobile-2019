package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarTitularCuentaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultarTitularCuentaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public ConsultarTitularCuentaResponseBean() {
    }

    public ConsultarTitularCuentaResponseBean(PrivateHeaderBean privateHeaderBean, ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean2) {
        this.headerBean = privateHeaderBean;
        this.consultarTitularCuentaBodyResponseBean = consultarTitularCuentaBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public ConsultarTitularCuentaBodyResponseBean getConsultarTitularCuentaBodyResponseBean() {
        return this.consultarTitularCuentaBodyResponseBean;
    }

    public void setConsultarTitularCuentaBodyResponseBean(ConsultarTitularCuentaBodyResponseBean consultarTitularCuentaBodyResponseBean2) {
        this.consultarTitularCuentaBodyResponseBean = consultarTitularCuentaBodyResponseBean2;
    }

    public Object getErrorBeanObject() {
        return this.consultarTitularCuentaBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }
}
