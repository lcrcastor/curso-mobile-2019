package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConsultaSolicitudCrediticiaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConsultaSolicitudCrediticiaRequestBean() {
    }

    public ConsultaSolicitudCrediticiaRequestBean(PrivateHeaderBean privateHeaderBean, ConsultaSolicitudCrediticiaBodyRequestBean consultaSolicitudCrediticiaBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.consultaSolicitudCrediticiaBodyRequestBean = consultaSolicitudCrediticiaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
