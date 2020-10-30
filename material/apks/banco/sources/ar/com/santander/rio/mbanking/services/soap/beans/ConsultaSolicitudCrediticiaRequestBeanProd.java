package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaSolicitudCrediticiaRequestBeanProd extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaSolicitudCrediticiaRequestBeanProd() {
    }

    public ConsultaSolicitudCrediticiaRequestBeanProd(PrivateHeaderBean privateHeaderBean, ConsultaSolicitudCrediticiaBodyRequestBeanProd consultaSolicitudCrediticiaBodyRequestBeanProd2) {
        this.headerBean = privateHeaderBean;
        this.consultaSolicitudCrediticiaBodyRequestBeanProd = consultaSolicitudCrediticiaBodyRequestBeanProd2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
