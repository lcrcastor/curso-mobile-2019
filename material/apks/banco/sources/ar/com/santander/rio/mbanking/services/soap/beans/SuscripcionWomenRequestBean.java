package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class SuscripcionWomenRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    private PrivateHeaderBean headerBean;
    @SerializedName("body")
    private SuscripcionWomenBodyRequestBean suscripcionWomenBodyRequestBean;

    public SuscripcionWomenRequestBean() {
    }

    public SuscripcionWomenRequestBean(PrivateHeaderBean privateHeaderBean, SuscripcionWomenBodyRequestBean suscripcionWomenBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.suscripcionWomenBodyRequestBean = suscripcionWomenBodyRequestBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }

    public SuscripcionWomenBodyRequestBean getSuscripcionWomenBodyRequestBean() {
        return this.suscripcionWomenBodyRequestBean;
    }

    public void setSuscripcionWomenBodyRequestBean(SuscripcionWomenBodyRequestBean suscripcionWomenBodyRequestBean2) {
        this.suscripcionWomenBodyRequestBean = suscripcionWomenBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
