package ar.com.santander.rio.mbanking.services.soap.beans;

import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaRecargaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import com.google.gson.annotations.SerializedName;
import com.indra.httpclient.beans.IBeanWS;

public class CnsDeudaRecargaRequestBean extends BaseResponseBean implements IBeanWS {
    @SerializedName("body")
    public CnsDeudaRecargaBodyRequestBean cnsDeudaRecargaBodyRequestBean;
    @SerializedName("header")
    public PrivateHeaderBean headerBean;

    public CnsDeudaRecargaRequestBean() {
    }

    public CnsDeudaRecargaRequestBean(PrivateHeaderBean privateHeaderBean, CnsDeudaRecargaBodyRequestBean cnsDeudaRecargaBodyRequestBean2) {
        this.headerBean = privateHeaderBean;
        this.cnsDeudaRecargaBodyRequestBean = cnsDeudaRecargaBodyRequestBean2;
    }

    public Class getClassBean() {
        return getClass();
    }
}
