package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class SuscripcionWomenResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SuscripcionWomenBodyResponseBean suscripcionWomenBodyResponseBean;

    public SuscripcionWomenResponseBean(SuscripcionWomenBodyResponseBean suscripcionWomenBodyResponseBean2) {
        this.suscripcionWomenBodyResponseBean = suscripcionWomenBodyResponseBean2;
    }

    public SuscripcionWomenResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.suscripcionWomenBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public SuscripcionWomenBodyResponseBean getSuscripcionWomenBodyResponseBean() {
        return this.suscripcionWomenBodyResponseBean;
    }

    public void setSuscripcionWomenBodyResponseBean(SuscripcionWomenBodyResponseBean suscripcionWomenBodyResponseBean2) {
        this.suscripcionWomenBodyResponseBean = suscripcionWomenBodyResponseBean2;
    }

    public PrivateHeaderBean getHeaderBean() {
        return this.headerBean;
    }

    public void setHeaderBean(PrivateHeaderBean privateHeaderBean) {
        this.headerBean = privateHeaderBean;
    }
}
