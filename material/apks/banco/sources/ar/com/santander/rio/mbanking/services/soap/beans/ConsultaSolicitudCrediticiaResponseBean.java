package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaSolicitudCrediticiaResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public ConsultaSolicitudCrediticiaResponseBean() {
        this.headerBean = new HeaderBean();
        this.consultaSolicitudCrediticiaBodyResponseBean = new ConsultaSolicitudCrediticiaBodyResponseBean();
    }

    public ConsultaSolicitudCrediticiaResponseBean(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean2) {
        this.consultaSolicitudCrediticiaBodyResponseBean = consultaSolicitudCrediticiaBodyResponseBean2;
    }

    public ConsultaSolicitudCrediticiaResponseBean(HeaderBean headerBean2, ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean2) {
        this.headerBean = headerBean2;
        this.consultaSolicitudCrediticiaBodyResponseBean = consultaSolicitudCrediticiaBodyResponseBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaSolicitudCrediticiaBodyResponseBean;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public ConsultaSolicitudCrediticiaBodyResponseBean getConsultaSolicitudCrediticiaBodyResponseBean() {
        return this.consultaSolicitudCrediticiaBodyResponseBean;
    }

    public void setConsultaSolicitudCrediticiaBodyResponseBean(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean2) {
        this.consultaSolicitudCrediticiaBodyResponseBean = consultaSolicitudCrediticiaBodyResponseBean2;
    }
}
