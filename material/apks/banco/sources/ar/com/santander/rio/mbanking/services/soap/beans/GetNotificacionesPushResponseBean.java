package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNotificacionesPushBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class GetNotificacionesPushResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("body")
    private GetNotificacionesPushBodyResponseBean getNotificacionesPushBodyResponseBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetNotificacionesPushResponseBean(GetNotificacionesPushBodyResponseBean getNotificacionesPushBodyResponseBean2) {
        this.getNotificacionesPushBodyResponseBean = getNotificacionesPushBodyResponseBean2;
    }

    public GetNotificacionesPushResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.getNotificacionesPushBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public GetNotificacionesPushBodyResponseBean getNotificacionesPushBodyResponseBean() {
        return this.getNotificacionesPushBodyResponseBean;
    }

    public void setGetNotificacionesPushBodyResponseBean(GetNotificacionesPushBodyResponseBean getNotificacionesPushBodyResponseBean2) {
        this.getNotificacionesPushBodyResponseBean = getNotificacionesPushBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
