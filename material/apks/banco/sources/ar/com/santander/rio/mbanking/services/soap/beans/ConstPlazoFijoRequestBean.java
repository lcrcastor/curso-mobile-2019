package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ConstPlazoFijoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class ConstPlazoFijoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public ConstPlazoFijoRequestBean() {
    }

    public ConstPlazoFijoRequestBean(PrivateHeaderBean privateHeaderBean, ConstPlazoFijoBodyRequestBean constPlazoFijoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.constPlazoFijoBodyRequestBean = constPlazoFijoBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
