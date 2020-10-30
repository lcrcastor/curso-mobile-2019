package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanErroWS;
import com.indra.httpclient.beans.IBeanWS;

public class SuscribirFondoResponseBean extends BaseResponseBean implements IBeanErroWS, IBeanWS {
    @SerializedName("header")
    public PrivateHeaderBean headerBean;
    @SerializedName("body")
    public SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean;

    public SuscribirFondoResponseBean(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean2) {
        this.suscribirFondoBodyResponseBean = suscribirFondoBodyResponseBean2;
    }

    public SuscribirFondoResponseBean() {
    }

    public Object getErrorBeanObject() {
        return this.suscribirFondoBodyResponseBean;
    }

    public Class getClassBean() {
        return getClass();
    }

    public SuscribirFondoBodyResponseBean getSuscribirFondoBodyResponseBean() {
        return this.suscribirFondoBodyResponseBean;
    }

    public void setSuscribirFondoBodyResponseBean(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean2) {
        this.suscribirFondoBodyResponseBean = suscribirFondoBodyResponseBean2;
    }
}
