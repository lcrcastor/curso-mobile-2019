package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaSolicitudCrediticiaResponseBeanProd extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    public ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd;
    @SerializedName("header")
    public HeaderBean headerBean;

    public ConsultaSolicitudCrediticiaResponseBeanProd() {
        this.headerBean = new HeaderBean();
        this.consultaSolicitudCrediticiaBodyResponseBeanProd = new ConsultaSolicitudCrediticiaBodyResponseBeanProd();
    }

    public ConsultaSolicitudCrediticiaResponseBeanProd(ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd2) {
        this.consultaSolicitudCrediticiaBodyResponseBeanProd = consultaSolicitudCrediticiaBodyResponseBeanProd2;
    }

    public ConsultaSolicitudCrediticiaResponseBeanProd(HeaderBean headerBean2, ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd2) {
        this.headerBean = headerBean2;
        this.consultaSolicitudCrediticiaBodyResponseBeanProd = consultaSolicitudCrediticiaBodyResponseBeanProd2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public Object getErrorBeanObject() {
        return this.consultaSolicitudCrediticiaBodyResponseBeanProd;
    }

    public HeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(HeaderBean headerBean2) {
        this.headerBean = headerBean2;
    }

    public ConsultaSolicitudCrediticiaBodyResponseBeanProd getConsultaSolicitudCrediticiaBodyResponseBeanProd() {
        return this.consultaSolicitudCrediticiaBodyResponseBeanProd;
    }

    public void setConsultaSolicitudCrediticiaBodyResponseBeanProd(ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd2) {
        this.consultaSolicitudCrediticiaBodyResponseBeanProd = consultaSolicitudCrediticiaBodyResponseBeanProd2;
    }
}
