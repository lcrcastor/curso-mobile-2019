package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class SuscribirFondoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    private PrivateHeaderBean headerBean;
    @SerializedName("body")
    private SuscribirFondoBodyRequestBean suscribirFondoBodyRequestBean;

    public SuscribirFondoRequestBean(PrivateHeaderBean privateHeaderBean, SuscribirFondoBodyRequestBean suscribirFondoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.suscribirFondoBodyRequestBean = suscribirFondoBodyRequestBean2;
    }

    public SuscribirFondoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
