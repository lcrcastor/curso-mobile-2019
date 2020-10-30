package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetMovimientosFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetMovimientosFondoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetMovimientosFondoBodyRequestBean getMovimientosFondoBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetMovimientosFondoRequestBean(PrivateHeaderBean privateHeaderBean, GetMovimientosFondoBodyRequestBean getMovimientosFondoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getMovimientosFondoBodyRequestBean = getMovimientosFondoBodyRequestBean2;
    }

    public GetMovimientosFondoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
