package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleFondoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class GetDetalleFondoRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    private GetDetalleFondoBodyRequestBean getDetalleFondoBodyRequestBean;
    @SerializedName("header")
    private PrivateHeaderBean headerBean;

    public GetDetalleFondoRequestBean(PrivateHeaderBean privateHeaderBean, GetDetalleFondoBodyRequestBean getDetalleFondoBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.getDetalleFondoBodyRequestBean = getDetalleFondoBodyRequestBean2;
    }

    public GetDetalleFondoRequestBean() {
    }

    public Class getClassBean() {
        return getClass();
    }
}
