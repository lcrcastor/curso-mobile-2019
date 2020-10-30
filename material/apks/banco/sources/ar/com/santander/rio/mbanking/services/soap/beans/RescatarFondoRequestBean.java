package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class RescatarFondoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("header")
    private PrivateHeaderBean headerBean;
    @SerializedName("body")
    private RescatarFondoBodyRequestBean rescatarFondoBodyRequestBean;

    public RescatarFondoRequestBean(PrivateHeaderBean privateHeaderBean, RescatarFondoBodyRequestBean rescatarFondoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.rescatarFondoBodyRequestBean = rescatarFondoBodyRequestBean2;
    }

    public RescatarFondoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
