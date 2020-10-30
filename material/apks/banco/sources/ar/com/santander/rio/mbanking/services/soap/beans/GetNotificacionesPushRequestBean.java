package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNotificacionesPushBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetNotificacionesPushRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public GetNotificacionesPushBodyRequestBean getNotificacionesPushBodyRequestBean;
    @SerializedName("header")
    public HeaderBean headerBean;

    public GetNotificacionesPushRequestBean(HeaderBean headerBean2, GetNotificacionesPushBodyRequestBean getNotificacionesPushBodyRequestBean2) {
        this.headerBean = headerBean2;
        this.getNotificacionesPushBodyRequestBean = getNotificacionesPushBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetNotificacionesPushBodyRequestBean getGetNotificacionesPushBodyRequestBean() {
        return this.getNotificacionesPushBodyRequestBean;
    }

    public void setGetNotificacionesPushBodyRequestBean(GetNotificacionesPushBodyRequestBean getNotificacionesPushBodyRequestBean2) {
        this.getNotificacionesPushBodyRequestBean = getNotificacionesPushBodyRequestBean2;
    }
}
