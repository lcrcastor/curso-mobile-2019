package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CnsDeudaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CnsDeudaBodyRequestBean cnsDeudaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public CnsDeudaRequestBean() {
    }

    public CnsDeudaRequestBean(PrivateHeaderBean privateHeaderBean, CnsDeudaBodyRequestBean cnsDeudaBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaBodyRequestBean = cnsDeudaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
